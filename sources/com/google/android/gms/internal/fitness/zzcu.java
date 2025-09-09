package com.google.android.gms.internal.fitness;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;

/* loaded from: classes3.dex */
final class zzcu extends zzk {

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ String f13067d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzcu(zzcy zzcyVar, GoogleApiClient googleApiClient, String str) {
        super(googleApiClient);
        this.f13067d = str;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* bridge */ /* synthetic */ void b(Api.AnyClient anyClient) throws RemoteException {
        ((zzbx) ((zzm) anyClient).getService()).zzh(new com.google.android.gms.fitness.request.zzbf(this.f13067d, (zzcp) new zzes(this)));
    }
}
