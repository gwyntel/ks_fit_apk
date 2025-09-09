package com.google.android.gms.internal.identity;

import android.os.RemoteException;
import com.google.android.gms.common.api.internal.TaskUtil;
import com.google.android.gms.location.zzz;
import com.google.android.gms.tasks.TaskCompletionSource;

/* loaded from: classes3.dex */
final class zzdo extends zzq {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ TaskCompletionSource f13158a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ zzz f13159b;

    zzdo(TaskCompletionSource taskCompletionSource, zzz zzzVar) {
        this.f13158a = taskCompletionSource;
        this.f13159b = zzzVar;
    }

    @Override // com.google.android.gms.internal.identity.zzr
    public final void zzd(zzl zzlVar) {
        TaskUtil.setResultOrApiException(zzlVar.getStatus(), this.f13158a);
    }

    @Override // com.google.android.gms.internal.identity.zzr
    public final void zze() throws RemoteException {
        this.f13159b.zze();
    }
}
