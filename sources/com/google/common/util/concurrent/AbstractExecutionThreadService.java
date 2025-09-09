package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.J2ktIncompatible;
import com.google.common.base.Supplier;
import com.google.common.util.concurrent.Service;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;

@J2ktIncompatible
@ElementTypesAreNonnullByDefault
@GwtIncompatible
/* loaded from: classes3.dex */
public abstract class AbstractExecutionThreadService implements Service {
    private static final LazyLogger logger = new LazyLogger(AbstractExecutionThreadService.class);
    private final Service delegate;

    /* renamed from: com.google.common.util.concurrent.AbstractExecutionThreadService$1, reason: invalid class name */
    class AnonymousClass1 extends AbstractService {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ AbstractExecutionThreadService f14752a;

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ String lambda$doStart$0() {
            return this.f14752a.e();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$doStart$1() throws Exception {
            try {
                this.f14752a.g();
                f();
                if (isRunning()) {
                    try {
                        this.f14752a.d();
                    } catch (Throwable th) {
                        Platform.b(th);
                        try {
                            this.f14752a.f();
                        } catch (Exception e2) {
                            Platform.b(e2);
                            AbstractExecutionThreadService.logger.a().log(Level.WARNING, "Error while attempting to shut down the service after failure.", (Throwable) e2);
                        }
                        e(th);
                        return;
                    }
                }
                this.f14752a.f();
                g();
            } catch (Throwable th2) {
                Platform.b(th2);
                e(th2);
            }
        }

        @Override // com.google.common.util.concurrent.AbstractService
        protected final void c() {
            MoreExecutors.d(this.f14752a.c(), new Supplier() { // from class: com.google.common.util.concurrent.b
                @Override // com.google.common.base.Supplier
                public final Object get() {
                    return this.f14948a.lambda$doStart$0();
                }
            }).execute(new Runnable() { // from class: com.google.common.util.concurrent.c
                @Override // java.lang.Runnable
                public final void run() throws Exception {
                    this.f14952a.lambda$doStart$1();
                }
            });
        }

        @Override // com.google.common.util.concurrent.AbstractService
        protected void d() {
            this.f14752a.h();
        }

        @Override // com.google.common.util.concurrent.AbstractService
        public String toString() {
            return this.f14752a.toString();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$executor$0(Runnable runnable) {
        MoreExecutors.b(e(), runnable).start();
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
        return new Executor() { // from class: com.google.common.util.concurrent.a
            @Override // java.util.concurrent.Executor
            public final void execute(Runnable runnable) {
                this.f14947a.lambda$executor$0(runnable);
            }
        };
    }

    protected abstract void d();

    protected String e() {
        return getClass().getSimpleName();
    }

    protected void f() {
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
        return e() + " [" + state() + "]";
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
