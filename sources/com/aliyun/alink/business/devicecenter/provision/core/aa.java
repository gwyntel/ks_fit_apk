package com.aliyun.alink.business.devicecenter.provision.core;

import android.text.TextUtils;
import com.aliyun.alink.business.devicecenter.api.add.DeviceInfo;
import com.aliyun.alink.business.devicecenter.base.DCErrorCode;
import com.aliyun.alink.business.devicecenter.config.IDeviceInfoNotifyListener;
import com.aliyun.alink.business.devicecenter.config.model.DeviceReportTokenType;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.provision.core.qr.QRConfigStrategy;
import com.aliyun.alink.business.devicecenter.utils.StringUtils;

/* loaded from: classes2.dex */
public class aa implements IDeviceInfoNotifyListener {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ QRConfigStrategy f10566a;

    public aa(QRConfigStrategy qRConfigStrategy) {
        this.f10566a = qRConfigStrategy;
    }

    @Override // com.aliyun.alink.business.devicecenter.config.IDeviceInfoNotifyListener
    public void onDeviceFound(DeviceInfo deviceInfo) {
        if (deviceInfo == null || this.f10566a.mConfigParams == null || TextUtils.isEmpty(deviceInfo.productKey)) {
            return;
        }
        if (!this.f10566a.waitForResult.get()) {
            ALog.d(QRConfigStrategy.TAG, "qr provision finished return.");
            return;
        }
        if (!StringUtils.isEqualString(deviceInfo.productKey, this.f10566a.mConfigParams.productKey) || (!TextUtils.isEmpty(this.f10566a.mConfigParams.deviceName) && !StringUtils.isEqualString(this.f10566a.mConfigParams.deviceName, deviceInfo.deviceName))) {
            ALog.i(QRConfigStrategy.TAG, "onDeviceFound QR otherDeviceInfo=" + deviceInfo);
            return;
        }
        ALog.i(QRConfigStrategy.TAG, "onDeviceFound QR Provision Success.");
        this.f10566a.updateCache(deviceInfo, DeviceReportTokenType.APP_TOKEN);
        this.f10566a.waitForResult.set(false);
        this.f10566a.stopBackupCheck();
        this.f10566a.mConfigParams.deviceName = deviceInfo.deviceName;
        this.f10566a.provisionResultCallback(deviceInfo);
        this.f10566a.stopConfig();
    }

    @Override // com.aliyun.alink.business.devicecenter.config.IDeviceInfoNotifyListener
    public void onFailure(DCErrorCode dCErrorCode) {
    }
}
