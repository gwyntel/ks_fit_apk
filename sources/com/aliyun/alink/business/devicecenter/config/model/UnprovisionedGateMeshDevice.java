package com.aliyun.alink.business.devicecenter.config.model;

import android.text.TextUtils;
import android.util.Log;
import com.aliyun.alink.business.devicecenter.api.add.DeviceInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class UnprovisionedGateMeshDevice {

    /* renamed from: a, reason: collision with root package name */
    public List<DeviceInfo> f10398a;

    /* renamed from: b, reason: collision with root package name */
    public Map<String, Boolean> f10399b = new HashMap();

    public boolean addDeviceInfo(DeviceInfo deviceInfo) {
        if (this.f10398a == null) {
            this.f10398a = new ArrayList();
        }
        if (this.f10398a.size() + 1 > 201) {
            return false;
        }
        this.f10398a.add(deviceInfo);
        return true;
    }

    public void addProvisionResult(String str, boolean z2) {
        Log.d("UnprovisionedGateMesh", "addProvisionResult() called with: iotId = [" + str + "], success = [" + z2 + "]");
        this.f10399b.put(str, Boolean.valueOf(z2));
    }

    public boolean containerDevice(String str) {
        List<DeviceInfo> list;
        if (!TextUtils.isEmpty(str) && (list = this.f10398a) != null && list.size() > 0) {
            Iterator<DeviceInfo> it = this.f10398a.iterator();
            while (it.hasNext()) {
                if (str.equals(it.next().iotId)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean endProvision() {
        List<DeviceInfo> list = this.f10398a;
        return list == null || list.size() <= 0 || this.f10399b.size() == this.f10398a.size();
    }

    public List<DeviceInfo> getDeviceInfo() {
        return this.f10398a;
    }

    public DeviceInfo getDeviceInfoFromIotId(String str) {
        List<DeviceInfo> list;
        if (!TextUtils.isEmpty(str) && (list = this.f10398a) != null && list.size() > 0) {
            for (DeviceInfo deviceInfo : this.f10398a) {
                if (str.equals(deviceInfo.iotId)) {
                    return deviceInfo;
                }
            }
        }
        return null;
    }

    public List<DeviceInfo> getUnProvisionDeviceList() {
        List<DeviceInfo> list = this.f10398a;
        if (list == null || list.size() <= 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (DeviceInfo deviceInfo : this.f10398a) {
            if (!this.f10399b.containsKey(deviceInfo.iotId)) {
                arrayList.add(deviceInfo);
            }
        }
        return arrayList;
    }

    public void setDeviceInfo(List<DeviceInfo> list) {
        this.f10398a = list;
    }

    public DeviceInfo getDeviceInfo(String str) {
        List<DeviceInfo> list;
        if (!TextUtils.isEmpty(str) && (list = this.f10398a) != null && list.size() > 0) {
            for (DeviceInfo deviceInfo : this.f10398a) {
                if (str.equals(deviceInfo.deviceId)) {
                    return deviceInfo;
                }
            }
        }
        return null;
    }
}
