package com.meizu.cloud.pushsdk.d.m;

import java.lang.Thread;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/* loaded from: classes4.dex */
public final class d {

    /* renamed from: a, reason: collision with root package name */
    private String f19265a = null;

    /* renamed from: b, reason: collision with root package name */
    private Boolean f19266b = null;

    /* renamed from: c, reason: collision with root package name */
    private Integer f19267c = null;

    /* renamed from: d, reason: collision with root package name */
    private Thread.UncaughtExceptionHandler f19268d = null;

    /* renamed from: e, reason: collision with root package name */
    private ThreadFactory f19269e = null;

    class a implements ThreadFactory {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ ThreadFactory f19270a;

        /* renamed from: b, reason: collision with root package name */
        final /* synthetic */ String f19271b;

        /* renamed from: c, reason: collision with root package name */
        final /* synthetic */ AtomicLong f19272c;

        /* renamed from: d, reason: collision with root package name */
        final /* synthetic */ Boolean f19273d;

        /* renamed from: e, reason: collision with root package name */
        final /* synthetic */ Integer f19274e;

        /* renamed from: f, reason: collision with root package name */
        final /* synthetic */ Thread.UncaughtExceptionHandler f19275f;

        a(ThreadFactory threadFactory, String str, AtomicLong atomicLong, Boolean bool, Integer num, Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
            this.f19270a = threadFactory;
            this.f19271b = str;
            this.f19272c = atomicLong;
            this.f19273d = bool;
            this.f19274e = num;
            this.f19275f = uncaughtExceptionHandler;
        }

        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable runnable) {
            Thread threadNewThread = this.f19270a.newThread(runnable);
            String str = this.f19271b;
            if (str != null) {
                threadNewThread.setName(String.format(str, Long.valueOf(this.f19272c.getAndIncrement())));
            }
            Boolean bool = this.f19273d;
            if (bool != null) {
                threadNewThread.setDaemon(bool.booleanValue());
            }
            Integer num = this.f19274e;
            if (num != null) {
                threadNewThread.setPriority(num.intValue());
            }
            Thread.UncaughtExceptionHandler uncaughtExceptionHandler = this.f19275f;
            if (uncaughtExceptionHandler != null) {
                threadNewThread.setUncaughtExceptionHandler(uncaughtExceptionHandler);
            }
            return threadNewThread;
        }
    }

    public d a(Integer num) {
        this.f19267c = num;
        return this;
    }

    public d a(String str) {
        String.format(str, 0);
        this.f19265a = str;
        return this;
    }

    public ThreadFactory a() {
        return a(this);
    }

    private static ThreadFactory a(d dVar) {
        String str = dVar.f19265a;
        Boolean bool = dVar.f19266b;
        Integer num = dVar.f19267c;
        Thread.UncaughtExceptionHandler uncaughtExceptionHandler = dVar.f19268d;
        ThreadFactory threadFactoryDefaultThreadFactory = dVar.f19269e;
        if (threadFactoryDefaultThreadFactory == null) {
            threadFactoryDefaultThreadFactory = Executors.defaultThreadFactory();
        }
        return new a(threadFactoryDefaultThreadFactory, str, str != null ? new AtomicLong(0L) : null, bool, num, uncaughtExceptionHandler);
    }
}
