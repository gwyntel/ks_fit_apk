package com.google.android.gms.internal.fitness;

import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.fitness.result.SessionStopResult;

/* loaded from: classes3.dex */
final class zzeo extends zzcl {
    private final BaseImplementation.ResultHolder zza;

    @Override // com.google.android.gms.internal.fitness.zzcm
    public final void zzd(SessionStopResult sessionStopResult) {
        this.zza.setResult(sessionStopResult);
    }
}
