package com.vivo.push;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.text.TextUtils;
import androidx.media3.exoplayer.ExoPlayer;
import com.vivo.push.util.t;
import com.vivo.push.util.z;
import com.vivo.vms.IPCInvoke;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes4.dex */
public final class b implements ServiceConnection {

    /* renamed from: a, reason: collision with root package name */
    private static final Object f23019a = new Object();

    /* renamed from: b, reason: collision with root package name */
    private static Map<String, b> f23020b = new HashMap();

    /* renamed from: c, reason: collision with root package name */
    private boolean f23021c;

    /* renamed from: d, reason: collision with root package name */
    private String f23022d;

    /* renamed from: e, reason: collision with root package name */
    private Context f23023e;

    /* renamed from: g, reason: collision with root package name */
    private volatile IPCInvoke f23025g;

    /* renamed from: i, reason: collision with root package name */
    private String f23027i;

    /* renamed from: j, reason: collision with root package name */
    private Handler f23028j;

    /* renamed from: h, reason: collision with root package name */
    private Object f23026h = new Object();

    /* renamed from: f, reason: collision with root package name */
    private AtomicInteger f23024f = new AtomicInteger(1);

    private b(Context context, String str) {
        this.f23022d = null;
        this.f23028j = null;
        this.f23023e = context;
        this.f23027i = str;
        this.f23028j = new Handler(Looper.getMainLooper(), new c(this));
        String strB = t.b(context);
        this.f23022d = strB;
        if (!TextUtils.isEmpty(strB) && !TextUtils.isEmpty(this.f23027i)) {
            this.f23021c = z.a(context, this.f23022d) >= 1260;
            b();
            return;
        }
        com.vivo.push.util.p.c(this.f23023e, "init error : push pkgname is " + this.f23022d + " ; action is " + this.f23027i);
        this.f23021c = false;
    }

    private void d() {
        this.f23028j.removeMessages(1);
        this.f23028j.sendEmptyMessageDelayed(1, 3000L);
    }

    private void e() {
        this.f23028j.removeMessages(1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void f() {
        try {
            this.f23023e.unbindService(this);
        } catch (Exception e2) {
            com.vivo.push.util.p.a("AidlManager", "On unBindServiceException:" + e2.getMessage());
        }
    }

    @Override // android.content.ServiceConnection
    public final void onBindingDied(ComponentName componentName) {
        com.vivo.push.util.p.b("AidlManager", "onBindingDied : ".concat(String.valueOf(componentName)));
    }

    @Override // android.content.ServiceConnection
    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        e();
        this.f23025g = IPCInvoke.Stub.asInterface(iBinder);
        if (this.f23025g == null) {
            com.vivo.push.util.p.d("AidlManager", "onServiceConnected error : aidl must not be null.");
            f();
            this.f23024f.set(1);
            return;
        }
        if (this.f23024f.get() == 2) {
            a(4);
        } else if (this.f23024f.get() != 4) {
            f();
        }
        synchronized (this.f23026h) {
            this.f23026h.notifyAll();
        }
    }

    @Override // android.content.ServiceConnection
    public final void onServiceDisconnected(ComponentName componentName) {
        this.f23025g = null;
        a(1);
    }

    public static b a(Context context, String str) {
        b bVar = f23020b.get(str);
        if (bVar == null) {
            synchronized (f23019a) {
                try {
                    bVar = f23020b.get(str);
                    if (bVar == null) {
                        bVar = new b(context, str);
                        f23020b.put(str, bVar);
                    }
                } finally {
                }
            }
        }
        return bVar;
    }

    private void b() {
        int i2 = this.f23024f.get();
        com.vivo.push.util.p.d("AidlManager", "Enter connect, Connection Status: ".concat(String.valueOf(i2)));
        if (i2 == 4 || i2 == 2 || i2 == 3 || i2 == 5 || !this.f23021c) {
            return;
        }
        a(2);
        if (c()) {
            d();
        } else {
            a(1);
            com.vivo.push.util.p.a("AidlManager", "bind core service fail");
        }
    }

    private boolean c() {
        Intent intent = new Intent(this.f23027i);
        intent.setPackage(this.f23022d);
        try {
            return this.f23023e.bindService(intent, this, 1);
        } catch (Exception e2) {
            com.vivo.push.util.p.a("AidlManager", "bind core error", e2);
            return false;
        }
    }

    public final boolean a() {
        String strB = t.b(this.f23023e);
        this.f23022d = strB;
        if (TextUtils.isEmpty(strB)) {
            com.vivo.push.util.p.c(this.f23023e, "push pkgname is null");
            return false;
        }
        boolean z2 = z.a(this.f23023e, this.f23022d) >= 1260;
        this.f23021c = z2;
        return z2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i2) {
        this.f23024f.set(i2);
    }

    public final boolean a(Bundle bundle) {
        b();
        if (this.f23024f.get() == 2) {
            synchronized (this.f23026h) {
                try {
                    this.f23026h.wait(ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
            }
        }
        try {
            int i2 = this.f23024f.get();
            if (i2 == 4) {
                this.f23028j.removeMessages(2);
                this.f23028j.sendEmptyMessageDelayed(2, 30000L);
                this.f23025g.asyncCall(bundle, null);
                return true;
            }
            com.vivo.push.util.p.d("AidlManager", "invoke error : connect status = ".concat(String.valueOf(i2)));
            return false;
        } catch (Exception e3) {
            com.vivo.push.util.p.a("AidlManager", "invoke error ", e3);
            int i3 = this.f23024f.get();
            com.vivo.push.util.p.d("AidlManager", "Enter disconnect, Connection Status: ".concat(String.valueOf(i3)));
            if (i3 == 2) {
                e();
                a(1);
                return false;
            }
            if (i3 == 3) {
                a(1);
                return false;
            }
            if (i3 != 4) {
                return false;
            }
            a(1);
            f();
            return false;
        }
    }
}
