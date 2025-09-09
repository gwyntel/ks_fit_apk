package com.google.android.gms.fido.fido2;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.List;

/* loaded from: classes3.dex */
final class zzv extends com.google.android.gms.internal.fido.zzf {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ TaskCompletionSource f12937a;

    zzv(Fido2PrivilegedApiClient fido2PrivilegedApiClient, TaskCompletionSource taskCompletionSource) {
        this.f12937a = taskCompletionSource;
    }

    @Override // com.google.android.gms.internal.fido.zzg
    public final void zzb(List list) {
        this.f12937a.setResult(list);
    }

    @Override // com.google.android.gms.internal.fido.zzg
    public final void zzc(Status status) {
        this.f12937a.trySetException(new ApiException(status));
    }
}
