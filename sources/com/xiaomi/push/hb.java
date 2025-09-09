package com.xiaomi.push;

import android.os.SystemClock;
import android.util.Pair;
import com.xiaomi.push.service.XMPushService;
import com.xiaomi.push.service.bf;
import java.io.Reader;
import java.io.Writer;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes4.dex */
public abstract class hb {

    /* renamed from: a, reason: collision with root package name */
    private static final AtomicInteger f23861a = new AtomicInteger(0);

    /* renamed from: a, reason: collision with other field name */
    public static boolean f516a;

    /* renamed from: a, reason: collision with other field name */
    protected hc f519a;

    /* renamed from: a, reason: collision with other field name */
    protected XMPushService f521a;

    /* renamed from: a, reason: collision with other field name */
    protected int f517a = 0;

    /* renamed from: a, reason: collision with other field name */
    protected long f518a = -1;

    /* renamed from: b, reason: collision with other field name */
    protected volatile long f526b = 0;

    /* renamed from: c, reason: collision with other field name */
    protected volatile long f529c = 0;

    /* renamed from: a, reason: collision with other field name */
    private LinkedList<Pair<Integer, Long>> f524a = new LinkedList<>();

    /* renamed from: a, reason: collision with other field name */
    private final Collection<he> f523a = new CopyOnWriteArrayList();

    /* renamed from: a, reason: collision with other field name */
    protected final Map<hg, a> f525a = new ConcurrentHashMap();

    /* renamed from: b, reason: collision with other field name */
    protected final Map<hg, a> f528b = new ConcurrentHashMap();

    /* renamed from: a, reason: collision with other field name */
    protected hn f520a = null;

    /* renamed from: a, reason: collision with other field name */
    protected String f522a = "";

    /* renamed from: b, reason: collision with other field name */
    protected String f527b = "";

    /* renamed from: c, reason: collision with root package name */
    private int f23863c = 2;

    /* renamed from: b, reason: collision with root package name */
    protected final int f23862b = f23861a.getAndIncrement();

    /* renamed from: e, reason: collision with root package name */
    private long f23865e = 0;

    /* renamed from: d, reason: collision with root package name */
    protected long f23864d = 0;

    static {
        f516a = false;
        try {
            f516a = Boolean.getBoolean("smack.debugEnabled");
        } catch (Exception unused) {
        }
        hh.m477a();
    }

    protected hb(XMPushService xMPushService, hc hcVar) throws ClassNotFoundException {
        this.f519a = hcVar;
        this.f521a = xMPushService;
        m471b();
    }

    public abstract void a(hs hsVar);

    public abstract void a(bf.b bVar);

    public abstract void a(String str, String str2);

    public abstract void a(gq[] gqVarArr);

    /* renamed from: a */
    public boolean mo461a() {
        return false;
    }

    /* renamed from: b, reason: collision with other method in class */
    public String m470b() {
        return this.f519a.b();
    }

    public abstract void b(int i2, Exception exc);

    public abstract void b(gq gqVar);

    public abstract void b(boolean z2);

    /* renamed from: c, reason: collision with other method in class */
    public boolean m473c() {
        return this.f23863c == 1;
    }

    public void d() {
        synchronized (this.f524a) {
            this.f524a.clear();
        }
    }

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        private hg f23866a;

        /* renamed from: a, reason: collision with other field name */
        private ho f530a;

        public a(hg hgVar, ho hoVar) {
            this.f23866a = hgVar;
            this.f530a = hoVar;
        }

        public void a(hs hsVar) {
            ho hoVar = this.f530a;
            if (hoVar == null || hoVar.mo281a(hsVar)) {
                this.f23866a.a(hsVar);
            }
        }

        public void a(gq gqVar) {
            this.f23866a.a(gqVar);
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public hc m467a() {
        return this.f519a;
    }

    public void b(he heVar) {
        this.f523a.remove(heVar);
    }

    public synchronized void c() {
        this.f23865e = SystemClock.elapsedRealtime();
    }

    /* renamed from: a, reason: collision with other method in class */
    public String mo468a() {
        return this.f519a.c();
    }

    public void b(hg hgVar, ho hoVar) {
        if (hgVar != null) {
            this.f528b.put(hgVar, new a(hgVar, hoVar));
            return;
        }
        throw new NullPointerException("Packet listener is null.");
    }

    /* renamed from: a, reason: collision with other method in class */
    public long m466a() {
        return this.f529c;
    }

    public void a(he heVar) {
        if (heVar == null || this.f523a.contains(heVar)) {
            return;
        }
        this.f523a.add(heVar);
    }

    public void b(hg hgVar) {
        this.f528b.remove(hgVar);
    }

    public void a(hg hgVar, ho hoVar) {
        if (hgVar != null) {
            this.f525a.put(hgVar, new a(hgVar, hoVar));
            return;
        }
        throw new NullPointerException("Packet listener is null.");
    }

    /* renamed from: b, reason: collision with other method in class */
    protected void m471b() throws ClassNotFoundException {
        String property;
        if (this.f519a.m475a() && this.f520a == null) {
            Class<?> cls = null;
            try {
                property = System.getProperty("smack.debuggerClass");
            } catch (Throwable unused) {
                property = null;
            }
            if (property != null) {
                try {
                    cls = Class.forName(property);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            if (cls == null) {
                this.f520a = new gz(this);
                return;
            }
            try {
                this.f520a = (hn) cls.getConstructor(hb.class, Writer.class, Reader.class).newInstance(this);
            } catch (Exception e3) {
                throw new IllegalArgumentException("Can't initialize the configured debugger!", e3);
            }
        }
    }

    public void a(hg hgVar) {
        this.f525a.remove(hgVar);
    }

    /* renamed from: a, reason: collision with other method in class */
    protected Map<hg, a> m469a() {
        return this.f525a;
    }

    public int a() {
        return this.f517a;
    }

    public void a(int i2, int i3, Exception exc) {
        int i4 = this.f23863c;
        if (i2 != i4) {
            com.xiaomi.channel.commonutils.logger.b.m91a(String.format("update the connection status. %1$s -> %2$s : %3$s ", a(i4), a(i2), com.xiaomi.push.service.bj.a(i3)));
        }
        if (bg.b(this.f521a)) {
            m465a(i2);
        }
        if (i2 == 1) {
            this.f521a.a(10);
            if (this.f23863c != 0) {
                com.xiaomi.channel.commonutils.logger.b.m91a("try set connected while not connecting.");
            }
            this.f23863c = i2;
            Iterator<he> it = this.f523a.iterator();
            while (it.hasNext()) {
                it.next().b(this);
            }
            return;
        }
        if (i2 == 0) {
            if (this.f23863c != 2) {
                com.xiaomi.channel.commonutils.logger.b.m91a("try set connecting while not disconnected.");
            }
            this.f23863c = i2;
            Iterator<he> it2 = this.f523a.iterator();
            while (it2.hasNext()) {
                it2.next().a(this);
            }
            return;
        }
        if (i2 == 2) {
            this.f521a.a(10);
            int i5 = this.f23863c;
            if (i5 == 0) {
                Iterator<he> it3 = this.f523a.iterator();
                while (it3.hasNext()) {
                    it3.next().a(this, exc == null ? new CancellationException("disconnect while connecting") : exc);
                }
            } else if (i5 == 1) {
                Iterator<he> it4 = this.f523a.iterator();
                while (it4.hasNext()) {
                    it4.next().a(this, i3, exc);
                }
            }
            this.f23863c = i2;
        }
    }

    /* renamed from: b, reason: collision with other method in class */
    public boolean m472b() {
        return this.f23863c == 0;
    }

    public int b() {
        return this.f23863c;
    }

    private String a(int i2) {
        if (i2 == 1) {
            return "connected";
        }
        if (i2 == 0) {
            return "connecting";
        }
        if (i2 == 2) {
            return "disconnected";
        }
        return "unknown";
    }

    public synchronized void a(String str) {
        try {
            if (this.f23863c == 0) {
                com.xiaomi.channel.commonutils.logger.b.m91a("setChallenge hash = " + bo.a(str).substring(0, 8));
                this.f522a = str;
                a(1, 0, null);
            } else {
                com.xiaomi.channel.commonutils.logger.b.m91a("ignore setChallenge because connection was disconnected");
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public synchronized boolean a(long j2) {
        return this.f23865e >= j2;
    }

    /* renamed from: a, reason: collision with other method in class */
    private void m465a(int i2) {
        synchronized (this.f524a) {
            try {
                if (i2 == 1) {
                    this.f524a.clear();
                } else {
                    this.f524a.add(new Pair<>(Integer.valueOf(i2), Long.valueOf(System.currentTimeMillis())));
                    if (this.f524a.size() > 6) {
                        this.f524a.remove(0);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
