package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.J2ktIncompatible;
import com.google.common.collect.ImmutableMap;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.DoNotCall;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collector;
import javax.annotation.CheckForNull;

@GwtCompatible(emulated = true, serializable = true)
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public abstract class ImmutableBiMap<K, V> extends ImmutableMap<K, V> implements BiMap<K, V> {
    private static final long serialVersionUID = 912559;

    public static final class Builder<K, V> extends ImmutableMap.Builder<K, V> {
        public Builder() {
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.collect.ImmutableMap.Builder
        /* renamed from: c, reason: merged with bridge method [inline-methods] */
        public Builder a(ImmutableMap.Builder builder) {
            super.a(builder);
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.collect.ImmutableMap.Builder
        @CanIgnoreReturnValue
        public /* bridge */ /* synthetic */ ImmutableMap.Builder put(Object obj, Object obj2) {
            return put((Builder<K, V>) obj, obj2);
        }

        Builder(int i2) {
            super(i2);
        }

        @Override // com.google.common.collect.ImmutableMap.Builder
        public ImmutableBiMap<K, V> build() {
            return buildOrThrow();
        }

        @Override // com.google.common.collect.ImmutableMap.Builder
        @DoNotCall
        @Deprecated
        public ImmutableBiMap<K, V> buildKeepingLast() {
            throw new UnsupportedOperationException("Not supported for bimaps");
        }

        @Override // com.google.common.collect.ImmutableMap.Builder
        public ImmutableBiMap<K, V> buildOrThrow() {
            int i2 = this.f14008c;
            if (i2 == 0) {
                return ImmutableBiMap.of();
            }
            if (this.f14006a != null) {
                if (this.f14009d) {
                    this.f14007b = Arrays.copyOf(this.f14007b, i2 * 2);
                }
                ImmutableMap.Builder.b(this.f14007b, this.f14008c, this.f14006a);
            }
            this.f14009d = true;
            return new RegularImmutableBiMap(this.f14007b, this.f14008c);
        }

        @Override // com.google.common.collect.ImmutableMap.Builder
        @CanIgnoreReturnValue
        public Builder<K, V> orderEntriesByValue(Comparator<? super V> comparator) {
            super.orderEntriesByValue((Comparator) comparator);
            return this;
        }

        @Override // com.google.common.collect.ImmutableMap.Builder
        @CanIgnoreReturnValue
        public Builder<K, V> put(K k2, V v2) {
            super.put((Builder<K, V>) k2, (K) v2);
            return this;
        }

        @Override // com.google.common.collect.ImmutableMap.Builder
        @CanIgnoreReturnValue
        public Builder<K, V> putAll(Map<? extends K, ? extends V> map) {
            super.putAll((Map) map);
            return this;
        }

        @Override // com.google.common.collect.ImmutableMap.Builder
        @CanIgnoreReturnValue
        public Builder<K, V> put(Map.Entry<? extends K, ? extends V> entry) {
            super.put((Map.Entry) entry);
            return this;
        }

        @Override // com.google.common.collect.ImmutableMap.Builder
        @CanIgnoreReturnValue
        public Builder<K, V> putAll(Iterable<? extends Map.Entry<? extends K, ? extends V>> iterable) {
            super.putAll((Iterable) iterable);
            return this;
        }
    }

    @J2ktIncompatible
    private static class SerializedForm<K, V> extends ImmutableMap.SerializedForm<K, V> {
        private static final long serialVersionUID = 0;

        SerializedForm(ImmutableBiMap<K, V> immutableBiMap) {
            super(immutableBiMap);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.collect.ImmutableMap.SerializedForm
        public Builder<K, V> makeBuilder(int i2) {
            return new Builder<>(i2);
        }
    }

    ImmutableBiMap() {
    }

    public static <K, V> Builder<K, V> builder() {
        return new Builder<>();
    }

    public static <K, V> Builder<K, V> builderWithExpectedSize(int i2) {
        CollectPreconditions.b(i2, "expectedSize");
        return new Builder<>(i2);
    }

    public static <K, V> ImmutableBiMap<K, V> copyOf(Map<? extends K, ? extends V> map) {
        if (map instanceof ImmutableBiMap) {
            ImmutableBiMap<K, V> immutableBiMap = (ImmutableBiMap) map;
            if (!immutableBiMap.isPartialView()) {
                return immutableBiMap;
            }
        }
        return copyOf((Iterable) map.entrySet());
    }

    public static <K, V> ImmutableBiMap<K, V> of() {
        return RegularImmutableBiMap.EMPTY;
    }

    @SafeVarargs
    public static <K, V> ImmutableBiMap<K, V> ofEntries(Map.Entry<? extends K, ? extends V>... entryArr) {
        return copyOf((Iterable) Arrays.asList(entryArr));
    }

    @J2ktIncompatible
    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Use SerializedForm");
    }

    @IgnoreJRERequirement
    public static <T, K, V> Collector<T, ?, ImmutableBiMap<K, V>> toImmutableBiMap(Function<? super T, ? extends K> function, Function<? super T, ? extends V> function2) {
        return CollectCollectors.G(function, function2);
    }

    @IgnoreJRERequirement
    @DoNotCall("Use toImmutableBiMap")
    @Deprecated
    public static <T, K, V> Collector<T, ?, ImmutableMap<K, V>> toImmutableMap(Function<? super T, ? extends K> function, Function<? super T, ? extends V> function2) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.common.collect.BiMap
    @CheckForNull
    @DoNotCall("Always throws UnsupportedOperationException")
    @Deprecated
    @CanIgnoreReturnValue
    public final V forcePut(K k2, V v2) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.common.collect.BiMap
    public abstract ImmutableBiMap<V, K> inverse();

    @Override // com.google.common.collect.ImmutableMap
    @J2ktIncompatible
    Object writeReplace() {
        return new SerializedForm(this);
    }

    public static <K, V> ImmutableBiMap<K, V> of(K k2, V v2) {
        CollectPreconditions.a(k2, v2);
        return new RegularImmutableBiMap(new Object[]{k2, v2}, 1);
    }

    @IgnoreJRERequirement
    @DoNotCall("Use toImmutableBiMap")
    @Deprecated
    public static <T, K, V> Collector<T, ?, ImmutableMap<K, V>> toImmutableMap(Function<? super T, ? extends K> function, Function<? super T, ? extends V> function2, BinaryOperator<V> binaryOperator) {
        throw new UnsupportedOperationException();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.common.collect.ImmutableMap
    public final ImmutableSet<V> createValues() {
        throw new AssertionError("should never be called");
    }

    public static <K, V> ImmutableBiMap<K, V> of(K k2, V v2, K k3, V v3) {
        CollectPreconditions.a(k2, v2);
        CollectPreconditions.a(k3, v3);
        return new RegularImmutableBiMap(new Object[]{k2, v2, k3, v3}, 2);
    }

    @Override // com.google.common.collect.ImmutableMap, java.util.Map, com.google.common.collect.BiMap
    public ImmutableSet<V> values() {
        return inverse().keySet();
    }

    public static <K, V> ImmutableBiMap<K, V> copyOf(Iterable<? extends Map.Entry<? extends K, ? extends V>> iterable) {
        return new Builder(iterable instanceof Collection ? ((Collection) iterable).size() : 4).putAll((Iterable) iterable).build();
    }

    public static <K, V> ImmutableBiMap<K, V> of(K k2, V v2, K k3, V v3, K k4, V v4) {
        CollectPreconditions.a(k2, v2);
        CollectPreconditions.a(k3, v3);
        CollectPreconditions.a(k4, v4);
        return new RegularImmutableBiMap(new Object[]{k2, v2, k3, v3, k4, v4}, 3);
    }

    public static <K, V> ImmutableBiMap<K, V> of(K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
        CollectPreconditions.a(k2, v2);
        CollectPreconditions.a(k3, v3);
        CollectPreconditions.a(k4, v4);
        CollectPreconditions.a(k5, v5);
        return new RegularImmutableBiMap(new Object[]{k2, v2, k3, v3, k4, v4, k5, v5}, 4);
    }

    public static <K, V> ImmutableBiMap<K, V> of(K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6) {
        CollectPreconditions.a(k2, v2);
        CollectPreconditions.a(k3, v3);
        CollectPreconditions.a(k4, v4);
        CollectPreconditions.a(k5, v5);
        CollectPreconditions.a(k6, v6);
        return new RegularImmutableBiMap(new Object[]{k2, v2, k3, v3, k4, v4, k5, v5, k6, v6}, 5);
    }

    public static <K, V> ImmutableBiMap<K, V> of(K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7) {
        CollectPreconditions.a(k2, v2);
        CollectPreconditions.a(k3, v3);
        CollectPreconditions.a(k4, v4);
        CollectPreconditions.a(k5, v5);
        CollectPreconditions.a(k6, v6);
        CollectPreconditions.a(k7, v7);
        return new RegularImmutableBiMap(new Object[]{k2, v2, k3, v3, k4, v4, k5, v5, k6, v6, k7, v7}, 6);
    }

    public static <K, V> ImmutableBiMap<K, V> of(K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8) {
        CollectPreconditions.a(k2, v2);
        CollectPreconditions.a(k3, v3);
        CollectPreconditions.a(k4, v4);
        CollectPreconditions.a(k5, v5);
        CollectPreconditions.a(k6, v6);
        CollectPreconditions.a(k7, v7);
        CollectPreconditions.a(k8, v8);
        return new RegularImmutableBiMap(new Object[]{k2, v2, k3, v3, k4, v4, k5, v5, k6, v6, k7, v7, k8, v8}, 7);
    }

    public static <K, V> ImmutableBiMap<K, V> of(K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8, K k9, V v9) {
        CollectPreconditions.a(k2, v2);
        CollectPreconditions.a(k3, v3);
        CollectPreconditions.a(k4, v4);
        CollectPreconditions.a(k5, v5);
        CollectPreconditions.a(k6, v6);
        CollectPreconditions.a(k7, v7);
        CollectPreconditions.a(k8, v8);
        CollectPreconditions.a(k9, v9);
        return new RegularImmutableBiMap(new Object[]{k2, v2, k3, v3, k4, v4, k5, v5, k6, v6, k7, v7, k8, v8, k9, v9}, 8);
    }

    public static <K, V> ImmutableBiMap<K, V> of(K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8, K k9, V v9, K k10, V v10) {
        CollectPreconditions.a(k2, v2);
        CollectPreconditions.a(k3, v3);
        CollectPreconditions.a(k4, v4);
        CollectPreconditions.a(k5, v5);
        CollectPreconditions.a(k6, v6);
        CollectPreconditions.a(k7, v7);
        CollectPreconditions.a(k8, v8);
        CollectPreconditions.a(k9, v9);
        CollectPreconditions.a(k10, v10);
        return new RegularImmutableBiMap(new Object[]{k2, v2, k3, v3, k4, v4, k5, v5, k6, v6, k7, v7, k8, v8, k9, v9, k10, v10}, 9);
    }

    public static <K, V> ImmutableBiMap<K, V> of(K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8, K k9, V v9, K k10, V v10, K k11, V v11) {
        CollectPreconditions.a(k2, v2);
        CollectPreconditions.a(k3, v3);
        CollectPreconditions.a(k4, v4);
        CollectPreconditions.a(k5, v5);
        CollectPreconditions.a(k6, v6);
        CollectPreconditions.a(k7, v7);
        CollectPreconditions.a(k8, v8);
        CollectPreconditions.a(k9, v9);
        CollectPreconditions.a(k10, v10);
        CollectPreconditions.a(k11, v11);
        return new RegularImmutableBiMap(new Object[]{k2, v2, k3, v3, k4, v4, k5, v5, k6, v6, k7, v7, k8, v8, k9, v9, k10, v10, k11, v11}, 10);
    }
}
