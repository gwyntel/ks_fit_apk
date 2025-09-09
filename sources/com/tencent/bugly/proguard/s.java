package com.tencent.bugly.proguard;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import com.tencent.bugly.BuglyStrategy;
import com.tencent.bugly.crashreport.biz.UserInfoBean;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import com.tencent.bugly.proguard.r.a;
import java.util.List;

/* loaded from: classes4.dex */
public class s {

    /* renamed from: a, reason: collision with root package name */
    public static boolean f21130a = false;

    /* renamed from: b, reason: collision with root package name */
    public static r f21131b = null;

    /* renamed from: c, reason: collision with root package name */
    private static int f21132c = 10;

    /* renamed from: d, reason: collision with root package name */
    private static long f21133d = 300000;

    /* renamed from: e, reason: collision with root package name */
    private static long f21134e = 30000;

    /* renamed from: f, reason: collision with root package name */
    private static long f21135f = 0;

    /* renamed from: g, reason: collision with root package name */
    private static int f21136g = 0;

    /* renamed from: h, reason: collision with root package name */
    private static long f21137h = 0;

    /* renamed from: i, reason: collision with root package name */
    private static long f21138i = 0;

    /* renamed from: j, reason: collision with root package name */
    private static long f21139j = 0;

    /* renamed from: k, reason: collision with root package name */
    private static Application.ActivityLifecycleCallbacks f21140k = null;

    /* renamed from: l, reason: collision with root package name */
    private static Class<?> f21141l = null;

    /* renamed from: m, reason: collision with root package name */
    private static boolean f21142m = true;

    static class a implements Application.ActivityLifecycleCallbacks {
        a() {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public final void onActivityCreated(Activity activity, Bundle bundle) {
            String name = activity.getClass().getName();
            if (s.f21141l == null || s.f21141l.getName().equals(name)) {
                al.c(">>> %s onCreated <<<", name);
                aa aaVarB = aa.b();
                if (aaVarB != null) {
                    aaVarB.L.add(s.a(name, "onCreated"));
                }
            }
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public final void onActivityDestroyed(Activity activity) {
            String name = activity.getClass().getName();
            if (s.f21141l == null || s.f21141l.getName().equals(name)) {
                al.c(">>> %s onDestroyed <<<", name);
                aa aaVarB = aa.b();
                if (aaVarB != null) {
                    aaVarB.L.add(s.a(name, "onDestroyed"));
                }
            }
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public final void onActivityPaused(Activity activity) {
            String name = activity.getClass().getName();
            if (s.f21141l == null || s.f21141l.getName().equals(name)) {
                al.c(">>> %s onPaused <<<", name);
                aa aaVarB = aa.b();
                if (aaVarB == null) {
                    return;
                }
                aaVarB.L.add(s.a(name, "onPaused"));
                long jCurrentTimeMillis = System.currentTimeMillis();
                aaVarB.A = jCurrentTimeMillis;
                aaVarB.B = jCurrentTimeMillis - aaVarB.f20701z;
                long unused = s.f21137h = jCurrentTimeMillis;
                if (aaVarB.B < 0) {
                    aaVarB.B = 0L;
                }
                aaVarB.f20700y = "background";
            }
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public final void onActivityResumed(Activity activity) {
            String name = activity.getClass().getName();
            if (s.f21141l == null || s.f21141l.getName().equals(name)) {
                al.c(">>> %s onResumed <<<", name);
                aa aaVarB = aa.b();
                if (aaVarB == null) {
                    return;
                }
                aaVarB.L.add(s.a(name, "onResumed"));
                aaVarB.f20700y = name;
                long jCurrentTimeMillis = System.currentTimeMillis();
                aaVarB.f20701z = jCurrentTimeMillis;
                aaVarB.C = jCurrentTimeMillis - s.f21138i;
                long j2 = aaVarB.f20701z - s.f21137h;
                if (j2 > (s.f21135f > 0 ? s.f21135f : s.f21134e)) {
                    aaVarB.c();
                    s.g();
                    al.a("[session] launch app one times (app in background %d seconds and over %d seconds)", Long.valueOf(j2 / 1000), Long.valueOf(s.f21134e / 1000));
                    if (s.f21136g % s.f21132c == 0) {
                        s.f21131b.a(4, s.f21142m);
                        return;
                    }
                    s.f21131b.a(4, false);
                    long jCurrentTimeMillis2 = System.currentTimeMillis();
                    if (jCurrentTimeMillis2 - s.f21139j > s.f21133d) {
                        long unused = s.f21139j = jCurrentTimeMillis2;
                        al.a("add a timer to upload hot start user info", new Object[0]);
                        if (s.f21142m) {
                            ak.a().a(s.f21131b.new a(null, true), s.f21133d);
                        }
                    }
                }
            }
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public final void onActivityStarted(Activity activity) {
            al.c(">>> %s onStart <<<", activity.getClass().getName());
            aa.b().a(activity.hashCode(), true);
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public final void onActivityStopped(Activity activity) {
            al.c(">>> %s onStop <<<", activity.getClass().getName());
            aa.b().a(activity.hashCode(), false);
        }
    }

    public static void a(final Context context, final BuglyStrategy buglyStrategy) {
        long appReportDelay;
        if (f21130a) {
            return;
        }
        f21142m = aa.a(context).f20681f;
        f21131b = new r(context, f21142m);
        f21130a = true;
        if (buglyStrategy != null) {
            f21141l = buglyStrategy.getUserInfoActivity();
            appReportDelay = buglyStrategy.getAppReportDelay();
        } else {
            appReportDelay = 0;
        }
        if (appReportDelay <= 0) {
            c(context, buglyStrategy);
        } else {
            ak.a().a(new Runnable() { // from class: com.tencent.bugly.proguard.s.1
                @Override // java.lang.Runnable
                public final void run() {
                    s.c(context, buglyStrategy);
                }
            }, appReportDelay);
        }
    }

    static /* synthetic */ int g() {
        int i2 = f21136g;
        f21136g = i2 + 1;
        return i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void c(Context context, BuglyStrategy buglyStrategy) {
        boolean zIsEnableUserInfo;
        boolean zRecordUserInfoOnceADay;
        if (buglyStrategy != null) {
            zRecordUserInfoOnceADay = buglyStrategy.recordUserInfoOnceADay();
            zIsEnableUserInfo = buglyStrategy.isEnableUserInfo();
        } else {
            zIsEnableUserInfo = true;
            zRecordUserInfoOnceADay = false;
        }
        if (zRecordUserInfoOnceADay) {
            aa aaVarA = aa.a(context);
            List<UserInfoBean> listA = r.a(aaVarA.f20679d);
            if (listA != null) {
                for (int i2 = 0; i2 < listA.size(); i2++) {
                    UserInfoBean userInfoBean = listA.get(i2);
                    if (userInfoBean.f20588n.equals(aaVarA.f20690o) && userInfoBean.f20576b == 1) {
                        long jB = ap.b();
                        if (jB <= 0) {
                            break;
                        }
                        if (userInfoBean.f20579e >= jB) {
                            if (userInfoBean.f20580f <= 0) {
                                f21131b.b();
                                return;
                            }
                            return;
                        }
                    }
                }
            }
            zIsEnableUserInfo = false;
        }
        aa aaVarB = aa.b();
        if (aaVarB != null && z.a()) {
            aaVarB.a(0, true);
        }
        if (zIsEnableUserInfo) {
            Application application = context.getApplicationContext() instanceof Application ? (Application) context.getApplicationContext() : null;
            if (application != null) {
                try {
                    if (f21140k == null) {
                        f21140k = new a();
                    }
                    application.registerActivityLifecycleCallbacks(f21140k);
                } catch (Exception e2) {
                    if (!al.a(e2)) {
                        e2.printStackTrace();
                    }
                }
            }
        }
        if (f21142m) {
            f21138i = System.currentTimeMillis();
            f21131b.a(1, false);
            al.a("[session] launch app, new start", new Object[0]);
            f21131b.a();
            f21131b.a(21600000L);
        }
    }

    public static void a(long j2) {
        if (j2 < 0) {
            j2 = ac.a().c().f20612p;
        }
        f21135f = j2;
    }

    public static void a(StrategyBean strategyBean, boolean z2) {
        r rVar = f21131b;
        if (rVar != null && !z2) {
            rVar.b();
        }
        if (strategyBean == null) {
            return;
        }
        long j2 = strategyBean.f20612p;
        if (j2 > 0) {
            f21134e = j2;
        }
        int i2 = strategyBean.f20617u;
        if (i2 > 0) {
            f21132c = i2;
        }
        long j3 = strategyBean.f20618v;
        if (j3 > 0) {
            f21133d = j3;
        }
    }

    public static void a() {
        r rVar = f21131b;
        if (rVar != null) {
            rVar.a(2, false);
        }
    }

    public static void a(Context context) {
        if (!f21130a || context == null) {
            return;
        }
        Application application = context.getApplicationContext() instanceof Application ? (Application) context.getApplicationContext() : null;
        if (application != null) {
            try {
                Application.ActivityLifecycleCallbacks activityLifecycleCallbacks = f21140k;
                if (activityLifecycleCallbacks != null) {
                    application.unregisterActivityLifecycleCallbacks(activityLifecycleCallbacks);
                }
            } catch (Exception e2) {
                if (!al.a(e2)) {
                    e2.printStackTrace();
                }
            }
        }
        f21130a = false;
    }

    static /* synthetic */ String a(String str, String str2) {
        return ap.a() + "  " + str + "  " + str2 + "\n";
    }
}
