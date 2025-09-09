package com.google.android.gms.internal.fitness;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.fitness.request.SessionInsertRequest;
import com.google.android.gms.fitness.request.SessionReadRequest;

/* loaded from: classes3.dex */
public final class zzcd extends zza implements IInterface {
    zzcd(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.fitness.internal.IGoogleFitSessionsApi");
    }

    public final void zzd(SessionInsertRequest sessionInsertRequest) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzc(parcelD, sessionInsertRequest);
        e(3, parcelD);
    }

    public final void zze(SessionReadRequest sessionReadRequest) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzc(parcelD, sessionReadRequest);
        e(4, parcelD);
    }

    public final void zzf(com.google.android.gms.fitness.request.zzar zzarVar) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzc(parcelD, zzarVar);
        e(5, parcelD);
    }

    public final void zzg(com.google.android.gms.fitness.request.zzat zzatVar) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzc(parcelD, zzatVar);
        e(1, parcelD);
    }

    public final void zzh(com.google.android.gms.fitness.request.zzav zzavVar) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzc(parcelD, zzavVar);
        e(2, parcelD);
    }

    public final void zzi(com.google.android.gms.fitness.request.zzax zzaxVar) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzc(parcelD, zzaxVar);
        e(6, parcelD);
    }
}
