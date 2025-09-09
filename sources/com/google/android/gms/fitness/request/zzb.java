package com.google.android.gms.fitness.request;

import com.google.android.gms.common.api.internal.ListenerHolder;

/* loaded from: classes3.dex */
final class zzb implements ListenerHolder.Notifier {
    zzb(zze zzeVar) {
    }

    @Override // com.google.android.gms.common.api.internal.ListenerHolder.Notifier
    public final /* synthetic */ void notifyListener(Object obj) {
        ((BleScanCallback) obj).onScanStopped();
    }

    @Override // com.google.android.gms.common.api.internal.ListenerHolder.Notifier
    public final void onNotifyListenerFailed() {
    }
}
