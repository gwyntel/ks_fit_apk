package com.google.android.gms.internal.fitness;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.fitness.request.DataTypeCreateRequest;

/* loaded from: classes3.dex */
public final class zzby extends zza implements IInterface {
    zzby(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.fitness.internal.IGoogleFitConfigApi");
    }

    public final void zzd(DataTypeCreateRequest dataTypeCreateRequest) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzc(parcelD, dataTypeCreateRequest);
        e(1, parcelD);
    }

    public final void zze(com.google.android.gms.fitness.request.zzv zzvVar) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzc(parcelD, zzvVar);
        e(22, parcelD);
    }

    public final void zzf(com.google.android.gms.fitness.request.zzp zzpVar) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzc(parcelD, zzpVar);
        e(2, parcelD);
    }
}
