package com.meizu.cloud.pushsdk.d;

import java.lang.Thread;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/* loaded from: classes4.dex */
public final class j {

    /* renamed from: a, reason: collision with root package name */
    private String f19226a = null;

    /* renamed from: b, reason: collision with root package name */
    private Boolean f19227b = null;

    /* renamed from: c, reason: collision with root package name */
    private Integer f19228c = null;

    /* renamed from: d, reason: collision with root package name */
    private Thread.UncaughtExceptionHandler f19229d = null;

    /* renamed from: e, reason: collision with root package name */
    private ThreadFactory f19230e = null;

    class a implements ThreadFactory {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ ThreadFactory f19231a;

        /* renamed from: b, reason: collision with root package name */
        final /* synthetic */ String f19232b;

        /* renamed from: c, reason: collision with root package name */
        final /* synthetic */ AtomicLong f19233c;

        /* renamed from: d, reason: collision with root package name */
        final /* synthetic */ Boolean f19234d;

        /* renamed from: e, reason: collision with root package name */
        final /* synthetic */ Integer f19235e;

        /* renamed from: f, reason: collision with root package name */
        final /* synthetic */ Thread.UncaughtExceptionHandler f19236f;

        a(ThreadFactory threadFactory, String str, AtomicLong atomicLong, Boolean bool, Integer num, Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
            this.f19231a = threadFactory;
            this.f19232b = str;
            this.f19233c = atomicLong;
            this.f19234d = bool;
            this.f19235e = num;
            this.f19236f = uncaughtExceptionHandler;
        }

        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable runnable) {
            Thread threadNewThread = this.f19231a.newThread(runnable);
            String str = this.f19232b;
            if (str != null) {
                threadNewThread.setName(String.format(str, Long.valueOf(this.f19233c.getAndIncrement())));
            }
            Boolean bool = this.f19234d;
            if (bool != null) {
                threadNewThread.setDaemon(bool.booleanValue());
            }
            Integer num = this.f19235e;
            if (num != null) {
                threadNewThread.setPriority(num.intValue());
            }
            Thread.UncaughtExceptionHandler uncaughtExceptionHandler = this.f19236f;
            if (uncaughtExceptionHandler != null) {
                threadNewThread.setUncaughtExceptionHandler(uncaughtExceptionHandler);
            }
            return threadNewThread;
        }
    }

    public j a(String str) {
        String.format(str, 0);
        this.f19226a = str;
        return this;
    }

    public ThreadFactory a() {
        return a(this);
    }

    private static ThreadFactory a(j jVar) {
        String str = jVar.f19226a;
        Boolean bool = jVar.f19227b;
        Integer num = jVar.f19228c;
        Thread.UncaughtExceptionHandler uncaughtExceptionHandler = jVar.f19229d;
        ThreadFactory threadFactoryDefaultThreadFactory = jVar.f19230e;
        if (threadFactoryDefaultThreadFactory == null) {
            threadFactoryDefaultThreadFactory = Executors.defaultThreadFactory();
        }
        return new a(threadFactoryDefaultThreadFactory, str, str != null ? new AtomicLong(0L) : null, bool, num, uncaughtExceptionHandler);
    }
}
