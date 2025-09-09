package com.google.android.gms.fitness;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.api.internal.ListenerHolders;
import com.google.android.gms.common.api.internal.RegistrationMethods;
import com.google.android.gms.common.internal.PendingResultUtil;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.request.DataSourcesRequest;
import com.google.android.gms.fitness.request.OnDataPointListener;
import com.google.android.gms.fitness.request.SensorRequest;
import com.google.android.gms.fitness.result.DataSourcesResult;
import com.google.android.gms.internal.fitness.zzaz;
import com.google.android.gms.internal.fitness.zzee;
import com.google.android.gms.tasks.Task;
import java.util.List;

@Deprecated
/* loaded from: classes3.dex */
public class SensorsClient extends GoogleApi<Api.ApiOptions.HasGoogleSignInAccountOptions> {
    private static final SensorsApi zza = new zzee();

    SensorsClient(Activity activity, Api.ApiOptions.HasGoogleSignInAccountOptions hasGoogleSignInAccountOptions) {
        super(activity, (Api<Api.ApiOptions.HasGoogleSignInAccountOptions>) zzaz.zzg, hasGoogleSignInAccountOptions, GoogleApi.Settings.DEFAULT_SETTINGS);
    }

    @NonNull
    @RequiresPermission(anyOf = {"android.permission.ACCESS_FINE_LOCATION", "android.permission.BODY_SENSORS"}, conditional = true)
    @SuppressLint({"InlinedApi"})
    public Task<Void> add(@NonNull SensorRequest sensorRequest, @NonNull PendingIntent pendingIntent) {
        return PendingResultUtil.toVoidTask(zza.add(asGoogleApiClient(), sensorRequest, pendingIntent));
    }

    @NonNull
    public Task<List<DataSource>> findDataSources(@NonNull DataSourcesRequest dataSourcesRequest) {
        return PendingResultUtil.toTask(zza.findDataSources(asGoogleApiClient(), dataSourcesRequest), new PendingResultUtil.ResultConverter() { // from class: com.google.android.gms.fitness.zzn
            @Override // com.google.android.gms.common.internal.PendingResultUtil.ResultConverter
            public final Object convert(Result result) {
                return ((DataSourcesResult) result).getDataSources();
            }
        });
    }

    @NonNull
    public Task<Void> remove(@NonNull PendingIntent pendingIntent) {
        return PendingResultUtil.toVoidTask(zza.remove(asGoogleApiClient(), pendingIntent));
    }

    protected SensorsClient(Context context, Api.ApiOptions.HasGoogleSignInAccountOptions hasGoogleSignInAccountOptions) {
        super(context, (Api<Api.ApiOptions.HasGoogleSignInAccountOptions>) zzaz.zzg, hasGoogleSignInAccountOptions, GoogleApi.Settings.DEFAULT_SETTINGS);
    }

    @NonNull
    @RequiresPermission(anyOf = {"android.permission.ACCESS_FINE_LOCATION", "android.permission.BODY_SENSORS"}, conditional = true)
    @SuppressLint({"InlinedApi"})
    public Task<Void> add(@NonNull SensorRequest sensorRequest, @NonNull OnDataPointListener onDataPointListener) {
        ListenerHolder<L> listenerHolderRegisterListener = registerListener(onDataPointListener, OnDataPointListener.class.getSimpleName());
        return doRegisterEventListener(RegistrationMethods.builder().withHolder(listenerHolderRegisterListener).register(new zzp(this, listenerHolderRegisterListener, sensorRequest)).unregister(new zzo(this, listenerHolderRegisterListener)).build());
    }

    @NonNull
    public Task<Boolean> remove(@NonNull OnDataPointListener onDataPointListener) {
        return doUnregisterEventListener(ListenerHolders.createListenerKey(onDataPointListener, OnDataPointListener.class.getSimpleName()));
    }
}
