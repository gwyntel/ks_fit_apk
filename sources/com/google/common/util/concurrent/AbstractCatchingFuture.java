package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.FluentFuture;
import com.google.common.util.concurrent.internal.InternalFutureFailureAccess;
import com.google.common.util.concurrent.internal.InternalFutures;
import java.lang.Throwable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

@ElementTypesAreNonnullByDefault
@GwtCompatible
/* loaded from: classes3.dex */
abstract class AbstractCatchingFuture<V, X extends Throwable, F, T> extends FluentFuture.TrustedFuture<V> implements Runnable {

    /* renamed from: c, reason: collision with root package name */
    ListenableFuture f14749c;

    /* renamed from: d, reason: collision with root package name */
    Class f14750d;

    /* renamed from: e, reason: collision with root package name */
    Object f14751e;

    private static final class AsyncCatchingFuture<V, X extends Throwable> extends AbstractCatchingFuture<V, X, AsyncFunction<? super X, ? extends V>, ListenableFuture<? extends V>> {
        AsyncCatchingFuture(ListenableFuture listenableFuture, Class cls, AsyncFunction asyncFunction) {
            super(listenableFuture, cls, asyncFunction);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.util.concurrent.AbstractCatchingFuture
        /* renamed from: t, reason: merged with bridge method [inline-methods] */
        public ListenableFuture r(AsyncFunction asyncFunction, Throwable th) throws Exception {
            ListenableFuture listenableFutureApply = asyncFunction.apply(th);
            Preconditions.checkNotNull(listenableFutureApply, "AsyncFunction.apply returned null instead of a Future. Did you mean to return immediateFuture(null)? %s", asyncFunction);
            return listenableFutureApply;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.util.concurrent.AbstractCatchingFuture
        /* renamed from: u, reason: merged with bridge method [inline-methods] */
        public void s(ListenableFuture listenableFuture) {
            setFuture(listenableFuture);
        }
    }

    private static final class CatchingFuture<V, X extends Throwable> extends AbstractCatchingFuture<V, X, Function<? super X, ? extends V>, V> {
        CatchingFuture(ListenableFuture listenableFuture, Class cls, Function function) {
            super(listenableFuture, cls, function);
        }

        @Override // com.google.common.util.concurrent.AbstractCatchingFuture
        void s(Object obj) {
            set(obj);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.util.concurrent.AbstractCatchingFuture
        /* renamed from: t, reason: merged with bridge method [inline-methods] */
        public Object r(Function function, Throwable th) {
            return function.apply(th);
        }
    }

    AbstractCatchingFuture(ListenableFuture listenableFuture, Class cls, Object obj) {
        this.f14749c = (ListenableFuture) Preconditions.checkNotNull(listenableFuture);
        this.f14750d = (Class) Preconditions.checkNotNull(cls);
        this.f14751e = Preconditions.checkNotNull(obj);
    }

    static ListenableFuture p(ListenableFuture listenableFuture, Class cls, Function function, Executor executor) {
        CatchingFuture catchingFuture = new CatchingFuture(listenableFuture, cls, function);
        listenableFuture.addListener(catchingFuture, MoreExecutors.c(executor, catchingFuture));
        return catchingFuture;
    }

    static ListenableFuture q(ListenableFuture listenableFuture, Class cls, AsyncFunction asyncFunction, Executor executor) {
        AsyncCatchingFuture asyncCatchingFuture = new AsyncCatchingFuture(listenableFuture, cls, asyncFunction);
        listenableFuture.addListener(asyncCatchingFuture, MoreExecutors.c(executor, asyncCatchingFuture));
        return asyncCatchingFuture;
    }

    @Override // com.google.common.util.concurrent.AbstractFuture
    protected final void k() {
        m(this.f14749c);
        this.f14749c = null;
        this.f14750d = null;
        this.f14751e = null;
    }

    @Override // com.google.common.util.concurrent.AbstractFuture
    protected String n() {
        String str;
        ListenableFuture listenableFuture = this.f14749c;
        Class cls = this.f14750d;
        Object obj = this.f14751e;
        String strN = super.n();
        if (listenableFuture != null) {
            str = "inputFuture=[" + listenableFuture + "], ";
        } else {
            str = "";
        }
        if (cls == null || obj == null) {
            if (strN == null) {
                return null;
            }
            return str + strN;
        }
        return str + "exceptionType=[" + cls + "], fallback=[" + obj + "]";
    }

    abstract Object r(Object obj, Throwable th);

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.lang.Runnable
    public final void run() {
        ListenableFuture listenableFuture = this.f14749c;
        Class cls = this.f14750d;
        Object obj = this.f14751e;
        if (((obj == null) || ((listenableFuture == 0) | (cls == null))) || isCancelled()) {
            return;
        }
        this.f14749c = null;
        try {
            th = listenableFuture instanceof InternalFutureFailureAccess ? InternalFutures.tryInternalFastPathGetFailure((InternalFutureFailureAccess) listenableFuture) : null;
        } catch (ExecutionException e2) {
            Throwable cause = e2.getCause();
            if (cause == null) {
                cause = new NullPointerException("Future type " + listenableFuture.getClass() + " threw " + e2.getClass() + " without a cause");
            }
            th = cause;
        } catch (Throwable th) {
            th = th;
        }
        Object done = th == null ? Futures.getDone(listenableFuture) : null;
        if (th == null) {
            set(NullnessCasts.a(done));
            return;
        }
        if (!Platform.a(th, cls)) {
            setFuture(listenableFuture);
            return;
        }
        try {
            Object objR = r(obj, th);
            this.f14750d = null;
            this.f14751e = null;
            s(objR);
        } catch (Throwable th2) {
            try {
                Platform.b(th2);
                setException(th2);
            } finally {
                this.f14750d = null;
                this.f14751e = null;
            }
        }
    }

    abstract void s(Object obj);
}
