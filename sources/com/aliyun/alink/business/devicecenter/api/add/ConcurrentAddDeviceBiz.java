package com.aliyun.alink.business.devicecenter.api.add;

import android.content.Context;
import android.text.TextUtils;
import com.aliyun.alink.business.devicecenter.BuildConfig;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.aliyun.alink.business.devicecenter.base.DCErrorCode;
import com.aliyun.alink.business.devicecenter.config.DeviceCenterBiz;
import com.aliyun.alink.business.devicecenter.config.IConfigCallback;
import com.aliyun.alink.business.devicecenter.config.IConfigStrategy;
import com.aliyun.alink.business.devicecenter.config.model.DCAlibabaConcurrentConfigParams;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.log.PerformanceLog;
import com.aliyun.alink.business.devicecenter.track.DCUserTrackV2;
import com.aliyun.alink.business.devicecenter.utils.DeviceInfoUtils;
import com.aliyun.alink.business.devicecenter.utils.NetworkTypeUtils;
import com.aliyun.alink.business.devicecenter.utils.WifiManagerUtil;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.umeng.message.common.inter.ITagManager;
import datasource.bean.ConfigurationData;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
public class ConcurrentAddDeviceBiz {

    /* renamed from: a, reason: collision with root package name */
    public static volatile ConcurrentAddDeviceBiz f10020a;

    /* renamed from: d, reason: collision with root package name */
    public Context f10023d;

    /* renamed from: k, reason: collision with root package name */
    public IConcurrentAddDeviceStatusListener f10030k;

    /* renamed from: b, reason: collision with root package name */
    public IConcurrentAddDeviceListener f10021b = null;

    /* renamed from: c, reason: collision with root package name */
    public Map<String, List<String>> f10022c = new LinkedHashMap();

    /* renamed from: e, reason: collision with root package name */
    public InnerConfigCallback f10024e = new InnerConfigCallback();

    /* renamed from: f, reason: collision with root package name */
    public List<String> f10025f = new LinkedList();

    /* renamed from: g, reason: collision with root package name */
    public List<DeviceInfo> f10026g = new LinkedList();

    /* renamed from: h, reason: collision with root package name */
    public int f10027h = 0;

    /* renamed from: i, reason: collision with root package name */
    public IConfigStrategy f10028i = null;

    /* renamed from: j, reason: collision with root package name */
    public Map<String, WeakReference<DeviceInfo>> f10029j = new LinkedHashMap();

    /* renamed from: l, reason: collision with root package name */
    public ConcurrentHashMap<String, ConfigurationData> f10031l = new ConcurrentHashMap<>();

    /* renamed from: com.aliyun.alink.business.devicecenter.api.add.ConcurrentAddDeviceBiz$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {

        /* renamed from: a, reason: collision with root package name */
        public static final /* synthetic */ int[] f10038a;

        static {
            int[] iArr = new int[AddDeviceState.values().length];
            f10038a = iArr;
            try {
                iArr[AddDeviceState.AddStatePrechecking.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f10038a[AddDeviceState.AddStateProvisionPreparing.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f10038a[AddDeviceState.AddStateProvisioning.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f10038a[AddDeviceState.AddStateProvisionOver.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    private class InnerConfigCallback implements IConfigCallback {
        public InnerConfigCallback() {
        }

        @Override // com.aliyun.alink.business.devicecenter.config.IDCFailCallback
        public void onFailure(DCErrorCode dCErrorCode) {
            ALog.e("ConcurrentAddDeviceBiz", "onFailure provision fail Callback, " + dCErrorCode);
            ConcurrentAddDeviceBiz.this.a(AddDeviceState.AddStateProvisionOver, -1, false, null, dCErrorCode);
        }

        @Override // com.aliyun.alink.business.devicecenter.config.IConfigCallback
        public void onStatus(final ProvisionStatus provisionStatus) {
            ALog.i("ConcurrentAddDeviceBiz", "onStatus status=" + provisionStatus + ",addDeviceListener=" + ConcurrentAddDeviceBiz.this.f10021b);
            if (provisionStatus != ProvisionStatus.PROVISION_START_IN_CONCURRENT_MODE) {
                final DeviceInfo deviceInfo = null;
                DeviceCenterBiz.getInstance().runOnUIThread(new Runnable() { // from class: com.aliyun.alink.business.devicecenter.api.add.ConcurrentAddDeviceBiz.InnerConfigCallback.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (ConcurrentAddDeviceBiz.this.f10021b != null) {
                            ConcurrentAddDeviceBiz.this.f10021b.onProvisionStatus(deviceInfo, provisionStatus);
                        }
                    }
                });
                return;
            }
            PerformanceLog.trace("ConcurrentAddDeviceBiz", "startProvision");
            Object extraParams = provisionStatus.getExtraParams(AlinkConstants.KEY_CACHE_START_PROVISION_DEVICE_INFO);
            if (extraParams instanceof DeviceInfo) {
                DeviceInfo deviceInfo2 = (DeviceInfo) extraParams;
                ConcurrentAddDeviceBiz.this.a(AddDeviceState.AddStatePrechecking, -1, true, deviceInfo2, null);
                ConcurrentAddDeviceBiz.this.a(AddDeviceState.AddStateProvisioning, -1, true, deviceInfo2, null);
                String meshDeviceUniqueIDByDeviceInfo = DeviceInfoUtils.getMeshDeviceUniqueIDByDeviceInfo(deviceInfo2);
                ConcurrentAddDeviceBiz.this.a(false, meshDeviceUniqueIDByDeviceInfo, AlinkConstants.KEY_START_TIME_PROVISION, String.valueOf(System.currentTimeMillis()));
                ConcurrentAddDeviceBiz concurrentAddDeviceBiz = ConcurrentAddDeviceBiz.this;
                concurrentAddDeviceBiz.a(false, meshDeviceUniqueIDByDeviceInfo, AlinkConstants.KEY_WIFI_TYPE, concurrentAddDeviceBiz.a(concurrentAddDeviceBiz.f10023d));
                ConcurrentAddDeviceBiz concurrentAddDeviceBiz2 = ConcurrentAddDeviceBiz.this;
                concurrentAddDeviceBiz2.a(false, meshDeviceUniqueIDByDeviceInfo, AlinkConstants.KEY_HAS_SIM, String.valueOf(NetworkTypeUtils.hasSimCard(concurrentAddDeviceBiz2.f10023d)));
                ConcurrentAddDeviceBiz.this.a(false, meshDeviceUniqueIDByDeviceInfo, "sdkVersion", BuildConfig.SDK_VERSION);
                ConcurrentAddDeviceBiz.this.a(false, meshDeviceUniqueIDByDeviceInfo, AlinkConstants.KEY_LINKTYPE, deviceInfo2.linkType);
            }
        }

        @Override // com.aliyun.alink.business.devicecenter.config.IConfigCallback
        public void onSuccess(DeviceInfo deviceInfo) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append("success,info=");
                sb.append(deviceInfo);
                ALog.i("ConcurrentAddDeviceBiz", sb.toString());
                if (deviceInfo == null) {
                    return;
                }
                ConcurrentAddDeviceBiz.this.a(AddDeviceState.AddStateProvisionOver, -1, true, deviceInfo, null);
            } catch (Exception e2) {
                ALog.w("ConcurrentAddDeviceBiz", "onSuccess recv Callback，but parse error,e = " + e2.toString());
                e2.printStackTrace();
            }
        }
    }

    public static ConcurrentAddDeviceBiz getInstance() {
        if (f10020a == null) {
            synchronized (ConcurrentAddDeviceBiz.class) {
                try {
                    if (f10020a == null) {
                        f10020a = new ConcurrentAddDeviceBiz();
                    }
                } finally {
                }
            }
        }
        return f10020a;
    }

    public ConcurrentHashMap<String, ConfigurationData> getConfigurationInfoCache() {
        return this.f10031l;
    }

    public ConfigurationData getConfigurationInfoCacheValue(String str) {
        if (!TextUtils.isEmpty(str)) {
            return this.f10031l.get(str);
        }
        ALog.w("ConcurrentAddDeviceBiz", "getConfigurationInfoCache key  is null");
        return null;
    }

    public void setConfigurationInfoCache(String str, ConfigurationData configurationData) {
        if (TextUtils.isEmpty(str) || configurationData == null) {
            ALog.w("ConcurrentAddDeviceBiz", "setConfigurationInfoCache key or configurationData is null");
        } else {
            this.f10031l.put(str, configurationData);
        }
    }

    public void stopConfig() {
        this.f10026g.clear();
        this.f10025f.clear();
        this.f10031l.clear();
        this.f10027h = 0;
        IConfigStrategy iConfigStrategy = this.f10028i;
        if (iConfigStrategy != null) {
            iConfigStrategy.stopConfig();
            this.f10028i = null;
        }
    }

    public void a(IConcurrentAddDeviceStatusListener iConcurrentAddDeviceStatusListener) {
        this.f10030k = iConcurrentAddDeviceStatusListener;
    }

    public void a(Context context, List<DeviceInfo> list, IConcurrentAddDeviceListener iConcurrentAddDeviceListener) {
        ALog.i("ConcurrentAddDeviceBiz", "startConcurrentAddDevice() call.");
        if (context != null) {
            this.f10023d = context;
            DeviceCenterBiz.getInstance().setAppContext(context);
            this.f10021b = iConcurrentAddDeviceListener;
            if (list != null && list.size() != 0) {
                this.f10026g.addAll(list);
                DCAlibabaConcurrentConfigParams dCAlibabaConcurrentConfigParams = null;
                for (DeviceInfo deviceInfo : list) {
                    this.f10029j.put(DeviceInfoUtils.getMeshDeviceUniqueIDByDeviceInfo(deviceInfo), new WeakReference<>(deviceInfo));
                    this.f10025f.remove(DeviceInfoUtils.getMeshDeviceUniqueIDByDeviceInfo(deviceInfo));
                    if (LinkType.ALI_APP_MESH.getName().equalsIgnoreCase(deviceInfo.linkType)) {
                        if (dCAlibabaConcurrentConfigParams == null) {
                            dCAlibabaConcurrentConfigParams = new DCAlibabaConcurrentConfigParams();
                            dCAlibabaConcurrentConfigParams.linkType = LinkType.ALI_BATCH_APP_MESH;
                            dCAlibabaConcurrentConfigParams.configParamsList = new LinkedList();
                        }
                        if (!TextUtils.isEmpty(deviceInfo.familyId)) {
                            dCAlibabaConcurrentConfigParams.familyId = deviceInfo.familyId;
                        }
                        dCAlibabaConcurrentConfigParams.configParamsList.add(deviceInfo.getDCConfigParams());
                    }
                    LinkType.ALI_GATEWAY_MESH.getName().equalsIgnoreCase(deviceInfo.linkType);
                }
                if (dCAlibabaConcurrentConfigParams != null) {
                    if (this.f10028i == null) {
                        this.f10028i = DeviceCenterBiz.getInstance().getConfigStrategy(LinkType.ALI_BATCH_APP_MESH);
                    }
                    try {
                        this.f10028i.startConfig(this.f10024e, dCAlibabaConcurrentConfigParams);
                        return;
                    } catch (Exception e2) {
                        ALog.e("ConcurrentAddDeviceBiz", e2.toString());
                        for (DeviceInfo deviceInfo2 : list) {
                            a(AddDeviceState.AddStateProvisionOver, -1, false, null, new DCErrorCode("SDKError", DCErrorCode.PF_SDK_ERROR).setSubcode(DCErrorCode.SUBCODE_SKE_START_CONFIG_EXCEPTION).setMsg("startConfig" + e2).setExtra(deviceInfo2));
                        }
                        return;
                    }
                }
                ALog.e("ConcurrentAddDeviceBiz", "Could not find provision params for concurrent-provision");
                return;
            }
            ALog.e("ConcurrentAddDeviceBiz", "startAddDevice, params error");
            a(AddDeviceState.AddStateProvisionOver, -1, false, null, new DCErrorCode(DCErrorCode.PARAM_ERROR_MSG, DCErrorCode.PF_PARAMS_ERROR).setSubcode(DCErrorCode.SUBCODE_PE_PRODUCTKEY_EMPTY).setMsg("pkError"));
            return;
        }
        ALog.e("ConcurrentAddDeviceBiz", "startConcurrentAddDevice context=null.");
        throw new RuntimeException("startAddDeviceParamContextNull");
    }

    public final void a(final AddDeviceState addDeviceState, final int i2, final boolean z2, DeviceInfo deviceInfo, final DCErrorCode dCErrorCode) {
        if (deviceInfo == null) {
            Object obj = dCErrorCode.extra;
            if (obj instanceof DeviceInfo) {
                deviceInfo = (DeviceInfo) obj;
            } else if (obj instanceof Map) {
                Map map = (Map) obj;
                if (map.containsKey(AlinkConstants.KEY_CACHE_MESH_DEVICE_UNIQUE_ID)) {
                    WeakReference<DeviceInfo> weakReference = this.f10029j.get((String) map.get(AlinkConstants.KEY_CACHE_MESH_DEVICE_UNIQUE_ID));
                    if (weakReference != null) {
                        deviceInfo = weakReference.get();
                    }
                }
            }
        }
        final DeviceInfo deviceInfo2 = deviceInfo;
        if (addDeviceState == AddDeviceState.AddStateProvisionOver && !z2 && dCErrorCode != null) {
            ALog.e("ConcurrentAddDeviceBiz", "state=" + addDeviceState + ",isSuccess=" + z2 + ",info=" + deviceInfo2 + ",error=" + dCErrorCode);
        } else {
            ALog.i("ConcurrentAddDeviceBiz", "state=" + addDeviceState + ",isSuccess=" + z2 + ",info=" + deviceInfo2 + ",error=" + dCErrorCode);
        }
        DeviceCenterBiz.getInstance().runOnUIThread(new Runnable() { // from class: com.aliyun.alink.business.devicecenter.api.add.ConcurrentAddDeviceBiz.1
            @Override // java.lang.Runnable
            public void run() {
                int i3 = AnonymousClass2.f10038a[addDeviceState.ordinal()];
                if (i3 == 1) {
                    if (ConcurrentAddDeviceBiz.this.f10021b != null) {
                        ConcurrentAddDeviceBiz.this.f10021b.onPreCheck(deviceInfo2, z2, dCErrorCode);
                        return;
                    }
                    return;
                }
                if (i3 == 2) {
                    if (ConcurrentAddDeviceBiz.this.f10021b != null) {
                        ConcurrentAddDeviceBiz.this.f10021b.onProvisionPrepare(deviceInfo2, i2);
                        return;
                    }
                    return;
                }
                if (i3 == 3) {
                    if (ConcurrentAddDeviceBiz.this.f10021b != null) {
                        ConcurrentAddDeviceBiz.this.f10021b.onProvisioning(deviceInfo2);
                        return;
                    }
                    return;
                }
                if (i3 != 4) {
                    return;
                }
                if (z2) {
                    ConcurrentAddDeviceBiz.this.a(deviceInfo2);
                } else {
                    ConcurrentAddDeviceBiz.this.a(dCErrorCode);
                }
                PerformanceLog.trace("ConcurrentAddDeviceBiz", "provisionResult", PerformanceLog.getJsonObject("result", z2 ? "success" : ITagManager.FAIL));
                ALog.d("ConcurrentAddDeviceBiz", "onProvisionedResult addDeviceListener=" + ConcurrentAddDeviceBiz.this.f10021b);
                if (!TextUtils.isEmpty(deviceInfo2.mac) && ConcurrentAddDeviceBiz.this.f10025f.contains(DeviceInfoUtils.getMeshDeviceUniqueIDByDeviceInfo(deviceInfo2))) {
                    ALog.w("ConcurrentAddDeviceBiz", "Duplicate provision over received, mac: " + deviceInfo2.mac);
                    return;
                }
                ConcurrentAddDeviceBiz.this.f10025f.add(DeviceInfoUtils.getMeshDeviceUniqueIDByDeviceInfo(deviceInfo2));
                if (ConcurrentAddDeviceBiz.this.f10021b != null) {
                    ConcurrentAddDeviceBiz.this.f10021b.onProvisionedResult(z2, deviceInfo2, dCErrorCode);
                }
                ConcurrentAddDeviceBiz.this.f10027h++;
                if (ConcurrentAddDeviceBiz.this.f10027h == ConcurrentAddDeviceBiz.this.f10026g.size()) {
                    ALog.e("ConcurrentAddDeviceBiz", "All provision task completed, call stopConfig");
                    ConcurrentAddDeviceBiz.this.stopConfig();
                    if (ConcurrentAddDeviceBiz.this.f10030k != null) {
                        ConcurrentAddDeviceBiz.this.f10030k.onIdle();
                    }
                }
            }
        });
    }

    public final void a(boolean z2, String str, String... strArr) {
        if (TextUtils.isDigitsOnly(str)) {
            return;
        }
        List<String> linkedList = this.f10022c.get(str);
        if (linkedList == null) {
            linkedList = new LinkedList<>();
        }
        this.f10022c.put(str, linkedList);
        linkedList.addAll(Arrays.asList(strArr));
        if (z2) {
            DCUserTrackV2 dCUserTrackV2 = new DCUserTrackV2();
            dCUserTrackV2.resetTrackData();
            dCUserTrackV2.addTrackData((String[]) linkedList.toArray(new String[0]));
            dCUserTrackV2.sendEvent();
        }
    }

    public final void a(Object obj) {
        ALog.d("ConcurrentAddDeviceBiz", "provisionTrack obj=" + obj);
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
