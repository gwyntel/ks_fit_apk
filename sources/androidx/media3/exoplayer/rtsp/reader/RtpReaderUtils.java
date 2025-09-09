package androidx.media3.exoplayer.rtsp.reader;

import androidx.media3.common.util.Util;

/* loaded from: classes.dex */
class RtpReaderUtils {
    private RtpReaderUtils() {
    }

    public static long toSampleTimeUs(long j2, long j3, long j4, int i2) {
        return j2 + Util.scaleLargeTimestamp(j3 - j4, 1000000L, i2);
    }
}
