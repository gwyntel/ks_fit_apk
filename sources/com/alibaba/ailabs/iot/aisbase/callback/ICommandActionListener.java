package com.alibaba.ailabs.iot.aisbase.callback;

/* loaded from: classes2.dex */
public interface ICommandActionListener {
    void onError();

    void onNotify(byte[] bArr);

    void onResponse(byte[] bArr);
}
