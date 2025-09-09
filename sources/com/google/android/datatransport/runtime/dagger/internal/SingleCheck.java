package com.google.android.datatransport.runtime.dagger.internal;

import javax.inject.Provider;

/* loaded from: classes3.dex */
public final class SingleCheck<T> implements Provider<T> {
    private static final Object UNINITIALIZED = new Object();
    private volatile Object instance = UNINITIALIZED;
    private volatile Provider<T> provider;

    private SingleCheck(Provider<T> provider) {
        this.provider = provider;
    }

    public static <P extends Provider<T>, T> Provider<T> provider(P p2) {
        return ((p2 instanceof SingleCheck) || (p2 instanceof DoubleCheck)) ? p2 : new SingleCheck((Provider) Preconditions.checkNotNull(p2));
    }

    @Override // javax.inject.Provider
    public T get() {
        T t2 = (T) this.instance;
        if (t2 != UNINITIALIZED) {
            return t2;
        }
        Provider<T> provider = this.provider;
        if (provider == null) {
            return (T) this.instance;
        }
        T t3 = provider.get();
        this.instance = t3;
        this.provider = null;
        return t3;
    }
}
