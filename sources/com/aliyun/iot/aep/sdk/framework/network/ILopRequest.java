package com.aliyun.iot.aep.sdk.framework.network;

import com.aliyun.iot.aep.sdk.apiclient.emuns.Scheme;
import com.aliyun.iot.aep.sdk.framework.network.BaseRequest;
import java.io.Serializable;
import java.util.Map;

/* loaded from: classes3.dex */
public class ILopRequest<V extends BaseRequest> implements Serializable {

    /* renamed from: a, reason: collision with root package name */
    V f11796a;

    /* renamed from: b, reason: collision with root package name */
    private Scheme f11797b;

    /* renamed from: c, reason: collision with root package name */
    private String f11798c;

    /* renamed from: d, reason: collision with root package name */
    private String f11799d;

    /* renamed from: e, reason: collision with root package name */
    private String f11800e;

    /* renamed from: f, reason: collision with root package name */
    private String f11801f;

    ILopRequest(V v2, String str, String str2) {
        this.f11797b = Scheme.HTTPS;
        this.f11798c = "";
        this.f11801f = "";
        this.f11796a = v2;
        this.f11799d = str;
        this.f11800e = str2;
    }

    public String getApiVersion() {
        return this.f11800e;
    }

    public String getAuthType() {
        return this.f11801f;
    }

    public V getData() {
        return this.f11796a;
    }

    public String getHost() {
        return this.f11798c;
    }

    public Map<String, Object> getParams() {
        if (getData() == null) {
            return null;
        }
        return getData().getParams();
    }

    public String getPath() {
        return this.f11799d;
    }

    public Scheme getScheme() {
        return this.f11797b;
    }

    public void setApiVersion(String str) {
        this.f11800e = str;
    }

    public void setAuthType(String str) {
        this.f11801f = str;
    }

    public void setData(V v2) {
        this.f11796a = v2;
    }

    public void setHost(String str) {
        this.f11798c = str;
    }

    public void setPath(String str) {
        this.f11799d = str;
    }

    public void setScheme(Scheme scheme) {
        this.f11797b = scheme;
    }

    ILopRequest(V v2, String str, String str2, String str3, String str4) {
        this.f11797b = Scheme.HTTPS;
        this.f11796a = v2;
        this.f11799d = str2;
        this.f11800e = str3;
        this.f11798c = str;
        this.f11801f = str4;
    }
}
