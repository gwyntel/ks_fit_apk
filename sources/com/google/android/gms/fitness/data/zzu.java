package com.google.android.gms.fitness.data;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public abstract class zzu extends com.google.android.gms.internal.fitness.zzb implements zzv {
    public zzu() {
        super("com.google.android.gms.fitness.data.IDataSourceListener");
    }

    public static zzv zzb(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.fitness.data.IDataSourceListener");
        return iInterfaceQueryLocalInterface instanceof zzv ? (zzv) iInterfaceQueryLocalInterface : new zzt(iBinder);
    }

    @Override // com.google.android.gms.internal.fitness.zzb
    protected final boolean a(int i2, Parcel parcel, Parcel parcel2, int i3) throws RemoteException {
        if (i2 != 1) {
            return false;
        }
        DataPoint dataPoint = (DataPoint) com.google.android.gms.internal.fitness.zzc.zza(parcel, DataPoint.CREATOR);
        com.google.android.gms.internal.fitness.zzc.zzb(parcel);
        zzd(dataPoint);
        return true;
    }
}
