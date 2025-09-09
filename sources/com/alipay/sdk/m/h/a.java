package com.alipay.sdk.m.h;

/* loaded from: classes2.dex */
public class a extends com.alipay.sdk.m.g.a {

    /* renamed from: d, reason: collision with root package name */
    public static final /* synthetic */ boolean f9275d = true;

    public a(byte[] bArr) {
        super(bArr);
    }

    public static a a(String str, long j2, b bVar, short s2, e eVar) throws Exception {
        byte[] bArrA = com.alipay.sdk.m.g.c.a((byte) 1);
        boolean z2 = f9275d;
        if (!z2 && bArrA.length != 1) {
            throw new AssertionError();
        }
        byte[] bArrA2 = com.alipay.sdk.m.g.c.a(str.charAt(0), str.charAt(1));
        if (!z2 && bArrA2.length != 2) {
            throw new AssertionError();
        }
        byte[] bArrA3 = com.alipay.sdk.m.g.c.a(j2);
        if (!z2 && bArrA3.length != 8) {
            throw new AssertionError();
        }
        byte[] bArrB = com.alipay.sdk.m.g.c.b();
        if (!z2 && bArrB.length != 2) {
            throw new AssertionError();
        }
        bVar.a();
        byte[] bArrA4 = com.alipay.sdk.m.g.c.a(bVar.f9278a);
        if (!z2 && bArrA4.length != 1) {
            throw new AssertionError();
        }
        byte[] bArrA5 = com.alipay.sdk.m.g.c.a(bVar.f9279b);
        if (!z2 && bArrA5.length != 1) {
            throw new AssertionError();
        }
        byte[] bArr = (byte[]) bVar.f9280c.clone();
        if (!z2 && bArr.length != (bVar.f9279b & 255)) {
            throw new AssertionError();
        }
        byte[] bArrA6 = com.alipay.sdk.m.g.c.a(s2);
        if (!z2 && bArrA6.length != 2) {
            throw new AssertionError();
        }
        byte[] bArrB2 = com.alipay.sdk.m.g.c.b();
        if (!z2 && bArrB2.length != 2) {
            throw new AssertionError();
        }
        eVar.a();
        byte[] bArrA7 = com.alipay.sdk.m.g.c.a(eVar.f9282a);
        if (!z2 && bArrA7.length != 1) {
            throw new AssertionError();
        }
        byte[] bArr2 = (byte[]) eVar.f9283b.clone();
        if (!z2 && bArr2.length != (eVar.f9282a & 255)) {
            throw new AssertionError();
        }
        byte[] bArrC = com.alipay.sdk.m.g.c.c();
        if (z2 || bArrC.length == 4) {
            return new a(com.alipay.sdk.m.g.c.a(bArrA, bArrA2, bArrA3, bArrB, bArrA4, bArrA5, bArr, bArrA6, bArrB2, bArrA7, bArr2, bArrC));
        }
        throw new AssertionError();
    }

    public static a c() {
        try {
            return a(com.alipay.sdk.m.g.a.f9264c, 0L, new c(""), (short) 0, new f());
        } catch (Exception unused) {
            return null;
        }
    }
}
