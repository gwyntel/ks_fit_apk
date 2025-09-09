package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import java.util.ListIterator;

@GwtCompatible
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
abstract class TransformedListIterator<F, T> extends TransformedIterator<F, T> implements ListIterator<T> {
    TransformedListIterator(ListIterator listIterator) {
        super(listIterator);
    }

    private ListIterator<? extends F> backingIterator() {
        return (ListIterator) this.f14358a;
    }

    @Override // java.util.ListIterator
    public void add(@ParametricNullness T t2) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.ListIterator
    public final boolean hasPrevious() {
        return backingIterator().hasPrevious();
    }

    @Override // java.util.ListIterator
    public final int nextIndex() {
        return backingIterator().nextIndex();
    }

    @Override // java.util.ListIterator
    @ParametricNullness
    public final T previous() {
        return (T) a(backingIterator().previous());
    }

    @Override // java.util.ListIterator
    public final int previousIndex() {
        return backingIterator().previousIndex();
    }

    public void set(@ParametricNullness T t2) {
        throw new UnsupportedOperationException();
    }
}
