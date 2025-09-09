package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.J2ktIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import javax.annotation.CheckForNull;
import kotlin.UShort;

@GwtCompatible(emulated = true, serializable = true)
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
final class RegularImmutableMap<K, V> extends ImmutableMap<K, V> {
    private static final byte ABSENT = -1;
    private static final int BYTE_MASK = 255;
    private static final int BYTE_MAX_SIZE = 128;
    static final ImmutableMap<Object, Object> EMPTY = new RegularImmutableMap(null, new Object[0], 0);
    private static final int SHORT_MASK = 65535;
    private static final int SHORT_MAX_SIZE = 32768;

    @J2ktIncompatible
    private static final long serialVersionUID = 0;

    @VisibleForTesting
    final transient Object[] alternatingKeysAndValues;

    @CheckForNull
    private final transient Object hashTable;
    private final transient int size;

    static class EntrySet<K, V> extends ImmutableSet<Map.Entry<K, V>> {
        private final transient Object[] alternatingKeysAndValues;
        private final transient int keyOffset;
        private final transient ImmutableMap<K, V> map;
        private final transient int size;

        EntrySet(ImmutableMap<K, V> immutableMap, Object[] objArr, int i2, int i3) {
            this.map = immutableMap;
            this.alternatingKeysAndValues = objArr;
            this.keyOffset = i2;
            this.size = i3;
        }

        @Override // com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(@CheckForNull Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            Object key = entry.getKey();
            Object value = entry.getValue();
            return value != null && value.equals(this.map.get(key));
        }

        @Override // com.google.common.collect.ImmutableCollection
        int copyIntoArray(Object[] objArr, int i2) {
            return asList().copyIntoArray(objArr, i2);
        }

        @Override // com.google.common.collect.ImmutableSet
        ImmutableList<Map.Entry<K, V>> createAsList() {
            return new ImmutableList<Map.Entry<K, V>>() { // from class: com.google.common.collect.RegularImmutableMap.EntrySet.1
                @Override // com.google.common.collect.ImmutableCollection
                public boolean isPartialView() {
                    return true;
                }

                @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
                public int size() {
                    return EntrySet.this.size;
                }

                @Override // com.google.common.collect.ImmutableList, com.google.common.collect.ImmutableCollection
                @J2ktIncompatible
                Object writeReplace() {
                    return super.writeReplace();
                }

                @Override // java.util.List
                public Map.Entry<K, V> get(int i2) {
                    Preconditions.checkElementIndex(i2, EntrySet.this.size);
                    int i3 = i2 * 2;
                    Object obj = EntrySet.this.alternatingKeysAndValues[EntrySet.this.keyOffset + i3];
                    Objects.requireNonNull(obj);
                    Object obj2 = EntrySet.this.alternatingKeysAndValues[i3 + (EntrySet.this.keyOffset ^ 1)];
                    Objects.requireNonNull(obj2);
                    return new AbstractMap.SimpleImmutableEntry(obj, obj2);
                }
            };
        }

        @Override // com.google.common.collect.ImmutableCollection
        boolean isPartialView() {
            return true;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return this.size;
        }

        @Override // com.google.common.collect.ImmutableSet, com.google.common.collect.ImmutableCollection
        @J2ktIncompatible
        @GwtIncompatible
        Object writeReplace() {
            return super.writeReplace();
        }

        @Override // com.google.common.collect.ImmutableSet, com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set, java.util.NavigableSet, com.google.common.collect.SortedIterable
        public UnmodifiableIterator<Map.Entry<K, V>> iterator() {
            return asList().iterator();
        }
    }

    static final class KeySet<K> extends ImmutableSet<K> {
        private final transient ImmutableList<K> list;
        private final transient ImmutableMap<K, ?> map;

        KeySet(ImmutableMap<K, ?> immutableMap, ImmutableList<K> immutableList) {
            this.map = immutableMap;
            this.list = immutableList;
        }

        @Override // com.google.common.collect.ImmutableSet, com.google.common.collect.ImmutableCollection
        public ImmutableList<K> asList() {
            return this.list;
        }

        @Override // com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(@CheckForNull Object obj) {
            return this.map.get(obj) != null;
        }

        @Override // com.google.common.collect.ImmutableCollection
        int copyIntoArray(Object[] objArr, int i2) {
            return asList().copyIntoArray(objArr, i2);
        }

        @Override // com.google.common.collect.ImmutableCollection
        boolean isPartialView() {
            return true;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return this.map.size();
        }

        @Override // com.google.common.collect.ImmutableSet, com.google.common.collect.ImmutableCollection
        @J2ktIncompatible
        @GwtIncompatible
        Object writeReplace() {
            return super.writeReplace();
        }

        @Override // com.google.common.collect.ImmutableSet, com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set, java.util.NavigableSet, com.google.common.collect.SortedIterable
        public UnmodifiableIterator<K> iterator() {
            return asList().iterator();
        }
    }

    static final class KeysOrValuesAsList extends ImmutableList<Object> {
        private final transient Object[] alternatingKeysAndValues;
        private final transient int offset;
        private final transient int size;

        KeysOrValuesAsList(Object[] objArr, int i2, int i3) {
            this.alternatingKeysAndValues = objArr;
            this.offset = i2;
            this.size = i3;
        }

        @Override // java.util.List
        public Object get(int i2) {
            Preconditions.checkElementIndex(i2, this.size);
            Object obj = this.alternatingKeysAndValues[(i2 * 2) + this.offset];
            Objects.requireNonNull(obj);
            return obj;
        }

        @Override // com.google.common.collect.ImmutableCollection
        boolean isPartialView() {
            return true;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return this.size;
        }

        @Override // com.google.common.collect.ImmutableList, com.google.common.collect.ImmutableCollection
        Object writeReplace() {
            return super.writeReplace();
        }
    }

    private RegularImmutableMap(@CheckForNull Object obj, Object[] objArr, int i2) {
        this.hashTable = obj;
        this.alternatingKeysAndValues = objArr;
        this.size = i2;
    }

    static <K, V> RegularImmutableMap<K, V> create(int i2, Object[] objArr) {
        return create(i2, objArr, null);
    }

    @CheckForNull
    private static Object createHashTable(Object[] objArr, int i2, int i3, int i4) {
        ImmutableMap.Builder.DuplicateKey duplicateKey = null;
        if (i2 == 1) {
            Object obj = objArr[i4];
            Objects.requireNonNull(obj);
            Object obj2 = objArr[i4 ^ 1];
            Objects.requireNonNull(obj2);
            CollectPreconditions.a(obj, obj2);
            return null;
        }
        int i5 = i3 - 1;
        int i6 = -1;
        if (i3 <= 128) {
            byte[] bArr = new byte[i3];
            Arrays.fill(bArr, (byte) -1);
            int i7 = 0;
            for (int i8 = 0; i8 < i2; i8++) {
                int i9 = (i8 * 2) + i4;
                int i10 = (i7 * 2) + i4;
                Object obj3 = objArr[i9];
                Objects.requireNonNull(obj3);
                Object obj4 = objArr[i9 ^ 1];
                Objects.requireNonNull(obj4);
                CollectPreconditions.a(obj3, obj4);
                int iC = Hashing.c(obj3.hashCode());
                while (true) {
                    int i11 = iC & i5;
                    int i12 = bArr[i11] & 255;
                    if (i12 == 255) {
                        bArr[i11] = (byte) i10;
                        if (i7 < i8) {
                            objArr[i10] = obj3;
                            objArr[i10 ^ 1] = obj4;
                        }
                        i7++;
                    } else {
                        if (obj3.equals(objArr[i12])) {
                            int i13 = i12 ^ 1;
                            Object obj5 = objArr[i13];
                            Objects.requireNonNull(obj5);
                            duplicateKey = new ImmutableMap.Builder.DuplicateKey(obj3, obj4, obj5);
                            objArr[i13] = obj4;
                            break;
                        }
                        iC = i11 + 1;
                    }
                }
            }
            return i7 == i2 ? bArr : new Object[]{bArr, Integer.valueOf(i7), duplicateKey};
        }
        if (i3 <= 32768) {
            short[] sArr = new short[i3];
            Arrays.fill(sArr, (short) -1);
            int i14 = 0;
            for (int i15 = 0; i15 < i2; i15++) {
                int i16 = (i15 * 2) + i4;
                int i17 = (i14 * 2) + i4;
                Object obj6 = objArr[i16];
                Objects.requireNonNull(obj6);
                Object obj7 = objArr[i16 ^ 1];
                Objects.requireNonNull(obj7);
                CollectPreconditions.a(obj6, obj7);
                int iC2 = Hashing.c(obj6.hashCode());
                while (true) {
                    int i18 = iC2 & i5;
                    int i19 = sArr[i18] & UShort.MAX_VALUE;
                    if (i19 == 65535) {
                        sArr[i18] = (short) i17;
                        if (i14 < i15) {
                            objArr[i17] = obj6;
                            objArr[i17 ^ 1] = obj7;
                        }
                        i14++;
                    } else {
                        if (obj6.equals(objArr[i19])) {
                            int i20 = i19 ^ 1;
                            Object obj8 = objArr[i20];
                            Objects.requireNonNull(obj8);
                            duplicateKey = new ImmutableMap.Builder.DuplicateKey(obj6, obj7, obj8);
                            objArr[i20] = obj7;
                            break;
                        }
                        iC2 = i18 + 1;
                    }
                }
            }
            return i14 == i2 ? sArr : new Object[]{sArr, Integer.valueOf(i14), duplicateKey};
        }
        int[] iArr = new int[i3];
        Arrays.fill(iArr, -1);
        int i21 = 0;
        int i22 = 0;
        while (i21 < i2) {
            int i23 = (i21 * 2) + i4;
            int i24 = (i22 * 2) + i4;
            Object obj9 = objArr[i23];
            Objects.requireNonNull(obj9);
            Object obj10 = objArr[i23 ^ 1];
            Objects.requireNonNull(obj10);
            CollectPreconditions.a(obj9, obj10);
            int iC3 = Hashing.c(obj9.hashCode());
            while (true) {
                int i25 = iC3 & i5;
                int i26 = iArr[i25];
                if (i26 == i6) {
                    iArr[i25] = i24;
                    if (i22 < i21) {
                        objArr[i24] = obj9;
                        objArr[i24 ^ 1] = obj10;
                    }
                    i22++;
                } else {
                    if (obj9.equals(objArr[i26])) {
                        int i27 = i26 ^ 1;
                        Object obj11 = objArr[i27];
                        Objects.requireNonNull(obj11);
                        duplicateKey = new ImmutableMap.Builder.DuplicateKey(obj9, obj10, obj11);
                        objArr[i27] = obj10;
                        break;
                    }
                    iC3 = i25 + 1;
                    i6 = -1;
                }
            }
            i21++;
            i6 = -1;
        }
        return i22 == i2 ? iArr : new Object[]{iArr, Integer.valueOf(i22), duplicateKey};
    }

    @CheckForNull
    static Object createHashTableOrThrow(Object[] objArr, int i2, int i3, int i4) {
        Object objCreateHashTable = createHashTable(objArr, i2, i3, i4);
        if (objCreateHashTable instanceof Object[]) {
            throw ((ImmutableMap.Builder.DuplicateKey) ((Object[]) objCreateHashTable)[2]).a();
        }
        return objCreateHashTable;
    }

    @Override // com.google.common.collect.ImmutableMap
    ImmutableSet<Map.Entry<K, V>> createEntrySet() {
        return new EntrySet(this, this.alternatingKeysAndValues, 0, this.size);
    }

    @Override // com.google.common.collect.ImmutableMap
    ImmutableSet<K> createKeySet() {
        return new KeySet(this, new KeysOrValuesAsList(this.alternatingKeysAndValues, 0, this.size));
    }

    @Override // com.google.common.collect.ImmutableMap
    ImmutableCollection<V> createValues() {
        return new KeysOrValuesAsList(this.alternatingKeysAndValues, 1, this.size);
    }

    @Override // com.google.common.collect.ImmutableMap, java.util.Map
    @CheckForNull
    public V get(@CheckForNull Object obj) {
        V v2 = (V) get(this.hashTable, this.alternatingKeysAndValues, this.size, 0, obj);
        if (v2 == null) {
            return null;
        }
        return v2;
    }

    @Override // com.google.common.collect.ImmutableMap
    boolean isPartialView() {
        return false;
    }

    @Override // java.util.Map
    public int size() {
        return this.size;
    }

    @Override // com.google.common.collect.ImmutableMap
    @J2ktIncompatible
    @GwtIncompatible
    Object writeReplace() {
        return super.writeReplace();
    }

    static <K, V> RegularImmutableMap<K, V> create(int i2, Object[] objArr, ImmutableMap.Builder<K, V> builder) {
        if (i2 == 0) {
            return (RegularImmutableMap) EMPTY;
        }
        if (i2 == 1) {
            Object obj = objArr[0];
            Objects.requireNonNull(obj);
            Object obj2 = objArr[1];
            Objects.requireNonNull(obj2);
            CollectPreconditions.a(obj, obj2);
            return new RegularImmutableMap<>(null, objArr, 1);
        }
        Preconditions.checkPositionIndex(i2, objArr.length >> 1);
        Object objCreateHashTable = createHashTable(objArr, i2, ImmutableSet.chooseTableSize(i2), 0);
        if (objCreateHashTable instanceof Object[]) {
            Object[] objArr2 = (Object[]) objCreateHashTable;
            ImmutableMap.Builder.DuplicateKey duplicateKey = (ImmutableMap.Builder.DuplicateKey) objArr2[2];
            if (builder == null) {
                throw duplicateKey.a();
            }
            builder.f14010e = duplicateKey;
            Object obj3 = objArr2[0];
            int iIntValue = ((Integer) objArr2[1]).intValue();
            objArr = Arrays.copyOf(objArr, iIntValue * 2);
            objCreateHashTable = obj3;
            i2 = iIntValue;
        }
        return new RegularImmutableMap<>(objCreateHashTable, objArr, i2);
    }

    @CheckForNull
    static Object get(@CheckForNull Object obj, Object[] objArr, int i2, int i3, @CheckForNull Object obj2) {
        if (obj2 == null) {
            return null;
        }
        if (i2 == 1) {
            Object obj3 = objArr[i3];
            Objects.requireNonNull(obj3);
            if (!obj3.equals(obj2)) {
                return null;
            }
            Object obj4 = objArr[i3 ^ 1];
            Objects.requireNonNull(obj4);
            return obj4;
        }
        if (obj == null) {
            return null;
        }
        if (obj instanceof byte[]) {
            byte[] bArr = (byte[]) obj;
            int length = bArr.length - 1;
            int iC = Hashing.c(obj2.hashCode());
            while (true) {
                int i4 = iC & length;
                int i5 = bArr[i4] & 255;
                if (i5 == 255) {
                    return null;
                }
                if (obj2.equals(objArr[i5])) {
                    return objArr[i5 ^ 1];
                }
                iC = i4 + 1;
            }
        } else if (obj instanceof short[]) {
            short[] sArr = (short[]) obj;
            int length2 = sArr.length - 1;
            int iC2 = Hashing.c(obj2.hashCode());
            while (true) {
                int i6 = iC2 & length2;
                int i7 = sArr[i6] & UShort.MAX_VALUE;
                if (i7 == 65535) {
                    return null;
                }
                if (obj2.equals(objArr[i7])) {
                    return objArr[i7 ^ 1];
                }
                iC2 = i6 + 1;
            }
        } else {
            int[] iArr = (int[]) obj;
            int length3 = iArr.length - 1;
            int iC3 = Hashing.c(obj2.hashCode());
            while (true) {
                int i8 = iC3 & length3;
                int i9 = iArr[i8];
                if (i9 == -1) {
                    return null;
                }
                if (obj2.equals(objArr[i9])) {
                    return objArr[i9 ^ 1];
                }
                iC3 = i8 + 1;
            }
        }
    }
}
