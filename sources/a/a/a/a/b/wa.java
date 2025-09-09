package a.a.a.a.b;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import com.alibaba.ailabs.iot.mesh.TgScanManager;
import com.alibaba.ailabs.iot.mesh.utils.Utils;

/* loaded from: classes.dex */
public class wa extends BroadcastReceiver {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ TgScanManager f1646a;

    public wa(TgScanManager tgScanManager) {
        this.f1646a = tgScanManager;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) throws Settings.SettingNotFoundException {
        boolean zIsLocationEnabled = Utils.isLocationEnabled(context);
        this.f1646a.mScanner.setLocationEnabled(zIsLocationEnabled);
        if (zIsLocationEnabled) {
            return;
        }
        if (this.f1646a.mScanHandler != null) {
            this.f1646a.mScanHandler.onScanFailed(-6, "location_not_enabled");
        }
        this.f1646a.logw("location is not enabled");
    }
}
