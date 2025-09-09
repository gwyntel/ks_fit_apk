package com.google.android.gms.internal.identity;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.IStatusCallback;
import com.google.android.gms.common.api.internal.TaskUtil;
import com.google.android.gms.tasks.TaskCompletionSource;

/* loaded from: classes3.dex */
final class zzdj extends IStatusCallback.Stub {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Object f13151a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ TaskCompletionSource f13152b;

    zzdj(Object obj, TaskCompletionSource taskCompletionSource) {
        this.f13151a = obj;
        this.f13152b = taskCompletionSource;
    }

    @Override // com.google.android.gms.common.api.internal.IStatusCallback
    public final void onResult(Status status) {
        TaskUtil.setResultOrApiException(status, this.f13151a, this.f13152b);
    }
}
