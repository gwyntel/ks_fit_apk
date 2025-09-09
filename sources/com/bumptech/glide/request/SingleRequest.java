package com.bumptech.glide.request;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import androidx.annotation.DrawableRes;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.GlideContext;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.Engine;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.resource.drawable.DrawableDecoderCompat;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.TransitionFactory;
import com.bumptech.glide.util.LogTime;
import com.bumptech.glide.util.Util;
import com.bumptech.glide.util.pool.GlideTrace;
import com.bumptech.glide.util.pool.StateVerifier;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public final class SingleRequest<R> implements Request, SizeReadyCallback, ResourceCallback {
    private static final String GLIDE_TAG = "Glide";
    private final TransitionFactory<? super R> animationFactory;
    private final Executor callbackExecutor;
    private final Context context;
    private int cookie;
    private volatile Engine engine;

    @Nullable
    @GuardedBy("requestLock")
    private Drawable errorDrawable;

    @Nullable
    @GuardedBy("requestLock")
    private Drawable fallbackDrawable;
    private final GlideContext glideContext;

    @GuardedBy("requestLock")
    private int height;

    @GuardedBy("requestLock")
    private boolean isCallingCallbacks;

    @GuardedBy("requestLock")
    private Engine.LoadStatus loadStatus;

    @Nullable
    private final Object model;
    private final int overrideHeight;
    private final int overrideWidth;

    @Nullable
    @GuardedBy("requestLock")
    private Drawable placeholderDrawable;
    private final Priority priority;
    private final RequestCoordinator requestCoordinator;

    @Nullable
    private final List<RequestListener<R>> requestListeners;
    private final Object requestLock;
    private final BaseRequestOptions<?> requestOptions;

    @Nullable
    private RuntimeException requestOrigin;

    @GuardedBy("requestLock")
    private Resource<R> resource;

    @GuardedBy("requestLock")
    private long startTime;
    private final StateVerifier stateVerifier;

    @GuardedBy("requestLock")
    private Status status;

    @Nullable
    private final String tag;
    private final Target<R> target;

    @Nullable
    private final RequestListener<R> targetListener;
    private final Class<R> transcodeClass;

    @GuardedBy("requestLock")
    private int width;
    private static final String TAG = "GlideRequest";
    private static final boolean IS_VERBOSE_LOGGABLE = Log.isLoggable(TAG, 2);

    private enum Status {
        PENDING,
        RUNNING,
        WAITING_FOR_SIZE,
        COMPLETE,
        FAILED,
        CLEARED
    }

    private SingleRequest(Context context, GlideContext glideContext, @NonNull Object obj, @Nullable Object obj2, Class<R> cls, BaseRequestOptions<?> baseRequestOptions, int i2, int i3, Priority priority, Target<R> target, @Nullable RequestListener<R> requestListener, @Nullable List<RequestListener<R>> list, RequestCoordinator requestCoordinator, Engine engine, TransitionFactory<? super R> transitionFactory, Executor executor) {
        this.tag = IS_VERBOSE_LOGGABLE ? String.valueOf(super.hashCode()) : null;
        this.stateVerifier = StateVerifier.newInstance();
        this.requestLock = obj;
        this.context = context;
        this.glideContext = glideContext;
        this.model = obj2;
        this.transcodeClass = cls;
        this.requestOptions = baseRequestOptions;
        this.overrideWidth = i2;
        this.overrideHeight = i3;
        this.priority = priority;
        this.target = target;
        this.targetListener = requestListener;
        this.requestListeners = list;
        this.requestCoordinator = requestCoordinator;
        this.engine = engine;
        this.animationFactory = transitionFactory;
        this.callbackExecutor = executor;
        this.status = Status.PENDING;
        if (this.requestOrigin == null && glideContext.getExperiments().isEnabled(GlideBuilder.LogRequestOrigins.class)) {
            this.requestOrigin = new RuntimeException("Glide request origin trace");
        }
    }

    @GuardedBy("requestLock")
    private void assertNotCallingCallbacks() {
        if (this.isCallingCallbacks) {
            throw new IllegalStateException("You can't start or clear loads in RequestListener or Target callbacks. If you're trying to start a fallback request when a load fails, use RequestBuilder#error(RequestBuilder). Otherwise consider posting your into() or clear() calls to the main thread using a Handler instead.");
        }
    }

    @GuardedBy("requestLock")
    private boolean canNotifyCleared() {
        RequestCoordinator requestCoordinator = this.requestCoordinator;
        return requestCoordinator == null || requestCoordinator.canNotifyCleared(this);
    }

    @GuardedBy("requestLock")
    private boolean canNotifyStatusChanged() {
        RequestCoordinator requestCoordinator = this.requestCoordinator;
        return requestCoordinator == null || requestCoordinator.canNotifyStatusChanged(this);
    }

    @GuardedBy("requestLock")
    private boolean canSetResource() {
        RequestCoordinator requestCoordinator = this.requestCoordinator;
        return requestCoordinator == null || requestCoordinator.canSetImage(this);
    }

    @GuardedBy("requestLock")
    private void cancel() {
        assertNotCallingCallbacks();
        this.stateVerifier.throwIfRecycled();
        this.target.removeCallback(this);
        Engine.LoadStatus loadStatus = this.loadStatus;
        if (loadStatus != null) {
            loadStatus.cancel();
            this.loadStatus = null;
        }
    }

    private void experimentalNotifyRequestStarted(Object obj) {
        List<RequestListener<R>> list = this.requestListeners;
        if (list == null) {
            return;
        }
        for (RequestListener<R> requestListener : list) {
            if (requestListener instanceof ExperimentalRequestListener) {
                ((ExperimentalRequestListener) requestListener).onRequestStarted(obj);
            }
        }
    }

    @GuardedBy("requestLock")
    private Drawable getErrorDrawable() {
        if (this.errorDrawable == null) {
            Drawable errorPlaceholder = this.requestOptions.getErrorPlaceholder();
            this.errorDrawable = errorPlaceholder;
            if (errorPlaceholder == null && this.requestOptions.getErrorId() > 0) {
                this.errorDrawable = loadDrawable(this.requestOptions.getErrorId());
            }
        }
        return this.errorDrawable;
    }

    @GuardedBy("requestLock")
    private Drawable getFallbackDrawable() {
        if (this.fallbackDrawable == null) {
            Drawable fallbackDrawable = this.requestOptions.getFallbackDrawable();
            this.fallbackDrawable = fallbackDrawable;
            if (fallbackDrawable == null && this.requestOptions.getFallbackId() > 0) {
                this.fallbackDrawable = loadDrawable(this.requestOptions.getFallbackId());
            }
        }
        return this.fallbackDrawable;
    }

    @GuardedBy("requestLock")
    private Drawable getPlaceholderDrawable() {
        if (this.placeholderDrawable == null) {
            Drawable placeholderDrawable = this.requestOptions.getPlaceholderDrawable();
            this.placeholderDrawable = placeholderDrawable;
            if (placeholderDrawable == null && this.requestOptions.getPlaceholderId() > 0) {
                this.placeholderDrawable = loadDrawable(this.requestOptions.getPlaceholderId());
            }
        }
        return this.placeholderDrawable;
    }

    @GuardedBy("requestLock")
    private boolean isFirstReadyResource() {
        RequestCoordinator requestCoordinator = this.requestCoordinator;
        return requestCoordinator == null || !requestCoordinator.getRoot().isAnyResourceSet();
    }

    @GuardedBy("requestLock")
    private Drawable loadDrawable(@DrawableRes int i2) {
        return DrawableDecoderCompat.getDrawable(this.glideContext, i2, this.requestOptions.getTheme() != null ? this.requestOptions.getTheme() : this.context.getTheme());
    }

    private void logV(String str) {
        Log.v(TAG, str + " this: " + this.tag);
    }

    private static int maybeApplySizeMultiplier(int i2, float f2) {
        return i2 == Integer.MIN_VALUE ? i2 : Math.round(f2 * i2);
    }

    @GuardedBy("requestLock")
    private void notifyLoadFailed() {
        RequestCoordinator requestCoordinator = this.requestCoordinator;
        if (requestCoordinator != null) {
            requestCoordinator.onRequestFailed(this);
        }
    }

    @GuardedBy("requestLock")
    private void notifyLoadSuccess() {
        RequestCoordinator requestCoordinator = this.requestCoordinator;
        if (requestCoordinator != null) {
            requestCoordinator.onRequestSuccess(this);
        }
    }

    public static <R> SingleRequest<R> obtain(Context context, GlideContext glideContext, Object obj, Object obj2, Class<R> cls, BaseRequestOptions<?> baseRequestOptions, int i2, int i3, Priority priority, Target<R> target, RequestListener<R> requestListener, @Nullable List<RequestListener<R>> list, RequestCoordinator requestCoordinator, Engine engine, TransitionFactory<? super R> transitionFactory, Executor executor) {
        return new SingleRequest<>(context, glideContext, obj, obj2, cls, baseRequestOptions, i2, i3, priority, target, requestListener, list, requestCoordinator, engine, transitionFactory, executor);
    }

    @GuardedBy("requestLock")
    private void setErrorPlaceholder() {
        if (canNotifyStatusChanged()) {
            Drawable fallbackDrawable = this.model == null ? getFallbackDrawable() : null;
            if (fallbackDrawable == null) {
                fallbackDrawable = getErrorDrawable();
            }
            if (fallbackDrawable == null) {
                fallbackDrawable = getPlaceholderDrawable();
            }
            this.target.onLoadFailed(fallbackDrawable);
        }
    }

    @Override // com.bumptech.glide.request.Request
    public void begin() {
        synchronized (this.requestLock) {
            try {
                assertNotCallingCallbacks();
                this.stateVerifier.throwIfRecycled();
                this.startTime = LogTime.getLogTime();
                Object obj = this.model;
                if (obj == null) {
                    if (Util.isValidDimensions(this.overrideWidth, this.overrideHeight)) {
                        this.width = this.overrideWidth;
                        this.height = this.overrideHeight;
                    }
                    onLoadFailed(new GlideException("Received null model"), getFallbackDrawable() == null ? 5 : 3);
                    return;
                }
                Status status = this.status;
                Status status2 = Status.RUNNING;
                if (status == status2) {
                    throw new IllegalArgumentException("Cannot restart a running request");
                }
                if (status == Status.COMPLETE) {
                    onResourceReady(this.resource, DataSource.MEMORY_CACHE, false);
                    return;
                }
                experimentalNotifyRequestStarted(obj);
                this.cookie = GlideTrace.beginSectionAsync(TAG);
                Status status3 = Status.WAITING_FOR_SIZE;
                this.status = status3;
                if (Util.isValidDimensions(this.overrideWidth, this.overrideHeight)) {
                    onSizeReady(this.overrideWidth, this.overrideHeight);
                } else {
                    this.target.getSize(this);
                }
                Status status4 = this.status;
                if ((status4 == status2 || status4 == status3) && canNotifyStatusChanged()) {
                    this.target.onLoadStarted(getPlaceholderDrawable());
                }
                if (IS_VERBOSE_LOGGABLE) {
                    logV("finished run method in " + LogTime.getElapsedMillis(this.startTime));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.bumptech.glide.request.Request
    public void clear() {
        synchronized (this.requestLock) {
            try {
                assertNotCallingCallbacks();
                this.stateVerifier.throwIfRecycled();
                Status status = this.status;
                Status status2 = Status.CLEARED;
                if (status == status2) {
                    return;
                }
                cancel();
                Resource<R> resource = this.resource;
                if (resource != null) {
                    this.resource = null;
                } else {
                    resource = null;
                }
                if (canNotifyCleared()) {
                    this.target.onLoadCleared(getPlaceholderDrawable());
                }
                GlideTrace.endSectionAsync(TAG, this.cookie);
                this.status = status2;
                if (resource != null) {
                    this.engine.release(resource);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.bumptech.glide.request.ResourceCallback
    public Object getLock() {
        this.stateVerifier.throwIfRecycled();
        return this.requestLock;
    }

    @Override // com.bumptech.glide.request.Request
    public boolean isAnyResourceSet() {
        boolean z2;
        synchronized (this.requestLock) {
            z2 = this.status == Status.COMPLETE;
        }
        return z2;
    }

    @Override // com.bumptech.glide.request.Request
    public boolean isCleared() {
        boolean z2;
        synchronized (this.requestLock) {
            z2 = this.status == Status.CLEARED;
        }
        return z2;
    }

    @Override // com.bumptech.glide.request.Request
    public boolean isComplete() {
        boolean z2;
        synchronized (this.requestLock) {
            z2 = this.status == Status.COMPLETE;
        }
        return z2;
    }

    @Override // com.bumptech.glide.request.Request
    public boolean isEquivalentTo(Request request) {
        int i2;
        int i3;
        Object obj;
        Class<R> cls;
        BaseRequestOptions<?> baseRequestOptions;
        Priority priority;
        int size;
        int i4;
        int i5;
        Object obj2;
        Class<R> cls2;
        BaseRequestOptions<?> baseRequestOptions2;
        Priority priority2;
        int size2;
        if (!(request instanceof SingleRequest)) {
            return false;
        }
        synchronized (this.requestLock) {
            try {
                i2 = this.overrideWidth;
                i3 = this.overrideHeight;
                obj = this.model;
                cls = this.transcodeClass;
                baseRequestOptions = this.requestOptions;
                priority = this.priority;
                List<RequestListener<R>> list = this.requestListeners;
                size = list != null ? list.size() : 0;
            } finally {
            }
        }
        SingleRequest singleRequest = (SingleRequest) request;
        synchronized (singleRequest.requestLock) {
            try {
                i4 = singleRequest.overrideWidth;
                i5 = singleRequest.overrideHeight;
                obj2 = singleRequest.model;
                cls2 = singleRequest.transcodeClass;
                baseRequestOptions2 = singleRequest.requestOptions;
                priority2 = singleRequest.priority;
                List<RequestListener<R>> list2 = singleRequest.requestListeners;
                size2 = list2 != null ? list2.size() : 0;
            } finally {
            }
        }
        return i2 == i4 && i3 == i5 && Util.bothModelsNullEquivalentOrEquals(obj, obj2) && cls.equals(cls2) && baseRequestOptions.equals(baseRequestOptions2) && priority == priority2 && size == size2;
    }

    @Override // com.bumptech.glide.request.Request
    public boolean isRunning() {
        boolean z2;
        synchronized (this.requestLock) {
            try {
                Status status = this.status;
                z2 = status == Status.RUNNING || status == Status.WAITING_FOR_SIZE;
            } finally {
            }
        }
        return z2;
    }

    @Override // com.bumptech.glide.request.ResourceCallback
    public void onLoadFailed(GlideException glideException) {
        onLoadFailed(glideException, 5);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.bumptech.glide.request.ResourceCallback
    public void onResourceReady(Resource<?> resource, DataSource dataSource, boolean z2) {
        this.stateVerifier.throwIfRecycled();
        Resource<?> resource2 = null;
        try {
            synchronized (this.requestLock) {
                try {
                    this.loadStatus = null;
                    if (resource == null) {
                        onLoadFailed(new GlideException("Expected to receive a Resource<R> with an object of " + this.transcodeClass + " inside, but instead got null."));
                        return;
                    }
                    Object obj = resource.get();
                    try {
                        if (obj != null && this.transcodeClass.isAssignableFrom(obj.getClass())) {
                            if (canSetResource()) {
                                onResourceReady(resource, obj, dataSource, z2);
                                return;
                            }
                            this.resource = null;
                            this.status = Status.COMPLETE;
                            GlideTrace.endSectionAsync(TAG, this.cookie);
                            this.engine.release(resource);
                            return;
                        }
                        this.resource = null;
                        StringBuilder sb = new StringBuilder();
                        sb.append("Expected to receive an object of ");
                        sb.append(this.transcodeClass);
                        sb.append(" but instead got ");
                        sb.append(obj != null ? obj.getClass() : "");
                        sb.append("{");
                        sb.append(obj);
                        sb.append("} inside Resource{");
                        sb.append(resource);
                        sb.append("}.");
                        sb.append(obj != null ? "" : " To indicate failure return a null Resource object, rather than a Resource object containing null data.");
                        onLoadFailed(new GlideException(sb.toString()));
                        this.engine.release(resource);
                    } catch (Throwable th) {
                        resource2 = resource;
                        th = th;
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            }
        } catch (Throwable th3) {
            if (resource2 != null) {
                this.engine.release(resource2);
            }
            throw th3;
        }
    }

    @Override // com.bumptech.glide.request.target.SizeReadyCallback
    public void onSizeReady(int i2, int i3) throws Throwable {
        Object obj;
        this.stateVerifier.throwIfRecycled();
        Object obj2 = this.requestLock;
        synchronized (obj2) {
            try {
                try {
                    boolean z2 = IS_VERBOSE_LOGGABLE;
                    if (z2) {
                        logV("Got onSizeReady in " + LogTime.getElapsedMillis(this.startTime));
                    }
                    if (this.status == Status.WAITING_FOR_SIZE) {
                        Status status = Status.RUNNING;
                        this.status = status;
                        float sizeMultiplier = this.requestOptions.getSizeMultiplier();
                        this.width = maybeApplySizeMultiplier(i2, sizeMultiplier);
                        this.height = maybeApplySizeMultiplier(i3, sizeMultiplier);
                        if (z2) {
                            logV("finished setup for calling load in " + LogTime.getElapsedMillis(this.startTime));
                        }
                        obj = obj2;
                        try {
                            this.loadStatus = this.engine.load(this.glideContext, this.model, this.requestOptions.getSignature(), this.width, this.height, this.requestOptions.getResourceClass(), this.transcodeClass, this.priority, this.requestOptions.getDiskCacheStrategy(), this.requestOptions.getTransformations(), this.requestOptions.isTransformationRequired(), this.requestOptions.b(), this.requestOptions.getOptions(), this.requestOptions.isMemoryCacheable(), this.requestOptions.getUseUnlimitedSourceGeneratorsPool(), this.requestOptions.getUseAnimationPool(), this.requestOptions.getOnlyRetrieveFromCache(), this, this.callbackExecutor);
                            if (this.status != status) {
                                this.loadStatus = null;
                            }
                            if (z2) {
                                logV("finished onSizeReady in " + LogTime.getElapsedMillis(this.startTime));
                            }
                        } catch (Throwable th) {
                            th = th;
                            throw th;
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    obj = obj2;
                }
            } catch (Throwable th3) {
                th = th3;
            }
        }
    }

    @Override // com.bumptech.glide.request.Request
    public void pause() {
        synchronized (this.requestLock) {
            try {
                if (isRunning()) {
                    clear();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public String toString() {
        Object obj;
        Class<R> cls;
        synchronized (this.requestLock) {
            obj = this.model;
            cls = this.transcodeClass;
        }
        return super.toString() + "[model=" + obj + ", transcodeClass=" + cls + "]";
    }

    private void onLoadFailed(GlideException glideException, int i2) {
        boolean zOnLoadFailed;
        this.stateVerifier.throwIfRecycled();
        synchronized (this.requestLock) {
            try {
                glideException.setOrigin(this.requestOrigin);
                int logLevel = this.glideContext.getLogLevel();
                if (logLevel <= i2) {
                    Log.w(GLIDE_TAG, "Load failed for " + this.model + " with size [" + this.width + "x" + this.height + "]", glideException);
                    if (logLevel <= 4) {
                        glideException.logRootCauses(GLIDE_TAG);
                    }
                }
                this.loadStatus = null;
                this.status = Status.FAILED;
                boolean z2 = true;
                this.isCallingCallbacks = true;
                try {
                    List<RequestListener<R>> list = this.requestListeners;
                    if (list != null) {
                        Iterator<RequestListener<R>> it = list.iterator();
                        zOnLoadFailed = false;
                        while (it.hasNext()) {
                            zOnLoadFailed |= it.next().onLoadFailed(glideException, this.model, this.target, isFirstReadyResource());
                        }
                    } else {
                        zOnLoadFailed = false;
                    }
                    RequestListener<R> requestListener = this.targetListener;
                    if (requestListener == null || !requestListener.onLoadFailed(glideException, this.model, this.target, isFirstReadyResource())) {
                        z2 = false;
                    }
                    if (!(zOnLoadFailed | z2)) {
                        setErrorPlaceholder();
                    }
                    this.isCallingCallbacks = false;
                    notifyLoadFailed();
                    GlideTrace.endSectionAsync(TAG, this.cookie);
                } catch (Throwable th) {
                    this.isCallingCallbacks = false;
                    throw th;
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    @GuardedBy("requestLock")
    private void onResourceReady(Resource<R> resource, R r2, DataSource dataSource, boolean z2) {
        boolean zOnResourceReady;
        boolean zIsFirstReadyResource = isFirstReadyResource();
        this.status = Status.COMPLETE;
        this.resource = resource;
        if (this.glideContext.getLogLevel() <= 3) {
            Log.d(GLIDE_TAG, "Finished loading " + r2.getClass().getSimpleName() + " from " + dataSource + " for " + this.model + " with size [" + this.width + "x" + this.height + "] in " + LogTime.getElapsedMillis(this.startTime) + " ms");
        }
        boolean z3 = true;
        this.isCallingCallbacks = true;
        try {
            List<RequestListener<R>> list = this.requestListeners;
            if (list != null) {
                Iterator<RequestListener<R>> it = list.iterator();
                zOnResourceReady = false;
                while (it.hasNext()) {
                    zOnResourceReady |= it.next().onResourceReady(r2, this.model, this.target, dataSource, zIsFirstReadyResource);
                }
            } else {
                zOnResourceReady = false;
            }
            RequestListener<R> requestListener = this.targetListener;
            if (requestListener == null || !requestListener.onResourceReady(r2, this.model, this.target, dataSource, zIsFirstReadyResource)) {
                z3 = false;
            }
            if (!(z3 | zOnResourceReady)) {
                this.target.onResourceReady(r2, this.animationFactory.build(dataSource, zIsFirstReadyResource));
            }
            this.isCallingCallbacks = false;
            notifyLoadSuccess();
            GlideTrace.endSectionAsync(TAG, this.cookie);
        } catch (Throwable th) {
            this.isCallingCallbacks = false;
            throw th;
        }
    }
}
