package com.google.android.gms.internal.measurement;

import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.measurement.zzdf;

/* loaded from: classes3.dex */
final class zzdj extends zzdf.zza {
    private final /* synthetic */ String zzc;
    private final /* synthetic */ String zzd;
    private final /* synthetic */ zzcs zze;
    private final /* synthetic */ zzdf zzf;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzdj(zzdf zzdfVar, String str, String str2, zzcs zzcsVar) {
        super(zzdfVar);
        this.zzf = zzdfVar;
        this.zzc = str;
        this.zzd = str2;
        this.zze = zzcsVar;
    }

    @Override // com.google.android.gms.internal.measurement.zzdf.zza
    protected final void a() {
        this.zze.zza((Bundle) null);
    }

    @Override // com.google.android.gms.internal.measurement.zzdf.zza
    final void zza() throws RemoteException {
        ((zzcu) Preconditions.checkNotNull(this.zzf.zzj)).getConditionalUserProperties(this.zzc, this.zzd, this.zze);
    }
}
