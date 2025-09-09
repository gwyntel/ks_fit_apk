package com.meizu.cloud.pushsdk.f.f.d;

import com.meizu.cloud.pushsdk.f.f.a;
import com.meizu.cloud.pushsdk.f.g.c;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/* loaded from: classes4.dex */
public class a extends com.meizu.cloud.pushsdk.f.f.a {

    /* renamed from: o, reason: collision with root package name */
    private static final String f19665o = "a";

    /* renamed from: p, reason: collision with root package name */
    private static ScheduledExecutorService f19666p;

    /* renamed from: com.meizu.cloud.pushsdk.f.f.d.a$a, reason: collision with other inner class name */
    class RunnableC0160a implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ com.meizu.cloud.pushsdk.f.f.b f19667a;

        RunnableC0160a(com.meizu.cloud.pushsdk.f.f.b bVar) {
            this.f19667a = bVar;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.f19667a.a();
        }
    }

    class b implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ com.meizu.cloud.pushsdk.f.d.b f19669a;

        /* renamed from: b, reason: collision with root package name */
        final /* synthetic */ boolean f19670b;

        b(com.meizu.cloud.pushsdk.f.d.b bVar, boolean z2) {
            this.f19669a = bVar;
            this.f19670b = z2;
        }

        @Override // java.lang.Runnable
        public void run() {
            a.super.a(this.f19669a, this.f19670b);
        }
    }

    public a(a.C0159a c0159a) {
        super(c0159a);
        com.meizu.cloud.pushsdk.f.c.h.b.a(this.f19633l);
        c();
    }

    @Override // com.meizu.cloud.pushsdk.f.f.a
    public void a(com.meizu.cloud.pushsdk.f.d.b bVar, boolean z2) {
        com.meizu.cloud.pushsdk.f.c.h.b.a(new b(bVar, z2));
    }

    public void c() {
        if (f19666p == null && this.f19631j) {
            c.a(f19665o, "Session checking has been resumed.", new Object[0]);
            com.meizu.cloud.pushsdk.f.f.b bVar = this.f19626e;
            ScheduledExecutorService scheduledExecutorServiceNewSingleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor();
            f19666p = scheduledExecutorServiceNewSingleThreadScheduledExecutor;
            RunnableC0160a runnableC0160a = new RunnableC0160a(bVar);
            long j2 = this.f19632k;
            scheduledExecutorServiceNewSingleThreadScheduledExecutor.scheduleAtFixedRate(runnableC0160a, j2, j2, this.f19634m);
        }
    }
}
