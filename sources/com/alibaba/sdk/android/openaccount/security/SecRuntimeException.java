package com.alibaba.sdk.android.openaccount.security;

/* loaded from: classes2.dex */
public class SecRuntimeException extends RuntimeException {
    private static final long serialVersionUID = -2429061914258524220L;
    private int secCode;

    public SecRuntimeException(int i2, Throwable th) {
        super(th);
        this.secCode = i2;
    }

    public int getErrorCode() {
        return this.secCode;
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        return super.getMessage() + " secCode = " + this.secCode;
    }
}
