package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import java.util.NoSuchElementException;
import javax.annotation.CheckForNull;

@GwtCompatible
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public abstract class AbstractSequentialIterator<T> extends UnmodifiableIterator<T> {

    @CheckForNull
    private T nextOrNull;

    /* JADX WARN: Multi-variable type inference failed */
    protected AbstractSequentialIterator(Object obj) {
        this.nextOrNull = obj;
    }

    protected abstract Object a(Object obj);

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.nextOrNull != null;
    }

    @Override // java.util.Iterator
    public final T next() {
        T t2 = this.nextOrNull;
        if (t2 == null) {
            throw new NoSuchElementException();
        }
        this.nextOrNull = (T) a(t2);
        return t2;
    }
}
