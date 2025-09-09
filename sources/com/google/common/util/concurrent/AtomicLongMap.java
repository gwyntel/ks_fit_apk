package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.J2ktIncompatible;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import javax.annotation.CheckForNull;

@J2ktIncompatible
@ElementTypesAreNonnullByDefault
@GwtCompatible
/* loaded from: classes3.dex */
public final class AtomicLongMap<K> implements Serializable {

    @CheckForNull
    @LazyInit
    private transient Map<K, Long> asMap;
    private final ConcurrentHashMap<K, AtomicLong> map;

    private AtomicLongMap(ConcurrentHashMap<K, AtomicLong> concurrentHashMap) {
        this.map = (ConcurrentHashMap) Preconditions.checkNotNull(concurrentHashMap);
    }

    public static <K> AtomicLongMap<K> create() {
        return new AtomicLongMap<>(new ConcurrentHashMap());
    }

    private Map<K, Long> createAsMap() {
        return Collections.unmodifiableMap(Maps.transformValues(this.map, new Function<AtomicLong, Long>() { // from class: com.google.common.util.concurrent.AtomicLongMap.1
            @Override // com.google.common.base.Function
            public Long apply(AtomicLong atomicLong) {
                return Long.valueOf(atomicLong.get());
            }
        }));
    }

    @CanIgnoreReturnValue
    public long addAndGet(K k2, long j2) {
        AtomicLong atomicLongPutIfAbsent;
        long j3;
        long j4;
        do {
            atomicLongPutIfAbsent = this.map.get(k2);
            if (atomicLongPutIfAbsent == null && (atomicLongPutIfAbsent = this.map.putIfAbsent(k2, new AtomicLong(j2))) == null) {
                return j2;
            }
            do {
                j3 = atomicLongPutIfAbsent.get();
                if (j3 != 0) {
                    j4 = j3 + j2;
                }
            } while (!atomicLongPutIfAbsent.compareAndSet(j3, j4));
            return j4;
        } while (!this.map.replace(k2, atomicLongPutIfAbsent, new AtomicLong(j2)));
        return j2;
    }

    public Map<K, Long> asMap() {
        Map<K, Long> map = this.asMap;
        if (map != null) {
            return map;
        }
        Map<K, Long> mapCreateAsMap = createAsMap();
        this.asMap = mapCreateAsMap;
        return mapCreateAsMap;
    }

    public void clear() {
        this.map.clear();
    }

    public boolean containsKey(Object obj) {
        return this.map.containsKey(obj);
    }

    @CanIgnoreReturnValue
    public long decrementAndGet(K k2) {
        return addAndGet(k2, -1L);
    }

    public long get(K k2) {
        AtomicLong atomicLong = this.map.get(k2);
        if (atomicLong == null) {
            return 0L;
        }
        return atomicLong.get();
    }

    @CanIgnoreReturnValue
    public long getAndAdd(K k2, long j2) {
        AtomicLong atomicLongPutIfAbsent;
        long j3;
        do {
            atomicLongPutIfAbsent = this.map.get(k2);
            if (atomicLongPutIfAbsent == null && (atomicLongPutIfAbsent = this.map.putIfAbsent(k2, new AtomicLong(j2))) == null) {
                return 0L;
            }
            do {
                j3 = atomicLongPutIfAbsent.get();
                if (j3 == 0) {
                }
            } while (!atomicLongPutIfAbsent.compareAndSet(j3, j3 + j2));
            return j3;
        } while (!this.map.replace(k2, atomicLongPutIfAbsent, new AtomicLong(j2)));
        return 0L;
    }

    @CanIgnoreReturnValue
    public long getAndDecrement(K k2) {
        return getAndAdd(k2, -1L);
    }

    @CanIgnoreReturnValue
    public long getAndIncrement(K k2) {
        return getAndAdd(k2, 1L);
    }

    @CanIgnoreReturnValue
    public long incrementAndGet(K k2) {
        return addAndGet(k2, 1L);
    }

    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    @CanIgnoreReturnValue
    public long put(K k2, long j2) {
        AtomicLong atomicLongPutIfAbsent;
        long j3;
        do {
            atomicLongPutIfAbsent = this.map.get(k2);
            if (atomicLongPutIfAbsent == null && (atomicLongPutIfAbsent = this.map.putIfAbsent(k2, new AtomicLong(j2))) == null) {
                return 0L;
            }
            do {
                j3 = atomicLongPutIfAbsent.get();
                if (j3 == 0) {
                }
            } while (!atomicLongPutIfAbsent.compareAndSet(j3, j2));
            return j3;
        } while (!this.map.replace(k2, atomicLongPutIfAbsent, new AtomicLong(j2)));
        return 0L;
    }

    public void putAll(Map<? extends K, ? extends Long> map) {
        for (Map.Entry<? extends K, ? extends Long> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue().longValue());
        }
    }

    long putIfAbsent(K k2, long j2) {
        AtomicLong atomicLongPutIfAbsent;
        do {
            atomicLongPutIfAbsent = this.map.get(k2);
            if (atomicLongPutIfAbsent == null && (atomicLongPutIfAbsent = this.map.putIfAbsent(k2, new AtomicLong(j2))) == null) {
                return 0L;
            }
            long j3 = atomicLongPutIfAbsent.get();
            if (j3 != 0) {
                return j3;
            }
        } while (!this.map.replace(k2, atomicLongPutIfAbsent, new AtomicLong(j2)));
        return 0L;
    }

    @CanIgnoreReturnValue
    public long remove(K k2) {
        long j2;
        AtomicLong atomicLong = this.map.get(k2);
        if (atomicLong == null) {
            return 0L;
        }
        do {
            j2 = atomicLong.get();
            if (j2 == 0) {
                break;
            }
        } while (!atomicLong.compareAndSet(j2, 0L));
        this.map.remove(k2, atomicLong);
        return j2;
    }

    public void removeAllZeros() {
        Iterator<Map.Entry<K, AtomicLong>> it = this.map.entrySet().iterator();
        while (it.hasNext()) {
            AtomicLong value = it.next().getValue();
            if (value != null && value.get() == 0) {
                it.remove();
            }
        }
    }

    @CanIgnoreReturnValue
    public boolean removeIfZero(K k2) {
        return remove(k2, 0L);
    }

    boolean replace(K k2, long j2, long j3) {
        if (j2 == 0) {
            return putIfAbsent(k2, j3) == 0;
        }
        AtomicLong atomicLong = this.map.get(k2);
        if (atomicLong == null) {
            return false;
        }
        return atomicLong.compareAndSet(j2, j3);
    }

    public int size() {
        return this.map.size();
    }

    public long sum() {
        Iterator<AtomicLong> it = this.map.values().iterator();
        long j2 = 0;
        while (it.hasNext()) {
            j2 += it.next().get();
        }
        return j2;
    }

    public String toString() {
        return this.map.toString();
    }

    public static <K> AtomicLongMap<K> create(Map<? extends K, ? extends Long> map) {
        AtomicLongMap<K> atomicLongMapCreate = create();
        atomicLongMapCreate.putAll(map);
        return atomicLongMapCreate;
    }

    boolean remove(K k2, long j2) {
        AtomicLong atomicLong = this.map.get(k2);
        if (atomicLong == null) {
            return false;
        }
        long j3 = atomicLong.get();
        if (j3 != j2) {
            return false;
        }
        if (j3 != 0 && !atomicLong.compareAndSet(j3, 0L)) {
            return false;
        }
        this.map.remove(k2, atomicLong);
        return true;
    }
}
