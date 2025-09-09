package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.BaseGmsClient;
import com.google.errorprone.annotations.concurrent.GuardedBy;
import com.kingsmith.miot.KsProperty;

/* loaded from: classes3.dex */
final class zaan extends zabg {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ BaseGmsClient.ConnectionProgressReportCallbacks f12705a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zaan(zaao zaaoVar, zabf zabfVar, BaseGmsClient.ConnectionProgressReportCallbacks connectionProgressReportCallbacks) {
        super(zabfVar);
        this.f12705a = connectionProgressReportCallbacks;
    }

    @Override // com.google.android.gms.common.api.internal.zabg
    @GuardedBy(KsProperty.Lock)
    public final void zaa() {
        this.f12705a.onReportServiceBinding(new ConnectionResult(16, null));
    }
}
