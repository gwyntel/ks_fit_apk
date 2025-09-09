package com.google.android.gms.internal.identity;

import android.app.PendingIntent;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;

/* loaded from: classes3.dex */
final class zzco extends zzcq {

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ PendingIntent f13138d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzco(zzcr zzcrVar, GoogleApiClient googleApiClient, PendingIntent pendingIntent) {
        super(googleApiClient);
        this.f13138d = pendingIntent;
    }

    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* bridge */ /* synthetic */ void b(Api.AnyClient anyClient) throws RemoteException {
        ((zzdz) anyClient).zzF(zzem.zzb(this.f13138d), zzcr.a(this));
    }
}
