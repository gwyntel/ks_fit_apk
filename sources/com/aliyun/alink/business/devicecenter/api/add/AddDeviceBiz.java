package com.aliyun.alink.business.devicecenter.api.add;

import android.content.Context;
import android.text.TextUtils;
import com.aliyun.alink.business.devicecenter.activate.DeviceActivationRtosBind;
import com.aliyun.alink.business.devicecenter.activate.IActivateRtosDeviceCallback;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.aliyun.alink.business.devicecenter.base.AlinkHelper;
import com.aliyun.alink.business.devicecenter.base.DCErrorCode;
import com.aliyun.alink.business.devicecenter.biz.ProvisionRepositoryV2;
import com.aliyun.alink.business.devicecenter.cache.CacheCenter;
import com.aliyun.alink.business.devicecenter.cache.CacheType;
import com.aliyun.alink.business.devicecenter.cache.ProvisionDeviceInfoCache;
import com.aliyun.alink.business.devicecenter.channel.http.mtop.data.BindIotDeviceResult;
import com.aliyun.alink.business.devicecenter.channel.http.top.DefaultTopRtosBindRequestService;
import com.aliyun.alink.business.devicecenter.config.DeviceCenterBiz;
import com.aliyun.alink.business.devicecenter.config.IConfigCallback;
import com.aliyun.alink.business.devicecenter.config.model.DCAlibabaConfigParams;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.log.PerformanceLog;
import com.aliyun.alink.business.devicecenter.model.ProvisionSLBItem;
import com.aliyun.alink.business.devicecenter.track.DCUserTrack;
import com.aliyun.alink.business.devicecenter.utils.CompatUtil;
import com.aliyun.alink.business.devicecenter.utils.DeviceInfoUtils;
import com.aliyun.alink.business.devicecenter.utils.NetworkTypeUtils;
import com.aliyun.alink.business.devicecenter.utils.StringUtils;
import com.aliyun.alink.business.devicecenter.utils.WifiManagerUtil;
import com.aliyun.alink.linksdk.connectsdk.ApiCallBack;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.umeng.message.common.inter.ITagManager;
import datasource.bean.ConfigurationData;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class AddDeviceBiz implements IAddDeviceBiz {

    /* renamed from: a, reason: collision with root package name */
    public static IAddDeviceBiz f9985a;

    /* renamed from: b, reason: collision with root package name */
    public DeviceInfo f9986b = null;

    /* renamed from: c, reason: collision with root package name */
    public AddDeviceState f9987c = null;

    /* renamed from: d, reason: collision with root package name */
    public int f9988d = 60;

    /* renamed from: e, reason: collision with root package name */
    public IAddDeviceListener f9989e = null;

    /* renamed from: f, reason: collision with root package name */
    public DCAlibabaConfigParams f9990f = null;

    /* renamed from: g, reason: collision with root package name */
    public int f9991g = 0;

    /* renamed from: h, reason: collision with root package name */
    public boolean f9992h = false;

    /* renamed from: i, reason: collision with root package name */
    public final Object f9993i = new Object();

    /* renamed from: com.aliyun.alink.business.devicecenter.api.add.AddDeviceBiz$4, reason: invalid class name */
    public class AnonymousClass4 implements IAddDeviceListener {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ IConcurrentAddDeviceListener f10003a;

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ DeviceInfo f10004b;

        @Override // com.aliyun.alink.business.devicecenter.api.add.IAddDeviceListener
        public void onPreCheck(boolean z2, DCErrorCode dCErrorCode) {
            IConcurrentAddDeviceListener iConcurrentAddDeviceListener = this.f10003a;
            if (iConcurrentAddDeviceListener != null) {
                iConcurrentAddDeviceListener.onPreCheck(this.f10004b, z2, dCErrorCode);
            }
        }

        @Override // com.aliyun.alink.business.devicecenter.api.add.IAddDeviceListener
        public void onProvisionPrepare(int i2) {
            IConcurrentAddDeviceListener iConcurrentAddDeviceListener = this.f10003a;
            if (iConcurrentAddDeviceListener != null) {
                iConcurrentAddDeviceListener.onProvisionPrepare(this.f10004b, i2);
            }
        }

        @Override // com.aliyun.alink.business.devicecenter.api.add.IAddDeviceListener
        public void onProvisionStatus(ProvisionStatus provisionStatus) {
            IConcurrentAddDeviceListener iConcurrentAddDeviceListener = this.f10003a;
            if (iConcurrentAddDeviceListener != null) {
                iConcurrentAddDeviceListener.onProvisionStatus(this.f10004b, provisionStatus);
            }
        }

        @Override // com.aliyun.alink.business.devicecenter.api.add.IAddDeviceListener
        public void onProvisionedResult(boolean z2, DeviceInfo deviceInfo, DCErrorCode dCErrorCode) {
            if (deviceInfo == null) {
                deviceInfo = this.f10004b;
            } else {
                deviceInfo.mac = this.f10004b.mac;
            }
            ALog.w("AddDeviceBiz", deviceInfo.mac + ", on Provision result: " + z2);
            IConcurrentAddDeviceListener iConcurrentAddDeviceListener = this.f10003a;
            if (iConcurrentAddDeviceListener != null) {
                iConcurrentAddDeviceListener.onProvisionedResult(z2, deviceInfo, dCErrorCode);
            }
        }

        @Override // com.aliyun.alink.business.devicecenter.api.add.IAddDeviceListener
        public void onProvisioning() {
            IConcurrentAddDeviceListener iConcurrentAddDeviceListener = this.f10003a;
            if (iConcurrentAddDeviceListener != null) {
                iConcurrentAddDeviceListener.onProvisioning(this.f10004b);
            }
        }
    }

    /* renamed from: com.aliyun.alink.business.devicecenter.api.add.AddDeviceBiz$9, reason: invalid class name */
    static /* synthetic */ class AnonymousClass9 {

        /* renamed from: a, reason: collision with root package name */
        public static final /* synthetic */ int[] f10016a;

        static {
            int[] iArr = new int[AddDeviceState.values().length];
            f10016a = iArr;
            try {
                iArr[AddDeviceState.AddStatePrechecking.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f10016a[AddDeviceState.AddStateProvisionPreparing.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f10016a[AddDeviceState.AddStateProvisioning.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f10016a[AddDeviceState.AddStateProvisionOver.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    private class MyConfigCallback implements IConfigCallback {
        public MyConfigCallback() {
        }

        @Override // com.aliyun.alink.business.devicecenter.config.IDCFailCallback
        public void onFailure(DCErrorCode dCErrorCode) {
            ALog.e("AddDeviceBiz", "onFailure provision fail Callback, " + dCErrorCode);
            AddDeviceBiz.this.f9987c = AddDeviceState.AddStateProvisionOver;
            AddDeviceBiz addDeviceBiz = AddDeviceBiz.this;
            addDeviceBiz.a(addDeviceBiz.f9987c, -1, false, null, dCErrorCode);
        }

        @Override // com.aliyun.alink.business.devicecenter.config.IConfigCallback
        public void onStatus(final ProvisionStatus provisionStatus) {
            ALog.i("AddDeviceBiz", "onStatus status=" + provisionStatus + ",addDeviceListener=" + AddDeviceBiz.this.f9989e);
            DeviceCenterBiz.getInstance().runOnUIThread(new Runnable() { // from class: com.aliyun.alink.business.devicecenter.api.add.AddDeviceBiz.MyConfigCallback.1
                @Override // java.lang.Runnable
                public void run() {
                    if (AddDeviceBiz.this.f9989e != null) {
                        AddDeviceBiz.this.f9989e.onProvisionStatus(provisionStatus);
                    }
                    if (provisionStatus == ProvisionStatus.MESH_COMBO_WIFI_CONNECT_CLOUD_STATUS) {
                        AddDeviceBiz.this.f9987c = AddDeviceState.AddStateProvisionOver;
                        AddDeviceBiz.this.stopAddDevice();
                    }
                }
            });
        }

        @Override // com.aliyun.alink.business.devicecenter.config.IConfigCallback
        public void onSuccess(DeviceInfo deviceInfo) {
            try {
                if (AddDeviceBiz.this.f9987c != AddDeviceState.AddStateProvisioning && !AlinkHelper.isBatch(AddDeviceBiz.this.f9990f)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("not in provisioning state, not batch provision mode, ignore. curState=");
                    sb.append(AddDeviceBiz.this.f9987c);
                    ALog.d("AddDeviceBiz", sb.toString());
                    return;
                }
                StringBuilder sb2 = new StringBuilder();
                sb2.append("success,info=");
                sb2.append(deviceInfo);
                ALog.i("AddDeviceBiz", sb2.toString());
                if (deviceInfo == null) {
                    return;
                }
                AddDeviceBiz.this.f9987c = AddDeviceState.AddStateProvisionOver;
                AddDeviceBiz addDeviceBiz = AddDeviceBiz.this;
                addDeviceBiz.a(addDeviceBiz.f9987c, -1, true, deviceInfo, null);
            } catch (Exception e2) {
                ALog.w("AddDeviceBiz", "onSuccess recv Callback，but parse error,e = " + e2.toString());
                e2.printStackTrace();
            }
        }
    }

    public static IAddDeviceBiz getInstance() {
        if (f9985a == null) {
            synchronized (AddDeviceBiz.class) {
                try {
                    if (f9985a == null) {
                        f9985a = new AddDeviceBiz();
                    }
                } finally {
                }
            }
        }
        return f9985a;
    }

    @Override // com.aliyun.alink.business.devicecenter.api.add.IAddDeviceBiz
    public void continueProvision(Map map) {
        ALog.d("AddDeviceBiz", "continueProvision() called with: provisionParams = [" + map + "], curState=" + this.f9987c);
        AddDeviceState addDeviceState = this.f9987c;
        if (addDeviceState == null || addDeviceState == AddDeviceState.AddStateProvisionOver) {
            return;
        }
        DeviceCenterBiz.getInstance().continueConfig(map);
    }

    @Override // com.aliyun.alink.business.devicecenter.api.add.IAddDeviceBiz
    public String getCurrentSsid(Context context) {
        return AlinkHelper.getWifiSsid(context);
    }

    @Override // com.aliyun.alink.business.devicecenter.api.add.IAddDeviceBiz
    public AddDeviceState getProcedureState() {
        return this.f9987c;
    }

    @Override // com.aliyun.alink.business.devicecenter.api.add.IAddDeviceBiz
    public int getWifiRssid(Context context) {
        if (context != null) {
            return new WifiManagerUtil(context).getWifiRssid();
        }
        throw new IllegalArgumentException("context=null");
    }

    @Override // com.aliyun.alink.business.devicecenter.api.add.IAddDeviceBiz
    public String getWifiType(Context context) {
        if (context != null) {
            return new WifiManagerUtil(context).getWifiType();
        }
        throw new IllegalArgumentException("context=null");
    }

    @Override // com.aliyun.alink.business.devicecenter.api.add.IAddDeviceBiz
    public void setAliProvisionMode(String str) {
        ALog.i("AddDeviceBiz", "setAliProvisionMode() call. linkType=" + str);
        if (this.f9986b == null) {
            ALog.w("AddDeviceBiz", "setAliProvisionMode error, deviceInfo=null.");
            throw new IllegalStateException("call setDevice first");
        }
        if (!a(str)) {
            throw new IllegalStateException("linkType invalid.");
        }
        this.f9986b.linkType = str;
    }

    @Override // com.aliyun.alink.business.devicecenter.api.add.IAddDeviceBiz
    public void setDevice(DeviceInfo deviceInfo) {
        ALog.i("AddDeviceBiz", "setDevice() call. devInfo=" + deviceInfo);
        if (deviceInfo == null) {
            ALog.e("AddDeviceBiz", "setDevice(),emtpy");
        } else {
            this.f9986b = deviceInfo;
        }
    }

    @Override // com.aliyun.alink.business.devicecenter.api.add.IAddDeviceBiz
    public void setExtraInfo(Map map) {
        ALog.i("AddDeviceBiz", "setExtraInfo called() extraInfo=" + map);
        DeviceCenterBiz.getInstance().setExtraInfo(map);
    }

    @Override // com.aliyun.alink.business.devicecenter.api.add.IAddDeviceBiz
    public void setProvisionTimeOut(int i2) {
        ALog.d("AddDeviceBiz", "setProvisionTimeOut()  call. timeout=" + i2);
        if (i2 < 0) {
            this.f9988d = -1;
        } else if (i2 < 60) {
            this.f9988d = 58;
        } else {
            this.f9988d = i2 - 2;
        }
    }

    @Override // com.aliyun.alink.business.devicecenter.api.add.IAddDeviceBiz
    public void startAddDevice(Context context, final IAddDeviceListener iAddDeviceListener) {
        String str;
        LinkType linkType = LinkType.ALI_APP_MESH;
        boolean z2 = linkType.getName().equals(this.f9986b.linkType) || LinkType.ALI_GATEWAY_MESH.getName().equals(this.f9986b.linkType);
        boolean zIsEmpty = TextUtils.isEmpty(this.f9986b.mac);
        if (!zIsEmpty && DeviceInfoUtils.isSupportFastProvisioningV2(this.f9986b.deviceId)) {
            DeviceInfo deviceInfo = this.f9986b;
            deviceInfo.subDeviceId = deviceInfo.deviceId;
            deviceInfo.configurationInfo = ConcurrentAddDeviceBiz.getInstance().getConfigurationInfoCacheValue(this.f9986b.deviceId);
        }
        if (z2 && !zIsEmpty) {
            ALog.i("AddDeviceBiz", "startAddDevice to startConcurrentAddDevice call.");
            ArrayList arrayList = new ArrayList(1);
            arrayList.add(this.f9986b);
            startConcurrentAddDevice(context, arrayList, new IConcurrentAddDeviceListener() { // from class: com.aliyun.alink.business.devicecenter.api.add.AddDeviceBiz.6
                @Override // com.aliyun.alink.business.devicecenter.api.add.IConcurrentAddDeviceListener
                public void onPreCheck(DeviceInfo deviceInfo2, boolean z3, DCErrorCode dCErrorCode) {
                    IAddDeviceListener iAddDeviceListener2 = iAddDeviceListener;
                    if (iAddDeviceListener2 != null) {
                        iAddDeviceListener2.onPreCheck(z3, dCErrorCode);
                    }
                }

                @Override // com.aliyun.alink.business.devicecenter.api.add.IConcurrentAddDeviceListener
                public void onProvisionPrepare(DeviceInfo deviceInfo2, int i2) {
                    IAddDeviceListener iAddDeviceListener2 = iAddDeviceListener;
                    if (iAddDeviceListener2 != null) {
                        iAddDeviceListener2.onProvisionPrepare(i2);
                    }
                }

                @Override // com.aliyun.alink.business.devicecenter.api.add.IConcurrentAddDeviceListener
                public void onProvisionStatus(DeviceInfo deviceInfo2, ProvisionStatus provisionStatus) {
                    IAddDeviceListener iAddDeviceListener2 = iAddDeviceListener;
                    if (iAddDeviceListener2 != null) {
                        iAddDeviceListener2.onProvisionStatus(provisionStatus);
                    }
                }

                @Override // com.aliyun.alink.business.devicecenter.api.add.IConcurrentAddDeviceListener
                public void onProvisionedResult(boolean z3, DeviceInfo deviceInfo2, DCErrorCode dCErrorCode) {
                    IAddDeviceListener iAddDeviceListener2 = iAddDeviceListener;
                    if (iAddDeviceListener2 != null) {
                        iAddDeviceListener2.onProvisionedResult(z3, deviceInfo2, dCErrorCode);
                    }
                }

                @Override // com.aliyun.alink.business.devicecenter.api.add.IConcurrentAddDeviceListener
                public void onProvisioning(DeviceInfo deviceInfo2) {
                    IAddDeviceListener iAddDeviceListener2 = iAddDeviceListener;
                    if (iAddDeviceListener2 != null) {
                        iAddDeviceListener2.onProvisioning();
                    }
                }
            });
            return;
        }
        ALog.i("AddDeviceBiz", "startAddDevice() call.");
        if (context == null) {
            ALog.e("AddDeviceBiz", "startAddDevice context=null.");
            throw new RuntimeException("startAddDeviceParamContextNull");
        }
        AddDeviceState addDeviceState = this.f9987c;
        if (addDeviceState != null && addDeviceState != AddDeviceState.AddStateProvisionOver) {
            ALog.e("AddDeviceBiz", "startAddDevice running, return.");
            if (iAddDeviceListener != null) {
                DeviceCenterBiz.getInstance().runOnUIThread(new Runnable() { // from class: com.aliyun.alink.business.devicecenter.api.add.AddDeviceBiz.7
                    @Override // java.lang.Runnable
                    public void run() {
                        if (iAddDeviceListener != null) {
                            iAddDeviceListener.onProvisionedResult(false, null, new DCErrorCode("USER_INVOKE_ERROR", DCErrorCode.PF_USER_INVOKE_ERROR).setSubcode(DCErrorCode.SUBCODE_UIE_PROVISION_RUNNING).setMsg("startAddDevice running, return."));
                        }
                    }
                });
                return;
            }
            return;
        }
        DeviceCenterBiz.getInstance().setAppContext(context);
        PerformanceLog.trace("AddDeviceBiz", "startProvision");
        this.f9989e = iAddDeviceListener;
        this.f9987c = AddDeviceState.AddStatePrechecking;
        if (this.f9986b == null || !(linkType.getName().equalsIgnoreCase(this.f9986b.linkType) || LinkType.ALI_APP_COMBO_MESH.getName().equalsIgnoreCase(this.f9986b.linkType) || this.f9986b.isValid())) {
            ALog.e("AddDeviceBiz", "startAddDevice, params error");
            AddDeviceState addDeviceState2 = AddDeviceState.AddStateProvisionOver;
            this.f9987c = addDeviceState2;
            a(addDeviceState2, -1, false, null, new DCErrorCode(DCErrorCode.PARAM_ERROR_MSG, DCErrorCode.PF_PARAMS_ERROR).setSubcode(DCErrorCode.SUBCODE_PE_PRODUCTKEY_EMPTY).setMsg("pkError"));
            return;
        }
        if (!NetworkTypeUtils.isWiFi(DeviceCenterBiz.getInstance().getAppContext()) && ((!LinkType.ALI_PHONE_AP.getName().equalsIgnoreCase(this.f9986b.linkType) || !CompatUtil.isAlinkPhoneApConfigStrategyFromOldHotspotFlow()) && !LinkType.ALI_GENIE_SOUND_BOX.getName().equalsIgnoreCase(this.f9986b.linkType) && !linkType.getName().equalsIgnoreCase(this.f9986b.linkType) && !LinkType.ALI_APP_COMBO_MESH.getName().equalsIgnoreCase(this.f9986b.linkType))) {
            ALog.w("AddDeviceBiz", "startAddDevice, Wifi not enabled.");
            this.f9987c = AddDeviceState.AddStateProvisionOver;
            a(this.f9987c, -1, false, null, new DCErrorCode("NETWORK_ERROR", DCErrorCode.PF_NETWORK_ERROR).setSubcode(DCErrorCode.SUBCODE_NE_WIFI_NOT_CONNECTED).setMsg("wifiNotConnected"));
            return;
        }
        ProvisionDeviceInfoCache.getInstance().clearCache();
        CacheCenter.getInstance().clearCache(CacheType.APP_SEND_TOKEN);
        if (AlinkConstants.DEVICE_TYPE_COMBO_SUBTYPE_4.equals(this.f9986b.devType)) {
            ALog.e("AddDeviceBiz", "startAddDevice, devType error, " + this.f9986b.devType + " don't support.");
            AddDeviceState addDeviceState3 = AddDeviceState.AddStateProvisionOver;
            this.f9987c = addDeviceState3;
            a(addDeviceState3, -1, false, null, new DCErrorCode(DCErrorCode.PARAM_ERROR_MSG, DCErrorCode.PF_PARAMS_ERROR).setSubcode(DCErrorCode.SUBCODE_PE_DEVICETYPE_ERROR).setMsg("devTypeError"));
            return;
        }
        RegionInfo regionInfo = this.f9986b.regionInfo;
        if (regionInfo != null && (str = regionInfo.mqttUrl) != null && str.length() > 256) {
            ALog.w("AddDeviceBiz", "startAddDevice, mqttUrl is too long.");
        }
        DCAlibabaConfigParams dCConfigParams = this.f9986b.getDCConfigParams();
        this.f9990f = dCConfigParams;
        if (dCConfigParams == null) {
            ALog.e("AddDeviceBiz", "startAddDevice, linkType not support or not match addDeviceFrom.");
            AddDeviceState addDeviceState4 = AddDeviceState.AddStateProvisionOver;
            this.f9987c = addDeviceState4;
            a(addDeviceState4, -1, false, null, new DCErrorCode(DCErrorCode.PARAM_ERROR_MSG, DCErrorCode.PF_PARAMS_ERROR).setSubcode(DCErrorCode.SUBCODE_PE_PROVISION_PARAMS_ERROR).setMsg("dcParamsError"));
            return;
        }
        if (!ProtocolVersion.isValidVersion(dCConfigParams.protocolVersion)) {
            ALog.e("AddDeviceBiz", "startAddDevice, protocol version invalid.");
            AddDeviceState addDeviceState5 = AddDeviceState.AddStateProvisionOver;
            this.f9987c = addDeviceState5;
            a(addDeviceState5, -1, false, null, new DCErrorCode(DCErrorCode.PARAM_ERROR_MSG, DCErrorCode.PF_PARAMS_ERROR).setSubcode(DCErrorCode.SUBCODE_PE_VERSION_INVALID).setMsg("protocolVersionError"));
            return;
        }
        DCUserTrack.resetTrackData();
        DCUserTrack.addTrackData(AlinkConstants.KEY_START_TIME_PROVISION, String.valueOf(System.currentTimeMillis()));
        DCUserTrack.addTrackData(AlinkConstants.KEY_WIFI_TYPE, getWifiType(context));
        DCUserTrack.addTrackData(AlinkConstants.KEY_HAS_SIM, String.valueOf(NetworkTypeUtils.hasSimCard(context)));
        DeviceCenterBiz.getInstance().selectStrategy(this.f9990f.linkType);
        a(this.f9987c, -1, true, null, null);
        if (DeviceCenterBiz.getInstance().needWiFiSsidPwd()) {
            AddDeviceState addDeviceState6 = AddDeviceState.AddStateProvisionPreparing;
            this.f9987c = addDeviceState6;
            a(addDeviceState6, 1, true, null, null);
            return;
        }
        try {
            AddDeviceState addDeviceState7 = AddDeviceState.AddStateProvisioning;
            this.f9987c = addDeviceState7;
            this.f9990f.timeout = this.f9988d;
            a(addDeviceState7, -1, true, null, null);
            DeviceCenterBiz.getInstance().startConfig(new MyConfigCallback(), this.f9990f);
        } catch (Exception e2) {
            e2.printStackTrace();
            ALog.e("AddDeviceBiz", "startAddDevice,provisioning error , e" + e2.toString());
            AddDeviceState addDeviceState8 = AddDeviceState.AddStateProvisionOver;
            this.f9987c = addDeviceState8;
            a(addDeviceState8, -1, false, null, new DCErrorCode("SDKError", DCErrorCode.PF_SDK_ERROR).setSubcode(DCErrorCode.SUBCODE_SKE_START_CONFIG_EXCEPTION).setMsg("startConfig" + e2));
        }
    }

    @Override // com.aliyun.alink.business.devicecenter.api.add.IAddDeviceBiz
    public void startBindDevice(Context context, IAddDeviceListener iAddDeviceListener) {
        if (this.f9986b == null) {
            AddDeviceState addDeviceState = AddDeviceState.AddStateProvisionOver;
            this.f9987c = addDeviceState;
            a(addDeviceState, -1, false, null, new DCErrorCode("deviceInfo is null", 0));
            return;
        }
        DeviceCenterBiz.getInstance().setAppContext(context);
        this.f9989e = iAddDeviceListener;
        AddDeviceState addDeviceState2 = AddDeviceState.AddStatePrechecking;
        this.f9987c = addDeviceState2;
        a(addDeviceState2, -1, true, null, null);
        AddDeviceState addDeviceState3 = AddDeviceState.AddStateProvisioning;
        this.f9987c = addDeviceState3;
        a(addDeviceState3, -1, true, null, null);
        Map<String, Object> extraDeviceInfo = this.f9986b.getExtraDeviceInfo();
        DeviceActivationRtosBind.getInstance().init(new DefaultTopRtosBindRequestService());
        DeviceActivationRtosBind.getInstance().activateDevice(extraDeviceInfo, new IActivateRtosDeviceCallback() { // from class: com.aliyun.alink.business.devicecenter.api.add.AddDeviceBiz.5
            @Override // com.aliyun.alink.business.devicecenter.activate.IActivateRtosDeviceCallback
            public void onFailed(DCErrorCode dCErrorCode) {
                AddDeviceBiz.this.f9987c = AddDeviceState.AddStateProvisionOver;
                AddDeviceBiz addDeviceBiz = AddDeviceBiz.this;
                addDeviceBiz.a(addDeviceBiz.f9987c, -1, false, null, dCErrorCode);
            }

            @Override // com.aliyun.alink.business.devicecenter.activate.IActivateRtosDeviceCallback
            public void onSuccess(BindIotDeviceResult bindIotDeviceResult) {
                AddDeviceBiz.this.f9987c = AddDeviceState.AddStateProvisionOver;
                if (bindIotDeviceResult != null) {
                    AddDeviceBiz.this.f9986b.deviceId = bindIotDeviceResult.getDevId();
                    AddDeviceBiz addDeviceBiz = AddDeviceBiz.this;
                    addDeviceBiz.a(addDeviceBiz.f9987c, -1, true, AddDeviceBiz.this.f9986b, null);
                }
            }
        });
    }

    @Override // com.aliyun.alink.business.devicecenter.api.add.IAddDeviceBiz
    public void startConcurrentAddDevice(final Context context, final List<DeviceInfo> list, final IConcurrentAddDeviceListener iConcurrentAddDeviceListener) {
        ALog.d("AddDeviceBiz", "startConcurrentAddDevice() call.");
        if (list == null || list.size() == 0) {
            ALog.e("AddDeviceBiz", "Illegal parameter, deviceInfos cannot be null");
            return;
        }
        if (list.size() == 1 && list.get(0) != null && !LinkType.ALI_GATEWAY_MESH.getName().equals(list.get(0).linkType)) {
            if (list.get(0).configurationInfo == null) {
                ALog.d("AddDeviceBiz", "reProvision SLB configurationInfo is null ");
            } else {
                ALog.d("AddDeviceBiz", "reProvision SLB configurationInfo serverConfirmation : " + list.get(0).configurationInfo.getServerConfirmation() + "; AppKey : " + list.get(0).configurationInfo.getConfigResultMap().getSigmeshKeys().get(0).getProvisionAppKeys().get(0).getAppKey());
            }
            if (ConcurrentAddDeviceBiz.getInstance().getConfigurationInfoCache().size() > 0 && ConcurrentAddDeviceBiz.getInstance().getConfigurationInfoCacheValue(list.get(0).deviceId) != null && DeviceInfoUtils.isSupportFastProvisioningV2(list.get(0).deviceId)) {
                list.get(0).subDeviceId = list.get(0).deviceId;
                list.get(0).configurationInfo = ConcurrentAddDeviceBiz.getInstance().getConfigurationInfoCacheValue(list.get(0).deviceId);
                list.get(0).authFlag = true;
                a(context, list, iConcurrentAddDeviceListener);
                return;
            }
            if (!DeviceInfoUtils.isSupportFastProvisioningV2(list.get(0).deviceId)) {
                a(context, list, iConcurrentAddDeviceListener);
                return;
            }
        }
        final LinkedHashMap linkedHashMap = new LinkedHashMap();
        ArrayList arrayList = new ArrayList();
        for (DeviceInfo deviceInfo : list) {
            if (!TextUtils.isEmpty(deviceInfo.mac)) {
                linkedHashMap.put(DeviceInfoUtils.getMeshDeviceUniqueIDByMac(deviceInfo.mac), deviceInfo);
            }
            if (!TextUtils.isEmpty(deviceInfo.deviceId)) {
                arrayList.add(deviceInfo.deviceId);
            }
        }
        ALog.d("AddDeviceBiz", "To be provision device size: " + list.size());
        ProvisionRepositoryV2.provisionSLB(arrayList, new ApiCallBack<List<ProvisionSLBItem>>() { // from class: com.aliyun.alink.business.devicecenter.api.add.AddDeviceBiz.1
            @Override // com.aliyun.alink.linksdk.connectsdk.BaseCallBack
            public void onFail(int i2, String str) {
                ALog.e("AddDeviceBiz", "provision SLB, error: " + str);
                AddDeviceBiz.this.a(context, list, iConcurrentAddDeviceListener);
            }

            @Override // com.aliyun.alink.linksdk.connectsdk.BaseCallBack
            public void onSuccess(List<ProvisionSLBItem> list2) {
                ALog.i("AddDeviceBiz", "############### Provision SLB Strategy ###############");
                ALog.d("AddDeviceBiz", "SLB result size: " + list2.size());
                for (ProvisionSLBItem provisionSLBItem : list2) {
                    if (TextUtils.isEmpty(provisionSLBItem.getMac())) {
                        ALog.w("AddDeviceBiz", "Illegal SLB result for device: " + provisionSLBItem.getDeviceName() + ", mac address cannot be null");
                    } else {
                        DeviceInfo deviceInfo2 = (DeviceInfo) linkedHashMap.get(DeviceInfoUtils.getMeshDeviceUniqueIDByMac(provisionSLBItem.getMac()));
                        if (provisionSLBItem.getConfigurationInfo() != null && !TextUtils.isEmpty(provisionSLBItem.getConfirmCloud())) {
                            provisionSLBItem.getConfigurationInfo().setServerConfirmation(provisionSLBItem.getConfirmCloud());
                        }
                        if (deviceInfo2 != null) {
                            deviceInfo2.authFlag = provisionSLBItem.isAuthFlag();
                            deviceInfo2.deviceId = provisionSLBItem.getSubDeviceId();
                            deviceInfo2.random = provisionSLBItem.getRandom();
                            deviceInfo2.subDeviceId = provisionSLBItem.getSubDeviceId();
                            deviceInfo2.authDevice = provisionSLBItem.getAuthDevice();
                            deviceInfo2.confirmCloud = provisionSLBItem.getConfirmCloud();
                            ConfigurationData configurationInfo = provisionSLBItem.getConfigurationInfo();
                            deviceInfo2.configurationInfo = configurationInfo;
                            if (configurationInfo != null) {
                                configurationInfo.setServerConfirmation(provisionSLBItem.getConfirmCloud());
                            }
                            if (PushConstants.EXTRA_APPLICATION_PENDING_INTENT.equals(provisionSLBItem.getDiscoveredSource())) {
                                ALog.d("AddDeviceBiz", String.format("%s: app, previous: %s", provisionSLBItem.getMac(), deviceInfo2.linkType));
                                deviceInfo2.linkType = LinkType.ALI_APP_MESH.getName();
                                if (provisionSLBItem.getConfigurationInfo() != null) {
                                    deviceInfo2.configurationInfo = provisionSLBItem.getConfigurationInfo();
                                    ConcurrentAddDeviceBiz.getInstance().setConfigurationInfoCache(deviceInfo2.deviceId, deviceInfo2.configurationInfo);
                                }
                            } else if ("meshGw".equals(provisionSLBItem.getDiscoveredSource())) {
                                ALog.d("AddDeviceBiz", String.format("%s: meshGw, gwIotId: %s, previous: %s", provisionSLBItem.getMac(), provisionSLBItem.getGatewayIotId(), deviceInfo2.linkType));
                                deviceInfo2.linkType = LinkType.ALI_GATEWAY_MESH.getName();
                                deviceInfo2.regIotId = provisionSLBItem.getGatewayIotId();
                                deviceInfo2.iotId = provisionSLBItem.getIotId();
                                deviceInfo2.configurationInfo = provisionSLBItem.getConfigurationInfo();
                            } else {
                                ALog.w("AddDeviceBiz", "Unknown discoverdSource " + provisionSLBItem.getDiscoveredSource());
                            }
                        } else {
                            ALog.w("AddDeviceBiz", "Cannot find provision info for device: " + provisionSLBItem.getDeviceName());
                        }
                    }
                }
                ALog.i("AddDeviceBiz", "############### Provision SLB Strategy End ###############");
                AddDeviceBiz.this.a(context, list, iConcurrentAddDeviceListener);
            }
        });
    }

    @Override // com.aliyun.alink.business.devicecenter.api.add.IAddDeviceBiz
    public void stopAddDevice() {
        ALog.i("AddDeviceBiz", "stopAddDevice() call.");
        this.f9989e = null;
        this.f9990f = null;
        setProvisionTimeOut(60);
        this.f9987c = AddDeviceState.AddStateProvisionOver;
        try {
            DeviceCenterBiz.getInstance().stopConfig();
        } catch (Exception e2) {
            e2.printStackTrace();
            ALog.w("AddDeviceBiz", "stopProvision,error," + e2);
        }
        DeviceCenterBiz.getInstance().setExtraInfo(null);
    }

    @Override // com.aliyun.alink.business.devicecenter.api.add.IAddDeviceBiz
    public void stopConcurrentAddDevice() {
        this.f9991g = 0;
        stopAddDevice();
        ConcurrentAddDeviceBiz.getInstance().stopConfig();
        ConcurrentGateAddDeviceBiz.getInstance().stopConfig();
    }

    @Override // com.aliyun.alink.business.devicecenter.api.add.IAddDeviceBiz
    public void toggleProvision(String str, String str2, int i2) {
        ALog.i("AddDeviceBiz", "toggleProvision() call. ssid= " + str + ", len(p)=" + StringUtils.getStringLength(str2) + ", timeout =" + i2);
        PerformanceLog.trace("AddDeviceBiz", AlinkConstants.KEY_TOGGLE_PROVISION);
        if (TextUtils.isEmpty(str)) {
            AddDeviceState addDeviceState = AddDeviceState.AddStateProvisionOver;
            this.f9987c = addDeviceState;
            a(addDeviceState, -1, false, null, new DCErrorCode(DCErrorCode.PARAM_ERROR_MSG, DCErrorCode.PF_PARAMS_ERROR).setSubcode(DCErrorCode.SUBCODE_PE_SSID_EMPTY).setMsg("ssidEmpty"));
            return;
        }
        DeviceInfo deviceInfo = this.f9986b;
        if (deviceInfo == null || !(deviceInfo.isValid() || LinkType.ALI_APP_COMBO_MESH.getName().equalsIgnoreCase(this.f9986b.linkType))) {
            AddDeviceState addDeviceState2 = AddDeviceState.AddStateProvisionOver;
            this.f9987c = addDeviceState2;
            a(addDeviceState2, -1, false, null, new DCErrorCode(DCErrorCode.PARAM_ERROR_MSG, DCErrorCode.PF_PARAMS_ERROR).setSubcode(DCErrorCode.SUBCODE_PE_PRODUCTKEY_EMPTY).setMsg("tpDeviceInfoInvalid"));
            return;
        }
        if (this.f9990f == null) {
            AddDeviceState addDeviceState3 = AddDeviceState.AddStateProvisionOver;
            this.f9987c = addDeviceState3;
            a(addDeviceState3, -1, false, null, new DCErrorCode(DCErrorCode.PARAM_ERROR_MSG, DCErrorCode.PF_PARAMS_ERROR).setSubcode(DCErrorCode.SUBCODE_WRONG_CALL).setMsg("tpProvisionParamsNull"));
            return;
        }
        DeviceCenterBiz.getInstance().selectStrategy(this.f9990f.linkType);
        if (!DeviceCenterBiz.getInstance().needWiFiSsidPwd()) {
            ALog.w("AddDeviceBiz", "do not need to call this interface for " + this.f9990f.linkType);
            return;
        }
        DCUserTrack.addTrackData(AlinkConstants.KEY_TOGGLE_PROVISION, String.valueOf(System.currentTimeMillis()));
        DCAlibabaConfigParams dCAlibabaConfigParams = this.f9990f;
        dCAlibabaConfigParams.ssid = str;
        dCAlibabaConfigParams.password = str2;
        try {
            setProvisionTimeOut(i2);
            this.f9990f.timeout = this.f9988d;
            AddDeviceState addDeviceState4 = AddDeviceState.AddStateProvisioning;
            this.f9987c = addDeviceState4;
            a(addDeviceState4, -1, true, null, null);
            DeviceCenterBiz.getInstance().startConfig(new MyConfigCallback(), this.f9990f);
        } catch (Exception e2) {
            e2.printStackTrace();
            ALog.e("AddDeviceBiz", "toggleProvision,provisioning error , e" + e2);
            AddDeviceState addDeviceState5 = AddDeviceState.AddStateProvisionOver;
            this.f9987c = addDeviceState5;
            a(addDeviceState5, -1, false, null, new DCErrorCode("SDKError", DCErrorCode.PF_SDK_ERROR).setMsg("startConfigException=" + e2));
        }
    }

    public final void a(Context context, List<DeviceInfo> list, final IConcurrentAddDeviceListener iConcurrentAddDeviceListener) {
        LinkedList linkedList = new LinkedList();
        Iterator<DeviceInfo> it = list.iterator();
        while (it.hasNext()) {
            final DeviceInfo next = it.next();
            if (!TextUtils.isEmpty(next.mac)) {
                LinkType linkType = LinkType.ALI_GATEWAY_MESH;
                if (linkType.getName().equalsIgnoreCase(next.linkType) || LinkType.ALI_APP_MESH.getName().equalsIgnoreCase(next.linkType)) {
                    if (linkType.getName().equalsIgnoreCase(next.linkType)) {
                        linkedList.add(next);
                        it.remove();
                    }
                }
            }
            DeviceCenterBiz.getInstance().runOnUIThread(new Runnable() { // from class: com.aliyun.alink.business.devicecenter.api.add.AddDeviceBiz.2
                @Override // java.lang.Runnable
                public void run() {
                    if (iConcurrentAddDeviceListener != null) {
                        iConcurrentAddDeviceListener.onProvisionedResult(false, next, new DCErrorCode("USER_INVOKE_ERROR", DCErrorCode.PF_PARAMS_ERROR).setSubcode(60805).setMsg("startAddDevice running, return."));
                    }
                }
            });
        }
        if (list.size() > 0) {
            ConcurrentAddDeviceBiz.getInstance().a(new IConcurrentAddDeviceStatusListener() { // from class: com.aliyun.alink.business.devicecenter.api.add.AddDeviceBiz.3
                @Override // com.aliyun.alink.business.devicecenter.api.add.IConcurrentAddDeviceStatusListener
                public void onIdle() {
                }
            });
            ConcurrentAddDeviceBiz.getInstance().a(context, list, iConcurrentAddDeviceListener);
        }
        if (linkedList.size() > 0) {
            ALog.d("AddDeviceBiz", "startConcurrentAddDeviceInner onResponse: 开始急速配网");
            ConcurrentGateAddDeviceBiz.getInstance().a(context, linkedList, iConcurrentAddDeviceListener);
        }
    }

    public final boolean a(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        for (LinkType linkType : LinkType.values()) {
            if (str.equals(linkType.getName())) {
                return true;
            }
        }
        return false;
    }

    public final void a(final AddDeviceState addDeviceState, final int i2, final boolean z2, final DeviceInfo deviceInfo, final DCErrorCode dCErrorCode) {
        if (addDeviceState == AddDeviceState.AddStateProvisionOver && !z2 && dCErrorCode != null) {
            ALog.e("AddDeviceBiz", "state=" + addDeviceState + ",isSuccess=" + z2 + ",info=" + deviceInfo + ",error=" + dCErrorCode);
        } else {
            ALog.i("AddDeviceBiz", "state=" + addDeviceState + ",isSuccess=" + z2 + ",info=" + deviceInfo + ",error=" + dCErrorCode);
        }
        DeviceCenterBiz.getInstance().runOnUIThread(new Runnable() { // from class: com.aliyun.alink.business.devicecenter.api.add.AddDeviceBiz.8
            @Override // java.lang.Runnable
            public void run() {
                int i3 = AnonymousClass9.f10016a[addDeviceState.ordinal()];
                if (i3 == 1) {
                    if (AddDeviceBiz.this.f9989e != null) {
                        AddDeviceBiz.this.f9989e.onPreCheck(z2, dCErrorCode);
                        return;
                    }
                    return;
                }
                if (i3 == 2) {
                    if (AddDeviceBiz.this.f9989e != null) {
                        AddDeviceBiz.this.f9989e.onProvisionPrepare(i2);
                        return;
                    }
                    return;
                }
                if (i3 == 3) {
                    if (AddDeviceBiz.this.f9989e != null) {
                        AddDeviceBiz.this.f9989e.onProvisioning();
                        return;
                    }
                    return;
                }
                if (i3 != 4) {
                    return;
                }
                if (z2) {
                    AddDeviceBiz.this.a(deviceInfo);
                } else {
                    AddDeviceBiz.this.a(dCErrorCode);
                }
                PerformanceLog.trace("AddDeviceBiz", "provisionResult", PerformanceLog.getJsonObject("result", z2 ? "success" : ITagManager.FAIL));
                ALog.d("AddDeviceBiz", "onProvisionedResult addDeviceListener=" + AddDeviceBiz.this.f9989e);
                if (AddDeviceBiz.this.f9989e != null) {
                    AddDeviceBiz.this.f9989e.onProvisionedResult(z2, deviceInfo, dCErrorCode);
                }
                if (AddDeviceBiz.this.f9992h) {
                    return;
                }
                if ((AlinkHelper.isBatch(AddDeviceBiz.this.f9990f) && z2) || deviceInfo.comboMeshFlag) {
                    return;
                }
                AddDeviceBiz.this.stopAddDevice();
            }
        });
    }

    public final void a(Object obj) {
        ALog.d("AddDeviceBiz", "provisionTrack obj=" + obj);
        try {
            DeviceInfo deviceInfo = this.f9986b;
            if (deviceInfo != null && !TextUtils.isEmpty(deviceInfo.productKey)) {
                DCUserTrack.addTrackData("pk", this.f9986b.productKey);
            }
            DeviceInfo deviceInfo2 = this.f9986b;
            if (deviceInfo2 != null && !TextUtils.isEmpty(deviceInfo2.deviceName)) {
                DCUserTrack.addTrackData(AlinkConstants.KEY_DN, this.f9986b.deviceName);
            }
            DCUserTrack.addTrackData(AlinkConstants.KEY_END_TIME_PROVISION, String.valueOf(System.currentTimeMillis()));
            this.f9987c = AddDeviceState.AddStateProvisionOver;
            if (obj instanceof DeviceInfo) {
                DCUserTrack.addTrackData("pk", ((DeviceInfo) obj).productKey);
                DCUserTrack.addTrackData(AlinkConstants.KEY_DN, ((DeviceInfo) obj).deviceName);
                DCUserTrack.addTrackData(AlinkConstants.KEY_PROVISION_RESULT, "1");
                DCUserTrack.sendEvent();
                return;
            }
            if (obj instanceof DCErrorCode) {
                DCErrorCode dCErrorCode = (DCErrorCode) obj;
                DCUserTrack.addTrackData("errorCode", dCErrorCode.code);
                DCUserTrack.addTrackData("subErrorCode", dCErrorCode.subcode);
                DCUserTrack.addTrackData(AlinkConstants.KEY_SUB_ERROR_MSG, dCErrorCode.msg);
                DCUserTrack.addTrackData(AlinkConstants.KEY_PROVISION_RESULT, "0");
                DCUserTrack.addTrackData(PushConstants.EXTRA, String.valueOf(dCErrorCode.extra));
                if (!String.valueOf(DCErrorCode.SUBCODE_PT_SAP_NO_SOFTAP).equals(dCErrorCode.code) && !String.valueOf(DCErrorCode.SUBCODE_PT_SAP_CONNECT_DEV_AP_FAILED).equals(dCErrorCode.code)) {
                    DCUserTrack.sendEvent();
                    return;
                }
                DCUserTrack.sendEvent(AlinkConstants.KEY_DC_PROVISION_DISCOVER);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
