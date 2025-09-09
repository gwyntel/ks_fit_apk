package com.aliyun.alink.business.devicecenter.provision.core;

import com.alibaba.ailabs.iot.mesh.MeshStatusCallback;
import com.alibaba.ailabs.iot.mesh.UnprovisionedBluetoothMeshDevice;
import com.alibaba.fastjson.JSON;
import com.aliyun.alink.business.devicecenter.api.add.DeviceInfo;
import com.aliyun.alink.business.devicecenter.api.add.ProvisionStatus;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.aliyun.alink.business.devicecenter.base.DCErrorCode;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.provision.core.mesh.ConcurrentAppMeshStrategy;

/* loaded from: classes2.dex */
public class G implements MeshStatusCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ ConcurrentAppMeshStrategy f10530a;

    public G(ConcurrentAppMeshStrategy concurrentAppMeshStrategy) {
        this.f10530a = concurrentAppMeshStrategy;
    }

    @Override // com.alibaba.ailabs.iot.mesh.StatusCallback
    public void onStatus(int i2, String str) {
        DeviceInfo deviceInfoBuildDeviceInfoViaMac;
        ALog.d(ConcurrentAppMeshStrategy.TAG, "mesh sdk onStatus() called with: statusCode = [" + i2 + "], statusMsg = [" + str + "]");
        this.f10530a.meshProvisionStatus = i2;
        this.f10530a.meshProvisionErrorMessage = str;
        if (this.f10530a.provisionHasStopped.get()) {
            ALog.d(ConcurrentAppMeshStrategy.TAG, "provisionHasStopped = true, return.");
            return;
        }
        if (i2 == 1) {
            ALog.d(ConcurrentAppMeshStrategy.TAG, "mesh sdk init success.");
            ConcurrentAppMeshStrategy concurrentAppMeshStrategy = this.f10530a;
            concurrentAppMeshStrategy.startMeshDeviceProvision(concurrentAppMeshStrategy.mSerialExecuteIndex = 0);
        }
        if (i2 == -1) {
            if (this.f10530a.unprovisionedBluetoothMeshDeviceList == null || this.f10530a.mSerialExecuteIndex >= this.f10530a.unprovisionedBluetoothMeshDeviceList.size()) {
                deviceInfoBuildDeviceInfoViaMac = null;
            } else {
                deviceInfoBuildDeviceInfoViaMac = this.f10530a.buildDeviceInfoViaMac(((UnprovisionedBluetoothMeshDevice) this.f10530a.unprovisionedBluetoothMeshDeviceList.get(this.f10530a.mSerialExecuteIndex)).getAddress());
            }
            this.f10530a.provisionErrorInfo = new DCErrorCode("SdkError", DCErrorCode.PF_PROVISION_APP_MESH_ERROR).setMsg("mesh sdk returned bind fail, state=" + i2 + ", msg=" + str).setSubcode(DCErrorCode.SUBCODE_MESH_SDK_INIT_EXCEPTION);
            if (deviceInfoBuildDeviceInfoViaMac != null) {
                this.f10530a.provisionErrorInfo.setExtra(deviceInfoBuildDeviceInfoViaMac);
            }
            this.f10530a.provisionResultCallback(null);
            this.f10530a.scheduleNextConfigTask();
            return;
        }
        if (i2 != -3) {
            if (i2 == 20) {
                String string = JSON.parseObject(str).getString(AlinkConstants.KEY_MAC);
                ProvisionStatus provisionStatus = ProvisionStatus.PROVISION_START_IN_CONCURRENT_MODE;
                provisionStatus.addExtraParams(AlinkConstants.KEY_CACHE_START_PROVISION_DEVICE_INFO, string);
                this.f10530a.provisionStatusCallback(provisionStatus);
                return;
            }
            return;
        }
        String lowerCase = JSON.parseObject(str).getString("device_mac_address").toLowerCase();
        this.f10530a.provisionErrorInfo = new DCErrorCode("SdkError", DCErrorCode.PF_PROVISION_APP_MESH_ERROR).setMsg("mesh sdk returned provision fail, state=" + i2 + ", msg=" + str).setSubcode(DCErrorCode.SUBCODE_MESH_SDK_PROVISION_EXCEPTION).setExtra(this.f10530a.buildDeviceInfoViaMac(lowerCase));
        this.f10530a.provisionResultCallback(null);
        this.f10530a.scheduleNextConfigTask();
    }
}
