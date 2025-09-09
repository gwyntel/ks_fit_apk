package com.google.android.gms.internal.auth;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public final class zzau extends zza implements IInterface {
    zzau(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.auth.api.accounttransfer.internal.IAccountTransferService");
    }

    public final void zzd(zzat zzatVar, zzaq zzaqVar) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzd(parcelD, zzatVar);
        zzc.zzc(parcelD, zzaqVar);
        f(7, parcelD);
    }

    public final void zze(zzat zzatVar, zzbb zzbbVar) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzd(parcelD, zzatVar);
        zzc.zzc(parcelD, zzbbVar);
        f(8, parcelD);
    }

    public final void zzf(zzat zzatVar, zzav zzavVar) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzd(parcelD, zzatVar);
        zzc.zzc(parcelD, zzavVar);
        f(9, parcelD);
    }

    public final void zzg(zzat zzatVar, zzax zzaxVar) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzd(parcelD, zzatVar);
        zzc.zzc(parcelD, zzaxVar);
        f(6, parcelD);
    }

    public final void zzh(zzat zzatVar, zzaz zzazVar) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzd(parcelD, zzatVar);
        zzc.zzc(parcelD, zzazVar);
        f(5, parcelD);
    }
}
