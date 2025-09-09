package com.aliyun.alink.business.devicecenter.provision.core;

import com.alibaba.ailabs.iot.aisbase.plugin.auth.AuthPluginBusinessProxy;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.aliyun.alink.business.devicecenter.base.DCEnvHelper;
import com.aliyun.alink.business.devicecenter.base.DCErrorCode;
import com.aliyun.alink.business.devicecenter.channel.ble.IBleInterface;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.log.PerformanceLog;
import com.aliyun.alink.business.devicecenter.provision.core.ble.BreezeConfigState;
import com.aliyun.alink.business.devicecenter.provision.core.ble.BreezeConfigStrategy;
import com.aliyun.alink.business.devicecenter.track.DCUserTrack;
import com.aliyun.alink.business.devicecenter.ut.LinkUtHelper;
import com.aliyun.alink.business.devicecenter.ut.UtLinkInfo;
import com.umeng.message.common.inter.ITagManager;

/* renamed from: com.aliyun.alink.business.devicecenter.provision.core.i, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class C0446i implements IBleInterface.IBleConnectionCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ BreezeConfigStrategy f10575a;

    public C0446i(BreezeConfigStrategy breezeConfigStrategy) {
        this.f10575a = breezeConfigStrategy;
    }

    @Override // com.aliyun.alink.business.devicecenter.channel.ble.IBleInterface.IBleConnectionCallback
    public void onChannelStateChanged(IBleInterface.IBleChannelDevice iBleChannelDevice, IBleInterface.BleChannelState bleChannelState) {
        ALog.d(BreezeConfigStrategy.TAG, "onConnectionStateChange() called with: device = [" + iBleChannelDevice + "], state = [" + bleChannelState + "]");
        if (!this.f10575a.waitForResult.get() || this.f10575a.provisionHasStopped.get()) {
            return;
        }
        if (bleChannelState == IBleInterface.BleChannelState.CONNECTED) {
            this.f10575a.updateProvisionState(BreezeConfigState.BLE_CONNECTED);
            this.f10575a.mBleChannelDevice = iBleChannelDevice;
            this.f10575a.deviceConnection = "2";
            if (iBleChannelDevice != null) {
                LinkUtHelper.connectEvent(LinkUtHelper.CONNECT_SUCCESS, new UtLinkInfo(this.f10575a.mConfigParams.userId, String.valueOf(System.currentTimeMillis() - this.f10575a.utStartTime), this.f10575a.mConfigParams.productKey, this.f10575a.mConfigParams.linkType.getName()));
                return;
            }
            return;
        }
        if (bleChannelState == IBleInterface.BleChannelState.AUTH_SUCCESSFUL) {
            this.f10575a.updateProvisionState(BreezeConfigState.BLE_AUTHT_SUCC);
            this.f10575a.deviceConnection = "2";
            if (this.f10575a.hasBleEverConnectedAB.get()) {
                ALog.d(BreezeConfigStrategy.TAG, "provision has already started, return.");
                return;
            }
            this.f10575a.hasBleEverConnectedAB.set(true);
            DCUserTrack.addTrackData(AlinkConstants.KEY_END_TIME_CONNECT_BLE, String.valueOf(System.currentTimeMillis()));
            try {
                PerformanceLog.trace(BreezeConfigStrategy.TAG, "connectBleResult", PerformanceLog.getJsonObject("result", "success"));
                if (iBleChannelDevice != null) {
                    this.f10575a.mBleChannelDevice = iBleChannelDevice;
                }
                BreezeConfigStrategy breezeConfigStrategy = this.f10575a;
                breezeConfigStrategy.getDeviceName(breezeConfigStrategy.mBleChannelDevice);
                return;
            } catch (Exception e2) {
                e2.printStackTrace();
                ALog.w(BreezeConfigStrategy.TAG, "onConnectionStateChange exception=" + e2);
                return;
            }
        }
        if (bleChannelState != IBleInterface.BleChannelState.DISCONNECTED) {
            if (bleChannelState == IBleInterface.BleChannelState.AUTH_FAILED) {
                ALog.w(BreezeConfigStrategy.TAG, "ble auth failed.");
                this.f10575a.waitForResult.set(false);
                this.f10575a.deviceConnection = "3";
                this.f10575a.provisionErrorInfo = new DCErrorCode("DeviceFail", DCErrorCode.PF_DEVICE_FAIL).setMsg("BLE error, auth failed.").setSubcode(DCErrorCode.SUBCODE_DF_BLE_AUTH_FAIL);
                this.f10575a.provisionResultCallback(null);
                this.f10575a.stopConfig();
                if (this.f10575a.mConfigParams != null) {
                    UtLinkInfo utLinkInfo = new UtLinkInfo(this.f10575a.mConfigParams.userId, this.f10575a.mConfigParams.productKey, this.f10575a.mConfigParams.linkType.getName());
                    utLinkInfo.setErrorCode(String.valueOf(DCErrorCode.SUBCODE_DF_BLE_AUTH_FAIL));
                    LinkUtHelper.connectEvent(LinkUtHelper.CONNECT_FAIL, utLinkInfo);
                    return;
                }
                return;
            }
            return;
        }
        boolean z2 = this.f10575a.provisionHasStopped.get();
        int i2 = DCErrorCode.SUBCODE_DF_BLE_DISCONNECT;
        if (!z2 && this.f10575a.mBleRetryConnectCount.getAndIncrement() < 4) {
            this.f10575a.provisionErrorInfo = new DCErrorCode("DeviceFail", DCErrorCode.PF_DEVICE_FAIL).setMsg("BLE error, disconnected. " + this.f10575a.mBleRetryConnectCount.get()).setSubcode(DCErrorCode.SUBCODE_DF_BLE_DISCONNECT);
            this.f10575a.mHandler.sendEmptyMessageDelayed(BreezeConfigStrategy.MSG_RETRY_CONNECT_BLE_DEVICE, 1000L);
            return;
        }
        if (!this.f10575a.provisionHasStopped.get()) {
            PerformanceLog.trace(BreezeConfigStrategy.TAG, "connectBleResult", PerformanceLog.getJsonObject("result", ITagManager.FAIL));
            DCUserTrack.addTrackData(AlinkConstants.KEY_END_TIME_CONNECT_BLE, String.valueOf(System.currentTimeMillis()));
        }
        this.f10575a.deviceConnection = "3";
        if (!this.f10575a.waitForResult.get() || this.f10575a.hasBleEverConnectedAB.get()) {
            return;
        }
        this.f10575a.waitForResult.set(false);
        if (DCEnvHelper.isILopEnv() && AuthPluginBusinessProxy.isAuthAndBind.get()) {
            i2 = 2064;
        }
        this.f10575a.provisionErrorInfo = new DCErrorCode("DeviceFail", DCErrorCode.PF_DEVICE_FAIL).setMsg(i2 == 2064 ? "need authorize to bind." : "ble disconnected.").setSubcode(i2);
        this.f10575a.provisionResultCallback(null);
        this.f10575a.stopConfig();
    }
}
