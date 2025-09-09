package com.bumptech.glide.load.engine;

import android.os.Process;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.engine.EngineResource;
import com.bumptech.glide.util.Preconditions;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/* loaded from: classes3.dex */
final class ActiveResources {

    /* renamed from: a, reason: collision with root package name */
    final Map f12307a;

    @Nullable
    private volatile DequeuedResourceCallback cb;
    private final boolean isActiveResourceRetentionAllowed;
    private volatile boolean isShutdown;
    private EngineResource.ResourceListener listener;
    private final Executor monitorClearedResourcesExecutor;
    private final ReferenceQueue<EngineResource<?>> resourceReferenceQueue;

    @VisibleForTesting
    interface DequeuedResourceCallback {
        void onResourceDequeued();
    }

    @VisibleForTesting
    static final class ResourceWeakReference extends WeakReference<EngineResource<?>> {

        /* renamed from: a, reason: collision with root package name */
        final Key f12311a;

        /* renamed from: b, reason: collision with root package name */
        final boolean f12312b;

        /* renamed from: c, reason: collision with root package name */
        Resource f12313c;

        ResourceWeakReference(Key key, EngineResource engineResource, ReferenceQueue referenceQueue, boolean z2) {
            super(engineResource, referenceQueue);
            this.f12311a = (Key) Preconditions.checkNotNull(key);
            this.f12313c = (engineResource.c() && z2) ? (Resource) Preconditions.checkNotNull(engineResource.b()) : null;
            this.f12312b = engineResource.c();
        }

        void a() {
            this.f12313c = null;
            clear();
        }
    }

    ActiveResources(boolean z2) {
        this(z2, Executors.newSingleThreadExecutor(new ThreadFactory() { // from class: com.bumptech.glide.load.engine.ActiveResources.1
            @Override // java.util.concurrent.ThreadFactory
            public Thread newThread(@NonNull final Runnable runnable) {
                return new Thread(new Runnable() { // from class: com.bumptech.glide.load.engine.ActiveResources.1.1
                    @Override // java.lang.Runnable
                    public void run() throws SecurityException, IllegalArgumentException {
                        Process.setThreadPriority(10);
                        runnable.run();
                    }
                }, "glide-active-resources");
            }
        }));
    }

    synchronized void a(Key key, EngineResource engineResource) {
        ResourceWeakReference resourceWeakReference = (ResourceWeakReference) this.f12307a.put(key, new ResourceWeakReference(key, engineResource, this.resourceReferenceQueue, this.isActiveResourceRetentionAllowed));
        if (resourceWeakReference != null) {
            resourceWeakReference.a();
        }
    }

    void b() {
        while (!this.isShutdown) {
            try {
                c((ResourceWeakReference) this.resourceReferenceQueue.remove());
                DequeuedResourceCallback dequeuedResourceCallback = this.cb;
                if (dequeuedResourceCallback != null) {
                    dequeuedResourceCallback.onResourceDequeued();
                }
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
            }
        }
    }

    void c(ResourceWeakReference resourceWeakReference) {
        Resource resource;
        synchronized (this) {
            this.f12307a.remove(resourceWeakReference.f12311a);
            if (resourceWeakReference.f12312b && (resource = resourceWeakReference.f12313c) != null) {
                this.listener.onResourceReleased(resourceWeakReference.f12311a, new EngineResource<>(resource, true, false, resourceWeakReference.f12311a, this.listener));
            }
        }
    }

    synchronized void d(Key key) {
        ResourceWeakReference resourceWeakReference = (ResourceWeakReference) this.f12307a.remove(key);
        if (resourceWeakReference != null) {
            resourceWeakReference.a();
        }
    }

    synchronized EngineResource e(Key key) {
        ResourceWeakReference resourceWeakReference = (ResourceWeakReference) this.f12307a.get(key);
        if (resourceWeakReference == null) {
            return null;
        }
        EngineResource<?> engineResource = resourceWeakReference.get();
        if (engineResource == null) {
            c(resourceWeakReference);
        }
        return engineResource;
    }

    void f(EngineResource.ResourceListener resourceListener) {
        synchronized (resourceListener) {
            synchronized (this) {
                this.listener = resourceListener;
            }
        }
    }

    void g() {
        this.isShutdown = true;
        Executor executor = this.monitorClearedResourcesExecutor;
        if (executor instanceof ExecutorService) {
            com.bumptech.glide.util.Executors.shutdownAndAwaitTermination((ExecutorService) executor);
        }
    }

    ActiveResources(boolean z2, Executor executor) {
        this.f12307a = new HashMap();
        this.resourceReferenceQueue = new ReferenceQueue<>();
        this.isActiveResourceRetentionAllowed = z2;
        this.monitorClearedResourcesExecutor = executor;
        executor.execute(new Runnable() { // from class: com.bumptech.glide.load.engine.ActiveResources.2
            @Override // java.lang.Runnable
            public void run() {
                ActiveResources.this.b();
            }
        });
    }
}
