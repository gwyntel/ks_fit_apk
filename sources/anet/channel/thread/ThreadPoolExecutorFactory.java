package anet.channel.thread;

import anet.channel.util.ALog;
import com.huawei.hms.push.constant.RemoteMessageConst;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes2.dex */
public class ThreadPoolExecutorFactory {

    /* renamed from: a, reason: collision with root package name */
    private static ScheduledThreadPoolExecutor f7060a = new ScheduledThreadPoolExecutor(1, new b("AWCN Scheduler"));

    /* renamed from: b, reason: collision with root package name */
    private static ThreadPoolExecutor f7061b;

    /* renamed from: c, reason: collision with root package name */
    private static ThreadPoolExecutor f7062c;

    /* renamed from: d, reason: collision with root package name */
    private static ThreadPoolExecutor f7063d;

    /* renamed from: e, reason: collision with root package name */
    private static ThreadPoolExecutor f7064e;

    /* renamed from: f, reason: collision with root package name */
    private static ThreadPoolExecutor f7065f;

    /* renamed from: g, reason: collision with root package name */
    private static ThreadPoolExecutor f7066g;

    /* renamed from: h, reason: collision with root package name */
    private static ThreadPoolExecutor f7067h;

    public static class Priority {
        public static int HIGH = 0;
        public static int LOW = 9;
        public static int NORMAL = 1;
    }

    static class a implements Comparable<a>, Runnable {

        /* renamed from: a, reason: collision with root package name */
        Runnable f7068a;

        /* renamed from: b, reason: collision with root package name */
        int f7069b;

        /* renamed from: c, reason: collision with root package name */
        long f7070c;

        public a(Runnable runnable, int i2) {
            this.f7068a = null;
            this.f7069b = 0;
            this.f7070c = System.currentTimeMillis();
            this.f7068a = runnable;
            this.f7069b = i2;
            this.f7070c = System.currentTimeMillis();
        }

        @Override // java.lang.Comparable
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public int compareTo(a aVar) {
            int i2 = this.f7069b;
            int i3 = aVar.f7069b;
            return i2 != i3 ? i2 - i3 : (int) (aVar.f7070c - this.f7070c);
        }

        @Override // java.lang.Runnable
        public void run() {
            this.f7068a.run();
        }
    }

    private static class b implements ThreadFactory {

        /* renamed from: a, reason: collision with root package name */
        AtomicInteger f7071a = new AtomicInteger(0);

        /* renamed from: b, reason: collision with root package name */
        String f7072b;

        b(String str) {
            this.f7072b = str;
        }

        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable, this.f7072b + this.f7071a.incrementAndGet());
            ALog.i("awcn.ThreadPoolExecutorFactory", "thread created!", null, "name", thread.getName());
            thread.setPriority(5);
            return thread;
        }
    }

    static {
        TimeUnit timeUnit = TimeUnit.SECONDS;
        f7061b = new ThreadPoolExecutor(2, 2, 60L, timeUnit, new LinkedBlockingDeque(), new b("AWCN Worker(H)"));
        f7062c = new anet.channel.thread.a(16, 16, 60L, timeUnit, new PriorityBlockingQueue(), new b("AWCN Worker(M)"));
        f7063d = new ThreadPoolExecutor(2, 2, 60L, timeUnit, new LinkedBlockingDeque(), new b("AWCN Worker(L)"));
        f7064e = new ThreadPoolExecutor(32, 32, 60L, timeUnit, new LinkedBlockingDeque(), new b("AWCN Worker(Backup)"));
        f7065f = new ThreadPoolExecutor(1, 1, 30L, timeUnit, new LinkedBlockingDeque(), new b("AWCN Detector"));
        f7066g = new ThreadPoolExecutor(1, 1, 30L, timeUnit, new LinkedBlockingDeque(), new b("AWCN HR"));
        f7067h = new ThreadPoolExecutor(1, 1, 30L, timeUnit, new LinkedBlockingDeque(), new b("AWCN Cookie"));
        f7061b.allowCoreThreadTimeOut(true);
        f7062c.allowCoreThreadTimeOut(true);
        f7063d.allowCoreThreadTimeOut(true);
        f7064e.allowCoreThreadTimeOut(true);
        f7065f.allowCoreThreadTimeOut(true);
        f7066g.allowCoreThreadTimeOut(true);
        f7067h.allowCoreThreadTimeOut(true);
    }

    public static void removeScheduleTask(Runnable runnable) {
        f7060a.remove(runnable);
    }

    public static synchronized void setNormalExecutorPoolSize(int i2) {
        if (i2 < 6) {
            i2 = 6;
        }
        f7062c.setCorePoolSize(i2);
        f7062c.setMaximumPoolSize(i2);
    }

    public static Future<?> submitBackupTask(Runnable runnable) {
        return f7064e.submit(runnable);
    }

    public static Future<?> submitCookieMonitor(Runnable runnable) {
        return f7067h.submit(runnable);
    }

    public static Future<?> submitDetectTask(Runnable runnable) {
        return f7065f.submit(runnable);
    }

    public static Future<?> submitHRTask(Runnable runnable) {
        return f7066g.submit(runnable);
    }

    public static Future<?> submitPriorityTask(Runnable runnable, int i2) {
        if (ALog.isPrintLog(1)) {
            ALog.d("awcn.ThreadPoolExecutorFactory", "submit priority task", null, RemoteMessageConst.Notification.PRIORITY, Integer.valueOf(i2));
        }
        if (i2 < Priority.HIGH || i2 > Priority.LOW) {
            i2 = Priority.LOW;
        }
        return i2 == Priority.HIGH ? f7061b.submit(runnable) : i2 == Priority.LOW ? f7063d.submit(runnable) : f7062c.submit(new a(runnable, i2));
    }

    public static Future<?> submitScheduledTask(Runnable runnable) {
        return f7060a.submit(runnable);
    }

    public static Future<?> submitScheduledTask(Runnable runnable, long j2, TimeUnit timeUnit) {
        return f7060a.schedule(runnable, j2, timeUnit);
    }
}
