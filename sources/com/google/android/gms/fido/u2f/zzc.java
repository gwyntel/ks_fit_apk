package com.google.android.gms.fido.u2f;

import android.app.PendingIntent;
import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.TaskUtil;
import com.google.android.gms.internal.fido.zzt;
import com.google.android.gms.internal.fido.zzu;
import com.google.android.gms.tasks.TaskCompletionSource;

/* loaded from: classes3.dex */
final class zzc extends zzu {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ TaskCompletionSource f12953a;

    zzc(U2fApiClient u2fApiClient, TaskCompletionSource taskCompletionSource) {
        this.f12953a = taskCompletionSource;
    }

    @Override // com.google.android.gms.internal.fido.zzv
    public final void zzb(Status status, PendingIntent pendingIntent) throws RemoteException {
        TaskUtil.setResultOrApiException(status, new zzt(pendingIntent), this.f12953a);
    }
}
