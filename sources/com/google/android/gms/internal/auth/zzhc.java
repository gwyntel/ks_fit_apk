package com.google.android.gms.internal.auth;

import java.util.ListIterator;

/* loaded from: classes3.dex */
final class zzhc implements ListIterator {

    /* renamed from: a, reason: collision with root package name */
    final ListIterator f13022a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ int f13023b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ zzhe f13024c;

    zzhc(zzhe zzheVar, int i2) {
        this.f13024c = zzheVar;
        this.f13023b = i2;
        this.f13022a = zzheVar.zza.listIterator(i2);
    }

    @Override // java.util.ListIterator
    public final /* synthetic */ void add(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public final boolean hasNext() {
        return this.f13022a.hasNext();
    }

    @Override // java.util.ListIterator
    public final boolean hasPrevious() {
        return this.f13022a.hasPrevious();
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public final /* bridge */ /* synthetic */ Object next() {
        return (String) this.f13022a.next();
    }

    @Override // java.util.ListIterator
    public final int nextIndex() {
        return this.f13022a.nextIndex();
    }

    @Override // java.util.ListIterator
    public final /* bridge */ /* synthetic */ Object previous() {
        return (String) this.f13022a.previous();
    }

    @Override // java.util.ListIterator
    public final int previousIndex() {
        return this.f13022a.previousIndex();
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.ListIterator
    public final /* synthetic */ void set(Object obj) {
        throw new UnsupportedOperationException();
    }
}
