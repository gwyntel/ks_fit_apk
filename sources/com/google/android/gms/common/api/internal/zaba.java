package com.google.android.gms.common.api.internal;

import androidx.annotation.NonNull;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;

/* loaded from: classes3.dex */
final class zaba implements GoogleApiClient.OnConnectionFailedListener {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ StatusPendingResult f12716a;

    zaba(zabe zabeVar, StatusPendingResult statusPendingResult) {
        this.f12716a = statusPendingResult;
    }

    @Override // com.google.android.gms.common.api.internal.OnConnectionFailedListener
    public final void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        this.f12716a.setResult(new Status(8));
    }
}
