package com.aliyun.alink.linksdk.tmp.service;

import com.alibaba.fastjson.JSON;
import com.aliyun.alink.linksdk.tmp.api.DeviceBasicData;
import com.aliyun.alink.linksdk.tmp.api.DeviceManager;
import com.aliyun.alink.linksdk.tmp.api.IDiscoveryFilter;
import com.aliyun.alink.linksdk.tmp.data.cloud.CloudLcaRequestParams;
import com.aliyun.alink.linksdk.tmp.device.panel.data.BoneDisFilter;
import com.aliyun.alink.linksdk.tmp.listener.IDevListener;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class DevDiscoveryService {
    private static final String TAG = "[Tmp]DevDiscoveryService";

    public static void startDiscoveryWithFilter(JSONObject jSONObject, IDevListener iDevListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        final BoneDisFilter boneDisFilter;
        ALog.d(TAG, "startDiscoveryWithFilter params:" + jSONObject + " listener:" + iDevListener);
        if (iDevListener == null) {
            ALog.e(TAG, "startDiscoveryWithFilter params listener null");
            return;
        }
        if (jSONObject == null) {
            boneDisFilter = null;
        } else {
            try {
                boneDisFilter = (BoneDisFilter) JSON.parseObject(String.valueOf(jSONObject), BoneDisFilter.class);
            } catch (Exception e2) {
                ALog.e(TAG, "startDiscoveryWithFilter error:" + e2.toString());
                return;
            }
        }
        CloudLcaRequestParams cloudLcaRequestParams = new CloudLcaRequestParams();
        cloudLcaRequestParams.groupId = boneDisFilter.groupId;
        cloudLcaRequestParams.gatewayIotId = boneDisFilter.gatewayIotId;
        DeviceManager.getInstance().discoverDevices((Object) null, false, 5000L, (Object) cloudLcaRequestParams, new IDiscoveryFilter() { // from class: com.aliyun.alink.linksdk.tmp.service.DevDiscoveryService.1
            @Override // com.aliyun.alink.linksdk.tmp.api.IDiscoveryFilter
            public boolean doFilter(DeviceBasicData deviceBasicData) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                BoneDisFilter boneDisFilter2 = boneDisFilter;
                if (boneDisFilter2 != null) {
                    return boneDisFilter2.doFilter(deviceBasicData);
                }
                ALog.d(DevDiscoveryService.TAG, "boneDisFilter null return true");
                return true;
            }
        }, iDevListener);
    }

    public static void stopDiscovery(JSONObject jSONObject, IDevListener iDevListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "stopDiscovery params:" + jSONObject + " listener:" + iDevListener);
        if (iDevListener == null) {
            ALog.e(TAG, "stopDiscovery params listener null");
        } else {
            DeviceManager.getInstance().stopDiscoverDevices();
        }
    }
}
