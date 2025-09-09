package com.huawei.hms.scankit.p;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class h3 {

    /* renamed from: a, reason: collision with root package name */
    private final s f17319a;

    /* renamed from: b, reason: collision with root package name */
    private final List<g3> f17320b = new ArrayList();

    /* renamed from: c, reason: collision with root package name */
    private final int[] f17321c = new int[5];

    /* renamed from: d, reason: collision with root package name */
    private final v6 f17322d;

    private static final class b implements Comparator<g3>, Serializable {
        private b() {
        }

        @Override // java.util.Comparator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public int compare(g3 g3Var, g3 g3Var2) {
            return Float.compare(g3Var2.e(), g3Var.e());
        }
    }

    private static final class c implements Comparator<g3>, Serializable {
        private c() {
        }

        @Override // java.util.Comparator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public int compare(g3 g3Var, g3 g3Var2) {
            return Integer.compare(g3Var2.a(), g3Var.a());
        }
    }

    private static final class d implements Comparator<g3>, Serializable {

        /* renamed from: a, reason: collision with root package name */
        private final float f17323a;

        @Override // java.util.Comparator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public int compare(g3 g3Var, g3 g3Var2) {
            return Float.compare(Math.abs(g3Var.e() - this.f17323a), Math.abs(g3Var2.e() - this.f17323a));
        }

        private d(float f2) {
            this.f17323a = f2;
        }
    }

    public h3(s sVar, v6 v6Var) {
        this.f17319a = sVar;
        this.f17322d = v6Var;
    }

    protected static boolean b(int[] iArr) {
        int i2 = 0;
        for (int i3 = 0; i3 < 5; i3++) {
            int i4 = iArr[i3];
            if (i4 == 0) {
                return false;
            }
            i2 += i4;
        }
        if (i2 < 6) {
            return false;
        }
        float f2 = i2 / 6.0f;
        float f3 = f2 / 1.5f;
        return Math.abs(f2 - ((float) iArr[0])) < f3 && Math.abs(f2 - ((float) iArr[1])) < f3 && Math.abs((f2 * 2.0f) - ((float) iArr[2])) < 2.0f * f3 && Math.abs(f2 - ((float) iArr[3])) < f3 && Math.abs(f2 - ((float) iArr[4])) < f3;
    }

    protected static boolean c(int[] iArr) {
        int i2 = 0;
        for (int i3 = 0; i3 < 5; i3++) {
            int i4 = iArr[i3];
            if (i4 == 0) {
                return false;
            }
            i2 += i4;
        }
        if (i2 < 6) {
            return false;
        }
        float f2 = i2 / 6.0f;
        float f3 = f2 / 1.0f;
        return Math.abs(f2 - ((float) iArr[0])) < f3 && Math.abs(f2 - ((float) iArr[1])) < f3 && Math.abs((f2 * 2.0f) - ((float) iArr[2])) < 2.0f * f3 && Math.abs(f2 - ((float) iArr[3])) < f3 && Math.abs(f2 - ((float) iArr[4])) < f3;
    }

    private void d(int[] iArr) {
        iArr[0] = iArr[2];
        iArr[1] = iArr[3];
        iArr[2] = iArr[4];
        iArr[3] = 1;
        iArr[4] = 0;
    }

    final g3[] a(Map<l1, ?> map) throws com.huawei.hms.scankit.p.a {
        int iC = this.f17319a.c();
        int iE = this.f17319a.e();
        int[] iArr = new int[5];
        for (int i2 = 0; i2 < iC; i2++) {
            a(iArr);
            int i3 = 0;
            for (int i4 = 0; i4 < iE; i4++) {
                if (this.f17319a.b(i4, i2)) {
                    if ((i3 & 1) == 1) {
                        i3++;
                    }
                    iArr[i3] = iArr[i3] + 1;
                } else if ((i3 & 1) != 0) {
                    iArr[i3] = iArr[i3] + 1;
                } else if (i3 != 4) {
                    i3++;
                    iArr[i3] = iArr[i3] + 1;
                } else if (b(iArr) && a(iArr, i2, i4)) {
                    a(iArr);
                    i3 = 0;
                } else {
                    d(iArr);
                    i3 = 3;
                }
            }
            if (b(iArr)) {
                a(iArr, i2, iE);
            }
        }
        return b();
    }

    private float b(int i2, int i3, int i4, int i5) {
        int i6;
        int i7;
        int i8;
        s sVar = this.f17319a;
        int iC = sVar.c();
        int[] iArrA = a();
        int i9 = i2;
        while (i9 >= 0 && sVar.b(i3, i9)) {
            iArrA[2] = iArrA[2] + 1;
            i9--;
        }
        if (i9 < 0) {
            return Float.NaN;
        }
        while (i9 >= 0 && !sVar.b(i3, i9)) {
            int i10 = iArrA[1];
            if (i10 > i4) {
                break;
            }
            iArrA[1] = i10 + 1;
            i9--;
        }
        if (i9 < 0 || iArrA[1] > i4) {
            return Float.NaN;
        }
        while (i9 >= 0 && sVar.b(i3, i9) && (i8 = iArrA[0]) <= i4) {
            iArrA[0] = i8 + 1;
            i9--;
        }
        if (iArrA[0] > i4) {
            return Float.NaN;
        }
        int i11 = i2 + 1;
        while (i11 < iC && sVar.b(i3, i11)) {
            iArrA[2] = iArrA[2] + 1;
            i11++;
        }
        if (i11 == iC) {
            return Float.NaN;
        }
        while (i11 < iC && !sVar.b(i3, i11) && (i7 = iArrA[3]) < i4) {
            iArrA[3] = i7 + 1;
            i11++;
        }
        if (i11 == iC || iArrA[3] >= i4) {
            return Float.NaN;
        }
        while (i11 < iC && sVar.b(i3, i11) && (i6 = iArrA[4]) < i4) {
            iArrA[4] = i6 + 1;
            i11++;
        }
        int i12 = iArrA[4];
        if (i12 < i4 && Math.abs(((((iArrA[0] + iArrA[1]) + iArrA[2]) + iArrA[3]) + i12) - i5) * 5 < i5 * 2 && b(iArrA)) {
            return a(iArrA, i11);
        }
        return Float.NaN;
    }

    private static float a(int[] iArr, int i2) {
        return ((i2 - iArr[4]) - iArr[3]) - (iArr[2] / 2.0f);
    }

    private int[] a() {
        a(this.f17321c);
        return this.f17321c;
    }

    private void a(int[] iArr) {
        for (int i2 = 0; i2 < iArr.length; i2++) {
            iArr[i2] = 0;
        }
    }

    private boolean a(int i2, int i3) {
        int i4;
        int i5;
        int i6;
        int[] iArrA = a();
        int i7 = 0;
        while (i2 >= i7 && i3 >= i7 && this.f17319a.b(i3 - i7, i2 - i7)) {
            iArrA[2] = iArrA[2] + 1;
            i7++;
        }
        if (iArrA[2] == 0) {
            return false;
        }
        while (i2 >= i7 && i3 >= i7 && !this.f17319a.b(i3 - i7, i2 - i7)) {
            iArrA[1] = iArrA[1] + 1;
            i7++;
        }
        if (iArrA[1] == 0) {
            return false;
        }
        while (i2 >= i7 && i3 >= i7 && this.f17319a.b(i3 - i7, i2 - i7)) {
            iArrA[0] = iArrA[0] + 1;
            i7++;
        }
        if (iArrA[0] == 0) {
            return false;
        }
        int iC = this.f17319a.c();
        int iE = this.f17319a.e();
        int i8 = 1;
        while (true) {
            int i9 = i2 + i8;
            if (i9 >= iC || (i6 = i3 + i8) >= iE || !this.f17319a.b(i6, i9)) {
                break;
            }
            iArrA[2] = iArrA[2] + 1;
            i8++;
        }
        while (true) {
            int i10 = i2 + i8;
            if (i10 >= iC || (i5 = i3 + i8) >= iE || this.f17319a.b(i5, i10)) {
                break;
            }
            iArrA[3] = iArrA[3] + 1;
            i8++;
        }
        if (iArrA[3] == 0) {
            return false;
        }
        while (true) {
            int i11 = i2 + i8;
            if (i11 >= iC || (i4 = i3 + i8) >= iE || !this.f17319a.b(i4, i11)) {
                break;
            }
            iArrA[4] = iArrA[4] + 1;
            i8++;
        }
        if (iArrA[4] == 0) {
            return false;
        }
        return c(iArrA);
    }

    private g3[] b() throws com.huawei.hms.scankit.p.a {
        int i2 = 0;
        while (i2 < this.f17320b.size()) {
            g3 g3Var = this.f17320b.get(i2);
            if (g3Var.e() <= 5.0f && g3Var.a() <= 2) {
                this.f17320b.remove(i2);
                i2--;
            }
            i2++;
        }
        int size = this.f17320b.size();
        int i3 = 3;
        if (size >= 3) {
            if (size >= 4) {
                float f2 = 0.0f;
                float fA = 0.0f;
                while (this.f17320b.iterator().hasNext()) {
                    fA += r8.next().a();
                }
                float f3 = fA / size;
                int i4 = 0;
                while (i4 < this.f17320b.size() && this.f17320b.size() > 4) {
                    if (this.f17320b.get(i4).a() <= 0.5f * f3) {
                        this.f17320b.remove(i4);
                        i4--;
                    }
                    i4++;
                }
                int size2 = this.f17320b.size();
                Iterator<g3> it = this.f17320b.iterator();
                float f4 = 0.0f;
                while (it.hasNext()) {
                    float fE = it.next().e();
                    f2 += fE;
                    f4 += fE * fE;
                }
                float f5 = f2 / size2;
                float fSqrt = (float) Math.sqrt((f4 / r2) - (f5 * f5));
                Collections.sort(this.f17320b, new d(f5));
                float fMax = Math.max(0.36f * f5, fSqrt);
                int i5 = 0;
                while (i5 < this.f17320b.size() && this.f17320b.size() > 4) {
                    if (Math.abs(this.f17320b.get(i5).e() - f5) > fMax) {
                        this.f17320b.remove(i5);
                        i5--;
                    }
                    i5++;
                }
                int size3 = this.f17320b.size();
                if (size3 >= 4) {
                    Collections.sort(this.f17320b, new c());
                    if (size3 > 4 && this.f17320b.get(3).a() - this.f17320b.get(4).a() > 2) {
                        while (4 < this.f17320b.size()) {
                            this.f17320b.remove(4);
                        }
                    }
                    double[] dArr = new double[3];
                    while (i3 < this.f17320b.size()) {
                        dArr[0] = a(this.f17320b.get(0), this.f17320b.get(1));
                        dArr[1] = a(this.f17320b.get(1), this.f17320b.get(i3));
                        dArr[2] = a(this.f17320b.get(0), this.f17320b.get(i3));
                        Arrays.sort(dArr);
                        double d2 = dArr[1];
                        double d3 = dArr[0];
                        double dSqrt = ((d2 + d3) - dArr[2]) / ((Math.sqrt(d3) * 2.0d) * Math.sqrt(dArr[1]));
                        dArr[0] = a(this.f17320b.get(0), this.f17320b.get(2));
                        dArr[1] = a(this.f17320b.get(2), this.f17320b.get(i3));
                        dArr[2] = a(this.f17320b.get(0), this.f17320b.get(i3));
                        Arrays.sort(dArr);
                        double d4 = dArr[1];
                        double d5 = dArr[0];
                        double dSqrt2 = ((d4 + d5) - dArr[2]) / ((Math.sqrt(d5) * 2.0d) * Math.sqrt(dArr[1]));
                        if (Math.abs(dSqrt) > 0.25d || Math.abs(dSqrt2) > 0.25d) {
                            this.f17320b.remove(i3);
                            i3--;
                        }
                        i3++;
                    }
                }
            }
            Collections.sort(this.f17320b, new b());
            List<g3> list = this.f17320b;
            return (g3[]) list.toArray(new g3[list.size()]);
        }
        throw com.huawei.hms.scankit.p.a.a();
    }

    private float a(int i2, int i3, int i4, int i5) {
        int i6;
        int i7;
        int i8;
        s sVar = this.f17319a;
        int iE = sVar.e();
        int[] iArrA = a();
        int i9 = i2;
        while (i9 >= 0 && sVar.b(i9, i3)) {
            iArrA[2] = iArrA[2] + 1;
            i9--;
        }
        if (i9 < 0) {
            return Float.NaN;
        }
        while (i9 >= 0 && !sVar.b(i9, i3)) {
            int i10 = iArrA[1];
            if (i10 > i4) {
                break;
            }
            iArrA[1] = i10 + 1;
            i9--;
        }
        if (i9 < 0 || iArrA[1] > i4) {
            return Float.NaN;
        }
        while (i9 >= 0 && sVar.b(i9, i3) && (i8 = iArrA[0]) <= i4) {
            iArrA[0] = i8 + 1;
            i9--;
        }
        if (iArrA[0] > i4) {
            return Float.NaN;
        }
        int i11 = i2 + 1;
        while (i11 < iE && sVar.b(i11, i3)) {
            iArrA[2] = iArrA[2] + 1;
            i11++;
        }
        if (i11 == iE) {
            return Float.NaN;
        }
        while (i11 < iE && !sVar.b(i11, i3) && (i7 = iArrA[3]) < i4) {
            iArrA[3] = i7 + 1;
            i11++;
        }
        if (i11 == iE || iArrA[3] >= i4) {
            return Float.NaN;
        }
        while (i11 < iE && sVar.b(i11, i3) && (i6 = iArrA[4]) < i4) {
            iArrA[4] = i6 + 1;
            i11++;
        }
        int i12 = iArrA[4];
        if (i12 < i4 && Math.abs(((((iArrA[0] + iArrA[1]) + iArrA[2]) + iArrA[3]) + i12) - i5) * 5 < i5 && b(iArrA)) {
            return a(iArrA, i11);
        }
        return Float.NaN;
    }

    protected final boolean a(int[] iArr, int i2, int i3) {
        int i4 = 0;
        int i5 = iArr[0] + iArr[1] + iArr[2] + iArr[3] + iArr[4];
        int iA = (int) a(iArr, i3);
        float fB = b(i2, iA, iArr[2], i5);
        if (!Float.isNaN(fB)) {
            int i6 = (int) fB;
            float fA = a(iA, i6, iArr[2], i5);
            if (!Float.isNaN(fA) && a(i6, (int) fA)) {
                float f2 = i5 / 6.0f;
                while (true) {
                    if (i4 < this.f17320b.size()) {
                        g3 g3Var = this.f17320b.get(i4);
                        if (g3Var.b(f2, fB, fA)) {
                            this.f17320b.set(i4, g3Var.c(fB, fA, f2));
                            break;
                        }
                        i4++;
                    } else {
                        g3 g3Var2 = new g3(fA, fB, f2);
                        this.f17320b.add(g3Var2);
                        v6 v6Var = this.f17322d;
                        if (v6Var != null) {
                            v6Var.a(g3Var2);
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    private static double a(g3 g3Var, g3 g3Var2) {
        double dB = g3Var.b() - g3Var2.b();
        double dC = g3Var.c() - g3Var2.c();
        return (dB * dB) + (dC * dC);
    }
}
