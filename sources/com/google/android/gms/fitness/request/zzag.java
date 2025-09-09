package com.google.android.gms.fitness.request;

import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.fitness.data.DataPoint;

/* loaded from: classes3.dex */
final class zzag implements ListenerHolder.Notifier {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ DataPoint f12956a;

    zzag(zzaj zzajVar, DataPoint dataPoint) {
        this.f12956a = dataPoint;
    }

    @Override // com.google.android.gms.common.api.internal.ListenerHolder.Notifier
    public final /* bridge */ /* synthetic */ void notifyListener(Object obj) {
        ((OnDataPointListener) obj).onDataPoint(this.f12956a);
    }

    @Override // com.google.android.gms.common.api.internal.ListenerHolder.Notifier
    public final void onNotifyListenerFailed() {
    }
}
