package com.alibaba.ailabs.iot.aisbase.callback;

/* loaded from: classes2.dex */
public interface ICommandSendListener {
    void onFailure(int i2, String str);

    void onSent();
}
