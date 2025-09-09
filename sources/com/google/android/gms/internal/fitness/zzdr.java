package com.google.android.gms.internal.fitness;

import android.util.Log;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.fitness.result.DataReadResult;

/* loaded from: classes3.dex */
final class zzdr extends zzbm {
    private final BaseImplementation.ResultHolder zza;
    private int zzb = 0;
    private DataReadResult zzc;

    @Override // com.google.android.gms.internal.fitness.zzbn
    public final void zzd(DataReadResult dataReadResult) {
        synchronized (this) {
            try {
                if (Log.isLoggable("Fitness", 2)) {
                    Log.v("Fitness", "Received batch result " + this.zzb);
                }
                DataReadResult dataReadResult2 = this.zzc;
                if (dataReadResult2 == null) {
                    this.zzc = dataReadResult;
                } else {
                    dataReadResult2.zzb(dataReadResult);
                }
                int i2 = this.zzb + 1;
                this.zzb = i2;
                if (i2 == this.zzc.zza()) {
                    this.zza.setResult(this.zzc);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
