package com.google.android.gms.location;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public abstract class zzs extends com.google.android.gms.internal.identity.zzb implements zzt {
    public zzs() {
        super("com.google.android.gms.location.IDeviceOrientationListener");
    }

    public static zzt zzb(IBinder iBinder) {
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.location.IDeviceOrientationListener");
        return iInterfaceQueryLocalInterface instanceof zzt ? (zzt) iInterfaceQueryLocalInterface : new zzr(iBinder);
    }

    @Override // com.google.android.gms.internal.identity.zzb
    protected final boolean a(int i2, Parcel parcel, Parcel parcel2, int i3) throws RemoteException {
        if (i2 != 1) {
            return false;
        }
        DeviceOrientation deviceOrientation = (DeviceOrientation) com.google.android.gms.internal.identity.zzc.zza(parcel, DeviceOrientation.CREATOR);
        com.google.android.gms.internal.identity.zzc.zzd(parcel);
        zzd(deviceOrientation);
        return true;
    }
}
