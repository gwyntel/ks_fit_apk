package com.aliyun.alink.linksdk.tmp.connect.entity.cmp;

import com.aliyun.alink.linksdk.cmp.core.base.AResponse;

/* loaded from: classes2.dex */
public class i extends com.aliyun.alink.linksdk.tmp.connect.e<AResponse> {

    /* renamed from: b, reason: collision with root package name */
    protected String f11169b;

    /* renamed from: c, reason: collision with root package name */
    protected int f11170c;

    /* renamed from: d, reason: collision with root package name */
    protected String f11171d;

    public i(AResponse aResponse) {
        super(aResponse);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.aliyun.alink.linksdk.tmp.connect.e
    public void a(String str) {
        T t2 = this.f11148a;
        if (t2 == 0) {
            return;
        }
        ((AResponse) t2).data = str;
    }

    @Override // com.aliyun.alink.linksdk.tmp.connect.e
    public boolean b() {
        return true;
    }

    @Override // com.aliyun.alink.linksdk.tmp.connect.e
    public String c() {
        return this.f11169b;
    }

    @Override // com.aliyun.alink.linksdk.tmp.connect.e
    public int d() {
        return this.f11170c;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.aliyun.alink.linksdk.tmp.connect.e
    public String e() {
        T t2 = this.f11148a;
        if (t2 == 0 || ((AResponse) t2).data == null) {
            return null;
        }
        if (!(((AResponse) t2).data instanceof byte[])) {
            return ((AResponse) t2).data.toString();
        }
        try {
            return new String((byte[]) ((AResponse) t2).data, "UTF-8");
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.aliyun.alink.linksdk.tmp.connect.e
    public byte[] f() {
        T t2 = this.f11148a;
        if (t2 == 0 || ((AResponse) t2).data == null || !(((AResponse) t2).data instanceof byte[])) {
            return null;
        }
        return (byte[]) ((AResponse) t2).data;
    }

    @Override // com.aliyun.alink.linksdk.tmp.connect.e
    public String g() {
        return this.f11171d;
    }

    @Override // com.aliyun.alink.linksdk.tmp.connect.e
    public void b(String str) {
        this.f11171d = str;
    }

    public void c(String str) {
        this.f11169b = str;
    }

    public void a(int i2) {
        this.f11170c = i2;
    }
}
