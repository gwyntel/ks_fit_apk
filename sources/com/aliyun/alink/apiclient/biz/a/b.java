package com.aliyun.alink.apiclient.biz.a;

import com.aliyun.alink.apiclient.biz.IApiClientResponse;

/* loaded from: classes2.dex */
public class b implements IApiClientResponse {

    /* renamed from: a, reason: collision with root package name */
    private String f9972a;

    /* renamed from: b, reason: collision with root package name */
    private int f9973b;

    /* renamed from: c, reason: collision with root package name */
    private String f9974c;

    /* renamed from: d, reason: collision with root package name */
    private String f9975d;

    /* renamed from: e, reason: collision with root package name */
    private Object f9976e;

    /* renamed from: f, reason: collision with root package name */
    private byte[] f9977f;

    public void a(String str) {
        this.f9972a = str;
    }

    public void b(String str) {
        this.f9974c = str;
    }

    public void c(String str) {
        this.f9975d = str;
    }

    @Override // com.aliyun.alink.apiclient.biz.IApiClientResponse
    public int getCode() {
        return this.f9973b;
    }

    @Override // com.aliyun.alink.apiclient.biz.IApiClientResponse
    public Object getData() {
        return this.f9976e;
    }

    @Override // com.aliyun.alink.apiclient.biz.IApiClientResponse
    public String getId() {
        return this.f9972a;
    }

    @Override // com.aliyun.alink.apiclient.biz.IApiClientResponse
    public String getLocalizedMsg() {
        return this.f9975d;
    }

    @Override // com.aliyun.alink.apiclient.biz.IApiClientResponse
    public String getMessage() {
        return this.f9974c;
    }

    @Override // com.aliyun.alink.apiclient.biz.IApiClientResponse
    public byte[] getRawData() {
        return this.f9977f;
    }

    public String toString() {
        return "{\"id\":\"" + this.f9972a + "\",\"code\":\"" + this.f9973b + "\",\"message\":\"" + this.f9974c + "\",\"localizedMsg\":\"" + this.f9975d + "\",\"data\":\"" + this.f9976e + "\",\"rawData\":\"" + this.f9977f + "\"}";
    }

    public void a(int i2) {
        this.f9973b = i2;
    }

    public void a(Object obj) {
        this.f9976e = obj;
    }

    public void a(byte[] bArr) {
        this.f9977f = bArr;
    }
}
