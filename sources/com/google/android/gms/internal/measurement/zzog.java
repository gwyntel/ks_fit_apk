package com.google.android.gms.internal.measurement;

import java.util.Iterator;

/* loaded from: classes3.dex */
final class zzog extends zzoo {
    private final /* synthetic */ zzoc zza;

    @Override // com.google.android.gms.internal.measurement.zzoo, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public final Iterator iterator() {
        return new zzoe(this.zza);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private zzog(zzoc zzocVar) {
        super(zzocVar);
        this.zza = zzocVar;
    }
}
