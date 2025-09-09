package com.alibaba.ailabs.iot.aisbase;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.alibaba.ailabs.iot.aisbase.plugin.ota.OTAPluginProxy;
import com.alibaba.ailabs.tg.utils.LogUtils;

/* renamed from: com.alibaba.ailabs.iot.aisbase.ta, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class C0427ta extends BroadcastReceiver {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ OTAPluginProxy f8574a;

    public C0427ta(OTAPluginProxy oTAPluginProxy) {
        this.f8574a = oTAPluginProxy;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        LogUtils.i(this.f8574a.f8485a, "onReceive action: " + intent.getAction());
        if ("android.bluetooth.a2dp.profile.action.CONNECTION_STATE_CHANGED".equals(intent.getAction())) {
            int intExtra = intent.getIntExtra("android.bluetooth.profile.extra.STATE", Integer.MIN_VALUE);
            BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
            if (bluetoothDevice == null || this.f8574a.f8504t == null || this.f8574a.f8506v == null || !bluetoothDevice.getAddress().equals(this.f8574a.f8506v.getAddress()) || intExtra != 2) {
                return;
            }
            if (this.f8574a.P != null) {
                this.f8574a.f8488d.removeCallbacks(this.f8574a.P);
            }
            this.f8574a.f8504t.connectDevice(this.f8574a.f8506v, new C0425sa(this));
        }
    }
}
