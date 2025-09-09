package com.google.android.gms.internal.measurement;

/* loaded from: classes3.dex */
public final class zzha {
    private final boolean zza;

    public zzha(zzgz zzgzVar) {
        zzhn.zza(zzgzVar, "BuildInfo must be non-null");
        this.zza = !zzgzVar.zza();
    }

    public final boolean zza(String str) {
        zzhn.zza(str, "flagName must not be null");
        if (this.zza) {
            return zzhc.zza.zza().zza(str);
        }
        return true;
    }
}
