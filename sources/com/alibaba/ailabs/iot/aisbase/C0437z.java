package com.alibaba.ailabs.iot.aisbase;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.alibaba.ailabs.iot.aisbase.channel.TransmissionLayerManagerBase;
import com.alibaba.ailabs.tg.utils.LogUtils;

/* renamed from: com.alibaba.ailabs.iot.aisbase.z, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class C0437z extends BroadcastReceiver {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ TransmissionLayerManagerBase f8595a;

    public C0437z(TransmissionLayerManagerBase transmissionLayerManagerBase) {
        this.f8595a = transmissionLayerManagerBase;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (!"android.bluetooth.a2dp.profile.action.CONNECTION_STATE_CHANGED".equals(action)) {
            if ("android.bluetooth.device.action.BOND_STATE_CHANGED".equals(action)) {
                BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                LogUtils.d(TransmissionLayerManagerBase.f8375a, "Receive bind state changed: " + bluetoothDevice.getBondState());
                if (bluetoothDevice.getAddress().equals(this.f8595a.f8379e.getAddress())) {
                    this.f8595a.b(bluetoothDevice.getBondState());
                    return;
                }
                return;
            }
            return;
        }
        BluetoothDevice bluetoothDevice2 = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
        int intExtra = intent.getIntExtra("android.bluetooth.profile.extra.STATE", -1);
        if (bluetoothDevice2 == null) {
            LogUtils.e(TransmissionLayerManagerBase.f8375a, "device: a2dp state changed,but device is null! state value: " + intExtra);
            return;
        }
        if (!bluetoothDevice2.getAddress().equals(this.f8595a.f8379e.getAddress())) {
            LogUtils.w(TransmissionLayerManagerBase.f8375a, String.format("device(%s) A2DP state changed, but not current device(%s)", bluetoothDevice2.getAddress(), this.f8595a.f8379e.getAddress()));
            return;
        }
        LogUtils.d(TransmissionLayerManagerBase.f8375a, "Receive A2DP connection state changed: " + intExtra);
        this.f8595a.a(intExtra);
    }
}
