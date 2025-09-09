package com.google.android.gms.fido.fido2;

import android.app.PendingIntent;
import androidx.annotation.Nullable;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.TaskUtil;
import com.google.android.gms.tasks.TaskCompletionSource;

/* loaded from: classes3.dex */
final class zzr extends com.google.android.gms.internal.fido.zzl {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ TaskCompletionSource f12933a;

    zzr(Fido2PrivilegedApiClient fido2PrivilegedApiClient, TaskCompletionSource taskCompletionSource) {
        this.f12933a = taskCompletionSource;
    }

    @Override // com.google.android.gms.internal.fido.zzm
    public final void zzb(Status status, @Nullable PendingIntent pendingIntent) {
        TaskUtil.setResultOrApiException(status, pendingIntent, this.f12933a);
    }
}
