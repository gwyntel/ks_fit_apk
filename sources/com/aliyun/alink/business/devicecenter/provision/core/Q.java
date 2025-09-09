package com.aliyun.alink.business.devicecenter.provision.core;

import com.aliyun.alink.business.devicecenter.api.add.DeviceInfo;
import com.aliyun.alink.business.devicecenter.base.DCErrorCode;
import com.aliyun.alink.business.devicecenter.config.IDeviceInfoNotifyListener;
import com.aliyun.alink.business.devicecenter.config.model.DeviceReportTokenType;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.provision.core.mesh.GatewayMeshStrategy;
import com.aliyun.alink.business.devicecenter.utils.StringUtils;

/* loaded from: classes2.dex */
public class Q implements IDeviceInfoNotifyListener {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ GatewayMeshStrategy f10543a;

    public Q(GatewayMeshStrategy gatewayMeshStrategy) {
        this.f10543a = gatewayMeshStrategy;
    }

    @Override // com.aliyun.alink.business.devicecenter.config.IDeviceInfoNotifyListener
    public void onDeviceFound(DeviceInfo deviceInfo) {
        if (deviceInfo == null || this.f10543a.mConfigParams == null) {
            return;
        }
        if (this.f10543a.provisionHasStopped.get()) {
            ALog.d(GatewayMeshStrategy.TAG, "provision has stopped, return.");
            return;
        }
        if (!this.f10543a.waitForResult.get()) {
            ALog.d(GatewayMeshStrategy.TAG, "provision finished return.");
            return;
        }
        if (!StringUtils.isEqualString(deviceInfo.productKey, this.f10543a.mConfigParams.productKey) || !StringUtils.isEqualString(deviceInfo.deviceName, this.f10543a.mConfigParams.deviceName)) {
            ALog.i(GatewayMeshStrategy.TAG, "onDeviceFound Zero otherDeviceInfo=" + deviceInfo);
            return;
        }
        ALog.i(GatewayMeshStrategy.TAG, "onDeviceFound GatewayMesh Provision Success.");
        this.f10543a.updateCache(deviceInfo, DeviceReportTokenType.APP_TOKEN);
        this.f10543a.waitForResult.set(false);
        this.f10543a.stopBackupCheck();
        this.f10543a.provisionResultCallback(deviceInfo);
        this.f10543a.stopConfig();
    }

    @Override // com.aliyun.alink.business.devicecenter.config.IDeviceInfoNotifyListener
    public void onFailure(DCErrorCode dCErrorCode) {
    }
}
