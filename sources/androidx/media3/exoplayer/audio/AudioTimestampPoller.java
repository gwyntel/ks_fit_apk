package androidx.media3.exoplayer.audio;

import android.media.AudioTimestamp;
import android.media.AudioTrack;
import androidx.annotation.Nullable;
import androidx.media3.common.C;

/* loaded from: classes.dex */
final class AudioTimestampPoller {
    private static final int ERROR_POLL_INTERVAL_US = 500000;
    private static final int FAST_POLL_INTERVAL_US = 10000;
    private static final int INITIALIZING_DURATION_US = 500000;
    private static final int SLOW_POLL_INTERVAL_US = 10000000;
    private static final int STATE_ERROR = 4;
    private static final int STATE_INITIALIZING = 0;
    private static final int STATE_NO_TIMESTAMP = 3;
    private static final int STATE_TIMESTAMP = 1;
    private static final int STATE_TIMESTAMP_ADVANCING = 2;

    @Nullable
    private final AudioTimestampWrapper audioTimestamp;
    private long initialTimestampPositionFrames;
    private long initializeSystemTimeUs;
    private long lastTimestampSampleTimeUs;
    private long sampleIntervalUs;
    private int state;

    private static final class AudioTimestampWrapper {
        private long accumulatedRawTimestampFramePosition;
        private final AudioTimestamp audioTimestamp = new AudioTimestamp();
        private final AudioTrack audioTrack;
        private boolean expectTimestampFramePositionReset;
        private long lastTimestampPositionFrames;
        private long lastTimestampRawPositionFrames;
        private long rawTimestampFramePositionWrapCount;

        public AudioTimestampWrapper(AudioTrack audioTrack) {
            this.audioTrack = audioTrack;
        }

        public void expectTimestampFramePositionReset() {
            this.expectTimestampFramePositionReset = true;
        }

        public long getTimestampPositionFrames() {
            return this.lastTimestampPositionFrames;
        }

        public long getTimestampSystemTimeUs() {
            return this.audioTimestamp.nanoTime / 1000;
        }

        public boolean maybeUpdateTimestamp() {
            boolean timestamp = this.audioTrack.getTimestamp(this.audioTimestamp);
            if (timestamp) {
                long j2 = this.audioTimestamp.framePosition;
                long j3 = this.lastTimestampRawPositionFrames;
                if (j3 > j2) {
                    if (this.expectTimestampFramePositionReset) {
                        this.accumulatedRawTimestampFramePosition += j3;
                        this.expectTimestampFramePositionReset = false;
                    } else {
                        this.rawTimestampFramePositionWrapCount++;
                    }
                }
                this.lastTimestampRawPositionFrames = j2;
                this.lastTimestampPositionFrames = j2 + this.accumulatedRawTimestampFramePosition + (this.rawTimestampFramePositionWrapCount << 32);
            }
            return timestamp;
        }
    }

    public AudioTimestampPoller(AudioTrack audioTrack) {
        this.audioTimestamp = new AudioTimestampWrapper(audioTrack);
        reset();
    }

    private void updateState(int i2) {
        this.state = i2;
        if (i2 == 0) {
            this.lastTimestampSampleTimeUs = 0L;
            this.initialTimestampPositionFrames = -1L;
            this.initializeSystemTimeUs = System.nanoTime() / 1000;
            this.sampleIntervalUs = 10000L;
            return;
        }
        if (i2 == 1) {
            this.sampleIntervalUs = 10000L;
            return;
        }
        if (i2 == 2 || i2 == 3) {
            this.sampleIntervalUs = 10000000L;
        } else {
            if (i2 != 4) {
                throw new IllegalStateException();
            }
            this.sampleIntervalUs = 500000L;
        }
    }

    public void acceptTimestamp() {
        if (this.state == 4) {
            reset();
        }
    }

    public void expectTimestampFramePositionReset() {
        AudioTimestampWrapper audioTimestampWrapper = this.audioTimestamp;
        if (audioTimestampWrapper != null) {
            audioTimestampWrapper.expectTimestampFramePositionReset();
        }
    }

    public long getTimestampPositionFrames() {
        AudioTimestampWrapper audioTimestampWrapper = this.audioTimestamp;
        if (audioTimestampWrapper != null) {
            return audioTimestampWrapper.getTimestampPositionFrames();
        }
        return -1L;
    }

    public long getTimestampSystemTimeUs() {
        AudioTimestampWrapper audioTimestampWrapper = this.audioTimestamp;
        return audioTimestampWrapper != null ? audioTimestampWrapper.getTimestampSystemTimeUs() : C.TIME_UNSET;
    }

    public boolean hasAdvancingTimestamp() {
        return this.state == 2;
    }

    public boolean hasTimestamp() {
        int i2 = this.state;
        return i2 == 1 || i2 == 2;
    }

    public boolean maybePollTimestamp(long j2) {
        AudioTimestampWrapper audioTimestampWrapper = this.audioTimestamp;
        if (audioTimestampWrapper == null || j2 - this.lastTimestampSampleTimeUs < this.sampleIntervalUs) {
            return false;
        }
        this.lastTimestampSampleTimeUs = j2;
        boolean zMaybeUpdateTimestamp = audioTimestampWrapper.maybeUpdateTimestamp();
        int i2 = this.state;
        if (i2 != 0) {
            if (i2 != 1) {
                if (i2 != 2) {
                    if (i2 != 3) {
                        if (i2 != 4) {
                            throw new IllegalStateException();
                        }
                    } else if (zMaybeUpdateTimestamp) {
                        reset();
                    }
                } else if (!zMaybeUpdateTimestamp) {
                    reset();
                }
            } else if (!zMaybeUpdateTimestamp) {
                reset();
            } else if (this.audioTimestamp.getTimestampPositionFrames() > this.initialTimestampPositionFrames) {
                updateState(2);
            }
        } else if (zMaybeUpdateTimestamp) {
            if (this.audioTimestamp.getTimestampSystemTimeUs() < this.initializeSystemTimeUs) {
                return false;
            }
            this.initialTimestampPositionFrames = this.audioTimestamp.getTimestampPositionFrames();
            updateState(1);
        } else if (j2 - this.initializeSystemTimeUs > 500000) {
            updateState(3);
        }
        return zMaybeUpdateTimestamp;
    }

    public void rejectTimestamp() {
        updateState(4);
    }

    public void reset() {
        if (this.audioTimestamp != null) {
            updateState(0);
        }
    }
}
