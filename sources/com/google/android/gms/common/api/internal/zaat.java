package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.Preconditions;

/* loaded from: classes3.dex */
final class zaat implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ zaaw f12710a;

    /* synthetic */ zaat(zaaw zaawVar, zaas zaasVar) {
        this.f12710a = zaawVar;
    }

    @Override // com.google.android.gms.common.api.internal.ConnectionCallbacks
    public final void onConnected(@Nullable Bundle bundle) {
        ((com.google.android.gms.signin.zae) Preconditions.checkNotNull(this.f12710a.zak)).zad(new zaar(this.f12710a));
    }

    @Override // com.google.android.gms.common.api.internal.OnConnectionFailedListener
    public final void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        this.f12710a.zab.lock();
        try {
            if (this.f12710a.zaI(connectionResult)) {
                this.f12710a.zaA();
                this.f12710a.zaF();
            } else {
                this.f12710a.zaD(connectionResult);
            }
            this.f12710a.zab.unlock();
        } catch (Throwable th) {
            this.f12710a.zab.unlock();
            throw th;
        }
    }

    @Override // com.google.android.gms.common.api.internal.ConnectionCallbacks
    public final void onConnectionSuspended(int i2) {
    }
}
