package com.aliyun.alink.business.devicecenter.api.discovery;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.text.TextUtils;
import com.alibaba.ailabs.iot.aisbase.callback.ILeScanCallback;
import com.alibaba.ailabs.iot.aisbase.scanner.BLEScannerProxy;
import com.alibaba.ailabs.iot.aisbase.spec.BluetoothDeviceSubtype;
import com.alibaba.ailabs.iot.aisbase.spec.BluetoothDeviceWrapper;
import com.alibaba.ailabs.iot.mesh.UnprovisionedBluetoothMeshDevice;
import com.alibaba.fastjson.JSON;
import com.aliyun.alink.business.devicecenter.api.add.BatchDiscoveryParams;
import com.aliyun.alink.business.devicecenter.api.add.DeviceBindResultInfo;
import com.aliyun.alink.business.devicecenter.api.add.DeviceInfo;
import com.aliyun.alink.business.devicecenter.api.add.LKDeviceInfo;
import com.aliyun.alink.business.devicecenter.api.config.ProvisionConfigCenter;
import com.aliyun.alink.business.devicecenter.base.DCEnvHelper;
import com.aliyun.alink.business.devicecenter.base.DCErrorCode;
import com.aliyun.alink.business.devicecenter.biz.ProvisionRepository;
import com.aliyun.alink.business.devicecenter.cache.CacheCenter;
import com.aliyun.alink.business.devicecenter.cache.CacheType;
import com.aliyun.alink.business.devicecenter.cache.DeviceInfoICacheModel;
import com.aliyun.alink.business.devicecenter.cache.ProvisionDeviceInfoCache;
import com.aliyun.alink.business.devicecenter.channel.coap.response.DevicePayload;
import com.aliyun.alink.business.devicecenter.config.DeviceCenterBiz;
import com.aliyun.alink.business.devicecenter.config.model.DeviceReportTokenType;
import com.aliyun.alink.business.devicecenter.discover.DiscoverChainProcessor;
import com.aliyun.alink.business.devicecenter.discover.DiscoverListenerAdapter;
import com.aliyun.alink.business.devicecenter.discover.IDiscoverChain;
import com.aliyun.alink.business.devicecenter.discover.cloud.CloudBleMeshDiscoverChain;
import com.aliyun.alink.business.devicecenter.discover.cloud.CloudDiscoverChain;
import com.aliyun.alink.business.devicecenter.discover.coap.CoAPDiscoverChain;
import com.aliyun.alink.business.devicecenter.discover.mesh.BleMeshDiscoverChain;
import com.aliyun.alink.business.devicecenter.discover.mesh.BleMeshLocalAndCloudMixDiscoverChain;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.model.MultiTimerTaskWrapper;
import com.aliyun.alink.business.devicecenter.plugin.DiscoveryPluginMgr;
import com.aliyun.alink.business.devicecenter.utils.NetworkTypeUtils;
import com.aliyun.alink.business.devicecenter.utils.StringUtils;
import com.aliyun.alink.business.devicecenter.utils.ThreadPool;
import com.aliyun.alink.business.devicecenter.utils.TimerUtils;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTResponse;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequest;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes2.dex */
public class LocalDeviceMgr implements ILocalDeviceMgr {

    /* renamed from: a, reason: collision with root package name */
    public DiscoverListenerAdapter f10086a;

    /* renamed from: b, reason: collision with root package name */
    public DiscoverChainProcessor f10087b;

    /* renamed from: c, reason: collision with root package name */
    public HashMap<String, MultiTimerTaskWrapper> f10088c;

    /* renamed from: d, reason: collision with root package name */
    public HashMap<String, MultiTimerTaskWrapper> f10089d;

    /* renamed from: e, reason: collision with root package name */
    public Handler f10090e;

    /* renamed from: f, reason: collision with root package name */
    public ILeScanCallback f10091f;

    private static class SingletonHolder {

        /* renamed from: a, reason: collision with root package name */
        public static final ILocalDeviceMgr f10147a = new LocalDeviceMgr();
    }

    public static ILocalDeviceMgr getInstance() {
        return SingletonHolder.f10147a;
    }

    @Override // com.aliyun.alink.business.devicecenter.api.discovery.ILocalDeviceMgr
    public void getDeviceToken(Context context, String str, String str2, int i2, IOnDeviceTokenGetListener iOnDeviceTokenGetListener) {
        getDeviceToken(context, str, str2, i2 < 60000 ? 60000 : i2, 10000, iOnDeviceTokenGetListener);
    }

    @Override // com.aliyun.alink.business.devicecenter.api.discovery.ILocalDeviceMgr
    public List<DeviceInfo> getLanDevices() {
        ALog.i("LocalDeviceMgr", "getLanDevices(),call");
        DiscoverListenerAdapter discoverListenerAdapter = this.f10086a;
        if (discoverListenerAdapter != null) {
            return discoverListenerAdapter.getLanDevices();
        }
        return null;
    }

    @Override // com.aliyun.alink.business.devicecenter.api.discovery.ILocalDeviceMgr
    public void startBatchDiscovery(final Context context, final BatchDiscoveryParams batchDiscoveryParams, final IDiscovery iDiscovery) {
        ALog.d("LocalDeviceMgr", "startBatchDiscovery() called with: context = [" + context + "], params = [" + batchDiscoveryParams + "], listener = [" + iDiscovery + "]");
        if (batchDiscoveryParams == null || TextUtils.isEmpty(batchDiscoveryParams.productKey) || TextUtils.isEmpty(batchDiscoveryParams.deviceName)) {
            throw new IllegalArgumentException("invalid batch discovery params");
        }
        if (context == null) {
            throw new IllegalArgumentException("context is null.");
        }
        if (iDiscovery == null) {
            throw new IllegalArgumentException("callback is null");
        }
        DeviceCenterBiz.getInstance().setAppContext(context);
        final String str = TextUtils.isEmpty(batchDiscoveryParams.enrolleeProductKey) ? batchDiscoveryParams.productKey : batchDiscoveryParams.enrolleeProductKey;
        final CloudDiscoverChain cloudDiscoverChain = new CloudDiscoverChain(context, null, true);
        MultiTimerTaskWrapper multiTimerTaskWrapper = new MultiTimerTaskWrapper(null, null, null, cloudDiscoverChain, ProvisionRepository.triggerDevice2Search(batchDiscoveryParams, new IoTCallback() { // from class: com.aliyun.alink.business.devicecenter.api.discovery.LocalDeviceMgr.13
            @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
            public void onFailure(IoTRequest ioTRequest, Exception exc) {
                ALog.w("LocalDeviceMgr", "startBatchDiscovery device switch mode failed. e=" + exc);
                if (iDiscovery != null) {
                    if (NetworkTypeUtils.isNetworkAvailable(context)) {
                        iDiscovery.onFail(new DCErrorCode("NETWORK_ERROR", DCErrorCode.PF_NETWORK_ERROR).setSubcode(DCErrorCode.SUBCODE_NE_NETWORK_UNAVAILABLE).setMsg("switch mode failed, network unavailable, " + exc));
                        return;
                    }
                    iDiscovery.onFail(new DCErrorCode("NETWORK_ERROR", DCErrorCode.PF_NETWORK_ERROR).setSubcode(DCErrorCode.SUBCODE_API_REQUEST_ON_FAILURE).setMsg("switch mode failed " + exc));
                }
            }

            @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
            public void onResponse(IoTRequest ioTRequest, IoTResponse ioTResponse) {
                DCErrorCode msg;
                if (ioTResponse != null && ioTResponse.getCode() == 200) {
                    ALog.d("LocalDeviceMgr", "startBatchDiscovery device switch mode success. to get enrollee devices.");
                    try {
                        cloudDiscoverChain.startDiscover(new IDeviceDiscoveryListener() { // from class: com.aliyun.alink.business.devicecenter.api.discovery.LocalDeviceMgr.13.1
                            @Override // com.aliyun.alink.business.devicecenter.api.discovery.IDeviceDiscoveryListener
                            public void onDeviceFound(DiscoveryType discoveryType, List<DeviceInfo> list) {
                                String str2;
                                String str3;
                                String str4;
                                if (list == null || list.isEmpty()) {
                                    return;
                                }
                                List<DeviceInfo> arrayList = new ArrayList<>();
                                int size = list.size();
                                for (int i2 = 0; i2 < size; i2++) {
                                    DeviceInfo deviceInfo = list.get(i2);
                                    if (deviceInfo != null && !TextUtils.isEmpty(deviceInfo.productKey) && (str2 = str) != null && str2.equals(deviceInfo.productKey) && (str3 = batchDiscoveryParams.deviceName) != null && str3.equals(deviceInfo.regDeviceName) && (str4 = batchDiscoveryParams.productKey) != null && str4.equals(deviceInfo.regProductKey) && !arrayList.contains(deviceInfo)) {
                                        arrayList.add(deviceInfo);
                                    }
                                }
                                ALog.d("LocalDeviceMgr", "filtered device list " + arrayList);
                                if (iDiscovery == null || arrayList.isEmpty()) {
                                    return;
                                }
                                iDiscovery.onDeviceFound(DiscoveryType.CLOUD_ENROLLEE_DEVICE, arrayList);
                            }
                        });
                        return;
                    } catch (Exception e2) {
                        IDiscovery iDiscovery2 = iDiscovery;
                        if (iDiscovery2 != null) {
                            iDiscovery2.onFail(new DCErrorCode("SDK_ERROR", DCErrorCode.PF_SDK_ERROR).setSubcode(DCErrorCode.SUBCODE_SKE_DISCOVERY_EXCEPTION).setExtra(e2).setMsg("switch mode success, but discover failed."));
                            return;
                        }
                        return;
                    }
                }
                ALog.w("LocalDeviceMgr", "startBatchDiscovery onResponse device switch mode failed.");
                if (iDiscovery != null) {
                    if (ioTResponse == null) {
                        msg = new DCErrorCode(DCErrorCode.SERVER_ERROR_MSG, DCErrorCode.PF_SERVER_FAIL).setSubcode(DCErrorCode.SUBCODE_SRE_RESPONSE_EMPTY).setMsg("switchModeResponseEmpty");
                    } else {
                        msg = new DCErrorCode(DCErrorCode.SERVER_ERROR_MSG, DCErrorCode.PF_SERVER_FAIL).setSubcode(ioTResponse.getCode()).setMsg("sitchModeFailed:" + ioTResponse.getLocalizedMsg());
                    }
                    iDiscovery.onFail(msg);
                }
            }
        }));
        this.f10089d.put(batchDiscoveryParams.productKey + "&&" + batchDiscoveryParams.deviceName, multiTimerTaskWrapper);
    }

    @Override // com.aliyun.alink.business.devicecenter.api.discovery.ILocalDeviceMgr
    public void startDiscovery(Context context, IDiscoveryListener iDiscoveryListener) {
        ALog.i("LocalDeviceMgr", "startDiscovery(),call");
        startDiscoveryWithFilter(context, null, iDiscoveryListener);
    }

    @Override // com.aliyun.alink.business.devicecenter.api.discovery.ILocalDeviceMgr
    public void startDiscoveryWithFilter(final Context context, final Map map, final IDiscoveryListener iDiscoveryListener) {
        ALog.i("LocalDeviceMgr", "startDiscoveryWithFilter() filterParams=" + map);
        if (context == null) {
            throw new RuntimeException("startDiscoveryParamContextNull");
        }
        this.f10090e.post(new Runnable() { // from class: com.aliyun.alink.business.devicecenter.api.discovery.LocalDeviceMgr.1
            @Override // java.lang.Runnable
            public void run() {
                LocalDeviceMgr.this.a(context, map, iDiscoveryListener);
            }
        });
    }

    @Override // com.aliyun.alink.business.devicecenter.api.discovery.ILocalDeviceMgr
    public void stopBatchDiscovery(String str, String str2) {
        ALog.d("LocalDeviceMgr", "stopBatchDiscovery() called with: productKey = [" + str + "], deviceName = [" + str2 + "]");
        synchronized (this.f10089d) {
            try {
                HashMap<String, MultiTimerTaskWrapper> map = this.f10089d;
                if (map != null && !map.isEmpty()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(str);
                    sb.append("&&");
                    sb.append(str2);
                    String string = sb.toString();
                    MultiTimerTaskWrapper multiTimerTaskWrapper = this.f10089d.get(string);
                    if (multiTimerTaskWrapper == null) {
                        return;
                    }
                    multiTimerTaskWrapper.cancelTimerTask(-1);
                    this.f10088c.remove(string);
                }
            } finally {
            }
        }
    }

    @Override // com.aliyun.alink.business.devicecenter.api.discovery.ILocalDeviceMgr
    public void stopDiscovery() {
        ALog.i("LocalDeviceMgr", "stopDiscovery(), call");
        this.f10090e.post(new Runnable() { // from class: com.aliyun.alink.business.devicecenter.api.discovery.LocalDeviceMgr.5
            @Override // java.lang.Runnable
            public void run() {
                LocalDeviceMgr.this.a();
            }
        });
    }

    @Override // com.aliyun.alink.business.devicecenter.api.discovery.ILocalDeviceMgr
    public void stopGetDeviceToken() {
        ALog.d("LocalDeviceMgr", "stopGetDeviceToken() called");
        HashMap<String, MultiTimerTaskWrapper> map = this.f10088c;
        if (map == null || map.isEmpty() || this.f10088c.entrySet() == null) {
            return;
        }
        synchronized (this.f10088c) {
            try {
                for (Map.Entry<String, MultiTimerTaskWrapper> entry : this.f10088c.entrySet()) {
                    if (entry != null && entry.getKey() != null && this.f10088c.get(entry.getKey()) != null) {
                        this.f10088c.get(entry.getKey()).cancelTimerTask(TimerUtils.MSG_GET_TOKEN_TIMEOUT);
                    }
                }
                this.f10088c.clear();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public LocalDeviceMgr() {
        this.f10086a = null;
        this.f10087b = null;
        this.f10088c = null;
        this.f10089d = null;
        this.f10087b = new DiscoverChainProcessor();
        this.f10088c = new HashMap<>();
        this.f10089d = new HashMap<>();
        HandlerThread handlerThread = new HandlerThread("DiscoveryThread");
        handlerThread.start();
        this.f10090e = new Handler(handlerThread.getLooper());
    }

    @Override // com.aliyun.alink.business.devicecenter.api.discovery.ILocalDeviceMgr
    public void getDeviceToken(Context context, String str, String str2, int i2, int i3, final IOnDeviceTokenGetListener iOnDeviceTokenGetListener) {
        GetTokenParams getTokenParams = new GetTokenParams();
        getTokenParams.productKey = str;
        getTokenParams.deviceName = str2;
        getTokenParams.timeout = i2;
        getTokenParams.interval = i3;
        getDeviceToken(context, getTokenParams, new IOnTokenGetListerner() { // from class: com.aliyun.alink.business.devicecenter.api.discovery.LocalDeviceMgr.6
            @Override // com.aliyun.alink.business.devicecenter.api.discovery.IOnTokenGetListerner
            public void onFail(DCErrorCode dCErrorCode) {
                IOnDeviceTokenGetListener iOnDeviceTokenGetListener2 = iOnDeviceTokenGetListener;
                if (iOnDeviceTokenGetListener2 != null) {
                    iOnDeviceTokenGetListener2.onFail(dCErrorCode.msg);
                }
            }

            @Override // com.aliyun.alink.business.devicecenter.api.discovery.IOnTokenGetListerner
            public void onSuccess(GetTokenResult getTokenResult) {
                IOnDeviceTokenGetListener iOnDeviceTokenGetListener2 = iOnDeviceTokenGetListener;
                if (iOnDeviceTokenGetListener2 != null) {
                    iOnDeviceTokenGetListener2.onSuccess(getTokenResult.token);
                }
            }
        });
    }

    @Override // com.aliyun.alink.business.devicecenter.api.discovery.ILocalDeviceMgr
    public void startDiscovery(final Context context, final EnumSet<DiscoveryType> enumSet, final Map<String, Object> map, final IDeviceDiscoveryListener iDeviceDiscoveryListener) {
        ALog.i("LocalDeviceMgr", "startDiscovery() filterParams=" + map + ",discoverType=" + enumSet);
        if (context != null) {
            this.f10090e.post(new Runnable() { // from class: com.aliyun.alink.business.devicecenter.api.discovery.LocalDeviceMgr.3
                @Override // java.lang.Runnable
                public void run() {
                    LocalDeviceMgr.this.a(context, (EnumSet<DiscoveryType>) enumSet, (Map<String, Object>) map, iDeviceDiscoveryListener);
                }
            });
            return;
        }
        throw new RuntimeException("startDiscoveryParamContextNull");
    }

    public final void a(Context context, Map map, final IDiscoveryListener iDiscoveryListener) {
        DeviceCenterBiz.getInstance().setAppContext(context);
        a();
        if (this.f10086a == null) {
            this.f10086a = new DiscoverListenerAdapter(new IDeviceDiscoveryListener() { // from class: com.aliyun.alink.business.devicecenter.api.discovery.LocalDeviceMgr.2
                @Override // com.aliyun.alink.business.devicecenter.api.discovery.IDeviceDiscoveryListener
                public void onDeviceFound(DiscoveryType discoveryType, List<DeviceInfo> list) {
                    ALog.d("LocalDeviceMgr", "onDeviceFound type=" + discoveryType + ", deviceInfoList=" + list);
                    if (iDiscoveryListener == null) {
                        ALog.w("LocalDeviceMgr", "onDeviceFound listener=null.");
                        return;
                    }
                    if (list == null || list.size() < 1) {
                        ALog.w("LocalDeviceMgr", "onDeviceFound foundDeviceList empty");
                        return;
                    }
                    for (int i2 = 0; i2 < list.size(); i2++) {
                        DeviceInfo deviceInfo = list.get(i2);
                        if (discoveryType == DiscoveryType.LOCAL_ONLINE_DEVICE) {
                            iDiscoveryListener.onLocalDeviceFound(deviceInfo);
                        } else if (discoveryType == DiscoveryType.CLOUD_ENROLLEE_DEVICE) {
                            iDiscoveryListener.onEnrolleeDeviceFound(list);
                        }
                    }
                }
            });
        }
        IDiscoverChain iDiscoverChainCreateDiscoverChain = DiscoveryPluginMgr.getInstance().createDiscoverChain(DiscoveryType.LOCAL_ONLINE_DEVICE, context, map);
        if (iDiscoverChainCreateDiscoverChain != null) {
            this.f10087b.addChain(iDiscoverChainCreateDiscoverChain);
        }
        IDiscoverChain iDiscoverChainCreateDiscoverChain2 = DiscoveryPluginMgr.getInstance().createDiscoverChain(DiscoveryType.CLOUD_ENROLLEE_DEVICE, context, map);
        if (iDiscoverChainCreateDiscoverChain2 != null) {
            this.f10087b.addChain(iDiscoverChainCreateDiscoverChain2);
        }
        if (this.f10087b.getChainSize() < 1) {
            ALog.w("LocalDeviceMgr", "startDiscovery invalid discovery type.");
        } else {
            this.f10087b.startDiscover(this.f10086a);
        }
    }

    @Override // com.aliyun.alink.business.devicecenter.api.discovery.ILocalDeviceMgr
    public void getDeviceToken(Context context, final GetTokenParams getTokenParams, final IOnTokenGetListerner iOnTokenGetListerner) {
        ScheduledFuture<?> scheduledFutureScheduleAtFixedRate;
        ALog.d("LocalDeviceMgr", "getDeviceTokenL() called with: context = [" + context + "], params = [" + getTokenParams + "], listener = [" + iOnTokenGetListerner + "]");
        DeviceCenterBiz.getInstance().setAppContext(context);
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        if (iOnTokenGetListerner != null) {
            if (context != null && getTokenParams != null && !TextUtils.isEmpty(getTokenParams.productKey) && !TextUtils.isEmpty(getTokenParams.deviceName)) {
                int i2 = getTokenParams.timeout;
                int i3 = getTokenParams.interval;
                if (i2 >= i3 && i3 >= 2000) {
                    final String str = getTokenParams.productKey;
                    final String str2 = getTokenParams.deviceName;
                    final DevicePayload cache = ProvisionDeviceInfoCache.getInstance().getCache();
                    if (cache != null && !TextUtils.isEmpty(cache.token) && str.equals(cache.productKey) && str2.equals(cache.deviceName)) {
                        ALog.i("LocalDeviceMgr", "getDeviceToken cache hit token=" + cache.token);
                        DeviceCenterBiz.getInstance().runOnUIThread(new Runnable() { // from class: com.aliyun.alink.business.devicecenter.api.discovery.LocalDeviceMgr.9
                            @Override // java.lang.Runnable
                            public void run() {
                                ALog.i("LocalDeviceMgr", "getDeviceTokenL onSuccess cacheHit listener=" + iOnTokenGetListerner + ", hasGetDeviceToken=" + atomicBoolean.get());
                                if (iOnTokenGetListerner != null && atomicBoolean.compareAndSet(false, true)) {
                                    GetTokenResult getTokenResult = new GetTokenResult();
                                    getTokenResult.token = cache.token;
                                    getTokenResult.productKey = str;
                                    getTokenResult.deviceName = str2;
                                    iOnTokenGetListerner.onSuccess(getTokenResult);
                                }
                                CacheCenter cacheCenter = CacheCenter.getInstance();
                                CacheType cacheType = CacheType.APP_SEND_TOKEN;
                                GetTokenParams getTokenParams2 = getTokenParams;
                                cacheCenter.getCachedModel(cacheType, getTokenParams2.productKey, getTokenParams2.deviceName);
                                ProvisionDeviceInfoCache.getInstance().clearCache();
                            }
                        });
                        return;
                    }
                    synchronized (this.f10088c) {
                        try {
                            HashMap<String, MultiTimerTaskWrapper> map = this.f10088c;
                            StringBuilder sb = new StringBuilder();
                            sb.append(getTokenParams.productKey);
                            sb.append("&&");
                            sb.append(getTokenParams.deviceName);
                            if (map.containsKey(sb.toString())) {
                                StringBuilder sb2 = new StringBuilder();
                                sb2.append("getDeviceToken already started, stop last request and start a new request. pk=");
                                sb2.append(getTokenParams.productKey);
                                sb2.append(", deviceName=");
                                sb2.append(getTokenParams.deviceName);
                                ALog.d("LocalDeviceMgr", sb2.toString());
                                stopGetDeviceToken(getTokenParams.productKey, getTokenParams.deviceName);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    DiscoverChainProcessor discoverChainProcessor = new DiscoverChainProcessor();
                    TimerUtils timerUtils = new TimerUtils(getTokenParams.timeout);
                    timerUtils.setCallback(new TimerUtils.ITimerCallback() { // from class: com.aliyun.alink.business.devicecenter.api.discovery.LocalDeviceMgr.10
                        @Override // com.aliyun.alink.business.devicecenter.utils.TimerUtils.ITimerCallback
                        public void onTimeout() {
                            DeviceCenterBiz.getInstance().runOnUIThread(new Runnable() { // from class: com.aliyun.alink.business.devicecenter.api.discovery.LocalDeviceMgr.10.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    ALog.w("LocalDeviceMgr", "getDeviceTokenL onFail timeout。listener=" + iOnTokenGetListerner + ", hasGetDeviceToken=" + atomicBoolean.get());
                                    AnonymousClass10 anonymousClass10 = AnonymousClass10.this;
                                    LocalDeviceMgr localDeviceMgr = LocalDeviceMgr.this;
                                    GetTokenParams getTokenParams2 = getTokenParams;
                                    localDeviceMgr.stopGetDeviceToken(getTokenParams2.productKey, getTokenParams2.deviceName);
                                    AnonymousClass10 anonymousClass102 = AnonymousClass10.this;
                                    if (iOnTokenGetListerner == null || !atomicBoolean.compareAndSet(false, true)) {
                                        return;
                                    }
                                    DCErrorCode dCErrorCode = new DCErrorCode("GET_TOKEN_FAIL", DCErrorCode.PF_GET_DEVICE_TOKEN_ERROR);
                                    dCErrorCode.setMsg("getTokenTimeout");
                                    iOnTokenGetListerner.onFail(dCErrorCode);
                                }
                            });
                        }
                    });
                    timerUtils.start(TimerUtils.MSG_GET_TOKEN_TIMEOUT);
                    CoAPDiscoverChain coAPDiscoverChain = new CoAPDiscoverChain(context, false);
                    int i4 = getTokenParams.interval;
                    coAPDiscoverChain.setPeriod(i4 / 1000);
                    ALog.d("LocalDeviceMgr", "period=" + i4 + ", timeout=" + getTokenParams.timeout);
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("getDeviceToken chainSize=");
                    sb3.append(discoverChainProcessor.getChainSize());
                    ALog.d("LocalDeviceMgr", sb3.toString());
                    discoverChainProcessor.addChain(coAPDiscoverChain);
                    discoverChainProcessor.startDiscover(new IDeviceDiscoveryListener() { // from class: com.aliyun.alink.business.devicecenter.api.discovery.LocalDeviceMgr.11
                        @Override // com.aliyun.alink.business.devicecenter.api.discovery.IDeviceDiscoveryListener
                        public void onDeviceFound(DiscoveryType discoveryType, List<DeviceInfo> list) {
                            ALog.d("LocalDeviceMgr", "getDeviceToken onDeviceFound type=" + discoveryType + ", devInfoList=" + list);
                            if (list == null || list.size() < 1) {
                                return;
                            }
                            for (int i5 = 0; i5 < list.size(); i5++) {
                                DeviceInfo deviceInfo = list.get(i5);
                                if (deviceInfo != null && StringUtils.isEqualString(str, deviceInfo.productKey) && StringUtils.isEqualString(str2, deviceInfo.deviceName)) {
                                    ALog.i("LocalDeviceMgr", "getDeviceToken onDeviceFound pk=" + str + ",dn=" + str2 + ",token=" + deviceInfo.token);
                                    LocalDeviceMgr.this.stopGetDeviceToken(str, str2);
                                    if (atomicBoolean.get()) {
                                        ALog.d("LocalDeviceMgr", "getDeviceToken hasGetDeviceToken=true, coap return.");
                                        return;
                                    }
                                    ALog.i("LocalDeviceMgr", "getDeviceTokenL onSuccess listener=" + iOnTokenGetListerner + ", token=" + deviceInfo.token + ", hasGetDeviceToken=" + atomicBoolean.get());
                                    if (iOnTokenGetListerner != null) {
                                        atomicBoolean.set(true);
                                        GetTokenResult getTokenResult = new GetTokenResult();
                                        getTokenResult.productKey = str;
                                        getTokenResult.deviceName = str2;
                                        getTokenResult.token = deviceInfo.token;
                                        iOnTokenGetListerner.onSuccess(getTokenResult);
                                    }
                                }
                            }
                        }
                    });
                    final List cachedModel = CacheCenter.getInstance().getCachedModel(CacheType.APP_SEND_TOKEN, str, str2);
                    if (cachedModel == null || cachedModel.size() <= 0 || !DCEnvHelper.hasApiClient()) {
                        scheduledFutureScheduleAtFixedRate = null;
                    } else {
                        ALog.d("LocalDeviceMgr", "tryTokenCheckTask scheduleAtFixedRate start.");
                        scheduledFutureScheduleAtFixedRate = ThreadPool.scheduleAtFixedRate(new Runnable() { // from class: com.aliyun.alink.business.devicecenter.api.discovery.LocalDeviceMgr.12
                            @Override // java.lang.Runnable
                            public void run() {
                                List list;
                                DeviceReportTokenType deviceReportTokenType;
                                if (atomicBoolean.get() || (list = cachedModel) == null) {
                                    return;
                                }
                                final DeviceInfoICacheModel deviceInfoICacheModel = (DeviceInfoICacheModel) list.get(0);
                                if (deviceInfoICacheModel == null) {
                                    return;
                                }
                                boolean z2 = ProvisionConfigCenter.getInstance().enableGlobalCloudToken() && ((deviceReportTokenType = deviceInfoICacheModel.deviceReportTokenType) == DeviceReportTokenType.UNKNOWN || deviceReportTokenType == DeviceReportTokenType.CLOUD_TOKEN);
                                DeviceReportTokenType deviceReportTokenType2 = deviceInfoICacheModel.deviceReportTokenType;
                                boolean z3 = deviceReportTokenType2 == DeviceReportTokenType.UNKNOWN || deviceReportTokenType2 == DeviceReportTokenType.APP_TOKEN;
                                ALog.d("LocalDeviceMgr", "needCheckAppToken=" + z3 + ", needCheckCloudToken=" + z2);
                                if (z2) {
                                    ProvisionRepository.iLopTokenCheck(str, str2, deviceInfoICacheModel.token, new IoTCallback() { // from class: com.aliyun.alink.business.devicecenter.api.discovery.LocalDeviceMgr.12.1
                                        @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
                                        public void onFailure(IoTRequest ioTRequest, Exception exc) {
                                        }

                                        @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
                                        public void onResponse(IoTRequest ioTRequest, IoTResponse ioTResponse) {
                                            if (ioTResponse == null || ioTResponse.getCode() != 200 || ioTResponse.getData() == null) {
                                                return;
                                            }
                                            AnonymousClass12 anonymousClass12 = AnonymousClass12.this;
                                            GetTokenResult getTokenResult = new GetTokenResult(DeviceBindResultInfo.getFirstBindResultInfo(str, str2, ioTResponse.getData().toString()));
                                            getTokenResult.token = deviceInfoICacheModel.token;
                                            if (TextUtils.isEmpty(getTokenResult.productKey) || TextUtils.isEmpty(getTokenResult.deviceName)) {
                                                ALog.w("LocalDeviceMgr", "invalid ilop data:" + ioTResponse.getData());
                                                return;
                                            }
                                            if (!TextUtils.isEmpty(str) && !str.equals(getTokenResult.productKey)) {
                                                ALog.w("LocalDeviceMgr", "pk not equal, local = " + str + ", cloud = " + getTokenResult.productKey);
                                                return;
                                            }
                                            if (!TextUtils.isEmpty(str2) && !str2.equals(getTokenResult.deviceName)) {
                                                ALog.w("LocalDeviceMgr", "dn not equal, local = " + str2 + ", cloud = " + getTokenResult.deviceName);
                                                return;
                                            }
                                            getTokenResult.token = deviceInfoICacheModel.token;
                                            if (getTokenResult.bindResult == 0) {
                                                ALog.d("LocalDeviceMgr", "checkILopCloudToken device binding, return.");
                                                return;
                                            }
                                            AnonymousClass12 anonymousClass122 = AnonymousClass12.this;
                                            LocalDeviceMgr.this.stopGetDeviceToken(str, str2);
                                            AnonymousClass12 anonymousClass123 = AnonymousClass12.this;
                                            if (iOnTokenGetListerner == null || atomicBoolean.get()) {
                                                return;
                                            }
                                            ALog.i("LocalDeviceMgr", "getDeviceTokenL onSuccess checkToken listener=" + iOnTokenGetListerner);
                                            atomicBoolean.set(true);
                                            iOnTokenGetListerner.onSuccess(getTokenResult);
                                        }
                                    });
                                }
                                if (z3) {
                                    ProvisionRepository.checkToken(str, str2, deviceInfoICacheModel.token, new IoTCallback() { // from class: com.aliyun.alink.business.devicecenter.api.discovery.LocalDeviceMgr.12.2
                                        @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
                                        public void onFailure(IoTRequest ioTRequest, Exception exc) {
                                        }

                                        @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
                                        public void onResponse(IoTRequest ioTRequest, IoTResponse ioTResponse) {
                                            if (ioTResponse == null || ioTResponse.getCode() != 200 || ioTResponse.getData() == null) {
                                                return;
                                            }
                                            ALog.d("LocalDeviceMgr", "checkToken success code=200");
                                            try {
                                                LKDeviceInfo lKDeviceInfo = (LKDeviceInfo) JSON.parseObject(ioTResponse.getData().toString(), LKDeviceInfo.class);
                                                if (lKDeviceInfo == null) {
                                                    StringBuilder sb4 = new StringBuilder();
                                                    sb4.append("invalid data:");
                                                    sb4.append(ioTResponse.getData());
                                                    ALog.w("LocalDeviceMgr", sb4.toString());
                                                    return;
                                                }
                                                if (atomicBoolean.get()) {
                                                    ALog.d("LocalDeviceMgr", "getDeviceToken hasGetDeviceToken=true, check token return.");
                                                    return;
                                                }
                                                if (TextUtils.isEmpty(lKDeviceInfo.deviceName)) {
                                                    return;
                                                }
                                                AnonymousClass12 anonymousClass12 = AnonymousClass12.this;
                                                LocalDeviceMgr.this.stopGetDeviceToken(str, str2);
                                                AnonymousClass12 anonymousClass122 = AnonymousClass12.this;
                                                if (iOnTokenGetListerner == null || atomicBoolean.get()) {
                                                    return;
                                                }
                                                StringBuilder sb5 = new StringBuilder();
                                                sb5.append("getDeviceTokenL onSuccess checkToken listener=");
                                                sb5.append(iOnTokenGetListerner);
                                                ALog.i("LocalDeviceMgr", sb5.toString());
                                                atomicBoolean.set(true);
                                                GetTokenResult getTokenResult = new GetTokenResult();
                                                AnonymousClass12 anonymousClass123 = AnonymousClass12.this;
                                                getTokenResult.productKey = str;
                                                getTokenResult.deviceName = str2;
                                                getTokenResult.token = deviceInfoICacheModel.token;
                                                iOnTokenGetListerner.onSuccess(getTokenResult);
                                            } catch (Exception unused) {
                                            }
                                        }
                                    });
                                }
                            }
                        }, 5L, 5L, TimeUnit.SECONDS);
                    }
                    MultiTimerTaskWrapper multiTimerTaskWrapper = new MultiTimerTaskWrapper(discoverChainProcessor, timerUtils, scheduledFutureScheduleAtFixedRate);
                    synchronized (this.f10088c) {
                        HashMap<String, MultiTimerTaskWrapper> map2 = this.f10088c;
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append(str);
                        sb4.append("&&");
                        sb4.append(str2);
                        map2.put(sb4.toString(), multiTimerTaskWrapper);
                    }
                    return;
                }
                ALog.w("LocalDeviceMgr", "getDeviceToken timeout<interval.");
                DeviceCenterBiz.getInstance().runOnUIThread(new Runnable() { // from class: com.aliyun.alink.business.devicecenter.api.discovery.LocalDeviceMgr.8
                    @Override // java.lang.Runnable
                    public void run() {
                        ALog.w("LocalDeviceMgr", "getDeviceTokenL onFail error listener=" + iOnTokenGetListerner + ", hasGetDeviceToken=" + atomicBoolean.get());
                        if (iOnTokenGetListerner == null || !atomicBoolean.compareAndSet(false, true)) {
                            return;
                        }
                        DCErrorCode dCErrorCode = new DCErrorCode("USER_FAIL", DCErrorCode.PF_USER_INVOKE_ERROR);
                        dCErrorCode.setSubcode(DCErrorCode.SUBCODE_UIE_GET_DEVICE_TOKEN_PARAMS_INVALID);
                        dCErrorCode.setMsg("timeoutParamsInvalid");
                        iOnTokenGetListerner.onFail(dCErrorCode);
                    }
                });
                return;
            }
            ALog.w("LocalDeviceMgr", "getDeviceToken pk or dn empty.");
            DeviceCenterBiz.getInstance().runOnUIThread(new Runnable() { // from class: com.aliyun.alink.business.devicecenter.api.discovery.LocalDeviceMgr.7
                @Override // java.lang.Runnable
                public void run() {
                    ALog.w("LocalDeviceMgr", "getDeviceTokenL onFail listener=" + iOnTokenGetListerner + ", hasGetDeviceToken=" + atomicBoolean.get());
                    if (iOnTokenGetListerner == null || !atomicBoolean.compareAndSet(false, true)) {
                        return;
                    }
                    DCErrorCode dCErrorCode = new DCErrorCode("USER_FAIL", DCErrorCode.PF_USER_INVOKE_ERROR);
                    dCErrorCode.setSubcode(DCErrorCode.SUBCODE_UIE_GET_DEVICE_TOKEN_PARAMS_INVALID);
                    dCErrorCode.setMsg("paramsInvalid");
                    iOnTokenGetListerner.onFail(dCErrorCode);
                }
            });
            return;
        }
        throw new IllegalArgumentException("getDeviceToken listener is null.");
    }

    @Override // com.aliyun.alink.business.devicecenter.api.discovery.ILocalDeviceMgr
    public void stopGetDeviceToken(String str, String str2) {
        ALog.d("LocalDeviceMgr", "stopGetDeviceToken() called with: productKey = [" + str + "], deviceName = [" + str2 + "]");
        if (TextUtils.isEmpty(str) && TextUtils.isEmpty(str2)) {
            return;
        }
        synchronized (this.f10088c) {
            try {
                HashMap<String, MultiTimerTaskWrapper> map = this.f10088c;
                if (map != null && !map.isEmpty()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(str);
                    sb.append("&&");
                    sb.append(str2);
                    String string = sb.toString();
                    MultiTimerTaskWrapper multiTimerTaskWrapper = this.f10088c.get(string);
                    if (multiTimerTaskWrapper == null) {
                        return;
                    }
                    multiTimerTaskWrapper.cancelTimerTask(TimerUtils.MSG_GET_TOKEN_TIMEOUT);
                    this.f10088c.remove(string);
                }
            } finally {
            }
        }
    }

    public final void a(Context context, EnumSet<DiscoveryType> enumSet, Map<String, Object> map, IDeviceDiscoveryListener iDeviceDiscoveryListener) {
        DeviceCenterBiz.getInstance().setAppContext(context);
        BLEScannerProxy bLEScannerProxy = BLEScannerProxy.getInstance();
        int i2 = UnprovisionedBluetoothMeshDevice.GENIE_SIGMESH;
        ILeScanCallback iLeScanCallback = new ILeScanCallback() { // from class: com.aliyun.alink.business.devicecenter.api.discovery.LocalDeviceMgr.4
            @Override // com.alibaba.ailabs.iot.aisbase.callback.ILeScanCallback
            public void onAliBLEDeviceFound(BluetoothDeviceWrapper bluetoothDeviceWrapper, BluetoothDeviceSubtype bluetoothDeviceSubtype) {
            }

            @Override // com.alibaba.ailabs.iot.aisbase.callback.ILeScanCallback
            public void onStartScan() {
            }

            @Override // com.alibaba.ailabs.iot.aisbase.callback.ILeScanCallback
            public void onStopScan() {
            }
        };
        this.f10091f = iLeScanCallback;
        bLEScannerProxy.startLeScan(context, 0, false, i2, iLeScanCallback);
        a();
        if (enumSet != null && enumSet.size() >= 1) {
            if (this.f10086a == null) {
                this.f10086a = new DiscoverListenerAdapter(iDeviceDiscoveryListener);
            }
            Iterator<DiscoveryType> it = enumSet.iterator();
            while (it.hasNext()) {
                DiscoveryType next = it.next();
                if (DiscoveryType.CLOUD_BLE_MESH_DEVICE != next && DiscoveryType.APP_FOUND_BLE_MESH_DEVICE != next) {
                    IDiscoverChain iDiscoverChainCreateDiscoverChain = DiscoveryPluginMgr.getInstance().createDiscoverChain(next, context, map);
                    if (iDiscoverChainCreateDiscoverChain != null && iDiscoverChainCreateDiscoverChain.isSupport()) {
                        iDiscoverChainCreateDiscoverChain.addDiscoveryType(next);
                        this.f10087b.addChain(iDiscoverChainCreateDiscoverChain);
                    } else {
                        ALog.w("LocalDeviceMgr", "not support discoveryType: " + next);
                    }
                }
            }
            DiscoveryType discoveryType = DiscoveryType.CLOUD_BLE_MESH_DEVICE;
            if (enumSet.contains(discoveryType) && enumSet.contains(DiscoveryType.APP_FOUND_BLE_MESH_DEVICE)) {
                if (DCEnvHelper.hasMeshScanAbility()) {
                    this.f10087b.addChain(new BleMeshLocalAndCloudMixDiscoverChain(DeviceCenterBiz.getInstance().getAppContext()));
                }
            } else {
                if (enumSet.contains(discoveryType)) {
                    this.f10087b.addChain(new CloudBleMeshDiscoverChain(DeviceCenterBiz.getInstance().getAppContext()));
                }
                if (enumSet.contains(DiscoveryType.APP_FOUND_BLE_MESH_DEVICE) && DCEnvHelper.hasMeshScanAbility()) {
                    this.f10087b.addChain(new BleMeshDiscoverChain(DeviceCenterBiz.getInstance().getAppContext()));
                }
            }
            if (this.f10087b.getChainSize() < 1) {
                ALog.w("LocalDeviceMgr", "startDiscovery invalid discovery type.");
                return;
            }
            this.f10087b.startDiscover(this.f10086a);
            if (this.f10091f != null) {
                BLEScannerProxy.getInstance().stopScan(this.f10091f);
                return;
            }
            return;
        }
        ALog.w("LocalDeviceMgr", "startDiscovery empty discovery type.");
    }

    public final void a() {
        DiscoverChainProcessor discoverChainProcessor = this.f10087b;
        if (discoverChainProcessor != null) {
            discoverChainProcessor.stopDiscover();
        }
        DiscoverListenerAdapter discoverListenerAdapter = this.f10086a;
        if (discoverListenerAdapter != null) {
            discoverListenerAdapter.destroy();
            this.f10086a = null;
        }
    }

    @Override // com.aliyun.alink.business.devicecenter.api.discovery.ILocalDeviceMgr
    @Deprecated
    public void getDeviceToken(String str, String str2, int i2, IOnDeviceTokenGetListener iOnDeviceTokenGetListener) {
        ALog.i("LocalDeviceMgr", "getDeviceToken() called with: productKey = [" + str + "], deviceName = [" + str2 + "], timeout = [" + i2 + "], listener = [" + iOnDeviceTokenGetListener + "]");
        if (DeviceCenterBiz.getInstance().getAppContext() == null) {
            ALog.e("LocalDeviceMgr", "getDeviceToken error, call getDeviceToken with context.");
            if (iOnDeviceTokenGetListener != null) {
                iOnDeviceTokenGetListener.onFail("Context=null,Call getDeviceToken method with context.");
                return;
            }
            return;
        }
        getDeviceToken(DeviceCenterBiz.getInstance().getAppContext(), str, str2, i2, iOnDeviceTokenGetListener);
    }
}
