package com.google.android.gms.internal.fitness;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;

/* loaded from: classes3.dex */
final class zzcr extends zzk {

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ com.google.android.gms.fitness.request.zzab f13064d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzcr(zzcy zzcyVar, GoogleApiClient googleApiClient, com.google.android.gms.fitness.request.zzab zzabVar) {
        super(googleApiClient);
        this.f13064d = zzabVar;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* bridge */ /* synthetic */ void b(Api.AnyClient anyClient) throws RemoteException {
        ((zzbx) ((zzm) anyClient).getService()).zzg(new com.google.android.gms.fitness.request.zzbb(this.f13064d, new zzes(this)));
    }
}
