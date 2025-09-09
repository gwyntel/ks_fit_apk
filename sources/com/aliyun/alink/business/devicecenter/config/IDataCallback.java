package com.aliyun.alink.business.devicecenter.config;

/* loaded from: classes2.dex */
public interface IDataCallback<T> {
    void onResult(boolean z2, T t2);

    void onState(String str, String str2);
}
