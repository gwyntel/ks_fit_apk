package com.alibaba.cloudapi.sdk.model;

import com.alibaba.cloudapi.sdk.enums.Scheme;
import com.alibaba.cloudapi.sdk.exception.SdkException;

/* loaded from: classes2.dex */
public class BaseClientInitialParam {
    String appKey;
    String appSecret;
    String host;
    Scheme scheme;
    long connectionTimeout = 10000;
    long readTimeout = 10000;
    long writeTimeout = 10000;

    public void check() {
        if (isEmpty(this.appKey) || isEmpty(this.appKey)) {
            throw new SdkException("app key or app secret must be initialed");
        }
        if (isEmpty(this.host) || this.scheme == null) {
            throw new SdkException("host and scheme must be initialed");
        }
    }

    public String getAppKey() {
        return this.appKey;
    }

    public String getAppSecret() {
        return this.appSecret;
    }

    public long getConnectionTimeout() {
        return this.connectionTimeout;
    }

    public String getHost() {
        return this.host;
    }

    public long getReadTimeout() {
        return this.readTimeout;
    }

    public Scheme getScheme() {
        return this.scheme;
    }

    public long getWriteTimeout() {
        return this.writeTimeout;
    }

    protected boolean isEmpty(String str) {
        return str == null || str.equals("");
    }

    public void setAppKey(String str) {
        this.appKey = str;
    }

    public void setAppSecret(String str) {
        this.appSecret = str;
    }

    public void setConnectionTimeout(long j2) {
        this.connectionTimeout = j2;
    }

    public void setHost(String str) {
        this.host = str;
    }

    public void setReadTimeout(long j2) {
        this.readTimeout = j2;
    }

    public void setScheme(Scheme scheme) {
        this.scheme = scheme;
    }

    public void setWriteTimeout(long j2) {
        this.writeTimeout = j2;
    }
}
