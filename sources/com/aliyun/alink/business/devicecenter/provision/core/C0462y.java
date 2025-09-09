package com.aliyun.alink.business.devicecenter.provision.core;

import android.text.TextUtils;
import com.alibaba.ailabs.iot.mesh.TgScanManager;
import com.alibaba.ailabs.iot.mesh.UnprovisionedBluetoothMeshDevice;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.alink.business.devicecenter.api.add.DeviceInfo;
import com.aliyun.alink.business.devicecenter.api.add.LinkType;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.aliyun.alink.business.devicecenter.base.AlinkHelper;
import com.aliyun.alink.business.devicecenter.biz.MeshDiscoverCallback;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.provision.core.mesh.AppMeshStrategy;
import java.util.ArrayList;

/* renamed from: com.aliyun.alink.business.devicecenter.provision.core.y, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class C0462y implements MeshDiscoverCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ UnprovisionedBluetoothMeshDevice f10600a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ AppMeshStrategy f10601b;

    public C0462y(AppMeshStrategy appMeshStrategy, UnprovisionedBluetoothMeshDevice unprovisionedBluetoothMeshDevice) {
        this.f10601b = appMeshStrategy;
        this.f10600a = unprovisionedBluetoothMeshDevice;
    }

    @Override // com.aliyun.alink.business.devicecenter.biz.MeshDiscoverCallback
    public void onFailure(String str) {
        ALog.e(AppMeshStrategy.TAG, "Cloud Filter, error: " + str);
        this.f10601b.isScanStartedFirstQuery.set(false);
        this.f10601b.mInFilterProcess.set(false);
    }

    @Override // com.aliyun.alink.business.devicecenter.biz.MeshDiscoverCallback
    public void onSuccess(JSONArray jSONArray) {
        try {
            this.f10601b.isScanStartedFirstQuery.set(false);
        } catch (Exception e2) {
            ALog.w(AppMeshStrategy.TAG, "getDiscoveredMeshDevice parse exception. " + e2);
        }
        if (jSONArray != null && !jSONArray.isEmpty()) {
            TgScanManager.getInstance().stopGetRemoteSpecifiedPIDUnprovisionedSigMeshDeviceWithScan();
            this.f10601b.unprovisionedBluetoothMeshDevice = this.f10600a;
            this.f10601b.unprovisionedDeviceFound.set(true);
            ArrayList arrayList = new ArrayList();
            String str = AppMeshStrategy.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("Cloud Filter, response size: ");
            sb.append(jSONArray.size());
            sb.append(", chain: ");
            sb.append(this.f10601b);
            ALog.d(str, sb.toString());
            for (int i2 = 0; i2 < jSONArray.size(); i2++) {
                JSONObject jSONObject = jSONArray.getJSONObject(i2);
                if (jSONObject != null) {
                    this.f10601b.mDeviceInfo = new DeviceInfo();
                    this.f10601b.mDeviceInfo.deviceName = jSONObject.getString("deviceName");
                    this.f10601b.mDeviceInfo.productKey = jSONObject.getString("productKey");
                    this.f10601b.mDeviceInfo.productId = jSONObject.getString(AlinkConstants.KEY_PRODUCT_ID);
                    this.f10601b.mDeviceInfo.mac = jSONObject.getString(AlinkConstants.KEY_MAC);
                    this.f10601b.mDeviceInfo.deviceId = jSONObject.getString(AlinkConstants.KEY_SUB_DEVICE_ID);
                    this.f10601b.mDeviceInfo.linkType = LinkType.ALI_APP_MESH.getName();
                    this.f10601b.mDeviceInfo.authFlag = jSONObject.getBooleanValue(AlinkConstants.KEY_AUTH_FLAG);
                    this.f10601b.mDeviceInfo.confirmCloud = jSONObject.getString(AlinkConstants.KEY_CONFIRM_CLOUD);
                    this.f10601b.mDeviceInfo.subDeviceId = jSONObject.getString(AlinkConstants.KEY_SUB_DEVICE_ID);
                    if (this.f10601b.mDeviceInfo.authFlag) {
                        this.f10601b.mDeviceInfo.random = jSONObject.getString(AlinkConstants.KEY_RANDOM);
                        this.f10601b.mDeviceInfo.authDevice = jSONObject.getString(AlinkConstants.KEY_AUTH_DEVICE);
                    }
                    if (!TextUtils.isEmpty(this.f10601b.mDeviceInfo.mac) && !this.f10601b.mDeviceInfo.mac.contains(":")) {
                        this.f10601b.mDeviceInfo.mac = AlinkHelper.getMacFromSimpleMac(this.f10601b.mDeviceInfo.mac);
                    }
                    arrayList.add(this.f10601b.mDeviceInfo);
                    this.f10601b.startConcurrentAddDevice(arrayList);
                }
            }
            this.f10601b.mInFilterProcess.set(false);
            return;
        }
        this.f10601b.mInFilterProcess.set(false);
    }
}
