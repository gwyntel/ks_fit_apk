package com.google.android.gms.internal.measurement;

import java.util.AbstractMap;

/* loaded from: classes3.dex */
final class zzjj extends zzir {
    private final /* synthetic */ zzjk zza;

    zzjj(zzjk zzjkVar) {
        this.zza = zzjkVar;
    }

    @Override // java.util.List
    public final /* synthetic */ Object get(int i2) {
        zzhn.zza(i2, this.zza.zzd);
        int i3 = i2 * 2;
        Object obj = this.zza.zzb[i3];
        obj.getClass();
        Object obj2 = this.zza.zzb[i3 + 1];
        obj2.getClass();
        return new AbstractMap.SimpleImmutableEntry(obj, obj2);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.zza.zzd;
    }

    @Override // com.google.android.gms.internal.measurement.zziq
    public final boolean zze() {
        return true;
    }
}
