package com.google.common.util.concurrent;

import com.google.common.annotations.J2ktIncompatible;
import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Futures;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.DoNotMock;
import com.google.j2objc.annotations.RetainedWith;
import java.io.Closeable;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.CheckForNull;

@ElementTypesAreNonnullByDefault
@DoNotMock("Use ClosingFuture.from(Futures.immediate*Future)")
@J2ktIncompatible
/* loaded from: classes3.dex */
public final class ClosingFuture<V> {
    private static final LazyLogger logger = new LazyLogger(ClosingFuture.class);
    private final CloseableList closeables;
    private final FluentFuture<V> future;
    private final AtomicReference<State> state;

    public interface AsyncClosingCallable<V> {
        ClosingFuture<V> call(DeferredCloser deferredCloser) throws Exception;
    }

    public interface AsyncClosingFunction<T, U> {
        ClosingFuture<U> apply(DeferredCloser deferredCloser, @ParametricNullness T t2) throws Exception;
    }

    private static final class CloseableList extends IdentityHashMap<AutoCloseable, Executor> implements Closeable {
        private volatile boolean closed;
        private final DeferredCloser closer;

        @CheckForNull
        private volatile CountDownLatch whenClosed;

        private CloseableList() {
            this.closer = new DeferredCloser(this);
        }

        void add(@CheckForNull AutoCloseable autoCloseable, Executor executor) {
            Preconditions.checkNotNull(executor);
            if (autoCloseable == null) {
                return;
            }
            synchronized (this) {
                try {
                    if (this.closed) {
                        ClosingFuture.closeQuietly(autoCloseable, executor);
                    } else {
                        put(autoCloseable, executor);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        <V, U> FluentFuture<U> applyAsyncClosingFunction(AsyncClosingFunction<V, U> asyncClosingFunction, @ParametricNullness V v2) throws Exception {
            CloseableList closeableList = new CloseableList();
            try {
                ClosingFuture<U> closingFutureApply = asyncClosingFunction.apply(closeableList.closer, v2);
                closingFutureApply.becomeSubsumedInto(closeableList);
                return ((ClosingFuture) closingFutureApply).future;
            } finally {
                add(closeableList, MoreExecutors.directExecutor());
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        <V, U> ListenableFuture<U> applyClosingFunction(ClosingFunction<? super V, U> closingFunction, @ParametricNullness V v2) throws Exception {
            CloseableList closeableList = new CloseableList();
            try {
                return Futures.immediateFuture(closingFunction.apply(closeableList.closer, v2));
            } finally {
                add(closeableList, MoreExecutors.directExecutor());
            }
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            if (this.closed) {
                return;
            }
            synchronized (this) {
                try {
                    if (this.closed) {
                        return;
                    }
                    this.closed = true;
                    for (Map.Entry<AutoCloseable, Executor> entry : entrySet()) {
                        ClosingFuture.closeQuietly(entry.getKey(), entry.getValue());
                    }
                    clear();
                    if (this.whenClosed != null) {
                        this.whenClosed.countDown();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        CountDownLatch whenClosedCountDown() {
            if (this.closed) {
                return new CountDownLatch(0);
            }
            synchronized (this) {
                try {
                    if (this.closed) {
                        return new CountDownLatch(0);
                    }
                    Preconditions.checkState(this.whenClosed == null);
                    CountDownLatch countDownLatch = new CountDownLatch(1);
                    this.whenClosed = countDownLatch;
                    return countDownLatch;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public interface ClosingCallable<V> {
        @ParametricNullness
        V call(DeferredCloser deferredCloser) throws Exception;
    }

    public interface ClosingFunction<T, U> {
        @ParametricNullness
        U apply(DeferredCloser deferredCloser, @ParametricNullness T t2) throws Exception;
    }

    @DoNotMock("Use ClosingFuture.whenAllSucceed() or .whenAllComplete() instead.")
    public static class Combiner {

        /* renamed from: a, reason: collision with root package name */
        protected final ImmutableList f14831a;
        private final boolean allMustSucceed;
        private final CloseableList closeables;

        public interface AsyncCombiningCallable<V> {
            ClosingFuture<V> call(DeferredCloser deferredCloser, Peeker peeker) throws Exception;
        }

        public interface CombiningCallable<V> {
            @ParametricNullness
            V call(DeferredCloser deferredCloser, Peeker peeker) throws Exception;
        }

        private Futures.FutureCombiner<Object> futureCombiner() {
            return this.allMustSucceed ? Futures.whenAllSucceed(inputFutures()) : Futures.whenAllComplete(inputFutures());
        }

        private ImmutableList<FluentFuture<?>> inputFutures() {
            return FluentIterable.from(this.f14831a).transform(new Function() { // from class: com.google.common.util.concurrent.r
                @Override // com.google.common.base.Function
                public final Object apply(Object obj) {
                    return ClosingFuture.b((ClosingFuture) obj);
                }
            }).toList();
        }

        public <V> ClosingFuture<V> call(final CombiningCallable<V> combiningCallable, Executor executor) {
            ClosingFuture<V> closingFuture = new ClosingFuture<>(futureCombiner().call(new Callable<V>(this) { // from class: com.google.common.util.concurrent.ClosingFuture.Combiner.1

                /* renamed from: b, reason: collision with root package name */
                final /* synthetic */ Combiner f14833b;

                {
                    this.f14833b = this;
                }

                @Override // java.util.concurrent.Callable
                @ParametricNullness
                public V call() throws Exception {
                    return (V) new Peeker(this.f14833b.f14831a).call(combiningCallable, this.f14833b.closeables);
                }

                public String toString() {
                    return combiningCallable.toString();
                }
            }, executor));
            ((ClosingFuture) closingFuture).closeables.add(this.closeables, MoreExecutors.directExecutor());
            return closingFuture;
        }

        public <V> ClosingFuture<V> callAsync(final AsyncCombiningCallable<V> asyncCombiningCallable, Executor executor) {
            ClosingFuture<V> closingFuture = new ClosingFuture<>(futureCombiner().callAsync(new AsyncCallable<V>(this) { // from class: com.google.common.util.concurrent.ClosingFuture.Combiner.2

                /* renamed from: b, reason: collision with root package name */
                final /* synthetic */ Combiner f14835b;

                {
                    this.f14835b = this;
                }

                @Override // com.google.common.util.concurrent.AsyncCallable
                public ListenableFuture<V> call() throws Exception {
                    return new Peeker(this.f14835b.f14831a).callAsync(asyncCombiningCallable, this.f14835b.closeables);
                }

                public String toString() {
                    return asyncCombiningCallable.toString();
                }
            }, executor));
            ((ClosingFuture) closingFuture).closeables.add(this.closeables, MoreExecutors.directExecutor());
            return closingFuture;
        }

        private Combiner(boolean z2, Iterable<? extends ClosingFuture<?>> iterable) {
            this.closeables = new CloseableList();
            this.allMustSucceed = z2;
            this.f14831a = ImmutableList.copyOf(iterable);
            Iterator<? extends ClosingFuture<?>> it = iterable.iterator();
            while (it.hasNext()) {
                it.next().becomeSubsumedInto(this.closeables);
            }
        }
    }

    public static final class Combiner2<V1, V2> extends Combiner {
        private final ClosingFuture<V1> future1;
        private final ClosingFuture<V2> future2;

        public interface AsyncClosingFunction2<V1, V2, U> {
            ClosingFuture<U> apply(DeferredCloser deferredCloser, @ParametricNullness V1 v1, @ParametricNullness V2 v2) throws Exception;
        }

        public interface ClosingFunction2<V1, V2, U> {
            @ParametricNullness
            U apply(DeferredCloser deferredCloser, @ParametricNullness V1 v1, @ParametricNullness V2 v2) throws Exception;
        }

        public <U> ClosingFuture<U> call(final ClosingFunction2<V1, V2, U> closingFunction2, Executor executor) {
            return call(new Combiner.CombiningCallable<U>(this) { // from class: com.google.common.util.concurrent.ClosingFuture.Combiner2.1

                /* renamed from: b, reason: collision with root package name */
                final /* synthetic */ Combiner2 f14837b;

                {
                    this.f14837b = this;
                }

                /* JADX WARN: Multi-variable type inference failed */
                @Override // com.google.common.util.concurrent.ClosingFuture.Combiner.CombiningCallable
                @ParametricNullness
                public U call(DeferredCloser deferredCloser, Peeker peeker) throws Exception {
                    return (U) closingFunction2.apply(deferredCloser, peeker.getDone(this.f14837b.future1), peeker.getDone(this.f14837b.future2));
                }

                public String toString() {
                    return closingFunction2.toString();
                }
            }, executor);
        }

        public <U> ClosingFuture<U> callAsync(final AsyncClosingFunction2<V1, V2, U> asyncClosingFunction2, Executor executor) {
            return callAsync(new Combiner.AsyncCombiningCallable<U>(this) { // from class: com.google.common.util.concurrent.ClosingFuture.Combiner2.2

                /* renamed from: b, reason: collision with root package name */
                final /* synthetic */ Combiner2 f14839b;

                {
                    this.f14839b = this;
                }

                /* JADX WARN: Multi-variable type inference failed */
                @Override // com.google.common.util.concurrent.ClosingFuture.Combiner.AsyncCombiningCallable
                public ClosingFuture<U> call(DeferredCloser deferredCloser, Peeker peeker) throws Exception {
                    return asyncClosingFunction2.apply(deferredCloser, peeker.getDone(this.f14839b.future1), peeker.getDone(this.f14839b.future2));
                }

                public String toString() {
                    return asyncClosingFunction2.toString();
                }
            }, executor);
        }

        private Combiner2(ClosingFuture<V1> closingFuture, ClosingFuture<V2> closingFuture2) {
            super(true, ImmutableList.of((ClosingFuture<V2>) closingFuture, closingFuture2));
            this.future1 = closingFuture;
            this.future2 = closingFuture2;
        }
    }

    public static final class Combiner3<V1, V2, V3> extends Combiner {
        private final ClosingFuture<V1> future1;
        private final ClosingFuture<V2> future2;
        private final ClosingFuture<V3> future3;

        public interface AsyncClosingFunction3<V1, V2, V3, U> {
            ClosingFuture<U> apply(DeferredCloser deferredCloser, @ParametricNullness V1 v1, @ParametricNullness V2 v2, @ParametricNullness V3 v3) throws Exception;
        }

        public interface ClosingFunction3<V1, V2, V3, U> {
            @ParametricNullness
            U apply(DeferredCloser deferredCloser, @ParametricNullness V1 v1, @ParametricNullness V2 v2, @ParametricNullness V3 v3) throws Exception;
        }

        public <U> ClosingFuture<U> call(final ClosingFunction3<V1, V2, V3, U> closingFunction3, Executor executor) {
            return call(new Combiner.CombiningCallable<U>(this) { // from class: com.google.common.util.concurrent.ClosingFuture.Combiner3.1

                /* renamed from: b, reason: collision with root package name */
                final /* synthetic */ Combiner3 f14841b;

                {
                    this.f14841b = this;
                }

                /* JADX WARN: Multi-variable type inference failed */
                @Override // com.google.common.util.concurrent.ClosingFuture.Combiner.CombiningCallable
                @ParametricNullness
                public U call(DeferredCloser deferredCloser, Peeker peeker) throws Exception {
                    return (U) closingFunction3.apply(deferredCloser, peeker.getDone(this.f14841b.future1), peeker.getDone(this.f14841b.future2), peeker.getDone(this.f14841b.future3));
                }

                public String toString() {
                    return closingFunction3.toString();
                }
            }, executor);
        }

        public <U> ClosingFuture<U> callAsync(final AsyncClosingFunction3<V1, V2, V3, U> asyncClosingFunction3, Executor executor) {
            return callAsync(new Combiner.AsyncCombiningCallable<U>(this) { // from class: com.google.common.util.concurrent.ClosingFuture.Combiner3.2

                /* renamed from: b, reason: collision with root package name */
                final /* synthetic */ Combiner3 f14843b;

                {
                    this.f14843b = this;
                }

                /* JADX WARN: Multi-variable type inference failed */
                @Override // com.google.common.util.concurrent.ClosingFuture.Combiner.AsyncCombiningCallable
                public ClosingFuture<U> call(DeferredCloser deferredCloser, Peeker peeker) throws Exception {
                    return asyncClosingFunction3.apply(deferredCloser, peeker.getDone(this.f14843b.future1), peeker.getDone(this.f14843b.future2), peeker.getDone(this.f14843b.future3));
                }

                public String toString() {
                    return asyncClosingFunction3.toString();
                }
            }, executor);
        }

        private Combiner3(ClosingFuture<V1> closingFuture, ClosingFuture<V2> closingFuture2, ClosingFuture<V3> closingFuture3) {
            super(true, ImmutableList.of((ClosingFuture<V3>) closingFuture, (ClosingFuture<V3>) closingFuture2, closingFuture3));
            this.future1 = closingFuture;
            this.future2 = closingFuture2;
            this.future3 = closingFuture3;
        }
    }

    public static final class Combiner4<V1, V2, V3, V4> extends Combiner {
        private final ClosingFuture<V1> future1;
        private final ClosingFuture<V2> future2;
        private final ClosingFuture<V3> future3;
        private final ClosingFuture<V4> future4;

        public interface AsyncClosingFunction4<V1, V2, V3, V4, U> {
            ClosingFuture<U> apply(DeferredCloser deferredCloser, @ParametricNullness V1 v1, @ParametricNullness V2 v2, @ParametricNullness V3 v3, @ParametricNullness V4 v4) throws Exception;
        }

        public interface ClosingFunction4<V1, V2, V3, V4, U> {
            @ParametricNullness
            U apply(DeferredCloser deferredCloser, @ParametricNullness V1 v1, @ParametricNullness V2 v2, @ParametricNullness V3 v3, @ParametricNullness V4 v4) throws Exception;
        }

        public <U> ClosingFuture<U> call(final ClosingFunction4<V1, V2, V3, V4, U> closingFunction4, Executor executor) {
            return call(new Combiner.CombiningCallable<U>(this) { // from class: com.google.common.util.concurrent.ClosingFuture.Combiner4.1

                /* renamed from: b, reason: collision with root package name */
                final /* synthetic */ Combiner4 f14845b;

                {
                    this.f14845b = this;
                }

                /* JADX WARN: Multi-variable type inference failed */
                @Override // com.google.common.util.concurrent.ClosingFuture.Combiner.CombiningCallable
                @ParametricNullness
                public U call(DeferredCloser deferredCloser, Peeker peeker) throws Exception {
                    return (U) closingFunction4.apply(deferredCloser, peeker.getDone(this.f14845b.future1), peeker.getDone(this.f14845b.future2), peeker.getDone(this.f14845b.future3), peeker.getDone(this.f14845b.future4));
                }

                public String toString() {
                    return closingFunction4.toString();
                }
            }, executor);
        }

        public <U> ClosingFuture<U> callAsync(final AsyncClosingFunction4<V1, V2, V3, V4, U> asyncClosingFunction4, Executor executor) {
            return callAsync(new Combiner.AsyncCombiningCallable<U>(this) { // from class: com.google.common.util.concurrent.ClosingFuture.Combiner4.2

                /* renamed from: b, reason: collision with root package name */
                final /* synthetic */ Combiner4 f14847b;

                {
                    this.f14847b = this;
                }

                /* JADX WARN: Multi-variable type inference failed */
                @Override // com.google.common.util.concurrent.ClosingFuture.Combiner.AsyncCombiningCallable
                public ClosingFuture<U> call(DeferredCloser deferredCloser, Peeker peeker) throws Exception {
                    return asyncClosingFunction4.apply(deferredCloser, peeker.getDone(this.f14847b.future1), peeker.getDone(this.f14847b.future2), peeker.getDone(this.f14847b.future3), peeker.getDone(this.f14847b.future4));
                }

                public String toString() {
                    return asyncClosingFunction4.toString();
                }
            }, executor);
        }

        private Combiner4(ClosingFuture<V1> closingFuture, ClosingFuture<V2> closingFuture2, ClosingFuture<V3> closingFuture3, ClosingFuture<V4> closingFuture4) {
            super(true, ImmutableList.of((ClosingFuture<V4>) closingFuture, (ClosingFuture<V4>) closingFuture2, (ClosingFuture<V4>) closingFuture3, closingFuture4));
            this.future1 = closingFuture;
            this.future2 = closingFuture2;
            this.future3 = closingFuture3;
            this.future4 = closingFuture4;
        }
    }

    public static final class Combiner5<V1, V2, V3, V4, V5> extends Combiner {
        private final ClosingFuture<V1> future1;
        private final ClosingFuture<V2> future2;
        private final ClosingFuture<V3> future3;
        private final ClosingFuture<V4> future4;
        private final ClosingFuture<V5> future5;

        public interface AsyncClosingFunction5<V1, V2, V3, V4, V5, U> {
            ClosingFuture<U> apply(DeferredCloser deferredCloser, @ParametricNullness V1 v1, @ParametricNullness V2 v2, @ParametricNullness V3 v3, @ParametricNullness V4 v4, @ParametricNullness V5 v5) throws Exception;
        }

        public interface ClosingFunction5<V1, V2, V3, V4, V5, U> {
            @ParametricNullness
            U apply(DeferredCloser deferredCloser, @ParametricNullness V1 v1, @ParametricNullness V2 v2, @ParametricNullness V3 v3, @ParametricNullness V4 v4, @ParametricNullness V5 v5) throws Exception;
        }

        public <U> ClosingFuture<U> call(final ClosingFunction5<V1, V2, V3, V4, V5, U> closingFunction5, Executor executor) {
            return call(new Combiner.CombiningCallable<U>(this) { // from class: com.google.common.util.concurrent.ClosingFuture.Combiner5.1

                /* renamed from: b, reason: collision with root package name */
                final /* synthetic */ Combiner5 f14849b;

                {
                    this.f14849b = this;
                }

                /* JADX WARN: Multi-variable type inference failed */
                @Override // com.google.common.util.concurrent.ClosingFuture.Combiner.CombiningCallable
                @ParametricNullness
                public U call(DeferredCloser deferredCloser, Peeker peeker) throws Exception {
                    return (U) closingFunction5.apply(deferredCloser, peeker.getDone(this.f14849b.future1), peeker.getDone(this.f14849b.future2), peeker.getDone(this.f14849b.future3), peeker.getDone(this.f14849b.future4), peeker.getDone(this.f14849b.future5));
                }

                public String toString() {
                    return closingFunction5.toString();
                }
            }, executor);
        }

        public <U> ClosingFuture<U> callAsync(final AsyncClosingFunction5<V1, V2, V3, V4, V5, U> asyncClosingFunction5, Executor executor) {
            return callAsync(new Combiner.AsyncCombiningCallable<U>(this) { // from class: com.google.common.util.concurrent.ClosingFuture.Combiner5.2

                /* renamed from: b, reason: collision with root package name */
                final /* synthetic */ Combiner5 f14851b;

                {
                    this.f14851b = this;
                }

                /* JADX WARN: Multi-variable type inference failed */
                @Override // com.google.common.util.concurrent.ClosingFuture.Combiner.AsyncCombiningCallable
                public ClosingFuture<U> call(DeferredCloser deferredCloser, Peeker peeker) throws Exception {
                    return asyncClosingFunction5.apply(deferredCloser, peeker.getDone(this.f14851b.future1), peeker.getDone(this.f14851b.future2), peeker.getDone(this.f14851b.future3), peeker.getDone(this.f14851b.future4), peeker.getDone(this.f14851b.future5));
                }

                public String toString() {
                    return asyncClosingFunction5.toString();
                }
            }, executor);
        }

        private Combiner5(ClosingFuture<V1> closingFuture, ClosingFuture<V2> closingFuture2, ClosingFuture<V3> closingFuture3, ClosingFuture<V4> closingFuture4, ClosingFuture<V5> closingFuture5) {
            super(true, ImmutableList.of((ClosingFuture<V5>) closingFuture, (ClosingFuture<V5>) closingFuture2, (ClosingFuture<V5>) closingFuture3, (ClosingFuture<V5>) closingFuture4, closingFuture5));
            this.future1 = closingFuture;
            this.future2 = closingFuture2;
            this.future3 = closingFuture3;
            this.future4 = closingFuture4;
            this.future5 = closingFuture5;
        }
    }

    public static final class DeferredCloser {

        @RetainedWith
        private final CloseableList list;

        DeferredCloser(CloseableList closeableList) {
            this.list = closeableList;
        }

        @CanIgnoreReturnValue
        @ParametricNullness
        public <C extends AutoCloseable> C eventuallyClose(@ParametricNullness C c2, Executor executor) {
            Preconditions.checkNotNull(executor);
            if (c2 != null) {
                this.list.add(c2, executor);
            }
            return c2;
        }
    }

    public static final class Peeker {
        private volatile boolean beingCalled;
        private final ImmutableList<ClosingFuture<?>> futures;

        /* JADX INFO: Access modifiers changed from: private */
        @ParametricNullness
        public <V> V call(Combiner.CombiningCallable<V> combiningCallable, CloseableList closeableList) throws Exception {
            this.beingCalled = true;
            CloseableList closeableList2 = new CloseableList();
            try {
                return combiningCallable.call(closeableList2.closer, this);
            } finally {
                closeableList.add(closeableList2, MoreExecutors.directExecutor());
                this.beingCalled = false;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public <V> FluentFuture<V> callAsync(Combiner.AsyncCombiningCallable<V> asyncCombiningCallable, CloseableList closeableList) throws Exception {
            this.beingCalled = true;
            CloseableList closeableList2 = new CloseableList();
            try {
                ClosingFuture<V> closingFutureCall = asyncCombiningCallable.call(closeableList2.closer, this);
                closingFutureCall.becomeSubsumedInto(closeableList);
                return ((ClosingFuture) closingFutureCall).future;
            } finally {
                closeableList.add(closeableList2, MoreExecutors.directExecutor());
                this.beingCalled = false;
            }
        }

        @ParametricNullness
        public final <D> D getDone(ClosingFuture<D> closingFuture) throws ExecutionException {
            Preconditions.checkState(this.beingCalled);
            Preconditions.checkArgument(this.futures.contains(closingFuture));
            return (D) Futures.getDone(((ClosingFuture) closingFuture).future);
        }

        private Peeker(ImmutableList<ClosingFuture<?>> immutableList) {
            this.futures = (ImmutableList) Preconditions.checkNotNull(immutableList);
        }
    }

    enum State {
        OPEN,
        SUBSUMED,
        WILL_CLOSE,
        CLOSING,
        CLOSED,
        WILL_CREATE_VALUE_AND_CLOSER
    }

    public static final class ValueAndCloser<V> {
        private final ClosingFuture<? extends V> closingFuture;

        ValueAndCloser(ClosingFuture closingFuture) {
            this.closingFuture = (ClosingFuture) Preconditions.checkNotNull(closingFuture);
        }

        public void closeAsync() {
            this.closingFuture.close();
        }

        @ParametricNullness
        public V get() throws ExecutionException {
            return (V) Futures.getDone(((ClosingFuture) this.closingFuture).future);
        }
    }

    public interface ValueAndCloserConsumer<V> {
        void accept(ValueAndCloser<V> valueAndCloser);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ FluentFuture b(ClosingFuture closingFuture) {
        return closingFuture.future;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void becomeSubsumedInto(CloseableList closeableList) {
        checkAndUpdateState(State.OPEN, State.SUBSUMED);
        closeableList.add(this.closeables, MoreExecutors.directExecutor());
    }

    /* JADX WARN: Multi-variable type inference failed */
    private <X extends Throwable, W extends V> ClosingFuture<V> catchingAsyncMoreGeneric(Class<X> cls, final AsyncClosingFunction<? super X, W> asyncClosingFunction, Executor executor) {
        Preconditions.checkNotNull(asyncClosingFunction);
        return (ClosingFuture<V>) derive(this.future.catchingAsync(cls, new AsyncFunction<X, W>(this) { // from class: com.google.common.util.concurrent.ClosingFuture.8

            /* renamed from: b, reason: collision with root package name */
            final /* synthetic */ ClosingFuture f14829b;

            {
                this.f14829b = this;
            }

            public String toString() {
                return asyncClosingFunction.toString();
            }

            /* JADX WARN: Incorrect types in method signature: (TX;)Lcom/google/common/util/concurrent/ListenableFuture<TW;>; */
            @Override // com.google.common.util.concurrent.AsyncFunction
            public ListenableFuture apply(Throwable th) throws Exception {
                return this.f14829b.closeables.applyAsyncClosingFunction(asyncClosingFunction, th);
            }
        }, executor));
    }

    /* JADX WARN: Multi-variable type inference failed */
    private <X extends Throwable, W extends V> ClosingFuture<V> catchingMoreGeneric(Class<X> cls, final ClosingFunction<? super X, W> closingFunction, Executor executor) {
        Preconditions.checkNotNull(closingFunction);
        return (ClosingFuture<V>) derive(this.future.catchingAsync(cls, new AsyncFunction<X, W>(this) { // from class: com.google.common.util.concurrent.ClosingFuture.7

            /* renamed from: b, reason: collision with root package name */
            final /* synthetic */ ClosingFuture f14827b;

            {
                this.f14827b = this;
            }

            public String toString() {
                return closingFunction.toString();
            }

            /* JADX WARN: Incorrect types in method signature: (TX;)Lcom/google/common/util/concurrent/ListenableFuture<TW;>; */
            @Override // com.google.common.util.concurrent.AsyncFunction
            public ListenableFuture apply(Throwable th) throws Exception {
                return this.f14827b.closeables.applyClosingFunction(closingFunction, th);
            }
        }, executor));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkAndUpdateState(State state, State state2) {
        Preconditions.checkState(compareAndUpdateState(state, state2), "Expected state to be %s, but it was %s", state, state2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void close() {
        logger.a().log(Level.FINER, "closing {0}", this);
        this.closeables.close();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void closeQuietly(@CheckForNull final AutoCloseable autoCloseable, Executor executor) {
        if (autoCloseable == null) {
            return;
        }
        try {
            executor.execute(new Runnable() { // from class: com.google.common.util.concurrent.q
                @Override // java.lang.Runnable
                public final void run() throws Exception {
                    ClosingFuture.lambda$closeQuietly$0(autoCloseable);
                }
            });
        } catch (RejectedExecutionException e2) {
            LazyLogger lazyLogger = logger;
            Logger loggerA = lazyLogger.a();
            Level level = Level.WARNING;
            if (loggerA.isLoggable(level)) {
                lazyLogger.a().log(level, String.format("while submitting close to %s; will close inline", executor), (Throwable) e2);
            }
            closeQuietly(autoCloseable, MoreExecutors.directExecutor());
        }
    }

    private boolean compareAndUpdateState(State state, State state2) {
        return androidx.lifecycle.e.a(this.state, state, state2);
    }

    private <U> ClosingFuture<U> derive(FluentFuture<U> fluentFuture) {
        ClosingFuture<U> closingFuture = new ClosingFuture<>(fluentFuture);
        becomeSubsumedInto(closingFuture.closeables);
        return closingFuture;
    }

    @Deprecated
    public static <C extends AutoCloseable> ClosingFuture<C> eventuallyClosing(ListenableFuture<C> listenableFuture, final Executor executor) {
        Preconditions.checkNotNull(executor);
        ClosingFuture<C> closingFuture = new ClosingFuture<>(Futures.nonCancellationPropagating(listenableFuture));
        Futures.addCallback(listenableFuture, new FutureCallback<AutoCloseable>() { // from class: com.google.common.util.concurrent.ClosingFuture.3
            @Override // com.google.common.util.concurrent.FutureCallback
            public void onFailure(Throwable th) {
            }

            @Override // com.google.common.util.concurrent.FutureCallback
            public void onSuccess(@CheckForNull AutoCloseable autoCloseable) {
                ClosingFuture.this.closeables.closer.eventuallyClose(autoCloseable, executor);
            }
        }, MoreExecutors.directExecutor());
        return closingFuture;
    }

    public static <V> ClosingFuture<V> from(ListenableFuture<V> listenableFuture) {
        return new ClosingFuture<>(listenableFuture);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$closeQuietly$0(AutoCloseable autoCloseable) throws Exception {
        try {
            autoCloseable.close();
        } catch (Exception e2) {
            Platform.b(e2);
            logger.a().log(Level.WARNING, "thrown by close()", (Throwable) e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <C, V extends C> void provideValueAndCloser(ValueAndCloserConsumer<C> valueAndCloserConsumer, ClosingFuture<V> closingFuture) {
        valueAndCloserConsumer.accept(new ValueAndCloser<>(closingFuture));
    }

    public static <V> ClosingFuture<V> submit(final ClosingCallable<V> closingCallable, Executor executor) {
        Preconditions.checkNotNull(closingCallable);
        final CloseableList closeableList = new CloseableList();
        TrustedListenableFutureTask trustedListenableFutureTaskR = TrustedListenableFutureTask.r(new Callable<V>() { // from class: com.google.common.util.concurrent.ClosingFuture.1
            @Override // java.util.concurrent.Callable
            @ParametricNullness
            public V call() throws Exception {
                return (V) closingCallable.call(closeableList.closer);
            }

            public String toString() {
                return closingCallable.toString();
            }
        });
        executor.execute(trustedListenableFutureTaskR);
        return new ClosingFuture<>(trustedListenableFutureTaskR, closeableList);
    }

    public static <V> ClosingFuture<V> submitAsync(final AsyncClosingCallable<V> asyncClosingCallable, Executor executor) {
        Preconditions.checkNotNull(asyncClosingCallable);
        final CloseableList closeableList = new CloseableList();
        TrustedListenableFutureTask trustedListenableFutureTaskP = TrustedListenableFutureTask.p(new AsyncCallable<V>() { // from class: com.google.common.util.concurrent.ClosingFuture.2
            @Override // com.google.common.util.concurrent.AsyncCallable
            public ListenableFuture<V> call() throws Exception {
                CloseableList closeableList2 = new CloseableList();
                try {
                    ClosingFuture<V> closingFutureCall = asyncClosingCallable.call(closeableList2.closer);
                    closingFutureCall.becomeSubsumedInto(closeableList);
                    return ((ClosingFuture) closingFutureCall).future;
                } finally {
                    closeableList.add(closeableList2, MoreExecutors.directExecutor());
                }
            }

            public String toString() {
                return asyncClosingCallable.toString();
            }
        });
        executor.execute(trustedListenableFutureTaskP);
        return new ClosingFuture<>(trustedListenableFutureTaskP, closeableList);
    }

    public static Combiner whenAllComplete(Iterable<? extends ClosingFuture<?>> iterable) {
        return new Combiner(false, iterable);
    }

    public static Combiner whenAllSucceed(Iterable<? extends ClosingFuture<?>> iterable) {
        return new Combiner(true, iterable);
    }

    public static <V, U> AsyncClosingFunction<V, U> withoutCloser(final AsyncFunction<V, U> asyncFunction) {
        Preconditions.checkNotNull(asyncFunction);
        return new AsyncClosingFunction<V, U>() { // from class: com.google.common.util.concurrent.ClosingFuture.6
            @Override // com.google.common.util.concurrent.ClosingFuture.AsyncClosingFunction
            public ClosingFuture<U> apply(DeferredCloser deferredCloser, V v2) throws Exception {
                return ClosingFuture.from(asyncFunction.apply(v2));
            }
        };
    }

    @CanIgnoreReturnValue
    public boolean cancel(boolean z2) {
        logger.a().log(Level.FINER, "cancelling {0}", this);
        boolean zCancel = this.future.cancel(z2);
        if (zCancel) {
            close();
        }
        return zCancel;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <X extends Throwable> ClosingFuture<V> catching(Class<X> cls, ClosingFunction<? super X, ? extends V> closingFunction, Executor executor) {
        return catchingMoreGeneric(cls, closingFunction, executor);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <X extends Throwable> ClosingFuture<V> catchingAsync(Class<X> cls, AsyncClosingFunction<? super X, ? extends V> asyncClosingFunction, Executor executor) {
        return catchingAsyncMoreGeneric(cls, asyncClosingFunction, executor);
    }

    protected void finalize() {
        if (this.state.get().equals(State.OPEN)) {
            logger.a().log(Level.SEVERE, "Uh oh! An open ClosingFuture has leaked and will close: {0}", this);
            finishToFuture();
        }
    }

    public FluentFuture<V> finishToFuture() {
        if (compareAndUpdateState(State.OPEN, State.WILL_CLOSE)) {
            logger.a().log(Level.FINER, "will close {0}", this);
            this.future.addListener(new Runnable() { // from class: com.google.common.util.concurrent.ClosingFuture.9
                @Override // java.lang.Runnable
                public void run() {
                    ClosingFuture closingFuture = ClosingFuture.this;
                    State state = State.WILL_CLOSE;
                    State state2 = State.CLOSING;
                    closingFuture.checkAndUpdateState(state, state2);
                    ClosingFuture.this.close();
                    ClosingFuture.this.checkAndUpdateState(state2, State.CLOSED);
                }
            }, MoreExecutors.directExecutor());
        } else {
            int iOrdinal = this.state.get().ordinal();
            if (iOrdinal == 0) {
                throw new AssertionError();
            }
            if (iOrdinal == 1) {
                throw new IllegalStateException("Cannot call finishToFuture() after deriving another step");
            }
            if (iOrdinal == 2 || iOrdinal == 3 || iOrdinal == 4) {
                throw new IllegalStateException("Cannot call finishToFuture() twice");
            }
            if (iOrdinal == 5) {
                throw new IllegalStateException("Cannot call finishToFuture() after calling finishToValueAndCloser()");
            }
        }
        return this.future;
    }

    public void finishToValueAndCloser(final ValueAndCloserConsumer<? super V> valueAndCloserConsumer, Executor executor) {
        Preconditions.checkNotNull(valueAndCloserConsumer);
        if (compareAndUpdateState(State.OPEN, State.WILL_CREATE_VALUE_AND_CLOSER)) {
            this.future.addListener(new Runnable(this) { // from class: com.google.common.util.concurrent.ClosingFuture.10

                /* renamed from: b, reason: collision with root package name */
                final /* synthetic */ ClosingFuture f14816b;

                {
                    this.f14816b = this;
                }

                @Override // java.lang.Runnable
                public void run() {
                    ClosingFuture.provideValueAndCloser(valueAndCloserConsumer, this.f14816b);
                }
            }, executor);
            return;
        }
        int iOrdinal = this.state.get().ordinal();
        if (iOrdinal == 1) {
            throw new IllegalStateException("Cannot call finishToValueAndCloser() after deriving another step");
        }
        if (iOrdinal == 2 || iOrdinal == 3 || iOrdinal == 4) {
            throw new IllegalStateException("Cannot call finishToValueAndCloser() after calling finishToFuture()");
        }
        if (iOrdinal == 5) {
            throw new IllegalStateException("Cannot call finishToValueAndCloser() twice");
        }
        throw new AssertionError(this.state);
    }

    public ListenableFuture<?> statusFuture() {
        return Futures.nonCancellationPropagating(this.future.transform(Functions.constant(null), MoreExecutors.directExecutor()));
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("state", this.state.get()).addValue(this.future).toString();
    }

    public <U> ClosingFuture<U> transform(final ClosingFunction<? super V, U> closingFunction, Executor executor) {
        Preconditions.checkNotNull(closingFunction);
        return derive(this.future.transformAsync(new AsyncFunction<V, U>(this) { // from class: com.google.common.util.concurrent.ClosingFuture.4

            /* renamed from: b, reason: collision with root package name */
            final /* synthetic */ ClosingFuture f14822b;

            {
                this.f14822b = this;
            }

            @Override // com.google.common.util.concurrent.AsyncFunction
            public ListenableFuture<U> apply(V v2) throws Exception {
                return this.f14822b.closeables.applyClosingFunction(closingFunction, v2);
            }

            public String toString() {
                return closingFunction.toString();
            }
        }, executor));
    }

    public <U> ClosingFuture<U> transformAsync(final AsyncClosingFunction<? super V, U> asyncClosingFunction, Executor executor) {
        Preconditions.checkNotNull(asyncClosingFunction);
        return derive(this.future.transformAsync(new AsyncFunction<V, U>(this) { // from class: com.google.common.util.concurrent.ClosingFuture.5

            /* renamed from: b, reason: collision with root package name */
            final /* synthetic */ ClosingFuture f14824b;

            {
                this.f14824b = this;
            }

            @Override // com.google.common.util.concurrent.AsyncFunction
            public ListenableFuture<U> apply(V v2) throws Exception {
                return this.f14824b.closeables.applyAsyncClosingFunction(asyncClosingFunction, v2);
            }

            public String toString() {
                return asyncClosingFunction.toString();
            }
        }, executor));
    }

    private ClosingFuture(ListenableFuture<V> listenableFuture) {
        this(listenableFuture, new CloseableList());
    }

    public static Combiner whenAllComplete(ClosingFuture<?> closingFuture, ClosingFuture<?>... closingFutureArr) {
        return whenAllComplete(Lists.asList(closingFuture, closingFutureArr));
    }

    public static <V1, V2> Combiner2<V1, V2> whenAllSucceed(ClosingFuture<V1> closingFuture, ClosingFuture<V2> closingFuture2) {
        return new Combiner2<>(closingFuture2);
    }

    private ClosingFuture(ListenableFuture<V> listenableFuture, CloseableList closeableList) {
        this.state = new AtomicReference<>(State.OPEN);
        this.future = FluentFuture.from(listenableFuture);
        this.closeables = closeableList;
    }

    public static <V1, V2, V3> Combiner3<V1, V2, V3> whenAllSucceed(ClosingFuture<V1> closingFuture, ClosingFuture<V2> closingFuture2, ClosingFuture<V3> closingFuture3) {
        return new Combiner3<>(closingFuture2, closingFuture3);
    }

    public static <V1, V2, V3, V4> Combiner4<V1, V2, V3, V4> whenAllSucceed(ClosingFuture<V1> closingFuture, ClosingFuture<V2> closingFuture2, ClosingFuture<V3> closingFuture3, ClosingFuture<V4> closingFuture4) {
        return new Combiner4<>(closingFuture2, closingFuture3, closingFuture4);
    }

    public static <V1, V2, V3, V4, V5> Combiner5<V1, V2, V3, V4, V5> whenAllSucceed(ClosingFuture<V1> closingFuture, ClosingFuture<V2> closingFuture2, ClosingFuture<V3> closingFuture3, ClosingFuture<V4> closingFuture4, ClosingFuture<V5> closingFuture5) {
        return new Combiner5<>(closingFuture2, closingFuture3, closingFuture4, closingFuture5);
    }

    public static Combiner whenAllSucceed(ClosingFuture<?> closingFuture, ClosingFuture<?> closingFuture2, ClosingFuture<?> closingFuture3, ClosingFuture<?> closingFuture4, ClosingFuture<?> closingFuture5, ClosingFuture<?> closingFuture6, ClosingFuture<?>... closingFutureArr) {
        return whenAllSucceed(FluentIterable.of(closingFuture, closingFuture2, closingFuture3, closingFuture4, closingFuture5, closingFuture6).append(closingFutureArr));
    }
}
