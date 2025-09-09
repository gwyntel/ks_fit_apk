package com.aliyun.alink.linksdk.alcs.lpbs.api;

import android.text.TextUtils;
import com.aliyun.alink.linksdk.alcs.lpbs.a.a.a;
import com.aliyun.alink.linksdk.alcs.lpbs.a.e.f;
import com.aliyun.alink.linksdk.alcs.lpbs.a.e.k;
import com.aliyun.alink.linksdk.alcs.lpbs.a.e.l;
import com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalConnect;
import com.aliyun.alink.linksdk.alcs.lpbs.component.auth.IAuthProvider;
import com.aliyun.alink.linksdk.alcs.lpbs.component.cloud.ICloudChannelFactory;
import com.aliyun.alink.linksdk.alcs.lpbs.component.cloud.IDevInfoTrans;
import com.aliyun.alink.linksdk.alcs.lpbs.component.cloud.IJsProvider;
import com.aliyun.alink.linksdk.alcs.lpbs.component.cloud.IThingCloudChannel;
import com.aliyun.alink.linksdk.alcs.lpbs.component.jsengine.IJSEngine;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalConnectParams;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalDeviceInfo;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalDiscoveryConfig;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalDiscoveryDeviceInfo;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalInitData;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalProbeResult;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalReqMessage;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalSubMessage;
import com.aliyun.alink.linksdk.alcs.lpbs.listener.PalConnectListener;
import com.aliyun.alink.linksdk.alcs.lpbs.listener.PalDeviceStateListener;
import com.aliyun.alink.linksdk.alcs.lpbs.listener.PalDiscoveryListener;
import com.aliyun.alink.linksdk.alcs.lpbs.listener.PalMsgListener;
import com.aliyun.alink.linksdk.alcs.lpbs.listener.PalProbeListener;
import com.aliyun.alink.linksdk.alcs.lpbs.plugin.IPlugin;
import com.aliyun.alink.linksdk.alcs.lpbs.plugin.PluginConfig;
import com.aliyun.alink.linksdk.alcs.lpbs.utils.TopicUtils;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/* loaded from: classes2.dex */
public class PluginMgr extends IAlcsPal {
    private static final String TAG = "[AlcsLPBS]PluginMgr";
    private a mChannelMgr;
    private com.aliyun.alink.linksdk.alcs.lpbs.a.b.a mConnectMgr;
    private IDevInfoTrans mDevInfoTrans;
    private Map<String, Set<String>> mDevPluginIndex;
    private com.aliyun.alink.linksdk.alcs.lpbs.a.d.a mDevStateListenerMgr;
    private Map<String, Map<String, PalDiscoveryDeviceInfo>> mFoundDevList;
    private Map<String, IPlugin> mPluginList;
    private PluginMgrConfig mPluginMgrConfig;
    private l mRegisterLayer;

    private static class InstanceHolder {
        private static PluginMgr mInstace = new PluginMgr();

        private InstanceHolder() {
        }
    }

    public static PluginMgr getInstance() {
        return InstanceHolder.mInstace;
    }

    private void initLayer() {
        this.mRegisterLayer = new l(this.mDevStateListenerMgr, this.mChannelMgr, new f(this.mConnectMgr, this.mChannelMgr, new k(this.mConnectMgr, this.mChannelMgr, null)));
    }

    private void insertDevPluginId(String str, String str2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            ALog.e(TAG, "insertDevPluginId params null");
            return;
        }
        ALog.d(TAG, "insertDiscoveryDev devId:" + str + " pluginId:" + str2);
        Set<String> concurrentSkipListSet = this.mDevPluginIndex.get(str);
        if (concurrentSkipListSet == null) {
            concurrentSkipListSet = new ConcurrentSkipListSet<>();
            this.mDevPluginIndex.put(str, concurrentSkipListSet);
        }
        concurrentSkipListSet.add(str2);
    }

    private boolean isPkDnNeedConvert(PalDeviceInfo palDeviceInfo) {
        return true;
    }

    private void removeDevPluginId(String str, String str2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            ALog.e(TAG, "removeDevPluginId params null");
            return;
        }
        ALog.d(TAG, "removeDevPluginId devId:" + str + " pluginId:" + str2);
        Set<String> set = this.mDevPluginIndex.get(str);
        if (set == null) {
            return;
        }
        set.remove(str2);
    }

    public boolean addPlugin(IPlugin iPlugin) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "addPlugin plugin:" + iPlugin);
        if (iPlugin != null) {
            this.mPluginList.put(iPlugin.getPluginId(), iPlugin);
            return true;
        }
        ALog.e(TAG, "addPlugin plugin:" + iPlugin);
        return false;
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalConnect
    public boolean asyncSendRequest(PalReqMessage palReqMessage, PalMsgListener palMsgListener) {
        return this.mRegisterLayer.asyncSendRequest(palReqMessage, palMsgListener);
    }

    public void clearDiscoveryDevInfo() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "clearDiscoveryDevInfo");
        this.mFoundDevList.clear();
        this.mDevPluginIndex.clear();
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.api.IAlcsPal
    public void deInitAlcs() {
        this.mRegisterLayer.deInitAlcs();
    }

    public a getChannelMgr() {
        return this.mChannelMgr;
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalConnect
    public int getConnectType(PalDeviceInfo palDeviceInfo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (palDeviceInfo == null || TextUtils.isEmpty(palDeviceInfo.getDevId())) {
            return 0;
        }
        IPalConnect iPalConnectB = this.mConnectMgr.b(palDeviceInfo.getDevId());
        ALog.d(TAG, "getConnectType deviceInfo:" + palDeviceInfo + " iPalConnect:" + iPalConnectB);
        if (iPalConnectB != null) {
            return iPalConnectB.getConnectType(palDeviceInfo);
        }
        return 0;
    }

    public IJSEngine getJsEngine() {
        PluginMgrConfig pluginMgrConfig = this.mPluginMgrConfig;
        if (pluginMgrConfig == null) {
            return null;
        }
        return pluginMgrConfig.jsEngine;
    }

    public IJsProvider getJsProvider() {
        return this.mPluginMgrConfig.jsProvider;
    }

    public IPlugin getPluginByDevId(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "getPluginByDevId devId:" + str + " mDevPluginIndex:" + this.mDevPluginIndex);
        Set<String> set = this.mDevPluginIndex.get(str);
        if (set == null) {
            ALog.e(TAG, "getPluginByDevId devId pluginId null");
            return null;
        }
        String string = "iot_ica";
        if (!set.contains("iot_ica")) {
            Object[] array = set.toArray();
            string = array.length > 0 ? array[0].toString() : null;
        }
        if (TextUtils.isEmpty(string)) {
            return null;
        }
        return this.mPluginList.get(string);
    }

    public IPlugin getPluginByPluginId(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        IPlugin iPlugin = this.mPluginList.get(str);
        ALog.d(TAG, "getPluginByPluginId pluginId:" + str + " plugin:" + iPlugin + " mPluginList:" + this.mPluginList);
        return iPlugin;
    }

    public int getPluginCount() {
        return this.mPluginList.size();
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalConnect
    public String getPluginId() {
        return AlcsPalConst.DEFAULT_PLUGIN_ID;
    }

    public Map<String, IPlugin> getPluginList() {
        return this.mPluginList;
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.api.IAlcsPal
    public void initAlcs(PalInitData palInitData) {
        this.mRegisterLayer.initAlcs(palInitData);
    }

    public void initDevTrans(PalDiscoveryDeviceInfo palDiscoveryDeviceInfo, IDevInfoTrans.IDevInfoTransListener iDevInfoTransListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "initDevTrans deviceInfo:" + palDiscoveryDeviceInfo + " listener:" + iDevInfoTransListener);
        IDevInfoTrans iDevInfoTrans = this.mDevInfoTrans;
        if (iDevInfoTrans != null && iDevInfoTransListener != null) {
            iDevInfoTrans.init(palDiscoveryDeviceInfo, iDevInfoTransListener);
        } else if (iDevInfoTransListener != null) {
            iDevInfoTransListener.onComplete(true, null);
        }
    }

    public boolean initPluginMgr(PluginMgrConfig pluginMgrConfig) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "initPluginMgr config:" + pluginMgrConfig);
        if (pluginMgrConfig == null) {
            ALog.e(TAG, "initPluginMgr error config null");
            return false;
        }
        this.mPluginMgrConfig = pluginMgrConfig;
        this.mDevInfoTrans = pluginMgrConfig.devInfoTrans;
        this.mRegisterLayer.regCloudChannelFactory(pluginMgrConfig.cloudChannelFactory);
        return true;
    }

    public boolean insertDiscoveryDev(String str, String str2, PalDiscoveryDeviceInfo palDiscoveryDeviceInfo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (palDiscoveryDeviceInfo == null || TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            ALog.e(TAG, "insertDiscoveryDev params null");
            return false;
        }
        ALog.d(TAG, "insertDiscoveryDev devId:" + str + " pluginId:" + str2 + " discoveryDeviceInfo:" + palDiscoveryDeviceInfo);
        insertDevPluginId(str, str2);
        Map<String, PalDiscoveryDeviceInfo> concurrentHashMap = this.mFoundDevList.get(str);
        if (concurrentHashMap == null) {
            concurrentHashMap = new ConcurrentHashMap<>();
            this.mFoundDevList.put(str, concurrentHashMap);
        }
        concurrentHashMap.put(str2, palDiscoveryDeviceInfo);
        return true;
    }

    public boolean isDataNeedConvert(PalDeviceInfo palDeviceInfo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (palDeviceInfo == null) {
            ALog.e(TAG, "isDataNeedConvert false deviceInfo null");
        }
        Map<String, PalDiscoveryDeviceInfo> map = this.mFoundDevList.get(palDeviceInfo.getDevId());
        if (map == null || map.containsKey(AlcsPalConst.ICA_PAL_ID)) {
            ALog.e(TAG, "isDataNeedConvert false allPluginidDevList null");
            return false;
        }
        while (true) {
            boolean z2 = false;
            for (Map.Entry<String, PalDiscoveryDeviceInfo> entry : map.entrySet()) {
                if (!TextUtils.isEmpty(entry.getKey()) && !entry.getKey().equalsIgnoreCase(AlcsPalConst.ICA_PAL_ID)) {
                    ALog.d(TAG, "isDataNeedConvert deviceInfo:" + palDeviceInfo.getDevId() + " palDiscoveryDeviceInfo.dataFormat:" + entry.getValue().dataFormat);
                    if (TextUtils.isEmpty(entry.getValue().dataFormat) || "ALINK_FORMAT".equalsIgnoreCase(entry.getValue().dataFormat)) {
                        break;
                    }
                    z2 = true;
                }
            }
            ALog.d(TAG, "isDataNeedConvert deviceInfo:" + palDeviceInfo.getDevId() + " isDataNeedConvert:" + z2);
            return z2;
        }
    }

    public boolean isDataToCloud(PalDeviceInfo palDeviceInfo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (palDeviceInfo == null) {
            ALog.e(TAG, "isDataToCloud deviceInfo null");
            return false;
        }
        Map<String, PalDiscoveryDeviceInfo> map = this.mFoundDevList.get(palDeviceInfo.getDevId());
        if (map == null || map.containsKey(AlcsPalConst.ICA_PAL_ID)) {
            ALog.w(TAG, "isDataToCloud allPluginidDevList:" + map + " return false");
            return false;
        }
        for (Map.Entry<String, PalDiscoveryDeviceInfo> entry : map.entrySet()) {
            if (entry.getValue().extraData.get(PalDiscoveryDeviceInfo.EXTRA_KEY_BREEZE_SUB_TYPE) != null && (((Integer) entry.getValue().extraData.get(PalDiscoveryDeviceInfo.EXTRA_KEY_BREEZE_SUB_TYPE)).intValue() == 3 || ((Integer) entry.getValue().extraData.get(PalDiscoveryDeviceInfo.EXTRA_KEY_BREEZE_SUB_TYPE)).intValue() == 4)) {
                ALog.d(TAG, "isDataToCloud deviceInfo:" + palDeviceInfo.getDevId() + " false PalDiscoveryDeviceInfo:" + entry.getValue());
                return false;
            }
            if (!TextUtils.isEmpty(entry.getKey()) && !entry.getKey().equalsIgnoreCase(AlcsPalConst.ICA_PAL_ID)) {
                return entry.getValue().isDataToCloud;
            }
        }
        ALog.d(TAG, "isDataToCloud deviceInfo:" + palDeviceInfo.getDevId() + " false");
        return false;
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalConnect
    public boolean isDeviceConnected(PalDeviceInfo palDeviceInfo) {
        return this.mRegisterLayer.isDeviceConnected(palDeviceInfo);
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalConnect
    public void onCloudChannelCreate(IThingCloudChannel iThingCloudChannel) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.e(TAG, "onCloudChannelCreate empty impl");
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalProbe
    public void probeDevice(final PalDeviceInfo palDeviceInfo, final PalProbeListener palProbeListener) {
        this.mRegisterLayer.probeDevice(palDeviceInfo, new PalProbeListener() { // from class: com.aliyun.alink.linksdk.alcs.lpbs.api.PluginMgr.1
            @Override // com.aliyun.alink.linksdk.alcs.lpbs.listener.PalProbeListener
            public void onComplete(PalDeviceInfo palDeviceInfo2, PalProbeResult palProbeResult) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                if (palProbeResult != null && palProbeResult.probeResult != 0 && palDeviceInfo2 != null) {
                    PluginMgr.this.removeDiscoveryDev(palDeviceInfo2.getDevId(), palProbeResult.probePluginId);
                }
                PalProbeListener palProbeListener2 = palProbeListener;
                if (palProbeListener2 != null) {
                    palProbeListener2.onComplete(palDeviceInfo, palProbeResult);
                }
            }
        });
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.api.IAlcsPal
    public boolean regAuthProvider(String str, IAuthProvider iAuthProvider) {
        return this.mRegisterLayer.regAuthProvider(str, iAuthProvider);
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.api.IAlcsPal
    public void regCloudChannelFactory(ICloudChannelFactory iCloudChannelFactory) {
        this.mRegisterLayer.regCloudChannelFactory(iCloudChannelFactory);
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalConnect
    public boolean regDeviceStateListener(PalDeviceInfo palDeviceInfo, PalDeviceStateListener palDeviceStateListener) {
        return this.mRegisterLayer.regDeviceStateListener(palDeviceInfo, palDeviceStateListener);
    }

    public boolean removeDiscoveryDev(String str, String str2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            ALog.e(TAG, "removeDiscoveryDev params null");
            return false;
        }
        ALog.d(TAG, "removeDiscoveryDev devId:" + str + " pluginId:" + str2);
        removeDevPluginId(str, str2);
        Map<String, PalDiscoveryDeviceInfo> map = this.mFoundDevList.get(str);
        if (map == null) {
            return true;
        }
        map.remove(str2);
        return true;
    }

    public void removePlugin(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "removePlugin pluginId:" + str);
        IPlugin iPluginRemove = this.mPluginList.remove(str);
        if (iPluginRemove != null) {
            iPluginRemove.stopPlugin(str);
        }
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalConnect
    public void startConnect(PalConnectParams palConnectParams, PalConnectListener palConnectListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        updateDataFormat(palConnectParams);
        this.mRegisterLayer.startConnect(palConnectParams, palConnectListener);
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalDiscovery
    public boolean startDiscovery(int i2, PalDiscoveryListener palDiscoveryListener) {
        return this.mRegisterLayer.startDiscovery(i2, palDiscoveryListener);
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalDiscovery
    public boolean startNotifyMonitor(PalDiscoveryListener palDiscoveryListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "startNotifyMonitor listener:" + palDiscoveryListener);
        return this.mRegisterLayer.startNotifyMonitor(palDiscoveryListener);
    }

    public boolean startPlugin(IPlugin iPlugin) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "startPlugin plugin:" + iPlugin);
        PluginConfig pluginConfig = new PluginConfig();
        PluginMgrConfig pluginMgrConfig = this.mPluginMgrConfig;
        pluginConfig.context = pluginMgrConfig.context;
        pluginConfig.initData = pluginMgrConfig.initData;
        pluginConfig.lpbsCloudProxy = pluginMgrConfig.lpbsCloudProxy;
        pluginConfig.authProvider = pluginMgrConfig.authProvider;
        iPlugin.startPlugin(iPlugin.getPluginId(), pluginConfig);
        return true;
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalConnect
    public void stopConnect(PalDeviceInfo palDeviceInfo) {
        this.mRegisterLayer.stopConnect(palDeviceInfo);
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalDiscovery
    public boolean stopDiscovery() {
        return this.mRegisterLayer.stopDiscovery();
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalDiscovery
    public boolean stopNotifyMonitor() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "stopNotifyMonitor");
        return this.mRegisterLayer.stopNotifyMonitor();
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalConnect
    public boolean subscribe(PalSubMessage palSubMessage, PalMsgListener palMsgListener, PalMsgListener palMsgListener2) {
        return this.mRegisterLayer.subscribe(palSubMessage, palMsgListener, palMsgListener2);
    }

    public PalDeviceInfo toAliIoTPkDn(PalDeviceInfo palDeviceInfo, String str) {
        IDevInfoTrans iDevInfoTrans = this.mDevInfoTrans;
        return iDevInfoTrans != null ? iDevInfoTrans.toAliIoTDeviceInfo(palDeviceInfo, str) : palDeviceInfo;
    }

    public PalDeviceInfo toPrivatePkDn(PalDeviceInfo palDeviceInfo, String str) {
        IDevInfoTrans iDevInfoTrans;
        return (!isPkDnNeedConvert(palDeviceInfo) || (iDevInfoTrans = this.mDevInfoTrans) == null) ? palDeviceInfo : iDevInfoTrans.toPrivateDeviceInfo(palDeviceInfo, str);
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalConnect
    public boolean unregDeviceStateListener(PalDeviceInfo palDeviceInfo, PalDeviceStateListener palDeviceStateListener) {
        return this.mRegisterLayer.unregDeviceStateListener(palDeviceInfo, palDeviceStateListener);
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalConnect
    public boolean unsubscribe(PalSubMessage palSubMessage, PalMsgListener palMsgListener) {
        return this.mRegisterLayer.unsubscribe(palSubMessage, palMsgListener);
    }

    public void updateDataFormat(PalConnectParams palConnectParams) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (palConnectParams == null) {
            ALog.e(TAG, "updateDataFormat connectParams null");
            return;
        }
        if (TextUtils.isEmpty(palConnectParams.dataFormat)) {
            return;
        }
        if (TextUtils.isEmpty(palConnectParams.getDevId())) {
            ALog.w(TAG, "updateDataFormat connectParams.getDevId() empty");
            return;
        }
        Map<String, PalDiscoveryDeviceInfo> map = this.mFoundDevList.get(palConnectParams.getDevId());
        if (map == null) {
            ALog.w(TAG, "updateDataFormat allPluginidDevList empty");
            return;
        }
        for (Map.Entry<String, PalDiscoveryDeviceInfo> entry : map.entrySet()) {
            if (!TextUtils.isEmpty(entry.getKey())) {
                ALog.d(TAG, "updateDataFormat connectdev:" + palConnectParams.getDevId() + " dataFormat:" + palConnectParams.dataFormat + " palDiscoveryDeviceInfo:" + entry.getValue());
                if (entry.getValue() != null) {
                    entry.getValue().dataFormat = palConnectParams.dataFormat;
                }
            }
        }
    }

    public void updateDiscoveryInfo(String str, String str2, String str3) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "updateDiscoveryInfo oldId:" + str + " productKey:" + str2 + " devName:" + str3 + " mDevPluginIndex:" + this.mDevPluginIndex);
        String strCombineStr = TopicUtils.combineStr(str2, str3);
        Set<String> set = this.mDevPluginIndex.get(str);
        if (set != null && set.size() > 0) {
            Set<String> concurrentSkipListSet = this.mDevPluginIndex.get(strCombineStr);
            if (concurrentSkipListSet == null) {
                concurrentSkipListSet = new ConcurrentSkipListSet<>();
                this.mDevPluginIndex.put(strCombineStr, concurrentSkipListSet);
            }
            concurrentSkipListSet.addAll(set);
        }
        Map<String, PalDiscoveryDeviceInfo> map = this.mFoundDevList.get(str);
        Map<String, PalDiscoveryDeviceInfo> concurrentHashMap = this.mFoundDevList.get(strCombineStr);
        if (concurrentHashMap == null) {
            concurrentHashMap = new ConcurrentHashMap<>();
            this.mFoundDevList.put(strCombineStr, concurrentHashMap);
        }
        if (map != null) {
            concurrentHashMap.putAll(map);
        }
        ALog.d(TAG, "updateDiscoveryInfo after mFoundDevList:" + this.mFoundDevList + " mDevPluginIndex:" + this.mDevPluginIndex);
    }

    private PluginMgr() {
        this.mPluginList = new ConcurrentHashMap();
        this.mDevPluginIndex = new ConcurrentHashMap();
        this.mFoundDevList = new ConcurrentHashMap();
        this.mChannelMgr = new a();
        com.aliyun.alink.linksdk.alcs.lpbs.a.b.a aVar = new com.aliyun.alink.linksdk.alcs.lpbs.a.b.a();
        this.mConnectMgr = aVar;
        this.mDevStateListenerMgr = new com.aliyun.alink.linksdk.alcs.lpbs.a.d.a(aVar, this.mChannelMgr);
        initLayer();
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalDiscovery
    public boolean startDiscovery(int i2, PalDiscoveryConfig palDiscoveryConfig, PalDiscoveryListener palDiscoveryListener) {
        return this.mRegisterLayer.startDiscovery(i2, palDiscoveryConfig, palDiscoveryListener);
    }
}
