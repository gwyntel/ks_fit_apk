package com.aliyun.alink.linksdk.alcs.lpbs.a.e;

import com.aliyun.alink.linksdk.alcs.lpbs.api.AlcsPalConst;
import com.aliyun.alink.linksdk.alcs.lpbs.api.IAlcsPal;
import com.aliyun.alink.linksdk.alcs.lpbs.component.auth.IAuthProvider;
import com.aliyun.alink.linksdk.alcs.lpbs.component.cloud.ICloudChannelFactory;
import com.aliyun.alink.linksdk.alcs.lpbs.component.cloud.IThingCloudChannel;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalConnectParams;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalDeviceInfo;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalDiscoveryConfig;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalInitData;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalReqMessage;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalSubMessage;
import com.aliyun.alink.linksdk.alcs.lpbs.listener.PalConnectListener;
import com.aliyun.alink.linksdk.alcs.lpbs.listener.PalDeviceStateListener;
import com.aliyun.alink.linksdk.alcs.lpbs.listener.PalDiscoveryListener;
import com.aliyun.alink.linksdk.alcs.lpbs.listener.PalMsgListener;
import com.aliyun.alink.linksdk.alcs.lpbs.listener.PalProbeListener;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public abstract class e extends IAlcsPal {

    /* renamed from: a, reason: collision with root package name */
    private static final String f10728a = "[AlcsLPBS]IAlcsPalLayer";

    /* renamed from: b, reason: collision with root package name */
    private e f10729b;

    public e(e eVar) {
        this.f10729b = eVar;
    }

    public e a() {
        return this.f10729b;
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalConnect
    public boolean asyncSendRequest(PalReqMessage palReqMessage, PalMsgListener palMsgListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (a() != null) {
            return a().asyncSendRequest(palReqMessage, palMsgListener);
        }
        ALog.e(f10728a, "asyncSendRequest on error Layer");
        if (palMsgListener == null) {
            return false;
        }
        palMsgListener.onLoad(null);
        return false;
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.api.IAlcsPal
    public void deInitAlcs() {
        if (a() != null) {
            a().deInitAlcs();
        } else {
            ALog.e(f10728a, "deInitAlcs on error Layer");
        }
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalConnect
    public int getConnectType(PalDeviceInfo palDeviceInfo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.e(f10728a, "getConnectType empty impl");
        return 0;
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalConnect
    public String getPluginId() {
        return AlcsPalConst.DEFAULT_PLUGIN_ID;
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.api.IAlcsPal
    public void initAlcs(PalInitData palInitData) {
        if (a() != null) {
            a().initAlcs(palInitData);
        } else {
            ALog.e(f10728a, "initAlcs on error Layer");
        }
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalConnect
    public boolean isDeviceConnected(PalDeviceInfo palDeviceInfo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (a() != null) {
            return a().isDeviceConnected(palDeviceInfo);
        }
        ALog.e(f10728a, "isDeviceConnected on error Layer");
        return false;
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalConnect
    public void onCloudChannelCreate(IThingCloudChannel iThingCloudChannel) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.e(f10728a, "onCloudChannelCreate empty impl");
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalProbe
    public void probeDevice(PalDeviceInfo palDeviceInfo, PalProbeListener palProbeListener) {
        if (a() == null) {
            ALog.e(f10728a, "probeDevice on error Layer");
        } else {
            a().probeDevice(palDeviceInfo, palProbeListener);
        }
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.api.IAlcsPal
    public boolean regAuthProvider(String str, IAuthProvider iAuthProvider) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (a() != null) {
            return a().regAuthProvider(str, iAuthProvider);
        }
        ALog.e(f10728a, "regAuthProvider on error Layer");
        return false;
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.api.IAlcsPal
    public void regCloudChannelFactory(ICloudChannelFactory iCloudChannelFactory) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (a() != null) {
            a().regCloudChannelFactory(iCloudChannelFactory);
        } else {
            ALog.e(f10728a, "regCloudChannelFactory on error Layer");
        }
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalConnect
    public boolean regDeviceStateListener(PalDeviceInfo palDeviceInfo, PalDeviceStateListener palDeviceStateListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (a() != null) {
            return a().regDeviceStateListener(palDeviceInfo, palDeviceStateListener);
        }
        ALog.e(f10728a, "regDeviceStateListener on error Layer");
        return false;
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalConnect
    public void startConnect(PalConnectParams palConnectParams, PalConnectListener palConnectListener) {
        if (a() != null) {
            a().startConnect(palConnectParams, palConnectListener);
            return;
        }
        ALog.e(f10728a, "startConnect on error Layer");
        if (palConnectListener != null) {
            palConnectListener.onLoad(1, null, palConnectParams.deviceInfo);
        }
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalDiscovery
    public boolean startDiscovery(int i2, PalDiscoveryListener palDiscoveryListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (a() != null) {
            return a().startDiscovery(i2, palDiscoveryListener);
        }
        ALog.e(f10728a, "startDiscovery on error Layer");
        return false;
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalDiscovery
    public boolean startNotifyMonitor(PalDiscoveryListener palDiscoveryListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (a() != null) {
            return a().startNotifyMonitor(palDiscoveryListener);
        }
        ALog.e(f10728a, "startNotifyMonitor on error Layer");
        return false;
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalConnect
    public void stopConnect(PalDeviceInfo palDeviceInfo) {
        if (a() != null) {
            a().stopConnect(palDeviceInfo);
        } else {
            ALog.e(f10728a, "stopConnect on error Layer");
        }
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalDiscovery
    public boolean stopDiscovery() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (a() != null) {
            return a().stopDiscovery();
        }
        ALog.e(f10728a, "stopDiscovery on error Layer");
        return false;
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalDiscovery
    public boolean stopNotifyMonitor() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (a() != null) {
            return a().stopNotifyMonitor();
        }
        ALog.e(f10728a, "stopNotifyMonitor on error Layer");
        return false;
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalConnect
    public boolean subscribe(PalSubMessage palSubMessage, PalMsgListener palMsgListener, PalMsgListener palMsgListener2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (a() != null) {
            return a().subscribe(palSubMessage, palMsgListener, palMsgListener2);
        }
        ALog.e(f10728a, "subscribe on error Layer");
        if (palMsgListener == null) {
            return false;
        }
        palMsgListener.onLoad(null);
        return false;
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalConnect
    public boolean unregDeviceStateListener(PalDeviceInfo palDeviceInfo, PalDeviceStateListener palDeviceStateListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (a() != null) {
            return a().unregDeviceStateListener(palDeviceInfo, palDeviceStateListener);
        }
        ALog.e(f10728a, "unregDeviceStateListener on error Layer");
        return false;
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalConnect
    public boolean unsubscribe(PalSubMessage palSubMessage, PalMsgListener palMsgListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (a() != null) {
            return a().unsubscribe(palSubMessage, palMsgListener);
        }
        ALog.e(f10728a, "unsubscribe on error Layer");
        if (palMsgListener == null) {
            return false;
        }
        palMsgListener.onLoad(null);
        return false;
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalDiscovery
    public boolean startDiscovery(int i2, PalDiscoveryConfig palDiscoveryConfig, PalDiscoveryListener palDiscoveryListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (a() == null) {
            ALog.e(f10728a, "startDiscovery on error Layer");
            return false;
        }
        return a().startDiscovery(i2, palDiscoveryConfig, palDiscoveryListener);
    }
}
