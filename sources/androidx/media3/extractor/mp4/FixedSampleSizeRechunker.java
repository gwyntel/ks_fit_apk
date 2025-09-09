package androidx.media3.extractor.mp4;

import androidx.media3.common.util.Util;

/* loaded from: classes2.dex */
final class FixedSampleSizeRechunker {
    private static final int MAX_SAMPLE_SIZE = 8192;

    public static final class Results {
        public final long duration;
        public final int[] flags;
        public final int maximumSize;
        public final long[] offsets;
        public final int[] sizes;
        public final long[] timestamps;

        private Results(long[] jArr, int[] iArr, int i2, long[] jArr2, int[] iArr2, long j2) {
            this.offsets = jArr;
            this.sizes = iArr;
            this.maximumSize = i2;
            this.timestamps = jArr2;
            this.flags = iArr2;
            this.duration = j2;
        }
    }

    private FixedSampleSizeRechunker() {
    }

    public static Results rechunk(int i2, long[] jArr, int[] iArr, long j2) {
        int i3 = 8192 / i2;
        int iCeilDivide = 0;
        for (int i4 : iArr) {
            iCeilDivide += Util.ceilDivide(i4, i3);
        }
        long[] jArr2 = new long[iCeilDivide];
        int[] iArr2 = new int[iCeilDivide];
        long[] jArr3 = new long[iCeilDivide];
        int[] iArr3 = new int[iCeilDivide];
        int i5 = 0;
        int i6 = 0;
        int iMax = 0;
        for (int i7 = 0; i7 < iArr.length; i7++) {
            int i8 = iArr[i7];
            long j3 = jArr[i7];
            while (i8 > 0) {
                int iMin = Math.min(i3, i8);
                jArr2[i6] = j3;
                int i9 = i2 * iMin;
                iArr2[i6] = i9;
                iMax = Math.max(iMax, i9);
                jArr3[i6] = i5 * j2;
                iArr3[i6] = 1;
                j3 += iArr2[i6];
                i5 += iMin;
                i8 -= iMin;
                i6++;
            }
        }
        return new Results(jArr2, iArr2, iMax, jArr3, iArr3, j2 * i5);
    }
}
