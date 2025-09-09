package com.google.android.gms.internal.measurement;

/* loaded from: classes3.dex */
public final class zzqz implements zzra {
    private static final zzgl<Boolean> zza;
    private static final zzgl<Boolean> zzb;
    private static final zzgl<Boolean> zzc;
    private static final zzgl<Boolean> zzd;

    static {
        zzgt zzgtVarZza = new zzgt(zzgm.zza("com.google.android.gms.measurement")).zza();
        zza = zzgtVarZza.zza("measurement.service.audience.fix_skip_audience_with_failed_filters", true);
        zzb = zzgtVarZza.zza("measurement.audience.refresh_event_count_filters_timestamp", false);
        zzc = zzgtVarZza.zza("measurement.audience.use_bundle_end_timestamp_for_non_sequence_property_filters", false);
        zzd = zzgtVarZza.zza("measurement.audience.use_bundle_timestamp_for_event_count_filters", false);
    }

    @Override // com.google.android.gms.internal.measurement.zzra
    public final boolean zza() {
        return true;
    }

    @Override // com.google.android.gms.internal.measurement.zzra
    public final boolean zzb() {
        return zzb.zza().booleanValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzra
    public final boolean zzc() {
        return zzc.zza().booleanValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzra
    public final boolean zzd() {
        return zzd.zza().booleanValue();
    }
}
