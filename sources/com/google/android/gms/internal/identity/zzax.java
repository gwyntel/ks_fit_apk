package com.google.android.gms.internal.identity;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;

/* loaded from: classes3.dex */
final class zzax extends zzba {

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ boolean f13115d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzax(zzbb zzbbVar, GoogleApiClient googleApiClient, boolean z2) {
        super(googleApiClient);
        this.f13115d = z2;
    }

    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* bridge */ /* synthetic */ void b(Api.AnyClient anyClient) throws RemoteException {
        zzdz zzdzVar = (zzdz) anyClient;
        if (this.f13115d) {
            zzdzVar.zzy(zzbb.a(this));
        } else {
            zzdzVar.zzz(zzbb.a(this));
        }
    }
}
