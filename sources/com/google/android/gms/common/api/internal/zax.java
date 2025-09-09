package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;

/* loaded from: classes3.dex */
final class zax implements zabz {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ zaaa f12777a;

    /* synthetic */ zax(zaaa zaaaVar, zaw zawVar) {
        this.f12777a = zaaaVar;
    }

    @Override // com.google.android.gms.common.api.internal.zabz
    public final void zaa(@NonNull ConnectionResult connectionResult) {
        this.f12777a.zam.lock();
        try {
            this.f12777a.zaj = connectionResult;
            zaaa.j(this.f12777a);
        } finally {
            this.f12777a.zam.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.internal.zabz
    public final void zab(@Nullable Bundle bundle) {
        this.f12777a.zam.lock();
        try {
            zaaa.i(this.f12777a, bundle);
            this.f12777a.zaj = ConnectionResult.RESULT_SUCCESS;
            zaaa.j(this.f12777a);
        } finally {
            this.f12777a.zam.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.internal.zabz
    public final void zac(int i2, boolean z2) {
        this.f12777a.zam.lock();
        try {
            zaaa zaaaVar = this.f12777a;
            if (zaaaVar.zal || zaaaVar.zak == null || !zaaaVar.zak.isSuccess()) {
                this.f12777a.zal = false;
                zaaa.h(this.f12777a, i2, z2);
            } else {
                this.f12777a.zal = true;
                this.f12777a.zae.onConnectionSuspended(i2);
            }
            this.f12777a.zam.unlock();
        } catch (Throwable th) {
            this.f12777a.zam.unlock();
            throw th;
        }
    }
}
