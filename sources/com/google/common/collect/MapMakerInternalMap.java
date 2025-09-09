package com.google.common.collect;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.J2ktIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Equivalence;
import com.google.common.base.Preconditions;
import com.google.common.collect.MapMaker;
import com.google.common.collect.MapMakerInternalMap.InternalEntry;
import com.google.common.collect.MapMakerInternalMap.Segment;
import com.google.common.primitives.Ints;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.concurrent.GuardedBy;
import com.google.errorprone.annotations.concurrent.LazyInit;
import com.google.j2objc.annotations.Weak;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.locks.ReentrantLock;
import javax.annotation.CheckForNull;

@J2ktIncompatible
@GwtIncompatible
/* loaded from: classes3.dex */
class MapMakerInternalMap<K, V, E extends InternalEntry<K, V, E>, S extends Segment<K, V, E, S>> extends AbstractMap<K, V> implements ConcurrentMap<K, V>, Serializable {
    static final int CONTAINS_VALUE_RETRIES = 3;
    static final int DRAIN_MAX = 16;
    static final int DRAIN_THRESHOLD = 63;
    static final int MAXIMUM_CAPACITY = 1073741824;
    static final int MAX_SEGMENTS = 65536;
    static final WeakValueReference<Object, Object, DummyInternalEntry> UNSET_WEAK_VALUE_REFERENCE = new WeakValueReference<Object, Object, DummyInternalEntry>() { // from class: com.google.common.collect.MapMakerInternalMap.1
        @Override // com.google.common.collect.MapMakerInternalMap.WeakValueReference
        public void clear() {
        }

        @Override // com.google.common.collect.MapMakerInternalMap.WeakValueReference
        public WeakValueReference<Object, Object, DummyInternalEntry> copyFor(ReferenceQueue<Object> referenceQueue, DummyInternalEntry dummyInternalEntry) {
            return this;
        }

        @Override // com.google.common.collect.MapMakerInternalMap.WeakValueReference
        @CheckForNull
        public Object get() {
            return null;
        }

        @Override // com.google.common.collect.MapMakerInternalMap.WeakValueReference
        @CheckForNull
        public DummyInternalEntry getEntry() {
            return null;
        }
    };
    private static final long serialVersionUID = 5;
    final int concurrencyLevel;
    final transient InternalEntryHelper<K, V, E, S> entryHelper;

    @CheckForNull
    @LazyInit
    transient Set<Map.Entry<K, V>> entrySet;
    final Equivalence<Object> keyEquivalence;

    @CheckForNull
    @LazyInit
    transient Set<K> keySet;
    final transient int segmentMask;
    final transient int segmentShift;
    final transient Segment<K, V, E, S>[] segments;

    @CheckForNull
    @LazyInit
    transient Collection<V> values;

    static abstract class AbstractStrongKeyEntry<K, V, E extends InternalEntry<K, V, E>> implements InternalEntry<K, V, E> {

        /* renamed from: a, reason: collision with root package name */
        final Object f14140a;

        /* renamed from: b, reason: collision with root package name */
        final int f14141b;

        AbstractStrongKeyEntry(Object obj, int i2) {
            this.f14140a = obj;
            this.f14141b = i2;
        }

        @Override // com.google.common.collect.MapMakerInternalMap.InternalEntry
        public final int getHash() {
            return this.f14141b;
        }

        @Override // com.google.common.collect.MapMakerInternalMap.InternalEntry
        public final K getKey() {
            return (K) this.f14140a;
        }

        @Override // com.google.common.collect.MapMakerInternalMap.InternalEntry
        @CheckForNull
        public E getNext() {
            return null;
        }
    }

    static abstract class AbstractWeakKeyEntry<K, V, E extends InternalEntry<K, V, E>> extends WeakReference<K> implements InternalEntry<K, V, E> {

        /* renamed from: a, reason: collision with root package name */
        final int f14142a;

        AbstractWeakKeyEntry(ReferenceQueue referenceQueue, Object obj, int i2) {
            super(obj, referenceQueue);
            this.f14142a = i2;
        }

        @Override // com.google.common.collect.MapMakerInternalMap.InternalEntry
        public final int getHash() {
            return this.f14142a;
        }

        @Override // com.google.common.collect.MapMakerInternalMap.InternalEntry
        public final K getKey() {
            return get();
        }

        @Override // com.google.common.collect.MapMakerInternalMap.InternalEntry
        @CheckForNull
        public E getNext() {
            return null;
        }
    }

    static final class CleanupMapTask implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        final WeakReference f14143a;

        public CleanupMapTask(MapMakerInternalMap<?, ?, ?, ?> mapMakerInternalMap) {
            this.f14143a = new WeakReference(mapMakerInternalMap);
        }

        @Override // java.lang.Runnable
        public void run() {
            MapMakerInternalMap mapMakerInternalMap = (MapMakerInternalMap) this.f14143a.get();
            if (mapMakerInternalMap == null) {
                throw new CancellationException();
            }
            for (Segment<K, V, E, S> segment : mapMakerInternalMap.segments) {
                segment.runCleanup();
            }
        }
    }

    static final class DummyInternalEntry implements InternalEntry<Object, Object, DummyInternalEntry> {
        private DummyInternalEntry() {
            throw new AssertionError();
        }

        @Override // com.google.common.collect.MapMakerInternalMap.InternalEntry
        public int getHash() {
            throw new AssertionError();
        }

        @Override // com.google.common.collect.MapMakerInternalMap.InternalEntry
        public Object getKey() {
            throw new AssertionError();
        }

        @Override // com.google.common.collect.MapMakerInternalMap.InternalEntry
        public Object getValue() {
            throw new AssertionError();
        }

        @Override // com.google.common.collect.MapMakerInternalMap.InternalEntry
        public DummyInternalEntry getNext() {
            throw new AssertionError();
        }
    }

    final class EntryIterator extends MapMakerInternalMap<K, V, E, S>.HashIterator<Map.Entry<K, V>> {
        EntryIterator() {
            super();
        }

        @Override // com.google.common.collect.MapMakerInternalMap.HashIterator, java.util.Iterator
        public Map.Entry<K, V> next() {
            return c();
        }
    }

    final class EntrySet extends AbstractSet<Map.Entry<K, V>> {
        EntrySet() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            MapMakerInternalMap.this.clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            Map.Entry entry;
            Object key;
            Object obj2;
            return (obj instanceof Map.Entry) && (key = (entry = (Map.Entry) obj).getKey()) != null && (obj2 = MapMakerInternalMap.this.get(key)) != null && MapMakerInternalMap.this.valueEquivalence().equivalent(entry.getValue(), obj2);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean isEmpty() {
            return MapMakerInternalMap.this.isEmpty();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<Map.Entry<K, V>> iterator() {
            return new EntryIterator();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            Map.Entry entry;
            Object key;
            return (obj instanceof Map.Entry) && (key = (entry = (Map.Entry) obj).getKey()) != null && MapMakerInternalMap.this.remove(key, entry.getValue());
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return MapMakerInternalMap.this.size();
        }
    }

    abstract class HashIterator<T> implements Iterator<T> {

        /* renamed from: a, reason: collision with root package name */
        int f14146a;

        /* renamed from: b, reason: collision with root package name */
        int f14147b = -1;

        /* renamed from: c, reason: collision with root package name */
        Segment f14148c;

        /* renamed from: d, reason: collision with root package name */
        AtomicReferenceArray f14149d;

        /* renamed from: e, reason: collision with root package name */
        InternalEntry f14150e;

        /* renamed from: f, reason: collision with root package name */
        WriteThroughEntry f14151f;

        /* renamed from: g, reason: collision with root package name */
        WriteThroughEntry f14152g;

        HashIterator() {
            this.f14146a = MapMakerInternalMap.this.segments.length - 1;
            a();
        }

        final void a() {
            this.f14151f = null;
            if (d() || e()) {
                return;
            }
            while (true) {
                int i2 = this.f14146a;
                if (i2 < 0) {
                    return;
                }
                Segment<K, V, E, S>[] segmentArr = MapMakerInternalMap.this.segments;
                this.f14146a = i2 - 1;
                Segment<K, V, E, S> segment = segmentArr[i2];
                this.f14148c = segment;
                if (segment.count != 0) {
                    this.f14149d = this.f14148c.table;
                    this.f14147b = r0.length() - 1;
                    if (e()) {
                        return;
                    }
                }
            }
        }

        boolean b(InternalEntry internalEntry) {
            try {
                Object key = internalEntry.getKey();
                Object liveValue = MapMakerInternalMap.this.getLiveValue(internalEntry);
                if (liveValue == null) {
                    this.f14148c.postReadCleanup();
                    return false;
                }
                this.f14151f = new WriteThroughEntry(key, liveValue);
                this.f14148c.postReadCleanup();
                return true;
            } catch (Throwable th) {
                this.f14148c.postReadCleanup();
                throw th;
            }
        }

        WriteThroughEntry c() {
            WriteThroughEntry writeThroughEntry = this.f14151f;
            if (writeThroughEntry == null) {
                throw new NoSuchElementException();
            }
            this.f14152g = writeThroughEntry;
            a();
            return this.f14152g;
        }

        boolean d() {
            InternalEntry internalEntry = this.f14150e;
            if (internalEntry == null) {
                return false;
            }
            while (true) {
                this.f14150e = internalEntry.getNext();
                InternalEntry internalEntry2 = this.f14150e;
                if (internalEntry2 == null) {
                    return false;
                }
                if (b(internalEntry2)) {
                    return true;
                }
                internalEntry = this.f14150e;
            }
        }

        boolean e() {
            while (true) {
                int i2 = this.f14147b;
                if (i2 < 0) {
                    return false;
                }
                AtomicReferenceArray atomicReferenceArray = this.f14149d;
                this.f14147b = i2 - 1;
                InternalEntry internalEntry = (InternalEntry) atomicReferenceArray.get(i2);
                this.f14150e = internalEntry;
                if (internalEntry != null && (b(internalEntry) || d())) {
                    return true;
                }
            }
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.f14151f != null;
        }

        @Override // java.util.Iterator
        public abstract T next();

        @Override // java.util.Iterator
        public void remove() {
            CollectPreconditions.e(this.f14152g != null);
            MapMakerInternalMap.this.remove(this.f14152g.getKey());
            this.f14152g = null;
        }
    }

    interface InternalEntry<K, V, E extends InternalEntry<K, V, E>> {
        int getHash();

        K getKey();

        E getNext();

        V getValue();
    }

    interface InternalEntryHelper<K, V, E extends InternalEntry<K, V, E>, S extends Segment<K, V, E, S>> {
        E copy(S s2, E e2, @CheckForNull E e3);

        Strength keyStrength();

        E newEntry(S s2, K k2, int i2, @CheckForNull E e2);

        S newSegment(MapMakerInternalMap<K, V, E, S> mapMakerInternalMap, int i2);

        void setValue(S s2, E e2, V v2);

        Strength valueStrength();
    }

    final class KeyIterator extends MapMakerInternalMap<K, V, E, S>.HashIterator<K> {
        KeyIterator() {
            super();
        }

        @Override // com.google.common.collect.MapMakerInternalMap.HashIterator, java.util.Iterator
        public K next() {
            return (K) c().getKey();
        }
    }

    final class KeySet extends AbstractSet<K> {
        KeySet() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            MapMakerInternalMap.this.clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            return MapMakerInternalMap.this.containsKey(obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean isEmpty() {
            return MapMakerInternalMap.this.isEmpty();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<K> iterator() {
            return new KeyIterator();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            return MapMakerInternalMap.this.remove(obj) != null;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return MapMakerInternalMap.this.size();
        }
    }

    private static final class SerializationProxy<K, V> extends AbstractSerializationProxy<K, V> {
        private static final long serialVersionUID = 3;

        SerializationProxy(Strength strength, Strength strength2, Equivalence<Object> equivalence, Equivalence<Object> equivalence2, int i2, ConcurrentMap<K, V> concurrentMap) {
            super(strength, strength2, equivalence, equivalence2, i2, concurrentMap);
        }

        @J2ktIncompatible
        private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
            objectInputStream.defaultReadObject();
            this.delegate = readMapMaker(objectInputStream).makeMap();
            readEntries(objectInputStream);
        }

        private Object readResolve() {
            return this.delegate;
        }

        private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
            objectOutputStream.defaultWriteObject();
            writeMapTo(objectOutputStream);
        }
    }

    enum Strength {
        STRONG { // from class: com.google.common.collect.MapMakerInternalMap.Strength.1
            @Override // com.google.common.collect.MapMakerInternalMap.Strength
            Equivalence<Object> defaultEquivalence() {
                return Equivalence.equals();
            }
        },
        WEAK { // from class: com.google.common.collect.MapMakerInternalMap.Strength.2
            @Override // com.google.common.collect.MapMakerInternalMap.Strength
            Equivalence<Object> defaultEquivalence() {
                return Equivalence.identity();
            }
        };

        abstract Equivalence<Object> defaultEquivalence();
    }

    static class StrongKeyDummyValueEntry<K> extends AbstractStrongKeyEntry<K, MapMaker.Dummy, StrongKeyDummyValueEntry<K>> implements StrongValueEntry<K, MapMaker.Dummy, StrongKeyDummyValueEntry<K>> {

        static final class Helper<K> implements InternalEntryHelper<K, MapMaker.Dummy, StrongKeyDummyValueEntry<K>, StrongKeyDummyValueSegment<K>> {
            private static final Helper<?> INSTANCE = new Helper<>();

            Helper() {
            }

            static Helper a() {
                return INSTANCE;
            }

            @Override // com.google.common.collect.MapMakerInternalMap.InternalEntryHelper
            public Strength keyStrength() {
                return Strength.STRONG;
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.google.common.collect.MapMakerInternalMap.InternalEntryHelper
            public /* bridge */ /* synthetic */ InternalEntry newEntry(Segment segment, Object obj, int i2, @CheckForNull InternalEntry internalEntry) {
                return newEntry((StrongKeyDummyValueSegment<StrongKeyDummyValueSegment<K>>) segment, (StrongKeyDummyValueSegment<K>) obj, i2, (StrongKeyDummyValueEntry<StrongKeyDummyValueSegment<K>>) internalEntry);
            }

            @Override // com.google.common.collect.MapMakerInternalMap.InternalEntryHelper
            public void setValue(StrongKeyDummyValueSegment<K> strongKeyDummyValueSegment, StrongKeyDummyValueEntry<K> strongKeyDummyValueEntry, MapMaker.Dummy dummy) {
            }

            @Override // com.google.common.collect.MapMakerInternalMap.InternalEntryHelper
            public Strength valueStrength() {
                return Strength.STRONG;
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.google.common.collect.MapMakerInternalMap.InternalEntryHelper
            public StrongKeyDummyValueEntry<K> copy(StrongKeyDummyValueSegment<K> strongKeyDummyValueSegment, StrongKeyDummyValueEntry<K> strongKeyDummyValueEntry, @CheckForNull StrongKeyDummyValueEntry<K> strongKeyDummyValueEntry2) {
                return newEntry((StrongKeyDummyValueSegment<StrongKeyDummyValueSegment<K>>) strongKeyDummyValueSegment, (StrongKeyDummyValueSegment<K>) strongKeyDummyValueEntry.f14140a, strongKeyDummyValueEntry.f14141b, (StrongKeyDummyValueEntry<StrongKeyDummyValueSegment<K>>) strongKeyDummyValueEntry2);
            }

            public StrongKeyDummyValueEntry<K> newEntry(StrongKeyDummyValueSegment<K> strongKeyDummyValueSegment, K k2, int i2, @CheckForNull StrongKeyDummyValueEntry<K> strongKeyDummyValueEntry) {
                return strongKeyDummyValueEntry == null ? new StrongKeyDummyValueEntry<>(k2, i2) : new LinkedStrongKeyDummyValueEntry(k2, i2, strongKeyDummyValueEntry);
            }

            @Override // com.google.common.collect.MapMakerInternalMap.InternalEntryHelper
            public StrongKeyDummyValueSegment<K> newSegment(MapMakerInternalMap<K, MapMaker.Dummy, StrongKeyDummyValueEntry<K>, StrongKeyDummyValueSegment<K>> mapMakerInternalMap, int i2) {
                return new StrongKeyDummyValueSegment<>(mapMakerInternalMap, i2);
            }
        }

        private static final class LinkedStrongKeyDummyValueEntry<K> extends StrongKeyDummyValueEntry<K> {
            private final StrongKeyDummyValueEntry<K> next;

            LinkedStrongKeyDummyValueEntry(Object obj, int i2, StrongKeyDummyValueEntry strongKeyDummyValueEntry) {
                super(obj, i2);
                this.next = strongKeyDummyValueEntry;
            }

            @Override // com.google.common.collect.MapMakerInternalMap.StrongKeyDummyValueEntry, com.google.common.collect.MapMakerInternalMap.InternalEntry
            public /* bridge */ /* synthetic */ Object getValue() {
                return super.getValue();
            }

            @Override // com.google.common.collect.MapMakerInternalMap.AbstractStrongKeyEntry, com.google.common.collect.MapMakerInternalMap.InternalEntry
            public StrongKeyDummyValueEntry<K> getNext() {
                return this.next;
            }
        }

        private StrongKeyDummyValueEntry(K k2, int i2) {
            super(k2, i2);
        }

        @Override // com.google.common.collect.MapMakerInternalMap.InternalEntry
        public final MapMaker.Dummy getValue() {
            return MapMaker.Dummy.VALUE;
        }
    }

    static final class StrongKeyDummyValueSegment<K> extends Segment<K, MapMaker.Dummy, StrongKeyDummyValueEntry<K>, StrongKeyDummyValueSegment<K>> {
        StrongKeyDummyValueSegment(MapMakerInternalMap<K, MapMaker.Dummy, StrongKeyDummyValueEntry<K>, StrongKeyDummyValueSegment<K>> mapMakerInternalMap, int i2) {
            super(mapMakerInternalMap, i2);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.collect.MapMakerInternalMap.Segment
        public StrongKeyDummyValueSegment<K> self() {
            return this;
        }

        @Override // com.google.common.collect.MapMakerInternalMap.Segment
        public StrongKeyDummyValueEntry<K> castForTesting(InternalEntry<K, MapMaker.Dummy, ?> internalEntry) {
            return (StrongKeyDummyValueEntry) internalEntry;
        }
    }

    static class StrongKeyStrongValueEntry<K, V> extends AbstractStrongKeyEntry<K, V, StrongKeyStrongValueEntry<K, V>> implements StrongValueEntry<K, V, StrongKeyStrongValueEntry<K, V>> {

        @CheckForNull
        private volatile V value;

        static final class Helper<K, V> implements InternalEntryHelper<K, V, StrongKeyStrongValueEntry<K, V>, StrongKeyStrongValueSegment<K, V>> {
            private static final Helper<?, ?> INSTANCE = new Helper<>();

            Helper() {
            }

            static Helper a() {
                return INSTANCE;
            }

            @Override // com.google.common.collect.MapMakerInternalMap.InternalEntryHelper
            public Strength keyStrength() {
                return Strength.STRONG;
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.google.common.collect.MapMakerInternalMap.InternalEntryHelper
            public /* bridge */ /* synthetic */ InternalEntry newEntry(Segment segment, Object obj, int i2, @CheckForNull InternalEntry internalEntry) {
                return newEntry((StrongKeyStrongValueSegment<StrongKeyStrongValueSegment<K, V>, V>) segment, (StrongKeyStrongValueSegment<K, V>) obj, i2, (StrongKeyStrongValueEntry<StrongKeyStrongValueSegment<K, V>, V>) internalEntry);
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.google.common.collect.MapMakerInternalMap.InternalEntryHelper
            public /* bridge */ /* synthetic */ void setValue(Segment segment, InternalEntry internalEntry, Object obj) {
                setValue((StrongKeyStrongValueSegment<K, StrongKeyStrongValueEntry<K, V>>) segment, (StrongKeyStrongValueEntry<K, StrongKeyStrongValueEntry<K, V>>) internalEntry, (StrongKeyStrongValueEntry<K, V>) obj);
            }

            @Override // com.google.common.collect.MapMakerInternalMap.InternalEntryHelper
            public Strength valueStrength() {
                return Strength.STRONG;
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.google.common.collect.MapMakerInternalMap.InternalEntryHelper
            public StrongKeyStrongValueEntry<K, V> copy(StrongKeyStrongValueSegment<K, V> strongKeyStrongValueSegment, StrongKeyStrongValueEntry<K, V> strongKeyStrongValueEntry, @CheckForNull StrongKeyStrongValueEntry<K, V> strongKeyStrongValueEntry2) {
                StrongKeyStrongValueEntry<K, V> strongKeyStrongValueEntryNewEntry = newEntry((StrongKeyStrongValueSegment<StrongKeyStrongValueSegment<K, V>, V>) strongKeyStrongValueSegment, (StrongKeyStrongValueSegment<K, V>) strongKeyStrongValueEntry.f14140a, strongKeyStrongValueEntry.f14141b, (StrongKeyStrongValueEntry<StrongKeyStrongValueSegment<K, V>, V>) strongKeyStrongValueEntry2);
                ((StrongKeyStrongValueEntry) strongKeyStrongValueEntryNewEntry).value = ((StrongKeyStrongValueEntry) strongKeyStrongValueEntry).value;
                return strongKeyStrongValueEntryNewEntry;
            }

            public StrongKeyStrongValueEntry<K, V> newEntry(StrongKeyStrongValueSegment<K, V> strongKeyStrongValueSegment, K k2, int i2, @CheckForNull StrongKeyStrongValueEntry<K, V> strongKeyStrongValueEntry) {
                return strongKeyStrongValueEntry == null ? new StrongKeyStrongValueEntry<>(k2, i2) : new LinkedStrongKeyStrongValueEntry(k2, i2, strongKeyStrongValueEntry);
            }

            @Override // com.google.common.collect.MapMakerInternalMap.InternalEntryHelper
            public StrongKeyStrongValueSegment<K, V> newSegment(MapMakerInternalMap<K, V, StrongKeyStrongValueEntry<K, V>, StrongKeyStrongValueSegment<K, V>> mapMakerInternalMap, int i2) {
                return new StrongKeyStrongValueSegment<>(mapMakerInternalMap, i2);
            }

            public void setValue(StrongKeyStrongValueSegment<K, V> strongKeyStrongValueSegment, StrongKeyStrongValueEntry<K, V> strongKeyStrongValueEntry, V v2) {
                ((StrongKeyStrongValueEntry) strongKeyStrongValueEntry).value = v2;
            }
        }

        private static final class LinkedStrongKeyStrongValueEntry<K, V> extends StrongKeyStrongValueEntry<K, V> {
            private final StrongKeyStrongValueEntry<K, V> next;

            LinkedStrongKeyStrongValueEntry(Object obj, int i2, StrongKeyStrongValueEntry strongKeyStrongValueEntry) {
                super(obj, i2);
                this.next = strongKeyStrongValueEntry;
            }

            @Override // com.google.common.collect.MapMakerInternalMap.AbstractStrongKeyEntry, com.google.common.collect.MapMakerInternalMap.InternalEntry
            public StrongKeyStrongValueEntry<K, V> getNext() {
                return this.next;
            }
        }

        @Override // com.google.common.collect.MapMakerInternalMap.InternalEntry
        @CheckForNull
        public final V getValue() {
            return this.value;
        }

        private StrongKeyStrongValueEntry(K k2, int i2) {
            super(k2, i2);
            this.value = null;
        }
    }

    static final class StrongKeyStrongValueSegment<K, V> extends Segment<K, V, StrongKeyStrongValueEntry<K, V>, StrongKeyStrongValueSegment<K, V>> {
        StrongKeyStrongValueSegment(MapMakerInternalMap<K, V, StrongKeyStrongValueEntry<K, V>, StrongKeyStrongValueSegment<K, V>> mapMakerInternalMap, int i2) {
            super(mapMakerInternalMap, i2);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.collect.MapMakerInternalMap.Segment
        public StrongKeyStrongValueSegment<K, V> self() {
            return this;
        }

        @Override // com.google.common.collect.MapMakerInternalMap.Segment
        @CheckForNull
        public StrongKeyStrongValueEntry<K, V> castForTesting(@CheckForNull InternalEntry<K, V, ?> internalEntry) {
            return (StrongKeyStrongValueEntry) internalEntry;
        }
    }

    static class StrongKeyWeakValueEntry<K, V> extends AbstractStrongKeyEntry<K, V, StrongKeyWeakValueEntry<K, V>> implements WeakValueEntry<K, V, StrongKeyWeakValueEntry<K, V>> {
        private volatile WeakValueReference<K, V, StrongKeyWeakValueEntry<K, V>> valueReference;

        static final class Helper<K, V> implements InternalEntryHelper<K, V, StrongKeyWeakValueEntry<K, V>, StrongKeyWeakValueSegment<K, V>> {
            private static final Helper<?, ?> INSTANCE = new Helper<>();

            Helper() {
            }

            static Helper a() {
                return INSTANCE;
            }

            @Override // com.google.common.collect.MapMakerInternalMap.InternalEntryHelper
            public Strength keyStrength() {
                return Strength.STRONG;
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.google.common.collect.MapMakerInternalMap.InternalEntryHelper
            public /* bridge */ /* synthetic */ InternalEntry newEntry(Segment segment, Object obj, int i2, @CheckForNull InternalEntry internalEntry) {
                return newEntry((StrongKeyWeakValueSegment<StrongKeyWeakValueSegment<K, V>, V>) segment, (StrongKeyWeakValueSegment<K, V>) obj, i2, (StrongKeyWeakValueEntry<StrongKeyWeakValueSegment<K, V>, V>) internalEntry);
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.google.common.collect.MapMakerInternalMap.InternalEntryHelper
            public /* bridge */ /* synthetic */ void setValue(Segment segment, InternalEntry internalEntry, Object obj) {
                setValue((StrongKeyWeakValueSegment<K, StrongKeyWeakValueEntry<K, V>>) segment, (StrongKeyWeakValueEntry<K, StrongKeyWeakValueEntry<K, V>>) internalEntry, (StrongKeyWeakValueEntry<K, V>) obj);
            }

            @Override // com.google.common.collect.MapMakerInternalMap.InternalEntryHelper
            public Strength valueStrength() {
                return Strength.WEAK;
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.google.common.collect.MapMakerInternalMap.InternalEntryHelper
            @CheckForNull
            public StrongKeyWeakValueEntry<K, V> copy(StrongKeyWeakValueSegment<K, V> strongKeyWeakValueSegment, StrongKeyWeakValueEntry<K, V> strongKeyWeakValueEntry, @CheckForNull StrongKeyWeakValueEntry<K, V> strongKeyWeakValueEntry2) {
                if (Segment.isCollected(strongKeyWeakValueEntry)) {
                    return null;
                }
                StrongKeyWeakValueEntry<K, V> strongKeyWeakValueEntryNewEntry = newEntry((StrongKeyWeakValueSegment<StrongKeyWeakValueSegment<K, V>, V>) strongKeyWeakValueSegment, (StrongKeyWeakValueSegment<K, V>) strongKeyWeakValueEntry.f14140a, strongKeyWeakValueEntry.f14141b, (StrongKeyWeakValueEntry<StrongKeyWeakValueSegment<K, V>, V>) strongKeyWeakValueEntry2);
                ((StrongKeyWeakValueEntry) strongKeyWeakValueEntryNewEntry).valueReference = ((StrongKeyWeakValueEntry) strongKeyWeakValueEntry).valueReference.copyFor(((StrongKeyWeakValueSegment) strongKeyWeakValueSegment).queueForValues, strongKeyWeakValueEntryNewEntry);
                return strongKeyWeakValueEntryNewEntry;
            }

            public StrongKeyWeakValueEntry<K, V> newEntry(StrongKeyWeakValueSegment<K, V> strongKeyWeakValueSegment, K k2, int i2, @CheckForNull StrongKeyWeakValueEntry<K, V> strongKeyWeakValueEntry) {
                return strongKeyWeakValueEntry == null ? new StrongKeyWeakValueEntry<>(k2, i2) : new LinkedStrongKeyWeakValueEntry(k2, i2, strongKeyWeakValueEntry);
            }

            @Override // com.google.common.collect.MapMakerInternalMap.InternalEntryHelper
            public StrongKeyWeakValueSegment<K, V> newSegment(MapMakerInternalMap<K, V, StrongKeyWeakValueEntry<K, V>, StrongKeyWeakValueSegment<K, V>> mapMakerInternalMap, int i2) {
                return new StrongKeyWeakValueSegment<>(mapMakerInternalMap, i2);
            }

            public void setValue(StrongKeyWeakValueSegment<K, V> strongKeyWeakValueSegment, StrongKeyWeakValueEntry<K, V> strongKeyWeakValueEntry, V v2) {
                WeakValueReference weakValueReference = ((StrongKeyWeakValueEntry) strongKeyWeakValueEntry).valueReference;
                ((StrongKeyWeakValueEntry) strongKeyWeakValueEntry).valueReference = new WeakValueReferenceImpl(((StrongKeyWeakValueSegment) strongKeyWeakValueSegment).queueForValues, v2, strongKeyWeakValueEntry);
                weakValueReference.clear();
            }
        }

        private static final class LinkedStrongKeyWeakValueEntry<K, V> extends StrongKeyWeakValueEntry<K, V> {
            private final StrongKeyWeakValueEntry<K, V> next;

            LinkedStrongKeyWeakValueEntry(Object obj, int i2, StrongKeyWeakValueEntry strongKeyWeakValueEntry) {
                super(obj, i2);
                this.next = strongKeyWeakValueEntry;
            }

            @Override // com.google.common.collect.MapMakerInternalMap.AbstractStrongKeyEntry, com.google.common.collect.MapMakerInternalMap.InternalEntry
            public StrongKeyWeakValueEntry<K, V> getNext() {
                return this.next;
            }
        }

        @Override // com.google.common.collect.MapMakerInternalMap.InternalEntry
        @CheckForNull
        public final V getValue() {
            return this.valueReference.get();
        }

        @Override // com.google.common.collect.MapMakerInternalMap.WeakValueEntry
        public final WeakValueReference<K, V, StrongKeyWeakValueEntry<K, V>> getValueReference() {
            return this.valueReference;
        }

        private StrongKeyWeakValueEntry(K k2, int i2) {
            super(k2, i2);
            this.valueReference = MapMakerInternalMap.unsetWeakValueReference();
        }
    }

    static final class StrongKeyWeakValueSegment<K, V> extends Segment<K, V, StrongKeyWeakValueEntry<K, V>, StrongKeyWeakValueSegment<K, V>> {
        private final ReferenceQueue<V> queueForValues;

        StrongKeyWeakValueSegment(MapMakerInternalMap<K, V, StrongKeyWeakValueEntry<K, V>, StrongKeyWeakValueSegment<K, V>> mapMakerInternalMap, int i2) {
            super(mapMakerInternalMap, i2);
            this.queueForValues = new ReferenceQueue<>();
        }

        @Override // com.google.common.collect.MapMakerInternalMap.Segment
        ReferenceQueue<V> getValueReferenceQueueForTesting() {
            return this.queueForValues;
        }

        @Override // com.google.common.collect.MapMakerInternalMap.Segment
        public WeakValueReference<K, V, StrongKeyWeakValueEntry<K, V>> getWeakValueReferenceForTesting(InternalEntry<K, V, ?> internalEntry) {
            return castForTesting((InternalEntry) internalEntry).getValueReference();
        }

        @Override // com.google.common.collect.MapMakerInternalMap.Segment
        void maybeClearReferenceQueues() {
            clearReferenceQueue(this.queueForValues);
        }

        @Override // com.google.common.collect.MapMakerInternalMap.Segment
        void maybeDrainReferenceQueues() {
            drainValueReferenceQueue(this.queueForValues);
        }

        @Override // com.google.common.collect.MapMakerInternalMap.Segment
        public WeakValueReference<K, V, StrongKeyWeakValueEntry<K, V>> newWeakValueReferenceForTesting(InternalEntry<K, V, ?> internalEntry, V v2) {
            return new WeakValueReferenceImpl(this.queueForValues, v2, castForTesting((InternalEntry) internalEntry));
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.collect.MapMakerInternalMap.Segment
        public StrongKeyWeakValueSegment<K, V> self() {
            return this;
        }

        @Override // com.google.common.collect.MapMakerInternalMap.Segment
        public void setWeakValueReferenceForTesting(InternalEntry<K, V, ?> internalEntry, WeakValueReference<K, V, ? extends InternalEntry<K, V, ?>> weakValueReference) {
            StrongKeyWeakValueEntry<K, V> strongKeyWeakValueEntryCastForTesting = castForTesting((InternalEntry) internalEntry);
            WeakValueReference weakValueReference2 = ((StrongKeyWeakValueEntry) strongKeyWeakValueEntryCastForTesting).valueReference;
            ((StrongKeyWeakValueEntry) strongKeyWeakValueEntryCastForTesting).valueReference = weakValueReference;
            weakValueReference2.clear();
        }

        @Override // com.google.common.collect.MapMakerInternalMap.Segment
        @CheckForNull
        public StrongKeyWeakValueEntry<K, V> castForTesting(@CheckForNull InternalEntry<K, V, ?> internalEntry) {
            return (StrongKeyWeakValueEntry) internalEntry;
        }
    }

    interface StrongValueEntry<K, V, E extends InternalEntry<K, V, E>> extends InternalEntry<K, V, E> {
    }

    final class ValueIterator extends MapMakerInternalMap<K, V, E, S>.HashIterator<V> {
        ValueIterator() {
            super();
        }

        @Override // com.google.common.collect.MapMakerInternalMap.HashIterator, java.util.Iterator
        public V next() {
            return (V) c().getValue();
        }
    }

    final class Values extends AbstractCollection<V> {
        Values() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public void clear() {
            MapMakerInternalMap.this.clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean contains(Object obj) {
            return MapMakerInternalMap.this.containsValue(obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean isEmpty() {
            return MapMakerInternalMap.this.isEmpty();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public Iterator<V> iterator() {
            return new ValueIterator();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            return MapMakerInternalMap.this.size();
        }
    }

    static class WeakKeyDummyValueEntry<K> extends AbstractWeakKeyEntry<K, MapMaker.Dummy, WeakKeyDummyValueEntry<K>> implements StrongValueEntry<K, MapMaker.Dummy, WeakKeyDummyValueEntry<K>> {

        static final class Helper<K> implements InternalEntryHelper<K, MapMaker.Dummy, WeakKeyDummyValueEntry<K>, WeakKeyDummyValueSegment<K>> {
            private static final Helper<?> INSTANCE = new Helper<>();

            Helper() {
            }

            static Helper a() {
                return INSTANCE;
            }

            @Override // com.google.common.collect.MapMakerInternalMap.InternalEntryHelper
            public Strength keyStrength() {
                return Strength.WEAK;
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.google.common.collect.MapMakerInternalMap.InternalEntryHelper
            public /* bridge */ /* synthetic */ InternalEntry newEntry(Segment segment, Object obj, int i2, @CheckForNull InternalEntry internalEntry) {
                return newEntry((WeakKeyDummyValueSegment<WeakKeyDummyValueSegment<K>>) segment, (WeakKeyDummyValueSegment<K>) obj, i2, (WeakKeyDummyValueEntry<WeakKeyDummyValueSegment<K>>) internalEntry);
            }

            @Override // com.google.common.collect.MapMakerInternalMap.InternalEntryHelper
            public void setValue(WeakKeyDummyValueSegment<K> weakKeyDummyValueSegment, WeakKeyDummyValueEntry<K> weakKeyDummyValueEntry, MapMaker.Dummy dummy) {
            }

            @Override // com.google.common.collect.MapMakerInternalMap.InternalEntryHelper
            public Strength valueStrength() {
                return Strength.STRONG;
            }

            @Override // com.google.common.collect.MapMakerInternalMap.InternalEntryHelper
            @CheckForNull
            public WeakKeyDummyValueEntry<K> copy(WeakKeyDummyValueSegment<K> weakKeyDummyValueSegment, WeakKeyDummyValueEntry<K> weakKeyDummyValueEntry, @CheckForNull WeakKeyDummyValueEntry<K> weakKeyDummyValueEntry2) {
                K key = weakKeyDummyValueEntry.getKey();
                if (key == null) {
                    return null;
                }
                return newEntry((WeakKeyDummyValueSegment<WeakKeyDummyValueSegment<K>>) weakKeyDummyValueSegment, (WeakKeyDummyValueSegment<K>) key, weakKeyDummyValueEntry.f14142a, (WeakKeyDummyValueEntry<WeakKeyDummyValueSegment<K>>) weakKeyDummyValueEntry2);
            }

            public WeakKeyDummyValueEntry<K> newEntry(WeakKeyDummyValueSegment<K> weakKeyDummyValueSegment, K k2, int i2, @CheckForNull WeakKeyDummyValueEntry<K> weakKeyDummyValueEntry) {
                return weakKeyDummyValueEntry == null ? new WeakKeyDummyValueEntry<>(((WeakKeyDummyValueSegment) weakKeyDummyValueSegment).queueForKeys, k2, i2) : new LinkedWeakKeyDummyValueEntry(((WeakKeyDummyValueSegment) weakKeyDummyValueSegment).queueForKeys, k2, i2, weakKeyDummyValueEntry);
            }

            @Override // com.google.common.collect.MapMakerInternalMap.InternalEntryHelper
            public WeakKeyDummyValueSegment<K> newSegment(MapMakerInternalMap<K, MapMaker.Dummy, WeakKeyDummyValueEntry<K>, WeakKeyDummyValueSegment<K>> mapMakerInternalMap, int i2) {
                return new WeakKeyDummyValueSegment<>(mapMakerInternalMap, i2);
            }
        }

        private static final class LinkedWeakKeyDummyValueEntry<K> extends WeakKeyDummyValueEntry<K> {
            private final WeakKeyDummyValueEntry<K> next;

            @Override // com.google.common.collect.MapMakerInternalMap.WeakKeyDummyValueEntry, com.google.common.collect.MapMakerInternalMap.InternalEntry
            public /* bridge */ /* synthetic */ Object getValue() {
                return super.getValue();
            }

            private LinkedWeakKeyDummyValueEntry(ReferenceQueue<K> referenceQueue, K k2, int i2, WeakKeyDummyValueEntry<K> weakKeyDummyValueEntry) {
                super(referenceQueue, k2, i2);
                this.next = weakKeyDummyValueEntry;
            }

            @Override // com.google.common.collect.MapMakerInternalMap.AbstractWeakKeyEntry, com.google.common.collect.MapMakerInternalMap.InternalEntry
            public WeakKeyDummyValueEntry<K> getNext() {
                return this.next;
            }
        }

        private WeakKeyDummyValueEntry(ReferenceQueue<K> referenceQueue, K k2, int i2) {
            super(referenceQueue, k2, i2);
        }

        @Override // com.google.common.collect.MapMakerInternalMap.InternalEntry
        public final MapMaker.Dummy getValue() {
            return MapMaker.Dummy.VALUE;
        }
    }

    static final class WeakKeyDummyValueSegment<K> extends Segment<K, MapMaker.Dummy, WeakKeyDummyValueEntry<K>, WeakKeyDummyValueSegment<K>> {
        private final ReferenceQueue<K> queueForKeys;

        WeakKeyDummyValueSegment(MapMakerInternalMap<K, MapMaker.Dummy, WeakKeyDummyValueEntry<K>, WeakKeyDummyValueSegment<K>> mapMakerInternalMap, int i2) {
            super(mapMakerInternalMap, i2);
            this.queueForKeys = new ReferenceQueue<>();
        }

        @Override // com.google.common.collect.MapMakerInternalMap.Segment
        ReferenceQueue<K> getKeyReferenceQueueForTesting() {
            return this.queueForKeys;
        }

        @Override // com.google.common.collect.MapMakerInternalMap.Segment
        void maybeClearReferenceQueues() {
            clearReferenceQueue(this.queueForKeys);
        }

        @Override // com.google.common.collect.MapMakerInternalMap.Segment
        void maybeDrainReferenceQueues() {
            drainKeyReferenceQueue(this.queueForKeys);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.collect.MapMakerInternalMap.Segment
        public WeakKeyDummyValueSegment<K> self() {
            return this;
        }

        @Override // com.google.common.collect.MapMakerInternalMap.Segment
        public WeakKeyDummyValueEntry<K> castForTesting(InternalEntry<K, MapMaker.Dummy, ?> internalEntry) {
            return (WeakKeyDummyValueEntry) internalEntry;
        }
    }

    static class WeakKeyStrongValueEntry<K, V> extends AbstractWeakKeyEntry<K, V, WeakKeyStrongValueEntry<K, V>> implements StrongValueEntry<K, V, WeakKeyStrongValueEntry<K, V>> {

        @CheckForNull
        private volatile V value;

        static final class Helper<K, V> implements InternalEntryHelper<K, V, WeakKeyStrongValueEntry<K, V>, WeakKeyStrongValueSegment<K, V>> {
            private static final Helper<?, ?> INSTANCE = new Helper<>();

            Helper() {
            }

            static Helper a() {
                return INSTANCE;
            }

            @Override // com.google.common.collect.MapMakerInternalMap.InternalEntryHelper
            public Strength keyStrength() {
                return Strength.WEAK;
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.google.common.collect.MapMakerInternalMap.InternalEntryHelper
            public /* bridge */ /* synthetic */ InternalEntry newEntry(Segment segment, Object obj, int i2, @CheckForNull InternalEntry internalEntry) {
                return newEntry((WeakKeyStrongValueSegment<WeakKeyStrongValueSegment<K, V>, V>) segment, (WeakKeyStrongValueSegment<K, V>) obj, i2, (WeakKeyStrongValueEntry<WeakKeyStrongValueSegment<K, V>, V>) internalEntry);
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.google.common.collect.MapMakerInternalMap.InternalEntryHelper
            public /* bridge */ /* synthetic */ void setValue(Segment segment, InternalEntry internalEntry, Object obj) {
                setValue((WeakKeyStrongValueSegment<K, WeakKeyStrongValueEntry<K, V>>) segment, (WeakKeyStrongValueEntry<K, WeakKeyStrongValueEntry<K, V>>) internalEntry, (WeakKeyStrongValueEntry<K, V>) obj);
            }

            @Override // com.google.common.collect.MapMakerInternalMap.InternalEntryHelper
            public Strength valueStrength() {
                return Strength.STRONG;
            }

            @Override // com.google.common.collect.MapMakerInternalMap.InternalEntryHelper
            @CheckForNull
            public WeakKeyStrongValueEntry<K, V> copy(WeakKeyStrongValueSegment<K, V> weakKeyStrongValueSegment, WeakKeyStrongValueEntry<K, V> weakKeyStrongValueEntry, @CheckForNull WeakKeyStrongValueEntry<K, V> weakKeyStrongValueEntry2) {
                K key = weakKeyStrongValueEntry.getKey();
                if (key == null) {
                    return null;
                }
                WeakKeyStrongValueEntry<K, V> weakKeyStrongValueEntryNewEntry = newEntry((WeakKeyStrongValueSegment<WeakKeyStrongValueSegment<K, V>, V>) weakKeyStrongValueSegment, (WeakKeyStrongValueSegment<K, V>) key, weakKeyStrongValueEntry.f14142a, (WeakKeyStrongValueEntry<WeakKeyStrongValueSegment<K, V>, V>) weakKeyStrongValueEntry2);
                ((WeakKeyStrongValueEntry) weakKeyStrongValueEntryNewEntry).value = ((WeakKeyStrongValueEntry) weakKeyStrongValueEntry).value;
                return weakKeyStrongValueEntryNewEntry;
            }

            public WeakKeyStrongValueEntry<K, V> newEntry(WeakKeyStrongValueSegment<K, V> weakKeyStrongValueSegment, K k2, int i2, @CheckForNull WeakKeyStrongValueEntry<K, V> weakKeyStrongValueEntry) {
                return weakKeyStrongValueEntry == null ? new WeakKeyStrongValueEntry<>(((WeakKeyStrongValueSegment) weakKeyStrongValueSegment).queueForKeys, k2, i2) : new LinkedWeakKeyStrongValueEntry(((WeakKeyStrongValueSegment) weakKeyStrongValueSegment).queueForKeys, k2, i2, weakKeyStrongValueEntry);
            }

            @Override // com.google.common.collect.MapMakerInternalMap.InternalEntryHelper
            public WeakKeyStrongValueSegment<K, V> newSegment(MapMakerInternalMap<K, V, WeakKeyStrongValueEntry<K, V>, WeakKeyStrongValueSegment<K, V>> mapMakerInternalMap, int i2) {
                return new WeakKeyStrongValueSegment<>(mapMakerInternalMap, i2);
            }

            public void setValue(WeakKeyStrongValueSegment<K, V> weakKeyStrongValueSegment, WeakKeyStrongValueEntry<K, V> weakKeyStrongValueEntry, V v2) {
                ((WeakKeyStrongValueEntry) weakKeyStrongValueEntry).value = v2;
            }
        }

        private static final class LinkedWeakKeyStrongValueEntry<K, V> extends WeakKeyStrongValueEntry<K, V> {
            private final WeakKeyStrongValueEntry<K, V> next;

            private LinkedWeakKeyStrongValueEntry(ReferenceQueue<K> referenceQueue, K k2, int i2, WeakKeyStrongValueEntry<K, V> weakKeyStrongValueEntry) {
                super(referenceQueue, k2, i2);
                this.next = weakKeyStrongValueEntry;
            }

            @Override // com.google.common.collect.MapMakerInternalMap.AbstractWeakKeyEntry, com.google.common.collect.MapMakerInternalMap.InternalEntry
            public WeakKeyStrongValueEntry<K, V> getNext() {
                return this.next;
            }
        }

        @Override // com.google.common.collect.MapMakerInternalMap.InternalEntry
        @CheckForNull
        public final V getValue() {
            return this.value;
        }

        private WeakKeyStrongValueEntry(ReferenceQueue<K> referenceQueue, K k2, int i2) {
            super(referenceQueue, k2, i2);
            this.value = null;
        }
    }

    static final class WeakKeyStrongValueSegment<K, V> extends Segment<K, V, WeakKeyStrongValueEntry<K, V>, WeakKeyStrongValueSegment<K, V>> {
        private final ReferenceQueue<K> queueForKeys;

        WeakKeyStrongValueSegment(MapMakerInternalMap<K, V, WeakKeyStrongValueEntry<K, V>, WeakKeyStrongValueSegment<K, V>> mapMakerInternalMap, int i2) {
            super(mapMakerInternalMap, i2);
            this.queueForKeys = new ReferenceQueue<>();
        }

        @Override // com.google.common.collect.MapMakerInternalMap.Segment
        ReferenceQueue<K> getKeyReferenceQueueForTesting() {
            return this.queueForKeys;
        }

        @Override // com.google.common.collect.MapMakerInternalMap.Segment
        void maybeClearReferenceQueues() {
            clearReferenceQueue(this.queueForKeys);
        }

        @Override // com.google.common.collect.MapMakerInternalMap.Segment
        void maybeDrainReferenceQueues() {
            drainKeyReferenceQueue(this.queueForKeys);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.collect.MapMakerInternalMap.Segment
        public WeakKeyStrongValueSegment<K, V> self() {
            return this;
        }

        @Override // com.google.common.collect.MapMakerInternalMap.Segment
        public WeakKeyStrongValueEntry<K, V> castForTesting(InternalEntry<K, V, ?> internalEntry) {
            return (WeakKeyStrongValueEntry) internalEntry;
        }
    }

    static class WeakKeyWeakValueEntry<K, V> extends AbstractWeakKeyEntry<K, V, WeakKeyWeakValueEntry<K, V>> implements WeakValueEntry<K, V, WeakKeyWeakValueEntry<K, V>> {
        private volatile WeakValueReference<K, V, WeakKeyWeakValueEntry<K, V>> valueReference;

        static final class Helper<K, V> implements InternalEntryHelper<K, V, WeakKeyWeakValueEntry<K, V>, WeakKeyWeakValueSegment<K, V>> {
            private static final Helper<?, ?> INSTANCE = new Helper<>();

            Helper() {
            }

            static Helper a() {
                return INSTANCE;
            }

            @Override // com.google.common.collect.MapMakerInternalMap.InternalEntryHelper
            public Strength keyStrength() {
                return Strength.WEAK;
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.google.common.collect.MapMakerInternalMap.InternalEntryHelper
            public /* bridge */ /* synthetic */ InternalEntry newEntry(Segment segment, Object obj, int i2, @CheckForNull InternalEntry internalEntry) {
                return newEntry((WeakKeyWeakValueSegment<WeakKeyWeakValueSegment<K, V>, V>) segment, (WeakKeyWeakValueSegment<K, V>) obj, i2, (WeakKeyWeakValueEntry<WeakKeyWeakValueSegment<K, V>, V>) internalEntry);
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.google.common.collect.MapMakerInternalMap.InternalEntryHelper
            public /* bridge */ /* synthetic */ void setValue(Segment segment, InternalEntry internalEntry, Object obj) {
                setValue((WeakKeyWeakValueSegment<K, WeakKeyWeakValueEntry<K, V>>) segment, (WeakKeyWeakValueEntry<K, WeakKeyWeakValueEntry<K, V>>) internalEntry, (WeakKeyWeakValueEntry<K, V>) obj);
            }

            @Override // com.google.common.collect.MapMakerInternalMap.InternalEntryHelper
            public Strength valueStrength() {
                return Strength.WEAK;
            }

            @Override // com.google.common.collect.MapMakerInternalMap.InternalEntryHelper
            @CheckForNull
            public WeakKeyWeakValueEntry<K, V> copy(WeakKeyWeakValueSegment<K, V> weakKeyWeakValueSegment, WeakKeyWeakValueEntry<K, V> weakKeyWeakValueEntry, @CheckForNull WeakKeyWeakValueEntry<K, V> weakKeyWeakValueEntry2) {
                K key = weakKeyWeakValueEntry.getKey();
                if (key == null || Segment.isCollected(weakKeyWeakValueEntry)) {
                    return null;
                }
                WeakKeyWeakValueEntry<K, V> weakKeyWeakValueEntryNewEntry = newEntry((WeakKeyWeakValueSegment<WeakKeyWeakValueSegment<K, V>, V>) weakKeyWeakValueSegment, (WeakKeyWeakValueSegment<K, V>) key, weakKeyWeakValueEntry.f14142a, (WeakKeyWeakValueEntry<WeakKeyWeakValueSegment<K, V>, V>) weakKeyWeakValueEntry2);
                ((WeakKeyWeakValueEntry) weakKeyWeakValueEntryNewEntry).valueReference = ((WeakKeyWeakValueEntry) weakKeyWeakValueEntry).valueReference.copyFor(((WeakKeyWeakValueSegment) weakKeyWeakValueSegment).queueForValues, weakKeyWeakValueEntryNewEntry);
                return weakKeyWeakValueEntryNewEntry;
            }

            public WeakKeyWeakValueEntry<K, V> newEntry(WeakKeyWeakValueSegment<K, V> weakKeyWeakValueSegment, K k2, int i2, @CheckForNull WeakKeyWeakValueEntry<K, V> weakKeyWeakValueEntry) {
                return weakKeyWeakValueEntry == null ? new WeakKeyWeakValueEntry<>(((WeakKeyWeakValueSegment) weakKeyWeakValueSegment).queueForKeys, k2, i2) : new LinkedWeakKeyWeakValueEntry(((WeakKeyWeakValueSegment) weakKeyWeakValueSegment).queueForKeys, k2, i2, weakKeyWeakValueEntry);
            }

            @Override // com.google.common.collect.MapMakerInternalMap.InternalEntryHelper
            public WeakKeyWeakValueSegment<K, V> newSegment(MapMakerInternalMap<K, V, WeakKeyWeakValueEntry<K, V>, WeakKeyWeakValueSegment<K, V>> mapMakerInternalMap, int i2) {
                return new WeakKeyWeakValueSegment<>(mapMakerInternalMap, i2);
            }

            public void setValue(WeakKeyWeakValueSegment<K, V> weakKeyWeakValueSegment, WeakKeyWeakValueEntry<K, V> weakKeyWeakValueEntry, V v2) {
                WeakValueReference weakValueReference = ((WeakKeyWeakValueEntry) weakKeyWeakValueEntry).valueReference;
                ((WeakKeyWeakValueEntry) weakKeyWeakValueEntry).valueReference = new WeakValueReferenceImpl(((WeakKeyWeakValueSegment) weakKeyWeakValueSegment).queueForValues, v2, weakKeyWeakValueEntry);
                weakValueReference.clear();
            }
        }

        private static final class LinkedWeakKeyWeakValueEntry<K, V> extends WeakKeyWeakValueEntry<K, V> {
            private final WeakKeyWeakValueEntry<K, V> next;

            LinkedWeakKeyWeakValueEntry(ReferenceQueue referenceQueue, Object obj, int i2, WeakKeyWeakValueEntry weakKeyWeakValueEntry) {
                super(referenceQueue, obj, i2);
                this.next = weakKeyWeakValueEntry;
            }

            @Override // com.google.common.collect.MapMakerInternalMap.AbstractWeakKeyEntry, com.google.common.collect.MapMakerInternalMap.InternalEntry
            public WeakKeyWeakValueEntry<K, V> getNext() {
                return this.next;
            }
        }

        WeakKeyWeakValueEntry(ReferenceQueue referenceQueue, Object obj, int i2) {
            super(referenceQueue, obj, i2);
            this.valueReference = MapMakerInternalMap.unsetWeakValueReference();
        }

        @Override // com.google.common.collect.MapMakerInternalMap.InternalEntry
        public final V getValue() {
            return this.valueReference.get();
        }

        @Override // com.google.common.collect.MapMakerInternalMap.WeakValueEntry
        public final WeakValueReference<K, V, WeakKeyWeakValueEntry<K, V>> getValueReference() {
            return this.valueReference;
        }
    }

    static final class WeakKeyWeakValueSegment<K, V> extends Segment<K, V, WeakKeyWeakValueEntry<K, V>, WeakKeyWeakValueSegment<K, V>> {
        private final ReferenceQueue<K> queueForKeys;
        private final ReferenceQueue<V> queueForValues;

        WeakKeyWeakValueSegment(MapMakerInternalMap<K, V, WeakKeyWeakValueEntry<K, V>, WeakKeyWeakValueSegment<K, V>> mapMakerInternalMap, int i2) {
            super(mapMakerInternalMap, i2);
            this.queueForKeys = new ReferenceQueue<>();
            this.queueForValues = new ReferenceQueue<>();
        }

        @Override // com.google.common.collect.MapMakerInternalMap.Segment
        ReferenceQueue<K> getKeyReferenceQueueForTesting() {
            return this.queueForKeys;
        }

        @Override // com.google.common.collect.MapMakerInternalMap.Segment
        ReferenceQueue<V> getValueReferenceQueueForTesting() {
            return this.queueForValues;
        }

        @Override // com.google.common.collect.MapMakerInternalMap.Segment
        public WeakValueReference<K, V, WeakKeyWeakValueEntry<K, V>> getWeakValueReferenceForTesting(InternalEntry<K, V, ?> internalEntry) {
            return castForTesting((InternalEntry) internalEntry).getValueReference();
        }

        @Override // com.google.common.collect.MapMakerInternalMap.Segment
        void maybeClearReferenceQueues() {
            clearReferenceQueue(this.queueForKeys);
        }

        @Override // com.google.common.collect.MapMakerInternalMap.Segment
        void maybeDrainReferenceQueues() {
            drainKeyReferenceQueue(this.queueForKeys);
            drainValueReferenceQueue(this.queueForValues);
        }

        @Override // com.google.common.collect.MapMakerInternalMap.Segment
        public WeakValueReference<K, V, WeakKeyWeakValueEntry<K, V>> newWeakValueReferenceForTesting(InternalEntry<K, V, ?> internalEntry, V v2) {
            return new WeakValueReferenceImpl(this.queueForValues, v2, castForTesting((InternalEntry) internalEntry));
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.collect.MapMakerInternalMap.Segment
        public WeakKeyWeakValueSegment<K, V> self() {
            return this;
        }

        @Override // com.google.common.collect.MapMakerInternalMap.Segment
        public void setWeakValueReferenceForTesting(InternalEntry<K, V, ?> internalEntry, WeakValueReference<K, V, ? extends InternalEntry<K, V, ?>> weakValueReference) {
            WeakKeyWeakValueEntry<K, V> weakKeyWeakValueEntryCastForTesting = castForTesting((InternalEntry) internalEntry);
            WeakValueReference weakValueReference2 = ((WeakKeyWeakValueEntry) weakKeyWeakValueEntryCastForTesting).valueReference;
            ((WeakKeyWeakValueEntry) weakKeyWeakValueEntryCastForTesting).valueReference = weakValueReference;
            weakValueReference2.clear();
        }

        @Override // com.google.common.collect.MapMakerInternalMap.Segment
        @CheckForNull
        public WeakKeyWeakValueEntry<K, V> castForTesting(@CheckForNull InternalEntry<K, V, ?> internalEntry) {
            return (WeakKeyWeakValueEntry) internalEntry;
        }
    }

    interface WeakValueEntry<K, V, E extends InternalEntry<K, V, E>> extends InternalEntry<K, V, E> {
        WeakValueReference<K, V, E> getValueReference();
    }

    interface WeakValueReference<K, V, E extends InternalEntry<K, V, E>> {
        void clear();

        WeakValueReference<K, V, E> copyFor(ReferenceQueue<V> referenceQueue, E e2);

        @CheckForNull
        V get();

        E getEntry();
    }

    static final class WeakValueReferenceImpl<K, V, E extends InternalEntry<K, V, E>> extends WeakReference<V> implements WeakValueReference<K, V, E> {

        /* renamed from: a, reason: collision with root package name */
        final InternalEntry f14158a;

        WeakValueReferenceImpl(ReferenceQueue referenceQueue, Object obj, InternalEntry internalEntry) {
            super(obj, referenceQueue);
            this.f14158a = internalEntry;
        }

        @Override // com.google.common.collect.MapMakerInternalMap.WeakValueReference
        public WeakValueReference<K, V, E> copyFor(ReferenceQueue<V> referenceQueue, E e2) {
            return new WeakValueReferenceImpl(referenceQueue, get(), e2);
        }

        @Override // com.google.common.collect.MapMakerInternalMap.WeakValueReference
        public E getEntry() {
            return (E) this.f14158a;
        }
    }

    final class WriteThroughEntry extends AbstractMapEntry<K, V> {

        /* renamed from: a, reason: collision with root package name */
        final Object f14159a;

        /* renamed from: b, reason: collision with root package name */
        Object f14160b;

        WriteThroughEntry(Object obj, Object obj2) {
            this.f14159a = obj;
            this.f14160b = obj2;
        }

        @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
        public boolean equals(@CheckForNull Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            return this.f14159a.equals(entry.getKey()) && this.f14160b.equals(entry.getValue());
        }

        @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
        public K getKey() {
            return (K) this.f14159a;
        }

        @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
        public V getValue() {
            return (V) this.f14160b;
        }

        @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
        public int hashCode() {
            return this.f14159a.hashCode() ^ this.f14160b.hashCode();
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
        public V setValue(V v2) {
            V v3 = (V) MapMakerInternalMap.this.put(this.f14159a, v2);
            this.f14160b = v2;
            return v3;
        }
    }

    private MapMakerInternalMap(MapMaker mapMaker, InternalEntryHelper<K, V, E, S> internalEntryHelper) {
        this.concurrencyLevel = Math.min(mapMaker.a(), 65536);
        this.keyEquivalence = mapMaker.c();
        this.entryHelper = internalEntryHelper;
        int iMin = Math.min(mapMaker.b(), 1073741824);
        int i2 = 0;
        int i3 = 1;
        int i4 = 0;
        int i5 = 1;
        while (i5 < this.concurrencyLevel) {
            i4++;
            i5 <<= 1;
        }
        this.segmentShift = 32 - i4;
        this.segmentMask = i5 - 1;
        this.segments = newSegmentArray(i5);
        int i6 = iMin / i5;
        while (i3 < (i5 * i6 < iMin ? i6 + 1 : i6)) {
            i3 <<= 1;
        }
        while (true) {
            Segment<K, V, E, S>[] segmentArr = this.segments;
            if (i2 >= segmentArr.length) {
                return;
            }
            segmentArr[i2] = createSegment(i3);
            i2++;
        }
    }

    static <K, V> MapMakerInternalMap<K, V, ? extends InternalEntry<K, V, ?>, ?> create(MapMaker mapMaker) {
        Strength strengthD = mapMaker.d();
        Strength strength = Strength.STRONG;
        if (strengthD == strength && mapMaker.e() == strength) {
            return new MapMakerInternalMap<>(mapMaker, StrongKeyStrongValueEntry.Helper.a());
        }
        if (mapMaker.d() == strength && mapMaker.e() == Strength.WEAK) {
            return new MapMakerInternalMap<>(mapMaker, StrongKeyWeakValueEntry.Helper.a());
        }
        Strength strengthD2 = mapMaker.d();
        Strength strength2 = Strength.WEAK;
        if (strengthD2 == strength2 && mapMaker.e() == strength) {
            return new MapMakerInternalMap<>(mapMaker, WeakKeyStrongValueEntry.Helper.a());
        }
        if (mapMaker.d() == strength2 && mapMaker.e() == strength2) {
            return new MapMakerInternalMap<>(mapMaker, WeakKeyWeakValueEntry.Helper.a());
        }
        throw new AssertionError();
    }

    static <K> MapMakerInternalMap<K, MapMaker.Dummy, ? extends InternalEntry<K, MapMaker.Dummy, ?>, ?> createWithDummyValues(MapMaker mapMaker) {
        Strength strengthD = mapMaker.d();
        Strength strength = Strength.STRONG;
        if (strengthD == strength && mapMaker.e() == strength) {
            return new MapMakerInternalMap<>(mapMaker, StrongKeyDummyValueEntry.Helper.a());
        }
        Strength strengthD2 = mapMaker.d();
        Strength strength2 = Strength.WEAK;
        if (strengthD2 == strength2 && mapMaker.e() == strength) {
            return new MapMakerInternalMap<>(mapMaker, WeakKeyDummyValueEntry.Helper.a());
        }
        if (mapMaker.e() == strength2) {
            throw new IllegalArgumentException("Map cannot have both weak and dummy values");
        }
        throw new AssertionError();
    }

    @J2ktIncompatible
    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Use SerializationProxy");
    }

    static int rehash(int i2) {
        int i3 = i2 + ((i2 << 15) ^ (-12931));
        int i4 = i3 ^ (i3 >>> 10);
        int i5 = i4 + (i4 << 3);
        int i6 = i5 ^ (i5 >>> 6);
        int i7 = i6 + (i6 << 2) + (i6 << 14);
        return i7 ^ (i7 >>> 16);
    }

    static <K, V, E extends InternalEntry<K, V, E>> WeakValueReference<K, V, E> unsetWeakValueReference() {
        return (WeakValueReference<K, V, E>) UNSET_WEAK_VALUE_REFERENCE;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void clear() {
        for (Segment<K, V, E, S> segment : this.segments) {
            segment.clear();
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsKey(@CheckForNull Object obj) {
        if (obj == null) {
            return false;
        }
        int iHash = hash(obj);
        return segmentFor(iHash).containsKey(obj, iHash);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v0 */
    /* JADX WARN: Type inference failed for: r10v1, types: [int] */
    /* JADX WARN: Type inference failed for: r10v3 */
    /* JADX WARN: Type inference failed for: r13v0 */
    /* JADX WARN: Type inference failed for: r13v1, types: [int] */
    /* JADX WARN: Type inference failed for: r13v3 */
    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsValue(@CheckForNull Object obj) {
        boolean z2 = false;
        if (obj == null) {
            return false;
        }
        Segment<K, V, E, S>[] segmentArr = this.segments;
        long j2 = -1;
        int i2 = 0;
        while (i2 < 3) {
            int length = segmentArr.length;
            long j3 = 0;
            for (?? r10 = z2; r10 < length; r10++) {
                Segment<K, V, E, S> segment = segmentArr[r10];
                int i3 = segment.count;
                AtomicReferenceArray<E> atomicReferenceArray = segment.table;
                for (?? r13 = z2; r13 < atomicReferenceArray.length(); r13++) {
                    for (E next = atomicReferenceArray.get(r13); next != null; next = next.getNext()) {
                        V liveValue = segment.getLiveValue(next);
                        if (liveValue != null && valueEquivalence().equivalent(obj, liveValue)) {
                            return true;
                        }
                    }
                }
                j3 += segment.modCount;
                z2 = false;
            }
            if (j3 == j2) {
                return false;
            }
            i2++;
            j2 = j3;
            z2 = false;
        }
        return z2;
    }

    @VisibleForTesting
    E copyEntry(E e2, E e3) {
        return (E) segmentFor(e2.getHash()).copyEntry(e2, e3);
    }

    Segment<K, V, E, S> createSegment(int i2) {
        return this.entryHelper.newSegment(this, i2);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> set = this.entrySet;
        if (set != null) {
            return set;
        }
        EntrySet entrySet = new EntrySet();
        this.entrySet = entrySet;
        return entrySet;
    }

    @Override // java.util.AbstractMap, java.util.Map
    @CheckForNull
    public V get(@CheckForNull Object obj) {
        if (obj == null) {
            return null;
        }
        int iHash = hash(obj);
        return segmentFor(iHash).get(obj, iHash);
    }

    @CheckForNull
    E getEntry(@CheckForNull Object obj) {
        if (obj == null) {
            return null;
        }
        int iHash = hash(obj);
        return (E) segmentFor(iHash).getEntry(obj, iHash);
    }

    @CheckForNull
    V getLiveValue(E e2) {
        if (e2.getKey() == null) {
            return null;
        }
        return (V) e2.getValue();
    }

    int hash(Object obj) {
        return rehash(this.keyEquivalence.hash(obj));
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean isEmpty() {
        Segment<K, V, E, S>[] segmentArr = this.segments;
        long j2 = 0;
        for (int i2 = 0; i2 < segmentArr.length; i2++) {
            if (segmentArr[i2].count != 0) {
                return false;
            }
            j2 += segmentArr[i2].modCount;
        }
        if (j2 == 0) {
            return true;
        }
        for (int i3 = 0; i3 < segmentArr.length; i3++) {
            if (segmentArr[i3].count != 0) {
                return false;
            }
            j2 -= segmentArr[i3].modCount;
        }
        return j2 == 0;
    }

    @VisibleForTesting
    boolean isLiveForTesting(InternalEntry<K, V, ?> internalEntry) {
        return segmentFor(internalEntry.getHash()).getLiveValueForTesting(internalEntry) != null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<K> keySet() {
        Set<K> set = this.keySet;
        if (set != null) {
            return set;
        }
        KeySet keySet = new KeySet();
        this.keySet = keySet;
        return keySet;
    }

    @VisibleForTesting
    Strength keyStrength() {
        return this.entryHelper.keyStrength();
    }

    final Segment<K, V, E, S>[] newSegmentArray(int i2) {
        return new Segment[i2];
    }

    @Override // java.util.AbstractMap, java.util.Map
    @CanIgnoreReturnValue
    @CheckForNull
    public V put(K k2, V v2) {
        Preconditions.checkNotNull(k2);
        Preconditions.checkNotNull(v2);
        int iHash = hash(k2);
        return segmentFor(iHash).put(k2, iHash, v2, false);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void putAll(Map<? extends K, ? extends V> map) {
        for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap
    @CanIgnoreReturnValue
    @CheckForNull
    public V putIfAbsent(K k2, V v2) {
        Preconditions.checkNotNull(k2);
        Preconditions.checkNotNull(v2);
        int iHash = hash(k2);
        return segmentFor(iHash).put(k2, iHash, v2, true);
    }

    void reclaimKey(E e2) {
        int hash = e2.getHash();
        segmentFor(hash).reclaimKey(e2, hash);
    }

    /* JADX WARN: Multi-variable type inference failed */
    void reclaimValue(WeakValueReference<K, V, E> weakValueReference) {
        InternalEntry entry = weakValueReference.getEntry();
        int hash = entry.getHash();
        segmentFor(hash).reclaimValue(entry.getKey(), hash, weakValueReference);
    }

    @Override // java.util.AbstractMap, java.util.Map
    @CanIgnoreReturnValue
    @CheckForNull
    public V remove(@CheckForNull Object obj) {
        if (obj == null) {
            return null;
        }
        int iHash = hash(obj);
        return segmentFor(iHash).remove(obj, iHash);
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap
    @CanIgnoreReturnValue
    public boolean replace(K k2, @CheckForNull V v2, V v3) {
        Preconditions.checkNotNull(k2);
        Preconditions.checkNotNull(v3);
        if (v2 == null) {
            return false;
        }
        int iHash = hash(k2);
        return segmentFor(iHash).replace(k2, iHash, v2, v3);
    }

    Segment<K, V, E, S> segmentFor(int i2) {
        return this.segments[(i2 >>> this.segmentShift) & this.segmentMask];
    }

    @Override // java.util.AbstractMap, java.util.Map
    public int size() {
        long j2 = 0;
        for (int i2 = 0; i2 < this.segments.length; i2++) {
            j2 += r0[i2].count;
        }
        return Ints.saturatedCast(j2);
    }

    @VisibleForTesting
    Equivalence<Object> valueEquivalence() {
        return this.entryHelper.valueStrength().defaultEquivalence();
    }

    @VisibleForTesting
    Strength valueStrength() {
        return this.entryHelper.valueStrength();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Collection<V> values() {
        Collection<V> collection = this.values;
        if (collection != null) {
            return collection;
        }
        Values values = new Values();
        this.values = values;
        return values;
    }

    Object writeReplace() {
        return new SerializationProxy(this.entryHelper.keyStrength(), this.entryHelper.valueStrength(), this.keyEquivalence, this.entryHelper.valueStrength().defaultEquivalence(), this.concurrencyLevel, this);
    }

    static abstract class AbstractSerializationProxy<K, V> extends ForwardingConcurrentMap<K, V> implements Serializable {
        private static final long serialVersionUID = 3;
        final int concurrencyLevel;
        transient ConcurrentMap<K, V> delegate;
        final Equivalence<Object> keyEquivalence;
        final Strength keyStrength;
        final Equivalence<Object> valueEquivalence;
        final Strength valueStrength;

        AbstractSerializationProxy(Strength strength, Strength strength2, Equivalence<Object> equivalence, Equivalence<Object> equivalence2, int i2, ConcurrentMap<K, V> concurrentMap) {
            this.keyStrength = strength;
            this.valueStrength = strength2;
            this.keyEquivalence = equivalence;
            this.valueEquivalence = equivalence2;
            this.concurrencyLevel = i2;
            this.delegate = concurrentMap;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @J2ktIncompatible
        void readEntries(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
            while (true) {
                Object object = objectInputStream.readObject();
                if (object == null) {
                    return;
                }
                this.delegate.put(object, objectInputStream.readObject());
            }
        }

        @J2ktIncompatible
        MapMaker readMapMaker(ObjectInputStream objectInputStream) throws IOException {
            return new MapMaker().initialCapacity(objectInputStream.readInt()).g(this.keyStrength).h(this.valueStrength).f(this.keyEquivalence).concurrencyLevel(this.concurrencyLevel);
        }

        void writeMapTo(ObjectOutputStream objectOutputStream) throws IOException {
            objectOutputStream.writeInt(this.delegate.size());
            for (Map.Entry<K, V> entry : this.delegate.entrySet()) {
                objectOutputStream.writeObject(entry.getKey());
                objectOutputStream.writeObject(entry.getValue());
            }
            objectOutputStream.writeObject(null);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.common.collect.ForwardingConcurrentMap, com.google.common.collect.ForwardingMap, com.google.common.collect.ForwardingObject
        public ConcurrentMap<K, V> delegate() {
            return this.delegate;
        }
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap
    @CanIgnoreReturnValue
    public boolean remove(@CheckForNull Object obj, @CheckForNull Object obj2) {
        if (obj == null || obj2 == null) {
            return false;
        }
        int iHash = hash(obj);
        return segmentFor(iHash).remove(obj, iHash, obj2);
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap
    @CanIgnoreReturnValue
    @CheckForNull
    public V replace(K k2, V v2) {
        Preconditions.checkNotNull(k2);
        Preconditions.checkNotNull(v2);
        int iHash = hash(k2);
        return segmentFor(iHash).replace(k2, iHash, v2);
    }

    static abstract class Segment<K, V, E extends InternalEntry<K, V, E>, S extends Segment<K, V, E, S>> extends ReentrantLock {
        volatile int count;

        @Weak
        final MapMakerInternalMap<K, V, E, S> map;
        int modCount;
        final AtomicInteger readCount = new AtomicInteger();

        @CheckForNull
        volatile AtomicReferenceArray<E> table;
        int threshold;

        Segment(MapMakerInternalMap<K, V, E, S> mapMakerInternalMap, int i2) {
            this.map = mapMakerInternalMap;
            initTable(newEntryArray(i2));
        }

        static <K, V, E extends InternalEntry<K, V, E>> boolean isCollected(E e2) {
            return e2.getValue() == null;
        }

        abstract E castForTesting(InternalEntry<K, V, ?> internalEntry);

        void clear() {
            if (this.count != 0) {
                lock();
                try {
                    AtomicReferenceArray<E> atomicReferenceArray = this.table;
                    for (int i2 = 0; i2 < atomicReferenceArray.length(); i2++) {
                        atomicReferenceArray.set(i2, null);
                    }
                    maybeClearReferenceQueues();
                    this.readCount.set(0);
                    this.modCount++;
                    this.count = 0;
                    unlock();
                } catch (Throwable th) {
                    unlock();
                    throw th;
                }
            }
        }

        <T> void clearReferenceQueue(ReferenceQueue<T> referenceQueue) {
            while (referenceQueue.poll() != null) {
            }
        }

        @CanIgnoreReturnValue
        boolean clearValueForTesting(K k2, int i2, WeakValueReference<K, V, ? extends InternalEntry<K, V, ?>> weakValueReference) {
            lock();
            try {
                AtomicReferenceArray<E> atomicReferenceArray = this.table;
                int length = (atomicReferenceArray.length() - 1) & i2;
                InternalEntry internalEntry = (InternalEntry) atomicReferenceArray.get(length);
                for (InternalEntry next = internalEntry; next != null; next = next.getNext()) {
                    Object key = next.getKey();
                    if (next.getHash() == i2 && key != null && this.map.keyEquivalence.equivalent(k2, key)) {
                        if (((WeakValueEntry) next).getValueReference() != weakValueReference) {
                            return false;
                        }
                        atomicReferenceArray.set(length, removeFromChain(internalEntry, next));
                        return true;
                    }
                }
                return false;
            } finally {
                unlock();
            }
        }

        boolean containsKey(Object obj, int i2) {
            try {
                boolean z2 = false;
                if (this.count == 0) {
                    return false;
                }
                InternalEntry liveEntry = getLiveEntry(obj, i2);
                if (liveEntry != null) {
                    if (liveEntry.getValue() != null) {
                        z2 = true;
                    }
                }
                return z2;
            } finally {
                postReadCleanup();
            }
        }

        @VisibleForTesting
        boolean containsValue(Object obj) {
            try {
                if (this.count != 0) {
                    AtomicReferenceArray<E> atomicReferenceArray = this.table;
                    int length = atomicReferenceArray.length();
                    for (int i2 = 0; i2 < length; i2++) {
                        for (E next = atomicReferenceArray.get(i2); next != null; next = next.getNext()) {
                            V liveValue = getLiveValue(next);
                            if (liveValue != null && this.map.valueEquivalence().equivalent(obj, liveValue)) {
                                postReadCleanup();
                                return true;
                            }
                        }
                    }
                }
                return false;
            } finally {
                postReadCleanup();
            }
        }

        @CheckForNull
        E copyEntry(E e2, E e3) {
            return (E) this.map.entryHelper.copy(self(), e2, e3);
        }

        E copyForTesting(InternalEntry<K, V, ?> internalEntry, @CheckForNull InternalEntry<K, V, ?> internalEntry2) {
            return (E) this.map.entryHelper.copy(self(), castForTesting(internalEntry), castForTesting(internalEntry2));
        }

        @GuardedBy("this")
        void drainKeyReferenceQueue(ReferenceQueue<K> referenceQueue) {
            int i2 = 0;
            do {
                Reference<? extends K> referencePoll = referenceQueue.poll();
                if (referencePoll == null) {
                    return;
                }
                this.map.reclaimKey((InternalEntry) referencePoll);
                i2++;
            } while (i2 != 16);
        }

        @GuardedBy("this")
        void drainValueReferenceQueue(ReferenceQueue<V> referenceQueue) {
            int i2 = 0;
            do {
                Reference<? extends V> referencePoll = referenceQueue.poll();
                if (referencePoll == null) {
                    return;
                }
                this.map.reclaimValue((WeakValueReference) referencePoll);
                i2++;
            } while (i2 != 16);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @GuardedBy("this")
        void expand() {
            AtomicReferenceArray<E> atomicReferenceArray = this.table;
            int length = atomicReferenceArray.length();
            if (length >= 1073741824) {
                return;
            }
            int i2 = this.count;
            AtomicReferenceArray<E> atomicReferenceArrayNewEntryArray = newEntryArray(length << 1);
            this.threshold = (atomicReferenceArrayNewEntryArray.length() * 3) / 4;
            int length2 = atomicReferenceArrayNewEntryArray.length() - 1;
            for (int i3 = 0; i3 < length; i3++) {
                E next = atomicReferenceArray.get(i3);
                if (next != null) {
                    InternalEntry next2 = next.getNext();
                    int hash = next.getHash() & length2;
                    if (next2 == null) {
                        atomicReferenceArrayNewEntryArray.set(hash, next);
                    } else {
                        InternalEntry internalEntry = next;
                        while (next2 != null) {
                            int hash2 = next2.getHash() & length2;
                            if (hash2 != hash) {
                                internalEntry = next2;
                                hash = hash2;
                            }
                            next2 = next2.getNext();
                        }
                        atomicReferenceArrayNewEntryArray.set(hash, internalEntry);
                        while (next != internalEntry) {
                            int hash3 = next.getHash() & length2;
                            InternalEntry internalEntryCopyEntry = copyEntry(next, (InternalEntry) atomicReferenceArrayNewEntryArray.get(hash3));
                            if (internalEntryCopyEntry != null) {
                                atomicReferenceArrayNewEntryArray.set(hash3, internalEntryCopyEntry);
                            } else {
                                i2--;
                            }
                            next = next.getNext();
                        }
                    }
                }
            }
            this.table = atomicReferenceArrayNewEntryArray;
            this.count = i2;
        }

        @CheckForNull
        V get(Object obj, int i2) {
            try {
                InternalEntry liveEntry = getLiveEntry(obj, i2);
                if (liveEntry == null) {
                    postReadCleanup();
                    return null;
                }
                V v2 = (V) liveEntry.getValue();
                if (v2 == null) {
                    tryDrainReferenceQueues();
                }
                return v2;
            } finally {
                postReadCleanup();
            }
        }

        @CheckForNull
        E getEntry(Object obj, int i2) {
            if (this.count == 0) {
                return null;
            }
            for (E e2 = (E) getFirst(i2); e2 != null; e2 = (E) e2.getNext()) {
                if (e2.getHash() == i2) {
                    Object key = e2.getKey();
                    if (key == null) {
                        tryDrainReferenceQueues();
                    } else if (this.map.keyEquivalence.equivalent(obj, key)) {
                        return e2;
                    }
                }
            }
            return null;
        }

        @CheckForNull
        E getFirst(int i2) {
            return this.table.get(i2 & (r0.length() - 1));
        }

        ReferenceQueue<K> getKeyReferenceQueueForTesting() {
            throw new AssertionError();
        }

        @CheckForNull
        E getLiveEntry(Object obj, int i2) {
            return (E) getEntry(obj, i2);
        }

        @CheckForNull
        V getLiveValue(E e2) {
            if (e2.getKey() == null) {
                tryDrainReferenceQueues();
                return null;
            }
            V v2 = (V) e2.getValue();
            if (v2 != null) {
                return v2;
            }
            tryDrainReferenceQueues();
            return null;
        }

        @CheckForNull
        V getLiveValueForTesting(InternalEntry<K, V, ?> internalEntry) {
            return getLiveValue(castForTesting(internalEntry));
        }

        ReferenceQueue<V> getValueReferenceQueueForTesting() {
            throw new AssertionError();
        }

        WeakValueReference<K, V, E> getWeakValueReferenceForTesting(InternalEntry<K, V, ?> internalEntry) {
            throw new AssertionError();
        }

        void initTable(AtomicReferenceArray<E> atomicReferenceArray) {
            this.threshold = (atomicReferenceArray.length() * 3) / 4;
            this.table = atomicReferenceArray;
        }

        void maybeClearReferenceQueues() {
        }

        @GuardedBy("this")
        void maybeDrainReferenceQueues() {
        }

        AtomicReferenceArray<E> newEntryArray(int i2) {
            return new AtomicReferenceArray<>(i2);
        }

        E newEntryForTesting(K k2, int i2, @CheckForNull InternalEntry<K, V, ?> internalEntry) {
            return (E) this.map.entryHelper.newEntry(self(), k2, i2, castForTesting(internalEntry));
        }

        WeakValueReference<K, V, E> newWeakValueReferenceForTesting(InternalEntry<K, V, ?> internalEntry, V v2) {
            throw new AssertionError();
        }

        void postReadCleanup() {
            if ((this.readCount.incrementAndGet() & 63) == 0) {
                runCleanup();
            }
        }

        @GuardedBy("this")
        void preWriteCleanup() {
            runLockedCleanup();
        }

        @CheckForNull
        V put(K k2, int i2, V v2, boolean z2) {
            lock();
            try {
                preWriteCleanup();
                int i3 = this.count + 1;
                if (i3 > this.threshold) {
                    expand();
                    i3 = this.count + 1;
                }
                AtomicReferenceArray<E> atomicReferenceArray = this.table;
                int length = (atomicReferenceArray.length() - 1) & i2;
                InternalEntry internalEntry = (InternalEntry) atomicReferenceArray.get(length);
                for (InternalEntry next = internalEntry; next != null; next = next.getNext()) {
                    Object key = next.getKey();
                    if (next.getHash() == i2 && key != null && this.map.keyEquivalence.equivalent(k2, key)) {
                        V v3 = (V) next.getValue();
                        if (v3 == null) {
                            this.modCount++;
                            setValue(next, v2);
                            this.count = this.count;
                            unlock();
                            return null;
                        }
                        if (z2) {
                            unlock();
                            return v3;
                        }
                        this.modCount++;
                        setValue(next, v2);
                        unlock();
                        return v3;
                    }
                }
                this.modCount++;
                InternalEntry internalEntryNewEntry = this.map.entryHelper.newEntry(self(), k2, i2, internalEntry);
                setValue(internalEntryNewEntry, v2);
                atomicReferenceArray.set(length, internalEntryNewEntry);
                this.count = i3;
                unlock();
                return null;
            } catch (Throwable th) {
                unlock();
                throw th;
            }
        }

        @CanIgnoreReturnValue
        boolean reclaimKey(E e2, int i2) {
            lock();
            try {
                AtomicReferenceArray<E> atomicReferenceArray = this.table;
                int length = i2 & (atomicReferenceArray.length() - 1);
                InternalEntry internalEntry = (InternalEntry) atomicReferenceArray.get(length);
                for (InternalEntry next = internalEntry; next != null; next = next.getNext()) {
                    if (next == e2) {
                        this.modCount++;
                        InternalEntry internalEntryRemoveFromChain = removeFromChain(internalEntry, next);
                        int i3 = this.count - 1;
                        atomicReferenceArray.set(length, internalEntryRemoveFromChain);
                        this.count = i3;
                        return true;
                    }
                }
                unlock();
                return false;
            } finally {
                unlock();
            }
        }

        @CanIgnoreReturnValue
        boolean reclaimValue(K k2, int i2, WeakValueReference<K, V, E> weakValueReference) {
            lock();
            try {
                AtomicReferenceArray<E> atomicReferenceArray = this.table;
                int length = (atomicReferenceArray.length() - 1) & i2;
                InternalEntry internalEntry = (InternalEntry) atomicReferenceArray.get(length);
                for (InternalEntry next = internalEntry; next != null; next = next.getNext()) {
                    Object key = next.getKey();
                    if (next.getHash() == i2 && key != null && this.map.keyEquivalence.equivalent(k2, key)) {
                        if (((WeakValueEntry) next).getValueReference() != weakValueReference) {
                            return false;
                        }
                        this.modCount++;
                        InternalEntry internalEntryRemoveFromChain = removeFromChain(internalEntry, next);
                        int i3 = this.count - 1;
                        atomicReferenceArray.set(length, internalEntryRemoveFromChain);
                        this.count = i3;
                        return true;
                    }
                }
                return false;
            } finally {
                unlock();
            }
        }

        @CanIgnoreReturnValue
        @CheckForNull
        V remove(Object obj, int i2) {
            lock();
            try {
                preWriteCleanup();
                AtomicReferenceArray<E> atomicReferenceArray = this.table;
                int length = (atomicReferenceArray.length() - 1) & i2;
                InternalEntry internalEntry = (InternalEntry) atomicReferenceArray.get(length);
                for (InternalEntry next = internalEntry; next != null; next = next.getNext()) {
                    Object key = next.getKey();
                    if (next.getHash() == i2 && key != null && this.map.keyEquivalence.equivalent(obj, key)) {
                        V v2 = (V) next.getValue();
                        if (v2 == null && !isCollected(next)) {
                            return null;
                        }
                        this.modCount++;
                        InternalEntry internalEntryRemoveFromChain = removeFromChain(internalEntry, next);
                        int i3 = this.count - 1;
                        atomicReferenceArray.set(length, internalEntryRemoveFromChain);
                        this.count = i3;
                        return v2;
                    }
                }
                return null;
            } finally {
                unlock();
            }
        }

        @GuardedBy("this")
        boolean removeEntryForTesting(E e2) {
            int hash = e2.getHash();
            AtomicReferenceArray<E> atomicReferenceArray = this.table;
            int length = hash & (atomicReferenceArray.length() - 1);
            InternalEntry internalEntry = (InternalEntry) atomicReferenceArray.get(length);
            for (InternalEntry next = internalEntry; next != null; next = next.getNext()) {
                if (next == e2) {
                    this.modCount++;
                    InternalEntry internalEntryRemoveFromChain = removeFromChain(internalEntry, next);
                    int i2 = this.count - 1;
                    atomicReferenceArray.set(length, internalEntryRemoveFromChain);
                    this.count = i2;
                    return true;
                }
            }
            return false;
        }

        @CheckForNull
        @GuardedBy("this")
        E removeFromChain(E e2, E e3) {
            int i2 = this.count;
            E e4 = (E) e3.getNext();
            while (e2 != e3) {
                InternalEntry internalEntryCopyEntry = copyEntry(e2, e4);
                if (internalEntryCopyEntry != null) {
                    e4 = (E) internalEntryCopyEntry;
                } else {
                    i2--;
                }
                e2 = (E) e2.getNext();
            }
            this.count = i2;
            return e4;
        }

        @CheckForNull
        E removeFromChainForTesting(InternalEntry<K, V, ?> internalEntry, InternalEntry<K, V, ?> internalEntry2) {
            return (E) removeFromChain(castForTesting(internalEntry), castForTesting(internalEntry2));
        }

        @CanIgnoreReturnValue
        boolean removeTableEntryForTesting(InternalEntry<K, V, ?> internalEntry) {
            return removeEntryForTesting(castForTesting(internalEntry));
        }

        boolean replace(K k2, int i2, V v2, V v3) {
            lock();
            try {
                preWriteCleanup();
                AtomicReferenceArray<E> atomicReferenceArray = this.table;
                int length = (atomicReferenceArray.length() - 1) & i2;
                InternalEntry internalEntry = (InternalEntry) atomicReferenceArray.get(length);
                for (InternalEntry next = internalEntry; next != null; next = next.getNext()) {
                    Object key = next.getKey();
                    if (next.getHash() == i2 && key != null && this.map.keyEquivalence.equivalent(k2, key)) {
                        Object value = next.getValue();
                        if (value != null) {
                            if (!this.map.valueEquivalence().equivalent(v2, value)) {
                                return false;
                            }
                            this.modCount++;
                            setValue(next, v3);
                            return true;
                        }
                        if (isCollected(next)) {
                            this.modCount++;
                            InternalEntry internalEntryRemoveFromChain = removeFromChain(internalEntry, next);
                            int i3 = this.count - 1;
                            atomicReferenceArray.set(length, internalEntryRemoveFromChain);
                            this.count = i3;
                        }
                        return false;
                    }
                }
                return false;
            } finally {
                unlock();
            }
        }

        void runCleanup() {
            runLockedCleanup();
        }

        void runLockedCleanup() {
            if (tryLock()) {
                try {
                    maybeDrainReferenceQueues();
                    this.readCount.set(0);
                } finally {
                    unlock();
                }
            }
        }

        abstract S self();

        void setTableEntryForTesting(int i2, InternalEntry<K, V, ?> internalEntry) {
            this.table.set(i2, castForTesting(internalEntry));
        }

        void setValue(E e2, V v2) {
            this.map.entryHelper.setValue(self(), e2, v2);
        }

        void setValueForTesting(InternalEntry<K, V, ?> internalEntry, V v2) {
            this.map.entryHelper.setValue(self(), castForTesting(internalEntry), v2);
        }

        void setWeakValueReferenceForTesting(InternalEntry<K, V, ?> internalEntry, WeakValueReference<K, V, ? extends InternalEntry<K, V, ?>> weakValueReference) {
            throw new AssertionError();
        }

        void tryDrainReferenceQueues() {
            if (tryLock()) {
                try {
                    maybeDrainReferenceQueues();
                } finally {
                    unlock();
                }
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:12:0x003d, code lost:
        
            if (r8.map.valueEquivalence().equivalent(r11, r4.getValue()) == false) goto L14;
         */
        /* JADX WARN: Code restructure failed: missing block: B:13:0x003f, code lost:
        
            r5 = true;
         */
        /* JADX WARN: Code restructure failed: missing block: B:15:0x0045, code lost:
        
            if (isCollected(r4) == false) goto L21;
         */
        /* JADX WARN: Code restructure failed: missing block: B:16:0x0047, code lost:
        
            r8.modCount++;
            r9 = removeFromChain(r3, r4);
            r10 = r8.count - 1;
            r0.set(r1, r9);
            r8.count = r10;
         */
        /* JADX WARN: Code restructure failed: missing block: B:18:0x005b, code lost:
        
            return r5;
         */
        /* JADX WARN: Code restructure failed: missing block: B:22:0x0061, code lost:
        
            return false;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        boolean remove(java.lang.Object r9, int r10, java.lang.Object r11) {
            /*
                r8 = this;
                r8.lock()
                r8.preWriteCleanup()     // Catch: java.lang.Throwable -> L5c
                java.util.concurrent.atomic.AtomicReferenceArray<E extends com.google.common.collect.MapMakerInternalMap$InternalEntry<K, V, E>> r0 = r8.table     // Catch: java.lang.Throwable -> L5c
                int r1 = r0.length()     // Catch: java.lang.Throwable -> L5c
                r2 = 1
                int r1 = r1 - r2
                r1 = r1 & r10
                java.lang.Object r3 = r0.get(r1)     // Catch: java.lang.Throwable -> L5c
                com.google.common.collect.MapMakerInternalMap$InternalEntry r3 = (com.google.common.collect.MapMakerInternalMap.InternalEntry) r3     // Catch: java.lang.Throwable -> L5c
                r4 = r3
            L16:
                r5 = 0
                if (r4 == 0) goto L67
                java.lang.Object r6 = r4.getKey()     // Catch: java.lang.Throwable -> L5c
                int r7 = r4.getHash()     // Catch: java.lang.Throwable -> L5c
                if (r7 != r10) goto L62
                if (r6 == 0) goto L62
                com.google.common.collect.MapMakerInternalMap<K, V, E extends com.google.common.collect.MapMakerInternalMap$InternalEntry<K, V, E>, S extends com.google.common.collect.MapMakerInternalMap$Segment<K, V, E, S>> r7 = r8.map     // Catch: java.lang.Throwable -> L5c
                com.google.common.base.Equivalence<java.lang.Object> r7 = r7.keyEquivalence     // Catch: java.lang.Throwable -> L5c
                boolean r6 = r7.equivalent(r9, r6)     // Catch: java.lang.Throwable -> L5c
                if (r6 == 0) goto L62
                java.lang.Object r9 = r4.getValue()     // Catch: java.lang.Throwable -> L5c
                com.google.common.collect.MapMakerInternalMap<K, V, E extends com.google.common.collect.MapMakerInternalMap$InternalEntry<K, V, E>, S extends com.google.common.collect.MapMakerInternalMap$Segment<K, V, E, S>> r10 = r8.map     // Catch: java.lang.Throwable -> L5c
                com.google.common.base.Equivalence r10 = r10.valueEquivalence()     // Catch: java.lang.Throwable -> L5c
                boolean r9 = r10.equivalent(r11, r9)     // Catch: java.lang.Throwable -> L5c
                if (r9 == 0) goto L41
                r5 = r2
                goto L47
            L41:
                boolean r9 = isCollected(r4)     // Catch: java.lang.Throwable -> L5c
                if (r9 == 0) goto L5e
            L47:
                int r9 = r8.modCount     // Catch: java.lang.Throwable -> L5c
                int r9 = r9 + r2
                r8.modCount = r9     // Catch: java.lang.Throwable -> L5c
                com.google.common.collect.MapMakerInternalMap$InternalEntry r9 = r8.removeFromChain(r3, r4)     // Catch: java.lang.Throwable -> L5c
                int r10 = r8.count     // Catch: java.lang.Throwable -> L5c
                int r10 = r10 - r2
                r0.set(r1, r9)     // Catch: java.lang.Throwable -> L5c
                r8.count = r10     // Catch: java.lang.Throwable -> L5c
                r8.unlock()
                return r5
            L5c:
                r9 = move-exception
                goto L6b
            L5e:
                r8.unlock()
                return r5
            L62:
                com.google.common.collect.MapMakerInternalMap$InternalEntry r4 = r4.getNext()     // Catch: java.lang.Throwable -> L5c
                goto L16
            L67:
                r8.unlock()
                return r5
            L6b:
                r8.unlock()
                throw r9
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.MapMakerInternalMap.Segment.remove(java.lang.Object, int, java.lang.Object):boolean");
        }

        @CheckForNull
        V replace(K k2, int i2, V v2) {
            lock();
            try {
                preWriteCleanup();
                AtomicReferenceArray<E> atomicReferenceArray = this.table;
                int length = (atomicReferenceArray.length() - 1) & i2;
                InternalEntry internalEntry = (InternalEntry) atomicReferenceArray.get(length);
                for (InternalEntry next = internalEntry; next != null; next = next.getNext()) {
                    Object key = next.getKey();
                    if (next.getHash() == i2 && key != null && this.map.keyEquivalence.equivalent(k2, key)) {
                        V v3 = (V) next.getValue();
                        if (v3 == null) {
                            if (isCollected(next)) {
                                this.modCount++;
                                InternalEntry internalEntryRemoveFromChain = removeFromChain(internalEntry, next);
                                int i3 = this.count - 1;
                                atomicReferenceArray.set(length, internalEntryRemoveFromChain);
                                this.count = i3;
                            }
                            return null;
                        }
                        this.modCount++;
                        setValue(next, v2);
                        return v3;
                    }
                }
                return null;
            } finally {
                unlock();
            }
        }
    }
}
