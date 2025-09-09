package com.google.android.gms.internal.identity;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.TaskUtil;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.tasks.TaskCompletionSource;

/* loaded from: classes3.dex */
final class zzdl extends zzw {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ TaskCompletionSource f13154a;

    zzdl(TaskCompletionSource taskCompletionSource) {
        this.f13154a = taskCompletionSource;
    }

    @Override // com.google.android.gms.internal.identity.zzx
    public final void zzb(Status status, LocationAvailability locationAvailability) {
        TaskUtil.setResultOrApiException(status, locationAvailability, this.f13154a);
    }
}
