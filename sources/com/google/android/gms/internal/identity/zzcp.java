package com.google.android.gms.internal.identity;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import java.util.List;

/* loaded from: classes3.dex */
final class zzcp extends zzcq {

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ List f13139d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzcp(zzcr zzcrVar, GoogleApiClient googleApiClient, List list) {
        super(googleApiClient);
        this.f13139d = list;
    }

    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* bridge */ /* synthetic */ void b(Api.AnyClient anyClient) throws RemoteException {
        ((zzdz) anyClient).zzF(zzem.zza(this.f13139d), zzcr.a(this));
    }
}
