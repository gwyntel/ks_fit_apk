package com.aliyun.alink.business.devicecenter.provision.core;

import android.text.TextUtils;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.aliyun.alink.business.devicecenter.base.DCErrorCode;
import com.aliyun.alink.business.devicecenter.channel.ble.IBleInterface;
import com.aliyun.alink.business.devicecenter.channel.http.DCError;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.provision.core.ble.BreezeConfigStrategy;
import com.aliyun.alink.business.devicecenter.track.DCUserTrack;

/* renamed from: com.aliyun.alink.business.devicecenter.provision.core.j, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class C0447j implements IBleInterface.IBleDeviceInfoCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ BreezeConfigStrategy f10576a;

    public C0447j(BreezeConfigStrategy breezeConfigStrategy) {
        this.f10576a = breezeConfigStrategy;
    }

    @Override // com.aliyun.alink.business.devicecenter.channel.ble.IBleInterface.IBleDeviceInfoCallback
    public void onDeviceInfo(IBleInterface.BleDeviceInfo bleDeviceInfo) {
        ALog.d(BreezeConfigStrategy.TAG, "onDeviceInfo() called with: deviceInfo = [" + bleDeviceInfo + "]");
        DCUserTrack.addTrackData(AlinkConstants.KEY_END_TIME_GET_DEVICE_INFO, String.valueOf(System.currentTimeMillis()));
        try {
            if (!this.f10576a.provisionHasStopped.get() && this.f10576a.mConfigParams != null) {
                if (bleDeviceInfo == null) {
                    this.f10576a.provisionErrorInfo = new DCErrorCode("DeviceFail", DCErrorCode.PF_DEVICE_FAIL).setMsg("BLE connected, getDeviceInfo null.").setSubcode(DCErrorCode.SUBCODE_DF_BLE_GET_DEVICE_INFO_EMPTY);
                    this.f10576a.provisionResultCallback(null);
                    this.f10576a.stopConfig();
                    return;
                }
                if (this.f10576a.mConfigParams == null) {
                    return;
                }
                if (!TextUtils.isEmpty(bleDeviceInfo.productKey) && !TextUtils.isEmpty(bleDeviceInfo.deviceName)) {
                    if (this.f10576a.mConfigParams == null) {
                        return;
                    }
                    if (TextUtils.isEmpty(this.f10576a.mConfigParams.productKey)) {
                        String str = BreezeConfigStrategy.TAG;
                        StringBuilder sb = new StringBuilder();
                        sb.append("onDeviceInfo provision productKey is empty, device productKey=");
                        sb.append(bleDeviceInfo.productKey);
                        ALog.d(str, sb.toString());
                        this.f10576a.mConfigParams.productKey = bleDeviceInfo.productKey;
                    }
                    if (TextUtils.isEmpty(this.f10576a.mConfigParams.deviceName)) {
                        String str2 = BreezeConfigStrategy.TAG;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("onDeviceInfo provision deviceName is empty, device deviceName=");
                        sb2.append(bleDeviceInfo.deviceName);
                        ALog.d(str2, sb2.toString());
                        this.f10576a.mConfigParams.deviceName = bleDeviceInfo.deviceName;
                    }
                    DCUserTrack.addTrackData("pk", this.f10576a.mConfigParams.productKey);
                    DCUserTrack.addTrackData(AlinkConstants.KEY_DN, this.f10576a.mConfigParams.deviceName);
                    this.f10576a.hasBleEverConnectedAB.set(true);
                    this.f10576a.handleBreBiz();
                    return;
                }
                this.f10576a.provisionErrorInfo = new DCErrorCode("DeviceFail", DCErrorCode.PF_DEVICE_FAIL).setMsg("BLE connected, getDeviceInfo invalid.").setSubcode(DCErrorCode.SUBCODE_DF_BLE_GET_DEVICE_INFO_INVALID);
                this.f10576a.provisionResultCallback(null);
                this.f10576a.stopConfig();
                return;
            }
            ALog.d(BreezeConfigStrategy.TAG, "provision has stopped, return.");
        } catch (Exception e2) {
            ALog.w(BreezeConfigStrategy.TAG, "getDeviceName parse data info failed. error:" + e2);
            this.f10576a.provisionErrorInfo = new DCErrorCode("DeviceFail", DCErrorCode.PF_DEVICE_FAIL).setMsg("BLE connected, getDeviceInfo parse data e=" + e2).setSubcode(DCErrorCode.SUBCODE_DF_BLE_GET_DEVICE_INFO_INVALID);
            this.f10576a.provisionResultCallback(null);
            this.f10576a.stopConfig();
        }
    }

    @Override // com.aliyun.alink.business.devicecenter.channel.ble.IBleInterface.IBleDeviceInfoCallback
    public void onError(DCError dCError) {
        if (dCError == null) {
            return;
        }
        try {
            BreezeConfigStrategy breezeConfigStrategy = this.f10576a;
            DCErrorCode dCErrorCode = new DCErrorCode("DeviceFail", Integer.parseInt(dCError.code));
            StringBuilder sb = new StringBuilder();
            sb.append("BLE connected, getDeviceInfo null.");
            sb.append(dCError.message);
            breezeConfigStrategy.provisionErrorInfo = dCErrorCode.setMsg(sb.toString()).setSubcode(DCErrorCode.SUBCODE_DF_BLE_GET_DEVICE_INFO_EMPTY);
            this.f10576a.provisionResultCallback(null);
            this.f10576a.stopConfig();
        } catch (Exception unused) {
        }
    }
}
