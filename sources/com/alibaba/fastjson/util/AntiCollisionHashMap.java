package com.alibaba.fastjson.util;

import com.huawei.hms.framework.common.ContainerUtils;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;

/* loaded from: classes2.dex */
public class AntiCollisionHashMap<K, V> extends AbstractMap<K, V> implements Map<K, V>, Cloneable, Serializable {
    static final int DEFAULT_INITIAL_CAPACITY = 16;
    static final float DEFAULT_LOAD_FACTOR = 0.75f;
    static final int KEY = 16777619;
    static final int MAXIMUM_CAPACITY = 1073741824;
    static final int M_MASK = -2023358765;
    static final int SEED = -2128831035;
    private static final long serialVersionUID = 362498820763181265L;
    private transient Set<Map.Entry<K, V>> entrySet;
    volatile transient Set<K> keySet;
    final float loadFactor;
    volatile transient int modCount;
    final int random;
    transient int size;
    transient Entry<K, V>[] table;
    int threshold;
    volatile transient Collection<V> values;

    static class Entry<K, V> implements Map.Entry<K, V> {
        final int hash;
        final K key;
        Entry<K, V> next;
        V value;

        Entry(int i2, K k2, V v2, Entry<K, V> entry) {
            this.value = v2;
            this.next = entry;
            this.key = k2;
            this.hash = i2;
        }

        @Override // java.util.Map.Entry
        public final boolean equals(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            K key = getKey();
            Object key2 = entry.getKey();
            if (key != key2 && (key == null || !key.equals(key2))) {
                return false;
            }
            V value = getValue();
            Object value2 = entry.getValue();
            return value == value2 || (value != null && value.equals(value2));
        }

        @Override // java.util.Map.Entry
        public final K getKey() {
            return this.key;
        }

        @Override // java.util.Map.Entry
        public final V getValue() {
            return this.value;
        }

        @Override // java.util.Map.Entry
        public final int hashCode() {
            K k2 = this.key;
            int iHashCode = k2 == null ? 0 : k2.hashCode();
            V v2 = this.value;
            return iHashCode ^ (v2 != null ? v2.hashCode() : 0);
        }

        @Override // java.util.Map.Entry
        public final V setValue(V v2) {
            V v3 = this.value;
            this.value = v2;
            return v3;
        }

        public final String toString() {
            return getKey() + ContainerUtils.KEY_VALUE_DELIMITER + getValue();
        }
    }

    private final class EntryIterator extends AntiCollisionHashMap<K, V>.HashIterator<Map.Entry<K, V>> {
        private EntryIterator() {
            super();
        }

        @Override // java.util.Iterator
        public Map.Entry<K, V> next() {
            return nextEntry();
        }
    }

    private final class EntrySet extends AbstractSet<Map.Entry<K, V>> {
        private EntrySet() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            AntiCollisionHashMap.this.clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            Entry<K, V> entry2 = AntiCollisionHashMap.this.getEntry(entry.getKey());
            return entry2 != null && entry2.equals(entry);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<Map.Entry<K, V>> iterator() {
            return AntiCollisionHashMap.this.newEntryIterator();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            return AntiCollisionHashMap.this.removeMapping(obj) != null;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return AntiCollisionHashMap.this.size;
        }
    }

    private abstract class HashIterator<E> implements Iterator<E> {
        Entry<K, V> current;
        int expectedModCount;
        int index;
        Entry<K, V> next;

        HashIterator() {
            Entry<K, V> entry;
            this.expectedModCount = AntiCollisionHashMap.this.modCount;
            if (AntiCollisionHashMap.this.size > 0) {
                Entry<K, V>[] entryArr = AntiCollisionHashMap.this.table;
                do {
                    int i2 = this.index;
                    if (i2 >= entryArr.length) {
                        return;
                    }
                    this.index = i2 + 1;
                    entry = entryArr[i2];
                    this.next = entry;
                } while (entry == null);
            }
        }

        @Override // java.util.Iterator
        public final boolean hasNext() {
            return this.next != null;
        }

        final Entry<K, V> nextEntry() {
            Entry<K, V> entry;
            if (AntiCollisionHashMap.this.modCount != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
            Entry<K, V> entry2 = this.next;
            if (entry2 == null) {
                throw new NoSuchElementException();
            }
            Entry<K, V> entry3 = entry2.next;
            this.next = entry3;
            if (entry3 == null) {
                Entry<K, V>[] entryArr = AntiCollisionHashMap.this.table;
                do {
                    int i2 = this.index;
                    if (i2 >= entryArr.length) {
                        break;
                    }
                    this.index = i2 + 1;
                    entry = entryArr[i2];
                    this.next = entry;
                } while (entry == null);
            }
            this.current = entry2;
            return entry2;
        }

        @Override // java.util.Iterator
        public void remove() {
            if (this.current == null) {
                throw new IllegalStateException();
            }
            if (AntiCollisionHashMap.this.modCount != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
            K k2 = this.current.key;
            this.current = null;
            AntiCollisionHashMap.this.removeEntryForKey(k2);
            this.expectedModCount = AntiCollisionHashMap.this.modCount;
        }
    }

    private final class KeyIterator extends AntiCollisionHashMap<K, V>.HashIterator<K> {
        private KeyIterator() {
            super();
        }

        @Override // java.util.Iterator
        public K next() {
            return nextEntry().getKey();
        }
    }

    private final class KeySet extends AbstractSet<K> {
        private KeySet() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            AntiCollisionHashMap.this.clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            return AntiCollisionHashMap.this.containsKey(obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<K> iterator() {
            return AntiCollisionHashMap.this.newKeyIterator();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            return AntiCollisionHashMap.this.removeEntryForKey(obj) != null;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return AntiCollisionHashMap.this.size;
        }
    }

    private final class ValueIterator extends AntiCollisionHashMap<K, V>.HashIterator<V> {
        private ValueIterator() {
            super();
        }

        @Override // java.util.Iterator
        public V next() {
            return nextEntry().value;
        }
    }

    private final class Values extends AbstractCollection<V> {
        private Values() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public void clear() {
            AntiCollisionHashMap.this.clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean contains(Object obj) {
            return AntiCollisionHashMap.this.containsValue(obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public Iterator<V> iterator() {
            return AntiCollisionHashMap.this.newValueIterator();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            return AntiCollisionHashMap.this.size;
        }
    }

    public AntiCollisionHashMap(int i2, float f2) {
        this.keySet = null;
        this.values = null;
        this.random = new Random().nextInt(99999);
        this.entrySet = null;
        if (i2 < 0) {
            throw new IllegalArgumentException("Illegal initial capacity: " + i2);
        }
        i2 = i2 > 1073741824 ? 1073741824 : i2;
        if (f2 <= 0.0f || Float.isNaN(f2)) {
            throw new IllegalArgumentException("Illegal load factor: " + f2);
        }
        int i3 = 1;
        while (i3 < i2) {
            i3 <<= 1;
        }
        this.loadFactor = f2;
        this.threshold = (int) (i3 * f2);
        this.table = new Entry[i3];
        init();
    }

    private boolean containsNullValue() {
        for (Entry<K, V> entry : this.table) {
            for (; entry != null; entry = entry.next) {
                if (entry.value == null) {
                    return true;
                }
            }
        }
        return false;
    }

    private Set<Map.Entry<K, V>> entrySet0() {
        Set<Map.Entry<K, V>> set = this.entrySet;
        if (set != null) {
            return set;
        }
        EntrySet entrySet = new EntrySet();
        this.entrySet = entrySet;
        return entrySet;
    }

    private V getForNullKey() {
        for (Entry<K, V> entry = this.table[0]; entry != null; entry = entry.next) {
            if (entry.key == null) {
                return entry.value;
            }
        }
        return null;
    }

    static int hash(int i2) {
        int i3 = i2 * i2;
        int i4 = i3 ^ ((i3 >>> 20) ^ (i3 >>> 12));
        return (i4 >>> 4) ^ ((i4 >>> 7) ^ i4);
    }

    private int hashString(String str) {
        int iCharAt = this.random * SEED;
        for (int i2 = 0; i2 < str.length(); i2++) {
            iCharAt = (iCharAt * KEY) ^ str.charAt(i2);
        }
        return ((iCharAt >> 1) ^ iCharAt) & M_MASK;
    }

    static int indexFor(int i2, int i3) {
        return i2 & (i3 - 1);
    }

    private void putAllForCreate(Map<? extends K, ? extends V> map) {
        for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
            putForCreate(entry.getKey(), entry.getValue());
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void putForCreate(K k2, V v2) {
        K k3;
        int iHash = k2 == 0 ? 0 : k2 instanceof String ? hash(hashString((String) k2)) : hash(k2.hashCode());
        int iIndexFor = indexFor(iHash, this.table.length);
        for (Entry<K, V> entry = this.table[iIndexFor]; entry != null; entry = entry.next) {
            if (entry.hash == iHash && ((k3 = entry.key) == k2 || (k2 != 0 && k2.equals(k3)))) {
                entry.value = v2;
                return;
            }
        }
        createEntry(iHash, k2, v2, iIndexFor);
    }

    private V putForNullKey(V v2) {
        for (Entry<K, V> entry = this.table[0]; entry != null; entry = entry.next) {
            if (entry.key == null) {
                V v3 = entry.value;
                entry.value = v2;
                return v3;
            }
        }
        this.modCount++;
        addEntry(0, null, v2, 0);
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        this.table = new Entry[objectInputStream.readInt()];
        init();
        int i2 = objectInputStream.readInt();
        for (int i3 = 0; i3 < i2; i3++) {
            putForCreate(objectInputStream.readObject(), objectInputStream.readObject());
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        Iterator<Map.Entry<K, V>> it = this.size > 0 ? entrySet0().iterator() : null;
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(this.table.length);
        objectOutputStream.writeInt(this.size);
        if (it != null) {
            while (it.hasNext()) {
                Map.Entry<K, V> next = it.next();
                objectOutputStream.writeObject(next.getKey());
                objectOutputStream.writeObject(next.getValue());
            }
        }
    }

    void addEntry(int i2, K k2, V v2, int i3) {
        Entry<K, V>[] entryArr = this.table;
        entryArr[i3] = new Entry<>(i2, k2, v2, entryArr[i3]);
        int i4 = this.size;
        this.size = i4 + 1;
        if (i4 >= this.threshold) {
            resize(this.table.length * 2);
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void clear() {
        this.modCount++;
        Entry<K, V>[] entryArr = this.table;
        for (int i2 = 0; i2 < entryArr.length; i2++) {
            entryArr[i2] = null;
        }
        this.size = 0;
    }

    @Override // java.util.AbstractMap
    public Object clone() {
        AntiCollisionHashMap antiCollisionHashMap;
        try {
            antiCollisionHashMap = (AntiCollisionHashMap) super.clone();
        } catch (CloneNotSupportedException unused) {
            antiCollisionHashMap = null;
        }
        antiCollisionHashMap.table = new Entry[this.table.length];
        antiCollisionHashMap.entrySet = null;
        antiCollisionHashMap.modCount = 0;
        antiCollisionHashMap.size = 0;
        antiCollisionHashMap.init();
        antiCollisionHashMap.putAllForCreate(this);
        return antiCollisionHashMap;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsKey(Object obj) {
        return getEntry(obj) != null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsValue(Object obj) {
        if (obj == null) {
            return containsNullValue();
        }
        for (Entry<K, V> entry : this.table) {
            for (; entry != null; entry = entry.next) {
                if (obj.equals(entry.value)) {
                    return true;
                }
            }
        }
        return false;
    }

    void createEntry(int i2, K k2, V v2, int i3) {
        Entry<K, V>[] entryArr = this.table;
        entryArr[i3] = new Entry<>(i2, k2, v2, entryArr[i3]);
        this.size++;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<Map.Entry<K, V>> entrySet() {
        return entrySet0();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public V get(Object obj) {
        K k2;
        if (obj == null) {
            return getForNullKey();
        }
        int iHash = obj instanceof String ? hash(hashString((String) obj)) : hash(obj.hashCode());
        Entry<K, V>[] entryArr = this.table;
        for (Entry<K, V> entry = entryArr[indexFor(iHash, entryArr.length)]; entry != null; entry = entry.next) {
            if (entry.hash == iHash && ((k2 = entry.key) == obj || obj.equals(k2))) {
                return entry.value;
            }
        }
        return null;
    }

    final Entry<K, V> getEntry(Object obj) {
        K k2;
        int iHash = obj == null ? 0 : obj instanceof String ? hash(hashString((String) obj)) : hash(obj.hashCode());
        Entry<K, V>[] entryArr = this.table;
        for (Entry<K, V> entry = entryArr[indexFor(iHash, entryArr.length)]; entry != null; entry = entry.next) {
            if (entry.hash == iHash && ((k2 = entry.key) == obj || (obj != null && obj.equals(k2)))) {
                return entry;
            }
        }
        return null;
    }

    void init() {
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean isEmpty() {
        return this.size == 0;
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

    Iterator<Map.Entry<K, V>> newEntryIterator() {
        return new EntryIterator();
    }

    Iterator<K> newKeyIterator() {
        return new KeyIterator();
    }

    Iterator<V> newValueIterator() {
        return new ValueIterator();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.AbstractMap, java.util.Map
    public V put(K k2, V v2) {
        K k3;
        if (k2 == 0) {
            return putForNullKey(v2);
        }
        int iHash = k2 instanceof String ? hash(hashString((String) k2)) : hash(k2.hashCode());
        int iIndexFor = indexFor(iHash, this.table.length);
        for (Entry<K, V> entry = this.table[iIndexFor]; entry != null; entry = entry.next) {
            if (entry.hash == iHash && ((k3 = entry.key) == k2 || k2.equals(k3))) {
                V v3 = entry.value;
                entry.value = v2;
                return v3;
            }
        }
        this.modCount++;
        addEntry(iHash, k2, v2, iIndexFor);
        return null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void putAll(Map<? extends K, ? extends V> map) {
        int size = map.size();
        if (size == 0) {
            return;
        }
        if (size > this.threshold) {
            int i2 = (int) ((size / this.loadFactor) + 1.0f);
            if (i2 > 1073741824) {
                i2 = 1073741824;
            }
            int length = this.table.length;
            while (length < i2) {
                length <<= 1;
            }
            if (length > this.table.length) {
                resize(length);
            }
        }
        for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public V remove(Object obj) {
        Entry<K, V> entryRemoveEntryForKey = removeEntryForKey(obj);
        if (entryRemoveEntryForKey == null) {
            return null;
        }
        return entryRemoveEntryForKey.value;
    }

    final Entry<K, V> removeEntryForKey(Object obj) {
        K k2;
        int iHash = obj == null ? 0 : obj instanceof String ? hash(hashString((String) obj)) : hash(obj.hashCode());
        int iIndexFor = indexFor(iHash, this.table.length);
        Entry<K, V> entry = this.table[iIndexFor];
        Entry<K, V> entry2 = entry;
        while (entry != null) {
            Entry<K, V> entry3 = entry.next;
            if (entry.hash == iHash && ((k2 = entry.key) == obj || (obj != null && obj.equals(k2)))) {
                this.modCount++;
                this.size--;
                if (entry2 == entry) {
                    this.table[iIndexFor] = entry3;
                } else {
                    entry2.next = entry3;
                }
                return entry;
            }
            entry2 = entry;
            entry = entry3;
        }
        return entry;
    }

    final Entry<K, V> removeMapping(Object obj) {
        if (!(obj instanceof Map.Entry)) {
            return null;
        }
        Map.Entry entry = (Map.Entry) obj;
        Object key = entry.getKey();
        int iHash = key == null ? 0 : key instanceof String ? hash(hashString((String) key)) : hash(key.hashCode());
        int iIndexFor = indexFor(iHash, this.table.length);
        Entry<K, V> entry2 = this.table[iIndexFor];
        Entry<K, V> entry3 = entry2;
        while (entry2 != null) {
            Entry<K, V> entry4 = entry2.next;
            if (entry2.hash == iHash && entry2.equals(entry)) {
                this.modCount++;
                this.size--;
                if (entry3 == entry2) {
                    this.table[iIndexFor] = entry4;
                } else {
                    entry3.next = entry4;
                }
                return entry2;
            }
            entry3 = entry2;
            entry2 = entry4;
        }
        return entry2;
    }

    void resize(int i2) {
        if (this.table.length == 1073741824) {
            this.threshold = Integer.MAX_VALUE;
            return;
        }
        Entry<K, V>[] entryArr = new Entry[i2];
        transfer(entryArr);
        this.table = entryArr;
        this.threshold = (int) (i2 * this.loadFactor);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public int size() {
        return this.size;
    }

    void transfer(Entry[] entryArr) {
        Entry<K, V>[] entryArr2 = this.table;
        int length = entryArr.length;
        for (int i2 = 0; i2 < entryArr2.length; i2++) {
            Entry<K, V> entry = entryArr2[i2];
            if (entry != null) {
                entryArr2[i2] = null;
                while (true) {
                    Entry<K, V> entry2 = entry.next;
                    int iIndexFor = indexFor(entry.hash, length);
                    entry.next = entryArr[iIndexFor];
                    entryArr[iIndexFor] = entry;
                    if (entry2 == null) {
                        break;
                    } else {
                        entry = entry2;
                    }
                }
            }
        }
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

    public AntiCollisionHashMap(int i2) {
        this(i2, 0.75f);
    }

    public AntiCollisionHashMap() {
        this.keySet = null;
        this.values = null;
        this.random = new Random().nextInt(99999);
        this.entrySet = null;
        this.loadFactor = 0.75f;
        this.threshold = 12;
        this.table = new Entry[16];
        init();
    }

    public AntiCollisionHashMap(Map<? extends K, ? extends V> map) {
        this(Math.max(((int) (map.size() / 0.75f)) + 1, 16), 0.75f);
        putAllForCreate(map);
    }
}
