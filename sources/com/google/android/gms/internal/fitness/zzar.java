package com.google.android.gms.internal.fitness;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.ClientSettings;

/* loaded from: classes3.dex */
public final class zzar extends zze {
    public static final Api.ClientKey zze;
    public static final Api zzf;
    public static final Api zzg;
    public static final int zzh = 58;

    static {
        Api.ClientKey clientKey = new Api.ClientKey();
        zze = clientKey;
        zzf = new Api("Fitness.RECORDING_API", new zzal(null), clientKey);
        zzg = new Api("Fitness.RECORDING_CLIENT", new zzao(0 == true ? 1 : 0), clientKey);
    }

    /* synthetic */ zzar(Context context, Looper looper, ClientSettings clientSettings, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener, zzaq zzaqVar) {
        super(context, looper, zzh, connectionCallbacks, onConnectionFailedListener, clientSettings);
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient
    public final /* synthetic */ IInterface createServiceInterface(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.fitness.internal.IGoogleFitRecordingApi");
        return iInterfaceQueryLocalInterface instanceof zzcb ? (zzcb) iInterfaceQueryLocalInterface : new zzcb(iBinder);
    }

    @Override // com.google.android.gms.internal.fitness.zze, com.google.android.gms.common.internal.BaseGmsClient, com.google.android.gms.common.api.Api.Client
    public final int getMinApkVersion() {
        return 12451000;
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient
    public final String getServiceDescriptor() {
        return "com.google.android.gms.fitness.internal.IGoogleFitRecordingApi";
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient
    public final String getStartServiceAction() {
        return "com.google.android.gms.fitness.RecordingApi";
    }
}
