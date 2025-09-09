package com.xiaomi.push;

import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ak {

    /* renamed from: a, reason: collision with root package name */
    private int f23437a;

    /* renamed from: a, reason: collision with other field name */
    private Handler f169a;

    /* renamed from: a, reason: collision with other field name */
    private a f170a;

    /* renamed from: a, reason: collision with other field name */
    private volatile b f171a;

    /* renamed from: a, reason: collision with other field name */
    private volatile boolean f172a;

    /* renamed from: b, reason: collision with root package name */
    private final boolean f23438b;

    public static abstract class b {
        public void a() {
        }

        public abstract void b();

        /* renamed from: c */
        public void mo308c() {
        }
    }

    public ak() {
        this(false);
    }

    private class a extends Thread {

        /* renamed from: a, reason: collision with other field name */
        private final LinkedBlockingQueue<b> f173a;

        public a() {
            super("PackageProcessor");
            this.f173a = new LinkedBlockingQueue<>();
        }

        public void a(b bVar) {
            try {
                this.f173a.add(bVar);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() throws InterruptedException {
            long j2 = ak.this.f23437a > 0 ? ak.this.f23437a : Long.MAX_VALUE;
            while (!ak.this.f172a) {
                try {
                    b bVarPoll = this.f173a.poll(j2, TimeUnit.SECONDS);
                    ak.this.f171a = bVarPoll;
                    if (bVarPoll != null) {
                        a(0, bVarPoll);
                        bVarPoll.b();
                        a(1, bVarPoll);
                    } else if (ak.this.f23437a > 0) {
                        ak.this.a();
                    }
                } catch (InterruptedException e2) {
                    com.xiaomi.channel.commonutils.logger.b.a(e2);
                }
            }
        }

        private void a(int i2, b bVar) {
            try {
                ak.this.f169a.sendMessage(ak.this.f169a.obtainMessage(i2, bVar));
            } catch (Exception e2) {
                com.xiaomi.channel.commonutils.logger.b.a(e2);
            }
        }
    }

    public ak(boolean z2) {
        this(z2, 0);
    }

    public ak(boolean z2, int i2) {
        this.f169a = null;
        this.f172a = false;
        this.f23437a = 0;
        this.f169a = new al(this, Looper.getMainLooper());
        this.f23438b = z2;
        this.f23437a = i2;
    }

    public synchronized void a(b bVar) {
        try {
            if (this.f170a == null) {
                a aVar = new a();
                this.f170a = aVar;
                aVar.setDaemon(this.f23438b);
                this.f172a = false;
                this.f170a.start();
            }
            this.f170a.a(bVar);
        } catch (Throwable th) {
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void a() {
        this.f170a = null;
        this.f172a = true;
    }

    public void a(b bVar, long j2) {
        this.f169a.postDelayed(new am(this, bVar), j2);
    }
}
