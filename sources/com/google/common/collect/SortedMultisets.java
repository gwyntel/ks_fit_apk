package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.j2objc.annotations.Weak;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.NoSuchElementException;
import java.util.SortedSet;
import javax.annotation.CheckForNull;

@GwtCompatible(emulated = true)
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
final class SortedMultisets {

    static class ElementSet<E> extends Multisets.ElementSet<E> implements SortedSet<E> {

        @Weak
        private final SortedMultiset<E> multiset;

        ElementSet(SortedMultiset sortedMultiset) {
            this.multiset = sortedMultiset;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.collect.Multisets.ElementSet
        /* renamed from: b, reason: merged with bridge method [inline-methods] */
        public final SortedMultiset a() {
            return this.multiset;
        }

        @Override // java.util.SortedSet
        public Comparator<? super E> comparator() {
            return a().comparator();
        }

        @Override // java.util.SortedSet
        @ParametricNullness
        public E first() {
            return (E) SortedMultisets.getElementOrThrow(a().firstEntry());
        }

        @Override // java.util.SortedSet
        public SortedSet<E> headSet(@ParametricNullness E e2) {
            return a().headMultiset(e2, BoundType.OPEN).elementSet();
        }

        @Override // com.google.common.collect.Multisets.ElementSet, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<E> iterator() {
            return Multisets.c(a().entrySet().iterator());
        }

        @Override // java.util.SortedSet
        @ParametricNullness
        public E last() {
            return (E) SortedMultisets.getElementOrThrow(a().lastEntry());
        }

        @Override // java.util.SortedSet
        public SortedSet<E> subSet(@ParametricNullness E e2, @ParametricNullness E e3) {
            return a().subMultiset(e2, BoundType.CLOSED, e3, BoundType.OPEN).elementSet();
        }

        @Override // java.util.SortedSet
        public SortedSet<E> tailSet(@ParametricNullness E e2) {
            return a().tailMultiset(e2, BoundType.CLOSED).elementSet();
        }
    }

    @GwtIncompatible
    static class NavigableElementSet<E> extends ElementSet<E> implements NavigableSet<E> {
        NavigableElementSet(SortedMultiset sortedMultiset) {
            super(sortedMultiset);
        }

        @Override // java.util.NavigableSet
        @CheckForNull
        public E ceiling(@ParametricNullness E e2) {
            return (E) SortedMultisets.getElementOrNull(a().tailMultiset(e2, BoundType.CLOSED).firstEntry());
        }

        @Override // java.util.NavigableSet
        public Iterator<E> descendingIterator() {
            return descendingSet().iterator();
        }

        @Override // java.util.NavigableSet
        public NavigableSet<E> descendingSet() {
            return new NavigableElementSet(a().descendingMultiset());
        }

        @Override // java.util.NavigableSet
        @CheckForNull
        public E floor(@ParametricNullness E e2) {
            return (E) SortedMultisets.getElementOrNull(a().headMultiset(e2, BoundType.CLOSED).lastEntry());
        }

        @Override // java.util.NavigableSet
        public NavigableSet<E> headSet(@ParametricNullness E e2, boolean z2) {
            return new NavigableElementSet(a().headMultiset(e2, BoundType.forBoolean(z2)));
        }

        @Override // java.util.NavigableSet
        @CheckForNull
        public E higher(@ParametricNullness E e2) {
            return (E) SortedMultisets.getElementOrNull(a().tailMultiset(e2, BoundType.OPEN).firstEntry());
        }

        @Override // java.util.NavigableSet
        @CheckForNull
        public E lower(@ParametricNullness E e2) {
            return (E) SortedMultisets.getElementOrNull(a().headMultiset(e2, BoundType.OPEN).lastEntry());
        }

        @Override // java.util.NavigableSet
        @CheckForNull
        public E pollFirst() {
            return (E) SortedMultisets.getElementOrNull(a().pollFirstEntry());
        }

        @Override // java.util.NavigableSet
        @CheckForNull
        public E pollLast() {
            return (E) SortedMultisets.getElementOrNull(a().pollLastEntry());
        }

        @Override // java.util.NavigableSet
        public NavigableSet<E> subSet(@ParametricNullness E e2, boolean z2, @ParametricNullness E e3, boolean z3) {
            return new NavigableElementSet(a().subMultiset(e2, BoundType.forBoolean(z2), e3, BoundType.forBoolean(z3)));
        }

        @Override // java.util.NavigableSet
        public NavigableSet<E> tailSet(@ParametricNullness E e2, boolean z2) {
            return new NavigableElementSet(a().tailMultiset(e2, BoundType.forBoolean(z2)));
        }
    }

    private SortedMultisets() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    @CheckForNull
    public static <E> E getElementOrNull(@CheckForNull Multiset.Entry<E> entry) {
        if (entry == null) {
            return null;
        }
        return entry.getElement();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <E> E getElementOrThrow(@CheckForNull Multiset.Entry<E> entry) {
        if (entry != null) {
            return entry.getElement();
        }
        throw new NoSuchElementException();
    }
}
