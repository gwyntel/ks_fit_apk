package com.google.android.gms.internal.fitness;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.fitness.result.ListSubscriptionsResult;

/* loaded from: classes3.dex */
public abstract class zzcf extends zzb implements zzcg {
    public zzcf() {
        super("com.google.android.gms.fitness.internal.IListSubscriptionsCallback");
    }

    public static zzcg zzb(IBinder iBinder) {
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.fitness.internal.IListSubscriptionsCallback");
        return iInterfaceQueryLocalInterface instanceof zzcg ? (zzcg) iInterfaceQueryLocalInterface : new zzce(iBinder);
    }

    @Override // com.google.android.gms.internal.fitness.zzb
    protected final boolean a(int i2, Parcel parcel, Parcel parcel2, int i3) throws RemoteException {
        if (i2 != 1) {
            return false;
        }
        ListSubscriptionsResult listSubscriptionsResult = (ListSubscriptionsResult) zzc.zza(parcel, ListSubscriptionsResult.CREATOR);
        zzc.zzb(parcel);
        zzd(listSubscriptionsResult);
        return true;
    }
}
