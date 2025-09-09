package com.aliyun.alink.business.devicecenter.cache;

import android.text.TextUtils;
import android.util.LruCache;
import com.alibaba.ailabs.iot.bluetoothlesdk.SmartSpeakerBLEDevice;
import com.aliyun.alink.business.devicecenter.api.add.DeviceInfo;
import com.aliyun.alink.business.devicecenter.api.add.ICacheModel;
import com.aliyun.alink.business.devicecenter.biz.SAPProvisionedICacheModel;
import com.aliyun.alink.business.devicecenter.log.ALog;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes2.dex */
public class CacheCenter {

    /* renamed from: a, reason: collision with root package name */
    public Set<String> f10210a;

    /* renamed from: b, reason: collision with root package name */
    public Set<String> f10211b;

    /* renamed from: c, reason: collision with root package name */
    public ConcurrentHashMap<String, LruCache<String, Object>> f10212c;

    private static class SingletonHolder {

        /* renamed from: a, reason: collision with root package name */
        public static final CacheCenter f10213a = new CacheCenter();
    }

    public static CacheCenter getInstance() {
        return SingletonHolder.f10213a;
    }

    public void clearCache() {
        ALog.d("CacheCenter", "clearCache() called ");
        for (Map.Entry<String, LruCache<String, Object>> entry : this.f10212c.entrySet()) {
            if (entry != null && entry.getValue() != null) {
                entry.getValue().evictAll();
            }
        }
    }

    public List getCachedModel(CacheType cacheType, String... strArr) {
        ALog.d("CacheCenter", "getCachedModel() called with: keyType = [" + cacheType + "], conditions = [" + strArr + "]");
        if (cacheType == null || TextUtils.isEmpty(cacheType.getCacheName())) {
            ALog.w("CacheCenter", "keyType or keyType name is null.");
            return null;
        }
        LruCache<String, Object> lruCache = this.f10212c.get(cacheType.getCacheName());
        if (lruCache == null) {
            ALog.w("CacheCenter", "itemLruCache is null. keyType = " + cacheType);
            return null;
        }
        Map<String, Object> mapSnapshot = lruCache.snapshot();
        if (mapSnapshot == null) {
            ALog.w("CacheCenter", "itemLruCache snapshot is null. keyType = " + cacheType);
            return null;
        }
        CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList();
        for (Map.Entry<String, Object> entry : mapSnapshot.entrySet()) {
            if (entry != null && entry.getKey() != null && entry.getValue() != null) {
                String[] strArrSplit = entry.getKey().split("&&");
                ALog.d("CacheCenter", "key = " + entry.getKey());
                if (cacheType == CacheType.BATCH_CLOUD_ENROLLEE || cacheType == CacheType.CLOUD_ENROLLEE) {
                    if (strArrSplit.length == 4 && (TextUtils.isEmpty(strArr[2]) || strArr[2].equals(strArrSplit[2]))) {
                        if (TextUtils.isEmpty(strArr[3]) || strArr[3].equals(strArrSplit[3])) {
                            if (TextUtils.isEmpty(strArr[0]) || strArr[0].equals(strArrSplit[0])) {
                                if (TextUtils.isEmpty(strArr[1]) || strArr[1].equals(strArrSplit[1])) {
                                    copyOnWriteArrayList.add(entry.getValue());
                                }
                            }
                        }
                    }
                } else if (cacheType == CacheType.DEVICE_NOTIFY_TOKEN) {
                    if (strArrSplit.length == 2 && !TextUtils.isEmpty(strArr[0]) && !TextUtils.isEmpty(strArr[1]) && strArr[0].equals(strArrSplit[0]) && strArr[1].equals(strArrSplit[1])) {
                        if (entry.getValue() instanceof DeviceInfo) {
                            DeviceInfo deviceInfo = (DeviceInfo) entry.getValue();
                            if (TextUtils.isEmpty(deviceInfo.token)) {
                                lruCache.remove(entry.getKey());
                            } else {
                                try {
                                    if (Long.valueOf(deviceInfo.remainTime).longValue() < System.currentTimeMillis()) {
                                        lruCache.remove(entry.getKey());
                                    }
                                } catch (Exception unused) {
                                }
                            }
                        } else {
                            lruCache.remove(entry.getKey());
                        }
                    }
                } else if (cacheType != CacheType.APP_SEND_TOKEN) {
                    if (cacheType == CacheType.SAP_PROVISIONED_SSID) {
                        if (!(entry.getValue() instanceof SAPProvisionedICacheModel)) {
                            lruCache.remove(entry.getKey());
                        } else if (entry.getValue() == null) {
                            lruCache.remove(entry.getKey());
                        } else if (((SAPProvisionedICacheModel) entry.getValue()).aliveTime < System.currentTimeMillis()) {
                            ALog.d("CacheCenter", "entry.getKey() cache expired, entry=" + entry);
                            lruCache.remove(entry.getKey());
                        } else if (!TextUtils.isEmpty(strArr[0]) && strArr[0].equals(entry.getKey())) {
                            if (TextUtils.isEmpty(strArr[1])) {
                                copyOnWriteArrayList.add(entry.getValue());
                            } else if (strArr[1].equals(((SAPProvisionedICacheModel) entry.getValue()).apBssid)) {
                                copyOnWriteArrayList.add(entry.getValue());
                            }
                        }
                    }
                    if (cacheType == CacheType.BLE_DISCOVERED_DEVICE || cacheType == CacheType.BLE_MESH_DISCOVERED_DEVICE) {
                        if (entry.getValue() == null) {
                            lruCache.remove(entry.getKey());
                        } else if (!TextUtils.isEmpty(strArr[0]) && strArr[0].equalsIgnoreCase(entry.getKey())) {
                            copyOnWriteArrayList.add(entry.getValue());
                        }
                    }
                    if (CacheType.GENIE_SOUND_BOX_DEVICE.equals(cacheType) && entry.getValue() != null && (entry.getValue() instanceof SmartSpeakerBLEDevice)) {
                        SmartSpeakerBLEDevice smartSpeakerBLEDevice = (SmartSpeakerBLEDevice) entry.getValue();
                        if (strArr == null || strArr.length == 0 || TextUtils.isEmpty(strArr[0])) {
                            copyOnWriteArrayList.add(smartSpeakerBLEDevice);
                        } else {
                            if (smartSpeakerBLEDevice.getWifiMacAddress().equalsIgnoreCase(strArr[0])) {
                                copyOnWriteArrayList.add(smartSpeakerBLEDevice);
                            }
                        }
                    }
                } else if (strArrSplit.length == 2 && !TextUtils.isEmpty(strArr[0]) && !TextUtils.isEmpty(strArr[1]) && strArr[0].equals(strArrSplit[0]) && strArr[1].equals(strArrSplit[1])) {
                    if (entry.getValue() instanceof DeviceInfoICacheModel) {
                        lruCache.remove(entry.getKey());
                        copyOnWriteArrayList.add(entry.getValue());
                    } else {
                        lruCache.remove(entry.getKey());
                    }
                }
            }
        }
        return copyOnWriteArrayList;
    }

    public Set<String> getDiscoveredDevices() {
        return this.f10210a;
    }

    public Set<String> getDiscoveredMeshDevices() {
        return this.f10211b;
    }

    public void updateCache(CacheType cacheType, Object obj) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(obj);
        updateCache(cacheType, (List) arrayList);
    }

    public CacheCenter() {
        this.f10210a = new HashSet();
        this.f10211b = new HashSet();
        this.f10212c = null;
        this.f10212c = new ConcurrentHashMap<>();
        for (CacheType cacheType : CacheType.values()) {
            this.f10212c.put(cacheType.getCacheName(), cacheType.getLruCache());
        }
    }

    public void updateCache(CacheType cacheType, List list) {
        ConcurrentHashMap<String, LruCache<String, Object>> concurrentHashMap;
        LruCache lruCache;
        ICacheModel iCacheModel;
        DeviceInfo deviceInfo;
        if (cacheType == null || (concurrentHashMap = this.f10212c) == null || !concurrentHashMap.containsKey(cacheType.getCacheName()) || (lruCache = this.f10212c.get(cacheType.getCacheName())) == null) {
            return;
        }
        if (list != null && !list.isEmpty()) {
            if (CacheType.DEVICE_NOTIFY_TOKEN == cacheType) {
                int size = list.size();
                for (int i2 = 0; i2 < size; i2++) {
                    if ((list.get(i2) instanceof DeviceInfo) && (deviceInfo = (DeviceInfo) list.get(i2)) != null && !TextUtils.isEmpty(deviceInfo.productKey) && !TextUtils.isEmpty(deviceInfo.deviceName) && TextUtils.isDigitsOnly(deviceInfo.remainTime)) {
                        try {
                            deviceInfo.remainTime = String.valueOf(Long.valueOf(deviceInfo.remainTime).longValue() + System.currentTimeMillis());
                            lruCache.put(deviceInfo.getKey(), deviceInfo);
                            return;
                        } catch (Exception unused) {
                            continue;
                        }
                    }
                }
            }
            int size2 = list.size();
            for (int i3 = 0; i3 < size2; i3++) {
                if ((list.get(i3) instanceof ICacheModel) && (iCacheModel = (ICacheModel) list.get(i3)) != null && iCacheModel.isValid()) {
                    lruCache.put(iCacheModel.getKey(), iCacheModel);
                }
            }
            return;
        }
        lruCache.evictAll();
    }

    public void clearCache(CacheType cacheType) {
        ALog.d("CacheCenter", "clearCache() called with: cacheType = [" + cacheType + "]");
        try {
            if (this.f10212c.get(cacheType.getCacheName()) != null) {
                this.f10212c.get(cacheType.getCacheName()).evictAll();
            }
        } catch (Exception unused) {
        }
    }
}
