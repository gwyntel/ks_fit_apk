package com.aliyun.alink.linksdk.tmp.device.payload;

/* loaded from: classes2.dex */
public class CommonResponsePayload<T> {
    protected int code;
    protected T data;
    protected String id;
    protected String msg;

    public int getCode() {
        return this.code;
    }

    public T getData() {
        return this.data;
    }

    public String getId() {
        return this.id;
    }

    public String getMsg() {
        return this.msg;
    }

    public boolean payloadSuccess() {
        int i2 = this.code;
        return i2 >= 200 && i2 < 300;
    }

    public void setCode(int i2) {
        this.code = i2;
    }

    public void setData(T t2) {
        this.data = t2;
    }

    public void setId(String str) {
        this.id = str;
    }

    public void setMsg(String str) {
        this.msg = str;
    }
}
