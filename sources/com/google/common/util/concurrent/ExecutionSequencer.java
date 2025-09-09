package com.google.common.util.concurrent;

import com.google.common.annotations.J2ktIncompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReference;
import javax.annotation.CheckForNull;

@J2ktIncompatible
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public final class ExecutionSequencer {
    private final AtomicReference<ListenableFuture<Void>> ref = new AtomicReference<>(Futures.immediateVoidFuture());

    @LazyInit
    private ThreadConfinedTaskQueue latestTaskQueue = new ThreadConfinedTaskQueue();

    enum RunningState {
        NOT_RUN,
        CANCELLED,
        STARTED
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class TaskNonReentrantExecutor extends AtomicReference<RunningState> implements Executor, Runnable {

        @CheckForNull
        Executor delegate;

        @CheckForNull
        ExecutionSequencer sequencer;

        @CheckForNull
        @LazyInit
        Thread submitting;

        @CheckForNull
        Runnable task;

        /* JADX INFO: Access modifiers changed from: private */
        public boolean trySetCancelled() {
            return compareAndSet(RunningState.NOT_RUN, RunningState.CANCELLED);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean trySetStarted() {
            return compareAndSet(RunningState.NOT_RUN, RunningState.STARTED);
        }

        @Override // java.util.concurrent.Executor
        public void execute(Runnable runnable) {
            if (get() == RunningState.CANCELLED) {
                this.delegate = null;
                this.sequencer = null;
                return;
            }
            this.submitting = Thread.currentThread();
            try {
                ExecutionSequencer executionSequencer = this.sequencer;
                Objects.requireNonNull(executionSequencer);
                ThreadConfinedTaskQueue threadConfinedTaskQueue = executionSequencer.latestTaskQueue;
                if (threadConfinedTaskQueue.f14865a == this.submitting) {
                    this.sequencer = null;
                    Preconditions.checkState(threadConfinedTaskQueue.f14866b == null);
                    threadConfinedTaskQueue.f14866b = runnable;
                    Executor executor = this.delegate;
                    Objects.requireNonNull(executor);
                    threadConfinedTaskQueue.f14867c = executor;
                    this.delegate = null;
                } else {
                    Executor executor2 = this.delegate;
                    Objects.requireNonNull(executor2);
                    this.delegate = null;
                    this.task = runnable;
                    executor2.execute(this);
                }
                this.submitting = null;
            } catch (Throwable th) {
                this.submitting = null;
                throw th;
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            Executor executor;
            Thread threadCurrentThread = Thread.currentThread();
            Thread thread = null;
            byte b2 = 0;
            if (threadCurrentThread != this.submitting) {
                Runnable runnable = this.task;
                Objects.requireNonNull(runnable);
                this.task = null;
                runnable.run();
                return;
            }
            ThreadConfinedTaskQueue threadConfinedTaskQueue = new ThreadConfinedTaskQueue();
            threadConfinedTaskQueue.f14865a = threadCurrentThread;
            ExecutionSequencer executionSequencer = this.sequencer;
            Objects.requireNonNull(executionSequencer);
            executionSequencer.latestTaskQueue = threadConfinedTaskQueue;
            this.sequencer = null;
            try {
                Runnable runnable2 = this.task;
                Objects.requireNonNull(runnable2);
                this.task = null;
                runnable2.run();
                while (true) {
                    Runnable runnable3 = threadConfinedTaskQueue.f14866b;
                    if (runnable3 == null || (executor = threadConfinedTaskQueue.f14867c) == null) {
                        break;
                    }
                    threadConfinedTaskQueue.f14866b = null;
                    threadConfinedTaskQueue.f14867c = null;
                    executor.execute(runnable3);
                }
            } finally {
                threadConfinedTaskQueue.f14865a = null;
            }
        }

        private TaskNonReentrantExecutor(Executor executor, ExecutionSequencer executionSequencer) {
            super(RunningState.NOT_RUN);
            this.delegate = executor;
            this.sequencer = executionSequencer;
        }
    }

    private static final class ThreadConfinedTaskQueue {

        /* renamed from: a, reason: collision with root package name */
        Thread f14865a;

        /* renamed from: b, reason: collision with root package name */
        Runnable f14866b;

        /* renamed from: c, reason: collision with root package name */
        Executor f14867c;

        private ThreadConfinedTaskQueue() {
        }
    }

    private ExecutionSequencer() {
    }

    public static ExecutionSequencer create() {
        return new ExecutionSequencer();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$submitAsync$0(TrustedListenableFutureTask trustedListenableFutureTask, SettableFuture settableFuture, ListenableFuture listenableFuture, ListenableFuture listenableFuture2, TaskNonReentrantExecutor taskNonReentrantExecutor) {
        if (trustedListenableFutureTask.isDone()) {
            settableFuture.setFuture(listenableFuture);
        } else if (listenableFuture2.isCancelled() && taskNonReentrantExecutor.trySetCancelled()) {
            trustedListenableFutureTask.cancel(false);
        }
    }

    public <T> ListenableFuture<T> submit(final Callable<T> callable, Executor executor) {
        Preconditions.checkNotNull(callable);
        Preconditions.checkNotNull(executor);
        return submitAsync(new AsyncCallable<T>(this) { // from class: com.google.common.util.concurrent.ExecutionSequencer.1

            /* renamed from: b, reason: collision with root package name */
            final /* synthetic */ ExecutionSequencer f14861b;

            {
                this.f14861b = this;
            }

            @Override // com.google.common.util.concurrent.AsyncCallable
            public ListenableFuture<T> call() throws Exception {
                return Futures.immediateFuture(callable.call());
            }

            public String toString() {
                return callable.toString();
            }
        }, executor);
    }

    public <T> ListenableFuture<T> submitAsync(final AsyncCallable<T> asyncCallable, Executor executor) {
        Preconditions.checkNotNull(asyncCallable);
        Preconditions.checkNotNull(executor);
        final TaskNonReentrantExecutor taskNonReentrantExecutor = new TaskNonReentrantExecutor(executor, this);
        AsyncCallable<T> asyncCallable2 = new AsyncCallable<T>(this) { // from class: com.google.common.util.concurrent.ExecutionSequencer.2

            /* renamed from: c, reason: collision with root package name */
            final /* synthetic */ ExecutionSequencer f14864c;

            {
                this.f14864c = this;
            }

            @Override // com.google.common.util.concurrent.AsyncCallable
            public ListenableFuture<T> call() throws Exception {
                return !taskNonReentrantExecutor.trySetStarted() ? Futures.immediateCancelledFuture() : asyncCallable.call();
            }

            public String toString() {
                return asyncCallable.toString();
            }
        };
        final SettableFuture settableFutureCreate = SettableFuture.create();
        final ListenableFuture<Void> andSet = this.ref.getAndSet(settableFutureCreate);
        final TrustedListenableFutureTask trustedListenableFutureTaskP = TrustedListenableFutureTask.p(asyncCallable2);
        andSet.addListener(trustedListenableFutureTaskP, taskNonReentrantExecutor);
        final ListenableFuture<T> listenableFutureNonCancellationPropagating = Futures.nonCancellationPropagating(trustedListenableFutureTaskP);
        Runnable runnable = new Runnable() { // from class: com.google.common.util.concurrent.s
            @Override // java.lang.Runnable
            public final void run() {
                ExecutionSequencer.lambda$submitAsync$0(trustedListenableFutureTaskP, settableFutureCreate, andSet, listenableFutureNonCancellationPropagating, taskNonReentrantExecutor);
            }
        };
        listenableFutureNonCancellationPropagating.addListener(runnable, MoreExecutors.directExecutor());
        trustedListenableFutureTaskP.addListener(runnable, MoreExecutors.directExecutor());
        return listenableFutureNonCancellationPropagating;
    }
}
