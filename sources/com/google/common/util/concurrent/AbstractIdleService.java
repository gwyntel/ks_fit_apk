package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.J2ktIncompatible;
import com.google.common.base.Supplier;
import com.google.common.util.concurrent.Service;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@J2ktIncompatible
@ElementTypesAreNonnullByDefault
@GwtIncompatible
/* loaded from: classes3.dex */
public abstract class AbstractIdleService implements Service {
    private final Service delegate;
    private final Supplier<String> threadNameSupplier;

    /* JADX INFO: Access modifiers changed from: private */
    final class DelegateService extends AbstractService {
        private DelegateService() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$doStart$0() throws Exception {
            try {
                AbstractIdleService.this.f();
                f();
            } catch (Throwable th) {
                Platform.b(th);
                e(th);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$doStop$1() throws Exception {
            try {
                AbstractIdleService.this.e();
                g();
            } catch (Throwable th) {
                Platform.b(th);
                e(th);
            }
        }

        @Override // com.google.common.util.concurrent.AbstractService
        protected final void c() {
            MoreExecutors.d(AbstractIdleService.this.c(), AbstractIdleService.this.threadNameSupplier).execute(new Runnable() { // from class: com.google.common.util.concurrent.g
                @Override // java.lang.Runnable
                public final void run() throws Exception {
                    this.f14957a.lambda$doStart$0();
                }
            });
        }

        @Override // com.google.common.util.concurrent.AbstractService
        protected final void d() {
            MoreExecutors.d(AbstractIdleService.this.c(), AbstractIdleService.this.threadNameSupplier).execute(new Runnable() { // from class: com.google.common.util.concurrent.f
                @Override // java.lang.Runnable
                public final void run() throws Exception {
                    this.f14955a.lambda$doStop$1();
                }
            });
        }

        @Override // com.google.common.util.concurrent.AbstractService
        public String toString() {
            return AbstractIdleService.this.toString();
        }
    }

    private final class ThreadNameSupplier implements Supplier<String> {
        private ThreadNameSupplier() {
        }

        @Override // com.google.common.base.Supplier
        public String get() {
            return AbstractIdleService.this.d() + " " + AbstractIdleService.this.state();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$executor$0(Runnable runnable) {
        MoreExecutors.b(this.threadNameSupplier.get(), runnable).start();
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

    protected Executor c() {
        return new Executor() { // from class: com.google.common.util.concurrent.e
            @Override // java.util.concurrent.Executor
            public final void execute(Runnable runnable) {
                this.f14953a.lambda$executor$0(runnable);
            }
        };
    }

    protected String d() {
        return getClass().getSimpleName();
    }

    protected abstract void e();

    protected abstract void f();

    @Override // com.google.common.util.concurrent.Service
    public final Throwable failureCause() {
        return this.delegate.failureCause();
    }

    @Override // com.google.common.util.concurrent.Service
    public final boolean isRunning() {
        return this.delegate.isRunning();
    }

    @Override // com.google.common.util.concurrent.Service
    @CanIgnoreReturnValue
    public final Service startAsync() {
        this.delegate.startAsync();
        return this;
    }

    @Override // com.google.common.util.concurrent.Service
    public final Service.State state() {
        return this.delegate.state();
    }

    @Override // com.google.common.util.concurrent.Service
    @CanIgnoreReturnValue
    public final Service stopAsync() {
        this.delegate.stopAsync();
        return this;
    }

    public String toString() {
        return d() + " [" + state() + "]";
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
