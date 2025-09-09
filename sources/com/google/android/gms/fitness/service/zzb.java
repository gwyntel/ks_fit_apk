package com.google.android.gms.fitness.service;

import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.result.DataSourcesResult;
import com.google.android.gms.internal.fitness.zzbq;
import com.google.android.gms.internal.fitness.zzcp;
import com.google.android.gms.internal.fitness.zzex;
import com.google.android.gms.internal.fitness.zzez;
import com.google.android.gms.internal.fitness.zzfb;

/* loaded from: classes3.dex */
final class zzb extends zzfb {
    private final FitnessSensorService zza;

    @Override // com.google.android.gms.internal.fitness.zzfc
    public final void zzb(zzex zzexVar, zzbq zzbqVar) throws RemoteException {
        this.zza.a();
        zzbqVar.zzb(new DataSourcesResult(this.zza.onFindDataSources(zzexVar.zza()), Status.RESULT_SUCCESS));
    }

    @Override // com.google.android.gms.internal.fitness.zzfc
    public final void zzc(FitnessSensorServiceRequest fitnessSensorServiceRequest, zzcp zzcpVar) throws RemoteException {
        this.zza.a();
        if (this.zza.onRegister(fitnessSensorServiceRequest)) {
            zzcpVar.zzd(Status.RESULT_SUCCESS);
        } else {
            zzcpVar.zzd(new Status(13));
        }
    }

    @Override // com.google.android.gms.internal.fitness.zzfc
    public final void zzd(zzez zzezVar, zzcp zzcpVar) throws RemoteException {
        this.zza.a();
        if (this.zza.onUnregister(zzezVar.zza())) {
            zzcpVar.zzd(Status.RESULT_SUCCESS);
        } else {
            zzcpVar.zzd(new Status(13));
        }
    }
}
