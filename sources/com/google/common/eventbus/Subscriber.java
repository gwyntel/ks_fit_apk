package com.google.common.eventbus;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.j2objc.annotations.Weak;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import javax.annotation.CheckForNull;

@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
class Subscriber {

    /* renamed from: a, reason: collision with root package name */
    final Object f14486a;

    @Weak
    private EventBus bus;
    private final Executor executor;
    private final Method method;

    @VisibleForTesting
    static final class SynchronizedSubscriber extends Subscriber {
        @Override // com.google.common.eventbus.Subscriber
        void d(Object obj) {
            synchronized (this) {
                super.d(obj);
            }
        }

        private SynchronizedSubscriber(EventBus eventBus, Object obj, Method method) {
            super(eventBus, obj, method);
        }
    }

    static Subscriber b(EventBus eventBus, Object obj, Method method) {
        return isDeclaredThreadSafe(method) ? new Subscriber(eventBus, obj, method) : new SynchronizedSubscriber(eventBus, obj, method);
    }

    private SubscriberExceptionContext context(Object obj) {
        return new SubscriberExceptionContext(this.bus, obj, this.f14486a, this.method);
    }

    private static boolean isDeclaredThreadSafe(Method method) {
        return method.getAnnotation(AllowConcurrentEvents.class) != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dispatchEvent$0(Object obj) throws IllegalAccessException, IllegalArgumentException {
        try {
            d(obj);
        } catch (InvocationTargetException e2) {
            this.bus.b(e2.getCause(), context(obj));
        }
    }

    final void c(final Object obj) {
        this.executor.execute(new Runnable() { // from class: com.google.common.eventbus.a
            @Override // java.lang.Runnable
            public final void run() throws IllegalAccessException, IllegalArgumentException {
                this.f14487a.lambda$dispatchEvent$0(obj);
            }
        });
    }

    void d(Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        try {
            this.method.invoke(this.f14486a, Preconditions.checkNotNull(obj));
        } catch (IllegalAccessException e2) {
            throw new Error("Method became inaccessible: " + obj, e2);
        } catch (IllegalArgumentException e3) {
            throw new Error("Method rejected target/argument: " + obj, e3);
        } catch (InvocationTargetException e4) {
            if (!(e4.getCause() instanceof Error)) {
                throw e4;
            }
            throw ((Error) e4.getCause());
        }
    }

    public final boolean equals(@CheckForNull Object obj) {
        if (!(obj instanceof Subscriber)) {
            return false;
        }
        Subscriber subscriber = (Subscriber) obj;
        return this.f14486a == subscriber.f14486a && this.method.equals(subscriber.method);
    }

    public final int hashCode() {
        return ((this.method.hashCode() + 31) * 31) + System.identityHashCode(this.f14486a);
    }

    private Subscriber(EventBus eventBus, Object obj, Method method) throws SecurityException {
        this.bus = eventBus;
        this.f14486a = Preconditions.checkNotNull(obj);
        this.method = method;
        method.setAccessible(true);
        this.executor = eventBus.a();
    }
}
