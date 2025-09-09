package com.umeng.analytics.pro;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Process;
import android.text.TextUtils;
import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.pro.g;
import com.umeng.analytics.pro.k;
import com.umeng.analytics.vshelper.PageNameMonitor;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.config.FieldManager;
import com.umeng.commonsdk.debug.UMLog;
import com.umeng.commonsdk.debug.UMRTLog;
import com.umeng.commonsdk.statistics.common.DeviceConfig;
import com.umeng.commonsdk.utils.UMUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONObject;

@TargetApi(14)
/* loaded from: classes4.dex */
public class n {

    /* renamed from: a, reason: collision with root package name */
    public static String f21826a;

    /* renamed from: b, reason: collision with root package name */
    boolean f21835b;

    /* renamed from: c, reason: collision with root package name */
    boolean f21836c;

    /* renamed from: f, reason: collision with root package name */
    com.umeng.analytics.vshelper.a f21837f;

    /* renamed from: g, reason: collision with root package name */
    Application.ActivityLifecycleCallbacks f21838g;

    /* renamed from: h, reason: collision with root package name */
    private final Map<String, Long> f21839h;

    /* renamed from: l, reason: collision with root package name */
    private boolean f21840l;

    /* renamed from: m, reason: collision with root package name */
    private int f21841m;

    /* renamed from: n, reason: collision with root package name */
    private int f21842n;

    /* renamed from: i, reason: collision with root package name */
    private static JSONArray f21829i = new JSONArray();

    /* renamed from: j, reason: collision with root package name */
    private static Object f21830j = new Object();

    /* renamed from: k, reason: collision with root package name */
    private static Application f21831k = null;

    /* renamed from: d, reason: collision with root package name */
    static String f21827d = null;

    /* renamed from: e, reason: collision with root package name */
    static int f21828e = -1;

    /* renamed from: o, reason: collision with root package name */
    private static boolean f21832o = true;

    /* renamed from: p, reason: collision with root package name */
    private static Object f21833p = new Object();

    /* renamed from: q, reason: collision with root package name */
    private static bm f21834q = new com.umeng.analytics.vshelper.b();

    private static class a {

        /* renamed from: a, reason: collision with root package name */
        private static final n f21844a = new n();

        private a() {
        }
    }

    static /* synthetic */ int a(n nVar) {
        int i2 = nVar.f21842n;
        nVar.f21842n = i2 - 1;
        return i2;
    }

    static /* synthetic */ int b(n nVar) {
        int i2 = nVar.f21841m;
        nVar.f21841m = i2 - 1;
        return i2;
    }

    static /* synthetic */ int e(n nVar) {
        int i2 = nVar.f21842n;
        nVar.f21842n = i2 + 1;
        return i2;
    }

    static /* synthetic */ int f(n nVar) {
        int i2 = nVar.f21841m;
        nVar.f21841m = i2 + 1;
        return i2;
    }

    private void g() {
        if (this.f21840l) {
            return;
        }
        this.f21840l = true;
        if (f21831k != null) {
            f21831k.registerActivityLifecycleCallbacks(this.f21838g);
        }
    }

    private n() {
        this.f21839h = new HashMap();
        this.f21840l = false;
        this.f21835b = false;
        this.f21836c = false;
        this.f21841m = 0;
        this.f21842n = 0;
        this.f21837f = PageNameMonitor.getInstance();
        this.f21838g = new Application.ActivityLifecycleCallbacks() { // from class: com.umeng.analytics.pro.n.1
            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityCreated(Activity activity, Bundle bundle) {
                n.f21834q.a(activity, bundle);
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityDestroyed(Activity activity) {
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityPaused(Activity activity) {
                if (FieldManager.allow(com.umeng.commonsdk.utils.d.F)) {
                    UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> onActivityPaused: FirstResumeTrigger enabled.");
                    synchronized (n.f21833p) {
                        try {
                            if (n.f21832o) {
                                return;
                            }
                        } finally {
                        }
                    }
                } else {
                    UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> onActivityPaused: FirstResumeTrigger disabled.");
                }
                if (UMConfigure.AUTO_ACTIVITY_PAGE_COLLECTION != MobclickAgent.PageMode.AUTO) {
                    if (UMConfigure.AUTO_ACTIVITY_PAGE_COLLECTION == MobclickAgent.PageMode.MANUAL) {
                        com.umeng.analytics.b.a().i();
                    }
                } else {
                    n.this.c(activity);
                    com.umeng.analytics.b.a().i();
                    n.this.f21835b = false;
                    n.f21834q.d(activity);
                }
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityResumed(Activity activity) {
                if (FieldManager.allow(com.umeng.commonsdk.utils.d.F)) {
                    UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> onActivityResumed: FirstResumeTrigger enabled.");
                    synchronized (n.f21833p) {
                        try {
                            if (n.f21832o) {
                                boolean unused = n.f21832o = false;
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    n.this.a(activity);
                } else {
                    UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> onActivityResumed: FirstResumeTrigger disabled.");
                    n.this.a(activity);
                }
                n.f21834q.c(activity);
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityStarted(Activity activity) {
                if (activity != null) {
                    if (n.this.f21841m <= 0) {
                        if (n.f21827d == null) {
                            n.f21827d = UUID.randomUUID().toString();
                        }
                        if (n.f21828e == -1) {
                            n.f21828e = activity.isTaskRoot() ? 1 : 0;
                        }
                        if (n.f21828e == 0 && UMUtils.isMainProgress(activity)) {
                            HashMap map = new HashMap();
                            map.put("activityName", activity.toString());
                            map.put("pid", Integer.valueOf(Process.myPid()));
                            map.put("isMainProcess", Integer.valueOf(UMUtils.isMainProgress(activity) ? 1 : 0));
                            com.umeng.analytics.b bVarA = com.umeng.analytics.b.a();
                            if (bVarA != null) {
                                bVarA.a((Context) activity, "$$_onUMengEnterForegroundInitError", (Map<String, Object>) map);
                            }
                            n.f21828e = -2;
                            if (UMConfigure.isDebugLog()) {
                                UMLog.mutlInfo(2, l.ar);
                            }
                        } else if (n.f21828e == 1 || !UMUtils.isMainProgress(activity)) {
                            HashMap map2 = new HashMap();
                            map2.put("pairUUID", n.f21827d);
                            map2.put("pid", Integer.valueOf(Process.myPid()));
                            map2.put("isMainProcess", Integer.valueOf(UMUtils.isMainProgress(activity) ? 1 : 0));
                            map2.put("activityName", activity.toString());
                            if (com.umeng.analytics.b.a() != null) {
                                com.umeng.analytics.b.a().a((Context) activity, "$$_onUMengEnterForeground", (Map<String, Object>) map2);
                            }
                        }
                    }
                    if (n.this.f21842n < 0) {
                        n.e(n.this);
                    } else {
                        n.f(n.this);
                    }
                }
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityStopped(Activity activity) {
                UMLog uMLog = UMConfigure.umDebugLog;
                MobclickAgent.PageMode pageMode = MobclickAgent.PageMode.AUTO;
                if (activity != null) {
                    if (activity.isChangingConfigurations()) {
                        n.a(n.this);
                        return;
                    }
                    n.b(n.this);
                    if (n.this.f21841m <= 0) {
                        if (n.f21828e == 0 && UMUtils.isMainProgress(activity)) {
                            return;
                        }
                        int i2 = n.f21828e;
                        if (i2 == 1 || (i2 == 0 && !UMUtils.isMainProgress(activity))) {
                            HashMap map = new HashMap();
                            map.put("pairUUID", n.f21827d);
                            map.put("reason", "Normal");
                            map.put("pid", Integer.valueOf(Process.myPid()));
                            map.put("isMainProcess", Integer.valueOf(UMUtils.isMainProgress(activity) ? 1 : 0));
                            map.put("activityName", activity.toString());
                            com.umeng.analytics.b bVarA = com.umeng.analytics.b.a();
                            if (bVarA != null) {
                                bVarA.a((Context) activity, "$$_onUMengEnterBackground", (Map<String, Object>) map);
                            }
                            if (n.f21827d != null) {
                                n.f21827d = null;
                            }
                        }
                    }
                }
            }
        };
        synchronized (this) {
            try {
                if (f21831k != null) {
                    g();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void c() {
        c((Activity) null);
        b();
    }

    public void b(Context context) {
        synchronized (f21833p) {
            if (f21832o) {
                f21832o = false;
                Activity globleActivity = DeviceConfig.getGlobleActivity(context);
                if (globleActivity == null) {
                    UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> init触发onResume: 无前台Activity，直接退出。");
                    return;
                }
                UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> init触发onResume: 补救成功，前台Activity名：" + globleActivity.getLocalClassName());
                a(globleActivity);
                return;
            }
            UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> init触发onResume: firstResumeCall = false，直接返回。");
        }
    }

    public static void c(Context context) {
        String string;
        if (context != null) {
            try {
                JSONObject jSONObject = new JSONObject();
                synchronized (f21830j) {
                    string = f21829i.toString();
                    f21829i = new JSONArray();
                }
                if (string.length() > 0) {
                    jSONObject.put(g.d.a.f21750c, new JSONArray(string));
                    k.a(context).a(w.a().c(), jSONObject, k.a.AUTOPAGE);
                }
            } catch (Throwable unused) {
            }
        }
    }

    public boolean a() {
        return this.f21840l;
    }

    public static synchronized n a(Context context) {
        try {
            if (f21831k == null && context != null) {
                if (context instanceof Activity) {
                    f21831k = ((Activity) context).getApplication();
                } else if (context instanceof Application) {
                    f21831k = (Application) context;
                }
            }
        } catch (Throwable th) {
            throw th;
        }
        return a.f21844a;
    }

    public static void a(Context context, String str) {
        if (f21828e == 1 && UMUtils.isMainProgress(context)) {
            HashMap map = new HashMap();
            map.put("pairUUID", f21827d);
            map.put("reason", str);
            if (f21827d != null) {
                f21827d = null;
            }
            if (context != null) {
                map.put("pid", Integer.valueOf(Process.myPid()));
                map.put("isMainProcess", Integer.valueOf(UMUtils.isMainProgress(context) ? 1 : 0));
                map.put("Context", context.toString());
                com.umeng.analytics.b.a().a(context, "$$_onUMengEnterBackground", (Map<String, Object>) map);
            }
        }
    }

    public void b() {
        this.f21840l = false;
        if (f21831k != null) {
            f21831k.unregisterActivityLifecycleCallbacks(this.f21838g);
            f21831k = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(Activity activity) {
        long jLongValue;
        long jCurrentTimeMillis;
        try {
            synchronized (this.f21839h) {
                try {
                    if (f21826a == null && activity != null) {
                        f21826a = activity.getPackageName() + "." + activity.getLocalClassName();
                    }
                    if (TextUtils.isEmpty(f21826a) || !this.f21839h.containsKey(f21826a)) {
                        jLongValue = 0;
                        jCurrentTimeMillis = 0;
                    } else {
                        jLongValue = this.f21839h.get(f21826a).longValue();
                        jCurrentTimeMillis = System.currentTimeMillis() - jLongValue;
                        this.f21839h.remove(f21826a);
                    }
                } finally {
                }
            }
            synchronized (f21830j) {
                try {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put(f.f21700v, f21826a);
                    jSONObject.put("duration", jCurrentTimeMillis);
                    jSONObject.put(f.f21702x, jLongValue);
                    jSONObject.put("type", 0);
                    f21829i.put(jSONObject);
                } catch (Throwable unused) {
                }
            }
        } catch (Throwable unused2) {
        }
    }

    private void b(Activity activity) {
        f21826a = activity.getPackageName() + "." + activity.getLocalClassName();
        synchronized (this.f21839h) {
            this.f21839h.put(f21826a, Long.valueOf(System.currentTimeMillis()));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Activity activity) {
        if (UMConfigure.AUTO_ACTIVITY_PAGE_COLLECTION != MobclickAgent.PageMode.AUTO) {
            if (UMConfigure.AUTO_ACTIVITY_PAGE_COLLECTION == MobclickAgent.PageMode.MANUAL) {
                synchronized (f21833p) {
                    com.umeng.analytics.b.a().h();
                }
                return;
            }
            return;
        }
        if (activity != null) {
            String str = activity.getPackageName() + "." + activity.getLocalClassName();
            this.f21837f.activityResume(str);
            if (this.f21835b) {
                this.f21835b = false;
                if (!TextUtils.isEmpty(f21826a)) {
                    if (f21826a.equals(str)) {
                        return;
                    }
                    b(activity);
                    synchronized (f21833p) {
                        com.umeng.analytics.b.a().h();
                    }
                    return;
                }
                f21826a = str;
                return;
            }
            b(activity);
            synchronized (f21833p) {
                com.umeng.analytics.b.a().h();
            }
        }
    }
}
