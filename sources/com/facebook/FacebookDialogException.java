package com.facebook;

import com.alipay.sdk.m.u.i;

/* loaded from: classes3.dex */
public class FacebookDialogException extends FacebookException {
    static final long serialVersionUID = 1;
    private int errorCode;
    private String failingUrl;

    public FacebookDialogException(String str, int i2, String str2) {
        super(str);
        this.errorCode = i2;
        this.failingUrl = str2;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public String getFailingUrl() {
        return this.failingUrl;
    }

    @Override // com.facebook.FacebookException, java.lang.Throwable
    public final String toString() {
        return "{FacebookDialogException: errorCode: " + getErrorCode() + ", message: " + getMessage() + ", url: " + getFailingUrl() + i.f9804d;
    }
}
