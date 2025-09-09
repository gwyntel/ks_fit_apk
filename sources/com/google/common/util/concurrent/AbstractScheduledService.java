package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.J2ktIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.util.concurrent.Service;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.concurrent.GuardedBy;
import com.kingsmith.miot.KsProperty;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.ReentrantLock;
import javax.annotation.CheckForNull;

@J2ktIncompatible
@ElementTypesAreNonnullByDefault
@GwtIncompatible
/* loaded from: classes3.dex */
public abstract class AbstractScheduledService implements Service {
    private static final LazyLogger logger = new LazyLogger(AbstractScheduledService.class);
    private final AbstractService delegate;

    interface Cancellable {
        void cancel(boolean z2);

        boolean isCancelled();
    }

    public static abstract class CustomScheduler extends Scheduler {

        private final class ReschedulableCallable implements Callable<Void> {

            @CheckForNull
            @GuardedBy(KsProperty.Lock)
            private SupplantableFuture cancellationDelegate;
            private final ScheduledExecutorService executor;
            private final ReentrantLock lock = new ReentrantLock();
            private final AbstractService service;
            private final Runnable wrappedRunnable;

            ReschedulableCallable(AbstractService abstractService, ScheduledExecutorService scheduledExecutorService, Runnable runnable) {
                this.wrappedRunnable = runnable;
                this.executor = scheduledExecutorService;
                this.service = abstractService;
            }

            @GuardedBy(KsProperty.Lock)
            private Cancellable initializeOrUpdateCancellationDelegate(Schedule schedule) {
                SupplantableFuture supplantableFuture = this.cancellationDelegate;
                if (supplantableFuture == null) {
                    SupplantableFuture supplantableFuture2 = new SupplantableFuture(this.lock, submitToExecutor(schedule));
                    this.cancellationDelegate = supplantableFuture2;
                    return supplantableFuture2;
                }
                if (!supplantableFuture.currentFuture.isCancelled()) {
                    this.cancellationDelegate.currentFuture = submitToExecutor(schedule);
                }
                return this.cancellationDelegate;
            }

            private ScheduledFuture<Void> submitToExecutor(Schedule schedule) {
                return this.executor.schedule(this, schedule.delay, schedule.unit);
            }

            @CanIgnoreReturnValue
            public Cancellable reschedule() throws Exception {
                Cancellable futureAsCancellable;
                try {
                    Schedule scheduleA = CustomScheduler.this.a();
                    this.lock.lock();
                    try {
                        futureAsCancellable = initializeOrUpdateCancellationDelegate(scheduleA);
                        this.lock.unlock();
                        th = null;
                    } catch (Throwable th) {
                        th = th;
                        try {
                            futureAsCancellable = new FutureAsCancellable(Futures.immediateCancelledFuture());
                        } finally {
                            this.lock.unlock();
                        }
                    }
                    if (th != null) {
                        this.service.e(th);
                    }
                    return futureAsCancellable;
                } catch (Throwable th2) {
                    Platform.b(th2);
                    this.service.e(th2);
                    return new FutureAsCancellable(Futures.immediateCancelledFuture());
                }
            }

            @Override // java.util.concurrent.Callable
            @CheckForNull
            public Void call() throws Exception {
                this.wrappedRunnable.run();
                reschedule();
                return null;
            }
        }

        protected static final class Schedule {
            private final long delay;
            private final TimeUnit unit;

            public Schedule(long j2, TimeUnit timeUnit) {
                this.delay = j2;
                this.unit = (TimeUnit) Preconditions.checkNotNull(timeUnit);
            }
        }

        private static final class SupplantableFuture implements Cancellable {

            @GuardedBy(KsProperty.Lock)
            private Future<Void> currentFuture;
            private final ReentrantLock lock;

            SupplantableFuture(ReentrantLock reentrantLock, Future future) {
                this.lock = reentrantLock;
                this.currentFuture = future;
            }

            @Override // com.google.common.util.concurrent.AbstractScheduledService.Cancellable
            public void cancel(boolean z2) {
                this.lock.lock();
                try {
                    this.currentFuture.cancel(z2);
                } finally {
                    this.lock.unlock();
                }
            }

            @Override // com.google.common.util.concurrent.AbstractScheduledService.Cancellable
            public boolean isCancelled() {
                this.lock.lock();
                try {
                    return this.currentFuture.isCancelled();
                } finally {
                    this.lock.unlock();
                }
            }
        }

        public CustomScheduler() {
            super();
        }

        protected abstract Schedule a();

        @Override // com.google.common.util.concurrent.AbstractScheduledService.Scheduler
        final Cancellable schedule(AbstractService abstractService, ScheduledExecutorService scheduledExecutorService, Runnable runnable) {
            return new ReschedulableCallable(abstractService, scheduledExecutorService, runnable).reschedule();
        }
    }

    private static final class FutureAsCancellable implements Cancellable {
        private final Future<?> delegate;

        FutureAsCancellable(Future future) {
            this.delegate = future;
        }

        @Override // com.google.common.util.concurrent.AbstractScheduledService.Cancellable
        public void cancel(boolean z2) {
            this.delegate.cancel(z2);
        }

        @Override // com.google.common.util.concurrent.AbstractScheduledService.Cancellable
        public boolean isCancelled() {
            return this.delegate.isCancelled();
        }
    }

    public static abstract class Scheduler {
        private Scheduler() {
        }

        public static Scheduler newFixedDelaySchedule(final long j2, final long j3, final TimeUnit timeUnit) {
            Preconditions.checkNotNull(timeUnit);
            Preconditions.checkArgument(j3 > 0, "delay must be > 0, found %s", j3);
            return new Scheduler() { // from class: com.google.common.util.concurrent.AbstractScheduledService.Scheduler.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super();
                }

                @Override // com.google.common.util.concurrent.AbstractScheduledService.Scheduler
                public Cancellable schedule(AbstractService abstractService, ScheduledExecutorService scheduledExecutorService, Runnable runnable) {
                    return new FutureAsCancellable(scheduledExecutorService.scheduleWithFixedDelay(runnable, j2, j3, timeUnit));
                }
            };
        }

        public static Scheduler newFixedRateSchedule(final long j2, final long j3, final TimeUnit timeUnit) {
            Preconditions.checkNotNull(timeUnit);
            Preconditions.checkArgument(j3 > 0, "period must be > 0, found %s", j3);
            return new Scheduler() { // from class: com.google.common.util.concurrent.AbstractScheduledService.Scheduler.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super();
                }

                @Override // com.google.common.util.concurrent.AbstractScheduledService.Scheduler
                public Cancellable schedule(AbstractService abstractService, ScheduledExecutorService scheduledExecutorService, Runnable runnable) {
                    return new FutureAsCancellable(scheduledExecutorService.scheduleAtFixedRate(runnable, j2, j3, timeUnit));
                }
            };
        }

        abstract Cancellable schedule(AbstractService abstractService, ScheduledExecutorService scheduledExecutorService, Runnable runnable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class ServiceDelegate extends AbstractService {

        @CheckForNull
        private volatile ScheduledExecutorService executorService;

        @CheckForNull
        private volatile Cancellable runningTask;
        private final ReentrantLock lock = new ReentrantLock();
        private final Runnable task = new Task();

        class Task implements Runnable {
            Task() {
            }

            @Override // java.lang.Runnable
            public void run() {
                Cancellable cancellable;
                ServiceDelegate.this.lock.lock();
                try {
                    cancellable = ServiceDelegate.this.runningTask;
                    Objects.requireNonNull(cancellable);
                } finally {
                    try {
                    } finally {
                    }
                }
                if (cancellable.isCancelled()) {
                    return;
                }
                AbstractScheduledService.this.d();
            }
        }

        private ServiceDelegate() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ String lambda$doStart$0() {
            return AbstractScheduledService.this.f() + " " + state();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$doStart$1() {
            this.lock.lock();
            try {
                AbstractScheduledService.this.h();
                Objects.requireNonNull(this.executorService);
                this.runningTask = AbstractScheduledService.this.e().schedule(AbstractScheduledService.this.delegate, this.executorService, this.task);
                f();
            } finally {
                try {
                } finally {
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$doStop$2() throws Exception {
            try {
                this.lock.lock();
                try {
                    if (state() != Service.State.STOPPING) {
                        return;
                    }
                    AbstractScheduledService.this.g();
                    this.lock.unlock();
                    g();
                } finally {
                    this.lock.unlock();
                }
            } catch (Throwable th) {
                Platform.b(th);
                e(th);
            }
        }

        @Override // com.google.common.util.concurrent.AbstractService
        protected final void c() {
            this.executorService = MoreExecutors.e(AbstractScheduledService.this.c(), new Supplier() { // from class: com.google.common.util.concurrent.h
                @Override // com.google.common.base.Supplier
                public final Object get() {
                    return this.f14958a.lambda$doStart$0();
                }
            });
            this.executorService.execute(new Runnable() { // from class: com.google.common.util.concurrent.i
                @Override // java.lang.Runnable
                public final void run() {
                    this.f14959a.lambda$doStart$1();
                }
            });
        }

        @Override // com.google.common.util.concurrent.AbstractService
        protected final void d() {
            Objects.requireNonNull(this.runningTask);
            Objects.requireNonNull(this.executorService);
            this.runningTask.cancel(false);
            this.executorService.execute(new Runnable() { // from class: com.google.common.util.concurrent.j
                @Override // java.lang.Runnable
                public final void run() throws Exception {
                    this.f14960a.lambda$doStop$2();
                }
            });
        }

        @Override // com.google.common.util.concurrent.AbstractService
        public String toString() {
            return AbstractScheduledService.this.toString();
        }
    }

    @Override // com.google.common.util.concurrent.Service
    public final void addListener(Service.Listener listener, Executor executor) {
        this.delegate.addListener(listener, executor);
    }

    @Override // com.google.common.util.concurrent.Service
    public final void awaitRunning() {
        this.delegate.awaitRunning();
    }

    @Override // com.google.common.util.concurrent.Service
    public final void awaitTerminated() {
        this.delegate.awaitTerminated();
    }

    protected ScheduledExecutorService c() {
        final ScheduledExecutorService scheduledExecutorServiceNewSingleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() { // from class: com.google.common.util.concurrent.AbstractScheduledService.1ThreadFactoryImpl
            @Override // java.util.concurrent.ThreadFactory
            public Thread newThread(Runnable runnable) {
                return MoreExecutors.b(AbstractScheduledService.this.f(), runnable);
            }
        });
        addListener(new Service.Listener(this) { // from class: com.google.common.util.concurrent.AbstractScheduledService.1

            /* renamed from: b, reason: collision with root package name */
            final /* synthetic */ AbstractScheduledService f14784b;

            {
                this.f14784b = this;
            }

            @Override // com.google.common.util.concurrent.Service.Listener
            public void failed(Service.State state, Throwable th) {
                scheduledExecutorServiceNewSingleThreadScheduledExecutor.shutdown();
            }

            @Override // com.google.common.util.concurrent.Service.Listener
            public void terminated(Service.State state) {
                scheduledExecutorServiceNewSingleThreadScheduledExecutor.shutdown();
            }
        }, MoreExecutors.directExecutor());
        return scheduledExecutorServiceNewSingleThreadScheduledExecutor;
    }

    protected abstract void d();

    protected abstract Scheduler e();

    protected String f() {
        return getClass().getSimpleName();
    }

    @Override // com.google.common.util.concurrent.Service
    public final Throwable failureCause() {
        return this.delegate.failureCause();
    }

    protected void g() {
    }

    protected void h() {
    }

    @Override // com.google.common.util.concurrent.Service
    public final boolean isRunning() {
        return this.delegate.isRunning();
    }

    @Override // com.google.common.util.concurrent.Service
    @CanIgnoreReturnValue
    public final Service startAsync() throws Exception {
        this.delegate.startAsync();
        return this;
    }

    @Override // com.google.common.util.concurrent.Service
    public final Service.State state() {
        return this.delegate.state();
    }

    @Override // com.google.common.util.concurrent.Service
    @CanIgnoreReturnValue
    public final Service stopAsync() throws Exception {
        this.delegate.stopAsync();
        return this;
    }

    public String toString() {
        return f() + " [" + state() + "]";
    }

    @Override // com.google.common.util.concurrent.Service
    public final void awaitRunning(long j2, TimeUnit timeUnit) throws TimeoutException {
        this.delegate.awaitRunning(j2, timeUnit);
    }

    @Override // com.google.common.util.concurrent.Service
    public final void awaitTerminated(long j2, TimeUnit timeUnit) throws TimeoutException {
        this.delegate.awaitTerminated(j2, timeUnit);
    }
}
