package com.alibaba.ailabs.iot.bluetoothlesdk.interfaces;

/* loaded from: classes2.dex */
public interface ICommandHandler {
    boolean canHandle(int i2, int[] iArr);

    void handleData(int i2, int[] iArr);
}
