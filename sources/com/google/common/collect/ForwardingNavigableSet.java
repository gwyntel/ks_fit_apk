package com.google.common.collect;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.collect.Sets;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.SortedSet;
import javax.annotation.CheckForNull;

@GwtIncompatible
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public abstract class ForwardingNavigableSet<E> extends ForwardingSortedSet<E> implements NavigableSet<E> {

    protected class StandardDescendingSet extends Sets.DescendingSet<E> {
        public StandardDescendingSet() {
            super(ForwardingNavigableSet.this);
        }
    }

    protected ForwardingNavigableSet() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.common.collect.ForwardingSortedSet, com.google.common.collect.ForwardingSet, com.google.common.collect.ForwardingCollection, com.google.common.collect.ForwardingObject
    /* renamed from: a */
    public abstract NavigableSet delegate();

    /* JADX WARN: Multi-variable type inference failed */
    protected SortedSet b(Object obj) {
        return headSet(obj, false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected SortedSet c(Object obj) {
        return tailSet(obj, true);
    }

    @Override // java.util.NavigableSet
    @CheckForNull
    public E ceiling(@ParametricNullness E e2) {
        return (E) delegate().ceiling(e2);
    }

    @Override // java.util.NavigableSet
    public Iterator<E> descendingIterator() {
        return delegate().descendingIterator();
    }

    @Override // java.util.NavigableSet
    public NavigableSet<E> descendingSet() {
        return delegate().descendingSet();
    }

    @Override // java.util.NavigableSet
    @CheckForNull
    public E floor(@ParametricNullness E e2) {
        return (E) delegate().floor(e2);
    }

    @Override // java.util.NavigableSet
    public NavigableSet<E> headSet(@ParametricNullness E e2, boolean z2) {
        return delegate().headSet(e2, z2);
    }

    @Override // java.util.NavigableSet
    @CheckForNull
    public E higher(@ParametricNullness E e2) {
        return (E) delegate().higher(e2);
    }

    @Override // java.util.NavigableSet
    @CheckForNull
    public E lower(@ParametricNullness E e2) {
        return (E) delegate().lower(e2);
    }

    @Override // java.util.NavigableSet
    @CheckForNull
    public E pollFirst() {
        return (E) delegate().pollFirst();
    }

    @Override // java.util.NavigableSet
    @CheckForNull
    public E pollLast() {
        return (E) delegate().pollLast();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.ForwardingSortedSet
    protected SortedSet standardSubSet(Object obj, Object obj2) {
        return subSet(obj, true, obj2, false);
    }

    @Override // java.util.NavigableSet
    public NavigableSet<E> subSet(@ParametricNullness E e2, boolean z2, @ParametricNullness E e3, boolean z3) {
        return delegate().subSet(e2, z2, e3, z3);
    }

    @Override // java.util.NavigableSet
    public NavigableSet<E> tailSet(@ParametricNullness E e2, boolean z2) {
        return delegate().tailSet(e2, z2);
    }
}
