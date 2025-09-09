package com.meizu.cloud.pushsdk.f.f;

import android.content.Context;
import com.meizu.cloud.pushsdk.PushManager;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes4.dex */
public abstract class a {

    /* renamed from: a, reason: collision with root package name */
    private static final String f19622a = "a";

    /* renamed from: c, reason: collision with root package name */
    protected com.meizu.cloud.pushsdk.f.c.a f19624c;

    /* renamed from: d, reason: collision with root package name */
    protected c f19625d;

    /* renamed from: e, reason: collision with root package name */
    protected b f19626e;

    /* renamed from: f, reason: collision with root package name */
    protected final String f19627f;

    /* renamed from: g, reason: collision with root package name */
    protected final String f19628g;

    /* renamed from: h, reason: collision with root package name */
    protected final boolean f19629h;

    /* renamed from: i, reason: collision with root package name */
    protected final com.meizu.cloud.pushsdk.f.g.b f19630i;

    /* renamed from: j, reason: collision with root package name */
    protected final boolean f19631j;

    /* renamed from: k, reason: collision with root package name */
    protected final long f19632k;

    /* renamed from: l, reason: collision with root package name */
    protected final int f19633l;

    /* renamed from: m, reason: collision with root package name */
    protected final TimeUnit f19634m;

    /* renamed from: b, reason: collision with root package name */
    protected final String f19623b = PushManager.TAG;

    /* renamed from: n, reason: collision with root package name */
    protected final AtomicBoolean f19635n = new AtomicBoolean(true);

    /* renamed from: com.meizu.cloud.pushsdk.f.f.a$a, reason: collision with other inner class name */
    public static class C0159a {

        /* renamed from: a, reason: collision with root package name */
        protected final com.meizu.cloud.pushsdk.f.c.a f19636a;

        /* renamed from: b, reason: collision with root package name */
        protected final String f19637b;

        /* renamed from: c, reason: collision with root package name */
        protected final String f19638c;

        /* renamed from: d, reason: collision with root package name */
        protected final Context f19639d;

        /* renamed from: e, reason: collision with root package name */
        protected c f19640e = null;

        /* renamed from: f, reason: collision with root package name */
        protected boolean f19641f = false;

        /* renamed from: g, reason: collision with root package name */
        protected com.meizu.cloud.pushsdk.f.g.b f19642g = com.meizu.cloud.pushsdk.f.g.b.OFF;

        /* renamed from: h, reason: collision with root package name */
        protected boolean f19643h = false;

        /* renamed from: i, reason: collision with root package name */
        protected long f19644i = 600;

        /* renamed from: j, reason: collision with root package name */
        protected long f19645j = 300;

        /* renamed from: k, reason: collision with root package name */
        protected long f19646k = 15;

        /* renamed from: l, reason: collision with root package name */
        protected int f19647l = 10;

        /* renamed from: m, reason: collision with root package name */
        protected TimeUnit f19648m = TimeUnit.SECONDS;

        public C0159a(com.meizu.cloud.pushsdk.f.c.a aVar, String str, String str2, Context context, Class<? extends a> cls) {
            this.f19636a = aVar;
            this.f19637b = str;
            this.f19638c = str2;
            this.f19639d = context;
        }

        public C0159a a(int i2) {
            this.f19647l = i2;
            return this;
        }

        public C0159a a(c cVar) {
            this.f19640e = cVar;
            return this;
        }

        public C0159a a(com.meizu.cloud.pushsdk.f.g.b bVar) {
            this.f19642g = bVar;
            return this;
        }

        public C0159a a(Boolean bool) {
            this.f19641f = bool.booleanValue();
            return this;
        }
    }

    public a(C0159a c0159a) {
        this.f19624c = c0159a.f19636a;
        this.f19628g = c0159a.f19638c;
        this.f19629h = c0159a.f19641f;
        this.f19627f = c0159a.f19637b;
        this.f19625d = c0159a.f19640e;
        this.f19630i = c0159a.f19642g;
        boolean z2 = c0159a.f19643h;
        this.f19631j = z2;
        this.f19632k = c0159a.f19646k;
        int i2 = c0159a.f19647l;
        this.f19633l = i2 < 2 ? 2 : i2;
        this.f19634m = c0159a.f19648m;
        if (z2) {
            this.f19626e = new b(c0159a.f19644i, c0159a.f19645j, c0159a.f19648m, c0159a.f19639d);
        }
        com.meizu.cloud.pushsdk.f.g.c.a(c0159a.f19642g);
        com.meizu.cloud.pushsdk.f.g.c.c(f19622a, "Tracker created successfully.", new Object[0]);
    }

    private com.meizu.cloud.pushsdk.f.b.b a(List<com.meizu.cloud.pushsdk.f.b.b> list) {
        if (this.f19631j) {
            list.add(this.f19626e.b());
        }
        c cVar = this.f19625d;
        if (cVar != null) {
            if (!cVar.b().isEmpty()) {
                list.add(new com.meizu.cloud.pushsdk.f.b.b("geolocation", this.f19625d.b()));
            }
            if (!this.f19625d.c().isEmpty()) {
                list.add(new com.meizu.cloud.pushsdk.f.b.b("mobileinfo", this.f19625d.c()));
            }
        }
        LinkedList linkedList = new LinkedList();
        Iterator<com.meizu.cloud.pushsdk.f.b.b> it = list.iterator();
        while (it.hasNext()) {
            linkedList.add(it.next().a());
        }
        return new com.meizu.cloud.pushsdk.f.b.b("push_extra_info", linkedList);
    }

    public void b() {
        if (this.f19635n.get()) {
            a().b();
        }
    }

    public com.meizu.cloud.pushsdk.f.c.a a() {
        return this.f19624c;
    }

    private void a(com.meizu.cloud.pushsdk.f.b.c cVar, List<com.meizu.cloud.pushsdk.f.b.b> list, boolean z2) {
        if (this.f19625d != null) {
            cVar.a(new HashMap(this.f19625d.a()));
            cVar.a("et", a(list).a());
        }
        com.meizu.cloud.pushsdk.f.g.c.c(f19622a, "Adding new payload to event storage: %s", cVar);
        this.f19624c.a(cVar, z2);
    }

    public void a(com.meizu.cloud.pushsdk.f.d.b bVar, boolean z2) {
        if (this.f19635n.get()) {
            a(bVar.e(), bVar.b(), z2);
        }
    }

    public void a(c cVar) {
        this.f19625d = cVar;
    }
}
