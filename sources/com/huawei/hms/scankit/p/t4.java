package com.huawei.hms.scankit.p;

import com.huawei.hms.hmsscankit.WriterException;
import org.apache.commons.io.IOUtils;

/* loaded from: classes4.dex */
final class t4 {

    /* renamed from: a, reason: collision with root package name */
    private static final int[][] f17815a = {new int[]{1, 1, 1, 1, 1, 1, 1}, new int[]{1, 0, 0, 0, 0, 0, 1}, new int[]{1, 0, 1, 1, 1, 0, 1}, new int[]{1, 0, 1, 1, 1, 0, 1}, new int[]{1, 0, 1, 1, 1, 0, 1}, new int[]{1, 0, 0, 0, 0, 0, 1}, new int[]{1, 1, 1, 1, 1, 1, 1}};

    /* renamed from: b, reason: collision with root package name */
    private static final int[][] f17816b = {new int[]{1, 1, 1, 1, 1}, new int[]{1, 0, 0, 0, 1}, new int[]{1, 0, 1, 0, 1}, new int[]{1, 0, 0, 0, 1}, new int[]{1, 1, 1, 1, 1}};

    /* renamed from: c, reason: collision with root package name */
    private static final int[][] f17817c = {new int[]{-1, -1, -1, -1, -1, -1, -1}, new int[]{6, 18, -1, -1, -1, -1, -1}, new int[]{6, 22, -1, -1, -1, -1, -1}, new int[]{6, 26, -1, -1, -1, -1, -1}, new int[]{6, 30, -1, -1, -1, -1, -1}, new int[]{6, 34, -1, -1, -1, -1, -1}, new int[]{6, 22, 38, -1, -1, -1, -1}, new int[]{6, 24, 42, -1, -1, -1, -1}, new int[]{6, 26, 46, -1, -1, -1, -1}, new int[]{6, 28, 50, -1, -1, -1, -1}, new int[]{6, 30, 54, -1, -1, -1, -1}, new int[]{6, 32, 58, -1, -1, -1, -1}, new int[]{6, 34, 62, -1, -1, -1, -1}, new int[]{6, 26, 46, 66, -1, -1, -1}, new int[]{6, 26, 48, 70, -1, -1, -1}, new int[]{6, 26, 50, 74, -1, -1, -1}, new int[]{6, 30, 54, 78, -1, -1, -1}, new int[]{6, 30, 56, 82, -1, -1, -1}, new int[]{6, 30, 58, 86, -1, -1, -1}, new int[]{6, 34, 62, 90, -1, -1, -1}, new int[]{6, 28, 50, 72, 94, -1, -1}, new int[]{6, 26, 50, 74, 98, -1, -1}, new int[]{6, 30, 54, 78, 102, -1, -1}, new int[]{6, 28, 54, 80, 106, -1, -1}, new int[]{6, 32, 58, 84, 110, -1, -1}, new int[]{6, 30, 58, 86, 114, -1, -1}, new int[]{6, 34, 62, 90, 118, -1, -1}, new int[]{6, 26, 50, 74, 98, 122, -1}, new int[]{6, 30, 54, 78, 102, 126, -1}, new int[]{6, 26, 52, 78, 104, 130, -1}, new int[]{6, 30, 56, 82, 108, 134, -1}, new int[]{6, 34, 60, 86, 112, 138, -1}, new int[]{6, 30, 58, 86, 114, 142, -1}, new int[]{6, 34, 62, 90, 118, 146, -1}, new int[]{6, 30, 54, 78, 102, 126, 150}, new int[]{6, 24, 50, 76, 102, 128, 154}, new int[]{6, 28, 54, 80, 106, 132, 158}, new int[]{6, 32, 58, 84, 110, 136, 162}, new int[]{6, 26, 54, 82, 110, 138, 166}, new int[]{6, 30, 58, 86, 114, 142, 170}};

    /* renamed from: d, reason: collision with root package name */
    private static final int[][] f17818d = {new int[]{8, 0}, new int[]{8, 1}, new int[]{8, 2}, new int[]{8, 3}, new int[]{8, 4}, new int[]{8, 5}, new int[]{8, 7}, new int[]{8, 8}, new int[]{7, 8}, new int[]{5, 8}, new int[]{4, 8}, new int[]{3, 8}, new int[]{2, 8}, new int[]{1, 8}, new int[]{0, 8}};

    static void a(c0 c0Var) {
        c0Var.a((byte) -1);
    }

    private static boolean b(int i2) {
        return i2 == -1;
    }

    static void c(b8 b8Var, c0 c0Var) throws WriterException {
        if (b8Var.f() < 7) {
            return;
        }
        r rVar = new r();
        a(b8Var, rVar);
        int i2 = 17;
        for (int i3 = 0; i3 < 6; i3++) {
            for (int i4 = 0; i4 < 3; i4++) {
                boolean zB = rVar.b(i2);
                i2--;
                c0Var.a(i3, (c0Var.b() - 11) + i4, zB);
                c0Var.a((c0Var.b() - 11) + i4, i3, zB);
            }
        }
    }

    private static void d(c0 c0Var) {
        int i2 = 8;
        while (i2 < c0Var.c() - 8) {
            int i3 = i2 + 1;
            int i4 = i3 % 2;
            if (b(c0Var.a(i2, 6))) {
                c0Var.a(i2, 6, i4);
            }
            if (b(c0Var.a(6, i2))) {
                c0Var.a(6, i2, i4);
            }
            i2 = i3;
        }
    }

    static void a(r rVar, b3 b3Var, b8 b8Var, int i2, c0 c0Var) throws WriterException {
        a(c0Var);
        a(b8Var, c0Var);
        a(b3Var, i2, c0Var);
        c(b8Var, c0Var);
        a(rVar, i2, c0Var);
    }

    private static void b(c0 c0Var) throws WriterException {
        if (c0Var.a(8, c0Var.b() - 8) == 0) {
            throw new WriterException();
        }
        c0Var.a(8, c0Var.b() - 8, 1);
    }

    private static void b(int i2, int i3, c0 c0Var) {
        for (int i4 = 0; i4 < 5; i4++) {
            int[] iArr = f17816b[i4];
            for (int i5 = 0; i5 < 5; i5++) {
                c0Var.a(i2 + i5, i3 + i4, iArr[i5]);
            }
        }
    }

    static void a(b8 b8Var, c0 c0Var) throws WriterException {
        c(c0Var);
        b(c0Var);
        b(b8Var, c0Var);
        d(c0Var);
    }

    private static void b(b8 b8Var, c0 c0Var) {
        if (b8Var.f() < 2) {
            return;
        }
        int iF = b8Var.f() - 1;
        int[][] iArr = f17817c;
        if (iF < iArr.length) {
            int[] iArr2 = iArr[iF];
            for (int i2 : iArr2) {
                if (i2 >= 0) {
                    for (int i3 : iArr2) {
                        if (i3 >= 0 && b(c0Var.a(i3, i2))) {
                            b(i3 - 2, i2 - 2, c0Var);
                        }
                    }
                }
            }
        }
    }

    private static void c(int i2, int i3, c0 c0Var) {
        for (int i4 = 0; i4 < 7; i4++) {
            int[] iArr = f17815a[i4];
            for (int i5 = 0; i5 < 7; i5++) {
                c0Var.a(i2 + i5, i3 + i4, iArr[i5]);
            }
        }
    }

    private static void d(int i2, int i3, c0 c0Var) throws WriterException {
        for (int i4 = 0; i4 < 7; i4++) {
            int i5 = i3 + i4;
            if (b(c0Var.a(i2, i5))) {
                c0Var.a(i2, i5, 0);
            } else {
                throw new WriterException();
            }
        }
    }

    private static void c(c0 c0Var) throws WriterException {
        int length = f17815a[0].length;
        c(0, 0, c0Var);
        c(c0Var.c() - length, 0, c0Var);
        c(0, c0Var.c() - length, c0Var);
        a(0, 7, c0Var);
        a(c0Var.c() - 8, 7, c0Var);
        a(0, c0Var.c() - 8, c0Var);
        d(7, 0, c0Var);
        d(c0Var.b() - 8, 0, c0Var);
        d(7, c0Var.b() - 7, c0Var);
    }

    static void a(b3 b3Var, int i2, c0 c0Var) throws WriterException {
        r rVar = new r();
        a(b3Var, i2, rVar);
        for (int i3 = 0; i3 < rVar.e(); i3++) {
            boolean zB = rVar.b((rVar.e() - 1) - i3);
            int[] iArr = f17818d[i3];
            c0Var.a(iArr[0], iArr[1], zB);
            if (i3 < 8) {
                c0Var.a((c0Var.c() - i3) - 1, 8, zB);
            } else {
                c0Var.a(8, (c0Var.b() - 7) + (i3 - 8), zB);
            }
        }
    }

    static void a(r rVar, int i2, c0 c0Var) throws WriterException {
        boolean zB;
        int iC = c0Var.c() - 1;
        int iB = c0Var.b() - 1;
        int i3 = 0;
        int i4 = -1;
        while (iC > 0) {
            if (iC == 6) {
                iC--;
            }
            while (iB >= 0 && iB < c0Var.b()) {
                for (int i5 = 0; i5 < 2; i5++) {
                    int i6 = iC - i5;
                    if (b(c0Var.a(i6, iB))) {
                        if (i3 < rVar.e()) {
                            zB = rVar.b(i3);
                            i3++;
                        } else {
                            zB = false;
                        }
                        if (i2 != -1 && r4.a(i2, i6, iB)) {
                            zB = !zB;
                        }
                        c0Var.a(i6, iB, zB);
                    }
                }
                iB += i4;
            }
            i4 = -i4;
            iB += i4;
            iC -= 2;
        }
        if (i3 == rVar.e()) {
            return;
        }
        throw new WriterException("Not all bits consumed: " + i3 + IOUtils.DIR_SEPARATOR_UNIX + rVar.e());
    }

    static int a(int i2) {
        return 32 - Integer.numberOfLeadingZeros(i2);
    }

    static int a(int i2, int i3) {
        if (i3 != 0) {
            int iA = a(i3);
            int iA2 = i2 << (iA - 1);
            while (a(iA2) >= iA) {
                iA2 ^= i3 << (a(iA2) - iA);
            }
            return iA2;
        }
        throw new IllegalArgumentException("0 polynomial");
    }

    static void a(b3 b3Var, int i2, r rVar) throws WriterException {
        if (h6.a(i2)) {
            int iA = (b3Var.a() << 3) | i2;
            rVar.a(iA, 5);
            rVar.a(a(iA, 1335), 10);
            r rVar2 = new r();
            rVar2.a(21522, 15);
            rVar.b(rVar2);
            if (rVar.e() == 15) {
                return;
            }
            throw new WriterException("should not happen but we got: " + rVar.e());
        }
        throw new WriterException("Invalid mask pattern");
    }

    static void a(b8 b8Var, r rVar) throws WriterException {
        rVar.a(b8Var.f(), 6);
        rVar.a(a(b8Var.f(), 7973), 12);
        if (rVar.e() == 18) {
            return;
        }
        throw new WriterException("should not happen but we got: " + rVar.e());
    }

    private static void a(int i2, int i3, c0 c0Var) throws WriterException {
        for (int i4 = 0; i4 < 8; i4++) {
            int i5 = i2 + i4;
            if (b(c0Var.a(i5, i3))) {
                c0Var.a(i5, i3, 0);
            } else {
                throw new WriterException();
            }
        }
    }
}
