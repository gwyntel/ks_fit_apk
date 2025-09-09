package com.google.common.collect;

import com.google.android.gms.fitness.data.WorkoutExercises;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;
import com.google.common.collect.TableCollectors;
import com.google.common.collect.Tables;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

@GwtCompatible
@ElementTypesAreNonnullByDefault
@IgnoreJRERequirement
/* loaded from: classes3.dex */
final class TableCollectors {

    /* JADX INFO: Access modifiers changed from: private */
    static final class ImmutableTableCollectorState<R, C, V> {

        /* renamed from: a, reason: collision with root package name */
        final List f14349a;

        /* renamed from: b, reason: collision with root package name */
        final Table f14350b;

        private ImmutableTableCollectorState() {
            this.f14349a = new ArrayList();
            this.f14350b = HashBasedTable.create();
        }

        ImmutableTableCollectorState a(ImmutableTableCollectorState immutableTableCollectorState, BinaryOperator binaryOperator) {
            for (MutableCell mutableCell : immutableTableCollectorState.f14349a) {
                b(mutableCell.getRowKey(), mutableCell.getColumnKey(), mutableCell.getValue(), binaryOperator);
            }
            return this;
        }

        void b(Object obj, Object obj2, Object obj3, BinaryOperator binaryOperator) {
            MutableCell mutableCell = (MutableCell) this.f14350b.get(obj, obj2);
            if (mutableCell != null) {
                mutableCell.a(obj3, binaryOperator);
                return;
            }
            MutableCell mutableCell2 = new MutableCell(obj, obj2, obj3);
            this.f14349a.add(mutableCell2);
            this.f14350b.put(obj, obj2, mutableCell2);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public ImmutableTable c() {
            return ImmutableTable.copyOf(this.f14349a);
        }
    }

    @IgnoreJRERequirement
    private static final class MutableCell<R, C, V> extends Tables.AbstractCell<R, C, V> {
        private final C column;
        private final R row;
        private V value;

        MutableCell(Object obj, Object obj2, Object obj3) {
            this.row = (R) Preconditions.checkNotNull(obj, WorkoutExercises.ROW);
            this.column = (C) Preconditions.checkNotNull(obj2, "column");
            this.value = (V) Preconditions.checkNotNull(obj3, "value");
        }

        void a(Object obj, BinaryOperator binaryOperator) {
            Preconditions.checkNotNull(obj, "value");
            this.value = (V) Preconditions.checkNotNull(binaryOperator.apply(this.value, obj), "mergeFunction.apply");
        }

        @Override // com.google.common.collect.Table.Cell
        public C getColumnKey() {
            return this.column;
        }

        @Override // com.google.common.collect.Table.Cell
        public R getRowKey() {
            return this.row;
        }

        @Override // com.google.common.collect.Table.Cell
        public V getValue() {
            return this.value;
        }
    }

    private TableCollectors() {
    }

    static Collector i(final Function function, final Function function2, final Function function3) {
        Preconditions.checkNotNull(function, "rowFunction");
        Preconditions.checkNotNull(function2, "columnFunction");
        Preconditions.checkNotNull(function3, "valueFunction");
        return Collector.of(new Supplier() { // from class: com.google.common.collect.e4
            @Override // java.util.function.Supplier
            public final Object get() {
                return new ImmutableTable.Builder();
            }
        }, new BiConsumer() { // from class: com.google.common.collect.f4
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                TableCollectors.lambda$toImmutableTable$0(function, function2, function3, (ImmutableTable.Builder) obj, obj2);
            }
        }, new BinaryOperator() { // from class: com.google.common.collect.v3
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return ((ImmutableTable.Builder) obj).a((ImmutableTable.Builder) obj2);
            }
        }, new Function() { // from class: com.google.common.collect.w3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((ImmutableTable.Builder) obj).build();
            }
        }, new Collector.Characteristics[0]);
    }

    static Collector j(final Function function, final Function function2, final Function function3, final BinaryOperator binaryOperator) {
        Preconditions.checkNotNull(function, "rowFunction");
        Preconditions.checkNotNull(function2, "columnFunction");
        Preconditions.checkNotNull(function3, "valueFunction");
        Preconditions.checkNotNull(binaryOperator, "mergeFunction");
        return Collector.of(new Supplier() { // from class: com.google.common.collect.a4
            @Override // java.util.function.Supplier
            public final Object get() {
                return TableCollectors.lambda$toImmutableTable$1();
            }
        }, new BiConsumer() { // from class: com.google.common.collect.b4
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                TableCollectors.lambda$toImmutableTable$2(function, function2, function3, binaryOperator, (TableCollectors.ImmutableTableCollectorState) obj, obj2);
            }
        }, new BinaryOperator() { // from class: com.google.common.collect.c4
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return TableCollectors.lambda$toImmutableTable$3(binaryOperator, (TableCollectors.ImmutableTableCollectorState) obj, (TableCollectors.ImmutableTableCollectorState) obj2);
            }
        }, new Function() { // from class: com.google.common.collect.d4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((TableCollectors.ImmutableTableCollectorState) obj).c();
            }
        }, new Collector.Characteristics[0]);
    }

    static Collector k(final Function function, final Function function2, final Function function3, final BinaryOperator binaryOperator, Supplier supplier) {
        Preconditions.checkNotNull(function);
        Preconditions.checkNotNull(function2);
        Preconditions.checkNotNull(function3);
        Preconditions.checkNotNull(binaryOperator);
        Preconditions.checkNotNull(supplier);
        return Collector.of(supplier, new BiConsumer() { // from class: com.google.common.collect.y3
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                TableCollectors.lambda$toTable$6(function, function2, function3, binaryOperator, (Table) obj, obj2);
            }
        }, new BinaryOperator() { // from class: com.google.common.collect.z3
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return TableCollectors.lambda$toTable$7(binaryOperator, (Table) obj, (Table) obj2);
            }
        }, new Collector.Characteristics[0]);
    }

    static Collector l(Function function, Function function2, Function function3, Supplier supplier) {
        return k(function, function2, function3, new BinaryOperator() { // from class: com.google.common.collect.x3
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return TableCollectors.lambda$toTable$5(obj, obj2);
            }
        }, supplier);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$toImmutableTable$0(Function function, Function function2, Function function3, ImmutableTable.Builder builder, Object obj) {
        builder.put(function.apply(obj), function2.apply(obj), function3.apply(obj));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ ImmutableTableCollectorState lambda$toImmutableTable$1() {
        return new ImmutableTableCollectorState();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$toImmutableTable$2(Function function, Function function2, Function function3, BinaryOperator binaryOperator, ImmutableTableCollectorState immutableTableCollectorState, Object obj) {
        immutableTableCollectorState.b(function.apply(obj), function2.apply(obj), function3.apply(obj), binaryOperator);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ ImmutableTableCollectorState lambda$toImmutableTable$3(BinaryOperator binaryOperator, ImmutableTableCollectorState immutableTableCollectorState, ImmutableTableCollectorState immutableTableCollectorState2) {
        return immutableTableCollectorState.a(immutableTableCollectorState2, binaryOperator);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object lambda$toTable$5(Object obj, Object obj2) {
        throw new IllegalStateException("Conflicting values " + obj + " and " + obj2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$toTable$6(Function function, Function function2, Function function3, BinaryOperator binaryOperator, Table table, Object obj) {
        mergeTables(table, function.apply(obj), function2.apply(obj), function3.apply(obj), binaryOperator);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Table lambda$toTable$7(BinaryOperator binaryOperator, Table table, Table table2) {
        for (Table.Cell cell : table2.cellSet()) {
            mergeTables(table, cell.getRowKey(), cell.getColumnKey(), cell.getValue(), binaryOperator);
        }
        return table;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static <R, C, V> void mergeTables(Table<R, C, V> table, @ParametricNullness R r2, @ParametricNullness C c2, V v2, BinaryOperator<V> binaryOperator) {
        Preconditions.checkNotNull(v2);
        Object obj = table.get(r2, c2);
        if (obj == null) {
            table.put(r2, c2, v2);
            return;
        }
        Object objApply = binaryOperator.apply(obj, v2);
        if (objApply == null) {
            table.remove(r2, c2);
        } else {
            table.put(r2, c2, objApply);
        }
    }
}
