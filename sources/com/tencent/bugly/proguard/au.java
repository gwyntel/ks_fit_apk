package com.tencent.bugly.proguard;

import android.content.Context;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import com.tencent.bugly.crashreport.crash.CrashDetailBean;
import java.util.LinkedHashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public final class au {

    /* renamed from: a, reason: collision with root package name */
    private static au f20875a;

    /* renamed from: b, reason: collision with root package name */
    private ac f20876b;

    /* renamed from: c, reason: collision with root package name */
    private aa f20877c;

    /* renamed from: d, reason: collision with root package name */
    private as f20878d;

    /* renamed from: e, reason: collision with root package name */
    private Context f20879e;

    private au(Context context) {
        at atVarA = at.a();
        if (atVarA == null) {
            return;
        }
        this.f20876b = ac.a();
        this.f20877c = aa.a(context);
        this.f20878d = atVarA.f20856s;
        this.f20879e = context;
        ak.a().a(new Runnable() { // from class: com.tencent.bugly.proguard.au.1
            @Override // java.lang.Runnable
            public final void run() {
                au.a(au.this);
            }
        });
    }

    public static au a(Context context) {
        if (f20875a == null) {
            f20875a = new au(context);
        }
        return f20875a;
    }

    public static void a(final Thread thread, final int i2, final String str, final String str2, final String str3, final Map<String, String> map) {
        ak.a().a(new Runnable() { // from class: com.tencent.bugly.proguard.au.2
            @Override // java.lang.Runnable
            public final void run() {
                try {
                    if (au.f20875a == null) {
                        al.e("[ExtraCrashManager] Extra crash manager has not been initialized.", new Object[0]);
                    } else {
                        au.a(au.f20875a, thread, i2, str, str2, str3, map);
                    }
                } catch (Throwable th) {
                    if (!al.b(th)) {
                        th.printStackTrace();
                    }
                    al.e("[ExtraCrashManager] Crash error %s %s %s", str, str2, str3);
                }
            }
        });
    }

    static /* synthetic */ void a(au auVar) {
        al.c("[ExtraCrashManager] Trying to notify Bugly agents.", new Object[0]);
        try {
            Class<?> cls = Class.forName("com.tencent.bugly.agent.GameAgent");
            auVar.f20877c.getClass();
            ap.a(cls, "sdkPackageName", "com.tencent.bugly");
            al.c("[ExtraCrashManager] Bugly game agent has been notified.", new Object[0]);
        } catch (Throwable unused) {
            al.a("[ExtraCrashManager] no game agent", new Object[0]);
        }
    }

    static /* synthetic */ void a(au auVar, Thread thread, int i2, String str, String str2, String str3, Map map) {
        String str4;
        String str5;
        Thread threadCurrentThread = thread == null ? Thread.currentThread() : thread;
        if (i2 == 4) {
            str4 = "Unity";
        } else if (i2 == 5 || i2 == 6) {
            str4 = "Cocos";
        } else {
            if (i2 != 8) {
                al.d("[ExtraCrashManager] Unknown extra crash type: %d", Integer.valueOf(i2));
                return;
            }
            str4 = "H5";
        }
        al.e("[ExtraCrashManager] %s Crash Happen", str4);
        try {
            if (!auVar.f20876b.b()) {
                al.d("[ExtraCrashManager] There is no remote strategy, but still store it.", new Object[0]);
            }
            StrategyBean strategyBeanC = auVar.f20876b.c();
            if (!strategyBeanC.f20602f && auVar.f20876b.b()) {
                al.e("[ExtraCrashManager] Crash report was closed by remote. Will not upload to Bugly , print local for helpful!", new Object[0]);
                as.a(str4, ap.a(), auVar.f20877c.f20679d, threadCurrentThread.getName(), str + "\n" + str2 + "\n" + str3, null);
                al.e("[ExtraCrashManager] Successfully handled.", new Object[0]);
                return;
            }
            if (i2 != 5 && i2 != 6) {
                if (i2 == 8 && !strategyBeanC.f20608l) {
                    al.e("[ExtraCrashManager] %s report is disabled.", str4);
                    al.e("[ExtraCrashManager] Successfully handled.", new Object[0]);
                    return;
                }
            } else if (!strategyBeanC.f20607k) {
                al.e("[ExtraCrashManager] %s report is disabled.", str4);
                al.e("[ExtraCrashManager] Successfully handled.", new Object[0]);
                return;
            }
            int i3 = i2 != 8 ? i2 : 5;
            CrashDetailBean crashDetailBean = new CrashDetailBean();
            crashDetailBean.C = ab.j();
            crashDetailBean.D = ab.f();
            crashDetailBean.E = ab.l();
            crashDetailBean.F = auVar.f20877c.k();
            crashDetailBean.G = auVar.f20877c.j();
            crashDetailBean.H = auVar.f20877c.l();
            crashDetailBean.I = ab.b(auVar.f20879e);
            crashDetailBean.J = ab.g();
            crashDetailBean.K = ab.h();
            crashDetailBean.f20621b = i3;
            crashDetailBean.f20624e = auVar.f20877c.g();
            aa aaVar = auVar.f20877c;
            crashDetailBean.f20625f = aaVar.f20690o;
            crashDetailBean.f20626g = aaVar.q();
            crashDetailBean.f20632m = auVar.f20877c.f();
            crashDetailBean.f20633n = String.valueOf(str);
            crashDetailBean.f20634o = String.valueOf(str2);
            String str6 = "";
            if (str3 == null) {
                str5 = "";
            } else {
                String[] strArrSplit = str3.split("\n");
                if (strArrSplit.length > 0) {
                    str6 = strArrSplit[0];
                }
                str5 = str3;
            }
            crashDetailBean.f20635p = str6;
            crashDetailBean.f20636q = str5;
            crashDetailBean.f20637r = System.currentTimeMillis();
            crashDetailBean.f20640u = ap.c(crashDetailBean.f20636q.getBytes());
            crashDetailBean.f20645z = ap.a(auVar.f20877c.Q, at.f20844h);
            crashDetailBean.A = auVar.f20877c.f20679d;
            crashDetailBean.B = threadCurrentThread.getName() + "(" + threadCurrentThread.getId() + ")";
            crashDetailBean.L = auVar.f20877c.s();
            crashDetailBean.f20627h = auVar.f20877c.p();
            aa aaVar2 = auVar.f20877c;
            crashDetailBean.Q = aaVar2.f20676a;
            crashDetailBean.R = aaVar2.a();
            crashDetailBean.U = auVar.f20877c.z();
            aa aaVar3 = auVar.f20877c;
            crashDetailBean.V = aaVar3.f20699x;
            crashDetailBean.W = aaVar3.t();
            crashDetailBean.X = auVar.f20877c.y();
            crashDetailBean.f20644y = ao.a();
            if (crashDetailBean.S == null) {
                crashDetailBean.S = new LinkedHashMap();
            }
            if (map != null) {
                crashDetailBean.S.putAll(map);
            }
            as.a(str4, ap.a(), auVar.f20877c.f20679d, threadCurrentThread.getName(), str + "\n" + str2 + "\n" + str3, crashDetailBean);
            if (!auVar.f20878d.a(crashDetailBean, !at.a().C)) {
                auVar.f20878d.b(crashDetailBean, false);
            }
            al.e("[ExtraCrashManager] Successfully handled.", new Object[0]);
        } catch (Throwable th) {
            try {
                if (!al.a(th)) {
                    th.printStackTrace();
                }
                al.e("[ExtraCrashManager] Successfully handled.", new Object[0]);
            } catch (Throwable th2) {
                al.e("[ExtraCrashManager] Successfully handled.", new Object[0]);
                throw th2;
            }
        }
    }
}
