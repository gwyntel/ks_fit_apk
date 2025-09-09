package com.google.android.gms.internal.fitness;

/* loaded from: classes3.dex */
final class zzfk extends zzfh {
    private final zzfm zza;

    zzfk(zzfm zzfmVar, int i2) {
        super(zzfmVar.size(), i2);
        this.zza = zzfmVar;
    }

    @Override // com.google.android.gms.internal.fitness.zzfh
    protected final Object a(int i2) {
        return this.zza.get(i2);
    }
}
