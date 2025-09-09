package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;

/* loaded from: classes3.dex */
public final class zzae extends com.google.android.gms.internal.common.zza implements zzag {
    zzae(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.common.internal.IGoogleCertificatesApi");
    }

    @Override // com.google.android.gms.common.internal.zzag
    public final com.google.android.gms.common.zzq zze(com.google.android.gms.common.zzo zzoVar) throws RemoteException {
        Parcel parcelD = d();
        com.google.android.gms.internal.common.zzc.zzc(parcelD, zzoVar);
        Parcel parcelA = a(6, parcelD);
        com.google.android.gms.common.zzq zzqVar = (com.google.android.gms.common.zzq) com.google.android.gms.internal.common.zzc.zza(parcelA, com.google.android.gms.common.zzq.CREATOR);
        parcelA.recycle();
        return zzqVar;
    }

    @Override // com.google.android.gms.common.internal.zzag
    public final com.google.android.gms.common.zzq zzf(com.google.android.gms.common.zzo zzoVar) throws RemoteException {
        Parcel parcelD = d();
        com.google.android.gms.internal.common.zzc.zzc(parcelD, zzoVar);
        Parcel parcelA = a(8, parcelD);
        com.google.android.gms.common.zzq zzqVar = (com.google.android.gms.common.zzq) com.google.android.gms.internal.common.zzc.zza(parcelA, com.google.android.gms.common.zzq.CREATOR);
        parcelA.recycle();
        return zzqVar;
    }

    @Override // com.google.android.gms.common.internal.zzag
    public final boolean zzg() throws RemoteException {
        Parcel parcelA = a(9, d());
        boolean zZzf = com.google.android.gms.internal.common.zzc.zzf(parcelA);
        parcelA.recycle();
        return zZzf;
    }

    @Override // com.google.android.gms.common.internal.zzag
    public final boolean zzh(com.google.android.gms.common.zzs zzsVar, IObjectWrapper iObjectWrapper) throws RemoteException {
        Parcel parcelD = d();
        com.google.android.gms.internal.common.zzc.zzc(parcelD, zzsVar);
        com.google.android.gms.internal.common.zzc.zze(parcelD, iObjectWrapper);
        Parcel parcelA = a(5, parcelD);
        boolean zZzf = com.google.android.gms.internal.common.zzc.zzf(parcelA);
        parcelA.recycle();
        return zZzf;
    }

    @Override // com.google.android.gms.common.internal.zzag
    public final boolean zzi() throws RemoteException {
        Parcel parcelA = a(7, d());
        boolean zZzf = com.google.android.gms.internal.common.zzc.zzf(parcelA);
        parcelA.recycle();
        return zZzf;
    }
}
