package com.google.android.gms.measurement.internal;

import java.lang.reflect.InvocationTargetException;

/* loaded from: classes3.dex */
final class zzjc implements Runnable {
    private final /* synthetic */ boolean zza;
    private final /* synthetic */ zzin zzb;

    zzjc(zzin zzinVar, boolean z2) {
        this.zzb = zzinVar;
        this.zza = z2;
    }

    @Override // java.lang.Runnable
    public final void run() throws IllegalStateException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        boolean zZzac = this.zzb.f13286a.zzac();
        boolean zZzab = this.zzb.f13286a.zzab();
        this.zzb.f13286a.d(this.zza);
        if (zZzab == this.zza) {
            this.zzb.f13286a.zzj().zzp().zza("Default data collection state already set to", Boolean.valueOf(this.zza));
        }
        if (this.zzb.f13286a.zzac() == zZzac || this.zzb.f13286a.zzac() != this.zzb.f13286a.zzab()) {
            this.zzb.f13286a.zzj().zzv().zza("Default data collection is different than actual status", Boolean.valueOf(this.zza), Boolean.valueOf(zZzac));
        }
        this.zzb.zzap();
    }
}
