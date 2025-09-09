package com.alibaba.ailabs.iot.mesh.callback;

/* loaded from: classes2.dex */
public interface ConfigActionListener<T> {
    void onFailure(String str, int i2, String str2);

    void onSuccess(String str, T t2);
}
