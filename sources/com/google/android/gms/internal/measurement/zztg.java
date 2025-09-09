package com.google.android.gms.internal.measurement;

/* loaded from: classes3.dex */
public final class zztg implements zztd {
    private static final zzgl<Boolean> zza = new zzgt(zzgm.zza("com.google.android.gms.measurement")).zza().zza("measurement.integration.disable_firebase_instance_id", false);

    @Override // com.google.android.gms.internal.measurement.zztd
    public final boolean zza() {
        return true;
    }

    @Override // com.google.android.gms.internal.measurement.zztd
    public final boolean zzb() {
        return zza.zza().booleanValue();
    }
}
