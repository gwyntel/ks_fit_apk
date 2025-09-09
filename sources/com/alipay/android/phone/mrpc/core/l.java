package com.alipay.android.phone.mrpc.core;

import android.content.Context;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
public final class l implements ab {

    /* renamed from: b, reason: collision with root package name */
    public static l f8999b;

    /* renamed from: i, reason: collision with root package name */
    public static final ThreadFactory f9000i = new n();

    /* renamed from: a, reason: collision with root package name */
    public Context f9001a;

    /* renamed from: c, reason: collision with root package name */
    public ThreadPoolExecutor f9002c;

    /* renamed from: d, reason: collision with root package name */
    public b f9003d = b.a("android");

    /* renamed from: e, reason: collision with root package name */
    public long f9004e;

    /* renamed from: f, reason: collision with root package name */
    public long f9005f;

    /* renamed from: g, reason: collision with root package name */
    public long f9006g;

    /* renamed from: h, reason: collision with root package name */
    public int f9007h;

    public l(Context context) {
        this.f9001a = context;
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 11, 3L, TimeUnit.SECONDS, new ArrayBlockingQueue(20), f9000i, new ThreadPoolExecutor.CallerRunsPolicy());
        this.f9002c = threadPoolExecutor;
        try {
            threadPoolExecutor.allowCoreThreadTimeOut(true);
        } catch (Exception unused) {
        }
        CookieSyncManager.createInstance(this.f9001a);
        CookieManager.getInstance().setAcceptCookie(true);
    }

    public static final synchronized l b(Context context) {
        l lVar = f8999b;
        if (lVar != null) {
            return lVar;
        }
        l lVar2 = new l(context);
        f8999b = lVar2;
        return lVar2;
    }

    public final b a() {
        return this.f9003d;
    }

    public final void c(long j2) {
        this.f9006g += j2;
    }

    public static final l a(Context context) {
        l lVar = f8999b;
        return lVar != null ? lVar : b(context);
    }

    public final void b(long j2) {
        this.f9005f += j2;
        this.f9007h++;
    }

    @Override // com.alipay.android.phone.mrpc.core.ab
    public final Future<u> a(t tVar) {
        if (s.a(this.f9001a)) {
            String str = "HttpManager" + hashCode() + ": Active Task = %d, Completed Task = %d, All Task = %d,Avarage Speed = %d KB/S, Connetct Time = %d ms, All data size = %d bytes, All enqueueConnect time = %d ms, All socket time = %d ms, All request times = %d times";
            Integer numValueOf = Integer.valueOf(this.f9002c.getActiveCount());
            Long lValueOf = Long.valueOf(this.f9002c.getCompletedTaskCount());
            Long lValueOf2 = Long.valueOf(this.f9002c.getTaskCount());
            long j2 = this.f9006g;
            Long lValueOf3 = Long.valueOf(j2 == 0 ? 0L : ((this.f9004e * 1000) / j2) >> 10);
            int i2 = this.f9007h;
            String.format(str, numValueOf, lValueOf, lValueOf2, lValueOf3, Long.valueOf(i2 != 0 ? this.f9005f / i2 : 0L), Long.valueOf(this.f9004e), Long.valueOf(this.f9005f), Long.valueOf(this.f9006g), Integer.valueOf(this.f9007h));
        }
        q qVar = new q(this, (o) tVar);
        m mVar = new m(this, qVar, qVar);
        this.f9002c.execute(mVar);
        return mVar;
    }

    public final void a(long j2) {
        this.f9004e += j2;
    }
}
