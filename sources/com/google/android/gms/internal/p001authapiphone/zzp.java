package com.google.android.gms.internal.p001authapiphone;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.TaskUtil;
import com.google.android.gms.tasks.TaskCompletionSource;

/* loaded from: classes3.dex */
final class zzp extends zzd {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ TaskCompletionSource f12966a;

    zzp(zzr zzrVar, TaskCompletionSource taskCompletionSource) {
        this.f12966a = taskCompletionSource;
    }

    @Override // com.google.android.gms.internal.p001authapiphone.zze
    public final void zzb(Status status, int i2) {
        TaskUtil.setResultOrApiException(status, Integer.valueOf(i2), this.f12966a);
    }
}
