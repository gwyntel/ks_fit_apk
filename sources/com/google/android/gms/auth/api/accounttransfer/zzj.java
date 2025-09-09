package com.google.android.gms.auth.api.accounttransfer;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.auth.zzan;
import com.google.android.gms.tasks.TaskCompletionSource;

/* loaded from: classes3.dex */
class zzj extends zzan {

    /* renamed from: a, reason: collision with root package name */
    final zzl f12629a;

    public zzj(zzl zzlVar) {
        this.f12629a = zzlVar;
    }

    @Override // com.google.android.gms.internal.auth.zzan, com.google.android.gms.internal.auth.zzat
    public final void zzd(Status status) {
        TaskCompletionSource taskCompletionSource = this.f12629a.f12630a;
        int i2 = AccountTransferClient.zza;
        taskCompletionSource.setException(new AccountTransferException(status));
    }
}
