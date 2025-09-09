package com.aliyun.iot.aep.sdk.init;

import android.app.Application;
import com.aliyun.alink.linksdk.tmp.TmpSdk;
import com.aliyun.alink.linksdk.tmp.api.TmpInitConfig;
import com.aliyun.alink.linksdk.tmp.data.discovery.DiscoveryConfig;
import com.aliyun.alink.linksdk.tools.ALog;
import com.aliyun.iot.aep.sdk.framework.sdk.SDKConfigure;
import com.aliyun.iot.aep.sdk.framework.sdk.SDKManager;
import com.aliyun.iot.aep.sdk.framework.sdk.SimpleSDKDelegateImp;
import com.aliyun.iot.aep.sdk.threadpool.ThreadPool;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/* loaded from: classes3.dex */
public class ThingModuleSDKDelegate extends SimpleSDKDelegateImp {
    public static final String DISCOVERY_ON = "discovery_on";
    public static final String DISCOVERY_SETTING_FILENAME = "discovery_setting";

    public static void startSchedule(Application application) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        boolean z2 = application.getSharedPreferences(DISCOVERY_SETTING_FILENAME, 0).getInt(DISCOVERY_ON, 1) >= 1;
        ALog.d("ThingModuleSDKDelegate", "startSchedule isDiscoveryOn:" + z2);
        if (z2) {
            DiscoveryConfig discoveryConfig = new DiscoveryConfig();
            DiscoveryConfig.DiscoveryParams discoveryParams = new DiscoveryConfig.DiscoveryParams();
            discoveryConfig.discoveryParams = discoveryParams;
            discoveryParams.discoveryStrategy = DiscoveryConfig.DiscoveryStrategy.LOW_ENERGY;
            TmpSdk.getDeviceManager().discoverDevicesInfinite(false, 40000L, 0L, discoveryConfig, null, null);
        }
    }

    public static void stopSchedule() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        TmpSdk.getDeviceManager().stopDiscoverDevicesInfinite();
    }

    @Override // com.aliyun.iot.aep.sdk.framework.sdk.ISDKDelegate
    public int init(final Application application, SDKConfigure sDKConfigure, Map<String, String> map) {
        if (!SDKManager.isTMPAvailable()) {
            return 0;
        }
        ThreadPool.DefaultThreadPool.getInstance().submit(new Runnable() { // from class: com.aliyun.iot.aep.sdk.init.ThingModuleSDKDelegate.1
            @Override // java.lang.Runnable
            public void run() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                TmpSdk.init(application, new TmpInitConfig(2));
                ThingModuleSDKDelegate.startSchedule(application);
            }
        });
        return 0;
    }
}
