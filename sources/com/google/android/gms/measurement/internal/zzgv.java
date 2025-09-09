package com.google.android.gms.measurement.internal;

import java.util.List;

/* loaded from: classes3.dex */
final class zzgv implements com.google.android.gms.internal.measurement.zzv {
    private final /* synthetic */ zzgp zza;

    zzgv(zzgp zzgpVar) {
        this.zza = zzgpVar;
    }

    @Override // com.google.android.gms.internal.measurement.zzv
    public final void zza(com.google.android.gms.internal.measurement.zzs zzsVar, String str, List<String> list, boolean z2, boolean z3) throws IllegalStateException {
        int i2 = zzgx.f13282a[zzsVar.ordinal()];
        zzfu zzfuVarZzn = i2 != 1 ? i2 != 2 ? i2 != 3 ? i2 != 4 ? this.zza.zzj().zzn() : this.zza.zzj().zzp() : z2 ? this.zza.zzj().zzw() : !z3 ? this.zza.zzj().zzv() : this.zza.zzj().zzu() : z2 ? this.zza.zzj().zzm() : !z3 ? this.zza.zzj().zzh() : this.zza.zzj().zzg() : this.zza.zzj().zzc();
        int size = list.size();
        if (size == 1) {
            zzfuVarZzn.zza(str, list.get(0));
            return;
        }
        if (size == 2) {
            zzfuVarZzn.zza(str, list.get(0), list.get(1));
        } else if (size != 3) {
            zzfuVarZzn.zza(str);
        } else {
            zzfuVarZzn.zza(str, list.get(0), list.get(1), list.get(2));
        }
    }
}
