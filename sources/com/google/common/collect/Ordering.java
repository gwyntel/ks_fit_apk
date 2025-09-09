package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.J2ktIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.CheckForNull;

@GwtCompatible
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public abstract class Ordering<T> implements Comparator<T> {
    static final int LEFT_IS_GREATER = 1;
    static final int RIGHT_IS_GREATER = -1;

    @VisibleForTesting
    @J2ktIncompatible
    static class ArbitraryOrdering extends Ordering<Object> {
        private final AtomicInteger counter = new AtomicInteger(0);
        private final ConcurrentMap<Object, Integer> uids = Platform.j(new MapMaker()).makeMap();

        ArbitraryOrdering() {
        }

        private Integer getUid(Object obj) {
            Integer num = this.uids.get(obj);
            if (num != null) {
                return num;
            }
            Integer numValueOf = Integer.valueOf(this.counter.getAndIncrement());
            Integer numPutIfAbsent = this.uids.putIfAbsent(obj, numValueOf);
            return numPutIfAbsent != null ? numPutIfAbsent : numValueOf;
        }

        int a(Object obj) {
            return System.identityHashCode(obj);
        }

        @Override // com.google.common.collect.Ordering, java.util.Comparator
        public int compare(@CheckForNull Object obj, @CheckForNull Object obj2) {
            if (obj == obj2) {
                return 0;
            }
            if (obj == null) {
                return -1;
            }
            if (obj2 == null) {
                return 1;
            }
            int iA = a(obj);
            int iA2 = a(obj2);
            if (iA != iA2) {
                return iA < iA2 ? -1 : 1;
            }
            int iCompareTo = getUid(obj).compareTo(getUid(obj2));
            if (iCompareTo != 0) {
                return iCompareTo;
            }
            throw new AssertionError();
        }

        public String toString() {
            return "Ordering.arbitrary()";
        }
    }

    @J2ktIncompatible
    private static class ArbitraryOrderingHolder {

        /* renamed from: a, reason: collision with root package name */
        static final Ordering f14272a = new ArbitraryOrdering();

        private ArbitraryOrderingHolder() {
        }
    }

    static class IncomparableValueException extends ClassCastException {
        private static final long serialVersionUID = 0;
        final Object value;

        IncomparableValueException(Object obj) {
            super("Cannot compare value: " + obj);
            this.value = obj;
        }
    }

    protected Ordering() {
    }

    @GwtCompatible(serializable = true)
    public static Ordering<Object> allEqual() {
        return AllEqualOrdering.INSTANCE;
    }

    @J2ktIncompatible
    public static Ordering<Object> arbitrary() {
        return ArbitraryOrderingHolder.f14272a;
    }

    @GwtCompatible(serializable = true)
    public static <T> Ordering<T> explicit(List<T> list) {
        return new ExplicitOrdering(list);
    }

    @GwtCompatible(serializable = true)
    public static <T> Ordering<T> from(Comparator<T> comparator) {
        return comparator instanceof Ordering ? (Ordering) comparator : new ComparatorOrdering(comparator);
    }

    @GwtCompatible(serializable = true)
    public static <C extends Comparable> Ordering<C> natural() {
        return NaturalOrdering.INSTANCE;
    }

    @GwtCompatible(serializable = true)
    public static Ordering<Object> usingToString() {
        return UsingToStringOrdering.INSTANCE;
    }

    @Deprecated
    public int binarySearch(List<? extends T> list, @ParametricNullness T t2) {
        return Collections.binarySearch(list, t2, this);
    }

    @Override // java.util.Comparator
    public abstract int compare(@ParametricNullness T t2, @ParametricNullness T t3);

    @GwtCompatible(serializable = true)
    public <U extends T> Ordering<U> compound(Comparator<? super U> comparator) {
        return new CompoundOrdering(this, (Comparator) Preconditions.checkNotNull(comparator));
    }

    public <E extends T> List<E> greatestOf(Iterable<E> iterable, int i2) {
        return reverse().leastOf(iterable, i2);
    }

    public <E extends T> ImmutableList<E> immutableSortedCopy(Iterable<E> iterable) {
        return ImmutableList.sortedCopyOf(this, iterable);
    }

    public boolean isOrdered(Iterable<? extends T> iterable) {
        Iterator<? extends T> it = iterable.iterator();
        if (!it.hasNext()) {
            return true;
        }
        T next = it.next();
        while (it.hasNext()) {
            T next2 = it.next();
            if (compare(next, next2) > 0) {
                return false;
            }
            next = next2;
        }
        return true;
    }

    public boolean isStrictlyOrdered(Iterable<? extends T> iterable) {
        Iterator<? extends T> it = iterable.iterator();
        if (!it.hasNext()) {
            return true;
        }
        T next = it.next();
        while (it.hasNext()) {
            T next2 = it.next();
            if (compare(next, next2) >= 0) {
                return false;
            }
            next = next2;
        }
        return true;
    }

    public <E extends T> List<E> leastOf(Iterable<E> iterable, int i2) {
        if (iterable instanceof Collection) {
            Collection collection = (Collection) iterable;
            if (collection.size() <= i2 * 2) {
                Object[] array = collection.toArray();
                Arrays.sort(array, this);
                if (array.length > i2) {
                    array = Arrays.copyOf(array, i2);
                }
                return Collections.unmodifiableList(Arrays.asList(array));
            }
        }
        return leastOf(iterable.iterator(), i2);
    }

    @GwtCompatible(serializable = true)
    public <S extends T> Ordering<Iterable<S>> lexicographical() {
        return new LexicographicalOrdering(this);
    }

    @ParametricNullness
    public <E extends T> E max(Iterator<E> it) {
        E next = it.next();
        while (it.hasNext()) {
            next = (E) max(next, it.next());
        }
        return next;
    }

    @ParametricNullness
    public <E extends T> E min(Iterator<E> it) {
        E next = it.next();
        while (it.hasNext()) {
            next = (E) min(next, it.next());
        }
        return next;
    }

    @GwtCompatible(serializable = true)
    public <S extends T> Ordering<S> nullsFirst() {
        return new NullsFirstOrdering(this);
    }

    @GwtCompatible(serializable = true)
    public <S extends T> Ordering<S> nullsLast() {
        return new NullsLastOrdering(this);
    }

    <T2 extends T> Ordering<Map.Entry<T2, ?>> onKeys() {
        return (Ordering<Map.Entry<T2, ?>>) onResultOf(Maps.q());
    }

    @GwtCompatible(serializable = true)
    public <F> Ordering<F> onResultOf(Function<F, ? extends T> function) {
        return new ByFunctionOrdering(function, this);
    }

    @GwtCompatible(serializable = true)
    public <S extends T> Ordering<S> reverse() {
        return new ReverseOrdering(this);
    }

    public <E extends T> List<E> sortedCopy(Iterable<E> iterable) {
        Object[] objArrB = Iterables.b(iterable);
        Arrays.sort(objArrB, this);
        return Lists.newArrayList(Arrays.asList(objArrB));
    }

    @GwtCompatible(serializable = true)
    public static <T> Ordering<T> compound(Iterable<? extends Comparator<? super T>> iterable) {
        return new CompoundOrdering(iterable);
    }

    @GwtCompatible(serializable = true)
    public static <T> Ordering<T> explicit(T t2, T... tArr) {
        return explicit(Lists.asList(t2, tArr));
    }

    public <E extends T> List<E> greatestOf(Iterator<E> it, int i2) {
        return reverse().leastOf(it, i2);
    }

    @GwtCompatible(serializable = true)
    @Deprecated
    public static <T> Ordering<T> from(Ordering<T> ordering) {
        return (Ordering) Preconditions.checkNotNull(ordering);
    }

    @ParametricNullness
    public <E extends T> E max(Iterable<E> iterable) {
        return (E) max(iterable.iterator());
    }

    @ParametricNullness
    public <E extends T> E min(Iterable<E> iterable) {
        return (E) min(iterable.iterator());
    }

    /* JADX WARN: Multi-variable type inference failed */
    @ParametricNullness
    public <E extends T> E max(@ParametricNullness E e2, @ParametricNullness E e3) {
        return compare(e2, e3) >= 0 ? e2 : e3;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @ParametricNullness
    public <E extends T> E min(@ParametricNullness E e2, @ParametricNullness E e3) {
        return compare(e2, e3) <= 0 ? e2 : e3;
    }

    @ParametricNullness
    public <E extends T> E max(@ParametricNullness E e2, @ParametricNullness E e3, @ParametricNullness E e4, E... eArr) {
        E e5 = (E) max(max(e2, e3), e4);
        for (E e6 : eArr) {
            e5 = (E) max(e5, e6);
        }
        return e5;
    }

    @ParametricNullness
    public <E extends T> E min(@ParametricNullness E e2, @ParametricNullness E e3, @ParametricNullness E e4, E... eArr) {
        E e5 = (E) min(min(e2, e3), e4);
        for (E e6 : eArr) {
            e5 = (E) min(e5, e6);
        }
        return e5;
    }

    public <E extends T> List<E> leastOf(Iterator<E> it, int i2) {
        Preconditions.checkNotNull(it);
        CollectPreconditions.b(i2, "k");
        if (i2 == 0 || !it.hasNext()) {
            return Collections.emptyList();
        }
        if (i2 >= 1073741823) {
            ArrayList arrayListNewArrayList = Lists.newArrayList(it);
            Collections.sort(arrayListNewArrayList, this);
            if (arrayListNewArrayList.size() > i2) {
                arrayListNewArrayList.subList(i2, arrayListNewArrayList.size()).clear();
            }
            arrayListNewArrayList.trimToSize();
            return Collections.unmodifiableList(arrayListNewArrayList);
        }
        TopKSelector topKSelectorLeast = TopKSelector.least(i2, this);
        topKSelectorLeast.offerAll(it);
        return topKSelectorLeast.topK();
    }
}
