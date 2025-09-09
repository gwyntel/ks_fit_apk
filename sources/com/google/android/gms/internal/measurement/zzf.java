package com.google.android.gms.internal.measurement;

import com.google.android.gms.internal.measurement.zzfo;
import java.util.concurrent.Callable;

/* loaded from: classes3.dex */
public final class zzf {

    /* renamed from: a, reason: collision with root package name */
    final zzh f13193a;

    /* renamed from: b, reason: collision with root package name */
    final zzh f13194b;
    private final zzbb zzc;
    private final zzl zzd;

    public zzf() {
        zzbb zzbbVar = new zzbb();
        this.zzc = zzbbVar;
        zzh zzhVar = new zzh(null, zzbbVar);
        this.f13194b = zzhVar;
        this.f13193a = zzhVar.zza();
        zzl zzlVar = new zzl();
        this.zzd = zzlVar;
        zzhVar.zzc("require", new zzz(zzlVar));
        zzlVar.zza("internal.platform", new Callable() { // from class: com.google.android.gms.internal.measurement.zze
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return new zzy();
            }
        });
        zzhVar.zzc("runtime.counter", new zzai(Double.valueOf(0.0d)));
    }

    public final zzaq zza(zzh zzhVar, zzfo.zzd... zzdVarArr) {
        zzaq zzaqVarZza = zzaq.zzc;
        for (zzfo.zzd zzdVar : zzdVarArr) {
            zzaqVarZza = zzj.zza(zzdVar);
            zzg.zza(this.f13194b);
            if ((zzaqVarZza instanceof zzat) || (zzaqVarZza instanceof zzar)) {
                zzaqVarZza = this.zzc.zza(zzhVar, zzaqVarZza);
            }
        }
        return zzaqVarZza;
    }

    public final void zza(String str, Callable<? extends zzal> callable) {
        this.zzd.zza(str, callable);
    }
}
