package com.aliyun.alink.linksdk.alcs.lpbs.a.e;

import android.text.TextUtils;
import com.aliyun.alink.linksdk.alcs.lpbs.api.PluginMgr;
import com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalConnect;
import com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalProbe;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalConnectParams;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalDeviceInfo;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalProbeResult;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalReqMessage;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalRspMessage;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalSubMessage;
import com.aliyun.alink.linksdk.alcs.lpbs.listener.PalConnectListener;
import com.aliyun.alink.linksdk.alcs.lpbs.listener.PalDiscoveryListener;
import com.aliyun.alink.linksdk.alcs.lpbs.listener.PalMsgListener;
import com.aliyun.alink.linksdk.alcs.lpbs.listener.PalProbeListener;
import com.aliyun.alink.linksdk.alcs.lpbs.plugin.IPlugin;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/* loaded from: classes2.dex */
public class k extends e {

    /* renamed from: a, reason: collision with root package name */
    private static final String f10755a = "[AlcsLPBS]PkDnConvertLayer";

    /* renamed from: b, reason: collision with root package name */
    private com.aliyun.alink.linksdk.alcs.lpbs.a.b.a f10756b;

    /* renamed from: c, reason: collision with root package name */
    private com.aliyun.alink.linksdk.alcs.lpbs.a.a.a f10757c;

    public k(com.aliyun.alink.linksdk.alcs.lpbs.a.b.a aVar, com.aliyun.alink.linksdk.alcs.lpbs.a.a.a aVar2, e eVar) {
        super(eVar);
        this.f10756b = aVar;
        this.f10757c = aVar2;
    }

    public String a(String str, PalDeviceInfo palDeviceInfo, PalDeviceInfo palDeviceInfo2) {
        return (TextUtils.isEmpty(str) || palDeviceInfo == null || palDeviceInfo2 == null) ? str : (palDeviceInfo.productModel.equalsIgnoreCase(palDeviceInfo2.productModel) && palDeviceInfo.deviceId.equalsIgnoreCase(palDeviceInfo2.deviceId)) ? str : str.replace(palDeviceInfo.productModel, palDeviceInfo2.productModel).replace(palDeviceInfo.deviceId, palDeviceInfo2.deviceId);
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.a.e.e, com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalConnect
    public boolean asyncSendRequest(PalReqMessage palReqMessage, PalMsgListener palMsgListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        IPalConnect iPalConnectB = this.f10756b.b(palReqMessage.deviceInfo.getDevId());
        PalDeviceInfo palDeviceInfo = palReqMessage.deviceInfo;
        ALog.d(f10755a, "asyncSendRequest connect:" + iPalConnectB + " callback:" + palMsgListener);
        if (iPalConnectB != null) {
            PalDeviceInfo privatePkDn = PluginMgr.getInstance().toPrivatePkDn(palReqMessage.deviceInfo, iPalConnectB.getPluginId());
            palReqMessage.deviceInfo = privatePkDn;
            palReqMessage.topic = a(palReqMessage.topic, palDeviceInfo, privatePkDn);
            return iPalConnectB.asyncSendRequest(palReqMessage, new h(palDeviceInfo, palMsgListener));
        }
        if (palMsgListener == null) {
            return false;
        }
        PalRspMessage palRspMessage = new PalRspMessage();
        palRspMessage.code = 1;
        palMsgListener.onLoad(palRspMessage);
        return false;
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.a.e.e, com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalConnect
    public boolean isDeviceConnected(PalDeviceInfo palDeviceInfo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        IPalConnect iPalConnectB = this.f10756b.b(palDeviceInfo.getDevId());
        if (iPalConnectB != null) {
            return iPalConnectB.isDeviceConnected(PluginMgr.getInstance().toPrivatePkDn(palDeviceInfo, iPalConnectB.getPluginId()));
        }
        return false;
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.a.e.e, com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalProbe
    public void probeDevice(PalDeviceInfo palDeviceInfo, PalProbeListener palProbeListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        IPlugin pluginByDevId = PluginMgr.getInstance().getPluginByDevId(palDeviceInfo.getDevId());
        if (pluginByDevId == null) {
            palProbeListener.onComplete(palDeviceInfo, new PalProbeResult(2));
            ALog.e(f10755a, "startConnect error plugin not found");
            return;
        }
        PalDeviceInfo privatePkDn = PluginMgr.getInstance().toPrivatePkDn(palDeviceInfo, pluginByDevId.getPluginId());
        try {
            IPalProbe palProbe = pluginByDevId.getPalBridge().getPalProbe();
            if (palProbe != null) {
                palProbe.probeDevice(privatePkDn, new j(palProbeListener, pluginByDevId.getPluginId()));
            }
        } catch (AbstractMethodError e2) {
            ALog.w(f10755a, e2.toString());
        } catch (Exception e3) {
            ALog.w(f10755a, e3.toString());
        }
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.a.e.e, com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalConnect
    public void startConnect(PalConnectParams palConnectParams, PalConnectListener palConnectListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f10755a, "params:" + palConnectParams + " listener:" + palConnectListener);
        if (palConnectParams == null || palConnectParams.deviceInfo == null || palConnectListener == null) {
            ALog.e(f10755a, "startConnect params null");
            return;
        }
        IPlugin pluginByDevId = TextUtils.isEmpty(palConnectParams.pluginId) ? PluginMgr.getInstance().getPluginByDevId(palConnectParams.getDevId()) : PluginMgr.getInstance().getPluginByPluginId(palConnectParams.pluginId);
        if (pluginByDevId == null) {
            palConnectListener.onLoad(1, null, palConnectParams.deviceInfo);
            ALog.e(f10755a, "startConnect error plugin not found");
            return;
        }
        PalDeviceInfo privatePkDn = PluginMgr.getInstance().toPrivatePkDn(palConnectParams.deviceInfo, pluginByDevId.getPluginId());
        PalDeviceInfo palDeviceInfo = palConnectParams.deviceInfo;
        IPalConnect palConnect = pluginByDevId.getPalBridge().getPalConnect(privatePkDn);
        if (palConnect == null) {
            palConnectListener.onLoad(1, null, palConnectParams.deviceInfo);
            ALog.e(f10755a, "startConnect error connect not found");
            return;
        }
        if (palDeviceInfo == null || TextUtils.isEmpty(palDeviceInfo.getDevId())) {
            palConnectListener.onLoad(1, null, palConnectParams.deviceInfo);
            ALog.e(f10755a, "startConnect error getDevId is null.");
            return;
        }
        this.f10756b.a(palDeviceInfo.getDevId(), palConnect);
        palConnectParams.deviceInfo = PluginMgr.getInstance().toPrivatePkDn(palConnectParams.deviceInfo, pluginByDevId.getPluginId());
        ALog.d(f10755a, "startConnect params:" + palConnectParams + " devid:" + palConnectParams.deviceInfo.getDevId() + " plugin:" + pluginByDevId.getPluginId());
        palConnect.startConnect(palConnectParams, new i(new a(palDeviceInfo, palConnectListener, new b(palConnect, privatePkDn), this.f10757c, palConnect), palConnect.getPluginId()));
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.a.e.e, com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalDiscovery
    public boolean startNotifyMonitor(PalDiscoveryListener palDiscoveryListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        for (Map.Entry<String, IPlugin> entry : PluginMgr.getInstance().getPluginList().entrySet()) {
            if (entry.getValue() != null && entry.getValue().getPalBridge() != null && entry.getValue().getPalBridge().getPalDiscovery() != null) {
                try {
                    entry.getValue().getPalBridge().getPalDiscovery().startNotifyMonitor(new d(entry.getValue().getPluginId(), palDiscoveryListener));
                } catch (Throwable th) {
                    ALog.e(f10755a, "startNotifyMonitor Throwable:" + th.toString());
                }
            }
        }
        return true;
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.a.e.e, com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalConnect
    public void stopConnect(PalDeviceInfo palDeviceInfo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (palDeviceInfo == null) {
            ALog.e(f10755a, "stopConnect deviceInfo null");
            return;
        }
        IPalConnect iPalConnectB = this.f10756b.b(palDeviceInfo.getDevId());
        this.f10756b.a(palDeviceInfo.getDevId());
        if (iPalConnectB != null) {
            iPalConnectB.stopConnect(PluginMgr.getInstance().toPrivatePkDn(palDeviceInfo, iPalConnectB.getPluginId()));
        }
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.a.e.e, com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalConnect
    public boolean subscribe(PalSubMessage palSubMessage, PalMsgListener palMsgListener, PalMsgListener palMsgListener2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        IPalConnect iPalConnectB = this.f10756b.b(palSubMessage.deviceInfo.getDevId());
        if (iPalConnectB == null) {
            if (palMsgListener != null) {
                PalRspMessage palRspMessage = new PalRspMessage();
                palRspMessage.code = 1;
                palMsgListener.onLoad(palRspMessage);
            }
            ALog.e(f10755a, "subscribe connect null");
            return false;
        }
        PalDeviceInfo palDeviceInfo = palSubMessage.deviceInfo;
        PalDeviceInfo privatePkDn = PluginMgr.getInstance().toPrivatePkDn(palSubMessage.deviceInfo, iPalConnectB.getPluginId());
        palSubMessage.deviceInfo = privatePkDn;
        palSubMessage.topic = a(palSubMessage.topic, palDeviceInfo, privatePkDn);
        ALog.d(f10755a, "subscribe topic:" + palSubMessage.topic);
        return iPalConnectB.subscribe(palSubMessage, new h(palDeviceInfo, palMsgListener), new h(palDeviceInfo, palMsgListener2));
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.a.e.e, com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalConnect
    public boolean unsubscribe(PalSubMessage palSubMessage, PalMsgListener palMsgListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        IPalConnect iPalConnectB = this.f10756b.b(palSubMessage.deviceInfo.getDevId());
        if (iPalConnectB == null) {
            if (palMsgListener == null) {
                return false;
            }
            PalRspMessage palRspMessage = new PalRspMessage();
            palRspMessage.code = 1;
            palMsgListener.onLoad(palRspMessage);
            return false;
        }
        PalDeviceInfo palDeviceInfo = palSubMessage.deviceInfo;
        PalDeviceInfo privatePkDn = PluginMgr.getInstance().toPrivatePkDn(palSubMessage.deviceInfo, iPalConnectB.getPluginId());
        palSubMessage.deviceInfo = privatePkDn;
        palSubMessage.topic = a(palSubMessage.topic, palDeviceInfo, privatePkDn);
        ALog.d(f10755a, "unsubscribe topic:" + palSubMessage.topic);
        return iPalConnectB.unsubscribe(palSubMessage, new h(palDeviceInfo, palMsgListener));
    }
}
