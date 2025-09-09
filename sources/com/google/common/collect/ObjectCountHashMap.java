package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Arrays;
import javax.annotation.CheckForNull;

@GwtCompatible(emulated = true, serializable = true)
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
class ObjectCountHashMap<K> {
    private static final long HASH_MASK = -4294967296L;
    private static final int MAXIMUM_CAPACITY = 1073741824;
    private static final long NEXT_MASK = 4294967295L;

    /* renamed from: a, reason: collision with root package name */
    transient Object[] f14263a;

    /* renamed from: b, reason: collision with root package name */
    transient int[] f14264b;

    /* renamed from: c, reason: collision with root package name */
    transient int f14265c;

    /* renamed from: d, reason: collision with root package name */
    transient int f14266d;

    /* renamed from: e, reason: collision with root package name */
    transient long[] f14267e;
    private transient float loadFactor;
    private transient int[] table;
    private transient int threshold;

    class MapEntry extends Multisets.AbstractEntry<K> {

        /* renamed from: a, reason: collision with root package name */
        final Object f14268a;

        /* renamed from: b, reason: collision with root package name */
        int f14269b;

        MapEntry(int i2) {
            this.f14268a = ObjectCountHashMap.this.f14263a[i2];
            this.f14269b = i2;
        }

        void a() {
            int i2 = this.f14269b;
            if (i2 == -1 || i2 >= ObjectCountHashMap.this.q() || !Objects.equal(this.f14268a, ObjectCountHashMap.this.f14263a[this.f14269b])) {
                this.f14269b = ObjectCountHashMap.this.h(this.f14268a);
            }
        }

        @Override // com.google.common.collect.Multiset.Entry
        public int getCount() {
            a();
            int i2 = this.f14269b;
            if (i2 == -1) {
                return 0;
            }
            return ObjectCountHashMap.this.f14264b[i2];
        }

        @Override // com.google.common.collect.Multiset.Entry
        @ParametricNullness
        public K getElement() {
            return (K) this.f14268a;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @CanIgnoreReturnValue
        public int setCount(int i2) {
            a();
            int i3 = this.f14269b;
            if (i3 == -1) {
                ObjectCountHashMap.this.put(this.f14268a, i2);
                return 0;
            }
            int[] iArr = ObjectCountHashMap.this.f14264b;
            int i4 = iArr[i3];
            iArr[i3] = i2;
            return i4;
        }
    }

    ObjectCountHashMap() {
        i(3, 1.0f);
    }

    static ObjectCountHashMap a() {
        return new ObjectCountHashMap();
    }

    static ObjectCountHashMap b(int i2) {
        return new ObjectCountHashMap(i2);
    }

    private static int getHash(long j2) {
        return (int) (j2 >>> 32);
    }

    private static int getNext(long j2) {
        return (int) j2;
    }

    private int hashTableMask() {
        return this.table.length - 1;
    }

    private static long[] newEntries(int i2) {
        long[] jArr = new long[i2];
        Arrays.fill(jArr, -1L);
        return jArr;
    }

    private static int[] newTable(int i2) {
        int[] iArr = new int[i2];
        Arrays.fill(iArr, -1);
        return iArr;
    }

    private void resizeMeMaybe(int i2) {
        int length = this.f14267e.length;
        if (i2 > length) {
            int iMax = Math.max(1, length >>> 1) + length;
            if (iMax < 0) {
                iMax = Integer.MAX_VALUE;
            }
            if (iMax != length) {
                o(iMax);
            }
        }
    }

    private void resizeTable(int i2) {
        if (this.table.length >= 1073741824) {
            this.threshold = Integer.MAX_VALUE;
            return;
        }
        int i3 = ((int) (i2 * this.loadFactor)) + 1;
        int[] iArrNewTable = newTable(i2);
        long[] jArr = this.f14267e;
        int length = iArrNewTable.length - 1;
        for (int i4 = 0; i4 < this.f14265c; i4++) {
            int hash = getHash(jArr[i4]);
            int i5 = hash & length;
            int i6 = iArrNewTable[i5];
            iArrNewTable[i5] = i4;
            jArr[i4] = (hash << 32) | (i6 & 4294967295L);
        }
        this.threshold = i3;
        this.table = iArrNewTable;
    }

    private static long swapNext(long j2, int i2) {
        return (j2 & HASH_MASK) | (4294967295L & i2);
    }

    void c(int i2) {
        if (i2 > this.f14267e.length) {
            o(i2);
        }
        if (i2 >= this.threshold) {
            resizeTable(Math.max(2, Integer.highestOneBit(i2 - 1) << 1));
        }
    }

    public void clear() {
        this.f14266d++;
        Arrays.fill(this.f14263a, 0, this.f14265c, (Object) null);
        Arrays.fill(this.f14264b, 0, this.f14265c, 0);
        Arrays.fill(this.table, -1);
        Arrays.fill(this.f14267e, -1L);
        this.f14265c = 0;
    }

    public boolean containsKey(@CheckForNull Object obj) {
        return h(obj) != -1;
    }

    int d() {
        return this.f14265c == 0 ? -1 : 0;
    }

    Multiset.Entry e(int i2) {
        Preconditions.checkElementIndex(i2, this.f14265c);
        return new MapEntry(i2);
    }

    Object f(int i2) {
        Preconditions.checkElementIndex(i2, this.f14265c);
        return this.f14263a[i2];
    }

    int g(int i2) {
        Preconditions.checkElementIndex(i2, this.f14265c);
        return this.f14264b[i2];
    }

    public int get(@CheckForNull Object obj) {
        int iH = h(obj);
        if (iH == -1) {
            return 0;
        }
        return this.f14264b[iH];
    }

    int h(Object obj) {
        int iD = Hashing.d(obj);
        int next = this.table[hashTableMask() & iD];
        while (next != -1) {
            long j2 = this.f14267e[next];
            if (getHash(j2) == iD && Objects.equal(obj, this.f14263a[next])) {
                return next;
            }
            next = getNext(j2);
        }
        return -1;
    }

    void i(int i2, float f2) {
        Preconditions.checkArgument(i2 >= 0, "Initial capacity must be non-negative");
        Preconditions.checkArgument(f2 > 0.0f, "Illegal load factor");
        int iA = Hashing.a(i2, f2);
        this.table = newTable(iA);
        this.loadFactor = f2;
        this.f14263a = new Object[i2];
        this.f14264b = new int[i2];
        this.f14267e = newEntries(i2);
        this.threshold = Math.max(1, (int) (iA * f2));
    }

    void j(int i2, Object obj, int i3, int i4) {
        this.f14267e[i2] = (i4 << 32) | 4294967295L;
        this.f14263a[i2] = obj;
        this.f14264b[i2] = i3;
    }

    void k(int i2) {
        int iQ = q() - 1;
        if (i2 >= iQ) {
            this.f14263a[i2] = null;
            this.f14264b[i2] = 0;
            this.f14267e[i2] = -1;
            return;
        }
        Object[] objArr = this.f14263a;
        objArr[i2] = objArr[iQ];
        int[] iArr = this.f14264b;
        iArr[i2] = iArr[iQ];
        objArr[iQ] = null;
        iArr[iQ] = 0;
        long[] jArr = this.f14267e;
        long j2 = jArr[iQ];
        jArr[i2] = j2;
        jArr[iQ] = -1;
        int hash = getHash(j2) & hashTableMask();
        int[] iArr2 = this.table;
        int i3 = iArr2[hash];
        if (i3 == iQ) {
            iArr2[hash] = i2;
            return;
        }
        while (true) {
            long j3 = this.f14267e[i3];
            int next = getNext(j3);
            if (next == iQ) {
                this.f14267e[i3] = swapNext(j3, i2);
                return;
            }
            i3 = next;
        }
    }

    int l(int i2) {
        int i3 = i2 + 1;
        if (i3 < this.f14265c) {
            return i3;
        }
        return -1;
    }

    int m(int i2, int i3) {
        return i2 - 1;
    }

    int n(int i2) {
        return remove(this.f14263a[i2], getHash(this.f14267e[i2]));
    }

    void o(int i2) {
        this.f14263a = Arrays.copyOf(this.f14263a, i2);
        this.f14264b = Arrays.copyOf(this.f14264b, i2);
        long[] jArr = this.f14267e;
        int length = jArr.length;
        long[] jArrCopyOf = Arrays.copyOf(jArr, i2);
        if (i2 > length) {
            Arrays.fill(jArrCopyOf, length, i2, -1L);
        }
        this.f14267e = jArrCopyOf;
    }

    void p(int i2, int i3) {
        Preconditions.checkElementIndex(i2, this.f14265c);
        this.f14264b[i2] = i3;
    }

    @CanIgnoreReturnValue
    public int put(@ParametricNullness K k2, int i2) {
        CollectPreconditions.d(i2, "count");
        long[] jArr = this.f14267e;
        Object[] objArr = this.f14263a;
        int[] iArr = this.f14264b;
        int iD = Hashing.d(k2);
        int iHashTableMask = hashTableMask() & iD;
        int i3 = this.f14265c;
        int[] iArr2 = this.table;
        int i4 = iArr2[iHashTableMask];
        if (i4 == -1) {
            iArr2[iHashTableMask] = i3;
        } else {
            while (true) {
                long j2 = jArr[i4];
                if (getHash(j2) == iD && Objects.equal(k2, objArr[i4])) {
                    int i5 = iArr[i4];
                    iArr[i4] = i2;
                    return i5;
                }
                int next = getNext(j2);
                if (next == -1) {
                    jArr[i4] = swapNext(j2, i3);
                    break;
                }
                i4 = next;
            }
        }
        if (i3 == Integer.MAX_VALUE) {
            throw new IllegalStateException("Cannot contain more than Integer.MAX_VALUE elements!");
        }
        int i6 = i3 + 1;
        resizeMeMaybe(i6);
        j(i3, k2, i2, iD);
        this.f14265c = i6;
        if (i3 >= this.threshold) {
            resizeTable(this.table.length * 2);
        }
        this.f14266d++;
        return 0;
    }

    int q() {
        return this.f14265c;
    }

    @CanIgnoreReturnValue
    public int remove(@CheckForNull Object obj) {
        return remove(obj, Hashing.d(obj));
    }

    private int remove(@CheckForNull Object obj, int i2) {
        int iHashTableMask = hashTableMask() & i2;
        int i3 = this.table[iHashTableMask];
        if (i3 == -1) {
            return 0;
        }
        int i4 = -1;
        while (true) {
            if (getHash(this.f14267e[i3]) == i2 && Objects.equal(obj, this.f14263a[i3])) {
                int i5 = this.f14264b[i3];
                if (i4 == -1) {
                    this.table[iHashTableMask] = getNext(this.f14267e[i3]);
                } else {
                    long[] jArr = this.f14267e;
                    jArr[i4] = swapNext(jArr[i4], getNext(jArr[i3]));
                }
                k(i3);
                this.f14265c--;
                this.f14266d++;
                return i5;
            }
            int next = getNext(this.f14267e[i3]);
            if (next == -1) {
                return 0;
            }
            i4 = i3;
            i3 = next;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    ObjectCountHashMap(ObjectCountHashMap objectCountHashMap) {
        i(objectCountHashMap.q(), 1.0f);
        int iD = objectCountHashMap.d();
        while (iD != -1) {
            put(objectCountHashMap.f(iD), objectCountHashMap.g(iD));
            iD = objectCountHashMap.l(iD);
        }
    }

    ObjectCountHashMap(int i2) {
        this(i2, 1.0f);
    }

    ObjectCountHashMap(int i2, float f2) {
        i(i2, f2);
    }
}
