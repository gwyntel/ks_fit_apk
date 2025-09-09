package com.umeng.analytics.pro;

import android.text.TextUtils;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConstants;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class ay {
    public static final String A = "rtd";
    public static final String B = "lepd";
    public static final String C = "ccfg";
    private static Map<String, String> D = null;
    private static String E = null;

    /* renamed from: a, reason: collision with root package name */
    public static final String f21366a = "env";

    /* renamed from: b, reason: collision with root package name */
    public static final String f21367b = "exp";

    /* renamed from: c, reason: collision with root package name */
    public static final String f21368c = "imp";

    /* renamed from: d, reason: collision with root package name */
    public static final String f21369d = "ua";

    /* renamed from: e, reason: collision with root package name */
    public static final String f21370e = "zc";

    /* renamed from: f, reason: collision with root package name */
    public static final String f21371f = "id";

    /* renamed from: g, reason: collision with root package name */
    public static final String f21372g = "zf";

    /* renamed from: h, reason: collision with root package name */
    public static final String f21373h = "exid";

    /* renamed from: i, reason: collision with root package name */
    public static final String f21374i = "ucc";

    /* renamed from: j, reason: collision with root package name */
    public static final String f21375j = "ugc";

    /* renamed from: k, reason: collision with root package name */
    public static final String f21376k = "usi";

    /* renamed from: l, reason: collision with root package name */
    public static final String f21377l = "uso";

    /* renamed from: m, reason: collision with root package name */
    public static final String f21378m = "user";

    /* renamed from: n, reason: collision with root package name */
    public static final String f21379n = "uspi";

    /* renamed from: o, reason: collision with root package name */
    public static final String f21380o = "dtfn";

    /* renamed from: p, reason: collision with root package name */
    public static final String f21381p = "pr";

    /* renamed from: q, reason: collision with root package name */
    public static final String f21382q = "upg";

    /* renamed from: r, reason: collision with root package name */
    public static final String f21383r = "pri";

    /* renamed from: s, reason: collision with root package name */
    public static final String f21384s = "probe";

    /* renamed from: t, reason: collision with root package name */
    public static final String f21385t = "bl";

    /* renamed from: u, reason: collision with root package name */
    public static final String f21386u = "wl";

    /* renamed from: v, reason: collision with root package name */
    public static final String f21387v = "subp";

    /* renamed from: w, reason: collision with root package name */
    public static final String f21388w = "subua";

    /* renamed from: x, reason: collision with root package name */
    public static final String f21389x = "sta";

    /* renamed from: y, reason: collision with root package name */
    public static final String f21390y = "emi";

    /* renamed from: z, reason: collision with root package name */
    public static final String f21391z = "sli";

    private static class a {

        /* renamed from: a, reason: collision with root package name */
        private static final ay f21392a = new ay();

        private a() {
        }
    }

    static {
        HashMap map = new HashMap();
        D = map;
        E = "";
        map.put(f21366a, "envelope");
        D.put(f21367b, ".umeng");
        D.put(f21368c, ".imprint");
        D.put(f21369d, "ua.db");
        D.put(f21370e, "umeng_zero_cache.db");
        D.put("id", "umeng_it.cache");
        D.put(f21372g, "umeng_zcfg_flag");
        D.put(f21373h, "exid.dat");
        D.put(f21374i, "umeng_common_config");
        D.put(f21375j, "umeng_general_config");
        D.put(f21376k, "um_session_id");
        D.put(f21377l, "umeng_sp_oaid");
        D.put(f21378m, "mobclick_agent_user_");
        D.put(f21379n, "umeng_subprocess_info");
        D.put(f21380o, "delayed_transmission_flag_new");
        D.put("pr", "umeng_policy_result_flag");
        D.put(f21382q, "um_policy_grant");
        D.put(f21383r, "um_pri");
        D.put(f21384s, "UM_PROBE_DATA");
        D.put("bl", "ekv_bl");
        D.put(f21386u, "ekv_wl");
        D.put(f21387v, g.f21705a);
        D.put(f21388w, "ua_");
        D.put(f21389x, "stateless");
        D.put(f21390y, ".emitter");
        D.put(f21391z, "um_slmode_sp");
        D.put(A, "um_rtd_conf");
        D.put(B, "");
        D.put(C, ".dmpvedpogjhejs.cfg");
    }

    private ay() {
    }

    public void a(String str) {
        if (!TextUtils.isEmpty(str) && TextUtils.isEmpty(E)) {
            if (str.length() > 3) {
                E = str.substring(0, 3) + OpenAccountUIConstants.UNDER_LINE;
                return;
            }
            E = str + OpenAccountUIConstants.UNDER_LINE;
        }
    }

    public String b(String str) {
        if (!D.containsKey(str)) {
            return "";
        }
        String str2 = D.get(str);
        if (!f21367b.equalsIgnoreCase(str) && !f21368c.equalsIgnoreCase(str) && !f21390y.equalsIgnoreCase(str)) {
            return E + str2;
        }
        return "." + E + str2.substring(1);
    }

    public void a() {
        E = "";
    }

    public static ay b() {
        return a.f21392a;
    }
}
