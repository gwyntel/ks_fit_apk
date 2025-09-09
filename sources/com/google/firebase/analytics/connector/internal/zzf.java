package com.google.firebase.analytics.connector.internal;

import android.os.Bundle;
import com.google.android.gms.measurement.api.AppMeasurementSdk;

/* loaded from: classes3.dex */
final class zzf implements AppMeasurementSdk.OnEventListener {
    private final /* synthetic */ zzg zza;

    public zzf(zzg zzgVar) {
        this.zza = zzgVar;
    }

    @Override // com.google.android.gms.measurement.api.AppMeasurementSdk.OnEventListener, com.google.android.gms.measurement.internal.zzim
    public final void onEvent(String str, String str2, Bundle bundle, long j2) {
        if (str == null || !zzb.zze(str2)) {
            return;
        }
        Bundle bundle2 = new Bundle();
        bundle2.putString("name", str2);
        bundle2.putLong("timestampInMillis", j2);
        bundle2.putBundle("params", bundle);
        this.zza.zza.onMessageTriggered(3, bundle2);
    }
}
