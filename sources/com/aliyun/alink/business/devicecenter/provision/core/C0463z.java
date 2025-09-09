package com.aliyun.alink.business.devicecenter.provision.core;

import android.text.TextUtils;
import com.aliyun.alink.business.devicecenter.api.add.DeviceInfo;
import com.aliyun.alink.business.devicecenter.base.DCErrorCode;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.model.ProvisionSLBItem;
import com.aliyun.alink.business.devicecenter.provision.core.mesh.AppMeshStrategy;
import com.aliyun.alink.business.devicecenter.utils.DeviceInfoUtils;
import com.aliyun.alink.linksdk.connectsdk.ApiCallBack;
import java.util.List;
import java.util.Map;

/* renamed from: com.aliyun.alink.business.devicecenter.provision.core.z, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class C0463z extends ApiCallBack<List<ProvisionSLBItem>> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ Map f10602a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ AppMeshStrategy f10603b;

    public C0463z(AppMeshStrategy appMeshStrategy, Map map) {
        this.f10603b = appMeshStrategy;
        this.f10602a = map;
    }

    @Override // com.aliyun.alink.linksdk.connectsdk.BaseCallBack
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(List<ProvisionSLBItem> list) {
        ALog.i(AppMeshStrategy.TAG, "############### Provision SLB Strategy ###############");
        ALog.d(AppMeshStrategy.TAG, "SLB result size: " + list.size());
        for (ProvisionSLBItem provisionSLBItem : list) {
            if (TextUtils.isEmpty(provisionSLBItem.getMac())) {
                ALog.w(AppMeshStrategy.TAG, "Illegal SLB result for device: " + provisionSLBItem.getDeviceName() + ", mac address cannot be null");
            } else {
                DeviceInfo deviceInfo = (DeviceInfo) this.f10602a.get(DeviceInfoUtils.getMeshDeviceUniqueIDByMac(provisionSLBItem.getMac()));
                if (provisionSLBItem.getConfigurationInfo() != null && !TextUtils.isEmpty(provisionSLBItem.getConfirmCloud())) {
                    provisionSLBItem.getConfigurationInfo().setServerConfirmation(provisionSLBItem.getConfirmCloud());
                }
                if (deviceInfo == null || provisionSLBItem.getConfigurationInfo() == null) {
                    ALog.w(AppMeshStrategy.TAG, "Cannot find provision info for device: " + provisionSLBItem.getDeviceName());
                } else {
                    this.f10603b.unprovisionedBluetoothMeshDevice.getmUnprovisionedMeshNodeData().setConfigurationInfo(provisionSLBItem.getConfigurationInfo());
                    this.f10603b.startProvision();
                }
            }
        }
        ALog.i(AppMeshStrategy.TAG, "############### Provision SLB Strategy End ###############");
    }

    @Override // com.aliyun.alink.linksdk.connectsdk.BaseCallBack
    public void onFail(int i2, String str) {
        ALog.e(AppMeshStrategy.TAG, "provision SLB, error: " + str);
        this.f10603b.provisionErrorInfo = new DCErrorCode("SdkError", DCErrorCode.PF_SERVER_FAIL).setMsg("provision SLB, error: " + str).setSubcode(DCErrorCode.SUBCODE_SRE_RESPONSE_FAIL);
        this.f10603b.provisionResultCallback(null);
    }
}
