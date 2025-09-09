package com.alibaba.ailabs.iot.gattlibrary.plugin;

import android.bluetooth.BluetoothGatt;
import androidx.annotation.NonNull;
import com.alibaba.ailabs.iot.aisbase.plugin.IPlugin;

/* loaded from: classes2.dex */
public interface BluetoothGattPlugin extends IPlugin {
    boolean isCommandSupported(byte b2);

    boolean isRequiredServiceSupported(@NonNull BluetoothGatt bluetoothGatt);
}
