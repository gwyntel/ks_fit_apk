package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import javax.annotation.CheckForNull;

@GwtCompatible
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public abstract class ForwardingList<E> extends ForwardingCollection<E> implements List<E> {
    protected ForwardingList() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.common.collect.ForwardingCollection, com.google.common.collect.ForwardingObject
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public abstract List delegate();

    public void add(int i2, @ParametricNullness E e2) {
        delegate().add(i2, e2);
    }

    @CanIgnoreReturnValue
    public boolean addAll(int i2, Collection<? extends E> collection) {
        return delegate().addAll(i2, collection);
    }

    @Override // java.util.Collection, java.util.List
    public boolean equals(@CheckForNull Object obj) {
        return obj == this || delegate().equals(obj);
    }

    @Override // java.util.List
    @ParametricNullness
    public E get(int i2) {
        return (E) delegate().get(i2);
    }

    @Override // java.util.Collection, java.util.List
    public int hashCode() {
        return delegate().hashCode();
    }

    @Override // java.util.List
    public int indexOf(@CheckForNull Object obj) {
        return delegate().indexOf(obj);
    }

    @Override // java.util.List
    public int lastIndexOf(@CheckForNull Object obj) {
        return delegate().lastIndexOf(obj);
    }

    @Override // java.util.List
    public ListIterator<E> listIterator() {
        return delegate().listIterator();
    }

    @Override // java.util.List
    @ParametricNullness
    @CanIgnoreReturnValue
    public E remove(int i2) {
        return (E) delegate().remove(i2);
    }

    @Override // java.util.List
    @ParametricNullness
    @CanIgnoreReturnValue
    public E set(int i2, @ParametricNullness E e2) {
        return (E) delegate().set(i2, e2);
    }

    @Override // java.util.List
    public List<E> subList(int i2, int i3) {
        return delegate().subList(i2, i3);
    }

    @Override // java.util.List
    public ListIterator<E> listIterator(int i2) {
        return delegate().listIterator(i2);
    }
}
