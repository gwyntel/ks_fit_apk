package com.google.android.gms.internal.auth;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.TaskCompletionSource;

/* loaded from: classes3.dex */
final class zzw extends zzn {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ TaskCompletionSource f13030a;

    zzw(zzab zzabVar, TaskCompletionSource taskCompletionSource) {
        this.f13030a = taskCompletionSource;
    }

    @Override // com.google.android.gms.internal.auth.zzo
    public final void zzb(Status status, @Nullable Bundle bundle) {
        zzab.d(status, bundle, this.f13030a);
    }
}
