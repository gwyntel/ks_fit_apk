package com.xiaomi.push.service;

import android.os.SystemClock;
import java.util.concurrent.RejectedExecutionException;

/* loaded from: classes4.dex */
public class q {

    /* renamed from: a, reason: collision with root package name */
    private static long f24604a;

    /* renamed from: b, reason: collision with root package name */
    private static long f24605b;

    /* renamed from: c, reason: collision with root package name */
    private static long f24606c;

    /* renamed from: a, reason: collision with other field name */
    private final a f1098a;

    /* renamed from: a, reason: collision with other field name */
    private final c f1099a;

    private static final class a {

        /* renamed from: a, reason: collision with root package name */
        private final c f24607a;

        a(c cVar) {
            this.f24607a = cVar;
        }

        protected void finalize() throws Throwable {
            try {
                synchronized (this.f24607a) {
                    this.f24607a.f24611c = true;
                    this.f24607a.notify();
                }
            } finally {
                super.finalize();
            }
        }
    }

    public static abstract class b implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        protected int f24608a;

        public b(int i2) {
            this.f24608a = i2;
        }
    }

    private static final class c extends Thread {

        /* renamed from: b, reason: collision with other field name */
        private boolean f1102b;

        /* renamed from: c, reason: collision with root package name */
        private boolean f24611c;

        /* renamed from: a, reason: collision with root package name */
        private volatile long f24609a = 0;

        /* renamed from: a, reason: collision with other field name */
        private volatile boolean f1101a = false;

        /* renamed from: b, reason: collision with root package name */
        private long f24610b = 50;

        /* renamed from: a, reason: collision with other field name */
        private a f1100a = new a();

        private static final class a {

            /* renamed from: a, reason: collision with root package name */
            private int f24612a;

            /* renamed from: a, reason: collision with other field name */
            private d[] f1103a;

            /* renamed from: b, reason: collision with root package name */
            private int f24613b;

            /* renamed from: c, reason: collision with root package name */
            private int f24614c;

            private a() {
                this.f24612a = 256;
                this.f1103a = new d[256];
                this.f24613b = 0;
                this.f24614c = 0;
            }

            private void c() {
                int i2 = this.f24613b;
                int i3 = i2 - 1;
                int i4 = (i2 - 2) / 2;
                while (true) {
                    d[] dVarArr = this.f1103a;
                    d dVar = dVarArr[i3];
                    long j2 = dVar.f1104a;
                    d dVar2 = dVarArr[i4];
                    if (j2 >= dVar2.f1104a) {
                        return;
                    }
                    dVarArr[i3] = dVar2;
                    dVarArr[i4] = dVar;
                    i3 = i4;
                    i4 = (i4 - 1) / 2;
                }
            }

            public void b(int i2) {
                int i3;
                if (i2 < 0 || i2 >= (i3 = this.f24613b)) {
                    return;
                }
                d[] dVarArr = this.f1103a;
                int i4 = i3 - 1;
                this.f24613b = i4;
                dVarArr[i2] = dVarArr[i4];
                dVarArr[i4] = null;
                c(i2);
            }

            public d a() {
                return this.f1103a[0];
            }

            /* renamed from: a, reason: collision with other method in class */
            public boolean m799a() {
                return this.f24613b == 0;
            }

            /* renamed from: a, reason: collision with other method in class */
            public void m798a(d dVar) {
                d[] dVarArr = this.f1103a;
                int length = dVarArr.length;
                int i2 = this.f24613b;
                if (length == i2) {
                    d[] dVarArr2 = new d[i2 * 2];
                    System.arraycopy(dVarArr, 0, dVarArr2, 0, i2);
                    this.f1103a = dVarArr2;
                }
                d[] dVarArr3 = this.f1103a;
                int i3 = this.f24613b;
                this.f24613b = i3 + 1;
                dVarArr3[i3] = dVar;
                c();
            }

            public void b() {
                int i2 = 0;
                while (i2 < this.f24613b) {
                    if (this.f1103a[i2].f1107a) {
                        this.f24614c++;
                        b(i2);
                        i2--;
                    }
                    i2++;
                }
            }

            private void c(int i2) {
                int i3 = (i2 * 2) + 1;
                while (true) {
                    int i4 = this.f24613b;
                    if (i3 >= i4 || i4 <= 0) {
                        return;
                    }
                    int i5 = i3 + 1;
                    if (i5 < i4) {
                        d[] dVarArr = this.f1103a;
                        if (dVarArr[i5].f1104a < dVarArr[i3].f1104a) {
                            i3 = i5;
                        }
                    }
                    d[] dVarArr2 = this.f1103a;
                    d dVar = dVarArr2[i2];
                    long j2 = dVar.f1104a;
                    d dVar2 = dVarArr2[i3];
                    if (j2 < dVar2.f1104a) {
                        return;
                    }
                    dVarArr2[i2] = dVar2;
                    dVarArr2[i3] = dVar;
                    int i6 = i3;
                    i3 = (i3 * 2) + 1;
                    i2 = i6;
                }
            }

            /* renamed from: a, reason: collision with other method in class */
            public boolean m800a(int i2) {
                for (int i3 = 0; i3 < this.f24613b; i3++) {
                    if (this.f1103a[i3].f24615a == i2) {
                        return true;
                    }
                }
                return false;
            }

            public void a(int i2) {
                for (int i3 = 0; i3 < this.f24613b; i3++) {
                    d dVar = this.f1103a[i3];
                    if (dVar.f24615a == i2) {
                        dVar.a();
                    }
                }
                b();
            }

            public void a(int i2, b bVar) {
                for (int i3 = 0; i3 < this.f24613b; i3++) {
                    d dVar = this.f1103a[i3];
                    if (dVar.f1105a == bVar) {
                        dVar.a();
                    }
                }
                b();
            }

            /* renamed from: a, reason: collision with other method in class */
            public void m797a() {
                this.f1103a = new d[this.f24612a];
                this.f24613b = 0;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public int a(d dVar) {
                int i2 = 0;
                while (true) {
                    d[] dVarArr = this.f1103a;
                    if (i2 >= dVarArr.length) {
                        return -1;
                    }
                    if (dVarArr[i2] == dVar) {
                        return i2;
                    }
                    i2++;
                }
            }
        }

        c(String str, boolean z2) {
            setName(str);
            setDaemon(z2);
            start();
        }

        /* JADX WARN: Code restructure failed: missing block: B:59:0x009a, code lost:
        
            r10.f24609a = android.os.SystemClock.uptimeMillis();
            r10.f1101a = true;
            r2.f1105a.run();
            r10.f1101a = false;
         */
        /* JADX WARN: Code restructure failed: missing block: B:61:0x00ab, code lost:
        
            r1 = move-exception;
         */
        /* JADX WARN: Code restructure failed: missing block: B:62:0x00ac, code lost:
        
            monitor-enter(r10);
         */
        /* JADX WARN: Code restructure failed: missing block: B:63:0x00ad, code lost:
        
            r10.f1102b = true;
         */
        /* JADX WARN: Code restructure failed: missing block: B:65:0x00b0, code lost:
        
            throw r1;
         */
        @Override // java.lang.Thread, java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void run() {
            /*
                Method dump skipped, instructions count: 186
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.service.q.c.run():void");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(d dVar) {
            this.f1100a.m798a(dVar);
            notify();
        }

        public synchronized void a() {
            this.f1102b = true;
            this.f1100a.m797a();
            notify();
        }

        /* renamed from: a, reason: collision with other method in class */
        public boolean m796a() {
            return this.f1101a && SystemClock.uptimeMillis() - this.f24609a > 600000;
        }
    }

    static {
        long jElapsedRealtime = SystemClock.elapsedRealtime() > 0 ? SystemClock.elapsedRealtime() : 0L;
        f24604a = jElapsedRealtime;
        f24605b = jElapsedRealtime;
    }

    public q(String str, boolean z2) {
        if (str == null) {
            throw new NullPointerException("name == null");
        }
        c cVar = new c(str, z2);
        this.f1099a = cVar;
        this.f1098a = new a(cVar);
    }

    static synchronized long a() {
        try {
            long jElapsedRealtime = SystemClock.elapsedRealtime();
            long j2 = f24605b;
            if (jElapsedRealtime > j2) {
                f24604a += jElapsedRealtime - j2;
            }
            f24605b = jElapsedRealtime;
        } catch (Throwable th) {
            throw th;
        }
        return f24604a;
    }

    private static synchronized long b() {
        long j2;
        j2 = f24606c;
        f24606c = 1 + j2;
        return j2;
    }

    /* renamed from: b, reason: collision with other method in class */
    public void m794b() {
        synchronized (this.f1099a) {
            this.f1099a.f1100a.m797a();
        }
    }

    static class d {

        /* renamed from: a, reason: collision with root package name */
        int f24615a;

        /* renamed from: a, reason: collision with other field name */
        long f1104a;

        /* renamed from: a, reason: collision with other field name */
        b f1105a;

        /* renamed from: a, reason: collision with other field name */
        final Object f1106a = new Object();

        /* renamed from: a, reason: collision with other field name */
        boolean f1107a;

        /* renamed from: b, reason: collision with root package name */
        private long f24616b;

        d() {
        }

        void a(long j2) {
            synchronized (this.f1106a) {
                this.f24616b = j2;
            }
        }

        public boolean a() {
            boolean z2;
            synchronized (this.f1106a) {
                try {
                    z2 = !this.f1107a && this.f1104a > 0;
                    this.f1107a = true;
                } catch (Throwable th) {
                    throw th;
                }
            }
            return z2;
        }
    }

    public q(String str) {
        this(str, false);
    }

    private void b(b bVar, long j2) {
        synchronized (this.f1099a) {
            try {
                if (!this.f1099a.f1102b) {
                    long jA = j2 + a();
                    if (jA >= 0) {
                        d dVar = new d();
                        dVar.f24615a = bVar.f24608a;
                        dVar.f1105a = bVar;
                        dVar.f1104a = jA;
                        this.f1099a.a(dVar);
                    } else {
                        throw new IllegalArgumentException("Illegal delay to start the TimerTask: " + jA);
                    }
                } else {
                    throw new IllegalStateException("Timer was canceled");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public q(boolean z2) {
        this("Timer-" + b(), z2);
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m791a() {
        com.xiaomi.channel.commonutils.logger.b.m91a("quit. finalizer:" + this.f1098a);
        this.f1099a.a();
    }

    public q() {
        this(false);
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m793a(int i2) {
        boolean zM800a;
        synchronized (this.f1099a) {
            zM800a = this.f1099a.f1100a.m800a(i2);
        }
        return zM800a;
    }

    public void a(int i2) {
        synchronized (this.f1099a) {
            this.f1099a.f1100a.a(i2);
        }
    }

    public void a(int i2, b bVar) {
        synchronized (this.f1099a) {
            this.f1099a.f1100a.a(i2, bVar);
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m792a() {
        return this.f1099a.m796a();
    }

    public void a(b bVar) {
        if (com.xiaomi.channel.commonutils.logger.b.a() < 1 && Thread.currentThread() != this.f1099a) {
            com.xiaomi.channel.commonutils.logger.b.d("run job outside job job thread");
            throw new RejectedExecutionException("Run job outside job thread");
        }
        bVar.run();
    }

    public void a(b bVar, long j2) {
        if (j2 >= 0) {
            b(bVar, j2);
            return;
        }
        throw new IllegalArgumentException("delay < 0: " + j2);
    }
}
