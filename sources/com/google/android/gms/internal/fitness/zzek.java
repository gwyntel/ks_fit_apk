package com.google.android.gms.internal.fitness;

import android.app.PendingIntent;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;

/* loaded from: classes3.dex */
final class zzek extends zzbf {

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ PendingIntent f13096d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzek(zzep zzepVar, GoogleApiClient googleApiClient, PendingIntent pendingIntent) {
        super(googleApiClient);
        this.f13096d = pendingIntent;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* bridge */ /* synthetic */ void b(Api.AnyClient anyClient) throws RemoteException {
        ((zzcd) ((zzbh) anyClient).getService()).zzi(new com.google.android.gms.fitness.request.zzax(this.f13096d, (zzcp) new zzes(this)));
    }
}
