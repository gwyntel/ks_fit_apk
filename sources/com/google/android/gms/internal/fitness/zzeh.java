package com.google.android.gms.internal.fitness;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.fitness.request.SessionInsertRequest;

/* loaded from: classes3.dex */
final class zzeh extends zzbf {

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ SessionInsertRequest f13093d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzeh(zzep zzepVar, GoogleApiClient googleApiClient, SessionInsertRequest sessionInsertRequest) {
        super(googleApiClient);
        this.f13093d = sessionInsertRequest;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* bridge */ /* synthetic */ void b(Api.AnyClient anyClient) throws RemoteException {
        ((zzcd) ((zzbh) anyClient).getService()).zzd(new SessionInsertRequest(this.f13093d, new zzes(this)));
    }
}
