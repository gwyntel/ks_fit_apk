package com.google.common.collect;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.J2ktIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.Multiset;
import com.google.common.collect.Serialization;
import com.google.common.math.IntMath;
import com.google.common.primitives.Ints;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.CheckForNull;

@GwtIncompatible
@J2ktIncompatible
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public final class ConcurrentHashMultiset<E> extends AbstractMultiset<E> implements Serializable {
    private static final long serialVersionUID = 1;
    private final transient ConcurrentMap<E, AtomicInteger> countMap;

    private class EntrySet extends AbstractMultiset<E>.EntrySet {
        private EntrySet() {
            super();
        }

        private List<Multiset.Entry<E>> snapshot() {
            ArrayList arrayListNewArrayListWithExpectedSize = Lists.newArrayListWithExpectedSize(size());
            Iterators.addAll(arrayListNewArrayListWithExpectedSize, iterator());
            return arrayListNewArrayListWithExpectedSize;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.collect.AbstractMultiset.EntrySet, com.google.common.collect.Multisets.EntrySet
        /* renamed from: b, reason: merged with bridge method [inline-methods] */
        public ConcurrentHashMultiset a() {
            return ConcurrentHashMultiset.this;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public Object[] toArray() {
            return snapshot().toArray();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public <T> T[] toArray(T[] tArr) {
            return (T[]) snapshot().toArray(tArr);
        }
    }

    private static class FieldSettersHolder {

        /* renamed from: a, reason: collision with root package name */
        static final Serialization.FieldSetter f13947a = Serialization.a(ConcurrentHashMultiset.class, "countMap");

        private FieldSettersHolder() {
        }
    }

    @VisibleForTesting
    ConcurrentHashMultiset(ConcurrentMap<E, AtomicInteger> concurrentMap) {
        Preconditions.checkArgument(concurrentMap.isEmpty(), "the backing map (%s) must be empty", concurrentMap);
        this.countMap = concurrentMap;
    }

    public static <E> ConcurrentHashMultiset<E> create() {
        return new ConcurrentHashMultiset<>(new ConcurrentHashMap());
    }

    @J2ktIncompatible
    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        Object object = objectInputStream.readObject();
        Objects.requireNonNull(object);
        FieldSettersHolder.f13947a.b(this, (ConcurrentMap) object);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private List<E> snapshot() {
        ArrayList arrayListNewArrayListWithExpectedSize = Lists.newArrayListWithExpectedSize(size());
        for (Multiset.Entry entry : entrySet()) {
            Object element = entry.getElement();
            for (int count = entry.getCount(); count > 0; count--) {
                arrayListNewArrayListWithExpectedSize.add(element);
            }
        }
        return arrayListNewArrayListWithExpectedSize;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(this.countMap);
    }

    @Override // com.google.common.collect.AbstractMultiset, com.google.common.collect.Multiset
    @CanIgnoreReturnValue
    public int add(E e2, int i2) {
        AtomicInteger atomicIntegerPutIfAbsent;
        int i3;
        AtomicInteger atomicInteger;
        Preconditions.checkNotNull(e2);
        if (i2 == 0) {
            return count(e2);
        }
        CollectPreconditions.d(i2, "occurrences");
        do {
            atomicIntegerPutIfAbsent = (AtomicInteger) Maps.y(this.countMap, e2);
            if (atomicIntegerPutIfAbsent == null && (atomicIntegerPutIfAbsent = this.countMap.putIfAbsent(e2, new AtomicInteger(i2))) == null) {
                return 0;
            }
            do {
                i3 = atomicIntegerPutIfAbsent.get();
                if (i3 == 0) {
                    atomicInteger = new AtomicInteger(i2);
                    if (this.countMap.putIfAbsent(e2, atomicInteger) == null) {
                        break;
                    }
                } else {
                    try {
                    } catch (ArithmeticException unused) {
                        throw new IllegalArgumentException("Overflow adding " + i2 + " occurrences to a count of " + i3);
                    }
                }
            } while (!atomicIntegerPutIfAbsent.compareAndSet(i3, IntMath.checkedAdd(i3, i2)));
            return i3;
        } while (!this.countMap.replace(e2, atomicIntegerPutIfAbsent, atomicInteger));
        return 0;
    }

    @Override // com.google.common.collect.AbstractMultiset, java.util.AbstractCollection, java.util.Collection
    public void clear() {
        this.countMap.clear();
    }

    @Override // com.google.common.collect.AbstractMultiset, java.util.AbstractCollection, java.util.Collection, com.google.common.collect.Multiset
    public /* bridge */ /* synthetic */ boolean contains(@CheckForNull Object obj) {
        return super.contains(obj);
    }

    @Override // com.google.common.collect.Multiset
    public int count(@CheckForNull Object obj) {
        AtomicInteger atomicInteger = (AtomicInteger) Maps.y(this.countMap, obj);
        if (atomicInteger == null) {
            return 0;
        }
        return atomicInteger.get();
    }

    @Override // com.google.common.collect.AbstractMultiset
    Set<E> createElementSet() {
        final Set<E> setKeySet = this.countMap.keySet();
        return new ForwardingSet<E>(this) { // from class: com.google.common.collect.ConcurrentHashMultiset.1

            /* renamed from: b, reason: collision with root package name */
            final /* synthetic */ ConcurrentHashMultiset f13942b;

            {
                this.f13942b = this;
            }

            @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.util.Set
            public boolean contains(@CheckForNull Object obj) {
                return obj != null && Collections2.d(setKeySet, obj);
            }

            @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.util.Set
            public boolean containsAll(Collection<?> collection) {
                return standardContainsAll(collection);
            }

            @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.util.Set
            public boolean remove(@CheckForNull Object obj) {
                return obj != null && Collections2.e(setKeySet, obj);
            }

            @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.util.Set
            public boolean removeAll(Collection<?> collection) {
                return standardRemoveAll(collection);
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.google.common.collect.ForwardingSet, com.google.common.collect.ForwardingCollection, com.google.common.collect.ForwardingObject
            public Set delegate() {
                return setKeySet;
            }
        };
    }

    @Override // com.google.common.collect.AbstractMultiset
    @Deprecated
    public Set<Multiset.Entry<E>> createEntrySet() {
        return new EntrySet();
    }

    @Override // com.google.common.collect.AbstractMultiset
    int distinctElements() {
        return this.countMap.size();
    }

    @Override // com.google.common.collect.AbstractMultiset
    Iterator<E> elementIterator() {
        throw new AssertionError("should never be called");
    }

    @Override // com.google.common.collect.AbstractMultiset, com.google.common.collect.Multiset
    public /* bridge */ /* synthetic */ Set elementSet() {
        return super.elementSet();
    }

    @Override // com.google.common.collect.AbstractMultiset
    Iterator<Multiset.Entry<E>> entryIterator() {
        final AbstractIterator<Multiset.Entry<E>> abstractIterator = new AbstractIterator<Multiset.Entry<E>>() { // from class: com.google.common.collect.ConcurrentHashMultiset.2
            private final Iterator<Map.Entry<E, AtomicInteger>> mapEntries;

            {
                this.mapEntries = ConcurrentHashMultiset.this.countMap.entrySet().iterator();
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.google.common.collect.AbstractIterator
            /* renamed from: b, reason: merged with bridge method [inline-methods] */
            public Multiset.Entry computeNext() {
                while (this.mapEntries.hasNext()) {
                    Map.Entry<E, AtomicInteger> next = this.mapEntries.next();
                    int i2 = next.getValue().get();
                    if (i2 != 0) {
                        return Multisets.immutableEntry(next.getKey(), i2);
                    }
                }
                return (Multiset.Entry) a();
            }
        };
        return new ForwardingIterator<Multiset.Entry<E>>(this) { // from class: com.google.common.collect.ConcurrentHashMultiset.3

            /* renamed from: b, reason: collision with root package name */
            final /* synthetic */ ConcurrentHashMultiset f13945b;

            @CheckForNull
            private Multiset.Entry<E> last;

            {
                this.f13945b = this;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.google.common.collect.ForwardingIterator, com.google.common.collect.ForwardingObject
            /* renamed from: a */
            public Iterator delegate() {
                return abstractIterator;
            }

            @Override // com.google.common.collect.ForwardingIterator, java.util.Iterator
            public void remove() {
                Preconditions.checkState(this.last != null, "no calls to next() since the last call to remove()");
                this.f13945b.setCount(this.last.getElement(), 0);
                this.last = null;
            }

            @Override // com.google.common.collect.ForwardingIterator, java.util.Iterator
            public Multiset.Entry<E> next() {
                Multiset.Entry<E> entry = (Multiset.Entry) super.next();
                this.last = entry;
                return entry;
            }
        };
    }

    @Override // com.google.common.collect.AbstractMultiset, com.google.common.collect.Multiset
    public /* bridge */ /* synthetic */ Set entrySet() {
        return super.entrySet();
    }

    @Override // com.google.common.collect.AbstractMultiset, java.util.AbstractCollection, java.util.Collection
    public boolean isEmpty() {
        return this.countMap.isEmpty();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.google.common.collect.Multiset
    public Iterator<E> iterator() {
        return Multisets.f(this);
    }

    @Override // com.google.common.collect.AbstractMultiset, com.google.common.collect.Multiset
    @CanIgnoreReturnValue
    public int remove(@CheckForNull Object obj, int i2) {
        int i3;
        int iMax;
        if (i2 == 0) {
            return count(obj);
        }
        CollectPreconditions.d(i2, "occurrences");
        AtomicInteger atomicInteger = (AtomicInteger) Maps.y(this.countMap, obj);
        if (atomicInteger == null) {
            return 0;
        }
        do {
            i3 = atomicInteger.get();
            if (i3 == 0) {
                return 0;
            }
            iMax = Math.max(0, i3 - i2);
        } while (!atomicInteger.compareAndSet(i3, iMax));
        if (iMax == 0) {
            this.countMap.remove(obj, atomicInteger);
        }
        return i3;
    }

    @CanIgnoreReturnValue
    public boolean removeExactly(@CheckForNull Object obj, int i2) {
        int i3;
        int i4;
        if (i2 == 0) {
            return true;
        }
        CollectPreconditions.d(i2, "occurrences");
        AtomicInteger atomicInteger = (AtomicInteger) Maps.y(this.countMap, obj);
        if (atomicInteger == null) {
            return false;
        }
        do {
            i3 = atomicInteger.get();
            if (i3 < i2) {
                return false;
            }
            i4 = i3 - i2;
        } while (!atomicInteger.compareAndSet(i3, i4));
        if (i4 == 0) {
            this.countMap.remove(obj, atomicInteger);
        }
        return true;
    }

    @Override // com.google.common.collect.AbstractMultiset, com.google.common.collect.Multiset
    @CanIgnoreReturnValue
    public int setCount(E e2, int i2) {
        AtomicInteger atomicIntegerPutIfAbsent;
        int i3;
        AtomicInteger atomicInteger;
        Preconditions.checkNotNull(e2);
        CollectPreconditions.b(i2, "count");
        do {
            atomicIntegerPutIfAbsent = (AtomicInteger) Maps.y(this.countMap, e2);
            if (atomicIntegerPutIfAbsent == null && (i2 == 0 || (atomicIntegerPutIfAbsent = this.countMap.putIfAbsent(e2, new AtomicInteger(i2))) == null)) {
                return 0;
            }
            do {
                i3 = atomicIntegerPutIfAbsent.get();
                if (i3 == 0) {
                    if (i2 != 0) {
                        atomicInteger = new AtomicInteger(i2);
                        if (this.countMap.putIfAbsent(e2, atomicInteger) == null) {
                            break;
                        }
                    } else {
                        return 0;
                    }
                }
            } while (!atomicIntegerPutIfAbsent.compareAndSet(i3, i2));
            if (i2 == 0) {
                this.countMap.remove(e2, atomicIntegerPutIfAbsent);
            }
            return i3;
        } while (!this.countMap.replace(e2, atomicIntegerPutIfAbsent, atomicInteger));
        return 0;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, com.google.common.collect.Multiset
    public int size() {
        long j2 = 0;
        while (this.countMap.values().iterator().hasNext()) {
            j2 += r0.next().get();
        }
        return Ints.saturatedCast(j2);
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public Object[] toArray() {
        return snapshot().toArray();
    }

    public static <E> ConcurrentHashMultiset<E> create(Iterable<? extends E> iterable) {
        ConcurrentHashMultiset<E> concurrentHashMultisetCreate = create();
        Iterables.addAll(concurrentHashMultisetCreate, iterable);
        return concurrentHashMultisetCreate;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public <T> T[] toArray(T[] tArr) {
        return (T[]) snapshot().toArray(tArr);
    }

    public static <E> ConcurrentHashMultiset<E> create(ConcurrentMap<E, AtomicInteger> concurrentMap) {
        return new ConcurrentHashMultiset<>(concurrentMap);
    }

    @Override // com.google.common.collect.AbstractMultiset, com.google.common.collect.Multiset
    @CanIgnoreReturnValue
    public boolean setCount(E e2, int i2, int i3) {
        Preconditions.checkNotNull(e2);
        CollectPreconditions.b(i2, "oldCount");
        CollectPreconditions.b(i3, "newCount");
        AtomicInteger atomicInteger = (AtomicInteger) Maps.y(this.countMap, e2);
        if (atomicInteger == null) {
            if (i2 != 0) {
                return false;
            }
            return i3 == 0 || this.countMap.putIfAbsent(e2, new AtomicInteger(i3)) == null;
        }
        int i4 = atomicInteger.get();
        if (i4 == i2) {
            if (i4 == 0) {
                if (i3 == 0) {
                    this.countMap.remove(e2, atomicInteger);
                    return true;
                }
                AtomicInteger atomicInteger2 = new AtomicInteger(i3);
                return this.countMap.putIfAbsent(e2, atomicInteger2) == null || this.countMap.replace(e2, atomicInteger, atomicInteger2);
            }
            if (atomicInteger.compareAndSet(i4, i3)) {
                if (i3 == 0) {
                    this.countMap.remove(e2, atomicInteger);
                }
                return true;
            }
        }
        return false;
    }
}
