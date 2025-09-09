package com.bumptech.glide.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public class LruCache<T, Y> {
    private final Map<T, Entry<Y>> cache = new LinkedHashMap(100, 0.75f, true);
    private long currentSize;
    private final long initialMaxSize;
    private long maxSize;

    static final class Entry<Y> {

        /* renamed from: a, reason: collision with root package name */
        final Object f12440a;

        /* renamed from: b, reason: collision with root package name */
        final int f12441b;

        Entry(Object obj, int i2) {
            this.f12440a = obj;
            this.f12441b = i2;
        }
    }

    public LruCache(long j2) {
        this.initialMaxSize = j2;
        this.maxSize = j2;
    }

    private void evict() {
        c(this.maxSize);
    }

    protected int a(Object obj) {
        return 1;
    }

    protected void b(Object obj, Object obj2) {
    }

    protected synchronized void c(long j2) {
        while (this.currentSize > j2) {
            Iterator<Map.Entry<T, Entry<Y>>> it = this.cache.entrySet().iterator();
            Map.Entry<T, Entry<Y>> next = it.next();
            Entry<Y> value = next.getValue();
            this.currentSize -= value.f12441b;
            T key = next.getKey();
            it.remove();
            b(key, value.f12440a);
        }
    }

    public void clearMemory() {
        c(0L);
    }

    public synchronized boolean contains(@NonNull T t2) {
        return this.cache.containsKey(t2);
    }

    @Nullable
    public synchronized Y get(@NonNull T t2) {
        Entry<Y> entry;
        entry = this.cache.get(t2);
        return entry != null ? (Y) entry.f12440a : null;
    }

    public synchronized long getCurrentSize() {
        return this.currentSize;
    }

    public synchronized long getMaxSize() {
        return this.maxSize;
    }

    @Nullable
    public synchronized Y put(@NonNull T t2, @Nullable Y y2) {
        int iA = a(y2);
        long j2 = iA;
        Y y3 = null;
        if (j2 >= this.maxSize) {
            b(t2, y2);
            return null;
        }
        if (y2 != null) {
            this.currentSize += j2;
        }
        Entry<Y> entryPut = this.cache.put(t2, y2 == null ? null : new Entry<>(y2, iA));
        if (entryPut != null) {
            this.currentSize -= entryPut.f12441b;
            if (!entryPut.f12440a.equals(y2)) {
                b(t2, entryPut.f12440a);
            }
        }
        evict();
        if (entryPut != null) {
            y3 = (Y) entryPut.f12440a;
        }
        return y3;
    }

    @Nullable
    public synchronized Y remove(@NonNull T t2) {
        Entry<Y> entryRemove = this.cache.remove(t2);
        if (entryRemove == null) {
            return null;
        }
        this.currentSize -= entryRemove.f12441b;
        return (Y) entryRemove.f12440a;
    }

    public synchronized void setSizeMultiplier(float f2) {
        if (f2 < 0.0f) {
            throw new IllegalArgumentException("Multiplier must be >= 0");
        }
        this.maxSize = Math.round(this.initialMaxSize * f2);
        evict();
    }
}
