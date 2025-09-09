package com.google.android.gms.internal.measurement;

/* loaded from: classes3.dex */
public final class zzqt implements zzqu {
    private static final zzgl<Boolean> zza = new zzgt(zzgm.zza("com.google.android.gms.measurement")).zza().zza("measurement.client.firebase_feature_rollout.v1.enable", true);

    @Override // com.google.android.gms.internal.measurement.zzqu
    public final boolean zza() {
        return true;
    }

    @Override // com.google.android.gms.internal.measurement.zzqu
    public final boolean zzb() {
        return zza.zza().booleanValue();
    }
}
