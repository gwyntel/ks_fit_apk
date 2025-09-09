package com.umeng.analytics.pro;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import androidx.webkit.ProxyConfig;
import anet.channel.strategy.dispatch.DispatchConstants;
import com.alibaba.ailabs.iot.mesh.utils.Utils;
import com.facebook.appevents.UserDataStore;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.umeng.commonsdk.debug.UMRTLog;
import com.umeng.commonsdk.statistics.AnalyticsConstants;
import com.umeng.commonsdk.statistics.common.DeviceConfig;
import com.umeng.commonsdk.utils.UMUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class ao {

    /* renamed from: a, reason: collision with root package name */
    private static JSONObject f21332a;

    public static JSONObject a(Context context, JSONArray jSONArray, String str) {
        JSONObject jSONObject = f21332a;
        if (jSONObject != null && jSONObject.length() > 0) {
            return f21332a;
        }
        try {
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("os", AnalyticsConstants.SDK_TYPE);
            jSONObject2.put("dm", Build.MODEL);
            jSONObject2.put("av", DeviceConfig.getAppVersionName(context));
            jSONObject2.put(bc.f21408g, UMUtils.getUMId(context));
            jSONObject2.put("ov", Build.VERSION.RELEASE);
            jSONObject2.put("chn", UMUtils.getChannel(context));
            jSONObject2.put(bc.al, UMUtils.getZid(context));
            jSONObject2.put(com.alipay.sdk.m.s.a.f9715r, "9.7.4.1+000");
            jSONObject2.put("ak", UMUtils.getAppkey(context));
            String idfa = DeviceConfig.getIdfa(context);
            if (!TextUtils.isEmpty(idfa)) {
                jSONObject2.put("tk_idfa", idfa);
            }
            jSONObject2.put(UserDataStore.DATE_OF_BIRTH, Build.BRAND);
            jSONObject2.put("tk_aid", DeviceConfig.getAndroidId(context));
            String oaid = DeviceConfig.getOaid(context);
            if (!TextUtils.isEmpty(oaid)) {
                jSONObject2.put("tk_oaid", oaid);
            }
            String imeiNew = DeviceConfig.getImeiNew(context);
            if (!TextUtils.isEmpty(imeiNew)) {
                jSONObject2.put("tk_imei", imeiNew);
            }
            jSONObject2.put("boa", Build.BOARD);
            jSONObject2.put("mant", Build.TIME);
            String[] localeInfo = DeviceConfig.getLocaleInfo(context);
            jSONObject2.put("ct", localeInfo[0]);
            jSONObject2.put("lang", localeInfo[1]);
            jSONObject2.put("tz", DeviceConfig.getTimeZone(context));
            jSONObject2.put("pkg", DeviceConfig.getPackageName(context));
            jSONObject2.put("disn", DeviceConfig.getAppName(context));
            String[] networkAccessMode = DeviceConfig.getNetworkAccessMode(context);
            if ("Wi-Fi".equals(networkAccessMode[0])) {
                jSONObject2.put("ac", "wifi");
            } else if ("2G/3G".equals(networkAccessMode[0])) {
                jSONObject2.put("ac", "2G/3G");
            } else {
                jSONObject2.put("ac", "unknown");
            }
            if (!"".equals(networkAccessMode[1])) {
                jSONObject2.put("ast", networkAccessMode[1]);
            }
            jSONObject2.put("nt", DeviceConfig.getNetworkType(context));
            String deviceToken = UMUtils.getDeviceToken(context);
            if (!TextUtils.isEmpty(deviceToken)) {
                jSONObject2.put(bc.f21402a, deviceToken);
            }
            int[] resolutionArray = DeviceConfig.getResolutionArray(context);
            if (resolutionArray != null) {
                jSONObject2.put("rl", resolutionArray[1] + ProxyConfig.MATCH_ALL_SCHEMES + resolutionArray[0]);
            }
            jSONObject2.put("car", DeviceConfig.getNetworkOperatorName(context));
            jSONObject2.put(bc.f21403b, "9.7.4.1+000");
            if (DeviceConfig.isHarmony(context)) {
                jSONObject2.put("oos", Utils.HARMONY_OS);
            } else {
                jSONObject2.put("oos", AnalyticsConstants.SDK_TYPE);
            }
            jSONObject2.put(com.umeng.ccg.a.f22016r, str);
            jSONObject2.put("sdk", jSONArray);
            f21332a = jSONObject2;
        } catch (Throwable unused) {
        }
        return f21332a;
    }

    public static JSONObject a(Context context, JSONObject jSONObject) {
        JSONArray jSONArray;
        JSONObject jSONObject2;
        JSONObject jSONObject3 = null;
        try {
            jSONArray = new JSONArray();
            jSONArray.put(jSONObject);
            jSONObject2 = new JSONObject();
        } catch (Throwable unused) {
        }
        try {
            jSONObject2.put("ekv", jSONArray);
            return jSONObject2;
        } catch (Throwable unused2) {
            jSONObject3 = jSONObject2;
            return jSONObject3;
        }
    }

    public static JSONObject a(JSONObject jSONObject, JSONObject jSONObject2) {
        JSONObject jSONObject3 = new JSONObject();
        try {
            jSONObject3.put("header", jSONObject);
            jSONObject3.put("analytics", jSONObject2);
        } catch (Throwable unused) {
        }
        return jSONObject3;
    }

    public static JSONObject a(Context context, String str) {
        JSONObject jSONObject = null;
        try {
            an anVar = new an();
            String uMId = UMUtils.getUMId(context);
            if (TextUtils.isEmpty(uMId)) {
                return null;
            }
            anVar.a(uMId);
            String appkey = UMUtils.getAppkey(context);
            if (TextUtils.isEmpty(appkey)) {
                return null;
            }
            anVar.b(appkey);
            anVar.c(UMUtils.getAppVersionName(context));
            anVar.d("9.7.4.1+000");
            anVar.e(UMUtils.getChannel(context));
            anVar.f(Build.VERSION.SDK_INT + "");
            anVar.g(Build.BRAND);
            anVar.h(Build.MODEL);
            String[] localeInfo = DeviceConfig.getLocaleInfo(context);
            anVar.i(localeInfo[1]);
            anVar.j(localeInfo[0]);
            int[] resolutionArray = DeviceConfig.getResolutionArray(context);
            anVar.b(Integer.valueOf(resolutionArray[1]));
            anVar.a(Integer.valueOf(resolutionArray[0]));
            anVar.k(as.a(context, "install_datetime", ""));
            try {
                JSONObject jSONObject2 = new JSONObject();
                try {
                    jSONObject2.put(an.f21306a, anVar.a());
                    jSONObject2.put(an.f21308c, anVar.c());
                    jSONObject2.put(an.f21307b, anVar.b());
                    jSONObject2.put(an.f21309d, anVar.d());
                    jSONObject2.put(an.f21310e, anVar.e());
                    jSONObject2.put(an.f21311f, anVar.f());
                    jSONObject2.put(an.f21312g, anVar.g());
                    jSONObject2.put(an.f21313h, anVar.h());
                    jSONObject2.put(an.f21316k, anVar.k());
                    jSONObject2.put(an.f21315j, anVar.j());
                    jSONObject2.put(an.f21317l, anVar.l());
                    jSONObject2.put(an.f21314i, anVar.i());
                    jSONObject2.put(an.f21318m, anVar.m());
                    jSONObject2.put(bc.al, UMUtils.getZid(context));
                    jSONObject2.put(DispatchConstants.PLATFORM, "android");
                    jSONObject2.put("optional", new JSONObject(as.a()));
                    String[] strArrSplit = str.split("@");
                    if (strArrSplit.length == 4) {
                        try {
                            long j2 = Long.parseLong(strArrSplit[0]);
                            String str2 = strArrSplit[1];
                            jSONObject2.put("s1", j2);
                            jSONObject2.put("s2", str2);
                        } catch (Throwable unused) {
                        }
                    }
                    try {
                        String str3 = Build.BRAND;
                        String strA = at.a(str3);
                        String strB = at.b(str3);
                        if (!TextUtils.isEmpty(strA) && !TextUtils.isEmpty(strB)) {
                            jSONObject2.put(an.f21319n, strA);
                            jSONObject2.put(an.f21320o, strB);
                        } else {
                            jSONObject2.put(an.f21319n, AnalyticsConstants.SDK_TYPE);
                            jSONObject2.put(an.f21320o, Build.VERSION.RELEASE);
                        }
                    } catch (Throwable unused2) {
                    }
                    return jSONObject2;
                } catch (JSONException e2) {
                    e = e2;
                    jSONObject = jSONObject2;
                    UMRTLog.e(UMRTLog.RTLOG_TAG, "[getCloudConfigParam] error " + e.getMessage());
                    return jSONObject;
                } catch (Throwable th) {
                    th = th;
                    jSONObject = jSONObject2;
                    UMRTLog.e(UMRTLog.RTLOG_TAG, "[getCloudConfigParam] error " + th.getMessage());
                    return jSONObject;
                }
            } catch (JSONException e3) {
                e = e3;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static JSONObject a(Context context, int i2, JSONArray jSONArray, String str) {
        JSONObject jSONObject;
        JSONObject jSONObject2 = null;
        try {
            jSONObject = new JSONObject();
        } catch (Throwable unused) {
        }
        try {
            String zid = UMUtils.getZid(context);
            if (TextUtils.isEmpty(zid)) {
                return jSONObject;
            }
            jSONObject.put("atoken", zid);
            String deviceToken = UMUtils.getDeviceToken(context);
            if (!TextUtils.isEmpty(deviceToken)) {
                jSONObject.put(RemoteMessageConst.DEVICE_TOKEN, deviceToken);
            }
            jSONObject.put("model", Build.MODEL);
            jSONObject.put("os", "android");
            jSONObject.put(bc.f21426y, Build.VERSION.RELEASE);
            jSONObject.put(com.umeng.ccg.a.f22016r, str);
            jSONObject.put("sdk", jSONArray);
            jSONObject.put("e", i2);
            return jSONObject;
        } catch (Throwable unused2) {
            jSONObject2 = jSONObject;
            return jSONObject2;
        }
    }
}
