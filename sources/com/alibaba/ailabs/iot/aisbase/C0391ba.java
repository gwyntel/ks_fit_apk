package com.alibaba.ailabs.iot.aisbase;

import aisble.callback.DataSentCallback;
import aisble.data.Data;
import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;
import com.alibaba.ailabs.iot.aisbase.plugin.ota.OTAPluginProxy;
import com.alibaba.ailabs.iot.aisbase.spec.AISCommand;
import com.alibaba.ailabs.tg.utils.LogUtils;

/* renamed from: com.alibaba.ailabs.iot.aisbase.ba, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class C0391ba implements DataSentCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ OTAPluginProxy f8358a;

    public C0391ba(OTAPluginProxy oTAPluginProxy) {
        this.f8358a = oTAPluginProxy;
    }

    @Override // aisble.callback.DataSentCallback
    public void onDataSent(@NonNull BluetoothDevice bluetoothDevice, @NonNull Data data) {
        LogUtils.v(this.f8358a.f8485a, "Send OTA PDU success, PDU index: " + this.f8358a.f8499o);
        int length = data.getValue() == null ? 0 : data.getValue().length - 4;
        this.f8358a.f8500p += length;
        this.f8358a.f8499o += this.f8358a.D;
        if (!this.f8358a.E) {
            this.f8358a.F = false;
            return;
        }
        if (this.f8358a.f8499o >= this.f8358a.f8489e.size()) {
            this.f8358a.F = false;
        } else if (((AISCommand) this.f8358a.f8489e.get(this.f8358a.f8499o)).getHeader().getFrameSeq() != 0) {
            this.f8358a.f();
        } else {
            LogUtils.d(this.f8358a.f8485a, "next package sequence is 0");
            this.f8358a.F = false;
        }
    }
}
