package com.aliyun.alink.linksdk.alcs.lpbs.a.e;

import com.aliyun.alink.linksdk.alcs.lpbs.api.PluginMgr;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalDeviceInfo;
import com.aliyun.alink.linksdk.alcs.lpbs.listener.PalDeviceStateListener;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class g implements PalDeviceStateListener {

    /* renamed from: a, reason: collision with root package name */
    private static final String f10744a = "[AlcsLPBS]PalDevStateListenerProxy";

    /* renamed from: b, reason: collision with root package name */
    private PalDeviceStateListener f10745b;

    /* renamed from: c, reason: collision with root package name */
    private PalDeviceInfo f10746c;

    /* renamed from: d, reason: collision with root package name */
    private com.aliyun.alink.linksdk.alcs.lpbs.a.a.a f10747d;

    public g(PalDeviceInfo palDeviceInfo, com.aliyun.alink.linksdk.alcs.lpbs.a.a.a aVar, PalDeviceStateListener palDeviceStateListener) {
        this.f10745b = palDeviceStateListener;
        this.f10746c = palDeviceInfo;
        this.f10747d = aVar;
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.listener.PalDeviceStateListener
    public void onDeviceStateChange(PalDeviceInfo palDeviceInfo, int i2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (palDeviceInfo == null) {
            ALog.e(f10744a, "deviceInfo null state:" + i2);
            return;
        }
        ALog.d(f10744a, "onDeviceStateChange deviceInfo:" + palDeviceInfo.toString() + " state:" + i2 + " mDeviceInfo:" + this.f10746c.toString());
        PalDeviceStateListener palDeviceStateListener = this.f10745b;
        if (palDeviceStateListener != null) {
            palDeviceStateListener.onDeviceStateChange(this.f10746c, i2);
        }
        if (i2 == 0 && PluginMgr.getInstance().isDataToCloud(this.f10746c)) {
            this.f10747d.a(this.f10746c);
        }
    }
}
