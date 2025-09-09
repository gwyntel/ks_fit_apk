package com.google.android.gms.internal.identity;

import com.google.android.gms.common.api.internal.ListenerHolder;

/* loaded from: classes3.dex */
final class zzdx implements ListenerHolder.Notifier {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ zzdy f13165a;

    zzdx(zzdy zzdyVar) {
        this.f13165a = zzdyVar;
    }

    @Override // com.google.android.gms.common.api.internal.ListenerHolder.Notifier
    public final /* bridge */ /* synthetic */ void notifyListener(Object obj) {
        this.f13165a.d().zzc();
    }

    @Override // com.google.android.gms.common.api.internal.ListenerHolder.Notifier
    public final void onNotifyListenerFailed() {
    }
}
