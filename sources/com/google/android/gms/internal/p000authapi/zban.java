package com.google.android.gms.internal.p000authapi;

import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.IStatusCallback;
import com.google.android.gms.common.api.internal.TaskUtil;
import com.google.android.gms.tasks.TaskCompletionSource;

/* loaded from: classes3.dex */
final class zban extends IStatusCallback.Stub {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ TaskCompletionSource f12973a;

    zban(zbaq zbaqVar, TaskCompletionSource taskCompletionSource) {
        this.f12973a = taskCompletionSource;
    }

    @Override // com.google.android.gms.common.api.internal.IStatusCallback
    public final void onResult(Status status) throws RemoteException {
        TaskUtil.setResultOrApiException(status, this.f12973a);
    }
}
