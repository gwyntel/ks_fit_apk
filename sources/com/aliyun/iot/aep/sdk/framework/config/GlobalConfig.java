package com.aliyun.iot.aep.sdk.framework.config;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.aliyun.iot.aep.sdk.IoTSmart;
import com.aliyun.iot.aep.sdk.framework.AApplication;
import com.aliyun.iot.aep.sdk.framework.language.LanguageManager;
import com.aliyun.iot.aep.sdk.framework.region.CountryManager;
import com.aliyun.iot.aep.sdk.framework.region.RegionInfo;
import com.aliyun.iot.aep.sdk.framework.region.RegionInfo2;
import com.aliyun.iot.aep.sdk.framework.region.RegionManager;
import com.aliyun.iot.aep.sdk.framework.utils.SpUtil;
import com.aliyun.iot.aep.sdk.log.ALog;
import java.io.Serializable;
import java.util.HashMap;

/* loaded from: classes3.dex */
public class GlobalConfig {
    public static final String API_ENV_ONLINE = "release";
    public static final String API_ENV_PRE = "pre";
    public static final String API_ENV_TEST = "test";
    public static final String BONE_ENV_PRETEST = "pretest";
    public static final String BONE_ENV_RELEASE = "release";
    public static final String BONE_ENV_TEST = "test";

    /* renamed from: a, reason: collision with root package name */
    private String f11763a;

    /* renamed from: b, reason: collision with root package name */
    private String f11764b;

    /* renamed from: c, reason: collision with root package name */
    private String f11765c;

    /* renamed from: d, reason: collision with root package name */
    private IoTSmart.InitConfig f11766d;

    /* renamed from: e, reason: collision with root package name */
    private RegionInfo2 f11767e;

    /* renamed from: f, reason: collision with root package name */
    private IoTSmart.Country f11768f;

    /* renamed from: g, reason: collision with root package name */
    private String f11769g;

    /* renamed from: h, reason: collision with root package name */
    private String f11770h;

    /* renamed from: i, reason: collision with root package name */
    private Boolean f11771i;

    /* renamed from: j, reason: collision with root package name */
    private HashMap<String, String> f11772j;

    /* renamed from: k, reason: collision with root package name */
    private IoTSmart.ICountrySelectCallBack f11773k;

    static class a {

        /* renamed from: a, reason: collision with root package name */
        @SuppressLint({"StaticFieldLeak"})
        private static final GlobalConfig f11774a = new GlobalConfig();
    }

    private boolean a(IoTSmart.Country country, IoTSmart.Country country2) {
        return false;
    }

    public static String getCentralHost() {
        return "test".equals(getInstance().f11763a) ? "api-performance.aliplus.com" : "api.link.aliyun.com";
    }

    public static GlobalConfig getInstance() {
        return a.f11774a;
    }

    public String getApiEnv() {
        return this.f11763a;
    }

    public String getAuthCode() {
        if (!TextUtils.isEmpty(this.f11770h)) {
            return this.f11770h;
        }
        if (TextUtils.isEmpty(this.f11763a) || this.f11766d == null) {
            return "";
        }
        if (!"test".equals(this.f11763a)) {
            return "china_production";
        }
        return "test_" + this.f11766d.getProductEnv();
    }

    public String getBoneEnv() {
        return this.f11764b;
    }

    public HashMap<String, String> getConfig() {
        return this.f11772j;
    }

    public IoTSmart.Country getCountry() {
        return this.f11768f;
    }

    public IoTSmart.ICountrySelectCallBack getCountrySelectCallBack() {
        return this.f11773k;
    }

    public Boolean getEnableSecurityGuard() {
        return this.f11771i;
    }

    public IoTSmart.InitConfig getInitConfig() {
        return this.f11766d;
    }

    public String getLanguage() {
        if (TextUtils.isEmpty(this.f11769g)) {
            this.f11769g = SpUtil.getString(AApplication.getInstance(), "key_language_global");
        }
        if (TextUtils.isEmpty(this.f11769g)) {
            setLanguage(LanguageManager.getDefaultLanguage());
        }
        return this.f11769g;
    }

    public String getProductScope() {
        return this.f11765c;
    }

    public RegionInfo2 getRegionInfo() {
        return this.f11767e;
    }

    public void reStoreConfigs() {
        setInitConfig((IoTSmart.InitConfig) a(AApplication.getInstance(), "key_init_config_global", IoTSmart.InitConfig.class));
        setRegionInfo(a(AApplication.getInstance(), "key_region_info_global"));
        setCountry((IoTSmart.Country) a(AApplication.getInstance(), "key_country_global", IoTSmart.Country.class), null);
        setConfig((HashMap) SpUtil.getMap(AApplication.getInstance(), "key_config_global"));
        setLanguage(SpUtil.getString(AApplication.getInstance(), "key_language_global"));
        setApiEnv(SpUtil.getString(AApplication.getInstance(), "key_api_env_global"));
        setProductScope(SpUtil.getString(AApplication.getInstance(), "key_product_scope_global"));
        ALog.d("GlobalConfig", "reStoreConfigs:" + toString());
    }

    public void setApiEnv(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.f11763a = str;
        SpUtil.putString(AApplication.getInstance(), "key_api_env_global", str);
    }

    public void setAuthCode(String str) {
        this.f11770h = str;
    }

    public void setBoneEnv(String str) {
        this.f11764b = str;
    }

    public void setConfig(HashMap<String, String> map) {
        this.f11772j = map;
        storeConfig();
    }

    public void setCountry(IoTSmart.Country country, IoTSmart.ICountrySetCallBack iCountrySetCallBack) {
        boolean zA = a(this.f11768f, country);
        this.f11768f = country;
        storeCountry();
        if (zA) {
            if (CountryManager.isChina(country)) {
                RegionManager.setRegionChina(null);
            } else {
                RegionManager.setRegionSingapore(null);
            }
        }
        if (iCountrySetCallBack != null) {
            iCountrySetCallBack.onCountrySet(zA);
        }
    }

    public void setCountrySelectCallBack(IoTSmart.ICountrySelectCallBack iCountrySelectCallBack) {
        this.f11773k = iCountrySelectCallBack;
    }

    public void setEnableSecurityGuard(Boolean bool) {
        this.f11771i = bool;
    }

    public void setInitConfig(IoTSmart.InitConfig initConfig) {
        this.f11766d = initConfig;
        storeInitConfig();
    }

    public void setLanguage(String str) {
        if (TextUtils.equals(this.f11769g, str)) {
            return;
        }
        this.f11769g = str;
        SpUtil.putString(AApplication.getInstance(), "key_language_global", str);
    }

    public void setProductScope(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.f11765c = str;
        SpUtil.putString(AApplication.getInstance(), "key_product_scope_global", str);
    }

    public void setRegionInfo(RegionInfo2 regionInfo2) {
        this.f11767e = regionInfo2;
        storeRegionInfo();
    }

    public void storeConfig() {
        SpUtil.putMap(AApplication.getInstance(), "key_config_global", getConfig());
    }

    public void storeConfigs() {
        storeConfig();
        storeCountry();
        storeInitConfig();
        storeRegionInfo();
    }

    public void storeCountry() {
        SpUtil.putObject(AApplication.getInstance(), "key_country_global", getCountry());
    }

    public void storeInitConfig() {
        SpUtil.putObject(AApplication.getInstance(), "key_init_config_global", getInitConfig());
        ALog.d("GlobalConfig", "storeInitConfig:" + toString());
    }

    public void storeRegionInfo() {
        SpUtil.putObject(AApplication.getInstance(), "key_region_info_global", getRegionInfo());
    }

    public String toString() {
        return "initConfig:" + JSON.toJSONString(this.f11766d) + "\nregionInfo:" + JSON.toJSONString(this.f11767e) + "\ncountry:" + JSON.toJSONString(this.f11768f) + "\nlanguage:" + this.f11769g + "\napiEnv:" + this.f11763a + "\nboneEnv:" + this.f11764b + "\nsecurityIndex(authCode):" + getAuthCode() + "\nproductScope:" + getProductScope() + "\nconfig:" + JSON.toJSONString(this.f11772j) + "\n";
    }

    private GlobalConfig() {
        this.f11763a = "release";
        this.f11764b = "release";
        this.f11765c = IoTSmart.PRODUCT_SCOPE_PUBLISHED;
        this.f11771i = Boolean.FALSE;
        this.f11773k = null;
        reStoreConfigs();
        if (this.f11766d == null) {
            this.f11766d = new IoTSmart.InitConfig();
        }
        if (this.f11767e == null) {
            this.f11767e = new RegionInfo2();
        }
        if (this.f11772j == null) {
            this.f11772j = new HashMap<>();
        }
    }

    private <T extends Serializable> T a(Context context, String str, Class<T> cls) {
        T t2 = (T) SpUtil.getObject(AApplication.getInstance(), str, cls);
        if (t2 != null) {
            return t2;
        }
        T t3 = (T) SpUtil.getObject(context, str);
        ALog.d("GlobalConfig", "getObjectOld2:" + JSON.toJSONString(t3));
        return t3;
    }

    private RegionInfo2 a(Context context, String str) {
        RegionInfo regionInfo;
        RegionInfo2 regionInfo2 = (RegionInfo2) SpUtil.getObject(AApplication.getInstance(), str, RegionInfo2.class);
        if (regionInfo2 != null || (regionInfo = (RegionInfo) SpUtil.getObject(AApplication.getInstance(), str)) == null) {
            return regionInfo2;
        }
        RegionInfo2 regionInfo22 = new RegionInfo2();
        regionInfo22.apiGatewayEndpoint = regionInfo.apiGatewayEndpoint;
        regionInfo22.mqttEndpoint = regionInfo.mqttEndpoint;
        regionInfo22.oaApiGatewayEndpoint = regionInfo.oaApiGatewayEndpoint;
        regionInfo22.pushChannelEndpoint = regionInfo.pushChannelEndpoint;
        regionInfo22.regionEnglishName = regionInfo.regionEnglishName;
        regionInfo22.regionId = regionInfo.regionId;
        ALog.d("GlobalConfig", "getObjectOld:" + JSON.toJSONString(regionInfo));
        SpUtil.putObject(AApplication.getInstance(), str, regionInfo22);
        return regionInfo22;
    }
}
