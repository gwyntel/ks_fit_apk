package com.google.common.util.concurrent;

import anetwork.channel.util.RequestConstant;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.util.concurrent.internal.InternalFutureFailureAccess;
import com.google.common.util.concurrent.internal.InternalFutures;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.j2objc.annotations.ReflectionSupport;
import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.concurrent.locks.LockSupport;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.CheckForNull;
import sun.misc.Unsafe;

@ElementTypesAreNonnullByDefault
@GwtCompatible(emulated = true)
@ReflectionSupport(ReflectionSupport.Level.FULL)
/* loaded from: classes3.dex */
public abstract class AbstractFuture<V> extends InternalFutureFailureAccess implements ListenableFuture<V> {
    private static final AtomicHelper ATOMIC_HELPER;
    private static final Object NULL;
    private static final long SPIN_THRESHOLD_NANOS = 1000;

    /* renamed from: a, reason: collision with root package name */
    static final boolean f14753a;

    /* renamed from: b, reason: collision with root package name */
    static final LazyLogger f14754b;

    @CheckForNull
    private volatile Listener listeners;

    @CheckForNull
    private volatile Object value;

    @CheckForNull
    private volatile Waiter waiters;

    private static abstract class AtomicHelper {
        private AtomicHelper() {
        }

        abstract boolean a(AbstractFuture abstractFuture, Listener listener, Listener listener2);

        abstract boolean b(AbstractFuture abstractFuture, Object obj, Object obj2);

        abstract boolean c(AbstractFuture abstractFuture, Waiter waiter, Waiter waiter2);

        abstract Listener d(AbstractFuture abstractFuture, Listener listener);

        abstract Waiter e(AbstractFuture abstractFuture, Waiter waiter);

        abstract void f(Waiter waiter, Waiter waiter2);

        abstract void g(Waiter waiter, Thread thread);
    }

    private static final class Cancellation {

        /* renamed from: c, reason: collision with root package name */
        static final Cancellation f14755c;

        /* renamed from: d, reason: collision with root package name */
        static final Cancellation f14756d;

        /* renamed from: a, reason: collision with root package name */
        final boolean f14757a;

        /* renamed from: b, reason: collision with root package name */
        final Throwable f14758b;

        static {
            if (AbstractFuture.f14753a) {
                f14756d = null;
                f14755c = null;
            } else {
                f14756d = new Cancellation(false, null);
                f14755c = new Cancellation(true, null);
            }
        }

        Cancellation(boolean z2, Throwable th) {
            this.f14757a = z2;
            this.f14758b = th;
        }
    }

    private static final class Failure {

        /* renamed from: b, reason: collision with root package name */
        static final Failure f14759b = new Failure(new Throwable("Failure occurred while trying to finish a future.") { // from class: com.google.common.util.concurrent.AbstractFuture.Failure.1
            @Override // java.lang.Throwable
            public synchronized Throwable fillInStackTrace() {
                return this;
            }
        });

        /* renamed from: a, reason: collision with root package name */
        final Throwable f14760a;

        Failure(Throwable th) {
            this.f14760a = (Throwable) Preconditions.checkNotNull(th);
        }
    }

    private static final class SafeAtomicHelper extends AtomicHelper {

        /* renamed from: a, reason: collision with root package name */
        final AtomicReferenceFieldUpdater f14765a;

        /* renamed from: b, reason: collision with root package name */
        final AtomicReferenceFieldUpdater f14766b;

        /* renamed from: c, reason: collision with root package name */
        final AtomicReferenceFieldUpdater f14767c;

        /* renamed from: d, reason: collision with root package name */
        final AtomicReferenceFieldUpdater f14768d;

        /* renamed from: e, reason: collision with root package name */
        final AtomicReferenceFieldUpdater f14769e;

        SafeAtomicHelper(AtomicReferenceFieldUpdater atomicReferenceFieldUpdater, AtomicReferenceFieldUpdater atomicReferenceFieldUpdater2, AtomicReferenceFieldUpdater atomicReferenceFieldUpdater3, AtomicReferenceFieldUpdater atomicReferenceFieldUpdater4, AtomicReferenceFieldUpdater atomicReferenceFieldUpdater5) {
            super();
            this.f14765a = atomicReferenceFieldUpdater;
            this.f14766b = atomicReferenceFieldUpdater2;
            this.f14767c = atomicReferenceFieldUpdater3;
            this.f14768d = atomicReferenceFieldUpdater4;
            this.f14769e = atomicReferenceFieldUpdater5;
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.AtomicHelper
        boolean a(AbstractFuture abstractFuture, Listener listener, Listener listener2) {
            return androidx.concurrent.futures.a.a(this.f14768d, abstractFuture, listener, listener2);
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.AtomicHelper
        boolean b(AbstractFuture abstractFuture, Object obj, Object obj2) {
            return androidx.concurrent.futures.a.a(this.f14769e, abstractFuture, obj, obj2);
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.AtomicHelper
        boolean c(AbstractFuture abstractFuture, Waiter waiter, Waiter waiter2) {
            return androidx.concurrent.futures.a.a(this.f14767c, abstractFuture, waiter, waiter2);
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.AtomicHelper
        Listener d(AbstractFuture abstractFuture, Listener listener) {
            return (Listener) this.f14768d.getAndSet(abstractFuture, listener);
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.AtomicHelper
        Waiter e(AbstractFuture abstractFuture, Waiter waiter) {
            return (Waiter) this.f14767c.getAndSet(abstractFuture, waiter);
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.AtomicHelper
        void f(Waiter waiter, Waiter waiter2) {
            this.f14766b.lazySet(waiter, waiter2);
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.AtomicHelper
        void g(Waiter waiter, Thread thread) {
            this.f14765a.lazySet(waiter, thread);
        }
    }

    private static final class SetFuture<V> implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        final AbstractFuture f14770a;

        /* renamed from: b, reason: collision with root package name */
        final ListenableFuture f14771b;

        SetFuture(AbstractFuture abstractFuture, ListenableFuture listenableFuture) {
            this.f14770a = abstractFuture;
            this.f14771b = listenableFuture;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.f14770a.value != this) {
                return;
            }
            if (AbstractFuture.ATOMIC_HELPER.b(this.f14770a, this, AbstractFuture.getFutureValue(this.f14771b))) {
                AbstractFuture.complete(this.f14770a, false);
            }
        }
    }

    private static final class SynchronizedHelper extends AtomicHelper {
        private SynchronizedHelper() {
            super();
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.AtomicHelper
        boolean a(AbstractFuture abstractFuture, Listener listener, Listener listener2) {
            synchronized (abstractFuture) {
                try {
                    if (abstractFuture.listeners != listener) {
                        return false;
                    }
                    abstractFuture.listeners = listener2;
                    return true;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.AtomicHelper
        boolean b(AbstractFuture abstractFuture, Object obj, Object obj2) {
            synchronized (abstractFuture) {
                try {
                    if (abstractFuture.value != obj) {
                        return false;
                    }
                    abstractFuture.value = obj2;
                    return true;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.AtomicHelper
        boolean c(AbstractFuture abstractFuture, Waiter waiter, Waiter waiter2) {
            synchronized (abstractFuture) {
                try {
                    if (abstractFuture.waiters != waiter) {
                        return false;
                    }
                    abstractFuture.waiters = waiter2;
                    return true;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.AtomicHelper
        Listener d(AbstractFuture abstractFuture, Listener listener) {
            Listener listener2;
            synchronized (abstractFuture) {
                try {
                    listener2 = abstractFuture.listeners;
                    if (listener2 != listener) {
                        abstractFuture.listeners = listener;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return listener2;
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.AtomicHelper
        Waiter e(AbstractFuture abstractFuture, Waiter waiter) {
            Waiter waiter2;
            synchronized (abstractFuture) {
                try {
                    waiter2 = abstractFuture.waiters;
                    if (waiter2 != waiter) {
                        abstractFuture.waiters = waiter;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return waiter2;
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.AtomicHelper
        void f(Waiter waiter, Waiter waiter2) {
            waiter.f14780b = waiter2;
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.AtomicHelper
        void g(Waiter waiter, Thread thread) {
            waiter.f14779a = thread;
        }
    }

    interface Trusted<V> extends ListenableFuture<V> {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static abstract class TrustedFuture<V> extends AbstractFuture<V> implements Trusted<V> {
        TrustedFuture() {
        }

        @Override // com.google.common.util.concurrent.AbstractFuture, com.google.common.util.concurrent.ListenableFuture
        public void addListener(Runnable runnable, Executor executor) {
            super.addListener(runnable, executor);
        }

        @Override // com.google.common.util.concurrent.AbstractFuture, java.util.concurrent.Future
        @CanIgnoreReturnValue
        public boolean cancel(boolean z2) {
            return super.cancel(z2);
        }

        @Override // com.google.common.util.concurrent.AbstractFuture, java.util.concurrent.Future
        @CanIgnoreReturnValue
        @ParametricNullness
        public V get() throws ExecutionException, InterruptedException {
            return (V) super.get();
        }

        @Override // com.google.common.util.concurrent.AbstractFuture, java.util.concurrent.Future
        public boolean isCancelled() {
            return super.isCancelled();
        }

        @Override // com.google.common.util.concurrent.AbstractFuture, java.util.concurrent.Future
        public boolean isDone() {
            return super.isDone();
        }

        @Override // com.google.common.util.concurrent.AbstractFuture, java.util.concurrent.Future
        @CanIgnoreReturnValue
        @ParametricNullness
        public V get(long j2, TimeUnit timeUnit) throws ExecutionException, InterruptedException, TimeoutException {
            return (V) super.get(j2, timeUnit);
        }
    }

    private static final class UnsafeAtomicHelper extends AtomicHelper {

        /* renamed from: a, reason: collision with root package name */
        static final Unsafe f14772a;

        /* renamed from: b, reason: collision with root package name */
        static final long f14773b;

        /* renamed from: c, reason: collision with root package name */
        static final long f14774c;

        /* renamed from: d, reason: collision with root package name */
        static final long f14775d;

        /* renamed from: e, reason: collision with root package name */
        static final long f14776e;

        /* renamed from: f, reason: collision with root package name */
        static final long f14777f;

        static {
            Unsafe unsafe;
            try {
                try {
                    unsafe = Unsafe.getUnsafe();
                } catch (SecurityException unused) {
                    unsafe = (Unsafe) AccessController.doPrivileged(new PrivilegedExceptionAction<Unsafe>() { // from class: com.google.common.util.concurrent.AbstractFuture.UnsafeAtomicHelper.1
                        @Override // java.security.PrivilegedExceptionAction
                        public Unsafe run() throws Exception {
                            for (Field field : Unsafe.class.getDeclaredFields()) {
                                field.setAccessible(true);
                                Object obj = field.get(null);
                                if (Unsafe.class.isInstance(obj)) {
                                    return (Unsafe) Unsafe.class.cast(obj);
                                }
                            }
                            throw new NoSuchFieldError("the Unsafe");
                        }
                    });
                }
                try {
                    f14774c = unsafe.objectFieldOffset(AbstractFuture.class.getDeclaredField("waiters"));
                    f14773b = unsafe.objectFieldOffset(AbstractFuture.class.getDeclaredField("listeners"));
                    f14775d = unsafe.objectFieldOffset(AbstractFuture.class.getDeclaredField("value"));
                    f14776e = unsafe.objectFieldOffset(Waiter.class.getDeclaredField("a"));
                    f14777f = unsafe.objectFieldOffset(Waiter.class.getDeclaredField("b"));
                    f14772a = unsafe;
                } catch (NoSuchFieldException e2) {
                    throw new RuntimeException(e2);
                }
            } catch (PrivilegedActionException e3) {
                throw new RuntimeException("Could not initialize intrinsics", e3.getCause());
            }
        }

        private UnsafeAtomicHelper() {
            super();
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.AtomicHelper
        boolean a(AbstractFuture abstractFuture, Listener listener, Listener listener2) {
            return d.a(f14772a, abstractFuture, f14773b, listener, listener2);
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.AtomicHelper
        boolean b(AbstractFuture abstractFuture, Object obj, Object obj2) {
            return d.a(f14772a, abstractFuture, f14775d, obj, obj2);
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.AtomicHelper
        boolean c(AbstractFuture abstractFuture, Waiter waiter, Waiter waiter2) {
            return d.a(f14772a, abstractFuture, f14774c, waiter, waiter2);
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.AtomicHelper
        Listener d(AbstractFuture abstractFuture, Listener listener) {
            Listener listener2;
            do {
                listener2 = abstractFuture.listeners;
                if (listener == listener2) {
                    return listener2;
                }
            } while (!a(abstractFuture, listener2, listener));
            return listener2;
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.AtomicHelper
        Waiter e(AbstractFuture abstractFuture, Waiter waiter) {
            Waiter waiter2;
            do {
                waiter2 = abstractFuture.waiters;
                if (waiter == waiter2) {
                    return waiter2;
                }
            } while (!c(abstractFuture, waiter2, waiter));
            return waiter2;
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.AtomicHelper
        void f(Waiter waiter, Waiter waiter2) {
            f14772a.putObject(waiter, f14777f, waiter2);
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.AtomicHelper
        void g(Waiter waiter, Thread thread) {
            f14772a.putObject(waiter, f14776e, thread);
        }
    }

    private static final class Waiter {

        /* renamed from: c, reason: collision with root package name */
        static final Waiter f14778c = new Waiter(false);

        /* renamed from: a, reason: collision with root package name */
        volatile Thread f14779a;

        /* renamed from: b, reason: collision with root package name */
        volatile Waiter f14780b;

        Waiter(boolean z2) {
        }

        void a(Waiter waiter) {
            AbstractFuture.ATOMIC_HELPER.f(this, waiter);
        }

        void b() {
            Thread thread = this.f14779a;
            if (thread != null) {
                this.f14779a = null;
                LockSupport.unpark(thread);
            }
        }

        Waiter() {
            AbstractFuture.ATOMIC_HELPER.g(this, Thread.currentThread());
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Exception] */
    /* JADX WARN: Type inference failed for: r0v6, types: [java.util.logging.Logger] */
    /* JADX WARN: Type inference failed for: r10v0, types: [com.google.common.util.concurrent.AbstractFuture$SafeAtomicHelper] */
    /* JADX WARN: Type inference failed for: r1v3, types: [com.google.common.util.concurrent.AbstractFuture$1] */
    /* JADX WARN: Type inference failed for: r1v4 */
    /* JADX WARN: Type inference failed for: r1v5, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r1v9 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.google.common.util.concurrent.AbstractFuture$UnsafeAtomicHelper] */
    static {
        boolean z2;
        SynchronizedHelper synchronizedHelper;
        try {
            z2 = Boolean.parseBoolean(System.getProperty("guava.concurrent.generate_cancellation_cause", RequestConstant.FALSE));
        } catch (SecurityException unused) {
            z2 = false;
        }
        f14753a = z2;
        f14754b = new LazyLogger(AbstractFuture.class);
        ?? r1 = 0;
        r1 = 0;
        try {
            synchronizedHelper = new UnsafeAtomicHelper();
            e = null;
        } catch (Error | Exception e2) {
            e = e2;
            try {
                synchronizedHelper = new SafeAtomicHelper(AtomicReferenceFieldUpdater.newUpdater(Waiter.class, Thread.class, "a"), AtomicReferenceFieldUpdater.newUpdater(Waiter.class, Waiter.class, "b"), AtomicReferenceFieldUpdater.newUpdater(AbstractFuture.class, Waiter.class, "waiters"), AtomicReferenceFieldUpdater.newUpdater(AbstractFuture.class, Listener.class, "listeners"), AtomicReferenceFieldUpdater.newUpdater(AbstractFuture.class, Object.class, "value"));
            } catch (Error | Exception e3) {
                synchronizedHelper = new SynchronizedHelper();
                r1 = e3;
            }
        }
        ATOMIC_HELPER = synchronizedHelper;
        if (r1 != 0) {
            LazyLogger lazyLogger = f14754b;
            Logger loggerA = lazyLogger.a();
            Level level = Level.SEVERE;
            loggerA.log(level, "UnsafeAtomicHelper is broken!", e);
            lazyLogger.a().log(level, "SafeAtomicHelper is broken!", r1);
        }
        NULL = new Object();
    }

    protected AbstractFuture() {
    }

    private void addDoneString(StringBuilder sb) {
        try {
            Object uninterruptibly = getUninterruptibly(this);
            sb.append("SUCCESS, result=[");
            appendResultObject(sb, uninterruptibly);
            sb.append("]");
        } catch (CancellationException unused) {
            sb.append("CANCELLED");
        } catch (ExecutionException e2) {
            sb.append("FAILURE, cause=[");
            sb.append(e2.getCause());
            sb.append("]");
        } catch (Exception e3) {
            sb.append("UNKNOWN, cause=[");
            sb.append(e3.getClass());
            sb.append(" thrown from get()]");
        }
    }

    private void addPendingString(StringBuilder sb) {
        String strEmptyToNull;
        int length = sb.length();
        sb.append("PENDING");
        Object obj = this.value;
        if (obj instanceof SetFuture) {
            sb.append(", setFuture=[");
            appendUserObject(sb, ((SetFuture) obj).f14771b);
            sb.append("]");
        } else {
            try {
                strEmptyToNull = Strings.emptyToNull(n());
            } catch (Exception | StackOverflowError e2) {
                strEmptyToNull = "Exception thrown from implementation: " + e2.getClass();
            }
            if (strEmptyToNull != null) {
                sb.append(", info=[");
                sb.append(strEmptyToNull);
                sb.append("]");
            }
        }
        if (isDone()) {
            sb.delete(length, sb.length());
            addDoneString(sb);
        }
    }

    private void appendResultObject(StringBuilder sb, @CheckForNull Object obj) {
        if (obj == null) {
            sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
        } else {
            if (obj == this) {
                sb.append("this future");
                return;
            }
            sb.append(obj.getClass().getName());
            sb.append("@");
            sb.append(Integer.toHexString(System.identityHashCode(obj)));
        }
    }

    private void appendUserObject(StringBuilder sb, @CheckForNull Object obj) {
        try {
            if (obj == this) {
                sb.append("this future");
            } else {
                sb.append(obj);
            }
        } catch (Exception e2) {
            e = e2;
            sb.append("Exception thrown from implementation: ");
            sb.append(e.getClass());
        } catch (StackOverflowError e3) {
            e = e3;
            sb.append("Exception thrown from implementation: ");
            sb.append(e.getClass());
        }
    }

    private static CancellationException cancellationExceptionWithCause(String str, @CheckForNull Throwable th) {
        CancellationException cancellationException = new CancellationException(str);
        cancellationException.initCause(th);
        return cancellationException;
    }

    @CheckForNull
    private Listener clearListeners(@CheckForNull Listener listener) {
        Listener listener2 = listener;
        Listener listenerD = ATOMIC_HELPER.d(this, Listener.f14761d);
        while (listenerD != null) {
            Listener listener3 = listenerD.f14764c;
            listenerD.f14764c = listener2;
            listener2 = listenerD;
            listenerD = listener3;
        }
        return listener2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void complete(AbstractFuture<?> abstractFuture, boolean z2) {
        Listener listener = null;
        while (true) {
            abstractFuture.releaseWaiters();
            if (z2) {
                abstractFuture.l();
                z2 = false;
            }
            abstractFuture.k();
            Listener listenerClearListeners = abstractFuture.clearListeners(listener);
            while (listenerClearListeners != null) {
                listener = listenerClearListeners.f14764c;
                Runnable runnable = listenerClearListeners.f14762a;
                Objects.requireNonNull(runnable);
                Runnable runnable2 = runnable;
                if (runnable2 instanceof SetFuture) {
                    SetFuture setFuture = (SetFuture) runnable2;
                    abstractFuture = setFuture.f14770a;
                    if (((AbstractFuture) abstractFuture).value == setFuture) {
                        if (ATOMIC_HELPER.b(abstractFuture, setFuture, getFutureValue(setFuture.f14771b))) {
                            break;
                        }
                    } else {
                        continue;
                    }
                } else {
                    Executor executor = listenerClearListeners.f14763b;
                    Objects.requireNonNull(executor);
                    executeListener(runnable2, executor);
                }
                listenerClearListeners = listener;
            }
            return;
        }
    }

    private static void executeListener(Runnable runnable, Executor executor) {
        try {
            executor.execute(runnable);
        } catch (Exception e2) {
            f14754b.a().log(Level.SEVERE, "RuntimeException while executing runnable " + runnable + " with executor " + executor, (Throwable) e2);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @ParametricNullness
    private V getDoneValue(Object obj) throws ExecutionException {
        if (obj instanceof Cancellation) {
            throw cancellationExceptionWithCause("Task was cancelled.", ((Cancellation) obj).f14758b);
        }
        if (obj instanceof Failure) {
            throw new ExecutionException(((Failure) obj).f14760a);
        }
        return obj == NULL ? (V) NullnessCasts.b() : obj;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static Object getFutureValue(ListenableFuture<?> listenableFuture) {
        Throwable thTryInternalFastPathGetFailure;
        if (listenableFuture instanceof Trusted) {
            Object cancellation = ((AbstractFuture) listenableFuture).value;
            if (cancellation instanceof Cancellation) {
                Cancellation cancellation2 = (Cancellation) cancellation;
                if (cancellation2.f14757a) {
                    cancellation = cancellation2.f14758b != null ? new Cancellation(false, cancellation2.f14758b) : Cancellation.f14756d;
                }
            }
            Objects.requireNonNull(cancellation);
            return cancellation;
        }
        if ((listenableFuture instanceof InternalFutureFailureAccess) && (thTryInternalFastPathGetFailure = InternalFutures.tryInternalFastPathGetFailure((InternalFutureFailureAccess) listenableFuture)) != null) {
            return new Failure(thTryInternalFastPathGetFailure);
        }
        boolean zIsCancelled = listenableFuture.isCancelled();
        if ((!f14753a) && zIsCancelled) {
            Cancellation cancellation3 = Cancellation.f14756d;
            Objects.requireNonNull(cancellation3);
            return cancellation3;
        }
        try {
            Object uninterruptibly = getUninterruptibly(listenableFuture);
            if (!zIsCancelled) {
                return uninterruptibly == null ? NULL : uninterruptibly;
            }
            return new Cancellation(false, new IllegalArgumentException("get() did not throw CancellationException, despite reporting isCancelled() == true: " + listenableFuture));
        } catch (Error e2) {
            e = e2;
            return new Failure(e);
        } catch (CancellationException e3) {
            if (zIsCancelled) {
                return new Cancellation(false, e3);
            }
            return new Failure(new IllegalArgumentException("get() threw CancellationException, despite reporting isCancelled() == false: " + listenableFuture, e3));
        } catch (ExecutionException e4) {
            if (!zIsCancelled) {
                return new Failure(e4.getCause());
            }
            return new Cancellation(false, new IllegalArgumentException("get() did not throw CancellationException, despite reporting isCancelled() == true: " + listenableFuture, e4));
        } catch (Exception e5) {
            e = e5;
            return new Failure(e);
        }
    }

    @ParametricNullness
    private static <V> V getUninterruptibly(Future<V> future) throws ExecutionException {
        V v2;
        boolean z2 = false;
        while (true) {
            try {
                v2 = future.get();
                break;
            } catch (InterruptedException unused) {
                z2 = true;
            } catch (Throwable th) {
                if (z2) {
                    Thread.currentThread().interrupt();
                }
                throw th;
            }
        }
        if (z2) {
            Thread.currentThread().interrupt();
        }
        return v2;
    }

    private void releaseWaiters() {
        for (Waiter waiterE = ATOMIC_HELPER.e(this, Waiter.f14778c); waiterE != null; waiterE = waiterE.f14780b) {
            waiterE.b();
        }
    }

    private void removeWaiter(Waiter waiter) {
        waiter.f14779a = null;
        while (true) {
            Waiter waiter2 = this.waiters;
            if (waiter2 == Waiter.f14778c) {
                return;
            }
            Waiter waiter3 = null;
            while (waiter2 != null) {
                Waiter waiter4 = waiter2.f14780b;
                if (waiter2.f14779a != null) {
                    waiter3 = waiter2;
                } else if (waiter3 != null) {
                    waiter3.f14780b = waiter4;
                    if (waiter3.f14779a == null) {
                        break;
                    }
                } else if (!ATOMIC_HELPER.c(this, waiter2, waiter4)) {
                    break;
                }
                waiter2 = waiter4;
            }
            return;
        }
    }

    @Override // com.google.common.util.concurrent.internal.InternalFutureFailureAccess
    protected final Throwable a() {
        if (!(this instanceof Trusted)) {
            return null;
        }
        Object obj = this.value;
        if (obj instanceof Failure) {
            return ((Failure) obj).f14760a;
        }
        return null;
    }

    @Override // com.google.common.util.concurrent.ListenableFuture
    public void addListener(Runnable runnable, Executor executor) {
        Listener listener;
        Preconditions.checkNotNull(runnable, "Runnable was null.");
        Preconditions.checkNotNull(executor, "Executor was null.");
        if (!isDone() && (listener = this.listeners) != Listener.f14761d) {
            Listener listener2 = new Listener(runnable, executor);
            do {
                listener2.f14764c = listener;
                if (ATOMIC_HELPER.a(this, listener, listener2)) {
                    return;
                } else {
                    listener = this.listeners;
                }
            } while (listener != Listener.f14761d);
        }
        executeListener(runnable, executor);
    }

    @Override // java.util.concurrent.Future
    @CanIgnoreReturnValue
    public boolean cancel(boolean z2) {
        Cancellation cancellation;
        Object obj = this.value;
        if (!(obj == null) && !(obj instanceof SetFuture)) {
            return false;
        }
        if (f14753a) {
            cancellation = new Cancellation(z2, new CancellationException("Future.cancel() was called."));
        } else {
            cancellation = z2 ? Cancellation.f14755c : Cancellation.f14756d;
            Objects.requireNonNull(cancellation);
        }
        AbstractFuture<V> abstractFuture = this;
        boolean z3 = false;
        while (true) {
            if (ATOMIC_HELPER.b(abstractFuture, obj, cancellation)) {
                complete(abstractFuture, z2);
                if (!(obj instanceof SetFuture)) {
                    return true;
                }
                ListenableFuture listenableFuture = ((SetFuture) obj).f14771b;
                if (!(listenableFuture instanceof Trusted)) {
                    listenableFuture.cancel(z2);
                    return true;
                }
                abstractFuture = (AbstractFuture) listenableFuture;
                obj = abstractFuture.value;
                if (!(obj == null) && !(obj instanceof SetFuture)) {
                    return true;
                }
                z3 = true;
            } else {
                obj = abstractFuture.value;
                if (!(obj instanceof SetFuture)) {
                    return z3;
                }
            }
        }
    }

    @Override // java.util.concurrent.Future
    @CanIgnoreReturnValue
    @ParametricNullness
    public V get(long j2, TimeUnit timeUnit) throws ExecutionException, InterruptedException, TimeoutException {
        long nanos = timeUnit.toNanos(j2);
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        Object obj = this.value;
        if ((obj != null) && (!(obj instanceof SetFuture))) {
            return getDoneValue(obj);
        }
        long jNanoTime = nanos > 0 ? System.nanoTime() + nanos : 0L;
        if (nanos >= 1000) {
            Waiter waiter = this.waiters;
            if (waiter != Waiter.f14778c) {
                Waiter waiter2 = new Waiter();
                do {
                    waiter2.a(waiter);
                    if (ATOMIC_HELPER.c(this, waiter, waiter2)) {
                        do {
                            OverflowAvoidingLockSupport.a(this, nanos);
                            if (Thread.interrupted()) {
                                removeWaiter(waiter2);
                                throw new InterruptedException();
                            }
                            Object obj2 = this.value;
                            if ((obj2 != null) && (!(obj2 instanceof SetFuture))) {
                                return getDoneValue(obj2);
                            }
                            nanos = jNanoTime - System.nanoTime();
                        } while (nanos >= 1000);
                        removeWaiter(waiter2);
                    } else {
                        waiter = this.waiters;
                    }
                } while (waiter != Waiter.f14778c);
            }
            Object obj3 = this.value;
            Objects.requireNonNull(obj3);
            return getDoneValue(obj3);
        }
        while (nanos > 0) {
            Object obj4 = this.value;
            if ((obj4 != null) && (!(obj4 instanceof SetFuture))) {
                return getDoneValue(obj4);
            }
            if (Thread.interrupted()) {
                throw new InterruptedException();
            }
            nanos = jNanoTime - System.nanoTime();
        }
        String string = toString();
        String string2 = timeUnit.toString();
        Locale locale = Locale.ROOT;
        String lowerCase = string2.toLowerCase(locale);
        String str = "Waited " + j2 + " " + timeUnit.toString().toLowerCase(locale);
        if (nanos + 1000 < 0) {
            String str2 = str + " (plus ";
            long j3 = -nanos;
            long jConvert = timeUnit.convert(j3, TimeUnit.NANOSECONDS);
            long nanos2 = j3 - timeUnit.toNanos(jConvert);
            boolean z2 = jConvert == 0 || nanos2 > 1000;
            if (jConvert > 0) {
                String str3 = str2 + jConvert + " " + lowerCase;
                if (z2) {
                    str3 = str3 + ",";
                }
                str2 = str3 + " ";
            }
            if (z2) {
                str2 = str2 + nanos2 + " nanoseconds ";
            }
            str = str2 + "delay)";
        }
        if (isDone()) {
            throw new TimeoutException(str + " but future completed as timeout expired");
        }
        throw new TimeoutException(str + " for " + string);
    }

    @Override // java.util.concurrent.Future
    public boolean isCancelled() {
        return this.value instanceof Cancellation;
    }

    @Override // java.util.concurrent.Future
    public boolean isDone() {
        return (!(r0 instanceof SetFuture)) & (this.value != null);
    }

    protected void k() {
    }

    protected void l() {
    }

    final void m(Future future) {
        if ((future != null) && isCancelled()) {
            future.cancel(o());
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected String n() {
        if (!(this instanceof ScheduledFuture)) {
            return null;
        }
        return "remaining delay=[" + ((ScheduledFuture) this).getDelay(TimeUnit.MILLISECONDS) + " ms]";
    }

    protected final boolean o() {
        Object obj = this.value;
        return (obj instanceof Cancellation) && ((Cancellation) obj).f14757a;
    }

    protected boolean set(Object obj) {
        if (obj == null) {
            obj = NULL;
        }
        if (!ATOMIC_HELPER.b(this, null, obj)) {
            return false;
        }
        complete(this, false);
        return true;
    }

    protected boolean setException(Throwable th) {
        if (!ATOMIC_HELPER.b(this, null, new Failure((Throwable) Preconditions.checkNotNull(th)))) {
            return false;
        }
        complete(this, false);
        return true;
    }

    protected boolean setFuture(ListenableFuture listenableFuture) {
        Failure failure;
        Preconditions.checkNotNull(listenableFuture);
        Object obj = this.value;
        if (obj == null) {
            if (listenableFuture.isDone()) {
                if (!ATOMIC_HELPER.b(this, null, getFutureValue(listenableFuture))) {
                    return false;
                }
                complete(this, false);
                return true;
            }
            SetFuture setFuture = new SetFuture(this, listenableFuture);
            if (ATOMIC_HELPER.b(this, null, setFuture)) {
                try {
                    listenableFuture.addListener(setFuture, DirectExecutor.INSTANCE);
                } catch (Throwable th) {
                    try {
                        failure = new Failure(th);
                    } catch (Error | Exception unused) {
                        failure = Failure.f14759b;
                    }
                    ATOMIC_HELPER.b(this, setFuture, failure);
                }
                return true;
            }
            obj = this.value;
        }
        if (obj instanceof Cancellation) {
            listenableFuture.cancel(((Cancellation) obj).f14757a);
        }
        return false;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (getClass().getName().startsWith("com.google.common.util.concurrent.")) {
            sb.append(getClass().getSimpleName());
        } else {
            sb.append(getClass().getName());
        }
        sb.append('@');
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append("[status=");
        if (isCancelled()) {
            sb.append("CANCELLED");
        } else if (isDone()) {
            addDoneString(sb);
        } else {
            addPendingString(sb);
        }
        sb.append("]");
        return sb.toString();
    }

    private static final class Listener {

        /* renamed from: d, reason: collision with root package name */
        static final Listener f14761d = new Listener();

        /* renamed from: a, reason: collision with root package name */
        final Runnable f14762a;

        /* renamed from: b, reason: collision with root package name */
        final Executor f14763b;

        /* renamed from: c, reason: collision with root package name */
        Listener f14764c;

        Listener(Runnable runnable, Executor executor) {
            this.f14762a = runnable;
            this.f14763b = executor;
        }

        Listener() {
            this.f14762a = null;
            this.f14763b = null;
        }
    }

    @Override // java.util.concurrent.Future
    @CanIgnoreReturnValue
    @ParametricNullness
    public V get() throws ExecutionException, InterruptedException {
        Object obj;
        if (!Thread.interrupted()) {
            Object obj2 = this.value;
            if ((obj2 != null) & (!(obj2 instanceof SetFuture))) {
                return getDoneValue(obj2);
            }
            Waiter waiter = this.waiters;
            if (waiter != Waiter.f14778c) {
                Waiter waiter2 = new Waiter();
                do {
                    waiter2.a(waiter);
                    if (ATOMIC_HELPER.c(this, waiter, waiter2)) {
                        do {
                            LockSupport.park(this);
                            if (!Thread.interrupted()) {
                                obj = this.value;
                            } else {
                                removeWaiter(waiter2);
                                throw new InterruptedException();
                            }
                        } while (!((obj != null) & (!(obj instanceof SetFuture))));
                        return getDoneValue(obj);
                    }
                    waiter = this.waiters;
                } while (waiter != Waiter.f14778c);
            }
            Object obj3 = this.value;
            Objects.requireNonNull(obj3);
            return getDoneValue(obj3);
        }
        throw new InterruptedException();
    }
}
