package com.google.android.gms.fido.fido2;

import android.os.RemoteException;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.TaskCompletionSource;

/* loaded from: classes3.dex */
final class zzj extends com.google.android.gms.internal.fido.zzd {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ TaskCompletionSource f12931a;

    zzj(Fido2ApiClient fido2ApiClient, TaskCompletionSource taskCompletionSource) {
        this.f12931a = taskCompletionSource;
    }

    @Override // com.google.android.gms.internal.fido.zze
    public final void zzb(boolean z2) throws RemoteException {
        this.f12931a.setResult(Boolean.valueOf(z2));
    }

    @Override // com.google.android.gms.internal.fido.zze
    public final void zzc(Status status) throws RemoteException {
        this.f12931a.trySetException(new ApiException(status));
    }
}
