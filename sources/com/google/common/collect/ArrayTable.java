package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.DoNotCall;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.annotation.CheckForNull;

@GwtCompatible(emulated = true)
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public final class ArrayTable<R, C, V> extends AbstractTable<R, C, V> implements Serializable {
    private static final long serialVersionUID = 0;
    private final V[][] array;
    private final ImmutableMap<C, Integer> columnKeyToIndex;
    private final ImmutableList<C> columnList;

    @CheckForNull
    @LazyInit
    private transient ArrayTable<R, C, V>.ColumnMap columnMap;
    private final ImmutableMap<R, Integer> rowKeyToIndex;
    private final ImmutableList<R> rowList;

    @CheckForNull
    @LazyInit
    private transient ArrayTable<R, C, V>.RowMap rowMap;

    private static abstract class ArrayMap<K, V> extends Maps.IteratorBasedAbstractMap<K, V> {
        private final ImmutableMap<K, Integer> keyIndex;

        @Override // com.google.common.collect.Maps.IteratorBasedAbstractMap
        Iterator a() {
            return new AbstractIndexedListIterator<Map.Entry<K, V>>(size()) { // from class: com.google.common.collect.ArrayTable.ArrayMap.2
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.google.common.collect.AbstractIndexedListIterator
                /* renamed from: a, reason: merged with bridge method [inline-methods] */
                public Map.Entry get(int i2) {
                    return ArrayMap.this.b(i2);
                }
            };
        }

        Map.Entry b(final int i2) {
            Preconditions.checkElementIndex(i2, size());
            return new AbstractMapEntry<K, V>(this) { // from class: com.google.common.collect.ArrayTable.ArrayMap.1

                /* renamed from: b, reason: collision with root package name */
                final /* synthetic */ ArrayMap f13902b;

                {
                    this.f13902b = this;
                }

                @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
                public K getKey() {
                    return (K) this.f13902b.c(i2);
                }

                @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
                @ParametricNullness
                public V getValue() {
                    return (V) this.f13902b.e(i2);
                }

                @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
                @ParametricNullness
                public V setValue(@ParametricNullness V v2) {
                    return (V) this.f13902b.f(i2, v2);
                }
            };
        }

        Object c(int i2) {
            return this.keyIndex.keySet().asList().get(i2);
        }

        @Override // com.google.common.collect.Maps.IteratorBasedAbstractMap, java.util.AbstractMap, java.util.Map
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean containsKey(@CheckForNull Object obj) {
            return this.keyIndex.containsKey(obj);
        }

        abstract String d();

        abstract Object e(int i2);

        abstract Object f(int i2, Object obj);

        @Override // java.util.AbstractMap, java.util.Map
        @CheckForNull
        public V get(@CheckForNull Object obj) {
            Integer num = this.keyIndex.get(obj);
            if (num == null) {
                return null;
            }
            return (V) e(num.intValue());
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean isEmpty() {
            return this.keyIndex.isEmpty();
        }

        @Override // java.util.AbstractMap, java.util.Map
        public Set<K> keySet() {
            return this.keyIndex.keySet();
        }

        @Override // java.util.AbstractMap, java.util.Map
        @CheckForNull
        public V put(K k2, @ParametricNullness V v2) {
            Integer num = this.keyIndex.get(k2);
            if (num != null) {
                return (V) f(num.intValue(), v2);
            }
            throw new IllegalArgumentException(d() + " " + k2 + " not in " + this.keyIndex.keySet());
        }

        @Override // java.util.AbstractMap, java.util.Map
        @CheckForNull
        public V remove(@CheckForNull Object obj) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.collect.Maps.IteratorBasedAbstractMap, java.util.AbstractMap, java.util.Map
        public int size() {
            return this.keyIndex.size();
        }

        private ArrayMap(ImmutableMap<K, Integer> immutableMap) {
            this.keyIndex = immutableMap;
        }
    }

    private class Column extends ArrayMap<R, V> {

        /* renamed from: a, reason: collision with root package name */
        final int f13904a;

        Column(int i2) {
            super(ArrayTable.this.rowKeyToIndex);
            this.f13904a = i2;
        }

        @Override // com.google.common.collect.ArrayTable.ArrayMap
        String d() {
            return "Row";
        }

        @Override // com.google.common.collect.ArrayTable.ArrayMap
        Object e(int i2) {
            return ArrayTable.this.at(i2, this.f13904a);
        }

        @Override // com.google.common.collect.ArrayTable.ArrayMap
        Object f(int i2, Object obj) {
            return ArrayTable.this.set(i2, this.f13904a, obj);
        }
    }

    private class ColumnMap extends ArrayMap<C, Map<R, V>> {
        @Override // com.google.common.collect.ArrayTable.ArrayMap
        String d() {
            return "Column";
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.collect.ArrayTable.ArrayMap
        /* renamed from: g, reason: merged with bridge method [inline-methods] */
        public Map e(int i2) {
            return new Column(i2);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.collect.ArrayTable.ArrayMap
        /* renamed from: h, reason: merged with bridge method [inline-methods] */
        public Map f(int i2, Map map) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.collect.ArrayTable.ArrayMap, java.util.AbstractMap, java.util.Map
        @CheckForNull
        public /* bridge */ /* synthetic */ Object put(Object obj, Object obj2) {
            return put((ColumnMap) obj, (Map) obj2);
        }

        private ColumnMap() {
            super(ArrayTable.this.columnKeyToIndex);
        }

        @CheckForNull
        public Map<R, V> put(C c2, Map<R, V> map) {
            throw new UnsupportedOperationException();
        }
    }

    private class Row extends ArrayMap<C, V> {

        /* renamed from: a, reason: collision with root package name */
        final int f13907a;

        Row(int i2) {
            super(ArrayTable.this.columnKeyToIndex);
            this.f13907a = i2;
        }

        @Override // com.google.common.collect.ArrayTable.ArrayMap
        String d() {
            return "Column";
        }

        @Override // com.google.common.collect.ArrayTable.ArrayMap
        Object e(int i2) {
            return ArrayTable.this.at(this.f13907a, i2);
        }

        @Override // com.google.common.collect.ArrayTable.ArrayMap
        Object f(int i2, Object obj) {
            return ArrayTable.this.set(this.f13907a, i2, obj);
        }
    }

    private class RowMap extends ArrayMap<R, Map<C, V>> {
        @Override // com.google.common.collect.ArrayTable.ArrayMap
        String d() {
            return "Row";
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.collect.ArrayTable.ArrayMap
        /* renamed from: g, reason: merged with bridge method [inline-methods] */
        public Map e(int i2) {
            return new Row(i2);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.collect.ArrayTable.ArrayMap
        /* renamed from: h, reason: merged with bridge method [inline-methods] */
        public Map f(int i2, Map map) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.collect.ArrayTable.ArrayMap, java.util.AbstractMap, java.util.Map
        @CheckForNull
        public /* bridge */ /* synthetic */ Object put(Object obj, Object obj2) {
            return put((RowMap) obj, (Map) obj2);
        }

        private RowMap() {
            super(ArrayTable.this.rowKeyToIndex);
        }

        @CheckForNull
        public Map<C, V> put(R r2, Map<C, V> map) {
            throw new UnsupportedOperationException();
        }
    }

    private ArrayTable(Iterable<? extends R> iterable, Iterable<? extends C> iterable2) {
        ImmutableList<R> immutableListCopyOf = ImmutableList.copyOf(iterable);
        this.rowList = immutableListCopyOf;
        ImmutableList<C> immutableListCopyOf2 = ImmutableList.copyOf(iterable2);
        this.columnList = immutableListCopyOf2;
        Preconditions.checkArgument(immutableListCopyOf.isEmpty() == immutableListCopyOf2.isEmpty());
        this.rowKeyToIndex = Maps.p(immutableListCopyOf);
        this.columnKeyToIndex = Maps.p(immutableListCopyOf2);
        this.array = (V[][]) ((Object[][]) Array.newInstance((Class<?>) Object.class, immutableListCopyOf.size(), immutableListCopyOf2.size()));
        eraseAll();
    }

    public static <R, C, V> ArrayTable<R, C, V> create(Iterable<? extends R> iterable, Iterable<? extends C> iterable2) {
        return new ArrayTable<>(iterable, iterable2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Table.Cell<R, C, V> getCell(int i2) {
        return new Tables.AbstractCell<R, C, V>(this, i2) { // from class: com.google.common.collect.ArrayTable.2

            /* renamed from: a, reason: collision with root package name */
            final int f13896a;

            /* renamed from: b, reason: collision with root package name */
            final int f13897b;

            /* renamed from: c, reason: collision with root package name */
            final /* synthetic */ int f13898c;

            /* renamed from: d, reason: collision with root package name */
            final /* synthetic */ ArrayTable f13899d;

            {
                this.f13898c = i2;
                this.f13899d = this;
                this.f13896a = i2 / this.columnList.size();
                this.f13897b = i2 % this.columnList.size();
            }

            @Override // com.google.common.collect.Table.Cell
            public C getColumnKey() {
                return (C) this.f13899d.columnList.get(this.f13897b);
            }

            @Override // com.google.common.collect.Table.Cell
            public R getRowKey() {
                return (R) this.f13899d.rowList.get(this.f13896a);
            }

            @Override // com.google.common.collect.Table.Cell
            @CheckForNull
            public V getValue() {
                return (V) this.f13899d.at(this.f13896a, this.f13897b);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    @CheckForNull
    public V getValue(int i2) {
        return at(i2 / this.columnList.size(), i2 % this.columnList.size());
    }

    @CheckForNull
    public V at(int i2, int i3) {
        Preconditions.checkElementIndex(i2, this.rowList.size());
        Preconditions.checkElementIndex(i3, this.columnList.size());
        return this.array[i2][i3];
    }

    @Override // com.google.common.collect.AbstractTable
    Iterator<Table.Cell<R, C, V>> cellIterator() {
        return new AbstractIndexedListIterator<Table.Cell<R, C, V>>(size()) { // from class: com.google.common.collect.ArrayTable.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.google.common.collect.AbstractIndexedListIterator
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public Table.Cell get(int i2) {
                return ArrayTable.this.getCell(i2);
            }
        };
    }

    @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
    public Set<Table.Cell<R, C, V>> cellSet() {
        return super.cellSet();
    }

    @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
    @DoNotCall("Always throws UnsupportedOperationException")
    @Deprecated
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.common.collect.Table
    public Map<R, V> column(C c2) {
        Preconditions.checkNotNull(c2);
        Integer num = this.columnKeyToIndex.get(c2);
        return num == null ? Collections.emptyMap() : new Column(num.intValue());
    }

    public ImmutableList<C> columnKeyList() {
        return this.columnList;
    }

    @Override // com.google.common.collect.Table
    public Map<C, Map<R, V>> columnMap() {
        ArrayTable<R, C, V>.ColumnMap columnMap = this.columnMap;
        if (columnMap != null) {
            return columnMap;
        }
        ArrayTable<R, C, V>.ColumnMap columnMap2 = new ColumnMap();
        this.columnMap = columnMap2;
        return columnMap2;
    }

    @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
    public boolean contains(@CheckForNull Object obj, @CheckForNull Object obj2) {
        return containsRow(obj) && containsColumn(obj2);
    }

    @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
    public boolean containsColumn(@CheckForNull Object obj) {
        return this.columnKeyToIndex.containsKey(obj);
    }

    @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
    public boolean containsRow(@CheckForNull Object obj) {
        return this.rowKeyToIndex.containsKey(obj);
    }

    @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
    public boolean containsValue(@CheckForNull Object obj) {
        for (V[] vArr : this.array) {
            for (V v2 : vArr) {
                if (Objects.equal(obj, v2)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
    public /* bridge */ /* synthetic */ boolean equals(@CheckForNull Object obj) {
        return super.equals(obj);
    }

    @CanIgnoreReturnValue
    @CheckForNull
    public V erase(@CheckForNull Object obj, @CheckForNull Object obj2) {
        Integer num = this.rowKeyToIndex.get(obj);
        Integer num2 = this.columnKeyToIndex.get(obj2);
        if (num == null || num2 == null) {
            return null;
        }
        return set(num.intValue(), num2.intValue(), null);
    }

    public void eraseAll() {
        for (V[] vArr : this.array) {
            Arrays.fill(vArr, (Object) null);
        }
    }

    @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
    @CheckForNull
    public V get(@CheckForNull Object obj, @CheckForNull Object obj2) {
        Integer num = this.rowKeyToIndex.get(obj);
        Integer num2 = this.columnKeyToIndex.get(obj2);
        if (num == null || num2 == null) {
            return null;
        }
        return at(num.intValue(), num2.intValue());
    }

    @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
    public /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
    public boolean isEmpty() {
        return this.rowList.isEmpty() || this.columnList.isEmpty();
    }

    @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
    @CanIgnoreReturnValue
    @CheckForNull
    public V put(R r2, C c2, @CheckForNull V v2) {
        Preconditions.checkNotNull(r2);
        Preconditions.checkNotNull(c2);
        Integer num = this.rowKeyToIndex.get(r2);
        Preconditions.checkArgument(num != null, "Row %s not in %s", r2, this.rowList);
        Integer num2 = this.columnKeyToIndex.get(c2);
        Preconditions.checkArgument(num2 != null, "Column %s not in %s", c2, this.columnList);
        return set(num.intValue(), num2.intValue(), v2);
    }

    @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
    public void putAll(Table<? extends R, ? extends C, ? extends V> table) {
        super.putAll(table);
    }

    @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
    @CheckForNull
    @DoNotCall("Always throws UnsupportedOperationException")
    @Deprecated
    @CanIgnoreReturnValue
    public V remove(@CheckForNull Object obj, @CheckForNull Object obj2) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.common.collect.Table
    public Map<C, V> row(R r2) {
        Preconditions.checkNotNull(r2);
        Integer num = this.rowKeyToIndex.get(r2);
        return num == null ? Collections.emptyMap() : new Row(num.intValue());
    }

    public ImmutableList<R> rowKeyList() {
        return this.rowList;
    }

    @Override // com.google.common.collect.Table
    public Map<R, Map<C, V>> rowMap() {
        ArrayTable<R, C, V>.RowMap rowMap = this.rowMap;
        if (rowMap != null) {
            return rowMap;
        }
        ArrayTable<R, C, V>.RowMap rowMap2 = new RowMap();
        this.rowMap = rowMap2;
        return rowMap2;
    }

    @CanIgnoreReturnValue
    @CheckForNull
    public V set(int i2, int i3, @CheckForNull V v2) {
        Preconditions.checkElementIndex(i2, this.rowList.size());
        Preconditions.checkElementIndex(i3, this.columnList.size());
        V[] vArr = this.array[i2];
        V v3 = vArr[i3];
        vArr[i3] = v2;
        return v3;
    }

    @Override // com.google.common.collect.Table
    public int size() {
        return this.rowList.size() * this.columnList.size();
    }

    @GwtIncompatible
    public V[][] toArray(Class<V> cls) {
        V[][] vArr = (V[][]) ((Object[][]) Array.newInstance((Class<?>) cls, this.rowList.size(), this.columnList.size()));
        for (int i2 = 0; i2 < this.rowList.size(); i2++) {
            V[] vArr2 = this.array[i2];
            System.arraycopy(vArr2, 0, vArr[i2], 0, vArr2.length);
        }
        return vArr;
    }

    @Override // com.google.common.collect.AbstractTable
    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
    public Collection<V> values() {
        return super.values();
    }

    @Override // com.google.common.collect.AbstractTable
    Iterator<V> valuesIterator() {
        return new AbstractIndexedListIterator<V>(size()) { // from class: com.google.common.collect.ArrayTable.3
            @Override // com.google.common.collect.AbstractIndexedListIterator
            protected Object get(int i2) {
                return ArrayTable.this.getValue(i2);
            }
        };
    }

    public static <R, C, V> ArrayTable<R, C, V> create(Table<R, C, ? extends V> table) {
        return table instanceof ArrayTable ? new ArrayTable<>((ArrayTable) table) : new ArrayTable<>(table);
    }

    @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
    public ImmutableSet<C> columnKeySet() {
        return this.columnKeyToIndex.keySet();
    }

    @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
    public ImmutableSet<R> rowKeySet() {
        return this.rowKeyToIndex.keySet();
    }

    /* JADX WARN: Multi-variable type inference failed */
    private ArrayTable(Table<R, C, ? extends V> table) {
        this(table.rowKeySet(), table.columnKeySet());
        putAll(table);
    }

    private ArrayTable(ArrayTable<R, C, V> arrayTable) {
        ImmutableList<R> immutableList = arrayTable.rowList;
        this.rowList = immutableList;
        ImmutableList<C> immutableList2 = arrayTable.columnList;
        this.columnList = immutableList2;
        this.rowKeyToIndex = arrayTable.rowKeyToIndex;
        this.columnKeyToIndex = arrayTable.columnKeyToIndex;
        V[][] vArr = (V[][]) ((Object[][]) Array.newInstance((Class<?>) Object.class, immutableList.size(), immutableList2.size()));
        this.array = vArr;
        for (int i2 = 0; i2 < this.rowList.size(); i2++) {
            V[] vArr2 = arrayTable.array[i2];
            System.arraycopy(vArr2, 0, vArr[i2], 0, vArr2.length);
        }
    }
}
