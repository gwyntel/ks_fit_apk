package com.umeng.commonsdk.statistics;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import androidx.webkit.ProxyConfig;
import com.alibaba.ailabs.iot.mesh.utils.Utils;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConstants;
import com.aliyun.alink.business.devicecenter.config.genie.smartconfig.constants.DeviceCommonConstants;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.pro.at;
import com.umeng.analytics.pro.ba;
import com.umeng.analytics.pro.bc;
import com.umeng.analytics.pro.bz;
import com.umeng.analytics.pro.f;
import com.umeng.commonsdk.config.FieldManager;
import com.umeng.commonsdk.framework.UMEnvelopeBuild;
import com.umeng.commonsdk.framework.UMFrUtils;
import com.umeng.commonsdk.internal.crash.UMCrashManager;
import com.umeng.commonsdk.statistics.common.DataHelper;
import com.umeng.commonsdk.statistics.common.DeviceConfig;
import com.umeng.commonsdk.statistics.common.ULog;
import com.umeng.commonsdk.statistics.idtracking.Envelope;
import com.umeng.commonsdk.statistics.idtracking.ImprintHandler;
import com.umeng.commonsdk.statistics.idtracking.e;
import com.umeng.commonsdk.statistics.internal.PreferenceWrapper;
import com.umeng.commonsdk.utils.UMUtils;
import com.umeng.commonsdk.utils.d;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    public static String f22322a = null;

    /* renamed from: b, reason: collision with root package name */
    public static String f22323b = "";

    /* renamed from: c, reason: collision with root package name */
    private static final String f22324c = "EnvelopeManager";

    /* renamed from: d, reason: collision with root package name */
    private static final String f22325d = "debug.umeng.umTaskId";

    /* renamed from: e, reason: collision with root package name */
    private static final String f22326e = "debug.umeng.umCaseId";

    /* renamed from: f, reason: collision with root package name */
    private static final String f22327f = "empty";

    /* renamed from: g, reason: collision with root package name */
    private static String f22328g = "";

    /* renamed from: h, reason: collision with root package name */
    private static String f22329h = "";

    /* renamed from: i, reason: collision with root package name */
    private static String f22330i;

    /* renamed from: j, reason: collision with root package name */
    private static Map<String, String> f22331j;

    /* renamed from: l, reason: collision with root package name */
    private static boolean f22332l;

    /* renamed from: k, reason: collision with root package name */
    private int f22333k = 0;

    static {
        HashMap map = new HashMap();
        f22331j = map;
        map.put("header", "#h");
        f22331j.put("sdk_type", "#sdt");
        f22331j.put(bc.Q, "#ac");
        f22331j.put("device_model", "#dm");
        f22331j.put(bc.f21408g, "#umid");
        f22331j.put("os", "os");
        f22331j.put(bc.N, "#lang");
        f22331j.put(bc.ai, "#dt");
        f22331j.put("resolution", "#rl");
        f22331j.put(bc.H, "#dmf");
        f22331j.put("device_name", "#dn");
        f22331j.put("platform_version", "#pv");
        f22331j.put("font_size_setting", "#fss");
        f22331j.put(bc.f21426y, "#ov");
        f22331j.put(bc.I, "#did");
        f22331j.put("platform_sdk_version", "#psv");
        f22331j.put(bc.F, "#db");
        f22331j.put("appkey", "#ak");
        f22331j.put(bc.Y, "#itr");
        f22331j.put("id_type", "#it");
        f22331j.put(DeviceCommonConstants.KEY_DEVICE_ID, "#ud");
        f22331j.put("device_id", "#dd");
        f22331j.put(bc.X, "#imp");
        f22331j.put("sdk_version", "#sv");
        f22331j.put("st", "#st");
        f22331j.put("analytics", "#a");
        f22331j.put("package_name", "#pkg");
        f22331j.put(bc.f21417p, "#sig");
        f22331j.put(bc.f21418q, "#sis1");
        f22331j.put(bc.f21419r, "#sis");
        f22331j.put("app_version", "#av");
        f22331j.put("version_code", "#vc");
        f22331j.put(bc.f21423v, "#imd");
        f22331j.put(bc.B, "#mnc");
        f22331j.put(bc.E, "#boa");
        f22331j.put(bc.G, "#mant");
        f22331j.put(bc.M, "#tz");
        f22331j.put("country", "#ct");
        f22331j.put("carrier", "#car");
        f22331j.put(bc.f21420s, "#disn");
        f22331j.put(bc.T, "#nt");
        f22331j.put(bc.f21403b, "#cv");
        f22331j.put(bc.f21405d, "#mv");
        f22331j.put(bc.f21404c, "#cot");
        f22331j.put("module", "#mod");
        f22331j.put(bc.aj, "#al");
        f22331j.put("session_id", "#sid");
        f22331j.put(bc.S, "#ip");
        f22331j.put(bc.U, "#sre");
        f22331j.put(bc.V, "#fre");
        f22331j.put(bc.W, "#ret");
        f22331j.put("channel", "#chn");
        f22331j.put("wrapper_type", "#wt");
        f22331j.put("wrapper_version", "#wv");
        f22331j.put(bc.bb, "#tsv");
        f22331j.put(bc.bc, "#rps");
        f22331j.put(bc.bh, "#mov");
        f22331j.put(f.f21687i, "#vt");
        f22331j.put("secret", "#sec");
        f22331j.put(f.an, "#prv");
        f22331j.put(f.f21690l, "#$prv");
        f22331j.put(f.f21691m, "#uda");
        f22331j.put(bc.f21402a, "#tok");
        f22331j.put(bc.aT, "#iv");
        f22331j.put(bc.R, "#ast");
        f22331j.put("backstate", "#bst");
        f22331j.put("zdata_ver", "#zv");
        f22331j.put("zdata_req_ts", "#zrt");
        f22331j.put("app_b_v", "#bv");
        f22331j.put("zdata", "#zta");
        f22331j.put(bc.ap, "#mt");
        f22331j.put(bc.am, "#zsv");
        f22331j.put(bc.ao, "#oos");
    }

    public static String a(String str) {
        return f22331j.containsKey(str) ? f22331j.get(str) : str;
    }

    private static boolean b() {
        f22328g = UMUtils.getSystemProperty(f22325d, "");
        f22329h = UMUtils.getSystemProperty(f22326e, "");
        return (!TextUtils.isEmpty(f22328g) && !f22327f.equals(f22328g)) && (!TextUtils.isEmpty(f22329h) && !f22327f.equals(f22329h));
    }

    public static void a() {
        if (f22330i != null) {
            f22330i = null;
            e.a();
        }
    }

    public JSONObject b(Context context, JSONObject jSONObject, JSONObject jSONObject2, String str) {
        Envelope envelopeA;
        try {
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put(a("header"), new JSONObject());
            try {
                if (b()) {
                    jSONObject.put("umTaskId", f22328g);
                    jSONObject.put("umCaseId", f22329h);
                }
            } catch (Throwable unused) {
            }
            if (jSONObject != null) {
                jSONObject3 = a(jSONObject3, jSONObject);
            }
            if (jSONObject3 != null && jSONObject2 != null) {
                Iterator<String> itKeys = jSONObject2.keys();
                while (itKeys.hasNext()) {
                    String next = itKeys.next();
                    if (next != null && (next instanceof String)) {
                        String str2 = next;
                        if (jSONObject2.opt(str2) != null) {
                            try {
                                jSONObject3.put(str2, jSONObject2.opt(str2));
                            } catch (Exception unused2) {
                            }
                        }
                    }
                }
            }
            if (jSONObject3 != null && DataHelper.largeThanMaxSize(jSONObject3.toString().getBytes().length, DataHelper.ENVELOPE_ENTITY_RAW_LENGTH_MAX)) {
                SharedPreferences sharedPreferences = PreferenceWrapper.getDefault(context);
                if (sharedPreferences != null) {
                    sharedPreferences.edit().putInt("serial", sharedPreferences.getInt("serial", 1) + 1).commit();
                }
                return a(113, jSONObject3);
            }
            if (jSONObject3 != null) {
                envelopeA = a(context, jSONObject3.toString().getBytes());
                if (envelopeA == null) {
                    return a(111, jSONObject3);
                }
            } else {
                envelopeA = null;
            }
            Envelope envelope = envelopeA;
            if (envelope != null && DataHelper.largeThanMaxSize(envelope.toBinary().length, DataHelper.ENVELOPE_LENGTH_MAX)) {
                return a(114, jSONObject3);
            }
            int iA = a(context, envelope, "z==1.2.0", DeviceConfig.getAppVersionName(context), str);
            if (iA != 0) {
                return a(iA, jSONObject3);
            }
            if (ULog.DEBUG) {
                Log.i(f22324c, "constructHeader size is " + jSONObject3.toString().getBytes().length);
            }
            return jSONObject3;
        } catch (Throwable th) {
            UMCrashManager.reportCrash(context, th);
            return a(110, new JSONObject());
        }
    }

    public static long a(Context context) {
        long j2 = DataHelper.ENVELOPE_ENTITY_RAW_LENGTH_MAX - DataHelper.ENVELOPE_EXTRA_LENGTH;
        if (ULog.DEBUG) {
            Log.i(f22324c, "free size is " + j2);
        }
        return j2;
    }

    private JSONObject a(int i2, JSONObject jSONObject) throws JSONException {
        if (jSONObject != null) {
            try {
                jSONObject.put("exception", i2);
            } catch (Exception unused) {
            }
            return jSONObject;
        }
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject2.put("exception", i2);
        } catch (Exception unused2) {
        }
        return jSONObject2;
    }

    public JSONObject a(Context context, JSONObject jSONObject, JSONObject jSONObject2, String str, String str2, String str3) throws JSONException {
        JSONObject jSONObject3;
        String str4;
        boolean z2;
        String str5;
        Envelope envelope;
        JSONObject jSONObjectOptJSONObject;
        if (ULog.DEBUG && jSONObject != null && jSONObject2 != null) {
            Log.i(f22324c, "headerJSONObject size is " + jSONObject.toString().getBytes().length);
            Log.i(f22324c, "bodyJSONObject size is " + jSONObject2.toString().getBytes().length);
        }
        if (context != null && jSONObject2 != null) {
            try {
                if (jSONObject2.has("analytics") && (jSONObjectOptJSONObject = jSONObject2.optJSONObject("analytics")) != null && jSONObjectOptJSONObject.has(f.f21692n)) {
                    str4 = str2;
                    z2 = true;
                } else {
                    str4 = str2;
                    z2 = false;
                }
                JSONObject jSONObjectA = a(context, str4, z2);
                if (jSONObjectA != null && jSONObject != null) {
                    jSONObjectA = a(jSONObjectA, jSONObject);
                }
                JSONObject jSONObject4 = jSONObjectA;
                if (jSONObject4 != null) {
                    Iterator<String> itKeys = jSONObject2.keys();
                    while (itKeys.hasNext()) {
                        String next = itKeys.next();
                        if (next != null && (next instanceof String)) {
                            String str6 = next;
                            if (jSONObject2.opt(str6) != null) {
                                try {
                                    jSONObject4.put(a(str6), jSONObject2.opt(str6));
                                } catch (Exception unused) {
                                }
                            }
                        }
                    }
                }
                if (TextUtils.isEmpty(str2)) {
                    str4 = bc.aN;
                }
                String str7 = TextUtils.isEmpty(str3) ? "1.0.0" : str3;
                if (jSONObject4 != null) {
                    String strSubstring = str4 + "==" + str7 + "&=";
                    if (TextUtils.isEmpty(strSubstring)) {
                        return a(101, jSONObject4);
                    }
                    if (strSubstring.endsWith("&=")) {
                        strSubstring = strSubstring.substring(0, strSubstring.length() - 2);
                    }
                    str5 = strSubstring;
                } else {
                    str5 = null;
                }
                if (jSONObject4 != null) {
                    try {
                        e eVarA = e.a(context);
                        if (eVarA != null) {
                            eVarA.b();
                            String strEncodeToString = Base64.encodeToString(new bz().a(eVarA.c()), 0);
                            if (!TextUtils.isEmpty(strEncodeToString)) {
                                JSONObject jSONObject5 = jSONObject4.getJSONObject(a("header"));
                                jSONObject5.put(a(bc.Y), strEncodeToString);
                                jSONObject4.put(a("header"), jSONObject5);
                            }
                        }
                    } catch (Exception unused2) {
                    }
                }
                if (jSONObject4 != null && DataHelper.largeThanMaxSize(jSONObject4.toString().getBytes().length, DataHelper.ENVELOPE_ENTITY_RAW_LENGTH_MAX)) {
                    SharedPreferences sharedPreferences = PreferenceWrapper.getDefault(context);
                    if (sharedPreferences != null) {
                        sharedPreferences.edit().putInt("serial", sharedPreferences.getInt("serial", 1) + 1).commit();
                    }
                    return a(113, jSONObject4);
                }
                if (jSONObject4 != null) {
                    Envelope envelopeA = a(context, jSONObject4.toString().getBytes());
                    if (envelopeA == null) {
                        return a(111, jSONObject4);
                    }
                    envelope = envelopeA;
                } else {
                    envelope = null;
                }
                if (envelope != null && DataHelper.largeThanMaxSize(envelope.toBinary().length, DataHelper.ENVELOPE_LENGTH_MAX)) {
                    return a(114, jSONObject4);
                }
                int iA = a(context, envelope, str5, jSONObject4 != null ? jSONObject4.optJSONObject(a("header")).optString(a("app_version")) : null, str);
                if (iA != 0) {
                    return a(iA, jSONObject4);
                }
                if (ULog.DEBUG) {
                    Log.i(f22324c, "constructHeader size is " + jSONObject4.toString().getBytes().length);
                }
                if (!str5.startsWith(bc.aJ) && !str5.startsWith("i") && !str5.startsWith("t") && !str5.startsWith("a") && !com.umeng.commonsdk.stateless.b.a()) {
                    new com.umeng.commonsdk.stateless.b(context);
                    com.umeng.commonsdk.stateless.b.b();
                }
                return jSONObject4;
            } catch (Throwable th) {
                UMCrashManager.reportCrash(context, th);
                if (jSONObject != null) {
                    try {
                        JSONObject jSONObject6 = new JSONObject();
                        try {
                            jSONObject6.put("header", jSONObject);
                        } catch (JSONException unused3) {
                        } catch (Exception e2) {
                            e = e2;
                            jSONObject3 = jSONObject6;
                            UMCrashManager.reportCrash(context, e);
                            return a(110, jSONObject3);
                        }
                        jSONObject3 = jSONObject6;
                    } catch (Exception e3) {
                        e = e3;
                        jSONObject3 = null;
                    }
                } else {
                    jSONObject3 = null;
                }
                if (jSONObject3 == null) {
                    try {
                        jSONObject3 = new JSONObject();
                    } catch (Exception e4) {
                        e = e4;
                        UMCrashManager.reportCrash(context, e);
                        return a(110, jSONObject3);
                    }
                }
                Iterator<String> itKeys2 = jSONObject2.keys();
                while (itKeys2.hasNext()) {
                    String next2 = itKeys2.next();
                    if (next2 != null && (next2 instanceof String)) {
                        String str8 = next2;
                        if (jSONObject2.opt(str8) != null) {
                            try {
                                jSONObject3.put(str8, jSONObject2.opt(str8));
                            } catch (Exception unused4) {
                            }
                        }
                    }
                }
                return a(110, jSONObject3);
            }
        }
        return a(110, (JSONObject) null);
    }

    private static int[] b(Context context) {
        int[] iArr = new int[3];
        try {
            SharedPreferences sharedPreferences = context.getApplicationContext().getSharedPreferences(com.umeng.commonsdk.internal.c.f22266a, 0);
            if (sharedPreferences != null) {
                iArr[0] = sharedPreferences.getInt(com.umeng.commonsdk.internal.c.f22267b, 0);
                iArr[1] = sharedPreferences.getInt(com.umeng.commonsdk.internal.c.f22268c, 0);
                iArr[2] = sharedPreferences.getInt("policyGrantResult", 0);
            }
        } catch (Throwable unused) {
        }
        return iArr;
    }

    public JSONObject a(Context context, JSONObject jSONObject, JSONObject jSONObject2, String str) {
        Envelope envelopeA;
        try {
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put(a("header"), new JSONObject());
            if (jSONObject != null) {
                jSONObject3 = a(jSONObject3, jSONObject);
            }
            if (jSONObject3 != null && jSONObject2 != null) {
                Iterator<String> itKeys = jSONObject2.keys();
                while (itKeys.hasNext()) {
                    String next = itKeys.next();
                    if (next != null && (next instanceof String)) {
                        String str2 = next;
                        if (jSONObject2.opt(str2) != null) {
                            try {
                                jSONObject3.put(str2, jSONObject2.opt(str2));
                            } catch (Exception unused) {
                            }
                        }
                    }
                }
            }
            if (jSONObject3 != null && DataHelper.largeThanMaxSize(jSONObject3.toString().getBytes().length, DataHelper.ENVELOPE_ENTITY_RAW_LENGTH_MAX)) {
                SharedPreferences sharedPreferences = PreferenceWrapper.getDefault(context);
                if (sharedPreferences != null) {
                    sharedPreferences.edit().putInt("serial", sharedPreferences.getInt("serial", 1) + 1).commit();
                }
                return a(113, jSONObject3);
            }
            if (jSONObject3 != null) {
                envelopeA = a(context, jSONObject3.toString().getBytes());
                if (envelopeA == null) {
                    return a(111, jSONObject3);
                }
            } else {
                envelopeA = null;
            }
            Envelope envelope = envelopeA;
            if (envelope != null && DataHelper.largeThanMaxSize(envelope.toBinary().length, DataHelper.ENVELOPE_LENGTH_MAX)) {
                return a(114, jSONObject3);
            }
            int iA = a(context, envelope, "h==1.2.0", "", str);
            if (iA != 0) {
                return a(iA, jSONObject3);
            }
            if (ULog.DEBUG) {
                Log.i(f22324c, "constructHeader size is " + jSONObject3.toString().getBytes().length);
            }
            return jSONObject3;
        } catch (Throwable th) {
            UMCrashManager.reportCrash(context, th);
            return a(110, new JSONObject());
        }
    }

    private static JSONObject a(Context context, String str, boolean z2) {
        SharedPreferences sharedPreferences;
        JSONObject jSONObject;
        try {
            SharedPreferences sharedPreferences2 = PreferenceWrapper.getDefault(context);
            if (!TextUtils.isEmpty(f22330i)) {
                try {
                    jSONObject = new JSONObject(f22330i);
                    sharedPreferences = sharedPreferences2;
                } catch (Exception unused) {
                    sharedPreferences = sharedPreferences2;
                    jSONObject = null;
                }
            } else {
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put(a(bc.f21417p), DeviceConfig.getAppMD5Signature(context));
                jSONObject2.put(a(bc.f21418q), DeviceConfig.getAppSHA1Key(context));
                jSONObject2.put(a(bc.f21419r), DeviceConfig.getAppHashKey(context));
                jSONObject2.put(a("app_version"), DeviceConfig.getAppVersionName(context));
                jSONObject2.put(a("version_code"), Integer.parseInt(DeviceConfig.getAppVersionCode(context)));
                jSONObject2.put(a(bc.f21423v), DeviceConfig.getDeviceIdUmengMD5(context));
                jSONObject2.put(a(bc.f21424w), DeviceConfig.getCPU());
                String mccmnc = DeviceConfig.getMCCMNC(context);
                if (!TextUtils.isEmpty(mccmnc)) {
                    jSONObject2.put(a(bc.B), mccmnc);
                    f22323b = mccmnc;
                } else {
                    jSONObject2.put(a(bc.B), "");
                }
                if (FieldManager.allow(d.I)) {
                    String subOSName = DeviceConfig.getSubOSName(context);
                    if (!TextUtils.isEmpty(subOSName)) {
                        jSONObject2.put(a(bc.K), subOSName);
                    }
                    String subOSVersion = DeviceConfig.getSubOSVersion(context);
                    if (!TextUtils.isEmpty(subOSVersion)) {
                        jSONObject2.put(a(bc.L), subOSVersion);
                    }
                }
                String deviceType = DeviceConfig.getDeviceType(context);
                if (!TextUtils.isEmpty(deviceType)) {
                    jSONObject2.put(a(bc.ai), deviceType);
                }
                jSONObject2.put(a("package_name"), DeviceConfig.getPackageName(context));
                jSONObject2.put(a("sdk_type"), AnalyticsConstants.SDK_TYPE);
                jSONObject2.put(a("device_id"), DeviceConfig.getDeviceId(context));
                jSONObject2.put(a("device_model"), Build.MODEL);
                jSONObject2.put(a(bc.E), Build.BOARD);
                jSONObject2.put(a(bc.F), Build.BRAND);
                sharedPreferences = sharedPreferences2;
                jSONObject2.put(a(bc.G), Build.TIME);
                jSONObject2.put(a(bc.H), Build.MANUFACTURER);
                jSONObject2.put(a(bc.I), Build.ID);
                jSONObject2.put(a("device_name"), Build.DEVICE);
                jSONObject2.put(a(bc.f21426y), Build.VERSION.RELEASE);
                jSONObject2.put(a("os"), AnalyticsConstants.SDK_TYPE);
                int[] resolutionArray = DeviceConfig.getResolutionArray(context);
                if (resolutionArray != null) {
                    jSONObject2.put(a("resolution"), resolutionArray[1] + ProxyConfig.MATCH_ALL_SCHEMES + resolutionArray[0]);
                }
                jSONObject2.put(a(bc.A), DeviceConfig.getMac(context));
                jSONObject2.put(a(bc.M), DeviceConfig.getTimeZone(context));
                String[] localeInfo = DeviceConfig.getLocaleInfo(context);
                jSONObject2.put(a("country"), localeInfo[0]);
                jSONObject2.put(a(bc.N), localeInfo[1]);
                jSONObject2.put(a("carrier"), DeviceConfig.getNetworkOperatorName(context));
                jSONObject2.put(a(bc.f21420s), DeviceConfig.getAppName(context));
                String[] networkAccessMode = DeviceConfig.getNetworkAccessMode(context);
                if ("Wi-Fi".equals(networkAccessMode[0])) {
                    jSONObject2.put(a(bc.Q), "wifi");
                } else if ("2G/3G".equals(networkAccessMode[0])) {
                    jSONObject2.put(a(bc.Q), "2G/3G");
                } else {
                    jSONObject2.put(a(bc.Q), "unknow");
                }
                if (!"".equals(networkAccessMode[1])) {
                    jSONObject2.put(a(bc.R), networkAccessMode[1]);
                }
                if (DeviceConfig.isHarmony(context)) {
                    jSONObject2.put(a(bc.ao), Utils.HARMONY_OS);
                } else {
                    jSONObject2.put(a(bc.ao), AnalyticsConstants.SDK_TYPE);
                }
                jSONObject2.put(a(bc.T), DeviceConfig.getNetworkType(context));
                jSONObject2.put(a(bc.f21403b), "9.7.4.1+000");
                jSONObject2.put(a(bc.f21404c), SdkVersion.SDK_TYPE);
                jSONObject2.put(a(bc.f21405d), "1");
                if (!TextUtils.isEmpty(f22322a)) {
                    jSONObject2.put(a("module"), f22322a);
                }
                jSONObject2.put(a(bc.aj), Build.VERSION.SDK_INT);
                if (!TextUtils.isEmpty(UMUtils.VALUE_REC_VERSION_NAME)) {
                    jSONObject2.put(a(bc.af), UMUtils.VALUE_REC_VERSION_NAME);
                }
                try {
                    String uUIDForZid = UMUtils.getUUIDForZid(context);
                    if (TextUtils.isEmpty(uUIDForZid)) {
                        UMUtils.setUUIDForZid(context);
                        uUIDForZid = UMUtils.getUUIDForZid(context);
                    }
                    jSONObject2.put(a("session_id"), uUIDForZid);
                } catch (Throwable unused2) {
                }
                if (DeviceConfig.isHonorDevice()) {
                    try {
                        if (ba.c()) {
                            jSONObject2.put(bc.at, 2);
                        }
                        if (ba.b()) {
                            jSONObject2.put(bc.at, 3);
                        }
                    } catch (Throwable unused3) {
                    }
                }
                try {
                    jSONObject2.put(bc.au, DeviceConfig.getNotificationStatus(context));
                } catch (Throwable unused4) {
                }
                try {
                    jSONObject2.put(bc.av, DeviceConfig.getRingerMode(context));
                } catch (Throwable unused5) {
                }
                f22330i = jSONObject2.toString();
                jSONObject = jSONObject2;
            }
            if (jSONObject == null) {
                return null;
            }
            try {
                jSONObject.put(a(bc.ak), UMUtils.getOaidRequiredTime(context));
            } catch (Exception unused6) {
            }
            try {
                SharedPreferences sharedPreferences3 = sharedPreferences;
                jSONObject.put(a(bc.U), sharedPreferences3.getInt("successful_request", 0));
                jSONObject.put(a(bc.V), sharedPreferences3.getInt(bc.V, 0));
                jSONObject.put(a(bc.W), sharedPreferences3.getInt("last_request_spent_ms", 0));
                String zid = UMUtils.getZid(context);
                if (!TextUtils.isEmpty(zid)) {
                    jSONObject.put(a(bc.al), zid);
                }
                if (!TextUtils.isEmpty(UMUtils.VALUE_ASMS_VERSION)) {
                    jSONObject.put(a(bc.am), UMUtils.VALUE_ASMS_VERSION);
                }
            } catch (Exception unused7) {
            }
            jSONObject.put(a("channel"), UMUtils.getChannel(context));
            jSONObject.put(a("appkey"), UMUtils.getAppkey(context));
            try {
                String deviceToken = UMUtils.getDeviceToken(context);
                if (!TextUtils.isEmpty(deviceToken)) {
                    jSONObject.put(a(bc.f21402a), deviceToken);
                }
            } catch (Exception e2) {
                UMCrashManager.reportCrash(context, e2);
            }
            try {
                String strImprintProperty = UMEnvelopeBuild.imprintProperty(context, bc.f21408g, null);
                if (!TextUtils.isEmpty(strImprintProperty)) {
                    jSONObject.put(a(bc.f21408g), strImprintProperty);
                }
            } catch (Exception e3) {
                UMCrashManager.reportCrash(context, e3);
            }
            try {
                jSONObject.put(a("wrapper_type"), a.f22319a);
                jSONObject.put(a("wrapper_version"), a.f22320b);
            } catch (Exception unused8) {
            }
            try {
                int targetSdkVersion = UMUtils.getTargetSdkVersion(context);
                boolean zCheckPermission = UMUtils.checkPermission(context, "android.permission.READ_PHONE_STATE");
                jSONObject.put(a(bc.bb), targetSdkVersion);
                if (zCheckPermission) {
                    jSONObject.put(a(bc.bc), "yes");
                } else {
                    jSONObject.put(a(bc.bc), "no");
                }
            } catch (Throwable unused9) {
            }
            try {
                if (b()) {
                    jSONObject.put("umTaskId", f22328g);
                    jSONObject.put("umCaseId", f22329h);
                }
            } catch (Throwable unused10) {
            }
            if (("t".equals(str) || "a".equals(str)) && z2) {
                try {
                    int[] iArrB = b(context);
                    jSONObject.put(a(bc.by), String.valueOf(iArrB[0]) + String.valueOf(iArrB[1]) + String.valueOf(iArrB[2]));
                } catch (Throwable unused11) {
                }
            }
            try {
                Map<String, String> moduleTags = TagHelper.getModuleTags();
                if (moduleTags != null && moduleTags.size() > 0) {
                    JSONObject jSONObject3 = new JSONObject();
                    for (Map.Entry<String, String> entry : moduleTags.entrySet()) {
                        jSONObject3.put(entry.getKey(), entry.getValue());
                    }
                    jSONObject.put(a(bc.ap), jSONObject3);
                }
            } catch (Throwable unused12) {
            }
            try {
                String realTimeDebugKey = AnalyticsConfig.getRealTimeDebugKey();
                if (!TextUtils.isEmpty(realTimeDebugKey)) {
                    jSONObject.put(a(bc.bx), realTimeDebugKey);
                }
            } catch (Throwable unused13) {
            }
            try {
                JSONObject moduleVer = UMUtils.getModuleVer();
                if (moduleVer.length() > 0) {
                    jSONObject.put(a(bc.bh), moduleVer);
                }
            } catch (Throwable unused14) {
            }
            try {
                String apmFlag = UMUtils.getApmFlag();
                if (!TextUtils.isEmpty(apmFlag)) {
                    jSONObject.put(a(bc.bw), apmFlag);
                }
            } catch (Throwable unused15) {
            }
            try {
                String str2 = Build.BRAND;
                String strA = at.a(str2);
                String strB = at.b(str2);
                jSONObject.put(bc.bf, strA);
                jSONObject.put(bc.bg, strB);
            } catch (Throwable unused16) {
            }
            byte[] bArrA = ImprintHandler.getImprintService(context).a();
            if (bArrA != null && bArrA.length > 0) {
                try {
                    jSONObject.put(a(bc.X), Base64.encodeToString(bArrA, 0));
                } catch (JSONException e4) {
                    UMCrashManager.reportCrash(context, e4);
                }
            }
            if (jSONObject.length() > 0) {
                return new JSONObject().put(a("header"), jSONObject);
            }
            return null;
        } catch (Throwable th) {
            UMCrashManager.reportCrash(context, th);
            return null;
        }
    }

    private JSONObject a(JSONObject jSONObject, JSONObject jSONObject2) throws JSONException {
        if (jSONObject != null && jSONObject2 != null && jSONObject.opt(a("header")) != null && (jSONObject.opt(a("header")) instanceof JSONObject)) {
            JSONObject jSONObject3 = (JSONObject) jSONObject.opt(a("header"));
            Iterator<String> itKeys = jSONObject2.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                if (next != null && (next instanceof String)) {
                    String str = next;
                    if (jSONObject2.opt(str) != null) {
                        try {
                            jSONObject3.put(str, jSONObject2.opt(str));
                            if (str.equals(a(f.f21687i)) && (jSONObject2.opt(str) instanceof Integer)) {
                                this.f22333k = ((Integer) jSONObject2.opt(str)).intValue();
                            }
                        } catch (Exception unused) {
                        }
                    }
                }
            }
        }
        return jSONObject;
    }

    private Envelope a(Context context, byte[] bArr) {
        String strImprintProperty = UMEnvelopeBuild.imprintProperty(context, "codex", null);
        int iIntValue = -1;
        try {
            if (!TextUtils.isEmpty(strImprintProperty)) {
                iIntValue = Integer.valueOf(strImprintProperty).intValue();
            }
        } catch (NumberFormatException e2) {
            UMCrashManager.reportCrash(context, e2);
        }
        if (iIntValue == 0) {
            return Envelope.genEnvelope(context, UMUtils.getAppkey(context), bArr);
        }
        if (iIntValue == 1) {
            return Envelope.genEncryptEnvelope(context, UMUtils.getAppkey(context), bArr);
        }
        if (f22332l) {
            return Envelope.genEncryptEnvelope(context, UMUtils.getAppkey(context), bArr);
        }
        return Envelope.genEnvelope(context, UMUtils.getAppkey(context), bArr);
    }

    private int a(Context context, Envelope envelope, String str, String str2, String str3) {
        if (context == null || envelope == null || TextUtils.isEmpty(str)) {
            return 101;
        }
        if (TextUtils.isEmpty(str2)) {
            str2 = DeviceConfig.getAppVersionName(context);
        }
        String strB = com.umeng.commonsdk.stateless.d.b(str3);
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("&&");
        sb.append(str2);
        sb.append(OpenAccountUIConstants.UNDER_LINE);
        sb.append(System.currentTimeMillis());
        sb.append(OpenAccountUIConstants.UNDER_LINE);
        sb.append(strB);
        sb.append(".log");
        byte[] binary = envelope.toBinary();
        if (com.umeng.commonsdk.utils.c.a()) {
            if (str.startsWith("h")) {
                return UMFrUtils.saveEnvelopeFile(context, sb.toString(), binary);
            }
            return 122;
        }
        if (str.startsWith("h")) {
            return 122;
        }
        if (!str.startsWith(bc.aJ) && !str.startsWith("i") && !str.startsWith("a") && !str.startsWith("t")) {
            return com.umeng.commonsdk.stateless.d.a(context, com.umeng.commonsdk.stateless.a.f22295f, sb.toString(), binary);
        }
        return UMFrUtils.saveEnvelopeFile(context, sb.toString(), binary);
    }

    public static void a(boolean z2) {
        f22332l = z2;
    }
}
