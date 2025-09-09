package com.google.android.gms.measurement.internal;

import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.google.android.gms.internal.measurement.zzev;
import com.google.android.gms.internal.measurement.zzfh;
import com.google.android.gms.internal.measurement.zzqx;

/* loaded from: classes3.dex */
final class zzab extends zzac {
    private zzev.zze zzg;
    private final /* synthetic */ zzt zzh;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzab(zzt zztVar, String str, int i2, zzev.zze zzeVar) {
        super(str, i2);
        this.zzh = zztVar;
        this.zzg = zzeVar;
    }

    @Override // com.google.android.gms.measurement.internal.zzac
    final int a() {
        return this.zzg.zza();
    }

    @Override // com.google.android.gms.measurement.internal.zzac
    final boolean g() {
        return false;
    }

    @Override // com.google.android.gms.measurement.internal.zzac
    final boolean h() {
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    final boolean i(Long l2, Long l3, zzfh.zzn zznVar, boolean z2) {
        byte b2 = zzqx.zzb() && this.zzh.zze().zzf(this.f13246a, zzbi.zzbe);
        boolean zZzf = this.zzg.zzf();
        boolean zZzg = this.zzg.zzg();
        boolean zZzh = this.zzg.zzh();
        byte b3 = zZzf || zZzg || zZzh;
        Boolean boolD = null;
        boolD = null;
        boolD = null;
        boolD = null;
        boolD = null;
        if (z2 && b3 != true) {
            this.zzh.zzj().zzp().zza("Property filter already evaluated true and it is not associated with an enhanced audience. audience ID, filter ID", Integer.valueOf(this.f13247b), this.zzg.zzi() ? Integer.valueOf(this.zzg.zza()) : null);
            return true;
        }
        zzev.zzc zzcVarZzb = this.zzg.zzb();
        boolean zZzf2 = zzcVarZzb.zzf();
        if (zznVar.zzk()) {
            if (zzcVarZzb.zzh()) {
                boolD = zzac.d(zzac.c(zznVar.zzc(), zzcVarZzb.zzc()), zZzf2);
            } else {
                this.zzh.zzj().zzu().zza("No number filter for long property. property", this.zzh.zzi().e(zznVar.zzg()));
            }
        } else if (zznVar.zzi()) {
            if (zzcVarZzb.zzh()) {
                boolD = zzac.d(zzac.b(zznVar.zza(), zzcVarZzb.zzc()), zZzf2);
            } else {
                this.zzh.zzj().zzu().zza("No number filter for double property. property", this.zzh.zzi().e(zznVar.zzg()));
            }
        } else if (!zznVar.zzm()) {
            this.zzh.zzj().zzu().zza("User property has no value, property", this.zzh.zzi().e(zznVar.zzg()));
        } else if (zzcVarZzb.zzj()) {
            boolD = zzac.d(zzac.f(zznVar.zzh(), zzcVarZzb.zzd(), this.zzh.zzj()), zZzf2);
        } else if (!zzcVarZzb.zzh()) {
            this.zzh.zzj().zzu().zza("No string or number filter defined. property", this.zzh.zzi().e(zznVar.zzg()));
        } else if (zzna.w(zznVar.zzh())) {
            boolD = zzac.d(zzac.e(zznVar.zzh(), zzcVarZzb.zzc()), zZzf2);
        } else {
            this.zzh.zzj().zzu().zza("Invalid user property value for Numeric number filter. property, value", this.zzh.zzi().e(zznVar.zzg()), zznVar.zzh());
        }
        this.zzh.zzj().zzp().zza("Property filter result", boolD == null ? TmpConstant.GROUP_ROLE_UNKNOWN : boolD);
        if (boolD == null) {
            return false;
        }
        this.f13248c = Boolean.TRUE;
        if (zZzh && !boolD.booleanValue()) {
            return true;
        }
        if (!z2 || this.zzg.zzf()) {
            this.f13249d = boolD;
        }
        if (boolD.booleanValue() && b3 != false && zznVar.zzl()) {
            long jZzd = zznVar.zzd();
            if (l2 != null) {
                jZzd = l2.longValue();
            }
            if (b2 != false && this.zzg.zzf() && !this.zzg.zzg() && l3 != null) {
                jZzd = l3.longValue();
            }
            if (this.zzg.zzg()) {
                this.f13251f = Long.valueOf(jZzd);
            } else {
                this.f13250e = Long.valueOf(jZzd);
            }
        }
        return true;
    }
}
