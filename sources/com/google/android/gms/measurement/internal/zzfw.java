package com.google.android.gms.measurement.internal;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* loaded from: classes3.dex */
public final class zzfw {

    @NonNull
    public String zza;

    @NonNull
    public Bundle zzb;

    @NonNull
    private String zzc;
    private long zzd;

    private zzfw(@NonNull String str, @NonNull String str2, @Nullable Bundle bundle, long j2) {
        this.zza = str;
        this.zzc = str2;
        this.zzb = bundle == null ? new Bundle() : bundle;
        this.zzd = j2;
    }

    public final String toString() {
        return "origin=" + this.zzc + ",name=" + this.zza + ",params=" + String.valueOf(this.zzb);
    }

    public final zzbg zza() {
        return new zzbg(this.zza, new zzbb(new Bundle(this.zzb)), this.zzc, this.zzd);
    }

    public static zzfw zza(zzbg zzbgVar) {
        return new zzfw(zzbgVar.zza, zzbgVar.zzc, zzbgVar.zzb.zzb(), zzbgVar.zzd);
    }
}
