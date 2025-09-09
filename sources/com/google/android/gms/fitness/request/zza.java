package com.google.android.gms.fitness.request;

import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.fitness.data.BleDevice;

/* loaded from: classes3.dex */
final class zza implements ListenerHolder.Notifier {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ BleDevice f12955a;

    zza(zze zzeVar, BleDevice bleDevice) {
        this.f12955a = bleDevice;
    }

    @Override // com.google.android.gms.common.api.internal.ListenerHolder.Notifier
    public final /* synthetic */ void notifyListener(Object obj) {
        ((BleScanCallback) obj).onDeviceFound(this.f12955a);
    }

    @Override // com.google.android.gms.common.api.internal.ListenerHolder.Notifier
    public final void onNotifyListenerFailed() {
    }
}
