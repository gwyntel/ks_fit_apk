package com.xiaomi.push;

import java.util.concurrent.Callable;

/* loaded from: classes4.dex */
public class bq<T> {

    /* renamed from: a, reason: collision with root package name */
    private final long f23501a;

    /* renamed from: b, reason: collision with root package name */
    private long f23502b;

    /* renamed from: c, reason: collision with root package name */
    private long f23503c;

    /* renamed from: d, reason: collision with root package name */
    private long f23504d;

    /* renamed from: e, reason: collision with root package name */
    private long f23505e;

    /* renamed from: f, reason: collision with root package name */
    private long f23506f;

    /* renamed from: g, reason: collision with root package name */
    private long f23507g;

    /* renamed from: h, reason: collision with root package name */
    private long f23508h;

    /* renamed from: i, reason: collision with root package name */
    private final long f23509i;

    public bq(long j2, long j3) {
        this.f23509i = j2 * 1000000;
        this.f23501a = j3;
    }

    public long a() {
        return this.f23503c;
    }

    public long b() {
        return this.f23504d;
    }

    public long c() {
        long j2 = this.f23506f;
        if (j2 > 0) {
            long j3 = this.f23505e;
            if (j3 > 0) {
                return j2 / j3;
            }
        }
        return 0L;
    }

    public long d() {
        long j2 = this.f23508h;
        long j3 = this.f23507g;
        if (j2 > j3) {
            return j2 - j3;
        }
        return 0L;
    }

    public T a(Callable<T> callable) throws Exception {
        T tCall;
        long j2 = this.f23502b;
        long j3 = this.f23509i;
        if (j2 > j3) {
            long j4 = (j2 / j3) * this.f23501a;
            this.f23502b = 0L;
            if (j4 > 0) {
                try {
                    Thread.sleep(j4);
                } catch (Exception unused) {
                }
            }
        }
        long jNanoTime = System.nanoTime();
        if (this.f23507g <= 0) {
            this.f23507g = jNanoTime;
        }
        try {
            tCall = callable.call();
        } catch (Exception e2) {
            e2.printStackTrace();
            tCall = null;
        }
        long jNanoTime2 = System.nanoTime() - jNanoTime;
        this.f23508h = System.nanoTime();
        this.f23505e++;
        if (this.f23503c < jNanoTime2) {
            this.f23503c = jNanoTime2;
        }
        if (jNanoTime2 > 0) {
            this.f23506f += jNanoTime2;
            long j5 = this.f23504d;
            if (j5 == 0 || j5 > jNanoTime2) {
                this.f23504d = jNanoTime2;
            }
        }
        this.f23502b += Math.max(jNanoTime2, 0L);
        return tCall;
    }
}
