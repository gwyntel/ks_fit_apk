package com.aliyun.alink.linksdk.alcs.lpbs.a.d;

import com.aliyun.alink.linksdk.alcs.lpbs.a.e.g;
import com.aliyun.alink.linksdk.alcs.lpbs.api.PluginMgr;
import com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalConnect;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalDeviceInfo;
import com.aliyun.alink.linksdk.alcs.lpbs.listener.PalDeviceStateListener;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private static final String f10695a = "[AlcsLPBS]DeviceStateListenerMgr";

    /* renamed from: b, reason: collision with root package name */
    private com.aliyun.alink.linksdk.alcs.lpbs.a.b.a f10696b;

    /* renamed from: c, reason: collision with root package name */
    private com.aliyun.alink.linksdk.alcs.lpbs.a.a.a f10697c;

    public a(com.aliyun.alink.linksdk.alcs.lpbs.a.b.a aVar, com.aliyun.alink.linksdk.alcs.lpbs.a.a.a aVar2) {
        this.f10696b = aVar;
        this.f10697c = aVar2;
    }

    public boolean a(PalDeviceInfo palDeviceInfo, PalDeviceStateListener palDeviceStateListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (palDeviceStateListener == null || palDeviceInfo == null) {
            ALog.e(f10695a, "regDeviceStateListener listener deviceInfo null");
            return false;
        }
        g gVar = new g(palDeviceInfo, this.f10697c, palDeviceStateListener);
        ALog.d(f10695a, "regDeviceStateListener " + palDeviceStateListener.hashCode() + " listenerProxy:" + gVar.hashCode());
        IPalConnect iPalConnectB = this.f10696b.b(palDeviceInfo.getDevId());
        if (iPalConnectB == null) {
            ALog.e(f10695a, "regDeviceStateListener connect null");
            return false;
        }
        iPalConnectB.regDeviceStateListener(PluginMgr.getInstance().toPrivatePkDn(palDeviceInfo, iPalConnectB.getPluginId()), gVar);
        return true;
    }

    public boolean b(PalDeviceInfo palDeviceInfo, PalDeviceStateListener palDeviceStateListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (palDeviceStateListener == null || palDeviceInfo == null) {
            ALog.e(f10695a, "unregDeviceStateListener listener deviceInfo null");
            return false;
        }
        ALog.d(f10695a, "unregDeviceStateListener listener:" + palDeviceStateListener.hashCode());
        IPalConnect iPalConnectB = this.f10696b.b(palDeviceInfo.getDevId());
        if (iPalConnectB == null) {
            ALog.e(f10695a, "unregDeviceStateListener connect null");
            return false;
        }
        iPalConnectB.unregDeviceStateListener(PluginMgr.getInstance().toPrivatePkDn(palDeviceInfo, iPalConnectB.getPluginId()), palDeviceStateListener);
        return true;
    }
}
