package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.math.IntMath;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import javax.annotation.CheckForNull;

@GwtCompatible
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public final class Collections2 {

    static class FilteredCollection<E> extends AbstractCollection<E> {

        /* renamed from: a, reason: collision with root package name */
        final Collection f13911a;

        /* renamed from: b, reason: collision with root package name */
        final Predicate f13912b;

        FilteredCollection(Collection collection, Predicate predicate) {
            this.f13911a = collection;
            this.f13912b = predicate;
        }

        FilteredCollection a(Predicate predicate) {
            return new FilteredCollection(this.f13911a, Predicates.and(this.f13912b, predicate));
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean add(@ParametricNullness E e2) {
            Preconditions.checkArgument(this.f13912b.apply(e2));
            return this.f13911a.add(e2);
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean addAll(Collection<? extends E> collection) {
            Iterator<? extends E> it = collection.iterator();
            while (it.hasNext()) {
                Preconditions.checkArgument(this.f13912b.apply(it.next()));
            }
            return this.f13911a.addAll(collection);
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public void clear() {
            Iterables.removeIf(this.f13911a, this.f13912b);
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean contains(@CheckForNull Object obj) {
            if (Collections2.d(this.f13911a, obj)) {
                return this.f13912b.apply(obj);
            }
            return false;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean containsAll(Collection<?> collection) {
            return Collections2.b(this, collection);
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean isEmpty() {
            return !Iterables.any(this.f13911a, this.f13912b);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public Iterator<E> iterator() {
            return Iterators.filter(this.f13911a.iterator(), this.f13912b);
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean remove(@CheckForNull Object obj) {
            return contains(obj) && this.f13911a.remove(obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean removeAll(Collection<?> collection) {
            Iterator<E> it = this.f13911a.iterator();
            boolean z2 = false;
            while (it.hasNext()) {
                E next = it.next();
                if (this.f13912b.apply(next) && collection.contains(next)) {
                    it.remove();
                    z2 = true;
                }
            }
            return z2;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean retainAll(Collection<?> collection) {
            Iterator<E> it = this.f13911a.iterator();
            boolean z2 = false;
            while (it.hasNext()) {
                E next = it.next();
                if (this.f13912b.apply(next) && !collection.contains(next)) {
                    it.remove();
                    z2 = true;
                }
            }
            return z2;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            Iterator<E> it = this.f13911a.iterator();
            int i2 = 0;
            while (it.hasNext()) {
                if (this.f13912b.apply(it.next())) {
                    i2++;
                }
            }
            return i2;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public Object[] toArray() {
            return Lists.newArrayList(iterator()).toArray();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public <T> T[] toArray(T[] tArr) {
            return (T[]) Lists.newArrayList(iterator()).toArray(tArr);
        }
    }

    private static final class OrderedPermutationCollection<E> extends AbstractCollection<List<E>> {

        /* renamed from: a, reason: collision with root package name */
        final ImmutableList f13913a;

        /* renamed from: b, reason: collision with root package name */
        final Comparator f13914b;

        /* renamed from: c, reason: collision with root package name */
        final int f13915c;

        OrderedPermutationCollection(Iterable iterable, Comparator comparator) {
            ImmutableList immutableListSortedCopyOf = ImmutableList.sortedCopyOf(comparator, iterable);
            this.f13913a = immutableListSortedCopyOf;
            this.f13914b = comparator;
            this.f13915c = calculateSize(immutableListSortedCopyOf, comparator);
        }

        private static <E> int calculateSize(List<E> list, Comparator<? super E> comparator) {
            int i2 = 1;
            int iSaturatedMultiply = 1;
            int i3 = 1;
            while (i2 < list.size()) {
                if (comparator.compare(list.get(i2 - 1), list.get(i2)) < 0) {
                    iSaturatedMultiply = IntMath.saturatedMultiply(iSaturatedMultiply, IntMath.binomial(i2, i3));
                    if (iSaturatedMultiply == Integer.MAX_VALUE) {
                        return Integer.MAX_VALUE;
                    }
                    i3 = 0;
                }
                i2++;
                i3++;
            }
            return IntMath.saturatedMultiply(iSaturatedMultiply, IntMath.binomial(i2, i3));
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean contains(@CheckForNull Object obj) {
            if (!(obj instanceof List)) {
                return false;
            }
            return Collections2.isPermutation(this.f13913a, (List) obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean isEmpty() {
            return false;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public Iterator<List<E>> iterator() {
            return new OrderedPermutationIterator(this.f13913a, this.f13914b);
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            return this.f13915c;
        }

        @Override // java.util.AbstractCollection
        public String toString() {
            return "orderedPermutationCollection(" + this.f13913a + ")";
        }
    }

    private static final class OrderedPermutationIterator<E> extends AbstractIterator<List<E>> {

        /* renamed from: a, reason: collision with root package name */
        List f13916a;

        /* renamed from: b, reason: collision with root package name */
        final Comparator f13917b;

        OrderedPermutationIterator(List list, Comparator comparator) {
            this.f13916a = Lists.newArrayList(list);
            this.f13917b = comparator;
        }

        void b() {
            int iD = d();
            if (iD == -1) {
                this.f13916a = null;
                return;
            }
            Objects.requireNonNull(this.f13916a);
            Collections.swap(this.f13916a, iD, e(iD));
            Collections.reverse(this.f13916a.subList(iD + 1, this.f13916a.size()));
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.common.collect.AbstractIterator
        /* renamed from: c, reason: merged with bridge method [inline-methods] */
        public List computeNext() {
            List list = this.f13916a;
            if (list == null) {
                return (List) a();
            }
            ImmutableList immutableListCopyOf = ImmutableList.copyOf((Collection) list);
            b();
            return immutableListCopyOf;
        }

        int d() {
            Objects.requireNonNull(this.f13916a);
            for (int size = this.f13916a.size() - 2; size >= 0; size--) {
                if (this.f13917b.compare(this.f13916a.get(size), this.f13916a.get(size + 1)) < 0) {
                    return size;
                }
            }
            return -1;
        }

        int e(int i2) {
            Objects.requireNonNull(this.f13916a);
            Object obj = this.f13916a.get(i2);
            for (int size = this.f13916a.size() - 1; size > i2; size--) {
                if (this.f13917b.compare(obj, this.f13916a.get(size)) < 0) {
                    return size;
                }
            }
            throw new AssertionError("this statement should be unreachable");
        }
    }

    private static final class PermutationCollection<E> extends AbstractCollection<List<E>> {

        /* renamed from: a, reason: collision with root package name */
        final ImmutableList f13918a;

        PermutationCollection(ImmutableList immutableList) {
            this.f13918a = immutableList;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean contains(@CheckForNull Object obj) {
            if (!(obj instanceof List)) {
                return false;
            }
            return Collections2.isPermutation(this.f13918a, (List) obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean isEmpty() {
            return false;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public Iterator<List<E>> iterator() {
            return new PermutationIterator(this.f13918a);
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            return IntMath.factorial(this.f13918a.size());
        }

        @Override // java.util.AbstractCollection
        public String toString() {
            return "permutations(" + this.f13918a + ")";
        }
    }

    private static class PermutationIterator<E> extends AbstractIterator<List<E>> {

        /* renamed from: a, reason: collision with root package name */
        final List f13919a;

        /* renamed from: b, reason: collision with root package name */
        final int[] f13920b;

        /* renamed from: c, reason: collision with root package name */
        final int[] f13921c;

        /* renamed from: d, reason: collision with root package name */
        int f13922d;

        PermutationIterator(List list) {
            this.f13919a = new ArrayList(list);
            int size = list.size();
            int[] iArr = new int[size];
            this.f13920b = iArr;
            int[] iArr2 = new int[size];
            this.f13921c = iArr2;
            Arrays.fill(iArr, 0);
            Arrays.fill(iArr2, 1);
            this.f13922d = Integer.MAX_VALUE;
        }

        void b() {
            int size = this.f13919a.size() - 1;
            this.f13922d = size;
            if (size == -1) {
                return;
            }
            int i2 = 0;
            while (true) {
                int[] iArr = this.f13920b;
                int i3 = this.f13922d;
                int i4 = iArr[i3];
                int i5 = this.f13921c[i3] + i4;
                if (i5 < 0) {
                    d();
                } else if (i5 != i3 + 1) {
                    Collections.swap(this.f13919a, (i3 - i4) + i2, (i3 - i5) + i2);
                    this.f13920b[this.f13922d] = i5;
                    return;
                } else {
                    if (i3 == 0) {
                        return;
                    }
                    i2++;
                    d();
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.common.collect.AbstractIterator
        /* renamed from: c, reason: merged with bridge method [inline-methods] */
        public List computeNext() {
            if (this.f13922d <= 0) {
                return (List) a();
            }
            ImmutableList immutableListCopyOf = ImmutableList.copyOf((Collection) this.f13919a);
            b();
            return immutableListCopyOf;
        }

        void d() {
            int[] iArr = this.f13921c;
            int i2 = this.f13922d;
            iArr[i2] = -iArr[i2];
            this.f13922d = i2 - 1;
        }
    }

    static class TransformedCollection<F, T> extends AbstractCollection<T> {

        /* renamed from: a, reason: collision with root package name */
        final Collection f13923a;

        /* renamed from: b, reason: collision with root package name */
        final Function f13924b;

        TransformedCollection(Collection collection, Function function) {
            this.f13923a = (Collection) Preconditions.checkNotNull(collection);
            this.f13924b = (Function) Preconditions.checkNotNull(function);
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public void clear() {
            this.f13923a.clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean isEmpty() {
            return this.f13923a.isEmpty();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public Iterator<T> iterator() {
            return Iterators.transform(this.f13923a.iterator(), this.f13924b);
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            return this.f13923a.size();
        }
    }

    private Collections2() {
    }

    static boolean b(Collection collection, Collection collection2) {
        Iterator it = collection2.iterator();
        while (it.hasNext()) {
            if (!collection.contains(it.next())) {
                return false;
            }
        }
        return true;
    }

    static StringBuilder c(int i2) {
        CollectPreconditions.b(i2, "size");
        return new StringBuilder((int) Math.min(i2 * 8, 1073741824L));
    }

    private static <E> ObjectCountHashMap<E> counts(Collection<E> collection) {
        ObjectCountHashMap<E> objectCountHashMap = new ObjectCountHashMap<>();
        for (E e2 : collection) {
            objectCountHashMap.put(e2, objectCountHashMap.get(e2) + 1);
        }
        return objectCountHashMap;
    }

    static boolean d(Collection collection, Object obj) {
        Preconditions.checkNotNull(collection);
        try {
            return collection.contains(obj);
        } catch (ClassCastException | NullPointerException unused) {
            return false;
        }
    }

    static boolean e(Collection collection, Object obj) {
        Preconditions.checkNotNull(collection);
        try {
            return collection.remove(obj);
        } catch (ClassCastException | NullPointerException unused) {
            return false;
        }
    }

    static String f(Collection collection) {
        StringBuilder sbC = c(collection.size());
        sbC.append('[');
        boolean z2 = true;
        for (Object obj : collection) {
            if (!z2) {
                sbC.append(", ");
            }
            if (obj == collection) {
                sbC.append("(this Collection)");
            } else {
                sbC.append(obj);
            }
            z2 = false;
        }
        sbC.append(']');
        return sbC.toString();
    }

    public static <E> Collection<E> filter(Collection<E> collection, Predicate<? super E> predicate) {
        return collection instanceof FilteredCollection ? ((FilteredCollection) collection).a(predicate) : new FilteredCollection((Collection) Preconditions.checkNotNull(collection), (Predicate) Preconditions.checkNotNull(predicate));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isPermutation(List<?> list, List<?> list2) {
        if (list.size() != list2.size()) {
            return false;
        }
        ObjectCountHashMap objectCountHashMapCounts = counts(list);
        ObjectCountHashMap objectCountHashMapCounts2 = counts(list2);
        if (list.size() != list2.size()) {
            return false;
        }
        for (int i2 = 0; i2 < list.size(); i2++) {
            if (objectCountHashMapCounts.g(i2) != objectCountHashMapCounts2.get(objectCountHashMapCounts.f(i2))) {
                return false;
            }
        }
        return true;
    }

    public static <E extends Comparable<? super E>> Collection<List<E>> orderedPermutations(Iterable<E> iterable) {
        return orderedPermutations(iterable, Ordering.natural());
    }

    public static <E> Collection<List<E>> permutations(Collection<E> collection) {
        return new PermutationCollection(ImmutableList.copyOf((Collection) collection));
    }

    public static <F, T> Collection<T> transform(Collection<F> collection, Function<? super F, T> function) {
        return new TransformedCollection(collection, function);
    }

    public static <E> Collection<List<E>> orderedPermutations(Iterable<E> iterable, Comparator<? super E> comparator) {
        return new OrderedPermutationCollection(iterable, comparator);
    }
}
