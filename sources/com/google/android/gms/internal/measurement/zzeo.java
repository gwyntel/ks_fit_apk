package com.google.android.gms.internal.measurement;

import android.app.Activity;
import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.internal.measurement.zzdf;

/* loaded from: classes3.dex */
final class zzeo extends zzdf.zza {
    private final /* synthetic */ Bundle zzc;
    private final /* synthetic */ Activity zzd;
    private final /* synthetic */ zzdf.zzd zze;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzeo(zzdf.zzd zzdVar, Bundle bundle, Activity activity) {
        super(zzdf.this);
        this.zze = zzdVar;
        this.zzc = bundle;
        this.zzd = activity;
    }

    @Override // com.google.android.gms.internal.measurement.zzdf.zza
    final void zza() throws RemoteException {
        Bundle bundle;
        if (this.zzc != null) {
            bundle = new Bundle();
            if (this.zzc.containsKey("com.google.app_measurement.screen_service")) {
                Object obj = this.zzc.get("com.google.app_measurement.screen_service");
                if (obj instanceof Bundle) {
                    bundle.putBundle("com.google.app_measurement.screen_service", (Bundle) obj);
                }
            }
        } else {
            bundle = null;
        }
        ((zzcu) Preconditions.checkNotNull(zzdf.this.zzj)).onActivityCreated(ObjectWrapper.wrap(this.zzd), bundle, this.f13189b);
    }
}
