package com.aliyun.alink.linksdk.tmp.api;

import android.text.TextUtils;
import com.aliyun.alink.linksdk.alcs.lpbs.api.PluginMgr;
import com.aliyun.alink.linksdk.tmp.component.pkdnconvert.DefaultDevInfoTrans;
import com.aliyun.alink.linksdk.tmp.config.DeviceConfig;
import com.aliyun.alink.linksdk.tmp.data.cloud.CloudLcaRequestParams;
import com.aliyun.alink.linksdk.tmp.data.devdetail.DevDTO;
import com.aliyun.alink.linksdk.tmp.data.devdetail.DevDetailData;
import com.aliyun.alink.linksdk.tmp.data.discovery.DiscoveryConfig;
import com.aliyun.alink.linksdk.tmp.device.a.a.a;
import com.aliyun.alink.linksdk.tmp.device.a.a.d;
import com.aliyun.alink.linksdk.tmp.device.e.c;
import com.aliyun.alink.linksdk.tmp.device.panel.listener.IPanelCallback;
import com.aliyun.alink.linksdk.tmp.listener.IDevListener;
import com.aliyun.alink.linksdk.tmp.network.NetConnected;
import com.aliyun.alink.linksdk.tmp.storage.TmpStorage;
import com.aliyun.alink.linksdk.tmp.utils.GsonUtils;
import com.aliyun.alink.linksdk.tmp.utils.TextHelper;
import com.aliyun.alink.linksdk.tmp.utils.TgMeshHelper;
import com.aliyun.alink.linksdk.tools.ALog;
import com.aliyun.alink.linksdk.tools.ThreadTools;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.InvocationTargetException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import javax.crypto.NoSuchPaddingException;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class DeviceManager {
    private static final String TAG = "[Tmp]DeviceManager";
    private static Timer timer;
    private long lastStartDiscoverDeviceTimeOut;
    private Map<String, DeviceBasicData> mDeviceBasicDataList;
    private a mDiscoveryTask;
    private a mInfiniteDiscoveryTask;
    private d mRecNotifyTask;
    private IDiscoveryFilter scanDeviceFilter;
    private IDevListener scanDeviceListener;

    private static class InstanceHolder {
        protected static DeviceManager mInstance = new DeviceManager();

        private InstanceHolder() {
        }
    }

    public static DeviceManager getInstance() {
        return InstanceHolder.mInstance;
    }

    public void addDevIotId(String str, String str2) {
        DeviceBasicData deviceBasicData = this.mDeviceBasicDataList.get(str);
        if (deviceBasicData != null) {
            deviceBasicData.setIotId(str2);
        }
    }

    public DeviceBasicData addDeviceBasicData(DeviceBasicData deviceBasicData) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (deviceBasicData == null) {
            ALog.e(TAG, "addDeviceBasicData basicData null");
            return null;
        }
        DeviceBasicData deviceBasicDataPut = this.mDeviceBasicDataList.put(deviceBasicData.getDevId(), deviceBasicData);
        ALog.d(TAG, "addDeviceBasicData basicData:" + deviceBasicData.getDevId() + " mDeviceBasicDataList:" + this.mDeviceBasicDataList);
        return deviceBasicDataPut;
    }

    public void clearAccessTokenCache() throws IllegalAccessException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalArgumentException, InvocationTargetException {
        TmpStorage.getInstance().clearAccessTokenCache();
    }

    public void clearBasicDataList() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "clearBasicDataList");
        this.mDeviceBasicDataList.clear();
        PluginMgr.getInstance().clearDiscoveryDevInfo();
    }

    public IDevice createDevice(DeviceConfig deviceConfig) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (deviceConfig == null) {
            ALog.e(TAG, "createDevice config empty");
            return null;
        }
        if (DeviceConfig.DeviceType.CLIENT == deviceConfig.getDeviceType()) {
            ALog.d(TAG, "createDevice ClientWrapper");
            return new com.aliyun.alink.linksdk.tmp.device.e.a(deviceConfig);
        }
        if (DeviceConfig.DeviceType.SERVER != deviceConfig.getDeviceType()) {
            return null;
        }
        ALog.d(TAG, "createDevice ServerWrapper");
        return new c(deviceConfig);
    }

    public IProvision createProvision(DeviceConfig deviceConfig) {
        if (deviceConfig != null) {
            return new com.aliyun.alink.linksdk.tmp.device.c.c(deviceConfig);
        }
        ALog.e(TAG, "createProvision config empty");
        return null;
    }

    public void discoverDevices(Object obj, boolean z2, long j2, DiscoveryConfig discoveryConfig, IDiscoveryFilter iDiscoveryFilter, IDevListener iDevListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (this.lastStartDiscoverDeviceTimeOut < 1) {
            this.lastStartDiscoverDeviceTimeOut = System.currentTimeMillis() + j2;
        }
        long jCurrentTimeMillis = this.lastStartDiscoverDeviceTimeOut > System.currentTimeMillis() + j2 ? this.lastStartDiscoverDeviceTimeOut - System.currentTimeMillis() : j2;
        if ("scan".equalsIgnoreCase(String.valueOf(obj))) {
            this.scanDeviceFilter = iDiscoveryFilter;
            this.scanDeviceListener = iDevListener;
        }
        if (z2) {
            getInstance().clearBasicDataList();
        }
        IDiscoveryFilter iDiscoveryFilter2 = this.scanDeviceFilter;
        if (iDiscoveryFilter2 != null) {
            iDiscoveryFilter = iDiscoveryFilter2;
        }
        IDevListener iDevListener2 = this.scanDeviceListener;
        if (iDevListener2 != null) {
            iDevListener = iDevListener2;
        }
        ALog.d(TAG, "discoverDevices tag:" + obj + " clearCache:" + z2 + " timeOutInMilSec:" + j2 + " newTimeout:" + jCurrentTimeMillis + " filter:" + iDiscoveryFilter + " handler:" + iDevListener + " discoveryConfig:" + discoveryConfig);
        a aVar = new a(com.aliyun.alink.linksdk.tmp.connect.a.a(), iDevListener);
        this.mDiscoveryTask = aVar;
        aVar.a(discoveryConfig).a(obj).a(iDiscoveryFilter).a(false).a(jCurrentTimeMillis);
        new com.aliyun.alink.linksdk.tmp.device.a.c().b(this.mDiscoveryTask).a();
        NetConnected.startNetChangeListen();
    }

    public void discoverDevicesInfinite(final boolean z2, final long j2, long j3, final DiscoveryConfig discoveryConfig, final IDiscoveryFilter iDiscoveryFilter, final IDevListener iDevListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        long j4 = j2 + j3;
        if (j4 <= 0) {
            ALog.e(TAG, "discoverDevicesInfinite time <= 0");
            return;
        }
        stopDiscoverDevicesInfinite();
        Timer timer2 = new Timer();
        timer = timer2;
        timer2.schedule(new TimerTask() { // from class: com.aliyun.alink.linksdk.tmp.api.DeviceManager.1
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                if (DeviceManager.this.mDiscoveryTask != null && DeviceManager.this.mDiscoveryTask.c()) {
                    ALog.w(DeviceManager.TAG, "mDiscoveryTask running");
                    return;
                }
                if (DeviceManager.this.mInfiniteDiscoveryTask != null) {
                    DeviceManager.this.mInfiniteDiscoveryTask.b(true);
                }
                if (z2) {
                    DeviceManager.getInstance().clearBasicDataList();
                }
                StringBuilder sb = new StringBuilder();
                sb.append("discoverDevicesInfinite clearCache:");
                sb.append(z2);
                sb.append(" timeOutInMilSec:");
                sb.append(j2);
                sb.append(" filter:");
                sb.append(iDiscoveryFilter);
                sb.append(" handler:");
                sb.append(iDevListener);
                sb.append(" discoveryConfig:");
                sb.append(discoveryConfig);
                sb.append(" discoveryTask isRunning:");
                sb.append(DeviceManager.this.mDiscoveryTask == null ? " empty" : Boolean.valueOf(DeviceManager.this.mDiscoveryTask.c()));
                ALog.d(DeviceManager.TAG, sb.toString());
                DeviceManager.this.mInfiniteDiscoveryTask = new a(com.aliyun.alink.linksdk.tmp.connect.a.a(), iDevListener);
                DeviceManager.this.mInfiniteDiscoveryTask.a(discoveryConfig).a(iDiscoveryFilter).a(false).a(j2);
                new com.aliyun.alink.linksdk.tmp.device.a.c().b(DeviceManager.this.mInfiniteDiscoveryTask).a();
                NetConnected.startNetChangeListen();
            }
        }, 0L, 1000 + j4);
    }

    public List<DeviceBasicData> getAllDeviceDataList() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "getAllDeviceDataList size:" + this.mDeviceBasicDataList.toString());
        ArrayList arrayList = new ArrayList();
        try {
            Iterator<DeviceBasicData> it = this.mDeviceBasicDataList.values().iterator();
            while (it.hasNext()) {
                arrayList.add((DeviceBasicData) it.next().clone());
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return arrayList;
    }

    public String getDevIotId(String str) {
        DeviceBasicData deviceBasicData = this.mDeviceBasicDataList.get(str);
        if (deviceBasicData != null) {
            return deviceBasicData.getIotId();
        }
        return null;
    }

    public DeviceBasicData getDeviceBasicData(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        DeviceBasicData deviceBasicData = this.mDeviceBasicDataList.get(str);
        ALog.d(TAG, "getDeviceBasicData id:" + str + " mDeviceBasicDataList:" + this.mDeviceBasicDataList.toString());
        return deviceBasicData;
    }

    public JSONArray getLocalAuthedDeviceDataList() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        List<DevDTO> list;
        ALog.d(TAG, "getLocalAuthedDeviceDataList");
        JSONArray jSONArray = new JSONArray();
        for (DeviceBasicData deviceBasicData : getAllDeviceDataList()) {
            String devDetailInfo = TmpStorage.getInstance().getDevDetailInfo(deviceBasicData.getDevId());
            ALog.d(TAG, "getLocalAuthedDeviceDataList id:" + deviceBasicData.getDevId() + " detailDataInfo:" + devDetailInfo);
            DevDetailData devDetailData = (DevDetailData) GsonUtils.fromJson(devDetailInfo, new TypeToken<DevDetailData>() { // from class: com.aliyun.alink.linksdk.tmp.api.DeviceManager.2
            }.getType());
            if (devDetailData != null && (list = devDetailData.data) != null && !list.isEmpty()) {
                DevDTO devDTO = devDetailData.data.get(0);
                devDTO.status = 1;
                try {
                    jSONArray.put(new JSONObject(GsonUtils.toJson(devDTO)));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
        return jSONArray;
    }

    public void groupClear(final JSONObject jSONObject, final IPanelCallback iPanelCallback) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "groupClear() called with: params = [" + jSONObject + "], callback = [" + iPanelCallback + "]");
        if (TgMeshHelper.hasTgMeshSdkDependency()) {
            ThreadTools.submitTask(new Runnable() { // from class: com.aliyun.alink.linksdk.tmp.api.DeviceManager.3
                @Override // java.lang.Runnable
                public void run() {
                    TgMeshHelper.groupClear(jSONObject, iPanelCallback);
                }
            }, false);
        } else {
            ALog.w(TAG, "groupClear failed, no tg-mesh dep, return.");
            TgMeshHelper.onFailCallback(iPanelCallback, "CODE_ERROR", "no tg-mesh dep, invalid call.");
        }
    }

    public boolean isDeviceDetailCache(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String devDetailInfo = TmpStorage.getInstance().getDevDetailInfo(str);
        ALog.d(TAG, "isDeviceDetailCache id:" + str + " detailData:" + devDetailInfo);
        return !TextUtils.isEmpty(devDetailInfo);
    }

    @Deprecated
    public void removeDevice(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        removeDeviceBasicData(str);
    }

    public DeviceBasicData removeDeviceBasicData(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "removeDeviceBasicData id:" + str + " mDeviceBasicDataList:" + this.mDeviceBasicDataList.toString());
        return this.mDeviceBasicDataList.remove(str);
    }

    public boolean removeDeviceDetailInfo(String str, String str2) {
        return TmpStorage.getInstance().removeDevDetailInfo(TextHelper.combineStr(str, str2));
    }

    public boolean saveDeviceDetailInfo(String str, String str2) {
        return TmpStorage.getInstance().saveDevDetailInfo(str, str2);
    }

    public void startNotifyMonitor() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "startNotifyMonitor");
        this.mRecNotifyTask = new d(com.aliyun.alink.linksdk.tmp.connect.a.a(), null);
        new com.aliyun.alink.linksdk.tmp.device.a.c().b(this.mRecNotifyTask).a();
    }

    public void stopDiscoverDevices() {
        this.lastStartDiscoverDeviceTimeOut = 0L;
        this.scanDeviceListener = null;
        this.scanDeviceFilter = null;
        a aVar = this.mDiscoveryTask;
        if (aVar != null) {
            aVar.b(false);
            this.mDiscoveryTask = null;
        }
    }

    public void stopDiscoverDevicesInfinite() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "stopDiscoverDevicesInfinite");
        if (timer == null) {
            return;
        }
        a aVar = this.mInfiniteDiscoveryTask;
        if (aVar != null) {
            aVar.b(false);
        }
        timer.cancel();
        timer = null;
    }

    public void stopNotifyMonitor() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "stopNotifyMonitor");
        d dVar = this.mRecNotifyTask;
        if (dVar != null) {
            dVar.b();
        }
    }

    public void updateDeviceInfo(String str, String str2, String str3, String str4) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "updateDeviceInfo oldPk:" + str + " oldDn:" + str2 + " produceKey:" + str3 + " deviceName:" + str4);
        String strCombineStr = TextHelper.combineStr(str, str2);
        DeviceBasicData deviceBasicData = getDeviceBasicData(strCombineStr);
        if (deviceBasicData == null) {
            ALog.e(TAG, "updateDeviceInfo basicData empty");
            return;
        }
        DeviceBasicData deviceBasicData2 = getDeviceBasicData(TextHelper.combineStr(str3, str4));
        if (deviceBasicData2 != null) {
            deviceBasicData2.localDiscoveryType |= deviceBasicData.localDiscoveryType;
            deviceBasicData2.mac = deviceBasicData.mac;
            addDeviceBasicData(deviceBasicData2);
        } else {
            DeviceBasicData deviceBasicData3 = new DeviceBasicData(deviceBasicData);
            deviceBasicData3.productKey = str3;
            deviceBasicData3.deviceName = str4;
            deviceBasicData3.isPluginFound = false;
            addDeviceBasicData(deviceBasicData3);
        }
        PluginMgr.getInstance().updateDiscoveryInfo(strCombineStr, str3, str4);
        DefaultDevInfoTrans.getInstance().updatePubDevInfo(strCombineStr, str3, str4, "com.aliyun.iot.breeze.lpbs");
        TmpStorage.getInstance().saveDnToMac(str4, str2);
        TmpStorage.getInstance().saveMacToDn(str2, str4);
    }

    private DeviceManager() {
        this.lastStartDiscoverDeviceTimeOut = 0L;
        this.scanDeviceFilter = null;
        this.scanDeviceListener = null;
        this.mDeviceBasicDataList = new ConcurrentHashMap();
    }

    public void discoverDevices(Object obj, boolean z2, long j2, Object obj2, IDiscoveryFilter iDiscoveryFilter, IDevListener iDevListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        DiscoveryConfig discoveryConfig = new DiscoveryConfig();
        if (obj2 != null && (obj2 instanceof CloudLcaRequestParams)) {
            discoveryConfig.cloudLcaRequestParams = (CloudLcaRequestParams) obj2;
        }
        discoverDevices(obj, z2, j2, discoveryConfig, iDiscoveryFilter, iDevListener);
    }

    public void discoverDevices(Object obj, boolean z2, long j2, IDiscoveryFilter iDiscoveryFilter, IDevListener iDevListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        discoverDevices(obj, z2, j2, (DiscoveryConfig) null, iDiscoveryFilter, iDevListener);
    }

    public void discoverDevices(Object obj, boolean z2, long j2, IDevListener iDevListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        discoverDevices(obj, z2, j2, null, iDevListener);
    }

    public void discoverDevices(Object obj, long j2, IDevListener iDevListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        discoverDevices(obj, false, j2, iDevListener);
    }
}
