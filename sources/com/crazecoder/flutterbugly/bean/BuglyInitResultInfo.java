package com.crazecoder.flutterbugly.bean;

/* loaded from: classes3.dex */
public class BuglyInitResultInfo {
    private String appId;
    private boolean isSuccess;
    private String message;

    public String getAppId() {
        return this.appId;
    }

    public String getMessage() {
        return this.message;
    }

    public boolean isSuccess() {
        return this.isSuccess;
    }

    public void setAppId(String str) {
        this.appId = str;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public void setSuccess(boolean z2) {
        this.isSuccess = z2;
    }
}
