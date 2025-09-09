package com.google.android.gms.internal.measurement;

/* loaded from: classes3.dex */
final class zzit<E> extends zzhy<E> {
    private final zzir<E> zza;

    zzit(zzir zzirVar, int i2) {
        super(zzirVar.size(), i2);
        this.zza = zzirVar;
    }

    @Override // com.google.android.gms.internal.measurement.zzhy
    protected final Object a(int i2) {
        return this.zza.get(i2);
    }
}
