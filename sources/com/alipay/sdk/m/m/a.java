package com.alipay.sdk.m.m;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.sdk.m.u.e;
import com.alipay.sdk.m.u.j;
import com.alipay.sdk.m.u.n;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class a {
    public static final String A = "DynCon";
    public static final int B = 10000;
    public static final String C = "https://h5.m.taobao.com/mlapp/olist.html";
    public static final int D = 10;
    public static final boolean E = true;
    public static final boolean F = true;
    public static final boolean G = false;
    public static final boolean H = true;
    public static final boolean I = true;
    public static final String J = "";
    public static final boolean K = false;
    public static final boolean L = false;
    public static final boolean M = false;
    public static final boolean N = false;
    public static final boolean O = true;
    public static final String P = "";
    public static final boolean Q = false;
    public static final boolean R = false;
    public static final boolean S = false;
    public static final int T = 1000;
    public static final boolean U = true;
    public static final String V = "";
    public static final int W = 1000;
    public static final int X = 20000;
    public static final String Y = "alipay_cashier_dynamic_config";
    public static final String Z = "timeout";

    /* renamed from: a0, reason: collision with root package name */
    public static final String f9487a0 = "h5_port_degrade";

    /* renamed from: b0, reason: collision with root package name */
    public static final String f9488b0 = "st_sdk_config";

    /* renamed from: c0, reason: collision with root package name */
    public static final String f9489c0 = "tbreturl";

    /* renamed from: d0, reason: collision with root package name */
    public static final String f9490d0 = "launchAppSwitch";

    /* renamed from: e0, reason: collision with root package name */
    public static final String f9491e0 = "configQueryInterval";

    /* renamed from: f0, reason: collision with root package name */
    public static final String f9492f0 = "deg_log_mcgw";

    /* renamed from: g0, reason: collision with root package name */
    public static final String f9493g0 = "deg_start_srv_first";

    /* renamed from: h0, reason: collision with root package name */
    public static final String f9494h0 = "prev_jump_dual";

    /* renamed from: i0, reason: collision with root package name */
    public static final String f9495i0 = "use_sc_only";

    /* renamed from: j0, reason: collision with root package name */
    public static final String f9496j0 = "retry_aidl_activity_not_start";

    /* renamed from: k0, reason: collision with root package name */
    public static final String f9497k0 = "bind_use_imp";

    /* renamed from: l0, reason: collision with root package name */
    public static final String f9498l0 = "retry_bnd_once";

    /* renamed from: m0, reason: collision with root package name */
    public static final String f9499m0 = "skip_trans";

    /* renamed from: n0, reason: collision with root package name */
    public static final String f9500n0 = "start_trans";

    /* renamed from: o0, reason: collision with root package name */
    public static final String f9501o0 = "up_before_pay";

    /* renamed from: p0, reason: collision with root package name */
    public static final String f9502p0 = "lck_k";

    /* renamed from: q0, reason: collision with root package name */
    public static final String f9503q0 = "use_sc_lck_a";

    /* renamed from: r0, reason: collision with root package name */
    public static final String f9504r0 = "utdid_factor";

    /* renamed from: s0, reason: collision with root package name */
    public static final String f9505s0 = "cfg_max_time";

    /* renamed from: t0, reason: collision with root package name */
    public static final String f9506t0 = "get_oa_id";

    /* renamed from: u0, reason: collision with root package name */
    public static final String f9507u0 = "notifyFailApp";

    /* renamed from: v0, reason: collision with root package name */
    public static final String f9508v0 = "scheme_pay_2";

    /* renamed from: w0, reason: collision with root package name */
    public static final String f9509w0 = "intercept_batch";

    /* renamed from: x0, reason: collision with root package name */
    public static final String f9510x0 = "bind_with_startActivity";

    /* renamed from: y0, reason: collision with root package name */
    public static a f9511y0;

    /* renamed from: w, reason: collision with root package name */
    public JSONObject f9534w;

    /* renamed from: a, reason: collision with root package name */
    public int f9512a = 10000;

    /* renamed from: b, reason: collision with root package name */
    public boolean f9513b = false;

    /* renamed from: c, reason: collision with root package name */
    public String f9514c = C;

    /* renamed from: d, reason: collision with root package name */
    public int f9515d = 10;

    /* renamed from: e, reason: collision with root package name */
    public boolean f9516e = true;

    /* renamed from: f, reason: collision with root package name */
    public boolean f9517f = true;

    /* renamed from: g, reason: collision with root package name */
    public boolean f9518g = false;

    /* renamed from: h, reason: collision with root package name */
    public boolean f9519h = false;

    /* renamed from: i, reason: collision with root package name */
    public boolean f9520i = true;

    /* renamed from: j, reason: collision with root package name */
    public boolean f9521j = true;

    /* renamed from: k, reason: collision with root package name */
    public String f9522k = "";

    /* renamed from: l, reason: collision with root package name */
    public boolean f9523l = false;

    /* renamed from: m, reason: collision with root package name */
    public boolean f9524m = false;

    /* renamed from: n, reason: collision with root package name */
    public boolean f9525n = false;

    /* renamed from: o, reason: collision with root package name */
    public boolean f9526o = false;

    /* renamed from: p, reason: collision with root package name */
    public boolean f9527p = true;

    /* renamed from: q, reason: collision with root package name */
    public String f9528q = "";

    /* renamed from: r, reason: collision with root package name */
    public String f9529r = "";

    /* renamed from: s, reason: collision with root package name */
    public boolean f9530s = false;

    /* renamed from: t, reason: collision with root package name */
    public boolean f9531t = false;

    /* renamed from: u, reason: collision with root package name */
    public int f9532u = 1000;

    /* renamed from: v, reason: collision with root package name */
    public boolean f9533v = false;

    /* renamed from: x, reason: collision with root package name */
    public boolean f9535x = true;

    /* renamed from: y, reason: collision with root package name */
    public List<b> f9536y = null;

    /* renamed from: z, reason: collision with root package name */
    public int f9537z = -1;

    /* renamed from: com.alipay.sdk.m.m.a$a, reason: collision with other inner class name */
    public class RunnableC0050a implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ com.alipay.sdk.m.s.a f9538a;

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ Context f9539b;

        /* renamed from: c, reason: collision with root package name */
        public final /* synthetic */ boolean f9540c;

        /* renamed from: d, reason: collision with root package name */
        public final /* synthetic */ int f9541d;

        public RunnableC0050a(com.alipay.sdk.m.s.a aVar, Context context, boolean z2, int i2) {
            this.f9538a = aVar;
            this.f9539b = context;
            this.f9540c = z2;
            this.f9541d = i2;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                com.alipay.sdk.m.p.b bVarA = new com.alipay.sdk.m.q.b().a(this.f9538a, this.f9539b);
                if (bVarA != null) {
                    a.this.a(this.f9538a, bVarA.a());
                    a.this.a(com.alipay.sdk.m.s.a.f());
                    com.alipay.sdk.m.k.a.a(this.f9538a, com.alipay.sdk.m.k.b.f9364l, "offcfg|" + this.f9540c + "|" + this.f9541d);
                }
            } catch (Throwable th) {
                e.a(th);
            }
        }
    }

    private JSONObject A() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("timeout", k());
        jSONObject.put(f9487a0, v());
        jSONObject.put(f9489c0, q());
        jSONObject.put(f9491e0, d());
        jSONObject.put(f9490d0, b.a(l()));
        jSONObject.put(f9508v0, i());
        jSONObject.put(f9509w0, h());
        jSONObject.put(f9492f0, e());
        jSONObject.put(f9493g0, f());
        jSONObject.put(f9494h0, m());
        jSONObject.put(f9495i0, g());
        jSONObject.put(f9497k0, b());
        jSONObject.put(f9498l0, n());
        jSONObject.put(f9499m0, p());
        jSONObject.put(f9500n0, x());
        jSONObject.put(f9501o0, r());
        jSONObject.put(f9503q0, o());
        jSONObject.put(f9502p0, j());
        jSONObject.put(f9510x0, c());
        jSONObject.put(f9496j0, w());
        jSONObject.put(f9505s0, y());
        jSONObject.put(f9506t0, u());
        jSONObject.put(f9507u0, t());
        jSONObject.put(com.alipay.sdk.m.u.a.f9755b, a());
        return jSONObject;
    }

    private int y() {
        return this.f9532u;
    }

    public static a z() {
        if (f9511y0 == null) {
            a aVar = new a();
            f9511y0 = aVar;
            aVar.s();
        }
        return f9511y0;
    }

    public boolean b() {
        return this.f9523l;
    }

    public String c() {
        return this.f9529r;
    }

    public int d() {
        return this.f9515d;
    }

    public boolean e() {
        return this.f9519h;
    }

    public boolean f() {
        return this.f9520i;
    }

    public String g() {
        return this.f9522k;
    }

    public boolean h() {
        return this.f9517f;
    }

    public boolean i() {
        return this.f9516e;
    }

    public String j() {
        return this.f9528q;
    }

    public int k() {
        int i2 = this.f9512a;
        if (i2 < 1000 || i2 > 20000) {
            e.b(A, "time(def) = 10000");
            return 10000;
        }
        e.b(A, "time = " + this.f9512a);
        return this.f9512a;
    }

    public List<b> l() {
        return this.f9536y;
    }

    public boolean m() {
        return this.f9521j;
    }

    public boolean n() {
        return this.f9524m;
    }

    public boolean o() {
        return this.f9530s;
    }

    public boolean p() {
        return this.f9525n;
    }

    public String q() {
        return this.f9514c;
    }

    public boolean r() {
        return this.f9527p;
    }

    public void s() {
        Context contextB = com.alipay.sdk.m.s.b.d().b();
        String strA = j.a(com.alipay.sdk.m.s.a.f(), contextB, Y, null);
        try {
            this.f9537z = Integer.parseInt(j.a(com.alipay.sdk.m.s.a.f(), contextB, f9504r0, "-1"));
        } catch (Exception unused) {
        }
        a(strA);
    }

    public boolean t() {
        return this.f9533v;
    }

    public boolean u() {
        return this.f9535x;
    }

    public boolean v() {
        return this.f9513b;
    }

    public boolean w() {
        return this.f9531t;
    }

    public boolean x() {
        return this.f9526o;
    }

    public static final class b {

        /* renamed from: a, reason: collision with root package name */
        public final String f9543a;

        /* renamed from: b, reason: collision with root package name */
        public final int f9544b;

        /* renamed from: c, reason: collision with root package name */
        public final String f9545c;

        public b(String str, int i2, String str2) {
            this.f9543a = str;
            this.f9544b = i2;
            this.f9545c = str2;
        }

        public static b a(JSONObject jSONObject) {
            if (jSONObject == null) {
                return null;
            }
            return new b(jSONObject.optString("pn"), jSONObject.optInt("v", 0), jSONObject.optString("pk"));
        }

        public String toString() {
            return String.valueOf(a(this));
        }

        public static List<b> a(JSONArray jSONArray) {
            if (jSONArray == null) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            int length = jSONArray.length();
            for (int i2 = 0; i2 < length; i2++) {
                b bVarA = a(jSONArray.optJSONObject(i2));
                if (bVarA != null) {
                    arrayList.add(bVarA);
                }
            }
            return arrayList;
        }

        public static JSONObject a(b bVar) throws JSONException {
            if (bVar == null) {
                return null;
            }
            try {
                return new JSONObject().put("pn", bVar.f9543a).put("v", bVar.f9544b).put("pk", bVar.f9545c);
            } catch (JSONException e2) {
                e.a(e2);
                return null;
            }
        }

        public static JSONArray a(List<b> list) {
            if (list == null) {
                return null;
            }
            JSONArray jSONArray = new JSONArray();
            Iterator<b> it = list.iterator();
            while (it.hasNext()) {
                jSONArray.put(a(it.next()));
            }
            return jSONArray;
        }
    }

    public JSONObject a() {
        return this.f9534w;
    }

    public void a(boolean z2) {
        this.f9518g = z2;
    }

    private void a(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        try {
            a(new JSONObject(str));
        } catch (Throwable th) {
            e.a(th);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(com.alipay.sdk.m.s.a aVar) {
        try {
            JSONObject jSONObjectA = A();
            j.b(aVar, com.alipay.sdk.m.s.b.d().b(), Y, jSONObjectA.toString());
        } catch (Exception e2) {
            e.a(e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(com.alipay.sdk.m.s.a aVar, String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject(f9488b0);
            com.alipay.sdk.m.u.a.a(aVar, jSONObjectOptJSONObject, com.alipay.sdk.m.u.a.a(aVar, jSONObject));
            if (jSONObjectOptJSONObject != null) {
                a(jSONObjectOptJSONObject);
            } else {
                e.e(A, "empty config");
            }
        } catch (Throwable th) {
            e.a(th);
        }
    }

    private void a(JSONObject jSONObject) {
        this.f9512a = jSONObject.optInt("timeout", 10000);
        this.f9513b = jSONObject.optBoolean(f9487a0, false);
        this.f9514c = jSONObject.optString(f9489c0, C).trim();
        this.f9515d = jSONObject.optInt(f9491e0, 10);
        this.f9536y = b.a(jSONObject.optJSONArray(f9490d0));
        this.f9516e = jSONObject.optBoolean(f9508v0, true);
        this.f9517f = jSONObject.optBoolean(f9509w0, true);
        this.f9519h = jSONObject.optBoolean(f9492f0, false);
        this.f9520i = jSONObject.optBoolean(f9493g0, true);
        this.f9521j = jSONObject.optBoolean(f9494h0, true);
        this.f9522k = jSONObject.optString(f9495i0, "");
        this.f9523l = jSONObject.optBoolean(f9497k0, false);
        this.f9524m = jSONObject.optBoolean(f9498l0, false);
        this.f9525n = jSONObject.optBoolean(f9499m0, false);
        this.f9526o = jSONObject.optBoolean(f9500n0, false);
        this.f9527p = jSONObject.optBoolean(f9501o0, true);
        this.f9528q = jSONObject.optString(f9502p0, "");
        this.f9530s = jSONObject.optBoolean(f9503q0, false);
        this.f9531t = jSONObject.optBoolean(f9496j0, false);
        this.f9533v = jSONObject.optBoolean(f9507u0, false);
        this.f9529r = jSONObject.optString(f9510x0, "");
        this.f9532u = jSONObject.optInt(f9505s0, 1000);
        this.f9535x = jSONObject.optBoolean(f9506t0, true);
        this.f9534w = jSONObject.optJSONObject(com.alipay.sdk.m.u.a.f9755b);
    }

    public void a(com.alipay.sdk.m.s.a aVar, Context context, boolean z2, int i2) {
        com.alipay.sdk.m.k.a.a(aVar, com.alipay.sdk.m.k.b.f9364l, "oncfg|" + z2 + "|" + i2);
        RunnableC0050a runnableC0050a = new RunnableC0050a(aVar, context, z2, i2);
        if (z2 && !n.h()) {
            int iY = y();
            if (n.a(iY, runnableC0050a, "AlipayDCPBlok")) {
                return;
            }
            com.alipay.sdk.m.k.a.b(aVar, com.alipay.sdk.m.k.b.f9364l, com.alipay.sdk.m.k.b.f9361j0, "" + iY);
            return;
        }
        Thread thread = new Thread(runnableC0050a);
        thread.setName("AlipayDCP");
        thread.start();
    }

    public boolean a(Context context, int i2) {
        if (this.f9537z == -1) {
            this.f9537z = n.a();
            j.b(com.alipay.sdk.m.s.a.f(), context, f9504r0, String.valueOf(this.f9537z));
        }
        return this.f9537z < i2;
    }
}
