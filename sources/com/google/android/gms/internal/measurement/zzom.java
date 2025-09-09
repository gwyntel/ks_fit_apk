package com.google.android.gms.internal.measurement;

import java.util.Iterator;
import java.util.Map;

/* loaded from: classes3.dex */
final class zzom implements Iterator {
    private int zza;
    private boolean zzb;
    private Iterator zzc;
    private final /* synthetic */ zzoc zzd;

    private final Iterator zza() {
        if (this.zzc == null) {
            this.zzc = this.zzd.zzc.entrySet().iterator();
        }
        return this.zzc;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.zza + 1 < this.zzd.zzb.size() || (!this.zzd.zzc.isEmpty() && zza().hasNext());
    }

    @Override // java.util.Iterator
    public final /* synthetic */ Object next() {
        this.zzb = true;
        int i2 = this.zza + 1;
        this.zza = i2;
        return i2 < this.zzd.zzb.size() ? (Map.Entry) this.zzd.zzb.get(this.zza) : (Map.Entry) zza().next();
    }

    @Override // java.util.Iterator
    public final void remove() {
        if (!this.zzb) {
            throw new IllegalStateException("remove() was called before next()");
        }
        this.zzb = false;
        this.zzd.zzg();
        if (this.zza >= this.zzd.zzb.size()) {
            zza().remove();
            return;
        }
        zzoc zzocVar = this.zzd;
        int i2 = this.zza;
        this.zza = i2 - 1;
        zzocVar.zzc(i2);
    }

    private zzom(zzoc zzocVar) {
        this.zzd = zzocVar;
        this.zza = -1;
    }
}
