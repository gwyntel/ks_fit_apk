package com.tencent.bugly.proguard;

import java.io.UnsupportedEncodingException;

/* loaded from: classes4.dex */
public final class bn extends m implements Cloneable {

    /* renamed from: d, reason: collision with root package name */
    static byte[] f20972d;

    /* renamed from: a, reason: collision with root package name */
    public byte f20973a;

    /* renamed from: b, reason: collision with root package name */
    public String f20974b;

    /* renamed from: c, reason: collision with root package name */
    public byte[] f20975c;

    public bn() {
        this.f20973a = (byte) 0;
        this.f20974b = "";
        this.f20975c = null;
    }

    @Override // com.tencent.bugly.proguard.m
    public final void a(StringBuilder sb, int i2) {
    }

    @Override // com.tencent.bugly.proguard.m
    public final void a(l lVar) throws UnsupportedEncodingException {
        lVar.a(this.f20973a, 0);
        lVar.a(this.f20974b, 1);
        byte[] bArr = this.f20975c;
        if (bArr != null) {
            lVar.a(bArr, 2);
        }
    }

    public bn(byte b2, String str, byte[] bArr) {
        this.f20973a = b2;
        this.f20974b = str;
        this.f20975c = bArr;
    }

    @Override // com.tencent.bugly.proguard.m
    public final void a(k kVar) {
        this.f20973a = kVar.a(this.f20973a, 0, true);
        this.f20974b = kVar.b(1, true);
        if (f20972d == null) {
            f20972d = new byte[]{0};
        }
        this.f20975c = kVar.c(2, false);
    }
}
