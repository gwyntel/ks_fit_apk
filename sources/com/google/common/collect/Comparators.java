package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

@GwtCompatible
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public final class Comparators {
    private Comparators() {
    }

    @IgnoreJRERequirement
    public static <T> Collector<T, ?, List<T>> greatest(int i2, Comparator<? super T> comparator) {
        return least(i2, comparator.reversed());
    }

    public static <T> boolean isInOrder(Iterable<? extends T> iterable, Comparator<T> comparator) {
        Preconditions.checkNotNull(comparator);
        Iterator<? extends T> it = iterable.iterator();
        if (!it.hasNext()) {
            return true;
        }
        T next = it.next();
        while (it.hasNext()) {
            T next2 = it.next();
            if (comparator.compare(next, next2) > 0) {
                return false;
            }
            next = next2;
        }
        return true;
    }

    public static <T> boolean isInStrictOrder(Iterable<? extends T> iterable, Comparator<T> comparator) {
        Preconditions.checkNotNull(comparator);
        Iterator<? extends T> it = iterable.iterator();
        if (!it.hasNext()) {
            return true;
        }
        T next = it.next();
        while (it.hasNext()) {
            T next2 = it.next();
            if (comparator.compare(next, next2) >= 0) {
                return false;
            }
            next = next2;
        }
        return true;
    }

    @IgnoreJRERequirement
    public static <T> Collector<T, ?, List<T>> least(final int i2, final Comparator<? super T> comparator) {
        CollectPreconditions.b(i2, "k");
        Preconditions.checkNotNull(comparator);
        return Collector.of(new Supplier() { // from class: com.google.common.collect.k2
            @Override // java.util.function.Supplier
            public final Object get() {
                return TopKSelector.least(i2, comparator);
            }
        }, new BiConsumer() { // from class: com.google.common.collect.l2
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                ((TopKSelector) obj).offer(obj2);
            }
        }, new BinaryOperator() { // from class: com.google.common.collect.m2
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return ((TopKSelector) obj).a((TopKSelector) obj2);
            }
        }, new Function() { // from class: com.google.common.collect.n2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((TopKSelector) obj).topK();
            }
        }, Collector.Characteristics.UNORDERED);
    }

    public static <T, S extends T> Comparator<Iterable<S>> lexicographical(Comparator<T> comparator) {
        return new LexicographicalOrdering((Comparator) Preconditions.checkNotNull(comparator));
    }

    public static <T extends Comparable<? super T>> T max(T t2, T t3) {
        return t2.compareTo(t3) >= 0 ? t2 : t3;
    }

    public static <T extends Comparable<? super T>> T min(T t2, T t3) {
        return t2.compareTo(t3) <= 0 ? t2 : t3;
    }

    @ParametricNullness
    public static <T> T max(@ParametricNullness T t2, @ParametricNullness T t3, Comparator<? super T> comparator) {
        return comparator.compare(t2, t3) >= 0 ? t2 : t3;
    }

    @ParametricNullness
    public static <T> T min(@ParametricNullness T t2, @ParametricNullness T t3, Comparator<? super T> comparator) {
        return comparator.compare(t2, t3) <= 0 ? t2 : t3;
    }
}
