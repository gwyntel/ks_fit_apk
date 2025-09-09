package com.google.android.gms.internal.fitness;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.fitness.result.BleDevicesResult;

/* loaded from: classes3.dex */
public abstract class zzev extends zzb implements zzew {
    public zzev() {
        super("com.google.android.gms.fitness.internal.ble.IBleDevicesCallback");
    }

    public static zzew zzc(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.fitness.internal.ble.IBleDevicesCallback");
        return iInterfaceQueryLocalInterface instanceof zzew ? (zzew) iInterfaceQueryLocalInterface : new zzeu(iBinder);
    }

    @Override // com.google.android.gms.internal.fitness.zzb
    protected final boolean a(int i2, Parcel parcel, Parcel parcel2, int i3) throws RemoteException {
        if (i2 != 1) {
            return false;
        }
        BleDevicesResult bleDevicesResult = (BleDevicesResult) zzc.zza(parcel, BleDevicesResult.CREATOR);
        zzc.zzb(parcel);
        zzb(bleDevicesResult);
        return true;
    }
}
