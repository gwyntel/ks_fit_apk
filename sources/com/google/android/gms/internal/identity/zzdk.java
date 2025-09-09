package com.google.android.gms.internal.identity;

import android.location.Location;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.TaskUtil;
import com.google.android.gms.tasks.TaskCompletionSource;

/* loaded from: classes3.dex */
final class zzdk extends zzy {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ TaskCompletionSource f13153a;

    zzdk(TaskCompletionSource taskCompletionSource) {
        this.f13153a = taskCompletionSource;
    }

    @Override // com.google.android.gms.internal.identity.zzz
    public final void zzb(Status status, Location location) {
        TaskUtil.setResultOrApiException(status, location, this.f13153a);
    }
}
