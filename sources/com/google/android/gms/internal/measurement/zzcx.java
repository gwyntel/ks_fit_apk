package com.google.android.gms.internal.measurement;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public final class zzcx extends zzbu implements zzcv {
    zzcx(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.measurement.api.internal.IBundleReceiver");
    }

    @Override // com.google.android.gms.internal.measurement.zzcv
    public final void zza(Bundle bundle) throws RemoteException {
        Parcel parcelD = d();
        zzbw.zza(parcelD, bundle);
        f(1, parcelD);
    }
}
