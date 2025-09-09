package com.huawei.hms.scankit.p;

import java.util.Map;

/* loaded from: classes4.dex */
public final class u1 {

    /* renamed from: a, reason: collision with root package name */
    private final p6 f17825a = new p6(o3.f17633l);

    public w1 a(s sVar, Map<l1, ?> map) throws a {
        t tVar = new t(sVar);
        try {
            try {
                return a(tVar, map);
            } catch (a unused) {
                tVar.e();
                tVar.a(true);
                tVar.d();
                tVar.c();
                tVar.a();
                w1 w1VarA = a(tVar, map);
                w1VarA.a(new l6(true));
                return w1VarA;
            }
        } catch (a unused2) {
            throw a.a();
        }
    }

    private w1 a(t tVar, Map<l1, ?> map) throws a {
        a8 a8VarD = tVar.d();
        c3 c3VarA = tVar.c().a();
        c1[] c1VarArrA = c1.a(tVar.b(), a8VarD, c3VarA);
        int iB = 0;
        for (c1 c1Var : c1VarArrA) {
            iB += c1Var.b();
        }
        byte[] bArr = new byte[iB];
        int i2 = 0;
        for (c1 c1Var2 : c1VarArrA) {
            byte[] bArrA = c1Var2.a();
            int iB2 = c1Var2.b();
            a(bArrA, iB2);
            int i3 = 0;
            while (i3 < iB2) {
                bArr[i2] = bArrA[i3];
                i3++;
                i2++;
            }
        }
        return p1.a(bArr, a8VarD, c3VarA, map);
    }

    private void a(byte[] bArr, int i2) throws a {
        int length = bArr.length;
        int[] iArr = new int[length];
        for (int i3 = 0; i3 < length; i3++) {
            iArr[i3] = bArr[i3] & 255;
        }
        try {
            this.f17825a.a(iArr, bArr.length - i2);
            for (int i4 = 0; i4 < i2; i4++) {
                bArr[i4] = (byte) iArr[i4];
            }
        } catch (a unused) {
            throw a.a();
        }
    }
}
