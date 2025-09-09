package com.bumptech.glide.load.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pools;
import com.bumptech.glide.Registry;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.util.Preconditions;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* loaded from: classes3.dex */
public class MultiModelLoaderFactory {
    private static final Factory DEFAULT_FACTORY = new Factory();
    private static final ModelLoader<Object, Object> EMPTY_MODEL_LOADER = new EmptyModelLoader();
    private final Set<Entry<?, ?>> alreadyUsedEntries;
    private final List<Entry<?, ?>> entries;
    private final Factory factory;
    private final Pools.Pool<List<Throwable>> throwableListPool;

    private static class EmptyModelLoader implements ModelLoader<Object, Object> {
        EmptyModelLoader() {
        }

        @Override // com.bumptech.glide.load.model.ModelLoader
        @Nullable
        public ModelLoader.LoadData<Object> buildLoadData(@NonNull Object obj, int i2, int i3, @NonNull Options options) {
            return null;
        }

        @Override // com.bumptech.glide.load.model.ModelLoader
        public boolean handles(@NonNull Object obj) {
            return false;
        }
    }

    private static class Entry<Model, Data> {

        /* renamed from: a, reason: collision with root package name */
        final Class f12382a;

        /* renamed from: b, reason: collision with root package name */
        final ModelLoaderFactory f12383b;
        private final Class<Model> modelClass;

        public Entry(@NonNull Class<Model> cls, @NonNull Class<Data> cls2, @NonNull ModelLoaderFactory<? extends Model, ? extends Data> modelLoaderFactory) {
            this.modelClass = cls;
            this.f12382a = cls2;
            this.f12383b = modelLoaderFactory;
        }

        public boolean handles(@NonNull Class<?> cls, @NonNull Class<?> cls2) {
            return handles(cls) && this.f12382a.isAssignableFrom(cls2);
        }

        public boolean handles(@NonNull Class<?> cls) {
            return this.modelClass.isAssignableFrom(cls);
        }
    }

    static class Factory {
        Factory() {
        }

        @NonNull
        public <Model, Data> MultiModelLoader<Model, Data> build(@NonNull List<ModelLoader<Model, Data>> list, @NonNull Pools.Pool<List<Throwable>> pool) {
            return new MultiModelLoader<>(list, pool);
        }
    }

    public MultiModelLoaderFactory(@NonNull Pools.Pool<List<Throwable>> pool) {
        this(pool, DEFAULT_FACTORY);
    }

    private <Model, Data> void add(@NonNull Class<Model> cls, @NonNull Class<Data> cls2, @NonNull ModelLoaderFactory<? extends Model, ? extends Data> modelLoaderFactory, boolean z2) {
        Entry<?, ?> entry = new Entry<>(cls, cls2, modelLoaderFactory);
        List<Entry<?, ?>> list = this.entries;
        list.add(z2 ? list.size() : 0, entry);
    }

    @NonNull
    private static <Model, Data> ModelLoader<Model, Data> emptyModelLoader() {
        return (ModelLoader<Model, Data>) EMPTY_MODEL_LOADER;
    }

    @NonNull
    private <Model, Data> ModelLoaderFactory<Model, Data> getFactory(@NonNull Entry<?, ?> entry) {
        return entry.f12383b;
    }

    synchronized void a(Class cls, Class cls2, ModelLoaderFactory modelLoaderFactory) {
        add(cls, cls2, modelLoaderFactory, true);
    }

    synchronized List b(Class cls) {
        ArrayList arrayList;
        try {
            arrayList = new ArrayList();
            for (Entry<?, ?> entry : this.entries) {
                if (!this.alreadyUsedEntries.contains(entry) && entry.handles(cls)) {
                    this.alreadyUsedEntries.add(entry);
                    arrayList.add(build(entry));
                    this.alreadyUsedEntries.remove(entry);
                }
            }
        } finally {
        }
        return arrayList;
    }

    @NonNull
    public synchronized <Model, Data> ModelLoader<Model, Data> build(@NonNull Class<Model> cls, @NonNull Class<Data> cls2) {
        try {
            ArrayList arrayList = new ArrayList();
            boolean z2 = false;
            for (Entry<?, ?> entry : this.entries) {
                if (this.alreadyUsedEntries.contains(entry)) {
                    z2 = true;
                } else if (entry.handles(cls, cls2)) {
                    this.alreadyUsedEntries.add(entry);
                    arrayList.add(build(entry));
                    this.alreadyUsedEntries.remove(entry);
                }
            }
            if (arrayList.size() > 1) {
                return this.factory.build(arrayList, this.throwableListPool);
            }
            if (arrayList.size() == 1) {
                return (ModelLoader) arrayList.get(0);
            }
            if (!z2) {
                throw new Registry.NoModelLoaderAvailableException((Class<?>) cls, (Class<?>) cls2);
            }
            return emptyModelLoader();
        } catch (Throwable th) {
            this.alreadyUsedEntries.clear();
            throw th;
        }
    }

    synchronized List c(Class cls) {
        ArrayList arrayList;
        arrayList = new ArrayList();
        for (Entry<?, ?> entry : this.entries) {
            if (!arrayList.contains(entry.f12382a) && entry.handles(cls)) {
                arrayList.add(entry.f12382a);
            }
        }
        return arrayList;
    }

    synchronized void d(Class cls, Class cls2, ModelLoaderFactory modelLoaderFactory) {
        add(cls, cls2, modelLoaderFactory, false);
    }

    synchronized List e(Class cls, Class cls2) {
        ArrayList arrayList;
        arrayList = new ArrayList();
        Iterator<Entry<?, ?>> it = this.entries.iterator();
        while (it.hasNext()) {
            Entry<?, ?> next = it.next();
            if (next.handles(cls, cls2)) {
                it.remove();
                arrayList.add(getFactory(next));
            }
        }
        return arrayList;
    }

    synchronized List f(Class cls, Class cls2, ModelLoaderFactory modelLoaderFactory) {
        List listE;
        listE = e(cls, cls2);
        a(cls, cls2, modelLoaderFactory);
        return listE;
    }

    MultiModelLoaderFactory(Pools.Pool pool, Factory factory) {
        this.entries = new ArrayList();
        this.alreadyUsedEntries = new HashSet();
        this.throwableListPool = pool;
        this.factory = factory;
    }

    @NonNull
    private <Model, Data> ModelLoader<Model, Data> build(@NonNull Entry<?, ?> entry) {
        return (ModelLoader) Preconditions.checkNotNull(entry.f12383b.build(this));
    }
}
