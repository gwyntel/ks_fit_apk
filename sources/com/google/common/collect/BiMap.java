package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import javax.annotation.CheckForNull;

@GwtCompatible
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public interface BiMap<K, V> extends Map<K, V> {
    @CanIgnoreReturnValue
    @CheckForNull
    V forcePut(@ParametricNullness K k2, @ParametricNullness V v2);

    BiMap<V, K> inverse();

    @CanIgnoreReturnValue
    @CheckForNull
    V put(@ParametricNullness K k2, @ParametricNullness V v2);

    void putAll(Map<? extends K, ? extends V> map);

    /* bridge */ /* synthetic */ Collection values();

    @Override // java.util.Map
    Set<V> values();
}
