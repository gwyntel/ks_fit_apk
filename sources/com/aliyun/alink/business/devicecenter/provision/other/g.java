package com.aliyun.alink.business.devicecenter.provision.other;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.alink.business.devicecenter.config.model.DeviceReportTokenType;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.provision.other.softap.SoftAPConfigStrategy;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTResponse;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequest;

/* loaded from: classes2.dex */
public class g implements IoTCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ SoftAPConfigStrategy f10610a;

    public g(SoftAPConfigStrategy softAPConfigStrategy) {
        this.f10610a = softAPConfigStrategy;
    }

    @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
    public void onFailure(IoTRequest ioTRequest, Exception exc) {
        ALog.w(SoftAPConfigStrategy.TAG, "getCloudProvisionToken onFailure, use app token e=" + exc);
        if (this.f10610a.provisionHasStopped.get()) {
            return;
        }
        SoftAPConfigStrategy softAPConfigStrategy = this.f10610a;
        softAPConfigStrategy.provisioning(softAPConfigStrategy.mConfigCallback);
    }

    @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
    public void onResponse(IoTRequest ioTRequest, IoTResponse ioTResponse) {
        if (this.f10610a.provisionHasStopped.get()) {
            return;
        }
        if (ioTResponse == null || ioTResponse.getCode() != 200 || ioTResponse.getData() == null) {
            ALog.w(SoftAPConfigStrategy.TAG, "getCloudProvisionToken onResponse error, use app token.");
            SoftAPConfigStrategy softAPConfigStrategy = this.f10610a;
            softAPConfigStrategy.provisioning(softAPConfigStrategy.mConfigCallback);
            return;
        }
        JSONObject object = JSON.parseObject(ioTResponse.getData().toString());
        if (TextUtils.isEmpty(object.getString("token"))) {
            ALog.w(SoftAPConfigStrategy.TAG, "getCloudProvisionToken onResponse data null, use app token.");
            SoftAPConfigStrategy softAPConfigStrategy2 = this.f10610a;
            softAPConfigStrategy2.provisioning(softAPConfigStrategy2.mConfigCallback);
            return;
        }
        this.f10610a.sendAppToken2DeviceAB.set(false);
        this.f10610a.deviceReportTokenType = DeviceReportTokenType.UNKNOWN;
        this.f10610a.mConfigParams.bindToken = object.getString("token");
        ALog.i(SoftAPConfigStrategy.TAG, "use cloud generate token = " + this.f10610a.mConfigParams.bindToken);
        SoftAPConfigStrategy softAPConfigStrategy3 = this.f10610a;
        softAPConfigStrategy3.provisioning(softAPConfigStrategy3.mConfigCallback);
    }
}
