package androidx.media3.exoplayer.source.ads;

import androidx.annotation.CheckResult;
import androidx.media3.common.AdPlaybackState;
import androidx.media3.common.C;
import androidx.media3.common.Player;
import androidx.media3.common.Timeline;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.common.util.Util;
import androidx.media3.exoplayer.source.MediaSource;

@UnstableApi
/* loaded from: classes2.dex */
public final class ServerSideAdInsertionUtil {
    private ServerSideAdInsertionUtil() {
    }

    @CheckResult
    public static AdPlaybackState addAdGroupToAdPlaybackState(AdPlaybackState adPlaybackState, long j2, long j3, long... jArr) {
        long mediaPeriodPositionUsForContent = getMediaPeriodPositionUsForContent(j2, -1, adPlaybackState);
        int i2 = adPlaybackState.removedAdGroupCount;
        while (i2 < adPlaybackState.adGroupCount && adPlaybackState.getAdGroup(i2).timeUs != Long.MIN_VALUE && adPlaybackState.getAdGroup(i2).timeUs <= mediaPeriodPositionUsForContent) {
            i2++;
        }
        AdPlaybackState adPlaybackStateWithContentResumeOffsetUs = adPlaybackState.withNewAdGroup(i2, mediaPeriodPositionUsForContent).withIsServerSideInserted(i2, true).withAdCount(i2, jArr.length).withAdDurationsUs(i2, jArr).withContentResumeOffsetUs(i2, j3);
        AdPlaybackState adPlaybackStateWithSkippedAd = adPlaybackStateWithContentResumeOffsetUs;
        for (int i3 = 0; i3 < jArr.length && jArr[i3] == 0; i3++) {
            adPlaybackStateWithSkippedAd = adPlaybackStateWithSkippedAd.withSkippedAd(i2, i3);
        }
        return correctFollowingAdGroupTimes(adPlaybackStateWithSkippedAd, i2, Util.sum(jArr), j3);
    }

    private static AdPlaybackState correctFollowingAdGroupTimes(AdPlaybackState adPlaybackState, int i2, long j2, long j3) {
        long j4 = (-j2) + j3;
        while (true) {
            i2++;
            if (i2 >= adPlaybackState.adGroupCount) {
                return adPlaybackState;
            }
            long j5 = adPlaybackState.getAdGroup(i2).timeUs;
            if (j5 != Long.MIN_VALUE) {
                adPlaybackState = adPlaybackState.withAdGroupTimeUs(i2, j5 + j4);
            }
        }
    }

    public static int getAdCountInGroup(AdPlaybackState adPlaybackState, int i2) {
        int i3 = adPlaybackState.getAdGroup(i2).count;
        if (i3 == -1) {
            return 0;
        }
        return i3;
    }

    public static long getMediaPeriodPositionUs(long j2, MediaSource.MediaPeriodId mediaPeriodId, AdPlaybackState adPlaybackState) {
        return mediaPeriodId.isAd() ? getMediaPeriodPositionUsForAd(j2, mediaPeriodId.adGroupIndex, mediaPeriodId.adIndexInAdGroup, adPlaybackState) : getMediaPeriodPositionUsForContent(j2, mediaPeriodId.nextAdGroupIndex, adPlaybackState);
    }

    public static long getMediaPeriodPositionUsForAd(long j2, int i2, int i3, AdPlaybackState adPlaybackState) {
        int i4;
        AdPlaybackState.AdGroup adGroup = adPlaybackState.getAdGroup(i2);
        long j3 = j2 - adGroup.timeUs;
        int i5 = adPlaybackState.removedAdGroupCount;
        while (true) {
            i4 = 0;
            if (i5 >= i2) {
                break;
            }
            AdPlaybackState.AdGroup adGroup2 = adPlaybackState.getAdGroup(i5);
            while (i4 < getAdCountInGroup(adPlaybackState, i5)) {
                j3 -= adGroup2.durationsUs[i4];
                i4++;
            }
            j3 += adGroup2.contentResumeOffsetUs;
            i5++;
        }
        if (i3 < getAdCountInGroup(adPlaybackState, i2)) {
            while (i4 < i3) {
                j3 -= adGroup.durationsUs[i4];
                i4++;
            }
        }
        return j3;
    }

    public static long getMediaPeriodPositionUsForContent(long j2, int i2, AdPlaybackState adPlaybackState) {
        if (i2 == -1) {
            i2 = adPlaybackState.adGroupCount;
        }
        long j3 = 0;
        for (int i3 = adPlaybackState.removedAdGroupCount; i3 < i2; i3++) {
            AdPlaybackState.AdGroup adGroup = adPlaybackState.getAdGroup(i3);
            long j4 = adGroup.timeUs;
            if (j4 == Long.MIN_VALUE || j4 > j2 - j3) {
                break;
            }
            for (int i4 = 0; i4 < getAdCountInGroup(adPlaybackState, i3); i4++) {
                j3 += adGroup.durationsUs[i4];
            }
            long j5 = adGroup.contentResumeOffsetUs;
            j3 -= j5;
            long j6 = adGroup.timeUs;
            long j7 = j2 - j3;
            if (j5 + j6 > j7) {
                return Math.max(j6, j7);
            }
        }
        return j2 - j3;
    }

    public static long getStreamPositionUs(Player player, AdPlaybackState adPlaybackState) {
        Timeline currentTimeline = player.getCurrentTimeline();
        if (currentTimeline.isEmpty()) {
            return C.TIME_UNSET;
        }
        Timeline.Period period = currentTimeline.getPeriod(player.getCurrentPeriodIndex(), new Timeline.Period());
        if (!Util.areEqual(period.getAdsId(), adPlaybackState.adsId)) {
            return C.TIME_UNSET;
        }
        if (!player.isPlayingAd()) {
            return getStreamPositionUsForContent(Util.msToUs(player.getCurrentPosition()) - period.getPositionInWindowUs(), -1, adPlaybackState);
        }
        return getStreamPositionUsForAd(Util.msToUs(player.getCurrentPosition()), player.getCurrentAdGroupIndex(), player.getCurrentAdIndexInAdGroup(), adPlaybackState);
    }

    public static long getStreamPositionUsForAd(long j2, int i2, int i3, AdPlaybackState adPlaybackState) {
        int i4;
        AdPlaybackState.AdGroup adGroup = adPlaybackState.getAdGroup(i2);
        long j3 = j2 + adGroup.timeUs;
        int i5 = adPlaybackState.removedAdGroupCount;
        while (true) {
            i4 = 0;
            if (i5 >= i2) {
                break;
            }
            AdPlaybackState.AdGroup adGroup2 = adPlaybackState.getAdGroup(i5);
            while (i4 < getAdCountInGroup(adPlaybackState, i5)) {
                j3 += adGroup2.durationsUs[i4];
                i4++;
            }
            j3 -= adGroup2.contentResumeOffsetUs;
            i5++;
        }
        if (i3 < getAdCountInGroup(adPlaybackState, i2)) {
            while (i4 < i3) {
                j3 += adGroup.durationsUs[i4];
                i4++;
            }
        }
        return j3;
    }

    public static long getStreamPositionUsForContent(long j2, int i2, AdPlaybackState adPlaybackState) {
        if (i2 == -1) {
            i2 = adPlaybackState.adGroupCount;
        }
        long j3 = 0;
        for (int i3 = adPlaybackState.removedAdGroupCount; i3 < i2; i3++) {
            AdPlaybackState.AdGroup adGroup = adPlaybackState.getAdGroup(i3);
            long j4 = adGroup.timeUs;
            if (j4 == Long.MIN_VALUE || j4 > j2) {
                break;
            }
            long j5 = j4 + j3;
            for (int i4 = 0; i4 < getAdCountInGroup(adPlaybackState, i3); i4++) {
                j3 += adGroup.durationsUs[i4];
            }
            long j6 = adGroup.contentResumeOffsetUs;
            j3 -= j6;
            if (adGroup.timeUs + j6 > j2) {
                return Math.max(j5, j2 + j3);
            }
        }
        return j2 + j3;
    }

    public static long getStreamPositionUs(long j2, MediaSource.MediaPeriodId mediaPeriodId, AdPlaybackState adPlaybackState) {
        if (mediaPeriodId.isAd()) {
            return getStreamPositionUsForAd(j2, mediaPeriodId.adGroupIndex, mediaPeriodId.adIndexInAdGroup, adPlaybackState);
        }
        return getStreamPositionUsForContent(j2, mediaPeriodId.nextAdGroupIndex, adPlaybackState);
    }
}
