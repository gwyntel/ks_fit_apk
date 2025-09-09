package org.mozilla.javascript.typedarrays;

import java.util.ListIterator;
import java.util.NoSuchElementException;

/* loaded from: classes5.dex */
public class NativeTypedArrayIterator<T> implements ListIterator<T> {
    private int lastPosition = -1;
    private int position;
    private final NativeTypedArrayView<T> view;

    NativeTypedArrayIterator(NativeTypedArrayView<T> nativeTypedArrayView, int i2) {
        this.view = nativeTypedArrayView;
        this.position = i2;
    }

    @Override // java.util.ListIterator
    public void add(T t2) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public boolean hasNext() {
        return this.position < this.view.length;
    }

    @Override // java.util.ListIterator
    public boolean hasPrevious() {
        return this.position > 0;
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        T t2 = this.view.get(this.position);
        int i2 = this.position;
        this.lastPosition = i2;
        this.position = i2 + 1;
        return t2;
    }

    @Override // java.util.ListIterator
    public int nextIndex() {
        return this.position;
    }

    @Override // java.util.ListIterator
    public T previous() {
        if (!hasPrevious()) {
            throw new NoSuchElementException();
        }
        int i2 = this.position - 1;
        this.position = i2;
        this.lastPosition = i2;
        return this.view.get(i2);
    }

    @Override // java.util.ListIterator
    public int previousIndex() {
        return this.position - 1;
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.ListIterator
    public void set(T t2) {
        int i2 = this.lastPosition;
        if (i2 < 0) {
            throw new IllegalStateException();
        }
        this.view.js_set(i2, t2);
    }
}
