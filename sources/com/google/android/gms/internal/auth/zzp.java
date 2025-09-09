package com.google.android.gms.internal.auth;

import android.accounts.Account;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.auth.AccountChangeEventsRequest;
import com.google.android.gms.common.api.internal.IStatusCallback;

/* loaded from: classes3.dex */
public final class zzp extends zza implements IInterface {
    zzp(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.auth.account.data.IGoogleAuthService");
    }

    public final void zzd(IStatusCallback iStatusCallback, zzbw zzbwVar) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzd(parcelD, iStatusCallback);
        zzc.zzc(parcelD, zzbwVar);
        f(2, parcelD);
    }

    public final void zze(zzm zzmVar, AccountChangeEventsRequest accountChangeEventsRequest) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzd(parcelD, zzmVar);
        zzc.zzc(parcelD, accountChangeEventsRequest);
        f(4, parcelD);
    }

    public final void zzf(zzo zzoVar, Account account, String str, Bundle bundle) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzd(parcelD, zzoVar);
        zzc.zzc(parcelD, account);
        parcelD.writeString(str);
        zzc.zzc(parcelD, bundle);
        f(1, parcelD);
    }

    public final void zzg(zzk zzkVar, Account account) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzd(parcelD, zzkVar);
        zzc.zzc(parcelD, account);
        f(6, parcelD);
    }

    public final void zzh(zzk zzkVar, String str) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzd(parcelD, zzkVar);
        parcelD.writeString(str);
        f(3, parcelD);
    }
}
