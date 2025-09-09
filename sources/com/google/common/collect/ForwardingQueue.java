package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.NoSuchElementException;
import java.util.Queue;
import javax.annotation.CheckForNull;

@GwtCompatible
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public abstract class ForwardingQueue<E> extends ForwardingCollection<E> implements Queue<E> {
    protected ForwardingQueue() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.common.collect.ForwardingCollection, com.google.common.collect.ForwardingObject
    public abstract Queue delegate();

    @Override // java.util.Queue
    @ParametricNullness
    public E element() {
        return (E) delegate().element();
    }

    @CanIgnoreReturnValue
    public boolean offer(@ParametricNullness E e2) {
        return delegate().offer(e2);
    }

    @Override // java.util.Queue
    @CheckForNull
    public E peek() {
        return (E) delegate().peek();
    }

    @Override // java.util.Queue
    @CanIgnoreReturnValue
    @CheckForNull
    public E poll() {
        return (E) delegate().poll();
    }

    @Override // java.util.Queue
    @ParametricNullness
    @CanIgnoreReturnValue
    public E remove() {
        return (E) delegate().remove();
    }

    protected boolean standardOffer(@ParametricNullness E e2) {
        try {
            return add(e2);
        } catch (IllegalStateException unused) {
            return false;
        }
    }

    @CheckForNull
    protected E standardPeek() {
        try {
            return element();
        } catch (NoSuchElementException unused) {
            return null;
        }
    }

    @CheckForNull
    protected E standardPoll() {
        try {
            return remove();
        } catch (NoSuchElementException unused) {
            return null;
        }
    }
}
