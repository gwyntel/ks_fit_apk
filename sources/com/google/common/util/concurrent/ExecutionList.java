package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.J2ktIncompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.concurrent.GuardedBy;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import javax.annotation.CheckForNull;

@J2ktIncompatible
@ElementTypesAreNonnullByDefault
@GwtIncompatible
/* loaded from: classes3.dex */
public final class ExecutionList {
    private static final LazyLogger log = new LazyLogger(ExecutionList.class);

    @GuardedBy("this")
    private boolean executed;

    @CheckForNull
    @GuardedBy("this")
    private RunnableExecutorPair runnables;

    private static final class RunnableExecutorPair {

        /* renamed from: a, reason: collision with root package name */
        final Runnable f14857a;

        /* renamed from: b, reason: collision with root package name */
        final Executor f14858b;

        /* renamed from: c, reason: collision with root package name */
        RunnableExecutorPair f14859c;

        RunnableExecutorPair(Runnable runnable, Executor executor, RunnableExecutorPair runnableExecutorPair) {
            this.f14857a = runnable;
            this.f14858b = executor;
            this.f14859c = runnableExecutorPair;
        }
    }

    private static void executeListener(Runnable runnable, Executor executor) {
        try {
            executor.execute(runnable);
        } catch (Exception e2) {
            log.a().log(Level.SEVERE, "RuntimeException while executing runnable " + runnable + " with executor " + executor, (Throwable) e2);
        }
    }

    public void add(Runnable runnable, Executor executor) {
        Preconditions.checkNotNull(runnable, "Runnable was null.");
        Preconditions.checkNotNull(executor, "Executor was null.");
        synchronized (this) {
            try {
                if (this.executed) {
                    executeListener(runnable, executor);
                } else {
                    this.runnables = new RunnableExecutorPair(runnable, executor, this.runnables);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void execute() {
        synchronized (this) {
            try {
                if (this.executed) {
                    return;
                }
                this.executed = true;
                RunnableExecutorPair runnableExecutorPair = this.runnables;
                RunnableExecutorPair runnableExecutorPair2 = null;
                this.runnables = null;
                while (runnableExecutorPair != null) {
                    RunnableExecutorPair runnableExecutorPair3 = runnableExecutorPair.f14859c;
                    runnableExecutorPair.f14859c = runnableExecutorPair2;
                    runnableExecutorPair2 = runnableExecutorPair;
                    runnableExecutorPair = runnableExecutorPair3;
                }
                while (runnableExecutorPair2 != null) {
                    executeListener(runnableExecutorPair2.f14857a, runnableExecutorPair2.f14858b);
                    runnableExecutorPair2 = runnableExecutorPair2.f14859c;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
