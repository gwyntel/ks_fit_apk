package com.bumptech.glide.load.engine;

import android.os.Build;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.core.util.Pools;
import com.bumptech.glide.GlideContext;
import com.bumptech.glide.Priority;
import com.bumptech.glide.Registry;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.EncodeStrategy;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.Option;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.ResourceEncoder;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.data.DataRewinder;
import com.bumptech.glide.load.engine.DataFetcherGenerator;
import com.bumptech.glide.load.engine.DecodePath;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.resource.bitmap.Downsampler;
import com.bumptech.glide.util.LogTime;
import com.bumptech.glide.util.pool.FactoryPools;
import com.bumptech.glide.util.pool.GlideTrace;
import com.bumptech.glide.util.pool.StateVerifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
class DecodeJob<R> implements DataFetcherGenerator.FetcherReadyCallback, Runnable, Comparable<DecodeJob<?>>, FactoryPools.Poolable {
    private static final String TAG = "DecodeJob";
    private Callback<R> callback;
    private Key currentAttemptingKey;
    private Object currentData;
    private DataSource currentDataSource;
    private DataFetcher<?> currentFetcher;
    private volatile DataFetcherGenerator currentGenerator;
    private Key currentSourceKey;
    private Thread currentThread;
    private final DiskCacheProvider diskCacheProvider;
    private DiskCacheStrategy diskCacheStrategy;
    private GlideContext glideContext;
    private int height;
    private volatile boolean isCallbackNotified;
    private volatile boolean isCancelled;
    private boolean isLoadingFromAlternateCacheKey;
    private EngineKey loadKey;
    private Object model;
    private boolean onlyRetrieveFromCache;
    private Options options;
    private int order;
    private final Pools.Pool<DecodeJob<?>> pool;
    private Priority priority;
    private RunReason runReason;
    private Key signature;
    private Stage stage;
    private long startFetchTime;
    private int width;
    private final DecodeHelper<R> decodeHelper = new DecodeHelper<>();
    private final List<Throwable> throwables = new ArrayList();
    private final StateVerifier stateVerifier = StateVerifier.newInstance();
    private final DeferredEncodeManager<?> deferredEncodeManager = new DeferredEncodeManager<>();
    private final ReleaseManager releaseManager = new ReleaseManager();

    /* renamed from: com.bumptech.glide.load.engine.DecodeJob$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f12314a;

        /* renamed from: b, reason: collision with root package name */
        static final /* synthetic */ int[] f12315b;

        /* renamed from: c, reason: collision with root package name */
        static final /* synthetic */ int[] f12316c;

        static {
            int[] iArr = new int[EncodeStrategy.values().length];
            f12316c = iArr;
            try {
                iArr[EncodeStrategy.SOURCE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f12316c[EncodeStrategy.TRANSFORMED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            int[] iArr2 = new int[Stage.values().length];
            f12315b = iArr2;
            try {
                iArr2[Stage.RESOURCE_CACHE.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f12315b[Stage.DATA_CACHE.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f12315b[Stage.SOURCE.ordinal()] = 3;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f12315b[Stage.FINISHED.ordinal()] = 4;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f12315b[Stage.INITIALIZE.ordinal()] = 5;
            } catch (NoSuchFieldError unused7) {
            }
            int[] iArr3 = new int[RunReason.values().length];
            f12314a = iArr3;
            try {
                iArr3[RunReason.INITIALIZE.ordinal()] = 1;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                f12314a[RunReason.SWITCH_TO_SOURCE_SERVICE.ordinal()] = 2;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                f12314a[RunReason.DECODE_DATA.ordinal()] = 3;
            } catch (NoSuchFieldError unused10) {
            }
        }
    }

    interface Callback<R> {
        void onLoadFailed(GlideException glideException);

        void onResourceReady(Resource<R> resource, DataSource dataSource, boolean z2);

        void reschedule(DecodeJob<?> decodeJob);
    }

    private final class DecodeCallback<Z> implements DecodePath.DecodeCallback<Z> {
        private final DataSource dataSource;

        DecodeCallback(DataSource dataSource) {
            this.dataSource = dataSource;
        }

        @Override // com.bumptech.glide.load.engine.DecodePath.DecodeCallback
        @NonNull
        public Resource<Z> onResourceDecoded(@NonNull Resource<Z> resource) {
            return DecodeJob.this.b(this.dataSource, resource);
        }
    }

    private static class DeferredEncodeManager<Z> {
        private ResourceEncoder<Z> encoder;
        private Key key;
        private LockedResource<Z> toEncode;

        DeferredEncodeManager() {
        }

        void a() {
            this.key = null;
            this.encoder = null;
            this.toEncode = null;
        }

        void b(DiskCacheProvider diskCacheProvider, Options options) {
            GlideTrace.beginSection("DecodeJob.encode");
            try {
                diskCacheProvider.getDiskCache().put(this.key, new DataCacheWriter(this.encoder, this.toEncode, options));
            } finally {
                this.toEncode.b();
                GlideTrace.endSection();
            }
        }

        boolean c() {
            return this.toEncode != null;
        }

        void d(Key key, ResourceEncoder resourceEncoder, LockedResource lockedResource) {
            this.key = key;
            this.encoder = resourceEncoder;
            this.toEncode = lockedResource;
        }
    }

    interface DiskCacheProvider {
        DiskCache getDiskCache();
    }

    private static class ReleaseManager {
        private boolean isEncodeComplete;
        private boolean isFailed;
        private boolean isReleased;

        ReleaseManager() {
        }

        private boolean isComplete(boolean z2) {
            return (this.isFailed || z2 || this.isEncodeComplete) && this.isReleased;
        }

        synchronized boolean a() {
            this.isEncodeComplete = true;
            return isComplete(false);
        }

        synchronized boolean b() {
            this.isFailed = true;
            return isComplete(false);
        }

        synchronized boolean c(boolean z2) {
            this.isReleased = true;
            return isComplete(z2);
        }

        synchronized void d() {
            this.isEncodeComplete = false;
            this.isReleased = false;
            this.isFailed = false;
        }
    }

    private enum RunReason {
        INITIALIZE,
        SWITCH_TO_SOURCE_SERVICE,
        DECODE_DATA
    }

    private enum Stage {
        INITIALIZE,
        RESOURCE_CACHE,
        DATA_CACHE,
        SOURCE,
        ENCODE,
        FINISHED
    }

    DecodeJob(DiskCacheProvider diskCacheProvider, Pools.Pool pool) {
        this.diskCacheProvider = diskCacheProvider;
        this.pool = pool;
    }

    private <Data> Resource<R> decodeFromData(DataFetcher<?> dataFetcher, Data data, DataSource dataSource) throws GlideException {
        if (data == null) {
            dataFetcher.cleanup();
            return null;
        }
        try {
            long logTime = LogTime.getLogTime();
            Resource<R> resourceDecodeFromFetcher = decodeFromFetcher(data, dataSource);
            if (Log.isLoggable(TAG, 2)) {
                logWithTimeAndKey("Decoded result " + resourceDecodeFromFetcher, logTime);
            }
            return resourceDecodeFromFetcher;
        } finally {
            dataFetcher.cleanup();
        }
    }

    private <Data> Resource<R> decodeFromFetcher(Data data, DataSource dataSource) throws GlideException {
        return runLoadPath(data, dataSource, this.decodeHelper.h(data.getClass()));
    }

    private void decodeFromRetrievedData() {
        Resource<R> resourceDecodeFromData;
        if (Log.isLoggable(TAG, 2)) {
            logWithTimeAndKey("Retrieved data", this.startFetchTime, "data: " + this.currentData + ", cache key: " + this.currentSourceKey + ", fetcher: " + this.currentFetcher);
        }
        try {
            resourceDecodeFromData = decodeFromData(this.currentFetcher, this.currentData, this.currentDataSource);
        } catch (GlideException e2) {
            e2.setLoggingDetails(this.currentAttemptingKey, this.currentDataSource);
            this.throwables.add(e2);
            resourceDecodeFromData = null;
        }
        if (resourceDecodeFromData != null) {
            notifyEncodeAndRelease(resourceDecodeFromData, this.currentDataSource, this.isLoadingFromAlternateCacheKey);
        } else {
            runGenerators();
        }
    }

    private DataFetcherGenerator getNextGenerator() {
        int i2 = AnonymousClass1.f12315b[this.stage.ordinal()];
        if (i2 == 1) {
            return new ResourceCacheGenerator(this.decodeHelper, this);
        }
        if (i2 == 2) {
            return new DataCacheGenerator(this.decodeHelper, this);
        }
        if (i2 == 3) {
            return new SourceGenerator(this.decodeHelper, this);
        }
        if (i2 == 4) {
            return null;
        }
        throw new IllegalStateException("Unrecognized stage: " + this.stage);
    }

    private Stage getNextStage(Stage stage) {
        int i2 = AnonymousClass1.f12315b[stage.ordinal()];
        if (i2 == 1) {
            return this.diskCacheStrategy.decodeCachedData() ? Stage.DATA_CACHE : getNextStage(Stage.DATA_CACHE);
        }
        if (i2 == 2) {
            return this.onlyRetrieveFromCache ? Stage.FINISHED : Stage.SOURCE;
        }
        if (i2 == 3 || i2 == 4) {
            return Stage.FINISHED;
        }
        if (i2 == 5) {
            return this.diskCacheStrategy.decodeCachedResource() ? Stage.RESOURCE_CACHE : getNextStage(Stage.RESOURCE_CACHE);
        }
        throw new IllegalArgumentException("Unrecognized stage: " + stage);
    }

    @NonNull
    private Options getOptionsWithHardwareConfig(DataSource dataSource) {
        Options options = this.options;
        if (Build.VERSION.SDK_INT < 26) {
            return options;
        }
        boolean z2 = dataSource == DataSource.RESOURCE_DISK_CACHE || this.decodeHelper.x();
        Option<Boolean> option = Downsampler.ALLOW_HARDWARE_CONFIG;
        Boolean bool = (Boolean) options.get(option);
        if (bool != null && (!bool.booleanValue() || z2)) {
            return options;
        }
        Options options2 = new Options();
        options2.putAll(this.options);
        options2.set(option, Boolean.valueOf(z2));
        return options2;
    }

    private int getPriority() {
        return this.priority.ordinal();
    }

    private void logWithTimeAndKey(String str, long j2) {
        logWithTimeAndKey(str, j2, null);
    }

    private void notifyComplete(Resource<R> resource, DataSource dataSource, boolean z2) {
        setNotifiedOrThrow();
        this.callback.onResourceReady(resource, dataSource, z2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void notifyEncodeAndRelease(Resource<R> resource, DataSource dataSource, boolean z2) {
        LockedResource lockedResource;
        GlideTrace.beginSection("DecodeJob.notifyEncodeAndRelease");
        try {
            if (resource instanceof Initializable) {
                ((Initializable) resource).initialize();
            }
            if (this.deferredEncodeManager.c()) {
                resource = LockedResource.a(resource);
                lockedResource = resource;
            } else {
                lockedResource = 0;
            }
            notifyComplete(resource, dataSource, z2);
            this.stage = Stage.ENCODE;
            try {
                if (this.deferredEncodeManager.c()) {
                    this.deferredEncodeManager.b(this.diskCacheProvider, this.options);
                }
                onEncodeComplete();
                GlideTrace.endSection();
            } finally {
                if (lockedResource != 0) {
                    lockedResource.b();
                }
            }
        } catch (Throwable th) {
            GlideTrace.endSection();
            throw th;
        }
    }

    private void notifyFailed() {
        setNotifiedOrThrow();
        this.callback.onLoadFailed(new GlideException("Failed to load resource", new ArrayList(this.throwables)));
        onLoadFailed();
    }

    private void onEncodeComplete() {
        if (this.releaseManager.a()) {
            releaseInternal();
        }
    }

    private void onLoadFailed() {
        if (this.releaseManager.b()) {
            releaseInternal();
        }
    }

    private void releaseInternal() {
        this.releaseManager.d();
        this.deferredEncodeManager.a();
        this.decodeHelper.a();
        this.isCallbackNotified = false;
        this.glideContext = null;
        this.signature = null;
        this.options = null;
        this.priority = null;
        this.loadKey = null;
        this.callback = null;
        this.stage = null;
        this.currentGenerator = null;
        this.currentThread = null;
        this.currentSourceKey = null;
        this.currentData = null;
        this.currentDataSource = null;
        this.currentFetcher = null;
        this.startFetchTime = 0L;
        this.isCancelled = false;
        this.model = null;
        this.throwables.clear();
        this.pool.release(this);
    }

    private void runGenerators() {
        this.currentThread = Thread.currentThread();
        this.startFetchTime = LogTime.getLogTime();
        boolean zStartNext = false;
        while (!this.isCancelled && this.currentGenerator != null && !(zStartNext = this.currentGenerator.startNext())) {
            this.stage = getNextStage(this.stage);
            this.currentGenerator = getNextGenerator();
            if (this.stage == Stage.SOURCE) {
                reschedule();
                return;
            }
        }
        if ((this.stage == Stage.FINISHED || this.isCancelled) && !zStartNext) {
            notifyFailed();
        }
    }

    private <Data, ResourceType> Resource<R> runLoadPath(Data data, DataSource dataSource, LoadPath<Data, ResourceType, R> loadPath) throws GlideException {
        Options optionsWithHardwareConfig = getOptionsWithHardwareConfig(dataSource);
        DataRewinder<Data> rewinder = this.glideContext.getRegistry().getRewinder(data);
        try {
            return loadPath.load(rewinder, optionsWithHardwareConfig, this.width, this.height, new DecodeCallback(dataSource));
        } finally {
            rewinder.cleanup();
        }
    }

    private void runWrapped() {
        int i2 = AnonymousClass1.f12314a[this.runReason.ordinal()];
        if (i2 == 1) {
            this.stage = getNextStage(Stage.INITIALIZE);
            this.currentGenerator = getNextGenerator();
            runGenerators();
        } else if (i2 == 2) {
            runGenerators();
        } else {
            if (i2 == 3) {
                decodeFromRetrievedData();
                return;
            }
            throw new IllegalStateException("Unrecognized run reason: " + this.runReason);
        }
    }

    private void setNotifiedOrThrow() {
        Throwable th;
        this.stateVerifier.throwIfRecycled();
        if (!this.isCallbackNotified) {
            this.isCallbackNotified = true;
            return;
        }
        if (this.throwables.isEmpty()) {
            th = null;
        } else {
            List<Throwable> list = this.throwables;
            th = list.get(list.size() - 1);
        }
        throw new IllegalStateException("Already notified", th);
    }

    DecodeJob a(GlideContext glideContext, Object obj, EngineKey engineKey, Key key, int i2, int i3, Class cls, Class cls2, Priority priority, DiskCacheStrategy diskCacheStrategy, Map map, boolean z2, boolean z3, boolean z4, Options options, Callback callback, int i4) {
        this.decodeHelper.v(glideContext, obj, key, i2, i3, diskCacheStrategy, cls, cls2, priority, options, map, z2, z3, this.diskCacheProvider);
        this.glideContext = glideContext;
        this.signature = key;
        this.priority = priority;
        this.loadKey = engineKey;
        this.width = i2;
        this.height = i3;
        this.diskCacheStrategy = diskCacheStrategy;
        this.onlyRetrieveFromCache = z4;
        this.options = options;
        this.callback = callback;
        this.order = i4;
        this.runReason = RunReason.INITIALIZE;
        this.model = obj;
        return this;
    }

    Resource b(DataSource dataSource, Resource resource) {
        Resource resourceTransform;
        Transformation transformation;
        EncodeStrategy encodeStrategy;
        Key dataCacheKey;
        Class<?> cls = resource.get().getClass();
        ResourceEncoder resourceEncoderN = null;
        if (dataSource != DataSource.RESOURCE_DISK_CACHE) {
            Transformation transformationS = this.decodeHelper.s(cls);
            transformation = transformationS;
            resourceTransform = transformationS.transform(this.glideContext, resource, this.width, this.height);
        } else {
            resourceTransform = resource;
            transformation = null;
        }
        if (!resource.equals(resourceTransform)) {
            resource.recycle();
        }
        if (this.decodeHelper.w(resourceTransform)) {
            resourceEncoderN = this.decodeHelper.n(resourceTransform);
            encodeStrategy = resourceEncoderN.getEncodeStrategy(this.options);
        } else {
            encodeStrategy = EncodeStrategy.NONE;
        }
        ResourceEncoder resourceEncoder = resourceEncoderN;
        if (!this.diskCacheStrategy.isResourceCacheable(!this.decodeHelper.y(this.currentSourceKey), dataSource, encodeStrategy)) {
            return resourceTransform;
        }
        if (resourceEncoder == null) {
            throw new Registry.NoResultEncoderAvailableException(resourceTransform.get().getClass());
        }
        int i2 = AnonymousClass1.f12316c[encodeStrategy.ordinal()];
        if (i2 == 1) {
            dataCacheKey = new DataCacheKey(this.currentSourceKey, this.signature);
        } else {
            if (i2 != 2) {
                throw new IllegalArgumentException("Unknown strategy: " + encodeStrategy);
            }
            dataCacheKey = new ResourceCacheKey(this.decodeHelper.b(), this.currentSourceKey, this.signature, this.width, this.height, transformation, cls, this.options);
        }
        LockedResource lockedResourceA = LockedResource.a(resourceTransform);
        this.deferredEncodeManager.d(dataCacheKey, resourceEncoder, lockedResourceA);
        return lockedResourceA;
    }

    void c(boolean z2) {
        if (this.releaseManager.c(z2)) {
            releaseInternal();
        }
    }

    public void cancel() {
        this.isCancelled = true;
        DataFetcherGenerator dataFetcherGenerator = this.currentGenerator;
        if (dataFetcherGenerator != null) {
            dataFetcherGenerator.cancel();
        }
    }

    boolean d() {
        Stage nextStage = getNextStage(Stage.INITIALIZE);
        return nextStage == Stage.RESOURCE_CACHE || nextStage == Stage.DATA_CACHE;
    }

    @Override // com.bumptech.glide.util.pool.FactoryPools.Poolable
    @NonNull
    public StateVerifier getVerifier() {
        return this.stateVerifier;
    }

    @Override // com.bumptech.glide.load.engine.DataFetcherGenerator.FetcherReadyCallback
    public void onDataFetcherFailed(Key key, Exception exc, DataFetcher<?> dataFetcher, DataSource dataSource) {
        dataFetcher.cleanup();
        GlideException glideException = new GlideException("Fetching data failed", exc);
        glideException.setLoggingDetails(key, dataSource, dataFetcher.getDataClass());
        this.throwables.add(glideException);
        if (Thread.currentThread() == this.currentThread) {
            runGenerators();
        } else {
            this.runReason = RunReason.SWITCH_TO_SOURCE_SERVICE;
            this.callback.reschedule(this);
        }
    }

    @Override // com.bumptech.glide.load.engine.DataFetcherGenerator.FetcherReadyCallback
    public void onDataFetcherReady(Key key, Object obj, DataFetcher<?> dataFetcher, DataSource dataSource, Key key2) {
        this.currentSourceKey = key;
        this.currentData = obj;
        this.currentFetcher = dataFetcher;
        this.currentDataSource = dataSource;
        this.currentAttemptingKey = key2;
        this.isLoadingFromAlternateCacheKey = key != this.decodeHelper.c().get(0);
        if (Thread.currentThread() != this.currentThread) {
            this.runReason = RunReason.DECODE_DATA;
            this.callback.reschedule(this);
        } else {
            GlideTrace.beginSection("DecodeJob.decodeFromRetrievedData");
            try {
                decodeFromRetrievedData();
            } finally {
                GlideTrace.endSection();
            }
        }
    }

    @Override // com.bumptech.glide.load.engine.DataFetcherGenerator.FetcherReadyCallback
    public void reschedule() {
        this.runReason = RunReason.SWITCH_TO_SOURCE_SERVICE;
        this.callback.reschedule(this);
    }

    @Override // java.lang.Runnable
    public void run() {
        GlideTrace.beginSectionFormat("DecodeJob#run(reason=%s, model=%s)", this.runReason, this.model);
        DataFetcher<?> dataFetcher = this.currentFetcher;
        try {
            try {
                if (this.isCancelled) {
                    notifyFailed();
                    if (dataFetcher != null) {
                        dataFetcher.cleanup();
                    }
                    GlideTrace.endSection();
                    return;
                }
                runWrapped();
                if (dataFetcher != null) {
                    dataFetcher.cleanup();
                }
                GlideTrace.endSection();
            } catch (CallbackException e2) {
                throw e2;
            } catch (Throwable th) {
                if (Log.isLoggable(TAG, 3)) {
                    Log.d(TAG, "DecodeJob threw unexpectedly, isCancelled: " + this.isCancelled + ", stage: " + this.stage, th);
                }
                if (this.stage != Stage.ENCODE) {
                    this.throwables.add(th);
                    notifyFailed();
                }
                if (!this.isCancelled) {
                    throw th;
                }
                throw th;
            }
        } catch (Throwable th2) {
            if (dataFetcher != null) {
                dataFetcher.cleanup();
            }
            GlideTrace.endSection();
            throw th2;
        }
    }

    private void logWithTimeAndKey(String str, long j2, String str2) {
        String str3;
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(" in ");
        sb.append(LogTime.getElapsedMillis(j2));
        sb.append(", load key: ");
        sb.append(this.loadKey);
        if (str2 != null) {
            str3 = ", " + str2;
        } else {
            str3 = "";
        }
        sb.append(str3);
        sb.append(", thread: ");
        sb.append(Thread.currentThread().getName());
        Log.v(TAG, sb.toString());
    }

    @Override // java.lang.Comparable
    public int compareTo(@NonNull DecodeJob<?> decodeJob) {
        int priority = getPriority() - decodeJob.getPriority();
        return priority == 0 ? this.order - decodeJob.order : priority;
    }
}
