package com.google.android.gms.internal.fitness;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.fitness.result.DataSourcesResult;

/* loaded from: classes3.dex */
public abstract class zzbp extends zzb implements zzbq {
    public zzbp() {
        super("com.google.android.gms.fitness.internal.IDataSourcesCallback");
    }

    public static zzbq zzc(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.fitness.internal.IDataSourcesCallback");
        return iInterfaceQueryLocalInterface instanceof zzbq ? (zzbq) iInterfaceQueryLocalInterface : new zzbo(iBinder);
    }

    @Override // com.google.android.gms.internal.fitness.zzb
    protected final boolean a(int i2, Parcel parcel, Parcel parcel2, int i3) throws RemoteException {
        if (i2 != 1) {
            return false;
        }
        DataSourcesResult dataSourcesResult = (DataSourcesResult) zzc.zza(parcel, DataSourcesResult.CREATOR);
        zzc.zzb(parcel);
        zzb(dataSourcesResult);
        return true;
    }
}
