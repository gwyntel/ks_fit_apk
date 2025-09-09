package com.google.android.gms.internal.fitness;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.tasks.TaskCompletionSource;

/* loaded from: classes3.dex */
public final class zzes extends zzco {
    private final BaseImplementation.ResultHolder zza;

    public zzes(BaseImplementation.ResultHolder resultHolder) {
        this.zza = resultHolder;
    }

    public static zzes zzc(TaskCompletionSource taskCompletionSource) {
        return new zzes(new zzeq(taskCompletionSource));
    }

    public static zzes zze(TaskCompletionSource taskCompletionSource) {
        return new zzes(new zzer(taskCompletionSource));
    }

    @Override // com.google.android.gms.internal.fitness.zzcp
    public final void zzd(Status status) {
        this.zza.setResult(status);
    }
}
