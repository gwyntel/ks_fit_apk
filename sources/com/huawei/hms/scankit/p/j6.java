package com.huawei.hms.scankit.p;

import com.huawei.hms.scankit.aiscan.common.BarcodeFormat;
import java.util.Map;

/* loaded from: classes4.dex */
public class j6 implements o6 {

    /* renamed from: a, reason: collision with root package name */
    private final v1 f17443a = new v1();

    public static void a(p4 p4Var) {
        int iA = p4Var.a();
        if (iA == p4Var.c() && iA == 805) {
            r3.f17735v[0] = true;
        }
    }

    public static void a(p4 p4Var, s6 s6Var) {
        boolean z2;
        int iA = p4Var.a();
        int iC = p4Var.c();
        while (true) {
            if (r3.f17736w.size() == 0) {
                z2 = false;
                break;
            }
            int iIntValue = r3.f17736w.pop().intValue();
            if (iIntValue != 0 && iA % iIntValue == 0) {
                z2 = true;
                break;
            }
        }
        if (iA != iC || !z2 || s6Var == null || s6Var.j() == null) {
            return;
        }
        float fMin = Math.min(Math.min(s6Var.j()[0].b(), s6Var.j()[1].b()), s6Var.j()[2].b());
        if ((Math.max(Math.max(s6Var.j()[0].b(), s6Var.j()[1].b()), s6Var.j()[2].b()) - fMin) * (Math.max(Math.max(s6Var.j()[0].c(), s6Var.j()[1].c()), s6Var.j()[2].c()) - Math.min(Math.min(s6Var.j()[0].c(), s6Var.j()[1].c()), s6Var.j()[2].c())) > iA * iC * 0.8d) {
            r3.f17735v[1] = true;
        }
    }

    @Override // com.huawei.hms.scankit.p.o6
    public final s6 a(p pVar, Map<l1, ?> map) throws a {
        w1 w1VarA;
        boolean z2 = true;
        r3.f17723j++;
        try {
            j2 j2VarA = new g2(pVar.b()).a(map);
            boolean z3 = a(j2VarA) > 0;
            try {
                w1VarA = this.f17443a.a(j2VarA.a(), map);
                z2 = false;
            } catch (Exception unused) {
                w1VarA = null;
            }
            if (z2 && z3) {
                return new s6(null, null, j2VarA.b(), BarcodeFormat.QR_CODE);
            }
            if (z2) {
                throw a.a();
            }
            if (w1VarA == null) {
                return null;
            }
            u6[] u6VarArrD = j2VarA.d();
            if (w1VarA.b() instanceof i6) {
                ((i6) w1VarA.b()).a(u6VarArrD);
            }
            s6 s6Var = new s6(w1VarA.d(), w1VarA.c(), u6VarArrD, BarcodeFormat.QR_CODE);
            s6Var.b(j2VarA.b());
            return s6Var;
        } catch (a unused2) {
            throw a.a();
        }
    }

    private int a(j2 j2Var) {
        r3.f17722i = j2Var.c();
        s sVarA = j2Var.a();
        int[] iArr = {3, sVarA.e() - 4, 3};
        int[] iArr2 = {3, 3, sVarA.c() - 4};
        int i2 = 0;
        for (int i3 = 0; i3 < 3; i3++) {
            if (a(sVarA, iArr[i3], iArr2[i3])) {
                i2++;
            }
        }
        return i2;
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [boolean, int] */
    private boolean a(s sVar, int i2, int i3) {
        if (sVar == null || sVar.c() < 21 || sVar.e() < 21) {
            return false;
        }
        ?? B = sVar.b(i2, i3);
        int i4 = B;
        if (sVar.b(i2 + 1, i3)) {
            i4 = B + 1;
        }
        int i5 = i4;
        if (!sVar.b(i2 + 2, i3)) {
            i5 = i4 + 1;
        }
        int i6 = i5;
        if (sVar.b(i2 + 3, i3)) {
            i6 = i5 + 1;
        }
        int i7 = i6;
        if (sVar.b(i2 - 1, i3)) {
            i7 = i6 + 1;
        }
        int i8 = i7;
        if (!sVar.b(i2 - 2, i3)) {
            i8 = i7 + 1;
        }
        int i9 = i8;
        if (sVar.b(i2 - 3, i3)) {
            i9 = i8 + 1;
        }
        int i10 = i9;
        if (sVar.b(i2, i3 + 1)) {
            i10 = i9 + 1;
        }
        int i11 = i10;
        if (!sVar.b(i2, i3 + 2)) {
            i11 = i10 + 1;
        }
        int i12 = i11;
        if (sVar.b(i2, i3 + 3)) {
            i12 = i11 + 1;
        }
        int i13 = i12;
        if (sVar.b(i2, i3 - 1)) {
            i13 = i12 + 1;
        }
        int i14 = i13;
        if (!sVar.b(i2, i3 - 2)) {
            i14 = i13 + 1;
        }
        int i15 = i14;
        if (sVar.b(i2, i3 - 3)) {
            i15 = i14 + 1;
        }
        return i15 > 10;
    }
}
