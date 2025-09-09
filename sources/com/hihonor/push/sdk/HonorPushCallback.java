package com.hihonor.push.sdk;

/* loaded from: classes3.dex */
public interface HonorPushCallback<T> {
    void onFailure(int i2, String str);

    void onSuccess(T t2);
}
