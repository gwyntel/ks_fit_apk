package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.J2ktIncompatible;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.collect.Table;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.function.BinaryOperator;
import java.util.stream.Collector;
import javax.annotation.CheckForNull;

@GwtCompatible
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public final class Tables {
    private static final Function<? extends Map<?, ?>, ? extends Map<?, ?>> UNMODIFIABLE_WRAPPER = new Function<Map<Object, Object>, Map<Object, Object>>() { // from class: com.google.common.collect.Tables.1
        @Override // com.google.common.base.Function
        public Map<Object, Object> apply(Map<Object, Object> map) {
            return Collections.unmodifiableMap(map);
        }
    };

    static abstract class AbstractCell<R, C, V> implements Table.Cell<R, C, V> {
        AbstractCell() {
        }

        @Override // com.google.common.collect.Table.Cell
        public boolean equals(@CheckForNull Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Table.Cell)) {
                return false;
            }
            Table.Cell cell = (Table.Cell) obj;
            return Objects.equal(getRowKey(), cell.getRowKey()) && Objects.equal(getColumnKey(), cell.getColumnKey()) && Objects.equal(getValue(), cell.getValue());
        }

        @Override // com.google.common.collect.Table.Cell
        public int hashCode() {
            return Objects.hashCode(getRowKey(), getColumnKey(), getValue());
        }

        public String toString() {
            return "(" + getRowKey() + "," + getColumnKey() + ")=" + getValue();
        }
    }

    static final class ImmutableCell<R, C, V> extends AbstractCell<R, C, V> implements Serializable {
        private static final long serialVersionUID = 0;

        @ParametricNullness
        private final C columnKey;

        @ParametricNullness
        private final R rowKey;

        @ParametricNullness
        private final V value;

        ImmutableCell(@ParametricNullness R r2, @ParametricNullness C c2, @ParametricNullness V v2) {
            this.rowKey = r2;
            this.columnKey = c2;
            this.value = v2;
        }

        @Override // com.google.common.collect.Table.Cell
        @ParametricNullness
        public C getColumnKey() {
            return this.columnKey;
        }

        @Override // com.google.common.collect.Table.Cell
        @ParametricNullness
        public R getRowKey() {
            return this.rowKey;
        }

        @Override // com.google.common.collect.Table.Cell
        @ParametricNullness
        public V getValue() {
            return this.value;
        }
    }

    private static class TransformedTable<R, C, V1, V2> extends AbstractTable<R, C, V2> {

        /* renamed from: a, reason: collision with root package name */
        final Table f14351a;

        /* renamed from: b, reason: collision with root package name */
        final Function f14352b;

        TransformedTable(Table table, Function function) {
            this.f14351a = (Table) Preconditions.checkNotNull(table);
            this.f14352b = (Function) Preconditions.checkNotNull(function);
        }

        Function a() {
            return new Function<Table.Cell<R, C, V1>, Table.Cell<R, C, V2>>() { // from class: com.google.common.collect.Tables.TransformedTable.1
                @Override // com.google.common.base.Function
                public Table.Cell<R, C, V2> apply(Table.Cell<R, C, V1> cell) {
                    return Tables.immutableCell(cell.getRowKey(), cell.getColumnKey(), TransformedTable.this.f14352b.apply(cell.getValue()));
                }
            };
        }

        @Override // com.google.common.collect.AbstractTable
        Iterator cellIterator() {
            return Iterators.transform(this.f14351a.cellSet().iterator(), a());
        }

        @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
        public void clear() {
            this.f14351a.clear();
        }

        @Override // com.google.common.collect.Table
        public Map<R, V2> column(@ParametricNullness C c2) {
            return Maps.transformValues(this.f14351a.column(c2), this.f14352b);
        }

        @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
        public Set<C> columnKeySet() {
            return this.f14351a.columnKeySet();
        }

        @Override // com.google.common.collect.Table
        public Map<C, Map<R, V2>> columnMap() {
            return Maps.transformValues(this.f14351a.columnMap(), new Function<Map<R, V1>, Map<R, V2>>() { // from class: com.google.common.collect.Tables.TransformedTable.3
                @Override // com.google.common.base.Function
                public Map<R, V2> apply(Map<R, V1> map) {
                    return Maps.transformValues(map, TransformedTable.this.f14352b);
                }
            });
        }

        @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
        public boolean contains(@CheckForNull Object obj, @CheckForNull Object obj2) {
            return this.f14351a.contains(obj, obj2);
        }

        @Override // com.google.common.collect.AbstractTable
        Collection createValues() {
            return Collections2.transform(this.f14351a.values(), this.f14352b);
        }

        @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
        @CheckForNull
        public V2 get(@CheckForNull Object obj, @CheckForNull Object obj2) {
            if (contains(obj, obj2)) {
                return (V2) this.f14352b.apply(NullnessCasts.a(this.f14351a.get(obj, obj2)));
            }
            return null;
        }

        @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
        @CheckForNull
        public V2 put(@ParametricNullness R r2, @ParametricNullness C c2, @ParametricNullness V2 v2) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
        public void putAll(Table<? extends R, ? extends C, ? extends V2> table) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
        @CheckForNull
        public V2 remove(@CheckForNull Object obj, @CheckForNull Object obj2) {
            if (contains(obj, obj2)) {
                return (V2) this.f14352b.apply(NullnessCasts.a(this.f14351a.remove(obj, obj2)));
            }
            return null;
        }

        @Override // com.google.common.collect.Table
        public Map<C, V2> row(@ParametricNullness R r2) {
            return Maps.transformValues(this.f14351a.row(r2), this.f14352b);
        }

        @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
        public Set<R> rowKeySet() {
            return this.f14351a.rowKeySet();
        }

        @Override // com.google.common.collect.Table
        public Map<R, Map<C, V2>> rowMap() {
            return Maps.transformValues(this.f14351a.rowMap(), new Function<Map<C, V1>, Map<C, V2>>() { // from class: com.google.common.collect.Tables.TransformedTable.2
                @Override // com.google.common.base.Function
                public Map<C, V2> apply(Map<C, V1> map) {
                    return Maps.transformValues(map, TransformedTable.this.f14352b);
                }
            });
        }

        @Override // com.google.common.collect.Table
        public int size() {
            return this.f14351a.size();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class TransposeTable<C, R, V> extends AbstractTable<C, R, V> {

        /* renamed from: a, reason: collision with root package name */
        final Table f14356a;

        TransposeTable(Table table) {
            this.f14356a = (Table) Preconditions.checkNotNull(table);
        }

        @Override // com.google.common.collect.AbstractTable
        Iterator cellIterator() {
            return Iterators.transform(this.f14356a.cellSet().iterator(), new Function() { // from class: com.google.common.collect.g4
                @Override // com.google.common.base.Function
                public final Object apply(Object obj) {
                    return Tables.a((Table.Cell) obj);
                }
            });
        }

        @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
        public void clear() {
            this.f14356a.clear();
        }

        @Override // com.google.common.collect.Table
        public Map<C, V> column(@ParametricNullness R r2) {
            return this.f14356a.row(r2);
        }

        @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
        public Set<R> columnKeySet() {
            return this.f14356a.rowKeySet();
        }

        @Override // com.google.common.collect.Table
        public Map<R, Map<C, V>> columnMap() {
            return this.f14356a.rowMap();
        }

        @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
        public boolean contains(@CheckForNull Object obj, @CheckForNull Object obj2) {
            return this.f14356a.contains(obj2, obj);
        }

        @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
        public boolean containsColumn(@CheckForNull Object obj) {
            return this.f14356a.containsRow(obj);
        }

        @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
        public boolean containsRow(@CheckForNull Object obj) {
            return this.f14356a.containsColumn(obj);
        }

        @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
        public boolean containsValue(@CheckForNull Object obj) {
            return this.f14356a.containsValue(obj);
        }

        @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
        @CheckForNull
        public V get(@CheckForNull Object obj, @CheckForNull Object obj2) {
            return (V) this.f14356a.get(obj2, obj);
        }

        @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
        @CheckForNull
        public V put(@ParametricNullness C c2, @ParametricNullness R r2, @ParametricNullness V v2) {
            return (V) this.f14356a.put(r2, c2, v2);
        }

        @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
        public void putAll(Table<? extends C, ? extends R, ? extends V> table) {
            this.f14356a.putAll(Tables.transpose(table));
        }

        @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
        @CheckForNull
        public V remove(@CheckForNull Object obj, @CheckForNull Object obj2) {
            return (V) this.f14356a.remove(obj2, obj);
        }

        @Override // com.google.common.collect.Table
        public Map<R, V> row(@ParametricNullness C c2) {
            return this.f14356a.column(c2);
        }

        @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
        public Set<C> rowKeySet() {
            return this.f14356a.columnKeySet();
        }

        @Override // com.google.common.collect.Table
        public Map<C, Map<R, V>> rowMap() {
            return this.f14356a.columnMap();
        }

        @Override // com.google.common.collect.Table
        public int size() {
            return this.f14356a.size();
        }

        @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
        public Collection<V> values() {
            return this.f14356a.values();
        }
    }

    private static final class UnmodifiableRowSortedMap<R, C, V> extends UnmodifiableTable<R, C, V> implements RowSortedTable<R, C, V> {
        private static final long serialVersionUID = 0;

        public UnmodifiableRowSortedMap(RowSortedTable<R, ? extends C, ? extends V> rowSortedTable) {
            super(rowSortedTable);
        }

        @Override // com.google.common.collect.Tables.UnmodifiableTable, com.google.common.collect.ForwardingTable, com.google.common.collect.Table
        public SortedSet<R> rowKeySet() {
            return Collections.unmodifiableSortedSet(delegate().rowKeySet());
        }

        @Override // com.google.common.collect.Tables.UnmodifiableTable, com.google.common.collect.ForwardingTable, com.google.common.collect.Table
        public SortedMap<R, Map<C, V>> rowMap() {
            return Collections.unmodifiableSortedMap(Maps.transformValues((SortedMap) delegate().rowMap(), Tables.unmodifiableWrapper()));
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.common.collect.Tables.UnmodifiableTable, com.google.common.collect.ForwardingTable, com.google.common.collect.ForwardingObject
        public RowSortedTable<R, C, V> delegate() {
            return (RowSortedTable) super.delegate();
        }
    }

    private static class UnmodifiableTable<R, C, V> extends ForwardingTable<R, C, V> implements Serializable {
        private static final long serialVersionUID = 0;
        final Table<? extends R, ? extends C, ? extends V> delegate;

        UnmodifiableTable(Table<? extends R, ? extends C, ? extends V> table) {
            this.delegate = (Table) Preconditions.checkNotNull(table);
        }

        @Override // com.google.common.collect.ForwardingTable, com.google.common.collect.Table
        public Set<Table.Cell<R, C, V>> cellSet() {
            return Collections.unmodifiableSet(super.cellSet());
        }

        @Override // com.google.common.collect.ForwardingTable, com.google.common.collect.Table
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.collect.ForwardingTable, com.google.common.collect.Table
        public Map<R, V> column(@ParametricNullness C c2) {
            return Collections.unmodifiableMap(super.column(c2));
        }

        @Override // com.google.common.collect.ForwardingTable, com.google.common.collect.Table
        public Set<C> columnKeySet() {
            return Collections.unmodifiableSet(super.columnKeySet());
        }

        @Override // com.google.common.collect.ForwardingTable, com.google.common.collect.Table
        public Map<C, Map<R, V>> columnMap() {
            return Collections.unmodifiableMap(Maps.transformValues(super.columnMap(), Tables.unmodifiableWrapper()));
        }

        @Override // com.google.common.collect.ForwardingTable, com.google.common.collect.Table
        @CheckForNull
        public V put(@ParametricNullness R r2, @ParametricNullness C c2, @ParametricNullness V v2) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.collect.ForwardingTable, com.google.common.collect.Table
        public void putAll(Table<? extends R, ? extends C, ? extends V> table) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.collect.ForwardingTable, com.google.common.collect.Table
        @CheckForNull
        public V remove(@CheckForNull Object obj, @CheckForNull Object obj2) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.collect.ForwardingTable, com.google.common.collect.Table
        public Map<C, V> row(@ParametricNullness R r2) {
            return Collections.unmodifiableMap(super.row(r2));
        }

        @Override // com.google.common.collect.ForwardingTable, com.google.common.collect.Table
        public Set<R> rowKeySet() {
            return Collections.unmodifiableSet(super.rowKeySet());
        }

        @Override // com.google.common.collect.ForwardingTable, com.google.common.collect.Table
        public Map<R, Map<C, V>> rowMap() {
            return Collections.unmodifiableMap(Maps.transformValues(super.rowMap(), Tables.unmodifiableWrapper()));
        }

        @Override // com.google.common.collect.ForwardingTable, com.google.common.collect.Table
        public Collection<V> values() {
            return Collections.unmodifiableCollection(super.values());
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.common.collect.ForwardingTable, com.google.common.collect.ForwardingObject
        public Table<R, C, V> delegate() {
            return this.delegate;
        }
    }

    private Tables() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ Table.Cell a(Table.Cell cell) {
        return transposeCell(cell);
    }

    static boolean c(Table table, Object obj) {
        if (obj == table) {
            return true;
        }
        if (obj instanceof Table) {
            return table.cellSet().equals(((Table) obj).cellSet());
        }
        return false;
    }

    public static <R, C, V> Table.Cell<R, C, V> immutableCell(@ParametricNullness R r2, @ParametricNullness C c2, @ParametricNullness V v2) {
        return new ImmutableCell(r2, c2, v2);
    }

    public static <R, C, V> Table<R, C, V> newCustomTable(Map<R, Map<C, V>> map, Supplier<? extends Map<C, V>> supplier) {
        Preconditions.checkArgument(map.isEmpty());
        Preconditions.checkNotNull(supplier);
        return new StandardTable(map, supplier);
    }

    @J2ktIncompatible
    public static <R, C, V> Table<R, C, V> synchronizedTable(Table<R, C, V> table) {
        return Synchronized.v(table, null);
    }

    @IgnoreJRERequirement
    public static <T, R, C, V, I extends Table<R, C, V>> Collector<T, ?, I> toTable(java.util.function.Function<? super T, ? extends R> function, java.util.function.Function<? super T, ? extends C> function2, java.util.function.Function<? super T, ? extends V> function3, java.util.function.Supplier<I> supplier) {
        return TableCollectors.l(function, function2, function3, supplier);
    }

    public static <R, C, V1, V2> Table<R, C, V2> transformValues(Table<R, C, V1> table, Function<? super V1, V2> function) {
        return new TransformedTable(table, function);
    }

    public static <R, C, V> Table<C, R, V> transpose(Table<R, C, V> table) {
        return table instanceof TransposeTable ? ((TransposeTable) table).f14356a : new TransposeTable(table);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <R, C, V> Table.Cell<C, R, V> transposeCell(Table.Cell<R, C, V> cell) {
        return immutableCell(cell.getColumnKey(), cell.getRowKey(), cell.getValue());
    }

    public static <R, C, V> RowSortedTable<R, C, V> unmodifiableRowSortedTable(RowSortedTable<R, ? extends C, ? extends V> rowSortedTable) {
        return new UnmodifiableRowSortedMap(rowSortedTable);
    }

    public static <R, C, V> Table<R, C, V> unmodifiableTable(Table<? extends R, ? extends C, ? extends V> table) {
        return new UnmodifiableTable(table);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <K, V> Function<Map<K, V>, Map<K, V>> unmodifiableWrapper() {
        return (Function<Map<K, V>, Map<K, V>>) UNMODIFIABLE_WRAPPER;
    }

    @IgnoreJRERequirement
    public static <T, R, C, V, I extends Table<R, C, V>> Collector<T, ?, I> toTable(java.util.function.Function<? super T, ? extends R> function, java.util.function.Function<? super T, ? extends C> function2, java.util.function.Function<? super T, ? extends V> function3, BinaryOperator<V> binaryOperator, java.util.function.Supplier<I> supplier) {
        return TableCollectors.k(function, function2, function3, binaryOperator, supplier);
    }
}
