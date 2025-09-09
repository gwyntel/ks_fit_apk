package com.huawei.hms.framework.network.grs;

import android.content.Context;
import android.text.TextUtils;
import com.facebook.internal.NativeProtocol;
import com.huawei.hms.framework.common.ContainerUtils;
import com.huawei.hms.framework.common.Logger;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Locale;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/* loaded from: classes4.dex */
public class GrsBaseInfo implements Cloneable {
    private static final String TAG = "GrsBaseInfo";
    private String androidVersion;
    private String appName;
    private String countrySource;
    private String deviceModel;
    private String issueCountry;
    private int queryTimeout = -1;
    private String regCountry;
    private String romVersion;
    private String serCountry;
    private String uid;
    private String versionName;

    public @interface CountryCodeSource {
        public static final String APP = "APP";
        public static final String LOCALE_INFO = "LOCALE_INFO";
        public static final String NETWORK_COUNTRY = "NETWORK_COUNTRY";
        public static final String SIM_COUNTRY = "SIM_COUNTRY";
        public static final String UNKNOWN = "UNKNOWN";
        public static final String VENDOR_COUNTRY = "VENDOR_COUNTRY";
    }

    public GrsBaseInfo() {
    }

    private static String getObjSummary(GrsBaseInfo grsBaseInfo) {
        if (grsBaseInfo == null) {
            return "";
        }
        return "serCountry:" + grsBaseInfo.serCountry + "versionName:" + grsBaseInfo.versionName + "appName:" + grsBaseInfo.appName + "uid:" + grsBaseInfo.uid + "regCountry:" + grsBaseInfo.regCountry + "issueCountry:" + grsBaseInfo.issueCountry + "androidVersion:" + grsBaseInfo.androidVersion + "romVersion:" + grsBaseInfo.romVersion + "deviceModel:" + grsBaseInfo.deviceModel + "countrySource:" + grsBaseInfo.countrySource;
    }

    private StringBuffer getStringBuffer(StringBuffer stringBuffer, boolean z2, Context context) {
        String androidVersion = getAndroidVersion();
        if (!TextUtils.isEmpty(androidVersion)) {
            if (!TextUtils.isEmpty(stringBuffer.toString())) {
                stringBuffer.append("&");
            }
            stringBuffer.append("android_version");
            stringBuffer.append(ContainerUtils.KEY_VALUE_DELIMITER);
            stringBuffer.append(getUrlEncodedStr(androidVersion));
        }
        String romVersion = getRomVersion();
        if (!TextUtils.isEmpty(romVersion)) {
            if (!TextUtils.isEmpty(stringBuffer.toString())) {
                stringBuffer.append("&");
            }
            stringBuffer.append("rom_version");
            stringBuffer.append(ContainerUtils.KEY_VALUE_DELIMITER);
            stringBuffer.append(getUrlEncodedStr(romVersion));
        }
        String deviceModel = getDeviceModel();
        if (!TextUtils.isEmpty(deviceModel)) {
            if (!TextUtils.isEmpty(stringBuffer.toString())) {
                stringBuffer.append("&");
            }
            stringBuffer.append("device_model");
            stringBuffer.append(ContainerUtils.KEY_VALUE_DELIMITER);
            stringBuffer.append(getUrlEncodedStr(deviceModel));
        }
        String countrySource = getCountrySource();
        if (!TextUtils.isEmpty(countrySource)) {
            if (!TextUtils.isEmpty(stringBuffer.toString())) {
                stringBuffer.append("&");
            }
            stringBuffer.append("country_source");
            stringBuffer.append(ContainerUtils.KEY_VALUE_DELIMITER);
            stringBuffer.append(getUrlEncodedStr(countrySource));
        }
        if (!TextUtils.isEmpty(stringBuffer.toString())) {
            stringBuffer.append("&");
        }
        stringBuffer.append("package_name");
        stringBuffer.append(ContainerUtils.KEY_VALUE_DELIMITER);
        stringBuffer.append(getUrlEncodedStr(context.getPackageName()));
        return stringBuffer;
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public GrsBaseInfo m71clone() {
        return (GrsBaseInfo) super.clone();
    }

    public boolean compare(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || GrsBaseInfo.class != obj.getClass()) {
            return false;
        }
        return getObjSummary(this).equals(getObjSummary((GrsBaseInfo) obj));
    }

    public GrsBaseInfo copy() {
        GrsBaseInfo grsBaseInfo = new GrsBaseInfo();
        grsBaseInfo.setAppName(this.appName);
        grsBaseInfo.setSerCountry(this.serCountry);
        grsBaseInfo.setRegCountry(this.regCountry);
        grsBaseInfo.setIssueCountry(this.issueCountry);
        grsBaseInfo.setCountrySource(this.countrySource);
        grsBaseInfo.setAndroidVersion(this.androidVersion);
        grsBaseInfo.setDeviceModel(this.deviceModel);
        grsBaseInfo.setRomVersion(this.romVersion);
        grsBaseInfo.setUid(this.uid);
        grsBaseInfo.setVersionName(this.versionName);
        grsBaseInfo.setQueryTimeout(this.queryTimeout);
        return grsBaseInfo;
    }

    public String getAndroidVersion() {
        return this.androidVersion;
    }

    public String getAppName() {
        return this.appName;
    }

    public String getCountrySource() {
        return this.countrySource;
    }

    public String getDeviceModel() {
        return this.deviceModel;
    }

    public String getGrsParasKey(boolean z2, boolean z3, Context context) {
        StringBuffer stringBuffer = new StringBuffer();
        com.huawei.hms.framework.network.grs.f.b bVarA = com.huawei.hms.framework.network.grs.f.b.a(context.getPackageName());
        com.huawei.hms.framework.network.grs.local.model.a aVarA = bVarA != null ? bVarA.a() : null;
        String grsReqParamJoint = getGrsReqParamJoint(z2, z3, aVarA != null ? aVarA.a() : "", context);
        if (!TextUtils.isEmpty(grsReqParamJoint)) {
            if (!TextUtils.isEmpty(stringBuffer.toString())) {
                stringBuffer.append("&");
            }
            stringBuffer.append(grsReqParamJoint);
        }
        return stringBuffer.toString();
    }

    public String getGrsReqParamJoint(boolean z2, boolean z3, String str, Context context) {
        String strA;
        StringBuffer stringBuffer = new StringBuffer();
        if ("1.0".equals(str)) {
            Logger.v(TAG, "1.0 interface has no query param appname");
        } else {
            if (!TextUtils.isEmpty(str)) {
                stringBuffer.append(NativeProtocol.BRIDGE_ARG_APP_NAME_STRING);
                stringBuffer.append(ContainerUtils.KEY_VALUE_DELIMITER);
            } else if (!TextUtils.isEmpty(getAppName())) {
                stringBuffer.append(NativeProtocol.BRIDGE_ARG_APP_NAME_STRING);
                stringBuffer.append(ContainerUtils.KEY_VALUE_DELIMITER);
                str = getAppName();
            }
            stringBuffer.append(getUrlEncodedStr(str));
        }
        String versionName = getVersionName();
        if (!TextUtils.isEmpty(versionName)) {
            if (!TextUtils.isEmpty(stringBuffer.toString())) {
                stringBuffer.append("&");
            }
            stringBuffer.append("app_version");
            stringBuffer.append(ContainerUtils.KEY_VALUE_DELIMITER);
            stringBuffer.append(getUrlEncodedStr(versionName));
        }
        String uid = getUid();
        if (!TextUtils.isEmpty(uid)) {
            if (!TextUtils.isEmpty(stringBuffer.toString())) {
                stringBuffer.append("&");
            }
            stringBuffer.append("uid");
            stringBuffer.append(ContainerUtils.KEY_VALUE_DELIMITER);
            if (z2) {
                strA = com.huawei.hms.framework.network.grs.h.b.b(uid);
            } else if (z3) {
                strA = com.huawei.hms.framework.network.grs.h.b.a(uid);
            } else {
                stringBuffer.append(uid);
            }
            stringBuffer.append(strA);
        }
        String regCountry = getRegCountry();
        if (!TextUtils.isEmpty(regCountry) && !"UNKNOWN".equals(regCountry)) {
            if (!TextUtils.isEmpty(stringBuffer.toString())) {
                stringBuffer.append("&");
            }
            stringBuffer.append("reg_country");
            stringBuffer.append(ContainerUtils.KEY_VALUE_DELIMITER);
            stringBuffer.append(getUrlEncodedStr(regCountry));
        }
        String serCountry = getSerCountry();
        if (!TextUtils.isEmpty(serCountry) && !"UNKNOWN".equals(serCountry)) {
            if (!TextUtils.isEmpty(stringBuffer.toString())) {
                stringBuffer.append("&");
            }
            stringBuffer.append("ser_country");
            stringBuffer.append(ContainerUtils.KEY_VALUE_DELIMITER);
            stringBuffer.append(getUrlEncodedStr(serCountry));
        }
        String issueCountry = getIssueCountry();
        if (!TextUtils.isEmpty(issueCountry) && !"UNKNOWN".equals(issueCountry)) {
            if (!TextUtils.isEmpty(stringBuffer.toString())) {
                stringBuffer.append("&");
            }
            stringBuffer.append("issue_country");
            stringBuffer.append(ContainerUtils.KEY_VALUE_DELIMITER);
            stringBuffer.append(getUrlEncodedStr(issueCountry));
        }
        return getStringBuffer(stringBuffer, z3, context).toString();
    }

    public String getIssueCountry() {
        return this.issueCountry;
    }

    public int getQueryTimeout() {
        return this.queryTimeout;
    }

    public String getRegCountry() {
        return this.regCountry;
    }

    public String getRomVersion() {
        return this.romVersion;
    }

    public String getSerCountry() {
        return this.serCountry;
    }

    public String getUid() {
        return this.uid;
    }

    public String getUrlEncodedStr(String str) {
        try {
            return URLEncoder.encode(str, "utf-8");
        } catch (UnsupportedEncodingException unused) {
            Logger.e(TAG, "UnsupportedEncodingException, Please check the params!");
            return "";
        }
    }

    public String getVersionName() {
        return this.versionName;
    }

    public void setAndroidVersion(String str) {
        this.androidVersion = str;
    }

    public void setAppName(String str) {
        this.appName = str;
    }

    public void setCountrySource(String str) {
        this.countrySource = str;
    }

    public void setDeviceModel(String str) {
        this.deviceModel = str;
    }

    @Deprecated
    public void setIssueCountry(String str) {
        this.issueCountry = str != null ? str.toUpperCase(Locale.ENGLISH) : "";
    }

    public void setQueryTimeout(int i2) {
        this.queryTimeout = i2;
    }

    public void setRegCountry(String str) {
        this.regCountry = str != null ? str.toUpperCase(Locale.ENGLISH) : "";
    }

    public void setRomVersion(String str) {
        this.romVersion = str;
    }

    public void setSerCountry(String str) {
        this.serCountry = str != null ? str.toUpperCase(Locale.ENGLISH) : "";
    }

    @Deprecated
    public void setUid(String str) {
        this.uid = str;
    }

    public void setVersionName(String str) {
        this.versionName = str;
    }

    public int uniqueCode() {
        return (this.appName + MqttTopic.MULTI_LEVEL_WILDCARD + this.serCountry + MqttTopic.MULTI_LEVEL_WILDCARD + this.regCountry + MqttTopic.MULTI_LEVEL_WILDCARD + this.issueCountry).hashCode();
    }

    public GrsBaseInfo(Context context) {
        this.issueCountry = GrsApp.getInstance().getIssueCountryCode(context);
    }
}
