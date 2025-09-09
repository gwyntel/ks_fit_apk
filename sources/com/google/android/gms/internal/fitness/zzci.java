package com.google.android.gms.internal.fitness;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.fitness.result.SessionReadResult;

/* loaded from: classes3.dex */
public abstract class zzci extends zzb implements zzcj {
    public zzci() {
        super("com.google.android.gms.fitness.internal.ISessionReadCallback");
    }

    public static zzcj zzb(IBinder iBinder) {
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.fitness.internal.ISessionReadCallback");
        return iInterfaceQueryLocalInterface instanceof zzcj ? (zzcj) iInterfaceQueryLocalInterface : new zzch(iBinder);
    }

    @Override // com.google.android.gms.internal.fitness.zzb
    protected final boolean a(int i2, Parcel parcel, Parcel parcel2, int i3) throws RemoteException {
        if (i2 != 1) {
            return false;
        }
        SessionReadResult sessionReadResult = (SessionReadResult) zzc.zza(parcel, SessionReadResult.CREATOR);
        zzc.zzb(parcel);
        zzd(sessionReadResult);
        return true;
    }
}
