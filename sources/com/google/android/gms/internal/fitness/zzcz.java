package com.google.android.gms.internal.fitness;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.request.DataTypeCreateRequest;
import com.google.android.gms.fitness.result.DataTypeResult;

/* loaded from: classes3.dex */
final class zzcz extends zzp {

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ DataTypeCreateRequest f13068d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzcz(zzde zzdeVar, GoogleApiClient googleApiClient, DataTypeCreateRequest dataTypeCreateRequest) {
        super(googleApiClient);
        this.f13068d = dataTypeCreateRequest;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* bridge */ /* synthetic */ void b(Api.AnyClient anyClient) throws RemoteException {
        ((zzby) ((zzu) anyClient).getService()).zzd(new DataTypeCreateRequest(this.f13068d, new zzdd(this, null)));
    }

    @Override // com.google.android.gms.common.api.internal.BasePendingResult
    protected final /* synthetic */ Result createFailedResult(Status status) {
        return new DataTypeResult(status, null);
    }
}
