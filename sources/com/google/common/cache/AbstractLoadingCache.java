package com.google.common.cache;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.UncheckedExecutionException;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@ElementTypesAreNonnullByDefault
@GwtIncompatible
/* loaded from: classes3.dex */
public abstract class AbstractLoadingCache<K, V> extends AbstractCache<K, V> implements LoadingCache<K, V> {
    @Override // com.google.common.cache.LoadingCache, com.google.common.base.Function
    public final V apply(K k2) {
        return getUnchecked(k2);
    }

    @Override // com.google.common.cache.LoadingCache
    public ImmutableMap<K, V> getAll(Iterable<? extends K> iterable) throws ExecutionException {
        LinkedHashMap linkedHashMapNewLinkedHashMap = Maps.newLinkedHashMap();
        for (K k2 : iterable) {
            if (!linkedHashMapNewLinkedHashMap.containsKey(k2)) {
                linkedHashMapNewLinkedHashMap.put(k2, get(k2));
            }
        }
        return ImmutableMap.copyOf((Map) linkedHashMapNewLinkedHashMap);
    }

    @Override // com.google.common.cache.LoadingCache
    @CanIgnoreReturnValue
    public V getUnchecked(K k2) {
        try {
            return get(k2);
        } catch (ExecutionException e2) {
            throw new UncheckedExecutionException(e2.getCause());
        }
    }

    @Override // com.google.common.cache.LoadingCache
    public void refresh(K k2) {
        throw new UnsupportedOperationException();
    }
}
