package com.aliyun.alink.apiclient.model;

import java.io.Serializable;

/* loaded from: classes2.dex */
public class DeviceResponse<T> implements Serializable {
    private String code = null;
    private String message = null;

    /* renamed from: info, reason: collision with root package name */
    private T f9979info = null;

    public String getCode() {
        return this.code;
    }

    public T getInfo() {
        return this.f9979info;
    }

    public String getMessage() {
        return this.message;
    }

    public void setCode(String str) {
        this.code = str;
    }

    public void setInfo(T t2) {
        this.f9979info = t2;
    }

    public void setMessage(String str) {
        this.message = str;
    }
}
