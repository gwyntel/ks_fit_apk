package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.J2ktIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Multiset;
import com.google.common.collect.Table;
import com.google.j2objc.annotations.RetainedWith;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Queue;
import java.util.RandomAccess;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import javax.annotation.CheckForNull;

@J2ktIncompatible
@GwtCompatible(emulated = true)
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
final class Synchronized {

    static final class SynchronizedAsMap<K, V> extends SynchronizedMap<K, Collection<V>> {
        private static final long serialVersionUID = 0;

        @CheckForNull
        transient Set<Map.Entry<K, Collection<V>>> asMapEntrySet;

        @CheckForNull
        transient Collection<Collection<V>> asMapValues;

        SynchronizedAsMap(Map<K, Collection<V>> map, @CheckForNull Object obj) {
            super(map, obj);
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedMap, java.util.Map
        public boolean containsValue(@CheckForNull Object obj) {
            return values().contains(obj);
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedMap, java.util.Map
        public Set<Map.Entry<K, Collection<V>>> entrySet() {
            Set<Map.Entry<K, Collection<V>>> set;
            synchronized (this.mutex) {
                try {
                    if (this.asMapEntrySet == null) {
                        this.asMapEntrySet = new SynchronizedAsMapEntries(delegate().entrySet(), this.mutex);
                    }
                    set = this.asMapEntrySet;
                } catch (Throwable th) {
                    throw th;
                }
            }
            return set;
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedMap, java.util.Map
        public Collection<Collection<V>> values() {
            Collection<Collection<V>> collection;
            synchronized (this.mutex) {
                try {
                    if (this.asMapValues == null) {
                        this.asMapValues = new SynchronizedAsMapValues(delegate().values(), this.mutex);
                    }
                    collection = this.asMapValues;
                } catch (Throwable th) {
                    throw th;
                }
            }
            return collection;
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedMap, java.util.Map
        @CheckForNull
        public Collection<V> get(@CheckForNull Object obj) {
            Collection<V> collectionTypePreservingCollection;
            synchronized (this.mutex) {
                Collection collection = (Collection) super.get(obj);
                collectionTypePreservingCollection = collection == null ? null : Synchronized.typePreservingCollection(collection, this.mutex);
            }
            return collectionTypePreservingCollection;
        }
    }

    static final class SynchronizedAsMapValues<V> extends SynchronizedCollection<Collection<V>> {
        private static final long serialVersionUID = 0;

        SynchronizedAsMapValues(Collection<Collection<V>> collection, @CheckForNull Object obj) {
            super(collection, obj);
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<Collection<V>> iterator() {
            return new TransformedIterator<Collection<V>, Collection<V>>(super.iterator()) { // from class: com.google.common.collect.Synchronized.SynchronizedAsMapValues.1
                /* JADX INFO: Access modifiers changed from: package-private */
                @Override // com.google.common.collect.TransformedIterator
                /* renamed from: b, reason: merged with bridge method [inline-methods] */
                public Collection a(Collection collection) {
                    return Synchronized.typePreservingCollection(collection, SynchronizedAsMapValues.this.mutex);
                }
            };
        }
    }

    static final class SynchronizedBiMap<K, V> extends SynchronizedMap<K, V> implements BiMap<K, V>, Serializable {
        private static final long serialVersionUID = 0;

        @RetainedWith
        @CheckForNull
        private transient BiMap<V, K> inverse;

        @CheckForNull
        private transient Set<V> valueSet;

        @Override // com.google.common.collect.BiMap
        @CheckForNull
        public V forcePut(@ParametricNullness K k2, @ParametricNullness V v2) {
            V vForcePut;
            synchronized (this.mutex) {
                vForcePut = delegate().forcePut(k2, v2);
            }
            return vForcePut;
        }

        @Override // com.google.common.collect.BiMap
        public BiMap<V, K> inverse() {
            BiMap<V, K> biMap;
            synchronized (this.mutex) {
                try {
                    if (this.inverse == null) {
                        this.inverse = new SynchronizedBiMap(delegate().inverse(), this.mutex, this);
                    }
                    biMap = this.inverse;
                } catch (Throwable th) {
                    throw th;
                }
            }
            return biMap;
        }

        private SynchronizedBiMap(BiMap<K, V> biMap, @CheckForNull Object obj, @CheckForNull BiMap<V, K> biMap2) {
            super(biMap, obj);
            this.inverse = biMap2;
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedMap, java.util.Map
        public Set<V> values() {
            Set<V> set;
            synchronized (this.mutex) {
                try {
                    if (this.valueSet == null) {
                        this.valueSet = Synchronized.r(delegate().values(), this.mutex);
                    }
                    set = this.valueSet;
                } catch (Throwable th) {
                    throw th;
                }
            }
            return set;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.collect.Synchronized.SynchronizedMap, com.google.common.collect.Synchronized.SynchronizedObject
        public BiMap<K, V> delegate() {
            return (BiMap) super.delegate();
        }
    }

    @VisibleForTesting
    static class SynchronizedCollection<E> extends SynchronizedObject implements Collection<E> {
        private static final long serialVersionUID = 0;

        @Override // java.util.Collection
        public boolean add(E e2) {
            boolean zAdd;
            synchronized (this.mutex) {
                zAdd = delegate().add(e2);
            }
            return zAdd;
        }

        @Override // java.util.Collection
        public boolean addAll(Collection<? extends E> collection) {
            boolean zAddAll;
            synchronized (this.mutex) {
                zAddAll = delegate().addAll(collection);
            }
            return zAddAll;
        }

        @Override // java.util.Collection
        public void clear() {
            synchronized (this.mutex) {
                delegate().clear();
            }
        }

        public boolean contains(@CheckForNull Object obj) {
            boolean zContains;
            synchronized (this.mutex) {
                zContains = delegate().contains(obj);
            }
            return zContains;
        }

        public boolean containsAll(Collection<?> collection) {
            boolean zContainsAll;
            synchronized (this.mutex) {
                zContainsAll = delegate().containsAll(collection);
            }
            return zContainsAll;
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            boolean zIsEmpty;
            synchronized (this.mutex) {
                zIsEmpty = delegate().isEmpty();
            }
            return zIsEmpty;
        }

        public Iterator<E> iterator() {
            return delegate().iterator();
        }

        public boolean remove(@CheckForNull Object obj) {
            boolean zRemove;
            synchronized (this.mutex) {
                zRemove = delegate().remove(obj);
            }
            return zRemove;
        }

        public boolean removeAll(Collection<?> collection) {
            boolean zRemoveAll;
            synchronized (this.mutex) {
                zRemoveAll = delegate().removeAll(collection);
            }
            return zRemoveAll;
        }

        public boolean retainAll(Collection<?> collection) {
            boolean zRetainAll;
            synchronized (this.mutex) {
                zRetainAll = delegate().retainAll(collection);
            }
            return zRetainAll;
        }

        @Override // java.util.Collection
        public int size() {
            int size;
            synchronized (this.mutex) {
                size = delegate().size();
            }
            return size;
        }

        public Object[] toArray() {
            Object[] array;
            synchronized (this.mutex) {
                array = delegate().toArray();
            }
            return array;
        }

        private SynchronizedCollection(Collection<E> collection, @CheckForNull Object obj) {
            super(collection, obj);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.collect.Synchronized.SynchronizedObject
        public Collection<E> delegate() {
            return (Collection) super.delegate();
        }

        public <T> T[] toArray(T[] tArr) {
            T[] tArr2;
            synchronized (this.mutex) {
                tArr2 = (T[]) delegate().toArray(tArr);
            }
            return tArr2;
        }
    }

    @GwtIncompatible
    static final class SynchronizedEntry<K, V> extends SynchronizedObject implements Map.Entry<K, V> {
        private static final long serialVersionUID = 0;

        SynchronizedEntry(Map.Entry<K, V> entry, @CheckForNull Object obj) {
            super(entry, obj);
        }

        @Override // java.util.Map.Entry
        public boolean equals(@CheckForNull Object obj) {
            boolean zEquals;
            synchronized (this.mutex) {
                zEquals = delegate().equals(obj);
            }
            return zEquals;
        }

        @Override // java.util.Map.Entry
        public K getKey() {
            K key;
            synchronized (this.mutex) {
                key = delegate().getKey();
            }
            return key;
        }

        @Override // java.util.Map.Entry
        public V getValue() {
            V value;
            synchronized (this.mutex) {
                value = delegate().getValue();
            }
            return value;
        }

        @Override // java.util.Map.Entry
        public int hashCode() {
            int iHashCode;
            synchronized (this.mutex) {
                iHashCode = delegate().hashCode();
            }
            return iHashCode;
        }

        @Override // java.util.Map.Entry
        public V setValue(V v2) {
            V value;
            synchronized (this.mutex) {
                value = delegate().setValue(v2);
            }
            return value;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.collect.Synchronized.SynchronizedObject
        public Map.Entry<K, V> delegate() {
            return (Map.Entry) super.delegate();
        }
    }

    static class SynchronizedList<E> extends SynchronizedCollection<E> implements List<E> {
        private static final long serialVersionUID = 0;

        SynchronizedList(List<E> list, @CheckForNull Object obj) {
            super(list, obj);
        }

        @Override // java.util.List
        public void add(int i2, E e2) {
            synchronized (this.mutex) {
                delegate().add(i2, e2);
            }
        }

        @Override // java.util.List
        public boolean addAll(int i2, Collection<? extends E> collection) {
            boolean zAddAll;
            synchronized (this.mutex) {
                zAddAll = delegate().addAll(i2, collection);
            }
            return zAddAll;
        }

        @Override // java.util.Collection, java.util.List
        public boolean equals(@CheckForNull Object obj) {
            boolean zEquals;
            if (obj == this) {
                return true;
            }
            synchronized (this.mutex) {
                zEquals = delegate().equals(obj);
            }
            return zEquals;
        }

        @Override // java.util.List
        public E get(int i2) {
            E e2;
            synchronized (this.mutex) {
                e2 = delegate().get(i2);
            }
            return e2;
        }

        @Override // java.util.Collection, java.util.List
        public int hashCode() {
            int iHashCode;
            synchronized (this.mutex) {
                iHashCode = delegate().hashCode();
            }
            return iHashCode;
        }

        @Override // java.util.List
        public int indexOf(@CheckForNull Object obj) {
            int iIndexOf;
            synchronized (this.mutex) {
                iIndexOf = delegate().indexOf(obj);
            }
            return iIndexOf;
        }

        @Override // java.util.List
        public int lastIndexOf(@CheckForNull Object obj) {
            int iLastIndexOf;
            synchronized (this.mutex) {
                iLastIndexOf = delegate().lastIndexOf(obj);
            }
            return iLastIndexOf;
        }

        @Override // java.util.List
        public ListIterator<E> listIterator() {
            return delegate().listIterator();
        }

        @Override // java.util.List
        public E remove(int i2) {
            E eRemove;
            synchronized (this.mutex) {
                eRemove = delegate().remove(i2);
            }
            return eRemove;
        }

        @Override // java.util.List
        public E set(int i2, E e2) {
            E e3;
            synchronized (this.mutex) {
                e3 = delegate().set(i2, e2);
            }
            return e3;
        }

        @Override // java.util.List
        public List<E> subList(int i2, int i3) {
            List<E> list;
            synchronized (this.mutex) {
                list = Synchronized.list(delegate().subList(i2, i3), this.mutex);
            }
            return list;
        }

        @Override // java.util.List
        public ListIterator<E> listIterator(int i2) {
            return delegate().listIterator(i2);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.collect.Synchronized.SynchronizedCollection, com.google.common.collect.Synchronized.SynchronizedObject
        public List<E> delegate() {
            return (List) super.delegate();
        }
    }

    static final class SynchronizedListMultimap<K, V> extends SynchronizedMultimap<K, V> implements ListMultimap<K, V> {
        private static final long serialVersionUID = 0;

        SynchronizedListMultimap(ListMultimap<K, V> listMultimap, @CheckForNull Object obj) {
            super(listMultimap, obj);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.collect.Synchronized.SynchronizedMultimap, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
        public /* bridge */ /* synthetic */ Collection get(Object obj) {
            return get((SynchronizedListMultimap<K, V>) obj);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.collect.Synchronized.SynchronizedMultimap, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
        public /* bridge */ /* synthetic */ Collection replaceValues(Object obj, Iterable iterable) {
            return replaceValues((SynchronizedListMultimap<K, V>) obj, iterable);
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedMultimap, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
        public List<V> get(K k2) {
            List<V> list;
            synchronized (this.mutex) {
                list = Synchronized.list(delegate().get((ListMultimap<K, V>) k2), this.mutex);
            }
            return list;
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedMultimap, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
        public List<V> removeAll(@CheckForNull Object obj) {
            List<V> listRemoveAll;
            synchronized (this.mutex) {
                listRemoveAll = delegate().removeAll(obj);
            }
            return listRemoveAll;
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedMultimap, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
        public List<V> replaceValues(K k2, Iterable<? extends V> iterable) {
            List<V> listReplaceValues;
            synchronized (this.mutex) {
                listReplaceValues = delegate().replaceValues((ListMultimap<K, V>) k2, (Iterable) iterable);
            }
            return listReplaceValues;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.collect.Synchronized.SynchronizedMultimap, com.google.common.collect.Synchronized.SynchronizedObject
        public ListMultimap<K, V> delegate() {
            return (ListMultimap) super.delegate();
        }
    }

    static class SynchronizedMap<K, V> extends SynchronizedObject implements Map<K, V> {
        private static final long serialVersionUID = 0;

        @CheckForNull
        transient Set<Map.Entry<K, V>> entrySet;

        @CheckForNull
        transient Set<K> keySet;

        @CheckForNull
        transient Collection<V> values;

        SynchronizedMap(Map<K, V> map, @CheckForNull Object obj) {
            super(map, obj);
        }

        @Override // java.util.Map
        public void clear() {
            synchronized (this.mutex) {
                delegate().clear();
            }
        }

        @Override // java.util.Map
        public boolean containsKey(@CheckForNull Object obj) {
            boolean zContainsKey;
            synchronized (this.mutex) {
                zContainsKey = delegate().containsKey(obj);
            }
            return zContainsKey;
        }

        public boolean containsValue(@CheckForNull Object obj) {
            boolean zContainsValue;
            synchronized (this.mutex) {
                zContainsValue = delegate().containsValue(obj);
            }
            return zContainsValue;
        }

        public Set<Map.Entry<K, V>> entrySet() {
            Set<Map.Entry<K, V>> set;
            synchronized (this.mutex) {
                try {
                    if (this.entrySet == null) {
                        this.entrySet = Synchronized.r(delegate().entrySet(), this.mutex);
                    }
                    set = this.entrySet;
                } catch (Throwable th) {
                    throw th;
                }
            }
            return set;
        }

        @Override // java.util.Map
        public boolean equals(@CheckForNull Object obj) {
            boolean zEquals;
            if (obj == this) {
                return true;
            }
            synchronized (this.mutex) {
                zEquals = delegate().equals(obj);
            }
            return zEquals;
        }

        @CheckForNull
        public V get(@CheckForNull Object obj) {
            V v2;
            synchronized (this.mutex) {
                v2 = delegate().get(obj);
            }
            return v2;
        }

        @Override // java.util.Map
        public int hashCode() {
            int iHashCode;
            synchronized (this.mutex) {
                iHashCode = delegate().hashCode();
            }
            return iHashCode;
        }

        @Override // java.util.Map
        public boolean isEmpty() {
            boolean zIsEmpty;
            synchronized (this.mutex) {
                zIsEmpty = delegate().isEmpty();
            }
            return zIsEmpty;
        }

        @Override // java.util.Map
        public Set<K> keySet() {
            Set<K> set;
            synchronized (this.mutex) {
                try {
                    if (this.keySet == null) {
                        this.keySet = Synchronized.r(delegate().keySet(), this.mutex);
                    }
                    set = this.keySet;
                } catch (Throwable th) {
                    throw th;
                }
            }
            return set;
        }

        @Override // java.util.Map
        @CheckForNull
        public V put(K k2, V v2) {
            V vPut;
            synchronized (this.mutex) {
                vPut = delegate().put(k2, v2);
            }
            return vPut;
        }

        @Override // java.util.Map
        public void putAll(Map<? extends K, ? extends V> map) {
            synchronized (this.mutex) {
                delegate().putAll(map);
            }
        }

        @Override // java.util.Map
        @CheckForNull
        public V remove(@CheckForNull Object obj) {
            V vRemove;
            synchronized (this.mutex) {
                vRemove = delegate().remove(obj);
            }
            return vRemove;
        }

        @Override // java.util.Map
        public int size() {
            int size;
            synchronized (this.mutex) {
                size = delegate().size();
            }
            return size;
        }

        public Collection<V> values() {
            Collection<V> collection;
            synchronized (this.mutex) {
                try {
                    if (this.values == null) {
                        this.values = Synchronized.collection(delegate().values(), this.mutex);
                    }
                    collection = this.values;
                } catch (Throwable th) {
                    throw th;
                }
            }
            return collection;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.collect.Synchronized.SynchronizedObject
        public Map<K, V> delegate() {
            return (Map) super.delegate();
        }
    }

    static class SynchronizedMultimap<K, V> extends SynchronizedObject implements Multimap<K, V> {
        private static final long serialVersionUID = 0;

        @CheckForNull
        transient Map<K, Collection<V>> asMap;

        @CheckForNull
        transient Collection<Map.Entry<K, V>> entries;

        @CheckForNull
        transient Set<K> keySet;

        @CheckForNull
        transient Multiset<K> keys;

        @CheckForNull
        transient Collection<V> valuesCollection;

        SynchronizedMultimap(Multimap<K, V> multimap, @CheckForNull Object obj) {
            super(multimap, obj);
        }

        @Override // com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
        public Map<K, Collection<V>> asMap() {
            Map<K, Collection<V>> map;
            synchronized (this.mutex) {
                try {
                    if (this.asMap == null) {
                        this.asMap = new SynchronizedAsMap(delegate().asMap(), this.mutex);
                    }
                    map = this.asMap;
                } catch (Throwable th) {
                    throw th;
                }
            }
            return map;
        }

        @Override // com.google.common.collect.Multimap
        public void clear() {
            synchronized (this.mutex) {
                delegate().clear();
            }
        }

        @Override // com.google.common.collect.Multimap
        public boolean containsEntry(@CheckForNull Object obj, @CheckForNull Object obj2) {
            boolean zContainsEntry;
            synchronized (this.mutex) {
                zContainsEntry = delegate().containsEntry(obj, obj2);
            }
            return zContainsEntry;
        }

        @Override // com.google.common.collect.Multimap
        public boolean containsKey(@CheckForNull Object obj) {
            boolean zContainsKey;
            synchronized (this.mutex) {
                zContainsKey = delegate().containsKey(obj);
            }
            return zContainsKey;
        }

        @Override // com.google.common.collect.Multimap
        public boolean containsValue(@CheckForNull Object obj) {
            boolean zContainsValue;
            synchronized (this.mutex) {
                zContainsValue = delegate().containsValue(obj);
            }
            return zContainsValue;
        }

        @Override // com.google.common.collect.Multimap
        public Collection<Map.Entry<K, V>> entries() {
            Collection<Map.Entry<K, V>> collection;
            synchronized (this.mutex) {
                try {
                    if (this.entries == null) {
                        this.entries = Synchronized.typePreservingCollection(delegate().entries(), this.mutex);
                    }
                    collection = this.entries;
                } catch (Throwable th) {
                    throw th;
                }
            }
            return collection;
        }

        @Override // com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
        public boolean equals(@CheckForNull Object obj) {
            boolean zEquals;
            if (obj == this) {
                return true;
            }
            synchronized (this.mutex) {
                zEquals = delegate().equals(obj);
            }
            return zEquals;
        }

        public Collection<V> get(@ParametricNullness K k2) {
            Collection<V> collectionTypePreservingCollection;
            synchronized (this.mutex) {
                collectionTypePreservingCollection = Synchronized.typePreservingCollection(delegate().get(k2), this.mutex);
            }
            return collectionTypePreservingCollection;
        }

        @Override // com.google.common.collect.Multimap
        public int hashCode() {
            int iHashCode;
            synchronized (this.mutex) {
                iHashCode = delegate().hashCode();
            }
            return iHashCode;
        }

        @Override // com.google.common.collect.Multimap
        public boolean isEmpty() {
            boolean zIsEmpty;
            synchronized (this.mutex) {
                zIsEmpty = delegate().isEmpty();
            }
            return zIsEmpty;
        }

        @Override // com.google.common.collect.Multimap
        public Set<K> keySet() {
            Set<K> set;
            synchronized (this.mutex) {
                try {
                    if (this.keySet == null) {
                        this.keySet = Synchronized.typePreservingSet(delegate().keySet(), this.mutex);
                    }
                    set = this.keySet;
                } catch (Throwable th) {
                    throw th;
                }
            }
            return set;
        }

        @Override // com.google.common.collect.Multimap
        public Multiset<K> keys() {
            Multiset<K> multiset;
            synchronized (this.mutex) {
                try {
                    if (this.keys == null) {
                        this.keys = Synchronized.l(delegate().keys(), this.mutex);
                    }
                    multiset = this.keys;
                } catch (Throwable th) {
                    throw th;
                }
            }
            return multiset;
        }

        @Override // com.google.common.collect.Multimap
        public boolean put(@ParametricNullness K k2, @ParametricNullness V v2) {
            boolean zPut;
            synchronized (this.mutex) {
                zPut = delegate().put(k2, v2);
            }
            return zPut;
        }

        @Override // com.google.common.collect.Multimap
        public boolean putAll(@ParametricNullness K k2, Iterable<? extends V> iterable) {
            boolean zPutAll;
            synchronized (this.mutex) {
                zPutAll = delegate().putAll(k2, iterable);
            }
            return zPutAll;
        }

        @Override // com.google.common.collect.Multimap
        public boolean remove(@CheckForNull Object obj, @CheckForNull Object obj2) {
            boolean zRemove;
            synchronized (this.mutex) {
                zRemove = delegate().remove(obj, obj2);
            }
            return zRemove;
        }

        public Collection<V> removeAll(@CheckForNull Object obj) {
            Collection<V> collectionRemoveAll;
            synchronized (this.mutex) {
                collectionRemoveAll = delegate().removeAll(obj);
            }
            return collectionRemoveAll;
        }

        public Collection<V> replaceValues(@ParametricNullness K k2, Iterable<? extends V> iterable) {
            Collection<V> collectionReplaceValues;
            synchronized (this.mutex) {
                collectionReplaceValues = delegate().replaceValues(k2, iterable);
            }
            return collectionReplaceValues;
        }

        @Override // com.google.common.collect.Multimap
        public int size() {
            int size;
            synchronized (this.mutex) {
                size = delegate().size();
            }
            return size;
        }

        @Override // com.google.common.collect.Multimap
        public Collection<V> values() {
            Collection<V> collection;
            synchronized (this.mutex) {
                try {
                    if (this.valuesCollection == null) {
                        this.valuesCollection = Synchronized.collection(delegate().values(), this.mutex);
                    }
                    collection = this.valuesCollection;
                } catch (Throwable th) {
                    throw th;
                }
            }
            return collection;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.collect.Synchronized.SynchronizedObject
        public Multimap<K, V> delegate() {
            return (Multimap) super.delegate();
        }

        @Override // com.google.common.collect.Multimap
        public boolean putAll(Multimap<? extends K, ? extends V> multimap) {
            boolean zPutAll;
            synchronized (this.mutex) {
                zPutAll = delegate().putAll(multimap);
            }
            return zPutAll;
        }
    }

    static class SynchronizedObject implements Serializable {

        @J2ktIncompatible
        @GwtIncompatible
        private static final long serialVersionUID = 0;
        final Object delegate;
        final Object mutex;

        SynchronizedObject(Object obj, @CheckForNull Object obj2) {
            this.delegate = Preconditions.checkNotNull(obj);
            this.mutex = obj2 == null ? this : obj2;
        }

        @J2ktIncompatible
        @GwtIncompatible
        private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
            synchronized (this.mutex) {
                objectOutputStream.defaultWriteObject();
            }
        }

        Object delegate() {
            return this.delegate;
        }

        public String toString() {
            String string;
            synchronized (this.mutex) {
                string = this.delegate.toString();
            }
            return string;
        }
    }

    static final class SynchronizedRandomAccessList<E> extends SynchronizedList<E> implements RandomAccess {
        private static final long serialVersionUID = 0;

        SynchronizedRandomAccessList(List<E> list, @CheckForNull Object obj) {
            super(list, obj);
        }
    }

    static class SynchronizedSetMultimap<K, V> extends SynchronizedMultimap<K, V> implements SetMultimap<K, V> {
        private static final long serialVersionUID = 0;

        @CheckForNull
        transient Set<Map.Entry<K, V>> entrySet;

        SynchronizedSetMultimap(SetMultimap<K, V> setMultimap, @CheckForNull Object obj) {
            super(setMultimap, obj);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.collect.Synchronized.SynchronizedMultimap, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
        public /* bridge */ /* synthetic */ Collection get(Object obj) {
            return get((SynchronizedSetMultimap<K, V>) obj);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.collect.Synchronized.SynchronizedMultimap, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
        public /* bridge */ /* synthetic */ Collection replaceValues(Object obj, Iterable iterable) {
            return replaceValues((SynchronizedSetMultimap<K, V>) obj, iterable);
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedMultimap, com.google.common.collect.Multimap
        public Set<Map.Entry<K, V>> entries() {
            Set<Map.Entry<K, V>> set;
            synchronized (this.mutex) {
                try {
                    if (this.entrySet == null) {
                        this.entrySet = Synchronized.r(delegate().entries(), this.mutex);
                    }
                    set = this.entrySet;
                } catch (Throwable th) {
                    throw th;
                }
            }
            return set;
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedMultimap, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
        public Set<V> get(K k2) {
            Set<V> setR;
            synchronized (this.mutex) {
                setR = Synchronized.r(delegate().get((SetMultimap<K, V>) k2), this.mutex);
            }
            return setR;
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedMultimap, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
        public Set<V> removeAll(@CheckForNull Object obj) {
            Set<V> setRemoveAll;
            synchronized (this.mutex) {
                setRemoveAll = delegate().removeAll(obj);
            }
            return setRemoveAll;
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedMultimap, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
        public Set<V> replaceValues(K k2, Iterable<? extends V> iterable) {
            Set<V> setReplaceValues;
            synchronized (this.mutex) {
                setReplaceValues = delegate().replaceValues((SetMultimap<K, V>) k2, (Iterable) iterable);
            }
            return setReplaceValues;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.collect.Synchronized.SynchronizedMultimap, com.google.common.collect.Synchronized.SynchronizedObject
        public SetMultimap<K, V> delegate() {
            return (SetMultimap) super.delegate();
        }
    }

    static final class SynchronizedSortedSetMultimap<K, V> extends SynchronizedSetMultimap<K, V> implements SortedSetMultimap<K, V> {
        private static final long serialVersionUID = 0;

        SynchronizedSortedSetMultimap(SortedSetMultimap<K, V> sortedSetMultimap, @CheckForNull Object obj) {
            super(sortedSetMultimap, obj);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.collect.Synchronized.SynchronizedSetMultimap, com.google.common.collect.Synchronized.SynchronizedMultimap, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
        public /* bridge */ /* synthetic */ Collection get(Object obj) {
            return get((SynchronizedSortedSetMultimap<K, V>) obj);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.collect.Synchronized.SynchronizedSetMultimap, com.google.common.collect.Synchronized.SynchronizedMultimap, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
        public /* bridge */ /* synthetic */ Collection replaceValues(Object obj, Iterable iterable) {
            return replaceValues((SynchronizedSortedSetMultimap<K, V>) obj, iterable);
        }

        @Override // com.google.common.collect.SortedSetMultimap
        @CheckForNull
        public Comparator<? super V> valueComparator() {
            Comparator<? super V> comparatorValueComparator;
            synchronized (this.mutex) {
                comparatorValueComparator = delegate().valueComparator();
            }
            return comparatorValueComparator;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.collect.Synchronized.SynchronizedSetMultimap, com.google.common.collect.Synchronized.SynchronizedMultimap, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
        public /* bridge */ /* synthetic */ Set get(Object obj) {
            return get((SynchronizedSortedSetMultimap<K, V>) obj);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.collect.Synchronized.SynchronizedSetMultimap, com.google.common.collect.Synchronized.SynchronizedMultimap, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
        public /* bridge */ /* synthetic */ Set replaceValues(Object obj, Iterable iterable) {
            return replaceValues((SynchronizedSortedSetMultimap<K, V>) obj, iterable);
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedSetMultimap, com.google.common.collect.Synchronized.SynchronizedMultimap, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
        public SortedSet<V> get(K k2) {
            SortedSet<V> sortedSet;
            synchronized (this.mutex) {
                sortedSet = Synchronized.sortedSet(delegate().get((SortedSetMultimap<K, V>) k2), this.mutex);
            }
            return sortedSet;
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedSetMultimap, com.google.common.collect.Synchronized.SynchronizedMultimap, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
        public SortedSet<V> removeAll(@CheckForNull Object obj) {
            SortedSet<V> sortedSetRemoveAll;
            synchronized (this.mutex) {
                sortedSetRemoveAll = delegate().removeAll(obj);
            }
            return sortedSetRemoveAll;
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedSetMultimap, com.google.common.collect.Synchronized.SynchronizedMultimap, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
        public SortedSet<V> replaceValues(K k2, Iterable<? extends V> iterable) {
            SortedSet<V> sortedSetReplaceValues;
            synchronized (this.mutex) {
                sortedSetReplaceValues = delegate().replaceValues((SortedSetMultimap<K, V>) k2, (Iterable) iterable);
            }
            return sortedSetReplaceValues;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.collect.Synchronized.SynchronizedSetMultimap, com.google.common.collect.Synchronized.SynchronizedMultimap, com.google.common.collect.Synchronized.SynchronizedObject
        public SortedSetMultimap<K, V> delegate() {
            return (SortedSetMultimap) super.delegate();
        }
    }

    static final class SynchronizedTable<R, C, V> extends SynchronizedObject implements Table<R, C, V> {
        SynchronizedTable(Table<R, C, V> table, @CheckForNull Object obj) {
            super(table, obj);
        }

        @Override // com.google.common.collect.Table
        public Set<Table.Cell<R, C, V>> cellSet() {
            Set<Table.Cell<R, C, V>> setR;
            synchronized (this.mutex) {
                setR = Synchronized.r(delegate().cellSet(), this.mutex);
            }
            return setR;
        }

        @Override // com.google.common.collect.Table
        public void clear() {
            synchronized (this.mutex) {
                delegate().clear();
            }
        }

        @Override // com.google.common.collect.Table
        public Map<R, V> column(@ParametricNullness C c2) {
            Map<R, V> mapJ;
            synchronized (this.mutex) {
                mapJ = Synchronized.j(delegate().column(c2), this.mutex);
            }
            return mapJ;
        }

        @Override // com.google.common.collect.Table
        public Set<C> columnKeySet() {
            Set<C> setR;
            synchronized (this.mutex) {
                setR = Synchronized.r(delegate().columnKeySet(), this.mutex);
            }
            return setR;
        }

        @Override // com.google.common.collect.Table
        public Map<C, Map<R, V>> columnMap() {
            Map<C, Map<R, V>> mapJ;
            synchronized (this.mutex) {
                mapJ = Synchronized.j(Maps.transformValues(delegate().columnMap(), new Function<Map<R, V>, Map<R, V>>() { // from class: com.google.common.collect.Synchronized.SynchronizedTable.2
                    @Override // com.google.common.base.Function
                    public Map<R, V> apply(Map<R, V> map) {
                        return Synchronized.j(map, SynchronizedTable.this.mutex);
                    }
                }), this.mutex);
            }
            return mapJ;
        }

        @Override // com.google.common.collect.Table
        public boolean contains(@CheckForNull Object obj, @CheckForNull Object obj2) {
            boolean zContains;
            synchronized (this.mutex) {
                zContains = delegate().contains(obj, obj2);
            }
            return zContains;
        }

        @Override // com.google.common.collect.Table
        public boolean containsColumn(@CheckForNull Object obj) {
            boolean zContainsColumn;
            synchronized (this.mutex) {
                zContainsColumn = delegate().containsColumn(obj);
            }
            return zContainsColumn;
        }

        @Override // com.google.common.collect.Table
        public boolean containsRow(@CheckForNull Object obj) {
            boolean zContainsRow;
            synchronized (this.mutex) {
                zContainsRow = delegate().containsRow(obj);
            }
            return zContainsRow;
        }

        @Override // com.google.common.collect.Table
        public boolean containsValue(@CheckForNull Object obj) {
            boolean zContainsValue;
            synchronized (this.mutex) {
                zContainsValue = delegate().containsValue(obj);
            }
            return zContainsValue;
        }

        @Override // com.google.common.collect.Table
        public boolean equals(@CheckForNull Object obj) {
            boolean zEquals;
            if (this == obj) {
                return true;
            }
            synchronized (this.mutex) {
                zEquals = delegate().equals(obj);
            }
            return zEquals;
        }

        @Override // com.google.common.collect.Table
        @CheckForNull
        public V get(@CheckForNull Object obj, @CheckForNull Object obj2) {
            V v2;
            synchronized (this.mutex) {
                v2 = delegate().get(obj, obj2);
            }
            return v2;
        }

        @Override // com.google.common.collect.Table
        public int hashCode() {
            int iHashCode;
            synchronized (this.mutex) {
                iHashCode = delegate().hashCode();
            }
            return iHashCode;
        }

        @Override // com.google.common.collect.Table
        public boolean isEmpty() {
            boolean zIsEmpty;
            synchronized (this.mutex) {
                zIsEmpty = delegate().isEmpty();
            }
            return zIsEmpty;
        }

        @Override // com.google.common.collect.Table
        @CheckForNull
        public V put(@ParametricNullness R r2, @ParametricNullness C c2, @ParametricNullness V v2) {
            V vPut;
            synchronized (this.mutex) {
                vPut = delegate().put(r2, c2, v2);
            }
            return vPut;
        }

        @Override // com.google.common.collect.Table
        public void putAll(Table<? extends R, ? extends C, ? extends V> table) {
            synchronized (this.mutex) {
                delegate().putAll(table);
            }
        }

        @Override // com.google.common.collect.Table
        @CheckForNull
        public V remove(@CheckForNull Object obj, @CheckForNull Object obj2) {
            V vRemove;
            synchronized (this.mutex) {
                vRemove = delegate().remove(obj, obj2);
            }
            return vRemove;
        }

        @Override // com.google.common.collect.Table
        public Map<C, V> row(@ParametricNullness R r2) {
            Map<C, V> mapJ;
            synchronized (this.mutex) {
                mapJ = Synchronized.j(delegate().row(r2), this.mutex);
            }
            return mapJ;
        }

        @Override // com.google.common.collect.Table
        public Set<R> rowKeySet() {
            Set<R> setR;
            synchronized (this.mutex) {
                setR = Synchronized.r(delegate().rowKeySet(), this.mutex);
            }
            return setR;
        }

        @Override // com.google.common.collect.Table
        public Map<R, Map<C, V>> rowMap() {
            Map<R, Map<C, V>> mapJ;
            synchronized (this.mutex) {
                mapJ = Synchronized.j(Maps.transformValues(delegate().rowMap(), new Function<Map<C, V>, Map<C, V>>() { // from class: com.google.common.collect.Synchronized.SynchronizedTable.1
                    @Override // com.google.common.base.Function
                    public Map<C, V> apply(Map<C, V> map) {
                        return Synchronized.j(map, SynchronizedTable.this.mutex);
                    }
                }), this.mutex);
            }
            return mapJ;
        }

        @Override // com.google.common.collect.Table
        public int size() {
            int size;
            synchronized (this.mutex) {
                size = delegate().size();
            }
            return size;
        }

        @Override // com.google.common.collect.Table
        public Collection<V> values() {
            Collection<V> collection;
            synchronized (this.mutex) {
                collection = Synchronized.collection(delegate().values(), this.mutex);
            }
            return collection;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.collect.Synchronized.SynchronizedObject
        public Table<R, C, V> delegate() {
            return (Table) super.delegate();
        }
    }

    private Synchronized() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <E> Collection<E> collection(Collection<E> collection, @CheckForNull Object obj) {
        return new SynchronizedCollection(collection, obj);
    }

    static BiMap g(BiMap biMap, Object obj) {
        return ((biMap instanceof SynchronizedBiMap) || (biMap instanceof ImmutableBiMap)) ? biMap : new SynchronizedBiMap(biMap, obj, null);
    }

    static Deque h(Deque deque, Object obj) {
        return new SynchronizedDeque(deque, obj);
    }

    static ListMultimap i(ListMultimap listMultimap, Object obj) {
        return ((listMultimap instanceof SynchronizedListMultimap) || (listMultimap instanceof BaseImmutableMultimap)) ? listMultimap : new SynchronizedListMultimap(listMultimap, obj);
    }

    static Map j(Map map, Object obj) {
        return new SynchronizedMap(map, obj);
    }

    static Multimap k(Multimap multimap, Object obj) {
        return ((multimap instanceof SynchronizedMultimap) || (multimap instanceof BaseImmutableMultimap)) ? multimap : new SynchronizedMultimap(multimap, obj);
    }

    static Multiset l(Multiset multiset, Object obj) {
        return ((multiset instanceof SynchronizedMultiset) || (multiset instanceof ImmutableMultiset)) ? multiset : new SynchronizedMultiset(multiset, obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <E> List<E> list(List<E> list, @CheckForNull Object obj) {
        return list instanceof RandomAccess ? new SynchronizedRandomAccessList(list, obj) : new SynchronizedList(list, obj);
    }

    static NavigableMap m(NavigableMap navigableMap) {
        return n(navigableMap, null);
    }

    static NavigableMap n(NavigableMap navigableMap, Object obj) {
        return new SynchronizedNavigableMap(navigableMap, obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @CheckForNull
    @GwtIncompatible
    public static <K, V> Map.Entry<K, V> nullableSynchronizedEntry(@CheckForNull Map.Entry<K, V> entry, @CheckForNull Object obj) {
        if (entry == null) {
            return null;
        }
        return new SynchronizedEntry(entry, obj);
    }

    static NavigableSet o(NavigableSet navigableSet) {
        return p(navigableSet, null);
    }

    static NavigableSet p(NavigableSet navigableSet, Object obj) {
        return new SynchronizedNavigableSet(navigableSet, obj);
    }

    static Queue q(Queue queue, Object obj) {
        return queue instanceof SynchronizedQueue ? queue : new SynchronizedQueue(queue, obj);
    }

    static Set r(Set set, Object obj) {
        return new SynchronizedSet(set, obj);
    }

    static SetMultimap s(SetMultimap setMultimap, Object obj) {
        return ((setMultimap instanceof SynchronizedSetMultimap) || (setMultimap instanceof BaseImmutableMultimap)) ? setMultimap : new SynchronizedSetMultimap(setMultimap, obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <E> SortedSet<E> sortedSet(SortedSet<E> sortedSet, @CheckForNull Object obj) {
        return new SynchronizedSortedSet(sortedSet, obj);
    }

    static SortedMap t(SortedMap sortedMap, Object obj) {
        return new SynchronizedSortedMap(sortedMap, obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <E> Collection<E> typePreservingCollection(Collection<E> collection, @CheckForNull Object obj) {
        return collection instanceof SortedSet ? sortedSet((SortedSet) collection, obj) : collection instanceof Set ? r((Set) collection, obj) : collection instanceof List ? list((List) collection, obj) : collection(collection, obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <E> Set<E> typePreservingSet(Set<E> set, @CheckForNull Object obj) {
        return set instanceof SortedSet ? sortedSet((SortedSet) set, obj) : r(set, obj);
    }

    static SortedSetMultimap u(SortedSetMultimap sortedSetMultimap, Object obj) {
        return sortedSetMultimap instanceof SynchronizedSortedSetMultimap ? sortedSetMultimap : new SynchronizedSortedSetMultimap(sortedSetMultimap, obj);
    }

    static Table v(Table table, Object obj) {
        return new SynchronizedTable(table, obj);
    }

    static final class SynchronizedMultiset<E> extends SynchronizedCollection<E> implements Multiset<E> {
        private static final long serialVersionUID = 0;

        @CheckForNull
        transient Set<E> elementSet;

        @CheckForNull
        transient Set<Multiset.Entry<E>> entrySet;

        SynchronizedMultiset(Multiset<E> multiset, @CheckForNull Object obj) {
            super(multiset, obj);
        }

        @Override // com.google.common.collect.Multiset
        public int add(@ParametricNullness E e2, int i2) {
            int iAdd;
            synchronized (this.mutex) {
                iAdd = delegate().add(e2, i2);
            }
            return iAdd;
        }

        @Override // com.google.common.collect.Multiset
        public int count(@CheckForNull Object obj) {
            int iCount;
            synchronized (this.mutex) {
                iCount = delegate().count(obj);
            }
            return iCount;
        }

        @Override // com.google.common.collect.Multiset
        public Set<E> elementSet() {
            Set<E> set;
            synchronized (this.mutex) {
                try {
                    if (this.elementSet == null) {
                        this.elementSet = Synchronized.typePreservingSet(delegate().elementSet(), this.mutex);
                    }
                    set = this.elementSet;
                } catch (Throwable th) {
                    throw th;
                }
            }
            return set;
        }

        @Override // com.google.common.collect.Multiset
        public Set<Multiset.Entry<E>> entrySet() {
            Set<Multiset.Entry<E>> set;
            synchronized (this.mutex) {
                try {
                    if (this.entrySet == null) {
                        this.entrySet = Synchronized.typePreservingSet(delegate().entrySet(), this.mutex);
                    }
                    set = this.entrySet;
                } catch (Throwable th) {
                    throw th;
                }
            }
            return set;
        }

        @Override // java.util.Collection, com.google.common.collect.Multiset
        public boolean equals(@CheckForNull Object obj) {
            boolean zEquals;
            if (obj == this) {
                return true;
            }
            synchronized (this.mutex) {
                zEquals = delegate().equals(obj);
            }
            return zEquals;
        }

        @Override // java.util.Collection, com.google.common.collect.Multiset
        public int hashCode() {
            int iHashCode;
            synchronized (this.mutex) {
                iHashCode = delegate().hashCode();
            }
            return iHashCode;
        }

        @Override // com.google.common.collect.Multiset
        public int remove(@CheckForNull Object obj, int i2) {
            int iRemove;
            synchronized (this.mutex) {
                iRemove = delegate().remove(obj, i2);
            }
            return iRemove;
        }

        @Override // com.google.common.collect.Multiset
        public int setCount(@ParametricNullness E e2, int i2) {
            int count;
            synchronized (this.mutex) {
                count = delegate().setCount(e2, i2);
            }
            return count;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.collect.Synchronized.SynchronizedCollection, com.google.common.collect.Synchronized.SynchronizedObject
        public Multiset<E> delegate() {
            return (Multiset) super.delegate();
        }

        @Override // com.google.common.collect.Multiset
        public boolean setCount(@ParametricNullness E e2, int i2, int i3) {
            boolean count;
            synchronized (this.mutex) {
                count = delegate().setCount(e2, i2, i3);
            }
            return count;
        }
    }

    static class SynchronizedQueue<E> extends SynchronizedCollection<E> implements Queue<E> {
        private static final long serialVersionUID = 0;

        SynchronizedQueue(Queue<E> queue, @CheckForNull Object obj) {
            super(queue, obj);
        }

        @Override // java.util.Queue
        public E element() {
            E eElement;
            synchronized (this.mutex) {
                eElement = delegate().element();
            }
            return eElement;
        }

        @Override // java.util.Queue
        public boolean offer(E e2) {
            boolean zOffer;
            synchronized (this.mutex) {
                zOffer = delegate().offer(e2);
            }
            return zOffer;
        }

        @Override // java.util.Queue
        @CheckForNull
        public E peek() {
            E ePeek;
            synchronized (this.mutex) {
                ePeek = delegate().peek();
            }
            return ePeek;
        }

        @Override // java.util.Queue
        @CheckForNull
        public E poll() {
            E ePoll;
            synchronized (this.mutex) {
                ePoll = delegate().poll();
            }
            return ePoll;
        }

        @Override // java.util.Queue
        public E remove() {
            E eRemove;
            synchronized (this.mutex) {
                eRemove = delegate().remove();
            }
            return eRemove;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.collect.Synchronized.SynchronizedCollection, com.google.common.collect.Synchronized.SynchronizedObject
        public Queue<E> delegate() {
            return (Queue) super.delegate();
        }
    }

    static class SynchronizedSet<E> extends SynchronizedCollection<E> implements Set<E> {
        private static final long serialVersionUID = 0;

        SynchronizedSet(Set<E> set, @CheckForNull Object obj) {
            super(set, obj);
        }

        public boolean equals(@CheckForNull Object obj) {
            boolean zEquals;
            if (obj == this) {
                return true;
            }
            synchronized (this.mutex) {
                zEquals = delegate().equals(obj);
            }
            return zEquals;
        }

        @Override // java.util.Collection, java.util.Set
        public int hashCode() {
            int iHashCode;
            synchronized (this.mutex) {
                iHashCode = delegate().hashCode();
            }
            return iHashCode;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.collect.Synchronized.SynchronizedCollection, com.google.common.collect.Synchronized.SynchronizedObject
        public Set<E> delegate() {
            return (Set) super.delegate();
        }
    }

    static class SynchronizedSortedMap<K, V> extends SynchronizedMap<K, V> implements SortedMap<K, V> {
        private static final long serialVersionUID = 0;

        SynchronizedSortedMap(SortedMap<K, V> sortedMap, @CheckForNull Object obj) {
            super(sortedMap, obj);
        }

        @Override // java.util.SortedMap
        @CheckForNull
        public Comparator<? super K> comparator() {
            Comparator<? super K> comparator;
            synchronized (this.mutex) {
                comparator = delegate().comparator();
            }
            return comparator;
        }

        @Override // java.util.SortedMap
        public K firstKey() {
            K kFirstKey;
            synchronized (this.mutex) {
                kFirstKey = delegate().firstKey();
            }
            return kFirstKey;
        }

        public SortedMap<K, V> headMap(K k2) {
            SortedMap<K, V> sortedMapT;
            synchronized (this.mutex) {
                sortedMapT = Synchronized.t(delegate().headMap(k2), this.mutex);
            }
            return sortedMapT;
        }

        @Override // java.util.SortedMap
        public K lastKey() {
            K kLastKey;
            synchronized (this.mutex) {
                kLastKey = delegate().lastKey();
            }
            return kLastKey;
        }

        public SortedMap<K, V> subMap(K k2, K k3) {
            SortedMap<K, V> sortedMapT;
            synchronized (this.mutex) {
                sortedMapT = Synchronized.t(delegate().subMap(k2, k3), this.mutex);
            }
            return sortedMapT;
        }

        public SortedMap<K, V> tailMap(K k2) {
            SortedMap<K, V> sortedMapT;
            synchronized (this.mutex) {
                sortedMapT = Synchronized.t(delegate().tailMap(k2), this.mutex);
            }
            return sortedMapT;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.collect.Synchronized.SynchronizedMap, com.google.common.collect.Synchronized.SynchronizedObject
        public SortedMap<K, V> delegate() {
            return (SortedMap) super.delegate();
        }
    }

    static final class SynchronizedAsMapEntries<K, V> extends SynchronizedSet<Map.Entry<K, Collection<V>>> {
        private static final long serialVersionUID = 0;

        SynchronizedAsMapEntries(Set<Map.Entry<K, Collection<V>>> set, @CheckForNull Object obj) {
            super(set, obj);
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedCollection, java.util.Collection, java.util.Set
        public boolean contains(@CheckForNull Object obj) {
            boolean zL;
            synchronized (this.mutex) {
                zL = Maps.l(delegate(), obj);
            }
            return zL;
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedCollection, java.util.Collection, java.util.Set
        public boolean containsAll(Collection<?> collection) {
            boolean zB;
            synchronized (this.mutex) {
                zB = Collections2.b(delegate(), collection);
            }
            return zB;
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedSet, java.util.Collection, java.util.Set
        public boolean equals(@CheckForNull Object obj) {
            boolean zA;
            if (obj == this) {
                return true;
            }
            synchronized (this.mutex) {
                zA = Sets.a(delegate(), obj);
            }
            return zA;
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<Map.Entry<K, Collection<V>>> iterator() {
            return new TransformedIterator<Map.Entry<K, Collection<V>>, Map.Entry<K, Collection<V>>>(super.iterator()) { // from class: com.google.common.collect.Synchronized.SynchronizedAsMapEntries.1
                /* JADX INFO: Access modifiers changed from: package-private */
                @Override // com.google.common.collect.TransformedIterator
                /* renamed from: b, reason: merged with bridge method [inline-methods] */
                public Map.Entry a(final Map.Entry entry) {
                    return new ForwardingMapEntry<K, Collection<V>>(this) { // from class: com.google.common.collect.Synchronized.SynchronizedAsMapEntries.1.1

                        /* renamed from: b, reason: collision with root package name */
                        final /* synthetic */ AnonymousClass1 f14345b;

                        {
                            this.f14345b = this;
                        }

                        /* JADX INFO: Access modifiers changed from: protected */
                        @Override // com.google.common.collect.ForwardingMapEntry, com.google.common.collect.ForwardingObject
                        /* renamed from: a */
                        public Map.Entry delegate() {
                            return entry;
                        }

                        @Override // com.google.common.collect.ForwardingMapEntry, java.util.Map.Entry
                        public Collection<V> getValue() {
                            return Synchronized.typePreservingCollection((Collection) entry.getValue(), SynchronizedAsMapEntries.this.mutex);
                        }
                    };
                }
            };
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedCollection, java.util.Collection, java.util.Set
        public boolean remove(@CheckForNull Object obj) {
            boolean zW;
            synchronized (this.mutex) {
                zW = Maps.w(delegate(), obj);
            }
            return zW;
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedCollection, java.util.Collection, java.util.Set
        public boolean removeAll(Collection<?> collection) {
            boolean zRemoveAll;
            synchronized (this.mutex) {
                zRemoveAll = Iterators.removeAll(delegate().iterator(), collection);
            }
            return zRemoveAll;
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedCollection, java.util.Collection, java.util.Set
        public boolean retainAll(Collection<?> collection) {
            boolean zRetainAll;
            synchronized (this.mutex) {
                zRetainAll = Iterators.retainAll(delegate().iterator(), collection);
            }
            return zRetainAll;
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedCollection, java.util.Collection, java.util.Set
        public Object[] toArray() {
            Object[] objArrD;
            synchronized (this.mutex) {
                objArrD = ObjectArrays.d(delegate());
            }
            return objArrD;
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedCollection, java.util.Collection, java.util.Set
        public <T> T[] toArray(T[] tArr) {
            T[] tArr2;
            synchronized (this.mutex) {
                tArr2 = (T[]) ObjectArrays.e(delegate(), tArr);
            }
            return tArr2;
        }
    }

    static final class SynchronizedDeque<E> extends SynchronizedQueue<E> implements Deque<E> {
        private static final long serialVersionUID = 0;

        SynchronizedDeque(Deque<E> deque, @CheckForNull Object obj) {
            super(deque, obj);
        }

        @Override // java.util.Deque
        public void addFirst(E e2) {
            synchronized (this.mutex) {
                delegate().addFirst(e2);
            }
        }

        @Override // java.util.Deque
        public void addLast(E e2) {
            synchronized (this.mutex) {
                delegate().addLast(e2);
            }
        }

        @Override // java.util.Deque
        public Iterator<E> descendingIterator() {
            Iterator<E> itDescendingIterator;
            synchronized (this.mutex) {
                itDescendingIterator = delegate().descendingIterator();
            }
            return itDescendingIterator;
        }

        @Override // java.util.Deque
        public E getFirst() {
            E first;
            synchronized (this.mutex) {
                first = delegate().getFirst();
            }
            return first;
        }

        @Override // java.util.Deque
        public E getLast() {
            E last;
            synchronized (this.mutex) {
                last = delegate().getLast();
            }
            return last;
        }

        @Override // java.util.Deque
        public boolean offerFirst(E e2) {
            boolean zOfferFirst;
            synchronized (this.mutex) {
                zOfferFirst = delegate().offerFirst(e2);
            }
            return zOfferFirst;
        }

        @Override // java.util.Deque
        public boolean offerLast(E e2) {
            boolean zOfferLast;
            synchronized (this.mutex) {
                zOfferLast = delegate().offerLast(e2);
            }
            return zOfferLast;
        }

        @Override // java.util.Deque
        @CheckForNull
        public E peekFirst() {
            E ePeekFirst;
            synchronized (this.mutex) {
                ePeekFirst = delegate().peekFirst();
            }
            return ePeekFirst;
        }

        @Override // java.util.Deque
        @CheckForNull
        public E peekLast() {
            E ePeekLast;
            synchronized (this.mutex) {
                ePeekLast = delegate().peekLast();
            }
            return ePeekLast;
        }

        @Override // java.util.Deque
        @CheckForNull
        public E pollFirst() {
            E ePollFirst;
            synchronized (this.mutex) {
                ePollFirst = delegate().pollFirst();
            }
            return ePollFirst;
        }

        @Override // java.util.Deque
        @CheckForNull
        public E pollLast() {
            E ePollLast;
            synchronized (this.mutex) {
                ePollLast = delegate().pollLast();
            }
            return ePollLast;
        }

        @Override // java.util.Deque
        public E pop() {
            E ePop;
            synchronized (this.mutex) {
                ePop = delegate().pop();
            }
            return ePop;
        }

        @Override // java.util.Deque
        public void push(E e2) {
            synchronized (this.mutex) {
                delegate().push(e2);
            }
        }

        @Override // java.util.Deque
        public E removeFirst() {
            E eRemoveFirst;
            synchronized (this.mutex) {
                eRemoveFirst = delegate().removeFirst();
            }
            return eRemoveFirst;
        }

        @Override // java.util.Deque
        public boolean removeFirstOccurrence(@CheckForNull Object obj) {
            boolean zRemoveFirstOccurrence;
            synchronized (this.mutex) {
                zRemoveFirstOccurrence = delegate().removeFirstOccurrence(obj);
            }
            return zRemoveFirstOccurrence;
        }

        @Override // java.util.Deque
        public E removeLast() {
            E eRemoveLast;
            synchronized (this.mutex) {
                eRemoveLast = delegate().removeLast();
            }
            return eRemoveLast;
        }

        @Override // java.util.Deque
        public boolean removeLastOccurrence(@CheckForNull Object obj) {
            boolean zRemoveLastOccurrence;
            synchronized (this.mutex) {
                zRemoveLastOccurrence = delegate().removeLastOccurrence(obj);
            }
            return zRemoveLastOccurrence;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.collect.Synchronized.SynchronizedQueue, com.google.common.collect.Synchronized.SynchronizedCollection, com.google.common.collect.Synchronized.SynchronizedObject
        public Deque<E> delegate() {
            return (Deque) super.delegate();
        }
    }

    @GwtIncompatible
    @VisibleForTesting
    static final class SynchronizedNavigableMap<K, V> extends SynchronizedSortedMap<K, V> implements NavigableMap<K, V> {
        private static final long serialVersionUID = 0;

        @CheckForNull
        transient NavigableSet<K> descendingKeySet;

        @CheckForNull
        transient NavigableMap<K, V> descendingMap;

        @CheckForNull
        transient NavigableSet<K> navigableKeySet;

        SynchronizedNavigableMap(NavigableMap<K, V> navigableMap, @CheckForNull Object obj) {
            super(navigableMap, obj);
        }

        @Override // java.util.NavigableMap
        @CheckForNull
        public Map.Entry<K, V> ceilingEntry(K k2) {
            Map.Entry<K, V> entryNullableSynchronizedEntry;
            synchronized (this.mutex) {
                entryNullableSynchronizedEntry = Synchronized.nullableSynchronizedEntry(delegate().ceilingEntry(k2), this.mutex);
            }
            return entryNullableSynchronizedEntry;
        }

        @Override // java.util.NavigableMap
        @CheckForNull
        public K ceilingKey(K k2) {
            K kCeilingKey;
            synchronized (this.mutex) {
                kCeilingKey = delegate().ceilingKey(k2);
            }
            return kCeilingKey;
        }

        @Override // java.util.NavigableMap
        public NavigableSet<K> descendingKeySet() {
            synchronized (this.mutex) {
                try {
                    NavigableSet<K> navigableSet = this.descendingKeySet;
                    if (navigableSet != null) {
                        return navigableSet;
                    }
                    NavigableSet<K> navigableSetP = Synchronized.p(delegate().descendingKeySet(), this.mutex);
                    this.descendingKeySet = navigableSetP;
                    return navigableSetP;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // java.util.NavigableMap
        public NavigableMap<K, V> descendingMap() {
            synchronized (this.mutex) {
                try {
                    NavigableMap<K, V> navigableMap = this.descendingMap;
                    if (navigableMap != null) {
                        return navigableMap;
                    }
                    NavigableMap<K, V> navigableMapN = Synchronized.n(delegate().descendingMap(), this.mutex);
                    this.descendingMap = navigableMapN;
                    return navigableMapN;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // java.util.NavigableMap
        @CheckForNull
        public Map.Entry<K, V> firstEntry() {
            Map.Entry<K, V> entryNullableSynchronizedEntry;
            synchronized (this.mutex) {
                entryNullableSynchronizedEntry = Synchronized.nullableSynchronizedEntry(delegate().firstEntry(), this.mutex);
            }
            return entryNullableSynchronizedEntry;
        }

        @Override // java.util.NavigableMap
        @CheckForNull
        public Map.Entry<K, V> floorEntry(K k2) {
            Map.Entry<K, V> entryNullableSynchronizedEntry;
            synchronized (this.mutex) {
                entryNullableSynchronizedEntry = Synchronized.nullableSynchronizedEntry(delegate().floorEntry(k2), this.mutex);
            }
            return entryNullableSynchronizedEntry;
        }

        @Override // java.util.NavigableMap
        @CheckForNull
        public K floorKey(K k2) {
            K kFloorKey;
            synchronized (this.mutex) {
                kFloorKey = delegate().floorKey(k2);
            }
            return kFloorKey;
        }

        @Override // java.util.NavigableMap
        public NavigableMap<K, V> headMap(K k2, boolean z2) {
            NavigableMap<K, V> navigableMapN;
            synchronized (this.mutex) {
                navigableMapN = Synchronized.n(delegate().headMap(k2, z2), this.mutex);
            }
            return navigableMapN;
        }

        @Override // java.util.NavigableMap
        @CheckForNull
        public Map.Entry<K, V> higherEntry(K k2) {
            Map.Entry<K, V> entryNullableSynchronizedEntry;
            synchronized (this.mutex) {
                entryNullableSynchronizedEntry = Synchronized.nullableSynchronizedEntry(delegate().higherEntry(k2), this.mutex);
            }
            return entryNullableSynchronizedEntry;
        }

        @Override // java.util.NavigableMap
        @CheckForNull
        public K higherKey(K k2) {
            K kHigherKey;
            synchronized (this.mutex) {
                kHigherKey = delegate().higherKey(k2);
            }
            return kHigherKey;
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedMap, java.util.Map
        public Set<K> keySet() {
            return navigableKeySet();
        }

        @Override // java.util.NavigableMap
        @CheckForNull
        public Map.Entry<K, V> lastEntry() {
            Map.Entry<K, V> entryNullableSynchronizedEntry;
            synchronized (this.mutex) {
                entryNullableSynchronizedEntry = Synchronized.nullableSynchronizedEntry(delegate().lastEntry(), this.mutex);
            }
            return entryNullableSynchronizedEntry;
        }

        @Override // java.util.NavigableMap
        @CheckForNull
        public Map.Entry<K, V> lowerEntry(K k2) {
            Map.Entry<K, V> entryNullableSynchronizedEntry;
            synchronized (this.mutex) {
                entryNullableSynchronizedEntry = Synchronized.nullableSynchronizedEntry(delegate().lowerEntry(k2), this.mutex);
            }
            return entryNullableSynchronizedEntry;
        }

        @Override // java.util.NavigableMap
        @CheckForNull
        public K lowerKey(K k2) {
            K kLowerKey;
            synchronized (this.mutex) {
                kLowerKey = delegate().lowerKey(k2);
            }
            return kLowerKey;
        }

        @Override // java.util.NavigableMap
        public NavigableSet<K> navigableKeySet() {
            synchronized (this.mutex) {
                try {
                    NavigableSet<K> navigableSet = this.navigableKeySet;
                    if (navigableSet != null) {
                        return navigableSet;
                    }
                    NavigableSet<K> navigableSetP = Synchronized.p(delegate().navigableKeySet(), this.mutex);
                    this.navigableKeySet = navigableSetP;
                    return navigableSetP;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // java.util.NavigableMap
        @CheckForNull
        public Map.Entry<K, V> pollFirstEntry() {
            Map.Entry<K, V> entryNullableSynchronizedEntry;
            synchronized (this.mutex) {
                entryNullableSynchronizedEntry = Synchronized.nullableSynchronizedEntry(delegate().pollFirstEntry(), this.mutex);
            }
            return entryNullableSynchronizedEntry;
        }

        @Override // java.util.NavigableMap
        @CheckForNull
        public Map.Entry<K, V> pollLastEntry() {
            Map.Entry<K, V> entryNullableSynchronizedEntry;
            synchronized (this.mutex) {
                entryNullableSynchronizedEntry = Synchronized.nullableSynchronizedEntry(delegate().pollLastEntry(), this.mutex);
            }
            return entryNullableSynchronizedEntry;
        }

        @Override // java.util.NavigableMap
        public NavigableMap<K, V> subMap(K k2, boolean z2, K k3, boolean z3) {
            NavigableMap<K, V> navigableMapN;
            synchronized (this.mutex) {
                navigableMapN = Synchronized.n(delegate().subMap(k2, z2, k3, z3), this.mutex);
            }
            return navigableMapN;
        }

        @Override // java.util.NavigableMap
        public NavigableMap<K, V> tailMap(K k2, boolean z2) {
            NavigableMap<K, V> navigableMapN;
            synchronized (this.mutex) {
                navigableMapN = Synchronized.n(delegate().tailMap(k2, z2), this.mutex);
            }
            return navigableMapN;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.collect.Synchronized.SynchronizedSortedMap, com.google.common.collect.Synchronized.SynchronizedMap, com.google.common.collect.Synchronized.SynchronizedObject
        public NavigableMap<K, V> delegate() {
            return (NavigableMap) super.delegate();
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedSortedMap, java.util.SortedMap, java.util.NavigableMap
        public SortedMap<K, V> headMap(K k2) {
            return headMap(k2, false);
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedSortedMap, java.util.SortedMap, java.util.NavigableMap
        public SortedMap<K, V> subMap(K k2, K k3) {
            return subMap(k2, true, k3, false);
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedSortedMap, java.util.SortedMap, java.util.NavigableMap
        public SortedMap<K, V> tailMap(K k2) {
            return tailMap(k2, true);
        }
    }

    @GwtIncompatible
    @VisibleForTesting
    static final class SynchronizedNavigableSet<E> extends SynchronizedSortedSet<E> implements NavigableSet<E> {
        private static final long serialVersionUID = 0;

        @CheckForNull
        transient NavigableSet<E> descendingSet;

        SynchronizedNavigableSet(NavigableSet<E> navigableSet, @CheckForNull Object obj) {
            super(navigableSet, obj);
        }

        @Override // java.util.NavigableSet
        @CheckForNull
        public E ceiling(E e2) {
            E eCeiling;
            synchronized (this.mutex) {
                eCeiling = delegate().ceiling(e2);
            }
            return eCeiling;
        }

        @Override // java.util.NavigableSet
        public Iterator<E> descendingIterator() {
            return delegate().descendingIterator();
        }

        @Override // java.util.NavigableSet
        public NavigableSet<E> descendingSet() {
            synchronized (this.mutex) {
                try {
                    NavigableSet<E> navigableSet = this.descendingSet;
                    if (navigableSet != null) {
                        return navigableSet;
                    }
                    NavigableSet<E> navigableSetP = Synchronized.p(delegate().descendingSet(), this.mutex);
                    this.descendingSet = navigableSetP;
                    return navigableSetP;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // java.util.NavigableSet
        @CheckForNull
        public E floor(E e2) {
            E eFloor;
            synchronized (this.mutex) {
                eFloor = delegate().floor(e2);
            }
            return eFloor;
        }

        @Override // java.util.NavigableSet
        public NavigableSet<E> headSet(E e2, boolean z2) {
            NavigableSet<E> navigableSetP;
            synchronized (this.mutex) {
                navigableSetP = Synchronized.p(delegate().headSet(e2, z2), this.mutex);
            }
            return navigableSetP;
        }

        @Override // java.util.NavigableSet
        @CheckForNull
        public E higher(E e2) {
            E eHigher;
            synchronized (this.mutex) {
                eHigher = delegate().higher(e2);
            }
            return eHigher;
        }

        @Override // java.util.NavigableSet
        @CheckForNull
        public E lower(E e2) {
            E eLower;
            synchronized (this.mutex) {
                eLower = delegate().lower(e2);
            }
            return eLower;
        }

        @Override // java.util.NavigableSet
        @CheckForNull
        public E pollFirst() {
            E ePollFirst;
            synchronized (this.mutex) {
                ePollFirst = delegate().pollFirst();
            }
            return ePollFirst;
        }

        @Override // java.util.NavigableSet
        @CheckForNull
        public E pollLast() {
            E ePollLast;
            synchronized (this.mutex) {
                ePollLast = delegate().pollLast();
            }
            return ePollLast;
        }

        @Override // java.util.NavigableSet
        public NavigableSet<E> subSet(E e2, boolean z2, E e3, boolean z3) {
            NavigableSet<E> navigableSetP;
            synchronized (this.mutex) {
                navigableSetP = Synchronized.p(delegate().subSet(e2, z2, e3, z3), this.mutex);
            }
            return navigableSetP;
        }

        @Override // java.util.NavigableSet
        public NavigableSet<E> tailSet(E e2, boolean z2) {
            NavigableSet<E> navigableSetP;
            synchronized (this.mutex) {
                navigableSetP = Synchronized.p(delegate().tailSet(e2, z2), this.mutex);
            }
            return navigableSetP;
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedSortedSet, java.util.SortedSet, java.util.NavigableSet
        public SortedSet<E> headSet(E e2) {
            return headSet(e2, false);
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedSortedSet, java.util.SortedSet, java.util.NavigableSet
        public SortedSet<E> tailSet(E e2) {
            return tailSet(e2, true);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.collect.Synchronized.SynchronizedSortedSet, com.google.common.collect.Synchronized.SynchronizedSet, com.google.common.collect.Synchronized.SynchronizedCollection, com.google.common.collect.Synchronized.SynchronizedObject
        public NavigableSet<E> delegate() {
            return (NavigableSet) super.delegate();
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedSortedSet, java.util.SortedSet, java.util.NavigableSet
        public SortedSet<E> subSet(E e2, E e3) {
            return subSet(e2, true, e3, false);
        }
    }

    static class SynchronizedSortedSet<E> extends SynchronizedSet<E> implements SortedSet<E> {
        private static final long serialVersionUID = 0;

        SynchronizedSortedSet(SortedSet<E> sortedSet, @CheckForNull Object obj) {
            super(sortedSet, obj);
        }

        @Override // java.util.SortedSet
        @CheckForNull
        public Comparator<? super E> comparator() {
            Comparator<? super E> comparator;
            synchronized (this.mutex) {
                comparator = delegate().comparator();
            }
            return comparator;
        }

        @Override // java.util.SortedSet
        public E first() {
            E eFirst;
            synchronized (this.mutex) {
                eFirst = delegate().first();
            }
            return eFirst;
        }

        public SortedSet<E> headSet(E e2) {
            SortedSet<E> sortedSet;
            synchronized (this.mutex) {
                sortedSet = Synchronized.sortedSet(delegate().headSet(e2), this.mutex);
            }
            return sortedSet;
        }

        @Override // java.util.SortedSet
        public E last() {
            E eLast;
            synchronized (this.mutex) {
                eLast = delegate().last();
            }
            return eLast;
        }

        public SortedSet<E> subSet(E e2, E e3) {
            SortedSet<E> sortedSet;
            synchronized (this.mutex) {
                sortedSet = Synchronized.sortedSet(delegate().subSet(e2, e3), this.mutex);
            }
            return sortedSet;
        }

        public SortedSet<E> tailSet(E e2) {
            SortedSet<E> sortedSet;
            synchronized (this.mutex) {
                sortedSet = Synchronized.sortedSet(delegate().tailSet(e2), this.mutex);
            }
            return sortedSet;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.collect.Synchronized.SynchronizedSet, com.google.common.collect.Synchronized.SynchronizedCollection, com.google.common.collect.Synchronized.SynchronizedObject
        public SortedSet<E> delegate() {
            return (SortedSet) super.delegate();
        }
    }
}
