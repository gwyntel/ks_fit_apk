package com.google.android.gms.internal.p001authapiphone;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.api.internal.IStatusCallback;

/* loaded from: classes3.dex */
public final class zzh extends zza implements IInterface {
    zzh(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.auth.api.phone.internal.ISmsRetrieverApiService");
    }

    public final void zzc(zze zzeVar) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzc(parcelD, zzeVar);
        e(4, parcelD);
    }

    public final void zzd(String str, zzg zzgVar) throws RemoteException {
        Parcel parcelD = d();
        parcelD.writeString(str);
        zzc.zzc(parcelD, zzgVar);
        e(5, parcelD);
    }

    public final void zze(IStatusCallback iStatusCallback) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzc(parcelD, iStatusCallback);
        e(3, parcelD);
    }

    public final void zzf(IStatusCallback iStatusCallback) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzc(parcelD, iStatusCallback);
        e(6, parcelD);
    }

    public final void zzg(zzj zzjVar) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzc(parcelD, zzjVar);
        e(1, parcelD);
    }

    public final void zzh(String str, zzj zzjVar) throws RemoteException {
        Parcel parcelD = d();
        parcelD.writeString(str);
        zzc.zzc(parcelD, zzjVar);
        e(2, parcelD);
    }
}
