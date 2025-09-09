package com.google.android.gms.internal.identity;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.TaskUtil;
import com.google.android.gms.tasks.TaskCompletionSource;

/* loaded from: classes3.dex */
final class zzdm extends zzn {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ TaskCompletionSource f13155a;

    zzdm(TaskCompletionSource taskCompletionSource) {
        this.f13155a = taskCompletionSource;
    }

    @Override // com.google.android.gms.internal.identity.zzo
    public final void zzb(Status status, boolean z2) {
        TaskUtil.setResultOrApiException(status, Boolean.valueOf(z2), this.f13155a);
    }
}
