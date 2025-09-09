package com.umeng.analytics;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.pro.aa;
import com.umeng.analytics.pro.f;
import com.umeng.analytics.pro.k;
import com.umeng.analytics.pro.l;
import com.umeng.analytics.pro.m;
import com.umeng.analytics.pro.n;
import com.umeng.analytics.pro.o;
import com.umeng.analytics.pro.p;
import com.umeng.analytics.pro.q;
import com.umeng.analytics.pro.r;
import com.umeng.analytics.pro.u;
import com.umeng.analytics.pro.v;
import com.umeng.analytics.pro.w;
import com.umeng.analytics.pro.x;
import com.umeng.common.ISysListener;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.config.FieldManager;
import com.umeng.commonsdk.debug.UMLog;
import com.umeng.commonsdk.debug.UMRTLog;
import com.umeng.commonsdk.framework.UMWorkDispatch;
import com.umeng.commonsdk.service.UMGlobalContext;
import com.umeng.commonsdk.statistics.common.DataHelper;
import com.umeng.commonsdk.statistics.common.HelperUtils;
import com.umeng.commonsdk.statistics.common.MLog;
import com.umeng.commonsdk.statistics.internal.PreferenceWrapper;
import com.umeng.commonsdk.utils.UMUtils;
import com.umeng.commonsdk.utils.d;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.microedition.khronos.opengles.GL10;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class b implements p, v {
    private static final String A = "umsp_2";
    private static final String B = "umsp_3";
    private static final String C = "umsp_4";
    private static final String D = "umsp_5";

    /* renamed from: a, reason: collision with root package name */
    private static Context f21220a = null;

    /* renamed from: h, reason: collision with root package name */
    private static final String f21221h = "sp_uapp";

    /* renamed from: i, reason: collision with root package name */
    private static final String f21222i = "prepp_uapp";

    /* renamed from: o, reason: collision with root package name */
    private static final int f21223o = 128;

    /* renamed from: p, reason: collision with root package name */
    private static final int f21224p = 256;

    /* renamed from: q, reason: collision with root package name */
    private static String f21225q = "";

    /* renamed from: r, reason: collision with root package name */
    private static String f21226r = "";

    /* renamed from: t, reason: collision with root package name */
    private static final String f21228t = "ekv_bl_ver";

    /* renamed from: w, reason: collision with root package name */
    private static final String f21230w = "ekv_wl_ver";

    /* renamed from: z, reason: collision with root package name */
    private static final String f21231z = "umsp_1";

    /* renamed from: b, reason: collision with root package name */
    private ISysListener f21232b;

    /* renamed from: c, reason: collision with root package name */
    private r f21233c;

    /* renamed from: d, reason: collision with root package name */
    private x f21234d;

    /* renamed from: e, reason: collision with root package name */
    private m f21235e;

    /* renamed from: f, reason: collision with root package name */
    private w f21236f;

    /* renamed from: g, reason: collision with root package name */
    private n f21237g;

    /* renamed from: j, reason: collision with root package name */
    private boolean f21238j;

    /* renamed from: k, reason: collision with root package name */
    private volatile JSONObject f21239k;

    /* renamed from: l, reason: collision with root package name */
    private volatile JSONObject f21240l;

    /* renamed from: m, reason: collision with root package name */
    private volatile JSONObject f21241m;

    /* renamed from: n, reason: collision with root package name */
    private boolean f21242n;

    /* renamed from: u, reason: collision with root package name */
    private com.umeng.analytics.filter.a f21243u;

    /* renamed from: x, reason: collision with root package name */
    private com.umeng.analytics.filter.b f21244x;

    /* renamed from: y, reason: collision with root package name */
    private o f21245y;

    /* renamed from: s, reason: collision with root package name */
    private static final String f21227s = f.ar;

    /* renamed from: v, reason: collision with root package name */
    private static final String f21229v = f.as;

    private static class a {

        /* renamed from: a, reason: collision with root package name */
        private static final b f21246a = new b();

        private a() {
        }
    }

    static {
        Context appContext = UMGlobalContext.getAppContext();
        if (appContext != null) {
            f21220a = appContext.getApplicationContext();
        }
    }

    public static b a() {
        return a.f21246a;
    }

    private void i(Context context) {
        try {
            if (context == null) {
                MLog.e("unexpected null context in getNativeSuperProperties");
                return;
            }
            if (f21220a == null) {
                f21220a = context.getApplicationContext();
            }
            SharedPreferences sharedPreferences = PreferenceWrapper.getDefault(context);
            if (this.f21239k == null) {
                this.f21239k = new JSONObject();
            }
            if (this.f21240l == null) {
                this.f21240l = new JSONObject();
            }
            String string = sharedPreferences.getString(f21222i, null);
            if (!TextUtils.isEmpty(string)) {
                try {
                    this.f21241m = new JSONObject(string);
                } catch (JSONException unused) {
                }
            }
            if (this.f21241m == null) {
                this.f21241m = new JSONObject();
            }
        } catch (Throwable unused2) {
        }
    }

    public JSONObject b() {
        return this.f21239k;
    }

    public JSONObject c() {
        return this.f21241m;
    }

    public JSONObject d() {
        return this.f21240l;
    }

    public void e() {
        this.f21240l = null;
    }

    public String f() {
        if (UMUtils.isMainProgress(f21220a)) {
            return f21225q;
        }
        MLog.e("getOnResumedActivityName can not be called in child process");
        return null;
    }

    public String g() {
        if (UMUtils.isMainProgress(f21220a)) {
            return f21226r;
        }
        MLog.e("getOnPausedActivityName can not be called in child process");
        return null;
    }

    public void h() {
        try {
            Context context = f21220a;
            if (context != null) {
                if (!UMUtils.isMainProgress(context)) {
                    MLog.e("onStartSessionInternal can not be called in child process");
                    return;
                }
                long jCurrentTimeMillis = System.currentTimeMillis();
                Context context2 = f21220a;
                UMWorkDispatch.sendEvent(context2, q.a.f21889k, CoreProtocol.getInstance(context2), Long.valueOf(jCurrentTimeMillis));
                Context context3 = f21220a;
                UMWorkDispatch.sendEvent(context3, 4103, CoreProtocol.getInstance(context3), Long.valueOf(jCurrentTimeMillis));
            }
            ISysListener iSysListener = this.f21232b;
            if (iSysListener != null) {
                iSysListener.onAppResume();
            }
        } catch (Throwable unused) {
        }
    }

    void j() {
        try {
            Context context = f21220a;
            if (context == null) {
                return;
            }
            if (!UMUtils.isMainProgress(context)) {
                MLog.e("onProfileSignOff can not be called in child process");
                return;
            }
            long jCurrentTimeMillis = System.currentTimeMillis();
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("ts", jCurrentTimeMillis);
            Context context2 = f21220a;
            UMWorkDispatch.sendEvent(context2, 4102, CoreProtocol.getInstance(context2), jSONObject);
            Context context3 = f21220a;
            UMWorkDispatch.sendEvent(context3, q.a.f21893o, CoreProtocol.getInstance(context3), jSONObject);
        } catch (Throwable th) {
            if (MLog.DEBUG) {
                MLog.e(" Excepthon  in  onProfileSignOff", th);
            }
        }
    }

    public synchronized void k() {
        Context context;
        try {
            context = f21220a;
        } catch (Throwable unused) {
        }
        if (context == null) {
            return;
        }
        if (!UMUtils.isMainProgress(context)) {
            MLog.e("unregisterSuperPropertyByCoreProtocol can not be called in child process");
            return;
        }
        if (this.f21239k != null) {
            SharedPreferences.Editor editorEdit = PreferenceWrapper.getDefault(f21220a).edit();
            editorEdit.putString(f21221h, this.f21239k.toString());
            editorEdit.commit();
        } else {
            this.f21239k = new JSONObject();
        }
    }

    public synchronized JSONObject l() {
        Context context;
        try {
            context = f21220a;
        } catch (Throwable unused) {
        }
        if (context == null) {
            return null;
        }
        if (!UMUtils.isMainProgress(context)) {
            MLog.e("getSuperPropertiesJSONObject can not be called in child process");
            return null;
        }
        if (this.f21239k == null) {
            this.f21239k = new JSONObject();
        }
        return this.f21239k;
    }

    public synchronized void m() {
        try {
            Context context = f21220a;
            if (context != null) {
                if (!UMUtils.isMainProgress(context)) {
                    MLog.e("clearSuperPropertiesByCoreProtocol can not be called in child process");
                } else {
                    SharedPreferences.Editor editorEdit = PreferenceWrapper.getDefault(f21220a).edit();
                    editorEdit.remove(f21221h);
                    editorEdit.commit();
                }
            }
        } catch (Throwable unused) {
        }
    }

    @Override // com.umeng.analytics.pro.p
    public void n() {
        UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> onIntoBackground triggered.");
        if (AnalyticsConfig.enable && FieldManager.b()) {
            if (!FieldManager.allow(d.D)) {
                UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> 退出发送策略: 云控控制字关闭。功能不生效");
            } else {
                if (UMWorkDispatch.eventHasExist(q.a.B)) {
                    return;
                }
                UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> 退出时发送策略 被触发！");
                Context context = f21220a;
                UMWorkDispatch.sendEvent(context, q.a.B, CoreProtocol.getInstance(context), null);
            }
        }
    }

    private b() {
        this.f21233c = new r();
        this.f21234d = new x();
        this.f21235e = new m();
        this.f21236f = w.a();
        this.f21237g = null;
        this.f21238j = false;
        this.f21239k = null;
        this.f21240l = null;
        this.f21241m = null;
        this.f21242n = false;
        this.f21243u = null;
        this.f21244x = null;
        this.f21245y = null;
        this.f21233c.a(this);
    }

    private boolean e(String str) {
        if (this.f21243u.enabled() && this.f21243u.matchHit(str)) {
            return true;
        }
        if (!this.f21244x.enabled()) {
            return false;
        }
        if (!this.f21244x.matchHit(str)) {
            return true;
        }
        UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> white list match! id = " + str);
        return false;
    }

    public void a(Context context) {
        if (context == null) {
            return;
        }
        try {
            if (f21220a == null) {
                f21220a = context.getApplicationContext();
            }
            if (this.f21243u == null) {
                com.umeng.analytics.filter.a aVar = new com.umeng.analytics.filter.a(f21227s, "ekv_bl_ver");
                this.f21243u = aVar;
                aVar.register(f21220a);
            }
            if (this.f21244x == null) {
                com.umeng.analytics.filter.b bVar = new com.umeng.analytics.filter.b(f21229v, "ekv_wl_ver");
                this.f21244x = bVar;
                bVar.register(f21220a);
            }
            if (UMUtils.isMainProgress(f21220a)) {
                if (!this.f21238j) {
                    this.f21238j = true;
                    i(f21220a);
                }
                synchronized (this) {
                    try {
                        if (!this.f21242n) {
                            n nVarA = n.a(context);
                            this.f21237g = nVarA;
                            if (nVarA.a()) {
                                this.f21242n = true;
                            }
                            this.f21245y = o.a();
                            try {
                                o.a(context);
                                this.f21245y.a(this);
                            } catch (Throwable unused) {
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                if (UMConfigure.isDebugLog()) {
                    UMLog.mutlInfo(l.B, 3, "", null, null);
                }
                UMWorkDispatch.registerConnStateObserver(CoreProtocol.getInstance(f21220a));
            }
        } catch (Throwable unused2) {
        }
    }

    void b(String str) {
        if (!UMUtils.isMainProgress(f21220a)) {
            MLog.e("onPageEnd can not be called in child process");
            return;
        }
        try {
            if (UMConfigure.AUTO_ACTIVITY_PAGE_COLLECTION != MobclickAgent.PageMode.LEGACY_AUTO) {
                this.f21234d.b(str);
            }
        } catch (Throwable unused) {
        }
    }

    void c(Context context) {
        if (context == null) {
            UMLog.aq(l.f21815p, 0, "\\|");
            return;
        }
        if (UMConfigure.AUTO_ACTIVITY_PAGE_COLLECTION == MobclickAgent.PageMode.AUTO) {
            return;
        }
        if (f21220a == null) {
            f21220a = context.getApplicationContext();
        }
        if (!UMUtils.isMainProgress(f21220a)) {
            MLog.e("onPause can not be called in child process");
            return;
        }
        if (UMConfigure.isDebugLog() && !(context instanceof Activity)) {
            UMLog.aq(l.f21816q, 2, "\\|");
        }
        try {
            if (!this.f21238j || !this.f21242n) {
                a(context);
            }
            if (UMConfigure.AUTO_ACTIVITY_PAGE_COLLECTION != MobclickAgent.PageMode.LEGACY_MANUAL) {
                this.f21235e.b(context.getClass().getName());
            }
            i();
        } catch (Throwable th) {
            if (MLog.DEBUG) {
                MLog.e("Exception occurred in Mobclick.onRause(). ", th);
            }
        }
        if (UMConfigure.isDebugLog() && (context instanceof Activity)) {
            f21226r = context.getClass().getName();
        }
    }

    void d(Context context) {
        if (context == null) {
            return;
        }
        try {
            if (f21220a == null) {
                f21220a = context.getApplicationContext();
            }
            if (!UMUtils.isMainProgress(f21220a)) {
                MLog.e("onKillProcess can not be called in child process");
                return;
            }
            n nVar = this.f21237g;
            if (nVar != null) {
                nVar.c();
            }
            n.a(context, "onKillProcess");
            m mVar = this.f21235e;
            if (mVar != null) {
                mVar.b();
            }
            x xVar = this.f21234d;
            if (xVar != null) {
                xVar.b();
            }
            Context context2 = f21220a;
            if (context2 != null) {
                w wVar = this.f21236f;
                if (wVar != null) {
                    wVar.c(context2, Long.valueOf(System.currentTimeMillis()));
                }
                q.a(f21220a).d();
                x.a(f21220a);
                if (UMConfigure.AUTO_ACTIVITY_PAGE_COLLECTION == MobclickAgent.PageMode.AUTO) {
                    n.c(f21220a);
                }
                PreferenceWrapper.getDefault(f21220a).edit().commit();
            }
        } catch (Throwable unused) {
        }
    }

    public synchronized void f(Context context) {
        if (context == null) {
            UMLog.aq(l.ah, 0, "\\|");
            return;
        }
        if (f21220a == null) {
            f21220a = context.getApplicationContext();
        }
        if (!UMUtils.isMainProgress(f21220a)) {
            MLog.e("clearSuperProperties can not be called in child process");
            return;
        }
        if (!this.f21238j || !this.f21242n) {
            a(f21220a);
        }
        this.f21239k = new JSONObject();
        Context context2 = f21220a;
        UMWorkDispatch.sendEvent(context2, q.a.f21899u, CoreProtocol.getInstance(context2), null);
    }

    public synchronized void g(Context context) {
        if (context == null) {
            UMLog.aq(l.ap, 0, "\\|");
            return;
        }
        if (f21220a == null) {
            f21220a = context.getApplicationContext();
        }
        if (!UMUtils.isMainProgress(f21220a)) {
            MLog.e("clearPreProperties can not be called in child process");
            return;
        }
        if (!this.f21238j || !this.f21242n) {
            a(f21220a);
        }
        if (this.f21241m.length() > 0) {
            Context context2 = f21220a;
            UMWorkDispatch.sendEvent(context2, q.a.f21903y, CoreProtocol.getInstance(context2), null);
        }
        this.f21241m = new JSONObject();
    }

    void b(Context context) {
        if (context == null) {
            MLog.e("unexpected null context in onResume");
            return;
        }
        if (UMConfigure.AUTO_ACTIVITY_PAGE_COLLECTION == MobclickAgent.PageMode.AUTO) {
            return;
        }
        if (f21220a == null) {
            f21220a = context.getApplicationContext();
        }
        if (!UMUtils.isMainProgress(f21220a)) {
            MLog.e("onResume can not be called in child process");
            return;
        }
        if (UMConfigure.isDebugLog() && !(context instanceof Activity)) {
            UMLog.aq(l.f21814o, 2, "\\|");
        }
        try {
            if (!this.f21238j || !this.f21242n) {
                a(context);
            }
            if (UMConfigure.AUTO_ACTIVITY_PAGE_COLLECTION != MobclickAgent.PageMode.LEGACY_MANUAL) {
                this.f21235e.a(context.getClass().getName());
            }
            h();
            if (UMConfigure.isDebugLog() && (context instanceof Activity)) {
                f21225q = context.getClass().getName();
            }
        } catch (Throwable th) {
            MLog.e("Exception occurred in Mobclick.onResume(). ", th);
        }
    }

    public synchronized Object e(Context context, String str) {
        if (context == null) {
            UMLog.aq(l.ai, 0, "\\|");
            return null;
        }
        if (f21220a == null) {
            f21220a = context.getApplicationContext();
        }
        if (!UMUtils.isMainProgress(f21220a)) {
            MLog.e("getSuperProperty can not be called in child process");
            return null;
        }
        if (TextUtils.isEmpty(str)) {
            UMLog.aq(l.ag, 0, "\\|");
            return null;
        }
        if (!str.equals(f21231z) && !str.equals(A) && !str.equals(B) && !str.equals(C) && !str.equals(D)) {
            MLog.e("please check key or value, must be correct!");
            return null;
        }
        if (this.f21239k != null) {
            if (this.f21239k.has(str)) {
                return this.f21239k.opt(str);
            }
        } else {
            this.f21239k = new JSONObject();
        }
        return null;
    }

    public synchronized JSONObject h(Context context) {
        if (context == null) {
            UMLog.aq(l.aq, 0, "\\|");
            return null;
        }
        if (f21220a == null) {
            f21220a = context.getApplicationContext();
        }
        if (!UMUtils.isMainProgress(f21220a)) {
            MLog.e("getPreProperties can not be called in child process");
            return null;
        }
        if (!this.f21238j || !this.f21242n) {
            a(f21220a);
        }
        if (this.f21241m == null) {
            this.f21241m = new JSONObject();
        }
        JSONObject jSONObject = new JSONObject();
        if (this.f21241m.length() > 0) {
            try {
                jSONObject = new JSONObject(this.f21241m.toString());
            } catch (JSONException unused) {
            }
        }
        return jSONObject;
    }

    public void i() {
        try {
            Context context = f21220a;
            if (context != null) {
                if (!UMUtils.isMainProgress(context)) {
                    MLog.e("onEndSessionInternal can not be called in child process");
                    return;
                }
                Context context2 = f21220a;
                UMWorkDispatch.sendEvent(context2, q.a.f21886h, CoreProtocol.getInstance(context2), Long.valueOf(System.currentTimeMillis()));
                Context context3 = f21220a;
                UMWorkDispatch.sendEvent(context3, 4100, CoreProtocol.getInstance(context3), null);
                Context context4 = f21220a;
                UMWorkDispatch.sendEvent(context4, 4099, CoreProtocol.getInstance(context4), null);
                Context context5 = f21220a;
                UMWorkDispatch.sendEvent(context5, 4105, CoreProtocol.getInstance(context5), null);
            }
        } catch (Throwable unused) {
        }
        ISysListener iSysListener = this.f21232b;
        if (iSysListener != null) {
            iSysListener.onAppPause();
        }
    }

    public synchronized void f(Context context, String str) {
        if (context == null) {
            UMLog.aq(l.an, 0, "\\|");
            return;
        }
        if (f21220a == null) {
            f21220a = context.getApplicationContext();
        }
        if (!UMUtils.isMainProgress(f21220a)) {
            MLog.e("unregisterPreProperty can not be called in child process");
            return;
        }
        if (!this.f21238j || !this.f21242n) {
            a(f21220a);
        }
        if (this.f21241m == null) {
            this.f21241m = new JSONObject();
        }
        if (str != null && str.length() > 0) {
            if (this.f21241m.has(str)) {
                this.f21241m.remove(str);
                Context context2 = f21220a;
                UMWorkDispatch.sendEvent(context2, 8200, CoreProtocol.getInstance(context2), this.f21241m.toString());
            } else if (UMConfigure.isDebugLog()) {
                UMLog.aq(l.ao, 0, "\\|");
            }
            return;
        }
        MLog.e("please check propertics, property is null!");
    }

    private boolean g(String str) {
        if (str != null) {
            try {
                int length = str.trim().getBytes().length;
                if (length >= 0 && length < 256) {
                    return true;
                }
            } catch (Throwable unused) {
            }
        }
        MLog.e("The length of profile value must be less than 256 bytes.");
        return false;
    }

    public synchronized void d(Context context, String str) {
        try {
        } finally {
        }
        if (context == null) {
            UMLog.aq(l.ah, 0, "\\|");
            return;
        }
        if (f21220a == null) {
            f21220a = context.getApplicationContext();
        }
        if (!UMUtils.isMainProgress(f21220a)) {
            MLog.e("unregisterSuperProperty can not be called in child process");
            return;
        }
        if (!this.f21238j || !this.f21242n) {
            a(f21220a);
        }
        if (TextUtils.isEmpty(str)) {
            UMLog.aq(l.ag, 0, "\\|");
            return;
        }
        if (!str.equals(f21231z) && !str.equals(A) && !str.equals(B) && !str.equals(C) && !str.equals(D)) {
            MLog.e("please check key or value, must be correct!");
            return;
        }
        if (this.f21239k == null) {
            this.f21239k = new JSONObject();
        }
        if (this.f21239k.has(str)) {
            this.f21239k.remove(str);
            Context context2 = f21220a;
            UMWorkDispatch.sendEvent(context2, 8197, CoreProtocol.getInstance(context2), str);
        }
    }

    void c(Context context, String str) {
        if (context == null) {
            UMLog.aq(l.f21825z, 0, "\\|");
            return;
        }
        if (f21220a == null) {
            f21220a = context.getApplicationContext();
        }
        if (!UMUtils.isMainProgress(f21220a)) {
            MLog.e("setSecret can not be called in child process");
            return;
        }
        if (!this.f21238j || !this.f21242n) {
            a(f21220a);
        }
        AnalyticsConfig.a(f21220a, str);
    }

    public void b(Context context, String str) {
        try {
            if (context == null) {
                UMLog.aq(l.N, 0, "\\|");
                return;
            }
            if (f21220a == null) {
                f21220a = context.getApplicationContext();
            }
            if (!UMUtils.isMainProgress(f21220a)) {
                MLog.e("onDeepLinkReceived can not be called in child process");
                return;
            }
            if (!this.f21238j || !this.f21242n) {
                a(f21220a);
            }
            if (TextUtils.isEmpty(str)) {
                UMLog.aq(l.O, 0, "\\|");
                return;
            }
            HashMap map = new HashMap();
            map.put(f.aK, str);
            a(f21220a, f.aJ, (Map<String, Object>) map, -1L, false);
        } catch (Throwable th) {
            if (MLog.DEBUG) {
                MLog.e(th);
            }
        }
    }

    void a(String str) {
        if (!UMUtils.isMainProgress(f21220a)) {
            MLog.e("onPageStart can not be called in child process");
            return;
        }
        try {
            if (UMConfigure.AUTO_ACTIVITY_PAGE_COLLECTION != MobclickAgent.PageMode.LEGACY_AUTO) {
                this.f21234d.a(str);
            }
        } catch (Throwable unused) {
        }
    }

    public synchronized String e(Context context) {
        if (context == null) {
            UMLog.aq(l.ai, 0, "\\|");
            return null;
        }
        if (f21220a == null) {
            f21220a = context.getApplicationContext();
        }
        if (!UMUtils.isMainProgress(f21220a)) {
            MLog.e("getSuperProperties can not be called in child process");
            return null;
        }
        if (this.f21239k != null) {
            return this.f21239k.toString();
        }
        this.f21239k = new JSONObject();
        return null;
    }

    private boolean c(String str, Object obj) {
        int length;
        if (TextUtils.isEmpty(str)) {
            MLog.e("key is " + str + ", please check key, illegal");
            return false;
        }
        try {
            length = str.getBytes("UTF-8").length;
        } catch (UnsupportedEncodingException unused) {
            length = 0;
        }
        if (length > 128) {
            MLog.e("key length is " + length + ", please check key, illegal");
            return false;
        }
        if (obj instanceof String) {
            if (((String) obj).getBytes("UTF-8").length <= 256) {
                return true;
            }
            MLog.e("value length is " + ((String) obj).getBytes("UTF-8").length + ", please check value, illegal");
            return false;
        }
        if ((obj instanceof Integer) || (obj instanceof Long) || (obj instanceof Double) || (obj instanceof Float)) {
            return true;
        }
        MLog.e("value is " + obj + ", please check value, type illegal");
        return false;
    }

    public void a(ISysListener iSysListener) {
        if (!UMUtils.isMainProgress(f21220a)) {
            MLog.e("setSysListener can not be called in child process");
        } else {
            this.f21232b = iSysListener;
        }
    }

    public void a(Context context, int i2) {
        if (context == null) {
            MLog.e("unexpected null context in setVerticalType");
            return;
        }
        if (f21220a == null) {
            f21220a = context.getApplicationContext();
        }
        if (!UMUtils.isMainProgress(f21220a)) {
            MLog.e("setVerticalType can not be called in child process");
            return;
        }
        if (!this.f21238j || !this.f21242n) {
            a(f21220a);
        }
        AnalyticsConfig.a(f21220a, i2);
    }

    private void b(String str, Object obj) {
        try {
            if (this.f21239k == null) {
                this.f21239k = new JSONObject();
            }
            int i2 = 0;
            if (obj.getClass().isArray()) {
                if (obj instanceof String[]) {
                    String[] strArr = (String[]) obj;
                    if (strArr.length > 10) {
                        return;
                    }
                    JSONArray jSONArray = new JSONArray();
                    while (i2 < strArr.length) {
                        String str2 = strArr[i2];
                        if (str2 != null && !HelperUtils.checkStrLen(str2, 256)) {
                            jSONArray.put(strArr[i2]);
                        }
                        i2++;
                    }
                    this.f21239k.put(str, jSONArray);
                    return;
                }
                if (obj instanceof long[]) {
                    long[] jArr = (long[]) obj;
                    JSONArray jSONArray2 = new JSONArray();
                    while (i2 < jArr.length) {
                        jSONArray2.put(jArr[i2]);
                        i2++;
                    }
                    this.f21239k.put(str, jSONArray2);
                    return;
                }
                if (obj instanceof int[]) {
                    int[] iArr = (int[]) obj;
                    JSONArray jSONArray3 = new JSONArray();
                    while (i2 < iArr.length) {
                        jSONArray3.put(iArr[i2]);
                        i2++;
                    }
                    this.f21239k.put(str, jSONArray3);
                    return;
                }
                if (obj instanceof float[]) {
                    float[] fArr = (float[]) obj;
                    JSONArray jSONArray4 = new JSONArray();
                    while (i2 < fArr.length) {
                        jSONArray4.put(fArr[i2]);
                        i2++;
                    }
                    this.f21239k.put(str, jSONArray4);
                    return;
                }
                if (obj instanceof double[]) {
                    double[] dArr = (double[]) obj;
                    JSONArray jSONArray5 = new JSONArray();
                    while (i2 < dArr.length) {
                        jSONArray5.put(dArr[i2]);
                        i2++;
                    }
                    this.f21239k.put(str, jSONArray5);
                    return;
                }
                if (obj instanceof short[]) {
                    short[] sArr = (short[]) obj;
                    JSONArray jSONArray6 = new JSONArray();
                    while (i2 < sArr.length) {
                        jSONArray6.put((int) sArr[i2]);
                        i2++;
                    }
                    this.f21239k.put(str, jSONArray6);
                    return;
                }
                return;
            }
            if (obj instanceof List) {
                List list = (List) obj;
                JSONArray jSONArray7 = new JSONArray();
                while (i2 < list.size()) {
                    Object obj2 = list.get(i2);
                    if ((obj2 instanceof String) || (obj2 instanceof Long) || (obj2 instanceof Integer) || (obj2 instanceof Float) || (obj2 instanceof Double) || (obj2 instanceof Short)) {
                        jSONArray7.put(list.get(i2));
                    }
                    i2++;
                }
                this.f21239k.put(str, jSONArray7);
                return;
            }
            if ((obj instanceof String) || (obj instanceof Long) || (obj instanceof Integer) || (obj instanceof Float) || (obj instanceof Double) || (obj instanceof Short)) {
                this.f21239k.put(str, obj);
            }
        } catch (Throwable unused) {
        }
    }

    private boolean f(String str) {
        if (str != null) {
            try {
                int length = str.trim().getBytes().length;
                if (length > 0 && length < 128) {
                    return true;
                }
            } catch (Throwable unused) {
            }
        }
        MLog.e("The length of profile key must be less than 128 bytes.");
        return false;
    }

    public void a(Context context, String str, HashMap<String, Object> map) {
        if (context == null) {
            return;
        }
        try {
            if (f21220a == null) {
                f21220a = context.getApplicationContext();
            }
            if (!UMUtils.isMainProgress(f21220a)) {
                MLog.e("onGKVEvent can not be called in child process");
                return;
            }
            if (!this.f21238j || !this.f21242n) {
                a(f21220a);
            }
            String string = "";
            if (this.f21239k == null) {
                this.f21239k = new JSONObject();
            } else {
                string = this.f21239k.toString();
            }
            u.a(f21220a).a(str, map, string);
        } catch (Throwable th) {
            if (MLog.DEBUG) {
                MLog.e(th);
            }
        }
    }

    public void c(String str) {
        if (g(str)) {
            a(f.O, (Object) str);
        }
    }

    public void d(String str) {
        if (g(str)) {
            a(f.P, (Object) str);
        }
    }

    void a(Context context, String str) {
        if (context == null) {
            UMLog.aq(l.f21822w, 0, "\\|");
            return;
        }
        if (f21220a == null) {
            f21220a = context.getApplicationContext();
        }
        if (!UMUtils.isMainProgress(f21220a)) {
            MLog.e("reportError can not be called in child process");
            return;
        }
        if (TextUtils.isEmpty(str)) {
            if (UMConfigure.isDebugLog()) {
                UMLog.aq(l.f21823x, 0, "\\|");
                return;
            }
            return;
        }
        try {
            if (!this.f21238j || !this.f21242n) {
                a(f21220a);
            }
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("ts", System.currentTimeMillis());
            jSONObject.put(f.W, 2);
            jSONObject.put(f.X, str);
            jSONObject.put("__ii", this.f21236f.c());
            Context context2 = f21220a;
            UMWorkDispatch.sendEvent(context2, 4106, CoreProtocol.getInstance(context2), jSONObject);
        } catch (Throwable th) {
            if (MLog.DEBUG) {
                MLog.e(th);
            }
        }
    }

    void a(Context context, Throwable th) {
        if (context != null && th != null) {
            if (f21220a == null) {
                f21220a = context.getApplicationContext();
            }
            if (!UMUtils.isMainProgress(f21220a)) {
                MLog.e("reportError can not be called in child process");
                return;
            }
            try {
                if (!this.f21238j || !this.f21242n) {
                    a(f21220a);
                }
                a(f21220a, DataHelper.convertExceptionToString(th));
                return;
            } catch (Exception e2) {
                if (MLog.DEBUG) {
                    MLog.e(e2);
                    return;
                }
                return;
            }
        }
        UMLog.aq(l.f21824y, 0, "\\|");
    }

    public void a(Context context, String str, String str2, long j2, int i2) {
        if (context == null) {
            return;
        }
        try {
            if (f21220a == null) {
                f21220a = context.getApplicationContext();
            }
            if (!this.f21238j || !this.f21242n) {
                a(f21220a);
            }
            if (e(str)) {
                UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> filter ekv [" + str + "].");
                return;
            }
            String string = "";
            if (this.f21239k == null) {
                this.f21239k = new JSONObject();
            } else {
                string = this.f21239k.toString();
            }
            u.a(f21220a).a(str, str2, j2, i2, string);
        } catch (Throwable th) {
            if (MLog.DEBUG) {
                MLog.e(th);
            }
        }
    }

    public synchronized void b(Object obj) {
        Context context;
        try {
            context = f21220a;
        } catch (Throwable unused) {
        }
        if (context == null) {
            return;
        }
        if (!UMUtils.isMainProgress(context)) {
            MLog.e("updateNativePrePropertiesByCoreProtocol can not be called in child process");
            return;
        }
        SharedPreferences.Editor editorEdit = PreferenceWrapper.getDefault(f21220a).edit();
        if (obj != null) {
            String str = (String) obj;
            if (editorEdit != null && !TextUtils.isEmpty(str)) {
                editorEdit.putString(f21222i, str).commit();
            }
        } else if (editorEdit != null) {
            editorEdit.remove(f21222i).commit();
        }
    }

    void a(Context context, String str, Map<String, Object> map, long j2) {
        try {
        } catch (Throwable th) {
            if (MLog.DEBUG) {
                MLog.e(th);
            }
        }
        if (!TextUtils.isEmpty(str)) {
            if (Arrays.asList(f.aL).contains(str)) {
                UMLog.aq(l.f21801b, 0, "\\|");
                return;
            }
            if (map.isEmpty()) {
                UMLog.aq(l.f21803d, 0, "\\|");
                return;
            }
            Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
            while (it.hasNext()) {
                if (Arrays.asList(f.aL).contains(it.next().getKey())) {
                    UMLog.aq(l.f21804e, 0, "\\|");
                    return;
                }
            }
            a(context, str, map, j2, false);
            return;
        }
        UMLog.aq(l.f21802c, 0, "\\|");
    }

    public void a(Context context, String str, Map<String, Object> map) {
        a(context, str, map, -1L, true);
    }

    private void a(Context context, String str, Map<String, Object> map, long j2, boolean z2) {
        try {
            if (context == null) {
                MLog.e("context is null in onEventNoCheck, please check!");
                return;
            }
            if (f21220a == null) {
                f21220a = context.getApplicationContext();
            }
            if (!this.f21238j || !this.f21242n) {
                a(f21220a);
            }
            if (e(str)) {
                UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> filter ekv [" + str + "].");
                return;
            }
            String string = "";
            if (this.f21239k == null) {
                this.f21239k = new JSONObject();
            } else {
                string = this.f21239k.toString();
            }
            u.a(f21220a).a(str, map, j2, string, z2);
        } catch (Throwable th) {
            if (MLog.DEBUG) {
                MLog.e(th);
            }
        }
    }

    @Override // com.umeng.analytics.pro.v
    public void a(Throwable th) throws JSONException {
        try {
            Context context = f21220a;
            if (context == null) {
                return;
            }
            if (!UMUtils.isMainProgress(context)) {
                MLog.e("onAppCrash can not be called in child process");
                return;
            }
            if (AnalyticsConfig.enable) {
                x xVar = this.f21234d;
                if (xVar != null) {
                    xVar.b();
                }
                n.a(f21220a, "onAppCrash");
                m mVar = this.f21235e;
                if (mVar != null) {
                    mVar.b();
                }
                n nVar = this.f21237g;
                if (nVar != null) {
                    nVar.c();
                }
                w wVar = this.f21236f;
                if (wVar != null) {
                    wVar.c(f21220a, Long.valueOf(System.currentTimeMillis()));
                }
                if (th != null) {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("ts", System.currentTimeMillis());
                    jSONObject.put(f.W, 1);
                    jSONObject.put(f.X, DataHelper.convertExceptionToString(th));
                    k.a(f21220a).a(this.f21236f.c(), jSONObject.toString(), 1);
                }
                q.a(f21220a).d();
                x.a(f21220a);
                if (UMConfigure.AUTO_ACTIVITY_PAGE_COLLECTION == MobclickAgent.PageMode.AUTO) {
                    n.c(f21220a);
                }
                PreferenceWrapper.getDefault(f21220a).edit().commit();
            }
        } catch (Exception e2) {
            if (MLog.DEBUG) {
                MLog.e("Exception in onAppCrash", e2);
            }
        }
    }

    void a(String str, String str2) {
        try {
            Context context = f21220a;
            if (context == null) {
                return;
            }
            if (!UMUtils.isMainProgress(context)) {
                MLog.e("onProfileSignIn can not be called in child process");
                return;
            }
            long jCurrentTimeMillis = System.currentTimeMillis();
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(f.M, str);
            jSONObject.put("uid", str2);
            jSONObject.put("ts", jCurrentTimeMillis);
            Context context2 = f21220a;
            UMWorkDispatch.sendEvent(context2, 4101, CoreProtocol.getInstance(context2), jSONObject);
            Context context3 = f21220a;
            UMWorkDispatch.sendEvent(context3, q.a.f21893o, CoreProtocol.getInstance(context3), jSONObject);
        } catch (Throwable th) {
            if (MLog.DEBUG) {
                MLog.e(" Excepthon  in  onProfileSignIn", th);
            }
        }
    }

    void a(boolean z2) {
        Context context = f21220a;
        if (context == null) {
            return;
        }
        if (!UMUtils.isMainProgress(context)) {
            MLog.e("setCatchUncaughtExceptions can not be called in child process");
        } else {
            if (AnalyticsConfig.CHANGE_CATCH_EXCEPTION_NOTALLOW) {
                return;
            }
            AnalyticsConfig.CATCH_EXCEPTION = z2;
        }
    }

    void a(GL10 gl10) {
        String[] gpu = UMUtils.getGPU(gl10);
        if (gpu.length == 2) {
            AnalyticsConfig.GPU_VENDER = gpu[0];
            AnalyticsConfig.GPU_RENDERER = gpu[1];
        }
    }

    void a(double d2, double d3) {
        Context context = f21220a;
        if (context == null) {
            return;
        }
        if (!UMUtils.isMainProgress(context)) {
            MLog.e("setLocation can not be called in child process");
            return;
        }
        if (AnalyticsConfig.f21197a == null) {
            AnalyticsConfig.f21197a = new double[2];
        }
        double[] dArr = AnalyticsConfig.f21197a;
        dArr[0] = d2;
        dArr[1] = d3;
    }

    void a(Context context, MobclickAgent.EScenarioType eScenarioType) {
        if (context == null) {
            MLog.e("unexpected null context in setScenarioType");
            return;
        }
        if (f21220a == null) {
            f21220a = context.getApplicationContext();
        }
        if (!UMUtils.isMainProgress(f21220a)) {
            MLog.e("setScenarioType can not be called in child process");
            return;
        }
        if (eScenarioType != null) {
            a(f21220a, eScenarioType.toValue());
        }
        if (this.f21238j && this.f21242n) {
            return;
        }
        a(f21220a);
    }

    void a(long j2) {
        Context context = f21220a;
        if (context == null) {
            return;
        }
        if (!UMUtils.isMainProgress(context)) {
            MLog.e("setSessionContinueMillis can not be called in child process");
        } else {
            AnalyticsConfig.kContinueSessionMillis = j2;
            aa.a().a(AnalyticsConfig.kContinueSessionMillis);
        }
    }

    public synchronized void a(Context context, String str, Object obj) {
        int i2 = 0;
        if (context == null) {
            UMLog.aq(l.af, 0, "\\|");
            return;
        }
        if (f21220a == null) {
            f21220a = context.getApplicationContext();
        }
        if (!UMUtils.isMainProgress(f21220a)) {
            MLog.e("registerSuperProperty can not be called in child process");
            return;
        }
        if (!this.f21238j || !this.f21242n) {
            a(f21220a);
        }
        if (!TextUtils.isEmpty(str) && obj != null) {
            if (!str.equals(f21231z) && !str.equals(A) && !str.equals(B) && !str.equals(C) && !str.equals(D)) {
                MLog.e("property name is " + str + ", please check key, must be correct!");
                return;
            }
            if ((obj instanceof String) && !HelperUtils.checkStrLen(obj.toString(), 256)) {
                MLog.e("property value is " + obj + ", please check value, lawless!");
                return;
            }
            try {
                if (this.f21239k == null) {
                    this.f21239k = new JSONObject();
                }
                if (obj.getClass().isArray()) {
                    if (obj instanceof String[]) {
                        String[] strArr = (String[]) obj;
                        if (strArr.length > 10) {
                            MLog.e("please check value, size is " + strArr.length + ", overstep 10!");
                            return;
                        }
                        JSONArray jSONArray = new JSONArray();
                        while (i2 < strArr.length) {
                            String str2 = strArr[i2];
                            if (str2 != null && HelperUtils.checkStrLen(str2, 256)) {
                                jSONArray.put(strArr[i2]);
                                i2++;
                            }
                            MLog.e("please check value, length is " + strArr[i2].length() + ", overlength 256!");
                            return;
                        }
                        this.f21239k.put(str, jSONArray);
                    } else if (obj instanceof long[]) {
                        long[] jArr = (long[]) obj;
                        if (jArr.length > 10) {
                            MLog.e("please check value, size is " + jArr.length + ", overstep 10!");
                            return;
                        }
                        JSONArray jSONArray2 = new JSONArray();
                        while (i2 < jArr.length) {
                            jSONArray2.put(jArr[i2]);
                            i2++;
                        }
                        this.f21239k.put(str, jSONArray2);
                    } else if (obj instanceof int[]) {
                        int[] iArr = (int[]) obj;
                        if (iArr.length > 10) {
                            MLog.e("please check value, size is " + iArr.length + ", overstep 10!");
                            return;
                        }
                        JSONArray jSONArray3 = new JSONArray();
                        while (i2 < iArr.length) {
                            jSONArray3.put(iArr[i2]);
                            i2++;
                        }
                        this.f21239k.put(str, jSONArray3);
                    } else if (obj instanceof float[]) {
                        float[] fArr = (float[]) obj;
                        if (fArr.length > 10) {
                            MLog.e("please check value, size is " + fArr.length + ", overstep 10!");
                            return;
                        }
                        JSONArray jSONArray4 = new JSONArray();
                        while (i2 < fArr.length) {
                            jSONArray4.put(fArr[i2]);
                            i2++;
                        }
                        this.f21239k.put(str, jSONArray4);
                    } else if (obj instanceof double[]) {
                        double[] dArr = (double[]) obj;
                        if (dArr.length > 10) {
                            MLog.e("please check value, size is " + dArr.length + ", overstep 10!");
                            return;
                        }
                        JSONArray jSONArray5 = new JSONArray();
                        while (i2 < dArr.length) {
                            jSONArray5.put(dArr[i2]);
                            i2++;
                        }
                        this.f21239k.put(str, jSONArray5);
                    } else if (obj instanceof short[]) {
                        short[] sArr = (short[]) obj;
                        if (sArr.length > 10) {
                            MLog.e("please check value, size is " + sArr.length + ", overstep 10!");
                            return;
                        }
                        JSONArray jSONArray6 = new JSONArray();
                        while (i2 < sArr.length) {
                            jSONArray6.put((int) sArr[i2]);
                            i2++;
                        }
                        this.f21239k.put(str, jSONArray6);
                    } else {
                        MLog.e("please check value, illegal type!");
                        return;
                    }
                } else {
                    if (!(obj instanceof String) && !(obj instanceof Long) && !(obj instanceof Integer) && !(obj instanceof Float) && !(obj instanceof Double) && !(obj instanceof Short)) {
                        MLog.e("please check value, illegal type!");
                        return;
                    }
                    this.f21239k.put(str, obj);
                }
            } catch (Throwable unused) {
            }
            Context context2 = f21220a;
            UMWorkDispatch.sendEvent(context2, q.a.f21898t, CoreProtocol.getInstance(context2), this.f21239k.toString());
            return;
        }
        UMLog.aq(l.ag, 0, "\\|");
    }

    public synchronized void a(Object obj) {
        Context context;
        try {
            context = f21220a;
        } catch (Throwable unused) {
        }
        if (context == null) {
            return;
        }
        if (!UMUtils.isMainProgress(context)) {
            MLog.e("registerSuperPropertyByCoreProtocol can not be called in child process");
            return;
        }
        if (obj != null) {
            String str = (String) obj;
            SharedPreferences.Editor editorEdit = PreferenceWrapper.getDefault(f21220a).edit();
            if (editorEdit != null && !TextUtils.isEmpty(str)) {
                editorEdit.putString(f21221h, this.f21239k.toString()).commit();
            }
        }
    }

    public synchronized void a(Context context, List<String> list) {
        try {
        } finally {
        }
        if (context == null) {
            UMLog.aq(l.aj, 0, "\\|");
            return;
        }
        if (f21220a == null) {
            f21220a = context.getApplicationContext();
        }
        if (!UMUtils.isMainProgress(f21220a)) {
            MLog.e("setFirstLaunchEvent can not be called in child process");
            return;
        }
        if (!this.f21238j || !this.f21242n) {
            a(f21220a);
        }
        u.a(f21220a).a(list);
    }

    public synchronized void a(Context context, JSONObject jSONObject) {
        JSONObject jSONObject2;
        String string;
        Object obj;
        if (context == null) {
            UMLog.aq(l.al, 0, "\\|");
            return;
        }
        if (f21220a == null) {
            f21220a = context.getApplicationContext();
        }
        if (!UMUtils.isMainProgress(f21220a)) {
            MLog.e("registerPreProperties can not be called in child process");
            return;
        }
        if (!this.f21238j || !this.f21242n) {
            a(f21220a);
        }
        if (this.f21241m == null) {
            this.f21241m = new JSONObject();
        }
        if (jSONObject != null && jSONObject.length() > 0) {
            try {
                jSONObject2 = new JSONObject(this.f21241m.toString());
            } catch (Exception unused) {
                jSONObject2 = null;
            }
            if (jSONObject2 == null) {
                jSONObject2 = new JSONObject();
            }
            Iterator<String> itKeys = jSONObject.keys();
            if (itKeys != null) {
                while (itKeys.hasNext()) {
                    try {
                        string = itKeys.next().toString();
                        obj = jSONObject.get(string);
                    } catch (Exception unused2) {
                    }
                    if (c(string, obj)) {
                        jSONObject2.put(string, obj);
                        if (jSONObject2.length() > 10) {
                            MLog.e("please check propertics, size overlength!");
                            return;
                        }
                        continue;
                    } else {
                        return;
                    }
                }
            }
            this.f21241m = jSONObject2;
            if (this.f21241m.length() > 0) {
                Context context2 = f21220a;
                UMWorkDispatch.sendEvent(context2, q.a.f21901w, CoreProtocol.getInstance(context2), this.f21241m.toString());
            }
            return;
        }
        UMLog.aq(l.am, 0, "\\|");
    }

    public void a(String str, Object obj) {
        if (f(str)) {
            if (!(obj instanceof String) && !(obj instanceof Integer) && !(obj instanceof Long) && !(obj instanceof Short) && !(obj instanceof Float) && !(obj instanceof Double)) {
                MLog.e("userProfile: Invalid value type, please check!");
                return;
            }
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("pk", str);
                if (obj instanceof String) {
                    String strTrim = (String) obj;
                    if (strTrim.length() > 0) {
                        strTrim = strTrim.trim();
                    }
                    if (!g(strTrim)) {
                        return;
                    } else {
                        jSONObject.put(f.T, strTrim);
                    }
                } else {
                    jSONObject.put(f.T, obj);
                }
                Context context = f21220a;
                UMWorkDispatch.sendEvent(context, q.a.f21895q, CoreProtocol.getInstance(context), jSONObject);
            } catch (Throwable unused) {
            }
        }
    }
}
