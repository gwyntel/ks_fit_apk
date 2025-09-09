package com.google.android.gms.internal.fitness;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.fitness.data.DataSet;

/* loaded from: classes3.dex */
final class zzdi extends zzah {

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ DataSet f13072d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzdi(zzds zzdsVar, GoogleApiClient googleApiClient, DataSet dataSet, boolean z2) {
        super(googleApiClient);
        this.f13072d = dataSet;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* bridge */ /* synthetic */ void b(Api.AnyClient anyClient) throws RemoteException {
        ((zzca) ((zzaj) anyClient).getService()).zze(new com.google.android.gms.fitness.request.zzk(this.f13072d, (zzcp) new zzes(this), false));
    }
}
