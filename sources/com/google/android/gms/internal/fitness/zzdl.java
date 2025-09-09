package com.google.android.gms.internal.fitness;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.fitness.request.DataUpdateListenerRegistrationRequest;

/* loaded from: classes3.dex */
final class zzdl extends zzah {

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ DataUpdateListenerRegistrationRequest f13075d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzdl(zzds zzdsVar, GoogleApiClient googleApiClient, DataUpdateListenerRegistrationRequest dataUpdateListenerRegistrationRequest) {
        super(googleApiClient);
        this.f13075d = dataUpdateListenerRegistrationRequest;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* bridge */ /* synthetic */ void b(Api.AnyClient anyClient) throws RemoteException {
        ((zzca) ((zzaj) anyClient).getService()).zzh(new DataUpdateListenerRegistrationRequest(this.f13075d, new zzes(this)));
    }
}
