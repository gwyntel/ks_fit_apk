package com.google.android.gms.measurement.internal;

import androidx.annotation.GuardedBy;

/* loaded from: classes3.dex */
public final class zzff<V> {
    private static final Object zza = new Object();
    private final String zzb;
    private final zzfd<V> zzc;
    private final V zzd;
    private final V zze;
    private final Object zzf;

    @GuardedBy("overrideLock")
    private volatile V zzg;

    @GuardedBy("cachingLock")
    private volatile V zzh;

    public final V zza(V v2) {
        synchronized (this.zzf) {
        }
        if (v2 != null) {
            return v2;
        }
        if (zzfg.f13276a == null) {
            return this.zzd;
        }
        synchronized (zza) {
            try {
                if (zzae.zza()) {
                    return this.zzh == null ? this.zzd : this.zzh;
                }
                try {
                    for (zzff zzffVar : zzbi.zzcv) {
                        if (zzae.zza()) {
                            throw new IllegalStateException("Refreshing flag cache must be done on a worker thread.");
                        }
                        V vZza = null;
                        try {
                            zzfd<V> zzfdVar = zzffVar.zzc;
                            if (zzfdVar != null) {
                                vZza = zzfdVar.zza();
                            }
                        } catch (IllegalStateException unused) {
                        }
                        synchronized (zza) {
                            zzffVar.zzh = vZza;
                        }
                    }
                } catch (SecurityException unused2) {
                }
                zzfd<V> zzfdVar2 = this.zzc;
                if (zzfdVar2 == null) {
                    return this.zzd;
                }
                try {
                    return zzfdVar2.zza();
                } catch (IllegalStateException unused3) {
                    return this.zzd;
                } catch (SecurityException unused4) {
                    return this.zzd;
                }
            } finally {
            }
        }
    }

    private zzff(String str, V v2, V v3, zzfd<V> zzfdVar) {
        this.zzf = new Object();
        this.zzg = null;
        this.zzh = null;
        this.zzb = str;
        this.zzd = v2;
        this.zze = v3;
        this.zzc = zzfdVar;
    }

    public final String zza() {
        return this.zzb;
    }
}
