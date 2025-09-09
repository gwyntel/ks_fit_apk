package com.google.android.gms.internal.measurement;

import java.util.Iterator;

/* loaded from: classes3.dex */
final class zzpd implements Iterator<String> {
    private Iterator<String> zza;
    private final /* synthetic */ zzpb zzb;

    zzpd(zzpb zzpbVar) {
        this.zzb = zzpbVar;
        this.zza = zzpbVar.zza.iterator();
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.zza.hasNext();
    }

    @Override // java.util.Iterator
    public final /* synthetic */ String next() {
        return this.zza.next();
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException();
    }
}
