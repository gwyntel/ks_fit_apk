package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Objects;
import com.google.common.collect.Maps;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.annotation.CheckForNull;

@GwtCompatible
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public abstract class ForwardingMap<K, V> extends ForwardingObject implements Map<K, V> {

    protected abstract class StandardEntrySet extends Maps.EntrySet<K, V> {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ ForwardingMap f13979a;

        @Override // com.google.common.collect.Maps.EntrySet
        Map a() {
            return this.f13979a;
        }
    }

    protected class StandardKeySet extends Maps.KeySet<K, V> {
        public StandardKeySet() {
            super(ForwardingMap.this);
        }
    }

    protected class StandardValues extends Maps.Values<K, V> {
        public StandardValues() {
            super(ForwardingMap.this);
        }
    }

    protected ForwardingMap() {
    }

    public void clear() {
        delegate().clear();
    }

    public boolean containsKey(@CheckForNull Object obj) {
        return delegate().containsKey(obj);
    }

    public boolean containsValue(@CheckForNull Object obj) {
        return delegate().containsValue(obj);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.common.collect.ForwardingObject
    public abstract Map delegate();

    public Set<Map.Entry<K, V>> entrySet() {
        return delegate().entrySet();
    }

    public boolean equals(@CheckForNull Object obj) {
        return obj == this || delegate().equals(obj);
    }

    @CheckForNull
    public V get(@CheckForNull Object obj) {
        return (V) delegate().get(obj);
    }

    public int hashCode() {
        return delegate().hashCode();
    }

    public boolean isEmpty() {
        return delegate().isEmpty();
    }

    public Set<K> keySet() {
        return delegate().keySet();
    }

    @CanIgnoreReturnValue
    @CheckForNull
    public V put(@ParametricNullness K k2, @ParametricNullness V v2) {
        return (V) delegate().put(k2, v2);
    }

    public void putAll(Map<? extends K, ? extends V> map) {
        delegate().putAll(map);
    }

    @CanIgnoreReturnValue
    @CheckForNull
    public V remove(@CheckForNull Object obj) {
        return (V) delegate().remove(obj);
    }

    public int size() {
        return delegate().size();
    }

    protected void standardClear() {
        Iterators.b(entrySet().iterator());
    }

    protected boolean standardContainsKey(@CheckForNull Object obj) {
        return Maps.m(this, obj);
    }

    protected boolean standardContainsValue(@CheckForNull Object obj) {
        return Maps.n(this, obj);
    }

    protected boolean standardEquals(@CheckForNull Object obj) {
        return Maps.o(this, obj);
    }

    protected int standardHashCode() {
        return Sets.b(entrySet());
    }

    protected boolean standardIsEmpty() {
        return !entrySet().iterator().hasNext();
    }

    protected void standardPutAll(Map<? extends K, ? extends V> map) {
        Maps.v(this, map);
    }

    @CheckForNull
    protected V standardRemove(@CheckForNull Object obj) {
        Iterator<Map.Entry<K, V>> it = entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<K, V> next = it.next();
            if (Objects.equal(next.getKey(), obj)) {
                V value = next.getValue();
                it.remove();
                return value;
            }
        }
        return null;
    }

    protected String standardToString() {
        return Maps.A(this);
    }

    public Collection<V> values() {
        return delegate().values();
    }
}
