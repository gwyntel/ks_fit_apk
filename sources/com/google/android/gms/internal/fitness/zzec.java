package com.google.android.gms.internal.fitness;

import android.app.PendingIntent;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.request.SensorRequest;

/* loaded from: classes3.dex */
final class zzec extends zzax {

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ SensorRequest f13086d;

    /* renamed from: e, reason: collision with root package name */
    final /* synthetic */ com.google.android.gms.fitness.data.zzv f13087e;

    /* renamed from: f, reason: collision with root package name */
    final /* synthetic */ PendingIntent f13088f;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzec(zzee zzeeVar, GoogleApiClient googleApiClient, SensorRequest sensorRequest, com.google.android.gms.fitness.data.zzv zzvVar, PendingIntent pendingIntent) {
        super(googleApiClient);
        this.f13086d = sensorRequest;
        this.f13087e = zzvVar;
        this.f13088f = pendingIntent;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* bridge */ /* synthetic */ void b(Api.AnyClient anyClient) throws RemoteException {
        ((zzcc) ((zzaz) anyClient).getService()).zze(new com.google.android.gms.fitness.request.zzak(this.f13086d, this.f13087e, this.f13088f, new zzes(this)));
    }

    @Override // com.google.android.gms.internal.fitness.zzax, com.google.android.gms.common.api.internal.BasePendingResult
    protected final /* bridge */ /* synthetic */ Result createFailedResult(Status status) {
        return status;
    }
}
