package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.FilteredEntryMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.annotation.CheckForNull;

@GwtCompatible
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
class FilteredEntryMultimap<K, V> extends AbstractMultimap<K, V> implements FilteredMultimap<K, V> {

    /* renamed from: a, reason: collision with root package name */
    final Multimap f13958a;

    /* renamed from: b, reason: collision with root package name */
    final Predicate f13959b;

    class AsMap extends Maps.ViewCachingAbstractMap<K, Collection<V>> {
        AsMap() {
        }

        @Override // com.google.common.collect.Maps.ViewCachingAbstractMap
        Collection a() {
            return new Maps.Values<K, Collection<V>>() { // from class: com.google.common.collect.FilteredEntryMultimap.AsMap.1ValuesImpl
                @Override // com.google.common.collect.Maps.Values, java.util.AbstractCollection, java.util.Collection
                public boolean remove(@CheckForNull Object obj) {
                    if (!(obj instanceof Collection)) {
                        return false;
                    }
                    Collection collection = (Collection) obj;
                    Iterator<Map.Entry<K, Collection<V>>> it = FilteredEntryMultimap.this.f13958a.asMap().entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry<K, Collection<V>> next = it.next();
                        Collection collectionB = FilteredEntryMultimap.b(next.getValue(), new ValuePredicate(next.getKey()));
                        if (!collectionB.isEmpty() && collection.equals(collectionB)) {
                            if (collectionB.size() == next.getValue().size()) {
                                it.remove();
                                return true;
                            }
                            collectionB.clear();
                            return true;
                        }
                    }
                    return false;
                }

                @Override // com.google.common.collect.Maps.Values, java.util.AbstractCollection, java.util.Collection
                public boolean removeAll(Collection<?> collection) {
                    return FilteredEntryMultimap.this.c(Maps.I(Predicates.in(collection)));
                }

                @Override // com.google.common.collect.Maps.Values, java.util.AbstractCollection, java.util.Collection
                public boolean retainAll(Collection<?> collection) {
                    return FilteredEntryMultimap.this.c(Maps.I(Predicates.not(Predicates.in(collection))));
                }
            };
        }

        @Override // java.util.AbstractMap, java.util.Map
        public void clear() {
            FilteredEntryMultimap.this.clear();
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean containsKey(@CheckForNull Object obj) {
            return get(obj) != null;
        }

        @Override // com.google.common.collect.Maps.ViewCachingAbstractMap
        Set createEntrySet() {
            return new Maps.EntrySet<K, Collection<V>>() { // from class: com.google.common.collect.FilteredEntryMultimap.AsMap.1EntrySetImpl
                @Override // com.google.common.collect.Maps.EntrySet
                Map a() {
                    return AsMap.this;
                }

                @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
                public Iterator<Map.Entry<K, Collection<V>>> iterator() {
                    return new AbstractIterator<Map.Entry<K, Collection<V>>>() { // from class: com.google.common.collect.FilteredEntryMultimap.AsMap.1EntrySetImpl.1

                        /* renamed from: a, reason: collision with root package name */
                        final Iterator f13962a;

                        {
                            this.f13962a = FilteredEntryMultimap.this.f13958a.asMap().entrySet().iterator();
                        }

                        /* JADX INFO: Access modifiers changed from: protected */
                        @Override // com.google.common.collect.AbstractIterator
                        /* renamed from: b, reason: merged with bridge method [inline-methods] */
                        public Map.Entry computeNext() {
                            while (this.f13962a.hasNext()) {
                                Map.Entry entry = (Map.Entry) this.f13962a.next();
                                Object key = entry.getKey();
                                Collection collectionB = FilteredEntryMultimap.b((Collection) entry.getValue(), new ValuePredicate(key));
                                if (!collectionB.isEmpty()) {
                                    return Maps.immutableEntry(key, collectionB);
                                }
                            }
                            return (Map.Entry) a();
                        }
                    };
                }

                @Override // com.google.common.collect.Maps.EntrySet, com.google.common.collect.Sets.ImprovedAbstractSet, java.util.AbstractSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
                public boolean removeAll(Collection<?> collection) {
                    return FilteredEntryMultimap.this.c(Predicates.in(collection));
                }

                @Override // com.google.common.collect.Maps.EntrySet, com.google.common.collect.Sets.ImprovedAbstractSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
                public boolean retainAll(Collection<?> collection) {
                    return FilteredEntryMultimap.this.c(Predicates.not(Predicates.in(collection)));
                }

                @Override // com.google.common.collect.Maps.EntrySet, java.util.AbstractCollection, java.util.Collection, java.util.Set
                public int size() {
                    return Iterators.size(iterator());
                }
            };
        }

        @Override // com.google.common.collect.Maps.ViewCachingAbstractMap
        Set createKeySet() {
            return new Maps.KeySet<K, Collection<V>>() { // from class: com.google.common.collect.FilteredEntryMultimap.AsMap.1KeySetImpl
                @Override // com.google.common.collect.Maps.KeySet, java.util.AbstractCollection, java.util.Collection, java.util.Set
                public boolean remove(@CheckForNull Object obj) {
                    return AsMap.this.remove(obj) != null;
                }

                @Override // com.google.common.collect.Sets.ImprovedAbstractSet, java.util.AbstractSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
                public boolean removeAll(Collection<?> collection) {
                    return FilteredEntryMultimap.this.c(Maps.t(Predicates.in(collection)));
                }

                @Override // com.google.common.collect.Sets.ImprovedAbstractSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
                public boolean retainAll(Collection<?> collection) {
                    return FilteredEntryMultimap.this.c(Maps.t(Predicates.not(Predicates.in(collection))));
                }
            };
        }

        @Override // java.util.AbstractMap, java.util.Map
        @CheckForNull
        public Collection<V> get(@CheckForNull Object obj) {
            Collection<V> collection = FilteredEntryMultimap.this.f13958a.asMap().get(obj);
            if (collection == null) {
                return null;
            }
            Collection<V> collectionB = FilteredEntryMultimap.b(collection, new ValuePredicate(obj));
            if (collectionB.isEmpty()) {
                return null;
            }
            return collectionB;
        }

        @Override // java.util.AbstractMap, java.util.Map
        @CheckForNull
        public Collection<V> remove(@CheckForNull Object obj) {
            Collection<V> collection = FilteredEntryMultimap.this.f13958a.asMap().get(obj);
            if (collection == null) {
                return null;
            }
            ArrayList arrayListNewArrayList = Lists.newArrayList();
            Iterator<V> it = collection.iterator();
            while (it.hasNext()) {
                V next = it.next();
                if (FilteredEntryMultimap.this.satisfies(obj, next)) {
                    it.remove();
                    arrayListNewArrayList.add(next);
                }
            }
            if (arrayListNewArrayList.isEmpty()) {
                return null;
            }
            return FilteredEntryMultimap.this.f13958a instanceof SetMultimap ? Collections.unmodifiableSet(Sets.newLinkedHashSet(arrayListNewArrayList)) : Collections.unmodifiableList(arrayListNewArrayList);
        }
    }

    class Keys extends Multimaps.Keys<K, V> {

        /* renamed from: com.google.common.collect.FilteredEntryMultimap$Keys$1, reason: invalid class name */
        class AnonymousClass1 extends Multisets.EntrySet<K> {
            AnonymousClass1() {
            }

            /* JADX INFO: Access modifiers changed from: private */
            public static /* synthetic */ boolean lambda$removeEntriesIf$0(Predicate predicate, Map.Entry entry) {
                return predicate.apply(Multisets.immutableEntry(entry.getKey(), ((Collection) entry.getValue()).size()));
            }

            private boolean removeEntriesIf(final Predicate<? super Multiset.Entry<K>> predicate) {
                return FilteredEntryMultimap.this.c(new Predicate() { // from class: com.google.common.collect.o2
                    @Override // com.google.common.base.Predicate
                    public final boolean apply(Object obj) {
                        return FilteredEntryMultimap.Keys.AnonymousClass1.lambda$removeEntriesIf$0(predicate, (Map.Entry) obj);
                    }
                });
            }

            @Override // com.google.common.collect.Multisets.EntrySet
            Multiset a() {
                return Keys.this;
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public Iterator<Multiset.Entry<K>> iterator() {
                return Keys.this.entryIterator();
            }

            @Override // com.google.common.collect.Sets.ImprovedAbstractSet, java.util.AbstractSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean removeAll(Collection<?> collection) {
                return removeEntriesIf(Predicates.in(collection));
            }

            @Override // com.google.common.collect.Sets.ImprovedAbstractSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean retainAll(Collection<?> collection) {
                return removeEntriesIf(Predicates.not(Predicates.in(collection)));
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                return FilteredEntryMultimap.this.keySet().size();
            }
        }

        Keys() {
            super(FilteredEntryMultimap.this);
        }

        @Override // com.google.common.collect.AbstractMultiset, com.google.common.collect.Multiset
        public Set<Multiset.Entry<K>> entrySet() {
            return new AnonymousClass1();
        }

        @Override // com.google.common.collect.Multimaps.Keys, com.google.common.collect.AbstractMultiset, com.google.common.collect.Multiset
        public int remove(@CheckForNull Object obj, int i2) {
            CollectPreconditions.b(i2, "occurrences");
            if (i2 == 0) {
                return count(obj);
            }
            Collection<V> collection = FilteredEntryMultimap.this.f13958a.asMap().get(obj);
            int i3 = 0;
            if (collection == null) {
                return 0;
            }
            Iterator<V> it = collection.iterator();
            while (it.hasNext()) {
                if (FilteredEntryMultimap.this.satisfies(obj, it.next()) && (i3 = i3 + 1) <= i2) {
                    it.remove();
                }
            }
            return i3;
        }
    }

    final class ValuePredicate implements Predicate<V> {

        @ParametricNullness
        private final K key;

        /* JADX WARN: Multi-variable type inference failed */
        ValuePredicate(Object obj) {
            this.key = obj;
        }

        @Override // com.google.common.base.Predicate
        public boolean apply(@ParametricNullness V v2) {
            return FilteredEntryMultimap.this.satisfies(this.key, v2);
        }
    }

    FilteredEntryMultimap(Multimap multimap, Predicate predicate) {
        this.f13958a = (Multimap) Preconditions.checkNotNull(multimap);
        this.f13959b = (Predicate) Preconditions.checkNotNull(predicate);
    }

    static Collection b(Collection collection, Predicate predicate) {
        return collection instanceof Set ? Sets.filter((Set) collection, predicate) : Collections2.filter(collection, predicate);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean satisfies(@ParametricNullness K k2, @ParametricNullness V v2) {
        return this.f13959b.apply(Maps.immutableEntry(k2, v2));
    }

    boolean c(Predicate predicate) {
        Iterator<Map.Entry<K, Collection<V>>> it = this.f13958a.asMap().entrySet().iterator();
        boolean z2 = false;
        while (it.hasNext()) {
            Map.Entry<K, Collection<V>> next = it.next();
            K key = next.getKey();
            Collection collectionB = b(next.getValue(), new ValuePredicate(key));
            if (!collectionB.isEmpty() && predicate.apply(Maps.immutableEntry(key, collectionB))) {
                if (collectionB.size() == next.getValue().size()) {
                    it.remove();
                } else {
                    collectionB.clear();
                }
                z2 = true;
            }
        }
        return z2;
    }

    @Override // com.google.common.collect.Multimap
    public void clear() {
        entries().clear();
    }

    @Override // com.google.common.collect.Multimap
    public boolean containsKey(@CheckForNull Object obj) {
        return asMap().get(obj) != null;
    }

    @Override // com.google.common.collect.AbstractMultimap
    Map createAsMap() {
        return new AsMap();
    }

    @Override // com.google.common.collect.AbstractMultimap
    Collection createEntries() {
        return b(this.f13958a.entries(), this.f13959b);
    }

    @Override // com.google.common.collect.AbstractMultimap
    Set createKeySet() {
        return asMap().keySet();
    }

    @Override // com.google.common.collect.AbstractMultimap
    Multiset createKeys() {
        return new Keys();
    }

    @Override // com.google.common.collect.AbstractMultimap
    Collection createValues() {
        return new FilteredMultimapValues(this);
    }

    Collection d() {
        return this.f13958a instanceof SetMultimap ? Collections.emptySet() : Collections.emptyList();
    }

    @Override // com.google.common.collect.AbstractMultimap
    Iterator entryIterator() {
        throw new AssertionError("should never be called");
    }

    @Override // com.google.common.collect.FilteredMultimap
    public Predicate<? super Map.Entry<K, V>> entryPredicate() {
        return this.f13959b;
    }

    @Override // com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    public Collection<V> get(@ParametricNullness K k2) {
        return b(this.f13958a.get(k2), new ValuePredicate(k2));
    }

    @Override // com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    public Collection<V> removeAll(@CheckForNull Object obj) {
        return (Collection) MoreObjects.firstNonNull(asMap().remove(obj), d());
    }

    @Override // com.google.common.collect.Multimap
    public int size() {
        return entries().size();
    }

    @Override // com.google.common.collect.FilteredMultimap
    public Multimap<K, V> unfiltered() {
        return this.f13958a;
    }
}
