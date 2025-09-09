package com.aliyun.alink.business.devicecenter.provision.other;

import com.aliyun.alink.business.devicecenter.api.add.DeviceInfo;
import com.aliyun.alink.business.devicecenter.base.DCErrorCode;
import com.aliyun.alink.business.devicecenter.config.IDeviceInfoNotifyListener;
import com.aliyun.alink.business.devicecenter.config.model.DeviceReportTokenType;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.log.PerformanceLog;
import com.aliyun.alink.business.devicecenter.provision.other.zero.AlinkZeroConfigStrategy;
import com.aliyun.alink.business.devicecenter.utils.StringUtils;

/* loaded from: classes2.dex */
public class n implements IDeviceInfoNotifyListener {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ AlinkZeroConfigStrategy f10618a;

    public n(AlinkZeroConfigStrategy alinkZeroConfigStrategy) {
        this.f10618a = alinkZeroConfigStrategy;
    }

    @Override // com.aliyun.alink.business.devicecenter.config.IDeviceInfoNotifyListener
    public void onDeviceFound(DeviceInfo deviceInfo) {
        if (deviceInfo == null || this.f10618a.mConfigParams == null) {
            return;
        }
        if (!this.f10618a.waitForResult.get()) {
            ALog.d(AlinkZeroConfigStrategy.TAG, "provision finished return.");
            return;
        }
        if (!StringUtils.isEqualString(deviceInfo.productKey, this.f10618a.mConfigParams.productKey) || !StringUtils.isEqualString(deviceInfo.deviceName, this.f10618a.mConfigParams.deviceName)) {
            ALog.i(AlinkZeroConfigStrategy.TAG, "onDeviceFound Zero otherDeviceInfo=" + deviceInfo);
            return;
        }
        ALog.i(AlinkZeroConfigStrategy.TAG, "onDeviceFound Zero Provision Success.");
        PerformanceLog.trace(AlinkZeroConfigStrategy.TAG, "connectap");
        this.f10618a.updateCache(deviceInfo, DeviceReportTokenType.APP_TOKEN);
        this.f10618a.waitForResult.set(false);
        this.f10618a.stopBackupCheck();
        this.f10618a.provisionResultCallback(deviceInfo);
        this.f10618a.stopConfig();
    }

    @Override // com.aliyun.alink.business.devicecenter.config.IDeviceInfoNotifyListener
    public void onFailure(DCErrorCode dCErrorCode) {
    }
}
