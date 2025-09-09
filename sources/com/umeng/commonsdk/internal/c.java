package com.umeng.commonsdk.internal;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.CoreProtocol;
import com.umeng.analytics.pro.ay;
import com.umeng.analytics.pro.az;
import com.umeng.analytics.pro.ba;
import com.umeng.analytics.pro.bc;
import com.umeng.analytics.pro.be;
import com.umeng.analytics.pro.bf;
import com.umeng.analytics.pro.bg;
import com.umeng.analytics.pro.q;
import com.umeng.commonsdk.UMConfigureImpl;
import com.umeng.commonsdk.UMInnerManager;
import com.umeng.commonsdk.config.FieldManager;
import com.umeng.commonsdk.debug.UMRTLog;
import com.umeng.commonsdk.framework.UMEnvelopeBuild;
import com.umeng.commonsdk.framework.UMLogDataProtocol;
import com.umeng.commonsdk.framework.UMWorkDispatch;
import com.umeng.commonsdk.listener.OnGetOaidListener;
import com.umeng.commonsdk.service.UMGlobalContext;
import com.umeng.commonsdk.statistics.AnalyticsConstants;
import com.umeng.commonsdk.statistics.SdkVersion;
import com.umeng.commonsdk.statistics.UMServerURL;
import com.umeng.commonsdk.statistics.common.DeviceConfig;
import com.umeng.commonsdk.statistics.idtracking.h;
import com.umeng.commonsdk.utils.UMUtils;
import com.umeng.commonsdk.utils.d;
import com.umeng.commonsdk.utils.onMessageSendListener;
import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class c implements UMLogDataProtocol {

    /* renamed from: b, reason: collision with root package name */
    public static final String f22267b = "preInitInvokedFlag";

    /* renamed from: c, reason: collision with root package name */
    public static final String f22268c = "policyGrantInvokedFlag";

    /* renamed from: d, reason: collision with root package name */
    public static final String f22269d = "policyGrantResult";

    /* renamed from: f, reason: collision with root package name */
    private static int f22270f = 1;

    /* renamed from: e, reason: collision with root package name */
    private Context f22278e;

    /* renamed from: a, reason: collision with root package name */
    public static final String f22266a = ay.b().b(ay.f21382q);

    /* renamed from: g, reason: collision with root package name */
    private static Class<?> f22271g = null;

    /* renamed from: h, reason: collision with root package name */
    private static Method f22272h = null;

    /* renamed from: i, reason: collision with root package name */
    private static Method f22273i = null;

    /* renamed from: j, reason: collision with root package name */
    private static Method f22274j = null;

    /* renamed from: k, reason: collision with root package name */
    private static volatile String f22275k = "";

    /* renamed from: l, reason: collision with root package name */
    private static volatile String f22276l = "";

    /* renamed from: m, reason: collision with root package name */
    private static boolean f22277m = false;

    static {
        c();
    }

    public c(Context context) {
        if (context != null) {
            this.f22278e = context.getApplicationContext();
        }
    }

    public static String b() {
        Method method;
        if (!TextUtils.isEmpty(f22276l)) {
            return f22276l;
        }
        Class<?> cls = f22271g;
        if (cls == null || (method = f22272h) == null || f22274j == null) {
            return "";
        }
        try {
            Object objInvoke = method.invoke(cls, null);
            if (objInvoke == null) {
                return "";
            }
            String str = (String) f22274j.invoke(objInvoke, null);
            try {
                f22276l = str;
            } catch (Throwable unused) {
            }
            return str;
        } catch (Throwable unused2) {
            return "";
        }
    }

    private static void c() {
        try {
            Class<?> cls = Class.forName("com.umeng.umzid.ZIDManager");
            f22271g = cls;
            Method declaredMethod = cls.getDeclaredMethod("getInstance", null);
            if (declaredMethod != null) {
                f22272h = declaredMethod;
            }
            Method declaredMethod2 = f22271g.getDeclaredMethod("getZID", Context.class);
            if (declaredMethod2 != null) {
                f22273i = declaredMethod2;
            }
            Method declaredMethod3 = f22271g.getDeclaredMethod("getSDKVersion", null);
            if (declaredMethod3 != null) {
                f22274j = declaredMethod3;
            }
        } catch (Throwable unused) {
        }
    }

    private void d() {
        be beVarA = be.a(this.f22278e);
        bf bfVarA = beVarA.a(bg.f21482c);
        if (bfVarA != null) {
            UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> [有状态]二级缓存记录构建成真正信封。");
            try {
                String str = bfVarA.f21474a;
                String str2 = bfVarA.f21475b;
                JSONObject jSONObjectA = new com.umeng.commonsdk.statistics.b().a(this.f22278e.getApplicationContext(), new JSONObject(bfVarA.f21476c), new JSONObject(bfVarA.f21477d), bfVarA.f21478e, str2, bfVarA.f21479f);
                if (jSONObjectA == null || !jSONObjectA.has("exception")) {
                    UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> [有状态]二级缓存记录构建真正信封 成功! 删除二级缓存记录。");
                } else {
                    UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> [有状态]二级缓存记录构建真正信封 失败。删除二级缓存记录");
                }
                beVarA.a(bg.f21482c, str);
                beVarA.b();
            } catch (Throwable unused) {
            }
        }
    }

    private void e() {
        if (f22277m) {
            if (FieldManager.allow(d.G)) {
                return;
            }
            f22277m = false;
        } else if (FieldManager.allow(d.G)) {
            f22277m = true;
            a(this.f22278e, new OnGetOaidListener() { // from class: com.umeng.commonsdk.internal.c.4
                @Override // com.umeng.commonsdk.listener.OnGetOaidListener
                public void onGetOaid(String str) {
                    UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> OAID云控参数更新(不采集->采集)：采集完成");
                    if (TextUtils.isEmpty(str)) {
                        UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> oaid返回null或者空串，不需要 伪冷启动。");
                        return;
                    }
                    try {
                        SharedPreferences sharedPreferences = c.this.f22278e.getSharedPreferences(h.f22404a, 0);
                        if (sharedPreferences != null) {
                            SharedPreferences.Editor editorEdit = sharedPreferences.edit();
                            editorEdit.putString(h.f22405b, str);
                            editorEdit.commit();
                        }
                    } catch (Throwable unused) {
                    }
                    UMWorkDispatch.sendEvent(c.this.f22278e, a.f22259w, b.a(c.this.f22278e).a(), null);
                }
            });
        }
    }

    private void f() {
        if (FieldManager.allow(d.G)) {
            f22277m = true;
            UMConfigureImpl.registerInterruptFlag();
            UMConfigureImpl.init(this.f22278e);
            f22270f++;
            UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> 要读取 oaid，需等待读取结果.");
            UMConfigureImpl.registerMessageSendListener(new onMessageSendListener() { // from class: com.umeng.commonsdk.internal.c.5
                @Override // com.umeng.commonsdk.utils.onMessageSendListener
                public void onMessageSend() {
                    if (c.this.f22278e != null) {
                        UMWorkDispatch.sendEvent(c.this.f22278e, a.f22260x, b.a(c.this.f22278e).a(), null);
                    }
                    UMConfigureImpl.removeMessageSendListener(this);
                }
            });
            a(this.f22278e, true);
        }
    }

    private void g() {
        if (f22270f <= 0) {
            h();
            e(this.f22278e);
        }
    }

    private void h() {
        UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> 真实构建条件满足，开始构建业务信封。");
        if (UMUtils.isMainProgress(this.f22278e)) {
            f(this.f22278e);
            UMInnerManager.sendInnerPackage(this.f22278e);
            if (!FieldManager.allow(d.at) && SdkVersion.SDK_TYPE == 0 && UMUtils.isMainProgress(this.f22278e)) {
                Context context = this.f22278e;
                UMWorkDispatch.sendEvent(context, a.G, b.a(context).a(), null, 5000L);
            }
            Context context2 = this.f22278e;
            UMWorkDispatch.sendEvent(context2, q.a.f21904z, CoreProtocol.getInstance(context2), null);
            Context context3 = this.f22278e;
            UMWorkDispatch.sendEvent(context3, a.f22256t, b.a(context3).a(), null);
        }
    }

    @Override // com.umeng.commonsdk.framework.UMLogDataProtocol
    public void removeCacheData(Object obj) {
    }

    @Override // com.umeng.commonsdk.framework.UMLogDataProtocol
    public JSONObject setupReportData(long j2) {
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:98:0x02b9  */
    @Override // com.umeng.commonsdk.framework.UMLogDataProtocol
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void workEvent(java.lang.Object r10, int r11) throws org.json.JSONException, java.lang.IllegalAccessException, java.lang.ClassNotFoundException, java.lang.IllegalArgumentException, java.lang.reflect.InvocationTargetException {
        /*
            Method dump skipped, instructions count: 1054
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.commonsdk.internal.c.workEvent(java.lang.Object, int):void");
    }

    public String a() {
        Method method;
        if (!TextUtils.isEmpty(f22275k)) {
            return f22275k;
        }
        Class<?> cls = f22271g;
        if (cls == null || (method = f22272h) == null || f22273i == null) {
            return "";
        }
        try {
            Object objInvoke = method.invoke(cls, null);
            if (objInvoke == null) {
                return "";
            }
            String str = (String) f22273i.invoke(objInvoke, this.f22278e);
            try {
                f22275k = str;
            } catch (Throwable unused) {
            }
            return str;
        } catch (Throwable unused2) {
            return "";
        }
    }

    private void b(Context context) {
        try {
            String strImprintProperty = UMEnvelopeBuild.imprintProperty(context, bc.f21408g, "");
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(com.umeng.commonsdk.statistics.b.a("appkey"), UMGlobalContext.getInstance(context).getAppkey());
            jSONObject.put(com.umeng.commonsdk.statistics.b.a(bc.f21408g), strImprintProperty);
            JSONObject jSONObjectBuildSilentEnvelopeWithExtHeader = UMEnvelopeBuild.buildSilentEnvelopeWithExtHeader(context, jSONObject, null, UMServerURL.SILENT_HEART_BEAT);
            if (jSONObjectBuildSilentEnvelopeWithExtHeader == null || !jSONObjectBuildSilentEnvelopeWithExtHeader.has("exception")) {
                UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> 构建心跳报文 成功!!!");
            } else {
                UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> 构建心跳报文失败.");
            }
        } catch (Throwable unused) {
        }
    }

    private void a(Context context) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(com.umeng.commonsdk.statistics.b.a("appkey"), UMGlobalContext.getInstance(context).getAppkey());
            jSONObject.put(com.umeng.commonsdk.statistics.b.a("app_version"), UMGlobalContext.getInstance(context).getAppVersion());
            jSONObject.put(com.umeng.commonsdk.statistics.b.a("os"), AnalyticsConstants.SDK_TYPE);
            JSONObject jSONObjectBuildZeroEnvelopeWithExtHeader = UMEnvelopeBuild.buildZeroEnvelopeWithExtHeader(context, jSONObject, null, UMServerURL.ZCFG_PATH);
            if (jSONObjectBuildZeroEnvelopeWithExtHeader == null || !jSONObjectBuildZeroEnvelopeWithExtHeader.has("exception")) {
                UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> 构建零号报文 成功!!!");
            } else {
                UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> 构建零号报文失败.");
            }
        } catch (Throwable unused) {
        }
    }

    private void e(Context context) {
        Object objInvoke;
        Method declaredMethod;
        Context applicationContext = context.getApplicationContext();
        String appkey = UMUtils.getAppkey(context);
        try {
            Class<?> clsA = a("com.umeng.umzid.ZIDManager");
            Method declaredMethod2 = clsA.getDeclaredMethod("getInstance", null);
            if (declaredMethod2 == null || (objInvoke = declaredMethod2.invoke(clsA, null)) == null || (declaredMethod = clsA.getDeclaredMethod("init", Context.class, String.class, a("com.umeng.umzid.IZIDCompletionCallback"))) == null) {
                return;
            }
            declaredMethod.invoke(objInvoke, applicationContext, appkey, null);
        } catch (Throwable unused) {
        }
    }

    private static void c(final Context context) {
        if (FieldManager.allow(d.G)) {
            a(context, new OnGetOaidListener() { // from class: com.umeng.commonsdk.internal.c.3
                @Override // com.umeng.commonsdk.listener.OnGetOaidListener
                public void onGetOaid(String str) {
                    if (TextUtils.isEmpty(str)) {
                        return;
                    }
                    try {
                        SharedPreferences sharedPreferences = context.getSharedPreferences(h.f22404a, 0);
                        if (sharedPreferences == null || sharedPreferences.getString(h.f22405b, "").equalsIgnoreCase(str)) {
                            return;
                        }
                        UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> 更新本地缓存OAID");
                        SharedPreferences.Editor editorEdit = sharedPreferences.edit();
                        editorEdit.putString(h.f22405b, str);
                        editorEdit.commit();
                    } catch (Throwable unused) {
                    }
                }
            });
        }
    }

    private static void f(Context context) {
        File file = new File(context.getFilesDir().getAbsolutePath() + File.separator + bg.f21491l);
        if (file.exists()) {
            return;
        }
        try {
            file.createNewFile();
        } catch (Throwable unused) {
        }
    }

    public static void a(final Context context, final boolean z2) {
        new Thread(new Runnable() { // from class: com.umeng.commonsdk.internal.c.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    SharedPreferences sharedPreferences = context.getSharedPreferences(h.f22404a, 0);
                    long jCurrentTimeMillis = System.currentTimeMillis();
                    String strA = az.a(context);
                    long jCurrentTimeMillis2 = System.currentTimeMillis();
                    if (!TextUtils.isEmpty(strA) && sharedPreferences != null) {
                        SharedPreferences.Editor editorEdit = sharedPreferences.edit();
                        editorEdit.putString(h.f22406c, (jCurrentTimeMillis2 - jCurrentTimeMillis) + "");
                        editorEdit.commit();
                    }
                    if (sharedPreferences != null) {
                        SharedPreferences.Editor editorEdit2 = sharedPreferences.edit();
                        editorEdit2.putString(h.f22405b, strA);
                        editorEdit2.commit();
                    }
                    if (DeviceConfig.isHonorDevice()) {
                        if (ba.c()) {
                            String strC = az.c(context);
                            if (sharedPreferences != null) {
                                SharedPreferences.Editor editorEdit3 = sharedPreferences.edit();
                                editorEdit3.putString(com.umeng.commonsdk.statistics.idtracking.c.f22383b, strC);
                                editorEdit3.commit();
                            }
                        } else if (ba.b() && sharedPreferences != null) {
                            SharedPreferences.Editor editorEdit4 = sharedPreferences.edit();
                            editorEdit4.putString(com.umeng.commonsdk.statistics.idtracking.c.f22383b, strA);
                            editorEdit4.commit();
                        }
                    }
                    if (z2) {
                        UMConfigureImpl.removeInterruptFlag();
                    }
                } catch (Throwable unused) {
                }
            }
        }).start();
    }

    private void d(Context context) {
        long jLongValue;
        long jLongValue2;
        if (context == null) {
            return;
        }
        String str = AnalyticsConfig.RTD_SP_FILE;
        String strA = com.umeng.common.b.a(context, str, AnalyticsConfig.DEBUG_KEY);
        if (TextUtils.isEmpty(strA)) {
            return;
        }
        String strA2 = com.umeng.common.b.a(context, str, "startTime");
        String strA3 = com.umeng.common.b.a(context, str, "period");
        if (TextUtils.isEmpty(strA2)) {
            jLongValue = 0;
        } else {
            try {
                jLongValue = Long.valueOf(strA2).longValue();
            } catch (Throwable unused) {
            }
        }
        if (TextUtils.isEmpty(strA3)) {
            jLongValue2 = 0;
        } else {
            try {
                jLongValue2 = Long.valueOf(strA3).longValue();
            } catch (Throwable unused2) {
            }
        }
        if (jLongValue == 0 || jLongValue2 == 0) {
            UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> [RTD]本地缓存startTime或者duration值无效，清除缓存数据");
            com.umeng.common.b.a(context, AnalyticsConfig.RTD_SP_FILE);
            return;
        }
        if (System.currentTimeMillis() - jLongValue > jLongValue2 * 60000) {
            UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> [RTD]本地缓存dk值已经超时，清除缓存数据。");
            com.umeng.common.b.a(context, AnalyticsConfig.RTD_SP_FILE);
            if (AnalyticsConfig.isRealTimeDebugMode()) {
                AnalyticsConfig.turnOffRealTimeDebug();
                return;
            }
            return;
        }
        HashMap map = new HashMap();
        map.put(AnalyticsConfig.DEBUG_KEY, strA);
        if (AnalyticsConfig.isRealTimeDebugMode()) {
            return;
        }
        UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> [RTD]本地缓存dk值在有效期内，切换到埋点验证模式。");
        AnalyticsConfig.turnOnRealTimeDebug(map);
    }

    private static void a(Context context, final OnGetOaidListener onGetOaidListener) {
        if (context == null) {
            return;
        }
        final Context applicationContext = context.getApplicationContext();
        new Thread(new Runnable() { // from class: com.umeng.commonsdk.internal.c.2
            @Override // java.lang.Runnable
            public void run() {
                String strA = az.a(applicationContext);
                OnGetOaidListener onGetOaidListener2 = onGetOaidListener;
                if (onGetOaidListener2 != null) {
                    onGetOaidListener2.onGetOaid(strA);
                }
            }
        }).start();
    }

    private static Class<?> a(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException unused) {
            return null;
        }
    }
}
