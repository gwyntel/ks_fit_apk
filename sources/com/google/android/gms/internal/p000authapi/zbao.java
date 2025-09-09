package com.google.android.gms.internal.p000authapi;

import android.app.PendingIntent;
import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.TaskUtil;
import com.google.android.gms.tasks.TaskCompletionSource;

/* loaded from: classes3.dex */
final class zbao extends zbq {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ TaskCompletionSource f12974a;

    zbao(zbaq zbaqVar, TaskCompletionSource taskCompletionSource) {
        this.f12974a = taskCompletionSource;
    }

    @Override // com.google.android.gms.internal.p000authapi.zbr
    public final void zbb(Status status, PendingIntent pendingIntent) throws RemoteException {
        TaskUtil.setResultOrApiException(status, pendingIntent, this.f12974a);
    }
}
