package com.google.android.gms.internal.measurement;

import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.measurement.zzdf;

/* loaded from: classes3.dex */
final class zzel extends zzdf.zza {
    private final /* synthetic */ Long zzc;
    private final /* synthetic */ String zzd;
    private final /* synthetic */ String zze;
    private final /* synthetic */ Bundle zzf;
    private final /* synthetic */ boolean zzg;
    private final /* synthetic */ boolean zzh;
    private final /* synthetic */ zzdf zzi;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzel(zzdf zzdfVar, Long l2, String str, String str2, Bundle bundle, boolean z2, boolean z3) {
        super(zzdfVar);
        this.zzi = zzdfVar;
        this.zzc = l2;
        this.zzd = str;
        this.zze = str2;
        this.zzf = bundle;
        this.zzg = z2;
        this.zzh = z3;
    }

    @Override // com.google.android.gms.internal.measurement.zzdf.zza
    final void zza() throws RemoteException {
        Long l2 = this.zzc;
        ((zzcu) Preconditions.checkNotNull(this.zzi.zzj)).logEvent(this.zzd, this.zze, this.zzf, this.zzg, this.zzh, l2 == null ? this.f13188a : l2.longValue());
    }
}
