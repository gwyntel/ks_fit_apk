package com.google.firebase.analytics.connector.internal;

import android.os.Bundle;
import com.google.android.gms.measurement.api.AppMeasurementSdk;

/* loaded from: classes3.dex */
final class zzd implements AppMeasurementSdk.OnEventListener {
    private final /* synthetic */ zze zza;

    public zzd(zze zzeVar) {
        this.zza = zzeVar;
    }

    @Override // com.google.android.gms.measurement.api.AppMeasurementSdk.OnEventListener, com.google.android.gms.measurement.internal.zzim
    public final void onEvent(String str, String str2, Bundle bundle, long j2) {
        if (this.zza.f14991a.contains(str2)) {
            Bundle bundle2 = new Bundle();
            bundle2.putString("events", zzb.zza(str2));
            this.zza.zzb.onMessageTriggered(2, bundle2);
        }
    }
}
