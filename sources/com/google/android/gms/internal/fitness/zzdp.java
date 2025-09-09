package com.google.android.gms.internal.fitness;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.result.DailyTotalResult;

/* loaded from: classes3.dex */
final class zzdp extends zzae {

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ DataType f13079d;

    /* renamed from: e, reason: collision with root package name */
    final /* synthetic */ boolean f13080e;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzdp(zzds zzdsVar, GoogleApiClient googleApiClient, DataType dataType, boolean z2) {
        super(googleApiClient);
        this.f13079d = dataType;
        this.f13080e = z2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* bridge */ /* synthetic */ void b(Api.AnyClient anyClient) throws RemoteException {
        ((zzca) ((zzaj) anyClient).getService()).zzf(new com.google.android.gms.fitness.request.zzh((zzbk) new zzdo(this), this.f13079d, this.f13080e));
    }

    @Override // com.google.android.gms.common.api.internal.BasePendingResult
    protected final /* synthetic */ Result createFailedResult(Status status) {
        DataType dataType = this.f13079d;
        DataSource.Builder builder = new DataSource.Builder();
        builder.setType(1);
        builder.setDataType(dataType);
        return new DailyTotalResult(status, DataSet.builder(builder.build()).build());
    }
}
