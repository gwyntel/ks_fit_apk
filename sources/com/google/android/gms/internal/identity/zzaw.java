package com.google.android.gms.internal.identity;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.ListenerHolders;
import com.google.android.gms.location.LocationCallback;

/* loaded from: classes3.dex */
final class zzaw extends zzba {

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ LocationCallback f13114d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzaw(zzbb zzbbVar, GoogleApiClient googleApiClient, LocationCallback locationCallback) {
        super(googleApiClient);
        this.f13114d = locationCallback;
    }

    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* bridge */ /* synthetic */ void b(Api.AnyClient anyClient) throws RemoteException {
        ((zzdz) anyClient).zzw(ListenerHolders.createListenerKey(this.f13114d, LocationCallback.class.getSimpleName()), true, zzbb.a(this));
    }
}
