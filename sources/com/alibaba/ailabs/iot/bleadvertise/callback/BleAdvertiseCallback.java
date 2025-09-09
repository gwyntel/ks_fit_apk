package com.alibaba.ailabs.iot.bleadvertise.callback;

/* loaded from: classes2.dex */
public interface BleAdvertiseCallback<T> {
    void onFailure(int i2, String str);

    void onSuccess(T t2);
}
