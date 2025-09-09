package com.bumptech.glide.load.engine;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.core.util.Pools;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.engine.DecodeJob;
import com.bumptech.glide.load.engine.EngineResource;
import com.bumptech.glide.load.engine.executor.GlideExecutor;
import com.bumptech.glide.request.ResourceCallback;
import com.bumptech.glide.util.Executors;
import com.bumptech.glide.util.Preconditions;
import com.bumptech.glide.util.pool.FactoryPools;
import com.bumptech.glide.util.pool.StateVerifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes3.dex */
class EngineJob<R> implements DecodeJob.Callback<R>, FactoryPools.Poolable {
    private static final EngineResourceFactory DEFAULT_FACTORY = new EngineResourceFactory();

    /* renamed from: a, reason: collision with root package name */
    final ResourceCallbacksAndExecutors f12330a;
    private final GlideExecutor animationExecutor;

    /* renamed from: b, reason: collision with root package name */
    DataSource f12331b;

    /* renamed from: c, reason: collision with root package name */
    GlideException f12332c;

    /* renamed from: d, reason: collision with root package name */
    EngineResource f12333d;
    private DecodeJob<R> decodeJob;
    private final GlideExecutor diskCacheExecutor;
    private final EngineJobListener engineJobListener;
    private final EngineResourceFactory engineResourceFactory;
    private boolean hasLoadFailed;
    private boolean hasResource;
    private boolean isCacheable;
    private volatile boolean isCancelled;
    private boolean isLoadedFromAlternateCacheKey;
    private Key key;
    private boolean onlyRetrieveFromCache;
    private final AtomicInteger pendingCallbacks;
    private final Pools.Pool<EngineJob<?>> pool;
    private Resource<?> resource;
    private final EngineResource.ResourceListener resourceListener;
    private final GlideExecutor sourceExecutor;
    private final GlideExecutor sourceUnlimitedExecutor;
    private final StateVerifier stateVerifier;
    private boolean useAnimationPool;
    private boolean useUnlimitedSourceGeneratorPool;

    private class CallLoadFailed implements Runnable {
        private final ResourceCallback cb;

        CallLoadFailed(ResourceCallback resourceCallback) {
            this.cb = resourceCallback;
        }

        @Override // java.lang.Runnable
        public void run() {
            synchronized (this.cb.getLock()) {
                synchronized (EngineJob.this) {
                    try {
                        if (EngineJob.this.f12330a.b(this.cb)) {
                            EngineJob.this.b(this.cb);
                        }
                        EngineJob.this.e();
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        }
    }

    private class CallResourceReady implements Runnable {
        private final ResourceCallback cb;

        CallResourceReady(ResourceCallback resourceCallback) {
            this.cb = resourceCallback;
        }

        @Override // java.lang.Runnable
        public void run() {
            synchronized (this.cb.getLock()) {
                synchronized (EngineJob.this) {
                    try {
                        if (EngineJob.this.f12330a.b(this.cb)) {
                            EngineJob.this.f12333d.a();
                            EngineJob.this.c(this.cb);
                            EngineJob.this.k(this.cb);
                        }
                        EngineJob.this.e();
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        }
    }

    @VisibleForTesting
    static class EngineResourceFactory {
        EngineResourceFactory() {
        }

        public <R> EngineResource<R> build(Resource<R> resource, boolean z2, Key key, EngineResource.ResourceListener resourceListener) {
            return new EngineResource<>(resource, z2, true, key, resourceListener);
        }
    }

    static final class ResourceCallbackAndExecutor {

        /* renamed from: a, reason: collision with root package name */
        final ResourceCallback f12336a;

        /* renamed from: b, reason: collision with root package name */
        final Executor f12337b;

        ResourceCallbackAndExecutor(ResourceCallback resourceCallback, Executor executor) {
            this.f12336a = resourceCallback;
            this.f12337b = executor;
        }

        public boolean equals(Object obj) {
            if (obj instanceof ResourceCallbackAndExecutor) {
                return this.f12336a.equals(((ResourceCallbackAndExecutor) obj).f12336a);
            }
            return false;
        }

        public int hashCode() {
            return this.f12336a.hashCode();
        }
    }

    static final class ResourceCallbacksAndExecutors implements Iterable<ResourceCallbackAndExecutor> {
        private final List<ResourceCallbackAndExecutor> callbacksAndExecutors;

        ResourceCallbacksAndExecutors() {
            this(new ArrayList(2));
        }

        private static ResourceCallbackAndExecutor defaultCallbackAndExecutor(ResourceCallback resourceCallback) {
            return new ResourceCallbackAndExecutor(resourceCallback, Executors.directExecutor());
        }

        void a(ResourceCallback resourceCallback, Executor executor) {
            this.callbacksAndExecutors.add(new ResourceCallbackAndExecutor(resourceCallback, executor));
        }

        boolean b(ResourceCallback resourceCallback) {
            return this.callbacksAndExecutors.contains(defaultCallbackAndExecutor(resourceCallback));
        }

        ResourceCallbacksAndExecutors c() {
            return new ResourceCallbacksAndExecutors(new ArrayList(this.callbacksAndExecutors));
        }

        void clear() {
            this.callbacksAndExecutors.clear();
        }

        void d(ResourceCallback resourceCallback) {
            this.callbacksAndExecutors.remove(defaultCallbackAndExecutor(resourceCallback));
        }

        boolean isEmpty() {
            return this.callbacksAndExecutors.isEmpty();
        }

        @Override // java.lang.Iterable
        @NonNull
        public Iterator<ResourceCallbackAndExecutor> iterator() {
            return this.callbacksAndExecutors.iterator();
        }

        int size() {
            return this.callbacksAndExecutors.size();
        }

        ResourceCallbacksAndExecutors(List list) {
            this.callbacksAndExecutors = list;
        }
    }

    EngineJob(GlideExecutor glideExecutor, GlideExecutor glideExecutor2, GlideExecutor glideExecutor3, GlideExecutor glideExecutor4, EngineJobListener engineJobListener, EngineResource.ResourceListener resourceListener, Pools.Pool pool) {
        this(glideExecutor, glideExecutor2, glideExecutor3, glideExecutor4, engineJobListener, resourceListener, pool, DEFAULT_FACTORY);
    }

    private GlideExecutor getActiveSourceExecutor() {
        return this.useUnlimitedSourceGeneratorPool ? this.sourceUnlimitedExecutor : this.useAnimationPool ? this.animationExecutor : this.sourceExecutor;
    }

    private boolean isDone() {
        return this.hasLoadFailed || this.hasResource || this.isCancelled;
    }

    private synchronized void release() {
        if (this.key == null) {
            throw new IllegalArgumentException();
        }
        this.f12330a.clear();
        this.key = null;
        this.f12333d = null;
        this.resource = null;
        this.hasLoadFailed = false;
        this.isCancelled = false;
        this.hasResource = false;
        this.isLoadedFromAlternateCacheKey = false;
        this.decodeJob.c(false);
        this.decodeJob = null;
        this.f12332c = null;
        this.f12331b = null;
        this.pool.release(this);
    }

    synchronized void a(ResourceCallback resourceCallback, Executor executor) {
        try {
            this.stateVerifier.throwIfRecycled();
            this.f12330a.a(resourceCallback, executor);
            if (this.hasResource) {
                f(1);
                executor.execute(new CallResourceReady(resourceCallback));
            } else if (this.hasLoadFailed) {
                f(1);
                executor.execute(new CallLoadFailed(resourceCallback));
            } else {
                Preconditions.checkArgument(!this.isCancelled, "Cannot add callbacks to a cancelled EngineJob");
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    void b(ResourceCallback resourceCallback) {
        try {
            resourceCallback.onLoadFailed(this.f12332c);
        } catch (Throwable th) {
            throw new CallbackException(th);
        }
    }

    void c(ResourceCallback resourceCallback) {
        try {
            resourceCallback.onResourceReady(this.f12333d, this.f12331b, this.isLoadedFromAlternateCacheKey);
        } catch (Throwable th) {
            throw new CallbackException(th);
        }
    }

    void d() {
        if (isDone()) {
            return;
        }
        this.isCancelled = true;
        this.decodeJob.cancel();
        this.engineJobListener.onEngineJobCancelled(this, this.key);
    }

    void e() {
        EngineResource engineResource;
        synchronized (this) {
            try {
                this.stateVerifier.throwIfRecycled();
                Preconditions.checkArgument(isDone(), "Not yet complete!");
                int iDecrementAndGet = this.pendingCallbacks.decrementAndGet();
                Preconditions.checkArgument(iDecrementAndGet >= 0, "Can't decrement below 0");
                if (iDecrementAndGet == 0) {
                    engineResource = this.f12333d;
                    release();
                } else {
                    engineResource = null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (engineResource != null) {
            engineResource.d();
        }
    }

    synchronized void f(int i2) {
        EngineResource engineResource;
        Preconditions.checkArgument(isDone(), "Not yet complete!");
        if (this.pendingCallbacks.getAndAdd(i2) == 0 && (engineResource = this.f12333d) != null) {
            engineResource.a();
        }
    }

    synchronized EngineJob g(Key key, boolean z2, boolean z3, boolean z4, boolean z5) {
        this.key = key;
        this.isCacheable = z2;
        this.useUnlimitedSourceGeneratorPool = z3;
        this.useAnimationPool = z4;
        this.onlyRetrieveFromCache = z5;
        return this;
    }

    @Override // com.bumptech.glide.util.pool.FactoryPools.Poolable
    @NonNull
    public StateVerifier getVerifier() {
        return this.stateVerifier;
    }

    void h() {
        synchronized (this) {
            try {
                this.stateVerifier.throwIfRecycled();
                if (this.isCancelled) {
                    release();
                    return;
                }
                if (this.f12330a.isEmpty()) {
                    throw new IllegalStateException("Received an exception without any callbacks to notify");
                }
                if (this.hasLoadFailed) {
                    throw new IllegalStateException("Already failed once");
                }
                this.hasLoadFailed = true;
                Key key = this.key;
                ResourceCallbacksAndExecutors resourceCallbacksAndExecutorsC = this.f12330a.c();
                f(resourceCallbacksAndExecutorsC.size() + 1);
                this.engineJobListener.onEngineJobComplete(this, key, null);
                Iterator<ResourceCallbackAndExecutor> it = resourceCallbacksAndExecutorsC.iterator();
                while (it.hasNext()) {
                    ResourceCallbackAndExecutor next = it.next();
                    next.f12337b.execute(new CallLoadFailed(next.f12336a));
                }
                e();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    void i() {
        synchronized (this) {
            try {
                this.stateVerifier.throwIfRecycled();
                if (this.isCancelled) {
                    this.resource.recycle();
                    release();
                    return;
                }
                if (this.f12330a.isEmpty()) {
                    throw new IllegalStateException("Received a resource without any callbacks to notify");
                }
                if (this.hasResource) {
                    throw new IllegalStateException("Already have resource");
                }
                this.f12333d = this.engineResourceFactory.build(this.resource, this.isCacheable, this.key, this.resourceListener);
                this.hasResource = true;
                ResourceCallbacksAndExecutors resourceCallbacksAndExecutorsC = this.f12330a.c();
                f(resourceCallbacksAndExecutorsC.size() + 1);
                this.engineJobListener.onEngineJobComplete(this, this.key, this.f12333d);
                Iterator<ResourceCallbackAndExecutor> it = resourceCallbacksAndExecutorsC.iterator();
                while (it.hasNext()) {
                    ResourceCallbackAndExecutor next = it.next();
                    next.f12337b.execute(new CallResourceReady(next.f12336a));
                }
                e();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    boolean j() {
        return this.onlyRetrieveFromCache;
    }

    synchronized void k(ResourceCallback resourceCallback) {
        try {
            this.stateVerifier.throwIfRecycled();
            this.f12330a.d(resourceCallback);
            if (this.f12330a.isEmpty()) {
                d();
                if (this.hasResource || this.hasLoadFailed) {
                    if (this.pendingCallbacks.get() == 0) {
                        release();
                    }
                }
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    @Override // com.bumptech.glide.load.engine.DecodeJob.Callback
    public void onLoadFailed(GlideException glideException) {
        synchronized (this) {
            this.f12332c = glideException;
        }
        h();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.bumptech.glide.load.engine.DecodeJob.Callback
    public void onResourceReady(Resource<R> resource, DataSource dataSource, boolean z2) {
        synchronized (this) {
            this.resource = resource;
            this.f12331b = dataSource;
            this.isLoadedFromAlternateCacheKey = z2;
        }
        i();
    }

    @Override // com.bumptech.glide.load.engine.DecodeJob.Callback
    public void reschedule(DecodeJob<?> decodeJob) {
        getActiveSourceExecutor().execute(decodeJob);
    }

    public synchronized void start(DecodeJob<R> decodeJob) {
        try {
            this.decodeJob = decodeJob;
            (decodeJob.d() ? this.diskCacheExecutor : getActiveSourceExecutor()).execute(decodeJob);
        } catch (Throwable th) {
            throw th;
        }
    }

    EngineJob(GlideExecutor glideExecutor, GlideExecutor glideExecutor2, GlideExecutor glideExecutor3, GlideExecutor glideExecutor4, EngineJobListener engineJobListener, EngineResource.ResourceListener resourceListener, Pools.Pool pool, EngineResourceFactory engineResourceFactory) {
        this.f12330a = new ResourceCallbacksAndExecutors();
        this.stateVerifier = StateVerifier.newInstance();
        this.pendingCallbacks = new AtomicInteger();
        this.diskCacheExecutor = glideExecutor;
        this.sourceExecutor = glideExecutor2;
        this.sourceUnlimitedExecutor = glideExecutor3;
        this.animationExecutor = glideExecutor4;
        this.engineJobListener = engineJobListener;
        this.resourceListener = resourceListener;
        this.pool = pool;
        this.engineResourceFactory = engineResourceFactory;
    }
}
