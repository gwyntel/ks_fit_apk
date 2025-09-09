package com.google.android.gms.internal.fitness;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.ConfigApi;
import com.google.android.gms.fitness.request.DataTypeCreateRequest;
import com.google.android.gms.fitness.result.DataTypeResult;

/* loaded from: classes3.dex */
public final class zzde implements ConfigApi {
    @Override // com.google.android.gms.fitness.ConfigApi
    public final PendingResult<DataTypeResult> createCustomDataType(GoogleApiClient googleApiClient, DataTypeCreateRequest dataTypeCreateRequest) {
        return googleApiClient.execute(new zzcz(this, googleApiClient, dataTypeCreateRequest));
    }

    @Override // com.google.android.gms.fitness.ConfigApi
    public final PendingResult<Status> disableFit(GoogleApiClient googleApiClient) {
        return googleApiClient.execute(new zzdb(this, googleApiClient));
    }

    @Override // com.google.android.gms.fitness.ConfigApi
    public final PendingResult<DataTypeResult> readDataType(GoogleApiClient googleApiClient, String str) {
        return googleApiClient.enqueue(new zzda(this, googleApiClient, str));
    }
}
