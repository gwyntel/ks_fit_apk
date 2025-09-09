package com.aliyun.alink.business.devicecenter.provision.core;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.alink.business.devicecenter.base.DCEnvHelper;
import com.aliyun.alink.business.devicecenter.base.DCErrorCode;
import com.aliyun.alink.business.devicecenter.biz.model.GetBindTokenMtopResponse;
import com.aliyun.alink.business.devicecenter.channel.http.DCError;
import com.aliyun.alink.business.devicecenter.channel.http.IRequestCallback;
import com.aliyun.alink.business.devicecenter.config.model.DeviceReportTokenType;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.provision.core.ble.BreezeConfigStrategy;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTResponse;

/* renamed from: com.aliyun.alink.business.devicecenter.provision.core.h, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class C0445h implements IRequestCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ BreezeConfigStrategy f10574a;

    public C0445h(BreezeConfigStrategy breezeConfigStrategy) {
        this.f10574a = breezeConfigStrategy;
    }

    @Override // com.aliyun.alink.business.devicecenter.channel.http.IRequestCallback
    public void onFail(DCError dCError, Object obj) {
        if (!this.f10574a.provisionHasStopped.get() && this.f10574a.needRetryGetCloudTokenAB.get()) {
            if (!DCEnvHelper.isTgEnv()) {
                if (DCEnvHelper.isILopEnv()) {
                    this.f10574a.connectBreDevice();
                    return;
                }
                return;
            }
            ALog.w(BreezeConfigStrategy.TAG, "getCloudProvisionToken onFail dcError=" + dCError + ", response=" + obj);
        }
    }

    @Override // com.aliyun.alink.business.devicecenter.channel.http.IRequestCallback
    public void onSuccess(Object obj) {
        ALog.d(BreezeConfigStrategy.TAG, "getCloudToken requestCallback onSuccess() called with: data = [" + obj + "]");
        if (!this.f10574a.provisionHasStopped.get() && this.f10574a.needRetryGetCloudTokenAB.get()) {
            if (!DCEnvHelper.isTgEnv()) {
                if (DCEnvHelper.isILopEnv()) {
                    if (!(obj instanceof IoTResponse)) {
                        ALog.w(BreezeConfigStrategy.TAG, "getCloudProvisionToken sth wrong with apiclient & mtop dep.");
                        this.f10574a.connectBreDevice();
                        return;
                    }
                    IoTResponse ioTResponse = (IoTResponse) obj;
                    if (ioTResponse == null || ioTResponse.getCode() != 200) {
                        ALog.w(BreezeConfigStrategy.TAG, "getCloudProvisionToken onResponse response error.");
                        this.f10574a.connectBreDevice();
                        return;
                    }
                    if (ioTResponse.getData() == null) {
                        ALog.w(BreezeConfigStrategy.TAG, "getCloudProvisionToken onResponse data null.");
                        this.f10574a.connectBreDevice();
                        return;
                    }
                    JSONObject object = JSON.parseObject(ioTResponse.getData().toString());
                    if (!TextUtils.isEmpty(object.getString("token"))) {
                        this.f10574a.sendAppToken2DeviceAB.set(false);
                        this.f10574a.deviceReportTokenType = DeviceReportTokenType.UNKNOWN;
                        this.f10574a.mConfigParams.bindToken = object.getString("token");
                        this.f10574a.connectBreDevice();
                        return;
                    }
                    ALog.w(BreezeConfigStrategy.TAG, "getCloudProvisionToken onResponse token null.");
                    if (!this.f10574a.mConfigParams.isInSide) {
                        this.f10574a.connectBreDevice();
                        return;
                    }
                    ALog.w(BreezeConfigStrategy.TAG, "getCloudProvisionToken onResponse token null. :inside");
                    this.f10574a.provisionErrorInfo = new DCErrorCode(DCErrorCode.SERVER_ERROR_MSG, DCErrorCode.PF_DEVICE_FAIL).setMsg("combo+inside get cloud token error").setSubcode(DCErrorCode.SUBCODE_DF_BLE_NO_CONNECTAP_NOTIFY_AND_CHECK_TOKEN_FAIL);
                    this.f10574a.provisionResultCallback(null);
                    this.f10574a.stopConfig();
                    return;
                }
                return;
            }
            if (!(obj instanceof GetBindTokenMtopResponse)) {
                this.f10574a.provisionErrorInfo = new DCErrorCode("UserFail", DCErrorCode.PF_USER_FAIL).setMsg("sth wrong with mtop & apiclient dep.").setSubcode(DCErrorCode.SUBCODE_APICLIENT_AND_MTOP_DEP_ERROR);
                this.f10574a.provisionResultCallback(null);
                this.f10574a.stopConfig();
                return;
            }
            GetBindTokenMtopResponse getBindTokenMtopResponse = (GetBindTokenMtopResponse) obj;
            if (getBindTokenMtopResponse.m58getData() == null) {
                ALog.w(BreezeConfigStrategy.TAG, "getCloudProvisionToken onResponse response error.");
                this.f10574a.provisionErrorInfo = new DCErrorCode(DCErrorCode.SERVER_ERROR_MSG, DCErrorCode.PF_SERVER_FAIL).setMsg("getCloudProvisionToken failed. data=null.").setSubcode(DCErrorCode.SUBCODE_SRE_RESPONSE_FAIL);
                this.f10574a.provisionResultCallback(null);
                this.f10574a.stopConfig();
                return;
            }
            if (!getBindTokenMtopResponse.m58getData().isSuccess()) {
                this.f10574a.provisionErrorInfo = new DCErrorCode(DCErrorCode.SERVER_ERROR_MSG, DCErrorCode.PF_SERVER_FAIL).setMsg("getCloudProvisionToken. success=false." + getBindTokenMtopResponse.m58getData().getMsgInfo()).setSubcodeStr(getBindTokenMtopResponse.m58getData().getMsgCode());
                this.f10574a.provisionResultCallback(null);
                this.f10574a.stopConfig();
                return;
            }
            if (getBindTokenMtopResponse.m58getData().getModel() == null) {
                ALog.w(BreezeConfigStrategy.TAG, "getCloudProvisionToken success, but model is empty.");
                this.f10574a.provisionErrorInfo = new DCErrorCode(DCErrorCode.SERVER_ERROR_MSG, DCErrorCode.PF_SERVER_FAIL).setMsg("getCloudProvisionToken failed. model is empty.").setSubcode(DCErrorCode.SUBCODE_SRE_RESPONSE_FAIL);
                this.f10574a.provisionResultCallback(null);
                this.f10574a.stopConfig();
                return;
            }
            ALog.d(BreezeConfigStrategy.TAG, "getCloudProvisionToken success, to connect ble device.");
            this.f10574a.sendAppToken2DeviceAB.set(false);
            this.f10574a.deviceReportTokenType = DeviceReportTokenType.UNKNOWN;
            this.f10574a.mConfigParams.bindToken = getBindTokenMtopResponse.m58getData().getModel().getToken();
            this.f10574a.connectBreDevice();
        }
    }
}
