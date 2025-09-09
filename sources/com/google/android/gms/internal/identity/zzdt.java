package com.google.android.gms.internal.identity;

import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;

/* loaded from: classes3.dex */
final class zzdt implements ListenerHolder.Notifier {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ LocationAvailability f13162a;

    zzdt(zzdv zzdvVar, LocationAvailability locationAvailability) {
        this.f13162a = locationAvailability;
    }

    @Override // com.google.android.gms.common.api.internal.ListenerHolder.Notifier
    public final /* synthetic */ void notifyListener(Object obj) {
        ((LocationCallback) obj).onLocationAvailability(this.f13162a);
    }

    @Override // com.google.android.gms.common.api.internal.ListenerHolder.Notifier
    public final void onNotifyListenerFailed() {
    }
}
