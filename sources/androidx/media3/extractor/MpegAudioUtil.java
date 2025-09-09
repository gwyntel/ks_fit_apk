package androidx.media3.extractor;

import androidx.annotation.Nullable;
import androidx.media3.common.MimeTypes;
import androidx.media3.common.util.UnstableApi;

@UnstableApi
/* loaded from: classes2.dex */
public final class MpegAudioUtil {
    public static final int MAX_FRAME_SIZE_BYTES = 4096;
    public static final int MAX_RATE_BYTES_PER_SECOND = 40000;
    private static final int SAMPLES_PER_FRAME_L1 = 384;
    private static final int SAMPLES_PER_FRAME_L2 = 1152;
    private static final int SAMPLES_PER_FRAME_L3_V1 = 1152;
    private static final int SAMPLES_PER_FRAME_L3_V2 = 576;
    private static final String[] MIME_TYPE_BY_LAYER = {MimeTypes.AUDIO_MPEG_L1, MimeTypes.AUDIO_MPEG_L2, "audio/mpeg"};
    private static final int[] SAMPLING_RATE_V1 = {44100, OpusUtil.SAMPLE_RATE, 32000};
    private static final int[] BITRATE_V1_L1 = {32000, 64000, 96000, 128000, 160000, DtsUtil.DTS_MAX_RATE_BYTES_PER_SECOND, 224000, AacUtil.AAC_XHE_MAX_RATE_BYTES_PER_SECOND, 288000, 320000, 352000, 384000, 416000, 448000};
    private static final int[] BITRATE_V2_L1 = {32000, OpusUtil.SAMPLE_RATE, 56000, 64000, Ac3Util.AC3_MAX_RATE_BYTES_PER_SECOND, 96000, 112000, 128000, 144000, 160000, 176000, DtsUtil.DTS_MAX_RATE_BYTES_PER_SECOND, 224000, AacUtil.AAC_XHE_MAX_RATE_BYTES_PER_SECOND};
    private static final int[] BITRATE_V1_L2 = {32000, OpusUtil.SAMPLE_RATE, 56000, 64000, Ac3Util.AC3_MAX_RATE_BYTES_PER_SECOND, 96000, 112000, 128000, 160000, DtsUtil.DTS_MAX_RATE_BYTES_PER_SECOND, 224000, AacUtil.AAC_XHE_MAX_RATE_BYTES_PER_SECOND, 320000, 384000};
    private static final int[] BITRATE_V1_L3 = {32000, 40000, OpusUtil.SAMPLE_RATE, 56000, 64000, Ac3Util.AC3_MAX_RATE_BYTES_PER_SECOND, 96000, 112000, 128000, 160000, DtsUtil.DTS_MAX_RATE_BYTES_PER_SECOND, 224000, AacUtil.AAC_XHE_MAX_RATE_BYTES_PER_SECOND, 320000};
    private static final int[] BITRATE_V2 = {8000, AacUtil.AAC_HE_V1_MAX_RATE_BYTES_PER_SECOND, 24000, 32000, 40000, OpusUtil.SAMPLE_RATE, 56000, 64000, Ac3Util.AC3_MAX_RATE_BYTES_PER_SECOND, 96000, 112000, 128000, 144000, 160000};

    public static final class Header {
        public int bitrate;
        public int channels;
        public int frameSize;

        @Nullable
        public String mimeType;
        public int sampleRate;
        public int samplesPerFrame;
        public int version;

        public Header() {
        }

        public boolean setForHeaderData(int i2) {
            int i3;
            int i4;
            int i5;
            int i6;
            if (!MpegAudioUtil.isMagicPresent(i2) || (i3 = (i2 >>> 19) & 3) == 1 || (i4 = (i2 >>> 17) & 3) == 0 || (i5 = (i2 >>> 12) & 15) == 0 || i5 == 15 || (i6 = (i2 >>> 10) & 3) == 3) {
                return false;
            }
            this.version = i3;
            this.mimeType = MpegAudioUtil.MIME_TYPE_BY_LAYER[3 - i4];
            int i7 = MpegAudioUtil.SAMPLING_RATE_V1[i6];
            this.sampleRate = i7;
            if (i3 == 2) {
                this.sampleRate = i7 / 2;
            } else if (i3 == 0) {
                this.sampleRate = i7 / 4;
            }
            int i8 = (i2 >>> 9) & 1;
            this.samplesPerFrame = MpegAudioUtil.getFrameSizeInSamples(i3, i4);
            if (i4 == 3) {
                int i9 = i3 == 3 ? MpegAudioUtil.BITRATE_V1_L1[i5 - 1] : MpegAudioUtil.BITRATE_V2_L1[i5 - 1];
                this.bitrate = i9;
                this.frameSize = (((i9 * 12) / this.sampleRate) + i8) * 4;
            } else {
                if (i3 == 3) {
                    int i10 = i4 == 2 ? MpegAudioUtil.BITRATE_V1_L2[i5 - 1] : MpegAudioUtil.BITRATE_V1_L3[i5 - 1];
                    this.bitrate = i10;
                    this.frameSize = ((i10 * 144) / this.sampleRate) + i8;
                } else {
                    int i11 = MpegAudioUtil.BITRATE_V2[i5 - 1];
                    this.bitrate = i11;
                    this.frameSize = (((i4 == 1 ? 72 : 144) * i11) / this.sampleRate) + i8;
                }
            }
            this.channels = ((i2 >> 6) & 3) == 3 ? 1 : 2;
            return true;
        }

        public Header(Header header) {
            this.version = header.version;
            this.mimeType = header.mimeType;
            this.frameSize = header.frameSize;
            this.sampleRate = header.sampleRate;
            this.channels = header.channels;
            this.bitrate = header.bitrate;
            this.samplesPerFrame = header.samplesPerFrame;
        }
    }

    private MpegAudioUtil() {
    }

    public static int getFrameSize(int i2) {
        int i3;
        int i4;
        int i5;
        int i6;
        if (!isMagicPresent(i2) || (i3 = (i2 >>> 19) & 3) == 1 || (i4 = (i2 >>> 17) & 3) == 0 || (i5 = (i2 >>> 12) & 15) == 0 || i5 == 15 || (i6 = (i2 >>> 10) & 3) == 3) {
            return -1;
        }
        int i7 = SAMPLING_RATE_V1[i6];
        if (i3 == 2) {
            i7 /= 2;
        } else if (i3 == 0) {
            i7 /= 4;
        }
        int i8 = (i2 >>> 9) & 1;
        if (i4 == 3) {
            return ((((i3 == 3 ? BITRATE_V1_L1[i5 - 1] : BITRATE_V2_L1[i5 - 1]) * 12) / i7) + i8) * 4;
        }
        int i9 = i3 == 3 ? i4 == 2 ? BITRATE_V1_L2[i5 - 1] : BITRATE_V1_L3[i5 - 1] : BITRATE_V2[i5 - 1];
        if (i3 == 3) {
            return ((i9 * 144) / i7) + i8;
        }
        return (((i4 == 1 ? 72 : 144) * i9) / i7) + i8;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int getFrameSizeInSamples(int i2, int i3) {
        if (i3 == 1) {
            if (i2 == 3) {
                return 1152;
            }
            return SAMPLES_PER_FRAME_L3_V2;
        }
        if (i3 == 2) {
            return 1152;
        }
        if (i3 == 3) {
            return 384;
        }
        throw new IllegalArgumentException();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isMagicPresent(int i2) {
        return (i2 & (-2097152)) == -2097152;
    }

    public static int parseMpegAudioFrameSampleCount(int i2) {
        int i3;
        int i4;
        if (!isMagicPresent(i2) || (i3 = (i2 >>> 19) & 3) == 1 || (i4 = (i2 >>> 17) & 3) == 0) {
            return -1;
        }
        int i5 = (i2 >>> 12) & 15;
        int i6 = (i2 >>> 10) & 3;
        if (i5 == 0 || i5 == 15 || i6 == 3) {
            return -1;
        }
        return getFrameSizeInSamples(i3, i4);
    }
}
