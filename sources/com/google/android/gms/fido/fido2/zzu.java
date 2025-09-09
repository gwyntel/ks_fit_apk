package com.google.android.gms.fido.fido2;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.TaskCompletionSource;

/* loaded from: classes3.dex */
final class zzu extends com.google.android.gms.internal.fido.zzd {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ TaskCompletionSource f12936a;

    zzu(Fido2PrivilegedApiClient fido2PrivilegedApiClient, TaskCompletionSource taskCompletionSource) {
        this.f12936a = taskCompletionSource;
    }

    @Override // com.google.android.gms.internal.fido.zze
    public final void zzb(boolean z2) {
        this.f12936a.setResult(Boolean.valueOf(z2));
    }

    @Override // com.google.android.gms.internal.fido.zze
    public final void zzc(Status status) {
        this.f12936a.trySetException(new ApiException(status));
    }
}
