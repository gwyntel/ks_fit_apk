package com.google.android.gms.internal.measurement;

/* loaded from: classes3.dex */
public final class zzrw implements zzrt {
    private static final zzgl<Boolean> zza;
    private static final zzgl<Boolean> zzb;
    private static final zzgl<Boolean> zzc;
    private static final zzgl<Long> zzd;

    static {
        zzgt zzgtVarZza = new zzgt(zzgm.zza("com.google.android.gms.measurement")).zza();
        zza = zzgtVarZza.zza("measurement.sdk.collection.enable_extend_user_property_size", true);
        zzb = zzgtVarZza.zza("measurement.sdk.collection.last_deep_link_referrer2", true);
        zzc = zzgtVarZza.zza("measurement.sdk.collection.last_deep_link_referrer_campaign2", false);
        zzd = zzgtVarZza.zza("measurement.id.sdk.collection.last_deep_link_referrer2", 0L);
    }

    @Override // com.google.android.gms.internal.measurement.zzrt
    public final boolean zza() {
        return zzc.zza().booleanValue();
    }
}
