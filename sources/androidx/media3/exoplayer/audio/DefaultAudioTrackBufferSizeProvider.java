package androidx.media3.exoplayer.audio;

import androidx.media3.common.util.UnstableApi;
import androidx.media3.common.util.Util;
import androidx.media3.exoplayer.audio.DefaultAudioSink;
import androidx.media3.extractor.AacUtil;
import androidx.media3.extractor.Ac3Util;
import androidx.media3.extractor.Ac4Util;
import androidx.media3.extractor.DtsUtil;
import androidx.media3.extractor.OpusUtil;
import com.google.common.primitives.Ints;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

@UnstableApi
/* loaded from: classes.dex */
public class DefaultAudioTrackBufferSizeProvider implements DefaultAudioSink.AudioTrackBufferSizeProvider {
    private static final int AC3_BUFFER_MULTIPLICATION_FACTOR = 2;
    private static final int DTSHD_BUFFER_MULTIPLICATION_FACTOR = 4;
    private static final int MAX_PCM_BUFFER_DURATION_US = 750000;
    private static final int MIN_PCM_BUFFER_DURATION_US = 250000;
    private static final int OFFLOAD_BUFFER_DURATION_US = 50000000;
    private static final int PASSTHROUGH_BUFFER_DURATION_US = 250000;
    private static final int PCM_BUFFER_MULTIPLICATION_FACTOR = 4;

    /* renamed from: a, reason: collision with root package name */
    protected final int f5108a;
    public final int ac3BufferMultiplicationFactor;

    /* renamed from: b, reason: collision with root package name */
    protected final int f5109b;

    /* renamed from: c, reason: collision with root package name */
    protected final int f5110c;

    /* renamed from: d, reason: collision with root package name */
    protected final int f5111d;
    public final int dtshdBufferMultiplicationFactor;

    /* renamed from: e, reason: collision with root package name */
    protected final int f5112e;

    public static class Builder {
        private int minPcmBufferDurationUs = 250000;
        private int maxPcmBufferDurationUs = DefaultAudioTrackBufferSizeProvider.MAX_PCM_BUFFER_DURATION_US;
        private int pcmBufferMultiplicationFactor = 4;
        private int passthroughBufferDurationUs = 250000;
        private int offloadBufferDurationUs = DefaultAudioTrackBufferSizeProvider.OFFLOAD_BUFFER_DURATION_US;
        private int ac3BufferMultiplicationFactor = 2;
        private int dtshdBufferMultiplicationFactor = 4;

        public DefaultAudioTrackBufferSizeProvider build() {
            return new DefaultAudioTrackBufferSizeProvider(this);
        }

        @CanIgnoreReturnValue
        public Builder setAc3BufferMultiplicationFactor(int i2) {
            this.ac3BufferMultiplicationFactor = i2;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setDtshdBufferMultiplicationFactor(int i2) {
            this.dtshdBufferMultiplicationFactor = i2;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setMaxPcmBufferDurationUs(int i2) {
            this.maxPcmBufferDurationUs = i2;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setMinPcmBufferDurationUs(int i2) {
            this.minPcmBufferDurationUs = i2;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setOffloadBufferDurationUs(int i2) {
            this.offloadBufferDurationUs = i2;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setPassthroughBufferDurationUs(int i2) {
            this.passthroughBufferDurationUs = i2;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setPcmBufferMultiplicationFactor(int i2) {
            this.pcmBufferMultiplicationFactor = i2;
            return this;
        }
    }

    protected DefaultAudioTrackBufferSizeProvider(Builder builder) {
        this.f5108a = builder.minPcmBufferDurationUs;
        this.f5109b = builder.maxPcmBufferDurationUs;
        this.f5110c = builder.pcmBufferMultiplicationFactor;
        this.f5111d = builder.passthroughBufferDurationUs;
        this.f5112e = builder.offloadBufferDurationUs;
        this.ac3BufferMultiplicationFactor = builder.ac3BufferMultiplicationFactor;
        this.dtshdBufferMultiplicationFactor = builder.dtshdBufferMultiplicationFactor;
    }

    protected static int a(int i2, int i3, int i4) {
        return Ints.checkedCast(((i2 * i3) * i4) / 1000000);
    }

    protected static int c(int i2) {
        if (i2 == 20) {
            return OpusUtil.MAX_BYTES_PER_SECOND;
        }
        if (i2 == 30) {
            return DtsUtil.DTS_HD_MAX_RATE_BYTES_PER_SECOND;
        }
        switch (i2) {
            case 5:
                return Ac3Util.AC3_MAX_RATE_BYTES_PER_SECOND;
            case 6:
                return 768000;
            case 7:
                return DtsUtil.DTS_MAX_RATE_BYTES_PER_SECOND;
            case 8:
                return DtsUtil.DTS_HD_MAX_RATE_BYTES_PER_SECOND;
            case 9:
                return 40000;
            case 10:
                return 100000;
            case 11:
                return AacUtil.AAC_HE_V1_MAX_RATE_BYTES_PER_SECOND;
            case 12:
                return 7000;
            default:
                switch (i2) {
                    case 14:
                        return Ac3Util.TRUEHD_MAX_RATE_BYTES_PER_SECOND;
                    case 15:
                        return 8000;
                    case 16:
                        return AacUtil.AAC_XHE_MAX_RATE_BYTES_PER_SECOND;
                    case 17:
                        return Ac4Util.MAX_RATE_BYTES_PER_SECOND;
                    case 18:
                        return 768000;
                    default:
                        throw new IllegalArgumentException();
                }
        }
    }

    protected int b(int i2, int i3, int i4, int i5, int i6, int i7) {
        if (i4 == 0) {
            return f(i2, i6, i5);
        }
        if (i4 == 1) {
            return d(i3);
        }
        if (i4 == 2) {
            return e(i3, i7);
        }
        throw new IllegalArgumentException();
    }

    protected int d(int i2) {
        return Ints.checkedCast((this.f5112e * c(i2)) / 1000000);
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0013  */
    /* JADX WARN: Removed duplicated region for block: B:11:0x001a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected int e(int r4, int r5) {
        /*
            r3 = this;
            int r0 = r3.f5111d
            r1 = 5
            r2 = 8
            if (r4 != r1) goto Lb
            int r1 = r3.ac3BufferMultiplicationFactor
        L9:
            int r0 = r0 * r1
            goto L10
        Lb:
            if (r4 != r2) goto L10
            int r1 = r3.dtshdBufferMultiplicationFactor
            goto L9
        L10:
            r1 = -1
            if (r5 == r1) goto L1a
            java.math.RoundingMode r4 = java.math.RoundingMode.CEILING
            int r4 = com.google.common.math.IntMath.divide(r5, r2, r4)
            goto L1e
        L1a:
            int r4 = c(r4)
        L1e:
            long r0 = (long) r0
            long r4 = (long) r4
            long r0 = r0 * r4
            r4 = 1000000(0xf4240, double:4.940656E-318)
            long r0 = r0 / r4
            int r4 = com.google.common.primitives.Ints.checkedCast(r0)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.media3.exoplayer.audio.DefaultAudioTrackBufferSizeProvider.e(int, int):int");
    }

    protected int f(int i2, int i3, int i4) {
        return Util.constrainValue(i2 * this.f5110c, a(this.f5108a, i3, i4), a(this.f5109b, i3, i4));
    }

    @Override // androidx.media3.exoplayer.audio.DefaultAudioSink.AudioTrackBufferSizeProvider
    public int getBufferSizeInBytes(int i2, int i3, int i4, int i5, int i6, int i7, double d2) {
        return (((Math.max(i2, (int) (b(i2, i3, i4, i5, i6, i7) * d2)) + i5) - 1) / i5) * i5;
    }
}
