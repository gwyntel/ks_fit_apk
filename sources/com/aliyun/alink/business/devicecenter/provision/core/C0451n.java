package com.aliyun.alink.business.devicecenter.provision.core;

import android.text.TextUtils;
import com.aliyun.alink.business.devicecenter.api.add.DeviceInfo;
import com.aliyun.alink.business.devicecenter.base.AlinkHelper;
import com.aliyun.alink.business.devicecenter.base.DCErrorCode;
import com.aliyun.alink.business.devicecenter.config.IDeviceInfoNotifyListener;
import com.aliyun.alink.business.devicecenter.config.model.DeviceReportTokenType;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.log.PerformanceLog;
import com.aliyun.alink.business.devicecenter.provision.core.broadcast.AlinkBroadcastConfigStrategy;
import com.aliyun.alink.business.devicecenter.utils.StringUtils;

/* renamed from: com.aliyun.alink.business.devicecenter.provision.core.n, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class C0451n implements IDeviceInfoNotifyListener {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ AlinkBroadcastConfigStrategy f10584a;

    public C0451n(AlinkBroadcastConfigStrategy alinkBroadcastConfigStrategy) {
        this.f10584a = alinkBroadcastConfigStrategy;
    }

    @Override // com.aliyun.alink.business.devicecenter.config.IDeviceInfoNotifyListener
    public void onDeviceFound(DeviceInfo deviceInfo) {
        if (deviceInfo == null || this.f10584a.mConfigParams == null) {
            return;
        }
        if (!this.f10584a.waitForResult.get()) {
            ALog.d(AlinkBroadcastConfigStrategy.TAG, "provision finished return.");
            return;
        }
        if (!StringUtils.isEqualString(deviceInfo.productKey, this.f10584a.mConfigParams.productKey) && (!TextUtils.isEmpty(this.f10584a.mConfigParams.productKey) || TextUtils.isEmpty(this.f10584a.mConfigParams.productEncryptKey))) {
            ALog.i(AlinkBroadcastConfigStrategy.TAG, "onDeviceFound BroadCast otherDeviceInfo=" + deviceInfo);
            return;
        }
        if (!AlinkHelper.isBatchBroadcast(this.f10584a.mConfigParams) && !TextUtils.isEmpty(this.f10584a.mConfigParams.id) && !TextUtils.isEmpty(deviceInfo.mac) && !this.f10584a.mConfigParams.id.equals(AlinkHelper.getHalfMacFromMac(deviceInfo.mac))) {
            ALog.i(AlinkBroadcastConfigStrategy.TAG, "deviceId not equal to device mac. return. deviceId=" + this.f10584a.mConfigParams.id + ", mac=" + deviceInfo.mac);
            return;
        }
        if (!AlinkHelper.isBatchBroadcast(this.f10584a.mConfigParams) && !TextUtils.isEmpty(this.f10584a.mConfigParams.deviceName) && !TextUtils.isEmpty(deviceInfo.deviceName) && !this.f10584a.mConfigParams.deviceName.equals(deviceInfo.deviceName)) {
            ALog.i(AlinkBroadcastConfigStrategy.TAG, "not same device. return. deviceName=" + this.f10584a.mConfigParams.deviceName + ", FDeviceName=" + deviceInfo.deviceName);
            return;
        }
        this.f10584a.mConfigParams.deviceName = deviceInfo.deviceName;
        ALog.i(AlinkBroadcastConfigStrategy.TAG, "onDeviceFound BroadCast Provision Success.");
        PerformanceLog.trace(AlinkBroadcastConfigStrategy.TAG, "connectap");
        this.f10584a.updateCache(deviceInfo, DeviceReportTokenType.APP_TOKEN);
        this.f10584a.provisionResultCallback(deviceInfo);
        if (AlinkHelper.isBatchBroadcast(this.f10584a.mConfigParams)) {
            return;
        }
        this.f10584a.waitForResult.set(false);
        this.f10584a.stopBackupCheck();
        this.f10584a.stopConfig();
    }

    @Override // com.aliyun.alink.business.devicecenter.config.IDeviceInfoNotifyListener
    public void onFailure(DCErrorCode dCErrorCode) {
    }
}
