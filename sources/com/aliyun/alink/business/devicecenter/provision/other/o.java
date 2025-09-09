package com.aliyun.alink.business.devicecenter.provision.other;

import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.aliyun.alink.business.devicecenter.base.DCErrorCode;
import com.aliyun.alink.business.devicecenter.channel.http.TransitoryClient;
import com.aliyun.alink.business.devicecenter.config.model.BackupCheckType;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.log.PerformanceLog;
import com.aliyun.alink.business.devicecenter.provision.other.zero.AlinkZeroConfigStrategy;
import com.aliyun.alink.business.devicecenter.track.DCUserTrack;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTResponse;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequest;
import com.facebook.internal.NativeProtocol;
import com.umeng.message.common.inter.ITagManager;
import java.util.EnumSet;

/* loaded from: classes2.dex */
public class o implements IoTCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ p f10619a;

    public o(p pVar) {
        this.f10619a = pVar;
    }

    @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
    public void onFailure(IoTRequest ioTRequest, Exception exc) {
        PerformanceLog.trace(AlinkZeroConfigStrategy.TAG, "reqEnrolleeResult", PerformanceLog.getJsonObject("result", ITagManager.FAIL));
        DCUserTrack.addTrackData(AlinkConstants.KEY_END_TIME_REQUEST_ENROLLEE, String.valueOf(System.currentTimeMillis()));
        this.f10619a.f10620a.provisionErrorInfo = new DCErrorCode(NativeProtocol.ERROR_NETWORK_ERROR, DCErrorCode.PF_NETWORK_ERROR).setSubcode(DCErrorCode.SUBCODE_API_REQUEST_ON_FAILURE).setMsg("ZApiClientError:" + exc);
        this.f10619a.f10620a.provisionResultCallback(null);
    }

    @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
    public void onResponse(IoTRequest ioTRequest, IoTResponse ioTResponse) {
        DCUserTrack.addTrackData(AlinkConstants.KEY_END_TIME_REQUEST_ENROLLEE, String.valueOf(System.currentTimeMillis()));
        if (ioTResponse != null && ioTResponse.getCode() == 200) {
            PerformanceLog.trace(AlinkZeroConfigStrategy.TAG, "reqEnrolleeResult", PerformanceLog.getJsonObject("result", "success", "alinkid", TransitoryClient.getInstance().getTraceId(ioTResponse)));
            ALog.i(AlinkZeroConfigStrategy.TAG, "Zero requestEnrollee success.");
            this.f10619a.f10620a.updateBackupCheckTypeSet(EnumSet.of(BackupCheckType.CHECK_COAP_GET, BackupCheckType.CHECK_APP_TOKEN));
            this.f10619a.f10620a.startBackupCheck(true, 0L);
            if (this.f10619a.f10620a.provisionErrorInfo != null) {
                this.f10619a.f10620a.provisionErrorInfo.setSubcode(DCErrorCode.SUBCODE_PT_NO_CONNECTAP_NOTIFY_AND_CHECK_TOKEN_FAIL).setMsg("noConnectApOrCheckTokenSuccess");
                return;
            }
            return;
        }
        PerformanceLog.trace(AlinkZeroConfigStrategy.TAG, "reqEnrolleeResult", PerformanceLog.getJsonObject("result", ITagManager.FAIL, "alinkid", TransitoryClient.getInstance().getTraceId(ioTResponse)));
        ALog.w(AlinkZeroConfigStrategy.TAG, "ZeroRequestEnrolleeFail request=" + TransitoryClient.getInstance().requestToStr(ioTRequest) + ",response=" + TransitoryClient.getInstance().responseToStr(ioTResponse));
        if (ioTResponse == null) {
            this.f10619a.f10620a.provisionErrorInfo = new DCErrorCode(DCErrorCode.SERVER_ERROR_MSG, DCErrorCode.PF_SERVER_FAIL).setSubcode(DCErrorCode.SUBCODE_SRE_RESPONSE_EMPTY).setMsg("getCipherError");
        } else {
            this.f10619a.f10620a.provisionErrorInfo = new DCErrorCode(DCErrorCode.SERVER_ERROR_MSG, DCErrorCode.PF_SERVER_FAIL).setSubcode(ioTResponse.getCode()).setMsg(ioTResponse.getLocalizedMsg());
        }
        this.f10619a.f10620a.provisionResultCallback(null);
    }
}
