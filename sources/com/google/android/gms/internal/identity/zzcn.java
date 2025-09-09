package com.google.android.gms.internal.identity;

import android.app.PendingIntent;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.GeofencingRequest;

/* loaded from: classes3.dex */
final class zzcn extends zzcq {

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ GeofencingRequest f13136d;

    /* renamed from: e, reason: collision with root package name */
    final /* synthetic */ PendingIntent f13137e;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzcn(zzcr zzcrVar, GoogleApiClient googleApiClient, GeofencingRequest geofencingRequest, PendingIntent pendingIntent) {
        super(googleApiClient);
        this.f13136d = geofencingRequest;
        this.f13137e = pendingIntent;
    }

    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* bridge */ /* synthetic */ void b(Api.AnyClient anyClient) throws RemoteException {
        ((zzdz) anyClient).zzE(this.f13136d, this.f13137e, zzcr.a(this));
    }
}
