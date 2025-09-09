package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.UnmodifiableIterator;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.annotation.CheckForNull;

@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
class MapIteratorCache<K, V> {
    private final Map<K, V> backingMap;

    @CheckForNull
    private volatile transient Map.Entry<K, V> cacheEntry;

    MapIteratorCache(Map map) {
        this.backingMap = (Map) Preconditions.checkNotNull(map);
    }

    void c() {
        this.cacheEntry = null;
    }

    final boolean d(Object obj) {
        return f(obj) != null || this.backingMap.containsKey(obj);
    }

    Object e(Object obj) {
        Preconditions.checkNotNull(obj);
        Object objF = f(obj);
        return objF == null ? g(obj) : objF;
    }

    Object f(Object obj) {
        Map.Entry<K, V> entry = this.cacheEntry;
        if (entry == null || entry.getKey() != obj) {
            return null;
        }
        return entry.getValue();
    }

    final Object g(Object obj) {
        Preconditions.checkNotNull(obj);
        return this.backingMap.get(obj);
    }

    final Object h(Object obj, Object obj2) {
        Preconditions.checkNotNull(obj);
        Preconditions.checkNotNull(obj2);
        c();
        return this.backingMap.put(obj, obj2);
    }

    final Object i(Object obj) {
        Preconditions.checkNotNull(obj);
        c();
        return this.backingMap.remove(obj);
    }

    final Set j() {
        return new AbstractSet<K>() { // from class: com.google.common.graph.MapIteratorCache.1
            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean contains(@CheckForNull Object obj) {
                return MapIteratorCache.this.d(obj);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                return MapIteratorCache.this.backingMap.size();
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public UnmodifiableIterator<K> iterator() {
                final Iterator<Map.Entry<K, V>> it = MapIteratorCache.this.backingMap.entrySet().iterator();
                return new UnmodifiableIterator<K>(this) { // from class: com.google.common.graph.MapIteratorCache.1.1

                    /* renamed from: b, reason: collision with root package name */
                    final /* synthetic */ AnonymousClass1 f14536b;

                    {
                        this.f14536b = this;
                    }

                    @Override // java.util.Iterator
                    public boolean hasNext() {
                        return it.hasNext();
                    }

                    @Override // java.util.Iterator
                    public K next() {
                        Map.Entry entry = (Map.Entry) it.next();
                        MapIteratorCache.this.cacheEntry = entry;
                        return (K) entry.getKey();
                    }
                };
            }
        };
    }
}
