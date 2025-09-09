package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.UnmodifiableIterator;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import javax.annotation.CheckForNull;

@ElementTypesAreNonnullByDefault
@GwtCompatible
/* loaded from: classes3.dex */
abstract class AggregateFuture<InputT, OutputT> extends AggregateFutureState<OutputT> {
    private static final LazyLogger logger = new LazyLogger(AggregateFuture.class);
    private final boolean allMustSucceed;
    private final boolean collectsValues;

    @CheckForNull
    @LazyInit
    private ImmutableCollection<? extends ListenableFuture<? extends InputT>> futures;

    enum ReleaseResourcesReason {
        OUTPUT_FUTURE_DONE,
        ALL_INPUT_FUTURES_PROCESSED
    }

    AggregateFuture(ImmutableCollection immutableCollection, boolean z2, boolean z3) {
        super(immutableCollection.size());
        this.futures = (ImmutableCollection) Preconditions.checkNotNull(immutableCollection);
        this.allMustSucceed = z2;
        this.collectsValues = z3;
    }

    private static boolean addCausalChain(Set<Throwable> set, Throwable th) {
        while (th != null) {
            if (!set.add(th)) {
                return false;
            }
            th = th.getCause();
        }
        return true;
    }

    private void collectValueFromNonCancelledFuture(int i2, Future<? extends InputT> future) {
        try {
            y(i2, Uninterruptibles.getUninterruptibly(future));
        } catch (ExecutionException e2) {
            handleException(e2.getCause());
        } catch (Throwable th) {
            handleException(th);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: decrementCountAndMaybeComplete, reason: merged with bridge method [inline-methods] */
    public void lambda$init$1(@CheckForNull ImmutableCollection<? extends Future<? extends InputT>> immutableCollection) {
        int iU = u();
        Preconditions.checkState(iU >= 0, "Less than 0 remaining futures");
        if (iU == 0) {
            processCompleted(immutableCollection);
        }
    }

    private void handleException(Throwable th) {
        Preconditions.checkNotNull(th);
        if (this.allMustSucceed && !setException(th) && addCausalChain(v(), th)) {
            log(th);
        } else if (th instanceof Error) {
            log(th);
        }
    }

    private static void log(Throwable th) {
        logger.a().log(Level.SEVERE, th instanceof Error ? "Input Future failed with Error" : "Got more than one input Future failure. Logging failures after the first", th);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: processAllMustSucceedDoneFuture, reason: merged with bridge method [inline-methods] */
    public void lambda$init$0(int i2, ListenableFuture<? extends InputT> listenableFuture) {
        try {
            if (listenableFuture.isCancelled()) {
                this.futures = null;
                cancel(false);
            } else {
                collectValueFromNonCancelledFuture(i2, listenableFuture);
            }
            lambda$init$1(null);
        } catch (Throwable th) {
            lambda$init$1(null);
            throw th;
        }
    }

    private void processCompleted(@CheckForNull ImmutableCollection<? extends Future<? extends InputT>> immutableCollection) {
        if (immutableCollection != null) {
            UnmodifiableIterator<? extends Future<? extends InputT>> it = immutableCollection.iterator();
            int i2 = 0;
            while (it.hasNext()) {
                Future<? extends InputT> next = it.next();
                if (!next.isCancelled()) {
                    collectValueFromNonCancelledFuture(i2, next);
                }
                i2++;
            }
        }
        t();
        z();
        B(ReleaseResourcesReason.ALL_INPUT_FUTURES_PROCESSED);
    }

    final void A() {
        Objects.requireNonNull(this.futures);
        if (this.futures.isEmpty()) {
            z();
            return;
        }
        if (!this.allMustSucceed) {
            final ImmutableCollection<? extends ListenableFuture<? extends InputT>> immutableCollection = this.collectsValues ? this.futures : null;
            Runnable runnable = new Runnable() { // from class: com.google.common.util.concurrent.l
                @Override // java.lang.Runnable
                public final void run() {
                    this.f14965a.lambda$init$1(immutableCollection);
                }
            };
            UnmodifiableIterator<? extends ListenableFuture<? extends InputT>> it = this.futures.iterator();
            while (it.hasNext()) {
                ListenableFuture<? extends InputT> next = it.next();
                if (next.isDone()) {
                    lambda$init$1(immutableCollection);
                } else {
                    next.addListener(runnable, MoreExecutors.directExecutor());
                }
            }
            return;
        }
        UnmodifiableIterator<? extends ListenableFuture<? extends InputT>> it2 = this.futures.iterator();
        final int i2 = 0;
        while (it2.hasNext()) {
            final ListenableFuture<? extends InputT> next2 = it2.next();
            int i3 = i2 + 1;
            if (next2.isDone()) {
                lambda$init$0(i2, next2);
            } else {
                next2.addListener(new Runnable() { // from class: com.google.common.util.concurrent.k
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f14962a.lambda$init$0(i2, next2);
                    }
                }, MoreExecutors.directExecutor());
            }
            i2 = i3;
        }
    }

    void B(ReleaseResourcesReason releaseResourcesReason) {
        Preconditions.checkNotNull(releaseResourcesReason);
        this.futures = null;
    }

    @Override // com.google.common.util.concurrent.AbstractFuture
    protected final void k() {
        super.k();
        ImmutableCollection<? extends ListenableFuture<? extends InputT>> immutableCollection = this.futures;
        B(ReleaseResourcesReason.OUTPUT_FUTURE_DONE);
        if (isCancelled() && (immutableCollection != null)) {
            boolean zO = o();
            UnmodifiableIterator<? extends ListenableFuture<? extends InputT>> it = immutableCollection.iterator();
            while (it.hasNext()) {
                it.next().cancel(zO);
            }
        }
    }

    @Override // com.google.common.util.concurrent.AbstractFuture
    protected final String n() {
        ImmutableCollection<? extends ListenableFuture<? extends InputT>> immutableCollection = this.futures;
        if (immutableCollection == null) {
            return super.n();
        }
        return "futures=" + immutableCollection;
    }

    @Override // com.google.common.util.concurrent.AggregateFutureState
    final void s(Set set) {
        Preconditions.checkNotNull(set);
        if (isCancelled()) {
            return;
        }
        Throwable thA = a();
        Objects.requireNonNull(thA);
        addCausalChain(set, thA);
    }

    abstract void y(int i2, Object obj);

    abstract void z();
}
