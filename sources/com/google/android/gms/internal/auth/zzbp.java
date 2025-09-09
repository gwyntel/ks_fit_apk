package com.google.android.gms.internal.auth;

import com.google.android.gms.auth.api.proxy.ProxyResponse;

/* loaded from: classes3.dex */
final class zzbp extends zzbd {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ zzbq f12990a;

    zzbp(zzbq zzbqVar) {
        this.f12990a = zzbqVar;
    }

    @Override // com.google.android.gms.internal.auth.zzbd, com.google.android.gms.internal.auth.zzbg
    public final void zzb(ProxyResponse proxyResponse) {
        this.f12990a.setResult((zzbq) new zzbu(proxyResponse));
    }
}
