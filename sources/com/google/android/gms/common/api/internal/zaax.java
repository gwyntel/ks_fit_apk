package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.BaseImplementation;
import java.util.Collections;
import java.util.Iterator;
import org.checkerframework.checker.initialization.qual.NotOnlyInitialized;

/* loaded from: classes3.dex */
public final class zaax implements zabf {

    @NotOnlyInitialized
    private final zabi zaa;

    public zaax(zabi zabiVar) {
        this.zaa = zabiVar;
    }

    @Override // com.google.android.gms.common.api.internal.zabf
    public final BaseImplementation.ApiMethodImpl zaa(BaseImplementation.ApiMethodImpl apiMethodImpl) {
        this.zaa.f12738g.f12722a.add(apiMethodImpl);
        return apiMethodImpl;
    }

    @Override // com.google.android.gms.common.api.internal.zabf
    public final BaseImplementation.ApiMethodImpl zab(BaseImplementation.ApiMethodImpl apiMethodImpl) {
        throw new IllegalStateException("GoogleApiClient is not connected yet.");
    }

    @Override // com.google.android.gms.common.api.internal.zabf
    public final void zad() {
        Iterator it = this.zaa.f12732a.values().iterator();
        while (it.hasNext()) {
            ((Api.Client) it.next()).disconnect();
        }
        this.zaa.f12738g.f12725d = Collections.emptySet();
    }

    @Override // com.google.android.gms.common.api.internal.zabf
    public final void zae() {
        this.zaa.d();
    }

    @Override // com.google.android.gms.common.api.internal.zabf
    public final void zag(@Nullable Bundle bundle) {
    }

    @Override // com.google.android.gms.common.api.internal.zabf
    public final void zah(ConnectionResult connectionResult, Api api, boolean z2) {
    }

    @Override // com.google.android.gms.common.api.internal.zabf
    public final void zai(int i2) {
    }

    @Override // com.google.android.gms.common.api.internal.zabf
    public final boolean zaj() {
        return true;
    }
}
