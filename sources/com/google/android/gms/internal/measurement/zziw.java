package com.google.android.gms.internal.measurement;

import java.util.List;
import javax.annotation.CheckForNull;

/* loaded from: classes3.dex */
final class zziw extends zzir {
    private final transient int zza;
    private final transient int zzb;
    private final /* synthetic */ zzir zzc;

    zziw(zzir zzirVar, int i2, int i3) {
        this.zzc = zzirVar;
        this.zza = i2;
        this.zzb = i3;
    }

    @Override // java.util.List
    public final Object get(int i2) {
        zzhn.zza(i2, this.zzb);
        return this.zzc.get(i2 + this.zza);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.zzb;
    }

    @Override // com.google.android.gms.internal.measurement.zzir, java.util.List
    public final /* synthetic */ List subList(int i2, int i3) {
        return subList(i2, i3);
    }

    @Override // com.google.android.gms.internal.measurement.zziq
    final int zza() {
        return this.zzc.zzb() + this.zza + this.zzb;
    }

    @Override // com.google.android.gms.internal.measurement.zziq
    final int zzb() {
        return this.zzc.zzb() + this.zza;
    }

    @Override // com.google.android.gms.internal.measurement.zziq
    final boolean zze() {
        return true;
    }

    @Override // com.google.android.gms.internal.measurement.zziq
    @CheckForNull
    final Object[] zzf() {
        return this.zzc.zzf();
    }

    @Override // com.google.android.gms.internal.measurement.zzir
    /* renamed from: zza */
    public final zzir subList(int i2, int i3) {
        zzhn.zza(i2, i3, this.zzb);
        zzir zzirVar = this.zzc;
        int i4 = this.zza;
        return (zzir) zzirVar.subList(i2 + i4, i3 + i4);
    }
}
