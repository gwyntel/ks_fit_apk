package com.google.android.gms.internal.fitness;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public final class zzcb extends zza implements IInterface {
    zzcb(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.fitness.internal.IGoogleFitRecordingApi");
    }

    public final void zzd(com.google.android.gms.fitness.request.zzae zzaeVar) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzc(parcelD, zzaeVar);
        e(3, parcelD);
    }

    public final void zze(com.google.android.gms.fitness.request.zzbd zzbdVar) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzc(parcelD, zzbdVar);
        e(1, parcelD);
    }

    public final void zzf(com.google.android.gms.fitness.request.zzbh zzbhVar) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzc(parcelD, zzbhVar);
        e(2, parcelD);
    }
}
