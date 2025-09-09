package com.aliyun.alink.business.devicecenter.discover;

import android.text.TextUtils;
import com.aliyun.alink.business.devicecenter.api.add.DeviceInfo;
import com.aliyun.alink.business.devicecenter.api.discovery.DiscoveryType;
import com.aliyun.alink.business.devicecenter.api.discovery.IDeviceDiscoveryListener;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.aliyun.alink.business.devicecenter.cache.CacheCenter;
import com.aliyun.alink.business.devicecenter.cache.CacheType;
import com.aliyun.alink.business.devicecenter.cache.EnrolleeMeshDeviceCacheModel;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.utils.DeviceInfoUtils;
import com.aliyun.alink.linksdk.tools.ThreadTools;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class DiscoverListenerAdapter implements IDeviceDiscoveryListener {

    /* renamed from: a, reason: collision with root package name */
    public IDeviceDiscoveryListener f10413a;

    /* renamed from: b, reason: collision with root package name */
    public final Object f10414b = new Object();

    /* renamed from: c, reason: collision with root package name */
    public ArrayList<DeviceInfo> f10415c;

    public DiscoverListenerAdapter(IDeviceDiscoveryListener iDeviceDiscoveryListener) {
        this.f10415c = null;
        this.f10413a = iDeviceDiscoveryListener;
        this.f10415c = new ArrayList<>();
    }

    public void clear() {
        ArrayList<DeviceInfo> arrayList = this.f10415c;
        if (arrayList != null) {
            arrayList.clear();
        }
        if (CacheCenter.getInstance().getDiscoveredMeshDevices() != null) {
            CacheCenter.getInstance().getDiscoveredMeshDevices().clear();
        }
    }

    public void destroy() {
        clear();
        this.f10413a = null;
        this.f10415c = null;
    }

    public ArrayList<DeviceInfo> getLanDevices() {
        return this.f10415c;
    }

    @Override // com.aliyun.alink.business.devicecenter.api.discovery.IDeviceDiscoveryListener
    public void onDeviceFound(final DiscoveryType discoveryType, List<DeviceInfo> list) {
        final List<DeviceInfo> listA = a(discoveryType, list);
        if (listA == null || listA.size() < 1) {
            return;
        }
        ALog.d("DiscoverListenerAdapter", "onDeviceFound type=" + discoveryType + ", to app deviceInfoList=" + listA);
        ThreadTools.runOnUiThread(new Runnable() { // from class: com.aliyun.alink.business.devicecenter.discover.DiscoverListenerAdapter.1
            @Override // java.lang.Runnable
            public void run() {
                if (DiscoverListenerAdapter.this.f10413a != null) {
                    DiscoverListenerAdapter.this.f10413a.onDeviceFound(discoveryType, listA);
                }
            }
        });
    }

    public final List<DeviceInfo> a(DiscoveryType discoveryType, List<DeviceInfo> list) {
        ArrayList<DeviceInfo> arrayList;
        if (list == null || list.size() < 1) {
            ALog.d("DiscoverListenerAdapter", "deviceInfoList empty, return.");
            return null;
        }
        if (this.f10415c == null) {
            ALog.w("DiscoverListenerAdapter", "discover stopped, return.");
            return null;
        }
        ArrayList arrayList2 = new ArrayList();
        if (discoveryType == DiscoveryType.CLOUD_BLE_MESH_DEVICE || discoveryType == DiscoveryType.APP_FOUND_BLE_MESH_DEVICE || discoveryType == DiscoveryType.APP_FOUND_COMBO_MESH_DEVICE) {
            for (DeviceInfo deviceInfo : list) {
                String meshDeviceUniqueIDByDeviceInfo = DeviceInfoUtils.getMeshDeviceUniqueIDByDeviceInfo(deviceInfo);
                if (!CacheCenter.getInstance().getDiscoveredMeshDevices().contains(meshDeviceUniqueIDByDeviceInfo)) {
                    CacheCenter.getInstance().getDiscoveredMeshDevices().add(meshDeviceUniqueIDByDeviceInfo);
                    arrayList2.add(deviceInfo);
                    this.f10415c.add(deviceInfo);
                    CacheCenter.getInstance().updateCache(CacheType.BLE_MESH_DISCOVERED_DEVICE, deviceInfo.getExtraDeviceInfo(AlinkConstants.KEY_CACHE_BLE_DISCOVERED_DEVICE));
                }
            }
            return arrayList2;
        }
        for (int i2 = 0; i2 < list.size(); i2++) {
            try {
                DeviceInfo deviceInfo2 = list.get(i2);
                if (deviceInfo2 == null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("deviceInfo null, continue. i=");
                    sb.append(i2);
                    ALog.w("DiscoverListenerAdapter", sb.toString());
                } else if (!deviceInfo2.isValid() && TextUtils.isEmpty(String.valueOf(deviceInfo2.getExtraDeviceInfo(AlinkConstants.KEY_APP_SSID)))) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("deviceInfo invalid, continue. i=");
                    sb2.append(i2);
                    ALog.w("DiscoverListenerAdapter", sb2.toString());
                } else if (this.f10415c != null) {
                    synchronized (this.f10414b) {
                        try {
                            if (this.f10415c.contains(deviceInfo2)) {
                                int size = this.f10415c.size() - 1;
                                while (true) {
                                    if (size <= -1 || (arrayList = this.f10415c) == null) {
                                        break;
                                    }
                                    DeviceInfo deviceInfo3 = arrayList.get(size);
                                    if (!deviceInfo2.equals(deviceInfo3)) {
                                        size--;
                                    } else if (!TextUtils.isEmpty(deviceInfo2.token) || !TextUtils.isEmpty(deviceInfo3.token)) {
                                        if (!TextUtils.isEmpty(deviceInfo2.token) && !TextUtils.isEmpty(deviceInfo3.token)) {
                                            deviceInfo3.tag = Long.valueOf(System.currentTimeMillis());
                                        } else if ((TextUtils.isEmpty(deviceInfo2.token) || !AlinkConstants.DEVICE_TYPE_COMBO_SUBTYPE_4.equals(deviceInfo3.devType)) && ((TextUtils.isEmpty(deviceInfo3.token) || !AlinkConstants.DEVICE_TYPE_COMBO_SUBTYPE_4.equals(deviceInfo2.devType)) && ((!TextUtils.isEmpty(deviceInfo2.token) || a(deviceInfo3.tag)) && !deviceInfo2.isSameApWithNoPk(deviceInfo3)))) {
                                            if (!TextUtils.isEmpty(deviceInfo2.token)) {
                                                deviceInfo2.tag = Long.valueOf(System.currentTimeMillis());
                                            }
                                            StringBuilder sb3 = new StringBuilder();
                                            sb3.append("replace callback device ");
                                            sb3.append(deviceInfo3);
                                            sb3.append(", to ");
                                            sb3.append(deviceInfo2);
                                            ALog.d("DiscoverListenerAdapter", sb3.toString());
                                            this.f10415c.remove(deviceInfo3);
                                            this.f10415c.add(deviceInfo2);
                                            arrayList2.add(deviceInfo2.copy());
                                            if (discoveryType == DiscoveryType.APP_FOUND_BLE_MESH_DEVICE && (deviceInfo2.getExtraDeviceInfo(AlinkConstants.KEY_CACHE_BLE_DISCOVERED_DEVICE) instanceof EnrolleeMeshDeviceCacheModel)) {
                                                CacheCenter.getInstance().updateCache(CacheType.BLE_MESH_DISCOVERED_DEVICE, deviceInfo2.getExtraDeviceInfo(AlinkConstants.KEY_CACHE_BLE_DISCOVERED_DEVICE));
                                            }
                                        }
                                    }
                                }
                            } else {
                                deviceInfo2.tag = Long.valueOf(System.currentTimeMillis());
                                this.f10415c.add(deviceInfo2);
                                arrayList2.add(deviceInfo2.copy());
                                if (discoveryType == DiscoveryType.APP_FOUND_BLE_MESH_DEVICE && (deviceInfo2.getExtraDeviceInfo(AlinkConstants.KEY_CACHE_BLE_DISCOVERED_DEVICE) instanceof EnrolleeMeshDeviceCacheModel)) {
                                    CacheCenter.getInstance().updateCache(CacheType.BLE_MESH_DISCOVERED_DEVICE, deviceInfo2.getExtraDeviceInfo(AlinkConstants.KEY_CACHE_BLE_DISCOVERED_DEVICE));
                                }
                            }
                        } finally {
                        }
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return arrayList2;
    }

    public final boolean a(Object obj) {
        if (obj instanceof Long) {
            return System.currentTimeMillis() > ((Long) obj).longValue() + 5000;
        }
        return false;
    }
}
