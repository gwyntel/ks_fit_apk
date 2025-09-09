package com.google.android.gms.internal.p000authapi;

import android.os.RemoteException;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.TaskUtil;
import com.google.android.gms.tasks.TaskCompletionSource;

/* loaded from: classes3.dex */
final class zbam extends zbl {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ TaskCompletionSource f12972a;

    zbam(zbaq zbaqVar, TaskCompletionSource taskCompletionSource) {
        this.f12972a = taskCompletionSource;
    }

    @Override // com.google.android.gms.internal.p000authapi.zbm
    public final void zbb(Status status, BeginSignInResult beginSignInResult) throws RemoteException {
        TaskUtil.setResultOrApiException(status, beginSignInResult, this.f12972a);
    }
}
