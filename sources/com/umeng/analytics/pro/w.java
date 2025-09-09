package com.umeng.analytics.pro;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.CoreProtocol;
import com.umeng.analytics.pro.aa;
import com.umeng.analytics.pro.g;
import com.umeng.analytics.pro.k;
import com.umeng.analytics.pro.q;
import com.umeng.analytics.process.UMProcessDBDatasSender;
import com.umeng.commonsdk.config.FieldManager;
import com.umeng.commonsdk.debug.UMRTLog;
import com.umeng.commonsdk.framework.UMWorkDispatch;
import com.umeng.commonsdk.service.UMGlobalContext;
import com.umeng.commonsdk.statistics.AnalyticsConstants;
import com.umeng.commonsdk.statistics.common.MLog;
import com.umeng.commonsdk.statistics.internal.PreferenceWrapper;
import com.umeng.commonsdk.utils.UMUtils;
import java.lang.reflect.Method;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class w implements aa.a {

    /* renamed from: a, reason: collision with root package name */
    public static final String f21933a = "session_start_time";

    /* renamed from: b, reason: collision with root package name */
    public static final String f21934b = "session_end_time";

    /* renamed from: c, reason: collision with root package name */
    public static final String f21935c = "session_id";

    /* renamed from: d, reason: collision with root package name */
    public static final String f21936d = "pre_session_id";

    /* renamed from: e, reason: collision with root package name */
    public static final String f21937e = "a_start_time";

    /* renamed from: f, reason: collision with root package name */
    public static final String f21938f = "a_end_time";

    /* renamed from: g, reason: collision with root package name */
    public static final String f21939g = "fg_count";

    /* renamed from: h, reason: collision with root package name */
    private static String f21940h = null;

    /* renamed from: i, reason: collision with root package name */
    private static Context f21941i = null;

    /* renamed from: j, reason: collision with root package name */
    private static boolean f21942j = false;

    /* renamed from: k, reason: collision with root package name */
    private static long f21943k = 0;

    /* renamed from: l, reason: collision with root package name */
    private static boolean f21944l = true;

    /* renamed from: m, reason: collision with root package name */
    private static long f21945m;

    private static class a {

        /* renamed from: a, reason: collision with root package name */
        private static final w f21946a = new w();

        private a() {
        }
    }

    public static w a() {
        return a.f21946a;
    }

    public static void b(Context context) {
        SharedPreferences sharedPreferences = PreferenceWrapper.getDefault(f21941i);
        if (sharedPreferences != null) {
            long j2 = sharedPreferences.getLong(f21939g, 0L);
            SharedPreferences.Editor editorEdit = sharedPreferences.edit();
            if (editorEdit != null) {
                editorEdit.putLong(f21939g, j2 + 1);
                editorEdit.commit();
            }
        }
    }

    private void d(Context context) {
        try {
            SharedPreferences.Editor editorEdit = PreferenceWrapper.getDefault(context).edit();
            editorEdit.putLong(f21939g, 0L);
            editorEdit.commit();
        } catch (Throwable unused) {
        }
    }

    private String e(Context context) {
        if (f21941i == null && context != null) {
            f21941i = context.getApplicationContext();
        }
        String strD = aa.a().d(f21941i);
        try {
            f(context);
            q.a(f21941i).d((Object) null);
        } catch (Throwable unused) {
        }
        return strD;
    }

    private void f(Context context) {
        q.a(context).b(context);
        q.a(context).d();
    }

    public void c(Context context, Object obj) {
        try {
            if (f21941i == null && context != null) {
                f21941i = context.getApplicationContext();
            }
            long jLongValue = ((Long) obj).longValue();
            SharedPreferences sharedPreferences = PreferenceWrapper.getDefault(context);
            if (sharedPreferences == null) {
                return;
            }
            if (sharedPreferences.getLong(f21937e, 0L) == 0) {
                MLog.e("onPause called before onResume");
                return;
            }
            SharedPreferences.Editor editorEdit = sharedPreferences.edit();
            UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> onEndSessionInternal: write activity end time = " + jLongValue);
            editorEdit.putLong(f21938f, jLongValue);
            editorEdit.putLong(f21934b, jLongValue);
            editorEdit.commit();
        } catch (Throwable unused) {
        }
    }

    private w() {
        aa.a().a(this);
    }

    public static long a(Context context) {
        try {
            return PreferenceWrapper.getDefault(context).getLong(f21939g, 0L);
        } catch (Throwable unused) {
            return 0L;
        }
    }

    public void a(Context context, long j2) {
        SharedPreferences.Editor editorEdit;
        SharedPreferences sharedPreferences = PreferenceWrapper.getDefault(f21941i);
        if (sharedPreferences == null || (editorEdit = sharedPreferences.edit()) == null) {
            return;
        }
        editorEdit.putLong(f21933a, j2);
        editorEdit.commit();
    }

    public void b(Context context, Object obj) {
        long jLongValue;
        try {
            if (f21941i == null) {
                f21941i = UMGlobalContext.getAppContext(context);
            }
            if (obj == null) {
                jLongValue = System.currentTimeMillis();
            } else {
                jLongValue = ((Long) obj).longValue();
            }
            SharedPreferences sharedPreferences = PreferenceWrapper.getDefault(f21941i);
            if (sharedPreferences == null) {
                return;
            }
            f21943k = sharedPreferences.getLong(f21938f, 0L);
            UMRTLog.i(UMRTLog.RTLOG_TAG, "------>>> lastActivityEndTime: " + f21943k);
            String string = sharedPreferences.getString(f.aF, "");
            String appVersionName = UMUtils.getAppVersionName(f21941i);
            SharedPreferences.Editor editorEdit = sharedPreferences.edit();
            if (editorEdit == null) {
                return;
            }
            if (!TextUtils.isEmpty(string) && !string.equals(appVersionName)) {
                UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> requestNewInstantSessionIf: version upgrade");
                editorEdit.putLong(f21933a, jLongValue);
                editorEdit.commit();
                q.a(f21941i).a((Object) null, true);
                UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> force generate new session: session id = " + aa.a().c(f21941i));
                f21942j = true;
                a(f21941i, jLongValue, true);
                return;
            }
            if (aa.a().e(f21941i)) {
                UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> More then 30 sec from last session.");
                f21942j = true;
                editorEdit.putLong(f21933a, jLongValue);
                editorEdit.commit();
                a(f21941i, jLongValue, false);
                return;
            }
            UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> less then 30 sec from last session, do nothing.");
            f21942j = false;
        } catch (Throwable unused) {
        }
    }

    public void a(Context context, Object obj) {
        SharedPreferences.Editor editorEdit;
        try {
            if (f21941i == null && context != null) {
                f21941i = context.getApplicationContext();
            }
            long jLongValue = ((Long) obj).longValue();
            SharedPreferences sharedPreferences = PreferenceWrapper.getDefault(f21941i);
            if (sharedPreferences == null || (editorEdit = sharedPreferences.edit()) == null) {
                return;
            }
            String string = sharedPreferences.getString(f.aF, "");
            String appVersionName = UMUtils.getAppVersionName(f21941i);
            if (TextUtils.isEmpty(string)) {
                editorEdit.putInt("versioncode", Integer.parseInt(UMUtils.getAppVersionCode(context)));
                editorEdit.putString(f.aF, appVersionName);
                editorEdit.commit();
            } else if (!string.equals(appVersionName)) {
                UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> onStartSessionInternal: upgrade version: " + string + "-> " + appVersionName);
                int i2 = sharedPreferences.getInt("versioncode", 0);
                String string2 = sharedPreferences.getString("pre_date", "");
                String string3 = sharedPreferences.getString("pre_version", "");
                String string4 = sharedPreferences.getString(f.aF, "");
                editorEdit.putInt("versioncode", Integer.parseInt(UMUtils.getAppVersionCode(context)));
                editorEdit.putString(f.aF, appVersionName);
                editorEdit.putString("vers_date", string2);
                editorEdit.putString("vers_pre_version", string3);
                editorEdit.putString("cur_version", string4);
                editorEdit.putInt("vers_code", i2);
                editorEdit.putString("vers_name", string);
                editorEdit.commit();
                if (f21944l) {
                    f21944l = false;
                }
                if (f21942j) {
                    f21942j = false;
                    b(f21941i, jLongValue, true);
                    b(f21941i, jLongValue);
                    return;
                }
                return;
            }
            if (f21942j) {
                f21942j = false;
                if (f21944l) {
                    f21944l = false;
                }
                f21940h = e(context);
                MLog.d("创建新会话: " + f21940h);
                UMRTLog.i(UMRTLog.RTLOG_TAG, "mSessionChanged flag has been set, Start new session: " + f21940h);
                return;
            }
            f21940h = sharedPreferences.getString("session_id", null);
            editorEdit.putLong(f21937e, jLongValue);
            editorEdit.putLong(f21938f, 0L);
            editorEdit.commit();
            MLog.d("延续上一个会话: " + f21940h);
            UMRTLog.i(UMRTLog.RTLOG_TAG, "Extend current session: " + f21940h);
            if (f21944l) {
                f21944l = false;
                if (FieldManager.allow(com.umeng.commonsdk.utils.d.E)) {
                    Context context2 = f21941i;
                    UMWorkDispatch.sendEventEx(context2, q.a.E, CoreProtocol.getInstance(context2), null, 0L);
                }
            }
            f(context);
            q.a(f21941i).a(false);
        } catch (Throwable unused) {
        }
    }

    @Deprecated
    public String c(Context context) {
        try {
            if (f21940h == null) {
                return PreferenceWrapper.getDefault(context).getString("session_id", null);
            }
        } catch (Throwable unused) {
        }
        return f21940h;
    }

    @Deprecated
    public String c() {
        return c(f21941i);
    }

    public boolean b(Context context, long j2, boolean z2) {
        String strA;
        long j3;
        try {
            SharedPreferences sharedPreferences = PreferenceWrapper.getDefault(context);
            if (sharedPreferences == null || (strA = aa.a().a(f21941i)) == null) {
                return false;
            }
            long j4 = sharedPreferences.getLong(f21937e, 0L);
            long j5 = sharedPreferences.getLong(f21938f, 0L);
            if (j4 <= 0 || j5 != 0) {
                return false;
            }
            try {
                if (z2) {
                    j3 = f21943k;
                    if (j3 == 0) {
                        UMRTLog.i(UMRTLog.RTLOG_TAG, "------>>> lastActivityEndTime = 0, In-app upgrade, use currentTime: = " + j2);
                        j3 = j2;
                    } else {
                        UMRTLog.i(UMRTLog.RTLOG_TAG, "------>>> lastActivityEndTime != 0, app upgrade, use lastActivityEndTime: = " + f21943k);
                    }
                    c(f21941i, Long.valueOf(j3));
                } else {
                    c(f21941i, Long.valueOf(j2));
                    j3 = j2;
                }
                JSONObject jSONObject = new JSONObject();
                if (z2) {
                    jSONObject.put(g.d.a.f21754g, j3);
                } else {
                    jSONObject.put(g.d.a.f21754g, j2);
                }
                JSONObject jSONObjectB = com.umeng.analytics.b.a().b();
                if (jSONObjectB != null && jSONObjectB.length() > 0) {
                    jSONObject.put("__sp", jSONObjectB);
                }
                JSONObject jSONObjectC = com.umeng.analytics.b.a().c();
                if (jSONObjectC != null && jSONObjectC.length() > 0) {
                    jSONObject.put("__pp", jSONObjectC);
                }
                if (FieldManager.allow(com.umeng.commonsdk.utils.d.E)) {
                    UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>>*** foregroundCount = " + f21945m);
                    jSONObject.put(g.d.a.f21755h, f21945m);
                    f21945m = 0L;
                } else {
                    jSONObject.put(g.d.a.f21755h, 0L);
                }
                k.a(context).a(strA, jSONObject, k.a.END);
                q.a(f21941i).e();
            } catch (Throwable unused) {
            }
            return true;
        } catch (Throwable unused2) {
            return false;
        }
    }

    public String a(Context context, long j2, boolean z2) {
        String strB = aa.a().b(context);
        UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> onInstantSessionInternal: current session id = " + strB);
        if (TextUtils.isEmpty(strB)) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("__e", j2);
            JSONObject jSONObjectB = com.umeng.analytics.b.a().b();
            if (jSONObjectB != null && jSONObjectB.length() > 0) {
                jSONObject.put("__sp", jSONObjectB);
            }
            JSONObject jSONObjectC = com.umeng.analytics.b.a().c();
            if (jSONObjectC != null && jSONObjectC.length() > 0) {
                jSONObject.put("__pp", jSONObjectC);
            }
            k.a(context).a(strB, jSONObject, k.a.INSTANTSESSIONBEGIN);
            q.a(context).a(jSONObject, z2);
        } catch (Throwable unused) {
        }
        return strB;
    }

    public void b(Context context, long j2) {
        if (PreferenceWrapper.getDefault(context) == null) {
            return;
        }
        try {
            q.a(f21941i).c((Object) null);
        } catch (Throwable unused) {
        }
    }

    @Deprecated
    public String b() {
        return f21940h;
    }

    @Override // com.umeng.analytics.pro.aa.a
    public void a(String str, String str2, long j2, long j3, long j4) throws JSONException {
        a(f21941i, str2, j2, j3, j4);
        UMRTLog.i(UMRTLog.RTLOG_TAG, "saveSessionToDB: complete");
        if (AnalyticsConstants.SUB_PROCESS_EVENT) {
            Context context = f21941i;
            UMWorkDispatch.sendEvent(context, UMProcessDBDatasSender.UM_PROCESS_EVENT_KEY, UMProcessDBDatasSender.getInstance(context), Long.valueOf(System.currentTimeMillis()));
        }
    }

    @Override // com.umeng.analytics.pro.aa.a
    public void a(String str, long j2, long j3, long j4) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        a(str, j2);
    }

    private void a(Context context, String str, long j2, long j3, long j4) throws JSONException {
        if (TextUtils.isEmpty(f21940h)) {
            f21940h = aa.a().a(f21941i);
        }
        if (TextUtils.isEmpty(str) || str.equals(f21940h)) {
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(g.d.a.f21754g, j3);
            jSONObject.put(g.d.a.f21755h, j4);
            JSONObject jSONObjectB = com.umeng.analytics.b.a().b();
            if (jSONObjectB != null && jSONObjectB.length() > 0) {
                jSONObject.put("__sp", jSONObjectB);
            }
            JSONObject jSONObjectC = com.umeng.analytics.b.a().c();
            if (jSONObjectC != null && jSONObjectC.length() > 0) {
                jSONObject.put("__pp", jSONObjectC);
            }
            k.a(context).a(f21940h, jSONObject, k.a.END);
        } catch (Exception unused) {
        }
        try {
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("__e", j2);
            k.a(context).a(str, jSONObject2, k.a.BEGIN);
            if (FieldManager.allow(com.umeng.commonsdk.utils.d.E)) {
                f21945m = j4;
                d(context);
                Context context2 = f21941i;
                UMWorkDispatch.sendEventEx(context2, q.a.E, CoreProtocol.getInstance(context2), null, 0L);
            }
        } catch (Exception unused2) {
        }
        f21940h = str;
    }

    private void a(String str, long j2) {
        SharedPreferences sharedPreferences = PreferenceWrapper.getDefault(f21941i);
        if (sharedPreferences == null) {
            return;
        }
        long j3 = sharedPreferences.getLong(f21934b, 0L);
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("__ii", str);
            jSONObject.put("__e", j2);
            jSONObject.put(g.d.a.f21754g, j3);
            double[] location = AnalyticsConfig.getLocation();
            if (location != null) {
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("lat", location[0]);
                jSONObject2.put("lng", location[1]);
                jSONObject2.put("ts", System.currentTimeMillis());
                jSONObject.put(g.d.a.f21752e, jSONObject2);
            }
            Class<?> cls = Class.forName("android.net.TrafficStats");
            Class<?> cls2 = Integer.TYPE;
            Method method = cls.getMethod("getUidRxBytes", cls2);
            Method method2 = cls.getMethod("getUidTxBytes", cls2);
            int i2 = f21941i.getApplicationInfo().uid;
            if (i2 == -1) {
                return;
            }
            long jLongValue = ((Long) method.invoke(null, Integer.valueOf(i2))).longValue();
            long jLongValue2 = ((Long) method2.invoke(null, Integer.valueOf(i2))).longValue();
            if (jLongValue > 0 && jLongValue2 > 0) {
                JSONObject jSONObject3 = new JSONObject();
                jSONObject3.put(f.H, jLongValue);
                jSONObject3.put(f.G, jLongValue2);
                jSONObject.put(g.d.a.f21751d, jSONObject3);
            }
            k.a(f21941i).a(str, jSONObject, k.a.NEWSESSION);
            x.a(f21941i);
            n.c(f21941i);
        } catch (Throwable unused) {
        }
    }
}
