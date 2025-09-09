package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.internal.BaseGmsClient;

/* loaded from: classes3.dex */
final class zabp implements BaseGmsClient.SignOutCallbacks {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ zabq f12745a;

    zabp(zabq zabqVar) {
        this.f12745a = zabqVar;
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient.SignOutCallbacks
    public final void onSignOutComplete() {
        this.f12745a.f12746a.zar.post(new zabo(this));
    }
}
