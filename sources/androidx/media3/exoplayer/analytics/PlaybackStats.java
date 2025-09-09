package androidx.media3.exoplayer.analytics;

import androidx.annotation.Nullable;
import androidx.media3.common.C;
import androidx.media3.common.Format;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.analytics.AnalyticsListener;
import androidx.media3.exoplayer.rtsp.RtspMediaSource;
import java.util.Collections;
import java.util.List;

@UnstableApi
/* loaded from: classes.dex */
public final class PlaybackStats {
    public static final PlaybackStats EMPTY = merge(new PlaybackStats[0]);
    public static final int PLAYBACK_STATE_ABANDONED = 15;
    public static final int PLAYBACK_STATE_BUFFERING = 6;
    public static final int PLAYBACK_STATE_ENDED = 11;
    public static final int PLAYBACK_STATE_FAILED = 13;
    public static final int PLAYBACK_STATE_INTERRUPTED_BY_AD = 14;
    public static final int PLAYBACK_STATE_JOINING_BACKGROUND = 1;
    public static final int PLAYBACK_STATE_JOINING_FOREGROUND = 2;
    public static final int PLAYBACK_STATE_NOT_STARTED = 0;
    public static final int PLAYBACK_STATE_PAUSED = 4;
    public static final int PLAYBACK_STATE_PAUSED_BUFFERING = 7;
    public static final int PLAYBACK_STATE_PLAYING = 3;
    public static final int PLAYBACK_STATE_SEEKING = 5;
    public static final int PLAYBACK_STATE_STOPPED = 12;
    public static final int PLAYBACK_STATE_SUPPRESSED = 9;
    public static final int PLAYBACK_STATE_SUPPRESSED_BUFFERING = 10;
    public final int abandonedBeforeReadyCount;
    public final int adPlaybackCount;
    public final List<EventTimeAndFormat> audioFormatHistory;
    public final int backgroundJoiningCount;
    public final int endedCount;
    public final int fatalErrorCount;
    public final List<EventTimeAndException> fatalErrorHistory;
    public final int fatalErrorPlaybackCount;
    public final long firstReportedTimeMs;
    public final int foregroundPlaybackCount;
    public final int initialAudioFormatBitrateCount;
    public final int initialVideoFormatBitrateCount;
    public final int initialVideoFormatHeightCount;
    public final long maxRebufferTimeMs;
    public final List<long[]> mediaTimeHistory;
    public final int nonFatalErrorCount;
    public final List<EventTimeAndException> nonFatalErrorHistory;
    public final int playbackCount;
    private final long[] playbackStateDurationsMs;
    public final List<EventTimeAndPlaybackState> playbackStateHistory;
    public final long totalAudioFormatBitrateTimeProduct;
    public final long totalAudioFormatTimeMs;
    public final long totalAudioUnderruns;
    public final long totalBandwidthBytes;
    public final long totalBandwidthTimeMs;
    public final long totalDroppedFrames;
    public final long totalInitialAudioFormatBitrate;
    public final long totalInitialVideoFormatBitrate;
    public final int totalInitialVideoFormatHeight;
    public final int totalPauseBufferCount;
    public final int totalPauseCount;
    public final int totalRebufferCount;
    public final int totalSeekCount;
    public final long totalValidJoinTimeMs;
    public final long totalVideoFormatBitrateTimeMs;
    public final long totalVideoFormatBitrateTimeProduct;
    public final long totalVideoFormatHeightTimeMs;
    public final long totalVideoFormatHeightTimeProduct;
    public final int validJoinTimeCount;
    public final List<EventTimeAndFormat> videoFormatHistory;

    public static final class EventTimeAndException {
        public final AnalyticsListener.EventTime eventTime;
        public final Exception exception;

        public EventTimeAndException(AnalyticsListener.EventTime eventTime, Exception exc) {
            this.eventTime = eventTime;
            this.exception = exc;
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || EventTimeAndException.class != obj.getClass()) {
                return false;
            }
            EventTimeAndException eventTimeAndException = (EventTimeAndException) obj;
            if (this.eventTime.equals(eventTimeAndException.eventTime)) {
                return this.exception.equals(eventTimeAndException.exception);
            }
            return false;
        }

        public int hashCode() {
            return (this.eventTime.hashCode() * 31) + this.exception.hashCode();
        }
    }

    public static final class EventTimeAndFormat {
        public final AnalyticsListener.EventTime eventTime;

        @Nullable
        public final Format format;

        public EventTimeAndFormat(AnalyticsListener.EventTime eventTime, @Nullable Format format) {
            this.eventTime = eventTime;
            this.format = format;
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || EventTimeAndFormat.class != obj.getClass()) {
                return false;
            }
            EventTimeAndFormat eventTimeAndFormat = (EventTimeAndFormat) obj;
            if (!this.eventTime.equals(eventTimeAndFormat.eventTime)) {
                return false;
            }
            Format format = this.format;
            Format format2 = eventTimeAndFormat.format;
            return format != null ? format.equals(format2) : format2 == null;
        }

        public int hashCode() {
            int iHashCode = this.eventTime.hashCode() * 31;
            Format format = this.format;
            return iHashCode + (format != null ? format.hashCode() : 0);
        }
    }

    public static final class EventTimeAndPlaybackState {
        public final AnalyticsListener.EventTime eventTime;
        public final int playbackState;

        public EventTimeAndPlaybackState(AnalyticsListener.EventTime eventTime, int i2) {
            this.eventTime = eventTime;
            this.playbackState = i2;
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || EventTimeAndPlaybackState.class != obj.getClass()) {
                return false;
            }
            EventTimeAndPlaybackState eventTimeAndPlaybackState = (EventTimeAndPlaybackState) obj;
            if (this.playbackState != eventTimeAndPlaybackState.playbackState) {
                return false;
            }
            return this.eventTime.equals(eventTimeAndPlaybackState.eventTime);
        }

        public int hashCode() {
            return (this.eventTime.hashCode() * 31) + this.playbackState;
        }
    }

    PlaybackStats(int i2, long[] jArr, List list, List list2, long j2, int i3, int i4, int i5, int i6, long j3, int i7, int i8, int i9, int i10, int i11, long j4, int i12, List list3, List list4, long j5, long j6, long j7, long j8, long j9, long j10, int i13, int i14, int i15, long j11, int i16, long j12, long j13, long j14, long j15, long j16, int i17, int i18, int i19, List list5, List list6) {
        this.playbackCount = i2;
        this.playbackStateDurationsMs = jArr;
        this.playbackStateHistory = Collections.unmodifiableList(list);
        this.mediaTimeHistory = Collections.unmodifiableList(list2);
        this.firstReportedTimeMs = j2;
        this.foregroundPlaybackCount = i3;
        this.abandonedBeforeReadyCount = i4;
        this.endedCount = i5;
        this.backgroundJoiningCount = i6;
        this.totalValidJoinTimeMs = j3;
        this.validJoinTimeCount = i7;
        this.totalPauseCount = i8;
        this.totalPauseBufferCount = i9;
        this.totalSeekCount = i10;
        this.totalRebufferCount = i11;
        this.maxRebufferTimeMs = j4;
        this.adPlaybackCount = i12;
        this.videoFormatHistory = Collections.unmodifiableList(list3);
        this.audioFormatHistory = Collections.unmodifiableList(list4);
        this.totalVideoFormatHeightTimeMs = j5;
        this.totalVideoFormatHeightTimeProduct = j6;
        this.totalVideoFormatBitrateTimeMs = j7;
        this.totalVideoFormatBitrateTimeProduct = j8;
        this.totalAudioFormatTimeMs = j9;
        this.totalAudioFormatBitrateTimeProduct = j10;
        this.initialVideoFormatHeightCount = i13;
        this.initialVideoFormatBitrateCount = i14;
        this.totalInitialVideoFormatHeight = i15;
        this.totalInitialVideoFormatBitrate = j11;
        this.initialAudioFormatBitrateCount = i16;
        this.totalInitialAudioFormatBitrate = j12;
        this.totalBandwidthTimeMs = j13;
        this.totalBandwidthBytes = j14;
        this.totalDroppedFrames = j15;
        this.totalAudioUnderruns = j16;
        this.fatalErrorPlaybackCount = i17;
        this.fatalErrorCount = i18;
        this.nonFatalErrorCount = i19;
        this.fatalErrorHistory = Collections.unmodifiableList(list5);
        this.nonFatalErrorHistory = Collections.unmodifiableList(list6);
    }

    public static PlaybackStats merge(PlaybackStats... playbackStatsArr) {
        int i2;
        int i3 = 16;
        long[] jArr = new long[16];
        int length = playbackStatsArr.length;
        long j2 = 0;
        long j3 = 0;
        long j4 = 0;
        long j5 = 0;
        long j6 = 0;
        long j7 = 0;
        long j8 = 0;
        long j9 = 0;
        long j10 = 0;
        long j11 = 0;
        int i4 = 0;
        int i5 = 0;
        int i6 = -1;
        long jMax = C.TIME_UNSET;
        long jMin = C.TIME_UNSET;
        int i7 = 0;
        int i8 = 0;
        int i9 = 0;
        int i10 = 0;
        long j12 = C.TIME_UNSET;
        int i11 = 0;
        int i12 = 0;
        int i13 = 0;
        int i14 = 0;
        int i15 = 0;
        int i16 = 0;
        int i17 = 0;
        int i18 = 0;
        long j13 = -1;
        int i19 = 0;
        long j14 = -1;
        int i20 = 0;
        int i21 = 0;
        int i22 = 0;
        while (i4 < length) {
            PlaybackStats playbackStats = playbackStatsArr[i4];
            i5 += playbackStats.playbackCount;
            for (int i23 = 0; i23 < i3; i23++) {
                jArr[i23] = jArr[i23] + playbackStats.playbackStateDurationsMs[i23];
            }
            if (jMin == C.TIME_UNSET) {
                jMin = playbackStats.firstReportedTimeMs;
                i2 = length;
            } else {
                i2 = length;
                long j15 = playbackStats.firstReportedTimeMs;
                if (j15 != C.TIME_UNSET) {
                    jMin = Math.min(jMin, j15);
                }
            }
            i7 += playbackStats.foregroundPlaybackCount;
            i8 += playbackStats.abandonedBeforeReadyCount;
            i9 += playbackStats.endedCount;
            i10 += playbackStats.backgroundJoiningCount;
            if (j12 == C.TIME_UNSET) {
                j12 = playbackStats.totalValidJoinTimeMs;
            } else {
                long j16 = playbackStats.totalValidJoinTimeMs;
                if (j16 != C.TIME_UNSET) {
                    j12 += j16;
                }
            }
            i11 += playbackStats.validJoinTimeCount;
            i12 += playbackStats.totalPauseCount;
            i13 += playbackStats.totalPauseBufferCount;
            i14 += playbackStats.totalSeekCount;
            i15 += playbackStats.totalRebufferCount;
            if (jMax == C.TIME_UNSET) {
                jMax = playbackStats.maxRebufferTimeMs;
            } else {
                long j17 = playbackStats.maxRebufferTimeMs;
                if (j17 != C.TIME_UNSET) {
                    jMax = Math.max(jMax, j17);
                }
            }
            i16 += playbackStats.adPlaybackCount;
            j2 += playbackStats.totalVideoFormatHeightTimeMs;
            j3 += playbackStats.totalVideoFormatHeightTimeProduct;
            j4 += playbackStats.totalVideoFormatBitrateTimeMs;
            j5 += playbackStats.totalVideoFormatBitrateTimeProduct;
            j6 += playbackStats.totalAudioFormatTimeMs;
            j7 += playbackStats.totalAudioFormatBitrateTimeProduct;
            i17 += playbackStats.initialVideoFormatHeightCount;
            i18 += playbackStats.initialVideoFormatBitrateCount;
            if (i6 == -1) {
                i6 = playbackStats.totalInitialVideoFormatHeight;
            } else {
                int i24 = playbackStats.totalInitialVideoFormatHeight;
                if (i24 != -1) {
                    i6 += i24;
                }
            }
            if (j13 == -1) {
                j13 = playbackStats.totalInitialVideoFormatBitrate;
            } else {
                long j18 = playbackStats.totalInitialVideoFormatBitrate;
                if (j18 != -1) {
                    j13 += j18;
                }
            }
            i19 += playbackStats.initialAudioFormatBitrateCount;
            if (j14 == -1) {
                j14 = playbackStats.totalInitialAudioFormatBitrate;
            } else {
                long j19 = playbackStats.totalInitialAudioFormatBitrate;
                if (j19 != -1) {
                    j14 += j19;
                }
            }
            j8 += playbackStats.totalBandwidthTimeMs;
            j9 += playbackStats.totalBandwidthBytes;
            j10 += playbackStats.totalDroppedFrames;
            j11 += playbackStats.totalAudioUnderruns;
            i20 += playbackStats.fatalErrorPlaybackCount;
            i21 += playbackStats.fatalErrorCount;
            i22 += playbackStats.nonFatalErrorCount;
            i4++;
            length = i2;
            i3 = 16;
        }
        return new PlaybackStats(i5, jArr, Collections.emptyList(), Collections.emptyList(), jMin, i7, i8, i9, i10, j12, i11, i12, i13, i14, i15, jMax, i16, Collections.emptyList(), Collections.emptyList(), j2, j3, j4, j5, j6, j7, i17, i18, i6, j13, i19, j14, j8, j9, j10, j11, i20, i21, i22, Collections.emptyList(), Collections.emptyList());
    }

    public float getAbandonedBeforeReadyRatio() {
        int i2 = this.abandonedBeforeReadyCount;
        int i3 = this.playbackCount;
        int i4 = this.foregroundPlaybackCount;
        int i5 = i2 - (i3 - i4);
        if (i4 == 0) {
            return 0.0f;
        }
        return i5 / i4;
    }

    public float getAudioUnderrunRate() {
        long totalPlayTimeMs = getTotalPlayTimeMs();
        if (totalPlayTimeMs == 0) {
            return 0.0f;
        }
        return (this.totalAudioUnderruns * 1000.0f) / totalPlayTimeMs;
    }

    public float getDroppedFramesRate() {
        long totalPlayTimeMs = getTotalPlayTimeMs();
        if (totalPlayTimeMs == 0) {
            return 0.0f;
        }
        return (this.totalDroppedFrames * 1000.0f) / totalPlayTimeMs;
    }

    public float getEndedRatio() {
        int i2 = this.foregroundPlaybackCount;
        if (i2 == 0) {
            return 0.0f;
        }
        return this.endedCount / i2;
    }

    public float getFatalErrorRate() {
        long totalPlayTimeMs = getTotalPlayTimeMs();
        if (totalPlayTimeMs == 0) {
            return 0.0f;
        }
        return (this.fatalErrorCount * 1000.0f) / totalPlayTimeMs;
    }

    public float getFatalErrorRatio() {
        int i2 = this.foregroundPlaybackCount;
        if (i2 == 0) {
            return 0.0f;
        }
        return this.fatalErrorPlaybackCount / i2;
    }

    public float getJoinTimeRatio() {
        long totalPlayAndWaitTimeMs = getTotalPlayAndWaitTimeMs();
        if (totalPlayAndWaitTimeMs == 0) {
            return 0.0f;
        }
        return getTotalJoinTimeMs() / totalPlayAndWaitTimeMs;
    }

    public int getMeanAudioFormatBitrate() {
        long j2 = this.totalAudioFormatTimeMs;
        if (j2 == 0) {
            return -1;
        }
        return (int) (this.totalAudioFormatBitrateTimeProduct / j2);
    }

    public int getMeanBandwidth() {
        long j2 = this.totalBandwidthTimeMs;
        if (j2 == 0) {
            return -1;
        }
        return (int) ((this.totalBandwidthBytes * RtspMediaSource.DEFAULT_TIMEOUT_MS) / j2);
    }

    public long getMeanElapsedTimeMs() {
        return this.playbackCount == 0 ? C.TIME_UNSET : getTotalElapsedTimeMs() / this.playbackCount;
    }

    public int getMeanInitialAudioFormatBitrate() {
        int i2 = this.initialAudioFormatBitrateCount;
        if (i2 == 0) {
            return -1;
        }
        return (int) (this.totalInitialAudioFormatBitrate / i2);
    }

    public int getMeanInitialVideoFormatBitrate() {
        int i2 = this.initialVideoFormatBitrateCount;
        if (i2 == 0) {
            return -1;
        }
        return (int) (this.totalInitialVideoFormatBitrate / i2);
    }

    public int getMeanInitialVideoFormatHeight() {
        int i2 = this.initialVideoFormatHeightCount;
        if (i2 == 0) {
            return -1;
        }
        return this.totalInitialVideoFormatHeight / i2;
    }

    public long getMeanJoinTimeMs() {
        int i2 = this.validJoinTimeCount;
        return i2 == 0 ? C.TIME_UNSET : this.totalValidJoinTimeMs / i2;
    }

    public float getMeanNonFatalErrorCount() {
        int i2 = this.foregroundPlaybackCount;
        if (i2 == 0) {
            return 0.0f;
        }
        return this.nonFatalErrorCount / i2;
    }

    public float getMeanPauseBufferCount() {
        int i2 = this.foregroundPlaybackCount;
        if (i2 == 0) {
            return 0.0f;
        }
        return this.totalPauseBufferCount / i2;
    }

    public float getMeanPauseCount() {
        int i2 = this.foregroundPlaybackCount;
        if (i2 == 0) {
            return 0.0f;
        }
        return this.totalPauseCount / i2;
    }

    public long getMeanPausedTimeMs() {
        return this.foregroundPlaybackCount == 0 ? C.TIME_UNSET : getTotalPausedTimeMs() / this.foregroundPlaybackCount;
    }

    public long getMeanPlayAndWaitTimeMs() {
        return this.foregroundPlaybackCount == 0 ? C.TIME_UNSET : getTotalPlayAndWaitTimeMs() / this.foregroundPlaybackCount;
    }

    public long getMeanPlayTimeMs() {
        return this.foregroundPlaybackCount == 0 ? C.TIME_UNSET : getTotalPlayTimeMs() / this.foregroundPlaybackCount;
    }

    public float getMeanRebufferCount() {
        int i2 = this.foregroundPlaybackCount;
        if (i2 == 0) {
            return 0.0f;
        }
        return this.totalRebufferCount / i2;
    }

    public long getMeanRebufferTimeMs() {
        return this.foregroundPlaybackCount == 0 ? C.TIME_UNSET : getTotalRebufferTimeMs() / this.foregroundPlaybackCount;
    }

    public float getMeanSeekCount() {
        int i2 = this.foregroundPlaybackCount;
        if (i2 == 0) {
            return 0.0f;
        }
        return this.totalSeekCount / i2;
    }

    public long getMeanSeekTimeMs() {
        return this.foregroundPlaybackCount == 0 ? C.TIME_UNSET : getTotalSeekTimeMs() / this.foregroundPlaybackCount;
    }

    public long getMeanSingleRebufferTimeMs() {
        return this.totalRebufferCount == 0 ? C.TIME_UNSET : (getPlaybackStateDurationMs(6) + getPlaybackStateDurationMs(7)) / this.totalRebufferCount;
    }

    public long getMeanSingleSeekTimeMs() {
        return this.totalSeekCount == 0 ? C.TIME_UNSET : getTotalSeekTimeMs() / this.totalSeekCount;
    }

    public float getMeanTimeBetweenFatalErrors() {
        return 1.0f / getFatalErrorRate();
    }

    public float getMeanTimeBetweenNonFatalErrors() {
        return 1.0f / getNonFatalErrorRate();
    }

    public float getMeanTimeBetweenRebuffers() {
        return 1.0f / getRebufferRate();
    }

    public int getMeanVideoFormatBitrate() {
        long j2 = this.totalVideoFormatBitrateTimeMs;
        if (j2 == 0) {
            return -1;
        }
        return (int) (this.totalVideoFormatBitrateTimeProduct / j2);
    }

    public int getMeanVideoFormatHeight() {
        long j2 = this.totalVideoFormatHeightTimeMs;
        if (j2 == 0) {
            return -1;
        }
        return (int) (this.totalVideoFormatHeightTimeProduct / j2);
    }

    public long getMeanWaitTimeMs() {
        return this.foregroundPlaybackCount == 0 ? C.TIME_UNSET : getTotalWaitTimeMs() / this.foregroundPlaybackCount;
    }

    public long getMediaTimeMsAtRealtimeMs(long j2) {
        if (this.mediaTimeHistory.isEmpty()) {
            return C.TIME_UNSET;
        }
        int i2 = 0;
        while (i2 < this.mediaTimeHistory.size() && this.mediaTimeHistory.get(i2)[0] <= j2) {
            i2++;
        }
        if (i2 == 0) {
            return this.mediaTimeHistory.get(0)[1];
        }
        if (i2 == this.mediaTimeHistory.size()) {
            List<long[]> list = this.mediaTimeHistory;
            return list.get(list.size() - 1)[1];
        }
        int i3 = i2 - 1;
        long j3 = this.mediaTimeHistory.get(i3)[0];
        long j4 = this.mediaTimeHistory.get(i3)[1];
        long j5 = this.mediaTimeHistory.get(i2)[0];
        long j6 = this.mediaTimeHistory.get(i2)[1];
        long j7 = j5 - j3;
        if (j7 == 0) {
            return j4;
        }
        return j4 + ((long) ((j6 - j4) * ((j2 - j3) / j7)));
    }

    public float getNonFatalErrorRate() {
        long totalPlayTimeMs = getTotalPlayTimeMs();
        if (totalPlayTimeMs == 0) {
            return 0.0f;
        }
        return (this.nonFatalErrorCount * 1000.0f) / totalPlayTimeMs;
    }

    public int getPlaybackStateAtTime(long j2) {
        int i2 = 0;
        for (EventTimeAndPlaybackState eventTimeAndPlaybackState : this.playbackStateHistory) {
            if (eventTimeAndPlaybackState.eventTime.realtimeMs > j2) {
                break;
            }
            i2 = eventTimeAndPlaybackState.playbackState;
        }
        return i2;
    }

    public long getPlaybackStateDurationMs(int i2) {
        return this.playbackStateDurationsMs[i2];
    }

    public float getRebufferRate() {
        long totalPlayTimeMs = getTotalPlayTimeMs();
        if (totalPlayTimeMs == 0) {
            return 0.0f;
        }
        return (this.totalRebufferCount * 1000.0f) / totalPlayTimeMs;
    }

    public float getRebufferTimeRatio() {
        long totalPlayAndWaitTimeMs = getTotalPlayAndWaitTimeMs();
        if (totalPlayAndWaitTimeMs == 0) {
            return 0.0f;
        }
        return getTotalRebufferTimeMs() / totalPlayAndWaitTimeMs;
    }

    public float getSeekTimeRatio() {
        long totalPlayAndWaitTimeMs = getTotalPlayAndWaitTimeMs();
        if (totalPlayAndWaitTimeMs == 0) {
            return 0.0f;
        }
        return getTotalSeekTimeMs() / totalPlayAndWaitTimeMs;
    }

    public long getTotalElapsedTimeMs() {
        long j2 = 0;
        for (int i2 = 0; i2 < 16; i2++) {
            j2 += this.playbackStateDurationsMs[i2];
        }
        return j2;
    }

    public long getTotalJoinTimeMs() {
        return getPlaybackStateDurationMs(2);
    }

    public long getTotalPausedTimeMs() {
        return getPlaybackStateDurationMs(4) + getPlaybackStateDurationMs(7);
    }

    public long getTotalPlayAndWaitTimeMs() {
        return getTotalPlayTimeMs() + getTotalWaitTimeMs();
    }

    public long getTotalPlayTimeMs() {
        return getPlaybackStateDurationMs(3);
    }

    public long getTotalRebufferTimeMs() {
        return getPlaybackStateDurationMs(6);
    }

    public long getTotalSeekTimeMs() {
        return getPlaybackStateDurationMs(5);
    }

    public long getTotalWaitTimeMs() {
        return getPlaybackStateDurationMs(2) + getPlaybackStateDurationMs(6) + getPlaybackStateDurationMs(5);
    }

    public float getWaitTimeRatio() {
        long totalPlayAndWaitTimeMs = getTotalPlayAndWaitTimeMs();
        if (totalPlayAndWaitTimeMs == 0) {
            return 0.0f;
        }
        return getTotalWaitTimeMs() / totalPlayAndWaitTimeMs;
    }
}
