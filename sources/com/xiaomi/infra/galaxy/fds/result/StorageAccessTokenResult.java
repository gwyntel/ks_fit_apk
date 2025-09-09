package com.xiaomi.infra.galaxy.fds.result;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
/* loaded from: classes4.dex */
public class StorageAccessTokenResult {
    private long expireTime;
    private String token;

    public StorageAccessTokenResult() {
    }

    public long getExpireTime() {
        return this.expireTime;
    }

    public String getToken() {
        return this.token;
    }

    public void setExpireTime(long j2) {
        this.expireTime = j2;
    }

    public void setToken(String str) {
        this.token = str;
    }

    public StorageAccessTokenResult(String str, long j2) {
        this.token = str;
        this.expireTime = j2;
    }
}
