package com.aliyun.alink.linksdk.alcs.api.utils;

/* loaded from: classes2.dex */
public class ErrorInfo {
    protected int mErrorCode;
    protected String mErrorMsg;

    public ErrorInfo(int i2, String str) {
        this.mErrorCode = i2;
        this.mErrorMsg = str;
    }

    public int getErrorCode() {
        return this.mErrorCode;
    }

    public String getErrorMsg() {
        return this.mErrorMsg;
    }

    public void setErrorCode(int i2) {
        this.mErrorCode = i2;
    }

    public void setErrorMsg(String str) {
        this.mErrorMsg = str;
    }
}
