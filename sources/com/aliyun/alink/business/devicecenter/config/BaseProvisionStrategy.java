package com.aliyun.alink.business.devicecenter.config;

import android.text.TextUtils;
import com.alibaba.ailabs.tg.basebiz.user.UserManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.aliyun.alink.business.devicecenter.api.add.DeviceBindResultInfo;
import com.aliyun.alink.business.devicecenter.api.add.DeviceInfo;
import com.aliyun.alink.business.devicecenter.api.add.LKDeviceInfo;
import com.aliyun.alink.business.devicecenter.api.add.ProvisionStatus;
import com.aliyun.alink.business.devicecenter.base.DCEnvHelper;
import com.aliyun.alink.business.devicecenter.base.DCErrorCode;
import com.aliyun.alink.business.devicecenter.base.LocalDevice;
import com.aliyun.alink.business.devicecenter.biz.ProvisionRepository;
import com.aliyun.alink.business.devicecenter.biz.model.CheckBindTokenMtopResponse;
import com.aliyun.alink.business.devicecenter.biz.model.CheckBindTokenRequest;
import com.aliyun.alink.business.devicecenter.biz.model.CheckBindTokenResponse;
import com.aliyun.alink.business.devicecenter.cache.CacheCenter;
import com.aliyun.alink.business.devicecenter.cache.CacheType;
import com.aliyun.alink.business.devicecenter.cache.DeviceInfoICacheModel;
import com.aliyun.alink.business.devicecenter.cache.ProvisionDeviceInfoCache;
import com.aliyun.alink.business.devicecenter.channel.coap.CoAPClient;
import com.aliyun.alink.business.devicecenter.channel.coap.response.CoapResponsePayload;
import com.aliyun.alink.business.devicecenter.channel.coap.response.DevicePayload;
import com.aliyun.alink.business.devicecenter.channel.http.ApiRequestClient;
import com.aliyun.alink.business.devicecenter.channel.http.DCError;
import com.aliyun.alink.business.devicecenter.channel.http.IRequestCallback;
import com.aliyun.alink.business.devicecenter.channel.http.RetryTransitoryClient;
import com.aliyun.alink.business.devicecenter.channel.http.TransitoryClient;
import com.aliyun.alink.business.devicecenter.config.model.BackupCheckType;
import com.aliyun.alink.business.devicecenter.config.model.DCAlibabaConfigParams;
import com.aliyun.alink.business.devicecenter.config.model.DeviceReportTokenType;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.model.CheckTokenModel;
import com.aliyun.alink.business.devicecenter.utils.NetworkEnvironmentUtils;
import com.aliyun.alink.business.devicecenter.utils.ThreadPool;
import com.aliyun.alink.business.devicecenter.utils.TimerUtils;
import com.aliyun.alink.business.devicecenter.utils.WiFiUtils;
import com.aliyun.alink.linksdk.alcs.coap.AlcsCoAPContext;
import com.aliyun.alink.linksdk.alcs.coap.AlcsCoAPRequest;
import com.aliyun.alink.linksdk.alcs.coap.AlcsCoAPResponse;
import com.aliyun.alink.linksdk.alcs.coap.IAlcsCoAPReqHandler;
import com.aliyun.alink.linksdk.alcs.coap.IAlcsCoAPResHandler;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTResponse;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequest;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes2.dex */
public abstract class BaseProvisionStrategy {
    public static String TAG = "BaseProvisionStrategy";

    /* renamed from: g, reason: collision with root package name */
    public ConcurrentHashMap<String, Object> f10360g;

    /* renamed from: h, reason: collision with root package name */
    public List<CheckTokenModel> f10361h;

    /* renamed from: a, reason: collision with root package name */
    public Future f10354a = null;

    /* renamed from: b, reason: collision with root package name */
    public AlcsCoAPRequest f10355b = null;

    /* renamed from: c, reason: collision with root package name */
    public long f10356c = -1;

    /* renamed from: d, reason: collision with root package name */
    public IAlcsCoAPResHandler f10357d = null;
    public IDeviceInfoNotifyListener mNotifyListner = null;

    /* renamed from: e, reason: collision with root package name */
    public AtomicBoolean f10358e = new AtomicBoolean(true);
    public AtomicBoolean waitForResult = new AtomicBoolean(false);

    /* renamed from: f, reason: collision with root package name */
    public AtomicBoolean f10359f = new AtomicBoolean(false);
    public AtomicBoolean provisionHasStopped = new AtomicBoolean(false);
    public RetryTransitoryClient retryTransitoryClient = null;
    public DCAlibabaConfigParams mConfigParams = null;
    public IConfigCallback mConfigCallback = null;
    public DCErrorCode provisionErrorInfo = null;
    public TimerUtils provisionTimer = null;
    public TimerUtils provisionNetInfoTimer = null;

    /* renamed from: i, reason: collision with root package name */
    public int f10362i = 10;

    /* renamed from: j, reason: collision with root package name */
    public int f10363j = 3;

    /* renamed from: k, reason: collision with root package name */
    public EnumSet<BackupCheckType> f10364k = EnumSet.of(BackupCheckType.CHECK_APP_TOKEN, BackupCheckType.CHECK_COAP_GET);

    /* renamed from: l, reason: collision with root package name */
    public ApiRequestClient f10365l = new ApiRequestClient(false);

    /* renamed from: m, reason: collision with root package name */
    public IRequestCallback f10366m = null;

    /* renamed from: n, reason: collision with root package name */
    public IAlcsCoAPReqHandler f10367n = new IAlcsCoAPReqHandler() { // from class: com.aliyun.alink.business.devicecenter.config.BaseProvisionStrategy.4
        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.aliyun.alink.linksdk.alcs.coap.IAlcsCoAPReqHandler
        public void onReqComplete(AlcsCoAPContext alcsCoAPContext, int i2, AlcsCoAPResponse alcsCoAPResponse) {
            CoapResponsePayload coapResponsePayload;
            CoAPClient.getInstance().printResponse(alcsCoAPContext, alcsCoAPResponse);
            if (alcsCoAPResponse == null || TextUtils.isEmpty(alcsCoAPResponse.getPayloadString())) {
                return;
            }
            ALog.llog((byte) 3, BaseProvisionStrategy.TAG, "waitForResult = " + BaseProvisionStrategy.this.waitForResult.get() + ", responseString=" + alcsCoAPResponse.getPayloadString());
            try {
                if (BaseProvisionStrategy.this.waitForResult.get() && (coapResponsePayload = (CoapResponsePayload) JSON.parseObject(alcsCoAPResponse.getPayloadString(), new TypeReference<CoapResponsePayload<LocalDevice>>() { // from class: com.aliyun.alink.business.devicecenter.config.BaseProvisionStrategy.4.1
                }.getType(), new Feature[0])) != null && coapResponsePayload.data != 0 && BaseProvisionStrategy.this.waitForResult.get()) {
                    DeviceInfo deviceInfoConvertLocalDevice = DeviceInfo.convertLocalDevice((LocalDevice) coapResponsePayload.data);
                    if (deviceInfoConvertLocalDevice != null && !TextUtils.isEmpty(deviceInfoConvertLocalDevice.productKey) && !TextUtils.isEmpty(deviceInfoConvertLocalDevice.deviceName)) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(deviceInfoConvertLocalDevice.productKey);
                        sb.append("&");
                        sb.append(deviceInfoConvertLocalDevice.deviceName);
                        String string = sb.toString();
                        if (BaseProvisionStrategy.this.f10358e.get() && BaseProvisionStrategy.this.f10360g.containsKey(string)) {
                            String str = BaseProvisionStrategy.TAG;
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append("device=");
                            sb2.append(string);
                            sb2.append(" has already returned.");
                            ALog.i(str, sb2.toString());
                            return;
                        }
                        ALog.d(BaseProvisionStrategy.TAG, "coAP provision success");
                        BaseProvisionStrategy baseProvisionStrategy = BaseProvisionStrategy.this;
                        if (baseProvisionStrategy.mNotifyListner != null) {
                            baseProvisionStrategy.f10360g.put(string, Boolean.TRUE);
                            BaseProvisionStrategy.this.mNotifyListner.onDeviceFound(deviceInfoConvertLocalDevice);
                            return;
                        }
                        return;
                    }
                    String str2 = BaseProvisionStrategy.TAG;
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("pk or dn invalid, device=");
                    sb3.append(deviceInfoConvertLocalDevice);
                    ALog.i(str2, sb3.toString());
                }
            } catch (Exception e2) {
                ALog.w(BaseProvisionStrategy.TAG, "startDiscovery device.info.get parsePayloadException= " + e2);
            }
        }
    };

    public BaseProvisionStrategy() {
        this.f10360g = null;
        this.f10361h = null;
        this.f10360g = new ConcurrentHashMap<>();
        this.f10361h = Collections.synchronizedList(new ArrayList());
    }

    public void addProvisionOverListener(IDeviceInfoNotifyListener iDeviceInfoNotifyListener) {
        addProvisionOverListener(iDeviceInfoNotifyListener, true);
    }

    public void cancelRequest(AlcsCoAPRequest alcsCoAPRequest, long j2) {
        if (alcsCoAPRequest != null) {
            alcsCoAPRequest.cancel();
        }
        if (j2 != -1) {
            CoAPClient.getInstance().cancelMessage(j2);
        }
    }

    public String getBroadcastIp() {
        return WiFiUtils.getBroadcastIp();
    }

    public boolean isProvisionTimerStarted() {
        TimerUtils timerUtils = this.provisionTimer;
        return timerUtils != null && timerUtils.isStart(TimerUtils.MSG_PROVISION_TIMEOUT);
    }

    public void provisionResCallback(DeviceInfo deviceInfo) {
        if (deviceInfo == null || ((TextUtils.isEmpty(deviceInfo.productId) && TextUtils.isEmpty(deviceInfo.productKey)) || TextUtils.isEmpty(deviceInfo.deviceName))) {
            DeviceCenterBiz.getInstance().onConfigCallback(new ConfigCallbackWrapper().callback(this.mConfigCallback).success(false).error(this.provisionErrorInfo));
        } else {
            DeviceCenterBiz.getInstance().onConfigCallback(new ConfigCallbackWrapper().callback(this.mConfigCallback).success(true).result(deviceInfo));
        }
    }

    public void provisionResultCallback(DeviceInfo deviceInfo) {
        if (deviceInfo == null || TextUtils.isEmpty(deviceInfo.productKey) || TextUtils.isEmpty(deviceInfo.deviceName)) {
            DeviceCenterBiz.getInstance().onConfigCallback(new ConfigCallbackWrapper().callback(this.mConfigCallback).success(false).error(this.provisionErrorInfo));
        } else {
            DeviceCenterBiz.getInstance().onConfigCallback(new ConfigCallbackWrapper().callback(this.mConfigCallback).success(true).result(deviceInfo));
        }
    }

    public void provisionStatusCallback(ProvisionStatus provisionStatus) {
        ALog.i(TAG, "provisionStatusCallback, status: " + provisionStatus);
        DeviceCenterBiz.getInstance().onConfigCallback(new ConfigCallbackWrapper().callback(this.mConfigCallback).status(provisionStatus));
    }

    public void removeProvisionOverListener() {
        ALog.d(TAG, "removePOverListener() called");
        this.waitForResult.set(false);
        this.f10360g.clear();
        cancelRequest(this.f10355b, this.f10356c);
        this.mNotifyListner = null;
        a();
        stopBackupCheck();
    }

    public void setCallbackOnce(boolean z2) {
        this.f10358e.set(z2);
    }

    public void setProvisionResultCallback() {
        TimerUtils timerUtils = new TimerUtils(15000);
        this.provisionNetInfoTimer = timerUtils;
        timerUtils.setCallback(new TimerUtils.ITimerCallback() { // from class: t.a
            @Override // com.aliyun.alink.business.devicecenter.utils.TimerUtils.ITimerCallback
            public final void onTimeout() {
                this.f26834a.c();
            }
        });
        this.provisionNetInfoTimer.start(TimerUtils.MSG_GET_NETWORK_ENV_TIMEOUT);
        ThreadPool.execute(new Runnable() { // from class: t.b
            @Override // java.lang.Runnable
            public final void run() {
                this.f26835a.d();
            }
        });
    }

    public void startBackupCheck(boolean z2, long j2) {
        DCAlibabaConfigParams dCAlibabaConfigParams;
        ALog.d(TAG, "startBackupCheck() called with: needSend = [" + z2 + "], delay = [" + j2 + "]], checkTypeList = [" + this.f10364k + "]");
        if (z2 && this.f10359f.get()) {
            ALog.d(TAG, "startBackupCheck has already started.");
            return;
        }
        EnumSet<BackupCheckType> enumSet = this.f10364k;
        if (enumSet == null || enumSet.isEmpty()) {
            ALog.d(TAG, "startBackupCheck  invalid, return.");
            return;
        }
        this.f10359f.set(z2);
        try {
            if (!this.f10359f.get()) {
                this.f10361h.clear();
                a(this.f10354a);
                return;
            }
            if (this.waitForResult.get() && (dCAlibabaConfigParams = this.mConfigParams) != null && !TextUtils.isEmpty(dCAlibabaConfigParams.bindToken)) {
                a(this.mConfigCallback, this.mConfigParams.bindToken);
            }
            a(this.f10354a);
            this.f10354a = ThreadPool.scheduleAtFixedRate(new Runnable() { // from class: com.aliyun.alink.business.devicecenter.config.BaseProvisionStrategy.1
                @Override // java.lang.Runnable
                public void run() {
                    ALog.d(BaseProvisionStrategy.TAG, "run waitForResult=" + BaseProvisionStrategy.this.waitForResult.get() + ",needBackupCheck=" + BaseProvisionStrategy.this.f10359f.get() + ", mCheckTypeEnumSet=" + BaseProvisionStrategy.this.f10364k);
                    if (BaseProvisionStrategy.this.waitForResult.get() && BaseProvisionStrategy.this.f10359f.get()) {
                        synchronized (BaseProvisionStrategy.this.f10364k) {
                            try {
                                if (BaseProvisionStrategy.this.f10364k != null && !BaseProvisionStrategy.this.f10364k.isEmpty()) {
                                    boolean zContains = BaseProvisionStrategy.this.f10364k.contains(BackupCheckType.CHECK_APP_TOKEN);
                                    boolean zContains2 = BaseProvisionStrategy.this.f10364k.contains(BackupCheckType.CHECK_CLOUD_TOKEN);
                                    boolean zContains3 = BaseProvisionStrategy.this.f10364k.contains(BackupCheckType.CHECK_COAP_GET);
                                    String str = BaseProvisionStrategy.TAG;
                                    StringBuilder sb = new StringBuilder();
                                    sb.append("startBackupCheck checkToken=");
                                    sb.append(zContains);
                                    sb.append(", checkILopToken=");
                                    sb.append(zContains2);
                                    sb.append(", checkCoAPGet=");
                                    sb.append(zContains3);
                                    ALog.d(str, sb.toString());
                                    if (zContains3) {
                                        BaseProvisionStrategy.this.b();
                                    }
                                    DCAlibabaConfigParams dCAlibabaConfigParams2 = BaseProvisionStrategy.this.mConfigParams;
                                    if (dCAlibabaConfigParams2 != null && !TextUtils.isEmpty(dCAlibabaConfigParams2.bindToken)) {
                                        if (zContains) {
                                            BaseProvisionStrategy baseProvisionStrategy = BaseProvisionStrategy.this;
                                            DCAlibabaConfigParams dCAlibabaConfigParams3 = baseProvisionStrategy.mConfigParams;
                                            if (!dCAlibabaConfigParams3.isInSide) {
                                                baseProvisionStrategy.c(dCAlibabaConfigParams3.productKey, dCAlibabaConfigParams3.deviceName, dCAlibabaConfigParams3.bindToken);
                                            }
                                        }
                                        if (zContains2) {
                                            BaseProvisionStrategy baseProvisionStrategy2 = BaseProvisionStrategy.this;
                                            DCAlibabaConfigParams dCAlibabaConfigParams4 = baseProvisionStrategy2.mConfigParams;
                                            baseProvisionStrategy2.a(dCAlibabaConfigParams4.productKey, dCAlibabaConfigParams4.deviceName, dCAlibabaConfigParams4.bindToken);
                                            BaseProvisionStrategy baseProvisionStrategy3 = BaseProvisionStrategy.this;
                                            DCAlibabaConfigParams dCAlibabaConfigParams5 = baseProvisionStrategy3.mConfigParams;
                                            baseProvisionStrategy3.b(dCAlibabaConfigParams5.productKey, dCAlibabaConfigParams5.deviceName, dCAlibabaConfigParams5.bindToken);
                                        }
                                    }
                                }
                            } finally {
                            }
                        }
                    }
                }
            }, j2, this.f10363j, TimeUnit.SECONDS);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void startProvisionTimer() {
        startProvisionTimer(true, null);
    }

    public void stopBackupCheck() {
        startBackupCheck(false, 0L);
    }

    public void stopProvisionTimer() {
        ALog.d(TAG, "stopProvisionTimer() called");
        TimerUtils timerUtils = this.provisionTimer;
        if (timerUtils != null) {
            timerUtils.stop(TimerUtils.MSG_PROVISION_TIMEOUT);
            this.provisionTimer = null;
        }
        TimerUtils timerUtils2 = this.provisionNetInfoTimer;
        if (timerUtils2 != null) {
            timerUtils2.stop(TimerUtils.MSG_GET_NETWORK_ENV_TIMEOUT);
            this.provisionNetInfoTimer = null;
        }
    }

    public void updateBackupCheckType(DeviceReportTokenType deviceReportTokenType) {
        updateBackupCheckType(deviceReportTokenType, true);
    }

    public void updateBackupCheckTypeSet(EnumSet<BackupCheckType> enumSet) {
        ALog.d(TAG, "updateBackupCheckTypeSet() called with: BackupCheckType = [" + enumSet + "]");
        this.f10364k = enumSet;
    }

    public void updateCache(DeviceInfo deviceInfo, DeviceReportTokenType deviceReportTokenType) {
        DCAlibabaConfigParams dCAlibabaConfigParams;
        int i2;
        if (deviceInfo == null || TextUtils.isEmpty(deviceInfo.productKey) || TextUtils.isEmpty(deviceInfo.deviceName)) {
            return;
        }
        DeviceBindResultInfo deviceBindResultInfo = deviceInfo.bindResultInfo;
        if (deviceBindResultInfo != null && !TextUtils.isEmpty(deviceBindResultInfo.productKey) && !TextUtils.isEmpty(deviceInfo.bindResultInfo.deviceName) && ((i2 = deviceInfo.bindResultInfo.bindResult) == 1 || i2 == 2)) {
            ALog.d(TAG, "bind result returned, do not cache.");
            return;
        }
        ProvisionDeviceInfoCache.getInstance().clearCache();
        DevicePayload devicePayload = new DevicePayload();
        devicePayload.productKey = deviceInfo.productKey;
        devicePayload.deviceName = deviceInfo.deviceName;
        if (TextUtils.isEmpty(deviceInfo.token)) {
            devicePayload.token = null;
            devicePayload.remainTime = null;
        } else {
            devicePayload.token = deviceInfo.token;
            devicePayload.remainTime = TextUtils.isEmpty(deviceInfo.remainTime) ? String.valueOf(30000) : deviceInfo.remainTime;
        }
        ProvisionDeviceInfoCache.getInstance().updateCache(devicePayload);
        CacheCenter cacheCenter = CacheCenter.getInstance();
        CacheType cacheType = CacheType.APP_SEND_TOKEN;
        cacheCenter.clearCache(cacheType);
        if (!TextUtils.isEmpty(deviceInfo.token) || (dCAlibabaConfigParams = this.mConfigParams) == null || TextUtils.isEmpty(dCAlibabaConfigParams.bindToken)) {
            return;
        }
        ALog.d(TAG, "update TryCheckTokenCache with bindToken=" + this.mConfigParams.bindToken + ",deviceInfo=" + deviceInfo);
        ArrayList arrayList = new ArrayList();
        DeviceInfoICacheModel deviceInfoICacheModel = new DeviceInfoICacheModel();
        deviceInfoICacheModel.productKey = deviceInfo.productKey;
        deviceInfoICacheModel.deviceName = deviceInfo.deviceName;
        deviceInfoICacheModel.token = this.mConfigParams.bindToken;
        deviceInfoICacheModel.aliveTime = System.currentTimeMillis() + (a(deviceInfo.remainTime) < 0 ? 45000L : a(deviceInfo.remainTime));
        deviceInfoICacheModel.deviceReportTokenType = deviceReportTokenType;
        arrayList.add(deviceInfoICacheModel);
        CacheCenter.getInstance().updateCache(cacheType, (List) arrayList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void d() {
        try {
            if (this.provisionHasStopped.get()) {
                return;
            }
            HashMap<String, String> mapPing = NetworkEnvironmentUtils.ping("g-aicloud.alibaba.com", false);
            if (mapPing != null && TextUtils.isEmpty(mapPing.get("res"))) {
                mapPing.put("res", "ping error");
            }
            if (mapPing != null) {
                mapPing.remove("res");
            }
            HashMap<String, String> mapPing2 = NetworkEnvironmentUtils.ping("cn-hangzhou.log.aliyuncs.com", false);
            if (mapPing2 != null && TextUtils.isEmpty(mapPing2.get("res"))) {
                mapPing2.put("res", "ping error");
            }
            if (mapPing != null) {
                mapPing2.remove("res");
            }
            JSONArray jSONArray = new JSONArray();
            JSONObject jSONObject = new JSONObject();
            jSONObject.putAll(mapPing);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.putAll(mapPing2);
            jSONArray.add(jSONObject);
            jSONArray.add(jSONObject2);
            this.provisionErrorInfo.setExtra(jSONArray);
            if (mapPing != null) {
                for (String str : mapPing.keySet()) {
                    ALog.d(TAG, "setProvisionResultCallback: key：" + str + ";value=" + mapPing.get(str));
                }
            }
            if (mapPing2 != null) {
                for (String str2 : mapPing2.keySet()) {
                    ALog.d(TAG, "setProvisionResultCallback: key：" + str2 + ";value=" + mapPing2.get(str2));
                }
            }
            TimerUtils timerUtils = this.provisionNetInfoTimer;
            if (timerUtils != null) {
                timerUtils.stop(TimerUtils.MSG_GET_NETWORK_ENV_TIMEOUT);
                this.provisionNetInfoTimer = null;
            }
            provisionResultCallback(null);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void addProvisionOverListener(final IDeviceInfoNotifyListener iDeviceInfoNotifyListener, boolean z2) {
        removeProvisionOverListener();
        this.f10360g.clear();
        this.mNotifyListner = iDeviceInfoNotifyListener;
        this.waitForResult.set(true);
        if (!z2 || this.mConfigParams.isInSide) {
            return;
        }
        this.f10357d = new CoAPProvisionOverNotifyHandler(new IDeviceInfoNotifyListener() { // from class: com.aliyun.alink.business.devicecenter.config.BaseProvisionStrategy.3
            @Override // com.aliyun.alink.business.devicecenter.config.IDeviceInfoNotifyListener
            public void onDeviceFound(DeviceInfo deviceInfo) {
                ALog.d(BaseProvisionStrategy.TAG, "waitForResult=" + BaseProvisionStrategy.this.waitForResult.get() + ", listener=" + iDeviceInfoNotifyListener);
                if (deviceInfo == null || TextUtils.isEmpty(deviceInfo.productKey) || TextUtils.isEmpty(deviceInfo.deviceName) || !BaseProvisionStrategy.this.waitForResult.get()) {
                    return;
                }
                String str = deviceInfo.productKey + "&" + deviceInfo.deviceName;
                if (!BaseProvisionStrategy.this.f10358e.get() || !BaseProvisionStrategy.this.f10360g.containsKey(str)) {
                    if (iDeviceInfoNotifyListener != null) {
                        BaseProvisionStrategy.this.f10360g.put(str, Boolean.TRUE);
                        iDeviceInfoNotifyListener.onDeviceFound(deviceInfo);
                        return;
                    }
                    return;
                }
                ALog.i(BaseProvisionStrategy.TAG, "device=" + str + " has already returned.");
            }

            @Override // com.aliyun.alink.business.devicecenter.config.IDeviceInfoNotifyListener
            public void onFailure(DCErrorCode dCErrorCode) {
            }
        });
        CoAPClient.getInstance().addNotifyListener(this.f10357d);
    }

    public void startProvisionTimer(TimerUtils.ITimerCallback iTimerCallback) {
        startProvisionTimer(true, iTimerCallback);
    }

    public void updateBackupCheckType(DeviceReportTokenType deviceReportTokenType, boolean z2) {
        ALog.d(TAG, "updateBackupCheckTypeSet() called with: type = [" + deviceReportTokenType + "]");
        if (deviceReportTokenType == DeviceReportTokenType.APP_TOKEN) {
            updateBackupCheckTypeSet(z2 ? EnumSet.of(BackupCheckType.CHECK_COAP_GET, BackupCheckType.CHECK_APP_TOKEN) : EnumSet.of(BackupCheckType.CHECK_APP_TOKEN));
        } else if (deviceReportTokenType == DeviceReportTokenType.CLOUD_TOKEN) {
            updateBackupCheckTypeSet(z2 ? EnumSet.of(BackupCheckType.CHECK_COAP_GET, BackupCheckType.CHECK_CLOUD_TOKEN) : EnumSet.of(BackupCheckType.CHECK_CLOUD_TOKEN));
        } else if (deviceReportTokenType == DeviceReportTokenType.UNKNOWN) {
            updateBackupCheckTypeSet(z2 ? EnumSet.of(BackupCheckType.CHECK_COAP_GET, BackupCheckType.CHECK_APP_TOKEN, BackupCheckType.CHECK_CLOUD_TOKEN) : EnumSet.of(BackupCheckType.CHECK_COAP_GET, BackupCheckType.CHECK_APP_TOKEN, BackupCheckType.CHECK_CLOUD_TOKEN));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x00aa  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00ad A[Catch: Exception -> 0x0055, TRY_ENTER, TryCatch #2 {Exception -> 0x0055, blocks: (B:2:0x0000, B:4:0x000c, B:6:0x001b, B:8:0x003d, B:10:0x0041, B:11:0x0044, B:16:0x0059, B:18:0x0062, B:20:0x0071, B:22:0x007a, B:33:0x00b1, B:35:0x00bb, B:37:0x00c5, B:39:0x00de, B:41:0x00ea, B:43:0x0112, B:32:0x00ad, B:26:0x0091, B:28:0x009a, B:23:0x008b), top: B:52:0x0000, inners: #0, #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void b() throws java.net.UnknownHostException {
        /*
            Method dump skipped, instructions count: 325
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aliyun.alink.business.devicecenter.config.BaseProvisionStrategy.b():void");
    }

    public final void c(String str, String str2, final String str3) {
        ALog.d(TAG, "checkToken() called with: pk = [" + str + "], dn = [" + str2 + "], token = [" + str3 + "]");
        try {
            if (!DCEnvHelper.hasApiClient()) {
                ALog.w(TAG, "checkToken no apiclient, return.");
            } else {
                ProvisionRepository.checkToken(str, str2, str3, new IoTCallback() { // from class: com.aliyun.alink.business.devicecenter.config.BaseProvisionStrategy.6
                    @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
                    public void onFailure(IoTRequest ioTRequest, Exception exc) {
                        ALog.w(BaseProvisionStrategy.TAG, "checkToken onFailure e=" + exc);
                    }

                    @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
                    public void onResponse(IoTRequest ioTRequest, IoTResponse ioTResponse) {
                        ALog.d(BaseProvisionStrategy.TAG, "checkToken onResponse request=" + TransitoryClient.getInstance().requestToStr(ioTRequest) + ",response=" + TransitoryClient.getInstance().responseToStr(ioTResponse));
                        try {
                            if (BaseProvisionStrategy.this.waitForResult.get() && ioTResponse != null && ioTResponse.getCode() == 200 && ioTResponse.getData() != null) {
                                DeviceInfo deviceInfo = new DeviceInfo();
                                LKDeviceInfo lKDeviceInfo = (LKDeviceInfo) JSON.parseObject(ioTResponse.getData().toString(), LKDeviceInfo.class);
                                if (lKDeviceInfo == null) {
                                    String str4 = BaseProvisionStrategy.TAG;
                                    StringBuilder sb = new StringBuilder();
                                    sb.append("invalid data:");
                                    sb.append(ioTResponse.getData());
                                    ALog.w(str4, sb.toString());
                                    return;
                                }
                                deviceInfo.deviceName = lKDeviceInfo.deviceName;
                                deviceInfo.productKey = lKDeviceInfo.productKey;
                                deviceInfo.token = str3;
                                StringBuilder sb2 = new StringBuilder();
                                sb2.append(deviceInfo.productKey);
                                sb2.append("&");
                                sb2.append(deviceInfo.deviceName);
                                String string = sb2.toString();
                                if (BaseProvisionStrategy.this.f10358e.get() && BaseProvisionStrategy.this.f10360g.containsKey(string)) {
                                    String str5 = BaseProvisionStrategy.TAG;
                                    StringBuilder sb3 = new StringBuilder();
                                    sb3.append("device=");
                                    sb3.append(string);
                                    sb3.append(" has already returned.");
                                    ALog.i(str5, sb3.toString());
                                    return;
                                }
                                ALog.i(BaseProvisionStrategy.TAG, "Provision success from check token. ");
                                BaseProvisionStrategy baseProvisionStrategy = BaseProvisionStrategy.this;
                                if (baseProvisionStrategy.mNotifyListner != null) {
                                    baseProvisionStrategy.f10360g.put(string, Boolean.TRUE);
                                    BaseProvisionStrategy.this.mNotifyListner.onDeviceFound(deviceInfo);
                                }
                            }
                        } catch (Exception e2) {
                            ALog.w(BaseProvisionStrategy.TAG, "checkToken exception= " + e2);
                        }
                    }
                });
            }
        } catch (Throwable th) {
            ALog.i(TAG, "checkToken exception=" + th);
            th.printStackTrace();
        }
    }

    public void cancelRequest(RetryTransitoryClient retryTransitoryClient) {
        if (retryTransitoryClient != null) {
            retryTransitoryClient.cancelRequest();
        }
    }

    public void startProvisionTimer(final boolean z2, final TimerUtils.ITimerCallback iTimerCallback) {
        ALog.d(TAG, "startProvisionTimer() called with: timerCallback = [" + iTimerCallback + "]");
        DCAlibabaConfigParams dCAlibabaConfigParams = this.mConfigParams;
        if (dCAlibabaConfigParams == null) {
            return;
        }
        TimerUtils timerUtils = new TimerUtils(dCAlibabaConfigParams.timeout * 1000);
        this.provisionTimer = timerUtils;
        timerUtils.setCallback(new TimerUtils.ITimerCallback() { // from class: com.aliyun.alink.business.devicecenter.config.BaseProvisionStrategy.9
            @Override // com.aliyun.alink.business.devicecenter.utils.TimerUtils.ITimerCallback
            public void onTimeout() {
                TimerUtils.ITimerCallback iTimerCallback2 = iTimerCallback;
                if (iTimerCallback2 != null) {
                    iTimerCallback2.onTimeout();
                }
                if (z2) {
                    BaseProvisionStrategy.this.provisionResultCallback(null);
                }
            }
        });
        this.provisionTimer.start(TimerUtils.MSG_PROVISION_TIMEOUT);
    }

    public final void a(IConfigCallback iConfigCallback, String str) {
        ALog.d(TAG, "notifyBindToken() called with: callback = [" + iConfigCallback + "], token = [" + str + "]");
        ProvisionStatus provisionStatus = ProvisionStatus.PROVISION_APP_TOKEN;
        provisionStatus.addExtraParams("appToken", str);
        DeviceCenterBiz.getInstance().onConfigCallback(new ConfigCallbackWrapper().callback(iConfigCallback).status(provisionStatus));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void c() {
        provisionResultCallback(null);
    }

    public final long a(String str) {
        try {
            return Long.valueOf(str).longValue();
        } catch (Exception unused) {
            return -1L;
        }
    }

    public final void a() {
        if (this.f10357d != null) {
            CoAPClient.getInstance().removeNotifyListener(this.f10357d);
            this.f10357d = null;
        }
    }

    public final void a(Future future) {
        if (future != null) {
            try {
                future.cancel(true);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void startBackupCheck(boolean z2, long j2, List<CheckTokenModel> list) {
        DCAlibabaConfigParams dCAlibabaConfigParams;
        ALog.d(TAG, "startBackupCheck() called with: needSend = [" + z2 + "], delay = [" + j2 + "] enrolleeList = [" + list + "]");
        if (list == null || list.size() < 1) {
            return;
        }
        if (z2 && this.f10359f.get()) {
            ALog.d(TAG, "startBackupCheck has already started.");
            return;
        }
        this.f10359f.set(z2);
        try {
            if (this.f10359f.get()) {
                if (this.waitForResult.get() && (dCAlibabaConfigParams = this.mConfigParams) != null && !TextUtils.isEmpty(dCAlibabaConfigParams.bindToken)) {
                    a(this.mConfigCallback, this.mConfigParams.bindToken);
                }
                a(this.f10354a);
                this.f10361h.addAll(list);
                this.f10354a = ThreadPool.scheduleAtFixedRate(new Runnable() { // from class: com.aliyun.alink.business.devicecenter.config.BaseProvisionStrategy.2
                    @Override // java.lang.Runnable
                    public void run() throws UnknownHostException {
                        ALog.d(BaseProvisionStrategy.TAG, "run waitForResult=" + BaseProvisionStrategy.this.waitForResult.get() + ",needBackupCheck=" + BaseProvisionStrategy.this.f10359f.get());
                        if (BaseProvisionStrategy.this.waitForResult.get() && BaseProvisionStrategy.this.f10359f.get()) {
                            BaseProvisionStrategy.this.b();
                            BaseProvisionStrategy baseProvisionStrategy = BaseProvisionStrategy.this;
                            baseProvisionStrategy.a((List<CheckTokenModel>) baseProvisionStrategy.f10361h, BaseProvisionStrategy.this.mConfigParams.bindToken);
                        }
                    }
                }, j2, this.f10363j, TimeUnit.SECONDS);
                return;
            }
            this.f10361h.clear();
            a(this.f10354a);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public final void a(List<CheckTokenModel> list, final String str) {
        ALog.d(TAG, "checkTokens() called with: checkTokenModelList = [" + list + "], token = [" + str + "]");
        try {
            ProvisionRepository.checkTokens(list, new IoTCallback() { // from class: com.aliyun.alink.business.devicecenter.config.BaseProvisionStrategy.5
                @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
                public void onFailure(IoTRequest ioTRequest, Exception exc) {
                    ALog.w(BaseProvisionStrategy.TAG, "checkToken onFailure e=" + exc);
                }

                @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
                public void onResponse(IoTRequest ioTRequest, IoTResponse ioTResponse) {
                    ALog.d(BaseProvisionStrategy.TAG, "checkToken onResponse request=" + TransitoryClient.getInstance().requestToStr(ioTRequest) + ",response=" + TransitoryClient.getInstance().responseToStr(ioTResponse));
                    try {
                        if (BaseProvisionStrategy.this.waitForResult.get() && ioTResponse != null && ioTResponse.getCode() == 200 && ioTResponse.getData() != null) {
                            JSONArray array = JSON.parseArray(ioTResponse.getData().toString());
                            for (int i2 = 0; i2 < array.size(); i2++) {
                                if (array.getJSONObject(i2) != null) {
                                    DeviceInfo deviceInfo = new DeviceInfo();
                                    deviceInfo.productKey = array.getJSONObject(i2).getString("productKey");
                                    deviceInfo.deviceName = array.getJSONObject(i2).getString("deviceName");
                                    deviceInfo.token = str;
                                    StringBuilder sb = new StringBuilder();
                                    sb.append(deviceInfo.productKey);
                                    sb.append("&");
                                    sb.append(deviceInfo.deviceName);
                                    String string = sb.toString();
                                    BaseProvisionStrategy baseProvisionStrategy = BaseProvisionStrategy.this;
                                    if (baseProvisionStrategy.mNotifyListner != null && !baseProvisionStrategy.f10360g.containsKey(string)) {
                                        BaseProvisionStrategy.this.f10360g.put(string, Boolean.TRUE);
                                        BaseProvisionStrategy.this.mNotifyListner.onDeviceFound(deviceInfo);
                                        CheckTokenModel checkTokenModel = new CheckTokenModel();
                                        checkTokenModel.productKey = deviceInfo.productKey;
                                        checkTokenModel.deviceName = deviceInfo.deviceName;
                                        checkTokenModel.bindToken = deviceInfo.token;
                                        BaseProvisionStrategy.this.f10361h.remove(checkTokenModel);
                                    }
                                }
                            }
                        }
                    } catch (Exception e2) {
                        ALog.w(BaseProvisionStrategy.TAG, "checkToken exception= " + e2);
                    }
                }
            });
        } catch (Exception e2) {
            ALog.i(TAG, "checkToken exception=" + e2);
            e2.printStackTrace();
        }
    }

    public final void a(final String str, final String str2, final String str3) {
        ALog.d(TAG, "checkILopCloudToken() called with: pk = [" + str + "], dn = [" + str2 + "], token = [" + str3 + "]");
        try {
            if (!DCEnvHelper.hasApiClient()) {
                ALog.w(TAG, "checkToken no apiclient, return.");
            } else {
                ProvisionRepository.iLopTokenCheck(str, str2, str3, new IoTCallback() { // from class: com.aliyun.alink.business.devicecenter.config.BaseProvisionStrategy.7
                    @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
                    public void onFailure(IoTRequest ioTRequest, Exception exc) {
                        ALog.w(BaseProvisionStrategy.TAG, "checkILopCloudToken onFailure e=" + exc);
                    }

                    @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
                    public void onResponse(IoTRequest ioTRequest, IoTResponse ioTResponse) {
                        ALog.d(BaseProvisionStrategy.TAG, "checkILopCloudToken onResponse request=" + TransitoryClient.getInstance().requestToStr(ioTRequest) + ",response=" + TransitoryClient.getInstance().responseToStr(ioTResponse));
                        try {
                            if (BaseProvisionStrategy.this.waitForResult.get() && ioTResponse != null && ioTResponse.getCode() == 200 && ioTResponse.getData() != null) {
                                DeviceInfo deviceInfo = new DeviceInfo();
                                DeviceBindResultInfo firstBindResultInfo = DeviceBindResultInfo.getFirstBindResultInfo(str, str2, ioTResponse.getData().toString());
                                if (firstBindResultInfo != null && !TextUtils.isEmpty(firstBindResultInfo.productKey) && !TextUtils.isEmpty(firstBindResultInfo.deviceName)) {
                                    if (!TextUtils.isEmpty(str) && !str.equals(firstBindResultInfo.productKey)) {
                                        String str4 = BaseProvisionStrategy.TAG;
                                        StringBuilder sb = new StringBuilder();
                                        sb.append("pk not equal, local = ");
                                        sb.append(str);
                                        sb.append(", cloud = ");
                                        sb.append(firstBindResultInfo.productKey);
                                        ALog.w(str4, sb.toString());
                                        return;
                                    }
                                    if (!TextUtils.isEmpty(str2) && !str2.equals(firstBindResultInfo.deviceName)) {
                                        String str5 = BaseProvisionStrategy.TAG;
                                        StringBuilder sb2 = new StringBuilder();
                                        sb2.append("dn not equal, local = ");
                                        sb2.append(str2);
                                        sb2.append(", cloud = ");
                                        sb2.append(firstBindResultInfo.deviceName);
                                        ALog.w(str5, sb2.toString());
                                        return;
                                    }
                                    deviceInfo.productKey = firstBindResultInfo.productKey;
                                    deviceInfo.deviceName = firstBindResultInfo.deviceName;
                                    deviceInfo.token = str3;
                                    deviceInfo.bindResultInfo = firstBindResultInfo;
                                    StringBuilder sb3 = new StringBuilder();
                                    sb3.append(deviceInfo.productKey);
                                    sb3.append("&");
                                    sb3.append(deviceInfo.deviceName);
                                    String string = sb3.toString();
                                    if (BaseProvisionStrategy.this.f10358e.get() && BaseProvisionStrategy.this.f10360g.containsKey(string)) {
                                        String str6 = BaseProvisionStrategy.TAG;
                                        StringBuilder sb4 = new StringBuilder();
                                        sb4.append("device=");
                                        sb4.append(string);
                                        sb4.append(" has already returned.");
                                        ALog.i(str6, sb4.toString());
                                        return;
                                    }
                                    String str7 = BaseProvisionStrategy.TAG;
                                    StringBuilder sb5 = new StringBuilder();
                                    sb5.append("checkingCloudToken fail，bindResult");
                                    sb5.append(firstBindResultInfo.insideResult);
                                    ALog.d(str7, sb5.toString());
                                    int i2 = firstBindResultInfo.bindResult;
                                    if (i2 != 1) {
                                        if (i2 == 2) {
                                            DCErrorCode subcode = new DCErrorCode("BindFail", DCErrorCode.PF_PROVISION_CLOUD_BIND_ERROR).setMsg(TextUtils.isEmpty(firstBindResultInfo.localizedMsg) ? "bind fail." : firstBindResultInfo.localizedMsg).setSubcode(firstBindResultInfo.insideResult);
                                            IDeviceInfoNotifyListener iDeviceInfoNotifyListener = BaseProvisionStrategy.this.mNotifyListner;
                                            if (iDeviceInfoNotifyListener != null) {
                                                iDeviceInfoNotifyListener.onFailure(subcode);
                                                return;
                                            }
                                            return;
                                        }
                                        if (i2 != 3) {
                                            ALog.d(BaseProvisionStrategy.TAG, "checkILopCloudToken device binding, return.");
                                            return;
                                        }
                                        IDeviceInfoNotifyListener iDeviceInfoNotifyListener2 = BaseProvisionStrategy.this.mNotifyListner;
                                        if (iDeviceInfoNotifyListener2 != null) {
                                            iDeviceInfoNotifyListener2.onDeviceFound(deviceInfo);
                                            return;
                                        }
                                        return;
                                    }
                                    int i3 = firstBindResultInfo.insideResult;
                                    if (100 == i3 || 1 == i3 || !BaseProvisionStrategy.this.mConfigParams.isInSide) {
                                        ALog.i(BaseProvisionStrategy.TAG, "Provision success from check ilop token. ");
                                        BaseProvisionStrategy baseProvisionStrategy = BaseProvisionStrategy.this;
                                        if (baseProvisionStrategy.mNotifyListner != null) {
                                            baseProvisionStrategy.f10360g.put(string, Boolean.TRUE);
                                            BaseProvisionStrategy.this.mNotifyListner.onDeviceFound(deviceInfo);
                                            return;
                                        }
                                        return;
                                    }
                                    String str8 = "inside bind err.";
                                    if (2 == i3) {
                                        DCErrorCode dCErrorCode = new DCErrorCode("BindFail", DCErrorCode.PF_PROVISION_CLOUD_BIND_ERROR);
                                        if (!TextUtils.isEmpty(firstBindResultInfo.localizedMsg)) {
                                            str8 = firstBindResultInfo.localizedMsg;
                                        }
                                        DCErrorCode subcode2 = dCErrorCode.setMsg(str8).setSubcode(firstBindResultInfo.insideResult);
                                        IDeviceInfoNotifyListener iDeviceInfoNotifyListener3 = BaseProvisionStrategy.this.mNotifyListner;
                                        if (iDeviceInfoNotifyListener3 != null) {
                                            iDeviceInfoNotifyListener3.onFailure(subcode2);
                                            return;
                                        }
                                        return;
                                    }
                                    DCErrorCode dCErrorCode2 = new DCErrorCode("BindFail", DCErrorCode.PF_PROVISION_INSIDE_BIND_ERROR);
                                    if (!TextUtils.isEmpty(firstBindResultInfo.localizedMsg)) {
                                        str8 = firstBindResultInfo.localizedMsg;
                                    }
                                    DCErrorCode subcode3 = dCErrorCode2.setMsg(str8).setSubcode(firstBindResultInfo.insideResult);
                                    IDeviceInfoNotifyListener iDeviceInfoNotifyListener4 = BaseProvisionStrategy.this.mNotifyListner;
                                    if (iDeviceInfoNotifyListener4 != null) {
                                        iDeviceInfoNotifyListener4.onFailure(subcode3);
                                        return;
                                    }
                                    return;
                                }
                                String str9 = BaseProvisionStrategy.TAG;
                                StringBuilder sb6 = new StringBuilder();
                                sb6.append("invalid ilop data:");
                                sb6.append(ioTResponse.getData());
                                ALog.w(str9, sb6.toString());
                            }
                        } catch (Exception e2) {
                            ALog.w(BaseProvisionStrategy.TAG, "checkILopCloudToken exception= " + e2);
                        }
                    }
                });
            }
        } catch (Throwable th) {
            ALog.i(TAG, "checkILopCloudToken exception=" + th);
            th.printStackTrace();
        }
    }

    public final void b(final String str, final String str2, final String str3) {
        ALog.d(TAG, "checkILopTgCloudToken() called with: pk = [" + str + "], dn = [" + str2 + "], token = [" + str3 + "]");
        try {
            if (!DCEnvHelper.isTgEnv()) {
                ALog.d(TAG, "checkILopTgCloudToken not tg return.");
                return;
            }
            CheckBindTokenRequest checkBindTokenRequest = new CheckBindTokenRequest();
            checkBindTokenRequest.setAuthInfo(UserManager.getInstance().getAuthInfoStr());
            checkBindTokenRequest.setProductKey(str);
            checkBindTokenRequest.setDeviceName(str2);
            checkBindTokenRequest.setToken(str3);
            if (this.f10366m == null) {
                this.f10366m = new IRequestCallback() { // from class: com.aliyun.alink.business.devicecenter.config.BaseProvisionStrategy.8
                    @Override // com.aliyun.alink.business.devicecenter.channel.http.IRequestCallback
                    public void onFail(DCError dCError, Object obj) {
                        ALog.d(BaseProvisionStrategy.TAG, "checkILopTgCloudToken onFail dcError=" + dCError + ", response=" + obj);
                    }

                    @Override // com.aliyun.alink.business.devicecenter.channel.http.IRequestCallback
                    public void onSuccess(Object obj) {
                        ALog.d(BaseProvisionStrategy.TAG, "checkILopTgCloudToken onSuccess() called with: data = [" + obj + "] ,wait=" + BaseProvisionStrategy.this.waitForResult);
                        try {
                            if (BaseProvisionStrategy.this.waitForResult.get()) {
                                if (!(obj instanceof CheckBindTokenMtopResponse)) {
                                    ALog.d(BaseProvisionStrategy.TAG, "checkBindTokenMtopResponse == null.");
                                    return;
                                }
                                CheckBindTokenMtopResponse checkBindTokenMtopResponse = (CheckBindTokenMtopResponse) obj;
                                if (checkBindTokenMtopResponse.m57getData() == null) {
                                    ALog.d(BaseProvisionStrategy.TAG, "checkBindTokenMtopResponse.getData == null.");
                                    return;
                                }
                                CheckBindTokenMtopResponse.Data dataM57getData = checkBindTokenMtopResponse.m57getData();
                                if (!dataM57getData.isSuccess()) {
                                    ALog.d(BaseProvisionStrategy.TAG, "responseData.isSuccess=false.");
                                    return;
                                }
                                List<CheckBindTokenResponse> model = dataM57getData.getModel();
                                if (model != null && !model.isEmpty()) {
                                    int size = model.size();
                                    for (int i2 = 0; i2 < size; i2++) {
                                        CheckBindTokenResponse checkBindTokenResponse = model.get(i2);
                                        if (checkBindTokenResponse != null && !TextUtils.isEmpty(checkBindTokenResponse.getDeviceName()) && !TextUtils.isEmpty(checkBindTokenResponse.getProductKey()) && !TextUtils.isEmpty(checkBindTokenResponse.getDeviceName()) && !TextUtils.isEmpty(checkBindTokenResponse.getProductKey())) {
                                            DeviceInfo deviceInfo = new DeviceInfo();
                                            DeviceBindResultInfo tgBindResultInfo = DeviceBindResultInfo.getTgBindResultInfo(checkBindTokenResponse);
                                            if (!TextUtils.isEmpty(str) && !str.equals(tgBindResultInfo.productKey)) {
                                                String str4 = BaseProvisionStrategy.TAG;
                                                StringBuilder sb = new StringBuilder();
                                                sb.append("checkILopTgCloudToken pk not equal, local = ");
                                                sb.append(str);
                                                sb.append(", cloud = ");
                                                sb.append(tgBindResultInfo.productKey);
                                                ALog.w(str4, sb.toString());
                                                return;
                                            }
                                            if (!TextUtils.isEmpty(str2) && !str2.equals(tgBindResultInfo.deviceName)) {
                                                String str5 = BaseProvisionStrategy.TAG;
                                                StringBuilder sb2 = new StringBuilder();
                                                sb2.append("checkILopTgCloudToken dn not equal, local = ");
                                                sb2.append(str2);
                                                sb2.append(", cloud = ");
                                                sb2.append(tgBindResultInfo.deviceName);
                                                ALog.w(str5, sb2.toString());
                                                return;
                                            }
                                            deviceInfo.productKey = tgBindResultInfo.productKey;
                                            deviceInfo.deviceName = tgBindResultInfo.deviceName;
                                            deviceInfo.token = str3;
                                            deviceInfo.bindResultInfo = tgBindResultInfo;
                                            if (tgBindResultInfo.bindResult == 0) {
                                                ALog.d(BaseProvisionStrategy.TAG, "checkILopTgCloudToken device binding, return.");
                                                return;
                                            }
                                            StringBuilder sb3 = new StringBuilder();
                                            sb3.append(deviceInfo.productKey);
                                            sb3.append("&");
                                            sb3.append(deviceInfo.deviceName);
                                            String string = sb3.toString();
                                            if (BaseProvisionStrategy.this.f10358e.get() && BaseProvisionStrategy.this.f10360g.containsKey(string)) {
                                                String str6 = BaseProvisionStrategy.TAG;
                                                StringBuilder sb4 = new StringBuilder();
                                                sb4.append("checkILopTgCloudToken device=");
                                                sb4.append(string);
                                                sb4.append(" has already returned.");
                                                ALog.i(str6, sb4.toString());
                                                return;
                                            }
                                            ALog.i(BaseProvisionStrategy.TAG, "checkILopTgCloudToken Provision success from check ilop & tg token. ");
                                            BaseProvisionStrategy baseProvisionStrategy = BaseProvisionStrategy.this;
                                            if (baseProvisionStrategy.mNotifyListner != null) {
                                                baseProvisionStrategy.f10360g.put(string, Boolean.TRUE);
                                                BaseProvisionStrategy.this.mNotifyListner.onDeviceFound(deviceInfo);
                                            }
                                        }
                                    }
                                    return;
                                }
                                ALog.d(BaseProvisionStrategy.TAG, "responseData.modelList=null.");
                            }
                        } catch (Exception e2) {
                            ALog.w(BaseProvisionStrategy.TAG, "checkILopTgCloudToken exception= " + e2);
                        }
                    }
                };
            }
            this.f10365l.send(checkBindTokenRequest, CheckBindTokenMtopResponse.class, this.f10366m);
        } catch (Throwable th) {
            ALog.i(TAG, "checkILopTgCloudToken exception=" + th);
            th.printStackTrace();
        }
    }
}
