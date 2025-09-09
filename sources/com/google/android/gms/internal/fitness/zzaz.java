package com.google.android.gms.internal.fitness;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.ClientSettings;

/* loaded from: classes3.dex */
public final class zzaz extends zze {
    public static final Api.ClientKey zze;
    public static final Api zzf;
    public static final Api zzg;
    public static final int zzh = 57;

    static {
        Api.ClientKey clientKey = new Api.ClientKey();
        zze = clientKey;
        zzf = new Api("Fitness.SENSORS_API", new zzat(null), clientKey);
        zzg = new Api("Fitness.SENSORS_CLIENT", new zzaw(0 == true ? 1 : 0), clientKey);
    }

    /* synthetic */ zzaz(Context context, Looper looper, ClientSettings clientSettings, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener, zzay zzayVar) {
        super(context, looper, zzh, connectionCallbacks, onConnectionFailedListener, clientSettings);
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient
    public final /* synthetic */ IInterface createServiceInterface(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.fitness.internal.IGoogleFitSensorsApi");
        return iInterfaceQueryLocalInterface instanceof zzcc ? (zzcc) iInterfaceQueryLocalInterface : new zzcc(iBinder);
    }

    @Override // com.google.android.gms.internal.fitness.zze, com.google.android.gms.common.internal.BaseGmsClient, com.google.android.gms.common.api.Api.Client
    public final int getMinApkVersion() {
        return 12451000;
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient
    public final String getServiceDescriptor() {
        return "com.google.android.gms.fitness.internal.IGoogleFitSensorsApi";
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient
    public final String getStartServiceAction() {
        return "com.google.android.gms.fitness.SensorsApi";
    }
}
