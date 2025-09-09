package com.google.android.gms.internal.fitness;

import java.util.NoSuchElementException;

/* loaded from: classes3.dex */
final class zzfo extends zzft {

    /* renamed from: a, reason: collision with root package name */
    boolean f13099a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ Object f13100b;

    zzfo(Object obj) {
        this.f13100b = obj;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return !this.f13099a;
    }

    @Override // java.util.Iterator
    public final Object next() {
        if (this.f13099a) {
            throw new NoSuchElementException();
        }
        this.f13099a = true;
        return this.f13100b;
    }
}
