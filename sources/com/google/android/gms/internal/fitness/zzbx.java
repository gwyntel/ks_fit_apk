package com.google.android.gms.internal.fitness;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.fitness.request.StartBleScanRequest;

/* loaded from: classes3.dex */
public final class zzbx extends zza implements IInterface {
    zzbx(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.fitness.internal.IGoogleFitBleApi");
    }

    public final void zzd(com.google.android.gms.fitness.request.zzf zzfVar) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzc(parcelD, zzfVar);
        e(3, parcelD);
    }

    public final void zze(com.google.android.gms.fitness.request.zzac zzacVar) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzc(parcelD, zzacVar);
        e(5, parcelD);
    }

    public final void zzf(StartBleScanRequest startBleScanRequest) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzc(parcelD, startBleScanRequest);
        e(1, parcelD);
    }

    public final void zzg(com.google.android.gms.fitness.request.zzbb zzbbVar) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzc(parcelD, zzbbVar);
        e(2, parcelD);
    }

    public final void zzh(com.google.android.gms.fitness.request.zzbf zzbfVar) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzc(parcelD, zzbfVar);
        e(4, parcelD);
    }
}
