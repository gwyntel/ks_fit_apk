package de.greenrobot.event.util;

import android.util.Log;
import de.greenrobot.event.EventBus;
import java.lang.reflect.Constructor;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/* loaded from: classes4.dex */
public class AsyncExecutor {
    private final EventBus eventBus;
    private final Constructor<?> failureEventConstructor;
    private final Executor threadPool;

    public static class Builder {
        private EventBus eventBus;
        private Class<?> failureEventType;
        private Executor threadPool;

        private Builder() {
        }

        public AsyncExecutor build() {
            if (this.eventBus == null) {
                this.eventBus = EventBus.getDefault();
            }
            if (this.threadPool == null) {
                this.threadPool = Executors.newCachedThreadPool();
            }
            if (this.failureEventType == null) {
                this.failureEventType = ThrowableFailureEvent.class;
            }
            return new AsyncExecutor(this.threadPool, this.eventBus, this.failureEventType, null);
        }

        public Builder eventBus(EventBus eventBus) {
            this.eventBus = eventBus;
            return this;
        }

        public Builder failureEventType(Class<?> cls) {
            this.failureEventType = cls;
            return this;
        }

        public Builder threadPool(Executor executor) {
            this.threadPool = executor;
            return this;
        }

        /* synthetic */ Builder(Builder builder) {
            this();
        }
    }

    public interface RunnableEx {
        void run() throws Exception;
    }

    private AsyncExecutor(Executor executor, EventBus eventBus, Class<?> cls) {
        this.threadPool = executor;
        this.eventBus = eventBus;
        try {
            this.failureEventConstructor = cls.getConstructor(Throwable.class);
        } catch (NoSuchMethodException e2) {
            throw new RuntimeException("Failure event class must have a constructor with one parameter of type Throwable", e2);
        }
    }

    public static Builder builder() {
        return new Builder(null);
    }

    public static AsyncExecutor create() {
        return new Builder(null).build();
    }

    public void execute(final RunnableEx runnableEx) {
        this.threadPool.execute(new Runnable() { // from class: de.greenrobot.event.util.AsyncExecutor.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    runnableEx.run();
                } catch (Exception e2) {
                    try {
                        AsyncExecutor.this.eventBus.post(AsyncExecutor.this.failureEventConstructor.newInstance(e2));
                    } catch (Exception e3) {
                        Log.e(EventBus.TAG, "Original exception:", e2);
                        throw new RuntimeException("Could not create failure event", e3);
                    }
                }
            }
        });
    }

    /* synthetic */ AsyncExecutor(Executor executor, EventBus eventBus, Class cls, AsyncExecutor asyncExecutor) {
        this(executor, eventBus, cls);
    }
}
