package com.alibaba.ailabs.iot.aisbase.callback;

/* loaded from: classes2.dex */
public interface IActionListener<T> {
    void onFailure(int i2, String str);

    void onSuccess(T t2);
}
