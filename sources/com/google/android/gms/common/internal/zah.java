package com.google.android.gms.common.internal;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.gms.common.api.internal.ConnectionCallbacks;
import com.google.android.gms.common.internal.BaseGmsClient;

/* loaded from: classes3.dex */
final class zah implements BaseGmsClient.BaseConnectionCallbacks {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ ConnectionCallbacks f12837a;

    zah(ConnectionCallbacks connectionCallbacks) {
        this.f12837a = connectionCallbacks;
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient.BaseConnectionCallbacks
    public final void onConnected(@Nullable Bundle bundle) {
        this.f12837a.onConnected(bundle);
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient.BaseConnectionCallbacks
    public final void onConnectionSuspended(int i2) {
        this.f12837a.onConnectionSuspended(i2);
    }
}
