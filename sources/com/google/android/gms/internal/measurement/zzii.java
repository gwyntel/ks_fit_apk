package com.google.android.gms.internal.measurement;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/* loaded from: classes3.dex */
abstract class zzii<T> implements Iterator<T> {
    private int zza;
    private int zzb;
    private int zzc;
    private final /* synthetic */ zzib zzd;

    private final void zza() {
        if (this.zzd.zzf != this.zza) {
            throw new ConcurrentModificationException();
        }
    }

    abstract Object a(int i2);

    @Override // java.util.Iterator
    public boolean hasNext() {
        return this.zzb >= 0;
    }

    @Override // java.util.Iterator
    public T next() {
        zza();
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        int i2 = this.zzb;
        this.zzc = i2;
        T t2 = (T) a(i2);
        this.zzb = this.zzd.zza(this.zzb);
        return t2;
    }

    @Override // java.util.Iterator
    public void remove() {
        zza();
        zzhn.zzb(this.zzc >= 0, "no calls to next() since the last call to remove()");
        this.zza += 32;
        zzib zzibVar = this.zzd;
        zzibVar.remove(zzib.zza(zzibVar, this.zzc));
        this.zzb = zzib.zza(this.zzb, this.zzc);
        this.zzc = -1;
    }

    private zzii(zzib zzibVar) {
        this.zzd = zzibVar;
        this.zza = zzibVar.zzf;
        this.zzb = zzibVar.zza();
        this.zzc = -1;
    }
}
