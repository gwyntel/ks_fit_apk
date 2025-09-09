package com.google.android.gms.internal.p000authapi;

import android.os.RemoteException;
import com.google.android.gms.auth.api.identity.SavePasswordResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.TaskUtil;
import com.google.android.gms.tasks.TaskCompletionSource;

/* loaded from: classes3.dex */
final class zbaf extends zbu {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ TaskCompletionSource f12971a;

    zbaf(zbag zbagVar, TaskCompletionSource taskCompletionSource) {
        this.f12971a = taskCompletionSource;
    }

    @Override // com.google.android.gms.internal.p000authapi.zbv
    public final void zbb(Status status, SavePasswordResult savePasswordResult) throws RemoteException {
        TaskUtil.setResultOrApiException(status, savePasswordResult, this.f12971a);
    }
}
