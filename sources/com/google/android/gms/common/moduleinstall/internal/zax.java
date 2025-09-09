package com.google.android.gms.common.moduleinstall.internal;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.IStatusCallback;
import com.google.android.gms.common.api.internal.TaskUtil;
import com.google.android.gms.tasks.TaskCompletionSource;

/* loaded from: classes3.dex */
final class zax extends IStatusCallback.Stub {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ TaskCompletionSource f12873a;

    zax(zay zayVar, TaskCompletionSource taskCompletionSource) {
        this.f12873a = taskCompletionSource;
    }

    @Override // com.google.android.gms.common.api.internal.IStatusCallback
    public final void onResult(Status status) {
        TaskUtil.trySetResultOrApiException(status, null, this.f12873a);
    }
}
