package com.aliyun.alink.business.devicecenter.provision.other;

import com.aliyun.alink.business.devicecenter.api.add.DeviceInfo;
import com.aliyun.alink.business.devicecenter.base.DCErrorCode;
import com.aliyun.alink.business.devicecenter.config.IDeviceInfoNotifyListener;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.log.PerformanceLog;
import com.aliyun.alink.business.devicecenter.provision.other.zero.BatchZeroConfigStrategy;
import com.aliyun.alink.business.devicecenter.utils.StringUtils;

/* loaded from: classes2.dex */
public class q implements IDeviceInfoNotifyListener {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ BatchZeroConfigStrategy f10621a;

    public q(BatchZeroConfigStrategy batchZeroConfigStrategy) {
        this.f10621a = batchZeroConfigStrategy;
    }

    @Override // com.aliyun.alink.business.devicecenter.config.IDeviceInfoNotifyListener
    public void onDeviceFound(DeviceInfo deviceInfo) {
        if (deviceInfo == null || this.f10621a.mConfigParams == null) {
            return;
        }
        if (!this.f10621a.waitForResult.get()) {
            ALog.d(BatchZeroConfigStrategy.TAG, "provision finished return.");
            return;
        }
        if (!StringUtils.isEqualString(deviceInfo.productKey, this.f10621a.mConfigParams.productKey)) {
            ALog.i(BatchZeroConfigStrategy.TAG, "onDeviceFound batch Zero otherDeviceInfo=" + deviceInfo);
            return;
        }
        ALog.i(BatchZeroConfigStrategy.TAG, "onDeviceFound batch Zero Provision Success.");
        if (StringUtils.isEqualString(deviceInfo.productKey, this.f10621a.mConfigParams.regProductKey) && StringUtils.isEqualString(deviceInfo.deviceName, this.f10621a.mConfigParams.regDeviceName)) {
            ALog.d(BatchZeroConfigStrategy.TAG, "onDeviceFound batch Zero, find provisioned device, return.");
            return;
        }
        PerformanceLog.trace(BatchZeroConfigStrategy.TAG, "connectap");
        BatchZeroConfigStrategy batchZeroConfigStrategy = this.f10621a;
        batchZeroConfigStrategy.updateCache(deviceInfo, batchZeroConfigStrategy.deviceReportTokenType);
        String str = deviceInfo.productKey + "&&" + deviceInfo.deviceName;
        deviceInfo.regProductKey = this.f10621a.mConfigParams.regProductKey;
        deviceInfo.regDeviceName = this.f10621a.mConfigParams.regDeviceName;
        if (this.f10621a.cacheCallbackMap.containsKey(str) || !this.f10621a.batchDeviceSuccess(deviceInfo.productKey, deviceInfo.deviceName)) {
            ALog.d(BatchZeroConfigStrategy.TAG, "cacheCallbackMap contains " + str);
        } else {
            ALog.d(BatchZeroConfigStrategy.TAG, "cacheCallbackMap not contain " + str);
            this.f10621a.cacheCallbackMap.put(str, Boolean.TRUE);
            this.f10621a.provisionResultCallback(deviceInfo);
        }
        if (this.f10621a.batchEnrolleeDeviceList == null || this.f10621a.cacheCallbackMap.size() != this.f10621a.batchEnrolleeDeviceList.size()) {
            return;
        }
        this.f10621a.waitForResult.set(false);
        this.f10621a.stopConfig();
    }

    @Override // com.aliyun.alink.business.devicecenter.config.IDeviceInfoNotifyListener
    public void onFailure(DCErrorCode dCErrorCode) {
    }
}
