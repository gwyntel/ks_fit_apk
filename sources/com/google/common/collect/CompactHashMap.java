package com.google.common.collect;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.J2ktIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Ints;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.annotation.CheckForNull;
import kotlinx.coroutines.internal.LockFreeTaskQueueCore;

@GwtIncompatible
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
class CompactHashMap<K, V> extends AbstractMap<K, V> implements Serializable {

    @VisibleForTesting
    static final double HASH_FLOODING_FPP = 0.001d;
    private static final int MAX_HASH_BUCKET_LENGTH = 9;
    private static final Object NOT_FOUND = new Object();

    @VisibleForTesting
    @CheckForNull
    transient int[] entries;

    @CheckForNull
    @LazyInit
    private transient Set<Map.Entry<K, V>> entrySetView;

    @CheckForNull
    @LazyInit
    private transient Set<K> keySetView;

    @VisibleForTesting
    @CheckForNull
    transient Object[] keys;
    private transient int metadata;
    private transient int size;

    @CheckForNull
    private transient Object table;

    @VisibleForTesting
    @CheckForNull
    transient Object[] values;

    @CheckForNull
    @LazyInit
    private transient Collection<V> valuesView;

    class EntrySetView extends AbstractSet<Map.Entry<K, V>> {
        EntrySetView() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            CompactHashMap.this.clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(@CheckForNull Object obj) {
            Map<K, V> mapDelegateOrNull = CompactHashMap.this.delegateOrNull();
            if (mapDelegateOrNull != null) {
                return mapDelegateOrNull.entrySet().contains(obj);
            }
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            int iIndexOf = CompactHashMap.this.indexOf(entry.getKey());
            return iIndexOf != -1 && Objects.equal(CompactHashMap.this.value(iIndexOf), entry.getValue());
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<Map.Entry<K, V>> iterator() {
            return CompactHashMap.this.entrySetIterator();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(@CheckForNull Object obj) {
            Map<K, V> mapDelegateOrNull = CompactHashMap.this.delegateOrNull();
            if (mapDelegateOrNull != null) {
                return mapDelegateOrNull.entrySet().remove(obj);
            }
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            if (CompactHashMap.this.needsAllocArrays()) {
                return false;
            }
            int iHashTableMask = CompactHashMap.this.hashTableMask();
            int iF = CompactHashing.f(entry.getKey(), entry.getValue(), iHashTableMask, CompactHashMap.this.requireTable(), CompactHashMap.this.requireEntries(), CompactHashMap.this.requireKeys(), CompactHashMap.this.requireValues());
            if (iF == -1) {
                return false;
            }
            CompactHashMap.this.moveLastEntry(iF, iHashTableMask);
            CompactHashMap.access$1210(CompactHashMap.this);
            CompactHashMap.this.incrementModCount();
            return true;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return CompactHashMap.this.size();
        }
    }

    private abstract class Itr<T> implements Iterator<T> {

        /* renamed from: a, reason: collision with root package name */
        int f13929a;

        /* renamed from: b, reason: collision with root package name */
        int f13930b;

        /* renamed from: c, reason: collision with root package name */
        int f13931c;

        private Itr() {
            this.f13929a = CompactHashMap.this.metadata;
            this.f13930b = CompactHashMap.this.firstEntryIndex();
            this.f13931c = -1;
        }

        private void checkForConcurrentModification() {
            if (CompactHashMap.this.metadata != this.f13929a) {
                throw new ConcurrentModificationException();
            }
        }

        abstract Object a(int i2);

        void b() {
            this.f13929a += 32;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.f13930b >= 0;
        }

        @Override // java.util.Iterator
        @ParametricNullness
        public T next() {
            checkForConcurrentModification();
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int i2 = this.f13930b;
            this.f13931c = i2;
            T t2 = (T) a(i2);
            this.f13930b = CompactHashMap.this.getSuccessor(this.f13930b);
            return t2;
        }

        @Override // java.util.Iterator
        public void remove() {
            checkForConcurrentModification();
            CollectPreconditions.e(this.f13931c >= 0);
            b();
            CompactHashMap compactHashMap = CompactHashMap.this;
            compactHashMap.remove(compactHashMap.key(this.f13931c));
            this.f13930b = CompactHashMap.this.adjustAfterRemove(this.f13930b, this.f13931c);
            this.f13931c = -1;
        }
    }

    class KeySetView extends AbstractSet<K> {
        KeySetView() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            CompactHashMap.this.clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(@CheckForNull Object obj) {
            return CompactHashMap.this.containsKey(obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<K> iterator() {
            return CompactHashMap.this.keySetIterator();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(@CheckForNull Object obj) {
            Map<K, V> mapDelegateOrNull = CompactHashMap.this.delegateOrNull();
            return mapDelegateOrNull != null ? mapDelegateOrNull.keySet().remove(obj) : CompactHashMap.this.removeHelper(obj) != CompactHashMap.NOT_FOUND;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return CompactHashMap.this.size();
        }
    }

    final class MapEntry extends AbstractMapEntry<K, V> {

        @ParametricNullness
        private final K key;
        private int lastKnownIndex;

        MapEntry(int i2) {
            this.key = (K) CompactHashMap.this.key(i2);
            this.lastKnownIndex = i2;
        }

        private void updateLastKnownIndex() {
            int i2 = this.lastKnownIndex;
            if (i2 == -1 || i2 >= CompactHashMap.this.size() || !Objects.equal(this.key, CompactHashMap.this.key(this.lastKnownIndex))) {
                this.lastKnownIndex = CompactHashMap.this.indexOf(this.key);
            }
        }

        @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
        @ParametricNullness
        public K getKey() {
            return this.key;
        }

        @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
        @ParametricNullness
        public V getValue() {
            Map<K, V> mapDelegateOrNull = CompactHashMap.this.delegateOrNull();
            if (mapDelegateOrNull != null) {
                return (V) NullnessCasts.a(mapDelegateOrNull.get(this.key));
            }
            updateLastKnownIndex();
            int i2 = this.lastKnownIndex;
            return i2 == -1 ? (V) NullnessCasts.b() : (V) CompactHashMap.this.value(i2);
        }

        @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
        @ParametricNullness
        public V setValue(@ParametricNullness V v2) {
            Map<K, V> mapDelegateOrNull = CompactHashMap.this.delegateOrNull();
            if (mapDelegateOrNull != null) {
                return (V) NullnessCasts.a(mapDelegateOrNull.put(this.key, v2));
            }
            updateLastKnownIndex();
            int i2 = this.lastKnownIndex;
            if (i2 == -1) {
                CompactHashMap.this.put(this.key, v2);
                return (V) NullnessCasts.b();
            }
            V v3 = (V) CompactHashMap.this.value(i2);
            CompactHashMap.this.setValue(this.lastKnownIndex, v2);
            return v3;
        }
    }

    class ValuesView extends AbstractCollection<V> {
        ValuesView() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public void clear() {
            CompactHashMap.this.clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public Iterator<V> iterator() {
            return CompactHashMap.this.valuesIterator();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            return CompactHashMap.this.size();
        }
    }

    CompactHashMap() {
        init(3);
    }

    static /* synthetic */ int access$1210(CompactHashMap compactHashMap) {
        int i2 = compactHashMap.size;
        compactHashMap.size = i2 - 1;
        return i2;
    }

    public static <K, V> CompactHashMap<K, V> create() {
        return new CompactHashMap<>();
    }

    public static <K, V> CompactHashMap<K, V> createWithExpectedSize(int i2) {
        return new CompactHashMap<>(i2);
    }

    private int entry(int i2) {
        return requireEntries()[i2];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int hashTableMask() {
        return (1 << (this.metadata & 31)) - 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int indexOf(@CheckForNull Object obj) {
        if (needsAllocArrays()) {
            return -1;
        }
        int iD = Hashing.d(obj);
        int iHashTableMask = hashTableMask();
        int iH = CompactHashing.h(requireTable(), iD & iHashTableMask);
        if (iH == 0) {
            return -1;
        }
        int iB = CompactHashing.b(iD, iHashTableMask);
        do {
            int i2 = iH - 1;
            int iEntry = entry(i2);
            if (CompactHashing.b(iEntry, iHashTableMask) == iB && Objects.equal(obj, key(i2))) {
                return i2;
            }
            iH = CompactHashing.c(iEntry, iHashTableMask);
        } while (iH != 0);
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public K key(int i2) {
        return (K) requireKeys()[i2];
    }

    /* JADX WARN: Multi-variable type inference failed */
    @J2ktIncompatible
    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        int i2 = objectInputStream.readInt();
        if (i2 < 0) {
            throw new InvalidObjectException("Invalid size: " + i2);
        }
        init(i2);
        for (int i3 = 0; i3 < i2; i3++) {
            put(objectInputStream.readObject(), objectInputStream.readObject());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Object removeHelper(@CheckForNull Object obj) {
        if (needsAllocArrays()) {
            return NOT_FOUND;
        }
        int iHashTableMask = hashTableMask();
        int iF = CompactHashing.f(obj, null, iHashTableMask, requireTable(), requireEntries(), requireKeys(), null);
        if (iF == -1) {
            return NOT_FOUND;
        }
        V vValue = value(iF);
        moveLastEntry(iF, iHashTableMask);
        this.size--;
        incrementModCount();
        return vValue;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int[] requireEntries() {
        int[] iArr = this.entries;
        java.util.Objects.requireNonNull(iArr);
        return iArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Object[] requireKeys() {
        Object[] objArr = this.keys;
        java.util.Objects.requireNonNull(objArr);
        return objArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Object requireTable() {
        Object obj = this.table;
        java.util.Objects.requireNonNull(obj);
        return obj;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Object[] requireValues() {
        Object[] objArr = this.values;
        java.util.Objects.requireNonNull(objArr);
        return objArr;
    }

    private void resizeMeMaybe(int i2) {
        int iMin;
        int length = requireEntries().length;
        if (i2 <= length || (iMin = Math.min(LockFreeTaskQueueCore.MAX_CAPACITY_MASK, (Math.max(1, length >>> 1) + length) | 1)) == length) {
            return;
        }
        resizeEntries(iMin);
    }

    @CanIgnoreReturnValue
    private int resizeTable(int i2, int i3, int i4, int i5) {
        Object objA = CompactHashing.a(i3);
        int i6 = i3 - 1;
        if (i5 != 0) {
            CompactHashing.i(objA, i4 & i6, i5 + 1);
        }
        Object objRequireTable = requireTable();
        int[] iArrRequireEntries = requireEntries();
        for (int i7 = 0; i7 <= i2; i7++) {
            int iH = CompactHashing.h(objRequireTable, i7);
            while (iH != 0) {
                int i8 = iH - 1;
                int i9 = iArrRequireEntries[i8];
                int iB = CompactHashing.b(i9, i2) | i7;
                int i10 = iB & i6;
                int iH2 = CompactHashing.h(objA, i10);
                CompactHashing.i(objA, i10, iH);
                iArrRequireEntries[i8] = CompactHashing.d(iB, iH2, i6);
                iH = CompactHashing.c(i9, i2);
            }
        }
        this.table = objA;
        setHashTableMask(i6);
        return i6;
    }

    private void setEntry(int i2, int i3) {
        requireEntries()[i2] = i3;
    }

    private void setHashTableMask(int i2) {
        this.metadata = CompactHashing.d(this.metadata, 32 - Integer.numberOfLeadingZeros(i2), 31);
    }

    private void setKey(int i2, K k2) {
        requireKeys()[i2] = k2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setValue(int i2, V v2) {
        requireValues()[i2] = v2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public V value(int i2) {
        return (V) requireValues()[i2];
    }

    @J2ktIncompatible
    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(size());
        Iterator<Map.Entry<K, V>> itEntrySetIterator = entrySetIterator();
        while (itEntrySetIterator.hasNext()) {
            Map.Entry<K, V> next = itEntrySetIterator.next();
            objectOutputStream.writeObject(next.getKey());
            objectOutputStream.writeObject(next.getValue());
        }
    }

    void accessEntry(int i2) {
    }

    int adjustAfterRemove(int i2, int i3) {
        return i2 - 1;
    }

    @CanIgnoreReturnValue
    int allocArrays() {
        Preconditions.checkState(needsAllocArrays(), "Arrays already allocated");
        int i2 = this.metadata;
        int iJ = CompactHashing.j(i2);
        this.table = CompactHashing.a(iJ);
        setHashTableMask(iJ - 1);
        this.entries = new int[i2];
        this.keys = new Object[i2];
        this.values = new Object[i2];
        return i2;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void clear() {
        if (needsAllocArrays()) {
            return;
        }
        incrementModCount();
        Map<K, V> mapDelegateOrNull = delegateOrNull();
        if (mapDelegateOrNull != null) {
            this.metadata = Ints.constrainToRange(size(), 3, LockFreeTaskQueueCore.MAX_CAPACITY_MASK);
            mapDelegateOrNull.clear();
            this.table = null;
            this.size = 0;
            return;
        }
        Arrays.fill(requireKeys(), 0, this.size, (Object) null);
        Arrays.fill(requireValues(), 0, this.size, (Object) null);
        CompactHashing.g(requireTable());
        Arrays.fill(requireEntries(), 0, this.size, 0);
        this.size = 0;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsKey(@CheckForNull Object obj) {
        Map<K, V> mapDelegateOrNull = delegateOrNull();
        return mapDelegateOrNull != null ? mapDelegateOrNull.containsKey(obj) : indexOf(obj) != -1;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsValue(@CheckForNull Object obj) {
        Map<K, V> mapDelegateOrNull = delegateOrNull();
        if (mapDelegateOrNull != null) {
            return mapDelegateOrNull.containsValue(obj);
        }
        for (int i2 = 0; i2 < this.size; i2++) {
            if (Objects.equal(obj, value(i2))) {
                return true;
            }
        }
        return false;
    }

    @CanIgnoreReturnValue
    Map<K, V> convertToHashFloodingResistantImplementation() {
        Map<K, V> mapCreateHashFloodingResistantDelegate = createHashFloodingResistantDelegate(hashTableMask() + 1);
        int iFirstEntryIndex = firstEntryIndex();
        while (iFirstEntryIndex >= 0) {
            mapCreateHashFloodingResistantDelegate.put(key(iFirstEntryIndex), value(iFirstEntryIndex));
            iFirstEntryIndex = getSuccessor(iFirstEntryIndex);
        }
        this.table = mapCreateHashFloodingResistantDelegate;
        this.entries = null;
        this.keys = null;
        this.values = null;
        incrementModCount();
        return mapCreateHashFloodingResistantDelegate;
    }

    Set<Map.Entry<K, V>> createEntrySet() {
        return new EntrySetView();
    }

    Map<K, V> createHashFloodingResistantDelegate(int i2) {
        return new LinkedHashMap(i2, 1.0f);
    }

    Set<K> createKeySet() {
        return new KeySetView();
    }

    Collection<V> createValues() {
        return new ValuesView();
    }

    @VisibleForTesting
    @CheckForNull
    Map<K, V> delegateOrNull() {
        Object obj = this.table;
        if (obj instanceof Map) {
            return (Map) obj;
        }
        return null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> set = this.entrySetView;
        if (set != null) {
            return set;
        }
        Set<Map.Entry<K, V>> setCreateEntrySet = createEntrySet();
        this.entrySetView = setCreateEntrySet;
        return setCreateEntrySet;
    }

    Iterator<Map.Entry<K, V>> entrySetIterator() {
        Map<K, V> mapDelegateOrNull = delegateOrNull();
        return mapDelegateOrNull != null ? mapDelegateOrNull.entrySet().iterator() : new CompactHashMap<K, V>.Itr<Map.Entry<K, V>>() { // from class: com.google.common.collect.CompactHashMap.2
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // com.google.common.collect.CompactHashMap.Itr
            /* renamed from: c, reason: merged with bridge method [inline-methods] */
            public Map.Entry a(int i2) {
                return new MapEntry(i2);
            }
        };
    }

    int firstEntryIndex() {
        return isEmpty() ? -1 : 0;
    }

    @Override // java.util.AbstractMap, java.util.Map
    @CheckForNull
    public V get(@CheckForNull Object obj) {
        Map<K, V> mapDelegateOrNull = delegateOrNull();
        if (mapDelegateOrNull != null) {
            return mapDelegateOrNull.get(obj);
        }
        int iIndexOf = indexOf(obj);
        if (iIndexOf == -1) {
            return null;
        }
        accessEntry(iIndexOf);
        return value(iIndexOf);
    }

    int getSuccessor(int i2) {
        int i3 = i2 + 1;
        if (i3 < this.size) {
            return i3;
        }
        return -1;
    }

    void incrementModCount() {
        this.metadata += 32;
    }

    void init(int i2) {
        Preconditions.checkArgument(i2 >= 0, "Expected size must be >= 0");
        this.metadata = Ints.constrainToRange(i2, 1, LockFreeTaskQueueCore.MAX_CAPACITY_MASK);
    }

    void insertEntry(int i2, @ParametricNullness K k2, @ParametricNullness V v2, int i3, int i4) {
        setEntry(i2, CompactHashing.d(i3, 0, i4));
        setKey(i2, k2);
        setValue(i2, v2);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<K> keySet() {
        Set<K> set = this.keySetView;
        if (set != null) {
            return set;
        }
        Set<K> setCreateKeySet = createKeySet();
        this.keySetView = setCreateKeySet;
        return setCreateKeySet;
    }

    Iterator<K> keySetIterator() {
        Map<K, V> mapDelegateOrNull = delegateOrNull();
        return mapDelegateOrNull != null ? mapDelegateOrNull.keySet().iterator() : new CompactHashMap<K, V>.Itr<K>() { // from class: com.google.common.collect.CompactHashMap.1
            @Override // com.google.common.collect.CompactHashMap.Itr
            Object a(int i2) {
                return CompactHashMap.this.key(i2);
            }
        };
    }

    void moveLastEntry(int i2, int i3) {
        Object objRequireTable = requireTable();
        int[] iArrRequireEntries = requireEntries();
        Object[] objArrRequireKeys = requireKeys();
        Object[] objArrRequireValues = requireValues();
        int size = size();
        int i4 = size - 1;
        if (i2 >= i4) {
            objArrRequireKeys[i2] = null;
            objArrRequireValues[i2] = null;
            iArrRequireEntries[i2] = 0;
            return;
        }
        Object obj = objArrRequireKeys[i4];
        objArrRequireKeys[i2] = obj;
        objArrRequireValues[i2] = objArrRequireValues[i4];
        objArrRequireKeys[i4] = null;
        objArrRequireValues[i4] = null;
        iArrRequireEntries[i2] = iArrRequireEntries[i4];
        iArrRequireEntries[i4] = 0;
        int iD = Hashing.d(obj) & i3;
        int iH = CompactHashing.h(objRequireTable, iD);
        if (iH == size) {
            CompactHashing.i(objRequireTable, iD, i2 + 1);
            return;
        }
        while (true) {
            int i5 = iH - 1;
            int i6 = iArrRequireEntries[i5];
            int iC = CompactHashing.c(i6, i3);
            if (iC == size) {
                iArrRequireEntries[i5] = CompactHashing.d(i6, i2 + 1, i3);
                return;
            }
            iH = iC;
        }
    }

    boolean needsAllocArrays() {
        return this.table == null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    @CanIgnoreReturnValue
    @CheckForNull
    public V put(@ParametricNullness K k2, @ParametricNullness V v2) {
        int iResizeTable;
        int i2;
        if (needsAllocArrays()) {
            allocArrays();
        }
        Map<K, V> mapDelegateOrNull = delegateOrNull();
        if (mapDelegateOrNull != null) {
            return mapDelegateOrNull.put(k2, v2);
        }
        int[] iArrRequireEntries = requireEntries();
        Object[] objArrRequireKeys = requireKeys();
        Object[] objArrRequireValues = requireValues();
        int i3 = this.size;
        int i4 = i3 + 1;
        int iD = Hashing.d(k2);
        int iHashTableMask = hashTableMask();
        int i5 = iD & iHashTableMask;
        int iH = CompactHashing.h(requireTable(), i5);
        if (iH != 0) {
            int iB = CompactHashing.b(iD, iHashTableMask);
            int i6 = 0;
            while (true) {
                int i7 = iH - 1;
                int i8 = iArrRequireEntries[i7];
                if (CompactHashing.b(i8, iHashTableMask) == iB && Objects.equal(k2, objArrRequireKeys[i7])) {
                    V v3 = (V) objArrRequireValues[i7];
                    objArrRequireValues[i7] = v2;
                    accessEntry(i7);
                    return v3;
                }
                int iC = CompactHashing.c(i8, iHashTableMask);
                i6++;
                if (iC != 0) {
                    iH = iC;
                } else {
                    if (i6 >= 9) {
                        return convertToHashFloodingResistantImplementation().put(k2, v2);
                    }
                    if (i4 > iHashTableMask) {
                        iResizeTable = resizeTable(iHashTableMask, CompactHashing.e(iHashTableMask), iD, i3);
                    } else {
                        iArrRequireEntries[i7] = CompactHashing.d(i8, i4, iHashTableMask);
                    }
                }
            }
            i2 = iHashTableMask;
        } else if (i4 > iHashTableMask) {
            iResizeTable = resizeTable(iHashTableMask, CompactHashing.e(iHashTableMask), iD, i3);
            i2 = iResizeTable;
        } else {
            CompactHashing.i(requireTable(), i5, i4);
            i2 = iHashTableMask;
        }
        resizeMeMaybe(i4);
        insertEntry(i3, k2, v2, iD, i2);
        this.size = i4;
        incrementModCount();
        return null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    @CanIgnoreReturnValue
    @CheckForNull
    public V remove(@CheckForNull Object obj) {
        Map<K, V> mapDelegateOrNull = delegateOrNull();
        if (mapDelegateOrNull != null) {
            return mapDelegateOrNull.remove(obj);
        }
        V v2 = (V) removeHelper(obj);
        if (v2 == NOT_FOUND) {
            return null;
        }
        return v2;
    }

    void resizeEntries(int i2) {
        this.entries = Arrays.copyOf(requireEntries(), i2);
        this.keys = Arrays.copyOf(requireKeys(), i2);
        this.values = Arrays.copyOf(requireValues(), i2);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public int size() {
        Map<K, V> mapDelegateOrNull = delegateOrNull();
        return mapDelegateOrNull != null ? mapDelegateOrNull.size() : this.size;
    }

    public void trimToSize() {
        if (needsAllocArrays()) {
            return;
        }
        Map<K, V> mapDelegateOrNull = delegateOrNull();
        if (mapDelegateOrNull != null) {
            Map<K, V> mapCreateHashFloodingResistantDelegate = createHashFloodingResistantDelegate(size());
            mapCreateHashFloodingResistantDelegate.putAll(mapDelegateOrNull);
            this.table = mapCreateHashFloodingResistantDelegate;
            return;
        }
        int i2 = this.size;
        if (i2 < requireEntries().length) {
            resizeEntries(i2);
        }
        int iJ = CompactHashing.j(i2);
        int iHashTableMask = hashTableMask();
        if (iJ < iHashTableMask) {
            resizeTable(iHashTableMask, iJ, 0, 0);
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Collection<V> values() {
        Collection<V> collection = this.valuesView;
        if (collection != null) {
            return collection;
        }
        Collection<V> collectionCreateValues = createValues();
        this.valuesView = collectionCreateValues;
        return collectionCreateValues;
    }

    Iterator<V> valuesIterator() {
        Map<K, V> mapDelegateOrNull = delegateOrNull();
        return mapDelegateOrNull != null ? mapDelegateOrNull.values().iterator() : new CompactHashMap<K, V>.Itr<V>() { // from class: com.google.common.collect.CompactHashMap.3
            @Override // com.google.common.collect.CompactHashMap.Itr
            Object a(int i2) {
                return CompactHashMap.this.value(i2);
            }
        };
    }

    CompactHashMap(int i2) {
        init(i2);
    }
}
