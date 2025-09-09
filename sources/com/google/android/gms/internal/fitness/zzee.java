package com.google.android.gms.internal.fitness;

import android.app.PendingIntent;
import androidx.annotation.Nullable;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.PendingResults;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.SensorsApi;
import com.google.android.gms.fitness.request.DataSourcesRequest;
import com.google.android.gms.fitness.request.OnDataPointListener;
import com.google.android.gms.fitness.request.SensorRequest;
import com.google.android.gms.fitness.result.DataSourcesResult;

/* loaded from: classes3.dex */
public final class zzee implements SensorsApi {
    private final PendingResult zza(GoogleApiClient googleApiClient, SensorRequest sensorRequest, @Nullable com.google.android.gms.fitness.data.zzv zzvVar, @Nullable PendingIntent pendingIntent) {
        return googleApiClient.enqueue(new zzec(this, googleApiClient, sensorRequest, zzvVar, pendingIntent));
    }

    private final PendingResult zzb(GoogleApiClient googleApiClient, @Nullable com.google.android.gms.fitness.data.zzv zzvVar, @Nullable PendingIntent pendingIntent) {
        return googleApiClient.execute(new zzed(this, googleApiClient, zzvVar, pendingIntent));
    }

    @Override // com.google.android.gms.fitness.SensorsApi
    public final PendingResult<Status> add(GoogleApiClient googleApiClient, SensorRequest sensorRequest, PendingIntent pendingIntent) {
        return zza(googleApiClient, sensorRequest, null, pendingIntent);
    }

    @Override // com.google.android.gms.fitness.SensorsApi
    public final PendingResult<DataSourcesResult> findDataSources(GoogleApiClient googleApiClient, DataSourcesRequest dataSourcesRequest) {
        return googleApiClient.enqueue(new zzeb(this, googleApiClient, dataSourcesRequest));
    }

    @Override // com.google.android.gms.fitness.SensorsApi
    public final PendingResult<Status> remove(GoogleApiClient googleApiClient, PendingIntent pendingIntent) {
        return zzb(googleApiClient, null, pendingIntent);
    }

    @Override // com.google.android.gms.fitness.SensorsApi
    public final PendingResult<Status> add(GoogleApiClient googleApiClient, SensorRequest sensorRequest, OnDataPointListener onDataPointListener) {
        return zza(googleApiClient, sensorRequest, com.google.android.gms.fitness.request.zzah.zza().zzc(onDataPointListener, googleApiClient.getLooper()), null);
    }

    @Override // com.google.android.gms.fitness.SensorsApi
    public final PendingResult<Status> remove(GoogleApiClient googleApiClient, OnDataPointListener onDataPointListener) {
        com.google.android.gms.fitness.request.zzaj zzajVarZze = com.google.android.gms.fitness.request.zzah.zza().zze(onDataPointListener, googleApiClient.getLooper());
        return zzajVarZze == null ? PendingResults.immediatePendingResult(Status.RESULT_SUCCESS, googleApiClient) : zzb(googleApiClient, zzajVarZze, null);
    }
}
