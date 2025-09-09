package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.J2ktIncompatible;
import com.google.errorprone.annotations.concurrent.GuardedBy;
import com.kingsmith.miot.KsProperty;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

@J2ktIncompatible
@ElementTypesAreNonnullByDefault
@GwtIncompatible
/* loaded from: classes3.dex */
final class DirectExecutorService extends AbstractListeningExecutorService {
    private final Object lock = new Object();

    @GuardedBy(KsProperty.Lock)
    private int runningTasks = 0;

    @GuardedBy(KsProperty.Lock)
    private boolean shutdown = false;

    DirectExecutorService() {
    }

    private void endTask() {
        synchronized (this.lock) {
            try {
                int i2 = this.runningTasks - 1;
                this.runningTasks = i2;
                if (i2 == 0) {
                    this.lock.notifyAll();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private void startTask() {
        synchronized (this.lock) {
            try {
                if (this.shutdown) {
                    throw new RejectedExecutionException("Executor already shutdown");
                }
                this.runningTasks++;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // java.util.concurrent.ExecutorService
    public boolean awaitTermination(long j2, TimeUnit timeUnit) throws InterruptedException {
        long nanos = timeUnit.toNanos(j2);
        synchronized (this.lock) {
            while (true) {
                try {
                    if (this.shutdown && this.runningTasks == 0) {
                        return true;
                    }
                    if (nanos <= 0) {
                        return false;
                    }
                    long jNanoTime = System.nanoTime();
                    TimeUnit.NANOSECONDS.timedWait(this.lock, nanos);
                    nanos -= System.nanoTime() - jNanoTime;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    @Override // java.util.concurrent.Executor
    public void execute(Runnable runnable) {
        startTask();
        try {
            runnable.run();
        } finally {
            endTask();
        }
    }

    @Override // java.util.concurrent.ExecutorService
    public boolean isShutdown() {
        boolean z2;
        synchronized (this.lock) {
            z2 = this.shutdown;
        }
        return z2;
    }

    @Override // java.util.concurrent.ExecutorService
    public boolean isTerminated() {
        boolean z2;
        synchronized (this.lock) {
            try {
                z2 = this.shutdown && this.runningTasks == 0;
            } finally {
            }
        }
        return z2;
    }

    @Override // java.util.concurrent.ExecutorService
    public void shutdown() {
        synchronized (this.lock) {
            try {
                this.shutdown = true;
                if (this.runningTasks == 0) {
                    this.lock.notifyAll();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // java.util.concurrent.ExecutorService
    public List<Runnable> shutdownNow() {
        shutdown();
        return Collections.emptyList();
    }
}
