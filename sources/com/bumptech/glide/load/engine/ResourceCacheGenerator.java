package com.bumptech.glide.load.engine;

import androidx.annotation.NonNull;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.engine.DataFetcherGenerator;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.util.pool.GlideTrace;
import java.io.File;
import java.util.List;

/* loaded from: classes3.dex */
class ResourceCacheGenerator implements DataFetcherGenerator, DataFetcher.DataCallback<Object> {
    private File cacheFile;
    private final DataFetcherGenerator.FetcherReadyCallback cb;
    private ResourceCacheKey currentKey;
    private final DecodeHelper<?> helper;
    private volatile ModelLoader.LoadData<?> loadData;
    private int modelLoaderIndex;
    private List<ModelLoader<File, ?>> modelLoaders;
    private int resourceClassIndex = -1;
    private int sourceIdIndex;
    private Key sourceKey;

    ResourceCacheGenerator(DecodeHelper decodeHelper, DataFetcherGenerator.FetcherReadyCallback fetcherReadyCallback) {
        this.helper = decodeHelper;
        this.cb = fetcherReadyCallback;
    }

    private boolean hasNextModelLoader() {
        return this.modelLoaderIndex < this.modelLoaders.size();
    }

    @Override // com.bumptech.glide.load.engine.DataFetcherGenerator
    public void cancel() {
        ModelLoader.LoadData<?> loadData = this.loadData;
        if (loadData != null) {
            loadData.fetcher.cancel();
        }
    }

    @Override // com.bumptech.glide.load.data.DataFetcher.DataCallback
    public void onDataReady(Object obj) {
        this.cb.onDataFetcherReady(this.sourceKey, obj, this.loadData.fetcher, DataSource.RESOURCE_DISK_CACHE, this.currentKey);
    }

    @Override // com.bumptech.glide.load.data.DataFetcher.DataCallback
    public void onLoadFailed(@NonNull Exception exc) {
        this.cb.onDataFetcherFailed(this.currentKey, exc, this.loadData.fetcher, DataSource.RESOURCE_DISK_CACHE);
    }

    @Override // com.bumptech.glide.load.engine.DataFetcherGenerator
    public boolean startNext() {
        GlideTrace.beginSection("ResourceCacheGenerator.startNext");
        try {
            List listC = this.helper.c();
            boolean z2 = false;
            if (listC.isEmpty()) {
                GlideTrace.endSection();
                return false;
            }
            List listM = this.helper.m();
            if (listM.isEmpty()) {
                if (File.class.equals(this.helper.r())) {
                    GlideTrace.endSection();
                    return false;
                }
                throw new IllegalStateException("Failed to find any load path from " + this.helper.i() + " to " + this.helper.r());
            }
            while (true) {
                if (this.modelLoaders != null && hasNextModelLoader()) {
                    this.loadData = null;
                    while (!z2 && hasNextModelLoader()) {
                        List<ModelLoader<File, ?>> list = this.modelLoaders;
                        int i2 = this.modelLoaderIndex;
                        this.modelLoaderIndex = i2 + 1;
                        this.loadData = list.get(i2).buildLoadData(this.cacheFile, this.helper.t(), this.helper.f(), this.helper.k());
                        if (this.loadData != null && this.helper.u(this.loadData.fetcher.getDataClass())) {
                            this.loadData.fetcher.loadData(this.helper.l(), this);
                            z2 = true;
                        }
                    }
                    GlideTrace.endSection();
                    return z2;
                }
                int i3 = this.resourceClassIndex + 1;
                this.resourceClassIndex = i3;
                if (i3 >= listM.size()) {
                    int i4 = this.sourceIdIndex + 1;
                    this.sourceIdIndex = i4;
                    if (i4 >= listC.size()) {
                        GlideTrace.endSection();
                        return false;
                    }
                    this.resourceClassIndex = 0;
                }
                Key key = (Key) listC.get(this.sourceIdIndex);
                Class cls = (Class) listM.get(this.resourceClassIndex);
                this.currentKey = new ResourceCacheKey(this.helper.b(), key, this.helper.p(), this.helper.t(), this.helper.f(), this.helper.s(cls), cls, this.helper.k());
                File file = this.helper.d().get(this.currentKey);
                this.cacheFile = file;
                if (file != null) {
                    this.sourceKey = key;
                    this.modelLoaders = this.helper.j(file);
                    this.modelLoaderIndex = 0;
                }
            }
        } catch (Throwable th) {
            GlideTrace.endSection();
            throw th;
        }
    }
}
