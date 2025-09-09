package com.huawei.hms.scankit.p;

import java.util.Map;

/* loaded from: classes4.dex */
public final class v1 {

    /* renamed from: a, reason: collision with root package name */
    private final p6 f17874a = new p6(o3.f17633l);

    public w1 a(s sVar, Map<l1, ?> map) throws a {
        u uVar = new u(sVar);
        try {
            return a(uVar, map);
        } catch (a e2) {
            try {
                uVar.e();
                uVar.a(true);
                uVar.d();
                uVar.c();
                uVar.a();
                w1 w1VarA = a(uVar, map);
                w1VarA.a(new i6(true));
                return w1VarA;
            } catch (a unused) {
                throw e2;
            }
        }
    }

    private w1 a(u uVar, Map<l1, ?> map) throws a {
        b8 b8VarD = uVar.d();
        b3 b3VarB = uVar.c().b();
        d1[] d1VarArrA = d1.a(uVar.b(), b8VarD, b3VarB);
        int iB = 0;
        for (d1 d1Var : d1VarArrA) {
            iB += d1Var.b();
        }
        byte[] bArr = new byte[iB];
        int i2 = 0;
        for (d1 d1Var2 : d1VarArrA) {
            byte[] bArrA = d1Var2.a();
            int iB2 = d1Var2.b();
            a(bArrA, iB2);
            int i3 = 0;
            while (i3 < iB2) {
                bArr[i2] = bArrA[i3];
                i3++;
                i2++;
            }
        }
        return r1.a(bArr, b8VarD, b3VarB, map);
    }

    private void a(byte[] bArr, int i2) throws a {
        int length = bArr.length;
        int[] iArr = new int[length];
        for (int i3 = 0; i3 < length; i3++) {
            iArr[i3] = bArr[i3] & 255;
        }
        try {
            this.f17874a.a(iArr, bArr.length - i2);
            for (int i4 = 0; i4 < i2; i4++) {
                bArr[i4] = (byte) iArr[i4];
            }
        } catch (a unused) {
            throw a.a();
        }
    }
}
