package com.google.android.gms.internal.p001authapiphone;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.TaskUtil;
import com.google.android.gms.tasks.TaskCompletionSource;

/* loaded from: classes3.dex */
final class zzaa extends zzi {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ TaskCompletionSource f12964a;

    zzaa(zzab zzabVar, TaskCompletionSource taskCompletionSource) {
        this.f12964a = taskCompletionSource;
    }

    @Override // com.google.android.gms.internal.p001authapiphone.zzj
    public final void zzb(Status status) {
        TaskUtil.setResultOrApiException(status, this.f12964a);
    }
}
