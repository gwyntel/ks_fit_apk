package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public final class zzab extends com.google.android.gms.internal.common.zza implements IGmsCallbacks {
    zzab(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.common.internal.IGmsCallbacks");
    }

    @Override // com.google.android.gms.common.internal.IGmsCallbacks
    public final void onPostInitComplete(int i2, IBinder iBinder, Bundle bundle) throws RemoteException {
        Parcel parcelD = d();
        parcelD.writeInt(i2);
        parcelD.writeStrongBinder(iBinder);
        com.google.android.gms.internal.common.zzc.zzc(parcelD, bundle);
        b(1, parcelD);
    }

    @Override // com.google.android.gms.common.internal.IGmsCallbacks
    public final void zzb(int i2, Bundle bundle) throws RemoteException {
        throw null;
    }

    @Override // com.google.android.gms.common.internal.IGmsCallbacks
    public final void zzc(int i2, IBinder iBinder, zzk zzkVar) throws RemoteException {
        throw null;
    }
}
