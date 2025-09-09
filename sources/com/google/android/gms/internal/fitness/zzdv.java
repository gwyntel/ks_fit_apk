package com.google.android.gms.internal.fitness;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.fitness.data.Subscription;

/* loaded from: classes3.dex */
final class zzdv extends zzap {

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ Subscription f13082d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzdv(zzea zzeaVar, GoogleApiClient googleApiClient, Subscription subscription) {
        super(googleApiClient);
        this.f13082d = subscription;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* bridge */ /* synthetic */ void b(Api.AnyClient anyClient) throws RemoteException {
        ((zzcb) ((zzar) anyClient).getService()).zze(new com.google.android.gms.fitness.request.zzbd(this.f13082d, false, (zzcp) new zzes(this)));
    }
}
