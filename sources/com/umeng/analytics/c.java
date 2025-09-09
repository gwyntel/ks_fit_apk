package com.umeng.analytics;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;
import com.umeng.analytics.pro.ax;
import com.umeng.analytics.pro.f;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.debug.UMRTLog;
import com.umeng.commonsdk.service.UMGlobalContext;
import com.umeng.commonsdk.statistics.common.MLog;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.json.JSONTokener;

/* loaded from: classes4.dex */
public class c {

    /* renamed from: a, reason: collision with root package name */
    private static final int f21247a = 20;

    /* renamed from: b, reason: collision with root package name */
    private static final String f21248b = "umeng_pcp";

    /* renamed from: c, reason: collision with root package name */
    private static final String f21249c = "mob";

    /* renamed from: d, reason: collision with root package name */
    private static final String f21250d = "em";

    /* renamed from: e, reason: collision with root package name */
    private static final String f21251e = "cp";

    /* renamed from: f, reason: collision with root package name */
    private static final String f21252f = "pk";

    /* renamed from: g, reason: collision with root package name */
    private static final String f21253g = "pv";

    /* renamed from: h, reason: collision with root package name */
    private static String[] f21254h = new String[2];

    /* renamed from: i, reason: collision with root package name */
    private static Object f21255i = new Object();

    /* renamed from: j, reason: collision with root package name */
    private static Map<String, Object> f21256j = new HashMap();

    public static void a(Context context, String str, String str2) {
        String[] strArr = f21254h;
        strArr[0] = str;
        strArr[1] = str2;
        if (context != null) {
            com.umeng.common.b.a(context).a(str, str2);
        }
    }

    public static void b(Context context) {
        String[] strArr = f21254h;
        strArr[0] = null;
        strArr[1] = null;
        if (context != null) {
            com.umeng.common.b.a(context).b();
        }
    }

    public static Map<String, Object> c(Context context) throws JSONException {
        SharedPreferences sharedPreferences = context.getSharedPreferences(f21248b, 0);
        String string = sharedPreferences.getString("cp", "");
        if (TextUtils.isEmpty(string)) {
            return null;
        }
        try {
            String str = new String(ax.a(Base64.decode(string, 0), UMConfigure.sAppkey.getBytes()));
            if (str.length() <= 0) {
                return null;
            }
            HashMap map = new HashMap();
            try {
                JSONArray jSONArray = (JSONArray) new JSONTokener(str).nextValue();
                for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                    JSONObject jSONObject = jSONArray.getJSONObject(i2);
                    map.put(jSONObject.getString("pk"), jSONObject.get("pv"));
                }
                sharedPreferences.edit().putString("cp", "").apply();
                return map;
            } catch (Throwable unused) {
                return map;
            }
        } catch (Throwable unused2) {
            return null;
        }
    }

    public static String[] a(Context context) {
        String[] strArrA;
        if (!TextUtils.isEmpty(f21254h[0]) && !TextUtils.isEmpty(f21254h[1])) {
            return f21254h;
        }
        if (context == null || (strArrA = com.umeng.common.b.a(context).a()) == null) {
            return null;
        }
        String[] strArr = f21254h;
        strArr[0] = strArrA[0];
        strArr[1] = strArrA[1];
        return strArr;
    }

    public static void b(String str) {
        String strEncodeToString;
        Context appContext = UMGlobalContext.getAppContext();
        if (appContext != null) {
            try {
                SharedPreferences sharedPreferences = appContext.getSharedPreferences(f21248b, 0);
                byte[] bArrA = ax.a(str.getBytes(), UMConfigure.sAppkey.getBytes());
                if (bArrA.length == 0) {
                    strEncodeToString = f.Q;
                } else {
                    strEncodeToString = Base64.encodeToString(bArrA, 0);
                }
                sharedPreferences.edit().putString("em", strEncodeToString).apply();
            } catch (Throwable unused) {
            }
        }
    }

    public static void a(String str) {
        String strEncodeToString;
        Context appContext = UMGlobalContext.getAppContext();
        if (appContext != null) {
            try {
                SharedPreferences sharedPreferences = appContext.getSharedPreferences(f21248b, 0);
                byte[] bArrA = ax.a(str.getBytes(), UMConfigure.sAppkey.getBytes());
                if (bArrA.length == 0) {
                    strEncodeToString = f.Q;
                } else {
                    strEncodeToString = Base64.encodeToString(bArrA, 0);
                }
                sharedPreferences.edit().putString(f21249c, strEncodeToString).apply();
            } catch (Throwable unused) {
            }
        }
    }

    public static String b() {
        Context appContext = UMGlobalContext.getAppContext();
        if (appContext == null) {
            return null;
        }
        try {
            SharedPreferences sharedPreferences = appContext.getSharedPreferences(f21248b, 0);
            String string = sharedPreferences.getString("em", "");
            if (f.Q.equals(string)) {
                sharedPreferences.edit().putString("em", "").apply();
                return "";
            }
            if (TextUtils.isEmpty(string)) {
                return null;
            }
            sharedPreferences.edit().putString("em", "").apply();
            return new String(ax.a(Base64.decode(string, 0), UMConfigure.sAppkey.getBytes()));
        } catch (Throwable unused) {
            return null;
        }
    }

    public static String a() {
        Context appContext = UMGlobalContext.getAppContext();
        if (appContext == null) {
            return null;
        }
        try {
            SharedPreferences sharedPreferences = appContext.getSharedPreferences(f21248b, 0);
            String string = sharedPreferences.getString(f21249c, "");
            if (f.Q.equals(string)) {
                sharedPreferences.edit().putString(f21249c, "").apply();
                return "";
            }
            if (TextUtils.isEmpty(string)) {
                return null;
            }
            sharedPreferences.edit().putString(f21249c, "").apply();
            return new String(ax.a(Base64.decode(string, 0), UMConfigure.sAppkey.getBytes()));
        } catch (Throwable unused) {
            return null;
        }
    }

    public static void a(Context context, Map<String, Object> map) {
        if (map != null) {
            JSONStringer jSONStringer = new JSONStringer();
            try {
                synchronized (f21255i) {
                    try {
                        jSONStringer.array();
                        for (String str : map.keySet()) {
                            jSONStringer.object();
                            jSONStringer.key("pk");
                            jSONStringer.value(str);
                            jSONStringer.key("pv");
                            jSONStringer.value(map.get(str));
                            jSONStringer.endObject();
                        }
                        jSONStringer.endArray();
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                SharedPreferences sharedPreferences = context.getSharedPreferences(f21248b, 0);
                sharedPreferences.edit().putString("cp", Base64.encodeToString(ax.a(jSONStringer.toString().getBytes(), UMConfigure.sAppkey.getBytes()), 0)).apply();
            } catch (Throwable unused) {
            }
        }
    }

    public static void a(String str, Object obj) {
        synchronized (f21255i) {
            try {
                if (f21256j.containsKey(str)) {
                    UMRTLog.i(UMRTLog.RTLOG_TAG, "更新账号自定义KV: key=" + str + "; val=" + obj);
                    f21256j.put(str, obj);
                    a(UMGlobalContext.getAppContext(), f21256j);
                } else {
                    if (f21256j.size() >= 20) {
                        UMRTLog.i(UMRTLog.RTLOG_TAG, "设置账号自定义KV: 已经设置20个KV键值对，忽略设置请求。");
                        MLog.e("userProfile: Only 20 user-defined key-value pairs can be configured, please check!");
                        return;
                    }
                    UMRTLog.i(UMRTLog.RTLOG_TAG, "设置账号自定义KV: key=" + str + "; val=" + obj);
                    f21256j.put(str, obj);
                    a(UMGlobalContext.getAppContext(), f21256j);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
