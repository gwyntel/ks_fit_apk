package androidx.collection;

import java.util.Iterator;
import java.util.NoSuchElementException;

/* loaded from: classes.dex */
abstract class IndexBasedArrayIterator<T> implements Iterator<T> {
    private boolean mCanRemove;
    private int mIndex;
    private int mSize;

    IndexBasedArrayIterator(int i2) {
        this.mSize = i2;
    }

    protected abstract Object a(int i2);

    protected abstract void b(int i2);

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.mIndex < this.mSize;
    }

    @Override // java.util.Iterator
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        T t2 = (T) a(this.mIndex);
        this.mIndex++;
        this.mCanRemove = true;
        return t2;
    }

    @Override // java.util.Iterator
    public void remove() {
        if (!this.mCanRemove) {
            throw new IllegalStateException();
        }
        int i2 = this.mIndex - 1;
        this.mIndex = i2;
        b(i2);
        this.mSize--;
        this.mCanRemove = false;
    }
}
