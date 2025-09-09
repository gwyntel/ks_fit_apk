package com.google.android.gms.internal.p000authapi;

import android.app.PendingIntent;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.TaskUtil;
import com.google.android.gms.tasks.TaskCompletionSource;

/* loaded from: classes3.dex */
final class zbap extends zbo {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ TaskCompletionSource f12975a;

    zbap(zbaq zbaqVar, TaskCompletionSource taskCompletionSource) {
        this.f12975a = taskCompletionSource;
    }

    @Override // com.google.android.gms.internal.p000authapi.zbp
    public final void zbb(Status status, PendingIntent pendingIntent) {
        TaskUtil.setResultOrApiException(status, pendingIntent, this.f12975a);
    }
}
