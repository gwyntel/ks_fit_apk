package com.aliyun.alink.business.devicecenter.channel.http;

/* loaded from: classes2.dex */
public interface IRequestCallback<T> {
    void onFail(DCError dCError, T t2);

    void onSuccess(T t2);
}
