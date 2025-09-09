package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.Preconditions;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes3.dex */
final class zaaz implements GoogleApiClient.ConnectionCallbacks {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ AtomicReference f12713a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ StatusPendingResult f12714b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ zabe f12715c;

    zaaz(zabe zabeVar, AtomicReference atomicReference, StatusPendingResult statusPendingResult) {
        this.f12715c = zabeVar;
        this.f12713a = atomicReference;
        this.f12714b = statusPendingResult;
    }

    @Override // com.google.android.gms.common.api.internal.ConnectionCallbacks
    public final void onConnected(@Nullable Bundle bundle) {
        this.f12715c.zam((GoogleApiClient) Preconditions.checkNotNull((GoogleApiClient) this.f12713a.get()), this.f12714b, true);
    }

    @Override // com.google.android.gms.common.api.internal.ConnectionCallbacks
    public final void onConnectionSuspended(int i2) {
    }
}
