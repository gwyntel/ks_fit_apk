package com.google.android.gms.internal.fitness;

import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.fitness.result.SessionStopResult;

/* loaded from: classes3.dex */
public final class zzck extends zza implements zzcm {
    zzck(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.fitness.internal.ISessionStopCallback");
    }

    @Override // com.google.android.gms.internal.fitness.zzcm
    public final void zzd(SessionStopResult sessionStopResult) throws RemoteException {
        throw null;
    }
}
