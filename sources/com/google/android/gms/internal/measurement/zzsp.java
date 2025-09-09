package com.google.android.gms.internal.measurement;

/* loaded from: classes3.dex */
public final class zzsp implements zzsq {
    private static final zzgl<Boolean> zza = new zzgt(zzgm.zza("com.google.android.gms.measurement")).zzb().zza().zza("measurement.sessionid.enable_client_session_id", true);

    @Override // com.google.android.gms.internal.measurement.zzsq
    public final boolean zza() {
        return true;
    }

    @Override // com.google.android.gms.internal.measurement.zzsq
    public final boolean zzb() {
        return zza.zza().booleanValue();
    }
}
