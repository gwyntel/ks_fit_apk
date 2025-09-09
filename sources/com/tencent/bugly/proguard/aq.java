package com.tencent.bugly.proguard;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import dev.fluttercommunity.plus.connectivity.ConnectivityBroadcastReceiver;

/* loaded from: classes4.dex */
public final class aq extends BroadcastReceiver {

    /* renamed from: d, reason: collision with root package name */
    private static aq f20808d;

    /* renamed from: b, reason: collision with root package name */
    private Context f20810b;

    /* renamed from: c, reason: collision with root package name */
    private String f20811c;

    /* renamed from: e, reason: collision with root package name */
    private boolean f20812e = true;

    /* renamed from: a, reason: collision with root package name */
    private IntentFilter f20809a = new IntentFilter();

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        try {
            a(context, intent);
        } catch (Throwable th) {
            if (al.a(th)) {
                return;
            }
            th.printStackTrace();
        }
    }

    public static synchronized aq a() {
        try {
            if (f20808d == null) {
                f20808d = new aq();
            }
        } catch (Throwable th) {
            throw th;
        }
        return f20808d;
    }

    public final synchronized void b(Context context) {
        try {
            al.a(aq.class, "Unregister broadcast receiver of Bugly.", new Object[0]);
            context.unregisterReceiver(this);
            this.f20810b = context;
        } catch (Throwable th) {
            if (al.a(th)) {
                return;
            }
            th.printStackTrace();
        }
    }

    public final synchronized void a(String str) {
        try {
            if (!this.f20809a.hasAction(str)) {
                this.f20809a.addAction(str);
            }
            al.c("add action %s", str);
        } catch (Throwable th) {
            throw th;
        }
    }

    public final synchronized void a(Context context) {
        this.f20810b = context;
        ap.a(new Runnable() { // from class: com.tencent.bugly.proguard.aq.1
            @Override // java.lang.Runnable
            public final void run() {
                try {
                    al.a(aq.f20808d.getClass(), "Register broadcast receiver of Bugly.", new Object[0]);
                    synchronized (this) {
                        aq.this.f20810b.registerReceiver(aq.f20808d, aq.this.f20809a, "com.tencent.bugly.BuglyBroadcastReceiver.permission", null);
                    }
                } catch (Throwable th) {
                    th.printStackTrace();
                }
            }
        });
    }

    private synchronized boolean a(Context context, Intent intent) {
        if (context != null && intent != null) {
            if (intent.getAction().equals(ConnectivityBroadcastReceiver.CONNECTIVITY_ACTION)) {
                if (this.f20812e) {
                    this.f20812e = false;
                    return true;
                }
                String strC = ab.c(this.f20810b);
                al.c("is Connect BC ".concat(String.valueOf(strC)), new Object[0]);
                al.a("network %s changed to %s", this.f20811c, String.valueOf(strC));
                if (strC == null) {
                    this.f20811c = null;
                    return true;
                }
                String str = this.f20811c;
                this.f20811c = strC;
                long jCurrentTimeMillis = System.currentTimeMillis();
                ac acVarA = ac.a();
                ai aiVarA = ai.a();
                aa aaVarA = aa.a(context);
                if (acVarA != null && aiVarA != null && aaVarA != null) {
                    if (!strC.equals(str) && jCurrentTimeMillis - aiVarA.a(at.f20838a) > 30000) {
                        al.a("try to upload crash on network changed.", new Object[0]);
                        at atVarA = at.a();
                        if (atVarA != null) {
                            atVarA.a(0L);
                        }
                        al.a("try to upload userinfo on network changed.", new Object[0]);
                        s.f21131b.b();
                    }
                    return true;
                }
                al.d("not inited BC not work", new Object[0]);
                return true;
            }
        }
        return false;
    }
}
