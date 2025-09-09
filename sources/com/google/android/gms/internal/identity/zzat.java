package com.google.android.gms.internal.identity;

import android.app.PendingIntent;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;

/* loaded from: classes3.dex */
final class zzat extends zzba {

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ PendingIntent f13110d;

    /* renamed from: e, reason: collision with root package name */
    final /* synthetic */ LocationRequest f13111e;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzat(zzbb zzbbVar, GoogleApiClient googleApiClient, PendingIntent pendingIntent, LocationRequest locationRequest) {
        super(googleApiClient);
        this.f13110d = pendingIntent;
        this.f13111e = locationRequest;
    }

    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* bridge */ /* synthetic */ void b(Api.AnyClient anyClient) throws RemoteException {
        ((zzdz) anyClient).zzu(this.f13110d, this.f13111e, zzbb.a(this));
    }
}
