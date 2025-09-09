package com.bumptech.glide.load.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pools;
import com.bumptech.glide.Registry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public class ModelLoaderRegistry {
    private final ModelLoaderCache cache;
    private final MultiModelLoaderFactory multiModelLoaderFactory;

    private static class ModelLoaderCache {
        private final Map<Class<?>, Entry<?>> cachedModelLoaders = new HashMap();

        private static class Entry<Model> {

            /* renamed from: a, reason: collision with root package name */
            final List f12381a;

            public Entry(List<ModelLoader<Model, ?>> list) {
                this.f12381a = list;
            }
        }

        ModelLoaderCache() {
        }

        public void clear() {
            this.cachedModelLoaders.clear();
        }

        @Nullable
        public <Model> List<ModelLoader<Model, ?>> get(Class<Model> cls) {
            Entry<?> entry = this.cachedModelLoaders.get(cls);
            if (entry == null) {
                return null;
            }
            return entry.f12381a;
        }

        public <Model> void put(Class<Model> cls, List<ModelLoader<Model, ?>> list) {
            if (this.cachedModelLoaders.put(cls, new Entry<>(list)) == null) {
                return;
            }
            throw new IllegalStateException("Already cached loaders for model: " + cls);
        }
    }

    public ModelLoaderRegistry(@NonNull Pools.Pool<List<Throwable>> pool) {
        this(new MultiModelLoaderFactory(pool));
    }

    @NonNull
    private static <A> Class<A> getClass(@NonNull A a2) {
        return (Class<A>) a2.getClass();
    }

    @NonNull
    private synchronized <A> List<ModelLoader<A, ?>> getModelLoadersForClass(@NonNull Class<A> cls) {
        List<ModelLoader<A, ?>> listUnmodifiableList;
        listUnmodifiableList = this.cache.get(cls);
        if (listUnmodifiableList == null) {
            listUnmodifiableList = Collections.unmodifiableList(this.multiModelLoaderFactory.b(cls));
            this.cache.put(cls, listUnmodifiableList);
        }
        return listUnmodifiableList;
    }

    private <Model, Data> void tearDown(@NonNull List<ModelLoaderFactory<? extends Model, ? extends Data>> list) {
        Iterator<ModelLoaderFactory<? extends Model, ? extends Data>> it = list.iterator();
        while (it.hasNext()) {
            it.next().teardown();
        }
    }

    public synchronized <Model, Data> void append(@NonNull Class<Model> cls, @NonNull Class<Data> cls2, @NonNull ModelLoaderFactory<? extends Model, ? extends Data> modelLoaderFactory) {
        this.multiModelLoaderFactory.a(cls, cls2, modelLoaderFactory);
        this.cache.clear();
    }

    public synchronized <Model, Data> ModelLoader<Model, Data> build(@NonNull Class<Model> cls, @NonNull Class<Data> cls2) {
        return this.multiModelLoaderFactory.build(cls, cls2);
    }

    @NonNull
    public synchronized List<Class<?>> getDataClasses(@NonNull Class<?> cls) {
        return this.multiModelLoaderFactory.c(cls);
    }

    @NonNull
    public <A> List<ModelLoader<A, ?>> getModelLoaders(@NonNull A a2) {
        List<ModelLoader<A, ?>> modelLoadersForClass = getModelLoadersForClass(getClass(a2));
        if (modelLoadersForClass.isEmpty()) {
            throw new Registry.NoModelLoaderAvailableException(a2);
        }
        int size = modelLoadersForClass.size();
        List<ModelLoader<A, ?>> listEmptyList = Collections.emptyList();
        boolean z2 = true;
        for (int i2 = 0; i2 < size; i2++) {
            ModelLoader<A, ?> modelLoader = modelLoadersForClass.get(i2);
            if (modelLoader.handles(a2)) {
                if (z2) {
                    listEmptyList = new ArrayList<>(size - i2);
                    z2 = false;
                }
                listEmptyList.add(modelLoader);
            }
        }
        if (listEmptyList.isEmpty()) {
            throw new Registry.NoModelLoaderAvailableException(a2, modelLoadersForClass);
        }
        return listEmptyList;
    }

    public synchronized <Model, Data> void prepend(@NonNull Class<Model> cls, @NonNull Class<Data> cls2, @NonNull ModelLoaderFactory<? extends Model, ? extends Data> modelLoaderFactory) {
        this.multiModelLoaderFactory.d(cls, cls2, modelLoaderFactory);
        this.cache.clear();
    }

    public synchronized <Model, Data> void remove(@NonNull Class<Model> cls, @NonNull Class<Data> cls2) {
        tearDown(this.multiModelLoaderFactory.e(cls, cls2));
        this.cache.clear();
    }

    public synchronized <Model, Data> void replace(@NonNull Class<Model> cls, @NonNull Class<Data> cls2, @NonNull ModelLoaderFactory<? extends Model, ? extends Data> modelLoaderFactory) {
        tearDown(this.multiModelLoaderFactory.f(cls, cls2, modelLoaderFactory));
        this.cache.clear();
    }

    private ModelLoaderRegistry(@NonNull MultiModelLoaderFactory multiModelLoaderFactory) {
        this.cache = new ModelLoaderCache();
        this.multiModelLoaderFactory = multiModelLoaderFactory;
    }
}
