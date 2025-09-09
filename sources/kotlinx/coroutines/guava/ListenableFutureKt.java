package kotlinx.coroutines.guava;

import androidx.exifinterface.media.ExifInterface;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.Uninterruptibles;
import com.google.common.util.concurrent.internal.InternalFutureFailureAccess;
import com.google.common.util.concurrent.internal.InternalFutures;
import com.umeng.analytics.pro.f;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.sequences.Sequence;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.ChildHandle;
import kotlinx.coroutines.ChildJob;
import kotlinx.coroutines.CompletableDeferred;
import kotlinx.coroutines.CompletableDeferredKt;
import kotlinx.coroutines.CoroutineContextKt;
import kotlinx.coroutines.CoroutineExceptionHandlerKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.Deferred;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.ExperimentalCoroutinesApi;
import kotlinx.coroutines.InternalCoroutinesApi;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.selects.SelectClause0;
import kotlinx.coroutines.selects.SelectClause1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000@\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\u0018\u0002\n\u0000\u001a\u001c\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003\u001a\u001c\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0001\u001a!\u0010\u0005\u001a\u0002H\u0002\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\u0006\u001a[\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\u0004\b\u0000\u0010\u0002*\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\f2'\u0010\r\u001a#\b\u0001\u0012\u0004\u0012\u00020\b\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u000f\u0012\u0006\u0012\u0004\u0018\u00010\u00100\u000e¢\u0006\u0002\b\u0011ø\u0001\u0000¢\u0006\u0002\u0010\u0012\u001a\f\u0010\u0013\u001a\u00020\u0014*\u00020\u0015H\u0002\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0016"}, d2 = {"asDeferred", "Lkotlinx/coroutines/Deferred;", ExifInterface.GPS_DIRECTION_TRUE, "Lcom/google/common/util/concurrent/ListenableFuture;", "asListenableFuture", "await", "(Lcom/google/common/util/concurrent/ListenableFuture;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "future", "Lkotlinx/coroutines/CoroutineScope;", f.X, "Lkotlin/coroutines/CoroutineContext;", "start", "Lkotlinx/coroutines/CoroutineStart;", "block", "Lkotlin/Function2;", "Lkotlin/coroutines/Continuation;", "", "Lkotlin/ExtensionFunctionType;", "(Lkotlinx/coroutines/CoroutineScope;Lkotlin/coroutines/CoroutineContext;Lkotlinx/coroutines/CoroutineStart;Lkotlin/jvm/functions/Function2;)Lcom/google/common/util/concurrent/ListenableFuture;", "nonNullCause", "", "Ljava/util/concurrent/ExecutionException;", "kotlinx-coroutines-guava"}, k = 2, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nListenableFuture.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ListenableFuture.kt\nkotlinx/coroutines/guava/ListenableFutureKt\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 CancellableContinuation.kt\nkotlinx/coroutines/CancellableContinuationKt\n*L\n1#1,509:1\n1#2:510\n314#3,11:511\n*S KotlinDebug\n*F\n+ 1 ListenableFuture.kt\nkotlinx/coroutines/guava/ListenableFutureKt\n*L\n242#1:511,11\n*E\n"})
/* loaded from: classes5.dex */
public final class ListenableFutureKt {
    /* JADX WARN: Multi-variable type inference failed */
    @NotNull
    public static final <T> Deferred<T> asDeferred(@NotNull final ListenableFuture<T> listenableFuture) {
        CompletableDeferred completableDeferredCompletableDeferred$default;
        Throwable thTryInternalFastPathGetFailure;
        if ((listenableFuture instanceof InternalFutureFailureAccess) && (thTryInternalFastPathGetFailure = InternalFutures.tryInternalFastPathGetFailure((InternalFutureFailureAccess) listenableFuture)) != null) {
            CompletableDeferred completableDeferredCompletableDeferred$default2 = CompletableDeferredKt.CompletableDeferred$default(null, 1, null);
            completableDeferredCompletableDeferred$default2.completeExceptionally(thTryInternalFastPathGetFailure);
            return completableDeferredCompletableDeferred$default2;
        }
        if (!listenableFuture.isDone()) {
            final CompletableDeferred completableDeferredCompletableDeferred$default3 = CompletableDeferredKt.CompletableDeferred$default(null, 1, null);
            Futures.addCallback(listenableFuture, new FutureCallback<T>() { // from class: kotlinx.coroutines.guava.ListenableFutureKt.asDeferred.4
                @Override // com.google.common.util.concurrent.FutureCallback
                public void onFailure(@NotNull Throwable t2) {
                    Object objM837constructorimpl;
                    CompletableDeferred completableDeferred = completableDeferredCompletableDeferred$default3;
                    try {
                        Result.Companion companion = Result.INSTANCE;
                        objM837constructorimpl = Result.m837constructorimpl(Boolean.valueOf(completableDeferred.completeExceptionally(t2)));
                    } catch (Throwable th) {
                        Result.Companion companion2 = Result.INSTANCE;
                        objM837constructorimpl = Result.m837constructorimpl(ResultKt.createFailure(th));
                    }
                    Throwable thM840exceptionOrNullimpl = Result.m840exceptionOrNullimpl(objM837constructorimpl);
                    if (thM840exceptionOrNullimpl != null) {
                        CoroutineExceptionHandlerKt.handleCoroutineException(EmptyCoroutineContext.INSTANCE, thM840exceptionOrNullimpl);
                    }
                }

                @Override // com.google.common.util.concurrent.FutureCallback
                public void onSuccess(T result) {
                    Object objM837constructorimpl;
                    CompletableDeferred completableDeferred = completableDeferredCompletableDeferred$default3;
                    try {
                        Result.Companion companion = Result.INSTANCE;
                        objM837constructorimpl = Result.m837constructorimpl(Boolean.valueOf(completableDeferred.complete(result)));
                    } catch (Throwable th) {
                        Result.Companion companion2 = Result.INSTANCE;
                        objM837constructorimpl = Result.m837constructorimpl(ResultKt.createFailure(th));
                    }
                    Throwable thM840exceptionOrNullimpl = Result.m840exceptionOrNullimpl(objM837constructorimpl);
                    if (thM840exceptionOrNullimpl != null) {
                        CoroutineExceptionHandlerKt.handleCoroutineException(EmptyCoroutineContext.INSTANCE, thM840exceptionOrNullimpl);
                    }
                }
            }, MoreExecutors.directExecutor());
            completableDeferredCompletableDeferred$default3.invokeOnCompletion(new Function1<Throwable, Unit>() { // from class: kotlinx.coroutines.guava.ListenableFutureKt.asDeferred.5
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                    invoke2(th);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(@Nullable Throwable th) {
                    listenableFuture.cancel(false);
                }
            });
            return new Deferred<T>() { // from class: kotlinx.coroutines.guava.ListenableFutureKt.asDeferred.6
                @Override // kotlinx.coroutines.Job
                @InternalCoroutinesApi
                @NotNull
                public ChildHandle attachChild(@NotNull ChildJob child) {
                    return completableDeferredCompletableDeferred$default3.attachChild(child);
                }

                @Override // kotlinx.coroutines.Deferred
                @Nullable
                public Object await(@NotNull Continuation<? super T> continuation) {
                    return completableDeferredCompletableDeferred$default3.await(continuation);
                }

                @Override // kotlinx.coroutines.Job
                @Deprecated(level = DeprecationLevel.HIDDEN, message = "Since 1.2.0, binary compatibility with versions <= 1.1.x")
                public /* synthetic */ void cancel() {
                    completableDeferredCompletableDeferred$default3.cancel();
                }

                @Override // kotlin.coroutines.CoroutineContext.Element, kotlin.coroutines.CoroutineContext
                public <R> R fold(R initial, @NotNull Function2<? super R, ? super CoroutineContext.Element, ? extends R> operation) {
                    return (R) completableDeferredCompletableDeferred$default3.fold(initial, operation);
                }

                @Override // kotlin.coroutines.CoroutineContext.Element, kotlin.coroutines.CoroutineContext
                @Nullable
                public <E extends CoroutineContext.Element> E get(@NotNull CoroutineContext.Key<E> key) {
                    return (E) completableDeferredCompletableDeferred$default3.get(key);
                }

                @Override // kotlinx.coroutines.Job
                @InternalCoroutinesApi
                @NotNull
                public CancellationException getCancellationException() {
                    return completableDeferredCompletableDeferred$default3.getCancellationException();
                }

                @Override // kotlinx.coroutines.Job
                @NotNull
                public Sequence<Job> getChildren() {
                    return completableDeferredCompletableDeferred$default3.getChildren();
                }

                @Override // kotlinx.coroutines.Deferred
                @ExperimentalCoroutinesApi
                public T getCompleted() {
                    return completableDeferredCompletableDeferred$default3.getCompleted();
                }

                @Override // kotlinx.coroutines.Deferred
                @ExperimentalCoroutinesApi
                @Nullable
                public Throwable getCompletionExceptionOrNull() {
                    return completableDeferredCompletableDeferred$default3.getCompletionExceptionOrNull();
                }

                @Override // kotlin.coroutines.CoroutineContext.Element
                @NotNull
                public CoroutineContext.Key<?> getKey() {
                    return completableDeferredCompletableDeferred$default3.getKey();
                }

                @Override // kotlinx.coroutines.Deferred
                @NotNull
                public SelectClause1<T> getOnAwait() {
                    return completableDeferredCompletableDeferred$default3.getOnAwait();
                }

                @Override // kotlinx.coroutines.Job
                @NotNull
                public SelectClause0 getOnJoin() {
                    return completableDeferredCompletableDeferred$default3.getOnJoin();
                }

                @Override // kotlinx.coroutines.Job
                @Nullable
                public Job getParent() {
                    return completableDeferredCompletableDeferred$default3.getParent();
                }

                @Override // kotlinx.coroutines.Job
                @NotNull
                public DisposableHandle invokeOnCompletion(@NotNull Function1<? super Throwable, Unit> handler) {
                    return completableDeferredCompletableDeferred$default3.invokeOnCompletion(handler);
                }

                @Override // kotlinx.coroutines.Job
                public boolean isActive() {
                    return completableDeferredCompletableDeferred$default3.isActive();
                }

                @Override // kotlinx.coroutines.Job
                public boolean isCancelled() {
                    return completableDeferredCompletableDeferred$default3.isCancelled();
                }

                @Override // kotlinx.coroutines.Job
                public boolean isCompleted() {
                    return completableDeferredCompletableDeferred$default3.isCompleted();
                }

                @Override // kotlinx.coroutines.Job
                @Nullable
                public Object join(@NotNull Continuation<? super Unit> continuation) {
                    return completableDeferredCompletableDeferred$default3.join(continuation);
                }

                @Override // kotlin.coroutines.CoroutineContext.Element, kotlin.coroutines.CoroutineContext
                @NotNull
                public CoroutineContext minusKey(@NotNull CoroutineContext.Key<?> key) {
                    return completableDeferredCompletableDeferred$default3.minusKey(key);
                }

                @Override // kotlin.coroutines.CoroutineContext
                @NotNull
                public CoroutineContext plus(@NotNull CoroutineContext context) {
                    return completableDeferredCompletableDeferred$default3.plus(context);
                }

                @Override // kotlinx.coroutines.Job
                public boolean start() {
                    return completableDeferredCompletableDeferred$default3.start();
                }

                @Override // kotlinx.coroutines.Job
                public void cancel(@Nullable CancellationException cause) {
                    completableDeferredCompletableDeferred$default3.cancel(cause);
                }

                @Override // kotlinx.coroutines.Job
                @InternalCoroutinesApi
                @NotNull
                public DisposableHandle invokeOnCompletion(boolean onCancelling, boolean invokeImmediately, @NotNull Function1<? super Throwable, Unit> handler) {
                    return completableDeferredCompletableDeferred$default3.invokeOnCompletion(onCancelling, invokeImmediately, handler);
                }

                @Override // kotlinx.coroutines.Job
                @Deprecated(level = DeprecationLevel.ERROR, message = "Operator '+' on two Job objects is meaningless. Job is a coroutine context element and `+` is a set-sum operator for coroutine contexts. The job to the right of `+` just replaces the job the left of `+`.")
                @NotNull
                public Job plus(@NotNull Job other) {
                    return completableDeferredCompletableDeferred$default3.plus(other);
                }

                @Override // kotlinx.coroutines.Job
                @Deprecated(level = DeprecationLevel.HIDDEN, message = "Since 1.2.0, binary compatibility with versions <= 1.1.x")
                public /* synthetic */ boolean cancel(Throwable cause) {
                    return completableDeferredCompletableDeferred$default3.cancel(cause);
                }
            };
        }
        try {
            return CompletableDeferredKt.CompletableDeferred(Uninterruptibles.getUninterruptibly(listenableFuture));
        } catch (CancellationException e2) {
            completableDeferredCompletableDeferred$default = CompletableDeferredKt.CompletableDeferred$default(null, 1, null);
            completableDeferredCompletableDeferred$default.cancel(e2);
            return completableDeferredCompletableDeferred$default;
        } catch (ExecutionException e3) {
            completableDeferredCompletableDeferred$default = CompletableDeferredKt.CompletableDeferred$default(null, 1, null);
            completableDeferredCompletableDeferred$default.completeExceptionally(nonNullCause(e3));
            return completableDeferredCompletableDeferred$default;
        }
    }

    @NotNull
    public static final <T> ListenableFuture<T> asListenableFuture(@NotNull final Deferred<? extends T> deferred) {
        final JobListenableFuture jobListenableFuture = new JobListenableFuture(deferred);
        deferred.invokeOnCompletion(new Function1<Throwable, Unit>() { // from class: kotlinx.coroutines.guava.ListenableFutureKt.asListenableFuture.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@Nullable Throwable th) {
                if (th == null) {
                    jobListenableFuture.complete(deferred.getCompleted());
                } else {
                    jobListenableFuture.completeExceptionallyOrCancel(th);
                }
            }
        });
        return jobListenableFuture;
    }

    @Nullable
    public static final <T> Object await(@NotNull final ListenableFuture<T> listenableFuture, @NotNull Continuation<? super T> continuation) {
        try {
            if (listenableFuture.isDone()) {
                return Uninterruptibles.getUninterruptibly(listenableFuture);
            }
            CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(continuation), 1);
            cancellableContinuationImpl.initCancellability();
            listenableFuture.addListener(new ToContinuation(listenableFuture, cancellableContinuationImpl), MoreExecutors.directExecutor());
            cancellableContinuationImpl.invokeOnCancellation(new Function1<Throwable, Unit>() { // from class: kotlinx.coroutines.guava.ListenableFutureKt$await$2$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                    invoke2(th);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(@Nullable Throwable th) {
                    listenableFuture.cancel(false);
                }
            });
            Object result = cancellableContinuationImpl.getResult();
            if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                DebugProbesKt.probeCoroutineSuspended(continuation);
            }
            return result;
        } catch (ExecutionException e2) {
            throw nonNullCause(e2);
        }
    }

    @NotNull
    public static final <T> ListenableFuture<T> future(@NotNull CoroutineScope coroutineScope, @NotNull CoroutineContext coroutineContext, @NotNull CoroutineStart coroutineStart, @NotNull Function2<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> function2) {
        if (!coroutineStart.isLazy()) {
            ListenableFutureCoroutine listenableFutureCoroutine = new ListenableFutureCoroutine(CoroutineContextKt.newCoroutineContext(coroutineScope, coroutineContext));
            listenableFutureCoroutine.start(coroutineStart, listenableFutureCoroutine, function2);
            return listenableFutureCoroutine.future;
        }
        throw new IllegalArgumentException((coroutineStart + " start is not supported").toString());
    }

    public static /* synthetic */ ListenableFuture future$default(CoroutineScope coroutineScope, CoroutineContext coroutineContext, CoroutineStart coroutineStart, Function2 function2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            coroutineContext = EmptyCoroutineContext.INSTANCE;
        }
        if ((i2 & 2) != 0) {
            coroutineStart = CoroutineStart.DEFAULT;
        }
        return future(coroutineScope, coroutineContext, coroutineStart, function2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Throwable nonNullCause(ExecutionException executionException) {
        Throwable cause = executionException.getCause();
        Intrinsics.checkNotNull(cause);
        return cause;
    }
}
