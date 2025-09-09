package com.google.android.gms.internal.identity;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.ListenerHolders;
import com.google.android.gms.location.LocationListener;

/* loaded from: classes3.dex */
final class zzau extends zzba {

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ LocationListener f13112d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzau(zzbb zzbbVar, GoogleApiClient googleApiClient, LocationListener locationListener) {
        super(googleApiClient);
        this.f13112d = locationListener;
    }

    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* bridge */ /* synthetic */ void b(Api.AnyClient anyClient) throws RemoteException {
        ((zzdz) anyClient).zzv(ListenerHolders.createListenerKey(this.f13112d, LocationListener.class.getSimpleName()), true, zzbb.a(this));
    }
}
