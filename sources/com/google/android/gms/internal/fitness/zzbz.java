package com.google.android.gms.internal.fitness;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.fitness.request.GoalsReadRequest;

/* loaded from: classes3.dex */
public final class zzbz extends zza implements IInterface {
    zzbz(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.fitness.internal.IGoogleFitGoalsApi");
    }

    public final void zzd(GoalsReadRequest goalsReadRequest) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzc(parcelD, goalsReadRequest);
        e(1, parcelD);
    }
}
