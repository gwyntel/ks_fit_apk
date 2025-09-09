package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public final class zzw extends com.google.android.gms.internal.common.zza implements IAccountAccessor {
    zzw(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.common.internal.IAccountAccessor");
    }

    @Override // com.google.android.gms.common.internal.IAccountAccessor
    public final Account zzb() throws RemoteException {
        Parcel parcelA = a(2, d());
        Account account = (Account) com.google.android.gms.internal.common.zzc.zza(parcelA, Account.CREATOR);
        parcelA.recycle();
        return account;
    }
}
