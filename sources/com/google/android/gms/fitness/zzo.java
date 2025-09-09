package com.google.android.gms.fitness;

import android.app.PendingIntent;
import android.os.RemoteException;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.fitness.data.zzv;
import com.google.android.gms.fitness.request.zzah;
import com.google.android.gms.fitness.request.zzaj;
import com.google.android.gms.fitness.request.zzan;
import com.google.android.gms.internal.fitness.zzaz;
import com.google.android.gms.internal.fitness.zzcc;
import com.google.android.gms.internal.fitness.zzcp;
import com.google.android.gms.internal.fitness.zzes;
import com.google.android.gms.tasks.TaskCompletionSource;

/* loaded from: classes3.dex */
final class zzo implements RemoteCall {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ ListenerHolder f12961a;

    zzo(SensorsClient sensorsClient, ListenerHolder listenerHolder) {
        this.f12961a = listenerHolder;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.common.api.internal.RemoteCall
    public final /* bridge */ /* synthetic */ void accept(Object obj, Object obj2) throws RemoteException {
        zzaz zzazVar = (zzaz) obj;
        TaskCompletionSource taskCompletionSource = (TaskCompletionSource) obj2;
        zzaj zzajVarZzd = zzah.zza().zzd(this.f12961a);
        if (zzajVarZzd == null) {
            taskCompletionSource.setResult(Boolean.FALSE);
        } else {
            ((zzcc) zzazVar.getService()).zzf(new zzan((zzv) zzajVarZzd, (PendingIntent) null, (zzcp) zzes.zze(taskCompletionSource)));
        }
    }
}
