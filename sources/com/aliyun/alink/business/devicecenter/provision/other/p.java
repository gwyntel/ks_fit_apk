package com.aliyun.alink.business.devicecenter.provision.other;

import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.aliyun.alink.business.devicecenter.base.DCErrorCode;
import com.aliyun.alink.business.devicecenter.channel.http.TransitoryClient;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.log.PerformanceLog;
import com.aliyun.alink.business.devicecenter.provision.other.zero.AlinkZeroConfigStrategy;
import com.aliyun.alink.business.devicecenter.track.DCUserTrack;
import com.aliyun.iot.aep.sdk.apiclient.emuns.Scheme;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequest;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequestBuilder;

/* loaded from: classes2.dex */
public class p implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ AlinkZeroConfigStrategy f10620a;

    public p(AlinkZeroConfigStrategy alinkZeroConfigStrategy) {
        this.f10620a = alinkZeroConfigStrategy;
    }

    public final void a() {
        ALog.d(AlinkZeroConfigStrategy.TAG, "startConfig requestEnrollee data=" + this.f10620a.mConfigParams);
        if (this.f10620a.provisionErrorInfo != null) {
            this.f10620a.provisionErrorInfo.setSubcode(DCErrorCode.SUBCODE_PT_REQUEST_ENROLLEE_TIMEOUT).setMsg("requestEnrolleeTimeout");
        }
        IoTRequest ioTRequestBuild = new IoTRequestBuilder().setApiVersion("1.0.2").setPath(AlinkConstants.HTTP_PATH_ENROLLEE_CONNECT).setScheme(Scheme.HTTPS).setAuthType(AlinkConstants.KEY_IOT_AUTH).addParam("regDeviceName", this.f10620a.mConfigParams.regDeviceName).addParam("regProductKey", this.f10620a.mConfigParams.regProductKey).addParam("enrolleeDeviceName", this.f10620a.mConfigParams.deviceName).addParam("enrolleeProductKey", this.f10620a.mConfigParams.productKey).build();
        PerformanceLog.trace(AlinkZeroConfigStrategy.TAG, "reqEnrollee");
        AlinkZeroConfigStrategy alinkZeroConfigStrategy = this.f10620a;
        alinkZeroConfigStrategy.cancelRequest(alinkZeroConfigStrategy.retryTransitoryClient);
        this.f10620a.retryTransitoryClient = TransitoryClient.getInstance().asynRequest(ioTRequestBuild, new o(this));
    }

    @Override // java.lang.Runnable
    public void run() {
        DCUserTrack.addTrackData(AlinkConstants.KEY_START_TIME_REQUEST_ENROLLEE, String.valueOf(System.currentTimeMillis()));
        a();
    }
}
