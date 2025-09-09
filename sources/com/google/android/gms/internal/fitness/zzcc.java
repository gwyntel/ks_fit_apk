package com.google.android.gms.internal.fitness;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.fitness.request.DataSourcesRequest;

/* loaded from: classes3.dex */
public final class zzcc extends zza implements IInterface {
    zzcc(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.fitness.internal.IGoogleFitSensorsApi");
    }

    public final void zzd(DataSourcesRequest dataSourcesRequest) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzc(parcelD, dataSourcesRequest);
        e(1, parcelD);
    }

    public final void zze(com.google.android.gms.fitness.request.zzak zzakVar) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzc(parcelD, zzakVar);
        e(2, parcelD);
    }

    public final void zzf(com.google.android.gms.fitness.request.zzan zzanVar) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzc(parcelD, zzanVar);
        e(3, parcelD);
    }
}
