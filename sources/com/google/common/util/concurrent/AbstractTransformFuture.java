package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.FluentFuture;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

@ElementTypesAreNonnullByDefault
@GwtCompatible
/* loaded from: classes3.dex */
abstract class AbstractTransformFuture<I, O, F, T> extends FluentFuture.TrustedFuture<O> implements Runnable {

    /* renamed from: c, reason: collision with root package name */
    ListenableFuture f14808c;

    /* renamed from: d, reason: collision with root package name */
    Object f14809d;

    private static final class AsyncTransformFuture<I, O> extends AbstractTransformFuture<I, O, AsyncFunction<? super I, ? extends O>, ListenableFuture<? extends O>> {
        AsyncTransformFuture(ListenableFuture listenableFuture, AsyncFunction asyncFunction) {
            super(listenableFuture, asyncFunction);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.util.concurrent.AbstractTransformFuture
        /* renamed from: t, reason: merged with bridge method [inline-methods] */
        public ListenableFuture r(AsyncFunction asyncFunction, Object obj) throws Exception {
            ListenableFuture<O> listenableFutureApply = asyncFunction.apply(obj);
            Preconditions.checkNotNull(listenableFutureApply, "AsyncFunction.apply returned null instead of a Future. Did you mean to return immediateFuture(null)? %s", asyncFunction);
            return listenableFutureApply;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.util.concurrent.AbstractTransformFuture
        /* renamed from: u, reason: merged with bridge method [inline-methods] */
        public void s(ListenableFuture listenableFuture) {
            setFuture(listenableFuture);
        }
    }

    private static final class TransformFuture<I, O> extends AbstractTransformFuture<I, O, Function<? super I, ? extends O>, O> {
        TransformFuture(ListenableFuture listenableFuture, Function function) {
            super(listenableFuture, function);
        }

        @Override // com.google.common.util.concurrent.AbstractTransformFuture
        void s(Object obj) {
            set(obj);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.util.concurrent.AbstractTransformFuture
        /* renamed from: t, reason: merged with bridge method [inline-methods] */
        public Object r(Function function, Object obj) {
            return function.apply(obj);
        }
    }

    AbstractTransformFuture(ListenableFuture listenableFuture, Object obj) {
        this.f14808c = (ListenableFuture) Preconditions.checkNotNull(listenableFuture);
        this.f14809d = Preconditions.checkNotNull(obj);
    }

    static ListenableFuture p(ListenableFuture listenableFuture, Function function, Executor executor) {
        Preconditions.checkNotNull(function);
        TransformFuture transformFuture = new TransformFuture(listenableFuture, function);
        listenableFuture.addListener(transformFuture, MoreExecutors.c(executor, transformFuture));
        return transformFuture;
    }

    static ListenableFuture q(ListenableFuture listenableFuture, AsyncFunction asyncFunction, Executor executor) {
        Preconditions.checkNotNull(executor);
        AsyncTransformFuture asyncTransformFuture = new AsyncTransformFuture(listenableFuture, asyncFunction);
        listenableFuture.addListener(asyncTransformFuture, MoreExecutors.c(executor, asyncTransformFuture));
        return asyncTransformFuture;
    }

    @Override // com.google.common.util.concurrent.AbstractFuture
    protected final void k() {
        m(this.f14808c);
        this.f14808c = null;
        this.f14809d = null;
    }

    @Override // com.google.common.util.concurrent.AbstractFuture
    protected String n() {
        String str;
        ListenableFuture listenableFuture = this.f14808c;
        Object obj = this.f14809d;
        String strN = super.n();
        if (listenableFuture != null) {
            str = "inputFuture=[" + listenableFuture + "], ";
        } else {
            str = "";
        }
        if (obj != null) {
            return str + "function=[" + obj + "]";
        }
        if (strN == null) {
            return null;
        }
        return str + strN;
    }

    abstract Object r(Object obj, Object obj2);

    @Override // java.lang.Runnable
    public final void run() {
        ListenableFuture listenableFuture = this.f14808c;
        Object obj = this.f14809d;
        if ((isCancelled() | (listenableFuture == null)) || (obj == null)) {
            return;
        }
        this.f14808c = null;
        if (listenableFuture.isCancelled()) {
            setFuture(listenableFuture);
            return;
        }
        try {
            try {
                Object objR = r(obj, Futures.getDone(listenableFuture));
                this.f14809d = null;
                s(objR);
            } catch (Throwable th) {
                try {
                    Platform.b(th);
                    setException(th);
                } finally {
                    this.f14809d = null;
                }
            }
        } catch (Error e2) {
            setException(e2);
        } catch (CancellationException unused) {
            cancel(false);
        } catch (ExecutionException e3) {
            setException(e3.getCause());
        } catch (Exception e4) {
            setException(e4);
        }
    }

    abstract void s(Object obj);
}
