package com.xiaomi.push;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ah {

    /* renamed from: a, reason: collision with root package name */
    private static volatile ah f23433a;

    /* renamed from: a, reason: collision with other field name */
    private SharedPreferences f163a;

    /* renamed from: a, reason: collision with other field name */
    private ScheduledThreadPoolExecutor f166a = new ScheduledThreadPoolExecutor(1);

    /* renamed from: a, reason: collision with other field name */
    private Map<String, ScheduledFuture> f165a = new HashMap();

    /* renamed from: a, reason: collision with other field name */
    private Object f164a = new Object();

    public static abstract class a implements Runnable {
        /* renamed from: a */
        public abstract String mo224a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class b implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        a f23434a;

        public b(a aVar) {
            this.f23434a = aVar;
        }

        void a() {
        }

        void b() {
        }

        @Override // java.lang.Runnable
        public void run() {
            a();
            this.f23434a.run();
            b();
        }
    }

    private ah(Context context) {
        this.f163a = context.getSharedPreferences("mipush_extra", 0);
    }

    public boolean b(a aVar, int i2) {
        if (aVar == null || a(aVar) != null) {
            return false;
        }
        ScheduledFuture<?> scheduledFutureSchedule = this.f166a.schedule(new aj(this, aVar), i2, TimeUnit.SECONDS);
        synchronized (this.f164a) {
            this.f165a.put(aVar.mo224a(), scheduledFutureSchedule);
        }
        return true;
    }

    public static ah a(Context context) {
        if (f23433a == null) {
            synchronized (ah.class) {
                try {
                    if (f23433a == null) {
                        f23433a = new ah(context);
                    }
                } finally {
                }
            }
        }
        return f23433a;
    }

    public boolean a(a aVar, int i2) {
        return a(aVar, i2, 0);
    }

    public boolean a(a aVar, int i2, int i3) {
        return a(aVar, i2, i3, false);
    }

    public boolean a(a aVar, int i2, int i3, boolean z2) {
        if (aVar == null || a(aVar) != null) {
            return false;
        }
        String strA = a(aVar.mo224a());
        ai aiVar = new ai(this, aVar, z2, strA);
        if (!z2) {
            long jAbs = Math.abs(System.currentTimeMillis() - this.f163a.getLong(strA, 0L)) / 1000;
            if (jAbs < i2 - i3) {
                i3 = (int) (i2 - jAbs);
            }
        }
        try {
            ScheduledFuture<?> scheduledFutureScheduleAtFixedRate = this.f166a.scheduleAtFixedRate(aiVar, i3, i2, TimeUnit.SECONDS);
            synchronized (this.f164a) {
                this.f165a.put(aVar.mo224a(), scheduledFutureScheduleAtFixedRate);
            }
            return true;
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
            return true;
        }
    }

    public void a(Runnable runnable) {
        a(runnable, 0);
    }

    public void a(Runnable runnable, int i2) {
        this.f166a.schedule(runnable, i2, TimeUnit.SECONDS);
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m173a(a aVar) {
        return b(aVar, 0);
    }

    private ScheduledFuture a(a aVar) {
        ScheduledFuture scheduledFuture;
        synchronized (this.f164a) {
            scheduledFuture = this.f165a.get(aVar.mo224a());
        }
        return scheduledFuture;
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m174a(String str) {
        synchronized (this.f164a) {
            try {
                ScheduledFuture scheduledFuture = this.f165a.get(str);
                if (scheduledFuture == null) {
                    return false;
                }
                this.f165a.remove(str);
                return scheduledFuture.cancel(false);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private static String a(String str) {
        return "last_job_time" + str;
    }
}
