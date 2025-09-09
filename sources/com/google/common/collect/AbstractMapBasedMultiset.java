package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.J2ktIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.Multiset;
import com.google.common.primitives.Ints;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import javax.annotation.CheckForNull;

@GwtCompatible(emulated = true)
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
abstract class AbstractMapBasedMultiset<E> extends AbstractMultiset<E> implements Serializable {

    @J2ktIncompatible
    @GwtIncompatible
    private static final long serialVersionUID = 0;
    transient ObjectCountHashMap<E> backingMap;
    transient long size;

    abstract class Itr<T> implements Iterator<T> {

        /* renamed from: a, reason: collision with root package name */
        int f13881a;

        /* renamed from: b, reason: collision with root package name */
        int f13882b = -1;

        /* renamed from: c, reason: collision with root package name */
        int f13883c;

        Itr() {
            this.f13881a = AbstractMapBasedMultiset.this.backingMap.d();
            this.f13883c = AbstractMapBasedMultiset.this.backingMap.f14266d;
        }

        private void checkForConcurrentModification() {
            if (AbstractMapBasedMultiset.this.backingMap.f14266d != this.f13883c) {
                throw new ConcurrentModificationException();
            }
        }

        abstract Object a(int i2);

        @Override // java.util.Iterator
        public boolean hasNext() {
            checkForConcurrentModification();
            return this.f13881a >= 0;
        }

        @Override // java.util.Iterator
        @ParametricNullness
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T t2 = (T) a(this.f13881a);
            int i2 = this.f13881a;
            this.f13882b = i2;
            this.f13881a = AbstractMapBasedMultiset.this.backingMap.l(i2);
            return t2;
        }

        @Override // java.util.Iterator
        public void remove() {
            checkForConcurrentModification();
            CollectPreconditions.e(this.f13882b != -1);
            AbstractMapBasedMultiset.this.size -= r0.backingMap.n(this.f13882b);
            this.f13881a = AbstractMapBasedMultiset.this.backingMap.m(this.f13881a, this.f13882b);
            this.f13882b = -1;
            this.f13883c = AbstractMapBasedMultiset.this.backingMap.f14266d;
        }
    }

    AbstractMapBasedMultiset(int i2) {
        this.backingMap = newBackingMap(i2);
    }

    @J2ktIncompatible
    @GwtIncompatible
    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        int iH = Serialization.h(objectInputStream);
        this.backingMap = newBackingMap(3);
        Serialization.g(this, objectInputStream, iH);
    }

    @J2ktIncompatible
    @GwtIncompatible
    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        Serialization.k(this, objectOutputStream);
    }

    @Override // com.google.common.collect.AbstractMultiset, com.google.common.collect.Multiset
    @CanIgnoreReturnValue
    public final int add(@ParametricNullness E e2, int i2) {
        if (i2 == 0) {
            return count(e2);
        }
        Preconditions.checkArgument(i2 > 0, "occurrences cannot be negative: %s", i2);
        int iH = this.backingMap.h(e2);
        if (iH == -1) {
            this.backingMap.put(e2, i2);
            this.size += i2;
            return 0;
        }
        int iG = this.backingMap.g(iH);
        long j2 = i2;
        long j3 = iG + j2;
        Preconditions.checkArgument(j3 <= 2147483647L, "too many occurrences: %s", j3);
        this.backingMap.p(iH, (int) j3);
        this.size += j2;
        return iG;
    }

    void addTo(Multiset<? super E> multiset) {
        Preconditions.checkNotNull(multiset);
        int iD = this.backingMap.d();
        while (iD >= 0) {
            multiset.add((Object) this.backingMap.f(iD), this.backingMap.g(iD));
            iD = this.backingMap.l(iD);
        }
    }

    @Override // com.google.common.collect.AbstractMultiset, java.util.AbstractCollection, java.util.Collection
    public final void clear() {
        this.backingMap.clear();
        this.size = 0L;
    }

    @Override // com.google.common.collect.Multiset
    public final int count(@CheckForNull Object obj) {
        return this.backingMap.get(obj);
    }

    @Override // com.google.common.collect.AbstractMultiset
    final int distinctElements() {
        return this.backingMap.q();
    }

    @Override // com.google.common.collect.AbstractMultiset
    final Iterator<E> elementIterator() {
        return new AbstractMapBasedMultiset<E>.Itr<E>() { // from class: com.google.common.collect.AbstractMapBasedMultiset.1
            @Override // com.google.common.collect.AbstractMapBasedMultiset.Itr
            Object a(int i2) {
                return AbstractMapBasedMultiset.this.backingMap.f(i2);
            }
        };
    }

    @Override // com.google.common.collect.AbstractMultiset
    final Iterator<Multiset.Entry<E>> entryIterator() {
        return new AbstractMapBasedMultiset<E>.Itr<Multiset.Entry<E>>() { // from class: com.google.common.collect.AbstractMapBasedMultiset.2
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // com.google.common.collect.AbstractMapBasedMultiset.Itr
            /* renamed from: b, reason: merged with bridge method [inline-methods] */
            public Multiset.Entry a(int i2) {
                return AbstractMapBasedMultiset.this.backingMap.e(i2);
            }
        };
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.google.common.collect.Multiset
    public final Iterator<E> iterator() {
        return Multisets.f(this);
    }

    abstract ObjectCountHashMap<E> newBackingMap(int i2);

    @Override // com.google.common.collect.AbstractMultiset, com.google.common.collect.Multiset
    @CanIgnoreReturnValue
    public final int remove(@CheckForNull Object obj, int i2) {
        if (i2 == 0) {
            return count(obj);
        }
        Preconditions.checkArgument(i2 > 0, "occurrences cannot be negative: %s", i2);
        int iH = this.backingMap.h(obj);
        if (iH == -1) {
            return 0;
        }
        int iG = this.backingMap.g(iH);
        if (iG > i2) {
            this.backingMap.p(iH, iG - i2);
        } else {
            this.backingMap.n(iH);
            i2 = iG;
        }
        this.size -= i2;
        return iG;
    }

    @Override // com.google.common.collect.AbstractMultiset, com.google.common.collect.Multiset
    @CanIgnoreReturnValue
    public final int setCount(@ParametricNullness E e2, int i2) {
        CollectPreconditions.b(i2, "count");
        ObjectCountHashMap<E> objectCountHashMap = this.backingMap;
        int iRemove = i2 == 0 ? objectCountHashMap.remove(e2) : objectCountHashMap.put(e2, i2);
        this.size += i2 - iRemove;
        return iRemove;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, com.google.common.collect.Multiset
    public final int size() {
        return Ints.saturatedCast(this.size);
    }

    @Override // com.google.common.collect.AbstractMultiset, com.google.common.collect.Multiset
    public final boolean setCount(@ParametricNullness E e2, int i2, int i3) {
        CollectPreconditions.b(i2, "oldCount");
        CollectPreconditions.b(i3, "newCount");
        int iH = this.backingMap.h(e2);
        if (iH == -1) {
            if (i2 != 0) {
                return false;
            }
            if (i3 > 0) {
                this.backingMap.put(e2, i3);
                this.size += i3;
            }
            return true;
        }
        if (this.backingMap.g(iH) != i2) {
            return false;
        }
        if (i3 == 0) {
            this.backingMap.n(iH);
            this.size -= i2;
        } else {
            this.backingMap.p(iH, i3);
            this.size += i3 - i2;
        }
        return true;
    }
}
