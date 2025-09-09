package com.google.android.gms.internal.measurement;

/* loaded from: classes3.dex */
public final class zztb implements zztc {
    private static final zzgl<Boolean> zza;
    private static final zzgl<Boolean> zzb;

    static {
        zzgt zzgtVarZza = new zzgt(zzgm.zza("com.google.android.gms.measurement")).zzb().zza();
        zza = zzgtVarZza.zza("measurement.sgtm.client.dev", false);
        zzb = zzgtVarZza.zza("measurement.sgtm.service", false);
    }

    @Override // com.google.android.gms.internal.measurement.zztc
    public final boolean zza() {
        return true;
    }

    @Override // com.google.android.gms.internal.measurement.zztc
    public final boolean zzb() {
        return zza.zza().booleanValue();
    }

    @Override // com.google.android.gms.internal.measurement.zztc
    public final boolean zzc() {
        return zzb.zza().booleanValue();
    }
}
