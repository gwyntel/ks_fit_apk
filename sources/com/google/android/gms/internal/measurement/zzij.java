package com.google.android.gms.internal.measurement;

import java.util.Map;

/* loaded from: classes3.dex */
final class zzij extends zzhx {
    private final Object zza;
    private int zzb;
    private final /* synthetic */ zzib zzc;

    zzij(zzib zzibVar, int i2) {
        this.zzc = zzibVar;
        this.zza = zzib.zza(zzibVar, i2);
        this.zzb = i2;
    }

    private final void zza() {
        int i2 = this.zzb;
        if (i2 == -1 || i2 >= this.zzc.size() || !zzhl.zza(this.zza, zzib.zza(this.zzc, this.zzb))) {
            this.zzb = this.zzc.zza(this.zza);
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzhx, java.util.Map.Entry
    public final Object getKey() {
        return this.zza;
    }

    @Override // com.google.android.gms.internal.measurement.zzhx, java.util.Map.Entry
    public final Object getValue() {
        Map mapZzf = this.zzc.zzf();
        if (mapZzf != null) {
            return mapZzf.get(this.zza);
        }
        zza();
        int i2 = this.zzb;
        if (i2 == -1) {
            return null;
        }
        return zzib.zzb(this.zzc, i2);
    }

    @Override // com.google.android.gms.internal.measurement.zzhx, java.util.Map.Entry
    public final Object setValue(Object obj) {
        Map mapZzf = this.zzc.zzf();
        if (mapZzf != null) {
            return mapZzf.put(this.zza, obj);
        }
        zza();
        int i2 = this.zzb;
        if (i2 == -1) {
            this.zzc.put(this.zza, obj);
            return null;
        }
        Object objZzb = zzib.zzb(this.zzc, i2);
        zzib.zza(this.zzc, this.zzb, obj);
        return objZzb;
    }
}
