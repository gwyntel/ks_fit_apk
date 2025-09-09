package com.google.android.gms.measurement.internal;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.WorkerThread;
import com.google.android.gms.common.util.Clock;
import org.checkerframework.dataflow.qual.Pure;

/* loaded from: classes3.dex */
public final class zzly extends zze {

    /* renamed from: b, reason: collision with root package name */
    protected final zzmg f13305b;

    /* renamed from: c, reason: collision with root package name */
    protected final zzme f13306c;
    private Handler zzc;
    private boolean zzd;
    private final zzlz zze;

    zzly(zzhc zzhcVar) {
        super(zzhcVar);
        this.zzd = true;
        this.f13305b = new zzmg(this);
        this.f13306c = new zzme(this);
        this.zze = new zzlz(this);
    }

    static /* synthetic */ void c(zzly zzlyVar, long j2) {
        zzlyVar.zzt();
        zzlyVar.zzab();
        zzlyVar.zzj().zzp().zza("Activity paused, time", Long.valueOf(j2));
        zzlyVar.zze.b(j2);
        if (zzlyVar.zze().zzu()) {
            zzlyVar.f13306c.d(j2);
        }
    }

    static /* synthetic */ void g(zzly zzlyVar, long j2) {
        zzlyVar.zzt();
        zzlyVar.zzab();
        zzlyVar.zzj().zzp().zza("Activity resumed, time", Long.valueOf(j2));
        if (zzlyVar.zze().zza(zzbi.zzcj)) {
            if (zzlyVar.zze().zzu() || zzlyVar.zzd) {
                zzlyVar.f13306c.e(j2);
            }
        } else if (zzlyVar.zze().zzu() || zzlyVar.zzk().zzn.zza()) {
            zzlyVar.f13306c.e(j2);
        }
        zzlyVar.zze.a();
        zzmg zzmgVar = zzlyVar.f13305b;
        zzmgVar.f13313a.zzt();
        if (zzmgVar.f13313a.f13286a.zzac()) {
            zzmgVar.b(zzmgVar.f13313a.zzb().currentTimeMillis(), false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @WorkerThread
    public final void zzab() {
        zzt();
        if (this.zzc == null) {
            this.zzc = new com.google.android.gms.internal.measurement.zzcp(Looper.getMainLooper());
        }
    }

    final void d(boolean z2) {
        zzt();
        this.zzd = z2;
    }

    final boolean e() {
        zzt();
        return this.zzd;
    }

    @Override // com.google.android.gms.measurement.internal.zzia, com.google.android.gms.measurement.internal.zzic
    @Pure
    public final /* bridge */ /* synthetic */ Context zza() {
        return super.zza();
    }

    @Override // com.google.android.gms.measurement.internal.zzia, com.google.android.gms.measurement.internal.zzic
    @Pure
    public final /* bridge */ /* synthetic */ Clock zzb() {
        return super.zzb();
    }

    @Override // com.google.android.gms.measurement.internal.zzf
    public final /* bridge */ /* synthetic */ zzb zzc() {
        return super.zzc();
    }

    @Override // com.google.android.gms.measurement.internal.zzia, com.google.android.gms.measurement.internal.zzic
    @Pure
    public final /* bridge */ /* synthetic */ zzae zzd() {
        return super.zzd();
    }

    @Override // com.google.android.gms.measurement.internal.zzia
    @Pure
    public final /* bridge */ /* synthetic */ zzaf zze() {
        return super.zze();
    }

    @Override // com.google.android.gms.measurement.internal.zzia
    @Pure
    public final /* bridge */ /* synthetic */ zzba zzf() {
        return super.zzf();
    }

    @Override // com.google.android.gms.measurement.internal.zzf
    public final /* bridge */ /* synthetic */ zzfm zzg() {
        return super.zzg();
    }

    @Override // com.google.android.gms.measurement.internal.zzf
    public final /* bridge */ /* synthetic */ zzfl zzh() {
        return super.zzh();
    }

    @Override // com.google.android.gms.measurement.internal.zzia
    @Pure
    public final /* bridge */ /* synthetic */ zzfn zzi() {
        return super.zzi();
    }

    @Override // com.google.android.gms.measurement.internal.zzia, com.google.android.gms.measurement.internal.zzic
    @Pure
    public final /* bridge */ /* synthetic */ zzfs zzj() {
        return super.zzj();
    }

    @Override // com.google.android.gms.measurement.internal.zzia
    @Pure
    public final /* bridge */ /* synthetic */ zzge zzk() {
        return super.zzk();
    }

    @Override // com.google.android.gms.measurement.internal.zzia, com.google.android.gms.measurement.internal.zzic
    @Pure
    public final /* bridge */ /* synthetic */ zzgz zzl() {
        return super.zzl();
    }

    @Override // com.google.android.gms.measurement.internal.zzf
    public final /* bridge */ /* synthetic */ zzin zzm() {
        return super.zzm();
    }

    @Override // com.google.android.gms.measurement.internal.zzf
    public final /* bridge */ /* synthetic */ zzki zzn() {
        return super.zzn();
    }

    @Override // com.google.android.gms.measurement.internal.zzf
    public final /* bridge */ /* synthetic */ zzkq zzo() {
        return super.zzo();
    }

    @Override // com.google.android.gms.measurement.internal.zzf
    public final /* bridge */ /* synthetic */ zzly zzp() {
        return super.zzp();
    }

    @Override // com.google.android.gms.measurement.internal.zzia
    @Pure
    public final /* bridge */ /* synthetic */ zzne zzq() {
        return super.zzq();
    }

    @Override // com.google.android.gms.measurement.internal.zzf, com.google.android.gms.measurement.internal.zzia
    public final /* bridge */ /* synthetic */ void zzr() {
        super.zzr();
    }

    @Override // com.google.android.gms.measurement.internal.zzf, com.google.android.gms.measurement.internal.zzia
    public final /* bridge */ /* synthetic */ void zzs() {
        super.zzs();
    }

    @Override // com.google.android.gms.measurement.internal.zzf, com.google.android.gms.measurement.internal.zzia
    public final /* bridge */ /* synthetic */ void zzt() {
        super.zzt();
    }

    @Override // com.google.android.gms.measurement.internal.zze
    protected final boolean zzz() {
        return false;
    }

    public final boolean zza(boolean z2, boolean z3, long j2) {
        return this.f13306c.zza(z2, z3, j2);
    }
}
