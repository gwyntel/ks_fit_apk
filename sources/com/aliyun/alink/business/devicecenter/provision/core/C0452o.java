package com.aliyun.alink.business.devicecenter.provision.core;

import android.text.TextUtils;
import com.aliyun.alink.business.devicecenter.base.DCErrorCode;
import com.aliyun.alink.business.devicecenter.channel.http.TransitoryClient;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.provision.core.broadcast.AlinkBroadcastConfigStrategy;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTResponse;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequest;
import com.facebook.internal.NativeProtocol;

/* renamed from: com.aliyun.alink.business.devicecenter.provision.core.o, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class C0452o implements IoTCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ RunnableC0453p f10585a;

    public C0452o(RunnableC0453p runnableC0453p) {
        this.f10585a = runnableC0453p;
    }

    @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
    public void onFailure(IoTRequest ioTRequest, Exception exc) {
        ALog.w(AlinkBroadcastConfigStrategy.TAG, "startConfig getCipher onFailure e=" + exc);
        this.f10585a.f10587b.provisionErrorInfo = new DCErrorCode(NativeProtocol.ERROR_NETWORK_ERROR, DCErrorCode.PF_NETWORK_ERROR).setSubcode(DCErrorCode.SUBCODE_API_REQUEST_ON_FAILURE).setMsg("getCipherError:" + exc);
        this.f10585a.f10587b.provisionResultCallback(null);
    }

    @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
    public void onResponse(IoTRequest ioTRequest, IoTResponse ioTResponse) {
        if (ioTResponse == null || ioTResponse.getCode() != 200) {
            ALog.w(AlinkBroadcastConfigStrategy.TAG, "startConfig getCipher BC onResponse data null. request=" + TransitoryClient.getInstance().requestToStr(ioTRequest) + ",response=" + TransitoryClient.getInstance().responseToStr(ioTResponse));
            if (ioTResponse == null) {
                this.f10585a.f10587b.provisionErrorInfo = new DCErrorCode(NativeProtocol.ERROR_NETWORK_ERROR, DCErrorCode.PF_SERVER_FAIL).setSubcode(DCErrorCode.SUBCODE_SRE_RESPONSE_EMPTY).setMsg("getCipherError");
            } else {
                this.f10585a.f10587b.provisionErrorInfo = new DCErrorCode(DCErrorCode.SERVER_ERROR_MSG, DCErrorCode.PF_SERVER_FAIL).setSubcode(ioTResponse.getCode()).setMsg(ioTResponse.getLocalizedMsg());
            }
            this.f10585a.f10587b.provisionResultCallback(null);
            return;
        }
        this.f10585a.f10587b.securityAesKey = String.valueOf(ioTResponse.getData());
        if (!TextUtils.isEmpty(this.f10585a.f10587b.securityAesKey)) {
            this.f10585a.f10587b.mConfigParams.productEncryptKey = this.f10585a.f10587b.securityAesKey;
            RunnableC0453p runnableC0453p = this.f10585a;
            runnableC0453p.f10587b.provisioning(runnableC0453p.f10586a);
            return;
        }
        ALog.w(AlinkBroadcastConfigStrategy.TAG, "startConfig getCipher BC onResponse securityAesKey fail. request=" + TransitoryClient.getInstance().requestToStr(ioTRequest) + ",response=" + TransitoryClient.getInstance().responseToStr(ioTResponse));
        this.f10585a.f10587b.provisionErrorInfo = new DCErrorCode(DCErrorCode.SERVER_ERROR_MSG, DCErrorCode.PF_SERVER_FAIL).setSubcode(DCErrorCode.SUBCODE_SRE_KEY_EMPTY).setMsg("getCipherBAesNull");
        this.f10585a.f10587b.provisionResultCallback(null);
    }
}
