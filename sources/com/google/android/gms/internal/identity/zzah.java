package com.google.android.gms.internal.identity;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.IStatusCallback;
import com.google.android.gms.common.api.internal.TaskUtil;
import com.google.android.gms.tasks.TaskCompletionSource;

/* loaded from: classes3.dex */
final class zzah extends IStatusCallback.Stub {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ TaskCompletionSource f13104a;

    zzah(zzaj zzajVar, TaskCompletionSource taskCompletionSource) {
        this.f13104a = taskCompletionSource;
    }

    @Override // com.google.android.gms.common.api.internal.IStatusCallback
    public final void onResult(Status status) {
        TaskUtil.setResultOrApiException(status, this.f13104a);
    }
}
