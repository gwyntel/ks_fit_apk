package com.google.android.gms.internal.fitness;

import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.fitness.result.DataReadResult;

/* loaded from: classes3.dex */
public final class zzbl extends zza implements zzbn {
    zzbl(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.fitness.internal.IDataReadCallback");
    }

    @Override // com.google.android.gms.internal.fitness.zzbn
    public final void zzd(DataReadResult dataReadResult) throws RemoteException {
        throw null;
    }
}
