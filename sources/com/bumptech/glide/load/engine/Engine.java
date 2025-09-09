package com.bumptech.glide.load.engine;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.core.util.Pools;
import com.bumptech.glide.GlideContext;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DecodeJob;
import com.bumptech.glide.load.engine.EngineResource;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskCacheAdapter;
import com.bumptech.glide.load.engine.cache.MemoryCache;
import com.bumptech.glide.load.engine.executor.GlideExecutor;
import com.bumptech.glide.request.ResourceCallback;
import com.bumptech.glide.util.Executors;
import com.bumptech.glide.util.LogTime;
import com.bumptech.glide.util.Preconditions;
import com.bumptech.glide.util.pool.FactoryPools;
import java.util.Map;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public class Engine implements EngineJobListener, MemoryCache.ResourceRemovedListener, EngineResource.ResourceListener {
    private static final int JOB_POOL_SIZE = 150;
    private static final String TAG = "Engine";
    private static final boolean VERBOSE_IS_LOGGABLE = Log.isLoggable(TAG, 2);
    private final ActiveResources activeResources;
    private final MemoryCache cache;
    private final DecodeJobFactory decodeJobFactory;
    private final LazyDiskCacheProvider diskCacheProvider;
    private final EngineJobFactory engineJobFactory;
    private final Jobs jobs;
    private final EngineKeyFactory keyFactory;
    private final ResourceRecycler resourceRecycler;

    @VisibleForTesting
    static class DecodeJobFactory {

        /* renamed from: a, reason: collision with root package name */
        final DecodeJob.DiskCacheProvider f12318a;

        /* renamed from: b, reason: collision with root package name */
        final Pools.Pool f12319b = FactoryPools.threadSafe(150, new FactoryPools.Factory<DecodeJob<?>>() { // from class: com.bumptech.glide.load.engine.Engine.DecodeJobFactory.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.bumptech.glide.util.pool.FactoryPools.Factory
            public DecodeJob<?> create() {
                DecodeJobFactory decodeJobFactory = DecodeJobFactory.this;
                return new DecodeJob<>(decodeJobFactory.f12318a, decodeJobFactory.f12319b);
            }
        });
        private int creationOrder;

        DecodeJobFactory(DecodeJob.DiskCacheProvider diskCacheProvider) {
            this.f12318a = diskCacheProvider;
        }

        DecodeJob a(GlideContext glideContext, Object obj, EngineKey engineKey, Key key, int i2, int i3, Class cls, Class cls2, Priority priority, DiskCacheStrategy diskCacheStrategy, Map map, boolean z2, boolean z3, boolean z4, Options options, DecodeJob.Callback callback) {
            DecodeJob decodeJob = (DecodeJob) Preconditions.checkNotNull((DecodeJob) this.f12319b.acquire());
            int i4 = this.creationOrder;
            this.creationOrder = i4 + 1;
            return decodeJob.a(glideContext, obj, engineKey, key, i2, i3, cls, cls2, priority, diskCacheStrategy, map, z2, z3, z4, options, callback, i4);
        }
    }

    @VisibleForTesting
    static class EngineJobFactory {

        /* renamed from: a, reason: collision with root package name */
        final GlideExecutor f12321a;

        /* renamed from: b, reason: collision with root package name */
        final GlideExecutor f12322b;

        /* renamed from: c, reason: collision with root package name */
        final GlideExecutor f12323c;

        /* renamed from: d, reason: collision with root package name */
        final GlideExecutor f12324d;

        /* renamed from: e, reason: collision with root package name */
        final EngineJobListener f12325e;

        /* renamed from: f, reason: collision with root package name */
        final EngineResource.ResourceListener f12326f;

        /* renamed from: g, reason: collision with root package name */
        final Pools.Pool f12327g = FactoryPools.threadSafe(150, new FactoryPools.Factory<EngineJob<?>>() { // from class: com.bumptech.glide.load.engine.Engine.EngineJobFactory.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.bumptech.glide.util.pool.FactoryPools.Factory
            public EngineJob<?> create() {
                EngineJobFactory engineJobFactory = EngineJobFactory.this;
                return new EngineJob<>(engineJobFactory.f12321a, engineJobFactory.f12322b, engineJobFactory.f12323c, engineJobFactory.f12324d, engineJobFactory.f12325e, engineJobFactory.f12326f, engineJobFactory.f12327g);
            }
        });

        EngineJobFactory(GlideExecutor glideExecutor, GlideExecutor glideExecutor2, GlideExecutor glideExecutor3, GlideExecutor glideExecutor4, EngineJobListener engineJobListener, EngineResource.ResourceListener resourceListener) {
            this.f12321a = glideExecutor;
            this.f12322b = glideExecutor2;
            this.f12323c = glideExecutor3;
            this.f12324d = glideExecutor4;
            this.f12325e = engineJobListener;
            this.f12326f = resourceListener;
        }

        EngineJob a(Key key, boolean z2, boolean z3, boolean z4, boolean z5) {
            return ((EngineJob) Preconditions.checkNotNull((EngineJob) this.f12327g.acquire())).g(key, z2, z3, z4, z5);
        }

        void b() {
            Executors.shutdownAndAwaitTermination(this.f12321a);
            Executors.shutdownAndAwaitTermination(this.f12322b);
            Executors.shutdownAndAwaitTermination(this.f12323c);
            Executors.shutdownAndAwaitTermination(this.f12324d);
        }
    }

    private static class LazyDiskCacheProvider implements DecodeJob.DiskCacheProvider {
        private volatile DiskCache diskCache;
        private final DiskCache.Factory factory;

        LazyDiskCacheProvider(DiskCache.Factory factory) {
            this.factory = factory;
        }

        synchronized void a() {
            if (this.diskCache == null) {
                return;
            }
            this.diskCache.clear();
        }

        @Override // com.bumptech.glide.load.engine.DecodeJob.DiskCacheProvider
        public DiskCache getDiskCache() {
            if (this.diskCache == null) {
                synchronized (this) {
                    try {
                        if (this.diskCache == null) {
                            this.diskCache = this.factory.build();
                        }
                        if (this.diskCache == null) {
                            this.diskCache = new DiskCacheAdapter();
                        }
                    } finally {
                    }
                }
            }
            return this.diskCache;
        }
    }

    public class LoadStatus {
        private final ResourceCallback cb;
        private final EngineJob<?> engineJob;

        LoadStatus(ResourceCallback resourceCallback, EngineJob engineJob) {
            this.cb = resourceCallback;
            this.engineJob = engineJob;
        }

        public void cancel() {
            synchronized (Engine.this) {
                this.engineJob.k(this.cb);
            }
        }
    }

    public Engine(MemoryCache memoryCache, DiskCache.Factory factory, GlideExecutor glideExecutor, GlideExecutor glideExecutor2, GlideExecutor glideExecutor3, GlideExecutor glideExecutor4, boolean z2) {
        this(memoryCache, factory, glideExecutor, glideExecutor2, glideExecutor3, glideExecutor4, null, null, null, null, null, null, z2);
    }

    private EngineResource<?> getEngineResourceFromCache(Key key) {
        Resource<?> resourceRemove = this.cache.remove(key);
        if (resourceRemove == null) {
            return null;
        }
        return resourceRemove instanceof EngineResource ? (EngineResource) resourceRemove : new EngineResource<>(resourceRemove, true, true, key, this);
    }

    @Nullable
    private EngineResource<?> loadFromActiveResources(Key key) {
        EngineResource<?> engineResourceE = this.activeResources.e(key);
        if (engineResourceE != null) {
            engineResourceE.a();
        }
        return engineResourceE;
    }

    private EngineResource<?> loadFromCache(Key key) {
        EngineResource<?> engineResourceFromCache = getEngineResourceFromCache(key);
        if (engineResourceFromCache != null) {
            engineResourceFromCache.a();
            this.activeResources.a(key, engineResourceFromCache);
        }
        return engineResourceFromCache;
    }

    @Nullable
    private EngineResource<?> loadFromMemory(EngineKey engineKey, boolean z2, long j2) {
        if (!z2) {
            return null;
        }
        EngineResource<?> engineResourceLoadFromActiveResources = loadFromActiveResources(engineKey);
        if (engineResourceLoadFromActiveResources != null) {
            if (VERBOSE_IS_LOGGABLE) {
                logWithTimeAndKey("Loaded resource from active resources", j2, engineKey);
            }
            return engineResourceLoadFromActiveResources;
        }
        EngineResource<?> engineResourceLoadFromCache = loadFromCache(engineKey);
        if (engineResourceLoadFromCache == null) {
            return null;
        }
        if (VERBOSE_IS_LOGGABLE) {
            logWithTimeAndKey("Loaded resource from cache", j2, engineKey);
        }
        return engineResourceLoadFromCache;
    }

    private static void logWithTimeAndKey(String str, long j2, Key key) {
        Log.v(TAG, str + " in " + LogTime.getElapsedMillis(j2) + "ms, key: " + key);
    }

    private <R> LoadStatus waitForExistingOrStartNewJob(GlideContext glideContext, Object obj, Key key, int i2, int i3, Class<?> cls, Class<R> cls2, Priority priority, DiskCacheStrategy diskCacheStrategy, Map<Class<?>, Transformation<?>> map, boolean z2, boolean z3, Options options, boolean z4, boolean z5, boolean z6, boolean z7, ResourceCallback resourceCallback, Executor executor, EngineKey engineKey, long j2) {
        EngineJob engineJobA = this.jobs.a(engineKey, z7);
        if (engineJobA != null) {
            engineJobA.a(resourceCallback, executor);
            if (VERBOSE_IS_LOGGABLE) {
                logWithTimeAndKey("Added to existing load", j2, engineKey);
            }
            return new LoadStatus(resourceCallback, engineJobA);
        }
        EngineJob engineJobA2 = this.engineJobFactory.a(engineKey, z4, z5, z6, z7);
        DecodeJob decodeJobA = this.decodeJobFactory.a(glideContext, obj, engineKey, key, i2, i3, cls, cls2, priority, diskCacheStrategy, map, z2, z3, z7, options, engineJobA2);
        this.jobs.b(engineKey, engineJobA2);
        engineJobA2.a(resourceCallback, executor);
        engineJobA2.start(decodeJobA);
        if (VERBOSE_IS_LOGGABLE) {
            logWithTimeAndKey("Started new load", j2, engineKey);
        }
        return new LoadStatus(resourceCallback, engineJobA2);
    }

    public void clearDiskCache() {
        this.diskCacheProvider.getDiskCache().clear();
    }

    public <R> LoadStatus load(GlideContext glideContext, Object obj, Key key, int i2, int i3, Class<?> cls, Class<R> cls2, Priority priority, DiskCacheStrategy diskCacheStrategy, Map<Class<?>, Transformation<?>> map, boolean z2, boolean z3, Options options, boolean z4, boolean z5, boolean z6, boolean z7, ResourceCallback resourceCallback, Executor executor) {
        long logTime = VERBOSE_IS_LOGGABLE ? LogTime.getLogTime() : 0L;
        EngineKey engineKeyA = this.keyFactory.a(obj, key, i2, i3, map, cls, cls2, options);
        synchronized (this) {
            try {
                EngineResource<?> engineResourceLoadFromMemory = loadFromMemory(engineKeyA, z4, logTime);
                if (engineResourceLoadFromMemory == null) {
                    return waitForExistingOrStartNewJob(glideContext, obj, key, i2, i3, cls, cls2, priority, diskCacheStrategy, map, z2, z3, options, z4, z5, z6, z7, resourceCallback, executor, engineKeyA, logTime);
                }
                resourceCallback.onResourceReady(engineResourceLoadFromMemory, DataSource.MEMORY_CACHE, false);
                return null;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.bumptech.glide.load.engine.EngineJobListener
    public synchronized void onEngineJobCancelled(EngineJob<?> engineJob, Key key) {
        this.jobs.c(key, engineJob);
    }

    @Override // com.bumptech.glide.load.engine.EngineJobListener
    public synchronized void onEngineJobComplete(EngineJob<?> engineJob, Key key, EngineResource<?> engineResource) {
        if (engineResource != null) {
            try {
                if (engineResource.c()) {
                    this.activeResources.a(key, engineResource);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        this.jobs.c(key, engineJob);
    }

    @Override // com.bumptech.glide.load.engine.EngineResource.ResourceListener
    public void onResourceReleased(Key key, EngineResource<?> engineResource) {
        this.activeResources.d(key);
        if (engineResource.c()) {
            this.cache.put(key, engineResource);
        } else {
            this.resourceRecycler.a(engineResource, false);
        }
    }

    @Override // com.bumptech.glide.load.engine.cache.MemoryCache.ResourceRemovedListener
    public void onResourceRemoved(@NonNull Resource<?> resource) {
        this.resourceRecycler.a(resource, true);
    }

    public void release(Resource<?> resource) {
        if (!(resource instanceof EngineResource)) {
            throw new IllegalArgumentException("Cannot release anything but an EngineResource");
        }
        ((EngineResource) resource).d();
    }

    @VisibleForTesting
    public void shutdown() {
        this.engineJobFactory.b();
        this.diskCacheProvider.a();
        this.activeResources.g();
    }

    Engine(MemoryCache memoryCache, DiskCache.Factory factory, GlideExecutor glideExecutor, GlideExecutor glideExecutor2, GlideExecutor glideExecutor3, GlideExecutor glideExecutor4, Jobs jobs, EngineKeyFactory engineKeyFactory, ActiveResources activeResources, EngineJobFactory engineJobFactory, DecodeJobFactory decodeJobFactory, ResourceRecycler resourceRecycler, boolean z2) {
        this.cache = memoryCache;
        LazyDiskCacheProvider lazyDiskCacheProvider = new LazyDiskCacheProvider(factory);
        this.diskCacheProvider = lazyDiskCacheProvider;
        ActiveResources activeResources2 = activeResources == null ? new ActiveResources(z2) : activeResources;
        this.activeResources = activeResources2;
        activeResources2.f(this);
        this.keyFactory = engineKeyFactory == null ? new EngineKeyFactory() : engineKeyFactory;
        this.jobs = jobs == null ? new Jobs() : jobs;
        this.engineJobFactory = engineJobFactory == null ? new EngineJobFactory(glideExecutor, glideExecutor2, glideExecutor3, glideExecutor4, this, this) : engineJobFactory;
        this.decodeJobFactory = decodeJobFactory == null ? new DecodeJobFactory(lazyDiskCacheProvider) : decodeJobFactory;
        this.resourceRecycler = resourceRecycler == null ? new ResourceRecycler() : resourceRecycler;
        memoryCache.setResourceRemovedListener(this);
    }
}
