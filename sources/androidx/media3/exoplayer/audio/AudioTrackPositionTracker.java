package androidx.media3.exoplayer.audio;

import android.media.AudioTrack;
import androidx.annotation.Nullable;
import androidx.media3.common.C;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.Clock;
import androidx.media3.common.util.Util;
import androidx.media3.exoplayer.dash.DashMediaSource;
import java.lang.reflect.Method;

/* loaded from: classes.dex */
final class AudioTrackPositionTracker {
    private static final long FORCE_RESET_WORKAROUND_TIMEOUT_MS = 200;
    private static final long MAX_AUDIO_TIMESTAMP_OFFSET_US = 5000000;
    private static final long MAX_LATENCY_US = 5000000;
    private static final int MAX_PLAYHEAD_OFFSET_COUNT = 10;
    private static final int MIN_LATENCY_SAMPLE_INTERVAL_US = 500000;
    private static final int MIN_PLAYHEAD_OFFSET_SAMPLE_INTERVAL_US = 30000;
    private static final long MODE_SWITCH_SMOOTHING_DURATION_US = 1000000;
    private static final int PLAYSTATE_PAUSED = 2;
    private static final int PLAYSTATE_PLAYING = 3;
    private static final int PLAYSTATE_STOPPED = 1;
    private static final long RAW_PLAYBACK_HEAD_POSITION_UPDATE_INTERVAL_MS = 5;

    @Nullable
    private AudioTimestampPoller audioTimestampPoller;

    @Nullable
    private AudioTrack audioTrack;
    private float audioTrackPlaybackSpeed;
    private int bufferSize;
    private long bufferSizeUs;
    private Clock clock;
    private long endPlaybackHeadPosition;
    private boolean expectRawPlaybackHeadReset;
    private long forceResetWorkaroundTimeMs;

    @Nullable
    private Method getLatencyMethod;
    private boolean hasData;
    private boolean isOutputPcm;
    private long lastLatencySampleTimeUs;
    private long lastPlayheadSampleTimeUs;
    private long lastPositionUs;
    private long lastRawPlaybackHeadPositionSampleTimeMs;
    private boolean lastSampleUsedGetTimestampMode;
    private long lastSystemTimeUs;
    private long latencyUs;
    private final Listener listener;
    private boolean needsPassthroughWorkarounds;
    private int nextPlayheadOffsetIndex;
    private boolean notifiedPositionIncreasing;
    private int outputPcmFrameSize;
    private int outputSampleRate;
    private long passthroughWorkaroundPauseOffset;
    private int playheadOffsetCount;
    private final long[] playheadOffsets;
    private long previousModePositionUs;
    private long previousModeSystemTimeUs;
    private long rawPlaybackHeadPosition;
    private long rawPlaybackHeadWrapCount;
    private long smoothedPlayheadOffsetUs;
    private long stopPlaybackHeadPosition;
    private long stopTimestampUs;
    private long sumRawPlaybackHeadPosition;

    public interface Listener {
        void onInvalidLatency(long j2);

        void onPositionAdvancing(long j2);

        void onPositionFramesMismatch(long j2, long j3, long j4, long j5);

        void onSystemTimeUsMismatch(long j2, long j3, long j4, long j5);

        void onUnderrun(int i2, long j2);
    }

    public AudioTrackPositionTracker(Listener listener) {
        this.listener = (Listener) Assertions.checkNotNull(listener);
        try {
            this.getLatencyMethod = AudioTrack.class.getMethod("getLatency", null);
        } catch (NoSuchMethodException unused) {
        }
        this.playheadOffsets = new long[10];
        this.clock = Clock.DEFAULT;
    }

    private boolean forceHasPendingData() {
        return this.needsPassthroughWorkarounds && ((AudioTrack) Assertions.checkNotNull(this.audioTrack)).getPlayState() == 2 && getPlaybackHeadPosition() == 0;
    }

    private long getPlaybackHeadPosition() {
        long jElapsedRealtime = this.clock.elapsedRealtime();
        if (this.stopTimestampUs != C.TIME_UNSET) {
            if (((AudioTrack) Assertions.checkNotNull(this.audioTrack)).getPlayState() == 2) {
                return this.stopPlaybackHeadPosition;
            }
            return Math.min(this.endPlaybackHeadPosition, this.stopPlaybackHeadPosition + Util.durationUsToSampleCount(Util.getMediaDurationForPlayoutDuration(Util.msToUs(jElapsedRealtime) - this.stopTimestampUs, this.audioTrackPlaybackSpeed), this.outputSampleRate));
        }
        if (jElapsedRealtime - this.lastRawPlaybackHeadPositionSampleTimeMs >= 5) {
            updateRawPlaybackHeadPosition(jElapsedRealtime);
            this.lastRawPlaybackHeadPositionSampleTimeMs = jElapsedRealtime;
        }
        return this.rawPlaybackHeadPosition + this.sumRawPlaybackHeadPosition + (this.rawPlaybackHeadWrapCount << 32);
    }

    private long getPlaybackHeadPositionUs() {
        return Util.sampleCountToDurationUs(getPlaybackHeadPosition(), this.outputSampleRate);
    }

    private void maybePollAndCheckTimestamp(long j2) {
        AudioTimestampPoller audioTimestampPoller = (AudioTimestampPoller) Assertions.checkNotNull(this.audioTimestampPoller);
        if (audioTimestampPoller.maybePollTimestamp(j2)) {
            long timestampSystemTimeUs = audioTimestampPoller.getTimestampSystemTimeUs();
            long timestampPositionFrames = audioTimestampPoller.getTimestampPositionFrames();
            long playbackHeadPositionUs = getPlaybackHeadPositionUs();
            if (Math.abs(timestampSystemTimeUs - j2) > DashMediaSource.MIN_LIVE_DEFAULT_START_POSITION_US) {
                this.listener.onSystemTimeUsMismatch(timestampPositionFrames, timestampSystemTimeUs, j2, playbackHeadPositionUs);
                audioTimestampPoller.rejectTimestamp();
            } else if (Math.abs(Util.sampleCountToDurationUs(timestampPositionFrames, this.outputSampleRate) - playbackHeadPositionUs) <= DashMediaSource.MIN_LIVE_DEFAULT_START_POSITION_US) {
                audioTimestampPoller.acceptTimestamp();
            } else {
                this.listener.onPositionFramesMismatch(timestampPositionFrames, timestampSystemTimeUs, j2, playbackHeadPositionUs);
                audioTimestampPoller.rejectTimestamp();
            }
        }
    }

    private void maybeSampleSyncParams() {
        long jNanoTime = this.clock.nanoTime() / 1000;
        if (jNanoTime - this.lastPlayheadSampleTimeUs >= 30000) {
            long playbackHeadPositionUs = getPlaybackHeadPositionUs();
            if (playbackHeadPositionUs != 0) {
                this.playheadOffsets[this.nextPlayheadOffsetIndex] = Util.getPlayoutDurationForMediaDuration(playbackHeadPositionUs, this.audioTrackPlaybackSpeed) - jNanoTime;
                this.nextPlayheadOffsetIndex = (this.nextPlayheadOffsetIndex + 1) % 10;
                int i2 = this.playheadOffsetCount;
                if (i2 < 10) {
                    this.playheadOffsetCount = i2 + 1;
                }
                this.lastPlayheadSampleTimeUs = jNanoTime;
                this.smoothedPlayheadOffsetUs = 0L;
                int i3 = 0;
                while (true) {
                    int i4 = this.playheadOffsetCount;
                    if (i3 >= i4) {
                        break;
                    }
                    this.smoothedPlayheadOffsetUs += this.playheadOffsets[i3] / i4;
                    i3++;
                }
            } else {
                return;
            }
        }
        if (this.needsPassthroughWorkarounds) {
            return;
        }
        maybePollAndCheckTimestamp(jNanoTime);
        maybeUpdateLatency(jNanoTime);
    }

    private void maybeUpdateLatency(long j2) {
        Method method;
        if (!this.isOutputPcm || (method = this.getLatencyMethod) == null || j2 - this.lastLatencySampleTimeUs < 500000) {
            return;
        }
        try {
            long jIntValue = (((Integer) Util.castNonNull((Integer) method.invoke(Assertions.checkNotNull(this.audioTrack), null))).intValue() * 1000) - this.bufferSizeUs;
            this.latencyUs = jIntValue;
            long jMax = Math.max(jIntValue, 0L);
            this.latencyUs = jMax;
            if (jMax > DashMediaSource.MIN_LIVE_DEFAULT_START_POSITION_US) {
                this.listener.onInvalidLatency(jMax);
                this.latencyUs = 0L;
            }
        } catch (Exception unused) {
            this.getLatencyMethod = null;
        }
        this.lastLatencySampleTimeUs = j2;
    }

    private static boolean needsPassthroughWorkarounds(int i2) {
        return Util.SDK_INT < 23 && (i2 == 5 || i2 == 6);
    }

    private void resetSyncParams() {
        this.smoothedPlayheadOffsetUs = 0L;
        this.playheadOffsetCount = 0;
        this.nextPlayheadOffsetIndex = 0;
        this.lastPlayheadSampleTimeUs = 0L;
        this.lastSystemTimeUs = 0L;
        this.previousModeSystemTimeUs = 0L;
        this.notifiedPositionIncreasing = false;
    }

    private void updateRawPlaybackHeadPosition(long j2) {
        int playState = ((AudioTrack) Assertions.checkNotNull(this.audioTrack)).getPlayState();
        if (playState == 1) {
            return;
        }
        long playbackHeadPosition = r0.getPlaybackHeadPosition() & 4294967295L;
        if (this.needsPassthroughWorkarounds) {
            if (playState == 2 && playbackHeadPosition == 0) {
                this.passthroughWorkaroundPauseOffset = this.rawPlaybackHeadPosition;
            }
            playbackHeadPosition += this.passthroughWorkaroundPauseOffset;
        }
        if (Util.SDK_INT <= 29) {
            if (playbackHeadPosition == 0 && this.rawPlaybackHeadPosition > 0 && playState == 3) {
                if (this.forceResetWorkaroundTimeMs == C.TIME_UNSET) {
                    this.forceResetWorkaroundTimeMs = j2;
                    return;
                }
                return;
            }
            this.forceResetWorkaroundTimeMs = C.TIME_UNSET;
        }
        long j3 = this.rawPlaybackHeadPosition;
        if (j3 > playbackHeadPosition) {
            if (this.expectRawPlaybackHeadReset) {
                this.sumRawPlaybackHeadPosition += j3;
                this.expectRawPlaybackHeadReset = false;
            } else {
                this.rawPlaybackHeadWrapCount++;
            }
        }
        this.rawPlaybackHeadPosition = playbackHeadPosition;
    }

    public void expectRawPlaybackHeadReset() {
        this.expectRawPlaybackHeadReset = true;
        AudioTimestampPoller audioTimestampPoller = this.audioTimestampPoller;
        if (audioTimestampPoller != null) {
            audioTimestampPoller.expectTimestampFramePositionReset();
        }
    }

    public int getAvailableBufferSize(long j2) {
        return this.bufferSize - ((int) (j2 - (getPlaybackHeadPosition() * this.outputPcmFrameSize)));
    }

    public long getCurrentPositionUs(boolean z2) {
        long playbackHeadPositionUs;
        if (((AudioTrack) Assertions.checkNotNull(this.audioTrack)).getPlayState() == 3) {
            maybeSampleSyncParams();
        }
        long jNanoTime = this.clock.nanoTime() / 1000;
        AudioTimestampPoller audioTimestampPoller = (AudioTimestampPoller) Assertions.checkNotNull(this.audioTimestampPoller);
        boolean zHasAdvancingTimestamp = audioTimestampPoller.hasAdvancingTimestamp();
        if (zHasAdvancingTimestamp) {
            playbackHeadPositionUs = Util.sampleCountToDurationUs(audioTimestampPoller.getTimestampPositionFrames(), this.outputSampleRate) + Util.getMediaDurationForPlayoutDuration(jNanoTime - audioTimestampPoller.getTimestampSystemTimeUs(), this.audioTrackPlaybackSpeed);
        } else {
            playbackHeadPositionUs = this.playheadOffsetCount == 0 ? getPlaybackHeadPositionUs() : Util.getMediaDurationForPlayoutDuration(this.smoothedPlayheadOffsetUs + jNanoTime, this.audioTrackPlaybackSpeed);
            if (!z2) {
                playbackHeadPositionUs = Math.max(0L, playbackHeadPositionUs - this.latencyUs);
            }
        }
        if (this.lastSampleUsedGetTimestampMode != zHasAdvancingTimestamp) {
            this.previousModeSystemTimeUs = this.lastSystemTimeUs;
            this.previousModePositionUs = this.lastPositionUs;
        }
        long j2 = jNanoTime - this.previousModeSystemTimeUs;
        if (j2 < 1000000) {
            long mediaDurationForPlayoutDuration = this.previousModePositionUs + Util.getMediaDurationForPlayoutDuration(j2, this.audioTrackPlaybackSpeed);
            long j3 = (j2 * 1000) / 1000000;
            playbackHeadPositionUs = ((playbackHeadPositionUs * j3) + ((1000 - j3) * mediaDurationForPlayoutDuration)) / 1000;
        }
        if (!this.notifiedPositionIncreasing) {
            long j4 = this.lastPositionUs;
            if (playbackHeadPositionUs > j4) {
                this.notifiedPositionIncreasing = true;
                this.listener.onPositionAdvancing(this.clock.currentTimeMillis() - Util.usToMs(Util.getPlayoutDurationForMediaDuration(Util.usToMs(playbackHeadPositionUs - j4), this.audioTrackPlaybackSpeed)));
            }
        }
        this.lastSystemTimeUs = jNanoTime;
        this.lastPositionUs = playbackHeadPositionUs;
        this.lastSampleUsedGetTimestampMode = zHasAdvancingTimestamp;
        return playbackHeadPositionUs;
    }

    public void handleEndOfStream(long j2) {
        this.stopPlaybackHeadPosition = getPlaybackHeadPosition();
        this.stopTimestampUs = Util.msToUs(this.clock.elapsedRealtime());
        this.endPlaybackHeadPosition = j2;
    }

    public boolean hasPendingData(long j2) {
        return j2 > Util.durationUsToSampleCount(getCurrentPositionUs(false), this.outputSampleRate) || forceHasPendingData();
    }

    public boolean isPlaying() {
        return ((AudioTrack) Assertions.checkNotNull(this.audioTrack)).getPlayState() == 3;
    }

    public boolean isStalled(long j2) {
        return this.forceResetWorkaroundTimeMs != C.TIME_UNSET && j2 > 0 && this.clock.elapsedRealtime() - this.forceResetWorkaroundTimeMs >= FORCE_RESET_WORKAROUND_TIMEOUT_MS;
    }

    public boolean mayHandleBuffer(long j2) {
        int playState = ((AudioTrack) Assertions.checkNotNull(this.audioTrack)).getPlayState();
        if (this.needsPassthroughWorkarounds) {
            if (playState == 2) {
                this.hasData = false;
                return false;
            }
            if (playState == 1 && getPlaybackHeadPosition() == 0) {
                return false;
            }
        }
        boolean z2 = this.hasData;
        boolean zHasPendingData = hasPendingData(j2);
        this.hasData = zHasPendingData;
        if (z2 && !zHasPendingData && playState != 1) {
            this.listener.onUnderrun(this.bufferSize, Util.usToMs(this.bufferSizeUs));
        }
        return true;
    }

    public boolean pause() {
        resetSyncParams();
        if (this.stopTimestampUs == C.TIME_UNSET) {
            ((AudioTimestampPoller) Assertions.checkNotNull(this.audioTimestampPoller)).reset();
            return true;
        }
        this.stopPlaybackHeadPosition = getPlaybackHeadPosition();
        return false;
    }

    public void reset() {
        resetSyncParams();
        this.audioTrack = null;
        this.audioTimestampPoller = null;
    }

    public void setAudioTrack(AudioTrack audioTrack, boolean z2, int i2, int i3, int i4) {
        this.audioTrack = audioTrack;
        this.outputPcmFrameSize = i3;
        this.bufferSize = i4;
        this.audioTimestampPoller = new AudioTimestampPoller(audioTrack);
        this.outputSampleRate = audioTrack.getSampleRate();
        this.needsPassthroughWorkarounds = z2 && needsPassthroughWorkarounds(i2);
        boolean zIsEncodingLinearPcm = Util.isEncodingLinearPcm(i2);
        this.isOutputPcm = zIsEncodingLinearPcm;
        this.bufferSizeUs = zIsEncodingLinearPcm ? Util.sampleCountToDurationUs(i4 / i3, this.outputSampleRate) : -9223372036854775807L;
        this.rawPlaybackHeadPosition = 0L;
        this.rawPlaybackHeadWrapCount = 0L;
        this.expectRawPlaybackHeadReset = false;
        this.sumRawPlaybackHeadPosition = 0L;
        this.passthroughWorkaroundPauseOffset = 0L;
        this.hasData = false;
        this.stopTimestampUs = C.TIME_UNSET;
        this.forceResetWorkaroundTimeMs = C.TIME_UNSET;
        this.lastLatencySampleTimeUs = 0L;
        this.latencyUs = 0L;
        this.audioTrackPlaybackSpeed = 1.0f;
    }

    public void setAudioTrackPlaybackSpeed(float f2) {
        this.audioTrackPlaybackSpeed = f2;
        AudioTimestampPoller audioTimestampPoller = this.audioTimestampPoller;
        if (audioTimestampPoller != null) {
            audioTimestampPoller.reset();
        }
        resetSyncParams();
    }

    public void setClock(Clock clock) {
        this.clock = clock;
    }

    public void start() {
        if (this.stopTimestampUs != C.TIME_UNSET) {
            this.stopTimestampUs = Util.msToUs(this.clock.elapsedRealtime());
        }
        ((AudioTimestampPoller) Assertions.checkNotNull(this.audioTimestampPoller)).reset();
    }
}
