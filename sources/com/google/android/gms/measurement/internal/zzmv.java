package com.google.android.gms.measurement.internal;

import android.os.Bundle;
import com.google.android.gms.common.internal.Preconditions;

/* loaded from: classes3.dex */
final class zzmv implements Runnable {
    private final /* synthetic */ String zza;
    private final /* synthetic */ String zzb;
    private final /* synthetic */ Bundle zzc;
    private final /* synthetic */ zzmt zzd;

    zzmv(zzmt zzmtVar, String str, String str2, Bundle bundle) {
        this.zzd = zzmtVar;
        this.zza = str;
        this.zzb = str2;
        this.zzc = bundle;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzd.f13320a.g((zzbg) Preconditions.checkNotNull(this.zzd.f13320a.zzq().i(this.zza, this.zzb, this.zzc, "auto", this.zzd.f13320a.zzb().currentTimeMillis(), false, true)), this.zza);
    }
}
