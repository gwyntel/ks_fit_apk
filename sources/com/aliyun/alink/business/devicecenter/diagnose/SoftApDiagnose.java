package com.aliyun.alink.business.devicecenter.diagnose;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.alink.business.devicecenter.api.diagnose.DiagnoseResult;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.aliyun.alink.business.devicecenter.biz.SilenceWorker;
import com.aliyun.alink.business.devicecenter.biz.worker.DeviceErrorUploadWorker;
import com.aliyun.alink.business.devicecenter.channel.coap.CoAPClient;
import com.aliyun.alink.business.devicecenter.channel.coap.request.CoapRequestPayload;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.utils.ThreadPool;
import com.aliyun.alink.business.devicecenter.utils.TimerUtils;
import com.aliyun.alink.business.devicecenter.utils.WiFiUtils;
import com.aliyun.alink.linksdk.alcs.coap.AlcsCoAPConstant;
import com.aliyun.alink.linksdk.alcs.coap.AlcsCoAPContext;
import com.aliyun.alink.linksdk.alcs.coap.AlcsCoAPRequest;
import com.aliyun.alink.linksdk.alcs.coap.AlcsCoAPResponse;
import com.aliyun.alink.linksdk.alcs.coap.IAlcsCoAPReqHandler;
import java.util.HashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
public class SoftApDiagnose {

    /* renamed from: f, reason: collision with root package name */
    public String f10405f;

    /* renamed from: g, reason: collision with root package name */
    public String f10406g;

    /* renamed from: a, reason: collision with root package name */
    public AlcsCoAPRequest f10400a = null;

    /* renamed from: b, reason: collision with root package name */
    public long f10401b = -1;

    /* renamed from: c, reason: collision with root package name */
    public ScheduledFuture<?> f10402c = null;

    /* renamed from: d, reason: collision with root package name */
    public final Object f10403d = new Object();

    /* renamed from: e, reason: collision with root package name */
    public TimerUtils f10404e = null;

    /* renamed from: h, reason: collision with root package name */
    public final TimerUtils.ITimerCallback f10407h = new TimerUtils.ITimerCallback() { // from class: com.aliyun.alink.business.devicecenter.diagnose.SoftApDiagnose.1
        @Override // com.aliyun.alink.business.devicecenter.utils.TimerUtils.ITimerCallback
        public void onTimeout() {
            ALog.w("SoftApDiagnose", "diagnose timeout in " + SoftApDiagnose.this.f10404e.getTimeout() + "s");
            SoftApDiagnose.this.stopDiagnose();
        }
    };

    private static class SingletonHolder {

        /* renamed from: a, reason: collision with root package name */
        public static final SoftApDiagnose f10411a = new SoftApDiagnose();
    }

    public static SoftApDiagnose getInstance() {
        return SingletonHolder.f10411a;
    }

    public void startDiagnose(String str, String str2, int i2) {
        ALog.w("SoftApDiagnose", "start diagnose.");
        synchronized (this.f10403d) {
            try {
                if (this.f10402c != null) {
                    ALog.w("SoftApDiagnose", "diagnose has started.");
                    return;
                }
                this.f10405f = str;
                this.f10406g = str2;
                this.f10402c = ThreadPool.scheduleAtFixedRate(new Runnable() { // from class: com.aliyun.alink.business.devicecenter.diagnose.SoftApDiagnose.2
                    @Override // java.lang.Runnable
                    public void run() {
                        SoftApDiagnose.this.b();
                    }
                }, 0L, 5L, TimeUnit.SECONDS);
                TimerUtils timerUtils = new TimerUtils(a(i2) * 1000);
                this.f10404e = timerUtils;
                timerUtils.setCallback(this.f10407h);
                this.f10404e.start(TimerUtils.MSG_DIAGNOSE);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void stopDiagnose() {
        ALog.d("SoftApDiagnose", "stop diagnose.");
        synchronized (this.f10403d) {
            try {
                ScheduledFuture<?> scheduledFuture = this.f10402c;
                if (scheduledFuture != null) {
                    scheduledFuture.cancel(true);
                    this.f10402c = null;
                }
                TimerUtils timerUtils = this.f10404e;
                if (timerUtils != null) {
                    timerUtils.stop(TimerUtils.MSG_DIAGNOSE);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void b() {
        ALog.d("SoftApDiagnose", "getDeviceErrorCode() called");
        try {
            CoapRequestPayload coapRequestPayloadBuild = new CoapRequestPayload.Builder().version("2.0").params(new HashMap()).method("awss.device.errcode.get").build();
            a();
            this.f10400a = new AlcsCoAPRequest(AlcsCoAPConstant.Code.GET, AlcsCoAPConstant.Type.NON);
            String broadcastIp = WiFiUtils.getBroadcastIp();
            StringBuilder sb = new StringBuilder();
            sb.append(broadcastIp);
            sb.append(":");
            sb.append(5683);
            sb.append("/sys/awss/device/errcode/get");
            String string = sb.toString();
            this.f10400a.setPayload(coapRequestPayloadBuild.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append("getDeviceErrorCode setPayload=");
            sb2.append(coapRequestPayloadBuild.toString());
            sb2.append(",getPayload=");
            sb2.append(this.f10400a.getPayloadString());
            ALog.llog((byte) 3, "SoftApDiagnose", sb2.toString());
            this.f10400a.setMulticast(1);
            this.f10400a.setURI(string);
            StringBuilder sb3 = new StringBuilder();
            sb3.append("coapUri=");
            sb3.append(string);
            ALog.d("SoftApDiagnose", sb3.toString());
        } catch (Exception e2) {
            ALog.w("SoftApDiagnose", "pre getDeviceErrorCode sendRequest params exception=" + e2);
        }
        this.f10401b = CoAPClient.getInstance().sendRequest(this.f10400a, new IAlcsCoAPReqHandler() { // from class: com.aliyun.alink.business.devicecenter.diagnose.SoftApDiagnose.3
            @Override // com.aliyun.alink.linksdk.alcs.coap.IAlcsCoAPReqHandler
            public void onReqComplete(AlcsCoAPContext alcsCoAPContext, int i2, AlcsCoAPResponse alcsCoAPResponse) {
                CoAPClient.getInstance().printResponse(alcsCoAPContext, alcsCoAPResponse);
                if (alcsCoAPResponse == null || TextUtils.isEmpty(alcsCoAPResponse.getPayloadString())) {
                    return;
                }
                ALog.llog((byte) 3, "SoftApDiagnose", "getDeviceErrorCode responseString=" + alcsCoAPResponse.getPayloadString());
                if (alcsCoAPResponse.getMID() != SoftApDiagnose.this.f10401b) {
                    ALog.d("SoftApDiagnose", "old coap message id, return.");
                    return;
                }
                try {
                    JSONObject jSONObject = JSON.parseObject(alcsCoAPResponse.getPayloadString()).getJSONObject("data");
                    DiagnoseResult diagnoseResult = new DiagnoseResult();
                    diagnoseResult.code = jSONObject.getString("code");
                    diagnoseResult.codeVer = jSONObject.getString(AlinkConstants.KEY_CODE_VER);
                    diagnoseResult.state = jSONObject.getString("state");
                    diagnoseResult.errMsg = jSONObject.getString(AlinkConstants.KEY_ERR_MSG);
                    diagnoseResult.sign = jSONObject.getString("sign");
                    diagnoseResult.signSecretType = jSONObject.getString(AlinkConstants.KEY_SIGN_SECRET_TYPE);
                    if (TextUtils.isEmpty(diagnoseResult.sign) || TextUtils.isEmpty(diagnoseResult.signSecretType)) {
                        ALog.i("SoftApDiagnose", "sign or signSecretType is empty -> old device.");
                    } else if ("0".equals(diagnoseResult.code)) {
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append("not upload error code 0, errMsg: ");
                        sb4.append(diagnoseResult.errMsg);
                        ALog.i("SoftApDiagnose", sb4.toString());
                    } else {
                        SoftApDiagnose.this.a(jSONObject);
                    }
                    SoftApDiagnose.this.stopDiagnose();
                } catch (Exception e3) {
                    ALog.w("SoftApDiagnose", "getDeviceErrorCode device.errcode.get parsePayloadException= " + e3);
                }
            }
        });
    }

    public final int a(int i2) {
        return Math.min(Math.max(i2, 5), 30);
    }

    public final void a(JSONObject jSONObject) {
        String str = this.f10405f;
        if (str != null || this.f10406g != null) {
            jSONObject.put("productKey", (Object) str);
            jSONObject.put("deviceName", (Object) this.f10406g);
        }
        ALog.d("SoftApDiagnose", "uploadDeviceError() called with: jsonObject = [" + jSONObject + "]");
        SilenceWorker.getInstance().registerWorker(new DeviceErrorUploadWorker(), jSONObject);
    }

    public final void a() {
        AlcsCoAPRequest alcsCoAPRequest = this.f10400a;
        if (alcsCoAPRequest != null) {
            alcsCoAPRequest.cancel();
            this.f10400a = null;
        }
        if (this.f10401b != -1) {
            CoAPClient.getInstance().cancelMessage(this.f10401b);
        }
    }
}
