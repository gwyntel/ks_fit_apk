package com.google.android.gms.internal.fitness;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.result.DataTypeResult;

/* loaded from: classes3.dex */
final class zzda extends zzp {

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ String f13069d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzda(zzde zzdeVar, GoogleApiClient googleApiClient, String str) {
        super(googleApiClient);
        this.f13069d = str;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* bridge */ /* synthetic */ void b(Api.AnyClient anyClient) throws RemoteException {
        ((zzby) ((zzu) anyClient).getService()).zzf(new com.google.android.gms.fitness.request.zzp(this.f13069d, (zzbt) new zzdd(this, null)));
    }

    @Override // com.google.android.gms.common.api.internal.BasePendingResult
    protected final /* synthetic */ Result createFailedResult(Status status) {
        return new DataTypeResult(status, null);
    }
}
