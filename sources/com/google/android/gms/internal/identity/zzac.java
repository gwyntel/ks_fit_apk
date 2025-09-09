package com.google.android.gms.internal.identity;

import android.app.PendingIntent;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Preconditions;

/* loaded from: classes3.dex */
final class zzac extends zzae {

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ long f13101d;

    /* renamed from: e, reason: collision with root package name */
    final /* synthetic */ PendingIntent f13102e;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzac(zzaf zzafVar, GoogleApiClient googleApiClient, long j2, PendingIntent pendingIntent) {
        super(googleApiClient);
        this.f13101d = j2;
        this.f13102e = pendingIntent;
    }

    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* bridge */ /* synthetic */ void b(Api.AnyClient anyClient) throws RemoteException {
        PendingIntent pendingIntent = this.f13102e;
        zzg zzgVar = (zzg) anyClient;
        Preconditions.checkNotNull(pendingIntent);
        long j2 = this.f13101d;
        Preconditions.checkArgument(j2 >= 0, "detectionIntervalMillis must be >= 0");
        ((zzv) zzgVar.getService()).zzh(j2, true, pendingIntent);
        setResult((zzac) Status.RESULT_SUCCESS);
    }
}
