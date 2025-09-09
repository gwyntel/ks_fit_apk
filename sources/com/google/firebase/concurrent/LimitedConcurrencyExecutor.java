package com.google.firebase.concurrent;

import com.google.firebase.components.Preconditions;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

/* loaded from: classes3.dex */
class LimitedConcurrencyExecutor implements Executor {
    private final Executor delegate;
    private final LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
    private final Semaphore semaphore;

    LimitedConcurrencyExecutor(Executor executor, int i2) {
        Preconditions.checkArgument(i2 > 0, "concurrency must be positive.");
        this.delegate = executor;
        this.semaphore = new Semaphore(i2, true);
    }

    private Runnable decorate(final Runnable runnable) {
        return new Runnable() { // from class: com.google.firebase.concurrent.w
            @Override // java.lang.Runnable
            public final void run() {
                this.f15063a.lambda$decorate$0(runnable);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$decorate$0(Runnable runnable) {
        try {
            runnable.run();
        } finally {
            this.semaphore.release();
            maybeEnqueueNext();
        }
    }

    private void maybeEnqueueNext() {
        while (this.semaphore.tryAcquire()) {
            Runnable runnablePoll = this.queue.poll();
            if (runnablePoll == null) {
                this.semaphore.release();
                return;
            }
            this.delegate.execute(decorate(runnablePoll));
        }
    }

    @Override // java.util.concurrent.Executor
    public void execute(Runnable runnable) {
        this.queue.offer(runnable);
        maybeEnqueueNext();
    }
}
