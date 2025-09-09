package com.huawei.hms.scankit.p;

import com.huawei.hms.scankit.aiscan.common.BarcodeFormat;
import java.util.EnumMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class z3 implements o6 {

    /* renamed from: a, reason: collision with root package name */
    private Map<l1, Object> f18054a = new EnumMap(l1.class);

    /* renamed from: b, reason: collision with root package name */
    private final u1 f18055b = new u1();

    private s6 a(int i2, e2 e2Var, Map<l1, ?> map) throws a {
        int i3 = 1;
        int iMax = Math.max(1, i2 - 2);
        int iMin = Math.min(6, i2 + 2);
        int iMax2 = Math.max(Math.abs(i2 - iMax), Math.abs(i2 - iMin));
        int i4 = 0;
        j2 j2VarA = null;
        while (i4 <= iMax2 * 2) {
            i2 += i4 * i3;
            if (i2 >= iMax && i2 <= iMin) {
                try {
                    j2VarA = e2Var.a(i2);
                    w1 w1VarA = this.f18055b.a(j2VarA.a(), map);
                    if (w1VarA.d() != null) {
                        return new s6(w1VarA.d(), w1VarA.c(), j2VarA.d(), BarcodeFormat.HARMONY_CODE);
                    }
                    continue;
                } catch (a unused) {
                    continue;
                }
            }
            i4++;
            i3 *= -1;
        }
        if (j2VarA == null || j2VarA.d().length <= 3) {
            return null;
        }
        return new s6(null, null, j2VarA.d(), BarcodeFormat.HARMONY_CODE);
    }

    private s6 a(int i2, int i3, g3[] g3VarArr, e2 e2Var, Map<l1, ?> map) throws a {
        char c2;
        char c3;
        float fB;
        float fC;
        float fMax;
        float fMax2;
        char c4 = 2;
        int i4 = 1;
        char c5 = 0;
        int i5 = 0;
        while (i5 <= i2 - 2) {
            int i6 = i5 + 1;
            int i7 = i6;
            while (i7 <= i2 - 1) {
                int i8 = i7 + 1;
                int i9 = i8;
                while (i9 <= i2) {
                    int i10 = i2;
                    while (i10 < i3) {
                        g3 g3Var = g3VarArr[i5];
                        g3 g3Var2 = g3VarArr[i7];
                        g3 g3Var3 = g3VarArr[i9];
                        g3[] g3VarArr2 = new g3[3];
                        g3VarArr2[c5] = g3Var;
                        g3VarArr2[i4] = g3Var2;
                        g3VarArr2[c4] = g3Var3;
                        u6.a(g3VarArr2);
                        g3 g3Var4 = g3VarArr[i10];
                        try {
                            fB = (g3VarArr2[c4].b() - g3VarArr2[i4].b()) + g3VarArr2[c5].b();
                            fC = (g3VarArr2[c4].c() - g3VarArr2[i4].c()) + g3VarArr2[c5].c();
                            try {
                                try {
                                    fMax = Math.max(Math.abs(g3VarArr2[i4].b() - g3VarArr2[c5].b()), Math.abs(g3VarArr2[i4].b() - g3VarArr2[c4].b()));
                                    c3 = 0;
                                    try {
                                        c2 = 2;
                                    } catch (a unused) {
                                        c2 = 2;
                                        i10++;
                                        i4 = 1;
                                        c5 = c3;
                                        c4 = c2;
                                    }
                                    try {
                                        fMax2 = Math.max(Math.abs(g3VarArr2[i4].c() - g3VarArr2[0].c()), Math.abs(g3VarArr2[i4].c() - g3VarArr2[2].c()));
                                    } catch (a unused2) {
                                        i10++;
                                        i4 = 1;
                                        c5 = c3;
                                        c4 = c2;
                                    }
                                } catch (a unused3) {
                                    c3 = 0;
                                }
                            } catch (a unused4) {
                                c2 = c4;
                                c3 = 0;
                            }
                        } catch (a unused5) {
                            c2 = c4;
                            c3 = c5;
                        }
                        if (Math.abs(fB - g3Var4.b()) >= fMax / 2.0f || Math.abs(fC - g3Var4.c()) >= fMax2 / 2.0f) {
                            i10++;
                            i4 = 1;
                            c5 = c3;
                            c4 = c2;
                        } else {
                            try {
                                s6 s6VarA = a(e2Var.a(g3VarArr2, g3Var4), e2Var, map);
                                if (s6VarA != null) {
                                    return s6VarA;
                                }
                            } catch (a unused6) {
                            }
                            i10++;
                            i4 = 1;
                            c5 = c3;
                            c4 = c2;
                        }
                    }
                    char c6 = c4;
                    int i11 = i4;
                    i9 += i11;
                    i4 = i11;
                    c5 = c5;
                    c4 = c6;
                }
                i4 = i4;
                i7 = i8;
                c5 = c5;
                c4 = c4;
            }
            i4 = i4;
            i5 = i6;
            c5 = c5;
            c4 = c4;
        }
        throw a.a();
    }

    @Override // com.huawei.hms.scankit.p.o6
    public s6 a(p pVar, Map<l1, ?> map) throws a {
        e2 e2Var = new e2(pVar.b());
        g3[] g3VarArrA = e2Var.a(map);
        int length = g3VarArrA.length;
        if (length == 3) {
            u6.a(g3VarArrA);
            s6 s6VarA = a(e2Var.a(g3VarArrA, null), e2Var, map);
            if (s6VarA != null) {
                return s6VarA;
            }
        } else {
            if (length == 4) {
                g3[] g3VarArr = new g3[3];
                for (int i2 = 0; i2 < 3; i2++) {
                    g3VarArr[i2] = g3VarArrA[i2];
                }
                u6.a(g3VarArr);
                s6 s6VarA2 = a(e2Var.a(g3VarArr, g3VarArrA[3]), e2Var, map);
                if (s6VarA2 != null) {
                    return s6VarA2;
                }
            } else {
                float fE = ((g3VarArrA[0].e() + g3VarArrA[1].e()) + g3VarArrA[2].e()) / 3.0f;
                float fE2 = g3VarArrA[length - 1].e();
                int i3 = length - 2;
                while (i3 > 2 && Math.abs(g3VarArrA[i3].e() - fE) >= Math.abs(g3VarArrA[i3].e() - fE2)) {
                    fE2 = (((r6 - 1) * fE2) + g3VarArrA[i3].e()) / (length - i3);
                    i3--;
                }
                return a(i3, length, g3VarArrA, e2Var, map);
            }
        }
        throw a.a();
    }
}
