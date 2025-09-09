package com.bumptech.glide.load.engine;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.Encoder;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.data.DataRewinder;
import com.bumptech.glide.load.engine.DataFetcherGenerator;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.util.LogTime;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/* loaded from: classes3.dex */
class SourceGenerator implements DataFetcherGenerator, DataFetcherGenerator.FetcherReadyCallback {
    private static final String TAG = "SourceGenerator";
    private final DataFetcherGenerator.FetcherReadyCallback cb;
    private volatile Object dataToCache;
    private final DecodeHelper<?> helper;
    private volatile ModelLoader.LoadData<?> loadData;
    private volatile int loadDataListIndex;
    private volatile DataCacheKey originalKey;
    private volatile DataCacheGenerator sourceCacheGenerator;

    SourceGenerator(DecodeHelper decodeHelper, DataFetcherGenerator.FetcherReadyCallback fetcherReadyCallback) {
        this.helper = decodeHelper;
        this.cb = fetcherReadyCallback;
    }

    private boolean cacheData(Object obj) throws Throwable {
        long logTime = LogTime.getLogTime();
        boolean z2 = false;
        try {
            DataRewinder dataRewinderO = this.helper.o(obj);
            Object objRewindAndGet = dataRewinderO.rewindAndGet();
            Encoder encoderQ = this.helper.q(objRewindAndGet);
            DataCacheWriter dataCacheWriter = new DataCacheWriter(encoderQ, objRewindAndGet, this.helper.k());
            DataCacheKey dataCacheKey = new DataCacheKey(this.loadData.sourceKey, this.helper.p());
            DiskCache diskCacheD = this.helper.d();
            diskCacheD.put(dataCacheKey, dataCacheWriter);
            if (Log.isLoggable(TAG, 2)) {
                Log.v(TAG, "Finished encoding source to cache, key: " + dataCacheKey + ", data: " + obj + ", encoder: " + encoderQ + ", duration: " + LogTime.getElapsedMillis(logTime));
            }
            if (diskCacheD.get(dataCacheKey) != null) {
                this.originalKey = dataCacheKey;
                this.sourceCacheGenerator = new DataCacheGenerator(Collections.singletonList(this.loadData.sourceKey), this.helper, this);
                this.loadData.fetcher.cleanup();
                return true;
            }
            if (Log.isLoggable(TAG, 3)) {
                Log.d(TAG, "Attempt to write: " + this.originalKey + ", data: " + obj + " to the disk cache failed, maybe the disk cache is disabled? Trying to decode the data directly...");
            }
            try {
                this.cb.onDataFetcherReady(this.loadData.sourceKey, dataRewinderO.rewindAndGet(), this.loadData.fetcher, this.loadData.fetcher.getDataSource(), this.loadData.sourceKey);
                return false;
            } catch (Throwable th) {
                th = th;
                z2 = true;
                if (!z2) {
                    this.loadData.fetcher.cleanup();
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private boolean hasNextModelLoader() {
        return this.loadDataListIndex < this.helper.g().size();
    }

    private void startNextLoad(final ModelLoader.LoadData<?> loadData) {
        this.loadData.fetcher.loadData(this.helper.l(), new DataFetcher.DataCallback<Object>() { // from class: com.bumptech.glide.load.engine.SourceGenerator.1
            @Override // com.bumptech.glide.load.data.DataFetcher.DataCallback
            public void onDataReady(@Nullable Object obj) {
                if (SourceGenerator.this.a(loadData)) {
                    SourceGenerator.this.b(loadData, obj);
                }
            }

            @Override // com.bumptech.glide.load.data.DataFetcher.DataCallback
            public void onLoadFailed(@NonNull Exception exc) {
                if (SourceGenerator.this.a(loadData)) {
                    SourceGenerator.this.c(loadData, exc);
                }
            }
        });
    }

    boolean a(ModelLoader.LoadData loadData) {
        ModelLoader.LoadData<?> loadData2 = this.loadData;
        return loadData2 != null && loadData2 == loadData;
    }

    void b(ModelLoader.LoadData loadData, Object obj) {
        DiskCacheStrategy diskCacheStrategyE = this.helper.e();
        if (obj != null && diskCacheStrategyE.isDataCacheable(loadData.fetcher.getDataSource())) {
            this.dataToCache = obj;
            this.cb.reschedule();
        } else {
            DataFetcherGenerator.FetcherReadyCallback fetcherReadyCallback = this.cb;
            Key key = loadData.sourceKey;
            DataFetcher<Data> dataFetcher = loadData.fetcher;
            fetcherReadyCallback.onDataFetcherReady(key, obj, dataFetcher, dataFetcher.getDataSource(), this.originalKey);
        }
    }

    void c(ModelLoader.LoadData loadData, Exception exc) {
        DataFetcherGenerator.FetcherReadyCallback fetcherReadyCallback = this.cb;
        Key key = this.originalKey;
        DataFetcher<Data> dataFetcher = loadData.fetcher;
        fetcherReadyCallback.onDataFetcherFailed(key, exc, dataFetcher, dataFetcher.getDataSource());
    }

    @Override // com.bumptech.glide.load.engine.DataFetcherGenerator
    public void cancel() {
        ModelLoader.LoadData<?> loadData = this.loadData;
        if (loadData != null) {
            loadData.fetcher.cancel();
        }
    }

    @Override // com.bumptech.glide.load.engine.DataFetcherGenerator.FetcherReadyCallback
    public void onDataFetcherFailed(Key key, Exception exc, DataFetcher<?> dataFetcher, DataSource dataSource) {
        this.cb.onDataFetcherFailed(key, exc, dataFetcher, this.loadData.fetcher.getDataSource());
    }

    @Override // com.bumptech.glide.load.engine.DataFetcherGenerator.FetcherReadyCallback
    public void onDataFetcherReady(Key key, Object obj, DataFetcher<?> dataFetcher, DataSource dataSource, Key key2) {
        this.cb.onDataFetcherReady(key, obj, dataFetcher, this.loadData.fetcher.getDataSource(), key);
    }

    @Override // com.bumptech.glide.load.engine.DataFetcherGenerator.FetcherReadyCallback
    public void reschedule() {
        throw new UnsupportedOperationException();
    }

    @Override // com.bumptech.glide.load.engine.DataFetcherGenerator
    public boolean startNext() {
        if (this.dataToCache != null) {
            Object obj = this.dataToCache;
            this.dataToCache = null;
            try {
                if (!cacheData(obj)) {
                    return true;
                }
            } catch (IOException e2) {
                if (Log.isLoggable(TAG, 3)) {
                    Log.d(TAG, "Failed to properly rewind or write data to cache", e2);
                }
            }
        }
        if (this.sourceCacheGenerator != null && this.sourceCacheGenerator.startNext()) {
            return true;
        }
        this.sourceCacheGenerator = null;
        this.loadData = null;
        boolean z2 = false;
        while (!z2 && hasNextModelLoader()) {
            List listG = this.helper.g();
            int i2 = this.loadDataListIndex;
            this.loadDataListIndex = i2 + 1;
            this.loadData = (ModelLoader.LoadData) listG.get(i2);
            if (this.loadData != null && (this.helper.e().isDataCacheable(this.loadData.fetcher.getDataSource()) || this.helper.u(this.loadData.fetcher.getDataClass()))) {
                startNextLoad(this.loadData);
                z2 = true;
            }
        }
        return z2;
    }
}
