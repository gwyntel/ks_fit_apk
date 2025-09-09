package com.google.android.gms.internal.auth;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.IStatusCallback;
import com.google.android.gms.tasks.TaskCompletionSource;

/* loaded from: classes3.dex */
final class zzx extends IStatusCallback.Stub {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ TaskCompletionSource f13031a;

    zzx(zzab zzabVar, TaskCompletionSource taskCompletionSource) {
        this.f13031a = taskCompletionSource;
    }

    @Override // com.google.android.gms.common.api.internal.IStatusCallback
    public final void onResult(Status status) {
        zzab.d(status, null, this.f13031a);
    }
}
