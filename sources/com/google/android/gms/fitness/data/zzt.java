package com.google.android.gms.fitness.data;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public final class zzt extends com.google.android.gms.internal.fitness.zza implements zzv {
    zzt(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.fitness.data.IDataSourceListener");
    }

    @Override // com.google.android.gms.fitness.data.zzv
    public final void zzd(DataPoint dataPoint) throws RemoteException {
        Parcel parcelD = d();
        com.google.android.gms.internal.fitness.zzc.zzc(parcelD, dataPoint);
        f(1, parcelD);
    }
}
