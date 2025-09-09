package com.google.android.gms.internal.fitness;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.fitness.request.DataUpdateRequest;

/* loaded from: classes3.dex */
final class zzdk extends zzah {

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ DataUpdateRequest f13074d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzdk(zzds zzdsVar, GoogleApiClient googleApiClient, DataUpdateRequest dataUpdateRequest) {
        super(googleApiClient);
        this.f13074d = dataUpdateRequest;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* bridge */ /* synthetic */ void b(Api.AnyClient anyClient) throws RemoteException {
        ((zzca) ((zzaj) anyClient).getService()).zzj(new DataUpdateRequest(this.f13074d, new zzes(this)));
    }
}
