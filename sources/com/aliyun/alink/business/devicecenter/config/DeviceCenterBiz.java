package com.aliyun.alink.business.devicecenter.config;

import android.content.Context;
import android.os.Build;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.aliyun.alink.business.devicecenter.BuildConfig;
import com.aliyun.alink.business.devicecenter.api.add.LinkType;
import com.aliyun.alink.business.devicecenter.api.add.ProvisionStatus;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.aliyun.alink.business.devicecenter.base.DCEnvHelper;
import com.aliyun.alink.business.devicecenter.base.DCErrorCode;
import com.aliyun.alink.business.devicecenter.biz.SilenceWorker;
import com.aliyun.alink.business.devicecenter.biz.service.SystemAbilityChangeBroadcast;
import com.aliyun.alink.business.devicecenter.channel.coap.CoAPClient;
import com.aliyun.alink.business.devicecenter.channel.http.TransitoryClient;
import com.aliyun.alink.business.devicecenter.config.annotation.ConfigStrategy;
import com.aliyun.alink.business.devicecenter.config.model.DCAlibabaConfigParams;
import com.aliyun.alink.business.devicecenter.config.model.DCConfigParams;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.plugin.DiscoveryPluginMgr;
import com.aliyun.alink.business.devicecenter.plugin.ProvisionPlugin;
import com.aliyun.alink.business.devicecenter.track.DCUserTrack;
import com.aliyun.alink.business.devicecenter.track.UsageTrack;
import com.aliyun.alink.business.devicecenter.utils.ClassUtil;
import com.aliyun.alink.business.devicecenter.utils.ContextHolder;
import com.aliyun.alink.business.devicecenter.utils.FileUtils;
import com.aliyun.alink.business.devicecenter.utils.NetworkConnectiveManager;
import com.aliyun.alink.business.devicecenter.utils.SystemPropertiesUtils;
import com.aliyun.alink.linksdk.channel.core.persistent.PersistentNet;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.aliyun.alink.linksdk.tools.ThreadTools;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes2.dex */
public class DeviceCenterBiz implements IConfigStrategy {

    /* renamed from: a, reason: collision with root package name */
    public static final ServiceLoader<IConfigStrategy> f10390a = ServiceLoader.load(IConfigStrategy.class, IConfigStrategy.class.getClassLoader());

    /* renamed from: b, reason: collision with root package name */
    public IConfigStrategy f10391b;

    /* renamed from: c, reason: collision with root package name */
    public Map f10392c;

    /* renamed from: d, reason: collision with root package name */
    public AtomicBoolean f10393d;

    private static class SingletonHolder {

        /* renamed from: a, reason: collision with root package name */
        public static final DeviceCenterBiz f10394a = new DeviceCenterBiz();
    }

    public static DeviceCenterBiz getInstance() {
        return SingletonHolder.f10394a;
    }

    public final boolean a(LinkType linkType) {
        return linkType == null || linkType == LinkType.ALI_DEFAULT || linkType.ordinal() <= LinkType.ALI_ZERO_IN_BATCHES.ordinal();
    }

    public final void b() {
        TransitoryClient.getInstance().registerIgnoreRetryApiPath(AlinkConstants.HTTP_PATH_CLOUD_ENROLLEE_DISCOVER);
        TransitoryClient.getInstance().registerIgnoreRetryApiPath(AlinkConstants.HTTP_PATH_CHECK_TOKEN);
        TransitoryClient.getInstance().registerIgnoreRetryApiPath(AlinkConstants.HTTP_PATH_ILOP_CHECK_BIND_TOKEN);
        TransitoryClient.getInstance().registerIgnoreRetryApiPath(AlinkConstants.HTTP_PATH_GET_DISCOVERED_MESH_DEVICE);
        TransitoryClient.getInstance().registerIgnoreRetryApiPath(AlinkConstants.HTTP_PATH_GET_MESH_PROVISION_RESULT);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void c() throws IOException {
        JSONArray jSONArray = JSON.parseObject(FileUtils.ReadFile(ContextHolder.getInstance().getAppContext().getAssets().open("config.conf"))).getJSONArray("configStrategy");
        List<Class<?>> classes = ClassUtil.getClasses(jSONArray.toJavaList(String.class));
        for (int i2 = 0; i2 < jSONArray.size(); i2++) {
        }
        for (Class<?> cls : classes) {
            if (cls.isAnnotationPresent(ConfigStrategy.class) && IConfigStrategy.class.isAssignableFrom(cls)) {
                ConfigStrategy configStrategy = (ConfigStrategy) cls.getAnnotation(ConfigStrategy.class);
                if (ProvisionPlugin.getInstance().isStrategyExist(configStrategy.linkType())) {
                    ALog.e("DeviceCenterBiz", "LinkType: " + configStrategy.linkType().getName() + " has exist, " + cls.getName() + " will be ignore");
                } else {
                    ProvisionPlugin.getInstance().registerProvisionStrategy(configStrategy.linkType(), cls);
                    ALog.i("DeviceCenterBiz", "register provision strategy: " + configStrategy.linkType() + " with class: " + cls.getName());
                }
            }
        }
    }

    @Override // com.aliyun.alink.business.devicecenter.config.IConfigStrategy
    public void continueConfig(Map map) {
        ALog.d("DeviceCenterBiz", "continueConfig() called with: provisionParams = [" + map + "]");
        IConfigStrategy iConfigStrategy = this.f10391b;
        if (iConfigStrategy != null) {
            iConfigStrategy.continueConfig(map);
        }
    }

    @Override // com.aliyun.alink.business.devicecenter.config.IConfigStrategy
    public void doExtraPrepareWork(IConfigExtraCallback iConfigExtraCallback) {
        IConfigStrategy iConfigStrategy = this.f10391b;
        if (iConfigStrategy != null) {
            iConfigStrategy.doExtraPrepareWork(iConfigExtraCallback);
        } else {
            ALog.e("DeviceCenterBiz", "strategy is null, maybe not select");
        }
    }

    public Context getAppContext() {
        return ContextHolder.getInstance().getAppContext();
    }

    public IConfigStrategy getConfigStrategy(LinkType linkType) {
        return new DCStrategyFactory().createStrategy(ContextHolder.getInstance().getAppContext(), linkType);
    }

    @Override // com.aliyun.alink.business.devicecenter.config.IConfigStrategy
    public String getProvisionType() {
        return null;
    }

    public String getTraceId() {
        Map map = this.f10392c;
        if (map != null) {
            return String.valueOf(map.get(TmpConstant.KEY_IOT_PERFORMANCE_ID));
        }
        return null;
    }

    @Override // com.aliyun.alink.business.devicecenter.config.IConfigStrategy
    public boolean hasExtraPrepareWork() {
        IConfigStrategy iConfigStrategy = this.f10391b;
        if (iConfigStrategy != null) {
            return iConfigStrategy.hasExtraPrepareWork();
        }
        ALog.e("DeviceCenterBiz", "strategy is null, maybe not select");
        return false;
    }

    @Override // com.aliyun.alink.business.devicecenter.config.IConfigStrategy
    public boolean isSupport() {
        return true;
    }

    @Override // com.aliyun.alink.business.devicecenter.config.IConfigStrategy
    public boolean needWiFiSsidPwd() {
        IConfigStrategy iConfigStrategy = this.f10391b;
        if (iConfigStrategy != null) {
            return iConfigStrategy.needWiFiSsidPwd();
        }
        ALog.e("DeviceCenterBiz", "strategy is null, maybe not select");
        return false;
    }

    public void onConfigCallback(ConfigCallbackWrapper configCallbackWrapper) {
        IConfigCallback iConfigCallback;
        if (configCallbackWrapper == null || (iConfigCallback = configCallbackWrapper.callback) == null) {
            return;
        }
        ProvisionStatus provisionStatus = configCallbackWrapper.provisioningStatus;
        if (provisionStatus != null) {
            iConfigCallback.onStatus(provisionStatus);
            return;
        }
        if (configCallbackWrapper.isSuccess) {
            iConfigCallback.onSuccess(configCallbackWrapper.result);
            return;
        }
        iConfigCallback.onFailure(configCallbackWrapper.errorCode);
        ALog.d("DeviceCenterBiz", "onConfigCallback onFail needTrack=" + configCallbackWrapper.needTrack + ".");
    }

    @Override // com.aliyun.alink.business.devicecenter.config.IConfigStrategy
    public void preConfig(IConfigCallback iConfigCallback, DCConfigParams dCConfigParams) {
    }

    public void runOnUIThread(Runnable runnable) {
        ThreadTools.runOnUiThread(runnable);
    }

    public void selectStrategy(LinkType linkType) {
        if (this.f10391b == null) {
            this.f10391b = new DCStrategyFactory().createStrategy(ContextHolder.getInstance().getAppContext(), linkType);
        }
    }

    public void setAppContext(Context context) {
        if (context != null && ContextHolder.getInstance().getAppContext() == null) {
            DCEnvHelper.initEnv();
            ALog.d("DeviceCenterBiz", "setAppContext context=" + context);
            ContextHolder.getInstance().init(context);
            try {
                c();
                DiscoveryPluginMgr.getInstance().init();
                try {
                    NetworkConnectiveManager.getInstance().initNetworkConnectiveManager(ContextHolder.getInstance().getAppContext());
                    SilenceWorker.getInstance().start();
                } catch (Exception e2) {
                    ALog.w("DeviceCenterBiz", "initNetworkConnectiveManager exception=" + e2);
                }
                try {
                    SystemAbilityChangeBroadcast.getInstance().init(context);
                } catch (Exception e3) {
                    ALog.w("DeviceCenterBiz", "initSystemAbilityChangeBroadcast exception=" + e3);
                }
                b();
                ALog.i("DeviceCenterBiz", "DeviceCenter VERSION =1.10.4-6b5e8ff, Android SDK VERSION = " + Build.VERSION.SDK_INT + ", OS = " + Build.VERSION.RELEASE + ", MODEL = " + Build.MODEL);
                a();
            } catch (IOException e4) {
                ALog.w("DeviceCenterBiz", "registerProvisionStrategy or init DiscoveryPluginMgr exception=" + e4);
                throw new RuntimeException("registerProvisionStrategy or init DiscoveryPluginMgr has exception", e4);
            }
        }
    }

    public void setExtraInfo(Map map) {
        this.f10392c = map;
    }

    @Override // com.aliyun.alink.business.devicecenter.config.IConfigStrategy
    public void startConfig(IConfigCallback iConfigCallback, DCConfigParams dCConfigParams) throws Exception {
        LinkType linkType;
        ALog.d("DeviceCenterBiz", "startDeviceConfig(),call," + dCConfigParams);
        if (dCConfigParams == null) {
            onConfigCallback(new ConfigCallbackWrapper().callback(iConfigCallback).success(false).error(new DCErrorCode(DCErrorCode.PARAM_ERROR_MSG, DCErrorCode.PF_PARAMS_ERROR).setMsg("configParams is empty").setSubcode(DCErrorCode.SUBCODE_PE_PROVISION_PARAMS_ERROR)));
            return;
        }
        LinkType linkType2 = dCConfigParams.linkType;
        if (linkType2 == null) {
            onConfigCallback(new ConfigCallbackWrapper().callback(iConfigCallback).success(false).error(new DCErrorCode(DCErrorCode.PARAM_ERROR_MSG, DCErrorCode.PF_PARAMS_ERROR).setMsg("type of ConfigParams is empty").setSubcode(DCErrorCode.SUBCODE_PE_PROVISION_PARAMS_ERROR)));
            return;
        }
        if (a(linkType2)) {
            CoAPClient.getInstance().registerResource("provisionOver", AlinkConstants.COAP_PATH_NOTIFY_PROVISION_SUCCESS);
        }
        IConfigStrategy iConfigStrategyCreateStrategy = new DCStrategyFactory().createStrategy(ContextHolder.getInstance().getAppContext(), dCConfigParams.linkType);
        this.f10391b = iConfigStrategyCreateStrategy;
        if (iConfigStrategyCreateStrategy == null) {
            onConfigCallback(new ConfigCallbackWrapper().callback(iConfigCallback).success(false).error(new DCErrorCode(DCErrorCode.PARAM_ERROR_MSG, DCErrorCode.PF_PARAMS_ERROR).setMsg("provisionTypeError " + dCConfigParams.linkType).setSubcode(DCErrorCode.SUBCODE_PE_PROVISION_PARAMS_ERROR)));
            return;
        }
        if (!this.f10393d.get() && DCEnvHelper.hasApiClientBiz()) {
            this.f10393d.set(true);
            UsageTrack.sdkUsage(BuildConfig.SDK_VERSION);
        }
        DCUserTrack.addTrackData("sdkVersion", BuildConfig.SDK_VERSION);
        if ((dCConfigParams instanceof DCAlibabaConfigParams) && (linkType = dCConfigParams.linkType) != null) {
            DCUserTrack.addTrackData(AlinkConstants.KEY_LINKTYPE, linkType.getName());
        }
        this.f10391b.startConfig(iConfigCallback, dCConfigParams);
    }

    @Override // com.aliyun.alink.business.devicecenter.config.IConfigStrategy
    public void stopConfig() {
        IConfigStrategy iConfigStrategy = this.f10391b;
        if (iConfigStrategy != null) {
            iConfigStrategy.stopConfig();
            this.f10391b = null;
        }
    }

    public DeviceCenterBiz() {
        this.f10391b = null;
        this.f10392c = null;
        this.f10393d = new AtomicBoolean(false);
    }

    public final void a() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if ("enable".equals(SystemPropertiesUtils.get(AlinkConstants.DEBUG_LOG_KEY))) {
            ALog.setLevel((byte) 1);
            try {
                int i2 = PersistentNet.f10859g;
                Object objInvoke = PersistentNet.class.getDeclaredMethod("getInstance", null).invoke(null, null);
                objInvoke.getClass().getDeclaredMethod("openLog", Boolean.TYPE).invoke(objInvoke, Boolean.TRUE);
            } catch (ClassNotFoundException e2) {
                ALog.d("DeviceCenterBiz", "ClassNotFoundException e=" + e2);
            } catch (IllegalAccessException e3) {
                ALog.d("DeviceCenterBiz", "IllegalAccessException e=" + e3);
            } catch (NoSuchMethodException e4) {
                ALog.d("DeviceCenterBiz", "NoSuchMethodException e=" + e4);
            } catch (InvocationTargetException e5) {
                ALog.d("DeviceCenterBiz", "InvocationTargetException e=" + e5);
            } catch (Exception e6) {
                ALog.d("DeviceCenterBiz", "Exception e=" + e6);
            }
        }
    }
}
