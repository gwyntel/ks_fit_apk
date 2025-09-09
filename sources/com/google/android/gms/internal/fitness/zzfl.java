package com.google.android.gms.internal.fitness;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import javax.annotation.CheckForNull;

/* loaded from: classes3.dex */
final class zzfl extends zzfm {
    final transient int zza;
    final transient int zzb;
    final /* synthetic */ zzfm zzc;

    zzfl(zzfm zzfmVar, int i2, int i3) {
        this.zzc = zzfmVar;
        this.zza = i2;
        this.zzb = i3;
    }

    @Override // java.util.List
    public final Object get(int i2) {
        zzff.zza(i2, this.zzb, FirebaseAnalytics.Param.INDEX);
        return this.zzc.get(i2 + this.zza);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.zzb;
    }

    @Override // com.google.android.gms.internal.fitness.zzfm, java.util.List
    public final /* bridge */ /* synthetic */ List subList(int i2, int i3) {
        return subList(i2, i3);
    }

    @Override // com.google.android.gms.internal.fitness.zzfj
    final int zzb() {
        return this.zzc.zzc() + this.zza + this.zzb;
    }

    @Override // com.google.android.gms.internal.fitness.zzfj
    final int zzc() {
        return this.zzc.zzc() + this.zza;
    }

    @Override // com.google.android.gms.internal.fitness.zzfj
    @CheckForNull
    final Object[] zze() {
        return this.zzc.zze();
    }

    @Override // com.google.android.gms.internal.fitness.zzfm
    /* renamed from: zzf */
    public final zzfm subList(int i2, int i3) {
        zzff.zzc(i2, i3, this.zzb);
        zzfm zzfmVar = this.zzc;
        int i4 = this.zza;
        return zzfmVar.subList(i2 + i4, i3 + i4);
    }
}
