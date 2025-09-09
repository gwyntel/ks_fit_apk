package com.aliyun.alink.linksdk.tmp.api;

import com.aliyun.alink.linksdk.tmp.utils.GsonUtils;
import com.google.gson.reflect.TypeToken;

/* loaded from: classes2.dex */
public class InputParams<T> {
    protected T mData;

    public InputParams() {
    }

    public void fromJson(String str) {
        this.mData = (T) GsonUtils.fromJson(str, new TypeToken<T>() { // from class: com.aliyun.alink.linksdk.tmp.api.InputParams.1
        }.getType());
    }

    public T getData() {
        return this.mData;
    }

    public InputParams(T t2) {
        this.mData = t2;
    }
}
