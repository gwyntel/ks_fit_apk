package com.google.android.gms.internal.measurement;

/* loaded from: classes3.dex */
public final class zzso implements zzsl {
    private static final zzgl<Boolean> zza;
    private static final zzgl<Long> zzb;

    static {
        zzgt zzgtVarZza = new zzgt(zzgm.zza("com.google.android.gms.measurement")).zzb().zza();
        zza = zzgtVarZza.zza("measurement.remove_app_background.client", false);
        zzb = zzgtVarZza.zza("measurement.id.remove_app_background.client", 0L);
    }

    @Override // com.google.android.gms.internal.measurement.zzsl
    public final boolean zza() {
        return true;
    }

    @Override // com.google.android.gms.internal.measurement.zzsl
    public final boolean zzb() {
        return zza.zza().booleanValue();
    }
}
