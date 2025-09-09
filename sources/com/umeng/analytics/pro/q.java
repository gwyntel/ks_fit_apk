package com.umeng.analytics.pro;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.CoreProtocol;
import com.umeng.analytics.process.UMProcessDBHelper;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.config.FieldManager;
import com.umeng.commonsdk.debug.UMLog;
import com.umeng.commonsdk.debug.UMRTLog;
import com.umeng.commonsdk.framework.UMEnvelopeBuild;
import com.umeng.commonsdk.framework.UMFrUtils;
import com.umeng.commonsdk.framework.UMLogDataProtocol;
import com.umeng.commonsdk.framework.UMWorkDispatch;
import com.umeng.commonsdk.service.UMGlobalContext;
import com.umeng.commonsdk.statistics.common.DeviceConfig;
import com.umeng.commonsdk.statistics.common.HelperUtils;
import com.umeng.commonsdk.statistics.common.MLog;
import com.umeng.commonsdk.statistics.common.ReportPolicy;
import com.umeng.commonsdk.statistics.internal.PreferenceWrapper;
import com.umeng.commonsdk.statistics.internal.StatTracer;
import com.umeng.commonsdk.statistics.noise.ABTest;
import com.umeng.commonsdk.statistics.noise.Defcon;
import com.umeng.commonsdk.utils.JSONArraySortUtil;
import com.umeng.commonsdk.utils.UMUtils;
import com.yc.utesdk.utils.open.CalendarUtils;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class q {

    /* renamed from: a, reason: collision with root package name */
    private static Context f21853a = null;

    /* renamed from: l, reason: collision with root package name */
    private static final String f21854l = "first_activate_time";

    /* renamed from: m, reason: collision with root package name */
    private static final String f21855m = "ana_is_f";

    /* renamed from: n, reason: collision with root package name */
    private static final String f21856n = "thtstart";

    /* renamed from: o, reason: collision with root package name */
    private static final String f21857o = "dstk_last_time";

    /* renamed from: p, reason: collision with root package name */
    private static final String f21858p = "dstk_cnt";

    /* renamed from: q, reason: collision with root package name */
    private static final String f21859q = "gkvc";

    /* renamed from: r, reason: collision with root package name */
    private static final String f21860r = "ekvc";

    /* renamed from: t, reason: collision with root package name */
    private static final String f21861t = "-1";

    /* renamed from: x, reason: collision with root package name */
    private static final String f21862x = "com.umeng.umcrash.UMCrashUtils";

    /* renamed from: y, reason: collision with root package name */
    private static Class<?> f21863y;

    /* renamed from: z, reason: collision with root package name */
    private static Method f21864z;

    /* renamed from: b, reason: collision with root package name */
    private c f21865b;

    /* renamed from: c, reason: collision with root package name */
    private SharedPreferences f21866c;

    /* renamed from: d, reason: collision with root package name */
    private String f21867d;

    /* renamed from: e, reason: collision with root package name */
    private String f21868e;

    /* renamed from: f, reason: collision with root package name */
    private int f21869f;

    /* renamed from: g, reason: collision with root package name */
    private JSONArray f21870g;

    /* renamed from: h, reason: collision with root package name */
    private final int f21871h;

    /* renamed from: i, reason: collision with root package name */
    private int f21872i;

    /* renamed from: j, reason: collision with root package name */
    private int f21873j;

    /* renamed from: k, reason: collision with root package name */
    private long f21874k;

    /* renamed from: s, reason: collision with root package name */
    private final long f21875s;

    /* renamed from: u, reason: collision with root package name */
    private boolean f21876u;

    /* renamed from: v, reason: collision with root package name */
    private boolean f21877v;

    /* renamed from: w, reason: collision with root package name */
    private Object f21878w;

    public static class a {
        public static final int A = 8209;
        public static final int B = 8210;
        public static final int C = 8211;
        public static final int D = 8212;
        public static final int E = 8213;
        public static final int F = 8214;
        public static final int G = 8215;

        /* renamed from: a, reason: collision with root package name */
        public static final int f21879a = 4097;

        /* renamed from: b, reason: collision with root package name */
        public static final int f21880b = 4098;

        /* renamed from: c, reason: collision with root package name */
        public static final int f21881c = 4099;

        /* renamed from: d, reason: collision with root package name */
        public static final int f21882d = 4100;

        /* renamed from: e, reason: collision with root package name */
        public static final int f21883e = 4101;

        /* renamed from: f, reason: collision with root package name */
        public static final int f21884f = 4102;

        /* renamed from: g, reason: collision with root package name */
        public static final int f21885g = 4103;

        /* renamed from: h, reason: collision with root package name */
        public static final int f21886h = 4104;

        /* renamed from: i, reason: collision with root package name */
        public static final int f21887i = 4105;

        /* renamed from: j, reason: collision with root package name */
        public static final int f21888j = 4106;

        /* renamed from: k, reason: collision with root package name */
        public static final int f21889k = 4352;

        /* renamed from: l, reason: collision with root package name */
        public static final int f21890l = 4353;

        /* renamed from: m, reason: collision with root package name */
        public static final int f21891m = 4354;

        /* renamed from: n, reason: collision with root package name */
        public static final int f21892n = 4355;

        /* renamed from: o, reason: collision with root package name */
        public static final int f21893o = 4356;

        /* renamed from: p, reason: collision with root package name */
        public static final int f21894p = 4357;

        /* renamed from: q, reason: collision with root package name */
        public static final int f21895q = 4358;

        /* renamed from: r, reason: collision with root package name */
        public static final int f21896r = 8193;

        /* renamed from: s, reason: collision with root package name */
        public static final int f21897s = 8194;

        /* renamed from: t, reason: collision with root package name */
        public static final int f21898t = 8195;

        /* renamed from: u, reason: collision with root package name */
        public static final int f21899u = 8196;

        /* renamed from: v, reason: collision with root package name */
        public static final int f21900v = 8197;

        /* renamed from: w, reason: collision with root package name */
        public static final int f21901w = 8199;

        /* renamed from: x, reason: collision with root package name */
        public static final int f21902x = 8200;

        /* renamed from: y, reason: collision with root package name */
        public static final int f21903y = 8201;

        /* renamed from: z, reason: collision with root package name */
        public static final int f21904z = 8208;
    }

    private static class b {

        /* renamed from: a, reason: collision with root package name */
        private static final q f21905a = new q();

        private b() {
        }
    }

    static {
        h();
    }

    public static q a(Context context) {
        if (f21853a == null && context != null) {
            f21853a = context.getApplicationContext();
        }
        return b.f21905a;
    }

    private void e(Object obj) {
        try {
            JSONObject jSONObject = (JSONObject) obj;
            if (2050 == jSONObject.getInt("__t")) {
                if (!a(this.f21874k, this.f21872i)) {
                    return;
                } else {
                    this.f21872i++;
                }
            } else if (2049 == jSONObject.getInt("__t")) {
                if (!a(this.f21874k, this.f21873j)) {
                    return;
                } else {
                    this.f21873j++;
                }
            }
            if (AnalyticsConfig.isRealTimeDebugMode()) {
                if (this.f21870g == null) {
                    this.f21870g = new JSONArray();
                }
                this.f21870g.put(jSONObject);
                k.a(f21853a).a(this.f21870g);
                this.f21870g = new JSONArray();
                return;
            }
            if (this.f21870g.length() >= this.f21869f) {
                UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>>*** 超过10个事件，事件落库。");
                k.a(f21853a).a(this.f21870g);
                this.f21870g = new JSONArray();
            }
            if (this.f21874k == 0) {
                this.f21874k = System.currentTimeMillis();
            }
            this.f21870g.put(jSONObject);
        } catch (Throwable th) {
            MLog.e(th);
        }
    }

    private void f(JSONObject jSONObject) throws JSONException {
        JSONObject jSONObjectOptJSONObject;
        JSONObject jSONObjectOptJSONObject2;
        try {
            if (!jSONObject.getJSONObject(com.umeng.commonsdk.statistics.b.a("header")).has(f.aH)) {
                if (jSONObject.has("content")) {
                    jSONObject = jSONObject.getJSONObject("content");
                }
                if (jSONObject.has(com.umeng.commonsdk.statistics.b.a("analytics")) && (jSONObjectOptJSONObject = jSONObject.optJSONObject(com.umeng.commonsdk.statistics.b.a("analytics"))) != null && jSONObjectOptJSONObject.length() > 0 && jSONObjectOptJSONObject.has(f.f21692n)) {
                    k.a(f21853a).a(true, false);
                }
                k.a(f21853a).b();
                return;
            }
            if (jSONObject.has("content")) {
                jSONObject = jSONObject.getJSONObject("content");
            }
            if (jSONObject.has(com.umeng.commonsdk.statistics.b.a("analytics"))) {
                JSONObject jSONObject2 = jSONObject.getJSONObject(com.umeng.commonsdk.statistics.b.a("analytics"));
                if (jSONObject2.has(f.f21692n) && (jSONObjectOptJSONObject2 = jSONObject2.getJSONArray(f.f21692n).optJSONObject(0)) != null) {
                    String strOptString = jSONObjectOptJSONObject2.optString("id");
                    if (!TextUtils.isEmpty(strOptString)) {
                        UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> removeAllInstantData: really delete instant session data");
                        k.a(f21853a).b(strOptString);
                    }
                }
            }
            k.a(f21853a).b();
            UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> removeAllInstantData: send INSTANT_SESSION_START_CONTINUE event because OVERSIZE.");
            Context context = f21853a;
            UMWorkDispatch.sendEvent(context, a.f21890l, CoreProtocol.getInstance(context), null);
        } catch (Exception unused) {
        }
    }

    private static void h() {
        try {
            Class<?> cls = Class.forName(f21862x);
            f21863y = cls;
            Method declaredMethod = cls.getDeclaredMethod("setPuidAndProvider", String.class, String.class);
            if (declaredMethod != null) {
                f21864z = declaredMethod;
            }
        } catch (Throwable unused) {
        }
    }

    private void i() {
        JSONObject jSONObjectB = b(UMEnvelopeBuild.maxDataSpace(f21853a));
        if (jSONObjectB == null || jSONObjectB.length() < 1) {
            return;
        }
        JSONObject jSONObject = (JSONObject) jSONObjectB.opt("header");
        JSONObject jSONObject2 = (JSONObject) jSONObjectB.opt("content");
        if (f21853a == null || jSONObject == null || jSONObject2 == null) {
            return;
        }
        UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> constructInstantMessage: request build envelope.");
        JSONObject jSONObjectBuildEnvelopeWithExtHeader = UMEnvelopeBuild.buildEnvelopeWithExtHeader(f21853a, jSONObject, jSONObject2);
        if (jSONObjectBuildEnvelopeWithExtHeader != null) {
            try {
                if (jSONObjectBuildEnvelopeWithExtHeader.has("exception")) {
                    UMRTLog.i(UMRTLog.RTLOG_TAG, "Build envelope error code: " + jSONObjectBuildEnvelopeWithExtHeader.getInt("exception"));
                }
            } catch (Throwable unused) {
            }
            if (UMConfigure.isDebugLog()) {
                e(jSONObjectBuildEnvelopeWithExtHeader);
            }
            b((Object) jSONObjectBuildEnvelopeWithExtHeader);
        }
    }

    private void j() {
        JSONObject jSONObjectBuildEnvelopeWithExtHeader;
        JSONObject jSONObjectA = a(UMEnvelopeBuild.maxDataSpace(f21853a));
        if (jSONObjectA == null || jSONObjectA.length() < 1) {
            return;
        }
        JSONObject jSONObject = (JSONObject) jSONObjectA.opt("header");
        JSONObject jSONObject2 = (JSONObject) jSONObjectA.opt("content");
        Context context = f21853a;
        if (context == null || jSONObject == null || jSONObject2 == null || (jSONObjectBuildEnvelopeWithExtHeader = UMEnvelopeBuild.buildEnvelopeWithExtHeader(context, jSONObject, jSONObject2)) == null) {
            return;
        }
        try {
            if (jSONObjectBuildEnvelopeWithExtHeader.has("exception")) {
                UMRTLog.i(UMRTLog.RTLOG_TAG, "Build envelope error code: " + jSONObjectBuildEnvelopeWithExtHeader.getInt("exception"));
            }
        } catch (Throwable unused) {
        }
        if (UMConfigure.isDebugLog()) {
            d(jSONObjectBuildEnvelopeWithExtHeader);
        }
        a((Object) jSONObjectBuildEnvelopeWithExtHeader);
    }

    private JSONObject k() {
        JSONObject jSONObjectL = l();
        if (jSONObjectL != null) {
            try {
                jSONObjectL.put("st", "1");
            } catch (Throwable unused) {
            }
        }
        return jSONObjectL;
    }

    private JSONObject l() {
        JSONObject jSONObject = new JSONObject();
        try {
            if (AnalyticsConfig.mWrapperType != null && AnalyticsConfig.mWrapperVersion != null) {
                jSONObject.put(com.umeng.commonsdk.statistics.b.a("wrapper_version"), AnalyticsConfig.mWrapperVersion);
                jSONObject.put(com.umeng.commonsdk.statistics.b.a("wrapper_type"), AnalyticsConfig.mWrapperType);
            }
            int verticalType = AnalyticsConfig.getVerticalType(f21853a);
            jSONObject.put(com.umeng.commonsdk.statistics.b.a(f.f21687i), verticalType);
            String str = "9.7.4.1+000";
            if (verticalType == 1) {
                String gameSdkVersion = AnalyticsConfig.getGameSdkVersion(f21853a);
                if (!TextUtils.isEmpty(gameSdkVersion)) {
                    str = gameSdkVersion;
                }
                jSONObject.put(com.umeng.commonsdk.statistics.b.a("sdk_version"), str);
            } else {
                jSONObject.put(com.umeng.commonsdk.statistics.b.a("sdk_version"), "9.7.4.1+000");
            }
            String strMD5 = HelperUtils.MD5(AnalyticsConfig.getSecretKey(f21853a));
            if (!TextUtils.isEmpty(strMD5)) {
                jSONObject.put(com.umeng.commonsdk.statistics.b.a("secret"), strMD5);
            }
            String strImprintProperty = UMEnvelopeBuild.imprintProperty(f21853a, "pr_ve", null);
            SharedPreferences sharedPreferences = PreferenceWrapper.getDefault(f21853a);
            String strImprintProperty2 = UMEnvelopeBuild.imprintProperty(f21853a, f.at, "");
            if (!TextUtils.isEmpty(strImprintProperty2)) {
                if (AnalyticsConfig.CLEAR_EKV_BL) {
                    jSONObject.put(com.umeng.commonsdk.statistics.b.a(f.av), "");
                } else {
                    jSONObject.put(com.umeng.commonsdk.statistics.b.a(f.av), strImprintProperty2);
                }
            }
            String strImprintProperty3 = UMEnvelopeBuild.imprintProperty(f21853a, f.au, "");
            if (!TextUtils.isEmpty(strImprintProperty3)) {
                if (AnalyticsConfig.CLEAR_EKV_WL) {
                    jSONObject.put(com.umeng.commonsdk.statistics.b.a(f.aw), "");
                } else {
                    jSONObject.put(com.umeng.commonsdk.statistics.b.a(f.aw), strImprintProperty3);
                }
            }
            jSONObject.put(com.umeng.commonsdk.statistics.b.a(f.an), "1.0.0");
            if (s()) {
                jSONObject.put(com.umeng.commonsdk.statistics.b.a(f.ap), "1");
                if (sharedPreferences != null) {
                    sharedPreferences.edit().putLong(f21855m, 0L).commit();
                }
            }
            jSONObject.put(com.umeng.commonsdk.statistics.b.a(f.f21690l), m());
            jSONObject.put(com.umeng.commonsdk.statistics.b.a(f.f21691m), n());
            if (sharedPreferences != null) {
                String string = sharedPreferences.getString("vers_name", "");
                if (!TextUtils.isEmpty(string)) {
                    String str2 = new SimpleDateFormat(CalendarUtils.yyyy_MM_dd, Locale.getDefault()).format(new Date(System.currentTimeMillis()));
                    if (TextUtils.isEmpty(strImprintProperty)) {
                        jSONObject.put(com.umeng.commonsdk.statistics.b.a(f.f21690l), sharedPreferences.getString("vers_pre_version", "0"));
                        jSONObject.put(com.umeng.commonsdk.statistics.b.a(f.f21691m), sharedPreferences.getString("vers_date", str2));
                    }
                    sharedPreferences.edit().putString("pre_version", string).putString("cur_version", DeviceConfig.getAppVersionName(f21853a)).putString("pre_date", str2).remove("vers_name").remove("vers_code").remove("vers_date").remove("vers_pre_version").commit();
                }
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return jSONObject;
    }

    private String m() {
        String string = "0";
        String strImprintProperty = null;
        try {
            strImprintProperty = UMEnvelopeBuild.imprintProperty(f21853a, "pr_ve", null);
        } catch (Throwable unused) {
        }
        if (!TextUtils.isEmpty(strImprintProperty)) {
            string = strImprintProperty;
        } else {
            if (!TextUtils.isEmpty(this.f21867d)) {
                return this.f21867d;
            }
            if (this.f21866c == null) {
                this.f21866c = PreferenceWrapper.getDefault(f21853a);
            }
            String string2 = this.f21866c.getString("pre_version", "");
            String appVersionName = DeviceConfig.getAppVersionName(f21853a);
            if (TextUtils.isEmpty(string2)) {
                this.f21866c.edit().putString("pre_version", "0").putString("cur_version", appVersionName).commit();
            } else {
                string = this.f21866c.getString("cur_version", "");
                if (appVersionName.equals(string)) {
                    string = string2;
                } else {
                    this.f21866c.edit().putString("pre_version", string).putString("cur_version", appVersionName).commit();
                }
            }
        }
        this.f21867d = string;
        return string;
    }

    private String n() {
        String strImprintProperty = null;
        try {
            strImprintProperty = UMEnvelopeBuild.imprintProperty(f21853a, "ud_da", null);
            if (TextUtils.isEmpty(strImprintProperty)) {
                if (!TextUtils.isEmpty(this.f21868e)) {
                    return this.f21868e;
                }
                if (this.f21866c == null) {
                    this.f21866c = PreferenceWrapper.getDefault(f21853a);
                }
                String string = this.f21866c.getString("pre_date", "");
                if (TextUtils.isEmpty(string)) {
                    string = new SimpleDateFormat(CalendarUtils.yyyy_MM_dd, Locale.getDefault()).format(new Date(System.currentTimeMillis()));
                    this.f21866c.edit().putString("pre_date", string).commit();
                } else {
                    String str = new SimpleDateFormat(CalendarUtils.yyyy_MM_dd, Locale.getDefault()).format(new Date(System.currentTimeMillis()));
                    if (!string.equals(str)) {
                        this.f21866c.edit().putString("pre_date", str).commit();
                        strImprintProperty = str;
                    }
                }
                strImprintProperty = string;
            }
        } catch (Throwable unused) {
        }
        this.f21868e = strImprintProperty;
        return strImprintProperty;
    }

    private void o() {
        try {
            this.f21872i = 0;
            this.f21873j = 0;
            this.f21874k = System.currentTimeMillis();
            PreferenceWrapper.getDefault(f21853a).edit().putLong(f21857o, System.currentTimeMillis()).putInt(f21858p, 0).commit();
        } catch (Throwable unused) {
        }
    }

    private boolean p() {
        try {
            if (!TextUtils.isEmpty(w.a().b())) {
                b(f21853a);
            }
            if (this.f21870g.length() <= 0) {
                return false;
            }
            for (int i2 = 0; i2 < this.f21870g.length(); i2++) {
                JSONObject jSONObjectOptJSONObject = this.f21870g.optJSONObject(i2);
                if (jSONObjectOptJSONObject != null && jSONObjectOptJSONObject.length() > 0) {
                    String strOptString = jSONObjectOptJSONObject.optString("__i");
                    if (TextUtils.isEmpty(strOptString) || f21861t.equals(strOptString)) {
                        return false;
                    }
                }
            }
            return true;
        } catch (Throwable unused) {
            return true;
        }
    }

    private void q() {
        if (this.f21870g.length() > 0) {
            JSONArray jSONArray = new JSONArray();
            for (int i2 = 0; i2 < this.f21870g.length(); i2++) {
                try {
                    JSONObject jSONObject = this.f21870g.getJSONObject(i2);
                    if (jSONObject == null || jSONObject.length() <= 0) {
                        jSONArray.put(jSONObject);
                    } else {
                        String strOptString = jSONObject.optString("__i");
                        boolean zIsEmpty = TextUtils.isEmpty(strOptString);
                        String str = f21861t;
                        if (zIsEmpty || f21861t.equals(strOptString)) {
                            String strB = w.a().b();
                            if (!TextUtils.isEmpty(strB)) {
                                str = strB;
                            }
                            jSONObject.put("__i", str);
                        }
                        jSONArray.put(jSONObject);
                    }
                } catch (Throwable unused) {
                }
            }
            this.f21870g = jSONArray;
        }
    }

    private void r() {
        Context context;
        SharedPreferences sharedPreferences;
        try {
            if (!s() || (context = f21853a) == null || (sharedPreferences = PreferenceWrapper.getDefault(context)) == null || sharedPreferences.getLong(f21854l, 0L) != 0) {
                return;
            }
            sharedPreferences.edit().putLong(f21854l, System.currentTimeMillis()).commit();
        } catch (Throwable unused) {
        }
    }

    private boolean s() {
        SharedPreferences sharedPreferences;
        try {
            Context context = f21853a;
            if (context == null || (sharedPreferences = PreferenceWrapper.getDefault(context)) == null) {
                return false;
            }
            return sharedPreferences.getLong(f21855m, -1L) != 0;
        } catch (Throwable unused) {
            return false;
        }
    }

    public void b() {
    }

    public void c() {
        b(f21853a);
        d();
        a(true);
    }

    public void d() {
        try {
            if (this.f21870g.length() > 0) {
                UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>>*** flushMemoryData: 事件落库。");
                k.a(f21853a).a(this.f21870g);
                this.f21870g = new JSONArray();
            }
            PreferenceWrapper.getDefault(f21853a).edit().putLong(f21856n, this.f21874k).putInt(f21859q, this.f21872i).putInt(f21860r, this.f21873j).commit();
        } catch (Throwable unused) {
        }
    }

    private q() {
        this.f21865b = null;
        this.f21866c = null;
        this.f21867d = null;
        this.f21868e = null;
        this.f21869f = 10;
        this.f21870g = new JSONArray();
        this.f21871h = 5000;
        this.f21872i = 0;
        this.f21873j = 0;
        this.f21874k = 0L;
        this.f21875s = 28800000L;
        this.f21876u = false;
        this.f21877v = false;
        this.f21878w = new Object();
        try {
            SharedPreferences sharedPreferences = PreferenceWrapper.getDefault(f21853a);
            this.f21874k = sharedPreferences.getLong(f21856n, 0L);
            this.f21872i = sharedPreferences.getInt(f21859q, 0);
            this.f21873j = sharedPreferences.getInt(f21860r, 0);
            this.f21865b = new c();
        } catch (Throwable unused) {
        }
    }

    private void b(JSONObject jSONObject) {
        JSONObject jSONObjectF;
        if (k.a(UMGlobalContext.getAppContext(f21853a)).c() || (jSONObjectF = k.a(UMGlobalContext.getAppContext(f21853a)).f()) == null) {
            return;
        }
        String strOptString = jSONObjectF.optString("__av");
        String strOptString2 = jSONObjectF.optString("__vc");
        try {
            if (TextUtils.isEmpty(strOptString)) {
                jSONObject.put(com.umeng.commonsdk.statistics.b.a("app_version"), UMUtils.getAppVersionName(f21853a));
            } else {
                jSONObject.put(com.umeng.commonsdk.statistics.b.a("app_version"), strOptString);
            }
            if (TextUtils.isEmpty(strOptString2)) {
                jSONObject.put(com.umeng.commonsdk.statistics.b.a("version_code"), UMUtils.getAppVersionCode(f21853a));
            } else {
                jSONObject.put(com.umeng.commonsdk.statistics.b.a("version_code"), strOptString2);
            }
        } catch (Throwable unused) {
        }
    }

    private void g(JSONObject jSONObject) throws JSONException {
        JSONObject jSONObjectOptJSONObject;
        try {
            if (jSONObject.getJSONObject(com.umeng.commonsdk.statistics.b.a("header")).has(f.aH)) {
                if (jSONObject.has("content")) {
                    jSONObject = jSONObject.getJSONObject("content");
                }
                if (jSONObject.has(com.umeng.commonsdk.statistics.b.a("analytics"))) {
                    if (!jSONObject.getJSONObject(com.umeng.commonsdk.statistics.b.a("analytics")).has(f.f21692n)) {
                        UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> Error, Should not go to this branch.");
                        return;
                    }
                    k.a(f21853a).i();
                    k.a(f21853a).h();
                    k.a(f21853a).b(true, false);
                    k.a(f21853a).a();
                    return;
                }
                return;
            }
            if (jSONObject.has("content")) {
                jSONObject = jSONObject.getJSONObject("content");
            }
            if (jSONObject.has(com.umeng.commonsdk.statistics.b.a("analytics")) && (jSONObjectOptJSONObject = jSONObject.optJSONObject(com.umeng.commonsdk.statistics.b.a("analytics"))) != null && jSONObjectOptJSONObject.length() > 0) {
                if (jSONObjectOptJSONObject.has(f.f21692n)) {
                    k.a(f21853a).b(true, false);
                }
                if (jSONObjectOptJSONObject.has("ekv") || jSONObjectOptJSONObject.has(f.Z)) {
                    k.a(f21853a).h();
                }
                if (jSONObjectOptJSONObject.has("error")) {
                    k.a(f21853a).i();
                }
            }
            k.a(f21853a).a();
        } catch (Exception unused) {
        }
    }

    public static class c {

        /* renamed from: a, reason: collision with root package name */
        private ReportPolicy.ReportStrategy f21906a = null;

        /* renamed from: b, reason: collision with root package name */
        private int f21907b = -1;

        /* renamed from: c, reason: collision with root package name */
        private int f21908c = -1;

        /* renamed from: d, reason: collision with root package name */
        private int f21909d = -1;

        /* renamed from: e, reason: collision with root package name */
        private int f21910e = -1;

        /* renamed from: f, reason: collision with root package name */
        private ABTest f21911f;

        public c() {
            this.f21911f = null;
            this.f21911f = ABTest.getService(q.f21853a);
        }

        public void a() {
            try {
                int[] iArrA = a(-1, -1);
                this.f21907b = iArrA[0];
                this.f21908c = iArrA[1];
            } catch (Throwable unused) {
            }
        }

        protected void b() {
            Defcon service = Defcon.getService(q.f21853a);
            if (service.isOpen()) {
                ReportPolicy.ReportStrategy reportStrategy = this.f21906a;
                this.f21906a = ((reportStrategy instanceof ReportPolicy.DefconPolicy) && reportStrategy.isValid()) ? this.f21906a : new ReportPolicy.DefconPolicy(StatTracer.getInstance(q.f21853a), service);
            } else {
                int iA = 0;
                boolean z2 = Integer.valueOf(UMEnvelopeBuild.imprintProperty(q.f21853a, "integrated_test", q.f21861t)).intValue() == 1;
                if (UMConfigure.isDebugLog() && z2 && !MLog.DEBUG) {
                    UMLog.mutlInfo(l.K, 3, "\\|", null, null);
                }
                if (MLog.DEBUG && z2) {
                    this.f21906a = new ReportPolicy.DebugPolicy(StatTracer.getInstance(q.f21853a));
                } else if (this.f21911f.isInTest() && "RPT".equals(this.f21911f.getTestName())) {
                    if (this.f21911f.getTestPolicy() == 6) {
                        if (Integer.valueOf(UMEnvelopeBuild.imprintProperty(q.f21853a, "test_report_interval", q.f21861t)).intValue() != -1) {
                            iA = a(90000);
                        } else {
                            iA = this.f21908c;
                            if (iA <= 0) {
                                iA = this.f21910e;
                            }
                        }
                    }
                    this.f21906a = b(this.f21911f.getTestPolicy(), iA);
                } else {
                    int i2 = this.f21909d;
                    int i3 = this.f21910e;
                    int i4 = this.f21907b;
                    if (i4 != -1) {
                        i3 = this.f21908c;
                        i2 = i4;
                    }
                    this.f21906a = b(i2, i3);
                }
            }
            if (UMConfigure.isDebugLog()) {
                try {
                    ReportPolicy.ReportStrategy reportStrategy2 = this.f21906a;
                    if (reportStrategy2 instanceof ReportPolicy.ReportAtLaunch) {
                        UMLog.mutlInfo(l.I, 3, "", null, null);
                    } else if (reportStrategy2 instanceof ReportPolicy.ReportByInterval) {
                        UMLog.mutlInfo(l.J, 3, "", new String[]{"@"}, new String[]{String.valueOf(((ReportPolicy.ReportByInterval) reportStrategy2).getReportInterval() / 1000)});
                    } else if (reportStrategy2 instanceof ReportPolicy.DebugPolicy) {
                        UMLog.mutlInfo(l.L, 3, "", null, null);
                    } else if (reportStrategy2 instanceof ReportPolicy.ReportQuasiRealtime) {
                        UMLog.mutlInfo(l.M, 3, "", new String[]{"@"}, new String[]{String.valueOf(((ReportPolicy.ReportQuasiRealtime) reportStrategy2).getReportInterval() / 1000)});
                    } else {
                        boolean z3 = reportStrategy2 instanceof ReportPolicy.DefconPolicy;
                    }
                } catch (Throwable unused) {
                }
            }
        }

        public ReportPolicy.ReportStrategy c() {
            b();
            return this.f21906a;
        }

        public int[] a(int i2, int i3) {
            int iIntValue = Integer.valueOf(UMEnvelopeBuild.imprintProperty(q.f21853a, "report_policy", q.f21861t)).intValue();
            int iIntValue2 = Integer.valueOf(UMEnvelopeBuild.imprintProperty(q.f21853a, "report_interval", q.f21861t)).intValue();
            if (iIntValue == -1 || !ReportPolicy.isValid(iIntValue)) {
                return new int[]{i2, i3};
            }
            if (6 == iIntValue) {
                if (iIntValue2 == -1 || iIntValue2 < 90 || iIntValue2 > 86400) {
                    iIntValue2 = 90;
                }
                return new int[]{iIntValue, iIntValue2 * 1000};
            }
            if (11 == iIntValue) {
                if (iIntValue2 == -1 || iIntValue2 < 15 || iIntValue2 > 3600) {
                    iIntValue2 = 15;
                }
                return new int[]{iIntValue, iIntValue2 * 1000};
            }
            return new int[]{i2, i3};
        }

        public int a(int i2) {
            int iIntValue = Integer.valueOf(UMEnvelopeBuild.imprintProperty(q.f21853a, "test_report_interval", q.f21861t)).intValue();
            return (iIntValue == -1 || iIntValue < 90 || iIntValue > 86400) ? i2 : iIntValue * 1000;
        }

        private ReportPolicy.ReportStrategy b(int i2, int i3) {
            if (i2 == 0) {
                ReportPolicy.ReportStrategy reportStrategy = this.f21906a;
                return reportStrategy instanceof ReportPolicy.ReportRealtime ? reportStrategy : new ReportPolicy.ReportRealtime();
            }
            if (i2 == 1) {
                ReportPolicy.ReportStrategy reportStrategy2 = this.f21906a;
                return reportStrategy2 instanceof ReportPolicy.ReportAtLaunch ? reportStrategy2 : new ReportPolicy.ReportAtLaunch();
            }
            if (i2 == 4) {
                ReportPolicy.ReportStrategy reportStrategy3 = this.f21906a;
                return reportStrategy3 instanceof ReportPolicy.ReportDaily ? reportStrategy3 : new ReportPolicy.ReportDaily(StatTracer.getInstance(q.f21853a));
            }
            if (i2 == 5) {
                ReportPolicy.ReportStrategy reportStrategy4 = this.f21906a;
                return reportStrategy4 instanceof ReportPolicy.ReportWifiOnly ? reportStrategy4 : new ReportPolicy.ReportWifiOnly(q.f21853a);
            }
            if (i2 == 6) {
                ReportPolicy.ReportStrategy reportStrategy5 = this.f21906a;
                if (reportStrategy5 instanceof ReportPolicy.ReportByInterval) {
                    ((ReportPolicy.ReportByInterval) reportStrategy5).setReportInterval(i3);
                    return reportStrategy5;
                }
                return new ReportPolicy.ReportByInterval(StatTracer.getInstance(q.f21853a), i3);
            }
            if (i2 == 8) {
                ReportPolicy.ReportStrategy reportStrategy6 = this.f21906a;
                return reportStrategy6 instanceof ReportPolicy.SmartPolicy ? reportStrategy6 : new ReportPolicy.SmartPolicy(StatTracer.getInstance(q.f21853a));
            }
            if (i2 != 11) {
                ReportPolicy.ReportStrategy reportStrategy7 = this.f21906a;
                return reportStrategy7 instanceof ReportPolicy.ReportAtLaunch ? reportStrategy7 : new ReportPolicy.ReportAtLaunch();
            }
            ReportPolicy.ReportStrategy reportStrategy8 = this.f21906a;
            if (reportStrategy8 instanceof ReportPolicy.ReportQuasiRealtime) {
                ((ReportPolicy.ReportQuasiRealtime) reportStrategy8).setReportInterval(i3);
                return reportStrategy8;
            }
            ReportPolicy.ReportQuasiRealtime reportQuasiRealtime = new ReportPolicy.ReportQuasiRealtime();
            reportQuasiRealtime.setReportInterval(i3);
            return reportQuasiRealtime;
        }
    }

    private void c(JSONObject jSONObject) {
        try {
            if (!k.a(f21853a).e()) {
                JSONObject jSONObjectG = k.a(f21853a).g();
                if (jSONObjectG != null) {
                    String strOptString = jSONObjectG.optString("__av");
                    String strOptString2 = jSONObjectG.optString("__vc");
                    if (TextUtils.isEmpty(strOptString)) {
                        jSONObject.put(com.umeng.commonsdk.statistics.b.a("app_version"), UMUtils.getAppVersionName(f21853a));
                    } else {
                        jSONObject.put(com.umeng.commonsdk.statistics.b.a("app_version"), strOptString);
                    }
                    if (TextUtils.isEmpty(strOptString2)) {
                        jSONObject.put(com.umeng.commonsdk.statistics.b.a("version_code"), UMUtils.getAppVersionCode(f21853a));
                        return;
                    } else {
                        jSONObject.put(com.umeng.commonsdk.statistics.b.a("version_code"), strOptString2);
                        return;
                    }
                }
                return;
            }
            jSONObject.put(com.umeng.commonsdk.statistics.b.a("app_version"), UMUtils.getAppVersionName(f21853a));
            jSONObject.put(com.umeng.commonsdk.statistics.b.a("version_code"), UMUtils.getAppVersionCode(f21853a));
        } catch (Throwable unused) {
        }
    }

    public void a() {
        if (f21853a != null) {
            synchronized (this.f21878w) {
                try {
                    if (this.f21876u) {
                        UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> network is now available, rebuild instant session data packet.");
                        Context context = f21853a;
                        UMWorkDispatch.sendEvent(context, a.f21890l, CoreProtocol.getInstance(context), null);
                    }
                } finally {
                }
            }
            synchronized (this.f21878w) {
                try {
                    if (this.f21877v) {
                        Context context2 = f21853a;
                        UMWorkDispatch.sendEvent(context2, a.f21891m, CoreProtocol.getInstance(context2), null);
                    }
                } finally {
                }
            }
        }
    }

    public static class d {

        /* renamed from: a, reason: collision with root package name */
        private Map<String, Object> f21912a;

        /* renamed from: b, reason: collision with root package name */
        private String f21913b;

        /* renamed from: c, reason: collision with root package name */
        private String f21914c;

        /* renamed from: d, reason: collision with root package name */
        private long f21915d;

        private d() {
            this.f21912a = null;
            this.f21913b = null;
            this.f21914c = null;
            this.f21915d = 0L;
        }

        public Map<String, Object> a() {
            return this.f21912a;
        }

        public String b() {
            return this.f21914c;
        }

        public String c() {
            return this.f21913b;
        }

        public long d() {
            return this.f21915d;
        }

        public d(String str, Map<String, Object> map, String str2, long j2) {
            this.f21912a = map;
            this.f21913b = str;
            this.f21915d = j2;
            this.f21914c = str2;
        }
    }

    private void h(Object obj) {
        try {
            JSONObject jSONObject = (JSONObject) obj;
            if (jSONObject == null || jSONObject.length() <= 0 || !jSONObject.has("__ii")) {
                return;
            }
            String strOptString = jSONObject.optString("__ii");
            jSONObject.remove("__ii");
            if (TextUtils.isEmpty(strOptString)) {
                return;
            }
            k.a(f21853a).a(strOptString, obj.toString(), 2);
        } catch (Throwable unused) {
        }
    }

    private void d(JSONObject jSONObject) {
        JSONObject jSONObject2;
        if (jSONObject == null) {
            return;
        }
        try {
            if (jSONObject.length() <= 0) {
                return;
            }
            JSONObject jSONObject3 = new JSONObject();
            if (jSONObject.has(com.umeng.commonsdk.statistics.b.a("analytics"))) {
                JSONObject jSONObject4 = jSONObject.getJSONObject(com.umeng.commonsdk.statistics.b.a("analytics"));
                if (jSONObject4.has("ekv")) {
                    jSONObject3.put("ekv", jSONObject4.getJSONArray("ekv"));
                    if (jSONObject3.length() > 0) {
                        if (AnalyticsConfig.isRealTimeDebugMode()) {
                            MLog.d("[埋点验证模式]事件:" + jSONObject3.toString());
                        } else {
                            MLog.d("事件:" + jSONObject3.toString());
                        }
                        jSONObject3 = new JSONObject();
                    }
                }
                if (jSONObject4.has(f.Z)) {
                    jSONObject3.put(f.Z, jSONObject4.getJSONArray(f.Z));
                    if (jSONObject3.length() > 0) {
                        if (AnalyticsConfig.isRealTimeDebugMode()) {
                            MLog.d("[埋点验证模式]游戏事件:" + jSONObject3.toString());
                        } else {
                            MLog.d("游戏事件:" + jSONObject3.toString());
                        }
                        jSONObject3 = new JSONObject();
                    }
                }
                if (jSONObject4.has("error")) {
                    jSONObject3.put("error", jSONObject4.getJSONArray("error"));
                    if (jSONObject3.length() > 0) {
                        if (AnalyticsConfig.isRealTimeDebugMode()) {
                            MLog.d("[埋点验证模式]错误:" + jSONObject3.toString());
                        } else {
                            MLog.d("错误:" + jSONObject3.toString());
                        }
                        jSONObject3 = new JSONObject();
                    }
                }
                if (jSONObject4.has(f.f21692n)) {
                    JSONArray jSONArray = jSONObject4.getJSONArray(f.f21692n);
                    JSONArray jSONArray2 = new JSONArray();
                    for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                        JSONObject jSONObject5 = jSONArray.getJSONObject(i2);
                        if (jSONObject5 != null && jSONObject5.length() > 0) {
                            if (jSONObject5.has(f.f21699u)) {
                                jSONObject5.remove(f.f21699u);
                            }
                            jSONArray2.put(jSONObject5);
                        }
                    }
                    jSONObject3.put(f.f21692n, jSONArray2);
                    if (jSONObject3.length() > 0) {
                        if (AnalyticsConfig.isRealTimeDebugMode()) {
                            MLog.d("[埋点验证模式]会话:" + jSONObject3.toString());
                        } else {
                            MLog.d("会话:" + jSONObject3.toString());
                        }
                        jSONObject3 = new JSONObject();
                    }
                }
                if (jSONObject4.has(f.I)) {
                    jSONObject3.put(f.I, jSONObject4.getJSONObject(f.I));
                }
                if (jSONObject4.has(f.L)) {
                    jSONObject3.put(f.L, jSONObject4.getJSONObject(f.L));
                    if (jSONObject3.length() > 0) {
                        if (AnalyticsConfig.isRealTimeDebugMode()) {
                            MLog.d("[埋点验证模式]账号:" + jSONObject3.toString());
                        } else {
                            MLog.d("账号:" + jSONObject3.toString());
                        }
                        jSONObject3 = new JSONObject();
                    }
                }
            }
            if (jSONObject.has("dplus")) {
                jSONObject3.put("dplus", jSONObject.getJSONObject("dplus"));
            }
            if (jSONObject.has(com.umeng.commonsdk.statistics.b.a("header")) && (jSONObject2 = jSONObject.getJSONObject(com.umeng.commonsdk.statistics.b.a("header"))) != null && jSONObject2.length() > 0) {
                if (jSONObject2.has(com.umeng.commonsdk.statistics.b.a("sdk_version"))) {
                    jSONObject3.put("sdk_version", jSONObject2.getString(com.umeng.commonsdk.statistics.b.a("sdk_version")));
                }
                if (jSONObject2.has(com.umeng.commonsdk.statistics.b.a("device_id"))) {
                    jSONObject3.put("device_id", jSONObject2.getString(com.umeng.commonsdk.statistics.b.a("device_id")));
                }
                if (jSONObject2.has(com.umeng.commonsdk.statistics.b.a("device_model"))) {
                    jSONObject3.put("device_model", jSONObject2.getString(com.umeng.commonsdk.statistics.b.a("device_model")));
                }
                if (jSONObject2.has(com.umeng.commonsdk.statistics.b.a("version_code"))) {
                    jSONObject3.put("version", jSONObject2.getInt(com.umeng.commonsdk.statistics.b.a("version_code")));
                }
                if (jSONObject2.has(com.umeng.commonsdk.statistics.b.a("appkey"))) {
                    jSONObject3.put("appkey", jSONObject2.getString(com.umeng.commonsdk.statistics.b.a("appkey")));
                }
                if (jSONObject2.has(com.umeng.commonsdk.statistics.b.a("channel"))) {
                    jSONObject3.put("channel", jSONObject2.getString(com.umeng.commonsdk.statistics.b.a("channel")));
                }
                if (jSONObject3.length() > 0) {
                    MLog.d("基础信息:" + jSONObject3.toString());
                    jSONObject3 = new JSONObject();
                }
            }
            jSONObject3.length();
        } catch (Throwable th) {
            MLog.e(th);
        }
    }

    public JSONObject b(long j2) {
        if (TextUtils.isEmpty(aa.a().d(UMGlobalContext.getAppContext(f21853a)))) {
            return null;
        }
        JSONObject jSONObjectB = k.a(UMGlobalContext.getAppContext(f21853a)).b(false);
        String[] strArrA = com.umeng.analytics.c.a(f21853a);
        if (strArrA != null && !TextUtils.isEmpty(strArrA[0]) && !TextUtils.isEmpty(strArrA[1])) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put(f.M, strArrA[0]);
                jSONObject.put(f.N, strArrA[1]);
                if (jSONObject.length() > 0) {
                    jSONObjectB.put(f.L, jSONObject);
                }
            } catch (Throwable unused) {
            }
        }
        int iA = t.a().a(f21853a);
        if (jSONObjectB.length() == 1 && jSONObjectB.optJSONObject(f.L) != null && iA != 3) {
            return null;
        }
        t.a().b(jSONObjectB, f21853a);
        if (jSONObjectB.length() <= 0 && iA != 3) {
            return null;
        }
        JSONObject jSONObjectK = k();
        if (jSONObjectK != null) {
            b(jSONObjectK);
        }
        JSONObject jSONObject2 = new JSONObject();
        JSONObject jSONObject3 = new JSONObject();
        try {
            if (iA == 3) {
                jSONObject3.put("analytics", new JSONObject());
            } else if (jSONObjectB.length() > 0) {
                jSONObject3.put("analytics", jSONObjectB);
            }
            if (jSONObjectK != null && jSONObjectK.length() > 0) {
                jSONObject2.put("header", jSONObjectK);
            }
            if (jSONObject3.length() > 0) {
                jSONObject2.put("content", jSONObject3);
            }
            return b(jSONObject2, j2);
        } catch (Throwable unused2) {
            return jSONObject2;
        }
    }

    private boolean c(boolean z2) {
        if (s() || AnalyticsConfig.isRealTimeDebugMode()) {
            return true;
        }
        if (this.f21865b == null) {
            this.f21865b = new c();
        }
        this.f21865b.a();
        ReportPolicy.ReportStrategy reportStrategyC = this.f21865b.c();
        boolean zShouldSendMessage = reportStrategyC.shouldSendMessage(z2);
        if (zShouldSendMessage) {
            if (((reportStrategyC instanceof ReportPolicy.ReportByInterval) || (reportStrategyC instanceof ReportPolicy.DebugPolicy) || (reportStrategyC instanceof ReportPolicy.ReportQuasiRealtime)) && p()) {
                d();
            }
            if ((reportStrategyC instanceof ReportPolicy.DefconPolicy) && p()) {
                d();
            }
            if (UMConfigure.isDebugLog()) {
                MLog.d("数据发送策略 : " + reportStrategyC.getClass().getSimpleName());
            }
        }
        return zShouldSendMessage;
    }

    private void a(String str, String str2) {
        Method method;
        Class<?> cls = f21863y;
        if (cls == null || (method = f21864z) == null) {
            return;
        }
        try {
            method.invoke(cls, str, str2);
        } catch (Throwable unused) {
            UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> reflect call setPuidAndProvider method of crash lib failed.");
        }
    }

    private void e(JSONObject jSONObject) {
        JSONObject jSONObject2;
        if (jSONObject == null) {
            return;
        }
        try {
            if (jSONObject.length() <= 0) {
                return;
            }
            JSONObject jSONObject3 = new JSONObject();
            if (jSONObject.has(com.umeng.commonsdk.statistics.b.a("analytics"))) {
                JSONObject jSONObject4 = jSONObject.getJSONObject(com.umeng.commonsdk.statistics.b.a("analytics"));
                if (jSONObject4.has(f.f21692n)) {
                    JSONArray jSONArray = jSONObject4.getJSONArray(f.f21692n);
                    JSONArray jSONArray2 = new JSONArray();
                    for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                        JSONObject jSONObject5 = jSONArray.getJSONObject(i2);
                        if (jSONObject5 != null && jSONObject5.length() > 0) {
                            jSONArray2.put(jSONObject5);
                        }
                    }
                    jSONObject3.put(f.f21692n, jSONArray2);
                    if (jSONObject3.length() > 0) {
                        MLog.d("本次启动会话:" + jSONObject3.toString());
                        jSONObject3 = new JSONObject();
                    }
                }
                if (jSONObject4.has(f.L)) {
                    jSONObject3.put(f.L, jSONObject4.getJSONObject(f.L));
                    if (jSONObject3.length() > 0) {
                        MLog.d("本次启动账号:" + jSONObject3.toString());
                        jSONObject3 = new JSONObject();
                    }
                }
            }
            if (jSONObject.has(com.umeng.commonsdk.statistics.b.a("header")) && jSONObject.has(com.umeng.commonsdk.statistics.b.a("header")) && (jSONObject2 = jSONObject.getJSONObject(com.umeng.commonsdk.statistics.b.a("header"))) != null && jSONObject2.length() > 0) {
                if (jSONObject2.has(com.umeng.commonsdk.statistics.b.a("sdk_version"))) {
                    jSONObject3.put("sdk_version", jSONObject2.getString(com.umeng.commonsdk.statistics.b.a("sdk_version")));
                }
                if (jSONObject2.has(com.umeng.commonsdk.statistics.b.a("device_id"))) {
                    jSONObject3.put("device_id", jSONObject2.getString(com.umeng.commonsdk.statistics.b.a("device_id")));
                }
                if (jSONObject2.has(com.umeng.commonsdk.statistics.b.a("device_model"))) {
                    jSONObject3.put("device_model", jSONObject2.getString(com.umeng.commonsdk.statistics.b.a("device_model")));
                }
                if (jSONObject2.has(com.umeng.commonsdk.statistics.b.a("version_code"))) {
                    jSONObject3.put("version", jSONObject2.getInt(com.umeng.commonsdk.statistics.b.a("version_code")));
                }
                if (jSONObject2.has(com.umeng.commonsdk.statistics.b.a("appkey"))) {
                    jSONObject3.put("appkey", jSONObject2.getString(com.umeng.commonsdk.statistics.b.a("appkey")));
                }
                if (jSONObject2.has(com.umeng.commonsdk.statistics.b.a("channel"))) {
                    jSONObject3.put("channel", jSONObject2.getString(com.umeng.commonsdk.statistics.b.a("channel")));
                }
                if (jSONObject3.length() > 0) {
                    MLog.d("本次启动基础信息:" + jSONObject3.toString());
                    jSONObject3 = new JSONObject();
                }
            }
            jSONObject3.length();
        } catch (Throwable th) {
            MLog.e(th);
        }
    }

    public void a(Object obj, int i2) {
        if (com.umeng.commonsdk.utils.c.a()) {
            if (i2 != 4357) {
                return;
            }
            try {
                UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> clean db in silent mode.");
                j.a(f21853a);
                com.umeng.commonsdk.utils.c.c(f21853a);
            } catch (Throwable unused) {
            }
        }
        if (AnalyticsConfig.enable) {
            try {
                if (i2 != 4358) {
                    switch (i2) {
                        case 4097:
                            if (UMUtils.isMainProgress(f21853a)) {
                                if (obj != null) {
                                    e(obj);
                                }
                                if (f21861t.equals(((JSONObject) obj).optString("__i"))) {
                                    return;
                                }
                                a(false);
                                return;
                            }
                            UMProcessDBHelper.getInstance(f21853a).insertEventsInSubProcess(UMFrUtils.getSubProcessName(f21853a), new JSONArray().put(obj));
                            return;
                        case 4098:
                            if (obj != null) {
                                e(obj);
                            }
                            if (f21861t.equals(((JSONObject) obj).optString("__i"))) {
                                return;
                            }
                            a(false);
                            return;
                        case 4099:
                            x.a(f21853a);
                            return;
                        case 4100:
                            n.c(f21853a);
                            return;
                        case 4101:
                            UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> PROFILE_SIGNIN");
                            a((Object) null, true);
                            g(obj);
                            return;
                        case 4102:
                            UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> PROFILE_SIGNOFF");
                            a((Object) null, true);
                            f(obj);
                            return;
                        case 4103:
                            UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> START_SESSION");
                            w.a().a(f21853a, obj);
                            synchronized (this.f21878w) {
                                this.f21877v = true;
                            }
                            return;
                        case a.f21886h /* 4104 */:
                            w.a().c(f21853a, obj);
                            return;
                        case 4105:
                            d();
                            return;
                        case 4106:
                            h(obj);
                            return;
                        default:
                            switch (i2) {
                                case a.f21889k /* 4352 */:
                                    UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> INSTANT_SESSION_START");
                                    w.a().b(f21853a, obj);
                                    synchronized (this.f21878w) {
                                        this.f21876u = true;
                                    }
                                    return;
                                case a.f21890l /* 4353 */:
                                    a(obj, true);
                                    return;
                                case a.f21891m /* 4354 */:
                                    c();
                                    return;
                                case a.f21892n /* 4355 */:
                                    if (!UMUtils.isMainProgress(f21853a)) {
                                        UMProcessDBHelper.getInstance(f21853a).insertEventsInSubProcess(UMFrUtils.getSubProcessName(f21853a), new JSONArray().put(obj));
                                        return;
                                    } else {
                                        if (obj != null) {
                                            e(obj);
                                            d();
                                            return;
                                        }
                                        return;
                                    }
                                case a.f21893o /* 4356 */:
                                    if (obj == null || f21863y == null || f21864z == null) {
                                        return;
                                    }
                                    UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> PROFILE_CHANGE_NOTIFY");
                                    String string = "";
                                    String string2 = "";
                                    if (obj instanceof JSONObject) {
                                        JSONObject jSONObject = (JSONObject) obj;
                                        if (jSONObject.has("uid") && jSONObject.has(f.M)) {
                                            string = jSONObject.getString(f.M);
                                            string2 = jSONObject.getString("uid");
                                        }
                                        a(string2, string);
                                        return;
                                    }
                                    return;
                                default:
                                    switch (i2) {
                                        case a.f21898t /* 8195 */:
                                            com.umeng.analytics.b.a().a(obj);
                                            return;
                                        case a.f21899u /* 8196 */:
                                            com.umeng.analytics.b.a().m();
                                            return;
                                        case 8197:
                                            com.umeng.analytics.b.a().k();
                                            return;
                                        default:
                                            switch (i2) {
                                                case a.f21901w /* 8199 */:
                                                case 8200:
                                                    com.umeng.analytics.b.a().b(obj);
                                                    return;
                                                case a.f21903y /* 8201 */:
                                                    com.umeng.analytics.b.a().b((Object) null);
                                                    return;
                                                default:
                                                    switch (i2) {
                                                        case a.f21904z /* 8208 */:
                                                            UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> receive DELAY_BUILD_ENVELOPE event.");
                                                            Context context = f21853a;
                                                            UMWorkDispatch.sendEvent(context, a.A, CoreProtocol.getInstance(context), null);
                                                            Context context2 = f21853a;
                                                            UMWorkDispatch.sendEvent(context2, a.f21891m, CoreProtocol.getInstance(context2), null);
                                                            return;
                                                        case a.A /* 8209 */:
                                                            a(obj, false);
                                                            return;
                                                        case a.B /* 8210 */:
                                                            UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> recv BUILD_ENVELOPE_IMMEDIATELY.");
                                                            if (!UMUtils.isMainProgress(f21853a) || (this.f21865b.c() instanceof ReportPolicy.ReportQuasiRealtime)) {
                                                                return;
                                                            }
                                                            a(true);
                                                            return;
                                                        default:
                                                            switch (i2) {
                                                                case a.E /* 8213 */:
                                                                    if (FieldManager.allow(com.umeng.commonsdk.utils.d.E)) {
                                                                        if (DeviceConfig.getGlobleActivity(f21853a) != null) {
                                                                            w.b(f21853a);
                                                                        }
                                                                        Context context3 = f21853a;
                                                                        UMWorkDispatch.sendEventEx(context3, a.E, CoreProtocol.getInstance(context3), null, 5000L);
                                                                        return;
                                                                    }
                                                                    return;
                                                                case a.F /* 8214 */:
                                                                    if (obj != null && (obj instanceof JSONObject)) {
                                                                        String strOptString = ((JSONObject) obj).optString("startTime");
                                                                        String strOptString2 = ((JSONObject) obj).optString("period");
                                                                        String strOptString3 = ((JSONObject) obj).optString(AnalyticsConfig.DEBUG_KEY);
                                                                        if (TextUtils.isEmpty(strOptString) || TextUtils.isEmpty(strOptString2) || TextUtils.isEmpty(strOptString3)) {
                                                                            return;
                                                                        }
                                                                        Context context4 = f21853a;
                                                                        String str = AnalyticsConfig.RTD_SP_FILE;
                                                                        com.umeng.common.b.a(context4, str, "startTime", strOptString);
                                                                        com.umeng.common.b.a(f21853a, str, "period", strOptString2);
                                                                        com.umeng.common.b.a(f21853a, str, AnalyticsConfig.DEBUG_KEY, strOptString3);
                                                                        return;
                                                                    }
                                                                    return;
                                                                case a.G /* 8215 */:
                                                                    com.umeng.common.b.a(f21853a, AnalyticsConfig.RTD_SP_FILE);
                                                                    return;
                                                                default:
                                                                    return;
                                                            }
                                                    }
                                            }
                                    }
                            }
                    }
                }
                if (obj != null && (obj instanceof JSONObject)) {
                    String strOptString4 = ((JSONObject) obj).optString("pk");
                    Object objOpt = ((JSONObject) obj).opt(f.T);
                    if (TextUtils.isEmpty(strOptString4)) {
                        return;
                    }
                    String[] strArrA = com.umeng.analytics.c.a(f21853a);
                    if (strArrA != null && !TextUtils.isEmpty(strArrA[0]) && !TextUtils.isEmpty(strArrA[1])) {
                        if (f.O.equals(strOptString4)) {
                            com.umeng.analytics.c.a((String) objOpt);
                            return;
                        } else if (f.P.equals(strOptString4)) {
                            com.umeng.analytics.c.b((String) objOpt);
                            return;
                        } else {
                            com.umeng.analytics.c.a(strOptString4, objOpt);
                            return;
                        }
                    }
                    MLog.e("Please call MobclickAgent.onProfileSignIn() function before set user profile property.");
                }
            } catch (Throwable unused2) {
            }
        }
    }

    private void g(Object obj) {
        try {
            b(f21853a);
            d();
            JSONObject jSONObject = (JSONObject) obj;
            if (jSONObject != null && jSONObject.length() > 0) {
                String string = jSONObject.getString(f.M);
                String string2 = jSONObject.getString("uid");
                long j2 = jSONObject.getLong("ts");
                String[] strArrA = com.umeng.analytics.c.a(f21853a);
                if (strArrA != null && string.equals(strArrA[0]) && string2.equals(strArrA[1])) {
                    return;
                }
                w.a().a(f21853a, j2);
                String strC = aa.a().c(f21853a);
                boolean zB = w.a().b(f21853a, j2, false);
                com.umeng.analytics.c.a(f21853a, string, string2);
                UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> onProfileSignIn: force generate new session: session id = " + strC);
                w.a().a(f21853a, j2, true);
                if (zB) {
                    w.a().b(f21853a, j2);
                }
            }
        } catch (Throwable unused) {
        }
    }

    public long f() {
        SharedPreferences sharedPreferences;
        long jCurrentTimeMillis = 0;
        try {
            Context context = f21853a;
            if (context == null || (sharedPreferences = PreferenceWrapper.getDefault(context)) == null) {
                return 0L;
            }
            long j2 = sharedPreferences.getLong(f21854l, 0L);
            if (j2 == 0) {
                try {
                    jCurrentTimeMillis = System.currentTimeMillis();
                    sharedPreferences.edit().putLong(f21854l, jCurrentTimeMillis).commit();
                    return jCurrentTimeMillis;
                } catch (Throwable unused) {
                }
            }
            return j2;
        } catch (Throwable unused2) {
            return jCurrentTimeMillis;
        }
    }

    public void c(Object obj) {
        b(f21853a);
        d();
        if (d(false)) {
            j();
        }
    }

    private void f(Object obj) {
        try {
            JSONObject jSONObject = (JSONObject) obj;
            if (jSONObject != null && jSONObject.length() > 0) {
                long j2 = jSONObject.getLong("ts");
                b(f21853a);
                d();
                String[] strArrA = com.umeng.analytics.c.a(f21853a);
                if (strArrA == null || TextUtils.isEmpty(strArrA[0]) || TextUtils.isEmpty(strArrA[1])) {
                    return;
                }
                w.a().a(f21853a, j2);
                UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> onProfileSignIn: force generate new session: session id = " + aa.a().c(f21853a));
                boolean zB = w.a().b(f21853a, j2, false);
                com.umeng.analytics.c.b(f21853a);
                w.a().a(f21853a, j2, true);
                if (zB) {
                    w.a().b(f21853a, j2);
                }
            }
        } catch (Throwable th) {
            if (MLog.DEBUG) {
                MLog.e(" Excepthon  in  onProfileSignOff", th);
            }
        }
    }

    private JSONObject b(JSONObject jSONObject, long j2) {
        try {
            if (s.a(jSONObject) <= j2) {
                return jSONObject;
            }
            jSONObject = null;
            k.a(f21853a).a(true, false);
            k.a(f21853a).b();
            UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> Instant session packet overload !!! ");
            return null;
        } catch (Throwable unused) {
            return jSONObject;
        }
    }

    public JSONObject b(boolean z2) {
        JSONArray jSONArray;
        JSONArray jSONArray2;
        JSONObject jSONObjectA = null;
        try {
            jSONObjectA = k.a(f21853a).a(z2);
            if (jSONObjectA == null) {
                jSONObjectA = new JSONObject();
            } else {
                try {
                    boolean zHas = jSONObjectA.has(f.f21692n);
                    jSONObjectA = jSONObjectA;
                    if (zHas) {
                        JSONArray jSONArray3 = jSONObjectA.getJSONArray(f.f21692n);
                        JSONArray jSONArray4 = new JSONArray();
                        int i2 = 0;
                        while (i2 < jSONArray3.length()) {
                            JSONObject jSONObject = (JSONObject) jSONArray3.get(i2);
                            JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray(f.f21698t);
                            JSONArray jSONArrayOptJSONArray2 = jSONObject.optJSONArray(f.f21699u);
                            if (jSONArrayOptJSONArray == null && jSONArrayOptJSONArray2 != null) {
                                jSONObject.put(f.f21698t, jSONArrayOptJSONArray2);
                                jSONObject.remove(f.f21699u);
                            }
                            if (jSONArrayOptJSONArray != null && jSONArrayOptJSONArray2 != null) {
                                ArrayList arrayList = new ArrayList();
                                for (int i3 = 0; i3 < jSONArrayOptJSONArray.length(); i3++) {
                                    arrayList.add((JSONObject) jSONArrayOptJSONArray.get(i3));
                                }
                                for (int i4 = 0; i4 < jSONArrayOptJSONArray2.length(); i4++) {
                                    arrayList.add((JSONObject) jSONArrayOptJSONArray2.get(i4));
                                }
                                JSONArraySortUtil jSONArraySortUtil = new JSONArraySortUtil();
                                jSONArraySortUtil.setCompareKey(f.f21702x);
                                Collections.sort(arrayList, jSONArraySortUtil);
                                JSONArray jSONArray5 = new JSONArray();
                                Iterator it = arrayList.iterator();
                                while (it.hasNext()) {
                                    jSONArray5.put((JSONObject) it.next());
                                }
                                jSONObject.put(f.f21698t, jSONArray5);
                                jSONObject.remove(f.f21699u);
                            }
                            if (jSONObject.has(f.f21698t)) {
                                JSONArray jSONArrayOptJSONArray3 = jSONObject.optJSONArray(f.f21698t);
                                int i5 = 0;
                                while (i5 < jSONArrayOptJSONArray3.length()) {
                                    JSONObject jSONObject2 = jSONArrayOptJSONArray3.getJSONObject(i5);
                                    if (jSONObject2.has(f.f21702x)) {
                                        jSONArray2 = jSONArray3;
                                        jSONObject2.put("ts", jSONObject2.getLong(f.f21702x));
                                        jSONObject2.remove(f.f21702x);
                                    } else {
                                        jSONArray2 = jSONArray3;
                                    }
                                    i5++;
                                    jSONArray3 = jSONArray2;
                                }
                                jSONArray = jSONArray3;
                                jSONObject.put(f.f21698t, jSONArrayOptJSONArray3);
                                jSONObject.put(f.f21704z, jSONArrayOptJSONArray3.length());
                            } else {
                                jSONArray = jSONArray3;
                                jSONObject.put(f.f21704z, 0);
                            }
                            jSONArray4.put(jSONObject);
                            i2++;
                            jSONArray3 = jSONArray;
                        }
                        jSONObjectA.put(f.f21692n, jSONArray4);
                        jSONObjectA = jSONObjectA;
                    }
                } catch (Exception e2) {
                    MLog.e("merge pages error");
                    e2.printStackTrace();
                    jSONObjectA = jSONObjectA;
                }
            }
            SharedPreferences sharedPreferences = PreferenceWrapper.getDefault(f21853a);
            if (sharedPreferences != null) {
                String string = sharedPreferences.getString("userlevel", "");
                if (!TextUtils.isEmpty(string)) {
                    jSONObjectA.put("userlevel", string);
                }
            }
            String[] strArrA = com.umeng.analytics.c.a(f21853a);
            if (strArrA != null && !TextUtils.isEmpty(strArrA[0]) && !TextUtils.isEmpty(strArrA[1])) {
                JSONObject jSONObject3 = new JSONObject();
                jSONObject3.put(f.M, strArrA[0]);
                jSONObject3.put(f.N, strArrA[1]);
                if (jSONObject3.length() > 0) {
                    JSONObject jSONObject4 = new JSONObject();
                    String strA = com.umeng.analytics.c.a();
                    if (strA != null) {
                        jSONObject4.put(f.O, strA);
                    }
                    String strB = com.umeng.analytics.c.b();
                    if (strB != null) {
                        jSONObject4.put(f.P, strB);
                    }
                    Map<String, Object> mapC = com.umeng.analytics.c.c(f21853a);
                    if (mapC != null && mapC.size() > 0) {
                        for (String str : mapC.keySet()) {
                            jSONObject4.put(str, mapC.get(str));
                        }
                    }
                    if (jSONObject4.length() > 0) {
                        jSONObject3.put(f.R, jSONObject4);
                    }
                    jSONObjectA.put(f.L, jSONObject3);
                }
            }
            if (ABTest.getService(f21853a).isInTest()) {
                JSONObject jSONObject5 = new JSONObject();
                jSONObject5.put(ABTest.getService(f21853a).getTestName(), ABTest.getService(f21853a).getGroupInfo());
                jSONObjectA.put(f.K, jSONObject5);
            }
            t.a().a(jSONObjectA, f21853a);
        } catch (Throwable unused) {
        }
        return jSONObjectA;
    }

    public void e() {
        if (d(false)) {
            j();
        }
    }

    public void d(Object obj) {
        r();
        m();
        n();
        a(true);
    }

    private boolean d(boolean z2) {
        if (this.f21865b == null) {
            this.f21865b = new c();
        }
        ReportPolicy.ReportStrategy reportStrategyC = this.f21865b.c();
        if (!(reportStrategyC instanceof ReportPolicy.DefconPolicy)) {
            return true;
        }
        if (z2) {
            return ((ReportPolicy.DefconPolicy) reportStrategyC).shouldSendMessageByInstant();
        }
        return reportStrategyC.shouldSendMessage(false);
    }

    public void b(Object obj) {
        if (obj != null) {
            try {
                JSONObject jSONObject = (JSONObject) obj;
                if (jSONObject.length() > 0 && (!jSONObject.has("exception") || 101 != jSONObject.getInt("exception"))) {
                    f(jSONObject);
                }
            } catch (Throwable unused) {
            }
        }
    }

    public void b(Context context) {
        try {
            k.a(context).d();
            q();
        } catch (Throwable unused) {
        }
    }

    public void a(boolean z2) {
        if (c(z2)) {
            if (!(this.f21865b.c() instanceof ReportPolicy.ReportQuasiRealtime)) {
                if (UMEnvelopeBuild.isReadyBuild(f21853a, UMLogDataProtocol.UMBusinessType.U_APP)) {
                    UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> constructMessage()");
                    j();
                    return;
                }
                return;
            }
            if (z2) {
                if (UMEnvelopeBuild.isOnline(f21853a)) {
                    UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> send session start in policy ReportQuasiRealtime.");
                    j();
                    return;
                }
                return;
            }
            if (UMEnvelopeBuild.isReadyBuild(f21853a, UMLogDataProtocol.UMBusinessType.U_APP)) {
                UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> send normal data in policy ReportQuasiRealtime.");
                j();
            }
        }
    }

    private boolean a(JSONArray jSONArray) {
        int length = jSONArray.length();
        List listAsList = Arrays.asList("$$_onUMengEnterForeground", "$$_onUMengEnterBackground", "$$_onUMengEnterForegroundInitError");
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3++) {
            try {
                JSONObject jSONObjectOptJSONObject = jSONArray.optJSONObject(i3);
                if (jSONObjectOptJSONObject != null && listAsList.contains(jSONObjectOptJSONObject.optString("id"))) {
                    i2++;
                }
            } catch (Throwable unused) {
            }
        }
        return i2 >= length;
    }

    private boolean a(JSONObject jSONObject) {
        JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("ekv");
        int length = jSONArrayOptJSONArray.length();
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3++) {
            try {
                JSONObject jSONObjectOptJSONObject = jSONArrayOptJSONArray.optJSONObject(i3);
                Iterator<String> itKeys = jSONObjectOptJSONObject.keys();
                while (itKeys.hasNext()) {
                    JSONArray jSONArrayOptJSONArray2 = jSONObjectOptJSONObject.optJSONArray(itKeys.next());
                    if (jSONArrayOptJSONArray2 != null && a(jSONArrayOptJSONArray2)) {
                        i2++;
                    }
                }
            } catch (Throwable unused) {
            }
        }
        return i2 >= length;
    }

    public JSONObject a(long j2) {
        if (TextUtils.isEmpty(aa.a().d(f21853a))) {
            return null;
        }
        JSONObject jSONObjectB = b(false);
        int iA = t.a().a(f21853a);
        if (jSONObjectB.length() > 0) {
            if (jSONObjectB.length() == 1) {
                if (jSONObjectB.optJSONObject(f.L) != null && iA != 3) {
                    return null;
                }
                if (!TextUtils.isEmpty(jSONObjectB.optString("userlevel")) && iA != 3) {
                    return null;
                }
            } else if (jSONObjectB.length() == 2 && jSONObjectB.optJSONObject(f.L) != null && !TextUtils.isEmpty(jSONObjectB.optString("userlevel")) && iA != 3) {
                return null;
            }
            String strOptString = jSONObjectB.optString(f.f21692n);
            String strOptString2 = jSONObjectB.optString(f.Z);
            String strOptString3 = jSONObjectB.optString("ekv");
            if (TextUtils.isEmpty(strOptString) && TextUtils.isEmpty(strOptString2) && !TextUtils.isEmpty(strOptString3) && a(jSONObjectB)) {
                return null;
            }
        } else if (iA != 3) {
            return null;
        }
        JSONObject jSONObjectL = l();
        if (jSONObjectL != null) {
            c(jSONObjectL);
        }
        JSONObject jSONObject = new JSONObject();
        try {
            JSONObject jSONObject2 = new JSONObject();
            if (iA == 3) {
                jSONObject2.put("analytics", new JSONObject());
            } else if (jSONObjectB.length() > 0) {
                jSONObject2.put("analytics", jSONObjectB);
            }
            if (jSONObjectL != null && jSONObjectL.length() > 0) {
                jSONObject.put("header", jSONObjectL);
            }
            if (jSONObject2.length() > 0) {
                jSONObject.put("content", jSONObject2);
            }
            return a(jSONObject, j2);
        } catch (Throwable unused) {
            return jSONObject;
        }
    }

    private JSONObject a(JSONObject jSONObject, long j2) {
        try {
            if (s.a(jSONObject) <= j2) {
                return jSONObject;
            }
            JSONObject jSONObject2 = jSONObject.getJSONObject("header");
            jSONObject2.put(f.aH, s.a(jSONObject));
            jSONObject.put("header", jSONObject2);
            return s.a(f21853a, j2, jSONObject);
        } catch (Throwable unused) {
            return jSONObject;
        }
    }

    private boolean a(long j2, int i2) {
        if (j2 == 0) {
            return true;
        }
        if (System.currentTimeMillis() - j2 <= 28800000) {
            return i2 < 5000;
        }
        o();
        return true;
    }

    public void a(Object obj) {
        if (obj != null) {
            try {
                JSONObject jSONObject = (JSONObject) obj;
                if (jSONObject.length() > 0 && (!jSONObject.has("exception") || 101 != jSONObject.getInt("exception"))) {
                    g(jSONObject);
                }
            } catch (Throwable unused) {
            }
        }
    }

    public void a(Object obj, boolean z2) {
        if (z2) {
            if (d(true)) {
                i();
            }
        } else if (UMEnvelopeBuild.isOnline(f21853a) && d(true)) {
            i();
        }
    }
}
