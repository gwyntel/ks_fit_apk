package com.google.android.gms.internal.measurement;

/* loaded from: classes3.dex */
final class zzmy implements zzng {
    private zzng[] zza;

    zzmy(zzng... zzngVarArr) {
        this.zza = zzngVarArr;
    }

    @Override // com.google.android.gms.internal.measurement.zzng
    public final zznh zza(Class<?> cls) {
        for (zzng zzngVar : this.zza) {
            if (zzngVar.zzb(cls)) {
                return zzngVar.zza(cls);
            }
        }
        throw new UnsupportedOperationException("No factory is available for message type: " + cls.getName());
    }

    @Override // com.google.android.gms.internal.measurement.zzng
    public final boolean zzb(Class<?> cls) {
        for (zzng zzngVar : this.zza) {
            if (zzngVar.zzb(cls)) {
                return true;
            }
        }
        return false;
    }
}
