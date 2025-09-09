package com.google.android.gms.internal.fitness;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.fitness.result.DailyTotalResult;

/* loaded from: classes3.dex */
public abstract class zzbj extends zzb implements zzbk {
    public zzbj() {
        super("com.google.android.gms.fitness.internal.IDailyTotalCallback");
    }

    public static zzbk zzb(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.fitness.internal.IDailyTotalCallback");
        return iInterfaceQueryLocalInterface instanceof zzbk ? (zzbk) iInterfaceQueryLocalInterface : new zzbi(iBinder);
    }

    @Override // com.google.android.gms.internal.fitness.zzb
    protected final boolean a(int i2, Parcel parcel, Parcel parcel2, int i3) throws RemoteException {
        if (i2 != 1) {
            return false;
        }
        DailyTotalResult dailyTotalResult = (DailyTotalResult) zzc.zza(parcel, DailyTotalResult.CREATOR);
        zzc.zzb(parcel);
        zzd(dailyTotalResult);
        return true;
    }
}
