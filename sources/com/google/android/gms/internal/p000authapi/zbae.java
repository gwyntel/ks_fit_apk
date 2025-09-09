package com.google.android.gms.internal.p000authapi;

import android.os.RemoteException;
import com.google.android.gms.auth.api.identity.SaveAccountLinkingTokenResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.ApiExceptionUtil;
import com.google.android.gms.tasks.TaskCompletionSource;

/* loaded from: classes3.dex */
final class zbae extends zbs {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ TaskCompletionSource f12970a;

    zbae(zbag zbagVar, TaskCompletionSource taskCompletionSource) {
        this.f12970a = taskCompletionSource;
    }

    @Override // com.google.android.gms.internal.p000authapi.zbt
    public final void zbb(Status status, SaveAccountLinkingTokenResult saveAccountLinkingTokenResult) throws RemoteException {
        if (status.isSuccess()) {
            this.f12970a.setResult(saveAccountLinkingTokenResult);
        } else {
            this.f12970a.setException(ApiExceptionUtil.fromStatus(status));
        }
    }
}
