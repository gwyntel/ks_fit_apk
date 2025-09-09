package com.google.android.gms.internal.measurement;

/* loaded from: classes3.dex */
public final class zzrx implements zzry {
    private static final zzgl<Long> zza;
    private static final zzgl<Boolean> zzb;
    private static final zzgl<Boolean> zzc;

    static {
        zzgt zzgtVarZza = new zzgt(zzgm.zza("com.google.android.gms.measurement")).zza();
        zza = zzgtVarZza.zza("measurement.id.lifecycle.app_in_background_parameter", 0L);
        zzb = zzgtVarZza.zza("measurement.lifecycle.app_backgrounded_tracking", true);
        zzc = zzgtVarZza.zza("measurement.lifecycle.app_in_background_parameter", false);
    }

    @Override // com.google.android.gms.internal.measurement.zzry
    public final boolean zza() {
        return zzc.zza().booleanValue();
    }
}
