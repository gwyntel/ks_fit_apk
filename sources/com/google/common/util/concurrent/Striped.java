package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.J2ktIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.MapMaker;
import com.google.common.math.IntMath;
import com.google.common.util.concurrent.Striped;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@ElementTypesAreNonnullByDefault
@GwtIncompatible
@J2ktIncompatible
/* loaded from: classes3.dex */
public abstract class Striped<L> {
    private static final int ALL_SET = -1;
    private static final int LARGE_LAZY_CUTOFF = 1024;

    private static class CompactStriped<L> extends PowerOfTwoStriped<L> {
        private final Object[] array;

        @Override // com.google.common.util.concurrent.Striped
        public L getAt(int i2) {
            return (L) this.array[i2];
        }

        @Override // com.google.common.util.concurrent.Striped
        public int size() {
            return this.array.length;
        }

        private CompactStriped(int i2, Supplier<L> supplier) {
            super(i2);
            int i3 = 0;
            Preconditions.checkArgument(i2 <= 1073741824, "Stripes must be <= 2^30)");
            this.array = new Object[this.f14933a + 1];
            while (true) {
                Object[] objArr = this.array;
                if (i3 >= objArr.length) {
                    return;
                }
                objArr[i3] = supplier.get();
                i3++;
            }
        }
    }

    @VisibleForTesting
    static class LargeLazyStriped<L> extends PowerOfTwoStriped<L> {

        /* renamed from: b, reason: collision with root package name */
        final ConcurrentMap f14930b;

        /* renamed from: c, reason: collision with root package name */
        final Supplier f14931c;

        /* renamed from: d, reason: collision with root package name */
        final int f14932d;

        LargeLazyStriped(int i2, Supplier supplier) {
            super(i2);
            int i3 = this.f14933a;
            this.f14932d = i3 == -1 ? Integer.MAX_VALUE : i3 + 1;
            this.f14931c = supplier;
            this.f14930b = new MapMaker().weakValues().makeMap();
        }

        @Override // com.google.common.util.concurrent.Striped
        public L getAt(int i2) {
            if (this.f14932d != Integer.MAX_VALUE) {
                Preconditions.checkElementIndex(i2, size());
            }
            L l2 = (L) this.f14930b.get(Integer.valueOf(i2));
            if (l2 != null) {
                return l2;
            }
            Object obj = this.f14931c.get();
            return (L) MoreObjects.firstNonNull(this.f14930b.putIfAbsent(Integer.valueOf(i2), obj), obj);
        }

        @Override // com.google.common.util.concurrent.Striped
        public int size() {
            return this.f14932d;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class PaddedLock extends ReentrantLock {
        long unused1;
        long unused2;
        long unused3;

        PaddedLock() {
            super(false);
        }
    }

    private static class PaddedSemaphore extends Semaphore {
        long unused1;
        long unused2;
        long unused3;

        PaddedSemaphore(int i2) {
            super(i2, false);
        }
    }

    private static abstract class PowerOfTwoStriped<L> extends Striped<L> {

        /* renamed from: a, reason: collision with root package name */
        final int f14933a;

        PowerOfTwoStriped(int i2) {
            super();
            Preconditions.checkArgument(i2 > 0, "Stripes must be positive");
            this.f14933a = i2 > 1073741824 ? -1 : Striped.ceilToPowerOfTwo(i2) - 1;
        }

        @Override // com.google.common.util.concurrent.Striped
        final int g(Object obj) {
            return Striped.smear(obj.hashCode()) & this.f14933a;
        }

        @Override // com.google.common.util.concurrent.Striped
        public final L get(Object obj) {
            return getAt(g(obj));
        }
    }

    @VisibleForTesting
    static class SmallLazyStriped<L> extends PowerOfTwoStriped<L> {

        /* renamed from: b, reason: collision with root package name */
        final AtomicReferenceArray f14934b;

        /* renamed from: c, reason: collision with root package name */
        final Supplier f14935c;

        /* renamed from: d, reason: collision with root package name */
        final int f14936d;

        /* renamed from: e, reason: collision with root package name */
        final ReferenceQueue f14937e;

        private static final class ArrayReference<L> extends WeakReference<L> {

            /* renamed from: a, reason: collision with root package name */
            final int f14938a;

            ArrayReference(Object obj, int i2, ReferenceQueue referenceQueue) {
                super(obj, referenceQueue);
                this.f14938a = i2;
            }
        }

        SmallLazyStriped(int i2, Supplier supplier) {
            super(i2);
            this.f14937e = new ReferenceQueue();
            int i3 = this.f14933a;
            int i4 = i3 == -1 ? Integer.MAX_VALUE : i3 + 1;
            this.f14936d = i4;
            this.f14934b = new AtomicReferenceArray(i4);
            this.f14935c = supplier;
        }

        private void drainQueue() {
            while (true) {
                Reference referencePoll = this.f14937e.poll();
                if (referencePoll == null) {
                    return;
                }
                ArrayReference arrayReference = (ArrayReference) referencePoll;
                i0.a(this.f14934b, arrayReference.f14938a, arrayReference, null);
            }
        }

        @Override // com.google.common.util.concurrent.Striped
        public L getAt(int i2) {
            if (this.f14936d != Integer.MAX_VALUE) {
                Preconditions.checkElementIndex(i2, size());
            }
            ArrayReference arrayReference = (ArrayReference) this.f14934b.get(i2);
            L l2 = arrayReference == null ? null : arrayReference.get();
            if (l2 != null) {
                return l2;
            }
            L l3 = (L) this.f14935c.get();
            ArrayReference arrayReference2 = new ArrayReference(l3, i2, this.f14937e);
            while (!i0.a(this.f14934b, i2, arrayReference, arrayReference2)) {
                arrayReference = (ArrayReference) this.f14934b.get(i2);
                L l4 = arrayReference == null ? null : arrayReference.get();
                if (l4 != null) {
                    return l4;
                }
            }
            drainQueue();
            return l3;
        }

        @Override // com.google.common.util.concurrent.Striped
        public int size() {
            return this.f14936d;
        }
    }

    private static final class WeakSafeCondition extends ForwardingCondition {
        private final Condition delegate;
        private final WeakSafeReadWriteLock strongReference;

        WeakSafeCondition(Condition condition, WeakSafeReadWriteLock weakSafeReadWriteLock) {
            this.delegate = condition;
            this.strongReference = weakSafeReadWriteLock;
        }

        @Override // com.google.common.util.concurrent.ForwardingCondition
        Condition a() {
            return this.delegate;
        }
    }

    private static final class WeakSafeLock extends ForwardingLock {
        private final Lock delegate;
        private final WeakSafeReadWriteLock strongReference;

        WeakSafeLock(Lock lock, WeakSafeReadWriteLock weakSafeReadWriteLock) {
            this.delegate = lock;
            this.strongReference = weakSafeReadWriteLock;
        }

        @Override // com.google.common.util.concurrent.ForwardingLock
        Lock a() {
            return this.delegate;
        }

        @Override // com.google.common.util.concurrent.ForwardingLock, java.util.concurrent.locks.Lock
        public Condition newCondition() {
            return new WeakSafeCondition(this.delegate.newCondition(), this.strongReference);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class WeakSafeReadWriteLock implements ReadWriteLock {
        private final ReadWriteLock delegate = new ReentrantReadWriteLock();

        WeakSafeReadWriteLock() {
        }

        @Override // java.util.concurrent.locks.ReadWriteLock
        public Lock readLock() {
            return new WeakSafeLock(this.delegate.readLock(), this);
        }

        @Override // java.util.concurrent.locks.ReadWriteLock
        public Lock writeLock() {
            return new WeakSafeLock(this.delegate.writeLock(), this);
        }
    }

    private Striped() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int ceilToPowerOfTwo(int i2) {
        return 1 << IntMath.log2(i2, RoundingMode.CEILING);
    }

    static Striped f(int i2, Supplier supplier) {
        return new CompactStriped(i2, supplier);
    }

    static Striped h(int i2, Supplier supplier) {
        return i2 < 1024 ? new SmallLazyStriped(i2, supplier) : new LargeLazyStriped(i2, supplier);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Lock lambda$lazyWeakLock$0() {
        return new ReentrantLock(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Semaphore lambda$lazyWeakSemaphore$2(int i2) {
        return new Semaphore(i2, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Semaphore lambda$semaphore$1(int i2) {
        return new PaddedSemaphore(i2);
    }

    public static Striped<Lock> lazyWeakLock(int i2) {
        return h(i2, new Supplier() { // from class: com.google.common.util.concurrent.g0
            @Override // com.google.common.base.Supplier
            public final Object get() {
                return Striped.lambda$lazyWeakLock$0();
            }
        });
    }

    public static Striped<ReadWriteLock> lazyWeakReadWriteLock(int i2) {
        return h(i2, new Supplier() { // from class: com.google.common.util.concurrent.c0
            @Override // com.google.common.base.Supplier
            public final Object get() {
                return new Striped.WeakSafeReadWriteLock();
            }
        });
    }

    public static Striped<Semaphore> lazyWeakSemaphore(int i2, final int i3) {
        return h(i2, new Supplier() { // from class: com.google.common.util.concurrent.f0
            @Override // com.google.common.base.Supplier
            public final Object get() {
                return Striped.lambda$lazyWeakSemaphore$2(i3);
            }
        });
    }

    public static Striped<Lock> lock(int i2) {
        return f(i2, new Supplier() { // from class: com.google.common.util.concurrent.d0
            @Override // com.google.common.base.Supplier
            public final Object get() {
                return new Striped.PaddedLock();
            }
        });
    }

    public static Striped<ReadWriteLock> readWriteLock(int i2) {
        return f(i2, new Supplier() { // from class: com.google.common.util.concurrent.h0
            @Override // com.google.common.base.Supplier
            public final Object get() {
                return new ReentrantReadWriteLock();
            }
        });
    }

    public static Striped<Semaphore> semaphore(int i2, final int i3) {
        return f(i2, new Supplier() { // from class: com.google.common.util.concurrent.e0
            @Override // com.google.common.base.Supplier
            public final Object get() {
                return Striped.lambda$semaphore$1(i3);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int smear(int i2) {
        int i3 = i2 ^ ((i2 >>> 20) ^ (i2 >>> 12));
        return (i3 >>> 4) ^ ((i3 >>> 7) ^ i3);
    }

    public Iterable<L> bulkGet(Iterable<? extends Object> iterable) {
        ArrayList arrayListNewArrayList = Lists.newArrayList(iterable);
        if (arrayListNewArrayList.isEmpty()) {
            return ImmutableList.of();
        }
        int[] iArr = new int[arrayListNewArrayList.size()];
        for (int i2 = 0; i2 < arrayListNewArrayList.size(); i2++) {
            iArr[i2] = g(arrayListNewArrayList.get(i2));
        }
        Arrays.sort(iArr);
        int i3 = iArr[0];
        arrayListNewArrayList.set(0, getAt(i3));
        for (int i4 = 1; i4 < arrayListNewArrayList.size(); i4++) {
            int i5 = iArr[i4];
            if (i5 == i3) {
                arrayListNewArrayList.set(i4, arrayListNewArrayList.get(i4 - 1));
            } else {
                arrayListNewArrayList.set(i4, getAt(i5));
                i3 = i5;
            }
        }
        return Collections.unmodifiableList(arrayListNewArrayList);
    }

    abstract int g(Object obj);

    public abstract L get(Object obj);

    public abstract L getAt(int i2);

    public abstract int size();
}
