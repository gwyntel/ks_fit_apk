package com.aliyun.alink.linksdk.channel.mobile.b;

/* loaded from: classes2.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private String f10999a;

    /* renamed from: b, reason: collision with root package name */
    private byte[] f11000b;

    /* renamed from: c, reason: collision with root package name */
    private String f11001c;

    public a(String str, byte[] bArr) {
        this.f10999a = str;
        this.f11000b = bArr;
        try {
            this.f11001c = new String(bArr, "UTF-8");
        } catch (Exception unused) {
            this.f11001c = null;
        }
    }

    public String a() {
        return this.f11001c;
    }

    public String b() {
        return this.f10999a;
    }
}
