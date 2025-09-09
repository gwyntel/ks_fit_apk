package androidx.media3.extractor;

import androidx.media3.common.util.UnstableApi;
import androidx.media3.common.util.Util;

@UnstableApi
/* loaded from: classes2.dex */
public final class WavUtil {
    public static final int DATA_FOURCC = 1684108385;
    public static final int DS64_FOURCC = 1685272116;
    public static final int FMT_FOURCC = 1718449184;
    public static final int RF64_FOURCC = 1380333108;
    public static final int RIFF_FOURCC = 1380533830;
    public static final int TYPE_ALAW = 6;
    public static final int TYPE_FLOAT = 3;
    public static final int TYPE_IMA_ADPCM = 17;
    public static final int TYPE_MLAW = 7;
    public static final int TYPE_PCM = 1;
    public static final int TYPE_WAVE_FORMAT_EXTENSIBLE = 65534;
    public static final int WAVE_FOURCC = 1463899717;

    private WavUtil() {
    }

    public static int getPcmEncodingForType(int i2, int i3) {
        if (i2 != 1) {
            if (i2 == 3) {
                return i3 == 32 ? 4 : 0;
            }
            if (i2 != 65534) {
                return 0;
            }
        }
        return Util.getPcmEncoding(i3);
    }

    public static int getTypeForPcmEncoding(int i2) {
        if (i2 == 2 || i2 == 3) {
            return 1;
        }
        if (i2 == 4) {
            return 3;
        }
        if (i2 == 21 || i2 == 22) {
            return 1;
        }
        throw new IllegalArgumentException();
    }
}
