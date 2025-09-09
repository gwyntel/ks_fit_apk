package com.google.zxing.qrcode.encoder;

/* loaded from: classes3.dex */
final class MaskUtil {
    private static final int N1 = 3;
    private static final int N2 = 3;
    private static final int N3 = 40;
    private static final int N4 = 10;

    private MaskUtil() {
    }

    static int a(ByteMatrix byteMatrix) {
        return applyMaskPenaltyRule1Internal(byteMatrix, true) + applyMaskPenaltyRule1Internal(byteMatrix, false);
    }

    private static int applyMaskPenaltyRule1Internal(ByteMatrix byteMatrix, boolean z2) {
        int height = z2 ? byteMatrix.getHeight() : byteMatrix.getWidth();
        int width = z2 ? byteMatrix.getWidth() : byteMatrix.getHeight();
        byte[][] array = byteMatrix.getArray();
        int i2 = 0;
        for (int i3 = 0; i3 < height; i3++) {
            byte b2 = -1;
            int i4 = 0;
            for (int i5 = 0; i5 < width; i5++) {
                byte b3 = z2 ? array[i3][i5] : array[i5][i3];
                if (b3 == b2) {
                    i4++;
                } else {
                    if (i4 >= 5) {
                        i2 += i4 - 2;
                    }
                    i4 = 1;
                    b2 = b3;
                }
            }
            if (i4 >= 5) {
                i2 += i4 - 2;
            }
        }
        return i2;
    }

    static int b(ByteMatrix byteMatrix) {
        byte[][] array = byteMatrix.getArray();
        int width = byteMatrix.getWidth();
        int height = byteMatrix.getHeight();
        int i2 = 0;
        for (int i3 = 0; i3 < height - 1; i3++) {
            byte[] bArr = array[i3];
            int i4 = 0;
            while (i4 < width - 1) {
                byte b2 = bArr[i4];
                int i5 = i4 + 1;
                if (b2 == bArr[i5]) {
                    byte[] bArr2 = array[i3 + 1];
                    if (b2 == bArr2[i4] && b2 == bArr2[i5]) {
                        i2++;
                    }
                }
                i4 = i5;
            }
        }
        return i2 * 3;
    }

    static int c(ByteMatrix byteMatrix) {
        byte[][] array = byteMatrix.getArray();
        int width = byteMatrix.getWidth();
        int height = byteMatrix.getHeight();
        int i2 = 0;
        for (int i3 = 0; i3 < height; i3++) {
            for (int i4 = 0; i4 < width; i4++) {
                byte[] bArr = array[i3];
                int i5 = i4 + 6;
                if (i5 < width && bArr[i4] == 1 && bArr[i4 + 1] == 0 && bArr[i4 + 2] == 1 && bArr[i4 + 3] == 1 && bArr[i4 + 4] == 1 && bArr[i4 + 5] == 0 && bArr[i5] == 1 && (isWhiteHorizontal(bArr, i4 - 4, i4) || isWhiteHorizontal(bArr, i4 + 7, i4 + 11))) {
                    i2++;
                }
                int i6 = i3 + 6;
                if (i6 < height && array[i3][i4] == 1 && array[i3 + 1][i4] == 0 && array[i3 + 2][i4] == 1 && array[i3 + 3][i4] == 1 && array[i3 + 4][i4] == 1 && array[i3 + 5][i4] == 0 && array[i6][i4] == 1 && (isWhiteVertical(array, i4, i3 - 4, i3) || isWhiteVertical(array, i4, i3 + 7, i3 + 11))) {
                    i2++;
                }
            }
        }
        return i2 * 40;
    }

    static int d(ByteMatrix byteMatrix) {
        byte[][] array = byteMatrix.getArray();
        int width = byteMatrix.getWidth();
        int height = byteMatrix.getHeight();
        int i2 = 0;
        for (int i3 = 0; i3 < height; i3++) {
            byte[] bArr = array[i3];
            for (int i4 = 0; i4 < width; i4++) {
                if (bArr[i4] == 1) {
                    i2++;
                }
            }
        }
        int height2 = byteMatrix.getHeight() * byteMatrix.getWidth();
        return ((Math.abs((i2 << 1) - height2) * 10) / height2) * 10;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:16:0x003c A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:17:0x003d A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    static boolean e(int r1, int r2, int r3) {
        /*
            r0 = 1
            switch(r1) {
                case 0: goto L30;
                case 1: goto L31;
                case 2: goto L38;
                case 3: goto L34;
                case 4: goto L2c;
                case 5: goto L25;
                case 6: goto L1e;
                case 7: goto L14;
                default: goto L4;
            }
        L4:
            java.lang.IllegalArgumentException r2 = new java.lang.IllegalArgumentException
            java.lang.String r3 = "Invalid mask pattern: "
            java.lang.String r1 = java.lang.String.valueOf(r1)
            java.lang.String r1 = r3.concat(r1)
            r2.<init>(r1)
            throw r2
        L14:
            int r1 = r3 * r2
            int r1 = r1 % 3
            int r3 = r3 + r2
            r2 = r3 & 1
            int r1 = r1 + r2
        L1c:
            r1 = r1 & r0
            goto L3a
        L1e:
            int r3 = r3 * r2
            r1 = r3 & 1
            int r3 = r3 % 3
            int r1 = r1 + r3
            goto L1c
        L25:
            int r3 = r3 * r2
            r1 = r3 & 1
            int r3 = r3 % 3
            int r1 = r1 + r3
            goto L3a
        L2c:
            int r3 = r3 / 2
            int r2 = r2 / 3
        L30:
            int r3 = r3 + r2
        L31:
            r1 = r3 & 1
            goto L3a
        L34:
            int r3 = r3 + r2
            int r1 = r3 % 3
            goto L3a
        L38:
            int r1 = r2 % 3
        L3a:
            if (r1 != 0) goto L3d
            return r0
        L3d:
            r1 = 0
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.qrcode.encoder.MaskUtil.e(int, int, int):boolean");
    }

    private static boolean isWhiteHorizontal(byte[] bArr, int i2, int i3) {
        int iMin = Math.min(i3, bArr.length);
        for (int iMax = Math.max(i2, 0); iMax < iMin; iMax++) {
            if (bArr[iMax] == 1) {
                return false;
            }
        }
        return true;
    }

    private static boolean isWhiteVertical(byte[][] bArr, int i2, int i3, int i4) {
        int iMin = Math.min(i4, bArr.length);
        for (int iMax = Math.max(i3, 0); iMax < iMin; iMax++) {
            if (bArr[iMax][i2] == 1) {
                return false;
            }
        }
        return true;
    }
}
