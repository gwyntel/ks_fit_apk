package kotlinx.coroutines;

import androidx.exifinterface.media.ExifInterface;
import androidx.media3.common.C;
import com.facebook.internal.AnalyticsEvents;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.tekartik.sqflite.Constant;
import com.umeng.analytics.pro.f;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;
import kotlin.jvm.JvmName;
import kotlin.jvm.Volatile;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.internal.DispatchedContinuation;
import kotlinx.coroutines.internal.Segment;
import kotlinx.coroutines.internal.Symbol;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000Ð\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0001\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0011\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00002\b\u0012\u0004\u0012\u00028\u00000\u00022\b\u0012\u0004\u0012\u00028\u00000\u00032\u00060\u0004j\u0002`\u00052\u00020\u0006B\u001d\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007\u0012\u0006\u0010\n\u001a\u00020\t¢\u0006\u0004\b\u000b\u0010\fJ\u000f\u0010\u000e\u001a\u00020\rH\u0002¢\u0006\u0004\b\u000e\u0010\u000fJ\u0017\u0010\u0012\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\u0010H\u0002¢\u0006\u0004\b\u0012\u0010\u0013J\u001e\u0010\u0017\u001a\u00020\u00152\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014H\u0082\b¢\u0006\u0004\b\u0017\u0010\u0018JB\u0010\u001e\u001a\u00020\u00152'\u0010\u001d\u001a#\u0012\u0015\u0012\u0013\u0018\u00010\u0010¢\u0006\f\b\u001a\u0012\b\b\u001b\u0012\u0004\b\b(\u0011\u0012\u0004\u0012\u00020\u00150\u0019j\u0002`\u001c2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0010H\u0002¢\u0006\u0004\b\u001e\u0010\u001fJ%\u0010\"\u001a\u00020\u00152\n\u0010!\u001a\u0006\u0012\u0002\b\u00030 2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0010H\u0002¢\u0006\u0004\b\"\u0010#J\u000f\u0010$\u001a\u00020\rH\u0002¢\u0006\u0004\b$\u0010\u000fJ\u000f\u0010%\u001a\u00020\rH\u0002¢\u0006\u0004\b%\u0010\u000fJ\u0011\u0010'\u001a\u0004\u0018\u00010&H\u0002¢\u0006\u0004\b'\u0010(J\u0017\u0010*\u001a\u00020\u00152\u0006\u0010\u001d\u001a\u00020)H\u0002¢\u0006\u0004\b*\u0010+J!\u0010-\u001a\u00020\u00152\u0006\u0010\u001d\u001a\u00020)2\b\u0010,\u001a\u0004\u0018\u00010)H\u0002¢\u0006\u0004\b-\u0010.J8\u00100\u001a\u00020/2'\u0010\u001d\u001a#\u0012\u0015\u0012\u0013\u0018\u00010\u0010¢\u0006\f\b\u001a\u0012\b\b\u001b\u0012\u0004\b\b(\u0011\u0012\u0004\u0012\u00020\u00150\u0019j\u0002`\u001cH\u0002¢\u0006\u0004\b0\u00101J\u0017\u00103\u001a\u00020\u00152\u0006\u00102\u001a\u00020\tH\u0002¢\u0006\u0004\b3\u00104JZ\u00109\u001a\u0004\u0018\u00010)2\u0006\u0010,\u001a\u0002052\b\u00106\u001a\u0004\u0018\u00010)2\u0006\u0010\n\u001a\u00020\t2#\u00107\u001a\u001f\u0012\u0013\u0012\u00110\u0010¢\u0006\f\b\u001a\u0012\b\b\u001b\u0012\u0004\b\b(\u0011\u0012\u0004\u0012\u00020\u0015\u0018\u00010\u00192\b\u00108\u001a\u0004\u0018\u00010)H\u0002¢\u0006\u0004\b9\u0010:JH\u0010;\u001a\u00020\u00152\b\u00106\u001a\u0004\u0018\u00010)2\u0006\u0010\n\u001a\u00020\t2%\b\u0002\u00107\u001a\u001f\u0012\u0013\u0012\u00110\u0010¢\u0006\f\b\u001a\u0012\b\b\u001b\u0012\u0004\b\b(\u0011\u0012\u0004\u0012\u00020\u0015\u0018\u00010\u0019H\u0002¢\u0006\u0004\b;\u0010<JJ\u0010>\u001a\u0004\u0018\u00010=2\b\u00106\u001a\u0004\u0018\u00010)2\b\u00108\u001a\u0004\u0018\u00010)2#\u00107\u001a\u001f\u0012\u0013\u0012\u00110\u0010¢\u0006\f\b\u001a\u0012\b\b\u001b\u0012\u0004\b\b(\u0011\u0012\u0004\u0012\u00020\u0015\u0018\u00010\u0019H\u0002¢\u0006\u0004\b>\u0010?J\u0019\u0010A\u001a\u00020@2\b\u00106\u001a\u0004\u0018\u00010)H\u0002¢\u0006\u0004\bA\u0010BJ\u000f\u0010C\u001a\u00020\u0015H\u0002¢\u0006\u0004\bC\u0010DJ\u000f\u0010E\u001a\u00020\u0015H\u0016¢\u0006\u0004\bE\u0010DJ\u000f\u0010F\u001a\u00020\rH\u0001¢\u0006\u0004\bF\u0010\u000fJ\u0017\u0010I\u001a\n\u0018\u00010Gj\u0004\u0018\u0001`HH\u0016¢\u0006\u0004\bI\u0010JJ\u0011\u0010M\u001a\u0004\u0018\u00010)H\u0010¢\u0006\u0004\bK\u0010LJ!\u0010Q\u001a\u00020\u00152\b\u0010N\u001a\u0004\u0018\u00010)2\u0006\u0010\u0011\u001a\u00020\u0010H\u0010¢\u0006\u0004\bO\u0010PJ\u0019\u0010R\u001a\u00020\r2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0010H\u0016¢\u0006\u0004\bR\u0010\u0013J\u0017\u0010U\u001a\u00020\u00152\u0006\u0010\u0011\u001a\u00020\u0010H\u0000¢\u0006\u0004\bS\u0010TJ\u001f\u0010\u001e\u001a\u00020\u00152\u0006\u0010\u001d\u001a\u00020/2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0010¢\u0006\u0004\b\u001e\u0010VJ8\u0010W\u001a\u00020\u00152!\u00107\u001a\u001d\u0012\u0013\u0012\u00110\u0010¢\u0006\f\b\u001a\u0012\b\b\u001b\u0012\u0004\b\b(\u0011\u0012\u0004\u0012\u00020\u00150\u00192\u0006\u0010\u0011\u001a\u00020\u0010¢\u0006\u0004\bW\u0010\u001fJ\u0017\u0010Z\u001a\u00020\u00102\u0006\u0010Y\u001a\u00020XH\u0016¢\u0006\u0004\bZ\u0010[J\u0011\u0010\\\u001a\u0004\u0018\u00010)H\u0001¢\u0006\u0004\b\\\u0010LJ\u000f\u0010^\u001a\u00020\u0015H\u0000¢\u0006\u0004\b]\u0010DJ \u0010a\u001a\u00020\u00152\f\u0010`\u001a\b\u0012\u0004\u0012\u00028\u00000_H\u0016ø\u0001\u0000¢\u0006\u0004\ba\u0010+J<\u0010c\u001a\u00020\u00152\u0006\u0010b\u001a\u00028\u00002#\u00107\u001a\u001f\u0012\u0013\u0012\u00110\u0010¢\u0006\f\b\u001a\u0012\b\b\u001b\u0012\u0004\b\b(\u0011\u0012\u0004\u0012\u00020\u0015\u0018\u00010\u0019H\u0016¢\u0006\u0004\bc\u0010dJ#\u0010f\u001a\u00020\u00152\n\u0010!\u001a\u0006\u0012\u0002\b\u00030 2\u0006\u0010e\u001a\u00020\tH\u0016¢\u0006\u0004\bf\u0010gJ8\u0010f\u001a\u00020\u00152'\u0010\u001d\u001a#\u0012\u0015\u0012\u0013\u0018\u00010\u0010¢\u0006\f\b\u001a\u0012\b\b\u001b\u0012\u0004\b\b(\u0011\u0012\u0004\u0012\u00020\u00150\u0019j\u0002`\u001cH\u0016¢\u0006\u0004\bf\u0010hJ\u000f\u0010j\u001a\u00020\u0015H\u0000¢\u0006\u0004\bi\u0010DJ#\u0010%\u001a\u0004\u0018\u00010)2\u0006\u0010b\u001a\u00028\u00002\b\u00108\u001a\u0004\u0018\u00010)H\u0016¢\u0006\u0004\b%\u0010kJH\u0010%\u001a\u0004\u0018\u00010)2\u0006\u0010b\u001a\u00028\u00002\b\u00108\u001a\u0004\u0018\u00010)2#\u00107\u001a\u001f\u0012\u0013\u0012\u00110\u0010¢\u0006\f\b\u001a\u0012\b\b\u001b\u0012\u0004\b\b(\u0011\u0012\u0004\u0012\u00020\u0015\u0018\u00010\u0019H\u0016¢\u0006\u0004\b%\u0010lJ\u0019\u0010n\u001a\u0004\u0018\u00010)2\u0006\u0010m\u001a\u00020\u0010H\u0016¢\u0006\u0004\bn\u0010oJ\u0017\u0010q\u001a\u00020\u00152\u0006\u0010p\u001a\u00020)H\u0016¢\u0006\u0004\bq\u0010+J\u001b\u0010s\u001a\u00020\u0015*\u00020r2\u0006\u0010b\u001a\u00028\u0000H\u0016¢\u0006\u0004\bs\u0010tJ\u001b\u0010u\u001a\u00020\u0015*\u00020r2\u0006\u0010m\u001a\u00020\u0010H\u0016¢\u0006\u0004\bu\u0010vJ\u001f\u0010y\u001a\u00028\u0001\"\u0004\b\u0001\u0010\u00012\b\u0010,\u001a\u0004\u0018\u00010)H\u0010¢\u0006\u0004\bw\u0010xJ\u001b\u0010|\u001a\u0004\u0018\u00010\u00102\b\u0010,\u001a\u0004\u0018\u00010)H\u0010¢\u0006\u0004\bz\u0010{J\u000f\u0010~\u001a\u00020}H\u0016¢\u0006\u0004\b~\u0010\u007fJ\u0011\u0010\u0080\u0001\u001a\u00020}H\u0014¢\u0006\u0005\b\u0080\u0001\u0010\u007fR#\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\u00078\u0000X\u0080\u0004¢\u0006\u000f\n\u0005\b\b\u0010\u0081\u0001\u001a\u0006\b\u0082\u0001\u0010\u0083\u0001R \u0010\u0085\u0001\u001a\u00030\u0084\u00018\u0016X\u0096\u0004¢\u0006\u0010\n\u0006\b\u0085\u0001\u0010\u0086\u0001\u001a\u0006\b\u0087\u0001\u0010\u0088\u0001R\u0018\u0010\u008a\u0001\u001a\u0004\u0018\u00010&8BX\u0082\u0004¢\u0006\u0007\u001a\u0005\b\u0089\u0001\u0010(R\u0016\u0010\u008c\u0001\u001a\u00020}8BX\u0082\u0004¢\u0006\u0007\u001a\u0005\b\u008b\u0001\u0010\u007fR\u0017\u0010,\u001a\u0004\u0018\u00010)8@X\u0080\u0004¢\u0006\u0007\u001a\u0005\b\u008d\u0001\u0010LR\u0016\u0010\u008e\u0001\u001a\u00020\r8VX\u0096\u0004¢\u0006\u0007\u001a\u0005\b\u008e\u0001\u0010\u000fR\u0016\u0010\u008f\u0001\u001a\u00020\r8VX\u0096\u0004¢\u0006\u0007\u001a\u0005\b\u008f\u0001\u0010\u000fR\u0016\u0010\u0090\u0001\u001a\u00020\r8VX\u0096\u0004¢\u0006\u0007\u001a\u0005\b\u0090\u0001\u0010\u000fR\u001f\u0010\u0093\u0001\u001a\n\u0018\u00010\u0004j\u0004\u0018\u0001`\u00058VX\u0096\u0004¢\u0006\b\u001a\u0006\b\u0091\u0001\u0010\u0092\u0001R\r\u0010\u0095\u0001\u001a\u00030\u0094\u00018\u0002X\u0082\u0004R\u0015\u0010\u0097\u0001\u001a\u000b\u0012\u0006\u0012\u0004\u0018\u00010&0\u0096\u00018\u0002X\u0082\u0004R\u0015\u0010\u0098\u0001\u001a\u000b\u0012\u0006\u0012\u0004\u0018\u00010)0\u0096\u00018\u0002X\u0082\u0004\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0099\u0001"}, d2 = {"Lkotlinx/coroutines/CancellableContinuationImpl;", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/DispatchedTask;", "Lkotlinx/coroutines/CancellableContinuation;", "Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "Lkotlinx/coroutines/internal/CoroutineStackFrame;", "Lkotlinx/coroutines/Waiter;", "Lkotlin/coroutines/Continuation;", "delegate", "", "resumeMode", "<init>", "(Lkotlin/coroutines/Continuation;I)V", "", "isReusable", "()Z", "", "cause", "cancelLater", "(Ljava/lang/Throwable;)Z", "Lkotlin/Function0;", "", "block", "callCancelHandlerSafely", "(Lkotlin/jvm/functions/Function0;)V", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "Lkotlinx/coroutines/CompletionHandler;", "handler", "callCancelHandler", "(Lkotlin/jvm/functions/Function1;Ljava/lang/Throwable;)V", "Lkotlinx/coroutines/internal/Segment;", "segment", "callSegmentOnCancellation", "(Lkotlinx/coroutines/internal/Segment;Ljava/lang/Throwable;)V", "trySuspend", "tryResume", "Lkotlinx/coroutines/DisposableHandle;", "installParentHandle", "()Lkotlinx/coroutines/DisposableHandle;", "", "invokeOnCancellationImpl", "(Ljava/lang/Object;)V", "state", "multipleHandlersError", "(Ljava/lang/Object;Ljava/lang/Object;)V", "Lkotlinx/coroutines/CancelHandler;", "makeCancelHandler", "(Lkotlin/jvm/functions/Function1;)Lkotlinx/coroutines/CancelHandler;", "mode", "dispatchResume", "(I)V", "Lkotlinx/coroutines/NotCompleted;", "proposedUpdate", "onCancellation", "idempotent", "resumedState", "(Lkotlinx/coroutines/NotCompleted;Ljava/lang/Object;ILkotlin/jvm/functions/Function1;Ljava/lang/Object;)Ljava/lang/Object;", "resumeImpl", "(Ljava/lang/Object;ILkotlin/jvm/functions/Function1;)V", "Lkotlinx/coroutines/internal/Symbol;", "tryResumeImpl", "(Ljava/lang/Object;Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Lkotlinx/coroutines/internal/Symbol;", "", "alreadyResumedError", "(Ljava/lang/Object;)Ljava/lang/Void;", "detachChildIfNonResuable", "()V", "initCancellability", "resetStateReusable", "Ljava/lang/StackTraceElement;", "Lkotlinx/coroutines/internal/StackTraceElement;", "getStackTraceElement", "()Ljava/lang/StackTraceElement;", "takeState$kotlinx_coroutines_core", "()Ljava/lang/Object;", "takeState", "takenState", "cancelCompletedResult$kotlinx_coroutines_core", "(Ljava/lang/Object;Ljava/lang/Throwable;)V", "cancelCompletedResult", Constant.PARAM_CANCEL, "parentCancelled$kotlinx_coroutines_core", "(Ljava/lang/Throwable;)V", "parentCancelled", "(Lkotlinx/coroutines/CancelHandler;Ljava/lang/Throwable;)V", "callOnCancellation", "Lkotlinx/coroutines/Job;", "parent", "getContinuationCancellationCause", "(Lkotlinx/coroutines/Job;)Ljava/lang/Throwable;", "getResult", "releaseClaimedReusableContinuation$kotlinx_coroutines_core", "releaseClaimedReusableContinuation", "Lkotlin/Result;", "result", "resumeWith", "value", "resume", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)V", FirebaseAnalytics.Param.INDEX, "invokeOnCancellation", "(Lkotlinx/coroutines/internal/Segment;I)V", "(Lkotlin/jvm/functions/Function1;)V", "detachChild$kotlinx_coroutines_core", "detachChild", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "(Ljava/lang/Object;Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "exception", "tryResumeWithException", "(Ljava/lang/Throwable;)Ljava/lang/Object;", "token", "completeResume", "Lkotlinx/coroutines/CoroutineDispatcher;", "resumeUndispatched", "(Lkotlinx/coroutines/CoroutineDispatcher;Ljava/lang/Object;)V", "resumeUndispatchedWithException", "(Lkotlinx/coroutines/CoroutineDispatcher;Ljava/lang/Throwable;)V", "getSuccessfulResult$kotlinx_coroutines_core", "(Ljava/lang/Object;)Ljava/lang/Object;", "getSuccessfulResult", "getExceptionalResult$kotlinx_coroutines_core", "(Ljava/lang/Object;)Ljava/lang/Throwable;", "getExceptionalResult", "", "toString", "()Ljava/lang/String;", "a", "Lkotlin/coroutines/Continuation;", "getDelegate$kotlinx_coroutines_core", "()Lkotlin/coroutines/Continuation;", "Lkotlin/coroutines/CoroutineContext;", f.X, "Lkotlin/coroutines/CoroutineContext;", "getContext", "()Lkotlin/coroutines/CoroutineContext;", "getParentHandle", "parentHandle", "getStateDebugRepresentation", "stateDebugRepresentation", "getState$kotlinx_coroutines_core", "isActive", "isCompleted", "isCancelled", "getCallerFrame", "()Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "callerFrame", "Lkotlinx/atomicfu/AtomicInt;", "_decisionAndIndex", "Lkotlinx/atomicfu/AtomicRef;", "_parentHandle", "_state", "kotlinx-coroutines-core"}, k = 1, mv = {1, 8, 0})
@PublishedApi
@SourceDebugExtension({"SMAP\nCancellableContinuationImpl.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CancellableContinuationImpl.kt\nkotlinx/coroutines/CancellableContinuationImpl\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 CancellableContinuationImpl.kt\nkotlinx/coroutines/CancellableContinuationImplKt\n+ 4 CompletionHandler.kt\nkotlinx/coroutines/CompletionHandlerKt\n+ 5 StackTraceRecovery.kt\nkotlinx/coroutines/internal/StackTraceRecoveryKt\n*L\n1#1,662:1\n230#1,2:666\n232#1,8:669\n230#1,10:677\n230#1,10:688\n1#2:663\n24#3:664\n24#3:665\n22#3:687\n21#3:698\n22#3,3:699\n21#3:702\n22#3,3:703\n22#3:711\n21#3,4:712\n22#4:668\n13#4:710\n61#5,2:706\n61#5,2:708\n61#5,2:716\n*S KotlinDebug\n*F\n+ 1 CancellableContinuationImpl.kt\nkotlinx/coroutines/CancellableContinuationImpl\n*L\n246#1:666,2\n246#1:669,8\n249#1:677,10\n254#1:688,10\n72#1:664\n158#1:665\n252#1:687\n277#1:698\n278#1:699,3\n287#1:702\n288#1:703,3\n389#1:711\n392#1:712,4\n246#1:668\n350#1:710\n329#1:706,2\n339#1:708,2\n613#1:716,2\n*E\n"})
/* loaded from: classes5.dex */
public class CancellableContinuationImpl<T> extends DispatchedTask<T> implements CancellableContinuation<T>, CoroutineStackFrame, Waiter {

    @Volatile
    private volatile int _decisionAndIndex;

    @Volatile
    @Nullable
    private volatile Object _parentHandle;

    @Volatile
    @Nullable
    private volatile Object _state;

    @NotNull
    private final CoroutineContext context;

    @NotNull
    private final Continuation<T> delegate;

    @NotNull
    private static final AtomicIntegerFieldUpdater _decisionAndIndex$FU = AtomicIntegerFieldUpdater.newUpdater(CancellableContinuationImpl.class, "_decisionAndIndex");

    @NotNull
    private static final AtomicReferenceFieldUpdater _state$FU = AtomicReferenceFieldUpdater.newUpdater(CancellableContinuationImpl.class, Object.class, "_state");

    @NotNull
    private static final AtomicReferenceFieldUpdater _parentHandle$FU = AtomicReferenceFieldUpdater.newUpdater(CancellableContinuationImpl.class, Object.class, "_parentHandle");

    /* JADX WARN: Multi-variable type inference failed */
    public CancellableContinuationImpl(@NotNull Continuation<? super T> continuation, int i2) {
        super(i2);
        this.delegate = continuation;
        this.context = continuation.getF25479a();
        this._decisionAndIndex = 536870911;
        this._state = Active.INSTANCE;
    }

    private final Void alreadyResumedError(Object proposedUpdate) {
        throw new IllegalStateException(("Already resumed, but proposed with update " + proposedUpdate).toString());
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ void b(CancellableContinuationImpl cancellableContinuationImpl, Object obj, int i2, Function1 function1, int i3, Object obj2) {
        if (obj2 != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: resumeImpl");
        }
        if ((i3 & 4) != 0) {
            function1 = null;
        }
        cancellableContinuationImpl.resumeImpl(obj, i2, function1);
    }

    private final void callCancelHandlerSafely(Function0<Unit> block) {
        try {
            block.invoke();
        } catch (Throwable th) {
            CoroutineExceptionHandlerKt.handleCoroutineException(getF25479a(), new CompletionHandlerException("Exception in invokeOnCancellation handler for " + this, th));
        }
    }

    private final void callSegmentOnCancellation(Segment<?> segment, Throwable cause) {
        int i2 = _decisionAndIndex$FU.get(this) & 536870911;
        if (i2 == 536870911) {
            throw new IllegalStateException("The index for Segment.onCancellation(..) is broken".toString());
        }
        try {
            segment.onCancellation(i2, cause, getF25479a());
        } catch (Throwable th) {
            CoroutineExceptionHandlerKt.handleCoroutineException(getF25479a(), new CompletionHandlerException("Exception in invokeOnCancellation handler for " + this, th));
        }
    }

    private final boolean cancelLater(Throwable cause) {
        if (!isReusable()) {
            return false;
        }
        Continuation<T> continuation = this.delegate;
        Intrinsics.checkNotNull(continuation, "null cannot be cast to non-null type kotlinx.coroutines.internal.DispatchedContinuation<*>");
        return ((DispatchedContinuation) continuation).postponeCancellation$kotlinx_coroutines_core(cause);
    }

    private final void detachChildIfNonResuable() {
        if (isReusable()) {
            return;
        }
        detachChild$kotlinx_coroutines_core();
    }

    private final void dispatchResume(int mode) {
        if (tryResume()) {
            return;
        }
        DispatchedTaskKt.dispatch(this, mode);
    }

    private final DisposableHandle getParentHandle() {
        return (DisposableHandle) _parentHandle$FU.get(this);
    }

    private final String getStateDebugRepresentation() {
        Object state$kotlinx_coroutines_core = getState$kotlinx_coroutines_core();
        return state$kotlinx_coroutines_core instanceof NotCompleted ? "Active" : state$kotlinx_coroutines_core instanceof CancelledContinuation ? AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_CANCELLED : AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_COMPLETED;
    }

    private final DisposableHandle installParentHandle() {
        Job job = (Job) getF25479a().get(Job.INSTANCE);
        if (job == null) {
            return null;
        }
        DisposableHandle disposableHandleInvokeOnCompletion$default = Job.DefaultImpls.invokeOnCompletion$default(job, true, false, new ChildContinuation(this), 2, null);
        androidx.concurrent.futures.a.a(_parentHandle$FU, this, null, disposableHandleInvokeOnCompletion$default);
        return disposableHandleInvokeOnCompletion$default;
    }

    private final void invokeOnCancellationImpl(Object handler) {
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = _state$FU;
        while (true) {
            Object obj = atomicReferenceFieldUpdater.get(this);
            if (!(obj instanceof Active)) {
                if (obj instanceof CancelHandler ? true : obj instanceof Segment) {
                    multipleHandlersError(handler, obj);
                } else {
                    if (obj instanceof CompletedExceptionally) {
                        CompletedExceptionally completedExceptionally = (CompletedExceptionally) obj;
                        if (!completedExceptionally.makeHandled()) {
                            multipleHandlersError(handler, obj);
                        }
                        if (obj instanceof CancelledContinuation) {
                            if (!(obj instanceof CompletedExceptionally)) {
                                completedExceptionally = null;
                            }
                            Throwable th = completedExceptionally != null ? completedExceptionally.cause : null;
                            if (handler instanceof CancelHandler) {
                                callCancelHandler((CancelHandler) handler, th);
                                return;
                            } else {
                                Intrinsics.checkNotNull(handler, "null cannot be cast to non-null type kotlinx.coroutines.internal.Segment<*>");
                                callSegmentOnCancellation((Segment) handler, th);
                                return;
                            }
                        }
                        return;
                    }
                    if (obj instanceof CompletedContinuation) {
                        CompletedContinuation completedContinuation = (CompletedContinuation) obj;
                        if (completedContinuation.cancelHandler != null) {
                            multipleHandlersError(handler, obj);
                        }
                        if (handler instanceof Segment) {
                            return;
                        }
                        Intrinsics.checkNotNull(handler, "null cannot be cast to non-null type kotlinx.coroutines.CancelHandler");
                        CancelHandler cancelHandler = (CancelHandler) handler;
                        if (completedContinuation.getCancelled()) {
                            callCancelHandler(cancelHandler, completedContinuation.cancelCause);
                            return;
                        } else {
                            if (androidx.concurrent.futures.a.a(_state$FU, this, obj, CompletedContinuation.copy$default(completedContinuation, null, cancelHandler, null, null, null, 29, null))) {
                                return;
                            }
                        }
                    } else {
                        if (handler instanceof Segment) {
                            return;
                        }
                        Intrinsics.checkNotNull(handler, "null cannot be cast to non-null type kotlinx.coroutines.CancelHandler");
                        if (androidx.concurrent.futures.a.a(_state$FU, this, obj, new CompletedContinuation(obj, (CancelHandler) handler, null, null, null, 28, null))) {
                            return;
                        }
                    }
                }
            } else if (androidx.concurrent.futures.a.a(_state$FU, this, obj, handler)) {
                return;
            }
        }
    }

    private final boolean isReusable() {
        if (DispatchedTaskKt.isReusableMode(this.resumeMode)) {
            Continuation<T> continuation = this.delegate;
            Intrinsics.checkNotNull(continuation, "null cannot be cast to non-null type kotlinx.coroutines.internal.DispatchedContinuation<*>");
            if (((DispatchedContinuation) continuation).isReusable$kotlinx_coroutines_core()) {
                return true;
            }
        }
        return false;
    }

    private final void loop$atomicfu(AtomicIntegerFieldUpdater atomicIntegerFieldUpdater, Function1<? super Integer, Unit> function1, Object obj) {
        while (true) {
            function1.invoke(Integer.valueOf(atomicIntegerFieldUpdater.get(obj)));
        }
    }

    private final CancelHandler makeCancelHandler(Function1<? super Throwable, Unit> handler) {
        return handler instanceof CancelHandler ? (CancelHandler) handler : new InvokeOnCancel(handler);
    }

    private final void multipleHandlersError(Object handler, Object state) {
        throw new IllegalStateException(("It's prohibited to register multiple handlers, tried to register " + handler + ", already has " + state).toString());
    }

    private final void resumeImpl(Object proposedUpdate, int resumeMode, Function1<? super Throwable, Unit> onCancellation) {
        Object obj;
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = _state$FU;
        do {
            obj = atomicReferenceFieldUpdater.get(this);
            if (!(obj instanceof NotCompleted)) {
                if (obj instanceof CancelledContinuation) {
                    CancelledContinuation cancelledContinuation = (CancelledContinuation) obj;
                    if (cancelledContinuation.makeResumed()) {
                        if (onCancellation != null) {
                            callOnCancellation(onCancellation, cancelledContinuation.cause);
                            return;
                        }
                        return;
                    }
                }
                alreadyResumedError(proposedUpdate);
                throw new KotlinNothingValueException();
            }
        } while (!androidx.concurrent.futures.a.a(_state$FU, this, obj, resumedState((NotCompleted) obj, proposedUpdate, resumeMode, onCancellation, null)));
        detachChildIfNonResuable();
        dispatchResume(resumeMode);
    }

    private final Object resumedState(NotCompleted state, Object proposedUpdate, int resumeMode, Function1<? super Throwable, Unit> onCancellation, Object idempotent) {
        if (proposedUpdate instanceof CompletedExceptionally) {
            return proposedUpdate;
        }
        if (!DispatchedTaskKt.isCancellableMode(resumeMode) && idempotent == null) {
            return proposedUpdate;
        }
        if (onCancellation == null && !(state instanceof CancelHandler) && idempotent == null) {
            return proposedUpdate;
        }
        return new CompletedContinuation(proposedUpdate, state instanceof CancelHandler ? (CancelHandler) state : null, onCancellation, idempotent, null, 16, null);
    }

    private final boolean tryResume() {
        int i2;
        AtomicIntegerFieldUpdater atomicIntegerFieldUpdater = _decisionAndIndex$FU;
        do {
            i2 = atomicIntegerFieldUpdater.get(this);
            int i3 = i2 >> 29;
            if (i3 != 0) {
                if (i3 == 1) {
                    return false;
                }
                throw new IllegalStateException("Already resumed".toString());
            }
        } while (!_decisionAndIndex$FU.compareAndSet(this, i2, 1073741824 + (536870911 & i2)));
        return true;
    }

    private final Symbol tryResumeImpl(Object proposedUpdate, Object idempotent, Function1<? super Throwable, Unit> onCancellation) {
        Object obj;
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = _state$FU;
        do {
            obj = atomicReferenceFieldUpdater.get(this);
            if (!(obj instanceof NotCompleted)) {
                if ((obj instanceof CompletedContinuation) && idempotent != null && ((CompletedContinuation) obj).idempotentResume == idempotent) {
                    return CancellableContinuationImplKt.RESUME_TOKEN;
                }
                return null;
            }
        } while (!androidx.concurrent.futures.a.a(_state$FU, this, obj, resumedState((NotCompleted) obj, proposedUpdate, this.resumeMode, onCancellation, idempotent)));
        detachChildIfNonResuable();
        return CancellableContinuationImplKt.RESUME_TOKEN;
    }

    private final boolean trySuspend() {
        int i2;
        AtomicIntegerFieldUpdater atomicIntegerFieldUpdater = _decisionAndIndex$FU;
        do {
            i2 = atomicIntegerFieldUpdater.get(this);
            int i3 = i2 >> 29;
            if (i3 != 0) {
                if (i3 == 2) {
                    return false;
                }
                throw new IllegalStateException("Already suspended".toString());
            }
        } while (!_decisionAndIndex$FU.compareAndSet(this, i2, C.BUFFER_FLAG_LAST_SAMPLE + (536870911 & i2)));
        return true;
    }

    private final void update$atomicfu(AtomicIntegerFieldUpdater atomicIntegerFieldUpdater, Function1<? super Integer, Integer> function1, Object obj) {
        int i2;
        do {
            i2 = atomicIntegerFieldUpdater.get(obj);
        } while (!atomicIntegerFieldUpdater.compareAndSet(obj, i2, function1.invoke(Integer.valueOf(i2)).intValue()));
    }

    protected String a() {
        return "CancellableContinuation";
    }

    public final void callCancelHandler(@NotNull CancelHandler handler, @Nullable Throwable cause) {
        try {
            handler.invoke(cause);
        } catch (Throwable th) {
            CoroutineExceptionHandlerKt.handleCoroutineException(getF25479a(), new CompletionHandlerException("Exception in invokeOnCancellation handler for " + this, th));
        }
    }

    public final void callOnCancellation(@NotNull Function1<? super Throwable, Unit> onCancellation, @NotNull Throwable cause) {
        try {
            onCancellation.invoke(cause);
        } catch (Throwable th) {
            CoroutineExceptionHandlerKt.handleCoroutineException(getF25479a(), new CompletionHandlerException("Exception in resume onCancellation handler for " + this, th));
        }
    }

    @Override // kotlinx.coroutines.CancellableContinuation
    public boolean cancel(@Nullable Throwable cause) {
        Object obj;
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = _state$FU;
        do {
            obj = atomicReferenceFieldUpdater.get(this);
            if (!(obj instanceof NotCompleted)) {
                return false;
            }
        } while (!androidx.concurrent.futures.a.a(_state$FU, this, obj, new CancelledContinuation(this, cause, (obj instanceof CancelHandler) || (obj instanceof Segment))));
        NotCompleted notCompleted = (NotCompleted) obj;
        if (notCompleted instanceof CancelHandler) {
            callCancelHandler((CancelHandler) obj, cause);
        } else if (notCompleted instanceof Segment) {
            callSegmentOnCancellation((Segment) obj, cause);
        }
        detachChildIfNonResuable();
        dispatchResume(this.resumeMode);
        return true;
    }

    @Override // kotlinx.coroutines.DispatchedTask
    public void cancelCompletedResult$kotlinx_coroutines_core(@Nullable Object takenState, @NotNull Throwable cause) {
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = _state$FU;
        while (true) {
            Object obj = atomicReferenceFieldUpdater.get(this);
            if (obj instanceof NotCompleted) {
                throw new IllegalStateException("Not completed".toString());
            }
            if (obj instanceof CompletedExceptionally) {
                return;
            }
            if (obj instanceof CompletedContinuation) {
                CompletedContinuation completedContinuation = (CompletedContinuation) obj;
                if (!(!completedContinuation.getCancelled())) {
                    throw new IllegalStateException("Must be called at most once".toString());
                }
                if (androidx.concurrent.futures.a.a(_state$FU, this, obj, CompletedContinuation.copy$default(completedContinuation, null, null, null, null, cause, 15, null))) {
                    completedContinuation.invokeHandlers(this, cause);
                    return;
                }
            } else {
                if (androidx.concurrent.futures.a.a(_state$FU, this, obj, new CompletedContinuation(obj, null, null, null, cause, 14, null))) {
                    return;
                }
            }
        }
    }

    @Override // kotlinx.coroutines.CancellableContinuation
    public void completeResume(@NotNull Object token) {
        dispatchResume(this.resumeMode);
    }

    public final void detachChild$kotlinx_coroutines_core() {
        DisposableHandle parentHandle = getParentHandle();
        if (parentHandle == null) {
            return;
        }
        parentHandle.dispose();
        _parentHandle$FU.set(this, NonDisposableHandle.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.CoroutineStackFrame
    @Nullable
    public CoroutineStackFrame getCallerFrame() {
        Continuation<T> continuation = this.delegate;
        if (continuation instanceof CoroutineStackFrame) {
            return (CoroutineStackFrame) continuation;
        }
        return null;
    }

    @Override // kotlin.coroutines.Continuation
    @NotNull
    /* renamed from: getContext, reason: from getter */
    public CoroutineContext getF25479a() {
        return this.context;
    }

    @NotNull
    public Throwable getContinuationCancellationCause(@NotNull Job parent) {
        return parent.getCancellationException();
    }

    @Override // kotlinx.coroutines.DispatchedTask
    @NotNull
    public final Continuation<T> getDelegate$kotlinx_coroutines_core() {
        return this.delegate;
    }

    @Override // kotlinx.coroutines.DispatchedTask
    @Nullable
    public Throwable getExceptionalResult$kotlinx_coroutines_core(@Nullable Object state) {
        Throwable exceptionalResult$kotlinx_coroutines_core = super.getExceptionalResult$kotlinx_coroutines_core(state);
        if (exceptionalResult$kotlinx_coroutines_core != null) {
            return exceptionalResult$kotlinx_coroutines_core;
        }
        return null;
    }

    @PublishedApi
    @Nullable
    public final Object getResult() {
        Job job;
        boolean zIsReusable = isReusable();
        if (trySuspend()) {
            if (getParentHandle() == null) {
                installParentHandle();
            }
            if (zIsReusable) {
                releaseClaimedReusableContinuation$kotlinx_coroutines_core();
            }
            return IntrinsicsKt.getCOROUTINE_SUSPENDED();
        }
        if (zIsReusable) {
            releaseClaimedReusableContinuation$kotlinx_coroutines_core();
        }
        Object state$kotlinx_coroutines_core = getState$kotlinx_coroutines_core();
        if (state$kotlinx_coroutines_core instanceof CompletedExceptionally) {
            throw ((CompletedExceptionally) state$kotlinx_coroutines_core).cause;
        }
        if (!DispatchedTaskKt.isCancellableMode(this.resumeMode) || (job = (Job) getF25479a().get(Job.INSTANCE)) == null || job.isActive()) {
            return getSuccessfulResult$kotlinx_coroutines_core(state$kotlinx_coroutines_core);
        }
        CancellationException cancellationException = job.getCancellationException();
        cancelCompletedResult$kotlinx_coroutines_core(state$kotlinx_coroutines_core, cancellationException);
        throw cancellationException;
    }

    @Override // kotlin.coroutines.jvm.internal.CoroutineStackFrame
    @Nullable
    public StackTraceElement getStackTraceElement() {
        return null;
    }

    @Nullable
    public final Object getState$kotlinx_coroutines_core() {
        return _state$FU.get(this);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // kotlinx.coroutines.DispatchedTask
    public <T> T getSuccessfulResult$kotlinx_coroutines_core(@Nullable Object state) {
        return state instanceof CompletedContinuation ? (T) ((CompletedContinuation) state).result : state;
    }

    @Override // kotlinx.coroutines.CancellableContinuation
    public void initCancellability() {
        DisposableHandle disposableHandleInstallParentHandle = installParentHandle();
        if (disposableHandleInstallParentHandle != null && isCompleted()) {
            disposableHandleInstallParentHandle.dispose();
            _parentHandle$FU.set(this, NonDisposableHandle.INSTANCE);
        }
    }

    @Override // kotlinx.coroutines.Waiter
    public void invokeOnCancellation(@NotNull Segment<?> segment, int index) {
        int i2;
        AtomicIntegerFieldUpdater atomicIntegerFieldUpdater = _decisionAndIndex$FU;
        do {
            i2 = atomicIntegerFieldUpdater.get(this);
            if ((i2 & 536870911) != 536870911) {
                throw new IllegalStateException("invokeOnCancellation should be called at most once".toString());
            }
        } while (!atomicIntegerFieldUpdater.compareAndSet(this, i2, ((i2 >> 29) << 29) + index));
        invokeOnCancellationImpl(segment);
    }

    @Override // kotlinx.coroutines.CancellableContinuation
    public boolean isActive() {
        return getState$kotlinx_coroutines_core() instanceof NotCompleted;
    }

    @Override // kotlinx.coroutines.CancellableContinuation
    public boolean isCancelled() {
        return getState$kotlinx_coroutines_core() instanceof CancelledContinuation;
    }

    @Override // kotlinx.coroutines.CancellableContinuation
    public boolean isCompleted() {
        return !(getState$kotlinx_coroutines_core() instanceof NotCompleted);
    }

    public final void parentCancelled$kotlinx_coroutines_core(@NotNull Throwable cause) {
        if (cancelLater(cause)) {
            return;
        }
        cancel(cause);
        detachChildIfNonResuable();
    }

    public final void releaseClaimedReusableContinuation$kotlinx_coroutines_core() {
        Throwable thTryReleaseClaimedContinuation$kotlinx_coroutines_core;
        Continuation<T> continuation = this.delegate;
        DispatchedContinuation dispatchedContinuation = continuation instanceof DispatchedContinuation ? (DispatchedContinuation) continuation : null;
        if (dispatchedContinuation == null || (thTryReleaseClaimedContinuation$kotlinx_coroutines_core = dispatchedContinuation.tryReleaseClaimedContinuation$kotlinx_coroutines_core(this)) == null) {
            return;
        }
        detachChild$kotlinx_coroutines_core();
        cancel(thTryReleaseClaimedContinuation$kotlinx_coroutines_core);
    }

    @JvmName(name = "resetStateReusable")
    public final boolean resetStateReusable() {
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = _state$FU;
        Object obj = atomicReferenceFieldUpdater.get(this);
        if ((obj instanceof CompletedContinuation) && ((CompletedContinuation) obj).idempotentResume != null) {
            detachChild$kotlinx_coroutines_core();
            return false;
        }
        _decisionAndIndex$FU.set(this, 536870911);
        atomicReferenceFieldUpdater.set(this, Active.INSTANCE);
        return true;
    }

    @Override // kotlinx.coroutines.CancellableContinuation
    public void resume(T value, @Nullable Function1<? super Throwable, Unit> onCancellation) {
        resumeImpl(value, this.resumeMode, onCancellation);
    }

    @Override // kotlinx.coroutines.CancellableContinuation
    public void resumeUndispatched(@NotNull CoroutineDispatcher coroutineDispatcher, T t2) {
        Continuation<T> continuation = this.delegate;
        DispatchedContinuation dispatchedContinuation = continuation instanceof DispatchedContinuation ? (DispatchedContinuation) continuation : null;
        b(this, t2, (dispatchedContinuation != null ? dispatchedContinuation.dispatcher : null) == coroutineDispatcher ? 4 : this.resumeMode, null, 4, null);
    }

    @Override // kotlinx.coroutines.CancellableContinuation
    public void resumeUndispatchedWithException(@NotNull CoroutineDispatcher coroutineDispatcher, @NotNull Throwable th) {
        Continuation<T> continuation = this.delegate;
        DispatchedContinuation dispatchedContinuation = continuation instanceof DispatchedContinuation ? (DispatchedContinuation) continuation : null;
        b(this, new CompletedExceptionally(th, false, 2, null), (dispatchedContinuation != null ? dispatchedContinuation.dispatcher : null) == coroutineDispatcher ? 4 : this.resumeMode, null, 4, null);
    }

    @Override // kotlin.coroutines.Continuation
    public void resumeWith(@NotNull Object result) {
        b(this, CompletionStateKt.toState(result, this), this.resumeMode, null, 4, null);
    }

    @Override // kotlinx.coroutines.DispatchedTask
    @Nullable
    public Object takeState$kotlinx_coroutines_core() {
        return getState$kotlinx_coroutines_core();
    }

    @NotNull
    public String toString() {
        return a() + '(' + DebugStringsKt.toDebugString(this.delegate) + "){" + getStateDebugRepresentation() + "}@" + DebugStringsKt.getHexAddress(this);
    }

    @Override // kotlinx.coroutines.CancellableContinuation
    @Nullable
    public Object tryResumeWithException(@NotNull Throwable exception) {
        return tryResumeImpl(new CompletedExceptionally(exception, false, 2, null), null, null);
    }

    private final void loop$atomicfu(AtomicReferenceFieldUpdater atomicReferenceFieldUpdater, Function1<Object, Unit> function1, Object obj) {
        while (true) {
            function1.invoke(atomicReferenceFieldUpdater.get(obj));
        }
    }

    @Override // kotlinx.coroutines.CancellableContinuation
    public void invokeOnCancellation(@NotNull Function1<? super Throwable, Unit> handler) {
        invokeOnCancellationImpl(makeCancelHandler(handler));
    }

    @Override // kotlinx.coroutines.CancellableContinuation
    @Nullable
    public Object tryResume(T value, @Nullable Object idempotent) {
        return tryResumeImpl(value, idempotent, null);
    }

    private final void callCancelHandler(Function1<? super Throwable, Unit> handler, Throwable cause) {
        try {
            handler.invoke(cause);
        } catch (Throwable th) {
            CoroutineExceptionHandlerKt.handleCoroutineException(getF25479a(), new CompletionHandlerException("Exception in invokeOnCancellation handler for " + this, th));
        }
    }

    @Override // kotlinx.coroutines.CancellableContinuation
    @Nullable
    public Object tryResume(T value, @Nullable Object idempotent, @Nullable Function1<? super Throwable, Unit> onCancellation) {
        return tryResumeImpl(value, idempotent, onCancellation);
    }
}
