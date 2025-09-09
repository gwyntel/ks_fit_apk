package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;

/* loaded from: classes3.dex */
final class zaz implements zabz {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ zaaa f12778a;

    /* synthetic */ zaz(zaaa zaaaVar, zay zayVar) {
        this.f12778a = zaaaVar;
    }

    @Override // com.google.android.gms.common.api.internal.zabz
    public final void zaa(@NonNull ConnectionResult connectionResult) {
        this.f12778a.zam.lock();
        try {
            this.f12778a.zak = connectionResult;
            zaaa.j(this.f12778a);
        } finally {
            this.f12778a.zam.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.internal.zabz
    public final void zab(@Nullable Bundle bundle) {
        this.f12778a.zam.lock();
        try {
            this.f12778a.zak = ConnectionResult.RESULT_SUCCESS;
            zaaa.j(this.f12778a);
        } finally {
            this.f12778a.zam.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.internal.zabz
    public final void zac(int i2, boolean z2) {
        this.f12778a.zam.lock();
        try {
            zaaa zaaaVar = this.f12778a;
            if (zaaaVar.zal) {
                zaaaVar.zal = false;
                zaaa.h(this.f12778a, i2, z2);
            } else {
                zaaaVar.zal = true;
                this.f12778a.zad.onConnectionSuspended(i2);
            }
            this.f12778a.zam.unlock();
        } catch (Throwable th) {
            this.f12778a.zam.unlock();
            throw th;
        }
    }
}
