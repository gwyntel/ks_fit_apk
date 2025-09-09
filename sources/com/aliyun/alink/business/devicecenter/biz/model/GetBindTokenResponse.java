package com.aliyun.alink.business.devicecenter.biz.model;

import java.io.Serializable;

/* loaded from: classes2.dex */
public class GetBindTokenResponse implements Serializable {
    public String expiredTime;
    public String random;
    public String token;

    public String getExpiredTime() {
        return this.expiredTime;
    }

    public String getRandom() {
        return this.random;
    }

    public String getToken() {
        return this.token;
    }

    public void setExpiredTime(String str) {
        this.expiredTime = str;
    }

    public void setRandom(String str) {
        this.random = str;
    }

    public void setToken(String str) {
        this.token = str;
    }
}
