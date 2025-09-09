package com.bumptech.glide.load.engine;

import com.bumptech.glide.GlideContext;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.Encoder;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.ResourceEncoder;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.data.DataRewinder;
import com.bumptech.glide.load.engine.DecodeJob;
import com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.resource.UnitTransformation;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
final class DecodeHelper<Transcode> {
    private DecodeJob.DiskCacheProvider diskCacheProvider;
    private DiskCacheStrategy diskCacheStrategy;
    private GlideContext glideContext;
    private int height;
    private boolean isCacheKeysSet;
    private boolean isLoadDataSet;
    private boolean isScaleOnlyOrNoTransform;
    private boolean isTransformationRequired;
    private Object model;
    private Options options;
    private Priority priority;
    private Class<?> resourceClass;
    private Key signature;
    private Class<Transcode> transcodeClass;
    private Map<Class<?>, Transformation<?>> transformations;
    private int width;
    private final List<ModelLoader.LoadData<?>> loadData = new ArrayList();
    private final List<Key> cacheKeys = new ArrayList();

    DecodeHelper() {
    }

    void a() {
        this.glideContext = null;
        this.model = null;
        this.signature = null;
        this.resourceClass = null;
        this.transcodeClass = null;
        this.options = null;
        this.priority = null;
        this.transformations = null;
        this.diskCacheStrategy = null;
        this.loadData.clear();
        this.isLoadDataSet = false;
        this.cacheKeys.clear();
        this.isCacheKeysSet = false;
    }

    ArrayPool b() {
        return this.glideContext.getArrayPool();
    }

    List c() {
        if (!this.isCacheKeysSet) {
            this.isCacheKeysSet = true;
            this.cacheKeys.clear();
            List listG = g();
            int size = listG.size();
            for (int i2 = 0; i2 < size; i2++) {
                ModelLoader.LoadData loadData = (ModelLoader.LoadData) listG.get(i2);
                if (!this.cacheKeys.contains(loadData.sourceKey)) {
                    this.cacheKeys.add(loadData.sourceKey);
                }
                for (int i3 = 0; i3 < loadData.alternateKeys.size(); i3++) {
                    if (!this.cacheKeys.contains(loadData.alternateKeys.get(i3))) {
                        this.cacheKeys.add(loadData.alternateKeys.get(i3));
                    }
                }
            }
        }
        return this.cacheKeys;
    }

    DiskCache d() {
        return this.diskCacheProvider.getDiskCache();
    }

    DiskCacheStrategy e() {
        return this.diskCacheStrategy;
    }

    int f() {
        return this.height;
    }

    List g() {
        if (!this.isLoadDataSet) {
            this.isLoadDataSet = true;
            this.loadData.clear();
            List modelLoaders = this.glideContext.getRegistry().getModelLoaders(this.model);
            int size = modelLoaders.size();
            for (int i2 = 0; i2 < size; i2++) {
                ModelLoader.LoadData<?> loadDataBuildLoadData = ((ModelLoader) modelLoaders.get(i2)).buildLoadData(this.model, this.width, this.height, this.options);
                if (loadDataBuildLoadData != null) {
                    this.loadData.add(loadDataBuildLoadData);
                }
            }
        }
        return this.loadData;
    }

    LoadPath h(Class cls) {
        return this.glideContext.getRegistry().getLoadPath(cls, this.resourceClass, this.transcodeClass);
    }

    Class i() {
        return this.model.getClass();
    }

    List j(File file) {
        return this.glideContext.getRegistry().getModelLoaders(file);
    }

    Options k() {
        return this.options;
    }

    Priority l() {
        return this.priority;
    }

    List m() {
        return this.glideContext.getRegistry().getRegisteredResourceClasses(this.model.getClass(), this.resourceClass, this.transcodeClass);
    }

    ResourceEncoder n(Resource resource) {
        return this.glideContext.getRegistry().getResultEncoder(resource);
    }

    DataRewinder o(Object obj) {
        return this.glideContext.getRegistry().getRewinder(obj);
    }

    Key p() {
        return this.signature;
    }

    Encoder q(Object obj) {
        return this.glideContext.getRegistry().getSourceEncoder(obj);
    }

    Class r() {
        return this.transcodeClass;
    }

    Transformation s(Class cls) {
        Transformation<?> value = this.transformations.get(cls);
        if (value == null) {
            Iterator<Map.Entry<Class<?>, Transformation<?>>> it = this.transformations.entrySet().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Map.Entry<Class<?>, Transformation<?>> next = it.next();
                if (next.getKey().isAssignableFrom(cls)) {
                    value = next.getValue();
                    break;
                }
            }
        }
        if (value != null) {
            return value;
        }
        if (!this.transformations.isEmpty() || !this.isTransformationRequired) {
            return UnitTransformation.get();
        }
        throw new IllegalArgumentException("Missing transformation for " + cls + ". If you wish to ignore unknown resource types, use the optional transformation methods.");
    }

    int t() {
        return this.width;
    }

    boolean u(Class cls) {
        return h(cls) != null;
    }

    void v(GlideContext glideContext, Object obj, Key key, int i2, int i3, DiskCacheStrategy diskCacheStrategy, Class cls, Class cls2, Priority priority, Options options, Map map, boolean z2, boolean z3, DecodeJob.DiskCacheProvider diskCacheProvider) {
        this.glideContext = glideContext;
        this.model = obj;
        this.signature = key;
        this.width = i2;
        this.height = i3;
        this.diskCacheStrategy = diskCacheStrategy;
        this.resourceClass = cls;
        this.diskCacheProvider = diskCacheProvider;
        this.transcodeClass = cls2;
        this.priority = priority;
        this.options = options;
        this.transformations = map;
        this.isTransformationRequired = z2;
        this.isScaleOnlyOrNoTransform = z3;
    }

    boolean w(Resource resource) {
        return this.glideContext.getRegistry().isResourceEncoderAvailable(resource);
    }

    boolean x() {
        return this.isScaleOnlyOrNoTransform;
    }

    boolean y(Key key) {
        List listG = g();
        int size = listG.size();
        for (int i2 = 0; i2 < size; i2++) {
            if (((ModelLoader.LoadData) listG.get(i2)).sourceKey.equals(key)) {
                return true;
            }
        }
        return false;
    }
}
