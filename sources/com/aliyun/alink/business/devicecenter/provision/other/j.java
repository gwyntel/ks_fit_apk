package com.aliyun.alink.business.devicecenter.provision.other;

import android.text.TextUtils;
import com.aliyun.alink.business.devicecenter.api.add.DeviceInfo;
import com.aliyun.alink.business.devicecenter.base.DCErrorCode;
import com.aliyun.alink.business.devicecenter.biz.SAPProvisionedICacheModel;
import com.aliyun.alink.business.devicecenter.cache.CacheCenter;
import com.aliyun.alink.business.devicecenter.cache.CacheType;
import com.aliyun.alink.business.devicecenter.config.IDeviceInfoNotifyListener;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.log.PerformanceLog;
import com.aliyun.alink.business.devicecenter.provision.other.softap.SoftAPConfigStrategy;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class j implements IDeviceInfoNotifyListener {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ SoftAPConfigStrategy f10613a;

    public j(SoftAPConfigStrategy softAPConfigStrategy) {
        this.f10613a = softAPConfigStrategy;
    }

    @Override // com.aliyun.alink.business.devicecenter.config.IDeviceInfoNotifyListener
    public void onDeviceFound(DeviceInfo deviceInfo) throws NumberFormatException {
        ALog.llog((byte) 3, SoftAPConfigStrategy.TAG, "onLocalDeviceFound SAP deviceInfo=" + deviceInfo);
        if (deviceInfo == null) {
            return;
        }
        if (this.f10613a.provisionHasStopped.get()) {
            ALog.d(SoftAPConfigStrategy.TAG, "provision finished return.");
            return;
        }
        if (this.f10613a.hasCallbackToApp.get()) {
            ALog.i(SoftAPConfigStrategy.TAG, "haven callback result to app. return.");
            return;
        }
        if (this.f10613a.mConfigParams == null) {
            ALog.i(SoftAPConfigStrategy.TAG, "hmSAPConfigParams is null.");
            return;
        }
        if ((!TextUtils.isEmpty(this.f10613a.mConfigParams.productKey) && !this.f10613a.mConfigParams.productKey.equals(deviceInfo.productKey)) || (!TextUtils.isEmpty(this.f10613a.mConfigParams.deviceName) && !this.f10613a.mConfigParams.deviceName.equals(deviceInfo.deviceName))) {
            ALog.i(SoftAPConfigStrategy.TAG, "onLocalDeviceFound SAP receive other device.");
            return;
        }
        PerformanceLog.trace(SoftAPConfigStrategy.TAG, "connectap");
        SoftAPConfigStrategy softAPConfigStrategy = this.f10613a;
        softAPConfigStrategy.updateCache(deviceInfo, softAPConfigStrategy.deviceReportTokenType);
        this.f10613a.hasCallbackToApp.set(true);
        this.f10613a.stopBackupCheck();
        this.f10613a.notifySupportProvisionService(deviceInfo);
        this.f10613a.mConfigParams.deviceName = deviceInfo.deviceName;
        if (!TextUtils.isEmpty(this.f10613a.mDeviceApSsid)) {
            SAPProvisionedICacheModel sAPProvisionedICacheModel = new SAPProvisionedICacheModel();
            sAPProvisionedICacheModel.productKey = deviceInfo.productKey;
            sAPProvisionedICacheModel.deviceName = deviceInfo.deviceName;
            sAPProvisionedICacheModel.apSsid = this.f10613a.mDeviceApSsid;
            sAPProvisionedICacheModel.apBssid = this.f10613a.mDeviceApBssid;
            sAPProvisionedICacheModel.aliveTime = System.currentTimeMillis() + 120000;
            ArrayList arrayList = new ArrayList();
            arrayList.add(sAPProvisionedICacheModel);
            CacheCenter.getInstance().updateCache(CacheType.SAP_PROVISIONED_SSID, (List) arrayList);
        }
        this.f10613a.provisionResultCallback(deviceInfo);
        this.f10613a.stopConfig();
    }

    @Override // com.aliyun.alink.business.devicecenter.config.IDeviceInfoNotifyListener
    public void onFailure(DCErrorCode dCErrorCode) {
    }
}
