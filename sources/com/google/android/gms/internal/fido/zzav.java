package com.google.android.gms.internal.fido;

import java.util.NoSuchElementException;

/* loaded from: classes3.dex */
final class zzav extends zzaz {

    /* renamed from: a, reason: collision with root package name */
    boolean f13052a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ Object f13053b;

    zzav(Object obj) {
        this.f13053b = obj;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return !this.f13052a;
    }

    @Override // java.util.Iterator
    public final Object next() {
        if (this.f13052a) {
            throw new NoSuchElementException();
        }
        this.f13052a = true;
        return this.f13053b;
    }
}
