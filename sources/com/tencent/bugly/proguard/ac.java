package com.tencent.bugly.proguard;

import android.content.Context;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public final class ac {

    /* renamed from: a, reason: collision with root package name */
    public static int f20705a = 1000;

    /* renamed from: b, reason: collision with root package name */
    public static long f20706b = 259200000;

    /* renamed from: d, reason: collision with root package name */
    private static ac f20707d;

    /* renamed from: i, reason: collision with root package name */
    private static String f20708i;

    /* renamed from: c, reason: collision with root package name */
    public final ak f20709c;

    /* renamed from: e, reason: collision with root package name */
    private final List<o> f20710e;

    /* renamed from: f, reason: collision with root package name */
    private final StrategyBean f20711f;

    /* renamed from: g, reason: collision with root package name */
    private StrategyBean f20712g = null;

    /* renamed from: h, reason: collision with root package name */
    private Context f20713h;

    private ac(Context context, List<o> list) {
        this.f20713h = context;
        if (aa.a(context) != null) {
            String str = aa.a(context).H;
            if ("oversea".equals(str)) {
                StrategyBean.f20597a = "https://astat.bugly.qcloud.com/rqd/async";
                StrategyBean.f20598b = "https://astat.bugly.qcloud.com/rqd/async";
            } else if ("na_https".equals(str)) {
                StrategyBean.f20597a = "https://astat.bugly.cros.wr.pvp.net/:8180/rqd/async";
                StrategyBean.f20598b = "https://astat.bugly.cros.wr.pvp.net/:8180/rqd/async";
            }
        }
        this.f20711f = new StrategyBean();
        this.f20710e = list;
        this.f20709c = ak.a();
    }

    public static StrategyBean d() {
        byte[] bArr;
        List<y> listA = w.a().a(2);
        if (listA == null || listA.size() <= 0 || (bArr = listA.get(0).f21194g) == null) {
            return null;
        }
        return (StrategyBean) ap.a(bArr, StrategyBean.CREATOR);
    }

    public final StrategyBean c() {
        StrategyBean strategyBean = this.f20712g;
        if (strategyBean != null) {
            if (!ap.d(strategyBean.f20613q)) {
                this.f20712g.f20613q = StrategyBean.f20597a;
            }
            if (!ap.d(this.f20712g.f20614r)) {
                this.f20712g.f20614r = StrategyBean.f20598b;
            }
            return this.f20712g;
        }
        if (!ap.b(f20708i) && ap.d(f20708i)) {
            StrategyBean strategyBean2 = this.f20711f;
            String str = f20708i;
            strategyBean2.f20613q = str;
            strategyBean2.f20614r = str;
        }
        return this.f20711f;
    }

    public final synchronized boolean b() {
        return this.f20712g != null;
    }

    public static synchronized ac a(Context context, List<o> list) {
        try {
            if (f20707d == null) {
                f20707d = new ac(context, list);
            }
        } catch (Throwable th) {
            throw th;
        }
        return f20707d;
    }

    public static synchronized ac a() {
        return f20707d;
    }

    protected final void a(StrategyBean strategyBean, boolean z2) {
        al.c("[Strategy] Notify %s", s.class.getName());
        s.a(strategyBean, z2);
        for (o oVar : this.f20710e) {
            try {
                al.c("[Strategy] Notify %s", oVar.getClass().getName());
                oVar.onServerStrategyChanged(strategyBean);
            } catch (Throwable th) {
                if (!al.a(th)) {
                    th.printStackTrace();
                }
            }
        }
    }

    public static void a(String str) {
        if (!ap.b(str) && ap.d(str)) {
            f20708i = str;
        } else {
            al.d("URL user set is invalid.", new Object[0]);
        }
    }

    public final void a(bt btVar) throws NumberFormatException {
        int i2;
        if (btVar == null) {
            return;
        }
        StrategyBean strategyBean = this.f20712g;
        if (strategyBean == null || btVar.f21052h != strategyBean.f20611o) {
            StrategyBean strategyBean2 = new StrategyBean();
            strategyBean2.f20602f = btVar.f21045a;
            strategyBean2.f20604h = btVar.f21047c;
            strategyBean2.f20603g = btVar.f21046b;
            if (ap.b(f20708i) || !ap.d(f20708i)) {
                if (ap.d(btVar.f21048d)) {
                    al.c("[Strategy] Upload url changes to %s", btVar.f21048d);
                    strategyBean2.f20613q = btVar.f21048d;
                }
                if (ap.d(btVar.f21049e)) {
                    al.c("[Strategy] Exception upload url changes to %s", btVar.f21049e);
                    strategyBean2.f20614r = btVar.f21049e;
                }
            }
            bs bsVar = btVar.f21050f;
            if (bsVar != null && !ap.b(bsVar.f21040a)) {
                strategyBean2.f20615s = btVar.f21050f.f21040a;
            }
            long j2 = btVar.f21052h;
            if (j2 != 0) {
                strategyBean2.f20611o = j2;
            }
            Map<String, String> map = btVar.f21051g;
            if (map != null && map.size() > 0) {
                Map<String, String> map2 = btVar.f21051g;
                strategyBean2.f20616t = map2;
                String str = map2.get("B11");
                strategyBean2.f20605i = str != null && str.equals("1");
                String str2 = btVar.f21051g.get("B3");
                if (str2 != null) {
                    strategyBean2.f20619w = Long.parseLong(str2);
                }
                int i3 = btVar.f21056l;
                strategyBean2.f20612p = i3;
                strategyBean2.f20618v = i3;
                String str3 = btVar.f21051g.get("B27");
                if (str3 != null && str3.length() > 0) {
                    try {
                        int i4 = Integer.parseInt(str3);
                        if (i4 > 0) {
                            strategyBean2.f20617u = i4;
                        }
                    } catch (Exception e2) {
                        if (!al.a(e2)) {
                            e2.printStackTrace();
                        }
                    }
                }
                String str4 = btVar.f21051g.get("B25");
                strategyBean2.f20607k = str4 != null && str4.equals("1");
            }
            al.a("[Strategy] enableCrashReport:%b, enableQuery:%b, enableUserInfo:%b, enableAnr:%b, enableBlock:%b, enableSession:%b, enableSessionTimer:%b, sessionOverTime:%d, enableCocos:%b, strategyLastUpdateTime:%d", Boolean.valueOf(strategyBean2.f20602f), Boolean.valueOf(strategyBean2.f20604h), Boolean.valueOf(strategyBean2.f20603g), Boolean.valueOf(strategyBean2.f20605i), Boolean.valueOf(strategyBean2.f20606j), Boolean.valueOf(strategyBean2.f20609m), Boolean.valueOf(strategyBean2.f20610n), Long.valueOf(strategyBean2.f20612p), Boolean.valueOf(strategyBean2.f20607k), Long.valueOf(strategyBean2.f20611o));
            this.f20712g = strategyBean2;
            if (ap.d(btVar.f21048d)) {
                i2 = 0;
            } else {
                i2 = 0;
                al.c("[Strategy] download url is null", new Object[0]);
                this.f20712g.f20613q = "";
            }
            if (!ap.d(btVar.f21049e)) {
                al.c("[Strategy] download crashurl is null", new Object[i2]);
                this.f20712g.f20614r = "";
            }
            w.a().b(2);
            y yVar = new y();
            yVar.f21189b = 2;
            yVar.f21188a = strategyBean2.f20600d;
            yVar.f21192e = strategyBean2.f20601e;
            yVar.f21194g = ap.a(strategyBean2);
            w.a().a(yVar);
            a(strategyBean2, true);
        }
    }
}
