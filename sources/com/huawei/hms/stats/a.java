package com.huawei.hms.stats;

import android.os.Handler;
import android.os.Looper;
import com.huawei.hms.support.hianalytics.HiAnalyticsUtils;
import com.huawei.hms.support.log.HMSLog;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class a {

    /* renamed from: f, reason: collision with root package name */
    private static final a f18073f = new a();

    /* renamed from: a, reason: collision with root package name */
    private final Object f18074a = new Object();

    /* renamed from: b, reason: collision with root package name */
    private boolean f18075b = false;

    /* renamed from: c, reason: collision with root package name */
    private final List<Runnable> f18076c = new ArrayList();

    /* renamed from: d, reason: collision with root package name */
    private final Handler f18077d = new Handler(Looper.getMainLooper());

    /* renamed from: e, reason: collision with root package name */
    private final Runnable f18078e = new RunnableC0138a();

    /* renamed from: com.huawei.hms.stats.a$a, reason: collision with other inner class name */
    class RunnableC0138a implements Runnable {
        RunnableC0138a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            HMSLog.i("AnalyticsCacheManager", "Timeout execCacheBi.");
            if (HiAnalyticsUtils.getInstance().getInitFlag()) {
                a.this.b();
            } else {
                a.this.a();
            }
        }
    }

    private a() {
    }

    public static a c() {
        return f18073f;
    }

    public void a(Runnable runnable) {
        synchronized (this.f18074a) {
            try {
                if (runnable == null) {
                    return;
                }
                if (this.f18075b) {
                    return;
                }
                if (this.f18076c.size() >= 60) {
                    return;
                }
                this.f18076c.add(runnable);
                this.f18077d.removeCallbacks(this.f18078e);
                this.f18077d.postDelayed(this.f18078e, 10000L);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void b() {
        synchronized (this.f18074a) {
            HMSLog.i("AnalyticsCacheManager", "execCacheBi: cache size: " + this.f18076c.size());
            this.f18075b = true;
            try {
                Iterator<Runnable> it = this.f18076c.iterator();
                while (it.hasNext()) {
                    it.next().run();
                    it.remove();
                }
            } catch (Throwable th) {
                HMSLog.e("AnalyticsCacheManager", "<execCacheBi> failed. " + th.getMessage());
                a();
            }
            this.f18075b = false;
        }
    }

    public void a() {
        synchronized (this.f18074a) {
            HMSLog.i("AnalyticsCacheManager", "clear AnalyticsCache.");
            this.f18076c.clear();
        }
    }
}
