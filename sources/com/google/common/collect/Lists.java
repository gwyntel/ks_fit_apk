package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.J2ktIncompatible;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.math.IntMath;
import com.google.common.primitives.Ints;
import java.io.Serializable;
import java.math.RoundingMode;
import java.util.AbstractList;
import java.util.AbstractSequentialList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.annotation.CheckForNull;

@GwtCompatible(emulated = true)
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public final class Lists {

    /* renamed from: com.google.common.collect.Lists$1, reason: invalid class name */
    class AnonymousClass1 extends RandomAccessListWrapper<Object> {

        @J2ktIncompatible
        private static final long serialVersionUID = 0;

        @Override // java.util.AbstractList, java.util.List
        public ListIterator<Object> listIterator(int i2) {
            return this.f14126a.listIterator(i2);
        }
    }

    /* renamed from: com.google.common.collect.Lists$2, reason: invalid class name */
    class AnonymousClass2 extends AbstractListWrapper<Object> {

        @J2ktIncompatible
        private static final long serialVersionUID = 0;

        @Override // java.util.AbstractList, java.util.List
        public ListIterator<Object> listIterator(int i2) {
            return this.f14126a.listIterator(i2);
        }
    }

    private static class AbstractListWrapper<E> extends AbstractList<E> {

        /* renamed from: a, reason: collision with root package name */
        final List f14126a;

        @Override // java.util.AbstractList, java.util.List
        public void add(int i2, @ParametricNullness E e2) {
            this.f14126a.add(i2, e2);
        }

        @Override // java.util.AbstractList, java.util.List
        public boolean addAll(int i2, Collection<? extends E> collection) {
            return this.f14126a.addAll(i2, collection);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public boolean contains(@CheckForNull Object obj) {
            return this.f14126a.contains(obj);
        }

        @Override // java.util.AbstractList, java.util.List
        @ParametricNullness
        public E get(int i2) {
            return (E) this.f14126a.get(i2);
        }

        @Override // java.util.AbstractList, java.util.List
        @ParametricNullness
        public E remove(int i2) {
            return (E) this.f14126a.remove(i2);
        }

        @Override // java.util.AbstractList, java.util.List
        @ParametricNullness
        public E set(int i2, @ParametricNullness E e2) {
            return (E) this.f14126a.set(i2, e2);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return this.f14126a.size();
        }
    }

    private static final class CharSequenceAsList extends AbstractList<Character> {
        private final CharSequence sequence;

        CharSequenceAsList(CharSequence charSequence) {
            this.sequence = charSequence;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return this.sequence.length();
        }

        @Override // java.util.AbstractList, java.util.List
        public Character get(int i2) {
            Preconditions.checkElementIndex(i2, size());
            return Character.valueOf(this.sequence.charAt(i2));
        }
    }

    private static class OnePlusArrayList<E> extends AbstractList<E> implements Serializable, RandomAccess {

        @J2ktIncompatible
        private static final long serialVersionUID = 0;

        @ParametricNullness
        final E first;
        final E[] rest;

        OnePlusArrayList(@ParametricNullness E e2, E[] eArr) {
            this.first = e2;
            this.rest = (E[]) ((Object[]) Preconditions.checkNotNull(eArr));
        }

        @Override // java.util.AbstractList, java.util.List
        @ParametricNullness
        public E get(int i2) {
            Preconditions.checkElementIndex(i2, size());
            return i2 == 0 ? this.first : this.rest[i2 - 1];
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return IntMath.saturatedAdd(this.rest.length, 1);
        }
    }

    private static class Partition<T> extends AbstractList<List<T>> {

        /* renamed from: a, reason: collision with root package name */
        final List f14127a;

        /* renamed from: b, reason: collision with root package name */
        final int f14128b;

        Partition(List list, int i2) {
            this.f14127a = list;
            this.f14128b = i2;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public boolean isEmpty() {
            return this.f14127a.isEmpty();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return IntMath.divide(this.f14127a.size(), this.f14128b, RoundingMode.CEILING);
        }

        @Override // java.util.AbstractList, java.util.List
        public List<T> get(int i2) {
            Preconditions.checkElementIndex(i2, size());
            int i3 = this.f14128b;
            int i4 = i2 * i3;
            return this.f14127a.subList(i4, Math.min(i3 + i4, this.f14127a.size()));
        }
    }

    private static class RandomAccessListWrapper<E> extends AbstractListWrapper<E> implements RandomAccess {
    }

    private static class RandomAccessPartition<T> extends Partition<T> implements RandomAccess {
        RandomAccessPartition(List list, int i2) {
            super(list, i2);
        }
    }

    private static class RandomAccessReverseList<T> extends ReverseList<T> implements RandomAccess {
        RandomAccessReverseList(List list) {
            super(list);
        }
    }

    private static class ReverseList<T> extends AbstractList<T> {
        private final List<T> forwardList;

        ReverseList(List list) {
            this.forwardList = (List) Preconditions.checkNotNull(list);
        }

        private int reverseIndex(int i2) {
            int size = size();
            Preconditions.checkElementIndex(i2, size);
            return (size - 1) - i2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int reversePosition(int i2) {
            int size = size();
            Preconditions.checkPositionIndex(i2, size);
            return size - i2;
        }

        @Override // java.util.AbstractList, java.util.List
        public void add(int i2, @ParametricNullness T t2) {
            this.forwardList.add(reversePosition(i2), t2);
        }

        List b() {
            return this.forwardList;
        }

        @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
        public void clear() {
            this.forwardList.clear();
        }

        @Override // java.util.AbstractList, java.util.List
        @ParametricNullness
        public T get(int i2) {
            return this.forwardList.get(reverseIndex(i2));
        }

        @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.List
        public Iterator<T> iterator() {
            return listIterator();
        }

        @Override // java.util.AbstractList, java.util.List
        public ListIterator<T> listIterator(int i2) {
            final ListIterator<T> listIterator = this.forwardList.listIterator(reversePosition(i2));
            return new ListIterator<T>(this) { // from class: com.google.common.collect.Lists.ReverseList.1

                /* renamed from: a, reason: collision with root package name */
                boolean f14129a;

                /* renamed from: c, reason: collision with root package name */
                final /* synthetic */ ReverseList f14131c;

                {
                    this.f14131c = this;
                }

                @Override // java.util.ListIterator
                public void add(@ParametricNullness T t2) {
                    listIterator.add(t2);
                    listIterator.previous();
                    this.f14129a = false;
                }

                @Override // java.util.ListIterator, java.util.Iterator
                public boolean hasNext() {
                    return listIterator.hasPrevious();
                }

                @Override // java.util.ListIterator
                public boolean hasPrevious() {
                    return listIterator.hasNext();
                }

                @Override // java.util.ListIterator, java.util.Iterator
                @ParametricNullness
                public T next() {
                    if (!hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.f14129a = true;
                    return (T) listIterator.previous();
                }

                @Override // java.util.ListIterator
                public int nextIndex() {
                    return this.f14131c.reversePosition(listIterator.nextIndex());
                }

                @Override // java.util.ListIterator
                @ParametricNullness
                public T previous() {
                    if (!hasPrevious()) {
                        throw new NoSuchElementException();
                    }
                    this.f14129a = true;
                    return (T) listIterator.next();
                }

                @Override // java.util.ListIterator
                public int previousIndex() {
                    return nextIndex() - 1;
                }

                @Override // java.util.ListIterator, java.util.Iterator
                public void remove() {
                    CollectPreconditions.e(this.f14129a);
                    listIterator.remove();
                    this.f14129a = false;
                }

                @Override // java.util.ListIterator
                public void set(@ParametricNullness T t2) {
                    Preconditions.checkState(this.f14129a);
                    listIterator.set(t2);
                }
            };
        }

        @Override // java.util.AbstractList, java.util.List
        @ParametricNullness
        public T remove(int i2) {
            return this.forwardList.remove(reverseIndex(i2));
        }

        @Override // java.util.AbstractList
        protected void removeRange(int i2, int i3) {
            subList(i2, i3).clear();
        }

        @Override // java.util.AbstractList, java.util.List
        @ParametricNullness
        public T set(int i2, @ParametricNullness T t2) {
            return this.forwardList.set(reverseIndex(i2), t2);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return this.forwardList.size();
        }

        @Override // java.util.AbstractList, java.util.List
        public List<T> subList(int i2, int i3) {
            Preconditions.checkPositionIndexes(i2, i3, size());
            return Lists.reverse(this.forwardList.subList(reversePosition(i3), reversePosition(i2)));
        }
    }

    private static final class StringAsImmutableList extends ImmutableList<Character> {
        private final String string;

        StringAsImmutableList(String str) {
            this.string = str;
        }

        @Override // com.google.common.collect.ImmutableList, java.util.List
        public int indexOf(@CheckForNull Object obj) {
            if (obj instanceof Character) {
                return this.string.indexOf(((Character) obj).charValue());
            }
            return -1;
        }

        @Override // com.google.common.collect.ImmutableCollection
        boolean isPartialView() {
            return false;
        }

        @Override // com.google.common.collect.ImmutableList, java.util.List
        public int lastIndexOf(@CheckForNull Object obj) {
            if (obj instanceof Character) {
                return this.string.lastIndexOf(((Character) obj).charValue());
            }
            return -1;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return this.string.length();
        }

        @Override // com.google.common.collect.ImmutableList, com.google.common.collect.ImmutableCollection
        @J2ktIncompatible
        @GwtIncompatible
        Object writeReplace() {
            return super.writeReplace();
        }

        @Override // java.util.List
        public Character get(int i2) {
            Preconditions.checkElementIndex(i2, size());
            return Character.valueOf(this.string.charAt(i2));
        }

        @Override // com.google.common.collect.ImmutableList, java.util.List
        public ImmutableList<Character> subList(int i2, int i3) {
            Preconditions.checkPositionIndexes(i2, i3, size());
            return Lists.charactersOf(this.string.substring(i2, i3));
        }
    }

    private static class TransformingRandomAccessList<F, T> extends AbstractList<T> implements RandomAccess, Serializable {
        private static final long serialVersionUID = 0;
        final List<F> fromList;
        final Function<? super F, ? extends T> function;

        TransformingRandomAccessList(List<F> list, Function<? super F, ? extends T> function) {
            this.fromList = (List) Preconditions.checkNotNull(list);
            this.function = (Function) Preconditions.checkNotNull(function);
        }

        @Override // java.util.AbstractList, java.util.List
        @ParametricNullness
        public T get(int i2) {
            return this.function.apply(this.fromList.get(i2));
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public boolean isEmpty() {
            return this.fromList.isEmpty();
        }

        @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.List
        public Iterator<T> iterator() {
            return listIterator();
        }

        @Override // java.util.AbstractList, java.util.List
        public ListIterator<T> listIterator(int i2) {
            return new TransformedListIterator<F, T>(this.fromList.listIterator(i2)) { // from class: com.google.common.collect.Lists.TransformingRandomAccessList.1
                @Override // com.google.common.collect.TransformedIterator
                Object a(Object obj) {
                    return TransformingRandomAccessList.this.function.apply(obj);
                }
            };
        }

        @Override // java.util.AbstractList, java.util.List
        public T remove(int i2) {
            return this.function.apply(this.fromList.remove(i2));
        }

        @Override // java.util.AbstractList
        protected void removeRange(int i2, int i3) {
            this.fromList.subList(i2, i3).clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return this.fromList.size();
        }
    }

    private static class TransformingSequentialList<F, T> extends AbstractSequentialList<T> implements Serializable {
        private static final long serialVersionUID = 0;
        final List<F> fromList;
        final Function<? super F, ? extends T> function;

        TransformingSequentialList(List<F> list, Function<? super F, ? extends T> function) {
            this.fromList = (List) Preconditions.checkNotNull(list);
            this.function = (Function) Preconditions.checkNotNull(function);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public boolean isEmpty() {
            return this.fromList.isEmpty();
        }

        @Override // java.util.AbstractSequentialList, java.util.AbstractList, java.util.List
        public ListIterator<T> listIterator(int i2) {
            return new TransformedListIterator<F, T>(this.fromList.listIterator(i2)) { // from class: com.google.common.collect.Lists.TransformingSequentialList.1
                @Override // com.google.common.collect.TransformedIterator
                Object a(Object obj) {
                    return TransformingSequentialList.this.function.apply(obj);
                }
            };
        }

        @Override // java.util.AbstractList
        protected void removeRange(int i2, int i3) {
            this.fromList.subList(i2, i3).clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return this.fromList.size();
        }
    }

    private static class TwoPlusArrayList<E> extends AbstractList<E> implements Serializable, RandomAccess {

        @J2ktIncompatible
        private static final long serialVersionUID = 0;

        @ParametricNullness
        final E first;
        final E[] rest;

        @ParametricNullness
        final E second;

        TwoPlusArrayList(@ParametricNullness E e2, @ParametricNullness E e3, E[] eArr) {
            this.first = e2;
            this.second = e3;
            this.rest = (E[]) ((Object[]) Preconditions.checkNotNull(eArr));
        }

        @Override // java.util.AbstractList, java.util.List
        @ParametricNullness
        public E get(int i2) {
            if (i2 == 0) {
                return this.first;
            }
            if (i2 == 1) {
                return this.second;
            }
            Preconditions.checkElementIndex(i2, size());
            return this.rest[i2 - 2];
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return IntMath.saturatedAdd(this.rest.length, 2);
        }
    }

    private Lists() {
    }

    static List a(Iterable iterable) {
        return (List) iterable;
    }

    public static <E> List<E> asList(@ParametricNullness E e2, E[] eArr) {
        return new OnePlusArrayList(e2, eArr);
    }

    static int b(int i2) {
        CollectPreconditions.b(i2, "arraySize");
        return Ints.saturatedCast(i2 + 5 + (i2 / 10));
    }

    static boolean c(List list, Object obj) {
        if (obj == Preconditions.checkNotNull(list)) {
            return true;
        }
        if (!(obj instanceof List)) {
            return false;
        }
        List list2 = (List) obj;
        int size = list.size();
        if (size != list2.size()) {
            return false;
        }
        if (!(list instanceof RandomAccess) || !(list2 instanceof RandomAccess)) {
            return Iterators.elementsEqual(list.iterator(), list2.iterator());
        }
        for (int i2 = 0; i2 < size; i2++) {
            if (!Objects.equal(list.get(i2), list2.get(i2))) {
                return false;
            }
        }
        return true;
    }

    public static <B> List<List<B>> cartesianProduct(List<? extends List<? extends B>> list) {
        return CartesianList.c(list);
    }

    public static ImmutableList<Character> charactersOf(String str) {
        return new StringAsImmutableList((String) Preconditions.checkNotNull(str));
    }

    static int d(List list, Object obj) {
        if (list instanceof RandomAccess) {
            return indexOfRandomAccess(list, obj);
        }
        ListIterator listIterator = list.listIterator();
        while (listIterator.hasNext()) {
            if (Objects.equal(obj, listIterator.next())) {
                return listIterator.previousIndex();
            }
        }
        return -1;
    }

    static int e(List list, Object obj) {
        if (list instanceof RandomAccess) {
            return lastIndexOfRandomAccess(list, obj);
        }
        ListIterator listIterator = list.listIterator(list.size());
        while (listIterator.hasPrevious()) {
            if (Objects.equal(obj, listIterator.previous())) {
                return listIterator.nextIndex();
            }
        }
        return -1;
    }

    private static int indexOfRandomAccess(List<?> list, @CheckForNull Object obj) {
        int size = list.size();
        int i2 = 0;
        if (obj == null) {
            while (i2 < size) {
                if (list.get(i2) == null) {
                    return i2;
                }
                i2++;
            }
            return -1;
        }
        while (i2 < size) {
            if (obj.equals(list.get(i2))) {
                return i2;
            }
            i2++;
        }
        return -1;
    }

    private static int lastIndexOfRandomAccess(List<?> list, @CheckForNull Object obj) {
        if (obj == null) {
            for (int size = list.size() - 1; size >= 0; size--) {
                if (list.get(size) == null) {
                    return size;
                }
            }
            return -1;
        }
        for (int size2 = list.size() - 1; size2 >= 0; size2--) {
            if (obj.equals(list.get(size2))) {
                return size2;
            }
        }
        return -1;
    }

    @GwtCompatible(serializable = true)
    public static <E> ArrayList<E> newArrayList() {
        return new ArrayList<>();
    }

    @GwtCompatible(serializable = true)
    public static <E> ArrayList<E> newArrayListWithCapacity(int i2) {
        CollectPreconditions.b(i2, "initialArraySize");
        return new ArrayList<>(i2);
    }

    @GwtCompatible(serializable = true)
    public static <E> ArrayList<E> newArrayListWithExpectedSize(int i2) {
        return new ArrayList<>(b(i2));
    }

    @J2ktIncompatible
    @GwtIncompatible
    public static <E> CopyOnWriteArrayList<E> newCopyOnWriteArrayList() {
        return new CopyOnWriteArrayList<>();
    }

    @GwtCompatible(serializable = true)
    public static <E> LinkedList<E> newLinkedList() {
        return new LinkedList<>();
    }

    public static <T> List<List<T>> partition(List<T> list, int i2) {
        Preconditions.checkNotNull(list);
        Preconditions.checkArgument(i2 > 0);
        return list instanceof RandomAccess ? new RandomAccessPartition(list, i2) : new Partition(list, i2);
    }

    public static <T> List<T> reverse(List<T> list) {
        return list instanceof ImmutableList ? ((ImmutableList) list).reverse() : list instanceof ReverseList ? ((ReverseList) list).b() : list instanceof RandomAccess ? new RandomAccessReverseList(list) : new ReverseList(list);
    }

    public static <F, T> List<T> transform(List<F> list, Function<? super F, ? extends T> function) {
        return list instanceof RandomAccess ? new TransformingRandomAccessList(list, function) : new TransformingSequentialList(list, function);
    }

    public static <E> List<E> asList(@ParametricNullness E e2, @ParametricNullness E e3, E[] eArr) {
        return new TwoPlusArrayList(e2, e3, eArr);
    }

    @SafeVarargs
    public static <B> List<List<B>> cartesianProduct(List<? extends B>... listArr) {
        return cartesianProduct(Arrays.asList(listArr));
    }

    public static List<Character> charactersOf(CharSequence charSequence) {
        return new CharSequenceAsList((CharSequence) Preconditions.checkNotNull(charSequence));
    }

    @SafeVarargs
    @GwtCompatible(serializable = true)
    public static <E> ArrayList<E> newArrayList(E... eArr) {
        Preconditions.checkNotNull(eArr);
        ArrayList<E> arrayList = new ArrayList<>(b(eArr.length));
        Collections.addAll(arrayList, eArr);
        return arrayList;
    }

    @J2ktIncompatible
    @GwtIncompatible
    public static <E> CopyOnWriteArrayList<E> newCopyOnWriteArrayList(Iterable<? extends E> iterable) {
        return new CopyOnWriteArrayList<>(iterable instanceof Collection ? (Collection) iterable : newArrayList(iterable));
    }

    @GwtCompatible(serializable = true)
    public static <E> LinkedList<E> newLinkedList(Iterable<? extends E> iterable) {
        LinkedList<E> linkedListNewLinkedList = newLinkedList();
        Iterables.addAll(linkedListNewLinkedList, iterable);
        return linkedListNewLinkedList;
    }

    @GwtCompatible(serializable = true)
    public static <E> ArrayList<E> newArrayList(Iterable<? extends E> iterable) {
        Preconditions.checkNotNull(iterable);
        if (iterable instanceof Collection) {
            return new ArrayList<>((Collection) iterable);
        }
        return newArrayList(iterable.iterator());
    }

    @GwtCompatible(serializable = true)
    public static <E> ArrayList<E> newArrayList(Iterator<? extends E> it) {
        ArrayList<E> arrayListNewArrayList = newArrayList();
        Iterators.addAll(arrayListNewArrayList, it);
        return arrayListNewArrayList;
    }
}
