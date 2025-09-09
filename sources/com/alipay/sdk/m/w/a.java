package com.alipay.sdk.m.w;

import android.content.Context;
import android.os.SystemClock;
import android.util.Pair;
import com.alipay.sdk.m.u.e;
import com.alipay.sdk.m.u.n;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    public static final String f9829a = "CDT";

    /* renamed from: b, reason: collision with root package name */
    public static final int f9830b = 1;

    /* renamed from: c, reason: collision with root package name */
    public static final int f9831c = 2;

    /* renamed from: d, reason: collision with root package name */
    public static final int f9832d = 3;

    /* renamed from: e, reason: collision with root package name */
    public static final int f9833e = 4;

    /* renamed from: f, reason: collision with root package name */
    public static final int f9834f = 5;

    /* renamed from: g, reason: collision with root package name */
    public static ConcurrentHashMap<Integer, Pair<Long, ?>> f9835g;

    /* renamed from: h, reason: collision with root package name */
    public static ExecutorService f9836h = Executors.newFixedThreadPool(16);

    /* renamed from: com.alipay.sdk.m.w.a$a, reason: collision with other inner class name */
    public interface InterfaceC0057a<T, R> {
        R a(T t2);
    }

    public static synchronized void a(int i2, Object obj) {
        try {
            if (f9835g == null) {
                f9835g = new ConcurrentHashMap<>();
            }
            f9835g.put(Integer.valueOf(i2), new Pair<>(Long.valueOf(SystemClock.elapsedRealtime()), obj));
        } catch (Throwable th) {
            throw th;
        }
    }

    public static Pair<Boolean, ?> a(int i2, TimeUnit timeUnit, long j2) {
        ConcurrentHashMap<Integer, Pair<Long, ?>> concurrentHashMap = f9835g;
        if (concurrentHashMap == null) {
            return new Pair<>(Boolean.FALSE, null);
        }
        Pair<Long, ?> pair = concurrentHashMap.get(Integer.valueOf(i2));
        if (pair == null) {
            return new Pair<>(Boolean.FALSE, null);
        }
        Long l2 = (Long) pair.first;
        Object obj = pair.second;
        if (l2 != null && SystemClock.elapsedRealtime() - l2.longValue() <= TimeUnit.MILLISECONDS.convert(j2, timeUnit)) {
            return new Pair<>(Boolean.TRUE, obj);
        }
        return new Pair<>(Boolean.FALSE, null);
    }

    public static synchronized void a() {
        f9835g = null;
    }

    public static Context a(Context context) {
        if (context == null) {
            return null;
        }
        return context.getApplicationContext();
    }

    public static <T> T a(int i2, long j2, TimeUnit timeUnit, InterfaceC0057a<Object, Boolean> interfaceC0057a, Callable<T> callable, boolean z2, long j3, TimeUnit timeUnit2, com.alipay.sdk.m.s.a aVar, boolean z3) {
        T tCall;
        try {
            Pair<Boolean, ?> pairA = a(i2, timeUnit, j2);
            if (((Boolean) pairA.first).booleanValue() && interfaceC0057a.a(pairA.second).booleanValue()) {
                e.d("getC", i2 + " got " + pairA.second);
                return (T) pairA.second;
            }
            if (z3 && n.h()) {
                com.alipay.sdk.m.k.a.b(aVar, com.alipay.sdk.m.k.b.f9364l, "ch_get_main", "" + i2);
                e.d("getC", i2 + " skip");
                tCall = null;
            } else {
                if (z2) {
                    tCall = f9836h.submit(callable).get(j3, timeUnit2);
                } else {
                    tCall = callable.call();
                }
                a(i2, tCall);
            }
            e.d("getC", i2 + " new " + tCall);
            return tCall;
        } catch (Throwable th) {
            e.a(f9829a, "ch_get_e|" + i2, th);
            com.alipay.sdk.m.k.a.a(aVar, com.alipay.sdk.m.k.b.f9364l, "ch_get_e|" + i2, th);
            e.d("getC", i2 + " err");
            return null;
        }
    }
}
