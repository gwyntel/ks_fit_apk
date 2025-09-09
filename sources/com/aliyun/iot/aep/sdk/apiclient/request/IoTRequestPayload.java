package com.aliyun.iot.aep.sdk.apiclient.request;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public class IoTRequestPayload {

    /* renamed from: a, reason: collision with root package name */
    public String f11661a;

    /* renamed from: b, reason: collision with root package name */
    public String f11662b;

    /* renamed from: c, reason: collision with root package name */
    public Map<String, Object> f11663c = new HashMap();

    /* renamed from: d, reason: collision with root package name */
    public Map<String, Object> f11664d = new HashMap();

    public IoTRequestPayload(String str, String str2) {
        this.f11661a = str;
        this.f11662b = str2;
    }

    public String getId() {
        return this.f11661a;
    }

    public Map<String, Object> getParams() {
        return this.f11664d;
    }

    public Map<String, Object> getRequest() {
        return this.f11663c;
    }

    public String getVersion() {
        return this.f11662b;
    }
}
