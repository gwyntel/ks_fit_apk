package com.google.android.gms.internal.measurement;

/* loaded from: classes3.dex */
public final class zzrk implements zzrh {
    private static final zzgl<Boolean> zza;
    private static final zzgl<Boolean> zzb;
    private static final zzgl<Long> zzc;

    static {
        zzgt zzgtVarZza = new zzgt(zzgm.zza("com.google.android.gms.measurement")).zzb().zza();
        zza = zzgtVarZza.zza("measurement.gbraid_campaign.gbraid.client.dev", false);
        zzb = zzgtVarZza.zza("measurement.gbraid_campaign.gbraid.service", false);
        zzc = zzgtVarZza.zza("measurement.id.gbraid_campaign.service", 0L);
    }

    @Override // com.google.android.gms.internal.measurement.zzrh
    public final boolean zza() {
        return true;
    }

    @Override // com.google.android.gms.internal.measurement.zzrh
    public final boolean zzb() {
        return zza.zza().booleanValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzrh
    public final boolean zzc() {
        return zzb.zza().booleanValue();
    }
}
