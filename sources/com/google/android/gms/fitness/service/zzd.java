package com.google.android.gms.fitness.service;

import android.os.RemoteException;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.zzv;
import java.util.List;

/* loaded from: classes3.dex */
final class zzd implements SensorEventDispatcher {
    private final zzv zza;

    zzd(zzv zzvVar) {
        this.zza = (zzv) Preconditions.checkNotNull(zzvVar);
    }

    @Override // com.google.android.gms.fitness.service.SensorEventDispatcher
    public final void publish(DataPoint dataPoint) throws RemoteException {
        dataPoint.zzd();
        this.zza.zzd(dataPoint);
    }

    @Override // com.google.android.gms.fitness.service.SensorEventDispatcher
    public final void publish(List<DataPoint> list) throws RemoteException {
        for (DataPoint dataPoint : list) {
            dataPoint.zzd();
            this.zza.zzd(dataPoint);
        }
    }
}
