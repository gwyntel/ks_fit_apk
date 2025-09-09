package com.google.android.gms.internal.fitness;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.api.Status;

/* loaded from: classes3.dex */
public final class zzcn extends zza implements zzcp {
    zzcn(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.fitness.internal.IStatusCallback");
    }

    @Override // com.google.android.gms.internal.fitness.zzcp
    public final void zzd(Status status) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzc(parcelD, status);
        f(1, parcelD);
    }
}
