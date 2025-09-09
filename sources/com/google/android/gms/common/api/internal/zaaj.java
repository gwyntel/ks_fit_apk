package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import android.os.DeadObjectException;
import androidx.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.internal.Preconditions;
import java.util.Iterator;
import java.util.Set;

/* loaded from: classes3.dex */
public final class zaaj implements zabf {
    private final zabi zaa;
    private boolean zab = false;

    public zaaj(zabi zabiVar) {
        this.zaa = zabiVar;
    }

    final void b() {
        if (this.zab) {
            this.zab = false;
            this.zaa.f12738g.f12730i.zab();
            zaj();
        }
    }

    @Override // com.google.android.gms.common.api.internal.zabf
    public final BaseImplementation.ApiMethodImpl zaa(BaseImplementation.ApiMethodImpl apiMethodImpl) {
        zab(apiMethodImpl);
        return apiMethodImpl;
    }

    @Override // com.google.android.gms.common.api.internal.zabf
    public final BaseImplementation.ApiMethodImpl zab(BaseImplementation.ApiMethodImpl apiMethodImpl) {
        try {
            this.zaa.f12738g.f12730i.a(apiMethodImpl);
            zabe zabeVar = this.zaa.f12738g;
            Api.Client client = (Api.Client) zabeVar.f12724c.get(apiMethodImpl.getClientKey());
            Preconditions.checkNotNull(client, "Appropriate Api was not requested.");
            if (client.isConnected() || !this.zaa.f12733b.containsKey(apiMethodImpl.getClientKey())) {
                apiMethodImpl.run(client);
            } else {
                apiMethodImpl.setFailedResult(new Status(17));
            }
        } catch (DeadObjectException unused) {
            this.zaa.f(new zaah(this, this));
        }
        return apiMethodImpl;
    }

    @Override // com.google.android.gms.common.api.internal.zabf
    public final void zad() {
    }

    @Override // com.google.android.gms.common.api.internal.zabf
    public final void zae() {
        if (this.zab) {
            this.zab = false;
            this.zaa.f(new zaai(this, this));
        }
    }

    @Override // com.google.android.gms.common.api.internal.zabf
    public final void zag(@Nullable Bundle bundle) {
    }

    @Override // com.google.android.gms.common.api.internal.zabf
    public final void zah(ConnectionResult connectionResult, Api api, boolean z2) {
    }

    @Override // com.google.android.gms.common.api.internal.zabf
    public final void zai(int i2) {
        this.zaa.e(null);
        this.zaa.f12739h.zac(i2, this.zab);
    }

    @Override // com.google.android.gms.common.api.internal.zabf
    public final boolean zaj() {
        if (this.zab) {
            return false;
        }
        Set set = this.zaa.f12738g.f12729h;
        if (set == null || set.isEmpty()) {
            this.zaa.e(null);
            return true;
        }
        this.zab = true;
        Iterator it = set.iterator();
        while (it.hasNext()) {
            ((zada) it.next()).h();
        }
        return false;
    }
}
