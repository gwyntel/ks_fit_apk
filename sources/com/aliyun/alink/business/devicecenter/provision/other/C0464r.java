package com.aliyun.alink.business.devicecenter.provision.other;

import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.aliyun.alink.business.devicecenter.base.DCErrorCode;
import com.aliyun.alink.business.devicecenter.channel.http.TransitoryClient;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.log.PerformanceLog;
import com.aliyun.alink.business.devicecenter.model.CheckTokenModel;
import com.aliyun.alink.business.devicecenter.provision.other.zero.BatchZeroConfigStrategy;
import com.aliyun.alink.business.devicecenter.track.DCUserTrack;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTResponse;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequest;
import com.facebook.internal.NativeProtocol;
import com.umeng.message.common.inter.ITagManager;

/* renamed from: com.aliyun.alink.business.devicecenter.provision.other.r, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class C0464r implements IoTCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ s f10622a;

    public C0464r(s sVar) {
        this.f10622a = sVar;
    }

    @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
    public void onFailure(IoTRequest ioTRequest, Exception exc) {
        PerformanceLog.trace(BatchZeroConfigStrategy.TAG, "reqEnrolleeResult", PerformanceLog.getJsonObject("result", ITagManager.FAIL));
        DCUserTrack.addTrackData(AlinkConstants.KEY_END_TIME_REQUEST_ENROLLEE, String.valueOf(System.currentTimeMillis()));
        this.f10622a.f10623a.provisionErrorInfo = new DCErrorCode(NativeProtocol.ERROR_NETWORK_ERROR, DCErrorCode.PF_NETWORK_ERROR).setSubcode(DCErrorCode.SUBCODE_API_REQUEST_ON_FAILURE).setMsg("BZApiClientError:" + exc);
        this.f10622a.f10623a.provisionResultCallback(null);
    }

    @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
    public void onResponse(IoTRequest ioTRequest, IoTResponse ioTResponse) {
        DCUserTrack.addTrackData(AlinkConstants.KEY_END_TIME_REQUEST_ENROLLEE, String.valueOf(System.currentTimeMillis()));
        if (!this.f10622a.f10623a.waitForResult.get()) {
            ALog.d(BatchZeroConfigStrategy.TAG, "request enrollee bz onresponse waitForResult=false, return.");
            return;
        }
        if (ioTResponse != null && ioTResponse.getCode() == 200) {
            PerformanceLog.trace(BatchZeroConfigStrategy.TAG, "reqEnrolleeResult", PerformanceLog.getJsonObject("result", "success", "alinkid", TransitoryClient.getInstance().getTraceId(ioTResponse)));
            ALog.i(BatchZeroConfigStrategy.TAG, "BZero requestEnrollee success.");
            if (this.f10622a.f10623a.mConfigParams != null) {
                BatchZeroConfigStrategy batchZeroConfigStrategy = this.f10622a.f10623a;
                batchZeroConfigStrategy.startBackupCheck(true, 5L, CheckTokenModel.getCheckModelList(batchZeroConfigStrategy.batchEnrolleeDeviceList, this.f10622a.f10623a.mConfigParams.bindToken));
            }
            if (this.f10622a.f10623a.provisionErrorInfo != null) {
                this.f10622a.f10623a.provisionErrorInfo.setSubcode(DCErrorCode.SUBCODE_PT_NO_CONNECTAP_NOTIFY_AND_CHECK_TOKEN_FAIL).setMsg("noConnectApOrCheckTokenSuccess");
                return;
            }
            return;
        }
        PerformanceLog.trace(BatchZeroConfigStrategy.TAG, "reqEnrolleeResult", PerformanceLog.getJsonObject("result", ITagManager.FAIL, "alinkid", TransitoryClient.getInstance().getTraceId(ioTResponse)));
        ALog.w(BatchZeroConfigStrategy.TAG, "BZeroRequestEnrolleeFail request=" + TransitoryClient.getInstance().requestToStr(ioTRequest) + ",response=" + TransitoryClient.getInstance().responseToStr(ioTResponse));
        if (ioTResponse == null) {
            this.f10622a.f10623a.provisionErrorInfo = new DCErrorCode(DCErrorCode.SERVER_ERROR_MSG, DCErrorCode.PF_SERVER_FAIL).setSubcode(DCErrorCode.SUBCODE_SRE_RESPONSE_EMPTY).setMsg("getCipherError");
        } else {
            this.f10622a.f10623a.provisionErrorInfo = new DCErrorCode(DCErrorCode.SERVER_ERROR_MSG, DCErrorCode.PF_SERVER_FAIL).setSubcode(ioTResponse.getCode()).setMsg(ioTResponse.getLocalizedMsg());
        }
        this.f10622a.f10623a.provisionResultCallback(null);
    }
}
