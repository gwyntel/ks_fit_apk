package com.google.android.gms.internal.identity;

import android.os.RemoteException;
import com.google.android.gms.common.api.internal.TaskUtil;
import com.google.android.gms.location.zzw;
import com.google.android.gms.tasks.TaskCompletionSource;

/* loaded from: classes3.dex */
final class zzdd extends zzq {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ TaskCompletionSource f13142a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ zzw f13143b;

    zzdd(TaskCompletionSource taskCompletionSource, zzw zzwVar) {
        this.f13142a = taskCompletionSource;
        this.f13143b = zzwVar;
    }

    @Override // com.google.android.gms.internal.identity.zzr
    public final void zzd(zzl zzlVar) {
        TaskUtil.setResultOrApiException(zzlVar.getStatus(), this.f13142a);
    }

    @Override // com.google.android.gms.internal.identity.zzr
    public final void zze() throws RemoteException {
        this.f13143b.zzf();
    }
}
