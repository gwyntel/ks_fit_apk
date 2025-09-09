package com.google.android.gms.internal.measurement;

/* loaded from: classes3.dex */
public final class zzsj implements zzsk {
    private static final zzgl<Boolean> zza;
    private static final zzgl<Boolean> zzb;
    private static final zzgl<Boolean> zzc;
    private static final zzgl<Boolean> zzd;
    private static final zzgl<Boolean> zze;
    private static final zzgl<Boolean> zzf;
    private static final zzgl<Boolean> zzg;
    private static final zzgl<Boolean> zzh;
    private static final zzgl<Boolean> zzi;
    private static final zzgl<Boolean> zzj;
    private static final zzgl<Boolean> zzk;
    private static final zzgl<Boolean> zzl;
    private static final zzgl<Boolean> zzm;
    private static final zzgl<Boolean> zzn;

    static {
        zzgt zzgtVarZza = new zzgt(zzgm.zza("com.google.android.gms.measurement")).zzb().zza();
        zza = zzgtVarZza.zza("measurement.redaction.app_instance_id", true);
        zzb = zzgtVarZza.zza("measurement.redaction.client_ephemeral_aiid_generation", true);
        zzc = zzgtVarZza.zza("measurement.redaction.config_redacted_fields", true);
        zzd = zzgtVarZza.zza("measurement.redaction.device_info", true);
        zze = zzgtVarZza.zza("measurement.redaction.e_tag", true);
        zzf = zzgtVarZza.zza("measurement.redaction.enhanced_uid", true);
        zzg = zzgtVarZza.zza("measurement.redaction.populate_ephemeral_app_instance_id", true);
        zzh = zzgtVarZza.zza("measurement.redaction.google_signals", true);
        zzi = zzgtVarZza.zza("measurement.redaction.no_aiid_in_config_request", true);
        zzj = zzgtVarZza.zza("measurement.redaction.retain_major_os_version", true);
        zzk = zzgtVarZza.zza("measurement.redaction.scion_payload_generator", true);
        zzl = zzgtVarZza.zza("measurement.redaction.upload_redacted_fields", true);
        zzm = zzgtVarZza.zza("measurement.redaction.upload_subdomain_override", true);
        zzn = zzgtVarZza.zza("measurement.redaction.user_id", true);
    }

    @Override // com.google.android.gms.internal.measurement.zzsk
    public final boolean zza() {
        return zzj.zza().booleanValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzsk
    public final boolean zzb() {
        return zzk.zza().booleanValue();
    }
}
