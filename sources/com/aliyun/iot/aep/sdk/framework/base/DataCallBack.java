package com.aliyun.iot.aep.sdk.framework.base;

/* loaded from: classes3.dex */
public interface DataCallBack<T> {
    void onFail(int i2, String str);

    void onSuccess(T t2);
}
