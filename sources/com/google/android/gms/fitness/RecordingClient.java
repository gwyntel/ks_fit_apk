package com.google.android.gms.fitness;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.PendingResultUtil;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Subscription;
import com.google.android.gms.internal.fitness.zzar;
import com.google.android.gms.internal.fitness.zzea;
import com.google.android.gms.tasks.Task;
import java.util.List;

/* loaded from: classes3.dex */
public class RecordingClient extends GoogleApi<Api.ApiOptions.HasGoogleSignInAccountOptions> {
    private static final RecordingApi zza = new zzea();

    RecordingClient(Activity activity, Api.ApiOptions.HasGoogleSignInAccountOptions hasGoogleSignInAccountOptions) {
        super(activity, (Api<Api.ApiOptions.HasGoogleSignInAccountOptions>) zzar.zzg, hasGoogleSignInAccountOptions, GoogleApi.Settings.DEFAULT_SETTINGS);
    }

    @NonNull
    public Task<List<Subscription>> listSubscriptions() {
        return PendingResultUtil.toTask(zza.listSubscriptions(asGoogleApiClient()), zzm.zza);
    }

    @NonNull
    @RequiresPermission(anyOf = {"android.permission.ACCESS_FINE_LOCATION", "android.permission.BODY_SENSORS"}, conditional = true)
    @SuppressLint({"InlinedApi"})
    public Task<Void> subscribe(@NonNull DataSource dataSource) {
        return PendingResultUtil.toVoidTask(zza.subscribe(asGoogleApiClient(), dataSource));
    }

    @NonNull
    public Task<Void> unsubscribe(@NonNull DataSource dataSource) {
        return PendingResultUtil.toVoidTask(zza.unsubscribe(asGoogleApiClient(), dataSource));
    }

    protected RecordingClient(Context context, Api.ApiOptions.HasGoogleSignInAccountOptions hasGoogleSignInAccountOptions) {
        super(context, (Api<Api.ApiOptions.HasGoogleSignInAccountOptions>) zzar.zzg, hasGoogleSignInAccountOptions, GoogleApi.Settings.DEFAULT_SETTINGS);
    }

    @NonNull
    public Task<List<Subscription>> listSubscriptions(@NonNull DataType dataType) {
        return PendingResultUtil.toTask(zza.listSubscriptions(asGoogleApiClient(), dataType), zzm.zza);
    }

    @NonNull
    @RequiresPermission(anyOf = {"android.permission.ACCESS_FINE_LOCATION", "android.permission.BODY_SENSORS"}, conditional = true)
    @SuppressLint({"InlinedApi"})
    public Task<Void> subscribe(@NonNull DataType dataType) {
        return PendingResultUtil.toVoidTask(zza.subscribe(asGoogleApiClient(), dataType));
    }

    @NonNull
    public Task<Void> unsubscribe(@NonNull DataType dataType) {
        return PendingResultUtil.toVoidTask(zza.unsubscribe(asGoogleApiClient(), dataType));
    }

    @NonNull
    public Task<Void> unsubscribe(@NonNull Subscription subscription) {
        PendingResult<Status> pendingResultUnsubscribe;
        RecordingApi recordingApi = zza;
        GoogleApiClient googleApiClientAsGoogleApiClient = asGoogleApiClient();
        if (subscription.getDataType() == null) {
            pendingResultUnsubscribe = ((zzea) recordingApi).unsubscribe(googleApiClientAsGoogleApiClient, (DataSource) Preconditions.checkNotNull(subscription.getDataSource()));
        } else {
            pendingResultUnsubscribe = ((zzea) recordingApi).unsubscribe(googleApiClientAsGoogleApiClient, (DataType) Preconditions.checkNotNull(subscription.getDataType()));
        }
        return PendingResultUtil.toVoidTask(pendingResultUnsubscribe);
    }
}
