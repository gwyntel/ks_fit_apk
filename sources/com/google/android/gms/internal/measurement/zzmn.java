package com.google.android.gms.internal.measurement;

/* loaded from: classes3.dex */
public class zzmn {
    private static final zzlj zza = zzlj.f13225a;
    private zzkm zzb;
    private volatile zznj zzc;
    private volatile zzkm zzd;

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzmn)) {
            return false;
        }
        zzmn zzmnVar = (zzmn) obj;
        zznj zznjVar = this.zzc;
        zznj zznjVar2 = zzmnVar.zzc;
        return (zznjVar == null && zznjVar2 == null) ? zzc().equals(zzmnVar.zzc()) : (zznjVar == null || zznjVar2 == null) ? zznjVar != null ? zznjVar.equals(zzmnVar.zzb(zznjVar.h_())) : zzb(zznjVar2.h_()).equals(zznjVar2) : zznjVar.equals(zznjVar2);
    }

    public int hashCode() {
        return 1;
    }

    public final zznj zza(zznj zznjVar) {
        zznj zznjVar2 = this.zzc;
        this.zzb = null;
        this.zzd = null;
        this.zzc = zznjVar;
        return zznjVar2;
    }

    public final int zzb() {
        if (this.zzd != null) {
            return this.zzd.zzb();
        }
        if (this.zzc != null) {
            return this.zzc.zzbw();
        }
        return 0;
    }

    public final zzkm zzc() {
        if (this.zzd != null) {
            return this.zzd;
        }
        synchronized (this) {
            try {
                if (this.zzd != null) {
                    return this.zzd;
                }
                if (this.zzc == null) {
                    this.zzd = zzkm.zza;
                } else {
                    this.zzd = this.zzc.zzbu();
                }
                return this.zzd;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private final zznj zzb(zznj zznjVar) {
        if (this.zzc == null) {
            synchronized (this) {
                if (this.zzc == null) {
                    try {
                        this.zzc = zznjVar;
                        this.zzd = zzkm.zza;
                    } catch (zzme unused) {
                        this.zzc = zznjVar;
                        this.zzd = zzkm.zza;
                    }
                }
            }
        }
        return this.zzc;
    }
}
