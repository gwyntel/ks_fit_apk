package com.google.firebase.concurrent;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;

/* loaded from: classes3.dex */
final class PausableExecutorImpl implements PausableExecutor {

    /* renamed from: a, reason: collision with root package name */
    final LinkedBlockingQueue f15016a = new LinkedBlockingQueue();
    private final Executor delegate;
    private volatile boolean paused;

    PausableExecutorImpl(boolean z2, Executor executor) {
        this.paused = z2;
        this.delegate = executor;
    }

    private void maybeEnqueueNext() {
        if (this.paused) {
            return;
        }
        Runnable runnable = (Runnable) this.f15016a.poll();
        while (runnable != null) {
            this.delegate.execute(runnable);
            runnable = !this.paused ? (Runnable) this.f15016a.poll() : null;
        }
    }

    @Override // java.util.concurrent.Executor
    public void execute(Runnable runnable) {
        this.f15016a.offer(runnable);
        maybeEnqueueNext();
    }

    @Override // com.google.firebase.concurrent.PausableExecutor
    public boolean isPaused() {
        return this.paused;
    }

    @Override // com.google.firebase.concurrent.PausableExecutor
    public void pause() {
        this.paused = true;
    }

    @Override // com.google.firebase.concurrent.PausableExecutor
    public void resume() {
        this.paused = false;
        maybeEnqueueNext();
    }
}
