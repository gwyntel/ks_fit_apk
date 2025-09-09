package com.google.android.gms.measurement.internal;

import android.os.Handler;
import com.google.android.gms.common.internal.Preconditions;

/* loaded from: classes3.dex */
abstract class zzaw {
    private static volatile Handler zza;
    private final zzic zzb;
    private final Runnable zzc;
    private volatile long zzd;

    zzaw(zzic zzicVar) {
        Preconditions.checkNotNull(zzicVar);
        this.zzb = zzicVar;
        this.zzc = new zzav(this, zzicVar);
    }

    private final Handler zzd() {
        Handler handler;
        if (zza != null) {
            return zza;
        }
        synchronized (zzaw.class) {
            try {
                if (zza == null) {
                    zza = new com.google.android.gms.internal.measurement.zzcp(this.zzb.zza().getMainLooper());
                }
                handler = zza;
            } catch (Throwable th) {
                throw th;
            }
        }
        return handler;
    }

    final void a() {
        this.zzd = 0L;
        zzd().removeCallbacks(this.zzc);
    }

    public final void zza(long j2) {
        a();
        if (j2 >= 0) {
            this.zzd = this.zzb.zzb().currentTimeMillis();
            if (zzd().postDelayed(this.zzc, j2)) {
                return;
            }
            this.zzb.zzj().zzg().zza("Failed to schedule delayed post. time", Long.valueOf(j2));
        }
    }

    public abstract void zzb();

    public final boolean zzc() {
        return this.zzd != 0;
    }
}
