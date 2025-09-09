package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.util.Iterator;

@GwtCompatible
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
abstract class TransformedIterator<F, T> implements Iterator<T> {

    /* renamed from: a, reason: collision with root package name */
    final Iterator f14358a;

    TransformedIterator(Iterator it) {
        this.f14358a = (Iterator) Preconditions.checkNotNull(it);
    }

    abstract Object a(Object obj);

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.f14358a.hasNext();
    }

    @Override // java.util.Iterator
    @ParametricNullness
    public final T next() {
        return (T) a(this.f14358a.next());
    }

    @Override // java.util.Iterator
    public final void remove() {
        this.f14358a.remove();
    }
}
