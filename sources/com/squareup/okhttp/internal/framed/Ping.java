package com.squareup.okhttp.internal.framed;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public final class Ping {
    private final CountDownLatch latch = new CountDownLatch(1);
    private long sent = -1;
    private long received = -1;

    Ping() {
    }

    void a() {
        if (this.received == -1) {
            long j2 = this.sent;
            if (j2 != -1) {
                this.received = j2 - 1;
                this.latch.countDown();
                return;
            }
        }
        throw new IllegalStateException();
    }

    void b() {
        if (this.received != -1 || this.sent == -1) {
            throw new IllegalStateException();
        }
        this.received = System.nanoTime();
        this.latch.countDown();
    }

    void c() {
        if (this.sent != -1) {
            throw new IllegalStateException();
        }
        this.sent = System.nanoTime();
    }

    public long roundTripTime() throws InterruptedException {
        this.latch.await();
        return this.received - this.sent;
    }

    public long roundTripTime(long j2, TimeUnit timeUnit) throws InterruptedException {
        if (this.latch.await(j2, timeUnit)) {
            return this.received - this.sent;
        }
        return -2L;
    }
}
