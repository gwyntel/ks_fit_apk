package com.huawei.hms.scankit.p;

/* loaded from: classes4.dex */
final class r4 {
    static int a(c0 c0Var) {
        return a(c0Var, true) + a(c0Var, false);
    }

    static int b(c0 c0Var) {
        byte[][] bArrA = c0Var.a();
        int iC = c0Var.c();
        int iB = c0Var.b();
        int i2 = 0;
        for (int i3 = 0; i3 < iB - 1; i3++) {
            byte[] bArr = bArrA[i3];
            int i4 = 0;
            while (i4 < iC - 1) {
                byte b2 = bArr[i4];
                int i5 = i4 + 1;
                if (b2 == bArr[i5]) {
                    byte[] bArr2 = bArrA[i3 + 1];
                    if (b2 == bArr2[i4] && b2 == bArr2[i5]) {
                        i2++;
                    }
                }
                i4 = i5;
            }
        }
        return i2 * 3;
    }

    static int c(c0 c0Var) {
        byte[][] bArrA = c0Var.a();
        int iC = c0Var.c();
        int iB = c0Var.b();
        int i2 = 0;
        for (int i3 = 0; i3 < iB; i3++) {
            for (int i4 = 0; i4 < iC; i4++) {
                byte[] bArr = bArrA[i3];
                int i5 = i4 + 6;
                if (i5 < iC && bArr[i4] == 1 && bArr[i4 + 1] == 0 && bArr[i4 + 2] == 1 && bArr[i4 + 3] == 1 && bArr[i4 + 4] == 1 && bArr[i4 + 5] == 0 && bArr[i5] == 1 && (a(bArr, i4 - 4, i4) || a(bArr, i4 + 7, i4 + 11))) {
                    i2++;
                }
                int i6 = i3 + 6;
                if (i6 < iB && bArrA[i3][i4] == 1 && bArrA[i3 + 1][i4] == 0 && bArrA[i3 + 2][i4] == 1 && bArrA[i3 + 3][i4] == 1 && bArrA[i3 + 4][i4] == 1 && bArrA[i3 + 5][i4] == 0 && bArrA[i6][i4] == 1 && (a(bArrA, i4, i3 - 4, i3) || a(bArrA, i4, i3 + 7, i3 + 11))) {
                    i2++;
                }
            }
        }
        return i2 * 40;
    }

    static int d(c0 c0Var) {
        byte[][] bArrA = c0Var.a();
        int iC = c0Var.c();
        int iB = c0Var.b();
        int i2 = 0;
        for (int i3 = 0; i3 < iB; i3++) {
            byte[] bArr = bArrA[i3];
            for (int i4 = 0; i4 < iC; i4++) {
                if (bArr[i4] == 1) {
                    i2++;
                }
            }
        }
        int iB2 = c0Var.b() * c0Var.c();
        return ((Math.abs((i2 * 2) - iB2) * 10) / iB2) * 10;
    }

    private static boolean a(byte[] bArr, int i2, int i3) {
        int iMin = Math.min(i3, bArr.length);
        for (int iMax = Math.max(i2, 0); iMax < iMin; iMax++) {
            if (bArr[iMax] == 1) {
                return false;
            }
        }
        return true;
    }

    private static boolean a(byte[][] bArr, int i2, int i3, int i4) {
        int iMin = Math.min(i4, bArr.length);
        for (int iMax = Math.max(i3, 0); iMax < iMin; iMax++) {
            if (iMax < bArr.length && i2 < bArr[0].length && bArr[iMax][i2] == 1) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0044 A[ORIG_RETURN, RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:19:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    static boolean a(int r1, int r2, int r3) {
        /*
            r0 = 1
            switch(r1) {
                case 0: goto L37;
                case 1: goto L38;
                case 2: goto L3f;
                case 3: goto L3b;
                case 4: goto L33;
                case 5: goto L2c;
                case 6: goto L25;
                case 7: goto L1b;
                default: goto L4;
            }
        L4:
            java.lang.IllegalArgumentException r2 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r0 = "Invalid mask pattern: "
            r3.append(r0)
            r3.append(r1)
            java.lang.String r1 = r3.toString()
            r2.<init>(r1)
            throw r2
        L1b:
            int r1 = r3 * r2
            int r1 = r1 % 3
            int r3 = r3 + r2
            r2 = r3 & 1
            int r1 = r1 + r2
        L23:
            r1 = r1 & r0
            goto L41
        L25:
            int r3 = r3 * r2
            r1 = r3 & 1
            int r3 = r3 % 3
            int r1 = r1 + r3
            goto L23
        L2c:
            int r3 = r3 * r2
            r1 = r3 & 1
            int r3 = r3 % 3
            int r1 = r1 + r3
            goto L41
        L33:
            int r3 = r3 / 2
            int r2 = r2 / 3
        L37:
            int r3 = r3 + r2
        L38:
            r1 = r3 & 1
            goto L41
        L3b:
            int r3 = r3 + r2
            int r1 = r3 % 3
            goto L41
        L3f:
            int r1 = r2 % 3
        L41:
            if (r1 != 0) goto L44
            goto L45
        L44:
            r0 = 0
        L45:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.scankit.p.r4.a(int, int, int):boolean");
    }

    private static int a(c0 c0Var, boolean z2) {
        int iB = z2 ? c0Var.b() : c0Var.c();
        int iC = z2 ? c0Var.c() : c0Var.b();
        byte[][] bArrA = c0Var.a();
        int i2 = 0;
        for (int i3 = 0; i3 < iB; i3++) {
            byte b2 = -1;
            int i4 = 0;
            for (int i5 = 0; i5 < iC; i5++) {
                byte b3 = z2 ? bArrA[i3][i5] : bArrA[i5][i3];
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
}
