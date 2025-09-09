package com.aliyun.alink.linksdk.tmp.timing;

/* loaded from: classes2.dex */
public interface ITimerActionCallback<T> {
    void onFailure(int i2, String str);

    void onSuccess(T t2);
}
