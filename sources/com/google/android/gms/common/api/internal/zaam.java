package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.ConnectionResult;
import com.google.errorprone.annotations.concurrent.GuardedBy;
import com.kingsmith.miot.KsProperty;

/* loaded from: classes3.dex */
final class zaam extends zabg {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ ConnectionResult f12703a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ zaao f12704b;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zaam(zaao zaaoVar, zabf zabfVar, ConnectionResult connectionResult) {
        super(zabfVar);
        this.f12704b = zaaoVar;
        this.f12703a = connectionResult;
    }

    @Override // com.google.android.gms.common.api.internal.zabg
    @GuardedBy(KsProperty.Lock)
    public final void zaa() {
        this.f12704b.f12706b.zaD(this.f12703a);
    }
}
