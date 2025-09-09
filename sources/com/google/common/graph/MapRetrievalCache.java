package com.google.common.graph;

import com.google.common.base.Preconditions;
import java.util.Map;
import javax.annotation.CheckForNull;

@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
final class MapRetrievalCache<K, V> extends MapIteratorCache<K, V> {

    @CheckForNull
    private volatile transient CacheEntry<K, V> cacheEntry1;

    @CheckForNull
    private volatile transient CacheEntry<K, V> cacheEntry2;

    private static final class CacheEntry<K, V> {

        /* renamed from: a, reason: collision with root package name */
        final Object f14537a;

        /* renamed from: b, reason: collision with root package name */
        final Object f14538b;

        CacheEntry(Object obj, Object obj2) {
            this.f14537a = obj;
            this.f14538b = obj2;
        }
    }

    MapRetrievalCache(Map map) {
        super(map);
    }

    private void addToCache(K k2, V v2) {
        addToCache(new CacheEntry<>(k2, v2));
    }

    @Override // com.google.common.graph.MapIteratorCache
    void c() {
        super.c();
        this.cacheEntry1 = null;
        this.cacheEntry2 = null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.graph.MapIteratorCache
    Object e(Object obj) {
        Preconditions.checkNotNull(obj);
        Object objF = f(obj);
        if (objF != null) {
            return objF;
        }
        Object objG = g(obj);
        if (objG != null) {
            addToCache(obj, objG);
        }
        return objG;
    }

    @Override // com.google.common.graph.MapIteratorCache
    Object f(Object obj) {
        Object objF = super.f(obj);
        if (objF != null) {
            return objF;
        }
        CacheEntry<K, V> cacheEntry = this.cacheEntry1;
        if (cacheEntry != null && cacheEntry.f14537a == obj) {
            return cacheEntry.f14538b;
        }
        CacheEntry<K, V> cacheEntry2 = this.cacheEntry2;
        if (cacheEntry2 == null || cacheEntry2.f14537a != obj) {
            return null;
        }
        addToCache(cacheEntry2);
        return cacheEntry2.f14538b;
    }

    private void addToCache(CacheEntry<K, V> cacheEntry) {
        this.cacheEntry2 = this.cacheEntry1;
        this.cacheEntry1 = cacheEntry;
    }
}
