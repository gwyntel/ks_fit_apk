package com.google.android.gms.internal.measurement;

import java.io.Serializable;

/* loaded from: classes3.dex */
public abstract class zzho<T> implements Serializable {
    zzho() {
    }

    public static <T> zzho<T> zza(T t2) {
        return new zzhq(zzhn.zza(t2));
    }

    public static <T> zzho<T> zzc() {
        return zzhe.zza;
    }

    public abstract T zza();

    public abstract boolean zzb();
}
