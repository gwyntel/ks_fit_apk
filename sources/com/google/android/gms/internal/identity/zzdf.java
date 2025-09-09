package com.google.android.gms.internal.identity;

import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.location.LocationSettingsResult;

/* loaded from: classes3.dex */
final class zzdf extends zzaa {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ BaseImplementation.ResultHolder f13145a;

    zzdf(BaseImplementation.ResultHolder resultHolder) {
        this.f13145a = resultHolder;
    }

    @Override // com.google.android.gms.internal.identity.zzab
    public final void zzb(LocationSettingsResult locationSettingsResult) {
        this.f13145a.setResult(locationSettingsResult);
    }
}
