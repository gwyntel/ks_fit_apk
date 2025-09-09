package com.google.android.gms.internal.measurement;

/* loaded from: classes3.dex */
public final class zzsu implements zzsr {
    private static final zzgl<Boolean> zza;
    private static final zzgl<Boolean> zzb;
    private static final zzgl<Boolean> zzc;
    private static final zzgl<Boolean> zzd;

    static {
        zzgt zzgtVarZza = new zzgt(zzgm.zza("com.google.android.gms.measurement")).zzb().zza();
        zza = zzgtVarZza.zza("measurement.collection.enable_session_stitching_token.client.dev", true);
        zzb = zzgtVarZza.zza("measurement.collection.enable_session_stitching_token.first_open_fix", true);
        zzc = zzgtVarZza.zza("measurement.session_stitching_token_enabled", false);
        zzd = zzgtVarZza.zza("measurement.link_sst_to_sid", true);
    }

    @Override // com.google.android.gms.internal.measurement.zzsr
    public final boolean zza() {
        return true;
    }

    @Override // com.google.android.gms.internal.measurement.zzsr
    public final boolean zzb() {
        return zza.zza().booleanValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzsr
    public final boolean zzc() {
        return zzb.zza().booleanValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzsr
    public final boolean zzd() {
        return zzc.zza().booleanValue();
    }
}
