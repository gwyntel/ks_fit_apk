package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.J2ktIncompatible;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.concurrent.LazyInit;
import com.google.j2objc.annotations.RetainedWith;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.annotation.CheckForNull;

@GwtCompatible
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public final class HashBiMap<K, V> extends AbstractMap<K, V> implements BiMap<K, V>, Serializable {
    private static final int ABSENT = -1;
    private static final int ENDPOINT = -2;

    @LazyInit
    private transient Set<Map.Entry<K, V>> entrySet;
    private transient int firstInInsertionOrder;
    private transient int[] hashTableKToV;
    private transient int[] hashTableVToK;

    @RetainedWith
    @CheckForNull
    @LazyInit
    private transient BiMap<V, K> inverse;

    @LazyInit
    private transient Set<K> keySet;
    transient K[] keys;
    private transient int lastInInsertionOrder;
    transient int modCount;
    private transient int[] nextInBucketKToV;
    private transient int[] nextInBucketVToK;
    private transient int[] nextInInsertionOrder;
    private transient int[] prevInInsertionOrder;
    transient int size;

    @LazyInit
    private transient Set<V> valueSet;
    transient V[] values;

    final class EntryForKey extends AbstractMapEntry<K, V> {

        /* renamed from: a, reason: collision with root package name */
        final Object f13990a;

        /* renamed from: b, reason: collision with root package name */
        int f13991b;

        EntryForKey(int i2) {
            this.f13990a = NullnessCasts.a(HashBiMap.this.keys[i2]);
            this.f13991b = i2;
        }

        @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
        @ParametricNullness
        public K getKey() {
            return (K) this.f13990a;
        }

        @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
        @ParametricNullness
        public V getValue() {
            updateIndex();
            int i2 = this.f13991b;
            return i2 == -1 ? (V) NullnessCasts.b() : (V) NullnessCasts.a(HashBiMap.this.values[i2]);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
        @ParametricNullness
        public V setValue(@ParametricNullness V v2) {
            updateIndex();
            int i2 = this.f13991b;
            if (i2 == -1) {
                HashBiMap.this.put(this.f13990a, v2);
                return (V) NullnessCasts.b();
            }
            V v3 = (V) NullnessCasts.a(HashBiMap.this.values[i2]);
            if (Objects.equal(v3, v2)) {
                return v2;
            }
            HashBiMap.this.replaceValueInEntry(this.f13991b, v2, false);
            return v3;
        }

        void updateIndex() {
            int i2 = this.f13991b;
            if (i2 != -1) {
                HashBiMap hashBiMap = HashBiMap.this;
                if (i2 <= hashBiMap.size && Objects.equal(hashBiMap.keys[i2], this.f13990a)) {
                    return;
                }
            }
            this.f13991b = HashBiMap.this.findEntryByKey(this.f13990a);
        }
    }

    static final class EntryForValue<K, V> extends AbstractMapEntry<V, K> {

        /* renamed from: a, reason: collision with root package name */
        final HashBiMap f13993a;

        /* renamed from: b, reason: collision with root package name */
        final Object f13994b;

        /* renamed from: c, reason: collision with root package name */
        int f13995c;

        EntryForValue(HashBiMap hashBiMap, int i2) {
            this.f13993a = hashBiMap;
            this.f13994b = NullnessCasts.a(hashBiMap.values[i2]);
            this.f13995c = i2;
        }

        private void updateIndex() {
            int i2 = this.f13995c;
            if (i2 != -1) {
                HashBiMap hashBiMap = this.f13993a;
                if (i2 <= hashBiMap.size && Objects.equal(this.f13994b, hashBiMap.values[i2])) {
                    return;
                }
            }
            this.f13995c = this.f13993a.findEntryByValue(this.f13994b);
        }

        @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
        @ParametricNullness
        public V getKey() {
            return (V) this.f13994b;
        }

        @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
        @ParametricNullness
        public K getValue() {
            updateIndex();
            int i2 = this.f13995c;
            return i2 == -1 ? (K) NullnessCasts.b() : (K) NullnessCasts.a(this.f13993a.keys[i2]);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
        @ParametricNullness
        public K setValue(@ParametricNullness K k2) {
            updateIndex();
            int i2 = this.f13995c;
            if (i2 == -1) {
                this.f13993a.putInverse(this.f13994b, k2, false);
                return (K) NullnessCasts.b();
            }
            K k3 = (K) NullnessCasts.a(this.f13993a.keys[i2]);
            if (Objects.equal(k3, k2)) {
                return k2;
            }
            this.f13993a.replaceKeyInEntry(this.f13995c, k2, false);
            return k3;
        }
    }

    final class EntrySet extends View<K, V, Map.Entry<K, V>> {
        EntrySet() {
            super(HashBiMap.this);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.collect.HashBiMap.View
        /* renamed from: b, reason: merged with bridge method [inline-methods] */
        public Map.Entry a(int i2) {
            return new EntryForKey(i2);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(@CheckForNull Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            Object key = entry.getKey();
            Object value = entry.getValue();
            int iFindEntryByKey = HashBiMap.this.findEntryByKey(key);
            return iFindEntryByKey != -1 && Objects.equal(value, HashBiMap.this.values[iFindEntryByKey]);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        @CanIgnoreReturnValue
        public boolean remove(@CheckForNull Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            Object key = entry.getKey();
            Object value = entry.getValue();
            int iD = Hashing.d(key);
            int iFindEntryByKey = HashBiMap.this.findEntryByKey(key, iD);
            if (iFindEntryByKey == -1 || !Objects.equal(value, HashBiMap.this.values[iFindEntryByKey])) {
                return false;
            }
            HashBiMap.this.removeEntryKeyHashKnown(iFindEntryByKey, iD);
            return true;
        }
    }

    static class Inverse<K, V> extends AbstractMap<V, K> implements BiMap<V, K>, Serializable {
        private final HashBiMap<K, V> forward;
        private transient Set<Map.Entry<V, K>> inverseEntrySet;

        Inverse(HashBiMap<K, V> hashBiMap) {
            this.forward = hashBiMap;
        }

        @GwtIncompatible("serialization")
        private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
            objectInputStream.defaultReadObject();
            ((HashBiMap) this.forward).inverse = this;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public void clear() {
            this.forward.clear();
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean containsKey(@CheckForNull Object obj) {
            return this.forward.containsValue(obj);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean containsValue(@CheckForNull Object obj) {
            return this.forward.containsKey(obj);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public Set<Map.Entry<V, K>> entrySet() {
            Set<Map.Entry<V, K>> set = this.inverseEntrySet;
            if (set != null) {
                return set;
            }
            InverseEntrySet inverseEntrySet = new InverseEntrySet(this.forward);
            this.inverseEntrySet = inverseEntrySet;
            return inverseEntrySet;
        }

        @Override // com.google.common.collect.BiMap
        @CanIgnoreReturnValue
        @CheckForNull
        public K forcePut(@ParametricNullness V v2, @ParametricNullness K k2) {
            return this.forward.putInverse(v2, k2, true);
        }

        @Override // java.util.AbstractMap, java.util.Map
        @CheckForNull
        public K get(@CheckForNull Object obj) {
            return this.forward.getInverse(obj);
        }

        @Override // com.google.common.collect.BiMap
        public BiMap<K, V> inverse() {
            return this.forward;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public Set<V> keySet() {
            return this.forward.values();
        }

        @Override // java.util.AbstractMap, java.util.Map, com.google.common.collect.BiMap
        @CanIgnoreReturnValue
        @CheckForNull
        public K put(@ParametricNullness V v2, @ParametricNullness K k2) {
            return this.forward.putInverse(v2, k2, false);
        }

        @Override // java.util.AbstractMap, java.util.Map
        @CanIgnoreReturnValue
        @CheckForNull
        public K remove(@CheckForNull Object obj) {
            return this.forward.removeInverse(obj);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public int size() {
            return this.forward.size;
        }

        @Override // java.util.AbstractMap, java.util.Map, com.google.common.collect.BiMap
        public Set<K> values() {
            return this.forward.keySet();
        }
    }

    static class InverseEntrySet<K, V> extends View<K, V, Map.Entry<V, K>> {
        InverseEntrySet(HashBiMap hashBiMap) {
            super(hashBiMap);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.collect.HashBiMap.View
        /* renamed from: b, reason: merged with bridge method [inline-methods] */
        public Map.Entry a(int i2) {
            return new EntryForValue(this.f13999a, i2);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(@CheckForNull Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            Object key = entry.getKey();
            Object value = entry.getValue();
            int iFindEntryByValue = this.f13999a.findEntryByValue(key);
            return iFindEntryByValue != -1 && Objects.equal(this.f13999a.keys[iFindEntryByValue], value);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(@CheckForNull Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            Object key = entry.getKey();
            Object value = entry.getValue();
            int iD = Hashing.d(key);
            int iFindEntryByValue = this.f13999a.findEntryByValue(key, iD);
            if (iFindEntryByValue == -1 || !Objects.equal(this.f13999a.keys[iFindEntryByValue], value)) {
                return false;
            }
            this.f13999a.removeEntryValueHashKnown(iFindEntryByValue, iD);
            return true;
        }
    }

    final class KeySet extends View<K, V, K> {
        KeySet() {
            super(HashBiMap.this);
        }

        @Override // com.google.common.collect.HashBiMap.View
        Object a(int i2) {
            return NullnessCasts.a(HashBiMap.this.keys[i2]);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(@CheckForNull Object obj) {
            return HashBiMap.this.containsKey(obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(@CheckForNull Object obj) {
            int iD = Hashing.d(obj);
            int iFindEntryByKey = HashBiMap.this.findEntryByKey(obj, iD);
            if (iFindEntryByKey == -1) {
                return false;
            }
            HashBiMap.this.removeEntryKeyHashKnown(iFindEntryByKey, iD);
            return true;
        }
    }

    final class ValueSet extends View<K, V, V> {
        ValueSet() {
            super(HashBiMap.this);
        }

        @Override // com.google.common.collect.HashBiMap.View
        Object a(int i2) {
            return NullnessCasts.a(HashBiMap.this.values[i2]);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(@CheckForNull Object obj) {
            return HashBiMap.this.containsValue(obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(@CheckForNull Object obj) {
            int iD = Hashing.d(obj);
            int iFindEntryByValue = HashBiMap.this.findEntryByValue(obj, iD);
            if (iFindEntryByValue == -1) {
                return false;
            }
            HashBiMap.this.removeEntryValueHashKnown(iFindEntryByValue, iD);
            return true;
        }
    }

    static abstract class View<K, V, T> extends AbstractSet<T> {

        /* renamed from: a, reason: collision with root package name */
        final HashBiMap f13999a;

        View(HashBiMap hashBiMap) {
            this.f13999a = hashBiMap;
        }

        abstract Object a(int i2);

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            this.f13999a.clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<T> iterator() {
            return new Iterator<T>() { // from class: com.google.common.collect.HashBiMap.View.1
                private int expectedModCount;
                private int index;
                private int indexToRemove = -1;
                private int remaining;

                {
                    this.index = View.this.f13999a.firstInInsertionOrder;
                    HashBiMap hashBiMap = View.this.f13999a;
                    this.expectedModCount = hashBiMap.modCount;
                    this.remaining = hashBiMap.size;
                }

                private void checkForComodification() {
                    if (View.this.f13999a.modCount != this.expectedModCount) {
                        throw new ConcurrentModificationException();
                    }
                }

                @Override // java.util.Iterator
                public boolean hasNext() {
                    checkForComodification();
                    return this.index != -2 && this.remaining > 0;
                }

                @Override // java.util.Iterator
                @ParametricNullness
                public T next() {
                    if (!hasNext()) {
                        throw new NoSuchElementException();
                    }
                    T t2 = (T) View.this.a(this.index);
                    this.indexToRemove = this.index;
                    this.index = View.this.f13999a.nextInInsertionOrder[this.index];
                    this.remaining--;
                    return t2;
                }

                @Override // java.util.Iterator
                public void remove() {
                    checkForComodification();
                    CollectPreconditions.e(this.indexToRemove != -1);
                    View.this.f13999a.removeEntry(this.indexToRemove);
                    int i2 = this.index;
                    HashBiMap hashBiMap = View.this.f13999a;
                    if (i2 == hashBiMap.size) {
                        this.index = this.indexToRemove;
                    }
                    this.indexToRemove = -1;
                    this.expectedModCount = hashBiMap.modCount;
                }
            };
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return this.f13999a.size;
        }
    }

    private HashBiMap(int i2) {
        init(i2);
    }

    private int bucket(int i2) {
        return i2 & (this.hashTableKToV.length - 1);
    }

    public static <K, V> HashBiMap<K, V> create() {
        return create(16);
    }

    private static int[] createFilledWithAbsent(int i2) {
        int[] iArr = new int[i2];
        Arrays.fill(iArr, -1);
        return iArr;
    }

    private void deleteFromTableKToV(int i2, int i3) {
        Preconditions.checkArgument(i2 != -1);
        int iBucket = bucket(i3);
        int[] iArr = this.hashTableKToV;
        int i4 = iArr[iBucket];
        if (i4 == i2) {
            int[] iArr2 = this.nextInBucketKToV;
            iArr[iBucket] = iArr2[i2];
            iArr2[i2] = -1;
            return;
        }
        int i5 = this.nextInBucketKToV[i4];
        while (true) {
            int i6 = i4;
            i4 = i5;
            if (i4 == -1) {
                throw new AssertionError("Expected to find entry with key " + this.keys[i2]);
            }
            if (i4 == i2) {
                int[] iArr3 = this.nextInBucketKToV;
                iArr3[i6] = iArr3[i2];
                iArr3[i2] = -1;
                return;
            }
            i5 = this.nextInBucketKToV[i4];
        }
    }

    private void deleteFromTableVToK(int i2, int i3) {
        Preconditions.checkArgument(i2 != -1);
        int iBucket = bucket(i3);
        int[] iArr = this.hashTableVToK;
        int i4 = iArr[iBucket];
        if (i4 == i2) {
            int[] iArr2 = this.nextInBucketVToK;
            iArr[iBucket] = iArr2[i2];
            iArr2[i2] = -1;
            return;
        }
        int i5 = this.nextInBucketVToK[i4];
        while (true) {
            int i6 = i4;
            i4 = i5;
            if (i4 == -1) {
                throw new AssertionError("Expected to find entry with value " + this.values[i2]);
            }
            if (i4 == i2) {
                int[] iArr3 = this.nextInBucketVToK;
                iArr3[i6] = iArr3[i2];
                iArr3[i2] = -1;
                return;
            }
            i5 = this.nextInBucketVToK[i4];
        }
    }

    private void ensureCapacity(int i2) {
        int[] iArr = this.nextInBucketKToV;
        if (iArr.length < i2) {
            int iA = ImmutableCollection.Builder.a(iArr.length, i2);
            this.keys = (K[]) Arrays.copyOf(this.keys, iA);
            this.values = (V[]) Arrays.copyOf(this.values, iA);
            this.nextInBucketKToV = expandAndFillWithAbsent(this.nextInBucketKToV, iA);
            this.nextInBucketVToK = expandAndFillWithAbsent(this.nextInBucketVToK, iA);
            this.prevInInsertionOrder = expandAndFillWithAbsent(this.prevInInsertionOrder, iA);
            this.nextInInsertionOrder = expandAndFillWithAbsent(this.nextInInsertionOrder, iA);
        }
        if (this.hashTableKToV.length < i2) {
            int iA2 = Hashing.a(i2, 1.0d);
            this.hashTableKToV = createFilledWithAbsent(iA2);
            this.hashTableVToK = createFilledWithAbsent(iA2);
            for (int i3 = 0; i3 < this.size; i3++) {
                int iBucket = bucket(Hashing.d(this.keys[i3]));
                int[] iArr2 = this.nextInBucketKToV;
                int[] iArr3 = this.hashTableKToV;
                iArr2[i3] = iArr3[iBucket];
                iArr3[iBucket] = i3;
                int iBucket2 = bucket(Hashing.d(this.values[i3]));
                int[] iArr4 = this.nextInBucketVToK;
                int[] iArr5 = this.hashTableVToK;
                iArr4[i3] = iArr5[iBucket2];
                iArr5[iBucket2] = i3;
            }
        }
    }

    private static int[] expandAndFillWithAbsent(int[] iArr, int i2) {
        int length = iArr.length;
        int[] iArrCopyOf = Arrays.copyOf(iArr, i2);
        Arrays.fill(iArrCopyOf, length, i2, -1);
        return iArrCopyOf;
    }

    private void insertIntoTableKToV(int i2, int i3) {
        Preconditions.checkArgument(i2 != -1);
        int iBucket = bucket(i3);
        int[] iArr = this.nextInBucketKToV;
        int[] iArr2 = this.hashTableKToV;
        iArr[i2] = iArr2[iBucket];
        iArr2[iBucket] = i2;
    }

    private void insertIntoTableVToK(int i2, int i3) {
        Preconditions.checkArgument(i2 != -1);
        int iBucket = bucket(i3);
        int[] iArr = this.nextInBucketVToK;
        int[] iArr2 = this.hashTableVToK;
        iArr[i2] = iArr2[iBucket];
        iArr2[iBucket] = i2;
    }

    private void moveEntryToIndex(int i2, int i3) {
        int i4;
        int i5;
        if (i2 == i3) {
            return;
        }
        int i6 = this.prevInInsertionOrder[i2];
        int i7 = this.nextInInsertionOrder[i2];
        setSucceeds(i6, i3);
        setSucceeds(i3, i7);
        K[] kArr = this.keys;
        K k2 = kArr[i2];
        V[] vArr = this.values;
        V v2 = vArr[i2];
        kArr[i3] = k2;
        vArr[i3] = v2;
        int iBucket = bucket(Hashing.d(k2));
        int[] iArr = this.hashTableKToV;
        int i8 = iArr[iBucket];
        if (i8 == i2) {
            iArr[iBucket] = i3;
        } else {
            int i9 = this.nextInBucketKToV[i8];
            while (true) {
                i4 = i8;
                i8 = i9;
                if (i8 == i2) {
                    break;
                } else {
                    i9 = this.nextInBucketKToV[i8];
                }
            }
            this.nextInBucketKToV[i4] = i3;
        }
        int[] iArr2 = this.nextInBucketKToV;
        iArr2[i3] = iArr2[i2];
        iArr2[i2] = -1;
        int iBucket2 = bucket(Hashing.d(v2));
        int[] iArr3 = this.hashTableVToK;
        int i10 = iArr3[iBucket2];
        if (i10 == i2) {
            iArr3[iBucket2] = i3;
        } else {
            int i11 = this.nextInBucketVToK[i10];
            while (true) {
                i5 = i10;
                i10 = i11;
                if (i10 == i2) {
                    break;
                } else {
                    i11 = this.nextInBucketVToK[i10];
                }
            }
            this.nextInBucketVToK[i5] = i3;
        }
        int[] iArr4 = this.nextInBucketVToK;
        iArr4[i3] = iArr4[i2];
        iArr4[i2] = -1;
    }

    @J2ktIncompatible
    @GwtIncompatible
    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        int iH = Serialization.h(objectInputStream);
        init(16);
        Serialization.c(this, objectInputStream, iH);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void replaceKeyInEntry(int i2, @ParametricNullness K k2, boolean z2) {
        int i3;
        Preconditions.checkArgument(i2 != -1);
        int iD = Hashing.d(k2);
        int iFindEntryByKey = findEntryByKey(k2, iD);
        int i4 = this.lastInInsertionOrder;
        if (iFindEntryByKey == -1) {
            i3 = -2;
        } else {
            if (!z2) {
                throw new IllegalArgumentException("Key already present in map: " + k2);
            }
            i4 = this.prevInInsertionOrder[iFindEntryByKey];
            i3 = this.nextInInsertionOrder[iFindEntryByKey];
            removeEntryKeyHashKnown(iFindEntryByKey, iD);
            if (i2 == this.size) {
                i2 = iFindEntryByKey;
            }
        }
        if (i4 == i2) {
            i4 = this.prevInInsertionOrder[i2];
        } else if (i4 == this.size) {
            i4 = iFindEntryByKey;
        }
        if (i3 == i2) {
            iFindEntryByKey = this.nextInInsertionOrder[i2];
        } else if (i3 != this.size) {
            iFindEntryByKey = i3;
        }
        setSucceeds(this.prevInInsertionOrder[i2], this.nextInInsertionOrder[i2]);
        deleteFromTableKToV(i2, Hashing.d(this.keys[i2]));
        this.keys[i2] = k2;
        insertIntoTableKToV(i2, Hashing.d(k2));
        setSucceeds(i4, i2);
        setSucceeds(i2, iFindEntryByKey);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void replaceValueInEntry(int i2, @ParametricNullness V v2, boolean z2) {
        Preconditions.checkArgument(i2 != -1);
        int iD = Hashing.d(v2);
        int iFindEntryByValue = findEntryByValue(v2, iD);
        if (iFindEntryByValue != -1) {
            if (!z2) {
                throw new IllegalArgumentException("Value already present in map: " + v2);
            }
            removeEntryValueHashKnown(iFindEntryByValue, iD);
            if (i2 == this.size) {
                i2 = iFindEntryByValue;
            }
        }
        deleteFromTableVToK(i2, Hashing.d(this.values[i2]));
        this.values[i2] = v2;
        insertIntoTableVToK(i2, iD);
    }

    private void setSucceeds(int i2, int i3) {
        if (i2 == -2) {
            this.firstInInsertionOrder = i3;
        } else {
            this.nextInInsertionOrder[i2] = i3;
        }
        if (i3 == -2) {
            this.lastInInsertionOrder = i2;
        } else {
            this.prevInInsertionOrder[i3] = i2;
        }
    }

    @J2ktIncompatible
    @GwtIncompatible
    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        Serialization.i(this, objectOutputStream);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void clear() {
        Arrays.fill(this.keys, 0, this.size, (Object) null);
        Arrays.fill(this.values, 0, this.size, (Object) null);
        Arrays.fill(this.hashTableKToV, -1);
        Arrays.fill(this.hashTableVToK, -1);
        Arrays.fill(this.nextInBucketKToV, 0, this.size, -1);
        Arrays.fill(this.nextInBucketVToK, 0, this.size, -1);
        Arrays.fill(this.prevInInsertionOrder, 0, this.size, -1);
        Arrays.fill(this.nextInInsertionOrder, 0, this.size, -1);
        this.size = 0;
        this.firstInInsertionOrder = -2;
        this.lastInInsertionOrder = -2;
        this.modCount++;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsKey(@CheckForNull Object obj) {
        return findEntryByKey(obj) != -1;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsValue(@CheckForNull Object obj) {
        return findEntryByValue(obj) != -1;
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

    int findEntry(@CheckForNull Object obj, int i2, int[] iArr, int[] iArr2, Object[] objArr) {
        int i3 = iArr[bucket(i2)];
        while (i3 != -1) {
            if (Objects.equal(objArr[i3], obj)) {
                return i3;
            }
            i3 = iArr2[i3];
        }
        return -1;
    }

    int findEntryByKey(@CheckForNull Object obj) {
        return findEntryByKey(obj, Hashing.d(obj));
    }

    int findEntryByValue(@CheckForNull Object obj) {
        return findEntryByValue(obj, Hashing.d(obj));
    }

    @Override // com.google.common.collect.BiMap
    @CanIgnoreReturnValue
    @CheckForNull
    public V forcePut(@ParametricNullness K k2, @ParametricNullness V v2) {
        return put(k2, v2, true);
    }

    @Override // java.util.AbstractMap, java.util.Map
    @CheckForNull
    public V get(@CheckForNull Object obj) {
        int iFindEntryByKey = findEntryByKey(obj);
        if (iFindEntryByKey == -1) {
            return null;
        }
        return this.values[iFindEntryByKey];
    }

    @CheckForNull
    K getInverse(@CheckForNull Object obj) {
        int iFindEntryByValue = findEntryByValue(obj);
        if (iFindEntryByValue == -1) {
            return null;
        }
        return this.keys[iFindEntryByValue];
    }

    void init(int i2) {
        CollectPreconditions.b(i2, "expectedSize");
        int iA = Hashing.a(i2, 1.0d);
        this.size = 0;
        this.keys = (K[]) new Object[i2];
        this.values = (V[]) new Object[i2];
        this.hashTableKToV = createFilledWithAbsent(iA);
        this.hashTableVToK = createFilledWithAbsent(iA);
        this.nextInBucketKToV = createFilledWithAbsent(i2);
        this.nextInBucketVToK = createFilledWithAbsent(i2);
        this.firstInInsertionOrder = -2;
        this.lastInInsertionOrder = -2;
        this.prevInInsertionOrder = createFilledWithAbsent(i2);
        this.nextInInsertionOrder = createFilledWithAbsent(i2);
    }

    @Override // com.google.common.collect.BiMap
    public BiMap<V, K> inverse() {
        BiMap<V, K> biMap = this.inverse;
        if (biMap != null) {
            return biMap;
        }
        Inverse inverse = new Inverse(this);
        this.inverse = inverse;
        return inverse;
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

    @Override // java.util.AbstractMap, java.util.Map, com.google.common.collect.BiMap
    @CanIgnoreReturnValue
    @CheckForNull
    public V put(@ParametricNullness K k2, @ParametricNullness V v2) {
        return put(k2, v2, false);
    }

    @CanIgnoreReturnValue
    @CheckForNull
    K putInverse(@ParametricNullness V v2, @ParametricNullness K k2, boolean z2) {
        int iD = Hashing.d(v2);
        int iFindEntryByValue = findEntryByValue(v2, iD);
        if (iFindEntryByValue != -1) {
            K k3 = this.keys[iFindEntryByValue];
            if (Objects.equal(k3, k2)) {
                return k2;
            }
            replaceKeyInEntry(iFindEntryByValue, k2, z2);
            return k3;
        }
        int i2 = this.lastInInsertionOrder;
        int iD2 = Hashing.d(k2);
        int iFindEntryByKey = findEntryByKey(k2, iD2);
        if (!z2) {
            Preconditions.checkArgument(iFindEntryByKey == -1, "Key already present: %s", k2);
        } else if (iFindEntryByKey != -1) {
            i2 = this.prevInInsertionOrder[iFindEntryByKey];
            removeEntryKeyHashKnown(iFindEntryByKey, iD2);
        }
        ensureCapacity(this.size + 1);
        K[] kArr = this.keys;
        int i3 = this.size;
        kArr[i3] = k2;
        this.values[i3] = v2;
        insertIntoTableKToV(i3, iD2);
        insertIntoTableVToK(this.size, iD);
        int i4 = i2 == -2 ? this.firstInInsertionOrder : this.nextInInsertionOrder[i2];
        setSucceeds(i2, this.size);
        setSucceeds(this.size, i4);
        this.size++;
        this.modCount++;
        return null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    @CanIgnoreReturnValue
    @CheckForNull
    public V remove(@CheckForNull Object obj) {
        int iD = Hashing.d(obj);
        int iFindEntryByKey = findEntryByKey(obj, iD);
        if (iFindEntryByKey == -1) {
            return null;
        }
        V v2 = this.values[iFindEntryByKey];
        removeEntryKeyHashKnown(iFindEntryByKey, iD);
        return v2;
    }

    void removeEntry(int i2) {
        removeEntryKeyHashKnown(i2, Hashing.d(this.keys[i2]));
    }

    void removeEntryKeyHashKnown(int i2, int i3) {
        removeEntry(i2, i3, Hashing.d(this.values[i2]));
    }

    void removeEntryValueHashKnown(int i2, int i3) {
        removeEntry(i2, Hashing.d(this.keys[i2]), i3);
    }

    @CheckForNull
    K removeInverse(@CheckForNull Object obj) {
        int iD = Hashing.d(obj);
        int iFindEntryByValue = findEntryByValue(obj, iD);
        if (iFindEntryByValue == -1) {
            return null;
        }
        K k2 = this.keys[iFindEntryByValue];
        removeEntryValueHashKnown(iFindEntryByValue, iD);
        return k2;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public int size() {
        return this.size;
    }

    public static <K, V> HashBiMap<K, V> create(int i2) {
        return new HashBiMap<>(i2);
    }

    private void removeEntry(int i2, int i3, int i4) {
        Preconditions.checkArgument(i2 != -1);
        deleteFromTableKToV(i2, i3);
        deleteFromTableVToK(i2, i4);
        setSucceeds(this.prevInInsertionOrder[i2], this.nextInInsertionOrder[i2]);
        moveEntryToIndex(this.size - 1, i2);
        K[] kArr = this.keys;
        int i5 = this.size;
        kArr[i5 - 1] = null;
        this.values[i5 - 1] = null;
        this.size = i5 - 1;
        this.modCount++;
    }

    int findEntryByKey(@CheckForNull Object obj, int i2) {
        return findEntry(obj, i2, this.hashTableKToV, this.nextInBucketKToV, this.keys);
    }

    int findEntryByValue(@CheckForNull Object obj, int i2) {
        return findEntry(obj, i2, this.hashTableVToK, this.nextInBucketVToK, this.values);
    }

    @CheckForNull
    V put(@ParametricNullness K k2, @ParametricNullness V v2, boolean z2) {
        int iD = Hashing.d(k2);
        int iFindEntryByKey = findEntryByKey(k2, iD);
        if (iFindEntryByKey != -1) {
            V v3 = this.values[iFindEntryByKey];
            if (Objects.equal(v3, v2)) {
                return v2;
            }
            replaceValueInEntry(iFindEntryByKey, v2, z2);
            return v3;
        }
        int iD2 = Hashing.d(v2);
        int iFindEntryByValue = findEntryByValue(v2, iD2);
        if (!z2) {
            Preconditions.checkArgument(iFindEntryByValue == -1, "Value already present: %s", v2);
        } else if (iFindEntryByValue != -1) {
            removeEntryValueHashKnown(iFindEntryByValue, iD2);
        }
        ensureCapacity(this.size + 1);
        K[] kArr = this.keys;
        int i2 = this.size;
        kArr[i2] = k2;
        this.values[i2] = v2;
        insertIntoTableKToV(i2, iD);
        insertIntoTableVToK(this.size, iD2);
        setSucceeds(this.lastInInsertionOrder, this.size);
        setSucceeds(this.size, -2);
        this.size++;
        this.modCount++;
        return null;
    }

    @Override // java.util.AbstractMap, java.util.Map, com.google.common.collect.BiMap
    public Set<V> values() {
        Set<V> set = this.valueSet;
        if (set != null) {
            return set;
        }
        ValueSet valueSet = new ValueSet();
        this.valueSet = valueSet;
        return valueSet;
    }

    public static <K, V> HashBiMap<K, V> create(Map<? extends K, ? extends V> map) {
        HashBiMap<K, V> hashBiMapCreate = create(map.size());
        hashBiMapCreate.putAll(map);
        return hashBiMapCreate;
    }
}
