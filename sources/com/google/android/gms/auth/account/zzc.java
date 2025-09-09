package com.google.android.gms.auth.account;

import android.accounts.Account;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public final class zzc extends com.google.android.gms.internal.auth.zza implements zze {
    zzc(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.auth.account.IWorkAccountService");
    }

    @Override // com.google.android.gms.auth.account.zze
    public final void zzd(zzb zzbVar, String str) throws RemoteException {
        Parcel parcelD = d();
        com.google.android.gms.internal.auth.zzc.zzd(parcelD, zzbVar);
        parcelD.writeString(str);
        f(2, parcelD);
    }

    @Override // com.google.android.gms.auth.account.zze
    public final void zze(zzb zzbVar, Account account) throws RemoteException {
        Parcel parcelD = d();
        com.google.android.gms.internal.auth.zzc.zzd(parcelD, zzbVar);
        com.google.android.gms.internal.auth.zzc.zzc(parcelD, account);
        f(3, parcelD);
    }

    @Override // com.google.android.gms.auth.account.zze
    public final void zzf(boolean z2) throws RemoteException {
        Parcel parcelD = d();
        int i2 = com.google.android.gms.internal.auth.zzc.zza;
        parcelD.writeInt(z2 ? 1 : 0);
        f(1, parcelD);
    }
}
