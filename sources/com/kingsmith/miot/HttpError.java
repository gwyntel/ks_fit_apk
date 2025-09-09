package com.kingsmith.miot;

/* loaded from: classes4.dex */
public class HttpError extends Exception {
    private int code;

    public HttpError(int i2, String str) {
        super(str);
        this.code = i2;
    }

    public int getCode() {
        return this.code;
    }
}
