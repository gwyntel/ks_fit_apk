package com.huawei.hms.scankit.p;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class j3 {

    /* renamed from: a, reason: collision with root package name */
    private final s f17426a;

    /* renamed from: b, reason: collision with root package name */
    private final List<f3> f17427b = new ArrayList();

    /* renamed from: c, reason: collision with root package name */
    private final int[] f17428c = new int[5];

    /* renamed from: d, reason: collision with root package name */
    private final v6 f17429d;

    private static final class b implements Comparator<f3>, Serializable {
        private b() {
        }

        @Override // java.util.Comparator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public int compare(f3 f3Var, f3 f3Var2) {
            return Float.compare(f3Var2.e(), f3Var.e());
        }
    }

    private static final class c implements Comparator<f3>, Serializable {

        /* renamed from: a, reason: collision with root package name */
        private final float f17430a;

        @Override // java.util.Comparator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public int compare(f3 f3Var, f3 f3Var2) {
            return Float.compare(Math.abs(f3Var2.e() - this.f17430a), Math.abs(f3Var.e() - this.f17430a));
        }

        private c(float f2) {
            this.f17430a = f2;
        }
    }

    public j3(s sVar, v6 v6Var) {
        this.f17426a = sVar;
        this.f17429d = v6Var;
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
        if (i2 < 5) {
            return false;
        }
        float f2 = i2 / 5.0f;
        float f3 = f2 / 1.6f;
        float f4 = f2 * 0.8f;
        float f5 = 0.8f * f3;
        if (Math.abs(f4 - iArr[0]) >= f5) {
            return false;
        }
        float f6 = f2 * 1.2f;
        float f7 = 1.2f * f3;
        return Math.abs(f6 - ((float) iArr[1])) < f7 && Math.abs(f2 - ((float) iArr[2])) < f3 && Math.abs(f6 - ((float) iArr[3])) < f7 && Math.abs(f4 - ((float) iArr[4])) < f5;
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
        if (i2 < 5) {
            return false;
        }
        float f2 = i2 / 5.0f;
        float f3 = f2 / 1.0f;
        float f4 = f2 * 0.8f;
        float f5 = 0.8f * f3;
        if (Math.abs(f4 - iArr[0]) >= f5) {
            return false;
        }
        float f6 = f2 * 1.2f;
        float f7 = 1.2f * f3;
        return Math.abs(f6 - ((float) iArr[1])) < f7 && Math.abs(f2 - ((float) iArr[2])) < f3 && Math.abs(f6 - ((float) iArr[3])) < f7 && Math.abs(f4 - ((float) iArr[4])) < f5;
    }

    private void d(int[] iArr) {
        iArr[0] = iArr[2];
        iArr[1] = iArr[3];
        iArr[2] = iArr[4];
        iArr[3] = 1;
        iArr[4] = 0;
    }

    final f3[] a(Map<l1, ?> map) throws com.huawei.hms.scankit.p.a {
        int iC = this.f17426a.c();
        int iE = this.f17426a.e();
        int[] iArr = new int[5];
        for (int i2 = 1; i2 < iC; i2 += 2) {
            a(iArr);
            int i3 = 0;
            for (int i4 = 0; i4 < iE; i4++) {
                if (this.f17426a.b(i4, i2)) {
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

    private float d(int i2, int i3, int i4) {
        s sVar = this.f17426a;
        int iC = sVar.c();
        int[] iArrA = a();
        int i5 = i2;
        while (i5 >= 0 && sVar.b(i3, i5)) {
            iArrA[2] = iArrA[2] + 1;
            i5--;
        }
        if (i5 < 0) {
            return Float.NaN;
        }
        while (i5 >= 0 && !sVar.b(i3, i5)) {
            iArrA[1] = iArrA[1] + 1;
            i5--;
        }
        if (i5 < 0 || iArrA[1] == 0) {
            return Float.NaN;
        }
        while (i5 >= 0 && sVar.b(i3, i5)) {
            iArrA[0] = iArrA[0] + 1;
            i5--;
        }
        if (iArrA[0] == 0) {
            return Float.NaN;
        }
        int i6 = i2 + 1;
        while (i6 < iC && sVar.b(i3, i6)) {
            iArrA[2] = iArrA[2] + 1;
            i6++;
        }
        if (i6 == iC || iArrA[2] == 0) {
            return Float.NaN;
        }
        while (i6 < iC && !sVar.b(i3, i6)) {
            iArrA[3] = iArrA[3] + 1;
            i6++;
        }
        if (i6 == iC || iArrA[3] == 0) {
            return Float.NaN;
        }
        while (i6 < iC && sVar.b(i3, i6)) {
            iArrA[4] = iArrA[4] + 1;
            i6++;
        }
        if (iArrA[4] != 0 && Math.abs(((((iArrA[0] + iArrA[1]) + iArrA[2]) + iArrA[3]) + r12) - i4) < i4 * 0.4f && b(iArrA)) {
            return a(iArrA, i6);
        }
        return Float.NaN;
    }

    private boolean b(int i2, int i3, int i4) {
        int i5;
        int i6;
        int[] iArrA = a();
        int iC = this.f17426a.c();
        int iE = this.f17426a.e();
        int i7 = 0;
        while (true) {
            int i8 = i2 + i7;
            if (i8 >= iC || i3 < i7 || !this.f17426a.b(i3 - i7, i8)) {
                break;
            }
            iArrA[2] = iArrA[2] + 1;
            i7++;
        }
        if (iArrA[2] == 0) {
            return false;
        }
        while (true) {
            int i9 = i2 + i7;
            if (i9 >= iC || i3 < i7 || this.f17426a.b(i3 - i7, i9)) {
                break;
            }
            iArrA[1] = iArrA[1] + 1;
            i7++;
        }
        if (iArrA[1] == 0) {
            return false;
        }
        while (true) {
            int i10 = i2 + i7;
            if (i10 >= iC || i3 < i7 || !this.f17426a.b(i3 - i7, i10)) {
                break;
            }
            iArrA[0] = iArrA[0] + 1;
            i7++;
        }
        if (iArrA[0] == 0) {
            return false;
        }
        int i11 = 1;
        while (i2 >= i11) {
            int i12 = i3 + i11;
            if (i12 >= iE || !this.f17426a.b(i12, i2 - i11)) {
                break;
            }
            iArrA[2] = iArrA[2] + 1;
            i11++;
        }
        while (i2 >= i11 && (i6 = i3 + i11) < iE && !this.f17426a.b(i6, i2 - i11)) {
            iArrA[3] = iArrA[3] + 1;
            i11++;
        }
        if (iArrA[3] == 0) {
            return false;
        }
        while (i2 >= i11 && (i5 = i3 + i11) < iE && this.f17426a.b(i5, i2 - i11)) {
            iArrA[4] = iArrA[4] + 1;
            i11++;
        }
        if (iArrA[4] != 0 && Math.abs(((((iArrA[0] + iArrA[1]) + iArrA[2]) + iArrA[3]) + r12) - i4) < i4 * 0.5f) {
            return c(iArrA);
        }
        return false;
    }

    private float c(int i2, int i3, int i4) {
        s sVar = this.f17426a;
        int iE = sVar.e();
        int[] iArrA = a();
        int i5 = i2;
        while (i5 >= 0 && sVar.b(i5, i3)) {
            iArrA[2] = iArrA[2] + 1;
            i5--;
        }
        if (i5 < 0) {
            return Float.NaN;
        }
        while (i5 >= 0 && !sVar.b(i5, i3)) {
            iArrA[1] = iArrA[1] + 1;
            i5--;
        }
        if (i5 < 0 || iArrA[1] == 0) {
            return Float.NaN;
        }
        while (i5 >= 0 && sVar.b(i5, i3)) {
            iArrA[0] = iArrA[0] + 1;
            i5--;
        }
        if (iArrA[0] == 0) {
            return Float.NaN;
        }
        int i6 = i2 + 1;
        while (i6 < iE && sVar.b(i6, i3)) {
            iArrA[2] = iArrA[2] + 1;
            i6++;
        }
        if (i6 == iE || iArrA[2] == 0) {
            return Float.NaN;
        }
        while (i6 < iE && !sVar.b(i6, i3)) {
            iArrA[3] = iArrA[3] + 1;
            i6++;
        }
        if (i6 == iE || iArrA[3] == 0) {
            return Float.NaN;
        }
        while (i6 < iE && sVar.b(i6, i3)) {
            iArrA[4] = iArrA[4] + 1;
            i6++;
        }
        if (iArrA[4] != 0 && Math.abs(((((iArrA[0] + iArrA[1]) + iArrA[2]) + iArrA[3]) + r12) - i4) < i4 * 0.2f && b(iArrA)) {
            return a(iArrA, i6);
        }
        return Float.NaN;
    }

    private static float a(int[] iArr, int i2) {
        return ((i2 - iArr[4]) - iArr[3]) - (iArr[2] / 2.0f);
    }

    private int[] a() {
        a(this.f17428c);
        return this.f17428c;
    }

    private void a(int[] iArr) {
        for (int i2 = 0; i2 < iArr.length; i2++) {
            iArr[i2] = 0;
        }
    }

    private boolean a(int i2, int i3, int i4) {
        int i5;
        int i6;
        int i7;
        int[] iArrA = a();
        int i8 = 0;
        while (i2 >= i8 && i3 >= i8 && this.f17426a.b(i3 - i8, i2 - i8)) {
            iArrA[2] = iArrA[2] + 1;
            i8++;
        }
        if (iArrA[2] == 0) {
            return false;
        }
        while (i2 >= i8 && i3 >= i8 && !this.f17426a.b(i3 - i8, i2 - i8)) {
            iArrA[1] = iArrA[1] + 1;
            i8++;
        }
        if (iArrA[1] == 0) {
            return false;
        }
        while (i2 >= i8 && i3 >= i8 && this.f17426a.b(i3 - i8, i2 - i8)) {
            iArrA[0] = iArrA[0] + 1;
            i8++;
        }
        if (iArrA[0] == 0) {
            return false;
        }
        int iC = this.f17426a.c();
        int iE = this.f17426a.e();
        int i9 = 1;
        while (true) {
            int i10 = i2 + i9;
            if (i10 >= iC || (i7 = i3 + i9) >= iE || !this.f17426a.b(i7, i10)) {
                break;
            }
            iArrA[2] = iArrA[2] + 1;
            i9++;
        }
        while (true) {
            int i11 = i2 + i9;
            if (i11 >= iC || (i6 = i3 + i9) >= iE || this.f17426a.b(i6, i11)) {
                break;
            }
            iArrA[3] = iArrA[3] + 1;
            i9++;
        }
        if (iArrA[3] == 0) {
            return false;
        }
        while (true) {
            int i12 = i2 + i9;
            if (i12 >= iC || (i5 = i3 + i9) >= iE || !this.f17426a.b(i5, i12)) {
                break;
            }
            iArrA[4] = iArrA[4] + 1;
            i9++;
        }
        if (iArrA[4] != 0 && Math.abs(((((iArrA[0] + iArrA[1]) + iArrA[2]) + iArrA[3]) + r13) - i4) < i4 * 0.5f) {
            return c(iArrA);
        }
        return false;
    }

    private f3[] b() throws com.huawei.hms.scankit.p.a {
        int i2 = 0;
        int i3 = 0;
        while (i3 < this.f17427b.size()) {
            f3 f3Var = this.f17427b.get(i3);
            if (f3Var.e() <= 5.0f || f3Var.a() <= 2) {
                this.f17427b.remove(i3);
                i3--;
            }
            i3++;
        }
        int size = this.f17427b.size();
        if (size >= 3) {
            Iterator<f3> it = this.f17427b.iterator();
            float fA = 0.0f;
            float f2 = 0.0f;
            float f3 = 0.0f;
            while (it.hasNext()) {
                float fE = it.next().e();
                f2 += fE;
                f3 += fE * fE;
            }
            float f4 = f2 / size;
            float fSqrt = (float) Math.sqrt((f3 / r1) - (f4 * f4));
            Collections.sort(this.f17427b, new c(f4));
            float fMax = Math.max(0.36f * f4, fSqrt);
            int i4 = 0;
            while (i4 < this.f17427b.size() && this.f17427b.size() > 3) {
                if (Math.abs(this.f17427b.get(i4).e() - f4) > fMax) {
                    this.f17427b.remove(i4);
                    i4--;
                }
                i4++;
            }
            int size2 = this.f17427b.size();
            while (this.f17427b.iterator().hasNext()) {
                fA += r3.next().a();
            }
            float f5 = fA / size2;
            while (i2 < this.f17427b.size() && this.f17427b.size() > 3) {
                if (this.f17427b.get(i2).a() <= 0.5f * f5) {
                    this.f17427b.remove(i2);
                    i2--;
                }
                i2++;
            }
            Collections.sort(this.f17427b, new b());
            List<f3> list = this.f17427b;
            return (f3[]) list.toArray(new f3[list.size()]);
        }
        throw com.huawei.hms.scankit.p.a.a();
    }

    protected final boolean a(int[] iArr, int i2, int i3) {
        int i4 = 0;
        int i5 = iArr[0] + iArr[1] + iArr[2] + iArr[3] + iArr[4];
        int iA = (int) a(iArr, i3);
        float fD = d(i2, iA, i5);
        if (!Float.isNaN(fD)) {
            int i6 = (int) fD;
            float fC = c(iA, i6, i5);
            if (!Float.isNaN(fC)) {
                int i7 = (int) fC;
                if (a(i6, i7, i5) && b(i6, i7, i5)) {
                    float f2 = i5 / 5.0f;
                    while (true) {
                        if (i4 < this.f17427b.size()) {
                            f3 f3Var = this.f17427b.get(i4);
                            if (f3Var.b(f2, fD, fC)) {
                                this.f17427b.set(i4, f3Var.c(fD, fC, f2));
                                break;
                            }
                            i4++;
                        } else {
                            f3 f3Var2 = new f3(fC, fD, f2);
                            this.f17427b.add(f3Var2);
                            v6 v6Var = this.f17429d;
                            if (v6Var != null) {
                                v6Var.a(f3Var2);
                            }
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
