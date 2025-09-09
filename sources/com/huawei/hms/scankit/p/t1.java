package com.huawei.hms.scankit.p;

import java.util.Map;

/* loaded from: classes4.dex */
public final class t1 {

    /* renamed from: b, reason: collision with root package name */
    private z7 f17808b = null;

    /* renamed from: a, reason: collision with root package name */
    private final p6 f17807a = new p6(o3.f17634m);

    public w1 a(s sVar, Map<l1, ?> map) throws a {
        v vVar = new v(sVar);
        z7 z7VarA = vVar.a();
        this.f17808b = z7VarA;
        e1[] e1VarArrA = e1.a(vVar.b(), z7VarA);
        int iB = 0;
        for (e1 e1Var : e1VarArrA) {
            iB += e1Var.b();
        }
        byte[] bArr = new byte[iB];
        int length = e1VarArrA.length;
        for (int i2 = 0; i2 < length; i2++) {
            e1 e1Var2 = e1VarArrA[i2];
            byte[] bArrA = e1Var2.a();
            int iB2 = e1Var2.b();
            a(bArrA, iB2);
            for (int i3 = 0; i3 < iB2; i3++) {
                bArr[(i3 * length) + i2] = bArrA[i3];
            }
        }
        return o1.a(bArr, map);
    }

    private void a(byte[] bArr, int i2) throws a {
        int length = bArr.length;
        int[] iArr = new int[length];
        for (int i3 = 0; i3 < length; i3++) {
            iArr[i3] = bArr[i3] & 255;
        }
        try {
            this.f17807a.a(iArr, bArr.length - i2);
            for (int i4 = 0; i4 < i2; i4++) {
                bArr[i4] = (byte) iArr[i4];
            }
        } catch (a unused) {
            throw a.a();
        }
    }

    public z7 a() {
        return this.f17808b;
    }
}
