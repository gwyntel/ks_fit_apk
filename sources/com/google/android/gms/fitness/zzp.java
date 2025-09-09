package com.google.android.gms.fitness;

import android.os.RemoteException;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.fitness.request.SensorRequest;
import com.google.android.gms.fitness.request.zzah;
import com.google.android.gms.fitness.request.zzak;
import com.google.android.gms.internal.fitness.zzaz;
import com.google.android.gms.internal.fitness.zzcc;
import com.google.android.gms.internal.fitness.zzes;
import com.google.android.gms.tasks.TaskCompletionSource;

/* loaded from: classes3.dex */
final class zzp implements RemoteCall {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ ListenerHolder f12962a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ SensorRequest f12963b;

    zzp(SensorsClient sensorsClient, ListenerHolder listenerHolder, SensorRequest sensorRequest) {
        this.f12962a = listenerHolder;
        this.f12963b = sensorRequest;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.common.api.internal.RemoteCall
    public final /* bridge */ /* synthetic */ void accept(Object obj, Object obj2) throws RemoteException {
        ((zzcc) ((zzaz) obj).getService()).zze(new zzak(this.f12963b, zzah.zza().zzb(this.f12962a), null, zzes.zzc((TaskCompletionSource) obj2)));
    }
}
