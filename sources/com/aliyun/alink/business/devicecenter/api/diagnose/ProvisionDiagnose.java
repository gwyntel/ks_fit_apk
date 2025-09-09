package com.aliyun.alink.business.devicecenter.api.diagnose;

import android.content.Context;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.aliyun.alink.business.devicecenter.base.DCErrorCode;
import com.aliyun.alink.business.devicecenter.biz.SilenceWorker;
import com.aliyun.alink.business.devicecenter.biz.worker.DeviceErrorUploadWorker;
import com.aliyun.alink.business.devicecenter.channel.coap.CoAPClient;
import com.aliyun.alink.business.devicecenter.channel.coap.request.CoapRequestPayload;
import com.aliyun.alink.business.devicecenter.config.DeviceCenterBiz;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.utils.NetworkConnectiveManager;
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
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes2.dex */
public class ProvisionDiagnose {

    /* renamed from: a, reason: collision with root package name */
    public WifiManager f10067a;

    /* renamed from: b, reason: collision with root package name */
    public NetworkConnectiveManager f10068b;

    /* renamed from: g, reason: collision with root package name */
    public Context f10073g;

    /* renamed from: c, reason: collision with root package name */
    public TimerUtils f10069c = null;

    /* renamed from: d, reason: collision with root package name */
    public DiagnoseCallback f10070d = null;

    /* renamed from: e, reason: collision with root package name */
    public AlcsCoAPRequest f10071e = null;

    /* renamed from: f, reason: collision with root package name */
    public long f10072f = -1;

    /* renamed from: h, reason: collision with root package name */
    public DiagnoseParams f10074h = null;

    /* renamed from: i, reason: collision with root package name */
    public ScheduledFuture<?> f10075i = null;

    /* renamed from: j, reason: collision with root package name */
    public AtomicBoolean f10076j = new AtomicBoolean(false);

    /* renamed from: k, reason: collision with root package name */
    public AtomicBoolean f10077k = new AtomicBoolean(false);

    /* renamed from: l, reason: collision with root package name */
    public AtomicBoolean f10078l = new AtomicBoolean(false);

    /* renamed from: m, reason: collision with root package name */
    public AtomicInteger f10079m = new AtomicInteger(0);

    /* renamed from: n, reason: collision with root package name */
    public NetworkConnectiveManager.INetworkChangeListener f10080n = new NetworkConnectiveManager.INetworkChangeListener() { // from class: com.aliyun.alink.business.devicecenter.api.diagnose.ProvisionDiagnose.1
        @Override // com.aliyun.alink.business.devicecenter.utils.NetworkConnectiveManager.INetworkChangeListener
        public void onNetworkStateChange(NetworkInfo networkInfo, Network network) {
            WifiInfo connectionInfo;
            ALog.d("ProvisionDiagnose", "onWiFiStateChange() called with: networkInfo = [" + networkInfo + "]");
            if (networkInfo == null) {
                return;
            }
            try {
                if (networkInfo.getState() != NetworkInfo.State.CONNECTED) {
                    return;
                }
                if (ProvisionDiagnose.this.f10077k.get()) {
                    ALog.d("ProvisionDiagnose", "diagnose stopped, return.");
                    return;
                }
                if (networkInfo.getType() != 1) {
                    ALog.d("ProvisionDiagnose", "scheduleGetErrorCode when device ap connected. ");
                    return;
                }
                if (ProvisionDiagnose.this.f10067a == null || ProvisionDiagnose.this.f10067a.getConnectionInfo() == null || ProvisionDiagnose.this.f10074h == null || TextUtils.isEmpty(ProvisionDiagnose.this.f10074h.deviceSSID) || (connectionInfo = ProvisionDiagnose.this.f10067a.getConnectionInfo()) == null) {
                    return;
                }
                String ssid = connectionInfo.getSSID();
                if (ssid != null) {
                    if (ssid.equals("\"" + ProvisionDiagnose.this.f10074h.deviceSSID + "\"")) {
                        if (ProvisionDiagnose.this.f10078l.get()) {
                            return;
                        }
                        ProvisionDiagnose.this.c();
                        return;
                    }
                }
                ProvisionDiagnose provisionDiagnose = ProvisionDiagnose.this;
                provisionDiagnose.a(provisionDiagnose.f10074h.deviceSSID);
            } catch (Exception e2) {
                e2.printStackTrace();
                ALog.w("ProvisionDiagnose", "handleWiFiStateChange exception=" + e2);
            }
        }
    };

    /* renamed from: o, reason: collision with root package name */
    public TimerUtils.ITimerCallback f10081o = new TimerUtils.ITimerCallback() { // from class: com.aliyun.alink.business.devicecenter.api.diagnose.ProvisionDiagnose.4
        @Override // com.aliyun.alink.business.devicecenter.utils.TimerUtils.ITimerCallback
        public void onTimeout() {
            if (ProvisionDiagnose.this.f10070d != null) {
                DiagnoseResult diagnoseResult = new DiagnoseResult();
                diagnoseResult.code = String.valueOf(DCErrorCode.ERROR_CODE_DIAGNOSE_TIMEOUT);
                diagnoseResult.errMsg = "diagnose timeout";
                if (ProvisionDiagnose.this.f10077k.get()) {
                    return;
                }
                ProvisionDiagnose.this.f10077k.set(true);
                if (ProvisionDiagnose.this.f10070d != null) {
                    ProvisionDiagnose.this.f10070d.onResult(diagnoseResult);
                }
                ProvisionDiagnose.this.stopDiagnose();
            }
        }
    };

    public ProvisionDiagnose(Context context) {
        this.f10068b = null;
        this.f10073g = null;
        if (context == null) {
            throw new RuntimeException("context cannot be null.");
        }
        this.f10073g = context;
        DeviceCenterBiz.getInstance().setAppContext(context);
        this.f10068b = NetworkConnectiveManager.getInstance();
        this.f10067a = (WifiManager) context.getApplicationContext().getSystemService("wifi");
    }

    public void startDiagnose(DiagnoseParams diagnoseParams, DiagnoseCallback diagnoseCallback) {
        ALog.d("ProvisionDiagnose", "startDiagnose() called with: params = [" + diagnoseParams + "], callback = [" + diagnoseCallback + "]");
        if (diagnoseParams == null || TextUtils.isEmpty(diagnoseParams.deviceSSID)) {
            throw new IllegalArgumentException("params error");
        }
        if (diagnoseParams.timeout < 3) {
            return;
        }
        if (this.f10076j.get()) {
            throw new IllegalStateException("diagnose has already started.");
        }
        this.f10076j.set(true);
        this.f10077k.set(false);
        this.f10078l.set(false);
        this.f10079m.set(1);
        this.f10074h = diagnoseParams;
        this.f10070d = diagnoseCallback;
        a(this.f10069c);
        TimerUtils timerUtils = new TimerUtils((this.f10074h.timeout - 2) * 1000);
        this.f10069c = timerUtils;
        timerUtils.setCallback(this.f10081o);
        this.f10069c.start(TimerUtils.MSG_DIAGNOSE);
        a();
        a(this.f10074h.deviceSSID);
    }

    public void stopDiagnose() {
        ALog.d("ProvisionDiagnose", "stopDiagnose() called");
        this.f10074h = null;
        this.f10070d = null;
        this.f10079m.set(0);
        this.f10076j.set(false);
        this.f10077k.set(true);
        this.f10078l.set(false);
        a(this.f10069c);
        a(this.f10075i);
        d();
    }

    public final void b() {
        ALog.d("ProvisionDiagnose", "getDeviceErrorCode() called");
        try {
            CoapRequestPayload coapRequestPayloadBuild = new CoapRequestPayload.Builder().version("2.0").params(new HashMap()).method("awss.device.errcode.get").build();
            a(this.f10071e);
            this.f10071e = new AlcsCoAPRequest(AlcsCoAPConstant.Code.GET, AlcsCoAPConstant.Type.NON);
            String broadcastIp = WiFiUtils.getBroadcastIp();
            StringBuilder sb = new StringBuilder();
            sb.append(broadcastIp);
            sb.append(":");
            sb.append(5683);
            sb.append("/sys/awss/device/errcode/get");
            String string = sb.toString();
            this.f10071e.setPayload(coapRequestPayloadBuild.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append("getDeviceErrorCode setPayload=");
            sb2.append(coapRequestPayloadBuild.toString());
            sb2.append(",getPayload=");
            sb2.append(this.f10071e.getPayloadString());
            ALog.llog((byte) 3, "ProvisionDiagnose", sb2.toString());
            this.f10071e.setMulticast(1);
            this.f10071e.setURI(string);
            StringBuilder sb3 = new StringBuilder();
            sb3.append("coapUri=");
            sb3.append(string);
            ALog.d("ProvisionDiagnose", sb3.toString());
        } catch (Exception e2) {
            ALog.w("ProvisionDiagnose", "pre getDeviceErrorCode sendRequest params exception=" + e2);
        }
        this.f10072f = CoAPClient.getInstance().sendRequest(this.f10071e, new IAlcsCoAPReqHandler() { // from class: com.aliyun.alink.business.devicecenter.api.diagnose.ProvisionDiagnose.3
            @Override // com.aliyun.alink.linksdk.alcs.coap.IAlcsCoAPReqHandler
            public void onReqComplete(AlcsCoAPContext alcsCoAPContext, int i2, AlcsCoAPResponse alcsCoAPResponse) {
                CoAPClient.getInstance().printResponse(alcsCoAPContext, alcsCoAPResponse);
                if (alcsCoAPResponse == null || TextUtils.isEmpty(alcsCoAPResponse.getPayloadString())) {
                    return;
                }
                ALog.llog((byte) 3, "ProvisionDiagnose", "getDeviceErrorCode responseString=" + alcsCoAPResponse.getPayloadString());
                if (ProvisionDiagnose.this.f10077k.get()) {
                    ALog.d("ProvisionDiagnose", "diagnose has stopped, return.");
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
                    ProvisionDiagnose.this.f10077k.set(true);
                    if (TextUtils.isEmpty(diagnoseResult.sign) || TextUtils.isEmpty(diagnoseResult.signSecretType)) {
                        ALog.i("ProvisionDiagnose", "sign or signSecretType is empty -> old device.");
                    } else {
                        ProvisionDiagnose.this.a(jSONObject);
                    }
                    if (ProvisionDiagnose.this.f10070d != null) {
                        ProvisionDiagnose.this.f10070d.onResult(diagnoseResult);
                    }
                    ProvisionDiagnose.this.stopDiagnose();
                } catch (Exception e3) {
                    ALog.w("ProvisionDiagnose", "getDeviceErrorCode device.errcode.get parsePayloadException= " + e3);
                }
            }
        });
    }

    public final void c() {
        a(this.f10075i);
        this.f10078l.set(true);
        this.f10075i = ThreadPool.scheduleAtFixedRate(new Runnable() { // from class: com.aliyun.alink.business.devicecenter.api.diagnose.ProvisionDiagnose.2
            @Override // java.lang.Runnable
            public void run() {
                if (ProvisionDiagnose.this.f10077k.get()) {
                    ALog.d("ProvisionDiagnose", "diagnose stopped, ignore getDeviceErrorCode and return.");
                } else {
                    ProvisionDiagnose.this.b();
                }
            }
        }, 0L, 5L, TimeUnit.SECONDS);
    }

    public final void d() {
        try {
            NetworkConnectiveManager networkConnectiveManager = this.f10068b;
            if (networkConnectiveManager != null) {
                networkConnectiveManager.unregisterConnectiveListener(this.f10080n);
            }
        } catch (Exception e2) {
            ALog.w("ProvisionDiagnose", "unregisterAPBroadcast exception=" + e2);
        }
    }

    public final void a(String str) {
        if (this.f10077k.get()) {
            return;
        }
        WiFiUtils.connect(this.f10073g, str, null, "", "", -1);
    }

    public final void a() {
        NetworkConnectiveManager networkConnectiveManager = this.f10068b;
        if (networkConnectiveManager != null) {
            networkConnectiveManager.registerConnectiveListener(this.f10080n);
        }
    }

    public final void a(JSONObject jSONObject) {
        DiagnoseParams diagnoseParams = this.f10074h;
        if (diagnoseParams != null) {
            jSONObject.put("productKey", (Object) diagnoseParams.productKey);
            jSONObject.put("deviceName", (Object) this.f10074h.deviceName);
        }
        ALog.d("ProvisionDiagnose", "uploadDeviceError() called with: jsonObject = [" + jSONObject + "]");
        SilenceWorker.getInstance().registerWorker(new DeviceErrorUploadWorker(), jSONObject);
    }

    public final void a(ScheduledFuture scheduledFuture) {
        if (scheduledFuture != null) {
            try {
                scheduledFuture.cancel(true);
            } catch (Exception unused) {
            }
        }
    }

    public final void a(TimerUtils timerUtils) {
        if (timerUtils != null) {
            timerUtils.stop(TimerUtils.MSG_DIAGNOSE);
        }
    }

    public final void a(AlcsCoAPRequest alcsCoAPRequest) {
        if (alcsCoAPRequest != null) {
            alcsCoAPRequest.cancel();
        }
        if (this.f10072f != -1) {
            CoAPClient.getInstance().cancelMessage(this.f10072f);
        }
    }
}
