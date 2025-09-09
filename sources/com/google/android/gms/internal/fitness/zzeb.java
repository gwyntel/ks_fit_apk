package com.google.android.gms.internal.fitness;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.request.DataSourcesRequest;
import com.google.android.gms.fitness.result.DataSourcesResult;
import java.util.Collections;

/* loaded from: classes3.dex */
final class zzeb extends zzau {

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ DataSourcesRequest f13085d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzeb(zzee zzeeVar, GoogleApiClient googleApiClient, DataSourcesRequest dataSourcesRequest) {
        super(googleApiClient);
        this.f13085d = dataSourcesRequest;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* bridge */ /* synthetic */ void b(Api.AnyClient anyClient) throws RemoteException {
        ((zzcc) ((zzaz) anyClient).getService()).zzd(new DataSourcesRequest(this.f13085d, new zzf(this)));
    }

    @Override // com.google.android.gms.common.api.internal.BasePendingResult
    protected final /* synthetic */ Result createFailedResult(Status status) {
        return new DataSourcesResult(Collections.emptyList(), status);
    }
}
