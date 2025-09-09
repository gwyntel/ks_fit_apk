package com.google.android.gms.internal.fitness;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.fitness.data.DataType;

/* loaded from: classes3.dex */
final class zzdw extends zzap {

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ DataType f13083d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzdw(zzea zzeaVar, GoogleApiClient googleApiClient, DataType dataType) {
        super(googleApiClient);
        this.f13083d = dataType;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* bridge */ /* synthetic */ void b(Api.AnyClient anyClient) throws RemoteException {
        ((zzcb) ((zzar) anyClient).getService()).zzf(new com.google.android.gms.fitness.request.zzbh(this.f13083d, null, new zzes(this)));
    }
}
