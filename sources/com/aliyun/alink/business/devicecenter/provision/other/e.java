package com.aliyun.alink.business.devicecenter.provision.other;

import android.text.TextUtils;
import com.aliyun.alink.business.devicecenter.base.DCErrorCode;
import com.aliyun.alink.business.devicecenter.channel.http.TransitoryClient;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.provision.other.softap.SoftAPConfigStrategy;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTResponse;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequest;
import com.facebook.internal.NativeProtocol;

/* loaded from: classes2.dex */
public class e implements IoTCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ f f10608a;

    public e(f fVar) {
        this.f10608a = fVar;
    }

    @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
    public void onFailure(IoTRequest ioTRequest, Exception exc) {
        ALog.w(SoftAPConfigStrategy.TAG, "startConfig getCipher onFailure e=" + exc);
        this.f10608a.f10609a.provisionErrorInfo = new DCErrorCode(NativeProtocol.ERROR_NETWORK_ERROR, DCErrorCode.PF_NETWORK_ERROR).setSubcode(DCErrorCode.SUBCODE_API_REQUEST_ON_FAILURE).setMsg("getCipherError:" + exc);
        this.f10608a.f10609a.provisionResultCallback(null);
    }

    @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
    public void onResponse(IoTRequest ioTRequest, IoTResponse ioTResponse) {
        if (ioTResponse == null || ioTResponse.getCode() != 200) {
            ALog.w(SoftAPConfigStrategy.TAG, "startConfig getCipher SAP onResponse data null. request=" + TransitoryClient.getInstance().requestToStr(ioTRequest) + ",response=" + TransitoryClient.getInstance().responseToStr(ioTResponse));
            if (ioTResponse == null) {
                this.f10608a.f10609a.provisionErrorInfo = new DCErrorCode(NativeProtocol.ERROR_NETWORK_ERROR, DCErrorCode.PF_SERVER_FAIL).setSubcode(DCErrorCode.SUBCODE_SRE_RESPONSE_EMPTY).setMsg("getCipherError");
            } else {
                this.f10608a.f10609a.provisionErrorInfo = new DCErrorCode(DCErrorCode.SERVER_ERROR_MSG, DCErrorCode.PF_SERVER_FAIL).setSubcode(ioTResponse.getCode()).setMsg(ioTResponse.getLocalizedMsg());
            }
            this.f10608a.f10609a.provisionResultCallback(null);
            return;
        }
        this.f10608a.f10609a.securityAesKey = String.valueOf(ioTResponse.getData());
        if (!TextUtils.isEmpty(this.f10608a.f10609a.securityAesKey)) {
            if (this.f10608a.f10609a.mConfigParams != null) {
                this.f10608a.f10609a.mConfigParams.productEncryptKey = this.f10608a.f10609a.securityAesKey;
            }
            this.f10608a.f10609a.getCloudToken();
            return;
        }
        ALog.w(SoftAPConfigStrategy.TAG, "startConfig getCipher SAP onResponse securityAesKey fail. request=" + TransitoryClient.getInstance().requestToStr(ioTRequest) + ",response=" + TransitoryClient.getInstance().responseToStr(ioTResponse));
        this.f10608a.f10609a.provisionErrorInfo = new DCErrorCode(DCErrorCode.SERVER_ERROR_MSG, DCErrorCode.PF_SERVER_FAIL).setSubcode(DCErrorCode.SUBCODE_SRE_KEY_EMPTY).setMsg("getCipherSAPAesNull");
        this.f10608a.f10609a.provisionResultCallback(null);
    }
}
