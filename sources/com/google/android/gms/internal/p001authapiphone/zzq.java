package com.google.android.gms.internal.p001authapiphone;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.TaskUtil;
import com.google.android.gms.tasks.TaskCompletionSource;

/* loaded from: classes3.dex */
final class zzq extends zzf {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ TaskCompletionSource f12967a;

    zzq(zzr zzrVar, TaskCompletionSource taskCompletionSource) {
        this.f12967a = taskCompletionSource;
    }

    @Override // com.google.android.gms.internal.p001authapiphone.zzg
    public final void zzb(Status status, boolean z2) {
        TaskUtil.setResultOrApiException(status, Boolean.valueOf(z2), this.f12967a);
    }
}
