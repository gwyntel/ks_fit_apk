package com.google.android.gms.internal.p000authapi;

import android.os.RemoteException;
import com.google.android.gms.auth.api.identity.AuthorizationResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.ApiExceptionUtil;
import com.google.android.gms.tasks.TaskCompletionSource;

/* loaded from: classes3.dex */
final class zbz extends zbi {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ TaskCompletionSource f12976a;

    zbz(zbaa zbaaVar, TaskCompletionSource taskCompletionSource) {
        this.f12976a = taskCompletionSource;
    }

    @Override // com.google.android.gms.internal.p000authapi.zbj
    public final void zbb(Status status, AuthorizationResult authorizationResult) throws RemoteException {
        if (status.isSuccess()) {
            this.f12976a.setResult(authorizationResult);
        } else {
            this.f12976a.setException(ApiExceptionUtil.fromStatus(status));
        }
    }
}
