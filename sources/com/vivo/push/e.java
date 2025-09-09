package com.vivo.push;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.SparseArray;
import androidx.media3.exoplayer.ExoPlayer;
import com.vivo.push.sdk.PushMessageCallback;
import com.vivo.push.util.ContextDelegate;
import com.vivo.push.util.VivoPushException;
import com.vivo.push.util.t;
import com.vivo.push.util.w;
import com.vivo.push.util.z;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public final class e {

    /* renamed from: a, reason: collision with root package name */
    private static volatile e f23138a;

    /* renamed from: h, reason: collision with root package name */
    private Context f23145h;

    /* renamed from: j, reason: collision with root package name */
    private com.vivo.push.util.b f23147j;

    /* renamed from: k, reason: collision with root package name */
    private String f23148k;

    /* renamed from: l, reason: collision with root package name */
    private String f23149l;

    /* renamed from: o, reason: collision with root package name */
    private Boolean f23152o;

    /* renamed from: p, reason: collision with root package name */
    private Long f23153p;

    /* renamed from: q, reason: collision with root package name */
    private boolean f23154q;

    /* renamed from: s, reason: collision with root package name */
    private int f23156s;

    /* renamed from: b, reason: collision with root package name */
    private long f23139b = -1;

    /* renamed from: c, reason: collision with root package name */
    private long f23140c = -1;

    /* renamed from: d, reason: collision with root package name */
    private long f23141d = -1;

    /* renamed from: e, reason: collision with root package name */
    private long f23142e = -1;

    /* renamed from: f, reason: collision with root package name */
    private long f23143f = -1;

    /* renamed from: g, reason: collision with root package name */
    private long f23144g = -1;

    /* renamed from: i, reason: collision with root package name */
    private boolean f23146i = true;

    /* renamed from: m, reason: collision with root package name */
    private SparseArray<a> f23150m = new SparseArray<>();

    /* renamed from: n, reason: collision with root package name */
    private int f23151n = 0;

    /* renamed from: r, reason: collision with root package name */
    private IPushClientFactory f23155r = new d();

    private e() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void m() {
        this.f23149l = null;
        this.f23147j.b("APP_ALIAS");
    }

    private boolean n() {
        if (this.f23152o == null) {
            this.f23152o = Boolean.valueOf(l() >= 1230 && z.d(this.f23145h));
        }
        return this.f23152o.booleanValue();
    }

    public final boolean d() {
        if (this.f23145h == null) {
            com.vivo.push.util.p.d("PushClientManager", "support:context is null");
            return false;
        }
        Boolean boolValueOf = Boolean.valueOf(n());
        this.f23152o = boolValueOf;
        return boolValueOf.booleanValue();
    }

    public final boolean e() {
        return this.f23154q;
    }

    public final String f() {
        if (!TextUtils.isEmpty(this.f23148k)) {
            return this.f23148k;
        }
        com.vivo.push.util.b bVar = this.f23147j;
        String strB = bVar != null ? bVar.b("APP_TOKEN", (String) null) : "";
        c(strB);
        return strB;
    }

    public final boolean g() {
        return this.f23146i;
    }

    public final Context h() {
        return this.f23145h;
    }

    public final void i() {
        this.f23147j.a();
    }

    public final String j() {
        return this.f23149l;
    }

    public final int k() {
        return this.f23156s;
    }

    public final long l() {
        Context context = this.f23145h;
        if (context == null) {
            return -1L;
        }
        if (this.f23153p == null) {
            this.f23153p = Long.valueOf(z.a(context));
        }
        return this.f23153p.longValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void e(String str) {
        m.a(new k(this, str));
    }

    public static synchronized e a() {
        try {
            if (f23138a == null) {
                f23138a = new e();
            }
        } catch (Throwable th) {
            throw th;
        }
        return f23138a;
    }

    protected final void b() throws PackageManager.NameNotFoundException, VivoPushException {
        Context context = this.f23145h;
        if (context != null) {
            z.b(context);
        }
    }

    public final List<String> c() {
        String strB = this.f23147j.b("APP_TAGS", (String) null);
        ArrayList arrayList = new ArrayList();
        try {
        } catch (JSONException unused) {
            this.f23147j.b("APP_TAGS");
            arrayList.clear();
            com.vivo.push.util.p.d("PushClientManager", "getTags error");
        }
        if (TextUtils.isEmpty(strB)) {
            return arrayList;
        }
        Iterator<String> itKeys = new JSONObject(strB).keys();
        while (itKeys.hasNext()) {
            arrayList.add(itKeys.next());
        }
        return arrayList;
    }

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        private IPushActionListener f23159a;

        /* renamed from: b, reason: collision with root package name */
        private com.vivo.push.b.c f23160b;

        /* renamed from: c, reason: collision with root package name */
        private IPushActionListener f23161c;

        /* renamed from: d, reason: collision with root package name */
        private Runnable f23162d;

        /* renamed from: e, reason: collision with root package name */
        private Object[] f23163e;

        public a(com.vivo.push.b.c cVar, IPushActionListener iPushActionListener) {
            this.f23160b = cVar;
            this.f23159a = iPushActionListener;
        }

        public final void a(int i2, Object... objArr) {
            this.f23163e = objArr;
            IPushActionListener iPushActionListener = this.f23161c;
            if (iPushActionListener != null) {
                iPushActionListener.onStateChanged(i2);
            }
            IPushActionListener iPushActionListener2 = this.f23159a;
            if (iPushActionListener2 != null) {
                iPushActionListener2.onStateChanged(i2);
            }
        }

        public final Object[] b() {
            return this.f23163e;
        }

        public final void a(Runnable runnable) {
            this.f23162d = runnable;
        }

        public final void a() {
            Runnable runnable = this.f23162d;
            if (runnable == null) {
                com.vivo.push.util.p.a("PushClientManager", "task is null");
            } else {
                runnable.run();
            }
        }

        public final void a(IPushActionListener iPushActionListener) {
            this.f23161c = iPushActionListener;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized a d(String str) {
        if (str != null) {
            try {
                int i2 = Integer.parseInt(str);
                a aVar = this.f23150m.get(i2);
                this.f23150m.delete(i2);
                return aVar;
            } catch (Exception unused) {
            }
        }
        return null;
    }

    public final void b(List<String> list) {
        JSONObject jSONObject;
        try {
            if (list.size() <= 0) {
                return;
            }
            String strB = this.f23147j.b("APP_TAGS", (String) null);
            if (TextUtils.isEmpty(strB)) {
                jSONObject = new JSONObject();
            } else {
                jSONObject = new JSONObject(strB);
            }
            Iterator<String> it = list.iterator();
            while (it.hasNext()) {
                jSONObject.remove(it.next());
            }
            String string = jSONObject.toString();
            if (TextUtils.isEmpty(string)) {
                this.f23147j.b("APP_TAGS");
            } else {
                this.f23147j.a("APP_TAGS", string);
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
            this.f23147j.b("APP_TAGS");
        }
    }

    public final synchronized void a(Context context) {
        if (this.f23145h == null) {
            this.f23145h = ContextDelegate.getContext(context);
            this.f23154q = t.c(context, context.getPackageName());
            w.b().a(this.f23145h);
            a(new com.vivo.push.b.g());
            com.vivo.push.util.b bVar = new com.vivo.push.util.b();
            this.f23147j = bVar;
            bVar.a(this.f23145h, "com.vivo.push_preferences.appconfig_v1");
            this.f23148k = f();
            this.f23149l = this.f23147j.b("APP_ALIAS", (String) null);
        }
    }

    public final void c(List<String> list) {
        if (list.contains(this.f23149l)) {
            m();
        }
    }

    private void c(String str) {
        m.c(new f(this, str));
    }

    public final void a(List<String> list) throws JSONException {
        JSONObject jSONObject;
        try {
            if (list.size() <= 0) {
                return;
            }
            String strB = this.f23147j.b("APP_TAGS", (String) null);
            if (TextUtils.isEmpty(strB)) {
                jSONObject = new JSONObject();
            } else {
                jSONObject = new JSONObject(strB);
            }
            Iterator<String> it = list.iterator();
            while (it.hasNext()) {
                jSONObject.put(it.next(), System.currentTimeMillis());
            }
            String string = jSONObject.toString();
            if (TextUtils.isEmpty(string)) {
                this.f23147j.b("APP_TAGS");
            } else {
                this.f23147j.a("APP_TAGS", string);
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
            this.f23147j.b("APP_TAGS");
        }
    }

    final void b(IPushActionListener iPushActionListener) {
        if (this.f23145h == null) {
            if (iPushActionListener != null) {
                iPushActionListener.onStateChanged(102);
                return;
            }
            return;
        }
        if ("".equals(this.f23148k)) {
            iPushActionListener.onStateChanged(0);
            return;
        }
        if (!a(this.f23140c)) {
            if (iPushActionListener != null) {
                iPushActionListener.onStateChanged(1002);
                return;
            }
            return;
        }
        this.f23140c = SystemClock.elapsedRealtime();
        String packageName = this.f23145h.getPackageName();
        a aVarA = null;
        if (this.f23145h != null) {
            com.vivo.push.b.b bVar = new com.vivo.push.b.b(false, packageName);
            bVar.d();
            bVar.e();
            bVar.g();
            bVar.a(100);
            if (this.f23154q) {
                if (n()) {
                    aVarA = new a(bVar, iPushActionListener);
                    String strA = a(aVarA);
                    bVar.b(strA);
                    aVarA.a(new j(this, bVar, strA));
                } else if (iPushActionListener != null) {
                    iPushActionListener.onStateChanged(101);
                }
            } else if (bVar.a(this.f23145h) == 2) {
                aVarA = a(bVar, iPushActionListener);
            } else {
                a(bVar);
                if (iPushActionListener != null) {
                    iPushActionListener.onStateChanged(0);
                }
            }
        } else if (iPushActionListener != null) {
            iPushActionListener.onStateChanged(102);
        }
        if (aVarA == null) {
            return;
        }
        aVarA.a(new i(this));
        aVarA.a();
    }

    public final void a(String str) {
        this.f23148k = str;
        this.f23147j.a("APP_TOKEN", str);
    }

    protected final void a(boolean z2) {
        this.f23146i = z2;
    }

    final void a(IPushActionListener iPushActionListener) {
        if (this.f23145h == null) {
            if (iPushActionListener != null) {
                iPushActionListener.onStateChanged(102);
                return;
            }
            return;
        }
        String strF = f();
        this.f23148k = strF;
        if (!TextUtils.isEmpty(strF)) {
            if (iPushActionListener != null) {
                iPushActionListener.onStateChanged(0);
                return;
            }
            return;
        }
        if (!a(this.f23139b)) {
            if (iPushActionListener != null) {
                iPushActionListener.onStateChanged(1002);
                return;
            }
            return;
        }
        this.f23139b = SystemClock.elapsedRealtime();
        String packageName = this.f23145h.getPackageName();
        a aVarA = null;
        if (this.f23145h != null) {
            com.vivo.push.b.b bVar = new com.vivo.push.b.b(true, packageName);
            bVar.g();
            bVar.d();
            bVar.e();
            bVar.a(100);
            if (this.f23154q) {
                if (n()) {
                    aVarA = a(bVar, iPushActionListener);
                } else if (iPushActionListener != null) {
                    iPushActionListener.onStateChanged(101);
                }
            } else if (bVar.a(this.f23145h) == 2) {
                aVarA = a(bVar, iPushActionListener);
            } else {
                a(bVar);
                if (iPushActionListener != null) {
                    iPushActionListener.onStateChanged(0);
                }
            }
        } else if (iPushActionListener != null) {
            iPushActionListener.onStateChanged(102);
        }
        if (aVarA == null) {
            return;
        }
        aVarA.a(new g(this, aVarA));
        aVarA.a();
    }

    final void b(String str, IPushActionListener iPushActionListener) {
        if (this.f23145h == null) {
            if (iPushActionListener != null) {
                iPushActionListener.onStateChanged(102);
                return;
            }
            return;
        }
        if (TextUtils.isEmpty(this.f23149l)) {
            if (iPushActionListener != null) {
                iPushActionListener.onStateChanged(0);
                return;
            }
            return;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(str);
        com.vivo.push.b.a aVar = new com.vivo.push.b.a(false, this.f23145h.getPackageName(), arrayList);
        aVar.a(100);
        if (this.f23154q) {
            if (!n()) {
                if (iPushActionListener != null) {
                    iPushActionListener.onStateChanged(101);
                    return;
                }
                return;
            }
            if (!a(this.f23142e)) {
                if (iPushActionListener != null) {
                    iPushActionListener.onStateChanged(1002);
                    return;
                }
                return;
            }
            this.f23142e = SystemClock.elapsedRealtime();
            String strA = a(new a(aVar, iPushActionListener));
            aVar.b(strA);
            if (TextUtils.isEmpty(this.f23148k)) {
                a(strA, 30001);
                return;
            }
            if (TextUtils.isEmpty(str)) {
                a(strA, 30002);
                return;
            } else if (str.length() > 70) {
                a(strA, 30003);
                return;
            } else {
                a(aVar);
                e(strA);
                return;
            }
        }
        a(aVar);
        if (iPushActionListener != null) {
            iPushActionListener.onStateChanged(0);
        }
    }

    private a a(com.vivo.push.b.b bVar, IPushActionListener iPushActionListener) {
        a aVar = new a(bVar, iPushActionListener);
        String strA = a(aVar);
        bVar.b(strA);
        aVar.a(new h(this, bVar, strA));
        return aVar;
    }

    public final void a(String str, int i2, Object... objArr) {
        a aVarD = d(str);
        if (aVarD != null) {
            aVarD.a(i2, objArr);
        } else {
            com.vivo.push.util.p.d("PushClientManager", "notifyApp token is null");
        }
    }

    final void a(String str, IPushActionListener iPushActionListener) {
        if (this.f23145h == null) {
            if (iPushActionListener != null) {
                iPushActionListener.onStateChanged(102);
                return;
            }
            return;
        }
        if (!TextUtils.isEmpty(this.f23149l) && this.f23149l.equals(str)) {
            if (iPushActionListener != null) {
                iPushActionListener.onStateChanged(0);
                return;
            }
            return;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(str);
        com.vivo.push.b.a aVar = new com.vivo.push.b.a(true, this.f23145h.getPackageName(), arrayList);
        aVar.a(100);
        if (this.f23154q) {
            if (!n()) {
                if (iPushActionListener != null) {
                    iPushActionListener.onStateChanged(101);
                    return;
                }
                return;
            }
            if (!a(this.f23141d)) {
                if (iPushActionListener != null) {
                    iPushActionListener.onStateChanged(1002);
                    return;
                }
                return;
            }
            this.f23141d = SystemClock.elapsedRealtime();
            String strA = a(new a(aVar, iPushActionListener));
            aVar.b(strA);
            if (TextUtils.isEmpty(this.f23148k)) {
                a(strA, 30001);
                return;
            }
            if (TextUtils.isEmpty(str)) {
                a(strA, 30002);
                return;
            } else if (str.length() > 70) {
                a(strA, 30003);
                return;
            } else {
                a(aVar);
                e(strA);
                return;
            }
        }
        a(aVar);
        if (iPushActionListener != null) {
            iPushActionListener.onStateChanged(0);
        }
    }

    public final void b(String str) {
        this.f23149l = str;
        this.f23147j.a("APP_ALIAS", str);
    }

    final void b(ArrayList<String> arrayList, IPushActionListener iPushActionListener) {
        Context context = this.f23145h;
        if (context == null) {
            if (iPushActionListener != null) {
                iPushActionListener.onStateChanged(102);
                return;
            }
            return;
        }
        com.vivo.push.b.z zVar = new com.vivo.push.b.z(false, context.getPackageName(), arrayList);
        zVar.a(500);
        if (this.f23154q) {
            if (!n()) {
                if (iPushActionListener != null) {
                    iPushActionListener.onStateChanged(101);
                    return;
                }
                return;
            }
            if (!a(this.f23144g)) {
                if (iPushActionListener != null) {
                    iPushActionListener.onStateChanged(1002);
                    return;
                }
                return;
            }
            this.f23144g = SystemClock.elapsedRealtime();
            String strA = a(new a(zVar, iPushActionListener));
            zVar.b(strA);
            if (TextUtils.isEmpty(this.f23148k)) {
                a(strA, 20001);
                return;
            }
            if (arrayList.size() < 0) {
                a(strA, 20002);
                return;
            }
            if (arrayList.size() > 500) {
                a(strA, 20004);
                return;
            }
            Iterator<String> it = arrayList.iterator();
            while (it.hasNext()) {
                if (it.next().length() > 70) {
                    a(strA, 20003);
                    return;
                }
            }
            a(zVar);
            e(strA);
            return;
        }
        a(zVar);
        if (iPushActionListener != null) {
            iPushActionListener.onStateChanged(0);
        }
    }

    private static boolean a(long j2) {
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        return j2 == -1 || jElapsedRealtime <= j2 || jElapsedRealtime >= j2 + ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS;
    }

    public final void a(String str, int i2) {
        a aVarD = d(str);
        if (aVarD != null) {
            aVarD.a(i2, new Object[0]);
        } else {
            com.vivo.push.util.p.d("PushClientManager", "notifyStatusChanged token is null");
        }
    }

    private synchronized String a(a aVar) {
        int i2;
        this.f23150m.put(this.f23151n, aVar);
        i2 = this.f23151n;
        this.f23151n = i2 + 1;
        return Integer.toString(i2);
    }

    final void a(ArrayList<String> arrayList, IPushActionListener iPushActionListener) {
        Context context = this.f23145h;
        if (context == null) {
            if (iPushActionListener != null) {
                iPushActionListener.onStateChanged(102);
                return;
            }
            return;
        }
        com.vivo.push.b.z zVar = new com.vivo.push.b.z(true, context.getPackageName(), arrayList);
        zVar.a(500);
        if (this.f23154q) {
            if (!n()) {
                if (iPushActionListener != null) {
                    iPushActionListener.onStateChanged(101);
                    return;
                }
                return;
            }
            if (!a(this.f23143f)) {
                if (iPushActionListener != null) {
                    iPushActionListener.onStateChanged(1002);
                    return;
                }
                return;
            }
            this.f23143f = SystemClock.elapsedRealtime();
            String strA = a(new a(zVar, iPushActionListener));
            zVar.b(strA);
            if (TextUtils.isEmpty(this.f23148k)) {
                a(strA, 20001);
                return;
            }
            if (arrayList.size() < 0) {
                a(strA, 20002);
                return;
            }
            if (arrayList.size() + c().size() > 500) {
                a(strA, 20004);
                return;
            }
            Iterator<String> it = arrayList.iterator();
            while (it.hasNext()) {
                if (it.next().length() > 70) {
                    a(strA, 20003);
                    return;
                }
            }
            a(zVar);
            e(strA);
            return;
        }
        a(zVar);
        if (iPushActionListener != null) {
            iPushActionListener.onStateChanged(0);
        }
    }

    public final void a(Intent intent, PushMessageCallback pushMessageCallback) {
        o oVarCreateReceiverCommand = this.f23155r.createReceiverCommand(intent);
        Context context = a().f23145h;
        if (oVarCreateReceiverCommand == null) {
            com.vivo.push.util.p.a("PushClientManager", "sendCommand, null command!");
            if (context != null) {
                com.vivo.push.util.p.c(context, "[执行指令失败]指令空！");
                return;
            }
            return;
        }
        com.vivo.push.d.z zVarCreateReceiveTask = this.f23155r.createReceiveTask(oVarCreateReceiverCommand);
        if (zVarCreateReceiveTask == null) {
            com.vivo.push.util.p.a("PushClientManager", "sendCommand, null command task! pushCommand = ".concat(String.valueOf(oVarCreateReceiverCommand)));
            if (context != null) {
                com.vivo.push.util.p.c(context, "[执行指令失败]指令" + oVarCreateReceiverCommand + "任务空！");
                return;
            }
            return;
        }
        if (context != null && !(oVarCreateReceiverCommand instanceof com.vivo.push.b.n)) {
            com.vivo.push.util.p.a(context, "[接收指令]".concat(String.valueOf(oVarCreateReceiverCommand)));
        }
        zVarCreateReceiveTask.a(pushMessageCallback);
        m.a((l) zVarCreateReceiveTask);
    }

    public final void a(o oVar) {
        Context context = a().f23145h;
        if (oVar == null) {
            com.vivo.push.util.p.a("PushClientManager", "sendCommand, null command!");
            if (context != null) {
                com.vivo.push.util.p.c(context, "[执行指令失败]指令空！");
                return;
            }
            return;
        }
        l lVarCreateTask = this.f23155r.createTask(oVar);
        if (lVarCreateTask == null) {
            com.vivo.push.util.p.a("PushClientManager", "sendCommand, null command task! pushCommand = ".concat(String.valueOf(oVar)));
            if (context != null) {
                com.vivo.push.util.p.c(context, "[执行指令失败]指令" + oVar + "任务空！");
                return;
            }
            return;
        }
        com.vivo.push.util.p.d("PushClientManager", "client--sendCommand, command = ".concat(String.valueOf(oVar)));
        m.a(lVarCreateTask);
    }
}
