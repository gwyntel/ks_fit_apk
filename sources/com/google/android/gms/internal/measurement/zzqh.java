package com.google.android.gms.internal.measurement;

/* loaded from: classes3.dex */
public final class zzqh implements zzqi {
    private static final zzgl<Boolean> zza;
    private static final zzgl<Boolean> zzb;
    private static final zzgl<Boolean> zzc;
    private static final zzgl<Long> zzd;

    static {
        zzgt zzgtVarZza = new zzgt(zzgm.zza("com.google.android.gms.measurement")).zza();
        zza = zzgtVarZza.zza("measurement.client.consent_state_v1", true);
        zzb = zzgtVarZza.zza("measurement.client.3p_consent_state_v1", true);
        zzc = zzgtVarZza.zza("measurement.service.consent_state_v1_W36", true);
        zzd = zzgtVarZza.zza("measurement.service.storage_consent_support_version", 203600L);
    }

    @Override // com.google.android.gms.internal.measurement.zzqi
    public final long zza() {
        return zzd.zza().longValue();
    }
}
