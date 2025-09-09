package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.AbstractFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

@ElementTypesAreNonnullByDefault
@GwtCompatible
/* loaded from: classes3.dex */
class ImmediateFuture<V> implements ListenableFuture<V> {

    /* renamed from: a, reason: collision with root package name */
    static final ListenableFuture f14875a = new ImmediateFuture(null);
    private static final LazyLogger log = new LazyLogger(ImmediateFuture.class);

    @ParametricNullness
    private final V value;

    static final class ImmediateCancelledFuture<V> extends AbstractFuture.TrustedFuture<V> {

        /* renamed from: c, reason: collision with root package name */
        static final ImmediateCancelledFuture f14876c;

        static {
            f14876c = AbstractFuture.f14753a ? null : new ImmediateCancelledFuture();
        }

        ImmediateCancelledFuture() {
            cancel(false);
        }
    }

    static final class ImmediateFailedFuture<V> extends AbstractFuture.TrustedFuture<V> {
        ImmediateFailedFuture(Throwable th) {
            setException(th);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    ImmediateFuture(Object obj) {
        this.value = obj;
    }

    @Override // com.google.common.util.concurrent.ListenableFuture
    public void addListener(Runnable runnable, Executor executor) {
        Preconditions.checkNotNull(runnable, "Runnable was null.");
        Preconditions.checkNotNull(executor, "Executor was null.");
        try {
            executor.execute(runnable);
        } catch (Exception e2) {
            log.a().log(Level.SEVERE, "RuntimeException while executing runnable " + runnable + " with executor " + executor, (Throwable) e2);
        }
    }

    @Override // java.util.concurrent.Future
    public boolean cancel(boolean z2) {
        return false;
    }

    @Override // java.util.concurrent.Future
    @ParametricNullness
    public V get() {
        return this.value;
    }

    @Override // java.util.concurrent.Future
    public boolean isCancelled() {
        return false;
    }

    @Override // java.util.concurrent.Future
    public boolean isDone() {
        return true;
    }

    public String toString() {
        return super.toString() + "[status=SUCCESS, result=[" + this.value + "]]";
    }

    @Override // java.util.concurrent.Future
    @ParametricNullness
    public V get(long j2, TimeUnit timeUnit) throws ExecutionException {
        Preconditions.checkNotNull(timeUnit);
        return get();
    }
}
