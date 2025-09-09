package com.google.android.gms.internal.fitness;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.fitness.data.DataSource;

/* loaded from: classes3.dex */
final class zzdx extends zzap {

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ DataSource f13084d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzdx(zzea zzeaVar, GoogleApiClient googleApiClient, DataSource dataSource) {
        super(googleApiClient);
        this.f13084d = dataSource;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* bridge */ /* synthetic */ void b(Api.AnyClient anyClient) throws RemoteException {
        ((zzcb) ((zzar) anyClient).getService()).zzf(new com.google.android.gms.fitness.request.zzbh(null, this.f13084d, new zzes(this)));
    }
}
