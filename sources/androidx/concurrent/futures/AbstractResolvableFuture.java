package androidx.concurrent.futures;

import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import anetwork.channel.util.RequestConstant;
import com.google.common.util.concurrent.ListenableFuture;
import com.umeng.analytics.pro.bc;
import java.util.Locale;
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

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* loaded from: classes.dex */
public abstract class AbstractResolvableFuture<V> implements ListenableFuture<V> {
    private static final Object NULL;
    private static final long SPIN_THRESHOLD_NANOS = 1000;

    /* renamed from: e, reason: collision with root package name */
    static final AtomicHelper f2519e;

    /* renamed from: a, reason: collision with root package name */
    volatile Object f2520a;

    /* renamed from: b, reason: collision with root package name */
    volatile Listener f2521b;

    /* renamed from: c, reason: collision with root package name */
    volatile Waiter f2522c;

    /* renamed from: d, reason: collision with root package name */
    static final boolean f2518d = Boolean.parseBoolean(System.getProperty("guava.concurrent.generate_cancellation_cause", RequestConstant.FALSE));
    private static final Logger log = Logger.getLogger(AbstractResolvableFuture.class.getName());

    private static abstract class AtomicHelper {
        private AtomicHelper() {
        }

        abstract boolean a(AbstractResolvableFuture abstractResolvableFuture, Listener listener, Listener listener2);

        abstract boolean b(AbstractResolvableFuture abstractResolvableFuture, Object obj, Object obj2);

        abstract boolean c(AbstractResolvableFuture abstractResolvableFuture, Waiter waiter, Waiter waiter2);

        abstract void d(Waiter waiter, Waiter waiter2);

        abstract void e(Waiter waiter, Thread thread);
    }

    private static final class Cancellation {

        /* renamed from: c, reason: collision with root package name */
        static final Cancellation f2523c;

        /* renamed from: d, reason: collision with root package name */
        static final Cancellation f2524d;

        /* renamed from: a, reason: collision with root package name */
        final boolean f2525a;

        /* renamed from: b, reason: collision with root package name */
        final Throwable f2526b;

        static {
            if (AbstractResolvableFuture.f2518d) {
                f2524d = null;
                f2523c = null;
            } else {
                f2524d = new Cancellation(false, null);
                f2523c = new Cancellation(true, null);
            }
        }

        Cancellation(boolean z2, Throwable th) {
            this.f2525a = z2;
            this.f2526b = th;
        }
    }

    private static final class Failure {

        /* renamed from: b, reason: collision with root package name */
        static final Failure f2527b = new Failure(new Throwable("Failure occurred while trying to finish a future.") { // from class: androidx.concurrent.futures.AbstractResolvableFuture.Failure.1
            @Override // java.lang.Throwable
            public synchronized Throwable fillInStackTrace() {
                return this;
            }
        });

        /* renamed from: a, reason: collision with root package name */
        final Throwable f2528a;

        Failure(Throwable th) {
            this.f2528a = (Throwable) AbstractResolvableFuture.b(th);
        }
    }

    private static final class Listener {

        /* renamed from: d, reason: collision with root package name */
        static final Listener f2529d = new Listener(null, null);

        /* renamed from: a, reason: collision with root package name */
        final Runnable f2530a;

        /* renamed from: b, reason: collision with root package name */
        final Executor f2531b;

        /* renamed from: c, reason: collision with root package name */
        Listener f2532c;

        Listener(Runnable runnable, Executor executor) {
            this.f2530a = runnable;
            this.f2531b = executor;
        }
    }

    private static final class SafeAtomicHelper extends AtomicHelper {

        /* renamed from: a, reason: collision with root package name */
        final AtomicReferenceFieldUpdater f2533a;

        /* renamed from: b, reason: collision with root package name */
        final AtomicReferenceFieldUpdater f2534b;

        /* renamed from: c, reason: collision with root package name */
        final AtomicReferenceFieldUpdater f2535c;

        /* renamed from: d, reason: collision with root package name */
        final AtomicReferenceFieldUpdater f2536d;

        /* renamed from: e, reason: collision with root package name */
        final AtomicReferenceFieldUpdater f2537e;

        SafeAtomicHelper(AtomicReferenceFieldUpdater atomicReferenceFieldUpdater, AtomicReferenceFieldUpdater atomicReferenceFieldUpdater2, AtomicReferenceFieldUpdater atomicReferenceFieldUpdater3, AtomicReferenceFieldUpdater atomicReferenceFieldUpdater4, AtomicReferenceFieldUpdater atomicReferenceFieldUpdater5) {
            super();
            this.f2533a = atomicReferenceFieldUpdater;
            this.f2534b = atomicReferenceFieldUpdater2;
            this.f2535c = atomicReferenceFieldUpdater3;
            this.f2536d = atomicReferenceFieldUpdater4;
            this.f2537e = atomicReferenceFieldUpdater5;
        }

        @Override // androidx.concurrent.futures.AbstractResolvableFuture.AtomicHelper
        boolean a(AbstractResolvableFuture abstractResolvableFuture, Listener listener, Listener listener2) {
            return a.a(this.f2536d, abstractResolvableFuture, listener, listener2);
        }

        @Override // androidx.concurrent.futures.AbstractResolvableFuture.AtomicHelper
        boolean b(AbstractResolvableFuture abstractResolvableFuture, Object obj, Object obj2) {
            return a.a(this.f2537e, abstractResolvableFuture, obj, obj2);
        }

        @Override // androidx.concurrent.futures.AbstractResolvableFuture.AtomicHelper
        boolean c(AbstractResolvableFuture abstractResolvableFuture, Waiter waiter, Waiter waiter2) {
            return a.a(this.f2535c, abstractResolvableFuture, waiter, waiter2);
        }

        @Override // androidx.concurrent.futures.AbstractResolvableFuture.AtomicHelper
        void d(Waiter waiter, Waiter waiter2) {
            this.f2534b.lazySet(waiter, waiter2);
        }

        @Override // androidx.concurrent.futures.AbstractResolvableFuture.AtomicHelper
        void e(Waiter waiter, Thread thread) {
            this.f2533a.lazySet(waiter, thread);
        }
    }

    private static final class SetFuture<V> implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        final AbstractResolvableFuture f2538a;

        /* renamed from: b, reason: collision with root package name */
        final ListenableFuture f2539b;

        SetFuture(AbstractResolvableFuture abstractResolvableFuture, ListenableFuture listenableFuture) {
            this.f2538a = abstractResolvableFuture;
            this.f2539b = listenableFuture;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.f2538a.f2520a != this) {
                return;
            }
            if (AbstractResolvableFuture.f2519e.b(this.f2538a, this, AbstractResolvableFuture.d(this.f2539b))) {
                AbstractResolvableFuture.c(this.f2538a);
            }
        }
    }

    private static final class SynchronizedHelper extends AtomicHelper {
        SynchronizedHelper() {
            super();
        }

        @Override // androidx.concurrent.futures.AbstractResolvableFuture.AtomicHelper
        boolean a(AbstractResolvableFuture abstractResolvableFuture, Listener listener, Listener listener2) {
            synchronized (abstractResolvableFuture) {
                try {
                    if (abstractResolvableFuture.f2521b != listener) {
                        return false;
                    }
                    abstractResolvableFuture.f2521b = listener2;
                    return true;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // androidx.concurrent.futures.AbstractResolvableFuture.AtomicHelper
        boolean b(AbstractResolvableFuture abstractResolvableFuture, Object obj, Object obj2) {
            synchronized (abstractResolvableFuture) {
                try {
                    if (abstractResolvableFuture.f2520a != obj) {
                        return false;
                    }
                    abstractResolvableFuture.f2520a = obj2;
                    return true;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // androidx.concurrent.futures.AbstractResolvableFuture.AtomicHelper
        boolean c(AbstractResolvableFuture abstractResolvableFuture, Waiter waiter, Waiter waiter2) {
            synchronized (abstractResolvableFuture) {
                try {
                    if (abstractResolvableFuture.f2522c != waiter) {
                        return false;
                    }
                    abstractResolvableFuture.f2522c = waiter2;
                    return true;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // androidx.concurrent.futures.AbstractResolvableFuture.AtomicHelper
        void d(Waiter waiter, Waiter waiter2) {
            waiter.f2542b = waiter2;
        }

        @Override // androidx.concurrent.futures.AbstractResolvableFuture.AtomicHelper
        void e(Waiter waiter, Thread thread) {
            waiter.f2541a = thread;
        }
    }

    private static final class Waiter {

        /* renamed from: c, reason: collision with root package name */
        static final Waiter f2540c = new Waiter(false);

        /* renamed from: a, reason: collision with root package name */
        volatile Thread f2541a;

        /* renamed from: b, reason: collision with root package name */
        volatile Waiter f2542b;

        Waiter(boolean z2) {
        }

        void a(Waiter waiter) {
            AbstractResolvableFuture.f2519e.d(this, waiter);
        }

        void b() {
            Thread thread = this.f2541a;
            if (thread != null) {
                this.f2541a = null;
                LockSupport.unpark(thread);
            }
        }

        Waiter() {
            AbstractResolvableFuture.f2519e.e(this, Thread.currentThread());
        }
    }

    static {
        AtomicHelper synchronizedHelper;
        try {
            synchronizedHelper = new SafeAtomicHelper(AtomicReferenceFieldUpdater.newUpdater(Waiter.class, Thread.class, "a"), AtomicReferenceFieldUpdater.newUpdater(Waiter.class, Waiter.class, "b"), AtomicReferenceFieldUpdater.newUpdater(AbstractResolvableFuture.class, Waiter.class, bc.aL), AtomicReferenceFieldUpdater.newUpdater(AbstractResolvableFuture.class, Listener.class, "b"), AtomicReferenceFieldUpdater.newUpdater(AbstractResolvableFuture.class, Object.class, "a"));
            th = null;
        } catch (Throwable th) {
            th = th;
            synchronizedHelper = new SynchronizedHelper();
        }
        f2519e = synchronizedHelper;
        if (th != null) {
            log.log(Level.SEVERE, "SafeAtomicHelper is broken!", th);
        }
        NULL = new Object();
    }

    protected AbstractResolvableFuture() {
    }

    private void addDoneString(StringBuilder sb) {
        try {
            Object objE = e(this);
            sb.append("SUCCESS, result=[");
            sb.append(userObjectToString(objE));
            sb.append("]");
        } catch (CancellationException unused) {
            sb.append("CANCELLED");
        } catch (RuntimeException e2) {
            sb.append("UNKNOWN, cause=[");
            sb.append(e2.getClass());
            sb.append(" thrown from get()]");
        } catch (ExecutionException e3) {
            sb.append("FAILURE, cause=[");
            sb.append(e3.getCause());
            sb.append("]");
        }
    }

    static Object b(Object obj) {
        obj.getClass();
        return obj;
    }

    static void c(AbstractResolvableFuture abstractResolvableFuture) {
        Listener listener = null;
        while (true) {
            abstractResolvableFuture.releaseWaiters();
            abstractResolvableFuture.a();
            Listener listenerClearListeners = abstractResolvableFuture.clearListeners(listener);
            while (listenerClearListeners != null) {
                listener = listenerClearListeners.f2532c;
                Runnable runnable = listenerClearListeners.f2530a;
                if (runnable instanceof SetFuture) {
                    SetFuture setFuture = (SetFuture) runnable;
                    abstractResolvableFuture = setFuture.f2538a;
                    if (abstractResolvableFuture.f2520a == setFuture) {
                        if (f2519e.b(abstractResolvableFuture, setFuture, d(setFuture.f2539b))) {
                            break;
                        }
                    } else {
                        continue;
                    }
                } else {
                    executeListener(runnable, listenerClearListeners.f2531b);
                }
                listenerClearListeners = listener;
            }
            return;
        }
    }

    private static CancellationException cancellationExceptionWithCause(@Nullable String str, @Nullable Throwable th) {
        CancellationException cancellationException = new CancellationException(str);
        cancellationException.initCause(th);
        return cancellationException;
    }

    private Listener clearListeners(Listener listener) {
        Listener listener2;
        do {
            listener2 = this.f2521b;
        } while (!f2519e.a(this, listener2, Listener.f2529d));
        Listener listener3 = listener;
        Listener listener4 = listener2;
        while (listener4 != null) {
            Listener listener5 = listener4.f2532c;
            listener4.f2532c = listener3;
            listener3 = listener4;
            listener4 = listener5;
        }
        return listener3;
    }

    static Object d(ListenableFuture listenableFuture) {
        if (listenableFuture instanceof AbstractResolvableFuture) {
            Object obj = ((AbstractResolvableFuture) listenableFuture).f2520a;
            if (!(obj instanceof Cancellation)) {
                return obj;
            }
            Cancellation cancellation = (Cancellation) obj;
            return cancellation.f2525a ? cancellation.f2526b != null ? new Cancellation(false, cancellation.f2526b) : Cancellation.f2524d : obj;
        }
        boolean zIsCancelled = listenableFuture.isCancelled();
        if ((!f2518d) && zIsCancelled) {
            return Cancellation.f2524d;
        }
        try {
            Object objE = e(listenableFuture);
            return objE == null ? NULL : objE;
        } catch (CancellationException e2) {
            if (zIsCancelled) {
                return new Cancellation(false, e2);
            }
            return new Failure(new IllegalArgumentException("get() threw CancellationException, despite reporting isCancelled() == false: " + listenableFuture, e2));
        } catch (ExecutionException e3) {
            return new Failure(e3.getCause());
        } catch (Throwable th) {
            return new Failure(th);
        }
    }

    static Object e(Future future) {
        Object obj;
        boolean z2 = false;
        while (true) {
            try {
                obj = future.get();
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
        return obj;
    }

    private static void executeListener(Runnable runnable, Executor executor) {
        try {
            executor.execute(runnable);
        } catch (RuntimeException e2) {
            log.log(Level.SEVERE, "RuntimeException while executing runnable " + runnable + " with executor " + executor, (Throwable) e2);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private V getDoneValue(Object obj) throws ExecutionException {
        if (obj instanceof Cancellation) {
            throw cancellationExceptionWithCause("Task was cancelled.", ((Cancellation) obj).f2526b);
        }
        if (obj instanceof Failure) {
            throw new ExecutionException(((Failure) obj).f2528a);
        }
        if (obj == NULL) {
            return null;
        }
        return obj;
    }

    private void releaseWaiters() {
        Waiter waiter;
        do {
            waiter = this.f2522c;
        } while (!f2519e.c(this, waiter, Waiter.f2540c));
        while (waiter != null) {
            waiter.b();
            waiter = waiter.f2542b;
        }
    }

    private void removeWaiter(Waiter waiter) {
        waiter.f2541a = null;
        while (true) {
            Waiter waiter2 = this.f2522c;
            if (waiter2 == Waiter.f2540c) {
                return;
            }
            Waiter waiter3 = null;
            while (waiter2 != null) {
                Waiter waiter4 = waiter2.f2542b;
                if (waiter2.f2541a != null) {
                    waiter3 = waiter2;
                } else if (waiter3 != null) {
                    waiter3.f2542b = waiter4;
                    if (waiter3.f2541a == null) {
                        break;
                    }
                } else if (!f2519e.c(this, waiter2, waiter4)) {
                    break;
                }
                waiter2 = waiter4;
            }
            return;
        }
    }

    private String userObjectToString(Object obj) {
        return obj == this ? "this future" : String.valueOf(obj);
    }

    protected void a() {
    }

    @Override // com.google.common.util.concurrent.ListenableFuture
    public final void addListener(Runnable runnable, Executor executor) {
        b(runnable);
        b(executor);
        Listener listener = this.f2521b;
        if (listener != Listener.f2529d) {
            Listener listener2 = new Listener(runnable, executor);
            do {
                listener2.f2532c = listener;
                if (f2519e.a(this, listener, listener2)) {
                    return;
                } else {
                    listener = this.f2521b;
                }
            } while (listener != Listener.f2529d);
        }
        executeListener(runnable, executor);
    }

    @Override // java.util.concurrent.Future
    public final boolean cancel(boolean z2) {
        Object obj = this.f2520a;
        if (!(obj == null) && !(obj instanceof SetFuture)) {
            return false;
        }
        Cancellation cancellation = f2518d ? new Cancellation(z2, new CancellationException("Future.cancel() was called.")) : z2 ? Cancellation.f2523c : Cancellation.f2524d;
        AbstractResolvableFuture<V> abstractResolvableFuture = this;
        boolean z3 = false;
        while (true) {
            if (f2519e.b(abstractResolvableFuture, obj, cancellation)) {
                if (z2) {
                    abstractResolvableFuture.f();
                }
                c(abstractResolvableFuture);
                if (!(obj instanceof SetFuture)) {
                    return true;
                }
                ListenableFuture listenableFuture = ((SetFuture) obj).f2539b;
                if (!(listenableFuture instanceof AbstractResolvableFuture)) {
                    listenableFuture.cancel(z2);
                    return true;
                }
                abstractResolvableFuture = (AbstractResolvableFuture) listenableFuture;
                obj = abstractResolvableFuture.f2520a;
                if (!(obj == null) && !(obj instanceof SetFuture)) {
                    return true;
                }
                z3 = true;
            } else {
                obj = abstractResolvableFuture.f2520a;
                if (!(obj instanceof SetFuture)) {
                    return z3;
                }
            }
        }
    }

    protected void f() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected String g() {
        Object obj = this.f2520a;
        if (obj instanceof SetFuture) {
            return "setFuture=[" + userObjectToString(((SetFuture) obj).f2539b) + "]";
        }
        if (!(this instanceof ScheduledFuture)) {
            return null;
        }
        return "remaining delay=[" + ((ScheduledFuture) this).getDelay(TimeUnit.MILLISECONDS) + " ms]";
    }

    @Override // java.util.concurrent.Future
    public final V get(long j2, TimeUnit timeUnit) throws ExecutionException, InterruptedException, TimeoutException {
        long nanos = timeUnit.toNanos(j2);
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        Object obj = this.f2520a;
        if ((obj != null) && (!(obj instanceof SetFuture))) {
            return getDoneValue(obj);
        }
        long jNanoTime = nanos > 0 ? System.nanoTime() + nanos : 0L;
        if (nanos >= 1000) {
            Waiter waiter = this.f2522c;
            if (waiter != Waiter.f2540c) {
                Waiter waiter2 = new Waiter();
                do {
                    waiter2.a(waiter);
                    if (f2519e.c(this, waiter, waiter2)) {
                        do {
                            LockSupport.parkNanos(this, nanos);
                            if (Thread.interrupted()) {
                                removeWaiter(waiter2);
                                throw new InterruptedException();
                            }
                            Object obj2 = this.f2520a;
                            if ((obj2 != null) && (!(obj2 instanceof SetFuture))) {
                                return getDoneValue(obj2);
                            }
                            nanos = jNanoTime - System.nanoTime();
                        } while (nanos >= 1000);
                        removeWaiter(waiter2);
                    } else {
                        waiter = this.f2522c;
                    }
                } while (waiter != Waiter.f2540c);
            }
            return getDoneValue(this.f2520a);
        }
        while (nanos > 0) {
            Object obj3 = this.f2520a;
            if ((obj3 != null) && (!(obj3 instanceof SetFuture))) {
                return getDoneValue(obj3);
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

    protected final boolean h() {
        Object obj = this.f2520a;
        return (obj instanceof Cancellation) && ((Cancellation) obj).f2525a;
    }

    @Override // java.util.concurrent.Future
    public final boolean isCancelled() {
        return this.f2520a instanceof Cancellation;
    }

    @Override // java.util.concurrent.Future
    public final boolean isDone() {
        return (!(r0 instanceof SetFuture)) & (this.f2520a != null);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean set(Object obj) {
        if (obj == null) {
            obj = NULL;
        }
        if (!f2519e.b(this, null, obj)) {
            return false;
        }
        c(this);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean setException(Throwable th) {
        if (!f2519e.b(this, null, new Failure((Throwable) b(th)))) {
            return false;
        }
        c(this);
        return true;
    }

    protected boolean setFuture(ListenableFuture listenableFuture) {
        Failure failure;
        b(listenableFuture);
        Object obj = this.f2520a;
        if (obj == null) {
            if (listenableFuture.isDone()) {
                if (!f2519e.b(this, null, d(listenableFuture))) {
                    return false;
                }
                c(this);
                return true;
            }
            SetFuture setFuture = new SetFuture(this, listenableFuture);
            if (f2519e.b(this, null, setFuture)) {
                try {
                    listenableFuture.addListener(setFuture, DirectExecutor.INSTANCE);
                } catch (Throwable th) {
                    try {
                        failure = new Failure(th);
                    } catch (Throwable unused) {
                        failure = Failure.f2527b;
                    }
                    f2519e.b(this, setFuture, failure);
                }
                return true;
            }
            obj = this.f2520a;
        }
        if (obj instanceof Cancellation) {
            listenableFuture.cancel(((Cancellation) obj).f2525a);
        }
        return false;
    }

    public String toString() {
        String strG;
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("[status=");
        if (isCancelled()) {
            sb.append("CANCELLED");
        } else if (isDone()) {
            addDoneString(sb);
        } else {
            try {
                strG = g();
            } catch (RuntimeException e2) {
                strG = "Exception thrown from implementation: " + e2.getClass();
            }
            if (strG != null && !strG.isEmpty()) {
                sb.append("PENDING, info=[");
                sb.append(strG);
                sb.append("]");
            } else if (isDone()) {
                addDoneString(sb);
            } else {
                sb.append("PENDING");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    @Override // java.util.concurrent.Future
    public final V get() throws ExecutionException, InterruptedException {
        Object obj;
        if (!Thread.interrupted()) {
            Object obj2 = this.f2520a;
            if ((obj2 != null) & (!(obj2 instanceof SetFuture))) {
                return getDoneValue(obj2);
            }
            Waiter waiter = this.f2522c;
            if (waiter != Waiter.f2540c) {
                Waiter waiter2 = new Waiter();
                do {
                    waiter2.a(waiter);
                    if (f2519e.c(this, waiter, waiter2)) {
                        do {
                            LockSupport.park(this);
                            if (!Thread.interrupted()) {
                                obj = this.f2520a;
                            } else {
                                removeWaiter(waiter2);
                                throw new InterruptedException();
                            }
                        } while (!((obj != null) & (!(obj instanceof SetFuture))));
                        return getDoneValue(obj);
                    }
                    waiter = this.f2522c;
                } while (waiter != Waiter.f2540c);
            }
            return getDoneValue(this.f2520a);
        }
        throw new InterruptedException();
    }
}
