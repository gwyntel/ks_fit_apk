package com.google.common.cache;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;

@ElementTypesAreNonnullByDefault
@GwtCompatible
/* loaded from: classes3.dex */
public interface LoadingCache<K, V> extends Cache<K, V>, Function<K, V> {
    @Override // com.google.common.base.Function
    @Deprecated
    V apply(K k2);

    @Override // com.google.common.cache.Cache
    ConcurrentMap<K, V> asMap();

    @CanIgnoreReturnValue
    V get(K k2) throws ExecutionException;

    @CanIgnoreReturnValue
    ImmutableMap<K, V> getAll(Iterable<? extends K> iterable) throws ExecutionException;

    @CanIgnoreReturnValue
    V getUnchecked(K k2);

    void refresh(K k2);
}
