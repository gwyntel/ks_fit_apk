package com.aliyun.alink.linksdk.connectsdk;

/* loaded from: classes2.dex */
public interface BaseCallBack<T> {
    void onFail(int i2, String str);

    void onSuccess(T t2);
}
