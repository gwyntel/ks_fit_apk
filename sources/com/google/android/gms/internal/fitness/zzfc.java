package com.google.android.gms.internal.fitness;

import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.fitness.service.FitnessSensorServiceRequest;

/* loaded from: classes3.dex */
public interface zzfc extends IInterface {
    void zzb(zzex zzexVar, zzbq zzbqVar) throws RemoteException;

    void zzc(FitnessSensorServiceRequest fitnessSensorServiceRequest, zzcp zzcpVar) throws RemoteException;

    void zzd(zzez zzezVar, zzcp zzcpVar) throws RemoteException;
}
