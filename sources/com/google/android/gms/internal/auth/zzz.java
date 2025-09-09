package com.google.android.gms.internal.auth;

import androidx.annotation.Nullable;
import com.google.android.gms.auth.AccountChangeEventsResponse;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.TaskCompletionSource;

/* loaded from: classes3.dex */
final class zzz extends zzl {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ TaskCompletionSource f13033a;

    zzz(zzab zzabVar, TaskCompletionSource taskCompletionSource) {
        this.f13033a = taskCompletionSource;
    }

    @Override // com.google.android.gms.internal.auth.zzm
    public final void zzb(Status status, @Nullable AccountChangeEventsResponse accountChangeEventsResponse) {
        zzab.d(status, accountChangeEventsResponse, this.f13033a);
    }
}
