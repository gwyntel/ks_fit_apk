package com.aliyun.alink.linksdk.alcs.lpbs.a.e;

import android.text.TextUtils;
import com.aliyun.alink.linksdk.alcs.lpbs.api.PluginMgr;
import com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalBridge;
import com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalDiscovery;
import com.aliyun.alink.linksdk.alcs.lpbs.component.cloud.IJsProvider;
import com.aliyun.alink.linksdk.alcs.lpbs.component.cloud.IJsQeuryCallback;
import com.aliyun.alink.linksdk.alcs.lpbs.component.cloud.IThingCloudChannel;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalDeviceInfo;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalDiscoveryConfig;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalInitData;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalReqMessage;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalSubMessage;
import com.aliyun.alink.linksdk.alcs.lpbs.listener.PalDiscoveryListener;
import com.aliyun.alink.linksdk.alcs.lpbs.listener.PalMsgListener;
import com.aliyun.alink.linksdk.alcs.lpbs.plugin.IPlugin;
import com.aliyun.alink.linksdk.alcs.lpbs.utils.TopicUtils;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class f extends e {

    /* renamed from: a, reason: collision with root package name */
    private static final String f10730a = "[AlcsLPBS]MainDataConvertLayer";

    /* renamed from: b, reason: collision with root package name */
    private com.aliyun.alink.linksdk.alcs.lpbs.a.b.a f10731b;

    /* renamed from: c, reason: collision with root package name */
    private com.aliyun.alink.linksdk.alcs.lpbs.a.a.a f10732c;

    public f(com.aliyun.alink.linksdk.alcs.lpbs.a.b.a aVar, com.aliyun.alink.linksdk.alcs.lpbs.a.a.a aVar2, e eVar) {
        super(eVar);
        this.f10731b = aVar;
        this.f10732c = aVar2;
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.a.e.e, com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalConnect
    public boolean asyncSendRequest(final PalReqMessage palReqMessage, final PalMsgListener palMsgListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f10730a, "asyncSendRequest reqMessageInfo:" + palReqMessage + " callback:" + palMsgListener);
        if (!PluginMgr.getInstance().isDataNeedConvert(palReqMessage.deviceInfo) || PluginMgr.getInstance().getJsProvider() == null || PluginMgr.getInstance().getJsEngine() == null) {
            super.asyncSendRequest(palReqMessage, palMsgListener);
            return true;
        }
        IJsProvider jsProvider = PluginMgr.getInstance().getJsProvider();
        PalDeviceInfo palDeviceInfo = palReqMessage.deviceInfo;
        jsProvider.queryJsCode(palDeviceInfo.productModel, palDeviceInfo.deviceId, new IJsQeuryCallback() { // from class: com.aliyun.alink.linksdk.alcs.lpbs.a.e.f.1
            @Override // com.aliyun.alink.linksdk.alcs.lpbs.component.cloud.IJsQeuryCallback
            public void onLoad(String str, String str2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                PalReqMessage palReqMessage2 = palReqMessage;
                String str3 = palReqMessage2.topic;
                PalDeviceInfo palDeviceInfo2 = palReqMessage2.deviceInfo;
                palReqMessage2.topic = TopicUtils.topicToRawDownTopic(str3, palDeviceInfo2.productModel, palDeviceInfo2.deviceId);
                try {
                    if (!TextUtils.isEmpty(str2)) {
                        byte[] bArrProtocolToRawData = PluginMgr.getInstance().getJsEngine().protocolToRawData(str2, new String(palReqMessage.payload, "UTF-8"));
                        if (bArrProtocolToRawData != null) {
                            palReqMessage.payload = bArrProtocolToRawData;
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                f.super.asyncSendRequest(palReqMessage, new com.aliyun.alink.linksdk.alcs.lpbs.bridge.a.b(palMsgListener, str2, PluginMgr.getInstance().getJsEngine()));
            }
        });
        return true;
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.a.e.e, com.aliyun.alink.linksdk.alcs.lpbs.api.IAlcsPal
    public void deInitAlcs() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f10730a, "deInitAlcs initData");
        for (Map.Entry<String, IPlugin> entry : PluginMgr.getInstance().getPluginList().entrySet()) {
            entry.getValue().getPalBridge().deInitBridge();
            entry.getValue().stopPlugin(entry.getValue().getPluginId());
        }
        PluginMgr.getInstance().getPluginList().clear();
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.a.e.e, com.aliyun.alink.linksdk.alcs.lpbs.api.IAlcsPal
    public void initAlcs(PalInitData palInitData) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f10730a, "initAlcs initData:" + palInitData);
        for (Map.Entry<String, IPlugin> entry : PluginMgr.getInstance().getPluginList().entrySet()) {
            if (entry.getValue().getPalBridge() != null) {
                entry.getValue().getPalBridge().initBridge(palInitData);
            }
        }
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.a.e.e, com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalDiscovery
    public boolean startDiscovery(int i2, PalDiscoveryListener palDiscoveryListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f10730a, "startDiscovery timeOut:" + i2 + " listener:" + palDiscoveryListener);
        Map<String, IPlugin> pluginList = PluginMgr.getInstance().getPluginList();
        if (pluginList == null) {
            ALog.e(f10730a, "startDiscovery pluginMap empty");
            return false;
        }
        for (Map.Entry<String, IPlugin> entry : pluginList.entrySet()) {
            if (entry.getValue() != null && entry.getValue().getPalBridge() != null && entry.getValue().getPalBridge().getPalDiscovery() != null) {
                entry.getValue().getPalBridge().getPalDiscovery().startDiscovery(i2, new d(entry.getValue().getPluginId(), palDiscoveryListener));
            }
        }
        return true;
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.a.e.e, com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalConnect
    public void stopConnect(PalDeviceInfo palDeviceInfo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (palDeviceInfo == null) {
            ALog.e(f10730a, "stopConnect deviceInfo null error");
            return;
        }
        ALog.d(f10730a, "stopConnect deviceInfo:" + palDeviceInfo.getDevId());
        if (this.f10731b.b(palDeviceInfo.getDevId()) != null) {
            this.f10732c.a(palDeviceInfo);
        }
        super.stopConnect(palDeviceInfo);
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.a.e.e, com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalDiscovery
    public boolean stopDiscovery() {
        for (Map.Entry<String, IPlugin> entry : PluginMgr.getInstance().getPluginList().entrySet()) {
            if (entry.getValue() != null && entry.getValue().getPalBridge() != null && entry.getValue().getPalBridge().getPalDiscovery() != null) {
                entry.getValue().getPalBridge().getPalDiscovery().stopDiscovery();
            }
        }
        return true;
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.a.e.e, com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalDiscovery
    public boolean stopNotifyMonitor() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        for (Map.Entry<String, IPlugin> entry : PluginMgr.getInstance().getPluginList().entrySet()) {
            if (entry.getValue() != null && entry.getValue().getPalBridge() != null && entry.getValue().getPalBridge().getPalDiscovery() != null) {
                try {
                    entry.getValue().getPalBridge().getPalDiscovery().stopNotifyMonitor();
                } catch (Throwable th) {
                    ALog.e(f10730a, "stopNotifyMonitor throwable:" + th.toString());
                }
            }
        }
        return true;
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.a.e.e, com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalConnect
    public boolean subscribe(final PalSubMessage palSubMessage, final PalMsgListener palMsgListener, final PalMsgListener palMsgListener2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f10730a, "subscribe getJsProvider:" + PluginMgr.getInstance().getJsProvider() + " getJsEngine:" + PluginMgr.getInstance().getJsEngine());
        final IThingCloudChannel iThingCloudChannelB = this.f10732c.b(palSubMessage.deviceInfo);
        if (!PluginMgr.getInstance().isDataNeedConvert(palSubMessage.deviceInfo) || PluginMgr.getInstance().getJsProvider() == null || PluginMgr.getInstance().getJsEngine() == null) {
            return super.subscribe(palSubMessage, palMsgListener, new m(palSubMessage.deviceInfo, iThingCloudChannelB, palSubMessage.topic, palMsgListener2));
        }
        IJsProvider jsProvider = PluginMgr.getInstance().getJsProvider();
        PalDeviceInfo palDeviceInfo = palSubMessage.deviceInfo;
        jsProvider.queryJsCode(palDeviceInfo.productModel, palDeviceInfo.deviceId, new IJsQeuryCallback() { // from class: com.aliyun.alink.linksdk.alcs.lpbs.a.e.f.2
            @Override // com.aliyun.alink.linksdk.alcs.lpbs.component.cloud.IJsQeuryCallback
            public void onLoad(String str, String str2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                PalSubMessage palSubMessage2 = palSubMessage;
                String str3 = palSubMessage2.topic;
                PalDeviceInfo palDeviceInfo2 = palSubMessage2.deviceInfo;
                palSubMessage2.topic = TopicUtils.topicToRawUpTopic(palDeviceInfo2.productModel, palDeviceInfo2.deviceId);
                try {
                    if (!TextUtils.isEmpty(str2)) {
                        byte[] bArrProtocolToRawData = PluginMgr.getInstance().getJsEngine().protocolToRawData(str2, new String(palSubMessage.payload, "UTF-8"));
                        if (bArrProtocolToRawData != null) {
                            palSubMessage.payload = bArrProtocolToRawData;
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                f.super.subscribe(palSubMessage, new com.aliyun.alink.linksdk.alcs.lpbs.bridge.a.b(palMsgListener, str2, PluginMgr.getInstance().getJsEngine()), new com.aliyun.alink.linksdk.alcs.lpbs.bridge.a.a(new m(palSubMessage.deviceInfo, iThingCloudChannelB, str3, palMsgListener2), str2, PluginMgr.getInstance().getJsEngine()));
            }
        });
        return true;
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.a.e.e, com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalConnect
    public boolean unsubscribe(final PalSubMessage palSubMessage, final PalMsgListener palMsgListener) {
        if (!PluginMgr.getInstance().isDataNeedConvert(palSubMessage.deviceInfo) || PluginMgr.getInstance().getJsProvider() == null || PluginMgr.getInstance().getJsEngine() == null) {
            return super.unsubscribe(palSubMessage, palMsgListener);
        }
        IJsProvider jsProvider = PluginMgr.getInstance().getJsProvider();
        PalDeviceInfo palDeviceInfo = palSubMessage.deviceInfo;
        jsProvider.queryJsCode(palDeviceInfo.productModel, palDeviceInfo.deviceId, new IJsQeuryCallback() { // from class: com.aliyun.alink.linksdk.alcs.lpbs.a.e.f.3
            @Override // com.aliyun.alink.linksdk.alcs.lpbs.component.cloud.IJsQeuryCallback
            public void onLoad(String str, String str2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                PalSubMessage palSubMessage2 = palSubMessage;
                PalDeviceInfo palDeviceInfo2 = palSubMessage2.deviceInfo;
                palSubMessage2.topic = TopicUtils.topicToRawUpTopic(palDeviceInfo2.productModel, palDeviceInfo2.deviceId);
                try {
                    if (!TextUtils.isEmpty(str2)) {
                        byte[] bArrProtocolToRawData = PluginMgr.getInstance().getJsEngine().protocolToRawData(str2, new String(palSubMessage.payload, "UTF-8"));
                        if (bArrProtocolToRawData != null) {
                            palSubMessage.payload = bArrProtocolToRawData;
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                f.super.unsubscribe(palSubMessage, palMsgListener);
            }
        });
        return true;
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.a.e.e, com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalDiscovery
    public boolean startDiscovery(int i2, PalDiscoveryConfig palDiscoveryConfig, PalDiscoveryListener palDiscoveryListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Map<String, IPlugin> pluginList = PluginMgr.getInstance().getPluginList();
        if (pluginList == null) {
            ALog.e(f10730a, "startDiscovery pluginMap empty");
            return false;
        }
        if (palDiscoveryConfig == null) {
            return startDiscovery(i2, palDiscoveryListener);
        }
        List<String> list = palDiscoveryConfig.mPluginIdList;
        if (list != null && !list.isEmpty()) {
            for (String str : palDiscoveryConfig.mPluginIdList) {
                IPlugin iPlugin = pluginList.get(str);
                if (iPlugin == null) {
                    ALog.w(f10730a, "pluginMap not find pluginId:" + str);
                } else {
                    IPalBridge palBridge = iPlugin.getPalBridge();
                    if (palBridge == null) {
                        ALog.w(f10730a, "not find palBridge");
                    } else {
                        IPalDiscovery palDiscovery = palBridge.getPalDiscovery();
                        if (palDiscovery == null) {
                            ALog.w(f10730a, "not find palDiscovery");
                        } else {
                            try {
                                ALog.d(f10730a, "startDiscovery pluginId:" + str + " discoveryConfig:" + palDiscoveryConfig + " timeOut:" + i2);
                                palDiscovery.startDiscovery(i2, palDiscoveryConfig, palDiscoveryListener);
                            } catch (Throwable th) {
                                ALog.e(f10730a, "startDiscovery error:" + th.toString());
                            }
                        }
                    }
                }
            }
            return true;
        }
        for (Map.Entry<String, IPlugin> entry : pluginList.entrySet()) {
            if (entry.getValue() != null && entry.getValue().getPalBridge() != null && entry.getValue().getPalBridge().getPalDiscovery() != null) {
                try {
                    entry.getValue().getPalBridge().getPalDiscovery().startDiscovery(i2, palDiscoveryConfig, new d(entry.getValue().getPluginId(), palDiscoveryListener));
                } catch (Throwable th2) {
                    ALog.e(f10730a, "startDiscovery error:" + th2.toString());
                }
            }
        }
        return true;
    }
}
