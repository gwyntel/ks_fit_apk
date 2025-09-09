package com.google.android.gms.internal.identity;

import com.google.android.gms.common.api.internal.TaskUtil;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.tasks.TaskCompletionSource;

/* loaded from: classes3.dex */
final class zzde extends zzaa {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ TaskCompletionSource f13144a;

    zzde(TaskCompletionSource taskCompletionSource) {
        this.f13144a = taskCompletionSource;
    }

    @Override // com.google.android.gms.internal.identity.zzab
    public final void zzb(LocationSettingsResult locationSettingsResult) {
        TaskUtil.setResultOrApiException(locationSettingsResult.getStatus(), new LocationSettingsResponse(locationSettingsResult), this.f13144a);
    }
}
