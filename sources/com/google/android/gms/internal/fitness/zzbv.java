package com.google.android.gms.internal.fitness;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.fitness.result.GoalsResult;

/* loaded from: classes3.dex */
public abstract class zzbv extends zzb implements zzbw {
    public zzbv() {
        super("com.google.android.gms.fitness.internal.IGoalsReadCallback");
    }

    public static zzbw zzb(IBinder iBinder) {
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.fitness.internal.IGoalsReadCallback");
        return iInterfaceQueryLocalInterface instanceof zzbw ? (zzbw) iInterfaceQueryLocalInterface : new zzbu(iBinder);
    }

    @Override // com.google.android.gms.internal.fitness.zzb
    protected final boolean a(int i2, Parcel parcel, Parcel parcel2, int i3) throws RemoteException {
        if (i2 != 1) {
            return false;
        }
        GoalsResult goalsResult = (GoalsResult) zzc.zza(parcel, GoalsResult.CREATOR);
        zzc.zzb(parcel);
        zzd(goalsResult);
        return true;
    }
}
