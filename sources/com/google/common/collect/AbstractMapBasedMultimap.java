package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimaps;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Objects;
import java.util.RandomAccess;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import javax.annotation.CheckForNull;

@GwtCompatible
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
abstract class AbstractMapBasedMultimap<K, V> extends AbstractMultimap<K, V> implements Serializable {
    private static final long serialVersionUID = 2447537837011683357L;
    private transient Map<K, Collection<V>> map;
    private transient int totalSize;

    private class AsMap extends Maps.ViewCachingAbstractMap<K, Collection<V>> {

        /* renamed from: a, reason: collision with root package name */
        final transient Map f13845a;

        class AsMapEntries extends Maps.EntrySet<K, Collection<V>> {
            AsMapEntries() {
            }

            @Override // com.google.common.collect.Maps.EntrySet
            Map a() {
                return AsMap.this;
            }

            @Override // com.google.common.collect.Maps.EntrySet, java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean contains(@CheckForNull Object obj) {
                return Collections2.d(AsMap.this.f13845a.entrySet(), obj);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public Iterator<Map.Entry<K, Collection<V>>> iterator() {
                return AsMap.this.new AsMapIterator();
            }

            @Override // com.google.common.collect.Maps.EntrySet, java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean remove(@CheckForNull Object obj) {
                if (!contains(obj)) {
                    return false;
                }
                Map.Entry entry = (Map.Entry) obj;
                Objects.requireNonNull(entry);
                AbstractMapBasedMultimap.this.removeValuesForKey(entry.getKey());
                return true;
            }
        }

        class AsMapIterator implements Iterator<Map.Entry<K, Collection<V>>> {

            /* renamed from: a, reason: collision with root package name */
            final Iterator f13848a;

            /* renamed from: b, reason: collision with root package name */
            Collection f13849b;

            AsMapIterator() {
                this.f13848a = AsMap.this.f13845a.entrySet().iterator();
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.f13848a.hasNext();
            }

            @Override // java.util.Iterator
            public void remove() {
                Preconditions.checkState(this.f13849b != null, "no calls to next() since the last call to remove()");
                this.f13848a.remove();
                AbstractMapBasedMultimap.access$220(AbstractMapBasedMultimap.this, this.f13849b.size());
                this.f13849b.clear();
                this.f13849b = null;
            }

            @Override // java.util.Iterator
            public Map.Entry<K, Collection<V>> next() {
                Map.Entry entry = (Map.Entry) this.f13848a.next();
                this.f13849b = (Collection) entry.getValue();
                return AsMap.this.b(entry);
            }
        }

        AsMap(Map map) {
            this.f13845a = map;
        }

        /* JADX WARN: Multi-variable type inference failed */
        Map.Entry b(Map.Entry entry) {
            Object key = entry.getKey();
            return Maps.immutableEntry(key, AbstractMapBasedMultimap.this.wrapCollection(key, (Collection) entry.getValue()));
        }

        @Override // java.util.AbstractMap, java.util.Map
        public void clear() {
            if (this.f13845a == AbstractMapBasedMultimap.this.map) {
                AbstractMapBasedMultimap.this.clear();
            } else {
                Iterators.b(new AsMapIterator());
            }
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean containsKey(@CheckForNull Object obj) {
            return Maps.x(this.f13845a, obj);
        }

        @Override // com.google.common.collect.Maps.ViewCachingAbstractMap
        protected Set createEntrySet() {
            return new AsMapEntries();
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean equals(@CheckForNull Object obj) {
            return this == obj || this.f13845a.equals(obj);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public int hashCode() {
            return this.f13845a.hashCode();
        }

        @Override // com.google.common.collect.Maps.ViewCachingAbstractMap, java.util.AbstractMap, java.util.Map
        public Set<K> keySet() {
            return AbstractMapBasedMultimap.this.keySet();
        }

        @Override // java.util.AbstractMap, java.util.Map
        public int size() {
            return this.f13845a.size();
        }

        @Override // java.util.AbstractMap
        public String toString() {
            return this.f13845a.toString();
        }

        @Override // java.util.AbstractMap, java.util.Map
        @CheckForNull
        public Collection<V> get(@CheckForNull Object obj) {
            Collection<V> collection = (Collection) Maps.y(this.f13845a, obj);
            if (collection == null) {
                return null;
            }
            return AbstractMapBasedMultimap.this.wrapCollection(obj, collection);
        }

        @Override // java.util.AbstractMap, java.util.Map
        @CheckForNull
        public Collection<V> remove(@CheckForNull Object obj) {
            Collection<? extends V> collection = (Collection) this.f13845a.remove(obj);
            if (collection == null) {
                return null;
            }
            Collection<V> collectionCreateCollection = AbstractMapBasedMultimap.this.createCollection();
            collectionCreateCollection.addAll(collection);
            AbstractMapBasedMultimap.access$220(AbstractMapBasedMultimap.this, collection.size());
            collection.clear();
            return collectionCreateCollection;
        }
    }

    private abstract class Itr<T> implements Iterator<T> {

        /* renamed from: a, reason: collision with root package name */
        final Iterator f13851a;

        /* renamed from: b, reason: collision with root package name */
        Object f13852b = null;

        /* renamed from: c, reason: collision with root package name */
        Collection f13853c = null;

        /* renamed from: d, reason: collision with root package name */
        Iterator f13854d = Iterators.f();

        Itr() {
            this.f13851a = AbstractMapBasedMultimap.this.map.entrySet().iterator();
        }

        abstract Object a(Object obj, Object obj2);

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.f13851a.hasNext() || this.f13854d.hasNext();
        }

        @Override // java.util.Iterator
        @ParametricNullness
        public T next() {
            if (!this.f13854d.hasNext()) {
                Map.Entry entry = (Map.Entry) this.f13851a.next();
                this.f13852b = entry.getKey();
                Collection collection = (Collection) entry.getValue();
                this.f13853c = collection;
                this.f13854d = collection.iterator();
            }
            return (T) a(NullnessCasts.a(this.f13852b), this.f13854d.next());
        }

        @Override // java.util.Iterator
        public void remove() {
            this.f13854d.remove();
            Collection collection = this.f13853c;
            Objects.requireNonNull(collection);
            if (collection.isEmpty()) {
                this.f13851a.remove();
            }
            AbstractMapBasedMultimap.access$210(AbstractMapBasedMultimap.this);
        }
    }

    private class KeySet extends Maps.KeySet<K, Collection<V>> {
        KeySet(Map map) {
            super(map);
        }

        @Override // com.google.common.collect.Maps.KeySet, java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            Iterators.b(iterator());
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean containsAll(Collection<?> collection) {
            return b().keySet().containsAll(collection);
        }

        @Override // java.util.AbstractSet, java.util.Collection, java.util.Set
        public boolean equals(@CheckForNull Object obj) {
            return this == obj || b().keySet().equals(obj);
        }

        @Override // java.util.AbstractSet, java.util.Collection, java.util.Set
        public int hashCode() {
            return b().keySet().hashCode();
        }

        @Override // com.google.common.collect.Maps.KeySet, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<K> iterator() {
            final Iterator<Map.Entry<K, V>> it = b().entrySet().iterator();
            return new Iterator<K>(this) { // from class: com.google.common.collect.AbstractMapBasedMultimap.KeySet.1

                /* renamed from: a, reason: collision with root package name */
                Map.Entry f13857a;

                /* renamed from: c, reason: collision with root package name */
                final /* synthetic */ KeySet f13859c;

                {
                    this.f13859c = this;
                }

                @Override // java.util.Iterator
                public boolean hasNext() {
                    return it.hasNext();
                }

                @Override // java.util.Iterator
                @ParametricNullness
                public K next() {
                    Map.Entry entry = (Map.Entry) it.next();
                    this.f13857a = entry;
                    return (K) entry.getKey();
                }

                @Override // java.util.Iterator
                public void remove() {
                    Preconditions.checkState(this.f13857a != null, "no calls to next() since the last call to remove()");
                    Collection collection = (Collection) this.f13857a.getValue();
                    it.remove();
                    AbstractMapBasedMultimap.access$220(AbstractMapBasedMultimap.this, collection.size());
                    collection.clear();
                    this.f13857a = null;
                }
            };
        }

        @Override // com.google.common.collect.Maps.KeySet, java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(@CheckForNull Object obj) {
            int size;
            Collection collection = (Collection) b().remove(obj);
            if (collection != null) {
                size = collection.size();
                collection.clear();
                AbstractMapBasedMultimap.access$220(AbstractMapBasedMultimap.this, size);
            } else {
                size = 0;
            }
            return size > 0;
        }
    }

    private final class NavigableAsMap extends AbstractMapBasedMultimap<K, V>.SortedAsMap implements NavigableMap<K, Collection<V>> {
        NavigableAsMap(NavigableMap navigableMap) {
            super(navigableMap);
        }

        @Override // java.util.NavigableMap
        @CheckForNull
        public Map.Entry<K, Collection<V>> ceilingEntry(@ParametricNullness K k2) {
            Map.Entry<K, V> entryCeilingEntry = d().ceilingEntry(k2);
            if (entryCeilingEntry == null) {
                return null;
            }
            return b(entryCeilingEntry);
        }

        @Override // java.util.NavigableMap
        @CheckForNull
        public K ceilingKey(@ParametricNullness K k2) {
            return (K) d().ceilingKey(k2);
        }

        @Override // java.util.NavigableMap
        public NavigableSet<K> descendingKeySet() {
            return descendingMap().navigableKeySet();
        }

        @Override // java.util.NavigableMap
        public NavigableMap<K, Collection<V>> descendingMap() {
            return new NavigableAsMap(d().descendingMap());
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.collect.AbstractMapBasedMultimap.SortedAsMap, com.google.common.collect.Maps.ViewCachingAbstractMap
        /* renamed from: e, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
        public NavigableSet createKeySet() {
            return new NavigableKeySet(d());
        }

        Map.Entry f(Iterator it) {
            if (!it.hasNext()) {
                return null;
            }
            Map.Entry entry = (Map.Entry) it.next();
            Collection<V> collectionCreateCollection = AbstractMapBasedMultimap.this.createCollection();
            collectionCreateCollection.addAll((Collection) entry.getValue());
            it.remove();
            return Maps.immutableEntry(entry.getKey(), AbstractMapBasedMultimap.this.unmodifiableCollectionSubclass(collectionCreateCollection));
        }

        @Override // java.util.NavigableMap
        @CheckForNull
        public Map.Entry<K, Collection<V>> firstEntry() {
            Map.Entry<K, V> entryFirstEntry = d().firstEntry();
            if (entryFirstEntry == null) {
                return null;
            }
            return b(entryFirstEntry);
        }

        @Override // java.util.NavigableMap
        @CheckForNull
        public Map.Entry<K, Collection<V>> floorEntry(@ParametricNullness K k2) {
            Map.Entry<K, V> entryFloorEntry = d().floorEntry(k2);
            if (entryFloorEntry == null) {
                return null;
            }
            return b(entryFloorEntry);
        }

        @Override // java.util.NavigableMap
        @CheckForNull
        public K floorKey(@ParametricNullness K k2) {
            return (K) d().floorKey(k2);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.collect.AbstractMapBasedMultimap.SortedAsMap
        /* renamed from: g, reason: merged with bridge method [inline-methods] */
        public NavigableMap d() {
            return (NavigableMap) super.d();
        }

        @Override // com.google.common.collect.AbstractMapBasedMultimap.SortedAsMap, java.util.SortedMap, java.util.NavigableMap
        public /* bridge */ /* synthetic */ SortedMap headMap(@ParametricNullness Object obj) {
            return headMap((NavigableAsMap) obj);
        }

        @Override // java.util.NavigableMap
        @CheckForNull
        public Map.Entry<K, Collection<V>> higherEntry(@ParametricNullness K k2) {
            Map.Entry<K, V> entryHigherEntry = d().higherEntry(k2);
            if (entryHigherEntry == null) {
                return null;
            }
            return b(entryHigherEntry);
        }

        @Override // java.util.NavigableMap
        @CheckForNull
        public K higherKey(@ParametricNullness K k2) {
            return (K) d().higherKey(k2);
        }

        @Override // java.util.NavigableMap
        @CheckForNull
        public Map.Entry<K, Collection<V>> lastEntry() {
            Map.Entry<K, V> entryLastEntry = d().lastEntry();
            if (entryLastEntry == null) {
                return null;
            }
            return b(entryLastEntry);
        }

        @Override // java.util.NavigableMap
        @CheckForNull
        public Map.Entry<K, Collection<V>> lowerEntry(@ParametricNullness K k2) {
            Map.Entry<K, V> entryLowerEntry = d().lowerEntry(k2);
            if (entryLowerEntry == null) {
                return null;
            }
            return b(entryLowerEntry);
        }

        @Override // java.util.NavigableMap
        @CheckForNull
        public K lowerKey(@ParametricNullness K k2) {
            return (K) d().lowerKey(k2);
        }

        @Override // java.util.NavigableMap
        public NavigableSet<K> navigableKeySet() {
            return keySet();
        }

        @Override // java.util.NavigableMap
        @CheckForNull
        public Map.Entry<K, Collection<V>> pollFirstEntry() {
            return f(entrySet().iterator());
        }

        @Override // java.util.NavigableMap
        @CheckForNull
        public Map.Entry<K, Collection<V>> pollLastEntry() {
            return f(descendingMap().entrySet().iterator());
        }

        @Override // com.google.common.collect.AbstractMapBasedMultimap.SortedAsMap, java.util.SortedMap, java.util.NavigableMap
        public /* bridge */ /* synthetic */ SortedMap tailMap(@ParametricNullness Object obj) {
            return tailMap((NavigableAsMap) obj);
        }

        @Override // com.google.common.collect.AbstractMapBasedMultimap.SortedAsMap, java.util.SortedMap, java.util.NavigableMap
        public NavigableMap<K, Collection<V>> headMap(@ParametricNullness K k2) {
            return headMap(k2, false);
        }

        @Override // com.google.common.collect.AbstractMapBasedMultimap.SortedAsMap, java.util.SortedMap, java.util.NavigableMap
        public NavigableMap<K, Collection<V>> subMap(@ParametricNullness K k2, @ParametricNullness K k3) {
            return subMap(k2, true, k3, false);
        }

        @Override // com.google.common.collect.AbstractMapBasedMultimap.SortedAsMap, java.util.SortedMap, java.util.NavigableMap
        public NavigableMap<K, Collection<V>> tailMap(@ParametricNullness K k2) {
            return tailMap(k2, true);
        }

        @Override // java.util.NavigableMap
        public NavigableMap<K, Collection<V>> headMap(@ParametricNullness K k2, boolean z2) {
            return new NavigableAsMap(d().headMap(k2, z2));
        }

        @Override // com.google.common.collect.AbstractMapBasedMultimap.SortedAsMap, com.google.common.collect.AbstractMapBasedMultimap.AsMap, com.google.common.collect.Maps.ViewCachingAbstractMap, java.util.AbstractMap, java.util.Map
        public NavigableSet<K> keySet() {
            return (NavigableSet) super.keySet();
        }

        @Override // java.util.NavigableMap
        public NavigableMap<K, Collection<V>> subMap(@ParametricNullness K k2, boolean z2, @ParametricNullness K k3, boolean z3) {
            return new NavigableAsMap(d().subMap(k2, z2, k3, z3));
        }

        @Override // java.util.NavigableMap
        public NavigableMap<K, Collection<V>> tailMap(@ParametricNullness K k2, boolean z2) {
            return new NavigableAsMap(d().tailMap(k2, z2));
        }
    }

    private final class NavigableKeySet extends AbstractMapBasedMultimap<K, V>.SortedKeySet implements NavigableSet<K> {
        NavigableKeySet(NavigableMap navigableMap) {
            super(navigableMap);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.collect.AbstractMapBasedMultimap.SortedKeySet
        /* renamed from: c, reason: merged with bridge method [inline-methods] */
        public NavigableMap b() {
            return (NavigableMap) super.b();
        }

        @Override // java.util.NavigableSet
        @CheckForNull
        public K ceiling(@ParametricNullness K k2) {
            return (K) b().ceilingKey(k2);
        }

        @Override // java.util.NavigableSet
        public Iterator<K> descendingIterator() {
            return descendingSet().iterator();
        }

        @Override // java.util.NavigableSet
        public NavigableSet<K> descendingSet() {
            return new NavigableKeySet(b().descendingMap());
        }

        @Override // java.util.NavigableSet
        @CheckForNull
        public K floor(@ParametricNullness K k2) {
            return (K) b().floorKey(k2);
        }

        @Override // com.google.common.collect.AbstractMapBasedMultimap.SortedKeySet, java.util.SortedSet, java.util.NavigableSet
        public /* bridge */ /* synthetic */ SortedSet headSet(@ParametricNullness Object obj) {
            return headSet((NavigableKeySet) obj);
        }

        @Override // java.util.NavigableSet
        @CheckForNull
        public K higher(@ParametricNullness K k2) {
            return (K) b().higherKey(k2);
        }

        @Override // java.util.NavigableSet
        @CheckForNull
        public K lower(@ParametricNullness K k2) {
            return (K) b().lowerKey(k2);
        }

        @Override // java.util.NavigableSet
        @CheckForNull
        public K pollFirst() {
            return (K) Iterators.h(iterator());
        }

        @Override // java.util.NavigableSet
        @CheckForNull
        public K pollLast() {
            return (K) Iterators.h(descendingIterator());
        }

        @Override // com.google.common.collect.AbstractMapBasedMultimap.SortedKeySet, java.util.SortedSet, java.util.NavigableSet
        public /* bridge */ /* synthetic */ SortedSet tailSet(@ParametricNullness Object obj) {
            return tailSet((NavigableKeySet) obj);
        }

        @Override // com.google.common.collect.AbstractMapBasedMultimap.SortedKeySet, java.util.SortedSet, java.util.NavigableSet
        public NavigableSet<K> headSet(@ParametricNullness K k2) {
            return headSet(k2, false);
        }

        @Override // com.google.common.collect.AbstractMapBasedMultimap.SortedKeySet, java.util.SortedSet, java.util.NavigableSet
        public NavigableSet<K> subSet(@ParametricNullness K k2, @ParametricNullness K k3) {
            return subSet(k2, true, k3, false);
        }

        @Override // com.google.common.collect.AbstractMapBasedMultimap.SortedKeySet, java.util.SortedSet, java.util.NavigableSet
        public NavigableSet<K> tailSet(@ParametricNullness K k2) {
            return tailSet(k2, true);
        }

        @Override // java.util.NavigableSet
        public NavigableSet<K> headSet(@ParametricNullness K k2, boolean z2) {
            return new NavigableKeySet(b().headMap(k2, z2));
        }

        @Override // java.util.NavigableSet
        public NavigableSet<K> subSet(@ParametricNullness K k2, boolean z2, @ParametricNullness K k3, boolean z3) {
            return new NavigableKeySet(b().subMap(k2, z2, k3, z3));
        }

        @Override // java.util.NavigableSet
        public NavigableSet<K> tailSet(@ParametricNullness K k2, boolean z2) {
            return new NavigableKeySet(b().tailMap(k2, z2));
        }
    }

    private class RandomAccessWrappedList extends AbstractMapBasedMultimap<K, V>.WrappedList implements RandomAccess {
        RandomAccessWrappedList(Object obj, List list, WrappedCollection wrappedCollection) {
            super(obj, list, wrappedCollection);
        }
    }

    private class SortedAsMap extends AbstractMapBasedMultimap<K, V>.AsMap implements SortedMap<K, Collection<V>> {

        /* renamed from: c, reason: collision with root package name */
        SortedSet f13863c;

        SortedAsMap(SortedMap sortedMap) {
            super(sortedMap);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.collect.Maps.ViewCachingAbstractMap
        /* renamed from: c */
        public SortedSet createKeySet() {
            return new SortedKeySet(d());
        }

        @Override // java.util.SortedMap
        @CheckForNull
        public Comparator<? super K> comparator() {
            return d().comparator();
        }

        SortedMap d() {
            return (SortedMap) this.f13845a;
        }

        @Override // java.util.SortedMap
        @ParametricNullness
        public K firstKey() {
            return (K) d().firstKey();
        }

        public SortedMap<K, Collection<V>> headMap(@ParametricNullness K k2) {
            return new SortedAsMap(d().headMap(k2));
        }

        @Override // java.util.SortedMap
        @ParametricNullness
        public K lastKey() {
            return (K) d().lastKey();
        }

        public SortedMap<K, Collection<V>> subMap(@ParametricNullness K k2, @ParametricNullness K k3) {
            return new SortedAsMap(d().subMap(k2, k3));
        }

        public SortedMap<K, Collection<V>> tailMap(@ParametricNullness K k2) {
            return new SortedAsMap(d().tailMap(k2));
        }

        @Override // com.google.common.collect.AbstractMapBasedMultimap.AsMap, com.google.common.collect.Maps.ViewCachingAbstractMap, java.util.AbstractMap, java.util.Map
        public SortedSet<K> keySet() {
            SortedSet<K> sortedSet = this.f13863c;
            if (sortedSet != null) {
                return sortedSet;
            }
            SortedSet<K> sortedSetCreateKeySet = createKeySet();
            this.f13863c = sortedSetCreateKeySet;
            return sortedSetCreateKeySet;
        }
    }

    private class SortedKeySet extends AbstractMapBasedMultimap<K, V>.KeySet implements SortedSet<K> {
        SortedKeySet(SortedMap sortedMap) {
            super(sortedMap);
        }

        SortedMap b() {
            return (SortedMap) super.b();
        }

        @Override // java.util.SortedSet
        @CheckForNull
        public Comparator<? super K> comparator() {
            return b().comparator();
        }

        @Override // java.util.SortedSet
        @ParametricNullness
        public K first() {
            return (K) b().firstKey();
        }

        public SortedSet<K> headSet(@ParametricNullness K k2) {
            return new SortedKeySet(b().headMap(k2));
        }

        @Override // java.util.SortedSet
        @ParametricNullness
        public K last() {
            return (K) b().lastKey();
        }

        public SortedSet<K> subSet(@ParametricNullness K k2, @ParametricNullness K k3) {
            return new SortedKeySet(b().subMap(k2, k3));
        }

        public SortedSet<K> tailSet(@ParametricNullness K k2) {
            return new SortedKeySet(b().tailMap(k2));
        }
    }

    class WrappedNavigableSet extends AbstractMapBasedMultimap<K, V>.WrappedSortedSet implements NavigableSet<V> {
        WrappedNavigableSet(Object obj, NavigableSet navigableSet, WrappedCollection wrappedCollection) {
            super(obj, navigableSet, wrappedCollection);
        }

        private NavigableSet<V> wrap(NavigableSet<V> navigableSet) {
            return new WrappedNavigableSet(this.f13866a, navigableSet, b() == null ? this : b());
        }

        @Override // java.util.NavigableSet
        @CheckForNull
        public V ceiling(@ParametricNullness V v2) {
            return (V) g().ceiling(v2);
        }

        @Override // java.util.NavigableSet
        public Iterator<V> descendingIterator() {
            return new WrappedCollection.WrappedIterator(g().descendingIterator());
        }

        @Override // java.util.NavigableSet
        public NavigableSet<V> descendingSet() {
            return wrap(g().descendingSet());
        }

        @Override // java.util.NavigableSet
        @CheckForNull
        public V floor(@ParametricNullness V v2) {
            return (V) g().floor(v2);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.collect.AbstractMapBasedMultimap.WrappedSortedSet
        /* renamed from: h, reason: merged with bridge method [inline-methods] */
        public NavigableSet g() {
            return (NavigableSet) super.g();
        }

        @Override // java.util.NavigableSet
        public NavigableSet<V> headSet(@ParametricNullness V v2, boolean z2) {
            return wrap(g().headSet(v2, z2));
        }

        @Override // java.util.NavigableSet
        @CheckForNull
        public V higher(@ParametricNullness V v2) {
            return (V) g().higher(v2);
        }

        @Override // java.util.NavigableSet
        @CheckForNull
        public V lower(@ParametricNullness V v2) {
            return (V) g().lower(v2);
        }

        @Override // java.util.NavigableSet
        @CheckForNull
        public V pollFirst() {
            return (V) Iterators.h(iterator());
        }

        @Override // java.util.NavigableSet
        @CheckForNull
        public V pollLast() {
            return (V) Iterators.h(descendingIterator());
        }

        @Override // java.util.NavigableSet
        public NavigableSet<V> subSet(@ParametricNullness V v2, boolean z2, @ParametricNullness V v3, boolean z3) {
            return wrap(g().subSet(v2, z2, v3, z3));
        }

        @Override // java.util.NavigableSet
        public NavigableSet<V> tailSet(@ParametricNullness V v2, boolean z2) {
            return wrap(g().tailSet(v2, z2));
        }
    }

    class WrappedSet extends AbstractMapBasedMultimap<K, V>.WrappedCollection implements Set<V> {
        WrappedSet(Object obj, Set set) {
            super(obj, set, null);
        }

        @Override // com.google.common.collect.AbstractMapBasedMultimap.WrappedCollection, java.util.AbstractCollection, java.util.Collection
        public boolean removeAll(Collection<?> collection) {
            if (collection.isEmpty()) {
                return false;
            }
            int size = size();
            boolean zC = Sets.c((Set) this.f13867b, collection);
            if (zC) {
                AbstractMapBasedMultimap.access$212(AbstractMapBasedMultimap.this, this.f13867b.size() - size);
                f();
            }
            return zC;
        }
    }

    class WrappedSortedSet extends AbstractMapBasedMultimap<K, V>.WrappedCollection implements SortedSet<V> {
        WrappedSortedSet(Object obj, SortedSet sortedSet, WrappedCollection wrappedCollection) {
            super(obj, sortedSet, wrappedCollection);
        }

        @Override // java.util.SortedSet
        @CheckForNull
        public Comparator<? super V> comparator() {
            return g().comparator();
        }

        @Override // java.util.SortedSet
        @ParametricNullness
        public V first() {
            e();
            return (V) g().first();
        }

        SortedSet g() {
            return (SortedSet) c();
        }

        @Override // java.util.SortedSet
        public SortedSet<V> headSet(@ParametricNullness V v2) {
            e();
            return new WrappedSortedSet(d(), g().headSet(v2), b() == null ? this : b());
        }

        @Override // java.util.SortedSet
        @ParametricNullness
        public V last() {
            e();
            return (V) g().last();
        }

        @Override // java.util.SortedSet
        public SortedSet<V> subSet(@ParametricNullness V v2, @ParametricNullness V v3) {
            e();
            return new WrappedSortedSet(d(), g().subSet(v2, v3), b() == null ? this : b());
        }

        @Override // java.util.SortedSet
        public SortedSet<V> tailSet(@ParametricNullness V v2) {
            e();
            return new WrappedSortedSet(d(), g().tailSet(v2), b() == null ? this : b());
        }
    }

    protected AbstractMapBasedMultimap(Map<K, Collection<V>> map) {
        Preconditions.checkArgument(map.isEmpty());
        this.map = map;
    }

    static /* synthetic */ int access$208(AbstractMapBasedMultimap abstractMapBasedMultimap) {
        int i2 = abstractMapBasedMultimap.totalSize;
        abstractMapBasedMultimap.totalSize = i2 + 1;
        return i2;
    }

    static /* synthetic */ int access$210(AbstractMapBasedMultimap abstractMapBasedMultimap) {
        int i2 = abstractMapBasedMultimap.totalSize;
        abstractMapBasedMultimap.totalSize = i2 - 1;
        return i2;
    }

    static /* synthetic */ int access$212(AbstractMapBasedMultimap abstractMapBasedMultimap, int i2) {
        int i3 = abstractMapBasedMultimap.totalSize + i2;
        abstractMapBasedMultimap.totalSize = i3;
        return i3;
    }

    static /* synthetic */ int access$220(AbstractMapBasedMultimap abstractMapBasedMultimap, int i2) {
        int i3 = abstractMapBasedMultimap.totalSize - i2;
        abstractMapBasedMultimap.totalSize = i3;
        return i3;
    }

    private Collection<V> getOrCreateCollection(@ParametricNullness K k2) {
        Collection<V> collection = this.map.get(k2);
        if (collection != null) {
            return collection;
        }
        Collection<V> collectionCreateCollection = createCollection(k2);
        this.map.put(k2, collectionCreateCollection);
        return collectionCreateCollection;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <E> Iterator<E> iteratorOrListIterator(Collection<E> collection) {
        return collection instanceof List ? ((List) collection).listIterator() : collection.iterator();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeValuesForKey(@CheckForNull Object obj) {
        Collection collection = (Collection) Maps.z(this.map, obj);
        if (collection != null) {
            int size = collection.size();
            collection.clear();
            this.totalSize -= size;
        }
    }

    Map<K, Collection<V>> backingMap() {
        return this.map;
    }

    @Override // com.google.common.collect.Multimap
    public void clear() {
        Iterator<Collection<V>> it = this.map.values().iterator();
        while (it.hasNext()) {
            it.next().clear();
        }
        this.map.clear();
        this.totalSize = 0;
    }

    @Override // com.google.common.collect.Multimap
    public boolean containsKey(@CheckForNull Object obj) {
        return this.map.containsKey(obj);
    }

    @Override // com.google.common.collect.AbstractMultimap
    Map<K, Collection<V>> createAsMap() {
        return new AsMap(this.map);
    }

    abstract Collection<V> createCollection();

    Collection<V> createCollection(@ParametricNullness K k2) {
        return createCollection();
    }

    @Override // com.google.common.collect.AbstractMultimap
    Collection<Map.Entry<K, V>> createEntries() {
        return this instanceof SetMultimap ? new AbstractMultimap.EntrySet() : new AbstractMultimap.Entries();
    }

    @Override // com.google.common.collect.AbstractMultimap
    Set<K> createKeySet() {
        return new KeySet(this.map);
    }

    @Override // com.google.common.collect.AbstractMultimap
    Multiset<K> createKeys() {
        return new Multimaps.Keys(this);
    }

    final Map<K, Collection<V>> createMaybeNavigableAsMap() {
        Map<K, Collection<V>> map = this.map;
        return map instanceof NavigableMap ? new NavigableAsMap((NavigableMap) this.map) : map instanceof SortedMap ? new SortedAsMap((SortedMap) this.map) : new AsMap(this.map);
    }

    final Set<K> createMaybeNavigableKeySet() {
        Map<K, Collection<V>> map = this.map;
        return map instanceof NavigableMap ? new NavigableKeySet((NavigableMap) this.map) : map instanceof SortedMap ? new SortedKeySet((SortedMap) this.map) : new KeySet(this.map);
    }

    Collection<V> createUnmodifiableEmptyCollection() {
        return (Collection<V>) unmodifiableCollectionSubclass(createCollection());
    }

    @Override // com.google.common.collect.AbstractMultimap
    Collection<V> createValues() {
        return new AbstractMultimap.Values();
    }

    @Override // com.google.common.collect.AbstractMultimap, com.google.common.collect.Multimap
    public Collection<Map.Entry<K, V>> entries() {
        return super.entries();
    }

    @Override // com.google.common.collect.AbstractMultimap
    Iterator<Map.Entry<K, V>> entryIterator() {
        return new AbstractMapBasedMultimap<K, V>.Itr<Map.Entry<K, V>>() { // from class: com.google.common.collect.AbstractMapBasedMultimap.2
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // com.google.common.collect.AbstractMapBasedMultimap.Itr
            /* renamed from: b, reason: merged with bridge method [inline-methods] */
            public Map.Entry a(Object obj, Object obj2) {
                return Maps.immutableEntry(obj, obj2);
            }
        };
    }

    @Override // com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    public Collection<V> get(@ParametricNullness K k2) {
        Collection<V> collectionCreateCollection = this.map.get(k2);
        if (collectionCreateCollection == null) {
            collectionCreateCollection = createCollection(k2);
        }
        return wrapCollection(k2, collectionCreateCollection);
    }

    @Override // com.google.common.collect.AbstractMultimap, com.google.common.collect.Multimap
    public boolean put(@ParametricNullness K k2, @ParametricNullness V v2) {
        Collection<V> collection = this.map.get(k2);
        if (collection != null) {
            if (!collection.add(v2)) {
                return false;
            }
            this.totalSize++;
            return true;
        }
        Collection<V> collectionCreateCollection = createCollection(k2);
        if (!collectionCreateCollection.add(v2)) {
            throw new AssertionError("New Collection violated the Collection spec");
        }
        this.totalSize++;
        this.map.put(k2, collectionCreateCollection);
        return true;
    }

    @Override // com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    public Collection<V> removeAll(@CheckForNull Object obj) {
        Collection<V> collectionRemove = this.map.remove(obj);
        if (collectionRemove == null) {
            return createUnmodifiableEmptyCollection();
        }
        Collection collectionCreateCollection = createCollection();
        collectionCreateCollection.addAll(collectionRemove);
        this.totalSize -= collectionRemove.size();
        collectionRemove.clear();
        return (Collection<V>) unmodifiableCollectionSubclass(collectionCreateCollection);
    }

    @Override // com.google.common.collect.AbstractMultimap, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    public Collection<V> replaceValues(@ParametricNullness K k2, Iterable<? extends V> iterable) {
        Iterator<? extends V> it = iterable.iterator();
        if (!it.hasNext()) {
            return removeAll(k2);
        }
        Collection<V> orCreateCollection = getOrCreateCollection(k2);
        Collection<V> collectionCreateCollection = createCollection();
        collectionCreateCollection.addAll(orCreateCollection);
        this.totalSize -= orCreateCollection.size();
        orCreateCollection.clear();
        while (it.hasNext()) {
            if (orCreateCollection.add(it.next())) {
                this.totalSize++;
            }
        }
        return (Collection<V>) unmodifiableCollectionSubclass(collectionCreateCollection);
    }

    final void setMap(Map<K, Collection<V>> map) {
        this.map = map;
        this.totalSize = 0;
        for (Collection<V> collection : map.values()) {
            Preconditions.checkArgument(!collection.isEmpty());
            this.totalSize += collection.size();
        }
    }

    @Override // com.google.common.collect.Multimap
    public int size() {
        return this.totalSize;
    }

    <E> Collection<E> unmodifiableCollectionSubclass(Collection<E> collection) {
        return Collections.unmodifiableCollection(collection);
    }

    @Override // com.google.common.collect.AbstractMultimap
    Iterator<V> valueIterator() {
        return new AbstractMapBasedMultimap<K, V>.Itr<V>() { // from class: com.google.common.collect.AbstractMapBasedMultimap.1
            @Override // com.google.common.collect.AbstractMapBasedMultimap.Itr
            Object a(Object obj, Object obj2) {
                return obj2;
            }
        };
    }

    @Override // com.google.common.collect.AbstractMultimap, com.google.common.collect.Multimap
    public Collection<V> values() {
        return super.values();
    }

    Collection<V> wrapCollection(@ParametricNullness K k2, Collection<V> collection) {
        return new WrappedCollection(k2, collection, null);
    }

    final List<V> wrapList(@ParametricNullness K k2, List<V> list, @CheckForNull AbstractMapBasedMultimap<K, V>.WrappedCollection wrappedCollection) {
        return list instanceof RandomAccess ? new RandomAccessWrappedList(k2, list, wrappedCollection) : new WrappedList(k2, list, wrappedCollection);
    }

    class WrappedCollection extends AbstractCollection<V> {

        /* renamed from: a, reason: collision with root package name */
        final Object f13866a;

        /* renamed from: b, reason: collision with root package name */
        Collection f13867b;

        /* renamed from: c, reason: collision with root package name */
        final WrappedCollection f13868c;

        /* renamed from: d, reason: collision with root package name */
        final Collection f13869d;

        WrappedCollection(Object obj, Collection collection, WrappedCollection wrappedCollection) {
            this.f13866a = obj;
            this.f13867b = collection;
            this.f13868c = wrappedCollection;
            this.f13869d = wrappedCollection == null ? null : wrappedCollection.c();
        }

        /* JADX WARN: Multi-variable type inference failed */
        void a() {
            WrappedCollection wrappedCollection = this.f13868c;
            if (wrappedCollection != null) {
                wrappedCollection.a();
            } else {
                AbstractMapBasedMultimap.this.map.put(this.f13866a, this.f13867b);
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean add(@ParametricNullness V v2) {
            e();
            boolean zIsEmpty = this.f13867b.isEmpty();
            boolean zAdd = this.f13867b.add(v2);
            if (zAdd) {
                AbstractMapBasedMultimap.access$208(AbstractMapBasedMultimap.this);
                if (zIsEmpty) {
                    a();
                }
            }
            return zAdd;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean addAll(Collection<? extends V> collection) {
            if (collection.isEmpty()) {
                return false;
            }
            int size = size();
            boolean zAddAll = this.f13867b.addAll(collection);
            if (zAddAll) {
                AbstractMapBasedMultimap.access$212(AbstractMapBasedMultimap.this, this.f13867b.size() - size);
                if (size == 0) {
                    a();
                }
            }
            return zAddAll;
        }

        WrappedCollection b() {
            return this.f13868c;
        }

        Collection c() {
            return this.f13867b;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public void clear() {
            int size = size();
            if (size == 0) {
                return;
            }
            this.f13867b.clear();
            AbstractMapBasedMultimap.access$220(AbstractMapBasedMultimap.this, size);
            f();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean contains(@CheckForNull Object obj) {
            e();
            return this.f13867b.contains(obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean containsAll(Collection<?> collection) {
            e();
            return this.f13867b.containsAll(collection);
        }

        Object d() {
            return this.f13866a;
        }

        void e() {
            Collection collection;
            WrappedCollection wrappedCollection = this.f13868c;
            if (wrappedCollection != null) {
                wrappedCollection.e();
                if (this.f13868c.c() != this.f13869d) {
                    throw new ConcurrentModificationException();
                }
            } else {
                if (!this.f13867b.isEmpty() || (collection = (Collection) AbstractMapBasedMultimap.this.map.get(this.f13866a)) == null) {
                    return;
                }
                this.f13867b = collection;
            }
        }

        @Override // java.util.Collection
        public boolean equals(@CheckForNull Object obj) {
            if (obj == this) {
                return true;
            }
            e();
            return this.f13867b.equals(obj);
        }

        void f() {
            WrappedCollection wrappedCollection = this.f13868c;
            if (wrappedCollection != null) {
                wrappedCollection.f();
            } else if (this.f13867b.isEmpty()) {
                AbstractMapBasedMultimap.this.map.remove(this.f13866a);
            }
        }

        @Override // java.util.Collection
        public int hashCode() {
            e();
            return this.f13867b.hashCode();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public Iterator<V> iterator() {
            e();
            return new WrappedIterator();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean remove(@CheckForNull Object obj) {
            e();
            boolean zRemove = this.f13867b.remove(obj);
            if (zRemove) {
                AbstractMapBasedMultimap.access$210(AbstractMapBasedMultimap.this);
                f();
            }
            return zRemove;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean removeAll(Collection<?> collection) {
            if (collection.isEmpty()) {
                return false;
            }
            int size = size();
            boolean zRemoveAll = this.f13867b.removeAll(collection);
            if (zRemoveAll) {
                AbstractMapBasedMultimap.access$212(AbstractMapBasedMultimap.this, this.f13867b.size() - size);
                f();
            }
            return zRemoveAll;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean retainAll(Collection<?> collection) {
            Preconditions.checkNotNull(collection);
            int size = size();
            boolean zRetainAll = this.f13867b.retainAll(collection);
            if (zRetainAll) {
                AbstractMapBasedMultimap.access$212(AbstractMapBasedMultimap.this, this.f13867b.size() - size);
                f();
            }
            return zRetainAll;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            e();
            return this.f13867b.size();
        }

        @Override // java.util.AbstractCollection
        public String toString() {
            e();
            return this.f13867b.toString();
        }

        class WrappedIterator implements Iterator<V> {

            /* renamed from: a, reason: collision with root package name */
            final Iterator f13871a;

            /* renamed from: b, reason: collision with root package name */
            final Collection f13872b;

            WrappedIterator() {
                Collection collection = WrappedCollection.this.f13867b;
                this.f13872b = collection;
                this.f13871a = AbstractMapBasedMultimap.iteratorOrListIterator(collection);
            }

            Iterator a() {
                b();
                return this.f13871a;
            }

            void b() {
                WrappedCollection.this.e();
                if (WrappedCollection.this.f13867b != this.f13872b) {
                    throw new ConcurrentModificationException();
                }
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                b();
                return this.f13871a.hasNext();
            }

            @Override // java.util.Iterator
            @ParametricNullness
            public V next() {
                b();
                return (V) this.f13871a.next();
            }

            @Override // java.util.Iterator
            public void remove() {
                this.f13871a.remove();
                AbstractMapBasedMultimap.access$210(AbstractMapBasedMultimap.this);
                WrappedCollection.this.f();
            }

            WrappedIterator(Iterator it) {
                this.f13872b = WrappedCollection.this.f13867b;
                this.f13871a = it;
            }
        }
    }

    class WrappedList extends AbstractMapBasedMultimap<K, V>.WrappedCollection implements List<V> {

        private class WrappedListIterator extends AbstractMapBasedMultimap<K, V>.WrappedCollection.WrappedIterator implements ListIterator<V> {
            WrappedListIterator() {
                super();
            }

            private ListIterator<V> getDelegateListIterator() {
                return (ListIterator) a();
            }

            @Override // java.util.ListIterator
            public void add(@ParametricNullness V v2) {
                boolean zIsEmpty = WrappedList.this.isEmpty();
                getDelegateListIterator().add(v2);
                AbstractMapBasedMultimap.access$208(AbstractMapBasedMultimap.this);
                if (zIsEmpty) {
                    WrappedList.this.a();
                }
            }

            @Override // java.util.ListIterator
            public boolean hasPrevious() {
                return getDelegateListIterator().hasPrevious();
            }

            @Override // java.util.ListIterator
            public int nextIndex() {
                return getDelegateListIterator().nextIndex();
            }

            @Override // java.util.ListIterator
            @ParametricNullness
            public V previous() {
                return getDelegateListIterator().previous();
            }

            @Override // java.util.ListIterator
            public int previousIndex() {
                return getDelegateListIterator().previousIndex();
            }

            @Override // java.util.ListIterator
            public void set(@ParametricNullness V v2) {
                getDelegateListIterator().set(v2);
            }

            public WrappedListIterator(int i2) {
                super(WrappedList.this.g().listIterator(i2));
            }
        }

        WrappedList(Object obj, List list, WrappedCollection wrappedCollection) {
            super(obj, list, wrappedCollection);
        }

        @Override // java.util.List
        public void add(int i2, @ParametricNullness V v2) {
            e();
            boolean zIsEmpty = c().isEmpty();
            g().add(i2, v2);
            AbstractMapBasedMultimap.access$208(AbstractMapBasedMultimap.this);
            if (zIsEmpty) {
                a();
            }
        }

        @Override // java.util.List
        public boolean addAll(int i2, Collection<? extends V> collection) {
            if (collection.isEmpty()) {
                return false;
            }
            int size = size();
            boolean zAddAll = g().addAll(i2, collection);
            if (zAddAll) {
                AbstractMapBasedMultimap.access$212(AbstractMapBasedMultimap.this, c().size() - size);
                if (size == 0) {
                    a();
                }
            }
            return zAddAll;
        }

        List g() {
            return (List) c();
        }

        @Override // java.util.List
        @ParametricNullness
        public V get(int i2) {
            e();
            return (V) g().get(i2);
        }

        @Override // java.util.List
        public int indexOf(@CheckForNull Object obj) {
            e();
            return g().indexOf(obj);
        }

        @Override // java.util.List
        public int lastIndexOf(@CheckForNull Object obj) {
            e();
            return g().lastIndexOf(obj);
        }

        @Override // java.util.List
        public ListIterator<V> listIterator() {
            e();
            return new WrappedListIterator();
        }

        @Override // java.util.List
        @ParametricNullness
        public V remove(int i2) {
            e();
            V v2 = (V) g().remove(i2);
            AbstractMapBasedMultimap.access$210(AbstractMapBasedMultimap.this);
            f();
            return v2;
        }

        @Override // java.util.List
        @ParametricNullness
        public V set(int i2, @ParametricNullness V v2) {
            e();
            return (V) g().set(i2, v2);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.List
        public List<V> subList(int i2, int i3) {
            e();
            return AbstractMapBasedMultimap.this.wrapList(d(), g().subList(i2, i3), b() == null ? this : b());
        }

        @Override // java.util.List
        public ListIterator<V> listIterator(int i2) {
            e();
            return new WrappedListIterator(i2);
        }
    }
}
