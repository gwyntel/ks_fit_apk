package com.google.android.gms.internal.ads_identifier;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public final class zzd extends zza implements zzf {
    zzd(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
    }

    @Override // com.google.android.gms.internal.ads_identifier.zzf
    public final String zzc() throws RemoteException {
        Parcel parcelE = e(1, d());
        String string = parcelE.readString();
        parcelE.recycle();
        return string;
    }

    @Override // com.google.android.gms.internal.ads_identifier.zzf
    public final boolean zzd() throws RemoteException {
        Parcel parcelE = e(6, d());
        boolean zZzb = zzc.zzb(parcelE);
        parcelE.recycle();
        return zZzb;
    }

    @Override // com.google.android.gms.internal.ads_identifier.zzf
    public final boolean zze(boolean z2) throws RemoteException {
        Parcel parcelD = d();
        zzc.zza(parcelD, true);
        Parcel parcelE = e(2, parcelD);
        boolean zZzb = zzc.zzb(parcelE);
        parcelE.recycle();
        return zZzb;
    }
}
