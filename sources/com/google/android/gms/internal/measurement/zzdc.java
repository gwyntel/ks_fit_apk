package com.google.android.gms.internal.measurement;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public final class zzdc extends zzbu implements zzda {
    zzdc(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.measurement.api.internal.IEventHandlerProxy");
    }

    @Override // com.google.android.gms.internal.measurement.zzda
    public final int zza() throws RemoteException {
        Parcel parcelE = e(2, d());
        int i2 = parcelE.readInt();
        parcelE.recycle();
        return i2;
    }

    @Override // com.google.android.gms.internal.measurement.zzda
    public final void zza(String str, String str2, Bundle bundle, long j2) throws RemoteException {
        Parcel parcelD = d();
        parcelD.writeString(str);
        parcelD.writeString(str2);
        zzbw.zza(parcelD, bundle);
        parcelD.writeLong(j2);
        f(1, parcelD);
    }
}
