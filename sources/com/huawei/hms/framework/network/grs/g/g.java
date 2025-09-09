package com.huawei.hms.framework.network.grs.g;

import com.huawei.hms.framework.common.ExecutorsUtils;
import com.huawei.hms.framework.common.Logger;
import com.huawei.hms.framework.common.NetworkUtil;
import com.huawei.hms.framework.network.grs.h.d;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* loaded from: classes4.dex */
public class g {

    /* renamed from: b, reason: collision with root package name */
    private static final ExecutorService f16270b = ExecutorsUtils.newCachedThreadPool("GRS_RequestController-Task");

    /* renamed from: c, reason: collision with root package name */
    private static final Map<String, com.huawei.hms.framework.network.grs.g.j.b> f16271c = new ConcurrentHashMap(16);

    /* renamed from: d, reason: collision with root package name */
    private static final Object f16272d = new Object();

    /* renamed from: a, reason: collision with root package name */
    private com.huawei.hms.framework.network.grs.e.a f16273a;

    class a implements Callable<d> {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ com.huawei.hms.framework.network.grs.g.j.c f16274a;

        /* renamed from: b, reason: collision with root package name */
        final /* synthetic */ String f16275b;

        /* renamed from: c, reason: collision with root package name */
        final /* synthetic */ com.huawei.hms.framework.network.grs.e.c f16276c;

        a(com.huawei.hms.framework.network.grs.g.j.c cVar, String str, com.huawei.hms.framework.network.grs.e.c cVar2) {
            this.f16274a = cVar;
            this.f16275b = str;
            this.f16276c = cVar2;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.concurrent.Callable
        public d call() {
            return new c(this.f16274a, g.this.f16273a).a(g.f16270b, this.f16275b, this.f16276c);
        }
    }

    class b implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ com.huawei.hms.framework.network.grs.g.j.c f16278a;

        /* renamed from: b, reason: collision with root package name */
        final /* synthetic */ String f16279b;

        /* renamed from: c, reason: collision with root package name */
        final /* synthetic */ com.huawei.hms.framework.network.grs.e.c f16280c;

        /* renamed from: d, reason: collision with root package name */
        final /* synthetic */ int f16281d;

        /* renamed from: e, reason: collision with root package name */
        final /* synthetic */ com.huawei.hms.framework.network.grs.b f16282e;

        b(com.huawei.hms.framework.network.grs.g.j.c cVar, String str, com.huawei.hms.framework.network.grs.e.c cVar2, int i2, com.huawei.hms.framework.network.grs.b bVar) {
            this.f16278a = cVar;
            this.f16279b = str;
            this.f16280c = cVar2;
            this.f16281d = i2;
            this.f16282e = bVar;
        }

        @Override // java.lang.Runnable
        public void run() {
            g gVar = g.this;
            gVar.a(gVar.a(this.f16278a, this.f16279b, this.f16280c, this.f16281d), this.f16282e);
        }
    }

    public d a(com.huawei.hms.framework.network.grs.g.j.c cVar, String str, com.huawei.hms.framework.network.grs.e.c cVar2, int i2) {
        Future<d> futureSubmit;
        String str2;
        String str3;
        Logger.d("RequestController", "request to server with service name is: " + str);
        String grsParasKey = cVar.b().getGrsParasKey(true, true, cVar.a());
        Logger.v("RequestController", "request spUrlKey: " + grsParasKey);
        synchronized (f16272d) {
            try {
                if (!NetworkUtil.isNetworkAvailable(cVar.a())) {
                    return null;
                }
                d.a aVarA = com.huawei.hms.framework.network.grs.h.d.a(grsParasKey);
                Map<String, com.huawei.hms.framework.network.grs.g.j.b> map = f16271c;
                com.huawei.hms.framework.network.grs.g.j.b bVar = map.get(grsParasKey);
                if (bVar == null || !bVar.b()) {
                    if (aVarA != null && aVarA.a()) {
                        return null;
                    }
                    Logger.d("RequestController", "hitGrsRequestBean == null or request block is released.");
                    futureSubmit = f16270b.submit(new a(cVar, str, cVar2));
                    map.put(grsParasKey, new com.huawei.hms.framework.network.grs.g.j.b(futureSubmit));
                } else {
                    futureSubmit = bVar.a();
                }
                if (i2 == -1) {
                    com.huawei.hms.framework.network.grs.g.j.d dVarA = com.huawei.hms.framework.network.grs.g.i.a.a(cVar.a());
                    i2 = dVarA != null ? dVarA.c() : 10;
                }
                Logger.i("RequestController", "use grsQueryTimeout %d", Integer.valueOf(i2));
                try {
                    return futureSubmit.get(i2, TimeUnit.SECONDS);
                } catch (InterruptedException e2) {
                    e = e2;
                    str2 = "RequestController";
                    str3 = "when check result, find InterruptedException, check others";
                    Logger.w(str2, str3, e);
                    return null;
                } catch (CancellationException e3) {
                    e = e3;
                    str2 = "RequestController";
                    str3 = "when check result, find CancellationException, check others";
                    Logger.w(str2, str3, e);
                    return null;
                } catch (ExecutionException e4) {
                    e = e4;
                    str2 = "RequestController";
                    str3 = "when check result, find ExecutionException, check others";
                    Logger.w(str2, str3, e);
                    return null;
                } catch (TimeoutException e5) {
                    e = e5;
                    str2 = "RequestController";
                    str3 = "when check result, find TimeoutException, check others";
                    Logger.w(str2, str3, e);
                    return null;
                } catch (Exception e6) {
                    e = e6;
                    str2 = "RequestController";
                    str3 = "when check result, find Other Exception, check others";
                    Logger.w(str2, str3, e);
                    return null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void a(com.huawei.hms.framework.network.grs.e.a aVar) {
        this.f16273a = aVar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(d dVar, com.huawei.hms.framework.network.grs.b bVar) {
        if (bVar != null) {
            if (dVar == null) {
                Logger.v("RequestController", "GrsResponse is null");
                bVar.a();
            } else {
                Logger.v("RequestController", "GrsResponse is not null");
                bVar.a(dVar);
            }
        }
    }

    public void a(com.huawei.hms.framework.network.grs.g.j.c cVar, com.huawei.hms.framework.network.grs.b bVar, String str, com.huawei.hms.framework.network.grs.e.c cVar2, int i2) {
        f16270b.execute(new b(cVar, str, cVar2, i2, bVar));
    }

    public void a(String str) {
        synchronized (f16272d) {
            f16271c.remove(str);
        }
    }
}
