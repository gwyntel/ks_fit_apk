package com.google.android.gms.fido.fido2;

import android.app.PendingIntent;
import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.TaskUtil;
import com.google.android.gms.tasks.TaskCompletionSource;

/* loaded from: classes3.dex */
final class zzf extends com.google.android.gms.internal.fido.zzq {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ TaskCompletionSource f12927a;

    zzf(Fido2ApiClient fido2ApiClient, TaskCompletionSource taskCompletionSource) {
        this.f12927a = taskCompletionSource;
    }

    @Override // com.google.android.gms.internal.fido.zzr
    public final void zzb(Status status, PendingIntent pendingIntent) throws RemoteException {
        TaskUtil.setResultOrApiException(status, pendingIntent, this.f12927a);
    }
}
