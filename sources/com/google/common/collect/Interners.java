package com.google.common.collect;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.J2ktIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Equivalence;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.MapMaker;
import com.google.common.collect.MapMakerInternalMap;
import javax.annotation.CheckForNull;

@J2ktIncompatible
@GwtIncompatible
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public final class Interners {

    public static class InternerBuilder {
        private final MapMaker mapMaker;
        private boolean strong;

        public <E> Interner<E> build() {
            if (!this.strong) {
                this.mapMaker.weakKeys();
            }
            return new InternerImpl(this.mapMaker);
        }

        public InternerBuilder concurrencyLevel(int i2) {
            this.mapMaker.concurrencyLevel(i2);
            return this;
        }

        public InternerBuilder strong() {
            this.strong = true;
            return this;
        }

        @GwtIncompatible("java.lang.ref.WeakReference")
        public InternerBuilder weak() {
            this.strong = false;
            return this;
        }

        private InternerBuilder() {
            this.mapMaker = new MapMaker();
            this.strong = true;
        }
    }

    private static class InternerFunction<E> implements Function<E, E> {
        private final Interner<E> interner;

        public InternerFunction(Interner<E> interner) {
            this.interner = interner;
        }

        @Override // com.google.common.base.Function
        public E apply(E e2) {
            return this.interner.intern(e2);
        }

        @Override // com.google.common.base.Function
        public boolean equals(@CheckForNull Object obj) {
            if (obj instanceof InternerFunction) {
                return this.interner.equals(((InternerFunction) obj).interner);
            }
            return false;
        }

        public int hashCode() {
            return this.interner.hashCode();
        }
    }

    @VisibleForTesting
    static final class InternerImpl<E> implements Interner<E> {

        /* renamed from: a, reason: collision with root package name */
        final MapMakerInternalMap f14046a;

        @Override // com.google.common.collect.Interner
        public E intern(E e2) {
            E e3;
            do {
                MapMakerInternalMap.InternalEntry entry = this.f14046a.getEntry(e2);
                if (entry != null && (e3 = (E) entry.getKey()) != null) {
                    return e3;
                }
            } while (((MapMaker.Dummy) this.f14046a.putIfAbsent(e2, MapMaker.Dummy.VALUE)) != null);
            return e2;
        }

        private InternerImpl(MapMaker mapMaker) {
            this.f14046a = MapMakerInternalMap.createWithDummyValues(mapMaker.f(Equivalence.equals()));
        }
    }

    private Interners() {
    }

    public static <E> Function<E, E> asFunction(Interner<E> interner) {
        return new InternerFunction((Interner) Preconditions.checkNotNull(interner));
    }

    public static InternerBuilder newBuilder() {
        return new InternerBuilder();
    }

    public static <E> Interner<E> newStrongInterner() {
        return newBuilder().strong().build();
    }

    @GwtIncompatible("java.lang.ref.WeakReference")
    public static <E> Interner<E> newWeakInterner() {
        return newBuilder().weak().build();
    }
}
