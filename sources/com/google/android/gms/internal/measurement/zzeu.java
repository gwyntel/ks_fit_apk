package com.google.android.gms.internal.measurement;

import android.app.Activity;
import android.os.RemoteException;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.internal.measurement.zzdf;

/* loaded from: classes3.dex */
final class zzeu extends zzdf.zza {
    private final /* synthetic */ Activity zzc;
    private final /* synthetic */ zzcs zzd;
    private final /* synthetic */ zzdf.zzd zze;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzeu(zzdf.zzd zzdVar, Activity activity, zzcs zzcsVar) {
        super(zzdf.this);
        this.zze = zzdVar;
        this.zzc = activity;
        this.zzd = zzcsVar;
    }

    @Override // com.google.android.gms.internal.measurement.zzdf.zza
    final void zza() throws RemoteException {
        ((zzcu) Preconditions.checkNotNull(zzdf.this.zzj)).onActivitySaveInstanceState(ObjectWrapper.wrap(this.zzc), this.zzd, this.f13189b);
    }
}
