package com.google.android.gms.common.moduleinstall.internal;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.TaskUtil;
import com.google.android.gms.tasks.TaskCompletionSource;

/* loaded from: classes3.dex */
final class zas extends zaa {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ TaskCompletionSource f12865a;

    zas(zay zayVar, TaskCompletionSource taskCompletionSource) {
        this.f12865a = taskCompletionSource;
    }

    @Override // com.google.android.gms.common.moduleinstall.internal.zaa, com.google.android.gms.common.moduleinstall.internal.zae
    public final void zab(Status status) {
        TaskUtil.trySetResultOrApiException(status, null, this.f12865a);
    }
}
