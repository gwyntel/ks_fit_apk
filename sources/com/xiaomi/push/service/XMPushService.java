package com.xiaomi.push.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Messenger;
import android.os.Parcelable;
import android.os.Process;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.TextUtils;
import androidx.media3.common.C;
import com.aliyun.alink.linksdk.alcs.lpbs.api.AlcsPalConst;
import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.push.C0472r;
import com.xiaomi.push.ah;
import com.xiaomi.push.dd;
import com.xiaomi.push.dm;
import com.xiaomi.push.dv;
import com.xiaomi.push.ed;
import com.xiaomi.push.ef;
import com.xiaomi.push.fd;
import com.xiaomi.push.fo;
import com.xiaomi.push.fu;
import com.xiaomi.push.gc;
import com.xiaomi.push.gm;
import com.xiaomi.push.go;
import com.xiaomi.push.gq;
import com.xiaomi.push.gx;
import com.xiaomi.push.hb;
import com.xiaomi.push.hc;
import com.xiaomi.push.he;
import com.xiaomi.push.hg;
import com.xiaomi.push.hh;
import com.xiaomi.push.hm;
import com.xiaomi.push.hr;
import com.xiaomi.push.hs;
import com.xiaomi.push.ig;
import com.xiaomi.push.ii;
import com.xiaomi.push.il;
import com.xiaomi.push.in;
import com.xiaomi.push.is;
import com.xiaomi.push.jj;
import com.xiaomi.push.jm;
import com.xiaomi.push.jn;
import com.xiaomi.push.jx;
import com.xiaomi.push.kd;
import com.xiaomi.push.service.bf;
import com.xiaomi.push.service.bq;
import com.xiaomi.push.service.q;
import dev.fluttercommunity.plus.connectivity.ConnectivityBroadcastReceiver;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class XMPushService extends Service implements he {

    /* renamed from: b, reason: collision with root package name */
    private static boolean f24419b = false;

    /* renamed from: a, reason: collision with other field name */
    private ContentObserver f952a;

    /* renamed from: a, reason: collision with other field name */
    private gx f954a;

    /* renamed from: a, reason: collision with other field name */
    private hb f955a;

    /* renamed from: a, reason: collision with other field name */
    private hc f956a;

    /* renamed from: a, reason: collision with other field name */
    private a f958a;

    /* renamed from: a, reason: collision with other field name */
    private f f959a;

    /* renamed from: a, reason: collision with other field name */
    private k f960a;

    /* renamed from: a, reason: collision with other field name */
    private r f961a;

    /* renamed from: a, reason: collision with other field name */
    private t f962a;

    /* renamed from: a, reason: collision with other field name */
    private bp f964a;

    /* renamed from: a, reason: collision with other field name */
    private com.xiaomi.push.service.k f965a;

    /* renamed from: a, reason: collision with other field name */
    private Object f968a;

    /* renamed from: a, reason: collision with other field name */
    private boolean f971a = false;

    /* renamed from: a, reason: collision with root package name */
    private int f24420a = 0;

    /* renamed from: b, reason: collision with other field name */
    private int f972b = 0;

    /* renamed from: a, reason: collision with other field name */
    private long f951a = 0;

    /* renamed from: a, reason: collision with other field name */
    protected Class f967a = XMJobService.class;

    /* renamed from: c, reason: collision with root package name */
    private int f24421c = -1;

    /* renamed from: a, reason: collision with other field name */
    private bd f963a = null;

    /* renamed from: a, reason: collision with other field name */
    private com.xiaomi.push.service.q f966a = null;

    /* renamed from: a, reason: collision with other field name */
    Messenger f953a = null;

    /* renamed from: a, reason: collision with other field name */
    private Collection<aq> f970a = Collections.synchronizedCollection(new ArrayList());

    /* renamed from: a, reason: collision with other field name */
    private ArrayList<n> f969a = new ArrayList<>();

    /* renamed from: a, reason: collision with other field name */
    private hg f957a = new cj(this);

    private class a extends BroadcastReceiver {

        /* renamed from: a, reason: collision with other field name */
        private final Object f973a;

        private a() {
            this.f973a = new Object();
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            com.xiaomi.channel.commonutils.logger.b.c("[Alarm] heartbeat alarm has been triggered.");
            if (!bj.f24533q.equals(intent.getAction())) {
                com.xiaomi.channel.commonutils.logger.b.m91a("[Alarm] cancel the old ping timer");
                fu.a();
                return;
            }
            if (TextUtils.equals(context.getPackageName(), intent.getPackage())) {
                com.xiaomi.channel.commonutils.logger.b.c("[Alarm] Ping XMChannelService on timer");
                try {
                    Intent intent2 = new Intent(context, (Class<?>) XMPushService.class);
                    intent2.putExtra("time_stamp", System.currentTimeMillis());
                    intent2.setAction("com.xiaomi.push.timer");
                    ServiceClient.getInstance(context).startServiceSafely(intent2);
                    a(3000L);
                    com.xiaomi.channel.commonutils.logger.b.m91a("[Alarm] heartbeat alarm finish in " + (System.currentTimeMillis() - jCurrentTimeMillis));
                } catch (Throwable unused) {
                }
            }
        }

        private void a(long j2) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                com.xiaomi.channel.commonutils.logger.b.d("[Alarm] Cannot perform lock.wait in the UI thread!");
                return;
            }
            synchronized (this.f973a) {
                try {
                    this.f973a.wait(j2);
                } catch (InterruptedException e2) {
                    com.xiaomi.channel.commonutils.logger.b.m91a("[Alarm] interrupt from waiting state. " + e2);
                }
            }
        }

        /* synthetic */ a(XMPushService xMPushService, cj cjVar) {
            this();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a() {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                com.xiaomi.channel.commonutils.logger.b.d("[Alarm] Cannot perform lock.notifyAll in the UI thread!");
                return;
            }
            synchronized (this.f973a) {
                try {
                    this.f973a.notifyAll();
                } catch (Exception e2) {
                    com.xiaomi.channel.commonutils.logger.b.m91a("[Alarm] notify lock. " + e2);
                }
            }
        }
    }

    static class c extends j {

        /* renamed from: a, reason: collision with root package name */
        private final bf.b f24424a;

        public c(bf.b bVar) {
            super(12);
            this.f24424a = bVar;
        }

        @Override // com.xiaomi.push.service.XMPushService.j
        /* renamed from: a */
        public void mo433a() {
            this.f24424a.a(bf.c.unbind, 1, 21, (String) null, (String) null);
        }

        public boolean equals(Object obj) {
            if (obj instanceof c) {
                return TextUtils.equals(((c) obj).f24424a.f24503g, this.f24424a.f24503g);
            }
            return false;
        }

        public int hashCode() {
            return this.f24424a.f24503g.hashCode();
        }

        @Override // com.xiaomi.push.service.XMPushService.j
        public String a() {
            return "bind time out. chid=" + this.f24424a.f24503g;
        }
    }

    class f extends BroadcastReceiver {
        f() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            com.xiaomi.push.bg.m207a();
            XMPushService.this.onStart(intent, 1);
        }
    }

    public class g extends j {

        /* renamed from: a, reason: collision with other field name */
        public Exception f976a;

        /* renamed from: b, reason: collision with root package name */
        public int f24429b;

        g(int i2, Exception exc) {
            super(2);
            this.f24429b = i2;
            this.f976a = exc;
        }

        @Override // com.xiaomi.push.service.XMPushService.j
        /* renamed from: a */
        public void mo433a() {
            XMPushService.this.a(this.f24429b, this.f976a);
        }

        @Override // com.xiaomi.push.service.XMPushService.j
        public String a() {
            return "disconnect the connection.";
        }
    }

    class h extends j {
        h() {
            super(65535);
        }

        @Override // com.xiaomi.push.service.XMPushService.j
        /* renamed from: a */
        public void mo433a() {
            XMPushService.this.c();
        }

        @Override // com.xiaomi.push.service.XMPushService.j
        public String a() {
            return "Init Job";
        }
    }

    class i extends j {

        /* renamed from: a, reason: collision with root package name */
        private Intent f24431a;

        public i(Intent intent) {
            super(15);
            this.f24431a = intent;
        }

        @Override // com.xiaomi.push.service.XMPushService.j
        /* renamed from: a */
        public void mo433a() throws PackageManager.NameNotFoundException, NumberFormatException {
            XMPushService.this.d(this.f24431a);
        }

        @Override // com.xiaomi.push.service.XMPushService.j
        public String a() {
            return "Handle intent action = " + this.f24431a.getAction();
        }
    }

    public static abstract class j extends q.b {
        public j(int i2) {
            super(i2);
        }

        public abstract String a();

        /* renamed from: a */
        public abstract void mo433a();

        @Override // java.lang.Runnable
        public void run() {
            int i2 = this.f24608a;
            if (i2 != 4 && i2 != 8) {
                com.xiaomi.channel.commonutils.logger.b.m92a(com.xiaomi.channel.commonutils.logger.a.f23334a, a());
            }
            mo433a();
        }
    }

    class k extends BroadcastReceiver {
        k() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            com.xiaomi.channel.commonutils.logger.b.m91a("[HB] hold short heartbeat, " + com.xiaomi.push.j.a(intent));
            if (intent == null || intent.getExtras() == null) {
                return;
            }
            XMPushService.this.onStart(intent, 1);
        }
    }

    class l extends j {
        public l() {
            super(5);
        }

        @Override // com.xiaomi.push.service.XMPushService.j
        /* renamed from: a */
        public void mo433a() {
            XMPushService.this.f966a.m791a();
        }

        @Override // com.xiaomi.push.service.XMPushService.j
        public String a() {
            return "ask the job queue to quit";
        }
    }

    class m extends j {

        /* renamed from: a, reason: collision with root package name */
        private hs f24434a;

        public m(hs hsVar) {
            super(8);
            this.f24434a = hsVar;
        }

        @Override // com.xiaomi.push.service.XMPushService.j
        /* renamed from: a */
        public void mo433a() {
            XMPushService.this.f963a.a(this.f24434a);
        }

        @Override // com.xiaomi.push.service.XMPushService.j
        public String a() {
            return "receive a message.";
        }
    }

    public interface n {
        /* renamed from: a */
        void mo511a();
    }

    class r extends BroadcastReceiver {
        r() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            XMPushService.this.onStart(intent, 1);
        }
    }

    class t extends BroadcastReceiver {
        t() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (!XMPushService.this.f971a) {
                XMPushService.this.f971a = true;
            }
            XMPushService.this.onStart(intent, 1);
        }
    }

    private void d() {
        com.xiaomi.push.bj bjVarM201a = com.xiaomi.push.bg.m201a();
        com.xiaomi.push.service.p.a(getApplicationContext()).a(bjVarM201a);
        if (bjVarM201a != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("network changed,");
            sb.append("[type: " + bjVarM201a.m211a() + "[" + bjVarM201a.m213b() + "], state: " + bjVarM201a.m210a() + "/" + bjVarM201a.m209a());
            com.xiaomi.channel.commonutils.logger.b.m92a("XMPushService", sb.toString());
            NetworkInfo.State stateM210a = bjVarM201a.m210a();
            if (stateM210a == NetworkInfo.State.SUSPENDED || stateM210a == NetworkInfo.State.UNKNOWN) {
                return;
            }
        } else {
            com.xiaomi.channel.commonutils.logger.b.m92a("XMPushService", "network changed, no active network");
        }
        if (gm.a() != null) {
            gm.a().m435a();
        }
        ig.m509a((Context) this);
        this.f954a.d();
        if (com.xiaomi.push.bg.b(this)) {
            if (m716c() && m703f()) {
                b(false);
            }
            if (!m716c() && !m717d()) {
                this.f966a.a(1);
                a(new e());
            }
            ef.a(this).a();
        } else {
            a(new g(2, null));
        }
        e();
    }

    private void e(Intent intent) throws NumberFormatException {
        int i2;
        try {
            fd.a(getApplicationContext()).a(new bl());
            String stringExtra = intent.getStringExtra("mipush_app_package");
            byte[] byteArrayExtra = intent.getByteArrayExtra("mipush_payload");
            if (byteArrayExtra == null) {
                return;
            }
            jm jmVar = new jm();
            jx.a(jmVar, byteArrayExtra);
            String strB = jmVar.b();
            Map<String, String> mapM609a = jmVar.m609a();
            if (mapM609a != null) {
                String str = mapM609a.get("extra_help_aw_info");
                String str2 = mapM609a.get("extra_aw_app_online_cmd");
                if (TextUtils.isEmpty(str2)) {
                    return;
                }
                try {
                    i2 = Integer.parseInt(str2);
                } catch (NumberFormatException unused) {
                    i2 = 0;
                }
                int i3 = i2;
                if (TextUtils.isEmpty(stringExtra) || TextUtils.isEmpty(strB) || TextUtils.isEmpty(str)) {
                    return;
                }
                HashMap map = new HashMap();
                map.put("packageName", stringExtra);
                map.put("appId", strB);
                map.put("awkInfo", str);
                map.put("cmdId", String.valueOf(i3));
                gc.a().a("check_doAWLogic", (Object) map);
                fd.a(getApplicationContext()).a(this, str, i3, stringExtra, strB);
            }
        } catch (kd e2) {
            com.xiaomi.channel.commonutils.logger.b.d("aw_logic: translate fail. " + e2.getMessage());
        }
    }

    /* renamed from: f, reason: collision with other method in class */
    private boolean m703f() {
        if (SystemClock.elapsedRealtime() - this.f951a < 30000) {
            return false;
        }
        return com.xiaomi.push.bg.d(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: g, reason: collision with other method in class */
    public boolean m704g() {
        return "com.xiaomi.xmsf".equals(getPackageName()) && Settings.System.getInt(getContentResolver(), "power_supersave_mode_open", 0) == 1;
    }

    private void h() {
    }

    /* renamed from: i, reason: collision with other method in class */
    private boolean m706i() {
        return getApplicationContext().getPackageName().equals("com.xiaomi.xmsf") && j() && !com.xiaomi.push.i.m500b((Context) this) && !com.xiaomi.push.i.m497a(getApplicationContext());
    }

    private boolean j() {
        int iIntValue = Integer.valueOf(String.format("%tH", new Date())).intValue();
        int i2 = this.f24420a;
        int i3 = this.f972b;
        if (i2 > i3) {
            if (iIntValue >= i2 || iIntValue < i3) {
                return true;
            }
        } else if (i2 < i3 && iIntValue >= i2 && iIntValue < i3) {
            return true;
        }
        return false;
    }

    private boolean k() {
        if (TextUtils.equals(getPackageName(), "com.xiaomi.xmsf")) {
            return false;
        }
        return az.a(this).a(is.ForegroundServiceSwitch.a(), false);
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return this.f953a.getBinder();
    }

    @Override // android.app.Service
    public void onCreate() {
        String[] strArrSplit;
        super.onCreate();
        com.xiaomi.channel.commonutils.logger.b.a(getApplicationContext());
        C0472r.a((Context) this);
        u uVarM802a = v.m802a((Context) this);
        if (uVarM802a != null) {
            com.xiaomi.push.aa.a(uVarM802a.f24623a);
        }
        if (com.xiaomi.push.j.m550a(getApplicationContext())) {
            HandlerThread handlerThread = new HandlerThread("hb-alarm");
            handlerThread.start();
            Handler handler = new Handler(handlerThread.getLooper());
            this.f958a = new a(this, null);
            com.xiaomi.push.l.a(this, this.f958a, new IntentFilter(bj.f24533q), "com.xiaomi.xmsf.permission.MIPUSH_RECEIVE", handler, 4);
            f24419b = true;
            handler.post(new cp(this));
        }
        this.f953a = new Messenger(new cq(this));
        bk.a(this);
        cr crVar = new cr(this, null, 5222, "xiaomi.com", null);
        this.f956a = crVar;
        crVar.a(true);
        this.f954a = new gx(this, this.f956a);
        this.f965a = m709a();
        fu.a(this);
        this.f954a.a(this);
        this.f963a = new bd(this);
        this.f964a = new bp(this);
        new com.xiaomi.push.service.l().a();
        gm.m436a().a(this);
        this.f966a = new com.xiaomi.push.service.q("Connection Controller Thread");
        bf bfVarA = bf.a();
        bfVarA.b();
        bfVarA.a(new cs(this));
        if (k()) {
            h();
        }
        il.a(this).a(new com.xiaomi.push.service.s(this), "UPLOADER_PUSH_CHANNEL");
        a(new ii(this));
        a(new ch(this));
        if (com.xiaomi.push.j.m550a((Context) this)) {
            a(new be());
            if (com.xiaomi.push.i.m496a()) {
                a(new ct(this));
            }
        }
        a(new h());
        this.f970a.add(by.a(this));
        if (m705h()) {
            this.f959a = new f();
            registerReceiver(this.f959a, new IntentFilter(ConnectivityBroadcastReceiver.CONNECTIVITY_ACTION));
            com.xiaomi.push.bg.m203a((Context) this);
        }
        if (com.xiaomi.push.j.m550a(getApplicationContext())) {
            this.f962a = new t();
            com.xiaomi.push.l.a(this, this.f962a, new IntentFilter("miui.net.wifi.DIGEST_INFORMATION_CHANGED"), "miui.net.wifi.permission.ACCESS_WIFI_DIGEST_INFO", null, 2);
            k kVar = new k();
            this.f960a = kVar;
            com.xiaomi.push.l.a(this, kVar, new IntentFilter("com.xiaomi.xmsf.USE_INTELLIGENT_HB"), "com.xiaomi.xmsf.permission.INTELLIGENT_HB", null, 2);
        }
        if ("com.xiaomi.xmsf".equals(getPackageName())) {
            Uri uriFor = Settings.System.getUriFor("power_supersave_mode_open");
            if (uriFor != null) {
                this.f952a = new cu(this, new Handler(Looper.getMainLooper()));
                try {
                    getContentResolver().registerContentObserver(uriFor, false, this.f952a);
                } catch (Throwable th) {
                    com.xiaomi.channel.commonutils.logger.b.d("register super-power-mode observer err:" + th.getMessage());
                }
            }
            int[] iArrM700a = m700a();
            if (iArrM700a != null) {
                this.f961a = new r();
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.intent.action.SCREEN_ON");
                intentFilter.addAction("android.intent.action.SCREEN_OFF");
                registerReceiver(this.f961a, intentFilter);
                this.f24420a = iArrM700a[0];
                this.f972b = iArrM700a[1];
                com.xiaomi.channel.commonutils.logger.b.m91a("falldown initialized: " + this.f24420a + "," + this.f972b);
            }
        }
        dm.a(this, this.f954a);
        dv.a(this, this.f954a);
        String str = "";
        if (uVarM802a != null) {
            try {
                if (!TextUtils.isEmpty(uVarM802a.f1111a) && (strArrSplit = uVarM802a.f1111a.split("@")) != null && strArrSplit.length > 0) {
                    str = strArrSplit[0];
                }
            } catch (Exception unused) {
            }
        }
        ed.a(this);
        com.xiaomi.channel.commonutils.logger.b.e("XMPushService created. pid=" + Process.myPid() + ", uid=" + Process.myUid() + ", vc=" + com.xiaomi.push.g.a(getApplicationContext(), getPackageName()) + ", uuid=" + str);
    }

    @Override // android.app.Service
    public void onDestroy() {
        f fVar = this.f959a;
        if (fVar != null) {
            a(fVar);
            this.f959a = null;
        }
        try {
            Object obj = this.f968a;
            if (obj != null && (obj instanceof ConnectivityManager.NetworkCallback)) {
                ((ConnectivityManager) getSystemService("connectivity")).unregisterNetworkCallback((ConnectivityManager.NetworkCallback) this.f968a);
            }
        } catch (Exception unused) {
        }
        t tVar = this.f962a;
        if (tVar != null) {
            a(tVar);
            this.f962a = null;
        }
        k kVar = this.f960a;
        if (kVar != null) {
            a(kVar);
            this.f960a = null;
        }
        r rVar = this.f961a;
        if (rVar != null) {
            a(rVar);
            this.f961a = null;
        }
        a aVar = this.f958a;
        if (aVar != null) {
            a(aVar);
            this.f958a = null;
        }
        if ("com.xiaomi.xmsf".equals(getPackageName()) && this.f952a != null) {
            try {
                getContentResolver().unregisterContentObserver(this.f952a);
            } catch (Throwable th) {
                com.xiaomi.channel.commonutils.logger.b.d("unregister super-power-mode err:" + th.getMessage());
            }
        }
        this.f970a.clear();
        this.f966a.m794b();
        a(new cm(this, 2));
        a(new l());
        bf.a().b();
        bf.a().a(this, 15);
        bf.a().m757a();
        this.f954a.b(this);
        bw.a().m772a();
        fu.a();
        i();
        dm.b(this, this.f954a);
        dv.b(this, this.f954a);
        super.onDestroy();
        com.xiaomi.channel.commonutils.logger.b.m91a("Service destroyed");
    }

    @Override // android.app.Service
    public void onStart(Intent intent, int i2) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (intent == null) {
            com.xiaomi.channel.commonutils.logger.b.d("onStart() with intent NULL");
        } else {
            try {
                String stringExtra = intent.getStringExtra(bj.f24538v);
                String stringExtra2 = intent.getStringExtra(bj.F);
                String stringExtra3 = intent.getStringExtra("mipush_app_package");
                if (ConnectivityBroadcastReceiver.CONNECTIVITY_ACTION.equals(intent.getAction()) || "miui.net.wifi.DIGEST_INFORMATION_CHANGED".equals(intent.getAction())) {
                    com.xiaomi.channel.commonutils.logger.b.m92a("XMPushService", String.format("onStart() with intent.Action = %s, chid = %s, pkg = %s|%s, intent = %s", intent.getAction(), stringExtra, stringExtra2, stringExtra3, com.xiaomi.push.j.a(intent)));
                } else {
                    com.xiaomi.channel.commonutils.logger.b.m92a("XMPushService", String.format("onStart() with intent.Action = %s, chid = %s, pkg = %s|%s", intent.getAction(), stringExtra, stringExtra2, stringExtra3));
                }
            } catch (Throwable th) {
                com.xiaomi.channel.commonutils.logger.b.d("onStart() cause error: " + th.getMessage());
                return;
            }
        }
        if (intent != null && intent.getAction() != null) {
            if ("com.xiaomi.push.timer".equalsIgnoreCase(intent.getAction()) || "com.xiaomi.push.check_alive".equalsIgnoreCase(intent.getAction())) {
                if (this.f966a.m792a()) {
                    com.xiaomi.channel.commonutils.logger.b.d("ERROR, the job controller is blocked.");
                    bf.a().a(this, 14);
                    stopSelf();
                } else {
                    a(new i(intent));
                }
            } else if (!"com.xiaomi.push.network_status_changed".equalsIgnoreCase(intent.getAction())) {
                a(new i(intent));
            }
        }
        long jCurrentTimeMillis2 = System.currentTimeMillis() - jCurrentTimeMillis;
        if (jCurrentTimeMillis2 > 50) {
            com.xiaomi.channel.commonutils.logger.b.c("[Prefs] spend " + jCurrentTimeMillis2 + " ms, too more times.");
        }
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i2, int i3) {
        onStart(intent, i3);
        return 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't wrap try/catch for region: R(15:0|2|(1:4)(1:5)|6|(2:12|(3:14|(1:16)(1:17)|18)(10:19|20|(1:22)|23|(1:25)|36|26|(1:28)|32|(2:34|35)(1:38)))(1:10)|11|20|(0)|23|(0)|36|26|(0)|32|(0)(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x00e4, code lost:
    
        r1 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x00e5, code lost:
    
        com.xiaomi.channel.commonutils.logger.b.a(r1);
     */
    /* JADX WARN: Removed duplicated region for block: B:22:0x00b3  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00c1  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00de A[Catch: Exception -> 0x00e4, TRY_LEAVE, TryCatch #0 {Exception -> 0x00e4, blocks: (B:26:0x00d8, B:28:0x00de), top: B:36:0x00d8 }] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00f2  */
    /* JADX WARN: Removed duplicated region for block: B:38:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void c() {
        /*
            r10 = this;
            r0 = 1
            com.xiaomi.push.dd r1 = com.xiaomi.push.dd.a()
            r1.m278d()
            android.content.Context r1 = r10.getApplicationContext()
            com.xiaomi.push.service.p r1 = com.xiaomi.push.service.p.a(r1)
            r1.m785a()
            android.content.Context r1 = r10.getApplicationContext()
            com.xiaomi.push.service.a r1 = com.xiaomi.push.service.a.a(r1)
            java.lang.String r2 = r1.a()
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "region of cache is "
            r3.append(r4)
            r3.append(r2)
            java.lang.String r3 = r3.toString()
            java.lang.String r4 = "XMPushService"
            com.xiaomi.channel.commonutils.logger.b.m92a(r4, r3)
            boolean r3 = android.text.TextUtils.isEmpty(r2)
            java.lang.String r5 = ""
            if (r3 == 0) goto L4d
            java.lang.String r2 = r10.b()
            com.xiaomi.push.m r3 = com.xiaomi.push.j.a(r2)
            java.lang.String r3 = r3.name()
            r9 = r3
            r3 = r2
            r2 = r9
            goto L4e
        L4d:
            r3 = r5
        L4e:
            boolean r6 = android.text.TextUtils.isEmpty(r2)
            java.lang.String r7 = "com.xiaomi.xmsf"
            java.lang.String r8 = "CN"
            if (r6 != 0) goto L6c
            com.xiaomi.push.m r6 = com.xiaomi.push.m.China
            java.lang.String r6 = r6.name()
            boolean r6 = r6.equals(r2)
            if (r6 == 0) goto L6c
            r1.a(r2, r0)
            r1.b(r8, r0)
        L6a:
            r3 = r8
            goto L92
        L6c:
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 != 0) goto L8c
            java.lang.String r2 = r10.getPackageName()
            boolean r2 = r7.equals(r2)
            if (r2 == 0) goto L7e
            r8 = r5
            goto L84
        L7e:
            com.xiaomi.push.m r2 = com.xiaomi.push.m.China
            java.lang.String r5 = r2.name()
        L84:
            r1.a(r5, r0)
            r1.b(r8, r0)
            r2 = r5
            goto L6a
        L8c:
            com.xiaomi.push.m r1 = com.xiaomi.push.m.China
            java.lang.String r2 = r1.name()
        L92:
            r1 = 4
            java.lang.Object[] r1 = new java.lang.Object[r1]
            java.lang.String r5 = "after check, appRegion is "
            r6 = 0
            r1[r6] = r5
            r1[r0] = r2
            java.lang.String r5 = ", countryCode="
            r6 = 2
            r1[r6] = r5
            r5 = 3
            r1[r5] = r3
            com.xiaomi.channel.commonutils.logger.b.m93a(r4, r1)
            com.xiaomi.push.m r1 = com.xiaomi.push.m.China
            java.lang.String r1 = r1.name()
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto Lb8
            java.lang.String r1 = "cn.app.chat.xiaomi.net"
            com.xiaomi.push.hc.a(r1)
        Lb8:
            a(r2)
            boolean r1 = r10.m705h()
            if (r1 == 0) goto Ld8
            java.lang.String r1 = "-->postOnCreate(): try trigger connect now"
            com.xiaomi.channel.commonutils.logger.b.m92a(r4, r1)
            com.xiaomi.push.service.cv r1 = new com.xiaomi.push.service.cv
            r2 = 11
            r1.<init>(r10, r2)
            r10.a(r1)
            com.xiaomi.push.service.cw r2 = new com.xiaomi.push.service.cw
            r2.<init>(r10, r1)
            com.xiaomi.push.service.v.a(r2)
        Ld8:
            boolean r1 = com.xiaomi.push.C0472r.m686a()     // Catch: java.lang.Exception -> Le4
            if (r1 == 0) goto Le8
            com.xiaomi.push.service.k r1 = r10.f965a     // Catch: java.lang.Exception -> Le4
            r1.a(r10)     // Catch: java.lang.Exception -> Le4
            goto Le8
        Le4:
            r1 = move-exception
            com.xiaomi.channel.commonutils.logger.b.a(r1)
        Le8:
            java.lang.String r1 = r10.getPackageName()
            boolean r1 = r7.equals(r1)
            if (r1 == 0) goto Lf9
            android.content.pm.ApplicationInfo r1 = r10.getApplicationInfo()
            com.xiaomi.push.g.a(r10, r1, r0)
        Lf9:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.service.XMPushService.c():void");
    }

    /* renamed from: h, reason: collision with other method in class */
    private boolean m705h() {
        boolean zEquals;
        String packageName = getPackageName();
        if ("com.xiaomi.xmsf".equals(packageName)) {
            com.xiaomi.channel.commonutils.logger.b.m91a("current sdk expect region is cn");
            zEquals = com.xiaomi.push.m.China.name().equals(com.xiaomi.push.service.a.a(getApplicationContext()).a());
        } else {
            zEquals = !w.a(this).m807b(packageName);
        }
        if (!zEquals) {
            com.xiaomi.channel.commonutils.logger.b.m93a("XMPushService", "-->isPushEnabled(): isEnabled=", Boolean.valueOf(zEquals), ", package=", packageName, ", region=", com.xiaomi.push.service.a.a(getApplicationContext()).a());
        }
        return zEquals;
    }

    class d extends j {

        /* renamed from: a, reason: collision with root package name */
        private gq f24425a;

        public d(gq gqVar) {
            super(8);
            this.f24425a = gqVar;
        }

        @Override // com.xiaomi.push.service.XMPushService.j
        /* renamed from: a */
        public void mo433a() {
            XMPushService.this.f963a.a(this.f24425a);
            if (com.xiaomi.push.service.e.a(this.f24425a)) {
                XMPushService.this.a(new bq.a(), C.DEFAULT_SEEK_FORWARD_INCREMENT_MS);
            }
        }

        @Override // com.xiaomi.push.service.XMPushService.j
        public String a() {
            return "receive a message.";
        }
    }

    public class e extends j {
        e() {
            super(1);
        }

        @Override // com.xiaomi.push.service.XMPushService.j
        /* renamed from: a */
        public void mo433a() {
            if (XMPushService.this.m711a()) {
                XMPushService xMPushService = XMPushService.this;
                if (xMPushService.a(xMPushService.getApplicationContext())) {
                    XMPushService.this.f();
                    return;
                }
            }
            com.xiaomi.channel.commonutils.logger.b.m91a("should not connect. quit the job.");
        }

        @Override // com.xiaomi.push.service.XMPushService.j
        public String a() {
            return "do reconnect..";
        }
    }

    class q extends j {
        q() {
            super(3);
        }

        @Override // com.xiaomi.push.service.XMPushService.j
        /* renamed from: a */
        public void mo433a() {
            XMPushService.this.a(11, (Exception) null);
            if (XMPushService.this.m711a()) {
                XMPushService xMPushService = XMPushService.this;
                if (xMPushService.a(xMPushService.getApplicationContext())) {
                    XMPushService.this.f();
                }
            }
        }

        @Override // com.xiaomi.push.service.XMPushService.j
        public String a() {
            return "reset the connection.";
        }
    }

    private String b() {
        String strA;
        com.xiaomi.push.an.a();
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        Object obj = new Object();
        int i2 = 0;
        if ("com.xiaomi.xmsf".equals(getPackageName())) {
            bm bmVarA = bm.a(this);
            String strA2 = null;
            while (true) {
                if (!TextUtils.isEmpty(strA2) && bmVarA.a() != 0) {
                    strA = a();
                    break;
                }
                if (TextUtils.isEmpty(strA2)) {
                    strA2 = a();
                }
                try {
                    synchronized (obj) {
                        if (i2 < 30) {
                            try {
                                obj.wait(1000L);
                            } catch (Throwable th) {
                                throw th;
                            }
                        } else {
                            obj.wait(30000L);
                        }
                    }
                } catch (InterruptedException unused) {
                }
                i2++;
            }
        } else {
            strA = "CN";
        }
        com.xiaomi.channel.commonutils.logger.b.m91a("wait coutrycode :" + strA + " cost = " + (SystemClock.elapsedRealtime() - jElapsedRealtime) + " , count = " + i2);
        return strA;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void f() {
        hb hbVar = this.f955a;
        if (hbVar != null && hbVar.m472b()) {
            com.xiaomi.channel.commonutils.logger.b.d("try to connect while connecting.");
            return;
        }
        hb hbVar2 = this.f955a;
        if (hbVar2 != null && hbVar2.m473c()) {
            com.xiaomi.channel.commonutils.logger.b.d("try to connect while is connected.");
            return;
        }
        this.f956a.b(com.xiaomi.push.bg.m204a((Context) this));
        g();
        if (this.f955a == null) {
            bf.a().a(this);
            c(false);
        }
    }

    private void g() {
        try {
            this.f954a.a(this.f957a, new cn(this));
            this.f954a.e();
            this.f955a = this.f954a;
        } catch (hm e2) {
            com.xiaomi.channel.commonutils.logger.b.a("fail to create Slim connection", e2);
            this.f954a.b(3, e2);
        }
    }

    private void i() {
        synchronized (this.f969a) {
            this.f969a.clear();
        }
    }

    class p extends j {

        /* renamed from: a, reason: collision with other field name */
        bf.b f980a;

        public p(bf.b bVar) {
            super(4);
            this.f980a = bVar;
        }

        @Override // com.xiaomi.push.service.XMPushService.j
        /* renamed from: a */
        public void mo433a() {
            try {
                this.f980a.a(bf.c.unbind, 1, 16, (String) null, (String) null);
                hb hbVar = XMPushService.this.f955a;
                bf.b bVar = this.f980a;
                hbVar.a(bVar.f24503g, bVar.f1043b);
                XMPushService xMPushService = XMPushService.this;
                xMPushService.a(xMPushService.new b(this.f980a), 300L);
            } catch (hm e2) {
                com.xiaomi.channel.commonutils.logger.b.a(e2);
                XMPushService.this.a(10, e2);
            }
        }

        @Override // com.xiaomi.push.service.XMPushService.j
        public String a() {
            return "rebind the client. " + this.f980a.f24503g;
        }
    }

    class s extends j {

        /* renamed from: a, reason: collision with other field name */
        bf.b f981a;

        /* renamed from: a, reason: collision with other field name */
        String f982a;

        /* renamed from: b, reason: collision with root package name */
        int f24440b;

        /* renamed from: b, reason: collision with other field name */
        String f983b;

        public s(bf.b bVar, int i2, String str, String str2) {
            super(9);
            this.f981a = bVar;
            this.f24440b = i2;
            this.f982a = str;
            this.f983b = str2;
        }

        @Override // com.xiaomi.push.service.XMPushService.j
        /* renamed from: a */
        public void mo433a() {
            if (this.f981a.f1038a != bf.c.unbind && XMPushService.this.f955a != null) {
                try {
                    hb hbVar = XMPushService.this.f955a;
                    bf.b bVar = this.f981a;
                    hbVar.a(bVar.f24503g, bVar.f1043b);
                } catch (hm e2) {
                    com.xiaomi.channel.commonutils.logger.b.a(e2);
                    XMPushService.this.a(10, e2);
                }
            }
            this.f981a.a(bf.c.unbind, this.f24440b, 0, this.f983b, this.f982a);
        }

        @Override // com.xiaomi.push.service.XMPushService.j
        public String a() {
            return "unbind the channel. " + this.f981a.f24503g;
        }
    }

    class o extends j {

        /* renamed from: a, reason: collision with other field name */
        boolean f979a;

        public o(boolean z2) {
            super(4);
            this.f979a = z2;
        }

        @Override // com.xiaomi.push.service.XMPushService.j
        /* renamed from: a */
        public void mo433a() {
            if (XMPushService.this.m716c()) {
                try {
                    if (!this.f979a) {
                        go.a();
                    }
                    XMPushService.this.f955a.b(this.f979a);
                } catch (hm e2) {
                    com.xiaomi.channel.commonutils.logger.b.a(e2);
                    XMPushService.this.a(10, e2);
                }
            }
        }

        @Override // com.xiaomi.push.service.XMPushService.j
        public String a() {
            return "send ping..";
        }
    }

    class b extends j {

        /* renamed from: a, reason: collision with other field name */
        bf.b f974a;

        public b(bf.b bVar) {
            super(9);
            this.f974a = bVar;
        }

        @Override // com.xiaomi.push.service.XMPushService.j
        /* renamed from: a */
        public void mo433a() {
            try {
                if (XMPushService.this.m716c()) {
                    bf bfVarA = bf.a();
                    bf.b bVar = this.f974a;
                    bf.b bVarA = bfVarA.a(bVar.f24503g, bVar.f1043b);
                    if (bVarA == null) {
                        com.xiaomi.channel.commonutils.logger.b.m91a("ignore bind because the channel " + this.f974a.f24503g + " is removed ");
                    } else if (bVarA.f1038a == bf.c.unbind) {
                        bVarA.a(bf.c.binding, 0, 0, (String) null, (String) null);
                        XMPushService.this.f955a.a(bVarA);
                        go.a(XMPushService.this, bVarA);
                    } else {
                        com.xiaomi.channel.commonutils.logger.b.m91a("trying duplicate bind, ingore! " + bVarA.f1038a);
                    }
                } else {
                    com.xiaomi.channel.commonutils.logger.b.d("trying bind while the connection is not created, quit!");
                }
            } catch (Exception e2) {
                com.xiaomi.channel.commonutils.logger.b.d("Meet error when trying to bind. " + e2);
                XMPushService.this.a(10, e2);
            } catch (Throwable unused) {
            }
        }

        @Override // com.xiaomi.push.service.XMPushService.j
        public String a() {
            return "bind the client. " + this.f974a.f24503g;
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public int m707a() {
        if (this.f24421c < 0) {
            this.f24421c = com.xiaomi.push.g.a((Context) this, "com.xiaomi.xmsf");
        }
        return this.f24421c;
    }

    /* renamed from: a, reason: collision with other method in class */
    private int[] m700a() {
        String[] strArrSplit;
        String strA = az.a(getApplicationContext()).a(is.FallDownTimeRange.a(), "");
        if (!TextUtils.isEmpty(strA) && (strArrSplit = strA.split(",")) != null && strArrSplit.length >= 2) {
            int[] iArr = new int[2];
            try {
                iArr[0] = Integer.valueOf(strArrSplit[0]).intValue();
                int iIntValue = Integer.valueOf(strArrSplit[1]).intValue();
                iArr[1] = iIntValue;
                int i2 = iArr[0];
                if (i2 >= 0 && i2 <= 23 && iIntValue >= 0 && iIntValue <= 23 && i2 != iIntValue) {
                    return iArr;
                }
            } catch (NumberFormatException e2) {
                com.xiaomi.channel.commonutils.logger.b.d("parse falldown time range failure: " + e2);
            }
        }
        return null;
    }

    private void b(boolean z2) {
        this.f951a = SystemClock.elapsedRealtime();
        if (!m716c()) {
            a(true);
        } else if (com.xiaomi.push.bg.b(this)) {
            c(new o(z2));
        } else {
            c(new g(17, null));
            a(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void e() {
        if (m711a()) {
            if (fu.m418a()) {
                return;
            }
            fu.a(true);
            return;
        }
        fu.a();
    }

    private String a() {
        String strM547a = com.xiaomi.push.j.m547a("ro.miui.region");
        return TextUtils.isEmpty(strM547a) ? com.xiaomi.push.j.m547a("ro.product.locale.region") : strM547a;
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x00b8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void b(android.content.Intent r11) throws java.lang.NumberFormatException {
        /*
            r10 = this;
            java.lang.String r0 = com.xiaomi.push.service.bj.F
            java.lang.String r0 = r11.getStringExtra(r0)
            java.lang.String r1 = com.xiaomi.push.service.bj.J
            java.lang.String r1 = r11.getStringExtra(r1)
            java.lang.String r2 = "ext_packet"
            android.os.Bundle r2 = r11.getBundleExtra(r2)
            com.xiaomi.push.service.bf r3 = com.xiaomi.push.service.bf.a()
            if (r2 == 0) goto L3a
            com.xiaomi.push.hr r11 = new com.xiaomi.push.hr
            r11.<init>(r2)
            com.xiaomi.push.hs r11 = r10.a(r11, r0, r1)
            com.xiaomi.push.hr r11 = (com.xiaomi.push.hr) r11
            if (r11 != 0) goto L26
            return
        L26:
            java.lang.String r0 = r11.k()
            java.lang.String r1 = r11.m()
            com.xiaomi.push.service.bf$b r0 = r3.a(r0, r1)
            java.lang.String r0 = r0.f24504h
            com.xiaomi.push.gq r11 = com.xiaomi.push.gq.a(r11, r0)
            goto Lb9
        L3a:
            java.lang.String r0 = "ext_raw_packet"
            byte[] r0 = r11.getByteArrayExtra(r0)
            r1 = 0
            if (r0 == 0) goto Lb8
            java.lang.String r2 = com.xiaomi.push.service.bj.f24535s     // Catch: java.lang.NumberFormatException -> L4e
            java.lang.String r2 = r11.getStringExtra(r2)     // Catch: java.lang.NumberFormatException -> L4e
            long r4 = java.lang.Long.parseLong(r2)     // Catch: java.lang.NumberFormatException -> L4e
            goto L50
        L4e:
            r4 = 0
        L50:
            java.lang.String r2 = com.xiaomi.push.service.bj.f24536t
            java.lang.String r2 = r11.getStringExtra(r2)
            java.lang.String r6 = com.xiaomi.push.service.bj.f24537u
            java.lang.String r6 = r11.getStringExtra(r6)
            java.lang.String r7 = "ext_chid"
            java.lang.String r7 = r11.getStringExtra(r7)
            java.lang.String r8 = java.lang.String.valueOf(r4)
            com.xiaomi.push.service.bf$b r3 = r3.a(r7, r8)
            if (r3 == 0) goto Lb8
            com.xiaomi.push.gq r8 = new com.xiaomi.push.gq
            r8.<init>()
            int r9 = java.lang.Integer.parseInt(r7)     // Catch: java.lang.NumberFormatException -> L78
            r8.a(r9)     // Catch: java.lang.NumberFormatException -> L78
        L78:
            java.lang.String r9 = "SECMSG"
            r8.a(r9, r1)
            boolean r1 = android.text.TextUtils.isEmpty(r2)
            if (r1 == 0) goto L85
            java.lang.String r2 = "xiaomi.com"
        L85:
            r8.a(r4, r2, r6)
            java.lang.String r1 = "ext_pkt_id"
            java.lang.String r2 = r11.getStringExtra(r1)
            r8.a(r2)
            java.lang.String r2 = r3.f24504h
            r8.a(r0, r2)
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r2 = "send a message: chid="
            r0.append(r2)
            r0.append(r7)
            java.lang.String r2 = ", packetId="
            r0.append(r2)
            java.lang.String r11 = r11.getStringExtra(r1)
            r0.append(r11)
            java.lang.String r11 = r0.toString()
            com.xiaomi.channel.commonutils.logger.b.m91a(r11)
            r11 = r8
            goto Lb9
        Lb8:
            r11 = r1
        Lb9:
            if (r11 == 0) goto Lc3
            com.xiaomi.push.service.bu r0 = new com.xiaomi.push.service.bu
            r0.<init>(r10, r11)
            r10.c(r0)
        Lc3:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.service.XMPushService.b(android.content.Intent):void");
    }

    /* renamed from: e, reason: collision with other method in class */
    public static boolean m702e() {
        return f24419b;
    }

    private static void a(String str) {
        if (com.xiaomi.push.m.China.name().equals(str)) {
            dd.a("cn.app.chat.xiaomi.net", "cn.app.chat.xiaomi.net");
            dd.a("cn.app.chat.xiaomi.net", "111.13.141.211:443");
            dd.a("cn.app.chat.xiaomi.net", "39.156.81.172:443");
            dd.a("cn.app.chat.xiaomi.net", "111.202.1.250:443");
            dd.a("cn.app.chat.xiaomi.net", "123.125.102.213:443");
            dd.a("resolver.msg.xiaomi.net", "111.13.142.153:443");
            dd.a("resolver.msg.xiaomi.net", "111.202.1.252:443");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:151:0x041f  */
    /* JADX WARN: Removed duplicated region for block: B:157:0x044d  */
    /* JADX WARN: Removed duplicated region for block: B:382:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void d(android.content.Intent r17) throws android.content.pm.PackageManager.NameNotFoundException, java.lang.NumberFormatException {
        /*
            Method dump skipped, instructions count: 2373
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.service.XMPushService.d(android.content.Intent):void");
    }

    private void c(Intent intent) {
        String stringExtra = intent.getStringExtra(bj.F);
        String stringExtra2 = intent.getStringExtra(bj.J);
        Parcelable[] parcelableArrayExtra = intent.getParcelableArrayExtra("ext_packets");
        int length = parcelableArrayExtra.length;
        hr[] hrVarArr = new hr[length];
        intent.getBooleanExtra("ext_encrypt", true);
        for (int i2 = 0; i2 < parcelableArrayExtra.length; i2++) {
            hr hrVar = new hr((Bundle) parcelableArrayExtra[i2]);
            hrVarArr[i2] = hrVar;
            hr hrVar2 = (hr) a(hrVar, stringExtra, stringExtra2);
            hrVarArr[i2] = hrVar2;
            if (hrVar2 == null) {
                return;
            }
        }
        bf bfVarA = bf.a();
        gq[] gqVarArr = new gq[length];
        for (int i3 = 0; i3 < length; i3++) {
            hr hrVar3 = hrVarArr[i3];
            gqVarArr[i3] = gq.a(hrVar3, bfVarA.a(hrVar3.k(), hrVar3.m()).f24504h);
        }
        c(new com.xiaomi.push.service.c(this, gqVarArr));
    }

    private void a(Intent intent) {
        Bundle extras;
        if (intent == null || (extras = intent.getExtras()) == null) {
            return;
        }
        String string = extras.getString("digest");
        com.xiaomi.push.service.p.a(getApplicationContext()).m786a(string);
        dm.a(this, string);
    }

    /* renamed from: a, reason: collision with other method in class */
    void m710a() {
        if (SystemClock.elapsedRealtime() - this.f951a >= hh.a() && com.xiaomi.push.bg.d(this)) {
            b(true);
        }
    }

    private void a(Intent intent, int i2) {
        byte[] byteArrayExtra = intent.getByteArrayExtra("mipush_payload");
        boolean booleanExtra = intent.getBooleanExtra("com.xiaomi.mipush.MESSAGE_CACHE", true);
        jm jmVar = new jm();
        try {
            jx.a(jmVar, byteArrayExtra);
            com.xiaomi.push.ah.a(getApplicationContext()).a((ah.a) new com.xiaomi.push.service.b(jmVar, new WeakReference(this), booleanExtra), i2);
        } catch (kd unused) {
            com.xiaomi.channel.commonutils.logger.b.d("aw_ping : send help app ping  error");
        }
    }

    private void c(j jVar) {
        this.f966a.a(jVar);
    }

    private void c(boolean z2) {
        try {
            if (C0472r.m686a()) {
                if (z2) {
                    sendBroadcast(new Intent("miui.intent.action.NETWORK_CONNECTED"));
                    for (aq aqVar : (aq[]) this.f970a.toArray(new aq[0])) {
                        aqVar.mo774a();
                    }
                    return;
                }
                sendBroadcast(new Intent("miui.intent.action.NETWORK_BLOCKED"));
            }
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
        }
    }

    void a(String str, byte[] bArr, boolean z2) {
        Collection<bf.b> collectionM755a = bf.a().m755a(AlcsPalConst.MODEL_TYPE_TGMESH);
        if (collectionM755a.isEmpty()) {
            if (z2) {
                y.b(str, bArr);
            }
        } else if (collectionM755a.iterator().next().f1038a == bf.c.binded) {
            a(new cl(this, 4, str, bArr));
        } else if (z2) {
            y.b(str, bArr);
        }
    }

    /* renamed from: b, reason: collision with other method in class */
    public boolean m715b() {
        try {
            Class<?> clsA = C0472r.a(this, "miui.os.Build");
            Field field = clsA.getField("IS_CM_CUSTOMIZATION_TEST");
            Field field2 = clsA.getField("IS_CU_CUSTOMIZATION_TEST");
            Field field3 = clsA.getField("IS_CT_CUSTOMIZATION_TEST");
            if (!field.getBoolean(null) && !field2.getBoolean(null)) {
                if (!field3.getBoolean(null)) {
                    return false;
                }
            }
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }

    /* renamed from: c, reason: collision with other method in class */
    public boolean m716c() {
        hb hbVar = this.f955a;
        return hbVar != null && hbVar.m473c();
    }

    /* renamed from: b, reason: collision with other method in class */
    public com.xiaomi.push.service.k m713b() {
        return this.f965a;
    }

    public void a(byte[] bArr, String str) {
        if (bArr == null) {
            y.a(this, str, bArr, ErrorCode.ERROR_INVALID_PAYLOAD, "null payload");
            com.xiaomi.channel.commonutils.logger.b.m91a("register request without payload");
            return;
        }
        jj jjVar = new jj();
        try {
            jx.a(jjVar, bArr);
            if (jjVar.f741a == in.Registration) {
                jn jnVar = new jn();
                try {
                    jx.a(jnVar, jjVar.m599a());
                    a(new x(this, jjVar.b(), jnVar.b(), jnVar.c(), bArr));
                    fo.a(getApplicationContext()).a(jjVar.b(), "E100003", jnVar.a(), 6002, null);
                } catch (kd e2) {
                    com.xiaomi.channel.commonutils.logger.b.d("app register error. " + e2);
                    y.a(this, str, bArr, ErrorCode.ERROR_INVALID_PAYLOAD, " data action error.");
                }
            } else {
                y.a(this, str, bArr, ErrorCode.ERROR_INVALID_PAYLOAD, " registration action required.");
                com.xiaomi.channel.commonutils.logger.b.m91a("register request with invalid payload");
            }
        } catch (kd e3) {
            com.xiaomi.channel.commonutils.logger.b.d("app register fail. " + e3);
            y.a(this, str, bArr, ErrorCode.ERROR_INVALID_PAYLOAD, " data container error.");
        }
    }

    public void b(j jVar) {
        this.f966a.a(jVar.f24608a, jVar);
    }

    @Override // com.xiaomi.push.he
    public void b(hb hbVar) {
        gm.a().b(hbVar);
        c(true);
        this.f964a.m765a();
        if (!fu.m418a() && !m706i()) {
            com.xiaomi.channel.commonutils.logger.b.m91a("reconnection successful, reactivate alarm.");
            fu.a(true);
        }
        Iterator<bf.b> it = bf.a().m754a().iterator();
        while (it.hasNext()) {
            a(new b(it.next()));
        }
        if (this.f971a || !com.xiaomi.push.j.m550a(getApplicationContext())) {
            return;
        }
        com.xiaomi.push.ah.a(getApplicationContext()).a(new co(this));
    }

    /* renamed from: b, reason: collision with other method in class */
    void m714b() {
        com.xiaomi.push.service.p.a(getApplicationContext()).m790d();
        Iterator it = new ArrayList(this.f969a).iterator();
        while (it.hasNext()) {
            ((n) it.next()).mo511a();
        }
    }

    private hs a(hs hsVar, String str, String str2) {
        bf bfVarA = bf.a();
        List<String> listM756a = bfVarA.m756a(str);
        if (listM756a.isEmpty()) {
            com.xiaomi.channel.commonutils.logger.b.m91a("open channel should be called first before sending a packet, pkg=" + str);
            return null;
        }
        hsVar.o(str);
        String strK = hsVar.k();
        if (TextUtils.isEmpty(strK)) {
            strK = listM756a.get(0);
            hsVar.l(strK);
        }
        bf.b bVarA = bfVarA.a(strK, hsVar.m());
        if (!m716c()) {
            com.xiaomi.channel.commonutils.logger.b.m91a("drop a packet as the channel is not connected, chid=" + strK);
            return null;
        }
        if (bVarA != null && bVarA.f1038a == bf.c.binded) {
            if (TextUtils.equals(str2, bVarA.f24505i)) {
                return hsVar;
            }
            com.xiaomi.channel.commonutils.logger.b.m91a("invalid session. " + str2);
            return null;
        }
        com.xiaomi.channel.commonutils.logger.b.m91a("drop a packet as the channel is not opened, chid=" + strK);
        return null;
    }

    /* renamed from: a, reason: collision with other method in class */
    private boolean m699a(String str, Intent intent) {
        bf.b bVarA = bf.a().a(str, intent.getStringExtra(bj.f24535s));
        boolean z2 = false;
        if (bVarA == null || str == null) {
            return false;
        }
        String stringExtra = intent.getStringExtra(bj.J);
        String stringExtra2 = intent.getStringExtra(bj.B);
        if (!TextUtils.isEmpty(bVarA.f24505i) && !TextUtils.equals(stringExtra, bVarA.f24505i)) {
            com.xiaomi.channel.commonutils.logger.b.m91a("session changed. old session=" + bVarA.f24505i + ", new session=" + stringExtra + " chid = " + str);
            z2 = true;
        }
        if (stringExtra2.equals(bVarA.f24504h)) {
            return z2;
        }
        com.xiaomi.channel.commonutils.logger.b.m91a("security changed. chid = " + str + " sechash = " + com.xiaomi.push.bo.a(stringExtra2));
        return true;
    }

    private bf.b a(String str, Intent intent) {
        bf.b bVarA = bf.a().a(str, intent.getStringExtra(bj.f24535s));
        if (bVarA == null) {
            bVarA = new bf.b(this);
        }
        bVarA.f24503g = intent.getStringExtra(bj.f24538v);
        bVarA.f1043b = intent.getStringExtra(bj.f24535s);
        bVarA.f24499c = intent.getStringExtra(bj.f24542z);
        bVarA.f1040a = intent.getStringExtra(bj.F);
        bVarA.f24501e = intent.getStringExtra(bj.D);
        bVarA.f24502f = intent.getStringExtra(bj.E);
        bVarA.f1042a = intent.getBooleanExtra(bj.C, false);
        bVarA.f24504h = intent.getStringExtra(bj.B);
        bVarA.f24505i = intent.getStringExtra(bj.J);
        bVarA.f24500d = intent.getStringExtra(bj.A);
        bVarA.f1039a = this.f965a;
        bVarA.a((Messenger) intent.getParcelableExtra(bj.N));
        bVarA.f1032a = getApplicationContext();
        bf.a().a(bVarA);
        return bVarA;
    }

    public void a(String str, String str2, int i2, String str3, String str4) {
        bf.b bVarA = bf.a().a(str, str2);
        if (bVarA != null) {
            a(new s(bVarA, i2, str4, str3));
        }
        bf.a().m759a(str, str2);
    }

    private void a(String str, int i2) {
        Collection<bf.b> collectionM755a = bf.a().m755a(str);
        if (collectionM755a != null) {
            for (bf.b bVar : collectionM755a) {
                if (bVar != null) {
                    a(new s(bVar, i2, null, null));
                }
            }
        }
        bf.a().m758a(str);
    }

    public void a(j jVar) {
        a(jVar, 0L);
    }

    public void a(j jVar, long j2) {
        try {
            this.f966a.a(jVar, j2);
        } catch (IllegalStateException e2) {
            com.xiaomi.channel.commonutils.logger.b.m91a("can't execute job err = " + e2.getMessage());
        }
    }

    private void a(BroadcastReceiver broadcastReceiver) {
        if (broadcastReceiver != null) {
            try {
                unregisterReceiver(broadcastReceiver);
            } catch (IllegalArgumentException e2) {
                com.xiaomi.channel.commonutils.logger.b.a(e2);
            }
        }
    }

    public void a(gq gqVar) throws hm {
        hb hbVar = this.f955a;
        if (hbVar != null) {
            hbVar.b(gqVar);
            return;
        }
        throw new hm("try send msg while connection is null.");
    }

    public void a(gq[] gqVarArr) throws hm {
        hb hbVar = this.f955a;
        if (hbVar != null) {
            hbVar.a(gqVarArr);
            return;
        }
        throw new hm("try send msg while connection is null.");
    }

    public void a(boolean z2) {
        this.f964a.a(z2);
    }

    public void a(bf.b bVar) {
        if (bVar != null) {
            long jA = bVar.a();
            com.xiaomi.channel.commonutils.logger.b.m91a("schedule rebind job in " + (jA / 1000));
            a(new b(bVar), jA);
        }
    }

    public void a(int i2, Exception exc) {
        StringBuilder sb = new StringBuilder();
        sb.append("disconnect ");
        sb.append(hashCode());
        sb.append(", ");
        hb hbVar = this.f955a;
        sb.append(hbVar == null ? null : Integer.valueOf(hbVar.hashCode()));
        com.xiaomi.channel.commonutils.logger.b.m91a(sb.toString());
        hb hbVar2 = this.f955a;
        if (hbVar2 != null) {
            hbVar2.b(i2, exc);
            this.f955a = null;
        }
        a(7);
        a(4);
        bf.a().a(this, i2);
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m711a() {
        boolean zB = com.xiaomi.push.bg.b(this);
        boolean z2 = bf.a().m753a() > 0;
        boolean z3 = !m715b();
        boolean zM705h = m705h();
        boolean z4 = !m704g();
        boolean z5 = zB && z2 && z3 && zM705h && z4;
        if (!z5) {
            com.xiaomi.channel.commonutils.logger.b.e(String.format("not conn, net=%s;cnt=%s;!dis=%s;enb=%s;!spm=%s;", Boolean.valueOf(zB), Boolean.valueOf(z2), Boolean.valueOf(z3), Boolean.valueOf(zM705h), Boolean.valueOf(z4)));
        }
        return z5;
    }

    /* renamed from: a, reason: collision with other method in class */
    public com.xiaomi.push.service.k m709a() {
        return new com.xiaomi.push.service.k();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(Context context) throws InterruptedException {
        try {
            com.xiaomi.push.an.a();
            for (int i2 = 100; i2 > 0; i2--) {
                if (com.xiaomi.push.bg.c(context)) {
                    com.xiaomi.channel.commonutils.logger.b.m91a("network connectivity ok.");
                    return true;
                }
                try {
                    Thread.sleep(100L);
                } catch (Exception unused) {
                }
            }
            return false;
        } catch (Exception unused2) {
            return true;
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public hb m708a() {
        return this.f955a;
    }

    public void a(int i2) {
        this.f966a.a(i2);
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m712a(int i2) {
        return this.f966a.m793a(i2);
    }

    @Override // com.xiaomi.push.he
    public void a(hb hbVar) {
        com.xiaomi.channel.commonutils.logger.b.c("begin to connect...");
        gm.a().a(hbVar);
    }

    @Override // com.xiaomi.push.he
    public void a(hb hbVar, int i2, Exception exc) {
        gm.a().a(hbVar, i2, exc);
        if (m706i()) {
            return;
        }
        a(false);
    }

    @Override // com.xiaomi.push.he
    public void a(hb hbVar, Exception exc) {
        gm.a().a(hbVar, exc);
        c(false);
        if (m706i()) {
            return;
        }
        a(false);
    }

    public void a(n nVar) {
        synchronized (this.f969a) {
            this.f969a.add(nVar);
        }
    }

    /* renamed from: d, reason: collision with other method in class */
    public boolean m717d() {
        hb hbVar = this.f955a;
        return hbVar != null && hbVar.m472b();
    }
}
