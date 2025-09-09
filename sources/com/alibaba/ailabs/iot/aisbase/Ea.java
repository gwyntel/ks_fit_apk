package com.alibaba.ailabs.iot.aisbase;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.alibaba.ailabs.iot.aisbase.scanner.BLEScannerProxy;
import com.alibaba.ailabs.tg.utils.LogUtils;

/* loaded from: classes2.dex */
public class Ea extends BroadcastReceiver {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ BLEScannerProxy f8277a;

    public Ea(BLEScannerProxy bLEScannerProxy) {
        this.f8277a = bLEScannerProxy;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        int intExtra = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", 10);
        int intExtra2 = intent.getIntExtra("android.bluetooth.adapter.extra.PREVIOUS_STATE", 10);
        if (intExtra != 10) {
            if (intExtra == 12) {
                LogUtils.v(BLEScannerProxy.f8518a, "bluetooth enabled");
                return;
            } else if (intExtra != 13) {
                return;
            }
        }
        if (intExtra2 == 13 || intExtra2 == 10) {
            return;
        }
        LogUtils.v(BLEScannerProxy.f8518a, "bluetooth disabled");
        if (this.f8277a.f8525h != null) {
            this.f8277a.stopDirectionalScan();
        }
        if (this.f8277a.f8524g != null) {
            this.f8277a.stopScan();
        }
    }
}
