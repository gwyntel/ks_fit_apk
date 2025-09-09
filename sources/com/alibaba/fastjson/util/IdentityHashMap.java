package com.alibaba.fastjson.util;

import java.util.Arrays;

/* loaded from: classes2.dex */
public class IdentityHashMap<K, V> {
    public static final int DEFAULT_SIZE = 8192;
    private final Entry<K, V>[] buckets;
    private final int indexMask;

    protected static final class Entry<K, V> {
        public final int hashCode;
        public final K key;
        public final Entry<K, V> next;
        public V value;

        public Entry(K k2, V v2, int i2, Entry<K, V> entry) {
            this.key = k2;
            this.value = v2;
            this.next = entry;
            this.hashCode = i2;
        }
    }

    public IdentityHashMap() {
        this(8192);
    }

    public void clear() {
        Arrays.fill(this.buckets, (Object) null);
    }

    public Class findClass(String str) {
        int i2 = 0;
        while (true) {
            Entry<K, V>[] entryArr = this.buckets;
            if (i2 >= entryArr.length) {
                return null;
            }
            Entry<K, V> entry = entryArr[i2];
            if (entry != null) {
                for (Entry<K, V> entry2 = entry; entry2 != null; entry2 = entry2.next) {
                    K k2 = entry.key;
                    if (k2 instanceof Class) {
                        Class cls = (Class) k2;
                        if (cls.getName().equals(str)) {
                            return cls;
                        }
                    }
                }
            }
            i2++;
        }
    }

    public final V get(K k2) {
        for (Entry<K, V> entry = this.buckets[System.identityHashCode(k2) & this.indexMask]; entry != null; entry = entry.next) {
            if (k2 == entry.key) {
                return entry.value;
            }
        }
        return null;
    }

    public boolean put(K k2, V v2) {
        int iIdentityHashCode = System.identityHashCode(k2);
        int i2 = this.indexMask & iIdentityHashCode;
        for (Entry<K, V> entry = this.buckets[i2]; entry != null; entry = entry.next) {
            if (k2 == entry.key) {
                entry.value = v2;
                return true;
            }
        }
        this.buckets[i2] = new Entry<>(k2, v2, iIdentityHashCode, this.buckets[i2]);
        return false;
    }

    public int size() {
        int i2 = 0;
        for (Entry<K, V> entry : this.buckets) {
            for (; entry != null; entry = entry.next) {
                i2++;
            }
        }
        return i2;
    }

    public IdentityHashMap(int i2) {
        this.indexMask = i2 - 1;
        this.buckets = new Entry[i2];
    }
}
