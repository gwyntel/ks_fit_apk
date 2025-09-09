package com.umeng.commonsdk.utils;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;

/* loaded from: classes4.dex */
public abstract class a {

    /* renamed from: e, reason: collision with root package name */
    private static final int f22534e = 1;

    /* renamed from: a, reason: collision with root package name */
    private final long f22535a;

    /* renamed from: b, reason: collision with root package name */
    private final long f22536b;

    /* renamed from: c, reason: collision with root package name */
    private long f22537c;

    /* renamed from: f, reason: collision with root package name */
    private HandlerThread f22539f;

    /* renamed from: g, reason: collision with root package name */
    private Handler f22540g;

    /* renamed from: d, reason: collision with root package name */
    private boolean f22538d = false;

    /* renamed from: h, reason: collision with root package name */
    private Handler.Callback f22541h = new Handler.Callback() { // from class: com.umeng.commonsdk.utils.a.1
        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            synchronized (a.this) {
                try {
                    if (a.this.f22538d) {
                        return true;
                    }
                    long jElapsedRealtime = a.this.f22537c - SystemClock.elapsedRealtime();
                    if (jElapsedRealtime <= 0) {
                        a.this.c();
                        if (a.this.f22539f != null) {
                            a.this.f22539f.quit();
                        }
                    } else if (jElapsedRealtime < a.this.f22536b) {
                        a.this.f22540g.sendMessageDelayed(a.this.f22540g.obtainMessage(1), jElapsedRealtime);
                    } else {
                        long jElapsedRealtime2 = SystemClock.elapsedRealtime();
                        a.this.a(jElapsedRealtime);
                        long jElapsedRealtime3 = (jElapsedRealtime2 + a.this.f22536b) - SystemClock.elapsedRealtime();
                        while (jElapsedRealtime3 < 0) {
                            jElapsedRealtime3 += a.this.f22536b;
                        }
                        a.this.f22540g.sendMessageDelayed(a.this.f22540g.obtainMessage(1), jElapsedRealtime3);
                    }
                    return false;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    };

    public a(long j2, long j3) {
        this.f22535a = j2;
        this.f22536b = j3;
        if (d()) {
            this.f22540g = new Handler(this.f22541h);
            return;
        }
        HandlerThread handlerThread = new HandlerThread("CountDownTimerThread");
        this.f22539f = handlerThread;
        handlerThread.start();
        this.f22540g = new Handler(this.f22539f.getLooper(), this.f22541h);
    }

    public abstract void a(long j2);

    public abstract void c();

    private boolean d() {
        return Looper.getMainLooper().getThread().equals(Thread.currentThread());
    }

    public final synchronized void a() {
        this.f22538d = true;
        this.f22540g.removeMessages(1);
    }

    public final synchronized a b() {
        this.f22538d = false;
        if (this.f22535a <= 0) {
            c();
            return this;
        }
        this.f22537c = SystemClock.elapsedRealtime() + this.f22535a;
        Handler handler = this.f22540g;
        handler.sendMessage(handler.obtainMessage(1));
        return this;
    }
}
