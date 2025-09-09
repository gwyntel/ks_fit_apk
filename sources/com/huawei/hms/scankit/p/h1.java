package com.huawei.hms.scankit.p;

import com.huawei.hms.scankit.aiscan.common.BarcodeFormat;
import java.util.Map;

/* loaded from: classes4.dex */
public final class h1 implements o6 {

    /* renamed from: b, reason: collision with root package name */
    private static final u6[] f17315b = new u6[0];

    /* renamed from: a, reason: collision with root package name */
    private final t1 f17316a = new t1();

    @Override // com.huawei.hms.scankit.p.o6
    public s6 a(p pVar, Map<l1, ?> map) throws a {
        j2 j2VarA = new d2(pVar.b()).a();
        try {
            w1 w1VarA = this.f17316a.a(j2VarA.a(), map);
            return new s6(w1VarA.d(), w1VarA.c(), j2VarA.d(), BarcodeFormat.DATA_MATRIX);
        } catch (a e2) {
            if (j2VarA.d() == null || r3.f17716c) {
                throw e2;
            }
            double dSqrt = Math.sqrt(Math.pow(j2VarA.d()[0].b() - j2VarA.d()[1].b(), 2.0d) + Math.pow(j2VarA.d()[0].c() - j2VarA.d()[1].c(), 2.0d));
            double dSqrt2 = Math.sqrt(Math.pow(j2VarA.d()[0].b() - j2VarA.d()[3].b(), 2.0d) + Math.pow(j2VarA.d()[0].c() - j2VarA.d()[3].c(), 2.0d));
            if (this.f17316a.a() == null || Math.abs(dSqrt - dSqrt2) / dSqrt >= 0.1d) {
                throw e2;
            }
            return new s6(null, null, j2VarA.d(), BarcodeFormat.DATA_MATRIX);
        }
    }
}
