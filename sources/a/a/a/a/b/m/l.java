package a.a.a.a.b.m;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class l {

    /* renamed from: a, reason: collision with root package name */
    public static final b f1591a = new c();

    /* renamed from: b, reason: collision with root package name */
    public static volatile l f1592b;

    /* renamed from: c, reason: collision with root package name */
    public d f1593c = new d(2);

    /* renamed from: d, reason: collision with root package name */
    public d f1594d = new d(2);

    /* renamed from: e, reason: collision with root package name */
    public final Executor f1595e = new ThreadPoolExecutor(4, 8, 10, TimeUnit.SECONDS, new LinkedBlockingQueue(), new i("thread-pool", 10));

    public interface a<T> {
        T a(b bVar);
    }

    public interface b {
    }

    private static class c implements b {
        public c() {
        }
    }

    private static class d {

        /* renamed from: a, reason: collision with root package name */
        public int f1596a;

        public d(int i2) {
            this.f1596a = i2;
        }
    }

    public static l a() {
        if (f1592b == null) {
            synchronized (l.class) {
                try {
                    if (f1592b == null) {
                        f1592b = new l();
                    }
                } finally {
                }
            }
        }
        return f1592b;
    }

    private class e<T> implements Runnable, a.a.a.a.b.m.d<T>, b {

        /* renamed from: a, reason: collision with root package name */
        public a<T> f1597a;

        /* renamed from: b, reason: collision with root package name */
        public a.a.a.a.b.m.e<T> f1598b;

        /* renamed from: c, reason: collision with root package name */
        public d f1599c;

        /* renamed from: d, reason: collision with root package name */
        public volatile boolean f1600d;

        /* renamed from: e, reason: collision with root package name */
        public boolean f1601e;

        /* renamed from: f, reason: collision with root package name */
        public T f1602f;

        /* renamed from: g, reason: collision with root package name */
        public int f1603g;

        public e(a<T> aVar, a.a.a.a.b.m.e<T> eVar) {
            this.f1597a = aVar;
            this.f1598b = eVar;
        }

        public final d a(int i2) {
            if (i2 == 1) {
                return l.this.f1593c;
            }
            if (i2 == 2) {
                return l.this.f1594d;
            }
            return null;
        }

        public boolean b(int i2) {
            d dVarA = a(this.f1603g);
            if (dVarA != null) {
                b(dVarA);
            }
            this.f1603g = 0;
            d dVarA2 = a(i2);
            if (dVarA2 == null) {
                return true;
            }
            if (!a(dVarA2)) {
                return false;
            }
            this.f1603g = i2;
            return true;
        }

        @Override // java.lang.Runnable
        public void run() {
            T tA;
            if (b(1)) {
                try {
                    tA = this.f1597a.a(this);
                } catch (Throwable th) {
                    a.a.a.a.b.m.a.d("Worker", "Exception in running a job" + th);
                }
            } else {
                tA = null;
            }
            synchronized (this) {
                b(0);
                this.f1602f = tA;
                this.f1601e = true;
                notifyAll();
            }
            a.a.a.a.b.m.e<T> eVar = this.f1598b;
            if (eVar != null) {
                eVar.a(this);
            }
        }

        public final boolean a(d dVar) {
            while (true) {
                synchronized (this) {
                    try {
                        if (this.f1600d) {
                            this.f1599c = null;
                            return false;
                        }
                        this.f1599c = dVar;
                        synchronized (dVar) {
                            int i2 = dVar.f1596a;
                            if (i2 > 0) {
                                dVar.f1596a = i2 - 1;
                                synchronized (this) {
                                    this.f1599c = null;
                                }
                                return true;
                            }
                            try {
                                dVar.wait();
                            } catch (InterruptedException unused) {
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        }

        public final void b(d dVar) {
            synchronized (dVar) {
                dVar.f1596a++;
                dVar.notifyAll();
            }
        }
    }

    public <T> a.a.a.a.b.m.d<T> a(a<T> aVar, a.a.a.a.b.m.e<T> eVar) {
        e eVar2 = new e(aVar, eVar);
        this.f1595e.execute(eVar2);
        return eVar2;
    }

    public <T> a.a.a.a.b.m.d<T> a(a<T> aVar) {
        return a(aVar, null);
    }
}
