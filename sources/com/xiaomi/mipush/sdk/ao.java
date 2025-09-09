package com.xiaomi.mipush.sdk;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.os.SystemClock;
import android.text.TextUtils;
import com.tekartik.sqflite.Constant;
import com.xiaomi.push.bg;
import com.xiaomi.push.bk;
import com.xiaomi.push.bo;
import com.xiaomi.push.dt;
import com.xiaomi.push.fo;
import com.xiaomi.push.in;
import com.xiaomi.push.io;
import com.xiaomi.push.ir;
import com.xiaomi.push.is;
import com.xiaomi.push.ix;
import com.xiaomi.push.ja;
import com.xiaomi.push.jj;
import com.xiaomi.push.jm;
import com.xiaomi.push.jn;
import com.xiaomi.push.jt;
import com.xiaomi.push.jx;
import com.xiaomi.push.jy;
import com.xiaomi.push.service.az;
import com.xiaomi.push.service.bc;
import com.xiaomi.push.service.bj;
import com.xiaomi.push.service.bm;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class ao {

    /* renamed from: a, reason: collision with root package name */
    private static ao f23380a = null;

    /* renamed from: a, reason: collision with other field name */
    private static final ArrayList<a> f122a = new ArrayList<>();

    /* renamed from: b, reason: collision with root package name */
    private static boolean f23381b = false;

    /* renamed from: a, reason: collision with other field name */
    private long f123a;

    /* renamed from: a, reason: collision with other field name */
    private Context f124a;

    /* renamed from: a, reason: collision with other field name */
    private Handler f126a;

    /* renamed from: a, reason: collision with other field name */
    private Messenger f127a;

    /* renamed from: a, reason: collision with other field name */
    private boolean f131a;

    /* renamed from: a, reason: collision with other field name */
    private List<Message> f130a = new ArrayList();

    /* renamed from: c, reason: collision with root package name */
    private boolean f23382c = false;

    /* renamed from: b, reason: collision with other field name */
    private String f132b = null;

    /* renamed from: a, reason: collision with other field name */
    private Intent f125a = null;

    /* renamed from: a, reason: collision with other field name */
    private Integer f128a = null;

    /* renamed from: a, reason: collision with other field name */
    private String f129a = null;

    static class a<T extends jy<T, ?>> {

        /* renamed from: a, reason: collision with root package name */
        in f23383a;

        /* renamed from: a, reason: collision with other field name */
        T f133a;

        /* renamed from: a, reason: collision with other field name */
        boolean f134a;

        a() {
        }
    }

    private ao(Context context) {
        this.f131a = false;
        this.f126a = null;
        this.f124a = context.getApplicationContext();
        this.f131a = m126c();
        f23381b = m127d();
        this.f126a = new ap(this, Looper.getMainLooper());
        if (com.xiaomi.push.j.m550a(context)) {
            com.xiaomi.push.service.j.a(new aq(this));
        }
        Intent intentB = b();
        if (intentB != null) {
            b(intentB);
        }
    }

    private synchronized void c(int i2) {
        this.f124a.getSharedPreferences("mipush_extra", 0).edit().putInt(Constants.EXTRA_KEY_BOOT_SERVICE_MODE, i2).commit();
    }

    private Intent d() {
        Intent intent = new Intent();
        String packageName = this.f124a.getPackageName();
        intent.setPackage("com.xiaomi.xmsf");
        intent.setClassName("com.xiaomi.xmsf", m124a());
        intent.putExtra("mipush_app_package", packageName);
        h();
        return intent;
    }

    private Intent e() {
        Intent intent = new Intent();
        String packageName = this.f124a.getPackageName();
        i();
        intent.setComponent(new ComponentName(this.f124a, "com.xiaomi.push.service.XMPushService"));
        intent.putExtra("mipush_app_package", packageName);
        return intent;
    }

    private void g() {
        this.f123a = SystemClock.elapsedRealtime();
    }

    private void h() {
        try {
            PackageManager packageManager = this.f124a.getPackageManager();
            ComponentName componentName = new ComponentName(this.f124a, "com.xiaomi.push.service.XMPushService");
            if (packageManager.getComponentEnabledSetting(componentName) == 2) {
                return;
            }
            packageManager.setComponentEnabledSetting(componentName, 2, 1);
        } catch (Throwable unused) {
        }
    }

    private void i() {
        try {
            PackageManager packageManager = this.f124a.getPackageManager();
            ComponentName componentName = new ComponentName(this.f124a, "com.xiaomi.push.service.XMPushService");
            if (packageManager.getComponentEnabledSetting(componentName) == 1) {
                return;
            }
            packageManager.setComponentEnabledSetting(componentName, 1, 1);
        } catch (Throwable unused) {
        }
    }

    /* renamed from: b, reason: collision with other method in class */
    public final void m135b() {
        Intent intentM121a = m121a();
        intentM121a.setAction("com.xiaomi.mipush.DISABLE_PUSH");
        c(intentM121a);
    }

    public void f() {
        Intent intentM121a = m121a();
        intentM121a.setAction("com.xiaomi.mipush.SET_NOTIFICATION_TYPE");
        intentM121a.putExtra(bj.F, this.f124a.getPackageName());
        intentM121a.putExtra(bj.K, bo.b(this.f124a.getPackageName()));
        c(intentM121a);
    }

    private Intent b() {
        if (!"com.xiaomi.xmsf".equals(this.f124a.getPackageName())) {
            return c();
        }
        com.xiaomi.channel.commonutils.logger.b.c("pushChannel xmsf create own channel");
        return e();
    }

    /* renamed from: c, reason: collision with other method in class */
    private boolean m126c() {
        try {
            PackageInfo packageInfo = this.f124a.getPackageManager().getPackageInfo("com.xiaomi.xmsf", 4);
            if (packageInfo == null) {
                return false;
            }
            return packageInfo.versionCode >= 105;
        } catch (Throwable unused) {
            return false;
        }
    }

    /* renamed from: e, reason: collision with other method in class */
    public void m139e() {
        Intent intentM121a = m121a();
        intentM121a.setAction("com.xiaomi.mipush.CLEAR_HEADSUPNOTIFICATION");
        Application application = (Application) bk.a("android.app.ActivityThread", "currentApplication", new Object[0]);
        String packageName = (application == null || application.getApplicationContext() == null) ? null : application.getApplicationContext().getPackageName();
        String packageName2 = this.f124a.getPackageName();
        if (TextUtils.isEmpty(packageName) || packageName.equals(packageName2)) {
            packageName = packageName2;
        } else {
            com.xiaomi.channel.commonutils.logger.b.m91a("application package name: " + packageName + ", not equals context package name: " + packageName2);
        }
        intentM121a.putExtra(bj.F, packageName);
        c(intentM121a);
    }

    private Intent c() {
        if (m133a()) {
            com.xiaomi.channel.commonutils.logger.b.c("pushChannel app start miui china channel");
            return d();
        }
        com.xiaomi.channel.commonutils.logger.b.c("pushChannel app start  own channel");
        return e();
    }

    /* renamed from: d, reason: collision with other method in class */
    public void m138d() {
        ArrayList<a> arrayList = f122a;
        synchronized (arrayList) {
            boolean z2 = Thread.currentThread() == Looper.getMainLooper().getThread();
            Iterator<a> it = arrayList.iterator();
            while (it.hasNext()) {
                a next = it.next();
                a(next.f133a, next.f23383a, next.f134a, false, null, true);
                if (!z2) {
                    try {
                        Thread.sleep(100L);
                    } catch (InterruptedException unused) {
                    }
                }
            }
            f122a.clear();
        }
    }

    public void b(int i2) {
        Intent intentM121a = m121a();
        intentM121a.setAction("com.xiaomi.mipush.SET_NOTIFICATION_TYPE");
        intentM121a.putExtra(bj.F, this.f124a.getPackageName());
        intentM121a.putExtra(bj.I, i2);
        intentM121a.putExtra(bj.K, bo.b(this.f124a.getPackageName() + i2));
        c(intentM121a);
    }

    public static synchronized ao a(Context context) {
        try {
            if (f23380a == null) {
                f23380a = new ao(context);
            }
        } catch (Throwable th) {
            throw th;
        }
        return f23380a;
    }

    private synchronized int a() {
        return this.f124a.getSharedPreferences("mipush_extra", 0).getInt(Constants.EXTRA_KEY_BOOT_SERVICE_MODE, -1);
    }

    /* renamed from: c, reason: collision with other method in class */
    public void m137c() {
        if (this.f125a != null) {
            g();
            c(this.f125a);
            this.f125a = null;
        }
    }

    /* renamed from: d, reason: collision with other method in class */
    private boolean m127d() {
        if (m133a()) {
            try {
                return this.f124a.getPackageManager().getPackageInfo("com.xiaomi.xmsf", 4).versionCode >= 108;
            } catch (Exception unused) {
            }
        }
        return true;
    }

    /* renamed from: b, reason: collision with other method in class */
    public boolean m136b() {
        if (!m133a() || !m128e()) {
            return true;
        }
        if (this.f128a == null) {
            Integer numValueOf = Integer.valueOf(bm.a(this.f124a).a());
            this.f128a = numValueOf;
            if (numValueOf.intValue() == 0) {
                this.f124a.getContentResolver().registerContentObserver(bm.a(this.f124a).m762a(), false, new ar(this, new Handler(Looper.getMainLooper())));
            }
        }
        return this.f128a.intValue() != 0;
    }

    /* renamed from: a, reason: collision with other method in class */
    public long m129a() {
        return this.f123a;
    }

    private void c(Intent intent) {
        az azVarA = az.a(this.f124a);
        int iA = is.ServiceBootMode.a();
        io ioVar = io.START;
        int iA2 = azVarA.a(iA, ioVar.a());
        int iA3 = a();
        io ioVar2 = io.BIND;
        boolean z2 = iA2 == ioVar2.a() && f23381b;
        int iA4 = z2 ? ioVar2.a() : ioVar.a();
        if (iA4 != iA3) {
            m134a(iA4);
        }
        if (z2) {
            d(intent);
        } else {
            b(intent);
        }
    }

    /* renamed from: e, reason: collision with other method in class */
    private boolean m128e() {
        String packageName = this.f124a.getPackageName();
        return packageName.contains("miui") || packageName.contains("xiaomi") || (this.f124a.getApplicationInfo().flags & 1) != 0;
    }

    public final void a(jn jnVar, boolean z2) {
        fo.a(this.f124a.getApplicationContext()).a(this.f124a.getPackageName(), "E100003", jnVar.a(), 6001, null);
        this.f125a = null;
        b.m140a(this.f124a).f139a = jnVar.a();
        Intent intentM121a = m121a();
        byte[] bArrA = jx.a(ai.a(this.f124a, jnVar, in.Registration));
        if (bArrA == null) {
            com.xiaomi.channel.commonutils.logger.b.m91a("register fail, because msgBytes is null.");
            return;
        }
        intentM121a.setAction("com.xiaomi.mipush.REGISTER_APP");
        intentM121a.putExtra("mipush_app_id", b.m140a(this.f124a).m141a());
        intentM121a.putExtra("mipush_payload", bArrA);
        intentM121a.putExtra("mipush_session", this.f129a);
        intentM121a.putExtra("mipush_env_chanage", z2);
        intentM121a.putExtra("mipush_env_type", b.m140a(this.f124a).a());
        if (bg.b(this.f124a) && m136b()) {
            g();
            c(intentM121a);
        } else {
            this.f125a = intentM121a;
        }
    }

    private synchronized void d(Intent intent) {
        try {
            if (this.f23382c) {
                Message messageA = a(intent);
                if (this.f130a.size() >= 50) {
                    this.f130a.remove(0);
                }
                this.f130a.add(messageA);
                return;
            }
            if (this.f127a == null) {
                this.f124a.bindService(intent, new as(this), 1);
                this.f23382c = true;
                this.f130a.clear();
                this.f130a.add(a(intent));
            } else {
                try {
                    this.f127a.send(a(intent));
                } catch (RemoteException unused) {
                    this.f127a = null;
                    this.f23382c = false;
                }
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    private void b(Intent intent) {
        try {
            if (!com.xiaomi.push.j.m549a() && Build.VERSION.SDK_INT >= 26) {
                d(intent);
            } else {
                this.f124a.startService(intent);
            }
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m130a() {
        b(m121a());
    }

    public final void a(jt jtVar) {
        byte[] bArrA = jx.a(ai.a(this.f124a, jtVar, in.UnRegistration));
        if (bArrA == null) {
            com.xiaomi.channel.commonutils.logger.b.m91a("unregister fail, because msgBytes is null.");
            return;
        }
        Intent intentM121a = m121a();
        intentM121a.setAction("com.xiaomi.mipush.UNREGISTER_APP");
        intentM121a.putExtra("mipush_app_id", b.m140a(this.f124a).m141a());
        intentM121a.putExtra("mipush_payload", bArrA);
        c(intentM121a);
    }

    public final void a(boolean z2) {
        a(z2, (String) null);
    }

    public final void a(boolean z2, String str) {
        if (z2) {
            af afVarA = af.a(this.f124a);
            au auVar = au.DISABLE_PUSH;
            afVarA.a(auVar, "syncing");
            af.a(this.f124a).a(au.ENABLE_PUSH, "");
            a(str, auVar, true, (HashMap<String, String>) null);
            return;
        }
        af afVarA2 = af.a(this.f124a);
        au auVar2 = au.ENABLE_PUSH;
        afVarA2.a(auVar2, "syncing");
        af.a(this.f124a).a(au.DISABLE_PUSH, "");
        a(str, auVar2, true, (HashMap<String, String>) null);
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m131a(Context context) {
        if (com.xiaomi.push.j.m549a()) {
            return;
        }
        ag agVarA = n.a(context);
        if (ag.HUAWEI.equals(agVarA)) {
            a((String) null, au.UPLOAD_HUAWEI_TOKEN, e.ASSEMBLE_PUSH_HUAWEI, Constant.METHOD_UPDATE);
        }
        if (ag.OPPO.equals(agVarA)) {
            a((String) null, au.UPLOAD_COS_TOKEN, e.ASSEMBLE_PUSH_COS, Constant.METHOD_UPDATE);
        }
        if (ag.VIVO.equals(agVarA)) {
            a((String) null, au.UPLOAD_FTOS_TOKEN, e.ASSEMBLE_PUSH_FTOS, Constant.METHOD_UPDATE);
        }
    }

    public final void a(String str, au auVar, e eVar, String str2) {
        af.a(this.f124a).a(auVar, "syncing");
        HashMap<String, String> mapM159a = i.m159a(this.f124a, eVar);
        mapM159a.put("third_sync_reason", str2);
        a(str, auVar, false, mapM159a);
    }

    void a(int i2, String str) {
        Intent intentM121a = m121a();
        intentM121a.setAction("com.xiaomi.mipush.thirdparty");
        intentM121a.putExtra("com.xiaomi.mipush.thirdparty_LEVEL", i2);
        intentM121a.putExtra("com.xiaomi.mipush.thirdparty_DESC", str);
        b(intentM121a);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str, au auVar, boolean z2, HashMap<String, String> map) {
        jm jmVar;
        String strA = str;
        if (b.m140a(this.f124a).m147b() && bg.b(this.f124a)) {
            jm jmVar2 = new jm();
            jmVar2.a(true);
            Intent intentM121a = m121a();
            if (TextUtils.isEmpty(str)) {
                strA = bc.a();
                jmVar2.a(strA);
                jmVar = z2 ? new jm(strA, true) : null;
                synchronized (af.class) {
                    af.a(this.f124a).m117a(strA);
                }
            } else {
                jmVar2.a(strA);
                jmVar = z2 ? new jm(strA, true) : null;
            }
            switch (at.f23388a[auVar.ordinal()]) {
                case 1:
                    ix ixVar = ix.DisablePushMessage;
                    jmVar2.c(ixVar.f620a);
                    jmVar.c(ixVar.f620a);
                    if (map != null) {
                        jmVar2.a(map);
                        jmVar.a(map);
                    }
                    intentM121a.setAction("com.xiaomi.mipush.DISABLE_PUSH_MESSAGE");
                    break;
                case 2:
                    ix ixVar2 = ix.EnablePushMessage;
                    jmVar2.c(ixVar2.f620a);
                    jmVar.c(ixVar2.f620a);
                    if (map != null) {
                        jmVar2.a(map);
                        jmVar.a(map);
                    }
                    intentM121a.setAction("com.xiaomi.mipush.ENABLE_PUSH_MESSAGE");
                    break;
                case 3:
                case 4:
                case 5:
                case 6:
                    jmVar2.c(ix.ThirdPartyRegUpdate.f620a);
                    if (map != null) {
                        jmVar2.a(map);
                        break;
                    }
                    break;
            }
            com.xiaomi.channel.commonutils.logger.b.e("type:" + auVar + ", " + strA);
            jmVar2.b(b.m140a(this.f124a).m141a());
            jmVar2.d(this.f124a.getPackageName());
            in inVar = in.Notification;
            a((ao) jmVar2, inVar, false, (ja) null);
            if (z2) {
                jmVar.b(b.m140a(this.f124a).m141a());
                jmVar.d(this.f124a.getPackageName());
                Context context = this.f124a;
                byte[] bArrA = jx.a(ai.a(context, jmVar, inVar, false, context.getPackageName(), b.m140a(this.f124a).m141a()));
                if (bArrA != null) {
                    dt.a(this.f124a.getPackageName(), this.f124a, jmVar, inVar, bArrA.length);
                    intentM121a.putExtra("mipush_payload", bArrA);
                    intentM121a.putExtra("com.xiaomi.mipush.MESSAGE_CACHE", true);
                    intentM121a.putExtra("mipush_app_id", b.m140a(this.f124a).m141a());
                    intentM121a.putExtra("mipush_app_token", b.m140a(this.f124a).b());
                    c(intentM121a);
                }
            }
            Message messageObtain = Message.obtain();
            messageObtain.what = 19;
            int iOrdinal = auVar.ordinal();
            messageObtain.obj = strA;
            messageObtain.arg1 = iOrdinal;
            if (map != null && map.get("third_sync_reason") != null) {
                Bundle bundle = new Bundle();
                bundle.putString("third_sync_reason", map.get("third_sync_reason"));
                messageObtain.setData(bundle);
            }
            this.f126a.sendMessageDelayed(messageObtain, 5000L);
        }
    }

    public final <T extends jy<T, ?>> void a(T t2, in inVar, ja jaVar) {
        a((ao) t2, inVar, !inVar.equals(in.Registration), jaVar);
    }

    public final <T extends jy<T, ?>> void a(T t2, in inVar, boolean z2, ja jaVar, boolean z3) {
        a(t2, inVar, z2, true, jaVar, z3);
    }

    public final <T extends jy<T, ?>> void a(T t2, in inVar, boolean z2, ja jaVar) {
        a(t2, inVar, z2, true, jaVar, true);
    }

    public final <T extends jy<T, ?>> void a(T t2, in inVar, boolean z2, boolean z3, ja jaVar, boolean z4) {
        a(t2, inVar, z2, z3, jaVar, z4, this.f124a.getPackageName(), b.m140a(this.f124a).m141a());
    }

    public final <T extends jy<T, ?>> void a(T t2, in inVar, boolean z2, boolean z3, ja jaVar, boolean z4, String str, String str2) {
        a(t2, inVar, z2, z3, jaVar, z4, str, str2, true);
    }

    public final <T extends jy<T, ?>> void a(T t2, in inVar, boolean z2, boolean z3, ja jaVar, boolean z4, String str, String str2, boolean z5) {
        a(t2, inVar, z2, z3, jaVar, z4, str, str2, z5, true);
    }

    public final <T extends jy<T, ?>> void a(T t2, in inVar, boolean z2, boolean z3, ja jaVar, boolean z4, String str, String str2, boolean z5, boolean z6) {
        jj jjVarB;
        if (z6 && !b.m140a(this.f124a).m149c()) {
            if (z3) {
                a((ao) t2, inVar, z2);
                return;
            } else {
                com.xiaomi.channel.commonutils.logger.b.m91a("drop the message before initialization.");
                return;
            }
        }
        if (z5) {
            jjVarB = ai.a(this.f124a, t2, inVar, z2, str, str2);
        } else {
            jjVarB = ai.b(this.f124a, t2, inVar, z2, str, str2);
        }
        if (jaVar != null) {
            jjVarB.a(jaVar);
        }
        byte[] bArrA = jx.a(jjVarB);
        if (bArrA == null) {
            com.xiaomi.channel.commonutils.logger.b.m91a("send message fail, because msgBytes is null.");
            return;
        }
        dt.a(this.f124a.getPackageName(), this.f124a, t2, inVar, bArrA.length);
        Intent intentM121a = m121a();
        intentM121a.setAction("com.xiaomi.mipush.SEND_MESSAGE");
        intentM121a.putExtra("mipush_payload", bArrA);
        intentM121a.putExtra("com.xiaomi.mipush.MESSAGE_CACHE", z4);
        c(intentM121a);
    }

    public final void a(ir irVar) {
        Intent intentM121a = m121a();
        byte[] bArrA = jx.a(irVar);
        if (bArrA == null) {
            com.xiaomi.channel.commonutils.logger.b.m91a("send TinyData failed, because tinyDataBytes is null.");
            return;
        }
        intentM121a.setAction("com.xiaomi.mipush.SEND_TINYDATA");
        intentM121a.putExtra("mipush_payload", bArrA);
        b(intentM121a);
    }

    /* renamed from: a, reason: collision with other method in class */
    private Intent m121a() {
        if (m133a() && !"com.xiaomi.xmsf".equals(this.f124a.getPackageName())) {
            return d();
        }
        return e();
    }

    /* renamed from: a, reason: collision with other method in class */
    private String m124a() {
        String str = this.f132b;
        if (str != null) {
            return str;
        }
        try {
            if (this.f124a.getPackageManager().getPackageInfo("com.xiaomi.xmsf", 4).versionCode >= 106) {
                this.f132b = "com.xiaomi.push.service.XMPushService";
                return "com.xiaomi.push.service.XMPushService";
            }
        } catch (Exception unused) {
        }
        this.f132b = "com.xiaomi.xmsf.push.service.XMPushService";
        return "com.xiaomi.xmsf.push.service.XMPushService";
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m133a() {
        return this.f131a && 1 == b.m140a(this.f124a).a();
    }

    public <T extends jy<T, ?>> void a(T t2, in inVar, boolean z2) {
        a aVar = new a();
        aVar.f133a = t2;
        aVar.f23383a = inVar;
        aVar.f134a = z2;
        ArrayList<a> arrayList = f122a;
        synchronized (arrayList) {
            try {
                arrayList.add(aVar);
                if (arrayList.size() > 10) {
                    arrayList.remove(0);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void a(int i2) {
        a(i2, 0);
    }

    void a(int i2, int i3) {
        Intent intentM121a = m121a();
        intentM121a.setAction("com.xiaomi.mipush.CLEAR_NOTIFICATION");
        intentM121a.putExtra(bj.F, this.f124a.getPackageName());
        intentM121a.putExtra(bj.G, i2);
        intentM121a.putExtra(bj.H, i3);
        c(intentM121a);
    }

    public void a(String str, String str2) {
        Intent intentM121a = m121a();
        intentM121a.setAction("com.xiaomi.mipush.CLEAR_NOTIFICATION");
        intentM121a.putExtra(bj.F, this.f124a.getPackageName());
        intentM121a.putExtra(bj.L, str);
        intentM121a.putExtra(bj.M, str2);
        c(intentM121a);
    }

    /* renamed from: a, reason: collision with other method in class */
    void m132a(Intent intent) {
        intent.fillIn(m121a(), 24);
        c(intent);
    }

    private Message a(Intent intent) {
        Message messageObtain = Message.obtain();
        messageObtain.what = 17;
        messageObtain.obj = intent;
        return messageObtain;
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m134a(int i2) {
        if (!b.m140a(this.f124a).m147b()) {
            return false;
        }
        c(i2);
        jm jmVar = new jm();
        jmVar.a(bc.a());
        jmVar.b(b.m140a(this.f124a).m141a());
        jmVar.d(this.f124a.getPackageName());
        jmVar.c(ix.ClientABTest.f620a);
        HashMap map = new HashMap();
        jmVar.f760a = map;
        map.put("boot_mode", i2 + "");
        a(this.f124a).a((ao) jmVar, in.Notification, false, (ja) null);
        return true;
    }
}
