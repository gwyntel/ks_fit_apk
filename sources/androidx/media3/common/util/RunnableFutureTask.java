package androidx.media3.common.util;

import androidx.annotation.Nullable;
import java.lang.Exception;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@UnstableApi
/* loaded from: classes.dex */
public abstract class RunnableFutureTask<R, E extends Exception> implements RunnableFuture<R> {
    private boolean canceled;

    @Nullable
    private Exception exception;

    @Nullable
    private R result;

    @Nullable
    private Thread workThread;
    private final ConditionVariable started = new ConditionVariable();
    private final ConditionVariable finished = new ConditionVariable();
    private final Object cancelLock = new Object();

    protected RunnableFutureTask() {
    }

    @UnknownNull
    private R getResult() throws ExecutionException {
        if (this.canceled) {
            throw new CancellationException();
        }
        if (this.exception == null) {
            return this.result;
        }
        throw new ExecutionException(this.exception);
    }

    protected void a() {
    }

    protected abstract Object b();

    public final void blockUntilFinished() {
        this.finished.blockUninterruptible();
    }

    public final void blockUntilStarted() {
        this.started.blockUninterruptible();
    }

    @Override // java.util.concurrent.Future
    public final boolean cancel(boolean z2) {
        synchronized (this.cancelLock) {
            try {
                if (!this.canceled && !this.finished.isOpen()) {
                    this.canceled = true;
                    a();
                    Thread thread = this.workThread;
                    if (thread == null) {
                        this.started.open();
                        this.finished.open();
                    } else if (z2) {
                        thread.interrupt();
                    }
                    return true;
                }
                return false;
            } finally {
            }
        }
    }

    @Override // java.util.concurrent.Future
    @UnknownNull
    public final R get() throws ExecutionException, InterruptedException {
        this.finished.block();
        return getResult();
    }

    @Override // java.util.concurrent.Future
    public final boolean isCancelled() {
        return this.canceled;
    }

    @Override // java.util.concurrent.Future
    public final boolean isDone() {
        return this.finished.isOpen();
    }

    @Override // java.util.concurrent.RunnableFuture, java.lang.Runnable
    public final void run() {
        synchronized (this.cancelLock) {
            try {
                if (this.canceled) {
                    return;
                }
                this.workThread = Thread.currentThread();
                this.started.open();
                try {
                    try {
                        this.result = (R) b();
                        synchronized (this.cancelLock) {
                            this.finished.open();
                            this.workThread = null;
                            Thread.interrupted();
                        }
                    } catch (Exception e2) {
                        this.exception = e2;
                        synchronized (this.cancelLock) {
                            this.finished.open();
                            this.workThread = null;
                            Thread.interrupted();
                        }
                    }
                } catch (Throwable th) {
                    synchronized (this.cancelLock) {
                        this.finished.open();
                        this.workThread = null;
                        Thread.interrupted();
                        throw th;
                    }
                }
            } finally {
            }
        }
    }

    @Override // java.util.concurrent.Future
    @UnknownNull
    public final R get(long j2, TimeUnit timeUnit) throws ExecutionException, InterruptedException, TimeoutException {
        if (this.finished.block(TimeUnit.MILLISECONDS.convert(j2, timeUnit))) {
            return getResult();
        }
        throw new TimeoutException();
    }
}
