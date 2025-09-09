package com.google.android.gms.internal.measurement;

/* loaded from: classes3.dex */
public final class zzta implements zzsx {
    private static final zzgl<Boolean> zza;
    private static final zzgl<Boolean> zzb;

    static {
        zzgt zzgtVarZza = new zzgt(zzgm.zza("com.google.android.gms.measurement")).zzb().zza();
        zza = zzgtVarZza.zza("measurement.sfmc.client", true);
        zzb = zzgtVarZza.zza("measurement.sfmc.service", true);
    }

    @Override // com.google.android.gms.internal.measurement.zzsx
    public final boolean zza() {
        return true;
    }

    @Override // com.google.android.gms.internal.measurement.zzsx
    public final boolean zzb() {
        return zza.zza().booleanValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzsx
    public final boolean zzc() {
        return zzb.zza().booleanValue();
    }
}
