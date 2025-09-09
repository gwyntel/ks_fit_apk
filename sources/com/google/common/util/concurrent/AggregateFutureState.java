package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.Sets;
import com.google.common.util.concurrent.AbstractFuture;
import com.google.j2objc.annotations.ReflectionSupport;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.logging.Level;
import javax.annotation.CheckForNull;

@ElementTypesAreNonnullByDefault
@GwtCompatible(emulated = true)
@ReflectionSupport(ReflectionSupport.Level.FULL)
/* loaded from: classes3.dex */
abstract class AggregateFutureState<OutputT> extends AbstractFuture.TrustedFuture<OutputT> {
    private static final AtomicHelper ATOMIC_HELPER;
    private static final LazyLogger log = new LazyLogger(AggregateFutureState.class);
    private volatile int remaining;

    @CheckForNull
    private volatile Set<Throwable> seenExceptions = null;

    private static abstract class AtomicHelper {
        private AtomicHelper() {
        }

        abstract void a(AggregateFutureState aggregateFutureState, Set set, Set set2);

        abstract int b(AggregateFutureState aggregateFutureState);
    }

    private static final class SafeAtomicHelper extends AtomicHelper {

        /* renamed from: a, reason: collision with root package name */
        final AtomicReferenceFieldUpdater f14810a;

        /* renamed from: b, reason: collision with root package name */
        final AtomicIntegerFieldUpdater f14811b;

        SafeAtomicHelper(AtomicReferenceFieldUpdater atomicReferenceFieldUpdater, AtomicIntegerFieldUpdater atomicIntegerFieldUpdater) {
            super();
            this.f14810a = atomicReferenceFieldUpdater;
            this.f14811b = atomicIntegerFieldUpdater;
        }

        @Override // com.google.common.util.concurrent.AggregateFutureState.AtomicHelper
        void a(AggregateFutureState aggregateFutureState, Set set, Set set2) {
            androidx.concurrent.futures.a.a(this.f14810a, aggregateFutureState, set, set2);
        }

        @Override // com.google.common.util.concurrent.AggregateFutureState.AtomicHelper
        int b(AggregateFutureState aggregateFutureState) {
            return this.f14811b.decrementAndGet(aggregateFutureState);
        }
    }

    private static final class SynchronizedAtomicHelper extends AtomicHelper {
        private SynchronizedAtomicHelper() {
            super();
        }

        @Override // com.google.common.util.concurrent.AggregateFutureState.AtomicHelper
        void a(AggregateFutureState aggregateFutureState, Set set, Set set2) {
            synchronized (aggregateFutureState) {
                try {
                    if (aggregateFutureState.seenExceptions == set) {
                        aggregateFutureState.seenExceptions = set2;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.google.common.util.concurrent.AggregateFutureState.AtomicHelper
        int b(AggregateFutureState aggregateFutureState) {
            int iR;
            synchronized (aggregateFutureState) {
                iR = AggregateFutureState.r(aggregateFutureState);
            }
            return iR;
        }
    }

    static {
        AtomicHelper synchronizedAtomicHelper;
        Throwable th = null;
        byte b2 = 0;
        try {
            synchronizedAtomicHelper = new SafeAtomicHelper(AtomicReferenceFieldUpdater.newUpdater(AggregateFutureState.class, Set.class, "seenExceptions"), AtomicIntegerFieldUpdater.newUpdater(AggregateFutureState.class, "remaining"));
        } catch (Throwable th2) {
            synchronizedAtomicHelper = new SynchronizedAtomicHelper();
            th = th2;
        }
        ATOMIC_HELPER = synchronizedAtomicHelper;
        if (th != null) {
            log.a().log(Level.SEVERE, "SafeAtomicHelper is broken!", th);
        }
    }

    AggregateFutureState(int i2) {
        this.remaining = i2;
    }

    static /* synthetic */ int r(AggregateFutureState aggregateFutureState) {
        int i2 = aggregateFutureState.remaining - 1;
        aggregateFutureState.remaining = i2;
        return i2;
    }

    abstract void s(Set set);

    final void t() {
        this.seenExceptions = null;
    }

    final int u() {
        return ATOMIC_HELPER.b(this);
    }

    final Set v() {
        Set<Throwable> set = this.seenExceptions;
        if (set != null) {
            return set;
        }
        Set setNewConcurrentHashSet = Sets.newConcurrentHashSet();
        s(setNewConcurrentHashSet);
        ATOMIC_HELPER.a(this, null, setNewConcurrentHashSet);
        Set<Throwable> set2 = this.seenExceptions;
        Objects.requireNonNull(set2);
        return set2;
    }
}
