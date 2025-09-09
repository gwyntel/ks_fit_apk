package com.kingsmith.aliiot;

/* loaded from: classes4.dex */
public class AliResponse<T> {
    private int code;
    private T data;
    private String message;

    public int getCode() {
        return this.code;
    }

    public T getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setCode(int i2) {
        this.code = i2;
    }

    public void setData(T t2) {
        this.data = t2;
    }

    public void setMessage(String str) {
        this.message = str;
    }
}
