package com.aliyun.alink.linksdk.tmp.network;

import android.content.Context;

/* loaded from: classes2.dex */
public abstract class BaseException extends Exception {
    protected int code;
    protected String errorMessage;

    public BaseException() {
    }

    public int getCode() {
        return this.code;
    }

    public String getErrorMessage() {
        String str = this.errorMessage;
        return str != null ? str : super.getMessage();
    }

    public abstract boolean handle(Context context);

    public void setCode(int i2) {
        this.code = i2;
    }

    public void setErrorMessage(String str) {
        this.errorMessage = str;
    }

    public BaseException(String str) {
        super(str);
    }

    public BaseException(Throwable th) {
        super(th);
    }

    public BaseException(int i2, String str) {
        this.code = i2;
        this.errorMessage = str;
    }

    public BaseException(String str, Throwable th) {
        super(th);
        this.errorMessage = str;
    }

    public BaseException(int i2, String str, Throwable th) {
        super(th);
        this.code = i2;
        this.errorMessage = str;
    }
}
