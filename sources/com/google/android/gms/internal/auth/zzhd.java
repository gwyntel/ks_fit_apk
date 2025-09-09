package com.google.android.gms.internal.auth;

import java.util.Iterator;

/* loaded from: classes3.dex */
final class zzhd implements Iterator {

    /* renamed from: a, reason: collision with root package name */
    final Iterator f13025a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ zzhe f13026b;

    zzhd(zzhe zzheVar) {
        this.f13026b = zzheVar;
        this.f13025a = zzheVar.zza.iterator();
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.f13025a.hasNext();
    }

    @Override // java.util.Iterator
    public final /* bridge */ /* synthetic */ Object next() {
        return (String) this.f13025a.next();
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException();
    }
}
