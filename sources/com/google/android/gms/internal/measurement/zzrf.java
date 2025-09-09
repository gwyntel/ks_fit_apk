package com.google.android.gms.internal.measurement;

/* loaded from: classes3.dex */
public final class zzrf implements zzrg {
    private static final zzgl<Boolean> zza;
    private static final zzgl<Boolean> zzb;
    private static final zzgl<Boolean> zzc;

    static {
        zzgt zzgtVarZza = new zzgt(zzgm.zza("com.google.android.gms.measurement")).zza();
        zza = zzgtVarZza.zza("measurement.client.sessions.check_on_reset_and_enable2", true);
        zzb = zzgtVarZza.zza("measurement.client.sessions.check_on_startup", true);
        zzc = zzgtVarZza.zza("measurement.client.sessions.start_session_before_view_screen", true);
    }

    @Override // com.google.android.gms.internal.measurement.zzrg
    public final boolean zza() {
        return true;
    }

    @Override // com.google.android.gms.internal.measurement.zzrg
    public final boolean zzb() {
        return zza.zza().booleanValue();
    }
}
