package com.tencent.bugly.proguard;

import android.os.Handler;
import android.os.SystemClock;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/* loaded from: classes4.dex */
public final class bf implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final Handler f20948a;

    /* renamed from: d, reason: collision with root package name */
    long f20951d;

    /* renamed from: e, reason: collision with root package name */
    private final String f20952e;

    /* renamed from: f, reason: collision with root package name */
    private final List<ba> f20953f = new LinkedList();

    /* renamed from: b, reason: collision with root package name */
    long f20949b = 5000;

    /* renamed from: g, reason: collision with root package name */
    private final long f20954g = 5000;

    /* renamed from: c, reason: collision with root package name */
    boolean f20950c = true;

    bf(Handler handler, String str) {
        this.f20948a = handler;
        this.f20952e = str;
    }

    private Thread e() {
        return this.f20948a.getLooper().getThread();
    }

    public final boolean a() {
        return !this.f20950c && SystemClock.uptimeMillis() >= this.f20951d + this.f20949b;
    }

    public final long b() {
        return SystemClock.uptimeMillis() - this.f20951d;
    }

    public final List<ba> c() {
        ArrayList arrayList;
        long jCurrentTimeMillis = System.currentTimeMillis();
        synchronized (this.f20953f) {
            try {
                arrayList = new ArrayList(this.f20953f.size());
                for (int i2 = 0; i2 < this.f20953f.size(); i2++) {
                    ba baVar = this.f20953f.get(i2);
                    if (!baVar.f20929e && jCurrentTimeMillis - baVar.f20926b < 200000) {
                        arrayList.add(baVar);
                        baVar.f20929e = true;
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return arrayList;
    }

    public final void d() {
        StringBuilder sb = new StringBuilder(1024);
        long jNanoTime = System.nanoTime();
        try {
            StackTraceElement[] stackTrace = e().getStackTrace();
            if (stackTrace.length == 0) {
                sb.append("Thread does not have stack trace.\n");
            } else {
                for (StackTraceElement stackTraceElement : stackTrace) {
                    sb.append(stackTraceElement);
                    sb.append("\n");
                }
            }
        } catch (SecurityException e2) {
            sb.append("getStackTrace() encountered:\n");
            sb.append(e2.getMessage());
            sb.append("\n");
            al.a(e2);
        }
        long jNanoTime2 = System.nanoTime();
        ba baVar = new ba(sb.toString(), System.currentTimeMillis());
        baVar.f20928d = jNanoTime2 - jNanoTime;
        String name = e().getName();
        if (name == null) {
            name = "";
        }
        baVar.f20925a = name;
        synchronized (this.f20953f) {
            while (this.f20953f.size() >= 32) {
                try {
                    this.f20953f.remove(0);
                } catch (Throwable th) {
                    throw th;
                }
            }
            this.f20953f.add(baVar);
        }
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.f20950c = true;
        this.f20949b = this.f20954g;
    }
}
