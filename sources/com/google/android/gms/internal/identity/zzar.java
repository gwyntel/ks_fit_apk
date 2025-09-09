package com.google.android.gms.internal.identity;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.location.LocationRequest;

/* loaded from: classes3.dex */
final class zzar extends zzba {

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ ListenerHolder f13106d;

    /* renamed from: e, reason: collision with root package name */
    final /* synthetic */ LocationRequest f13107e;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzar(zzbb zzbbVar, GoogleApiClient googleApiClient, ListenerHolder listenerHolder, LocationRequest locationRequest) {
        super(googleApiClient);
        this.f13106d = listenerHolder;
        this.f13107e = locationRequest;
    }

    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* bridge */ /* synthetic */ void b(Api.AnyClient anyClient) throws RemoteException {
        ((zzdz) anyClient).zzs(new zzaz(this.f13106d), this.f13107e, zzbb.a(this));
    }
}
