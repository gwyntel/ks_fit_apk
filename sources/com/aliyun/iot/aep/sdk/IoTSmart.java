package com.aliyun.iot.aep.sdk;

import com.aliyun.alink.linksdk.securesigner.ISecuritySource;
import com.aliyun.alink.linksdk.securesigner.SecuritySourceContext;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback;
import com.aliyun.iot.aep.sdk.framework.AApplication;
import com.aliyun.iot.aep.sdk.framework.model.ILopUserAccountInfo;
import java.io.Serializable;
import java.util.List;

/* loaded from: classes3.dex */
public class IoTSmart {
    public static final String PRODUCT_ENV_DEV = "develop";
    public static final String PRODUCT_ENV_PROD = "production";
    public static final String PRODUCT_SCOPE_ALL = "product_scope_all";
    public static final String PRODUCT_SCOPE_PUBLISHED = "product_scope_published";
    public static final int REGION_ALL = 0;
    public static final int REGION_CHINA_ONLY = 1;

    public static class AppKeyConfig implements Serializable {
        public String appKey;
        public String appSecret;
    }

    public static class Country implements Serializable {
        public String areaName;
        public String code;
        public String domainAbbreviation;
        public String isoCode;
        public String pinyin;
    }

    public interface ICountryListGetCallBack {
        void onFail(String str, int i2, String str2);

        void onSucess(List<Country> list);
    }

    public interface ICountrySelectCallBack {
        void onCountrySelect(Country country);
    }

    public interface ICountrySetCallBack {
        void onCountrySet(boolean z2);
    }

    public static class InitConfig implements Serializable {

        /* renamed from: a, reason: collision with root package name */
        private int f11534a = 1;

        /* renamed from: b, reason: collision with root package name */
        private String f11535b;

        /* renamed from: c, reason: collision with root package name */
        private boolean f11536c;

        /* renamed from: d, reason: collision with root package name */
        private PushConfig f11537d;

        /* renamed from: e, reason: collision with root package name */
        private AppKeyConfig f11538e;

        /* renamed from: f, reason: collision with root package name */
        private ISecuritySource f11539f;

        public AppKeyConfig getAppKeyConfig() {
            return this.f11538e;
        }

        public ISecuritySource getCustomSecurity() {
            return this.f11539f;
        }

        public String getProductEnv() {
            return this.f11535b;
        }

        public PushConfig getPushConfig() {
            return this.f11537d;
        }

        public int getRegionType() {
            return this.f11534a;
        }

        public boolean isDebug() {
            return this.f11536c;
        }

        public InitConfig setAppKeyConfig(AppKeyConfig appKeyConfig) {
            this.f11538e = appKeyConfig;
            SecuritySourceContext.getInstance().setAppKey(appKeyConfig.appKey);
            SecuritySourceContext.getInstance().setAppSecretKey(appKeyConfig.appSecret);
            return this;
        }

        public InitConfig setCustomSecurity(ISecuritySource iSecuritySource) {
            this.f11539f = iSecuritySource;
            SecuritySourceContext.getInstance().setISecuritySource(iSecuritySource);
            return this;
        }

        public InitConfig setDebug(boolean z2) {
            this.f11536c = z2;
            return this;
        }

        public InitConfig setProductEnv(String str) {
            this.f11535b = str;
            return this;
        }

        public InitConfig setPushConfig(PushConfig pushConfig) {
            this.f11537d = pushConfig;
            return this;
        }

        public InitConfig setRegionType(int i2) {
            this.f11534a = i2;
            return this;
        }
    }

    public static class PushConfig implements Serializable {
        public String fcmApiKey;
        public String fcmApplicationId;
        public String fcmProjectId;
        public String fcmSendId;
        public String oppoAppKey;
        public String oppoAppSecret;
        public String xiaomiAppId;
        public String xiaomiAppkey;
    }

    public static Country getCountry() {
        return IoTSmartImpl.getInstance().getCountry();
    }

    public static void getCountryList(ICountryListGetCallBack iCountryListGetCallBack) {
        IoTSmartImpl.getInstance().getCountryList(iCountryListGetCallBack);
    }

    public static String getLanguage() {
        return IoTSmartImpl.getInstance().getLanguage();
    }

    public static void getRegion(ILopUserAccountInfo iLopUserAccountInfo, IoTCallback ioTCallback) {
        IoTSmartImpl.getInstance().getRegion(iLopUserAccountInfo, ioTCallback);
    }

    public static String getShortRegionId() {
        return IoTSmartImpl.getInstance().getShortRegionId();
    }

    public static void init(AApplication aApplication) {
        IoTSmartImpl.getInstance().init(aApplication);
    }

    public static void setAuthCode(String str) {
        IoTSmartImpl.getInstance().setAuthCode(str);
    }

    public static void setCountry(Country country, ICountrySetCallBack iCountrySetCallBack) {
        IoTSmartImpl.getInstance().setCountry(country, iCountrySetCallBack);
    }

    public static void setDebug(boolean z2) {
        IoTSmartImpl.getInstance().setDebug(z2);
    }

    public static void setDebugLevel(int i2) {
        IoTSmartImpl.getInstance().setDebugLevel(i2);
    }

    public static void setLanguage(String str) {
        IoTSmartImpl.getInstance().setLanguage(str);
    }

    public static void setProductScope(String str) {
        IoTSmartImpl.getInstance().setProductScope(str);
    }

    public static void showCountryList(ICountrySelectCallBack iCountrySelectCallBack) {
        IoTSmartImpl.getInstance().showCountryList(iCountrySelectCallBack);
    }

    public static void init(AApplication aApplication, InitConfig initConfig) {
        IoTSmartImpl.getInstance().init(aApplication, initConfig);
    }

    public static void setCountry(String str, ICountrySetCallBack iCountrySetCallBack) {
        IoTSmartImpl.getInstance().setCountry(str, iCountrySetCallBack);
    }
}
