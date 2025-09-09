package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.CheckForNull;

@GwtCompatible
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
class FilteredKeyMultimap<K, V> extends AbstractMultimap<K, V> implements FilteredMultimap<K, V> {

    /* renamed from: a, reason: collision with root package name */
    final Multimap f13969a;

    /* renamed from: b, reason: collision with root package name */
    final Predicate f13970b;

    static class AddRejectingList<K, V> extends ForwardingList<V> {

        /* renamed from: a, reason: collision with root package name */
        final Object f13971a;

        AddRejectingList(Object obj) {
            this.f13971a = obj;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.common.collect.ForwardingList, com.google.common.collect.ForwardingCollection, com.google.common.collect.ForwardingObject
        /* renamed from: a */
        public List delegate() {
            return Collections.emptyList();
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.util.Queue
        public boolean add(@ParametricNullness V v2) {
            add(0, v2);
            return true;
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection
        public boolean addAll(Collection<? extends V> collection) {
            addAll(0, collection);
            return true;
        }

        @Override // com.google.common.collect.ForwardingList, java.util.List
        public void add(int i2, @ParametricNullness V v2) {
            Preconditions.checkPositionIndex(i2, 0);
            throw new IllegalArgumentException("Key does not satisfy predicate: " + this.f13971a);
        }

        @Override // com.google.common.collect.ForwardingList, java.util.List
        @CanIgnoreReturnValue
        public boolean addAll(int i2, Collection<? extends V> collection) {
            Preconditions.checkNotNull(collection);
            Preconditions.checkPositionIndex(i2, 0);
            throw new IllegalArgumentException("Key does not satisfy predicate: " + this.f13971a);
        }
    }

    class Entries extends ForwardingCollection<Map.Entry<K, V>> {
        Entries() {
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.util.Set
        public boolean remove(@CheckForNull Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            if (FilteredKeyMultimap.this.f13969a.containsKey(entry.getKey()) && FilteredKeyMultimap.this.f13970b.apply(entry.getKey())) {
                return FilteredKeyMultimap.this.f13969a.remove(entry.getKey(), entry.getValue());
            }
            return false;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.common.collect.ForwardingCollection, com.google.common.collect.ForwardingObject
        public Collection delegate() {
            return Collections2.filter(FilteredKeyMultimap.this.f13969a.entries(), FilteredKeyMultimap.this.entryPredicate());
        }
    }

    FilteredKeyMultimap(Multimap multimap, Predicate predicate) {
        this.f13969a = (Multimap) Preconditions.checkNotNull(multimap);
        this.f13970b = (Predicate) Preconditions.checkNotNull(predicate);
    }

    Collection a() {
        return this.f13969a instanceof SetMultimap ? Collections.emptySet() : Collections.emptyList();
    }

    @Override // com.google.common.collect.Multimap
    public void clear() {
        keySet().clear();
    }

    @Override // com.google.common.collect.Multimap
    public boolean containsKey(@CheckForNull Object obj) {
        if (this.f13969a.containsKey(obj)) {
            return this.f13970b.apply(obj);
        }
        return false;
    }

    @Override // com.google.common.collect.AbstractMultimap
    Map createAsMap() {
        return Maps.filterKeys(this.f13969a.asMap(), this.f13970b);
    }

    @Override // com.google.common.collect.AbstractMultimap
    Collection createEntries() {
        return new Entries();
    }

    @Override // com.google.common.collect.AbstractMultimap
    Set createKeySet() {
        return Sets.filter(this.f13969a.keySet(), this.f13970b);
    }

    @Override // com.google.common.collect.AbstractMultimap
    Multiset createKeys() {
        return Multisets.filter(this.f13969a.keys(), this.f13970b);
    }

    @Override // com.google.common.collect.AbstractMultimap
    Collection createValues() {
        return new FilteredMultimapValues(this);
    }

    @Override // com.google.common.collect.AbstractMultimap
    Iterator entryIterator() {
        throw new AssertionError("should never be called");
    }

    @Override // com.google.common.collect.FilteredMultimap
    public Predicate<? super Map.Entry<K, V>> entryPredicate() {
        return Maps.t(this.f13970b);
    }

    @Override // com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    public Collection<V> get(@ParametricNullness K k2) {
        return this.f13970b.apply(k2) ? this.f13969a.get(k2) : this.f13969a instanceof SetMultimap ? new AddRejectingSet(k2) : new AddRejectingList(k2);
    }

    @Override // com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    public Collection<V> removeAll(@CheckForNull Object obj) {
        return containsKey(obj) ? this.f13969a.removeAll(obj) : a();
    }

    @Override // com.google.common.collect.Multimap
    public int size() {
        Iterator<Collection<V>> it = asMap().values().iterator();
        int size = 0;
        while (it.hasNext()) {
            size += it.next().size();
        }
        return size;
    }

    public Multimap<K, V> unfiltered() {
        return this.f13969a;
    }

    static class AddRejectingSet<K, V> extends ForwardingSet<V> {

        /* renamed from: a, reason: collision with root package name */
        final Object f13972a;

        AddRejectingSet(Object obj) {
            this.f13972a = obj;
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.util.Queue
        public boolean add(@ParametricNullness V v2) {
            throw new IllegalArgumentException("Key does not satisfy predicate: " + this.f13972a);
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection
        public boolean addAll(Collection<? extends V> collection) {
            Preconditions.checkNotNull(collection);
            throw new IllegalArgumentException("Key does not satisfy predicate: " + this.f13972a);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.common.collect.ForwardingSet, com.google.common.collect.ForwardingCollection, com.google.common.collect.ForwardingObject
        public Set delegate() {
            return Collections.emptySet();
        }
    }
}
