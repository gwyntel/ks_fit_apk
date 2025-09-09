package com.aliyun.alink.business.devicecenter.provision.other;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.alink.business.devicecenter.api.add.DeviceInfo;
import com.aliyun.alink.business.devicecenter.api.add.ProtocolVersion;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.aliyun.alink.business.devicecenter.channel.coap.CoAPClient;
import com.aliyun.alink.business.devicecenter.config.model.DeviceReportTokenType;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.log.PerformanceLog;
import com.aliyun.alink.business.devicecenter.provision.other.softap.SoftAPConfigStrategy;
import com.aliyun.alink.business.devicecenter.track.DCUserTrack;
import com.aliyun.alink.linksdk.alcs.coap.AlcsCoAPContext;
import com.aliyun.alink.linksdk.alcs.coap.AlcsCoAPResponse;
import com.aliyun.alink.linksdk.alcs.coap.IAlcsCoAPReqHandler;

/* loaded from: classes2.dex */
public class h implements IAlcsCoAPReqHandler {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ i f10611a;

    public h(i iVar) {
        this.f10611a = iVar;
    }

    @Override // com.aliyun.alink.linksdk.alcs.coap.IAlcsCoAPReqHandler
    public void onReqComplete(AlcsCoAPContext alcsCoAPContext, int i2, AlcsCoAPResponse alcsCoAPResponse) {
        ALog.d(SoftAPConfigStrategy.TAG, "onReqComplete() called with: coapContext = [" + alcsCoAPContext + "], flag = [" + i2 + "], response = [" + alcsCoAPResponse + "]");
        CoAPClient.getInstance().printResponse(alcsCoAPContext, alcsCoAPResponse);
        try {
            if (this.f10611a.f10612a.discoveryFuture != null && !this.f10611a.f10612a.discoveryFuture.isCancelled()) {
                if (this.f10611a.f10612a.mConfigParams == null || alcsCoAPResponse == null || TextUtils.isEmpty(alcsCoAPResponse.getPayloadString())) {
                    return;
                }
                try {
                    String str = SoftAPConfigStrategy.TAG;
                    StringBuilder sb = new StringBuilder();
                    sb.append("payload=");
                    sb.append(alcsCoAPResponse.getPayloadString());
                    ALog.llog((byte) 3, str, sb.toString());
                    JSONObject object = JSON.parseObject(alcsCoAPResponse.getPayloadString());
                    if (object == null) {
                        ALog.w(SoftAPConfigStrategy.TAG, "SAP_SEND_CONNECT_INFO invalid device, info empty.");
                        return;
                    }
                    this.f10611a.f10612a.recvSwitchAPAck.set(true);
                    this.f10611a.f10612a.recvSwitchAPAckTime.set(System.currentTimeMillis());
                    JSONObject jSONObject = object.getJSONObject("data");
                    if (jSONObject != null && !TextUtils.isEmpty(jSONObject.getString("productKey"))) {
                        DeviceInfo deviceInfoConvertLocalDevice = DeviceInfo.convertLocalDevice(jSONObject);
                        if (TextUtils.isEmpty(this.f10611a.f10612a.mConfigParams.productKey) && ProtocolVersion.NO_PRODUCT.getVersion().equals(this.f10611a.f10612a.mConfigParams.protocolVersion)) {
                            this.f10611a.f10612a.mConfigParams.productKey = deviceInfoConvertLocalDevice.productKey;
                        }
                        if (this.f10611a.f10612a.mConfigParams == null || !deviceInfoConvertLocalDevice.productKey.equals(this.f10611a.f10612a.mConfigParams.productKey)) {
                            ALog.w(SoftAPConfigStrategy.TAG, "SAP_SEND_CONNECT_INFO productKey not match.");
                            return;
                        }
                        DCUserTrack.addTrackData(AlinkConstants.KEY_PROVISION_STARTED, "true");
                        String string = jSONObject.getString(AlinkConstants.KEY_TOKEN_TYPE);
                        if ("0".equals(string)) {
                            this.f10611a.f10612a.deviceReportTokenType = DeviceReportTokenType.APP_TOKEN;
                            SoftAPConfigStrategy softAPConfigStrategy = this.f10611a.f10612a;
                            softAPConfigStrategy.updateBackupCheckType(softAPConfigStrategy.deviceReportTokenType);
                        } else if ("1".equals(string)) {
                            this.f10611a.f10612a.deviceReportTokenType = DeviceReportTokenType.CLOUD_TOKEN;
                            SoftAPConfigStrategy softAPConfigStrategy2 = this.f10611a.f10612a;
                            softAPConfigStrategy2.updateBackupCheckType(softAPConfigStrategy2.deviceReportTokenType);
                        } else if (this.f10611a.f10612a.sendAppToken2DeviceAB.get()) {
                            this.f10611a.f10612a.deviceReportTokenType = DeviceReportTokenType.APP_TOKEN;
                            SoftAPConfigStrategy softAPConfigStrategy3 = this.f10611a.f10612a;
                            softAPConfigStrategy3.updateBackupCheckType(softAPConfigStrategy3.deviceReportTokenType);
                        } else {
                            this.f10611a.f10612a.deviceReportTokenType = DeviceReportTokenType.UNKNOWN;
                            SoftAPConfigStrategy softAPConfigStrategy4 = this.f10611a.f10612a;
                            softAPConfigStrategy4.updateBackupCheckType(softAPConfigStrategy4.deviceReportTokenType);
                        }
                        this.f10611a.f10612a.notifySupportProvisionService(deviceInfoConvertLocalDevice);
                        SoftAPConfigStrategy softAPConfigStrategy5 = this.f10611a.f10612a;
                        softAPConfigStrategy5.cancelRequest(softAPConfigStrategy5.getDeviceInfoRequest, this.f10611a.f10612a.deviceInfoCoapMessageId);
                        this.f10611a.f10612a.cancelTask();
                        this.f10611a.f10612a.isSendingConnectInfo.set(false);
                        this.f10611a.f10612a.mConfigParams.deviceName = deviceInfoConvertLocalDevice.deviceName;
                        DCUserTrack.addTrackData(AlinkConstants.KEY_DN, this.f10611a.f10612a.mConfigParams.deviceName);
                        DCUserTrack.addTrackData(AlinkConstants.KEY_END_TIME_SWITCH_AP, String.valueOf(System.currentTimeMillis()));
                        String str2 = SoftAPConfigStrategy.TAG;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("receive switchap ack, tokenType=");
                        sb2.append(string);
                        ALog.i(str2, sb2.toString());
                        PerformanceLog.trace(SoftAPConfigStrategy.TAG, "switchapAck");
                        if (this.f10611a.f10612a.isRecoveringWiFi.compareAndSet(false, true)) {
                            this.f10611a.f10612a.recoverWifiConnect("switchApAck", true);
                            return;
                        }
                        return;
                    }
                    ALog.w(SoftAPConfigStrategy.TAG, "SAP_SEND_CONNECT_INFO invalid device, data empty.");
                    return;
                } catch (Exception e2) {
                    e2.printStackTrace();
                    String str3 = SoftAPConfigStrategy.TAG;
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("getCoapResponse FastJson parse generic object failed.");
                    sb3.append(e2);
                    ALog.w(str3, sb3.toString());
                    return;
                }
            }
            ALog.i(SoftAPConfigStrategy.TAG, "SAP task finished or canceled, ignore.");
        } catch (Exception e3) {
            e3.printStackTrace();
        }
    }
}
