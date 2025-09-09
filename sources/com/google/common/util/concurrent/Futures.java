package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.J2ktIncompatible;
import com.google.common.base.Function;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.AbstractFuture;
import com.google.common.util.concurrent.CollectionFuture;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ImmediateFuture;
import com.google.common.util.concurrent.Partially;
import com.google.common.util.concurrent.internal.InternalFutureFailureAccess;
import com.google.common.util.concurrent.internal.InternalFutures;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.CheckForNull;

@ElementTypesAreNonnullByDefault
@GwtCompatible(emulated = true)
/* loaded from: classes3.dex */
public final class Futures extends GwtFuturesCatchingSpecialization {

    private static final class CallbackListener<V> implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        final Future f14870a;

        /* renamed from: b, reason: collision with root package name */
        final FutureCallback f14871b;

        CallbackListener(Future future, FutureCallback futureCallback) {
            this.f14870a = future;
            this.f14871b = futureCallback;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.lang.Runnable
        public void run() {
            Throwable thTryInternalFastPathGetFailure;
            Object obj = this.f14870a;
            if ((obj instanceof InternalFutureFailureAccess) && (thTryInternalFastPathGetFailure = InternalFutures.tryInternalFastPathGetFailure((InternalFutureFailureAccess) obj)) != null) {
                this.f14871b.onFailure(thTryInternalFastPathGetFailure);
                return;
            }
            try {
                this.f14871b.onSuccess(Futures.getDone(this.f14870a));
            } catch (ExecutionException e2) {
                this.f14871b.onFailure(e2.getCause());
            } catch (Throwable th) {
                this.f14871b.onFailure(th);
            }
        }

        public String toString() {
            return MoreObjects.toStringHelper(this).addValue(this.f14871b).toString();
        }
    }

    @GwtCompatible
    public static final class FutureCombiner<V> {
        private final boolean allMustSucceed;
        private final ImmutableList<ListenableFuture<? extends V>> futures;

        public <C> ListenableFuture<C> call(Callable<C> callable, Executor executor) {
            return new CombinedFuture(this.futures, this.allMustSucceed, executor, callable);
        }

        public <C> ListenableFuture<C> callAsync(AsyncCallable<C> asyncCallable, Executor executor) {
            return new CombinedFuture(this.futures, this.allMustSucceed, executor, asyncCallable);
        }

        public ListenableFuture<?> run(final Runnable runnable, Executor executor) {
            return call(new Callable<Void>(this) { // from class: com.google.common.util.concurrent.Futures.FutureCombiner.1

                /* renamed from: b, reason: collision with root package name */
                final /* synthetic */ FutureCombiner f14873b;

                {
                    this.f14873b = this;
                }

                @Override // java.util.concurrent.Callable
                @CheckForNull
                public Void call() throws Exception {
                    runnable.run();
                    return null;
                }
            }, executor);
        }

        private FutureCombiner(boolean z2, ImmutableList<ListenableFuture<? extends V>> immutableList) {
            this.allMustSucceed = z2;
            this.futures = immutableList;
        }
    }

    private static final class InCompletionOrderFuture<T> extends AbstractFuture<T> {

        @CheckForNull
        private InCompletionOrderState<T> state;

        @Override // com.google.common.util.concurrent.AbstractFuture, java.util.concurrent.Future
        public boolean cancel(boolean z2) {
            InCompletionOrderState<T> inCompletionOrderState = this.state;
            if (!super.cancel(z2)) {
                return false;
            }
            Objects.requireNonNull(inCompletionOrderState);
            inCompletionOrderState.recordOutputCancellation(z2);
            return true;
        }

        @Override // com.google.common.util.concurrent.AbstractFuture
        protected void k() {
            this.state = null;
        }

        @Override // com.google.common.util.concurrent.AbstractFuture
        protected String n() {
            InCompletionOrderState<T> inCompletionOrderState = this.state;
            if (inCompletionOrderState == null) {
                return null;
            }
            return "inputCount=[" + ((InCompletionOrderState) inCompletionOrderState).inputFutures.length + "], remaining=[" + ((InCompletionOrderState) inCompletionOrderState).incompleteOutputCount.get() + "]";
        }

        private InCompletionOrderFuture(InCompletionOrderState<T> inCompletionOrderState) {
            this.state = inCompletionOrderState;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class InCompletionOrderState<T> {
        private volatile int delegateIndex;
        private final AtomicInteger incompleteOutputCount;
        private final ListenableFuture<? extends T>[] inputFutures;
        private boolean shouldInterrupt;
        private boolean wasCancelled;

        /* JADX INFO: Access modifiers changed from: package-private */
        public static /* synthetic */ void d(InCompletionOrderState inCompletionOrderState, ImmutableList immutableList, int i2) {
            inCompletionOrderState.recordInputCompletion(immutableList, i2);
        }

        private void recordCompletion() {
            if (this.incompleteOutputCount.decrementAndGet() == 0 && this.wasCancelled) {
                for (ListenableFuture<? extends T> listenableFuture : this.inputFutures) {
                    if (listenableFuture != null) {
                        listenableFuture.cancel(this.shouldInterrupt);
                    }
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void recordInputCompletion(ImmutableList<AbstractFuture<T>> immutableList, int i2) {
            ListenableFuture<? extends T> listenableFuture = this.inputFutures[i2];
            Objects.requireNonNull(listenableFuture);
            ListenableFuture<? extends T> listenableFuture2 = listenableFuture;
            this.inputFutures[i2] = null;
            for (int i3 = this.delegateIndex; i3 < immutableList.size(); i3++) {
                if (immutableList.get(i3).setFuture(listenableFuture2)) {
                    recordCompletion();
                    this.delegateIndex = i3 + 1;
                    return;
                }
            }
            this.delegateIndex = immutableList.size();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void recordOutputCancellation(boolean z2) {
            this.wasCancelled = true;
            if (!z2) {
                this.shouldInterrupt = false;
            }
            recordCompletion();
        }

        private InCompletionOrderState(ListenableFuture<? extends T>[] listenableFutureArr) {
            this.wasCancelled = false;
            this.shouldInterrupt = true;
            this.delegateIndex = 0;
            this.inputFutures = listenableFutureArr;
            this.incompleteOutputCount = new AtomicInteger(listenableFutureArr.length);
        }
    }

    private static final class NonCancellationPropagatingFuture<V> extends AbstractFuture.TrustedFuture<V> implements Runnable {

        @CheckForNull
        @LazyInit
        private ListenableFuture<V> delegate;

        NonCancellationPropagatingFuture(ListenableFuture listenableFuture) {
            this.delegate = listenableFuture;
        }

        @Override // com.google.common.util.concurrent.AbstractFuture
        protected void k() {
            this.delegate = null;
        }

        @Override // com.google.common.util.concurrent.AbstractFuture
        protected String n() {
            ListenableFuture<V> listenableFuture = this.delegate;
            if (listenableFuture == null) {
                return null;
            }
            return "delegate=[" + listenableFuture + "]";
        }

        @Override // java.lang.Runnable
        public void run() {
            ListenableFuture<V> listenableFuture = this.delegate;
            if (listenableFuture != null) {
                setFuture(listenableFuture);
            }
        }
    }

    private Futures() {
    }

    public static <V> void addCallback(ListenableFuture<V> listenableFuture, FutureCallback<? super V> futureCallback, Executor executor) {
        Preconditions.checkNotNull(futureCallback);
        listenableFuture.addListener(new CallbackListener(listenableFuture, futureCallback), executor);
    }

    @SafeVarargs
    public static <V> ListenableFuture<List<V>> allAsList(ListenableFuture<? extends V>... listenableFutureArr) {
        return new CollectionFuture.ListFuture(ImmutableList.copyOf(listenableFutureArr), true);
    }

    @Partially.GwtIncompatible("AVAILABLE but requires exceptionType to be Throwable.class")
    @J2ktIncompatible
    public static <V, X extends Throwable> ListenableFuture<V> catching(ListenableFuture<? extends V> listenableFuture, Class<X> cls, Function<? super X, ? extends V> function, Executor executor) {
        return AbstractCatchingFuture.p(listenableFuture, cls, function, executor);
    }

    @Partially.GwtIncompatible("AVAILABLE but requires exceptionType to be Throwable.class")
    @J2ktIncompatible
    public static <V, X extends Throwable> ListenableFuture<V> catchingAsync(ListenableFuture<? extends V> listenableFuture, Class<X> cls, AsyncFunction<? super X, ? extends V> asyncFunction, Executor executor) {
        return AbstractCatchingFuture.q(listenableFuture, cls, asyncFunction, executor);
    }

    @GwtIncompatible
    @CanIgnoreReturnValue
    @J2ktIncompatible
    @ParametricNullness
    public static <V, X extends Exception> V getChecked(Future<V> future, Class<X> cls) throws Exception {
        return (V) FuturesGetChecked.f(future, cls);
    }

    @CanIgnoreReturnValue
    @ParametricNullness
    public static <V> V getDone(Future<V> future) throws ExecutionException {
        Preconditions.checkState(future.isDone(), "Future was expected to be done: %s", future);
        return (V) Uninterruptibles.getUninterruptibly(future);
    }

    @CanIgnoreReturnValue
    @ParametricNullness
    public static <V> V getUnchecked(Future<V> future) {
        Preconditions.checkNotNull(future);
        try {
            return (V) Uninterruptibles.getUninterruptibly(future);
        } catch (ExecutionException e2) {
            wrapAndThrowUnchecked(e2.getCause());
            throw new AssertionError();
        }
    }

    private static <T> ListenableFuture<? extends T>[] gwtCompatibleToArray(Iterable<? extends ListenableFuture<? extends T>> iterable) {
        return (ListenableFuture[]) (iterable instanceof Collection ? (Collection) iterable : ImmutableList.copyOf(iterable)).toArray(new ListenableFuture[0]);
    }

    public static <V> ListenableFuture<V> immediateCancelledFuture() {
        ImmediateFuture.ImmediateCancelledFuture immediateCancelledFuture = ImmediateFuture.ImmediateCancelledFuture.f14876c;
        return immediateCancelledFuture != null ? immediateCancelledFuture : new ImmediateFuture.ImmediateCancelledFuture();
    }

    public static <V> ListenableFuture<V> immediateFailedFuture(Throwable th) {
        Preconditions.checkNotNull(th);
        return new ImmediateFuture.ImmediateFailedFuture(th);
    }

    public static <V> ListenableFuture<V> immediateFuture(@ParametricNullness V v2) {
        return v2 == null ? ImmediateFuture.f14875a : new ImmediateFuture(v2);
    }

    public static ListenableFuture<Void> immediateVoidFuture() {
        return ImmediateFuture.f14875a;
    }

    public static <T> ImmutableList<ListenableFuture<T>> inCompletionOrder(Iterable<? extends ListenableFuture<? extends T>> iterable) {
        ListenableFuture[] listenableFutureArrGwtCompatibleToArray = gwtCompatibleToArray(iterable);
        final InCompletionOrderState inCompletionOrderState = new InCompletionOrderState(listenableFutureArrGwtCompatibleToArray);
        ImmutableList.Builder builderBuilderWithExpectedSize = ImmutableList.builderWithExpectedSize(listenableFutureArrGwtCompatibleToArray.length);
        for (int i2 = 0; i2 < listenableFutureArrGwtCompatibleToArray.length; i2++) {
            builderBuilderWithExpectedSize.add((ImmutableList.Builder) new InCompletionOrderFuture(inCompletionOrderState));
        }
        final ImmutableList<ListenableFuture<T>> immutableListBuild = builderBuilderWithExpectedSize.build();
        for (final int i3 = 0; i3 < listenableFutureArrGwtCompatibleToArray.length; i3++) {
            listenableFutureArrGwtCompatibleToArray[i3].addListener(new Runnable() { // from class: com.google.common.util.concurrent.t
                @Override // java.lang.Runnable
                public final void run() {
                    Futures.InCompletionOrderState.d(inCompletionOrderState, immutableListBuild, i3);
                }
            }, MoreExecutors.directExecutor());
        }
        return immutableListBuild;
    }

    @J2ktIncompatible
    @GwtIncompatible
    public static <I, O> Future<O> lazyTransform(final Future<I> future, final Function<? super I, ? extends O> function) {
        Preconditions.checkNotNull(future);
        Preconditions.checkNotNull(function);
        return new Future<O>() { // from class: com.google.common.util.concurrent.Futures.1
            private O applyTransformation(I i2) throws ExecutionException {
                try {
                    return (O) function.apply(i2);
                } catch (Throwable th) {
                    throw new ExecutionException(th);
                }
            }

            @Override // java.util.concurrent.Future
            public boolean cancel(boolean z2) {
                return future.cancel(z2);
            }

            @Override // java.util.concurrent.Future
            public O get() throws ExecutionException, InterruptedException {
                return applyTransformation(future.get());
            }

            @Override // java.util.concurrent.Future
            public boolean isCancelled() {
                return future.isCancelled();
            }

            @Override // java.util.concurrent.Future
            public boolean isDone() {
                return future.isDone();
            }

            @Override // java.util.concurrent.Future
            public O get(long j2, TimeUnit timeUnit) throws ExecutionException, InterruptedException, TimeoutException {
                return applyTransformation(future.get(j2, timeUnit));
            }
        };
    }

    public static <V> ListenableFuture<V> nonCancellationPropagating(ListenableFuture<V> listenableFuture) {
        if (listenableFuture.isDone()) {
            return listenableFuture;
        }
        NonCancellationPropagatingFuture nonCancellationPropagatingFuture = new NonCancellationPropagatingFuture(listenableFuture);
        listenableFuture.addListener(nonCancellationPropagatingFuture, MoreExecutors.directExecutor());
        return nonCancellationPropagatingFuture;
    }

    @J2ktIncompatible
    @GwtIncompatible
    public static <O> ListenableFuture<O> scheduleAsync(AsyncCallable<O> asyncCallable, long j2, TimeUnit timeUnit, ScheduledExecutorService scheduledExecutorService) {
        TrustedListenableFutureTask trustedListenableFutureTaskP = TrustedListenableFutureTask.p(asyncCallable);
        final ScheduledFuture<?> scheduledFutureSchedule = scheduledExecutorService.schedule(trustedListenableFutureTaskP, j2, timeUnit);
        trustedListenableFutureTaskP.addListener(new Runnable() { // from class: com.google.common.util.concurrent.u
            @Override // java.lang.Runnable
            public final void run() {
                scheduledFutureSchedule.cancel(false);
            }
        }, MoreExecutors.directExecutor());
        return trustedListenableFutureTaskP;
    }

    public static <O> ListenableFuture<O> submit(Callable<O> callable, Executor executor) {
        TrustedListenableFutureTask trustedListenableFutureTaskR = TrustedListenableFutureTask.r(callable);
        executor.execute(trustedListenableFutureTaskR);
        return trustedListenableFutureTaskR;
    }

    public static <O> ListenableFuture<O> submitAsync(AsyncCallable<O> asyncCallable, Executor executor) {
        TrustedListenableFutureTask trustedListenableFutureTaskP = TrustedListenableFutureTask.p(asyncCallable);
        executor.execute(trustedListenableFutureTaskP);
        return trustedListenableFutureTaskP;
    }

    @SafeVarargs
    public static <V> ListenableFuture<List<V>> successfulAsList(ListenableFuture<? extends V>... listenableFutureArr) {
        return new CollectionFuture.ListFuture(ImmutableList.copyOf(listenableFutureArr), false);
    }

    public static <I, O> ListenableFuture<O> transform(ListenableFuture<I> listenableFuture, Function<? super I, ? extends O> function, Executor executor) {
        return AbstractTransformFuture.p(listenableFuture, function, executor);
    }

    public static <I, O> ListenableFuture<O> transformAsync(ListenableFuture<I> listenableFuture, AsyncFunction<? super I, ? extends O> asyncFunction, Executor executor) {
        return AbstractTransformFuture.q(listenableFuture, asyncFunction, executor);
    }

    @SafeVarargs
    public static <V> FutureCombiner<V> whenAllComplete(ListenableFuture<? extends V>... listenableFutureArr) {
        return new FutureCombiner<>(false, ImmutableList.copyOf(listenableFutureArr));
    }

    @SafeVarargs
    public static <V> FutureCombiner<V> whenAllSucceed(ListenableFuture<? extends V>... listenableFutureArr) {
        return new FutureCombiner<>(true, ImmutableList.copyOf(listenableFutureArr));
    }

    @J2ktIncompatible
    @GwtIncompatible
    public static <V> ListenableFuture<V> withTimeout(ListenableFuture<V> listenableFuture, long j2, TimeUnit timeUnit, ScheduledExecutorService scheduledExecutorService) {
        return listenableFuture.isDone() ? listenableFuture : TimeoutFuture.s(listenableFuture, j2, timeUnit, scheduledExecutorService);
    }

    private static void wrapAndThrowUnchecked(Throwable th) {
        if (!(th instanceof Error)) {
            throw new UncheckedExecutionException(th);
        }
        throw new ExecutionError((Error) th);
    }

    @GwtIncompatible
    @CanIgnoreReturnValue
    @J2ktIncompatible
    @ParametricNullness
    public static <V, X extends Exception> V getChecked(Future<V> future, Class<X> cls, long j2, TimeUnit timeUnit) throws Exception {
        return (V) FuturesGetChecked.g(future, cls, j2, timeUnit);
    }

    public static <V> ListenableFuture<List<V>> successfulAsList(Iterable<? extends ListenableFuture<? extends V>> iterable) {
        return new CollectionFuture.ListFuture(ImmutableList.copyOf(iterable), false);
    }

    public static <V> FutureCombiner<V> whenAllComplete(Iterable<? extends ListenableFuture<? extends V>> iterable) {
        return new FutureCombiner<>(false, ImmutableList.copyOf(iterable));
    }

    public static <V> FutureCombiner<V> whenAllSucceed(Iterable<? extends ListenableFuture<? extends V>> iterable) {
        return new FutureCombiner<>(true, ImmutableList.copyOf(iterable));
    }

    public static <V> ListenableFuture<List<V>> allAsList(Iterable<? extends ListenableFuture<? extends V>> iterable) {
        return new CollectionFuture.ListFuture(ImmutableList.copyOf(iterable), true);
    }

    public static ListenableFuture<Void> submit(Runnable runnable, Executor executor) {
        TrustedListenableFutureTask trustedListenableFutureTaskQ = TrustedListenableFutureTask.q(runnable, null);
        executor.execute(trustedListenableFutureTaskQ);
        return trustedListenableFutureTaskQ;
    }
}
