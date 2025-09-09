package com.google.android.gms.internal.measurement;

import java.io.Serializable;

/* loaded from: classes3.dex */
public final class zzhr {
    public static <T> zzhs<T> zza(zzhs<T> zzhsVar) {
        return ((zzhsVar instanceof zzht) || (zzhsVar instanceof zzhu)) ? zzhsVar : zzhsVar instanceof Serializable ? new zzhu(zzhsVar) : new zzht(zzhsVar);
    }

    public static <T> zzhs<T> zza(T t2) {
        return new zzhv(t2);
    }
}
