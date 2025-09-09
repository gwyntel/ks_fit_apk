package com.google.android.gms.internal.identity;

import android.app.PendingIntent;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;

/* loaded from: classes3.dex */
final class zzav extends zzba {

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ PendingIntent f13113d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzav(zzbb zzbbVar, GoogleApiClient googleApiClient, PendingIntent pendingIntent) {
        super(googleApiClient);
        this.f13113d = pendingIntent;
    }

    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* bridge */ /* synthetic */ void b(Api.AnyClient anyClient) throws RemoteException {
        ((zzdz) anyClient).zzx(this.f13113d, zzbb.a(this), null);
    }
}
