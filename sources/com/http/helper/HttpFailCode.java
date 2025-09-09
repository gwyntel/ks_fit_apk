package com.http.helper;

/* loaded from: classes3.dex */
public class HttpFailCode extends Throwable {
    int code;

    public HttpFailCode(int i2) {
        this.code = i2;
    }

    public int getCode() {
        return this.code;
    }
}
