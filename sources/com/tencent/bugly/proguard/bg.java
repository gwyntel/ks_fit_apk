package com.tencent.bugly.proguard;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;

/* loaded from: classes4.dex */
public final class bg extends Thread {

    /* renamed from: a, reason: collision with root package name */
    public bf f20955a;

    /* renamed from: g, reason: collision with root package name */
    private a f20961g;

    /* renamed from: c, reason: collision with root package name */
    private boolean f20957c = false;

    /* renamed from: d, reason: collision with root package name */
    private boolean f20958d = true;

    /* renamed from: e, reason: collision with root package name */
    private boolean f20959e = false;

    /* renamed from: f, reason: collision with root package name */
    private int f20960f = 1;

    /* renamed from: b, reason: collision with root package name */
    public boolean f20956b = true;

    public interface a {
    }

    public final boolean a() {
        this.f20957c = true;
        if (!isAlive()) {
            return false;
        }
        try {
            interrupt();
        } catch (Exception e2) {
            al.b(e2);
        }
        al.d("MainHandlerChecker is reset to null.", new Object[0]);
        this.f20955a = null;
        return true;
    }

    public final boolean b() {
        Handler handler = new Handler(Looper.getMainLooper());
        bf bfVar = this.f20955a;
        if (bfVar != null) {
            bfVar.f20949b = 5000L;
        } else {
            this.f20955a = new bf(handler, handler.getLooper().getThread().getName());
        }
        if (isAlive()) {
            return false;
        }
        try {
            start();
            return true;
        } catch (Exception e2) {
            al.b(e2);
            return false;
        }
    }

    public final synchronized void c() {
        this.f20958d = false;
        al.c("Record stack trace is disabled.", new Object[0]);
    }

    public final synchronized void d() {
        this.f20959e = true;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public final void run() {
        bf bfVar;
        boolean z2;
        long jCurrentTimeMillis = System.currentTimeMillis();
        while (!this.f20957c) {
            try {
                bfVar = this.f20955a;
                z2 = false;
            } catch (Exception e2) {
                al.b(e2);
            } catch (OutOfMemoryError e3) {
                al.b(e3);
            }
            if (bfVar == null) {
                al.c("Main handler checker is null. Stop thread monitor.", new Object[0]);
                return;
            }
            if (bfVar.f20950c) {
                bfVar.f20950c = false;
                bfVar.f20951d = SystemClock.uptimeMillis();
                bfVar.f20948a.post(bfVar);
            }
            a(bfVar);
            if (this.f20956b && this.f20958d) {
                long jB = bfVar.b();
                if (jB > 1510 && jB < 199990) {
                    boolean z3 = true;
                    if (jB <= 5010) {
                        this.f20960f = 1;
                        al.c("timeSinceMsgSent in [2s, 5s], record stack", new Object[0]);
                    } else {
                        int i2 = this.f20960f;
                        int i3 = i2 + 1;
                        this.f20960f = i3;
                        if ((i2 & i3) != 0) {
                            z3 = false;
                        }
                        if (z3) {
                            al.c("timeSinceMsgSent in (5s, 200s), should record stack:true", new Object[0]);
                        }
                    }
                    z2 = z3;
                }
            }
            if (z2) {
                bfVar.d();
            }
            if (this.f20961g != null && this.f20958d) {
                bfVar.a();
                bfVar.b();
            }
            ap.b(500 - ((System.currentTimeMillis() - jCurrentTimeMillis) % 500));
        }
    }

    private synchronized void a(bf bfVar) {
        if (this.f20958d) {
            return;
        }
        if (this.f20959e && !bfVar.a()) {
            al.c("Restart getting main stack trace.", new Object[0]);
            this.f20958d = true;
            this.f20959e = false;
        }
    }
}
