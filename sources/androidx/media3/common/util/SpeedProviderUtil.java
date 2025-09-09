package androidx.media3.common.util;

import androidx.media3.common.C;
import androidx.media3.common.audio.SpeedProvider;

@UnstableApi
/* loaded from: classes.dex */
public class SpeedProviderUtil {
    private SpeedProviderUtil() {
    }

    public static long getDurationAfterSpeedProviderApplied(SpeedProvider speedProvider, long j2) {
        long j3 = 0;
        double dMin = 0.0d;
        while (j3 < j2) {
            long nextSpeedChangeTimeUs = speedProvider.getNextSpeedChangeTimeUs(j3);
            if (nextSpeedChangeTimeUs == C.TIME_UNSET) {
                nextSpeedChangeTimeUs = Long.MAX_VALUE;
            }
            dMin += (Math.min(nextSpeedChangeTimeUs, j2) - j3) / speedProvider.getSpeed(j3);
            j3 = nextSpeedChangeTimeUs;
        }
        return Math.round(dMin);
    }
}
