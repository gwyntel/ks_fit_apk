package com.google.android.gms.internal.fitness;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.fitness.result.DataSourcesResult;

/* loaded from: classes3.dex */
public final class zzbo extends zza implements zzbq {
    zzbo(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.fitness.internal.IDataSourcesCallback");
    }

    @Override // com.google.android.gms.internal.fitness.zzbq
    public final void zzb(DataSourcesResult dataSourcesResult) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzc(parcelD, dataSourcesResult);
        f(1, parcelD);
    }
}
