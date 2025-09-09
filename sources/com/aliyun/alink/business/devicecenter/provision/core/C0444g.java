package com.aliyun.alink.business.devicecenter.provision.core;

import android.text.TextUtils;
import com.aliyun.alink.business.devicecenter.api.add.DeviceInfo;
import com.aliyun.alink.business.devicecenter.api.add.ProtocolVersion;
import com.aliyun.alink.business.devicecenter.api.add.ProvisionStatus;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.aliyun.alink.business.devicecenter.channel.ble.IBleInterface;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.provision.core.ble.BreezeConfigStrategy;
import com.aliyun.alink.business.devicecenter.track.DCUserTrack;
import com.aliyun.alink.business.devicecenter.utils.DeviceInfoUtils;

/* renamed from: com.aliyun.alink.business.devicecenter.provision.core.g, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class C0444g implements IBleInterface.IBleScanCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ BreezeConfigStrategy f10573a;

    public C0444g(BreezeConfigStrategy breezeConfigStrategy) {
        this.f10573a = breezeConfigStrategy;
    }

    @Override // com.aliyun.alink.business.devicecenter.channel.ble.IBleInterface.IBleScanCallback
    public void onBLEDeviceFound(DeviceInfo deviceInfo) {
        if (deviceInfo == null || TextUtils.isEmpty(deviceInfo.productId) || TextUtils.isEmpty(deviceInfo.mac)) {
            return;
        }
        String str = deviceInfo.mac;
        String str2 = deviceInfo.productId;
        if (!TextUtils.isEmpty(this.f10573a.mConfigParams.mac) && !this.f10573a.mConfigParams.mac.equals(str)) {
            ALog.w(BreezeConfigStrategy.TAG, "mac not match, toProvisionMac=" + this.f10573a.mConfigParams.mac + ",foundMacWithColon=" + str);
            return;
        }
        if (!TextUtils.isEmpty(this.f10573a.mConfigParams.productId) && !this.f10573a.mConfigParams.productId.equals(str2)) {
            ALog.w(BreezeConfigStrategy.TAG, "1.0 productId not match, toProvisionPI=" + this.f10573a.mConfigParams.productId + ",foundPI=" + str2);
            return;
        }
        DCUserTrack.addTrackData(AlinkConstants.KEY_PI, this.f10573a.mConfigParams.productId);
        ALog.d(BreezeConfigStrategy.TAG, "needBreezeScan=" + this.f10573a.needBreezeScan);
        if (this.f10573a.needBreezeScan.get()) {
            ALog.i(BreezeConfigStrategy.TAG, "onLeScan find match device, breeze state=onLeScanMatch.");
            this.f10573a.mConfigParams.devType = deviceInfo.devType;
            this.f10573a.needBreezeScan.set(false);
            if (ProtocolVersion.NO_PRODUCT.getVersion().equals(this.f10573a.mConfigParams.protocolVersion)) {
                ALog.i(BreezeConfigStrategy.TAG, "No product version. set productId = " + str2);
                this.f10573a.mConfigParams.productId = str2;
            }
            if (TextUtils.isEmpty(this.f10573a.mConfigParams.productId)) {
                ALog.i(BreezeConfigStrategy.TAG, "1.0 product version. mac equal, set productId = " + str2);
                this.f10573a.mConfigParams.productId = str2;
            }
            if (TextUtils.isEmpty(this.f10573a.mConfigParams.productKey)) {
                DeviceInfoUtils.pidReturnToPk(this.f10573a.mConfigParams.productId, new C0443f(this));
            }
            this.f10573a.stopScanNotifyTimer();
            this.f10573a.comboDeviceMac = str;
            ProvisionStatus provisionStatus = ProvisionStatus.BLE_DEVICE_SCAN_SUCCESS;
            provisionStatus.setMessage("scan target ble device success.");
            provisionStatus.addExtraParams(AlinkConstants.KEY_DEV_TYPE, this.f10573a.mConfigParams.devType);
            provisionStatus.addExtraParams(AlinkConstants.KEY_BLE_MAC, this.f10573a.comboDeviceMac);
            provisionStatus.addExtraParams(AlinkConstants.KEY_PRODUCT_ID, this.f10573a.mConfigParams.productId);
            this.f10573a.provisionStatusCallback(provisionStatus);
            DCUserTrack.addTrackData(AlinkConstants.KEY_END_TIME_SCAN, String.valueOf(System.currentTimeMillis()));
            ALog.i(BreezeConfigStrategy.TAG, "onLeScan breeze state=stopLeScan.");
            this.f10573a.mBleChannelClient.stopScan(this.f10573a.bleScanCallback);
            if (AlinkConstants.DEVICE_TYPE_COMBO_SUBTYPE_3.equals(this.f10573a.mConfigParams.devType)) {
                ALog.i(BreezeConfigStrategy.TAG, "wait for user to call continueConfig interface.");
            } else {
                this.f10573a.getCloudToken();
            }
        }
    }

    @Override // com.aliyun.alink.business.devicecenter.channel.ble.IBleInterface.IBleScanCallback
    public void onStartScan() {
    }

    @Override // com.aliyun.alink.business.devicecenter.channel.ble.IBleInterface.IBleScanCallback
    public void onStopScan() {
    }
}
