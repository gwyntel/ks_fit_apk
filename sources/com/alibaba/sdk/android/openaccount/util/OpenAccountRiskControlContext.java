package com.alibaba.sdk.android.openaccount.util;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.text.TextUtils;
import anet.channel.strategy.dispatch.DispatchConstants;
import com.alibaba.sdk.android.openaccount.ConfigManager;
import com.alibaba.sdk.android.openaccount.OpenAccountConstants;
import com.alibaba.sdk.android.openaccount.OpenAccountSDK;
import com.alibaba.sdk.android.openaccount.model.OAWUAData;
import com.alibaba.sdk.android.openaccount.security.SecurityGuardService;
import com.alibaba.sdk.android.openaccount.trace.AliSDKLogger;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConstants;
import com.alibaba.sdk.android.pluto.Pluto;
import com.alipay.sdk.m.n.d;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/* loaded from: classes2.dex */
public class OpenAccountRiskControlContext {
    private static int appVersionCode = -1;
    private static String appVersionName;
    private static volatile Map<String, Object> cachedEnvironmentInfo;
    private static volatile long lastEnvironmentTimeMill;
    private Context context;

    private static void buildJaqRiskContext(Map<String, Object> map) {
        if ("true".equals(OpenAccountSDK.getProperty("disableJaqVerification"))) {
            return;
        }
        OAWUAData wua = ((SecurityGuardService) Pluto.DEFAULT_INSTANCE.getBean(SecurityGuardService.class)).getWUA();
        if (wua != null && !TextUtils.isEmpty(wua.wua)) {
            map.put("jaqVerificationToken", wua.wua);
            map.put("jaqVerificationEnabled", "true");
        }
        map.put("USE_H5_NC", "true");
        if (ConfigManager.getInstance().isDailyNocaptcha()) {
            map.put("JAQ_CODE", "abc");
        }
    }

    public static Map<String, Object> buildRiskContext() {
        HashMap map = new HashMap();
        map.putAll(getEnvironmentInfo());
        buildJaqRiskContext(map);
        map.putAll(ConfigManager.getInstance().getExtBizMap());
        map.put(DispatchConstants.SIGNTYPE, TextUtils.isEmpty(ConfigManager.getInstance().getAlipaySignType()) ? d.f9568a : ConfigManager.getInstance().getAlipaySignType());
        return map;
    }

    private static int getAppVersionCode() {
        if (appVersionCode < 0) {
            try {
                appVersionCode = OpenAccountSDK.getAndroidContext().getPackageManager().getPackageInfo(OpenAccountSDK.getAndroidContext().getPackageName(), 0).versionCode;
            } catch (Exception e2) {
                AliSDKLogger.e(OpenAccountConstants.LOG_TAG, "fail to getAppVersionCode", e2);
            }
        }
        return appVersionCode;
    }

    public static String getBSSID() {
        WifiInfo connectionInfo;
        WifiManager wifiManager = (WifiManager) OpenAccountSDK.getAndroidContext().getApplicationContext().getSystemService("wifi");
        return (!wifiManager.isWifiEnabled() || (connectionInfo = wifiManager.getConnectionInfo()) == null) ? "" : connectionInfo.getBSSID();
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x00e1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.util.Map<java.lang.String, java.lang.Object> getEnvironmentInfo() {
        /*
            Method dump skipped, instructions count: 410
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.openaccount.util.OpenAccountRiskControlContext.getEnvironmentInfo():java.util.Map");
    }

    public static Location getLastKnownLocation() {
        if (OpenAccountSDK.getAndroidContext() != null && !ConfigManager.getInstance().getBooleanProperty("disableLocationService", false)) {
            try {
                LocationManager locationManager = (LocationManager) OpenAccountSDK.getAndroidContext().getSystemService("location");
                if (locationManager == null) {
                    return null;
                }
                Criteria criteria = new Criteria();
                criteria.setPowerRequirement(1);
                String bestProvider = locationManager.getBestProvider(criteria, true);
                if (bestProvider != null) {
                    return locationManager.getLastKnownLocation(bestProvider);
                }
                AliSDKLogger.e(OpenAccountConstants.LOG_TAG, "Unable to find the best provider, requestSingleLocationUpdate failed");
                return null;
            } catch (Exception e2) {
                AliSDKLogger.e(OpenAccountConstants.LOG_TAG, "Unable to find the best provider, ex = " + e2.getMessage());
            }
        }
        return null;
    }

    public static String getLocale(Context context) {
        Locale locale = Build.VERSION.SDK_INT >= 24 ? context.getResources().getConfiguration().getLocales().get(0) : context.getResources().getConfiguration().locale;
        return locale.getLanguage() + OpenAccountUIConstants.UNDER_LINE + locale.getCountry();
    }

    public static void init(Context context) {
    }
}
