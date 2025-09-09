package com.alibaba.ailabs.iot.aisbase;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.ScanSettings;
import androidx.annotation.NonNull;

@TargetApi(23)
/* renamed from: com.alibaba.ailabs.iot.aisbase.q, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class C0420q extends C0418p {
    @Override // com.alibaba.ailabs.iot.aisbase.C0418p
    @NonNull
    public ScanSettings a(@NonNull BluetoothAdapter bluetoothAdapter, @NonNull aisscanner.ScanSettings scanSettings) {
        ScanSettings.Builder builder = new ScanSettings.Builder();
        if (bluetoothAdapter.isOffloadedScanBatchingSupported() && scanSettings.getUseHardwareBatchingIfSupported()) {
            builder.setReportDelay(scanSettings.getReportDelayMillis());
        }
        if (scanSettings.getUseHardwareCallbackTypesIfSupported()) {
            builder.setCallbackType(scanSettings.getCallbackType()).setMatchMode(scanSettings.getMatchMode()).setNumOfMatches(scanSettings.getNumOfMatches());
        }
        builder.setScanMode(scanSettings.getScanMode());
        return builder.build();
    }
}
