package com.umeng.message.proguard;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;

/* loaded from: classes4.dex */
public abstract class ah {

    /* renamed from: a, reason: collision with root package name */
    final long f22733a;

    /* renamed from: b, reason: collision with root package name */
    long f22734b;

    /* renamed from: d, reason: collision with root package name */
    private final long f22736d;

    /* renamed from: e, reason: collision with root package name */
    private long f22737e;

    /* renamed from: c, reason: collision with root package name */
    boolean f22735c = false;

    /* renamed from: f, reason: collision with root package name */
    private final Handler f22738f = new Handler(Looper.getMainLooper()) { // from class: com.umeng.message.proguard.ah.1
        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            synchronized (ah.this) {
                try {
                    if (message.what == 1) {
                        ah ahVar = ah.this;
                        if (ahVar.f22735c) {
                            return;
                        }
                        long jElapsedRealtime = ahVar.f22734b - SystemClock.elapsedRealtime();
                        if (jElapsedRealtime <= 0) {
                            ah.this.e();
                        } else {
                            ah ahVar2 = ah.this;
                            if (jElapsedRealtime < ahVar2.f22733a) {
                                ahVar2.a(jElapsedRealtime);
                                sendMessageDelayed(obtainMessage(1), jElapsedRealtime);
                            } else {
                                long jElapsedRealtime2 = SystemClock.elapsedRealtime();
                                ah.this.a(jElapsedRealtime);
                                long jElapsedRealtime3 = (jElapsedRealtime2 + ah.this.f22733a) - SystemClock.elapsedRealtime();
                                while (jElapsedRealtime3 < 0) {
                                    jElapsedRealtime3 += ah.this.f22733a;
                                }
                                sendMessageDelayed(obtainMessage(1), jElapsedRealtime3);
                            }
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    };

    public ah(long j2, long j3) {
        this.f22736d = j2;
        this.f22733a = j3;
    }

    public final synchronized void a() {
        this.f22735c = true;
        this.f22738f.removeMessages(1);
    }

    public abstract void a(long j2);

    public final synchronized ah b() {
        this.f22735c = false;
        if (this.f22736d <= 0) {
            e();
            return this;
        }
        this.f22734b = SystemClock.elapsedRealtime() + this.f22736d;
        Handler handler = this.f22738f;
        handler.sendMessage(handler.obtainMessage(1));
        return this;
    }

    public final synchronized ah c() {
        this.f22735c = false;
        long jElapsedRealtime = this.f22734b - SystemClock.elapsedRealtime();
        this.f22737e = jElapsedRealtime;
        if (jElapsedRealtime <= 0) {
            return this;
        }
        this.f22738f.removeMessages(1);
        Handler handler = this.f22738f;
        handler.sendMessageAtFrontOfQueue(handler.obtainMessage(2));
        return this;
    }

    public final synchronized ah d() {
        this.f22735c = false;
        if (this.f22737e <= 0) {
            return this;
        }
        this.f22738f.removeMessages(2);
        this.f22734b = this.f22737e + SystemClock.elapsedRealtime();
        Handler handler = this.f22738f;
        handler.sendMessageAtFrontOfQueue(handler.obtainMessage(1));
        return this;
    }

    public abstract void e();
}
