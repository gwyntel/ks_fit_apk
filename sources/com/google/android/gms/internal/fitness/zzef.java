package com.google.android.gms.internal.fitness;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.fitness.data.Session;

/* loaded from: classes3.dex */
final class zzef extends zzbf {

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ Session f13091d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzef(zzep zzepVar, GoogleApiClient googleApiClient, Session session) {
        super(googleApiClient);
        this.f13091d = session;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* bridge */ /* synthetic */ void b(Api.AnyClient anyClient) throws RemoteException {
        ((zzcd) ((zzbh) anyClient).getService()).zzg(new com.google.android.gms.fitness.request.zzat(this.f13091d, (zzcp) new zzes(this)));
    }
}
