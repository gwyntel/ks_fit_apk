package com.google.android.gms.internal.fitness;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.fitness.request.DataDeleteRequest;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.request.DataUpdateListenerRegistrationRequest;
import com.google.android.gms.fitness.request.DataUpdateRequest;

/* loaded from: classes3.dex */
public final class zzca extends zza implements IInterface {
    zzca(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.fitness.internal.IGoogleFitHistoryApi");
    }

    public final void zzd(DataDeleteRequest dataDeleteRequest) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzc(parcelD, dataDeleteRequest);
        e(3, parcelD);
    }

    public final void zze(com.google.android.gms.fitness.request.zzk zzkVar) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzc(parcelD, zzkVar);
        e(2, parcelD);
    }

    public final void zzf(com.google.android.gms.fitness.request.zzh zzhVar) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzc(parcelD, zzhVar);
        e(7, parcelD);
    }

    public final void zzg(DataReadRequest dataReadRequest) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzc(parcelD, dataReadRequest);
        e(1, parcelD);
    }

    public final void zzh(DataUpdateListenerRegistrationRequest dataUpdateListenerRegistrationRequest) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzc(parcelD, dataUpdateListenerRegistrationRequest);
        e(10, parcelD);
    }

    public final void zzi(com.google.android.gms.fitness.request.zzs zzsVar) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzc(parcelD, zzsVar);
        e(11, parcelD);
    }

    public final void zzj(DataUpdateRequest dataUpdateRequest) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzc(parcelD, dataUpdateRequest);
        e(9, parcelD);
    }
}
