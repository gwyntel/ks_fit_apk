package com.google.android.gms.internal.auth;

import android.content.Context;
import android.os.RemoteException;
import com.google.android.gms.auth.api.proxy.ProxyRequest;
import com.google.android.gms.common.api.GoogleApiClient;

/* loaded from: classes3.dex */
final class zzbq extends zzbi {

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ ProxyRequest f12991d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzbq(zzbt zzbtVar, GoogleApiClient googleApiClient, ProxyRequest proxyRequest) {
        super(googleApiClient);
        this.f12991d = proxyRequest;
    }

    @Override // com.google.android.gms.internal.auth.zzbi
    protected final void d(Context context, zzbh zzbhVar) throws RemoteException {
        zzbhVar.zze(new zzbp(this), this.f12991d);
    }
}
