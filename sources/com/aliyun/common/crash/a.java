package com.aliyun.common.crash;

import android.os.Process;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicLong;

/* loaded from: classes2.dex */
public class a implements Thread.UncaughtExceptionHandler {

    /* renamed from: a, reason: collision with root package name */
    private Stack<Thread.UncaughtExceptionHandler> f11525a = new Stack<>();

    /* renamed from: b, reason: collision with root package name */
    private volatile int f11526b = -1;

    /* renamed from: c, reason: collision with root package name */
    private volatile int f11527c = -1;

    /* renamed from: d, reason: collision with root package name */
    private AtomicLong f11528d = new AtomicLong(0);

    private String a(Throwable th) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        do {
            th.printStackTrace(printWriter);
            th = th.getCause();
        } while (th != null);
        printWriter.close();
        return stringWriter.toString();
    }

    public void b() {
        this.f11526b = 0;
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public void uncaughtException(Thread thread, Throwable th) {
        Thread.UncaughtExceptionHandler uncaughtExceptionHandlerPop;
        long id = thread.getId();
        if (this.f11528d.compareAndSet(0L, id)) {
            this.f11527c = this.f11526b;
        } else if (this.f11528d.get() != id) {
            return;
        }
        if (this.f11527c > 0 || (this.f11527c == 0 && this.f11525a.size() <= 1)) {
            try {
                AlivcJavaCrash.nativeOnCrashCallback(Process.myPid(), thread.getId(), thread.getName(), a(th));
            } catch (Throwable th2) {
                th2.printStackTrace();
            }
            this.f11527c = -1;
        }
        if (this.f11525a.isEmpty() || (uncaughtExceptionHandlerPop = this.f11525a.pop()) == null) {
            Process.killProcess(Process.myPid());
        } else {
            uncaughtExceptionHandlerPop.uncaughtException(thread, th);
        }
    }

    public void a() {
        if (this.f11528d.get() > 0) {
            return;
        }
        this.f11526b = 1;
        Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        if (defaultUncaughtExceptionHandler == this) {
            return;
        }
        Thread.setDefaultUncaughtExceptionHandler(this);
        this.f11525a.push(defaultUncaughtExceptionHandler);
    }
}
