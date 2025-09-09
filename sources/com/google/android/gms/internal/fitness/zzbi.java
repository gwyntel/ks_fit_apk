package com.google.android.gms.internal.fitness;

import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.fitness.result.DailyTotalResult;

/* loaded from: classes3.dex */
public final class zzbi extends zza implements zzbk {
    zzbi(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.fitness.internal.IDailyTotalCallback");
    }

    @Override // com.google.android.gms.internal.fitness.zzbk
    public final void zzd(DailyTotalResult dailyTotalResult) throws RemoteException {
        throw null;
    }
}
