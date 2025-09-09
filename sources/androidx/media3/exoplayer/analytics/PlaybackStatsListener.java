package androidx.media3.exoplayer.analytics;

import android.os.SystemClock;
import android.util.Pair;
import androidx.annotation.Nullable;
import androidx.media3.common.AudioAttributes;
import androidx.media3.common.C;
import androidx.media3.common.DeviceInfo;
import androidx.media3.common.Format;
import androidx.media3.common.MediaItem;
import androidx.media3.common.MediaMetadata;
import androidx.media3.common.Metadata;
import androidx.media3.common.PlaybackException;
import androidx.media3.common.PlaybackParameters;
import androidx.media3.common.Player;
import androidx.media3.common.Timeline;
import androidx.media3.common.TrackSelectionParameters;
import androidx.media3.common.Tracks;
import androidx.media3.common.VideoSize;
import androidx.media3.common.text.CueGroup;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.common.util.Util;
import androidx.media3.exoplayer.DecoderCounters;
import androidx.media3.exoplayer.DecoderReuseEvaluation;
import androidx.media3.exoplayer.analytics.AnalyticsListener;
import androidx.media3.exoplayer.analytics.PlaybackSessionManager;
import androidx.media3.exoplayer.analytics.PlaybackStats;
import androidx.media3.exoplayer.audio.AudioSink;
import androidx.media3.exoplayer.source.LoadEventInfo;
import androidx.media3.exoplayer.source.MediaLoadData;
import androidx.media3.exoplayer.source.MediaSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@UnstableApi
/* loaded from: classes.dex */
public final class PlaybackStatsListener implements AnalyticsListener, PlaybackSessionManager.Listener {

    @Nullable
    private Format audioFormat;
    private long bandwidthBytes;
    private long bandwidthTimeMs;

    @Nullable
    private final Callback callback;
    private long discontinuityFromPositionMs;

    @Nullable
    private String discontinuityFromSession;
    private int discontinuityReason;
    private int droppedFrames;
    private PlaybackStats finishedPlaybackStats;
    private final boolean keepHistory;

    @Nullable
    private Exception nonFatalException;
    private final Timeline.Period period;
    private final Map<String, PlaybackStatsTracker> playbackStatsTrackers;
    private final PlaybackSessionManager sessionManager;
    private final Map<String, AnalyticsListener.EventTime> sessionStartEventTimes;

    @Nullable
    private Format videoFormat;
    private VideoSize videoSize;

    public interface Callback {
        void onPlaybackStatsReady(AnalyticsListener.EventTime eventTime, PlaybackStats playbackStats);
    }

    private static final class PlaybackStatsTracker {
        private long audioFormatBitrateTimeProduct;
        private final List<PlaybackStats.EventTimeAndFormat> audioFormatHistory;
        private long audioFormatTimeMs;
        private long audioUnderruns;
        private long bandwidthBytes;
        private long bandwidthTimeMs;

        @Nullable
        private Format currentAudioFormat;
        private float currentPlaybackSpeed;
        private int currentPlaybackState;
        private long currentPlaybackStateStartTimeMs;

        @Nullable
        private Format currentVideoFormat;
        private long droppedFrames;
        private int fatalErrorCount;
        private final List<PlaybackStats.EventTimeAndException> fatalErrorHistory;
        private long firstReportedTimeMs;
        private boolean hasBeenReady;
        private boolean hasEnded;
        private boolean hasFatalError;
        private long initialAudioFormatBitrate;
        private long initialVideoFormatBitrate;
        private int initialVideoFormatHeight;
        private final boolean isAd;
        private boolean isForeground;
        private boolean isInterruptedByAd;
        private boolean isJoinTimeInvalid;
        private boolean isSeeking;
        private final boolean keepHistory;
        private long lastAudioFormatStartTimeMs;
        private long lastRebufferStartTimeMs;
        private long lastVideoFormatStartTimeMs;
        private long maxRebufferTimeMs;
        private final List<long[]> mediaTimeHistory;
        private int nonFatalErrorCount;
        private final List<PlaybackStats.EventTimeAndException> nonFatalErrorHistory;
        private int pauseBufferCount;
        private int pauseCount;
        private final long[] playbackStateDurationsMs = new long[16];
        private final List<PlaybackStats.EventTimeAndPlaybackState> playbackStateHistory;
        private int rebufferCount;
        private int seekCount;
        private boolean startedLoading;
        private long videoFormatBitrateTimeMs;
        private long videoFormatBitrateTimeProduct;
        private long videoFormatHeightTimeMs;
        private long videoFormatHeightTimeProduct;
        private final List<PlaybackStats.EventTimeAndFormat> videoFormatHistory;

        public PlaybackStatsTracker(boolean z2, AnalyticsListener.EventTime eventTime) {
            this.keepHistory = z2;
            this.playbackStateHistory = z2 ? new ArrayList<>() : Collections.emptyList();
            this.mediaTimeHistory = z2 ? new ArrayList<>() : Collections.emptyList();
            this.videoFormatHistory = z2 ? new ArrayList<>() : Collections.emptyList();
            this.audioFormatHistory = z2 ? new ArrayList<>() : Collections.emptyList();
            this.fatalErrorHistory = z2 ? new ArrayList<>() : Collections.emptyList();
            this.nonFatalErrorHistory = z2 ? new ArrayList<>() : Collections.emptyList();
            boolean z3 = false;
            this.currentPlaybackState = 0;
            this.currentPlaybackStateStartTimeMs = eventTime.realtimeMs;
            this.firstReportedTimeMs = C.TIME_UNSET;
            this.maxRebufferTimeMs = C.TIME_UNSET;
            MediaSource.MediaPeriodId mediaPeriodId = eventTime.mediaPeriodId;
            if (mediaPeriodId != null && mediaPeriodId.isAd()) {
                z3 = true;
            }
            this.isAd = z3;
            this.initialAudioFormatBitrate = -1L;
            this.initialVideoFormatBitrate = -1L;
            this.initialVideoFormatHeight = -1;
            this.currentPlaybackSpeed = 1.0f;
        }

        private long[] guessMediaTimeBasedOnElapsedRealtime(long j2) {
            List<long[]> list = this.mediaTimeHistory;
            return new long[]{j2, list.get(list.size() - 1)[1] + ((long) ((j2 - r0[0]) * this.currentPlaybackSpeed))};
        }

        private static boolean isInvalidJoinTransition(int i2, int i3) {
            return ((i2 != 1 && i2 != 2 && i2 != 14) || i3 == 1 || i3 == 2 || i3 == 14 || i3 == 3 || i3 == 4 || i3 == 9 || i3 == 11) ? false : true;
        }

        private static boolean isPausedState(int i2) {
            return i2 == 4 || i2 == 7;
        }

        private static boolean isReadyState(int i2) {
            return i2 == 3 || i2 == 4 || i2 == 9;
        }

        private static boolean isRebufferingState(int i2) {
            return i2 == 6 || i2 == 7 || i2 == 10;
        }

        private void maybeRecordAudioFormatTime(long j2) {
            Format format;
            int i2;
            if (this.currentPlaybackState == 3 && (format = this.currentAudioFormat) != null && (i2 = format.bitrate) != -1) {
                long j3 = (long) ((j2 - this.lastAudioFormatStartTimeMs) * this.currentPlaybackSpeed);
                this.audioFormatTimeMs += j3;
                this.audioFormatBitrateTimeProduct += j3 * i2;
            }
            this.lastAudioFormatStartTimeMs = j2;
        }

        private void maybeRecordVideoFormatTime(long j2) {
            Format format;
            if (this.currentPlaybackState == 3 && (format = this.currentVideoFormat) != null) {
                long j3 = (long) ((j2 - this.lastVideoFormatStartTimeMs) * this.currentPlaybackSpeed);
                int i2 = format.height;
                if (i2 != -1) {
                    this.videoFormatHeightTimeMs += j3;
                    this.videoFormatHeightTimeProduct += i2 * j3;
                }
                int i3 = format.bitrate;
                if (i3 != -1) {
                    this.videoFormatBitrateTimeMs += j3;
                    this.videoFormatBitrateTimeProduct += j3 * i3;
                }
            }
            this.lastVideoFormatStartTimeMs = j2;
        }

        private void maybeUpdateAudioFormat(AnalyticsListener.EventTime eventTime, @Nullable Format format) {
            int i2;
            if (Util.areEqual(this.currentAudioFormat, format)) {
                return;
            }
            maybeRecordAudioFormatTime(eventTime.realtimeMs);
            if (format != null && this.initialAudioFormatBitrate == -1 && (i2 = format.bitrate) != -1) {
                this.initialAudioFormatBitrate = i2;
            }
            this.currentAudioFormat = format;
            if (this.keepHistory) {
                this.audioFormatHistory.add(new PlaybackStats.EventTimeAndFormat(eventTime, format));
            }
        }

        private void maybeUpdateMaxRebufferTimeMs(long j2) {
            if (isRebufferingState(this.currentPlaybackState)) {
                long j3 = j2 - this.lastRebufferStartTimeMs;
                long j4 = this.maxRebufferTimeMs;
                if (j4 == C.TIME_UNSET || j3 > j4) {
                    this.maxRebufferTimeMs = j3;
                }
            }
        }

        private void maybeUpdateMediaTimeHistory(long j2, long j3) {
            if (this.keepHistory) {
                if (this.currentPlaybackState != 3) {
                    if (j3 == C.TIME_UNSET) {
                        return;
                    }
                    if (!this.mediaTimeHistory.isEmpty()) {
                        List<long[]> list = this.mediaTimeHistory;
                        long j4 = list.get(list.size() - 1)[1];
                        if (j4 != j3) {
                            this.mediaTimeHistory.add(new long[]{j2, j4});
                        }
                    }
                }
                if (j3 != C.TIME_UNSET) {
                    this.mediaTimeHistory.add(new long[]{j2, j3});
                } else {
                    if (this.mediaTimeHistory.isEmpty()) {
                        return;
                    }
                    this.mediaTimeHistory.add(guessMediaTimeBasedOnElapsedRealtime(j2));
                }
            }
        }

        private void maybeUpdateVideoFormat(AnalyticsListener.EventTime eventTime, @Nullable Format format) {
            int i2;
            int i3;
            if (Util.areEqual(this.currentVideoFormat, format)) {
                return;
            }
            maybeRecordVideoFormatTime(eventTime.realtimeMs);
            if (format != null) {
                if (this.initialVideoFormatHeight == -1 && (i3 = format.height) != -1) {
                    this.initialVideoFormatHeight = i3;
                }
                if (this.initialVideoFormatBitrate == -1 && (i2 = format.bitrate) != -1) {
                    this.initialVideoFormatBitrate = i2;
                }
            }
            this.currentVideoFormat = format;
            if (this.keepHistory) {
                this.videoFormatHistory.add(new PlaybackStats.EventTimeAndFormat(eventTime, format));
            }
        }

        private int resolveNewPlaybackState(Player player) {
            int playbackState = player.getPlaybackState();
            if (this.isSeeking && this.isForeground) {
                return 5;
            }
            if (this.hasFatalError) {
                return 13;
            }
            if (!this.isForeground) {
                return this.startedLoading ? 1 : 0;
            }
            if (this.isInterruptedByAd) {
                return 14;
            }
            if (playbackState == 4) {
                return 11;
            }
            if (playbackState != 2) {
                if (playbackState == 3) {
                    if (player.getPlayWhenReady()) {
                        return player.getPlaybackSuppressionReason() != 0 ? 9 : 3;
                    }
                    return 4;
                }
                if (playbackState != 1 || this.currentPlaybackState == 0) {
                    return this.currentPlaybackState;
                }
                return 12;
            }
            int i2 = this.currentPlaybackState;
            if (i2 == 0 || i2 == 1 || i2 == 2 || i2 == 14) {
                return 2;
            }
            if (player.getPlayWhenReady()) {
                return player.getPlaybackSuppressionReason() != 0 ? 10 : 6;
            }
            return 7;
        }

        private void updatePlaybackState(int i2, AnalyticsListener.EventTime eventTime) {
            Assertions.checkArgument(eventTime.realtimeMs >= this.currentPlaybackStateStartTimeMs);
            long j2 = eventTime.realtimeMs;
            long j3 = j2 - this.currentPlaybackStateStartTimeMs;
            long[] jArr = this.playbackStateDurationsMs;
            int i3 = this.currentPlaybackState;
            jArr[i3] = jArr[i3] + j3;
            if (this.firstReportedTimeMs == C.TIME_UNSET) {
                this.firstReportedTimeMs = j2;
            }
            this.isJoinTimeInvalid |= isInvalidJoinTransition(i3, i2);
            this.hasBeenReady |= isReadyState(i2);
            this.hasEnded |= i2 == 11;
            if (!isPausedState(this.currentPlaybackState) && isPausedState(i2)) {
                this.pauseCount++;
            }
            if (i2 == 5) {
                this.seekCount++;
            }
            if (!isRebufferingState(this.currentPlaybackState) && isRebufferingState(i2)) {
                this.rebufferCount++;
                this.lastRebufferStartTimeMs = eventTime.realtimeMs;
            }
            if (isRebufferingState(this.currentPlaybackState) && this.currentPlaybackState != 7 && i2 == 7) {
                this.pauseBufferCount++;
            }
            maybeUpdateMaxRebufferTimeMs(eventTime.realtimeMs);
            this.currentPlaybackState = i2;
            this.currentPlaybackStateStartTimeMs = eventTime.realtimeMs;
            if (this.keepHistory) {
                this.playbackStateHistory.add(new PlaybackStats.EventTimeAndPlaybackState(eventTime, i2));
            }
        }

        public PlaybackStats build(boolean z2) {
            long[] jArr;
            List<long[]> list;
            long j2;
            int i2;
            long[] jArr2 = this.playbackStateDurationsMs;
            List<long[]> list2 = this.mediaTimeHistory;
            if (z2) {
                jArr = jArr2;
                list = list2;
            } else {
                long jElapsedRealtime = SystemClock.elapsedRealtime();
                long[] jArrCopyOf = Arrays.copyOf(this.playbackStateDurationsMs, 16);
                long jMax = Math.max(0L, jElapsedRealtime - this.currentPlaybackStateStartTimeMs);
                int i3 = this.currentPlaybackState;
                jArrCopyOf[i3] = jArrCopyOf[i3] + jMax;
                maybeUpdateMaxRebufferTimeMs(jElapsedRealtime);
                maybeRecordVideoFormatTime(jElapsedRealtime);
                maybeRecordAudioFormatTime(jElapsedRealtime);
                ArrayList arrayList = new ArrayList(this.mediaTimeHistory);
                if (this.keepHistory && this.currentPlaybackState == 3) {
                    arrayList.add(guessMediaTimeBasedOnElapsedRealtime(jElapsedRealtime));
                }
                jArr = jArrCopyOf;
                list = arrayList;
            }
            int i4 = (this.isJoinTimeInvalid || !this.hasBeenReady) ? 1 : 0;
            long j3 = i4 != 0 ? C.TIME_UNSET : jArr[2];
            int i5 = jArr[1] > 0 ? 1 : 0;
            List arrayList2 = z2 ? this.videoFormatHistory : new ArrayList(this.videoFormatHistory);
            List arrayList3 = z2 ? this.audioFormatHistory : new ArrayList(this.audioFormatHistory);
            List arrayList4 = z2 ? this.playbackStateHistory : new ArrayList(this.playbackStateHistory);
            long j4 = this.firstReportedTimeMs;
            boolean z3 = this.isForeground;
            int i6 = !this.hasBeenReady ? 1 : 0;
            boolean z4 = this.hasEnded;
            int i7 = i4 ^ 1;
            int i8 = this.pauseCount;
            int i9 = this.pauseBufferCount;
            int i10 = this.seekCount;
            int i11 = this.rebufferCount;
            long j5 = this.maxRebufferTimeMs;
            boolean z5 = this.isAd;
            long[] jArr3 = jArr;
            long j6 = this.videoFormatHeightTimeMs;
            long j7 = this.videoFormatHeightTimeProduct;
            long j8 = this.videoFormatBitrateTimeMs;
            long j9 = this.videoFormatBitrateTimeProduct;
            long j10 = this.audioFormatTimeMs;
            long j11 = this.audioFormatBitrateTimeProduct;
            int i12 = this.initialVideoFormatHeight;
            int i13 = i12 == -1 ? 0 : 1;
            long j12 = this.initialVideoFormatBitrate;
            int i14 = j12 == -1 ? 0 : 1;
            long j13 = this.initialAudioFormatBitrate;
            if (j13 == -1) {
                j2 = j13;
                i2 = 0;
            } else {
                j2 = j13;
                i2 = 1;
            }
            long j14 = this.bandwidthTimeMs;
            long j15 = this.bandwidthBytes;
            long j16 = this.droppedFrames;
            long j17 = this.audioUnderruns;
            int i15 = this.fatalErrorCount;
            return new PlaybackStats(1, jArr3, arrayList4, list, j4, z3 ? 1 : 0, i6, z4 ? 1 : 0, i5, j3, i7, i8, i9, i10, i11, j5, z5 ? 1 : 0, arrayList2, arrayList3, j6, j7, j8, j9, j10, j11, i13, i14, i12, j12, i2, j2, j14, j15, j16, j17, i15 > 0 ? 1 : 0, i15, this.nonFatalErrorCount, this.fatalErrorHistory, this.nonFatalErrorHistory);
        }

        public void onEvents(Player player, AnalyticsListener.EventTime eventTime, boolean z2, long j2, boolean z3, int i2, boolean z4, boolean z5, @Nullable PlaybackException playbackException, @Nullable Exception exc, long j3, long j4, @Nullable Format format, @Nullable Format format2, @Nullable VideoSize videoSize) {
            long j5 = C.TIME_UNSET;
            if (j2 != C.TIME_UNSET) {
                maybeUpdateMediaTimeHistory(eventTime.realtimeMs, j2);
                this.isSeeking = true;
            }
            if (player.getPlaybackState() != 2) {
                this.isSeeking = false;
            }
            int playbackState = player.getPlaybackState();
            if (playbackState == 1 || playbackState == 4 || z3) {
                this.isInterruptedByAd = false;
            }
            if (playbackException != null) {
                this.hasFatalError = true;
                this.fatalErrorCount++;
                if (this.keepHistory) {
                    this.fatalErrorHistory.add(new PlaybackStats.EventTimeAndException(eventTime, playbackException));
                }
            } else if (player.getPlayerError() == null) {
                this.hasFatalError = false;
            }
            if (this.isForeground && !this.isInterruptedByAd) {
                Tracks currentTracks = player.getCurrentTracks();
                if (!currentTracks.isTypeSelected(2)) {
                    maybeUpdateVideoFormat(eventTime, null);
                }
                if (!currentTracks.isTypeSelected(1)) {
                    maybeUpdateAudioFormat(eventTime, null);
                }
            }
            if (format != null) {
                maybeUpdateVideoFormat(eventTime, format);
            }
            if (format2 != null) {
                maybeUpdateAudioFormat(eventTime, format2);
            }
            Format format3 = this.currentVideoFormat;
            if (format3 != null && format3.height == -1 && videoSize != null) {
                maybeUpdateVideoFormat(eventTime, format3.buildUpon().setWidth(videoSize.width).setHeight(videoSize.height).build());
            }
            if (z5) {
                this.startedLoading = true;
            }
            if (z4) {
                this.audioUnderruns++;
            }
            this.droppedFrames += i2;
            this.bandwidthTimeMs += j3;
            this.bandwidthBytes += j4;
            if (exc != null) {
                this.nonFatalErrorCount++;
                if (this.keepHistory) {
                    this.nonFatalErrorHistory.add(new PlaybackStats.EventTimeAndException(eventTime, exc));
                }
            }
            int iResolveNewPlaybackState = resolveNewPlaybackState(player);
            float f2 = player.getPlaybackParameters().speed;
            if (this.currentPlaybackState != iResolveNewPlaybackState || this.currentPlaybackSpeed != f2) {
                long j6 = eventTime.realtimeMs;
                if (z2) {
                    j5 = eventTime.eventPlaybackPositionMs;
                }
                maybeUpdateMediaTimeHistory(j6, j5);
                maybeRecordVideoFormatTime(eventTime.realtimeMs);
                maybeRecordAudioFormatTime(eventTime.realtimeMs);
            }
            this.currentPlaybackSpeed = f2;
            if (this.currentPlaybackState != iResolveNewPlaybackState) {
                updatePlaybackState(iResolveNewPlaybackState, eventTime);
            }
        }

        public void onFinished(AnalyticsListener.EventTime eventTime, boolean z2, long j2) {
            int i2 = 11;
            if (this.currentPlaybackState != 11 && !z2) {
                i2 = 15;
            }
            maybeUpdateMediaTimeHistory(eventTime.realtimeMs, j2);
            maybeRecordVideoFormatTime(eventTime.realtimeMs);
            maybeRecordAudioFormatTime(eventTime.realtimeMs);
            updatePlaybackState(i2, eventTime);
        }

        public void onForeground() {
            this.isForeground = true;
        }

        public void onInterruptedByAd() {
            this.isInterruptedByAd = true;
            this.isSeeking = false;
        }
    }

    public PlaybackStatsListener(boolean z2, @Nullable Callback callback) {
        this.callback = callback;
        this.keepHistory = z2;
        DefaultPlaybackSessionManager defaultPlaybackSessionManager = new DefaultPlaybackSessionManager();
        this.sessionManager = defaultPlaybackSessionManager;
        this.playbackStatsTrackers = new HashMap();
        this.sessionStartEventTimes = new HashMap();
        this.finishedPlaybackStats = PlaybackStats.EMPTY;
        this.period = new Timeline.Period();
        this.videoSize = VideoSize.UNKNOWN;
        defaultPlaybackSessionManager.setListener(this);
    }

    private Pair<AnalyticsListener.EventTime, Boolean> findBestEventTime(AnalyticsListener.Events events, String str) {
        MediaSource.MediaPeriodId mediaPeriodId;
        AnalyticsListener.EventTime eventTime = null;
        boolean zBelongsToSession = false;
        for (int i2 = 0; i2 < events.size(); i2++) {
            AnalyticsListener.EventTime eventTime2 = events.getEventTime(events.get(i2));
            boolean zBelongsToSession2 = this.sessionManager.belongsToSession(eventTime2, str);
            if (eventTime == null || ((zBelongsToSession2 && !zBelongsToSession) || (zBelongsToSession2 == zBelongsToSession && eventTime2.realtimeMs > eventTime.realtimeMs))) {
                eventTime = eventTime2;
                zBelongsToSession = zBelongsToSession2;
            }
        }
        Assertions.checkNotNull(eventTime);
        if (!zBelongsToSession && (mediaPeriodId = eventTime.mediaPeriodId) != null && mediaPeriodId.isAd()) {
            long adGroupTimeUs = eventTime.timeline.getPeriodByUid(eventTime.mediaPeriodId.periodUid, this.period).getAdGroupTimeUs(eventTime.mediaPeriodId.adGroupIndex);
            if (adGroupTimeUs == Long.MIN_VALUE) {
                adGroupTimeUs = this.period.durationUs;
            }
            long positionInWindowUs = adGroupTimeUs + this.period.getPositionInWindowUs();
            long j2 = eventTime.realtimeMs;
            Timeline timeline = eventTime.timeline;
            int i3 = eventTime.windowIndex;
            MediaSource.MediaPeriodId mediaPeriodId2 = eventTime.mediaPeriodId;
            AnalyticsListener.EventTime eventTime3 = new AnalyticsListener.EventTime(j2, timeline, i3, new MediaSource.MediaPeriodId(mediaPeriodId2.periodUid, mediaPeriodId2.windowSequenceNumber, mediaPeriodId2.adGroupIndex), Util.usToMs(positionInWindowUs), eventTime.timeline, eventTime.currentWindowIndex, eventTime.currentMediaPeriodId, eventTime.currentPlaybackPositionMs, eventTime.totalBufferedDurationMs);
            zBelongsToSession = this.sessionManager.belongsToSession(eventTime3, str);
            eventTime = eventTime3;
        }
        return Pair.create(eventTime, Boolean.valueOf(zBelongsToSession));
    }

    private boolean hasEvent(AnalyticsListener.Events events, String str, int i2) {
        return events.contains(i2) && this.sessionManager.belongsToSession(events.getEventTime(i2), str);
    }

    private void maybeAddSessions(AnalyticsListener.Events events) {
        for (int i2 = 0; i2 < events.size(); i2++) {
            int i3 = events.get(i2);
            AnalyticsListener.EventTime eventTime = events.getEventTime(i3);
            if (i3 == 0) {
                this.sessionManager.updateSessionsWithTimelineChange(eventTime);
            } else if (i3 == 11) {
                this.sessionManager.updateSessionsWithDiscontinuity(eventTime, this.discontinuityReason);
            } else {
                this.sessionManager.updateSessions(eventTime);
            }
        }
    }

    public PlaybackStats getCombinedPlaybackStats() {
        int i2 = 1;
        PlaybackStats[] playbackStatsArr = new PlaybackStats[this.playbackStatsTrackers.size() + 1];
        playbackStatsArr[0] = this.finishedPlaybackStats;
        Iterator<PlaybackStatsTracker> it = this.playbackStatsTrackers.values().iterator();
        while (it.hasNext()) {
            playbackStatsArr[i2] = it.next().build(false);
            i2++;
        }
        return PlaybackStats.merge(playbackStatsArr);
    }

    @Nullable
    public PlaybackStats getPlaybackStats() {
        String activeSessionId = this.sessionManager.getActiveSessionId();
        PlaybackStatsTracker playbackStatsTracker = activeSessionId == null ? null : this.playbackStatsTrackers.get(activeSessionId);
        if (playbackStatsTracker == null) {
            return null;
        }
        return playbackStatsTracker.build(false);
    }

    @Override // androidx.media3.exoplayer.analytics.PlaybackSessionManager.Listener
    public void onAdPlaybackStarted(AnalyticsListener.EventTime eventTime, String str, String str2) {
        ((PlaybackStatsTracker) Assertions.checkNotNull(this.playbackStatsTrackers.get(str))).onInterruptedByAd();
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public /* synthetic */ void onAudioAttributesChanged(AnalyticsListener.EventTime eventTime, AudioAttributes audioAttributes) {
        a.a(this, eventTime, audioAttributes);
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public /* synthetic */ void onAudioCodecError(AnalyticsListener.EventTime eventTime, Exception exc) {
        a.b(this, eventTime, exc);
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public /* synthetic */ void onAudioDecoderInitialized(AnalyticsListener.EventTime eventTime, String str, long j2) {
        a.c(this, eventTime, str, j2);
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public /* synthetic */ void onAudioDecoderReleased(AnalyticsListener.EventTime eventTime, String str) {
        a.e(this, eventTime, str);
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public /* synthetic */ void onAudioDisabled(AnalyticsListener.EventTime eventTime, DecoderCounters decoderCounters) {
        a.f(this, eventTime, decoderCounters);
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public /* synthetic */ void onAudioEnabled(AnalyticsListener.EventTime eventTime, DecoderCounters decoderCounters) {
        a.g(this, eventTime, decoderCounters);
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public /* synthetic */ void onAudioInputFormatChanged(AnalyticsListener.EventTime eventTime, Format format, DecoderReuseEvaluation decoderReuseEvaluation) {
        a.h(this, eventTime, format, decoderReuseEvaluation);
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public /* synthetic */ void onAudioPositionAdvancing(AnalyticsListener.EventTime eventTime, long j2) {
        a.i(this, eventTime, j2);
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public /* synthetic */ void onAudioSessionIdChanged(AnalyticsListener.EventTime eventTime, int i2) {
        a.j(this, eventTime, i2);
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public /* synthetic */ void onAudioSinkError(AnalyticsListener.EventTime eventTime, Exception exc) {
        a.k(this, eventTime, exc);
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public /* synthetic */ void onAudioTrackInitialized(AnalyticsListener.EventTime eventTime, AudioSink.AudioTrackConfig audioTrackConfig) {
        a.l(this, eventTime, audioTrackConfig);
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public /* synthetic */ void onAudioTrackReleased(AnalyticsListener.EventTime eventTime, AudioSink.AudioTrackConfig audioTrackConfig) {
        a.m(this, eventTime, audioTrackConfig);
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public /* synthetic */ void onAudioUnderrun(AnalyticsListener.EventTime eventTime, int i2, long j2, long j3) {
        a.n(this, eventTime, i2, j2, j3);
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public /* synthetic */ void onAvailableCommandsChanged(AnalyticsListener.EventTime eventTime, Player.Commands commands) {
        a.o(this, eventTime, commands);
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public void onBandwidthEstimate(AnalyticsListener.EventTime eventTime, int i2, long j2, long j3) {
        this.bandwidthTimeMs = i2;
        this.bandwidthBytes = j2;
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public /* synthetic */ void onCues(AnalyticsListener.EventTime eventTime, CueGroup cueGroup) {
        a.q(this, eventTime, cueGroup);
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public /* synthetic */ void onDeviceInfoChanged(AnalyticsListener.EventTime eventTime, DeviceInfo deviceInfo) {
        a.s(this, eventTime, deviceInfo);
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public /* synthetic */ void onDeviceVolumeChanged(AnalyticsListener.EventTime eventTime, int i2, boolean z2) {
        a.t(this, eventTime, i2, z2);
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public void onDownstreamFormatChanged(AnalyticsListener.EventTime eventTime, MediaLoadData mediaLoadData) {
        int i2 = mediaLoadData.trackType;
        if (i2 == 2 || i2 == 0) {
            this.videoFormat = mediaLoadData.trackFormat;
        } else if (i2 == 1) {
            this.audioFormat = mediaLoadData.trackFormat;
        }
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public /* synthetic */ void onDrmKeysLoaded(AnalyticsListener.EventTime eventTime) {
        a.v(this, eventTime);
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public /* synthetic */ void onDrmKeysRemoved(AnalyticsListener.EventTime eventTime) {
        a.w(this, eventTime);
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public /* synthetic */ void onDrmKeysRestored(AnalyticsListener.EventTime eventTime) {
        a.x(this, eventTime);
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public /* synthetic */ void onDrmSessionAcquired(AnalyticsListener.EventTime eventTime) {
        a.y(this, eventTime);
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public void onDrmSessionManagerError(AnalyticsListener.EventTime eventTime, Exception exc) {
        this.nonFatalException = exc;
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public /* synthetic */ void onDrmSessionReleased(AnalyticsListener.EventTime eventTime) {
        a.B(this, eventTime);
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public void onDroppedVideoFrames(AnalyticsListener.EventTime eventTime, int i2, long j2) {
        this.droppedFrames = i2;
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public void onEvents(Player player, AnalyticsListener.Events events) {
        if (events.size() == 0) {
            return;
        }
        maybeAddSessions(events);
        for (String str : this.playbackStatsTrackers.keySet()) {
            Pair<AnalyticsListener.EventTime, Boolean> pairFindBestEventTime = findBestEventTime(events, str);
            PlaybackStatsTracker playbackStatsTracker = this.playbackStatsTrackers.get(str);
            boolean zHasEvent = hasEvent(events, str, 11);
            boolean zHasEvent2 = hasEvent(events, str, 1018);
            boolean zHasEvent3 = hasEvent(events, str, 1011);
            boolean zHasEvent4 = hasEvent(events, str, 1000);
            boolean zHasEvent5 = hasEvent(events, str, 10);
            boolean z2 = hasEvent(events, str, 1003) || hasEvent(events, str, 1024);
            boolean zHasEvent6 = hasEvent(events, str, 1006);
            boolean zHasEvent7 = hasEvent(events, str, 1004);
            playbackStatsTracker.onEvents(player, (AnalyticsListener.EventTime) pairFindBestEventTime.first, ((Boolean) pairFindBestEventTime.second).booleanValue(), str.equals(this.discontinuityFromSession) ? this.discontinuityFromPositionMs : C.TIME_UNSET, zHasEvent, zHasEvent2 ? this.droppedFrames : 0, zHasEvent3, zHasEvent4, zHasEvent5 ? player.getPlayerError() : null, z2 ? this.nonFatalException : null, zHasEvent6 ? this.bandwidthTimeMs : 0L, zHasEvent6 ? this.bandwidthBytes : 0L, zHasEvent7 ? this.videoFormat : null, zHasEvent7 ? this.audioFormat : null, hasEvent(events, str, 25) ? this.videoSize : null);
        }
        this.videoFormat = null;
        this.audioFormat = null;
        this.discontinuityFromSession = null;
        if (events.contains(AnalyticsListener.EVENT_PLAYER_RELEASED)) {
            this.sessionManager.finishAllSessions(events.getEventTime(AnalyticsListener.EVENT_PLAYER_RELEASED));
        }
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public /* synthetic */ void onIsLoadingChanged(AnalyticsListener.EventTime eventTime, boolean z2) {
        a.E(this, eventTime, z2);
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public /* synthetic */ void onIsPlayingChanged(AnalyticsListener.EventTime eventTime, boolean z2) {
        a.F(this, eventTime, z2);
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public /* synthetic */ void onLoadCanceled(AnalyticsListener.EventTime eventTime, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
        a.G(this, eventTime, loadEventInfo, mediaLoadData);
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public /* synthetic */ void onLoadCompleted(AnalyticsListener.EventTime eventTime, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
        a.H(this, eventTime, loadEventInfo, mediaLoadData);
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public void onLoadError(AnalyticsListener.EventTime eventTime, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData, IOException iOException, boolean z2) {
        this.nonFatalException = iOException;
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public /* synthetic */ void onLoadStarted(AnalyticsListener.EventTime eventTime, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
        a.J(this, eventTime, loadEventInfo, mediaLoadData);
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public /* synthetic */ void onLoadingChanged(AnalyticsListener.EventTime eventTime, boolean z2) {
        a.K(this, eventTime, z2);
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public /* synthetic */ void onMaxSeekToPreviousPositionChanged(AnalyticsListener.EventTime eventTime, long j2) {
        a.L(this, eventTime, j2);
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public /* synthetic */ void onMediaItemTransition(AnalyticsListener.EventTime eventTime, MediaItem mediaItem, int i2) {
        a.M(this, eventTime, mediaItem, i2);
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public /* synthetic */ void onMediaMetadataChanged(AnalyticsListener.EventTime eventTime, MediaMetadata mediaMetadata) {
        a.N(this, eventTime, mediaMetadata);
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public /* synthetic */ void onMetadata(AnalyticsListener.EventTime eventTime, Metadata metadata) {
        a.O(this, eventTime, metadata);
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public /* synthetic */ void onPlayWhenReadyChanged(AnalyticsListener.EventTime eventTime, boolean z2, int i2) {
        a.P(this, eventTime, z2, i2);
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public /* synthetic */ void onPlaybackParametersChanged(AnalyticsListener.EventTime eventTime, PlaybackParameters playbackParameters) {
        a.Q(this, eventTime, playbackParameters);
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public /* synthetic */ void onPlaybackStateChanged(AnalyticsListener.EventTime eventTime, int i2) {
        a.R(this, eventTime, i2);
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public /* synthetic */ void onPlaybackSuppressionReasonChanged(AnalyticsListener.EventTime eventTime, int i2) {
        a.S(this, eventTime, i2);
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public /* synthetic */ void onPlayerError(AnalyticsListener.EventTime eventTime, PlaybackException playbackException) {
        a.T(this, eventTime, playbackException);
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public /* synthetic */ void onPlayerErrorChanged(AnalyticsListener.EventTime eventTime, PlaybackException playbackException) {
        a.U(this, eventTime, playbackException);
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public /* synthetic */ void onPlayerReleased(AnalyticsListener.EventTime eventTime) {
        a.V(this, eventTime);
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public /* synthetic */ void onPlayerStateChanged(AnalyticsListener.EventTime eventTime, boolean z2, int i2) {
        a.W(this, eventTime, z2, i2);
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public /* synthetic */ void onPlaylistMetadataChanged(AnalyticsListener.EventTime eventTime, MediaMetadata mediaMetadata) {
        a.X(this, eventTime, mediaMetadata);
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public /* synthetic */ void onPositionDiscontinuity(AnalyticsListener.EventTime eventTime, int i2) {
        a.Y(this, eventTime, i2);
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public /* synthetic */ void onRenderedFirstFrame(AnalyticsListener.EventTime eventTime, Object obj, long j2) {
        a.a0(this, eventTime, obj, j2);
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public /* synthetic */ void onRepeatModeChanged(AnalyticsListener.EventTime eventTime, int i2) {
        a.b0(this, eventTime, i2);
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public /* synthetic */ void onSeekBackIncrementChanged(AnalyticsListener.EventTime eventTime, long j2) {
        a.c0(this, eventTime, j2);
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public /* synthetic */ void onSeekForwardIncrementChanged(AnalyticsListener.EventTime eventTime, long j2) {
        a.d0(this, eventTime, j2);
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public /* synthetic */ void onSeekStarted(AnalyticsListener.EventTime eventTime) {
        a.e0(this, eventTime);
    }

    @Override // androidx.media3.exoplayer.analytics.PlaybackSessionManager.Listener
    public void onSessionActive(AnalyticsListener.EventTime eventTime, String str) {
        ((PlaybackStatsTracker) Assertions.checkNotNull(this.playbackStatsTrackers.get(str))).onForeground();
    }

    @Override // androidx.media3.exoplayer.analytics.PlaybackSessionManager.Listener
    public void onSessionCreated(AnalyticsListener.EventTime eventTime, String str) {
        this.playbackStatsTrackers.put(str, new PlaybackStatsTracker(this.keepHistory, eventTime));
        this.sessionStartEventTimes.put(str, eventTime);
    }

    @Override // androidx.media3.exoplayer.analytics.PlaybackSessionManager.Listener
    public void onSessionFinished(AnalyticsListener.EventTime eventTime, String str, boolean z2) {
        PlaybackStatsTracker playbackStatsTracker = (PlaybackStatsTracker) Assertions.checkNotNull(this.playbackStatsTrackers.remove(str));
        AnalyticsListener.EventTime eventTime2 = (AnalyticsListener.EventTime) Assertions.checkNotNull(this.sessionStartEventTimes.remove(str));
        playbackStatsTracker.onFinished(eventTime, z2, str.equals(this.discontinuityFromSession) ? this.discontinuityFromPositionMs : C.TIME_UNSET);
        PlaybackStats playbackStatsBuild = playbackStatsTracker.build(true);
        this.finishedPlaybackStats = PlaybackStats.merge(this.finishedPlaybackStats, playbackStatsBuild);
        Callback callback = this.callback;
        if (callback != null) {
            callback.onPlaybackStatsReady(eventTime2, playbackStatsBuild);
        }
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public /* synthetic */ void onShuffleModeChanged(AnalyticsListener.EventTime eventTime, boolean z2) {
        a.f0(this, eventTime, z2);
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public /* synthetic */ void onSkipSilenceEnabledChanged(AnalyticsListener.EventTime eventTime, boolean z2) {
        a.g0(this, eventTime, z2);
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public /* synthetic */ void onSurfaceSizeChanged(AnalyticsListener.EventTime eventTime, int i2, int i3) {
        a.h0(this, eventTime, i2, i3);
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public /* synthetic */ void onTimelineChanged(AnalyticsListener.EventTime eventTime, int i2) {
        a.i0(this, eventTime, i2);
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public /* synthetic */ void onTrackSelectionParametersChanged(AnalyticsListener.EventTime eventTime, TrackSelectionParameters trackSelectionParameters) {
        a.j0(this, eventTime, trackSelectionParameters);
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public /* synthetic */ void onTracksChanged(AnalyticsListener.EventTime eventTime, Tracks tracks) {
        a.k0(this, eventTime, tracks);
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public /* synthetic */ void onUpstreamDiscarded(AnalyticsListener.EventTime eventTime, MediaLoadData mediaLoadData) {
        a.l0(this, eventTime, mediaLoadData);
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public /* synthetic */ void onVideoCodecError(AnalyticsListener.EventTime eventTime, Exception exc) {
        a.m0(this, eventTime, exc);
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public /* synthetic */ void onVideoDecoderInitialized(AnalyticsListener.EventTime eventTime, String str, long j2) {
        a.n0(this, eventTime, str, j2);
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public /* synthetic */ void onVideoDecoderReleased(AnalyticsListener.EventTime eventTime, String str) {
        a.p0(this, eventTime, str);
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public /* synthetic */ void onVideoDisabled(AnalyticsListener.EventTime eventTime, DecoderCounters decoderCounters) {
        a.q0(this, eventTime, decoderCounters);
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public /* synthetic */ void onVideoEnabled(AnalyticsListener.EventTime eventTime, DecoderCounters decoderCounters) {
        a.r0(this, eventTime, decoderCounters);
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public /* synthetic */ void onVideoFrameProcessingOffset(AnalyticsListener.EventTime eventTime, long j2, int i2) {
        a.s0(this, eventTime, j2, i2);
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public /* synthetic */ void onVideoInputFormatChanged(AnalyticsListener.EventTime eventTime, Format format, DecoderReuseEvaluation decoderReuseEvaluation) {
        a.t0(this, eventTime, format, decoderReuseEvaluation);
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public /* synthetic */ void onVideoSizeChanged(AnalyticsListener.EventTime eventTime, int i2, int i3, int i4, float f2) {
        a.u0(this, eventTime, i2, i3, i4, f2);
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public /* synthetic */ void onVolumeChanged(AnalyticsListener.EventTime eventTime, float f2) {
        a.w0(this, eventTime, f2);
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public /* synthetic */ void onAudioDecoderInitialized(AnalyticsListener.EventTime eventTime, String str, long j2, long j3) {
        a.d(this, eventTime, str, j2, j3);
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public /* synthetic */ void onCues(AnalyticsListener.EventTime eventTime, List list) {
        a.r(this, eventTime, list);
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public /* synthetic */ void onDrmSessionAcquired(AnalyticsListener.EventTime eventTime, int i2) {
        a.z(this, eventTime, i2);
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public void onPositionDiscontinuity(AnalyticsListener.EventTime eventTime, Player.PositionInfo positionInfo, Player.PositionInfo positionInfo2, int i2) {
        if (this.discontinuityFromSession == null) {
            this.discontinuityFromSession = this.sessionManager.getActiveSessionId();
            this.discontinuityFromPositionMs = positionInfo.positionMs;
        }
        this.discontinuityReason = i2;
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public /* synthetic */ void onVideoDecoderInitialized(AnalyticsListener.EventTime eventTime, String str, long j2, long j3) {
        a.o0(this, eventTime, str, j2, j3);
    }

    @Override // androidx.media3.exoplayer.analytics.AnalyticsListener
    public void onVideoSizeChanged(AnalyticsListener.EventTime eventTime, VideoSize videoSize) {
        this.videoSize = videoSize;
    }
}
