package com.google.android.gms.internal.fitness;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.fitness.request.DataDeleteRequest;

/* loaded from: classes3.dex */
final class zzdj extends zzah {

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ DataDeleteRequest f13073d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzdj(zzds zzdsVar, GoogleApiClient googleApiClient, DataDeleteRequest dataDeleteRequest) {
        super(googleApiClient);
        this.f13073d = dataDeleteRequest;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* bridge */ /* synthetic */ void b(Api.AnyClient anyClient) throws RemoteException {
        ((zzca) ((zzaj) anyClient).getService()).zzd(new DataDeleteRequest(this.f13073d, new zzes(this)));
    }
}
