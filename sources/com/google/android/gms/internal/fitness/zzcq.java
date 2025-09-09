package com.google.android.gms.internal.fitness;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.fitness.request.StartBleScanRequest;

/* loaded from: classes3.dex */
final class zzcq extends zzk {

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ StartBleScanRequest f13062d;

    /* renamed from: e, reason: collision with root package name */
    final /* synthetic */ com.google.android.gms.fitness.request.zzab f13063e;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzcq(zzcy zzcyVar, GoogleApiClient googleApiClient, StartBleScanRequest startBleScanRequest, com.google.android.gms.fitness.request.zzab zzabVar) {
        super(googleApiClient);
        this.f13062d = startBleScanRequest;
        this.f13063e = zzabVar;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* bridge */ /* synthetic */ void b(Api.AnyClient anyClient) throws RemoteException {
        ((zzbx) ((zzm) anyClient).getService()).zzf(new StartBleScanRequest(this.f13062d.getDataTypes(), this.f13063e, this.f13062d.getTimeoutSecs(), new zzes(this)));
    }
}
