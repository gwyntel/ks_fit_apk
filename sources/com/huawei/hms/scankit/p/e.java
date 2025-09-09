package com.huawei.hms.scankit.p;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
final class e {

    /* renamed from: a, reason: collision with root package name */
    private final s f17164a;

    /* renamed from: c, reason: collision with root package name */
    private final int f17166c;

    /* renamed from: d, reason: collision with root package name */
    private final int f17167d;

    /* renamed from: e, reason: collision with root package name */
    private final int f17168e;

    /* renamed from: f, reason: collision with root package name */
    private final int f17169f;

    /* renamed from: g, reason: collision with root package name */
    private final float f17170g;

    /* renamed from: i, reason: collision with root package name */
    private final v6 f17172i;

    /* renamed from: b, reason: collision with root package name */
    private final List<d> f17165b = new ArrayList(5);

    /* renamed from: h, reason: collision with root package name */
    private final int[] f17171h = new int[3];

    e(s sVar, int i2, int i3, int i4, int i5, float f2, v6 v6Var) {
        this.f17164a = sVar;
        this.f17166c = i2;
        this.f17167d = i3;
        this.f17168e = i4;
        this.f17169f = i5;
        this.f17170g = f2;
        this.f17172i = v6Var;
    }

    d a() throws a {
        d dVarA;
        int i2 = this.f17166c;
        int i3 = this.f17169f;
        int i4 = this.f17168e + i2;
        int i5 = this.f17167d + (i3 / 2);
        for (int i6 = 0; i6 < i3; i6++) {
            int i7 = ((i6 & 1) == 0 ? (i6 + 1) / 2 : -((i6 + 1) / 2)) + i5;
            int[] iArr = {0, 0, 0};
            int i8 = i2;
            while (i8 < i4 && !this.f17164a.b(i8, i7)) {
                i8++;
            }
            d dVarA2 = a(i7, i8, i4, iArr);
            if (dVarA2 != null) {
                return dVarA2;
            }
            if (a(iArr) && (dVarA = a(iArr, i7, i4)) != null) {
                return dVarA;
            }
        }
        if (this.f17165b.isEmpty()) {
            throw a.a();
        }
        return this.f17165b.get(0);
    }

    private d a(int i2, int i3, int i4, int[] iArr) {
        d dVarA;
        int i5 = 0;
        while (i3 < i4) {
            if (!this.f17164a.b(i3, i2)) {
                if (i5 == 1) {
                    i5++;
                }
                iArr[i5] = iArr[i5] + 1;
            } else if (i5 == 1) {
                iArr[1] = iArr[1] + 1;
            } else if (i5 == 2) {
                if (a(iArr) && (dVarA = a(iArr, i2, i3)) != null) {
                    return dVarA;
                }
                iArr[0] = iArr[2];
                iArr[1] = 1;
                iArr[2] = 0;
                i5 = 1;
            } else {
                i5++;
                iArr[i5] = iArr[i5] + 1;
            }
            i3++;
        }
        return null;
    }

    private static float a(int[] iArr, int i2) {
        return (i2 - iArr[2]) - (iArr[1] / 2.0f);
    }

    private boolean a(int[] iArr) {
        float f2 = this.f17170g;
        float f3 = (3.0f * f2) / 4.0f;
        for (int i2 = 0; i2 < 3; i2++) {
            if (Math.abs(f2 - iArr[i2]) >= f3) {
                return false;
            }
        }
        return true;
    }

    private float a(int i2, int i3, int i4, int i5) {
        int i6;
        s sVar = this.f17164a;
        int iC = sVar.c();
        int[] iArr = this.f17171h;
        iArr[0] = 0;
        iArr[1] = 0;
        iArr[2] = 0;
        int i7 = i2;
        while (i7 >= 0 && sVar.b(i3, i7)) {
            int i8 = iArr[1];
            if (i8 > i4) {
                break;
            }
            iArr[1] = i8 + 1;
            i7--;
        }
        if (i7 < 0 || iArr[1] > i4) {
            return Float.NaN;
        }
        while (i7 >= 0 && !sVar.b(i3, i7)) {
            int i9 = iArr[0];
            if (i9 > i4) {
                break;
            }
            iArr[0] = i9 + 1;
            i7--;
        }
        if (iArr[0] > i4) {
            return Float.NaN;
        }
        int i10 = i2 + 1;
        while (i10 < iC && sVar.b(i3, i10)) {
            int i11 = iArr[1];
            if (i11 > i4) {
                break;
            }
            iArr[1] = i11 + 1;
            i10++;
        }
        if (i10 == iC || iArr[1] > i4) {
            return Float.NaN;
        }
        while (i10 < iC && !sVar.b(i3, i10)) {
            int i12 = iArr[2];
            if (i12 > i4) {
                break;
            }
            iArr[2] = i12 + 1;
            i10++;
        }
        int i13 = iArr[2];
        if (i13 <= i4 && (i6 = iArr[0] + iArr[1] + i13) < i5 * 3 && i6 * 3 > i5 && a(iArr)) {
            return a(iArr, i10);
        }
        return Float.NaN;
    }

    private d a(int[] iArr, int i2, int i3) {
        int i4 = iArr[0] + iArr[1] + iArr[2];
        float fA = a(iArr, i3);
        float fA2 = a(i2, (int) fA, iArr[1] * 3, i4);
        if (Float.isNaN(fA2)) {
            return null;
        }
        float f2 = ((iArr[0] + iArr[1]) + iArr[2]) / 3.0f;
        for (d dVar : this.f17165b) {
            if (dVar.b(f2, fA2, fA)) {
                return dVar.c(fA2, fA, f2);
            }
        }
        d dVar2 = new d(fA, fA2, f2);
        this.f17165b.add(dVar2);
        v6 v6Var = this.f17172i;
        if (v6Var == null) {
            return null;
        }
        v6Var.a(dVar2);
        return null;
    }
}
