package com.google.android.gms.internal.measurement;

import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.measurement.zzdf;

/* loaded from: classes3.dex */
final class zzeb extends zzdf.zza {
    private final /* synthetic */ Bundle zzc;
    private final /* synthetic */ zzcs zzd;
    private final /* synthetic */ zzdf zze;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzeb(zzdf zzdfVar, Bundle bundle, zzcs zzcsVar) {
        super(zzdfVar);
        this.zze = zzdfVar;
        this.zzc = bundle;
        this.zzd = zzcsVar;
    }

    @Override // com.google.android.gms.internal.measurement.zzdf.zza
    protected final void a() {
        this.zzd.zza((Bundle) null);
    }

    @Override // com.google.android.gms.internal.measurement.zzdf.zza
    final void zza() throws RemoteException {
        ((zzcu) Preconditions.checkNotNull(this.zze.zzj)).performAction(this.zzc, this.zzd, this.f13188a);
    }
}
