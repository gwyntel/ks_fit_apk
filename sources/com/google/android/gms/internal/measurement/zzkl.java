package com.google.android.gms.internal.measurement;

import java.util.NoSuchElementException;

/* loaded from: classes3.dex */
final class zzkl extends zzkn {
    private int zza = 0;
    private final int zzb;
    private final /* synthetic */ zzkm zzc;

    zzkl(zzkm zzkmVar) {
        this.zzc = zzkmVar;
        this.zzb = zzkmVar.zzb();
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.zza < this.zzb;
    }

    @Override // com.google.android.gms.internal.measurement.zzks
    public final byte zza() {
        int i2 = this.zza;
        if (i2 >= this.zzb) {
            throw new NoSuchElementException();
        }
        this.zza = i2 + 1;
        return this.zzc.zzb(i2);
    }
}
