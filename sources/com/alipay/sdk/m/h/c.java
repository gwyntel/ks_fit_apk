package com.alipay.sdk.m.h;

/* loaded from: classes2.dex */
public final class c extends b {

    /* renamed from: f, reason: collision with root package name */
    public final String f9281f;

    public c(String str) {
        this.f9281f = str;
    }

    @Override // com.alipay.sdk.m.h.b
    public void a() throws Exception {
        this.f9278a = (byte) 1;
        byte[] bytes = this.f9281f.getBytes("UTF-8");
        this.f9280c = bytes;
        this.f9279b = (byte) bytes.length;
    }
}
