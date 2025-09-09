package com.google.android.gms.internal.measurement;

import android.os.RemoteException;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.measurement.zzdf;

/* loaded from: classes3.dex */
final class zzdo extends zzdf.zza {
    private final /* synthetic */ Boolean zzc;
    private final /* synthetic */ zzdf zzd;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzdo(zzdf zzdfVar, Boolean bool) {
        super(zzdfVar);
        this.zzd = zzdfVar;
        this.zzc = bool;
    }

    @Override // com.google.android.gms.internal.measurement.zzdf.zza
    final void zza() throws RemoteException {
        if (this.zzc != null) {
            ((zzcu) Preconditions.checkNotNull(this.zzd.zzj)).setMeasurementEnabled(this.zzc.booleanValue(), this.f13188a);
        } else {
            ((zzcu) Preconditions.checkNotNull(this.zzd.zzj)).clearMeasurementEnabled(this.f13188a);
        }
    }
}
