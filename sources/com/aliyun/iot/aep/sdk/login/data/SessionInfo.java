package com.aliyun.iot.aep.sdk.login.data;

/* loaded from: classes3.dex */
public class SessionInfo {
    public String sessionId = "";
    public int sessionExpire = 0;
    public long sessionCreateTime = 0;

    public String toString() {
        return "SessionInfo{sessionId='" + this.sessionId + "', sessionExpire=" + this.sessionExpire + ", sessionCreateTime=" + this.sessionCreateTime + '}';
    }
}
