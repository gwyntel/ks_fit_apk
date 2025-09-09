package com.aliyun.alink.business.devicecenter.api.add;

import android.content.Context;
import android.text.TextUtils;
import com.aliyun.alink.business.devicecenter.BuildConfig;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.aliyun.alink.business.devicecenter.base.DCErrorCode;
import com.aliyun.alink.business.devicecenter.config.DeviceCenterBiz;
import com.aliyun.alink.business.devicecenter.config.IConfigCallback;
import com.aliyun.alink.business.devicecenter.config.IConfigStrategy;
import com.aliyun.alink.business.devicecenter.config.IExtraConfigStrategy;
import com.aliyun.alink.business.devicecenter.config.model.DCAlibabaConcurrentGateWayConfigParams;
import com.aliyun.alink.business.devicecenter.config.model.DCConfigParams;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.log.PerformanceLog;
import com.aliyun.alink.business.devicecenter.track.DCUserTrackV2;
import com.aliyun.alink.business.devicecenter.utils.DeviceInfoUtils;
import com.aliyun.alink.business.devicecenter.utils.NetworkTypeUtils;
import com.aliyun.alink.business.devicecenter.utils.WifiManagerUtil;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.umeng.message.common.inter.ITagManager;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class ConcurrentGateAddDeviceBiz {

    /* renamed from: a, reason: collision with root package name */
    public static volatile ConcurrentGateAddDeviceBiz f10043a;

    /* renamed from: d, reason: collision with root package name */
    public Context f10046d;

    /* renamed from: k, reason: collision with root package name */
    public IConcurrentAddDeviceStatusListener f10053k;

    /* renamed from: b, reason: collision with root package name */
    public IConcurrentAddDeviceListener f10044b = null;

    /* renamed from: c, reason: collision with root package name */
    public Map<String, List<String>> f10045c = new LinkedHashMap();

    /* renamed from: e, reason: collision with root package name */
    public InnerConfigCallback f10047e = new InnerConfigCallback();

    /* renamed from: f, reason: collision with root package name */
    public List<String> f10048f = new LinkedList();

    /* renamed from: g, reason: collision with root package name */
    public List<DeviceInfo> f10049g = new LinkedList();

    /* renamed from: h, reason: collision with root package name */
    public int f10050h = 0;

    /* renamed from: i, reason: collision with root package name */
    public final Map<String, IConfigStrategy> f10051i = new HashMap();

    /* renamed from: j, reason: collision with root package name */
    public Map<String, WeakReference<DeviceInfo>> f10052j = new LinkedHashMap();

    /* renamed from: com.aliyun.alink.business.devicecenter.api.add.ConcurrentGateAddDeviceBiz$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {

        /* renamed from: a, reason: collision with root package name */
        public static final /* synthetic */ int[] f10060a;

        static {
            int[] iArr = new int[AddDeviceState.values().length];
            f10060a = iArr;
            try {
                iArr[AddDeviceState.AddStatePrechecking.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f10060a[AddDeviceState.AddStateProvisionPreparing.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f10060a[AddDeviceState.AddStateProvisioning.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f10060a[AddDeviceState.AddStateProvisionOver.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    private class InnerConfigCallback implements IConfigCallback {
        public InnerConfigCallback() {
        }

        @Override // com.aliyun.alink.business.devicecenter.config.IDCFailCallback
        public void onFailure(DCErrorCode dCErrorCode) {
            ALog.e("ConcurrentGateAddDeviceBiz", "onFailure provision fail Callback, " + dCErrorCode);
            ConcurrentGateAddDeviceBiz.this.a(AddDeviceState.AddStateProvisionOver, -1, false, null, dCErrorCode);
        }

        @Override // com.aliyun.alink.business.devicecenter.config.IConfigCallback
        public void onStatus(final ProvisionStatus provisionStatus) {
            ALog.i("ConcurrentGateAddDeviceBiz", "onStatus status=" + provisionStatus + ",addDeviceListener=" + ConcurrentGateAddDeviceBiz.this.f10044b);
            if (provisionStatus != ProvisionStatus.PROVISION_START_IN_CONCURRENT_MODE) {
                final DeviceInfo deviceInfo = null;
                DeviceCenterBiz.getInstance().runOnUIThread(new Runnable() { // from class: com.aliyun.alink.business.devicecenter.api.add.ConcurrentGateAddDeviceBiz.InnerConfigCallback.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (ConcurrentGateAddDeviceBiz.this.f10044b != null) {
                            ConcurrentGateAddDeviceBiz.this.f10044b.onProvisionStatus(deviceInfo, provisionStatus);
                        }
                    }
                });
                return;
            }
            PerformanceLog.trace("ConcurrentGateAddDeviceBiz", "startProvision");
            Object extraParams = provisionStatus.getExtraParams(AlinkConstants.KEY_CACHE_START_PROVISION_DEVICE_INFO);
            if (extraParams instanceof DeviceInfo) {
                DeviceInfo deviceInfo2 = (DeviceInfo) extraParams;
                ConcurrentGateAddDeviceBiz.this.a(AddDeviceState.AddStateProvisioning, -1, true, deviceInfo2, null);
                String meshDeviceUniqueIDByDeviceInfo = DeviceInfoUtils.getMeshDeviceUniqueIDByDeviceInfo(deviceInfo2);
                ConcurrentGateAddDeviceBiz.this.a(false, meshDeviceUniqueIDByDeviceInfo, AlinkConstants.KEY_START_TIME_PROVISION, String.valueOf(System.currentTimeMillis()));
                ConcurrentGateAddDeviceBiz concurrentGateAddDeviceBiz = ConcurrentGateAddDeviceBiz.this;
                concurrentGateAddDeviceBiz.a(false, meshDeviceUniqueIDByDeviceInfo, AlinkConstants.KEY_WIFI_TYPE, concurrentGateAddDeviceBiz.a(concurrentGateAddDeviceBiz.f10046d));
                ConcurrentGateAddDeviceBiz concurrentGateAddDeviceBiz2 = ConcurrentGateAddDeviceBiz.this;
                concurrentGateAddDeviceBiz2.a(false, meshDeviceUniqueIDByDeviceInfo, AlinkConstants.KEY_HAS_SIM, String.valueOf(NetworkTypeUtils.hasSimCard(concurrentGateAddDeviceBiz2.f10046d)));
                ConcurrentGateAddDeviceBiz.this.a(false, meshDeviceUniqueIDByDeviceInfo, "sdkVersion", BuildConfig.SDK_VERSION);
                ConcurrentGateAddDeviceBiz.this.a(false, meshDeviceUniqueIDByDeviceInfo, AlinkConstants.KEY_LINKTYPE, deviceInfo2.linkType);
            }
        }

        @Override // com.aliyun.alink.business.devicecenter.config.IConfigCallback
        public void onSuccess(DeviceInfo deviceInfo) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append("success,info=");
                sb.append(deviceInfo);
                ALog.i("ConcurrentGateAddDeviceBiz", sb.toString());
                if (deviceInfo == null) {
                    return;
                }
                ConcurrentGateAddDeviceBiz.this.a(AddDeviceState.AddStateProvisionOver, -1, true, deviceInfo, null);
            } catch (Exception e2) {
                ALog.w("ConcurrentGateAddDeviceBiz", "onSuccess recv Callback，but parse error,e = " + e2.toString());
                e2.printStackTrace();
            }
        }
    }

    public static ConcurrentGateAddDeviceBiz getInstance() {
        if (f10043a == null) {
            synchronized (ConcurrentGateAddDeviceBiz.class) {
                try {
                    if (f10043a == null) {
                        f10043a = new ConcurrentGateAddDeviceBiz();
                    }
                } finally {
                }
            }
        }
        return f10043a;
    }

    public List<DeviceInfo> onConcurrentProvisionerIdle() {
        if (this.f10051i.size() <= 0) {
            return null;
        }
        IConfigStrategy configStrategy = DeviceCenterBiz.getInstance().getConfigStrategy(LinkType.ALI_BATCH_GATEWAY_MESH);
        Iterator<String> it = this.f10051i.keySet().iterator();
        while (it.hasNext()) {
            IConfigStrategy iConfigStrategy = this.f10051i.get(it.next());
            if ((iConfigStrategy instanceof IExtraConfigStrategy) && (configStrategy instanceof IExtraConfigStrategy)) {
                IExtraConfigStrategy iExtraConfigStrategy = (IExtraConfigStrategy) iConfigStrategy;
                if (iExtraConfigStrategy.getResetCount() > ((IExtraConfigStrategy) configStrategy).getResetCount()) {
                    configStrategy = iExtraConfigStrategy;
                }
            }
        }
        if (!(configStrategy instanceof IExtraConfigStrategy)) {
            return null;
        }
        IExtraConfigStrategy iExtraConfigStrategy2 = (IExtraConfigStrategy) configStrategy;
        if (iExtraConfigStrategy2.getResetCount() > 0) {
            return iExtraConfigStrategy2.getPrepareProvisionDevices();
        }
        return null;
    }

    public void stopConfig() {
        this.f10049g.clear();
        this.f10048f.clear();
        this.f10050h = 0;
        Map<String, IConfigStrategy> map = this.f10051i;
        if (map == null || map.size() <= 0) {
            return;
        }
        for (String str : this.f10051i.keySet()) {
            if (this.f10051i.get(str) != null) {
                this.f10051i.get(str).stopConfig();
            }
        }
    }

    public void a(Context context, List<DeviceInfo> list, IConcurrentAddDeviceListener iConcurrentAddDeviceListener) {
        DCAlibabaConcurrentGateWayConfigParams dCAlibabaConcurrentGateWayConfigParams;
        ALog.i("ConcurrentGateAddDeviceBiz", "startConcurrentAddDevice() call.");
        if (context != null) {
            this.f10046d = context;
            DeviceCenterBiz.getInstance().setAppContext(context);
            this.f10044b = iConcurrentAddDeviceListener;
            if (list != null && list.size() != 0) {
                ALog.i("ConcurrentGateAddDeviceBiz", "startConcurrentAddDevice() call 配网的数量: " + list.size());
                this.f10049g.addAll(list);
                HashMap map = new HashMap();
                for (DeviceInfo deviceInfo : list) {
                    this.f10052j.put(DeviceInfoUtils.getMeshDeviceUniqueIDByDeviceInfo(deviceInfo), new WeakReference<>(deviceInfo));
                    this.f10048f.remove(DeviceInfoUtils.getMeshDeviceUniqueIDByDeviceInfo(deviceInfo));
                    LinkType linkType = LinkType.ALI_GATEWAY_MESH;
                    if (linkType.getName().equalsIgnoreCase(deviceInfo.linkType)) {
                        if (TextUtils.isEmpty(deviceInfo.regIotId)) {
                            ALog.d("ConcurrentGateAddDeviceBiz", "startAddDevices: regIotId 为空,不作处理");
                            a(AddDeviceState.AddStateProvisionOver, -1, false, null, new DCErrorCode("SDKError", DCErrorCode.PF_SDK_ERROR).setSubcode(DCErrorCode.SUBCODE_SKE_START_CONFIG_EXCEPTION).setMsg("regIotId is null can not gateway provision").setExtra(deviceInfo));
                        } else {
                            if (map.containsKey(deviceInfo.regIotId) && map.get(deviceInfo.regIotId) != null) {
                                dCAlibabaConcurrentGateWayConfigParams = (DCAlibabaConcurrentGateWayConfigParams) map.get(deviceInfo.regIotId);
                            } else {
                                dCAlibabaConcurrentGateWayConfigParams = new DCAlibabaConcurrentGateWayConfigParams();
                                dCAlibabaConcurrentGateWayConfigParams.setGatewayIotId(deviceInfo.regIotId);
                                map.put(deviceInfo.regIotId, dCAlibabaConcurrentGateWayConfigParams);
                            }
                            dCAlibabaConcurrentGateWayConfigParams.linkType = linkType;
                            dCAlibabaConcurrentGateWayConfigParams.addDevice(deviceInfo);
                            map.put(deviceInfo.regIotId, dCAlibabaConcurrentGateWayConfigParams);
                            if (!this.f10051i.containsKey(deviceInfo.regIotId)) {
                                this.f10051i.put(deviceInfo.regIotId, DeviceCenterBiz.getInstance().getConfigStrategy(LinkType.ALI_BATCH_GATEWAY_MESH));
                            }
                        }
                    }
                }
                ALog.d("ConcurrentGateAddDeviceBiz", "startAddDevices() called with: context =, deviceInfos = [" + list.size() + "], listener = [" + iConcurrentAddDeviceListener + "]");
                StringBuilder sb = new StringBuilder();
                sb.append("startAddDevices: ateWayConfigParamsList.size()=");
                sb.append(map.size());
                ALog.d("ConcurrentGateAddDeviceBiz", sb.toString());
                if (map.size() > 0) {
                    try {
                        for (String str : this.f10051i.keySet()) {
                            if (this.f10051i.get(str) != null) {
                                this.f10051i.get(str).startConfig(this.f10047e, (DCConfigParams) map.get(str));
                            }
                        }
                        return;
                    } catch (Exception e2) {
                        ALog.e("ConcurrentGateAddDeviceBiz", e2.toString());
                        for (DeviceInfo deviceInfo2 : list) {
                            a(AddDeviceState.AddStateProvisionOver, -1, false, null, new DCErrorCode("SDKError", DCErrorCode.PF_SDK_ERROR).setSubcode(DCErrorCode.SUBCODE_SKE_START_CONFIG_EXCEPTION).setMsg("startConfig" + e2).setExtra(deviceInfo2));
                        }
                        return;
                    }
                }
                ALog.e("ConcurrentGateAddDeviceBiz", "gateWayConfigParamsList is 0, for concurrent-provision");
                return;
            }
            ALog.e("ConcurrentGateAddDeviceBiz", "startAddDevice, params error");
            a(AddDeviceState.AddStateProvisionOver, -1, false, null, new DCErrorCode(DCErrorCode.PARAM_ERROR_MSG, DCErrorCode.PF_PARAMS_ERROR).setSubcode(DCErrorCode.SUBCODE_PE_PRODUCTKEY_EMPTY).setMsg("pkError"));
            return;
        }
        ALog.e("ConcurrentGateAddDeviceBiz", "startConcurrentAddDevice context=null.");
        throw new RuntimeException("startAddDeviceParamContextNull");
    }

    public final synchronized void a(final AddDeviceState addDeviceState, final int i2, final boolean z2, DeviceInfo deviceInfo, final DCErrorCode dCErrorCode) {
        if (deviceInfo == null) {
            try {
                Object obj = dCErrorCode.extra;
                if (obj instanceof DeviceInfo) {
                    deviceInfo = (DeviceInfo) obj;
                } else if (obj instanceof Map) {
                    Map map = (Map) obj;
                    if (map.containsKey(AlinkConstants.KEY_CACHE_MESH_DEVICE_UNIQUE_ID)) {
                        WeakReference<DeviceInfo> weakReference = this.f10052j.get((String) map.get(AlinkConstants.KEY_CACHE_MESH_DEVICE_UNIQUE_ID));
                        if (weakReference != null) {
                            deviceInfo = weakReference.get();
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        final DeviceInfo deviceInfo2 = deviceInfo;
        if (addDeviceState == AddDeviceState.AddStateProvisionOver && !z2 && dCErrorCode != null) {
            ALog.e("ConcurrentGateAddDeviceBiz", "state=" + addDeviceState + ",isSuccess=" + z2 + ",info=" + deviceInfo2 + ",error=" + dCErrorCode);
        } else {
            ALog.i("ConcurrentGateAddDeviceBiz", "state=" + addDeviceState + ",isSuccess=" + z2 + ",info=" + deviceInfo2 + ",error=" + dCErrorCode);
        }
        DeviceCenterBiz.getInstance().runOnUIThread(new Runnable() { // from class: com.aliyun.alink.business.devicecenter.api.add.ConcurrentGateAddDeviceBiz.1
            @Override // java.lang.Runnable
            public void run() {
                ALog.d("ConcurrentGateAddDeviceBiz", "DeviceCenterBiz run: addDeviceState=" + addDeviceState);
                int i3 = AnonymousClass2.f10060a[addDeviceState.ordinal()];
                if (i3 == 1) {
                    if (ConcurrentGateAddDeviceBiz.this.f10044b != null) {
                        ConcurrentGateAddDeviceBiz.this.f10044b.onPreCheck(deviceInfo2, z2, dCErrorCode);
                        return;
                    }
                    return;
                }
                if (i3 == 2) {
                    if (ConcurrentGateAddDeviceBiz.this.f10044b != null) {
                        ConcurrentGateAddDeviceBiz.this.f10044b.onProvisionPrepare(deviceInfo2, i2);
                        return;
                    }
                    return;
                }
                if (i3 == 3) {
                    if (ConcurrentGateAddDeviceBiz.this.f10044b != null) {
                        ConcurrentGateAddDeviceBiz.this.f10044b.onProvisioning(deviceInfo2);
                        return;
                    }
                    return;
                }
                if (i3 != 4) {
                    return;
                }
                if (z2) {
                    ConcurrentGateAddDeviceBiz.this.a(deviceInfo2);
                } else {
                    ConcurrentGateAddDeviceBiz.this.a(dCErrorCode);
                }
                PerformanceLog.trace("ConcurrentGateAddDeviceBiz", "provisionResult", PerformanceLog.getJsonObject("result", z2 ? "success" : ITagManager.FAIL));
                ALog.d("ConcurrentGateAddDeviceBiz", "onProvisionedResult addDeviceListener=" + ConcurrentGateAddDeviceBiz.this.f10044b);
                if (!TextUtils.isEmpty(deviceInfo2.mac) && ConcurrentGateAddDeviceBiz.this.f10048f.contains(DeviceInfoUtils.getMeshDeviceUniqueIDByDeviceInfo(deviceInfo2))) {
                    ALog.w("ConcurrentGateAddDeviceBiz", "Duplicate provision over received, mac: " + deviceInfo2.mac);
                    return;
                }
                ConcurrentGateAddDeviceBiz.this.f10048f.add(DeviceInfoUtils.getMeshDeviceUniqueIDByDeviceInfo(deviceInfo2));
                if (ConcurrentGateAddDeviceBiz.this.f10044b != null) {
                    ConcurrentGateAddDeviceBiz.this.f10044b.onProvisionedResult(z2, deviceInfo2, dCErrorCode);
                }
                ConcurrentGateAddDeviceBiz.this.f10050h++;
                if (ConcurrentGateAddDeviceBiz.this.f10050h == ConcurrentGateAddDeviceBiz.this.f10049g.size()) {
                    ALog.e("ConcurrentGateAddDeviceBiz", "All provision task completed, call stopConfig");
                    ConcurrentGateAddDeviceBiz.this.stopConfig();
                    if (ConcurrentGateAddDeviceBiz.this.f10053k != null) {
                        ConcurrentGateAddDeviceBiz.this.f10053k.onIdle();
                    }
                }
            }
        });
    }

    public final void a(boolean z2, String str, String... strArr) {
        if (TextUtils.isDigitsOnly(str)) {
            return;
        }
        List<String> linkedList = this.f10045c.get(str);
        if (linkedList == null) {
            linkedList = new LinkedList<>();
        }
        this.f10045c.put(str, linkedList);
        linkedList.addAll(Arrays.asList(strArr));
        if (z2) {
            DCUserTrackV2 dCUserTrackV2 = new DCUserTrackV2();
            dCUserTrackV2.resetTrackData();
            dCUserTrackV2.addTrackData((String[]) linkedList.toArray(new String[0]));
            dCUserTrackV2.sendEvent();
        }
    }

    public final void a(Object obj) {
        ALog.d("ConcurrentGateAddDeviceBiz", "provisionTrack obj=" + obj);
        try {
            if (obj instanceof DeviceInfo) {
                String meshDeviceUniqueIDByDeviceInfo = DeviceInfoUtils.getMeshDeviceUniqueIDByDeviceInfo((DeviceInfo) obj);
                a(false, meshDeviceUniqueIDByDeviceInfo, AlinkConstants.KEY_END_TIME_PROVISION, String.valueOf(System.currentTimeMillis()));
                a(false, meshDeviceUniqueIDByDeviceInfo, "pk", ((DeviceInfo) obj).productKey);
                a(false, meshDeviceUniqueIDByDeviceInfo, AlinkConstants.KEY_DN, ((DeviceInfo) obj).deviceName);
                a(true, meshDeviceUniqueIDByDeviceInfo, AlinkConstants.KEY_PROVISION_RESULT, "1");
                return;
            }
            if (obj instanceof DCErrorCode) {
                DCErrorCode dCErrorCode = (DCErrorCode) obj;
                Object obj2 = dCErrorCode.extra;
                if (obj2 instanceof DeviceInfo) {
                    DeviceInfo deviceInfo = (DeviceInfo) obj2;
                    String meshDeviceUniqueIDByDeviceInfo2 = DeviceInfoUtils.getMeshDeviceUniqueIDByDeviceInfo(deviceInfo);
                    if (!TextUtils.isEmpty(deviceInfo.productKey)) {
                        a(true, meshDeviceUniqueIDByDeviceInfo2, "pk", deviceInfo.productKey);
                    }
                    if (!TextUtils.isEmpty(deviceInfo.deviceName)) {
                        a(true, meshDeviceUniqueIDByDeviceInfo2, AlinkConstants.KEY_DN, deviceInfo.deviceName);
                    }
                    a(false, meshDeviceUniqueIDByDeviceInfo2, AlinkConstants.KEY_END_TIME_PROVISION, String.valueOf(System.currentTimeMillis()));
                    a(false, meshDeviceUniqueIDByDeviceInfo2, "errorCode", dCErrorCode.code);
                    a(false, meshDeviceUniqueIDByDeviceInfo2, "subErrorCode", dCErrorCode.subcode);
                    a(false, meshDeviceUniqueIDByDeviceInfo2, AlinkConstants.KEY_SUB_ERROR_MSG, dCErrorCode.msg);
                    a(true, meshDeviceUniqueIDByDeviceInfo2, PushConstants.EXTRA, String.valueOf(dCErrorCode.extra));
                    return;
                }
                DCUserTrackV2 dCUserTrackV2 = new DCUserTrackV2();
                dCUserTrackV2.resetTrackData();
                dCUserTrackV2.addTrackData(AlinkConstants.KEY_END_TIME_PROVISION, String.valueOf(System.currentTimeMillis()));
                dCUserTrackV2.addTrackData("errorCode", dCErrorCode.code);
                dCUserTrackV2.addTrackData("subErrorCode", dCErrorCode.subcode);
                dCUserTrackV2.addTrackData(AlinkConstants.KEY_SUB_ERROR_MSG, dCErrorCode.msg);
                dCUserTrackV2.addTrackData(AlinkConstants.KEY_PROVISION_RESULT, "0");
                dCUserTrackV2.addTrackData(PushConstants.EXTRA, String.valueOf(dCErrorCode.extra));
                if (!String.valueOf(DCErrorCode.SUBCODE_PT_SAP_NO_SOFTAP).equals(dCErrorCode.code) && !String.valueOf(DCErrorCode.SUBCODE_PT_SAP_CONNECT_DEV_AP_FAILED).equals(dCErrorCode.code)) {
                    dCUserTrackV2.sendEvent();
                    return;
                }
                dCUserTrackV2.sendEvent(AlinkConstants.KEY_DC_PROVISION_DISCOVER);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public final String a(Context context) {
        if (context != null) {
            return new WifiManagerUtil(context).getWifiType();
        }
        throw new IllegalArgumentException("context=null");
    }
}
