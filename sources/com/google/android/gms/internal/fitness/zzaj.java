package com.google.android.gms.internal.fitness;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.ClientSettings;

/* loaded from: classes3.dex */
public final class zzaj extends zze {
    public static final Api.ClientKey zze;
    public static final Api zzf;
    public static final Api zzg;
    public static final int zzh = 59;

    static {
        Api.ClientKey clientKey = new Api.ClientKey();
        zze = clientKey;
        zzf = new Api("Fitness.API", new zzad(null), clientKey);
        zzg = new Api("Fitness.CLIENT", new zzag(0 == true ? 1 : 0), clientKey);
    }

    /* synthetic */ zzaj(Context context, Looper looper, ClientSettings clientSettings, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener, zzai zzaiVar) {
        super(context, looper, zzh, connectionCallbacks, onConnectionFailedListener, clientSettings);
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient
    public final /* synthetic */ IInterface createServiceInterface(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.fitness.internal.IGoogleFitHistoryApi");
        return iInterfaceQueryLocalInterface instanceof zzca ? (zzca) iInterfaceQueryLocalInterface : new zzca(iBinder);
    }

    @Override // com.google.android.gms.internal.fitness.zze, com.google.android.gms.common.internal.BaseGmsClient, com.google.android.gms.common.api.Api.Client
    public final int getMinApkVersion() {
        return 12451000;
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient
    public final String getServiceDescriptor() {
        return "com.google.android.gms.fitness.internal.IGoogleFitHistoryApi";
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient
    public final String getStartServiceAction() {
        return "com.google.android.gms.fitness.HistoryApi";
    }
}
