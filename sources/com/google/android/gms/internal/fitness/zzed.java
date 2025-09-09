package com.google.android.gms.internal.fitness;

import android.app.PendingIntent;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;

/* loaded from: classes3.dex */
final class zzed extends zzax {

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ com.google.android.gms.fitness.data.zzv f13089d;

    /* renamed from: e, reason: collision with root package name */
    final /* synthetic */ PendingIntent f13090e;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzed(zzee zzeeVar, GoogleApiClient googleApiClient, com.google.android.gms.fitness.data.zzv zzvVar, PendingIntent pendingIntent) {
        super(googleApiClient);
        this.f13089d = zzvVar;
        this.f13090e = pendingIntent;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* bridge */ /* synthetic */ void b(Api.AnyClient anyClient) throws RemoteException {
        ((zzcc) ((zzaz) anyClient).getService()).zzf(new com.google.android.gms.fitness.request.zzan(this.f13089d, this.f13090e, new zzes(this)));
    }

    @Override // com.google.android.gms.internal.fitness.zzax, com.google.android.gms.common.api.internal.BasePendingResult
    protected final /* bridge */ /* synthetic */ Result createFailedResult(Status status) {
        return status;
    }
}
