package com.aliyun.iot.aep.sdk.apiclient.callback;

/* loaded from: classes3.dex */
public class IoTResponseImpl implements IoTResponse {

    /* renamed from: a, reason: collision with root package name */
    public String f11622a;

    /* renamed from: b, reason: collision with root package name */
    public int f11623b;

    /* renamed from: c, reason: collision with root package name */
    public String f11624c;

    /* renamed from: d, reason: collision with root package name */
    public String f11625d;

    /* renamed from: e, reason: collision with root package name */
    public Object f11626e;

    /* renamed from: f, reason: collision with root package name */
    public byte[] f11627f;

    /* renamed from: g, reason: collision with root package name */
    public Object f11628g = null;

    @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTResponse
    public int getCode() {
        return this.f11623b;
    }

    @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTResponse
    public Object getData() {
        return this.f11626e;
    }

    public Object getExtraResponseData() {
        return this.f11628g;
    }

    @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTResponse
    public String getId() {
        return this.f11622a;
    }

    @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTResponse
    public String getLocalizedMsg() {
        return this.f11625d;
    }

    @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTResponse
    public String getMessage() {
        return this.f11624c;
    }

    @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTResponse
    public byte[] getRawData() {
        return this.f11627f;
    }

    public void setCode(int i2) {
        this.f11623b = i2;
    }

    public void setData(Object obj) {
        this.f11626e = obj;
    }

    public void setExtraResponseData(Object obj) {
        this.f11628g = obj;
    }

    public void setId(String str) {
        this.f11622a = str;
    }

    public void setLocalizedMsg(String str) {
        this.f11625d = str;
    }

    public void setMessage(String str) {
        this.f11624c = str;
    }

    public void setRawData(byte[] bArr) {
        this.f11627f = bArr;
    }
}
