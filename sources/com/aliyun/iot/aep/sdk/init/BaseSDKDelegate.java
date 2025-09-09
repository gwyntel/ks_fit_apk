package com.aliyun.iot.aep.sdk.init;

import android.app.Application;
import android.text.TextUtils;
import com.alibaba.wireless.security.jaq.JAQException;
import com.alibaba.wireless.security.jaq.SecurityInit;
import com.aliyun.alink.page.rn.InitializationHelper;
import com.aliyun.alink.sdk.bone.plugins.BaseBoneServiceFactory;
import com.aliyun.alink.sdk.bone.plugins.config.BoneConfig;
import com.aliyun.iot.aep.page.rn.j;
import com.aliyun.iot.aep.routerexternal.RouterExternal;
import com.aliyun.iot.aep.routerexternal.RouterServiceFactory;
import com.aliyun.iot.aep.sdk.IoTSmart;
import com.aliyun.iot.aep.sdk.apiclient.IoTAPIClientImpl;
import com.aliyun.iot.aep.sdk.apiclient.emuns.Env;
import com.aliyun.iot.aep.sdk.bridge.core.BoneServiceFactoryRegistry;
import com.aliyun.iot.aep.sdk.framework.config.GlobalConfig;
import com.aliyun.iot.aep.sdk.framework.region.RegionManager;
import com.aliyun.iot.aep.sdk.framework.sdk.SDKConfigure;
import com.aliyun.iot.aep.sdk.framework.sdk.SDKManager;
import com.aliyun.iot.aep.sdk.framework.sdk.SimpleSDKDelegateImp;
import com.aliyun.iot.aep.sdk.log.ALog;
import java.util.Map;

/* loaded from: classes3.dex */
public final class BaseSDKDelegate extends SimpleSDKDelegateImp {
    int a(Application application, SDKConfigure sDKConfigure, Map<String, String> map) {
        int errorCode = -1;
        try {
            errorCode = SecurityInit.Initialize(application);
        } catch (JAQException e2) {
            ALog.e("APIGatewaySDKDelegate", "security-sdk-initialize-failed", (Exception) e2);
            errorCode = e2.getErrorCode();
        } catch (Exception e3) {
            ALog.e("APIGatewaySDKDelegate", "e security-sdk-initialize-failed", e3);
        } catch (Throwable th) {
            ALog.e("APIGatewaySDKDelegate", "t security-sdk-initialize-failed" + th);
        }
        Env env = Env.RELEASE;
        String authCode = GlobalConfig.getInstance().getAuthCode();
        String language = GlobalConfig.getInstance().getLanguage();
        String storedApiAddress = RegionManager.getStoredApiAddress();
        String apiEnv = GlobalConfig.getInstance().getApiEnv();
        ALog.d("APIGatewaySDKDelegate", "host1:" + storedApiAddress);
        if ("pre".equals(apiEnv)) {
            env = Env.PRE;
        } else if ("test".equals(apiEnv)) {
            env = Env.TEST;
        }
        if (TextUtils.isEmpty(storedApiAddress)) {
            storedApiAddress = RegionManager.getApiAddr();
        }
        ALog.d("APIGatewaySDKDelegate", "host2:" + storedApiAddress);
        IoTAPIClientImpl.InitializeConfig initializeConfig = new IoTAPIClientImpl.InitializeConfig();
        if (TextUtils.isEmpty(storedApiAddress)) {
            storedApiAddress = "api.link.aliyun.com";
        }
        initializeConfig.host = storedApiAddress;
        initializeConfig.apiEnv = env;
        initializeConfig.authCode = authCode;
        if (GlobalConfig.getInstance().getInitConfig() != null) {
            initializeConfig.isDebug = GlobalConfig.getInstance().getInitConfig().isDebug();
        }
        IoTAPIClientImpl ioTAPIClientImpl = IoTAPIClientImpl.getInstance();
        ioTAPIClientImpl.init(application, initializeConfig);
        ioTAPIClientImpl.setLanguage(language);
        try {
            if (ioTAPIClientImpl.getOkHttpClient() == null || ioTAPIClientImpl.getOkHttpClient().dispatcher() == null) {
                ALog.w("APIGatewaySDKDelegate", "okhttpclient == null, setMaxRequestsPerHost failed.");
            } else {
                ALog.i("APIGatewaySDKDelegate", "setMaxRequestsPerHost = 32.");
                ioTAPIClientImpl.getOkHttpClient().dispatcher().setMaxRequestsPerHost(32);
            }
        } catch (Exception e4) {
            ALog.e("APIGatewaySDKDelegate", "setMaxRequestsPerHost failed. e=" + e4);
        }
        return errorCode;
    }

    int b(Application application, SDKConfigure sDKConfigure, Map<String, String> map) {
        String boneEnv = GlobalConfig.getInstance().getBoneEnv();
        String language = GlobalConfig.getInstance().getLanguage();
        String apiEnv = GlobalConfig.getInstance().getApiEnv();
        String str = "PRE".equalsIgnoreCase(apiEnv) ? "test" : "TEST".equalsIgnoreCase(apiEnv) ? "development" : IoTSmart.PRODUCT_ENV_PROD;
        String storedRegionName = RegionManager.getStoredRegionName();
        if (SDKManager.isRNAvailable() && !TextUtils.isEmpty(storedRegionName)) {
            try {
                BoneConfig.set("region", storedRegionName);
            } catch (Throwable th) {
                ALog.e("APIGatewaySDKDelegate", "BoneConfig. t=" + th);
            }
        }
        RouterExternal.getInstance().init(application, boneEnv);
        InitializationHelper.initialize(application, boneEnv, str, language);
        return 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x006a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    int c(android.app.Application r5, com.aliyun.iot.aep.sdk.framework.sdk.SDKConfigure r6, java.util.Map<java.lang.String, java.lang.String> r7) throws org.json.JSONException {
        /*
            r4 = this;
            java.lang.String r7 = "false"
            com.aliyun.alink.linksdk.channel.mobile.api.MobileConnectConfig r0 = new com.aliyun.alink.linksdk.channel.mobile.api.MobileConnectConfig
            r0.<init>()
            com.aliyun.iot.aep.sdk.framework.config.GlobalConfig r1 = com.aliyun.iot.aep.sdk.framework.config.GlobalConfig.getInstance()
            java.lang.String r1 = r1.getApiEnv()
            com.aliyun.iot.aep.sdk.framework.config.GlobalConfig r2 = com.aliyun.iot.aep.sdk.framework.config.GlobalConfig.getInstance()
            java.lang.String r2 = r2.getAuthCode()
            java.lang.String r5 = com.aliyun.iot.aep.sdk.apiclient.adapter.APIGatewayHttpAdapterImpl.getAppKey(r5, r2)
            r0.appkey = r5
            r0.securityGuardAuthcode = r2
            org.json.JSONObject r5 = r6.opts
            r6 = 0
            if (r5 == 0) goto L31
            java.lang.String r1 = r1.toLowerCase()     // Catch: org.json.JSONException -> L2d
            org.json.JSONObject r6 = r5.getJSONObject(r1)     // Catch: org.json.JSONException -> L2d
            goto L31
        L2d:
            r5 = move-exception
            r5.printStackTrace()
        L31:
            r5 = 0
            java.lang.String r1 = ""
            if (r6 == 0) goto L5e
            java.lang.String r2 = "channelHost"
            java.lang.String r1 = r6.getString(r2)     // Catch: org.json.JSONException -> L57
            java.lang.String r2 = "autoSelectChannelHost"
            java.lang.String r2 = r6.getString(r2)     // Catch: org.json.JSONException -> L57
            boolean r2 = r7.equalsIgnoreCase(r2)     // Catch: org.json.JSONException -> L57
            r2 = r2 ^ 1
            java.lang.String r3 = "isCheckChannelRootCrt"
            java.lang.String r6 = r6.getString(r3)     // Catch: org.json.JSONException -> L55
            boolean r6 = r7.equalsIgnoreCase(r6)     // Catch: org.json.JSONException -> L55
            r6 = r6 ^ 1
            goto L60
        L55:
            r6 = move-exception
            goto L59
        L57:
            r6 = move-exception
            r2 = r5
        L59:
            r6.printStackTrace()
            r6 = r5
            goto L60
        L5e:
            r6 = r5
            r2 = r6
        L60:
            java.lang.String r7 = com.aliyun.iot.aep.sdk.framework.region.RegionManager.getStoredMqttAddress()
            boolean r3 = android.text.TextUtils.isEmpty(r7)
            if (r3 != 0) goto L6b
            r1 = r7
        L6b:
            r0.autoSelectChannelHost = r2
            r0.channelHost = r1
            r0.isCheckChannelRootCrt = r6
            com.aliyun.iot.aep.sdk.init.DownStreamHelper r6 = com.aliyun.iot.aep.sdk.init.DownStreamHelper.getInstance()
            r6.startMqttOne(r0)
            java.lang.String r6 = "APIGatewaySDKDelegate"
            java.lang.String r7 = "initialized"
            com.aliyun.iot.aep.sdk.log.ALog.d(r6, r7)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aliyun.iot.aep.sdk.init.BaseSDKDelegate.c(android.app.Application, com.aliyun.iot.aep.sdk.framework.sdk.SDKConfigure, java.util.Map):int");
    }

    @Override // com.aliyun.iot.aep.sdk.framework.sdk.ISDKDelegate
    public int init(Application application, SDKConfigure sDKConfigure, Map<String, String> map) {
        int i2 = -1 == a(application, sDKConfigure, map) ? -1 : 0;
        if (!SDKManager.isRNAvailable()) {
            ALog.e("APIGatewaySDKDelegate", "isRNAvailable false");
            BoneServiceFactoryRegistry.register(new BaseBoneServiceFactory());
            if (SDKManager.isRNLibAvailable()) {
                ALog.e("APIGatewaySDKDelegate", "isRNLibAvailable true");
                BoneServiceFactoryRegistry.register(new j());
            } else if (SDKManager.isRouterExternalLibAvailable()) {
                ALog.e("APIGatewaySDKDelegate", "isRouterExternalLibAvailable true");
                BoneServiceFactoryRegistry.register(new RouterServiceFactory());
            } else {
                ALog.e("APIGatewaySDKDelegate", "isRouterExternalLibAvailable false");
            }
        } else if (-1 == b(application, sDKConfigure, map)) {
            i2 = -1;
        }
        int i3 = -1 != c(application, sDKConfigure, map) ? i2 : -1;
        DownStreamHelper.getInstance().initBreeze(application);
        return i3;
    }
}
