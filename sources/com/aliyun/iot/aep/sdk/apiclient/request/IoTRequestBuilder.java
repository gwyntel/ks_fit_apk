package com.aliyun.iot.aep.sdk.apiclient.request;

import android.text.TextUtils;
import com.aliyun.iot.aep.sdk.apiclient.emuns.Method;
import com.aliyun.iot.aep.sdk.apiclient.emuns.Scheme;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/* loaded from: classes3.dex */
public class IoTRequestBuilder {
    public static Scheme defaultScheme = Scheme.HTTPS;

    /* renamed from: a, reason: collision with root package name */
    public Scheme f11644a;

    /* renamed from: c, reason: collision with root package name */
    public String f11646c;

    /* renamed from: d, reason: collision with root package name */
    public String f11647d;

    /* renamed from: e, reason: collision with root package name */
    public String f11648e;

    /* renamed from: f, reason: collision with root package name */
    public String f11649f;

    /* renamed from: g, reason: collision with root package name */
    public String f11650g;

    /* renamed from: b, reason: collision with root package name */
    public Method f11645b = Method.POST;

    /* renamed from: h, reason: collision with root package name */
    public Map<String, Object> f11651h = new HashMap();

    public static class b implements IoTRequest {

        /* renamed from: a, reason: collision with root package name */
        public Scheme f11652a;

        /* renamed from: b, reason: collision with root package name */
        public Method f11653b;

        /* renamed from: c, reason: collision with root package name */
        public String f11654c;

        /* renamed from: d, reason: collision with root package name */
        public String f11655d;

        /* renamed from: e, reason: collision with root package name */
        public String f11656e;

        /* renamed from: f, reason: collision with root package name */
        public String f11657f;

        /* renamed from: g, reason: collision with root package name */
        public String f11658g;

        /* renamed from: h, reason: collision with root package name */
        public String f11659h;

        /* renamed from: i, reason: collision with root package name */
        public Map<String, Object> f11660i;

        public b() {
        }

        @Override // com.aliyun.iot.aep.sdk.apiclient.request.IoTRequest
        public String getAPIVersion() {
            return this.f11656e;
        }

        @Override // com.aliyun.iot.aep.sdk.apiclient.request.IoTRequest
        public String getAuthType() {
            return this.f11657f;
        }

        @Override // com.aliyun.iot.aep.sdk.apiclient.request.IoTRequest
        public String getHost() {
            return this.f11654c;
        }

        @Override // com.aliyun.iot.aep.sdk.apiclient.request.IoTRequest
        public String getId() {
            return this.f11659h;
        }

        @Override // com.aliyun.iot.aep.sdk.apiclient.request.IoTRequest
        public Method getMethod() {
            return this.f11653b;
        }

        @Override // com.aliyun.iot.aep.sdk.apiclient.request.IoTRequest
        public String getMockType() {
            return this.f11658g;
        }

        @Override // com.aliyun.iot.aep.sdk.apiclient.request.IoTRequest
        public Map<String, Object> getParams() {
            return this.f11660i;
        }

        @Override // com.aliyun.iot.aep.sdk.apiclient.request.IoTRequest
        public String getPath() {
            return this.f11655d;
        }

        @Override // com.aliyun.iot.aep.sdk.apiclient.request.IoTRequest
        public Scheme getScheme() {
            return this.f11652a;
        }
    }

    public IoTRequestBuilder() {
        this.f11644a = Scheme.HTTPS;
        this.f11644a = defaultScheme;
    }

    public static boolean a(Map<String, Object> map) {
        Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            if (!a(it.next().getValue())) {
                return false;
            }
        }
        return true;
    }

    @Deprecated
    public IoTRequestBuilder addParam(String str, Object obj) {
        this.f11651h.put(str, obj);
        return this;
    }

    public IoTRequest build() {
        b bVar = new b();
        bVar.f11652a = this.f11644a;
        bVar.f11653b = this.f11645b;
        bVar.f11654c = this.f11646c;
        if (TextUtils.isEmpty(this.f11647d)) {
            throw new IllegalArgumentException("path can not be empty");
        }
        if (!this.f11647d.startsWith("/")) {
            this.f11647d = "/" + this.f11647d;
        }
        bVar.f11655d = this.f11647d;
        if (TextUtils.isEmpty(this.f11648e)) {
            throw new IllegalArgumentException("apiVersion can not be empty");
        }
        bVar.f11656e = this.f11648e;
        bVar.f11659h = UUID.randomUUID().toString();
        bVar.f11657f = this.f11649f;
        bVar.f11658g = this.f11650g;
        if (this.f11651h == null) {
            this.f11651h = new HashMap();
        }
        bVar.f11660i = this.f11651h;
        if (a(this.f11651h)) {
            return bVar;
        }
        throw new IllegalArgumentException("params contains illegal value");
    }

    public IoTRequestBuilder setApiVersion(String str) {
        this.f11648e = str;
        return this;
    }

    public IoTRequestBuilder setAuthType(String str) {
        this.f11649f = str;
        return this;
    }

    public IoTRequestBuilder setHost(String str) {
        this.f11646c = str;
        return this;
    }

    public IoTRequestBuilder setMockType(String str) {
        this.f11650g = str;
        return this;
    }

    public IoTRequestBuilder setParams(Map<String, Object> map) {
        this.f11651h = map;
        return this;
    }

    public IoTRequestBuilder setPath(String str) {
        this.f11647d = str;
        return this;
    }

    public IoTRequestBuilder setScheme(Scheme scheme) {
        this.f11644a = scheme;
        return this;
    }

    public IoTRequestBuilder addParam(String str, String str2) {
        this.f11651h.put(str, str2);
        return this;
    }

    public static boolean a(List list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            if (!a(it.next())) {
                return false;
            }
        }
        return true;
    }

    public IoTRequestBuilder addParam(String str, int i2) {
        this.f11651h.put(str, Integer.valueOf(i2));
        return this;
    }

    public IoTRequestBuilder addParam(String str, long j2) {
        this.f11651h.put(str, Long.valueOf(j2));
        return this;
    }

    public static boolean a(Object obj) {
        if (obj == null || (obj instanceof Boolean) || (obj instanceof String) || (obj instanceof Integer) || (obj instanceof Long) || (obj instanceof Float) || (obj instanceof Double) || (obj instanceof BigDecimal)) {
            return true;
        }
        if (obj instanceof List) {
            return a((List) obj);
        }
        if (obj instanceof Map) {
            return a((Map<String, Object>) obj);
        }
        return false;
    }

    public IoTRequestBuilder addParam(String str, float f2) {
        this.f11651h.put(str, Float.valueOf(f2));
        return this;
    }

    public IoTRequestBuilder addParam(String str, double d2) {
        this.f11651h.put(str, Double.valueOf(d2));
        return this;
    }

    public IoTRequestBuilder addParam(String str, List list) {
        this.f11651h.put(str, list);
        return this;
    }

    public IoTRequestBuilder addParam(String str, Map map) {
        this.f11651h.put(str, map);
        return this;
    }
}
