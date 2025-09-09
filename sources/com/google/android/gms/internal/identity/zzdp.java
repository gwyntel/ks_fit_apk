package com.google.android.gms.internal.identity;

import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.location.DeviceOrientation;
import com.google.android.gms.location.DeviceOrientationListener;

/* loaded from: classes3.dex */
final class zzdp implements ListenerHolder.Notifier {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ DeviceOrientation f13160a;

    zzdp(zzdq zzdqVar, DeviceOrientation deviceOrientation) {
        this.f13160a = deviceOrientation;
    }

    @Override // com.google.android.gms.common.api.internal.ListenerHolder.Notifier
    public final /* bridge */ /* synthetic */ void notifyListener(Object obj) {
        ((DeviceOrientationListener) obj).onDeviceOrientationChanged(this.f13160a);
    }

    @Override // com.google.android.gms.common.api.internal.ListenerHolder.Notifier
    public final void onNotifyListenerFailed() {
    }
}
