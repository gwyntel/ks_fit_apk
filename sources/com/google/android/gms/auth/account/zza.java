package com.google.android.gms.auth.account;

import android.accounts.Account;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public abstract class zza extends com.google.android.gms.internal.auth.zzb implements zzb {
    public zza() {
        super("com.google.android.gms.auth.account.IWorkAccountCallback");
    }

    @Override // com.google.android.gms.internal.auth.zzb
    protected final boolean a(int i2, Parcel parcel, Parcel parcel2, int i3) throws RemoteException {
        if (i2 != 1) {
            if (i2 != 2) {
                return false;
            }
            int i4 = com.google.android.gms.internal.auth.zzc.zza;
            int i5 = parcel.readInt();
            com.google.android.gms.internal.auth.zzc.zzb(parcel);
            zzc(i5 != 0);
        } else {
            Account account = (Account) com.google.android.gms.internal.auth.zzc.zza(parcel, Account.CREATOR);
            com.google.android.gms.internal.auth.zzc.zzb(parcel);
            zzb(account);
        }
        return true;
    }
}
