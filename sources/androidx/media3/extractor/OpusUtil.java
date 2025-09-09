package androidx.media3.extractor;

import androidx.media3.common.C;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.DefaultLoadControl;
import com.google.common.base.Ascii;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

@UnstableApi
/* loaded from: classes2.dex */
public class OpusUtil {
    private static final int DEFAULT_SEEK_PRE_ROLL_SAMPLES = 3840;
    private static final int FULL_CODEC_INITIALIZATION_DATA_BUFFER_COUNT = 3;
    public static final int MAX_BYTES_PER_SECOND = 63750;
    public static final int SAMPLE_RATE = 48000;

    private OpusUtil() {
    }

    public static List<byte[]> buildInitializationData(byte[] bArr) {
        long jSampleCountToNanoseconds = sampleCountToNanoseconds(getPreSkipSamples(bArr));
        long jSampleCountToNanoseconds2 = sampleCountToNanoseconds(3840L);
        ArrayList arrayList = new ArrayList(3);
        arrayList.add(bArr);
        arrayList.add(buildNativeOrderByteArray(jSampleCountToNanoseconds));
        arrayList.add(buildNativeOrderByteArray(jSampleCountToNanoseconds2));
        return arrayList;
    }

    private static byte[] buildNativeOrderByteArray(long j2) {
        return ByteBuffer.allocate(8).order(ByteOrder.nativeOrder()).putLong(j2).array();
    }

    public static int getChannelCount(byte[] bArr) {
        return bArr[9] & 255;
    }

    private static long getPacketDurationUs(byte b2, byte b3) {
        int i2;
        int i3 = b2 & 255;
        int i4 = b2 & 3;
        if (i4 != 0) {
            i2 = 2;
            if (i4 != 1 && i4 != 2) {
                i2 = b3 & 63;
            }
        } else {
            i2 = 1;
        }
        int i5 = i3 >> 3;
        return i2 * (i5 >= 16 ? DefaultLoadControl.DEFAULT_BUFFER_FOR_PLAYBACK_MS << r6 : i5 >= 12 ? 10000 << (i5 & 1) : (i5 & 3) == 3 ? 60000 : 10000 << r6);
    }

    public static int getPreSkipSamples(byte[] bArr) {
        return (bArr[10] & 255) | ((bArr[11] & 255) << 8);
    }

    public static boolean needToDecodeOpusFrame(long j2, long j3) {
        return j2 - j3 <= sampleCountToNanoseconds(3840L) / 1000;
    }

    public static int parseOggPacketAudioSampleCount(ByteBuffer byteBuffer) {
        int oggPacketForPreAudioSampleByteCount = parseOggPacketForPreAudioSampleByteCount(byteBuffer);
        int i2 = byteBuffer.get(oggPacketForPreAudioSampleByteCount + 26) + Ascii.ESC + oggPacketForPreAudioSampleByteCount;
        return (int) ((getPacketDurationUs(byteBuffer.get(i2), byteBuffer.limit() - i2 > 1 ? byteBuffer.get(i2 + 1) : (byte) 0) * 48000) / 1000000);
    }

    public static int parseOggPacketForPreAudioSampleByteCount(ByteBuffer byteBuffer) {
        if ((byteBuffer.get(5) & 2) == 0) {
            return 0;
        }
        byte b2 = byteBuffer.get(26);
        int i2 = 28;
        int i3 = 28;
        for (int i4 = 0; i4 < b2; i4++) {
            i3 += byteBuffer.get(i4 + 27);
        }
        byte b3 = byteBuffer.get(i3 + 26);
        for (int i5 = 0; i5 < b3; i5++) {
            i2 += byteBuffer.get(i3 + 27 + i5);
        }
        return i3 + i2;
    }

    public static int parsePacketAudioSampleCount(ByteBuffer byteBuffer) {
        return (int) ((getPacketDurationUs(byteBuffer.get(0), byteBuffer.limit() > 1 ? byteBuffer.get(1) : (byte) 0) * 48000) / 1000000);
    }

    private static long sampleCountToNanoseconds(long j2) {
        return (j2 * C.NANOS_PER_SECOND) / 48000;
    }

    public static long getPacketDurationUs(byte[] bArr) {
        return getPacketDurationUs(bArr[0], bArr.length > 1 ? bArr[1] : (byte) 0);
    }
}
