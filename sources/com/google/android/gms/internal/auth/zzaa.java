package com.google.android.gms.internal.auth;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.TaskCompletionSource;

/* loaded from: classes3.dex */
final class zzaa extends zzj {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ TaskCompletionSource f12977a;

    zzaa(zzab zzabVar, TaskCompletionSource taskCompletionSource) {
        this.f12977a = taskCompletionSource;
    }

    @Override // com.google.android.gms.internal.auth.zzk
    public final void zzb(Status status, @Nullable Bundle bundle) {
        zzab.d(status, bundle, this.f12977a);
    }
}
