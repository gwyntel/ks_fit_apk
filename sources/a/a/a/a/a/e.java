package a.a.a.a.a;

import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseSettings;
import com.alibaba.ailabs.iot.bleadvertise.callback.BleAdvertiseCallback;

/* loaded from: classes.dex */
public class e extends AdvertiseCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ BleAdvertiseCallback f1163a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ g f1164b;

    public e(g gVar, BleAdvertiseCallback bleAdvertiseCallback) {
        this.f1164b = gVar;
        this.f1163a = bleAdvertiseCallback;
    }

    @Override // android.bluetooth.le.AdvertiseCallback
    public void onStartFailure(int i2) {
        super.onStartFailure(i2);
        a.a.a.a.b.m.a.b("AdvertiseManager", "onStartFailure errorCode" + i2);
        String str = i2 == 1 ? "Failed to start advertising as the advertise data to be broadcasted is larger than 31 bytes." : i2 == 2 ? "Failed to start advertising because no advertising instance is available." : i2 == 3 ? "Failed to start advertising as the advertising is already started" : i2 == 4 ? "Operation failed due to an internal error" : i2 == 5 ? "This feature is not supported on this platform" : "";
        a.a.a.a.b.m.a.b("AdvertiseManager", str);
        this.f1163a.onFailure(i2, str);
    }

    @Override // android.bluetooth.le.AdvertiseCallback
    public void onStartSuccess(AdvertiseSettings advertiseSettings) {
        super.onStartSuccess(advertiseSettings);
        if (advertiseSettings != null) {
            a.a.a.a.b.m.a.a("AdvertiseManager", "onStartSuccess TxPowerLv=" + advertiseSettings.getTxPowerLevel() + " mode=" + advertiseSettings.getMode() + " timeout=" + advertiseSettings.getTimeout());
        } else {
            a.a.a.a.b.m.a.b("AdvertiseManager", "onStartSuccess, settingInEffect is null");
        }
        this.f1163a.onSuccess(Boolean.TRUE);
    }
}
