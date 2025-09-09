package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.J2ktIncompatible;
import com.google.common.collect.ForwardingQueue;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import javax.annotation.CheckForNull;

@J2ktIncompatible
@ElementTypesAreNonnullByDefault
@GwtIncompatible
/* loaded from: classes3.dex */
public abstract class ForwardingBlockingQueue<E> extends ForwardingQueue<E> implements BlockingQueue<E> {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.common.collect.ForwardingQueue, com.google.common.collect.ForwardingCollection, com.google.common.collect.ForwardingObject
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public abstract BlockingQueue delegate();

    @Override // java.util.concurrent.BlockingQueue
    @CanIgnoreReturnValue
    public int drainTo(Collection<? super E> collection, int i2) {
        return delegate().drainTo(collection, i2);
    }

    @Override // java.util.concurrent.BlockingQueue
    @CanIgnoreReturnValue
    public boolean offer(E e2, long j2, TimeUnit timeUnit) throws InterruptedException {
        return delegate().offer(e2, j2, timeUnit);
    }

    @Override // java.util.concurrent.BlockingQueue
    @CanIgnoreReturnValue
    @CheckForNull
    public E poll(long j2, TimeUnit timeUnit) throws InterruptedException {
        return (E) delegate().poll(j2, timeUnit);
    }

    @Override // java.util.concurrent.BlockingQueue
    public void put(E e2) throws InterruptedException {
        delegate().put(e2);
    }

    @Override // java.util.concurrent.BlockingQueue
    public int remainingCapacity() {
        return delegate().remainingCapacity();
    }

    @Override // java.util.concurrent.BlockingQueue
    @CanIgnoreReturnValue
    public E take() throws InterruptedException {
        return (E) delegate().take();
    }

    @Override // java.util.concurrent.BlockingQueue
    @CanIgnoreReturnValue
    public int drainTo(Collection<? super E> collection) {
        return delegate().drainTo(collection);
    }
}
