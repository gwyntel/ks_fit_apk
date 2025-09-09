package com.yc.utesdk.listener;

import com.yc.utesdk.bean.DeviceBt3StateInfo;

/* loaded from: classes4.dex */
public interface DeviceBt3Listener {
    void onDeviceBt3State(boolean z2, DeviceBt3StateInfo deviceBt3StateInfo);

    void onDeviceBt3Switch(boolean z2, int i2);
}
