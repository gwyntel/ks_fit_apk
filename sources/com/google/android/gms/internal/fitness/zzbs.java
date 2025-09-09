package com.google.android.gms.internal.fitness;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.fitness.result.DataTypeResult;

/* loaded from: classes3.dex */
public abstract class zzbs extends zzb implements zzbt {
    public zzbs() {
        super("com.google.android.gms.fitness.internal.IDataTypeCallback");
    }

    public static zzbt zzb(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.fitness.internal.IDataTypeCallback");
        return iInterfaceQueryLocalInterface instanceof zzbt ? (zzbt) iInterfaceQueryLocalInterface : new zzbr(iBinder);
    }

    @Override // com.google.android.gms.internal.fitness.zzb
    protected final boolean a(int i2, Parcel parcel, Parcel parcel2, int i3) throws RemoteException {
        if (i2 != 1) {
            return false;
        }
        DataTypeResult dataTypeResult = (DataTypeResult) zzc.zza(parcel, DataTypeResult.CREATOR);
        zzc.zzb(parcel);
        zzd(dataTypeResult);
        return true;
    }
}
