package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.CollectCollectors;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableRangeMap;
import com.google.common.collect.ImmutableRangeSet;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.MultimapBuilder;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.CheckForNull;

@GwtCompatible
@ElementTypesAreNonnullByDefault
@IgnoreJRERequirement
/* loaded from: classes3.dex */
final class CollectCollectors {
    private static final Collector<Object, ?, ImmutableList<Object>> TO_IMMUTABLE_LIST = Collector.of(new Supplier() { // from class: com.google.common.collect.c0
        @Override // java.util.function.Supplier
        public final Object get() {
            return ImmutableList.builder();
        }
    }, new BiConsumer() { // from class: com.google.common.collect.h0
        @Override // java.util.function.BiConsumer
        public final void accept(Object obj, Object obj2) {
            ((ImmutableList.Builder) obj).add((ImmutableList.Builder) obj2);
        }
    }, new BinaryOperator() { // from class: com.google.common.collect.i0
        @Override // java.util.function.BiFunction
        public final Object apply(Object obj, Object obj2) {
            return ((ImmutableList.Builder) obj).d((ImmutableList.Builder) obj2);
        }
    }, new Function() { // from class: com.google.common.collect.j0
        @Override // java.util.function.Function
        public final Object apply(Object obj) {
            return ((ImmutableList.Builder) obj).build();
        }
    }, new Collector.Characteristics[0]);
    private static final Collector<Object, ?, ImmutableSet<Object>> TO_IMMUTABLE_SET = Collector.of(new Supplier() { // from class: com.google.common.collect.k0
        @Override // java.util.function.Supplier
        public final Object get() {
            return ImmutableSet.builder();
        }
    }, new BiConsumer() { // from class: com.google.common.collect.l0
        @Override // java.util.function.BiConsumer
        public final void accept(Object obj, Object obj2) {
            ((ImmutableSet.Builder) obj).add((ImmutableSet.Builder) obj2);
        }
    }, new BinaryOperator() { // from class: com.google.common.collect.m0
        @Override // java.util.function.BiFunction
        public final Object apply(Object obj, Object obj2) {
            return ((ImmutableSet.Builder) obj).c((ImmutableSet.Builder) obj2);
        }
    }, new Function() { // from class: com.google.common.collect.n0
        @Override // java.util.function.Function
        public final Object apply(Object obj) {
            return ((ImmutableSet.Builder) obj).build();
        }
    }, new Collector.Characteristics[0]);

    @GwtIncompatible
    private static final Collector<Range<Comparable<?>>, ?, ImmutableRangeSet<Comparable<?>>> TO_IMMUTABLE_RANGE_SET = Collector.of(new Supplier() { // from class: com.google.common.collect.d0
        @Override // java.util.function.Supplier
        public final Object get() {
            return ImmutableRangeSet.builder();
        }
    }, new BiConsumer() { // from class: com.google.common.collect.e0
        @Override // java.util.function.BiConsumer
        public final void accept(Object obj, Object obj2) {
            ((ImmutableRangeSet.Builder) obj).add((Range) obj2);
        }
    }, new BinaryOperator() { // from class: com.google.common.collect.f0
        @Override // java.util.function.BiFunction
        public final Object apply(Object obj, Object obj2) {
            return ((ImmutableRangeSet.Builder) obj).a((ImmutableRangeSet.Builder) obj2);
        }
    }, new Function() { // from class: com.google.common.collect.g0
        @Override // java.util.function.Function
        public final Object apply(Object obj) {
            return ((ImmutableRangeSet.Builder) obj).build();
        }
    }, new Collector.Characteristics[0]);

    /* JADX INFO: Access modifiers changed from: private */
    @IgnoreJRERequirement
    static class EnumMapAccumulator<K extends Enum<K>, V> {

        @CheckForNull
        private EnumMap<K, V> map = null;
        private final BinaryOperator<V> mergeFunction;

        EnumMapAccumulator(BinaryOperator binaryOperator) {
            this.mergeFunction = binaryOperator;
        }

        EnumMapAccumulator a(EnumMapAccumulator enumMapAccumulator) {
            if (this.map == null) {
                return enumMapAccumulator;
            }
            EnumMap<K, V> enumMap = enumMapAccumulator.map;
            if (enumMap == null) {
                return this;
            }
            enumMap.forEach(new BiConsumer() { // from class: com.google.common.collect.i2
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    this.f14440a.b((Enum) obj, obj2);
                }
            });
            return this;
        }

        void b(Enum r3, Object obj) {
            EnumMap<K, V> enumMap = this.map;
            if (enumMap == null) {
                this.map = new EnumMap<>(Collections.singletonMap(r3, obj));
            } else {
                enumMap.merge(r3, obj, this.mergeFunction);
            }
        }

        ImmutableMap c() {
            EnumMap<K, V> enumMap = this.map;
            return enumMap == null ? ImmutableMap.of() : ImmutableEnumMap.asImmutable(enumMap);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @IgnoreJRERequirement
    static final class EnumSetAccumulator<E extends Enum<E>> {

        /* renamed from: a, reason: collision with root package name */
        static final Collector f13910a = CollectCollectors.toImmutableEnumSetGeneric();

        @CheckForNull
        private EnumSet<E> set;

        private EnumSetAccumulator() {
        }

        void a(Enum r2) {
            EnumSet<E> enumSet = this.set;
            if (enumSet == null) {
                this.set = EnumSet.of(r2);
            } else {
                enumSet.add(r2);
            }
        }

        EnumSetAccumulator b(EnumSetAccumulator enumSetAccumulator) {
            EnumSet<E> enumSet = this.set;
            if (enumSet == null) {
                return enumSetAccumulator;
            }
            EnumSet<E> enumSet2 = enumSetAccumulator.set;
            if (enumSet2 == null) {
                return this;
            }
            enumSet.addAll(enumSet2);
            return this;
        }

        ImmutableSet c() {
            EnumSet<E> enumSet = this.set;
            if (enumSet == null) {
                return ImmutableSet.of();
            }
            ImmutableSet immutableSetAsImmutable = ImmutableEnumSet.asImmutable(enumSet);
            this.set = null;
            return immutableSetAsImmutable;
        }
    }

    private CollectCollectors() {
    }

    static Collector D(final Function function, final Function function2) {
        Preconditions.checkNotNull(function);
        Preconditions.checkNotNull(function2);
        Function function3 = new Function() { // from class: com.google.common.collect.q0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return CollectCollectors.lambda$flatteningToImmutableListMultimap$19(function, obj);
            }
        };
        Function function4 = new Function() { // from class: com.google.common.collect.r0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return CollectCollectors.lambda$flatteningToImmutableListMultimap$20(function2, obj);
            }
        };
        final MultimapBuilder.ListMultimapBuilder<Object, Object> listMultimapBuilderArrayListValues = MultimapBuilder.linkedHashKeys().arrayListValues();
        Objects.requireNonNull(listMultimapBuilderArrayListValues);
        return Collectors.collectingAndThen(F(function3, function4, new Supplier() { // from class: com.google.common.collect.s0
            @Override // java.util.function.Supplier
            public final Object get() {
                return listMultimapBuilderArrayListValues.build();
            }
        }), new Function() { // from class: com.google.common.collect.t0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ImmutableListMultimap.copyOf((Multimap) obj);
            }
        });
    }

    static Collector E(final Function function, final Function function2) {
        Preconditions.checkNotNull(function);
        Preconditions.checkNotNull(function2);
        Function function3 = new Function() { // from class: com.google.common.collect.y0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return CollectCollectors.lambda$flatteningToImmutableSetMultimap$22(function, obj);
            }
        };
        Function function4 = new Function() { // from class: com.google.common.collect.z0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return CollectCollectors.lambda$flatteningToImmutableSetMultimap$23(function2, obj);
            }
        };
        final MultimapBuilder.SetMultimapBuilder<Object, Object> setMultimapBuilderLinkedHashSetValues = MultimapBuilder.linkedHashKeys().linkedHashSetValues();
        Objects.requireNonNull(setMultimapBuilderLinkedHashSetValues);
        return Collectors.collectingAndThen(F(function3, function4, new Supplier() { // from class: com.google.common.collect.a1
            @Override // java.util.function.Supplier
            public final Object get() {
                return setMultimapBuilderLinkedHashSetValues.build();
            }
        }), new Function() { // from class: com.google.common.collect.b1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ImmutableSetMultimap.copyOf((Multimap) obj);
            }
        });
    }

    static Collector F(final Function function, final Function function2, Supplier supplier) {
        Preconditions.checkNotNull(function);
        Preconditions.checkNotNull(function2);
        Preconditions.checkNotNull(supplier);
        return Collector.of(supplier, new BiConsumer() { // from class: com.google.common.collect.o0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                CollectCollectors.lambda$flatteningToMultimap$26(function, function2, (Multimap) obj, obj2);
            }
        }, new BinaryOperator() { // from class: com.google.common.collect.p0
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return CollectCollectors.lambda$flatteningToMultimap$27((Multimap) obj, (Multimap) obj2);
            }
        }, new Collector.Characteristics[0]);
    }

    static Collector G(final Function function, final Function function2) {
        Preconditions.checkNotNull(function);
        Preconditions.checkNotNull(function2);
        return Collector.of(new Supplier() { // from class: com.google.common.collect.u
            @Override // java.util.function.Supplier
            public final Object get() {
                return new ImmutableBiMap.Builder();
            }
        }, new BiConsumer() { // from class: com.google.common.collect.v
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                CollectCollectors.lambda$toImmutableBiMap$11(function, function2, (ImmutableBiMap.Builder) obj, obj2);
            }
        }, new BinaryOperator() { // from class: com.google.common.collect.w
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return ((ImmutableBiMap.Builder) obj).a((ImmutableBiMap.Builder) obj2);
            }
        }, new Function() { // from class: com.google.common.collect.x
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((ImmutableBiMap.Builder) obj).buildOrThrow();
            }
        }, new Collector.Characteristics[0]);
    }

    static Collector H(final Function function, final Function function2) {
        Preconditions.checkNotNull(function);
        Preconditions.checkNotNull(function2);
        return Collector.of(new Supplier() { // from class: com.google.common.collect.f1
            @Override // java.util.function.Supplier
            public final Object get() {
                return CollectCollectors.lambda$toImmutableEnumMap$13();
            }
        }, new BiConsumer() { // from class: com.google.common.collect.g1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                CollectCollectors.lambda$toImmutableEnumMap$14(function, function2, (CollectCollectors.EnumMapAccumulator) obj, obj2);
            }
        }, new h1(), new i1(), Collector.Characteristics.UNORDERED);
    }

    static Collector I(final Function function, final Function function2, final BinaryOperator binaryOperator) {
        Preconditions.checkNotNull(function);
        Preconditions.checkNotNull(function2);
        Preconditions.checkNotNull(binaryOperator);
        return Collector.of(new Supplier() { // from class: com.google.common.collect.e2
            @Override // java.util.function.Supplier
            public final Object get() {
                return CollectCollectors.lambda$toImmutableEnumMap$15(binaryOperator);
            }
        }, new BiConsumer() { // from class: com.google.common.collect.f2
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                CollectCollectors.lambda$toImmutableEnumMap$16(function, function2, (CollectCollectors.EnumMapAccumulator) obj, obj2);
            }
        }, new h1(), new i1(), new Collector.Characteristics[0]);
    }

    static Collector J() {
        return EnumSetAccumulator.f13910a;
    }

    static Collector K() {
        return TO_IMMUTABLE_LIST;
    }

    static Collector L(final Function function, final Function function2) {
        Preconditions.checkNotNull(function, "keyFunction");
        Preconditions.checkNotNull(function2, "valueFunction");
        return Collector.of(new Supplier() { // from class: com.google.common.collect.r1
            @Override // java.util.function.Supplier
            public final Object get() {
                return ImmutableListMultimap.builder();
            }
        }, new BiConsumer() { // from class: com.google.common.collect.s1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                CollectCollectors.lambda$toImmutableListMultimap$18(function, function2, (ImmutableListMultimap.Builder) obj, obj2);
            }
        }, new BinaryOperator() { // from class: com.google.common.collect.t1
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return ((ImmutableListMultimap.Builder) obj).e((ImmutableListMultimap.Builder) obj2);
            }
        }, new Function() { // from class: com.google.common.collect.u1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((ImmutableListMultimap.Builder) obj).build();
            }
        }, new Collector.Characteristics[0]);
    }

    static Collector M(final Function function, final Function function2) {
        Preconditions.checkNotNull(function);
        Preconditions.checkNotNull(function2);
        return Collector.of(new Supplier() { // from class: com.google.common.collect.j1
            @Override // java.util.function.Supplier
            public final Object get() {
                return new ImmutableMap.Builder();
            }
        }, new BiConsumer() { // from class: com.google.common.collect.k1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                CollectCollectors.lambda$toImmutableMap$7(function, function2, (ImmutableMap.Builder) obj, obj2);
            }
        }, new BinaryOperator() { // from class: com.google.common.collect.l1
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return ((ImmutableMap.Builder) obj).a((ImmutableMap.Builder) obj2);
            }
        }, new Function() { // from class: com.google.common.collect.m1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((ImmutableMap.Builder) obj).buildOrThrow();
            }
        }, new Collector.Characteristics[0]);
    }

    static Collector N(Function function, Function function2, BinaryOperator binaryOperator) {
        Preconditions.checkNotNull(function);
        Preconditions.checkNotNull(function2);
        Preconditions.checkNotNull(binaryOperator);
        return Collectors.collectingAndThen(Collectors.toMap(function, function2, binaryOperator, new Supplier() { // from class: com.google.common.collect.z1
            @Override // java.util.function.Supplier
            public final Object get() {
                return new LinkedHashMap();
            }
        }), new Function() { // from class: com.google.common.collect.a2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ImmutableMap.copyOf((Map) obj);
            }
        });
    }

    static Collector O(final Function function, final ToIntFunction toIntFunction) {
        Preconditions.checkNotNull(function);
        Preconditions.checkNotNull(toIntFunction);
        return Collector.of(new Supplier() { // from class: com.google.common.collect.u0
            @Override // java.util.function.Supplier
            public final Object get() {
                return LinkedHashMultiset.create();
            }
        }, new BiConsumer() { // from class: com.google.common.collect.v0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                CollectCollectors.lambda$toImmutableMultiset$2(function, toIntFunction, (Multiset) obj, obj2);
            }
        }, new BinaryOperator() { // from class: com.google.common.collect.w0
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return CollectCollectors.lambda$toImmutableMultiset$3((Multiset) obj, (Multiset) obj2);
            }
        }, new Function() { // from class: com.google.common.collect.x0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return CollectCollectors.lambda$toImmutableMultiset$4((Multiset) obj);
            }
        }, new Collector.Characteristics[0]);
    }

    static Collector P(final Function function, final Function function2) {
        Preconditions.checkNotNull(function);
        Preconditions.checkNotNull(function2);
        return Collector.of(new Supplier() { // from class: com.google.common.collect.m
            @Override // java.util.function.Supplier
            public final Object get() {
                return ImmutableRangeMap.builder();
            }
        }, new BiConsumer() { // from class: com.google.common.collect.n
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                CollectCollectors.lambda$toImmutableRangeMap$17(function, function2, (ImmutableRangeMap.Builder) obj, obj2);
            }
        }, new BinaryOperator() { // from class: com.google.common.collect.o
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return ((ImmutableRangeMap.Builder) obj).a((ImmutableRangeMap.Builder) obj2);
            }
        }, new Function() { // from class: com.google.common.collect.p
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((ImmutableRangeMap.Builder) obj).build();
            }
        }, new Collector.Characteristics[0]);
    }

    static Collector Q() {
        return TO_IMMUTABLE_RANGE_SET;
    }

    static Collector R() {
        return TO_IMMUTABLE_SET;
    }

    static Collector S(final Function function, final Function function2) {
        Preconditions.checkNotNull(function, "keyFunction");
        Preconditions.checkNotNull(function2, "valueFunction");
        return Collector.of(new Supplier() { // from class: com.google.common.collect.v1
            @Override // java.util.function.Supplier
            public final Object get() {
                return ImmutableSetMultimap.builder();
            }
        }, new BiConsumer() { // from class: com.google.common.collect.w1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                CollectCollectors.lambda$toImmutableSetMultimap$21(function, function2, (ImmutableSetMultimap.Builder) obj, obj2);
            }
        }, new BinaryOperator() { // from class: com.google.common.collect.x1
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return ((ImmutableSetMultimap.Builder) obj).e((ImmutableSetMultimap.Builder) obj2);
            }
        }, new Function() { // from class: com.google.common.collect.y1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((ImmutableSetMultimap.Builder) obj).build();
            }
        }, new Collector.Characteristics[0]);
    }

    static Collector T(final Comparator comparator, final Function function, final Function function2) {
        Preconditions.checkNotNull(comparator);
        Preconditions.checkNotNull(function);
        Preconditions.checkNotNull(function2);
        return Collector.of(new Supplier() { // from class: com.google.common.collect.q
            @Override // java.util.function.Supplier
            public final Object get() {
                return CollectCollectors.lambda$toImmutableSortedMap$8(comparator);
            }
        }, new BiConsumer() { // from class: com.google.common.collect.r
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                CollectCollectors.lambda$toImmutableSortedMap$9(function, function2, (ImmutableSortedMap.Builder) obj, obj2);
            }
        }, new BinaryOperator() { // from class: com.google.common.collect.s
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return ((ImmutableSortedMap.Builder) obj).c((ImmutableSortedMap.Builder) obj2);
            }
        }, new Function() { // from class: com.google.common.collect.t
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((ImmutableSortedMap.Builder) obj).buildOrThrow();
            }
        }, Collector.Characteristics.UNORDERED);
    }

    static Collector U(final Comparator comparator, Function function, Function function2, BinaryOperator binaryOperator) {
        Preconditions.checkNotNull(comparator);
        Preconditions.checkNotNull(function);
        Preconditions.checkNotNull(function2);
        Preconditions.checkNotNull(binaryOperator);
        return Collectors.collectingAndThen(Collectors.toMap(function, function2, binaryOperator, new Supplier() { // from class: com.google.common.collect.c1
            @Override // java.util.function.Supplier
            public final Object get() {
                return CollectCollectors.lambda$toImmutableSortedMap$10(comparator);
            }
        }), new Function() { // from class: com.google.common.collect.d1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ImmutableSortedMap.copyOfSorted((TreeMap) obj);
            }
        });
    }

    static Collector V(final Comparator comparator) {
        Preconditions.checkNotNull(comparator);
        return Collector.of(new Supplier() { // from class: com.google.common.collect.n1
            @Override // java.util.function.Supplier
            public final Object get() {
                return CollectCollectors.lambda$toImmutableSortedSet$0(comparator);
            }
        }, new BiConsumer() { // from class: com.google.common.collect.o1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                ((ImmutableSortedSet.Builder) obj).add((ImmutableSortedSet.Builder) obj2);
            }
        }, new BinaryOperator() { // from class: com.google.common.collect.p1
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return ((ImmutableSortedSet.Builder) obj).c((ImmutableSortedSet.Builder) obj2);
            }
        }, new Function() { // from class: com.google.common.collect.q1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((ImmutableSortedSet.Builder) obj).build();
            }
        }, new Collector.Characteristics[0]);
    }

    static Collector W(final Function function, final Function function2, Supplier supplier) {
        Preconditions.checkNotNull(function);
        Preconditions.checkNotNull(function2);
        Preconditions.checkNotNull(supplier);
        return Collector.of(supplier, new BiConsumer() { // from class: com.google.common.collect.g2
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                CollectCollectors.lambda$toMultimap$24(function, function2, (Multimap) obj, obj2);
            }
        }, new BinaryOperator() { // from class: com.google.common.collect.h2
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return CollectCollectors.lambda$toMultimap$25((Multimap) obj, (Multimap) obj2);
            }
        }, new Collector.Characteristics[0]);
    }

    static Collector X(final Function function, final ToIntFunction toIntFunction, Supplier supplier) {
        Preconditions.checkNotNull(function);
        Preconditions.checkNotNull(toIntFunction);
        Preconditions.checkNotNull(supplier);
        return Collector.of(supplier, new BiConsumer() { // from class: com.google.common.collect.c2
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                CollectCollectors.lambda$toMultiset$5(function, toIntFunction, (Multiset) obj, obj2);
            }
        }, new BinaryOperator() { // from class: com.google.common.collect.d2
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return CollectCollectors.lambda$toMultiset$6((Multiset) obj, (Multiset) obj2);
            }
        }, new Collector.Characteristics[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object lambda$flatteningToImmutableListMultimap$19(Function function, Object obj) {
        return Preconditions.checkNotNull(function.apply(obj));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Stream lambda$flatteningToImmutableListMultimap$20(Function function, Object obj) {
        return h.a(function.apply(obj)).peek(new e1());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object lambda$flatteningToImmutableSetMultimap$22(Function function, Object obj) {
        return Preconditions.checkNotNull(function.apply(obj));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Stream lambda$flatteningToImmutableSetMultimap$23(Function function, Object obj) {
        return h.a(function.apply(obj)).peek(new e1());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$flatteningToMultimap$26(Function function, Function function2, Multimap multimap, Object obj) {
        final Collection collection = multimap.get(function.apply(obj));
        Stream streamA = h.a(function2.apply(obj));
        Objects.requireNonNull(collection);
        streamA.forEachOrdered(new Consumer() { // from class: com.google.common.collect.b2
            @Override // java.util.function.Consumer
            public final void accept(Object obj2) {
                collection.add(obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Multimap lambda$flatteningToMultimap$27(Multimap multimap, Multimap multimap2) {
        multimap.putAll(multimap2);
        return multimap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$toImmutableBiMap$11(Function function, Function function2, ImmutableBiMap.Builder builder, Object obj) {
        builder.put((ImmutableBiMap.Builder) function.apply(obj), function2.apply(obj));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object lambda$toImmutableEnumMap$12(Object obj, Object obj2) {
        throw new IllegalArgumentException("Multiple values for key: " + obj + ", " + obj2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ EnumMapAccumulator lambda$toImmutableEnumMap$13() {
        return new EnumMapAccumulator(new BinaryOperator() { // from class: com.google.common.collect.l
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return CollectCollectors.lambda$toImmutableEnumMap$12(obj, obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$toImmutableEnumMap$14(Function function, Function function2, EnumMapAccumulator enumMapAccumulator, Object obj) {
        enumMapAccumulator.b((Enum) Preconditions.checkNotNull((Enum) function.apply(obj), "Null key for input %s", obj), Preconditions.checkNotNull(function2.apply(obj), "Null value for input %s", obj));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ EnumMapAccumulator lambda$toImmutableEnumMap$15(BinaryOperator binaryOperator) {
        return new EnumMapAccumulator(binaryOperator);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$toImmutableEnumMap$16(Function function, Function function2, EnumMapAccumulator enumMapAccumulator, Object obj) {
        enumMapAccumulator.b((Enum) Preconditions.checkNotNull((Enum) function.apply(obj), "Null key for input %s", obj), Preconditions.checkNotNull(function2.apply(obj), "Null value for input %s", obj));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ EnumSetAccumulator lambda$toImmutableEnumSetGeneric$1() {
        return new EnumSetAccumulator();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$toImmutableListMultimap$18(Function function, Function function2, ImmutableListMultimap.Builder builder, Object obj) {
        builder.put((ImmutableListMultimap.Builder) function.apply(obj), function2.apply(obj));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$toImmutableMap$7(Function function, Function function2, ImmutableMap.Builder builder, Object obj) {
        builder.put(function.apply(obj), function2.apply(obj));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$toImmutableMultiset$2(Function function, ToIntFunction toIntFunction, Multiset multiset, Object obj) {
        multiset.add(Preconditions.checkNotNull(function.apply(obj)), toIntFunction.applyAsInt(obj));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Multiset lambda$toImmutableMultiset$3(Multiset multiset, Multiset multiset2) {
        multiset.addAll(multiset2);
        return multiset;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ ImmutableMultiset lambda$toImmutableMultiset$4(Multiset multiset) {
        return ImmutableMultiset.copyFromEntries(multiset.entrySet());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$toImmutableRangeMap$17(Function function, Function function2, ImmutableRangeMap.Builder builder, Object obj) {
        builder.put((Range) function.apply(obj), function2.apply(obj));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$toImmutableSetMultimap$21(Function function, Function function2, ImmutableSetMultimap.Builder builder, Object obj) {
        builder.put((ImmutableSetMultimap.Builder) function.apply(obj), function2.apply(obj));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ TreeMap lambda$toImmutableSortedMap$10(Comparator comparator) {
        return new TreeMap(comparator);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ ImmutableSortedMap.Builder lambda$toImmutableSortedMap$8(Comparator comparator) {
        return new ImmutableSortedMap.Builder(comparator);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$toImmutableSortedMap$9(Function function, Function function2, ImmutableSortedMap.Builder builder, Object obj) {
        builder.put((ImmutableSortedMap.Builder) function.apply(obj), function2.apply(obj));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ ImmutableSortedSet.Builder lambda$toImmutableSortedSet$0(Comparator comparator) {
        return new ImmutableSortedSet.Builder(comparator);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$toMultimap$24(Function function, Function function2, Multimap multimap, Object obj) {
        multimap.put(function.apply(obj), function2.apply(obj));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Multimap lambda$toMultimap$25(Multimap multimap, Multimap multimap2) {
        multimap.putAll(multimap2);
        return multimap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$toMultiset$5(Function function, ToIntFunction toIntFunction, Multiset multiset, Object obj) {
        multiset.add(function.apply(obj), toIntFunction.applyAsInt(obj));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Multiset lambda$toMultiset$6(Multiset multiset, Multiset multiset2) {
        multiset.addAll(multiset2);
        return multiset;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <E extends Enum<E>> Collector<E, EnumSetAccumulator<E>, ImmutableSet<E>> toImmutableEnumSetGeneric() {
        return Collector.of(new Supplier() { // from class: com.google.common.collect.y
            @Override // java.util.function.Supplier
            public final Object get() {
                return CollectCollectors.lambda$toImmutableEnumSetGeneric$1();
            }
        }, new BiConsumer() { // from class: com.google.common.collect.z
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                ((CollectCollectors.EnumSetAccumulator) obj).a((Enum) obj2);
            }
        }, new BinaryOperator() { // from class: com.google.common.collect.a0
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return ((CollectCollectors.EnumSetAccumulator) obj).b((CollectCollectors.EnumSetAccumulator) obj2);
            }
        }, new Function() { // from class: com.google.common.collect.b0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((CollectCollectors.EnumSetAccumulator) obj).c();
            }
        }, Collector.Characteristics.UNORDERED);
    }
}
