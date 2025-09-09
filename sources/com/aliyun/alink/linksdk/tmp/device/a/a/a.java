package com.aliyun.alink.linksdk.tmp.device.a.a;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.aliyun.alink.linksdk.cmp.core.base.ARequest;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener;
import com.aliyun.alink.linksdk.cmp.manager.discovery.DiscoveryMessage;
import com.aliyun.alink.linksdk.tmp.TmpSdk;
import com.aliyun.alink.linksdk.tmp.api.DevFoundOutputParams;
import com.aliyun.alink.linksdk.tmp.api.DeviceBasicData;
import com.aliyun.alink.linksdk.tmp.api.DeviceManager;
import com.aliyun.alink.linksdk.tmp.api.IDiscoveryFilter;
import com.aliyun.alink.linksdk.tmp.api.OutputParams;
import com.aliyun.alink.linksdk.tmp.connect.e;
import com.aliyun.alink.linksdk.tmp.connect.entity.cmp.i;
import com.aliyun.alink.linksdk.tmp.data.cloud.CloudLcaRequestParams;
import com.aliyun.alink.linksdk.tmp.data.discovery.DiscoveryConfig;
import com.aliyun.alink.linksdk.tmp.device.deviceshadow.DeviceShadowMgr;
import com.aliyun.alink.linksdk.tmp.device.payload.discovery.DiscoveryRequestPayload;
import com.aliyun.alink.linksdk.tmp.device.request.other.GetDeviceNetTypesSupportedRequest;
import com.aliyun.alink.linksdk.tmp.event.INotifyHandler;
import com.aliyun.alink.linksdk.tmp.listener.IDevListener;
import com.aliyun.alink.linksdk.tmp.listener.IProcessListener;
import com.aliyun.alink.linksdk.tmp.service.DevService;
import com.aliyun.alink.linksdk.tmp.storage.TmpStorage;
import com.aliyun.alink.linksdk.tmp.utils.CloudUtils;
import com.aliyun.alink.linksdk.tmp.utils.ErrorInfo;
import com.aliyun.alink.linksdk.tmp.utils.LogCat;
import com.aliyun.alink.linksdk.tmp.utils.TextHelper;
import com.aliyun.alink.linksdk.tmp.utils.TmpEnum;
import com.aliyun.alink.linksdk.tmp.utils.WifiManagerUtil;
import com.aliyun.alink.linksdk.tools.AError;
import com.aliyun.alink.linksdk.tools.ALog;
import com.aliyun.iot.breeze.mix.ConnectionCallback;
import com.aliyun.iot.breeze.mix.MixBleDelegate;
import com.aliyun.linksdk.alcs.AlcsCmpSDK;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes2.dex */
public class a extends com.aliyun.alink.linksdk.tmp.device.a.d<a> implements INotifyHandler {

    /* renamed from: y, reason: collision with root package name */
    private static final String f11229y = "[Tmp]DiscoveryTask";

    /* renamed from: n, reason: collision with root package name */
    protected b f11230n;

    /* renamed from: o, reason: collision with root package name */
    protected RunnableC0077a f11231o;

    /* renamed from: p, reason: collision with root package name */
    protected Handler f11232p;

    /* renamed from: q, reason: collision with root package name */
    protected long f11233q;

    /* renamed from: r, reason: collision with root package name */
    protected com.aliyun.alink.linksdk.tmp.connect.d f11234r;

    /* renamed from: s, reason: collision with root package name */
    protected IDiscoveryFilter f11235s;

    /* renamed from: t, reason: collision with root package name */
    protected Object f11236t;

    /* renamed from: u, reason: collision with root package name */
    protected Map<String, DeviceBasicData> f11237u;

    /* renamed from: v, reason: collision with root package name */
    protected List<c> f11238v;

    /* renamed from: w, reason: collision with root package name */
    protected DiscoveryConfig f11239w;

    /* renamed from: x, reason: collision with root package name */
    protected volatile boolean f11240x;

    /* renamed from: com.aliyun.alink.linksdk.tmp.device.a.a.a$1, reason: invalid class name */
    class AnonymousClass1 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ DiscoveryMessage f11241a;

        AnonymousClass1(DiscoveryMessage discoveryMessage) {
            this.f11241a = discoveryMessage;
        }

        @Override // java.lang.Runnable
        public void run() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            final int deviceNetType;
            String deviceSupportedNetTypesByPk = DeviceShadowMgr.getInstance().getDeviceSupportedNetTypesByPk(this.f11241a.productKey);
            if (TextUtils.isEmpty(deviceSupportedNetTypesByPk)) {
                DeviceShadowMgr.getInstance().updateDeviceNetTypesSupportedByPk(this.f11241a.productKey, true, new IProcessListener() { // from class: com.aliyun.alink.linksdk.tmp.device.a.a.a.1.1
                    @Override // com.aliyun.alink.linksdk.tmp.listener.IProcessListener
                    public void onFail(ErrorInfo errorInfo) {
                        TmpSdk.mHandler.post(new Runnable() { // from class: com.aliyun.alink.linksdk.tmp.device.a.a.a.1.1.2
                            @Override // java.lang.Runnable
                            public void run() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                                AnonymousClass1 anonymousClass1 = AnonymousClass1.this;
                                a.this.a(anonymousClass1.f11241a, TmpEnum.DeviceNetType.NET_UNKNOWN.getValue(), (String) null);
                            }
                        });
                    }

                    @Override // com.aliyun.alink.linksdk.tmp.listener.IProcessListener
                    public void onSuccess(Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                        final int deviceNetType2;
                        try {
                            deviceNetType2 = TmpEnum.DeviceNetType.formatDeviceNetType((List) ((GetDeviceNetTypesSupportedRequest.GetDeviceNetTypesSupportedResponse) JSON.parseObject(obj.toString(), GetDeviceNetTypesSupportedRequest.GetDeviceNetTypesSupportedResponse.class)).data);
                        } catch (Exception e2) {
                            ALog.e(a.f11229y, "  e:" + e2.toString());
                            deviceNetType2 = 0;
                        }
                        final String dnByMac = TmpEnum.DeviceNetType.isWifiBtCombo(deviceNetType2) ? AnonymousClass1.this.f11241a.modelType.equalsIgnoreCase("2") ? TmpStorage.getInstance().getDnByMac(AnonymousClass1.this.f11241a.deviceName) : TmpStorage.getInstance().getMacByDn(AnonymousClass1.this.f11241a.deviceName) : null;
                        TmpSdk.mHandler.post(new Runnable() { // from class: com.aliyun.alink.linksdk.tmp.device.a.a.a.1.1.1
                            @Override // java.lang.Runnable
                            public void run() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                                AnonymousClass1 anonymousClass1 = AnonymousClass1.this;
                                a.this.a(anonymousClass1.f11241a, deviceNetType2, dnByMac);
                            }
                        });
                    }
                });
                return;
            }
            try {
                deviceNetType = TmpEnum.DeviceNetType.formatDeviceNetType((List) ((GetDeviceNetTypesSupportedRequest.GetDeviceNetTypesSupportedResponse) JSON.parseObject(deviceSupportedNetTypesByPk, GetDeviceNetTypesSupportedRequest.GetDeviceNetTypesSupportedResponse.class)).data);
            } catch (Exception e2) {
                ALog.e(a.f11229y, "cached updateDeviceNetTypesSupported e:" + e2.toString());
                deviceNetType = 0;
            }
            final String dnByMac = TmpEnum.DeviceNetType.isWifiBtCombo(deviceNetType) ? this.f11241a.modelType.equalsIgnoreCase("2") ? TmpStorage.getInstance().getDnByMac(this.f11241a.deviceName) : TmpStorage.getInstance().getMacByDn(this.f11241a.deviceName) : null;
            TmpSdk.mHandler.post(new Runnable() { // from class: com.aliyun.alink.linksdk.tmp.device.a.a.a.1.2
                @Override // java.lang.Runnable
                public void run() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    AnonymousClass1 anonymousClass1 = AnonymousClass1.this;
                    a.this.a(anonymousClass1.f11241a, deviceNetType, dnByMac);
                }
            });
        }
    }

    /* renamed from: com.aliyun.alink.linksdk.tmp.device.a.a.a$a, reason: collision with other inner class name */
    public static class RunnableC0077a implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        protected WeakReference<a> f11255a;

        public RunnableC0077a(a aVar) {
            this.f11255a = new WeakReference<>(aVar);
        }

        @Override // java.lang.Runnable
        public void run() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            a aVar = this.f11255a.get();
            if (aVar != null) {
                aVar.d();
            }
        }
    }

    public static class b implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        protected WeakReference<a> f11256a;

        public b(a aVar) {
            this.f11256a = new WeakReference<>(aVar);
        }

        @Override // java.lang.Runnable
        public void run() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            a aVar = this.f11256a.get();
            if (aVar != null) {
                aVar.b();
            }
        }
    }

    public a(com.aliyun.alink.linksdk.tmp.connect.b bVar, IDevListener iDevListener) {
        super(null, iDevListener);
        this.f11294i = bVar;
        this.f11232p = new Handler(Looper.getMainLooper());
        this.f11230n = new b(this);
        this.f11231o = new RunnableC0077a(this);
        this.f11237u = new ConcurrentHashMap();
        this.f11238v = new CopyOnWriteArrayList();
        this.f11240x = true;
    }

    public boolean c() {
        return this.f11240x;
    }

    protected void d() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f11229y, "startProbeDifferentDeivces");
        List<DeviceBasicData> allDeviceDataList = DeviceManager.getInstance().getAllDeviceDataList();
        if (allDeviceDataList == null || allDeviceDataList.isEmpty()) {
            ALog.d(f11229y, "startProbeDifferentDeivces allFoundDeviceList empty");
            return;
        }
        for (final DeviceBasicData deviceBasicData : allDeviceDataList) {
            if (!this.f11237u.containsKey(deviceBasicData.getDevId())) {
                c cVar = new c(this.f11293h, this.f11294i, deviceBasicData, new IDevListener() { // from class: com.aliyun.alink.linksdk.tmp.device.a.a.a.2
                    @Override // com.aliyun.alink.linksdk.tmp.listener.IDevListener
                    public void onFail(Object obj, ErrorInfo errorInfo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                        if (DevService.isDeviceWifiAndBleCombo(deviceBasicData.supportedNetType) && "2".equalsIgnoreCase(deviceBasicData.modelType)) {
                            DeviceBasicData deviceBasicData2 = DeviceManager.getInstance().getDeviceBasicData(TextHelper.combineStr(deviceBasicData.productKey, TmpStorage.getInstance().getDnByMac(deviceBasicData.mac)));
                            if (deviceBasicData2 != null) {
                                deviceBasicData2.localDiscoveryType &= ~TmpEnum.DeviceNetType.NET_BT.getValue();
                                ALog.d(a.f11229y, " combo ble offline device localDiscoveryType :" + deviceBasicData2.localDiscoveryType + deviceBasicData2);
                            }
                        }
                        DeviceManager.getInstance().removeDeviceBasicData(deviceBasicData.getDevId());
                        ALog.d(a.f11229y, "ProbeTask onFail basicData:" + deviceBasicData);
                        com.aliyun.alink.linksdk.tmp.device.d.a.a().a(deviceBasicData, TmpEnum.DiscoveryDeviceState.DISCOVERY_STATE_OFFLINE);
                    }

                    @Override // com.aliyun.alink.linksdk.tmp.listener.IDevListener
                    public void onSuccess(Object obj, OutputParams outputParams) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                        ALog.d(a.f11229y, "ProbeTask onSuccess basicData:" + deviceBasicData);
                        com.aliyun.alink.linksdk.tmp.device.d.a.a().a(deviceBasicData, TmpEnum.DiscoveryDeviceState.DISCOVERY_STATE_ONLINE);
                    }
                });
                new com.aliyun.alink.linksdk.tmp.device.a.c().b(cVar).a();
                this.f11238v.add(cVar);
            }
        }
    }

    protected void e() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f11229y, "stopProbeDifferentDeivces");
        this.f11232p.removeCallbacks(this.f11231o);
        List<c> list = this.f11238v;
        if (list == null || list.isEmpty()) {
            ALog.d(f11229y, "stopProbeDifferentDeivces mProbeTaskFlowList empty");
            return;
        }
        Iterator<c> it = this.f11238v.iterator();
        while (it.hasNext()) {
            it.next().b();
        }
        this.f11238v.clear();
    }

    protected void f() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Object obj = this.f11236t;
        if (obj == null || !(obj instanceof CloudLcaRequestParams)) {
            return;
        }
        CloudLcaRequestParams cloudLcaRequestParams = (CloudLcaRequestParams) obj;
        CloudUtils.getLcaDeviceList(cloudLcaRequestParams.groupId, cloudLcaRequestParams.gatewayIotId, new IConnectSendListener() { // from class: com.aliyun.alink.linksdk.tmp.device.a.a.a.3
            @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener
            public void onFailure(ARequest aRequest, AError aError) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                ALog.e(a.f11229y, "queryCloudLcaDeviceList onFailure aRequest:" + aRequest + " aError:" + aError);
            }

            /* JADX WARN: Removed duplicated region for block: B:39:0x00d6  */
            @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public void onResponse(com.aliyun.alink.linksdk.cmp.core.base.ARequest r11, com.aliyun.alink.linksdk.cmp.core.base.AResponse r12) throws java.lang.IllegalAccessException, java.lang.IllegalArgumentException, java.lang.reflect.InvocationTargetException {
                /*
                    Method dump skipped, instructions count: 332
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.aliyun.alink.linksdk.tmp.device.a.a.a.AnonymousClass3.onResponse(com.aliyun.alink.linksdk.cmp.core.base.ARequest, com.aliyun.alink.linksdk.cmp.core.base.AResponse):void");
            }
        });
    }

    @Override // com.aliyun.alink.linksdk.tmp.event.INotifyHandler
    public void onMessage(com.aliyun.alink.linksdk.tmp.connect.d dVar, e eVar) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        a2(dVar, eVar);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public a a(IDiscoveryFilter iDiscoveryFilter) {
        this.f11235s = iDiscoveryFilter;
        return (a) this.f11289d;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public a b(Object obj) {
        this.f11236t = obj;
        return (a) this.f11289d;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public a a(DiscoveryConfig discoveryConfig) {
        this.f11239w = discoveryConfig;
        if (discoveryConfig != null) {
            this.f11236t = discoveryConfig.cloudLcaRequestParams;
        }
        return (a) this.f11289d;
    }

    public void b() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        LogCat.d(f11229y, "onTimeOut");
        b(true);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.aliyun.alink.linksdk.tmp.device.a.d
    protected void b(com.aliyun.alink.linksdk.tmp.connect.d dVar, ErrorInfo errorInfo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.f11232p.removeCallbacks(this.f11230n);
        this.f11294i.b();
        IDevListener iDevListener = this.f11291f;
        if (iDevListener == null) {
            LogCat.w(f11229y, "onFlowError empty error");
        } else {
            this.f11291f = null;
            iDevListener.onSuccess(this.f11290e, null);
        }
    }

    /* renamed from: a, reason: avoid collision after fix types in other method */
    public void a2(com.aliyun.alink.linksdk.tmp.connect.d dVar, e eVar) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (eVar == null || eVar.a() == null) {
            LogCat.e(f11229y, "addDevice error response null or unsuccess");
            return;
        }
        DiscoveryMessage discoveryMessage = (DiscoveryMessage) ((i) eVar).a().data;
        if (discoveryMessage == null) {
            ALog.e(f11229y, "onDeviceFound discoveryMessage or deviceInfo null");
        } else {
            AsyncTask.THREAD_POOL_EXECUTOR.execute(new AnonymousClass1(discoveryMessage));
        }
    }

    @Override // com.aliyun.alink.linksdk.tmp.device.a.a
    /* renamed from: b, reason: merged with bridge method [inline-methods] */
    public void a(com.aliyun.alink.linksdk.tmp.connect.d dVar, e eVar) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        d();
        this.f11240x = false;
        super.a((a) dVar, (com.aliyun.alink.linksdk.tmp.connect.d) eVar);
    }

    protected void a(DiscoveryMessage discoveryMessage, int i2, String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        boolean z2 = true;
        DeviceBasicData deviceBasicData = new DeviceBasicData(true);
        deviceBasicData.setProductKey(discoveryMessage.productKey);
        deviceBasicData.setDeviceName(discoveryMessage.deviceName);
        deviceBasicData.setModelType(discoveryMessage.modelType);
        deviceBasicData.setAddr(discoveryMessage.getIp());
        deviceBasicData.setPort(discoveryMessage.getPort());
        deviceBasicData.setSupportedNetType(i2);
        deviceBasicData.mac = discoveryMessage.mac;
        deviceBasicData.isPluginFound = true;
        deviceBasicData.extraData = discoveryMessage.extraData;
        if (!"1".equalsIgnoreCase(discoveryMessage.modelType) && "2".equalsIgnoreCase(discoveryMessage.modelType)) {
            deviceBasicData.localDiscoveryType = TmpEnum.DeviceNetType.NET_BT.getValue();
        } else {
            deviceBasicData.localDiscoveryType = TmpEnum.DeviceNetType.NET_WIFI.getValue();
        }
        this.f11237u.put(deviceBasicData.getDevId(), deviceBasicData);
        if (TmpEnum.DeviceNetType.isWifiBtCombo(i2) && !TextUtils.isEmpty(str)) {
            DeviceBasicData deviceBasicData2 = DeviceManager.getInstance().getDeviceBasicData(TextHelper.combineStr(discoveryMessage.productKey, str));
            if (deviceBasicData2 != null) {
                if (deviceBasicData.localDiscoveryType == TmpEnum.DeviceNetType.NET_WIFI.getValue() && (deviceBasicData2.localDiscoveryType | TmpEnum.DeviceNetType.NET_BT.getValue()) > 0) {
                    ALog.d(f11229y, "discovery combo wifi, try to close ble anotherComboDevData:" + deviceBasicData2 + " basicData:" + deviceBasicData);
                    MixBleDelegate.getInstance().close(deviceBasicData2.mac, (ConnectionCallback) null);
                }
                deviceBasicData2.localDiscoveryType |= deviceBasicData.localDiscoveryType;
                Map<String, Object> map = deviceBasicData2.extraData;
                if (map == null) {
                    deviceBasicData2.extraData = deviceBasicData.extraData;
                } else {
                    map.putAll(deviceBasicData.extraData);
                }
                deviceBasicData.localDiscoveryType = deviceBasicData2.localDiscoveryType;
            }
        }
        DeviceBasicData deviceBasicData3 = DeviceManager.getInstance().getDeviceBasicData(deviceBasicData.getDevId());
        if (deviceBasicData3 != null && !deviceBasicData3.isPluginFound) {
            deviceBasicData3.isPluginFound = true;
        }
        DeviceManager.getInstance().addDeviceBasicData(deviceBasicData);
        IDiscoveryFilter iDiscoveryFilter = this.f11235s;
        if (iDiscoveryFilter != null && !iDiscoveryFilter.doFilter(deviceBasicData)) {
            z2 = false;
        }
        com.aliyun.alink.linksdk.tmp.device.d.a.a().a(deviceBasicData, TmpEnum.DiscoveryDeviceState.DISCOVERY_STATE_ONLINE);
        IDevListener iDevListener = this.f11291f;
        ALog.d(f11229y, "onDeviceFound tmpHander:" + iDevListener + " isNeedNotify:" + z2 + " mFilter:" + this.f11235s + " basicData:" + deviceBasicData + " comboDeviceName:" + str + " nettype:" + i2);
        if (iDevListener == null || !z2) {
            return;
        }
        DevFoundOutputParams devFoundOutputParams = new DevFoundOutputParams();
        devFoundOutputParams.setProductKey(deviceBasicData.getProductKey());
        devFoundOutputParams.setDeviceName(deviceBasicData.getDeviceName());
        devFoundOutputParams.setModelType(deviceBasicData.getModelType());
        iDevListener.onSuccess(this.f11290e, devFoundOutputParams);
    }

    public boolean b(boolean z2) {
        ALog.d(f11229y, "stop isTimeout:" + z2);
        if (!z2) {
            e();
        }
        b(this.f11234r, (ErrorInfo) null);
        return true;
    }

    public void a(long j2) {
        this.f11233q = j2;
    }

    @Override // com.aliyun.alink.linksdk.tmp.device.a.d
    protected void a(com.aliyun.alink.linksdk.tmp.connect.d dVar, e eVar, ErrorInfo errorInfo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.f11232p.removeCallbacks(this.f11230n);
        this.f11294i.b();
        IDevListener iDevListener = this.f11291f;
        if (iDevListener == null) {
            LogCat.e(f11229y, "onFlowComplete handler empty error");
        } else {
            this.f11291f = null;
            iDevListener.onSuccess(this.f11290e, null);
        }
    }

    @Override // com.aliyun.alink.linksdk.tmp.device.a.a
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void b(com.aliyun.alink.linksdk.tmp.connect.d dVar, ErrorInfo errorInfo) {
        this.f11240x = false;
        super.b((a) dVar, errorInfo);
    }

    @Override // com.aliyun.alink.linksdk.tmp.device.a.d, com.aliyun.alink.linksdk.tmp.device.a.a
    public boolean a() throws IllegalAccessException, SocketException, IllegalArgumentException, InvocationTargetException {
        super.a();
        f();
        InetAddress broadcast = WifiManagerUtil.getBroadcast(WifiManagerUtil.getIpAddress(WifiManagerUtil.NetworkType.ETHERNET));
        if (broadcast != null) {
            AlcsCmpSDK.DISCOVERY_ADDR = broadcast.getHostAddress();
        }
        DiscoveryRequestPayload discoveryRequestPayload = new DiscoveryRequestPayload();
        discoveryRequestPayload.setMethod("core.service.dev");
        this.f11234r = com.aliyun.alink.linksdk.tmp.connect.a.c.d().a(this.f11290e).b((com.aliyun.alink.linksdk.tmp.connect.a.c) discoveryRequestPayload).a(broadcast != null ? broadcast.getHostAddress() : AlcsCmpSDK.DISCOVERY_ADDR).c();
        this.f11294i.a((int) this.f11233q, this.f11239w, this);
        this.f11232p.postDelayed(this.f11230n, this.f11233q + 1000);
        this.f11232p.postDelayed(this.f11231o, this.f11233q);
        return true;
    }
}
