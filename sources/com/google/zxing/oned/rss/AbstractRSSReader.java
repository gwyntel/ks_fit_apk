package com.google.zxing.oned.rss;

import com.google.zxing.NotFoundException;
import com.google.zxing.oned.OneDReader;

/* loaded from: classes3.dex */
public abstract class AbstractRSSReader extends OneDReader {
    private static final float MAX_AVG_VARIANCE = 0.2f;
    private static final float MAX_FINDER_PATTERN_RATIO = 0.89285713f;
    private static final float MAX_INDIVIDUAL_VARIANCE = 0.45f;
    private static final float MIN_FINDER_PATTERN_RATIO = 0.7916667f;
    private final int[] dataCharacterCounters;
    private final int[] evenCounts;
    private final int[] oddCounts;
    private final int[] decodeFinderCounters = new int[4];
    private final float[] oddRoundingErrors = new float[4];
    private final float[] evenRoundingErrors = new float[4];

    protected AbstractRSSReader() {
        int[] iArr = new int[8];
        this.dataCharacterCounters = iArr;
        this.oddCounts = new int[iArr.length / 2];
        this.evenCounts = new int[iArr.length / 2];
    }

    protected static void d(int[] iArr, float[] fArr) {
        int i2 = 0;
        float f2 = fArr[0];
        for (int i3 = 1; i3 < iArr.length; i3++) {
            float f3 = fArr[i3];
            if (f3 < f2) {
                i2 = i3;
                f2 = f3;
            }
        }
        iArr[i2] = iArr[i2] - 1;
    }

    protected static void k(int[] iArr, float[] fArr) {
        int i2 = 0;
        float f2 = fArr[0];
        for (int i3 = 1; i3 < iArr.length; i3++) {
            float f3 = fArr[i3];
            if (f3 > f2) {
                i2 = i3;
                f2 = f3;
            }
        }
        iArr[i2] = iArr[i2] + 1;
    }

    protected static boolean l(int[] iArr) {
        float f2 = (iArr[0] + iArr[1]) / ((iArr[2] + r1) + iArr[3]);
        if (f2 >= MIN_FINDER_PATTERN_RATIO && f2 <= MAX_FINDER_PATTERN_RATIO) {
            int i2 = Integer.MAX_VALUE;
            int i3 = Integer.MIN_VALUE;
            for (int i4 : iArr) {
                if (i4 > i3) {
                    i3 = i4;
                }
                if (i4 < i2) {
                    i2 = i4;
                }
            }
            if (i3 < i2 * 10) {
                return true;
            }
        }
        return false;
    }

    protected static int m(int[] iArr, int[][] iArr2) throws NotFoundException {
        for (int i2 = 0; i2 < iArr2.length; i2++) {
            if (OneDReader.a(iArr, iArr2[i2], MAX_INDIVIDUAL_VARIANCE) < 0.2f) {
                return i2;
            }
        }
        throw NotFoundException.getNotFoundInstance();
    }

    protected final int[] e() {
        return this.dataCharacterCounters;
    }

    protected final int[] f() {
        return this.decodeFinderCounters;
    }

    protected final int[] g() {
        return this.evenCounts;
    }

    protected final float[] h() {
        return this.evenRoundingErrors;
    }

    protected final int[] i() {
        return this.oddCounts;
    }

    protected final float[] j() {
        return this.oddRoundingErrors;
    }
}
