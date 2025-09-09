package com.google.android.gms.internal.measurement;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public final class zzca extends zzbu implements zzby {
    zzca(IBinder iBinder) {
        super(iBinder, "com.google.android.finsky.externalreferrer.IGetInstallReferrerService");
    }

    @Override // com.google.android.gms.internal.measurement.zzby
    public final Bundle zza(Bundle bundle) throws RemoteException {
        Parcel parcelD = d();
        zzbw.zza(parcelD, bundle);
        Parcel parcelE = e(1, parcelD);
        Bundle bundle2 = (Bundle) zzbw.zza(parcelE, Bundle.CREATOR);
        parcelE.recycle();
        return bundle2;
    }
}
