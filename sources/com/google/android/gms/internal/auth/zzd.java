package com.google.android.gms.internal.auth;

import android.accounts.Account;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.auth.AccountChangeEventsRequest;
import com.google.android.gms.auth.AccountChangeEventsResponse;

/* loaded from: classes3.dex */
public final class zzd extends zza implements zzf {
    zzd(IBinder iBinder) {
        super(iBinder, "com.google.android.auth.IAuthManagerService");
    }

    @Override // com.google.android.gms.internal.auth.zzf
    public final Bundle zzd(String str, Bundle bundle) throws RemoteException {
        Parcel parcelD = d();
        parcelD.writeString(str);
        zzc.zzc(parcelD, bundle);
        Parcel parcelE = e(2, parcelD);
        Bundle bundle2 = (Bundle) zzc.zza(parcelE, Bundle.CREATOR);
        parcelE.recycle();
        return bundle2;
    }

    @Override // com.google.android.gms.internal.auth.zzf
    public final Bundle zze(Account account, String str, Bundle bundle) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzc(parcelD, account);
        parcelD.writeString(str);
        zzc.zzc(parcelD, bundle);
        Parcel parcelE = e(5, parcelD);
        Bundle bundle2 = (Bundle) zzc.zza(parcelE, Bundle.CREATOR);
        parcelE.recycle();
        return bundle2;
    }

    @Override // com.google.android.gms.internal.auth.zzf
    public final Bundle zzf(Account account) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzc(parcelD, account);
        Parcel parcelE = e(7, parcelD);
        Bundle bundle = (Bundle) zzc.zza(parcelE, Bundle.CREATOR);
        parcelE.recycle();
        return bundle;
    }

    @Override // com.google.android.gms.internal.auth.zzf
    public final Bundle zzg(String str) throws RemoteException {
        Parcel parcelD = d();
        parcelD.writeString(str);
        Parcel parcelE = e(8, parcelD);
        Bundle bundle = (Bundle) zzc.zza(parcelE, Bundle.CREATOR);
        parcelE.recycle();
        return bundle;
    }

    @Override // com.google.android.gms.internal.auth.zzf
    public final AccountChangeEventsResponse zzh(AccountChangeEventsRequest accountChangeEventsRequest) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzc(parcelD, accountChangeEventsRequest);
        Parcel parcelE = e(3, parcelD);
        AccountChangeEventsResponse accountChangeEventsResponse = (AccountChangeEventsResponse) zzc.zza(parcelE, AccountChangeEventsResponse.CREATOR);
        parcelE.recycle();
        return accountChangeEventsResponse;
    }
}
