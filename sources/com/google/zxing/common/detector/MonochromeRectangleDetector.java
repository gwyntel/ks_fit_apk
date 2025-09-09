package com.google.zxing.common.detector;

import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;

@Deprecated
/* loaded from: classes3.dex */
public final class MonochromeRectangleDetector {
    private static final int MAX_MODULES = 32;
    private final BitMatrix image;

    public MonochromeRectangleDetector(BitMatrix bitMatrix) {
        this.image = bitMatrix;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0020  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0057  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0031 A[EDGE_INSN: B:70:0x0031->B:22:0x0031 BREAK  A[LOOP:1: B:13:0x001c->B:73:0x001c], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:86:0x0068 A[EDGE_INSN: B:86:0x0068->B:47:0x0068 BREAK  A[LOOP:3: B:38:0x0053->B:91:0x0053], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int[] blackWhiteRange(int r5, int r6, int r7, int r8, boolean r9) {
        /*
            r4 = this;
            int r0 = r7 + r8
            int r0 = r0 / 2
            r1 = r0
        L5:
            if (r1 < r7) goto L3a
            com.google.zxing.common.BitMatrix r2 = r4.image
            if (r9 == 0) goto L12
            boolean r2 = r2.get(r1, r5)
            if (r2 == 0) goto L1b
            goto L18
        L12:
            boolean r2 = r2.get(r5, r1)
            if (r2 == 0) goto L1b
        L18:
            int r1 = r1 + (-1)
            goto L5
        L1b:
            r2 = r1
        L1c:
            int r2 = r2 + (-1)
            if (r2 < r7) goto L31
            com.google.zxing.common.BitMatrix r3 = r4.image
            if (r9 == 0) goto L2b
            boolean r3 = r3.get(r2, r5)
            if (r3 == 0) goto L1c
            goto L31
        L2b:
            boolean r3 = r3.get(r5, r2)
            if (r3 == 0) goto L1c
        L31:
            int r3 = r1 - r2
            if (r2 < r7) goto L3a
            if (r3 <= r6) goto L38
            goto L3a
        L38:
            r1 = r2
            goto L5
        L3a:
            int r1 = r1 + 1
        L3c:
            if (r0 >= r8) goto L71
            com.google.zxing.common.BitMatrix r7 = r4.image
            if (r9 == 0) goto L49
            boolean r7 = r7.get(r0, r5)
            if (r7 == 0) goto L52
            goto L4f
        L49:
            boolean r7 = r7.get(r5, r0)
            if (r7 == 0) goto L52
        L4f:
            int r0 = r0 + 1
            goto L3c
        L52:
            r7 = r0
        L53:
            int r7 = r7 + 1
            if (r7 >= r8) goto L68
            com.google.zxing.common.BitMatrix r2 = r4.image
            if (r9 == 0) goto L62
            boolean r2 = r2.get(r7, r5)
            if (r2 == 0) goto L53
            goto L68
        L62:
            boolean r2 = r2.get(r5, r7)
            if (r2 == 0) goto L53
        L68:
            int r2 = r7 - r0
            if (r7 >= r8) goto L71
            if (r2 <= r6) goto L6f
            goto L71
        L6f:
            r0 = r7
            goto L3c
        L71:
            int r0 = r0 + (-1)
            if (r0 <= r1) goto L7a
            int[] r5 = new int[]{r1, r0}
            return r5
        L7a:
            r5 = 0
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.common.detector.MonochromeRectangleDetector.blackWhiteRange(int, int, int, int, boolean):int[]");
    }

    private ResultPoint findCornerFromCenter(int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10) throws NotFoundException {
        int[] iArr = null;
        int i11 = i2;
        int i12 = i6;
        while (i12 < i9 && i12 >= i8 && i11 < i5 && i11 >= i4) {
            int[] iArrBlackWhiteRange = i3 == 0 ? blackWhiteRange(i12, i10, i4, i5, true) : blackWhiteRange(i11, i10, i8, i9, false);
            if (iArrBlackWhiteRange == null) {
                if (iArr == null) {
                    throw NotFoundException.getNotFoundInstance();
                }
                if (i3 == 0) {
                    int i13 = i12 - i7;
                    int i14 = iArr[0];
                    if (i14 >= i2) {
                        return new ResultPoint(iArr[1], i13);
                    }
                    if (iArr[1] > i2) {
                        return new ResultPoint(iArr[i7 <= 0 ? (char) 1 : (char) 0], i13);
                    }
                    return new ResultPoint(i14, i13);
                }
                int i15 = i11 - i3;
                int i16 = iArr[0];
                if (i16 >= i6) {
                    return new ResultPoint(i15, iArr[1]);
                }
                if (iArr[1] > i6) {
                    return new ResultPoint(i15, iArr[i3 >= 0 ? (char) 1 : (char) 0]);
                }
                return new ResultPoint(i15, i16);
            }
            i12 += i7;
            i11 += i3;
            iArr = iArrBlackWhiteRange;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    public ResultPoint[] detect() throws NotFoundException {
        int height = this.image.getHeight();
        int width = this.image.getWidth();
        int i2 = height / 2;
        int i3 = width / 2;
        int iMax = Math.max(1, height / 256);
        int iMax2 = Math.max(1, width / 256);
        int i4 = -iMax;
        int i5 = i3 / 2;
        int y2 = ((int) findCornerFromCenter(i3, 0, 0, width, i2, i4, 0, height, i5).getY()) - 1;
        int i6 = i2 / 2;
        ResultPoint resultPointFindCornerFromCenter = findCornerFromCenter(i3, -iMax2, 0, width, i2, 0, y2, height, i6);
        int x2 = ((int) resultPointFindCornerFromCenter.getX()) - 1;
        ResultPoint resultPointFindCornerFromCenter2 = findCornerFromCenter(i3, iMax2, x2, width, i2, 0, y2, height, i6);
        int x3 = ((int) resultPointFindCornerFromCenter2.getX()) + 1;
        ResultPoint resultPointFindCornerFromCenter3 = findCornerFromCenter(i3, 0, x2, x3, i2, iMax, y2, height, i5);
        return new ResultPoint[]{findCornerFromCenter(i3, 0, x2, x3, i2, i4, y2, ((int) resultPointFindCornerFromCenter3.getY()) + 1, i3 / 4), resultPointFindCornerFromCenter, resultPointFindCornerFromCenter2, resultPointFindCornerFromCenter3};
    }
}
