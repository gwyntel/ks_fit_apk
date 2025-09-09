package com.google.common.cache;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.collect.ImmutableMap;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.concurrent.ExecutionException;

@ElementTypesAreNonnullByDefault
@GwtIncompatible
/* loaded from: classes3.dex */
public abstract class ForwardingLoadingCache<K, V> extends ForwardingCache<K, V> implements LoadingCache<K, V> {

    public static abstract class SimpleForwardingLoadingCache<K, V> extends ForwardingLoadingCache<K, V> {
        private final LoadingCache<K, V> delegate;

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.common.cache.ForwardingLoadingCache, com.google.common.cache.ForwardingCache, com.google.common.collect.ForwardingObject
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public final LoadingCache delegate() {
            return this.delegate;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.common.cache.ForwardingCache, com.google.common.collect.ForwardingObject
    /* renamed from: a */
    public abstract LoadingCache delegate();

    @Override // com.google.common.cache.LoadingCache, com.google.common.base.Function
    public V apply(K k2) {
        return (V) delegate().apply(k2);
    }

    @Override // com.google.common.cache.LoadingCache
    @CanIgnoreReturnValue
    public V get(K k2) throws ExecutionException {
        return (V) delegate().get(k2);
    }

    @Override // com.google.common.cache.LoadingCache
    @CanIgnoreReturnValue
    public ImmutableMap<K, V> getAll(Iterable<? extends K> iterable) throws ExecutionException {
        return delegate().getAll(iterable);
    }

    @Override // com.google.common.cache.LoadingCache
    @CanIgnoreReturnValue
    public V getUnchecked(K k2) {
        return (V) delegate().getUnchecked(k2);
    }

    @Override // com.google.common.cache.LoadingCache
    public void refresh(K k2) {
        delegate().refresh(k2);
    }
}
