package com.bumptech.glide.load.model;

import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.bumptech.glide.util.LruCache;
import com.bumptech.glide.util.Util;
import java.util.Queue;

/* loaded from: classes3.dex */
public class ModelCache<A, B> {
    private static final int DEFAULT_SIZE = 250;
    private final LruCache<ModelKey<A>, B> cache;

    @VisibleForTesting
    static final class ModelKey<A> {
        private static final Queue<ModelKey<?>> KEY_QUEUE = Util.createQueue(0);
        private int height;
        private A model;
        private int width;

        private ModelKey() {
        }

        static ModelKey a(Object obj, int i2, int i3) {
            ModelKey<?> modelKeyPoll;
            Queue<ModelKey<?>> queue = KEY_QUEUE;
            synchronized (queue) {
                modelKeyPoll = queue.poll();
            }
            if (modelKeyPoll == null) {
                modelKeyPoll = new ModelKey<>();
            }
            modelKeyPoll.init(obj, i2, i3);
            return modelKeyPoll;
        }

        private void init(A a2, int i2, int i3) {
            this.model = a2;
            this.width = i2;
            this.height = i3;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof ModelKey)) {
                return false;
            }
            ModelKey modelKey = (ModelKey) obj;
            return this.width == modelKey.width && this.height == modelKey.height && this.model.equals(modelKey.model);
        }

        public int hashCode() {
            return (((this.height * 31) + this.width) * 31) + this.model.hashCode();
        }

        public void release() {
            Queue<ModelKey<?>> queue = KEY_QUEUE;
            synchronized (queue) {
                queue.offer(this);
            }
        }
    }

    public ModelCache() {
        this(250L);
    }

    public void clear() {
        this.cache.clearMemory();
    }

    @Nullable
    public B get(A a2, int i2, int i3) {
        ModelKey<A> modelKeyA = ModelKey.a(a2, i2, i3);
        B b2 = this.cache.get(modelKeyA);
        modelKeyA.release();
        return b2;
    }

    public void put(A a2, int i2, int i3, B b2) {
        this.cache.put(ModelKey.a(a2, i2, i3), b2);
    }

    public ModelCache(long j2) {
        this.cache = new LruCache<ModelKey<A>, B>(j2) { // from class: com.bumptech.glide.load.model.ModelCache.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.bumptech.glide.util.LruCache
            /* renamed from: d, reason: merged with bridge method [inline-methods] */
            public void b(ModelKey modelKey, Object obj) {
                modelKey.release();
            }
        };
    }
}
