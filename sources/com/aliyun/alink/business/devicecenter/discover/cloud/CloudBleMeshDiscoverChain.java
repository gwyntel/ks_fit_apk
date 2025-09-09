package com.aliyun.alink.business.devicecenter.discover.cloud;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.alink.business.devicecenter.api.add.DeviceInfo;
import com.aliyun.alink.business.devicecenter.api.discovery.DiscoveryType;
import com.aliyun.alink.business.devicecenter.api.discovery.IDeviceDiscoveryListener;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.aliyun.alink.business.devicecenter.base.AlinkHelper;
import com.aliyun.alink.business.devicecenter.biz.MeshDiscoverCallback;
import com.aliyun.alink.business.devicecenter.biz.ProvisionRepositoryV2;
import com.aliyun.alink.business.devicecenter.biz.model.response.MeshDeviceDiscoveryTriggerResponse;
import com.aliyun.alink.business.devicecenter.discover.annotation.DeviceDiscovery;
import com.aliyun.alink.business.devicecenter.discover.base.DiscoverChainBase;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.utils.ThreadPool;
import com.aliyun.alink.linksdk.connectsdk.ApiCallBack;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@DeviceDiscovery(discoveryType = {DiscoveryType.CLOUD_BLE_MESH_DEVICE})
/* loaded from: classes2.dex */
public class CloudBleMeshDiscoverChain extends DiscoverChainBase {

    /* renamed from: c, reason: collision with root package name */
    public ScheduledFuture<?> f10432c;

    /* renamed from: d, reason: collision with root package name */
    public AtomicBoolean f10433d;

    public CloudBleMeshDiscoverChain(Context context) {
        super(context);
        this.f10432c = null;
        this.f10433d = new AtomicBoolean(false);
    }

    @Override // com.aliyun.alink.business.devicecenter.discover.base.AbilityReceiver
    public void onNotify(Intent intent) {
    }

    @Override // com.aliyun.alink.business.devicecenter.discover.IDiscoverChain
    public void startDiscover(final IDeviceDiscoveryListener iDeviceDiscoveryListener) {
        ALog.d("CloudBleMeshDiscoverCha", "startDiscover() called with: listener = [" + iDeviceDiscoveryListener + "]");
        this.f10433d.set(true);
        ProvisionRepositoryV2.meshDeviceDiscoveryTrigger(null, false, new ApiCallBack<MeshDeviceDiscoveryTriggerResponse>() { // from class: com.aliyun.alink.business.devicecenter.discover.cloud.CloudBleMeshDiscoverChain.1
            @Override // com.aliyun.alink.linksdk.connectsdk.BaseCallBack
            public void onFail(int i2, String str) {
            }

            @Override // com.aliyun.alink.linksdk.connectsdk.BaseCallBack
            public void onSuccess(MeshDeviceDiscoveryTriggerResponse meshDeviceDiscoveryTriggerResponse) {
                CloudBleMeshDiscoverChain.this.a(iDeviceDiscoveryListener);
            }
        });
    }

    @Override // com.aliyun.alink.business.devicecenter.discover.IDiscoverChain
    public void stopDiscover() {
        ALog.d("CloudBleMeshDiscoverCha", "stopDiscover() called");
        a();
        ProvisionRepositoryV2.meshDeviceDiscoveryTrigger(null, true, new ApiCallBack<MeshDeviceDiscoveryTriggerResponse>() { // from class: com.aliyun.alink.business.devicecenter.discover.cloud.CloudBleMeshDiscoverChain.3
            @Override // com.aliyun.alink.linksdk.connectsdk.BaseCallBack
            public void onFail(int i2, String str) {
            }

            @Override // com.aliyun.alink.linksdk.connectsdk.BaseCallBack
            public void onSuccess(MeshDeviceDiscoveryTriggerResponse meshDeviceDiscoveryTriggerResponse) {
            }
        });
    }

    public final void a(final IDeviceDiscoveryListener iDeviceDiscoveryListener) {
        a();
        this.f10432c = ThreadPool.scheduleAtFixedRate(new Runnable() { // from class: com.aliyun.alink.business.devicecenter.discover.cloud.CloudBleMeshDiscoverChain.2
            @Override // java.lang.Runnable
            public void run() {
                ProvisionRepositoryV2.getDiscoveredMeshDevice(null, 1, 100, Boolean.valueOf(CloudBleMeshDiscoverChain.this.f10433d.get()), null, new JSONArray(), new MeshDiscoverCallback() { // from class: com.aliyun.alink.business.devicecenter.discover.cloud.CloudBleMeshDiscoverChain.2.1
                    @Override // com.aliyun.alink.business.devicecenter.biz.MeshDiscoverCallback
                    public void onFailure(String str) {
                        CloudBleMeshDiscoverChain.this.f10433d.set(false);
                    }

                    @Override // com.aliyun.alink.business.devicecenter.biz.MeshDiscoverCallback
                    public void onSuccess(JSONArray jSONArray) {
                        if (jSONArray != null) {
                            try {
                                if (jSONArray.isEmpty()) {
                                    return;
                                }
                                CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList();
                                for (int i2 = 0; i2 < jSONArray.size(); i2++) {
                                    JSONObject jSONObject = jSONArray.getJSONObject(i2);
                                    if (jSONObject != null && "meshGw".equals(jSONObject.getString(AlinkConstants.KEY_DISCOVERED_SOURCE))) {
                                        DeviceInfo deviceInfo = new DeviceInfo();
                                        deviceInfo.productKey = jSONObject.getString("productKey");
                                        deviceInfo.deviceName = jSONObject.getString("deviceName");
                                        deviceInfo.iotId = jSONObject.getString("iotId");
                                        deviceInfo.deviceId = jSONObject.getString("deviceId");
                                        deviceInfo.productId = jSONObject.getString(AlinkConstants.KEY_PRODUCT_ID);
                                        String string = jSONObject.getString(AlinkConstants.KEY_MAC);
                                        deviceInfo.mac = string;
                                        if (!TextUtils.isEmpty(string) && !deviceInfo.mac.contains(":")) {
                                            deviceInfo.mac = AlinkHelper.getMacFromSimpleMac(deviceInfo.mac);
                                        }
                                        deviceInfo.regProductKey = jSONObject.getString(AlinkConstants.KEY_GATEWAY_PRODUCT_KEY);
                                        deviceInfo.regDeviceName = jSONObject.getString(AlinkConstants.KEY_GATEWAY_DEVICE_NAME);
                                        deviceInfo.regIotId = jSONObject.getString("gatewayIotId");
                                        deviceInfo.token = jSONObject.getString("token");
                                        deviceInfo.productName = jSONObject.getString(AlinkConstants.KEY_PRODUCT_NAME);
                                        deviceInfo.image = jSONObject.getString("image");
                                        deviceInfo.netType = jSONObject.getString("netType");
                                        deviceInfo.nodeType = jSONObject.getString(AlinkConstants.KEY_NODE_TYPE);
                                        deviceInfo.categoryKey = jSONObject.getString(AlinkConstants.KEY_CATEGORY_KEY);
                                        deviceInfo.categoryName = jSONObject.getString(AlinkConstants.KEY_CATEGORY_NAME);
                                        deviceInfo.categoryId = jSONObject.getString(AlinkConstants.KEY_CATEGORY_ID);
                                        copyOnWriteArrayList.add(deviceInfo);
                                    }
                                }
                                IDeviceDiscoveryListener iDeviceDiscoveryListener2 = iDeviceDiscoveryListener;
                                if (iDeviceDiscoveryListener2 != null) {
                                    iDeviceDiscoveryListener2.onDeviceFound(DiscoveryType.CLOUD_BLE_MESH_DEVICE, copyOnWriteArrayList);
                                }
                            } catch (Exception e2) {
                                ALog.w("CloudBleMeshDiscoverCha", "getDiscoveredMeshDevice parse exception. " + e2);
                            }
                        }
                    }
                });
            }
        }, 0L, 1500L, TimeUnit.MILLISECONDS);
    }

    public final void a() {
        try {
            ScheduledFuture<?> scheduledFuture = this.f10432c;
            if (scheduledFuture != null) {
                scheduledFuture.cancel(true);
                this.f10432c = null;
            }
        } catch (Exception unused) {
        }
    }
}
