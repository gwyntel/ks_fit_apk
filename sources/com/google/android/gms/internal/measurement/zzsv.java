package com.google.android.gms.internal.measurement;

/* loaded from: classes3.dex */
public final class zzsv implements zzsw {
    private static final zzgl<Boolean> zza;
    private static final zzgl<Boolean> zzb;
    private static final zzgl<Boolean> zzc;
    private static final zzgl<Boolean> zzd;
    private static final zzgl<Boolean> zze;
    private static final zzgl<Long> zzf;

    static {
        zzgt zzgtVarZza = new zzgt(zzgm.zza("com.google.android.gms.measurement")).zzb().zza();
        zza = zzgtVarZza.zza("measurement.client.sessions.background_sessions_enabled", true);
        zzb = zzgtVarZza.zza("measurement.client.sessions.enable_fix_background_engagement", false);
        zzc = zzgtVarZza.zza("measurement.client.sessions.immediate_start_enabled_foreground", true);
        zzd = zzgtVarZza.zza("measurement.client.sessions.remove_expired_session_properties_enabled", true);
        zze = zzgtVarZza.zza("measurement.client.sessions.session_id_enabled", true);
        zzf = zzgtVarZza.zza("measurement.id.client.sessions.enable_fix_background_engagement", 0L);
    }

    @Override // com.google.android.gms.internal.measurement.zzsw
    public final boolean zza() {
        return zzb.zza().booleanValue();
    }
}
