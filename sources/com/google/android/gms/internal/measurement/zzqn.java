package com.google.android.gms.internal.measurement;

/* loaded from: classes3.dex */
public final class zzqn implements zzqo {
    private static final zzgl<Boolean> zza;
    private static final zzgl<Boolean> zzb;
    private static final zzgl<Boolean> zzc;
    private static final zzgl<Boolean> zzd;
    private static final zzgl<Boolean> zze;
    private static final zzgl<Boolean> zzf;

    static {
        zzgt zzgtVarZza = new zzgt(zzgm.zza("com.google.android.gms.measurement")).zzb().zza();
        zza = zzgtVarZza.zza("measurement.dma_consent.client.dev", false);
        zzb = zzgtVarZza.zza("measurement.dma_consent.client_bow_check.dev", false);
        zzc = zzgtVarZza.zza("measurement.dma_consent.service", false);
        zzd = zzgtVarZza.zza("measurement.dma_consent.service_gcs_v2", false);
        zze = zzgtVarZza.zza("measurement.dma_consent.service_npa_remote_default", false);
        zzf = zzgtVarZza.zza("measurement.dma_consent.service_split_batch_on_consent", false);
    }

    @Override // com.google.android.gms.internal.measurement.zzqo
    public final boolean zza() {
        return true;
    }

    @Override // com.google.android.gms.internal.measurement.zzqo
    public final boolean zzb() {
        return zza.zza().booleanValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzqo
    public final boolean zzc() {
        return zzb.zza().booleanValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzqo
    public final boolean zzd() {
        return zzc.zza().booleanValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzqo
    public final boolean zze() {
        return zzd.zza().booleanValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzqo
    public final boolean zzf() {
        return zze.zza().booleanValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzqo
    public final boolean zzg() {
        return zzf.zza().booleanValue();
    }
}
