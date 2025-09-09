package com.aliyun.common.network;

import com.aliyun.aio.keep.CalledByNative;
import java.util.HashMap;
import java.util.Map;

@CalledByNative
/* loaded from: classes2.dex */
public class AlivcHttpRequest {
    private byte[] body;
    private String url;
    private String method = "GET";
    private Map<String, String> headers = new HashMap();
    private Map<String, String> urlParam = new HashMap();
    private int readTimeout = 10000;
    private int connectTimeout = 10000;
    private boolean trustAllSSLCert = false;
    private boolean followRedirects = true;

    public void addHeader(String str, String str2) {
        this.headers.put(str, str2);
    }

    public void addUrlParam(String str, String str2) {
        this.urlParam.put(str, str2);
    }

    public byte[] getBody() {
        return this.body;
    }

    public int getConnectTimeout() {
        return this.connectTimeout;
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }

    public String getMethod() {
        return this.method;
    }

    public int getReadTimeout() {
        return this.readTimeout;
    }

    public String getUrl() {
        return this.url;
    }

    public Map<String, String> getUrlParam() {
        return this.urlParam;
    }

    public boolean isFollowRedirects() {
        return this.followRedirects;
    }

    public boolean isTrustAllSSLCert() {
        return this.trustAllSSLCert;
    }

    public void setBody(String str) {
        this.body = str.getBytes();
    }

    public void setConnectTimeout(int i2) {
        this.connectTimeout = i2;
    }

    public void setFollowRedirects(boolean z2) {
        this.followRedirects = z2;
    }

    public void setMethod(String str) {
        this.method = str;
    }

    public void setReadTimeout(int i2) {
        this.readTimeout = i2;
    }

    public void setTrustAllSSLCert(boolean z2) {
        this.trustAllSSLCert = z2;
    }

    public void setUrl(String str) {
        this.url = str;
    }

    public void setBody(byte[] bArr) {
        this.body = bArr;
    }
}
