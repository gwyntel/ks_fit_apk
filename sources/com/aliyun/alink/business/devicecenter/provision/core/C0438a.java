package com.aliyun.alink.business.devicecenter.provision.core;

import android.text.TextUtils;
import com.aliyun.alink.business.devicecenter.api.add.DeviceInfo;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.provision.core.ble.BreezeConfigStrategy;
import com.aliyun.alink.business.devicecenter.utils.TimerUtils;

/* renamed from: com.aliyun.alink.business.devicecenter.provision.core.a, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class C0438a implements TimerUtils.ITimerCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ BreezeConfigStrategy f10565a;

    public C0438a(BreezeConfigStrategy breezeConfigStrategy) {
        this.f10565a = breezeConfigStrategy;
    }

    @Override // com.aliyun.alink.business.devicecenter.utils.TimerUtils.ITimerCallback
    public void onTimeout() {
        if (this.f10565a.comboDeviceProvisionState == 1) {
            if (this.f10565a.subErrorCode != 0 || !this.f10565a.isIlop() || this.f10565a.mConfigParams.isInSide) {
                BreezeConfigStrategy breezeConfigStrategy = this.f10565a;
                breezeConfigStrategy.provisionFailFromBleNotify(breezeConfigStrategy.subErrorCode, "device connect ap success, but connect cloud failed.");
                return;
            }
            ALog.i(BreezeConfigStrategy.TAG, "provision success from device ble notify connect ap success until timeout.");
            if (TextUtils.isEmpty(this.f10565a.mConfigParams.productKey)) {
                BreezeConfigStrategy breezeConfigStrategy2 = this.f10565a;
                breezeConfigStrategy2.provisionFailFromBleNotify(breezeConfigStrategy2.subErrorCode, "device connect ap success, but no pk info returned.");
                return;
            } else if (TextUtils.isEmpty(this.f10565a.mConfigParams.deviceName)) {
                BreezeConfigStrategy breezeConfigStrategy3 = this.f10565a;
                breezeConfigStrategy3.provisionFailFromBleNotify(breezeConfigStrategy3.subErrorCode, "device connect ap success, but no deviceName info returned.");
                return;
            } else {
                DeviceInfo deviceInfo = new DeviceInfo();
                deviceInfo.productKey = this.f10565a.mConfigParams.productKey;
                deviceInfo.deviceName = this.f10565a.mConfigParams.deviceName;
                this.f10565a.provisionResultCallback(deviceInfo);
                return;
            }
        }
        if (this.f10565a.comboDeviceProvisionState != 2) {
            if (this.f10565a.comboDeviceProvisionState == -1) {
                BreezeConfigStrategy breezeConfigStrategy4 = this.f10565a;
                breezeConfigStrategy4.provisionFailFromBleNotify(breezeConfigStrategy4.subErrorCode, "device provision fail until timeout.");
                return;
            } else if (this.f10565a.devWiFiMFromFromBleReceivedByteBuffer != null) {
                BreezeConfigStrategy breezeConfigStrategy5 = this.f10565a;
                breezeConfigStrategy5.provisionFailFromBleNotify(breezeConfigStrategy5.subErrorCode == 0 ? this.f10565a.devSubErrorCodeFromBleReceived : this.f10565a.subErrorCode, "device provision fail until timeout, but get ");
                return;
            } else {
                this.f10565a.getBleProvisionTimeoutErrorInfo();
                this.f10565a.provisionResultCallback(null);
                return;
            }
        }
        if (this.f10565a.subErrorCode != 0 || !this.f10565a.isIlop() || this.f10565a.mConfigParams.isInSide) {
            BreezeConfigStrategy breezeConfigStrategy6 = this.f10565a;
            breezeConfigStrategy6.provisionFailFromBleNotify(breezeConfigStrategy6.subErrorCode, "device connect ap success & report token success, check cloud failed.");
            return;
        }
        ALog.i(BreezeConfigStrategy.TAG, "provision success from device ble notify connect ap success until timeout.");
        if (TextUtils.isEmpty(this.f10565a.mConfigParams.productKey)) {
            BreezeConfigStrategy breezeConfigStrategy7 = this.f10565a;
            breezeConfigStrategy7.provisionFailFromBleNotify(breezeConfigStrategy7.subErrorCode, "device connect ap success, but no pk info returned.");
        } else if (TextUtils.isEmpty(this.f10565a.mConfigParams.deviceName)) {
            BreezeConfigStrategy breezeConfigStrategy8 = this.f10565a;
            breezeConfigStrategy8.provisionFailFromBleNotify(breezeConfigStrategy8.subErrorCode, "device connect ap success, but no deviceName info returned.");
        } else {
            DeviceInfo deviceInfo2 = new DeviceInfo();
            deviceInfo2.productKey = this.f10565a.mConfigParams.productKey;
            deviceInfo2.deviceName = this.f10565a.mConfigParams.deviceName;
            this.f10565a.provisionResultCallback(deviceInfo2);
        }
    }
}
