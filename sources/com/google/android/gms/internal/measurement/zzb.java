package com.google.android.gms.internal.measurement;

import com.google.android.gms.internal.measurement.zzfo;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;

/* loaded from: classes3.dex */
public final class zzb {

    /* renamed from: a, reason: collision with root package name */
    zzac f13180a;
    private final zzf zzb;
    private zzh zzc;
    private final zzaa zzd;

    public zzb() {
        this(new zzf());
    }

    final /* synthetic */ zzal a() {
        return new zzw(this.zzd);
    }

    public final zzac zza() {
        return this.f13180a;
    }

    public final boolean zzc() {
        return !this.f13180a.zzc().isEmpty();
    }

    public final boolean zzd() {
        return !this.f13180a.zzb().equals(this.f13180a.zza());
    }

    private zzb(zzf zzfVar) {
        this.zzb = zzfVar;
        this.zzc = zzfVar.f13193a.zza();
        this.f13180a = new zzac();
        this.zzd = new zzaa();
        zzfVar.zza("internal.registerCallback", new Callable() { // from class: com.google.android.gms.internal.measurement.zza
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return this.zza.a();
            }
        });
        zzfVar.zza("internal.eventLogger", new Callable() { // from class: com.google.android.gms.internal.measurement.zzd
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return new zzk(this.zza.f13180a);
            }
        });
    }

    public final void zza(zzfo.zzc zzcVar) throws zzc {
        zzal zzalVar;
        try {
            this.zzc = this.zzb.f13193a.zza();
            if (this.zzb.zza(this.zzc, (zzfo.zzd[]) zzcVar.zzc().toArray(new zzfo.zzd[0])) instanceof zzaj) {
                throw new IllegalStateException("Program loading failed");
            }
            for (zzfo.zzb zzbVar : zzcVar.zza().zzd()) {
                List<zzfo.zzd> listZzc = zzbVar.zzc();
                String strZzb = zzbVar.zzb();
                Iterator<zzfo.zzd> it = listZzc.iterator();
                while (it.hasNext()) {
                    zzaq zzaqVarZza = this.zzb.zza(this.zzc, it.next());
                    if (!(zzaqVarZza instanceof zzap)) {
                        throw new IllegalArgumentException("Invalid rule definition");
                    }
                    zzh zzhVar = this.zzc;
                    if (zzhVar.zzb(strZzb)) {
                        zzaq zzaqVarZza2 = zzhVar.zza(strZzb);
                        if (!(zzaqVarZza2 instanceof zzal)) {
                            throw new IllegalStateException("Invalid function name: " + strZzb);
                        }
                        zzalVar = (zzal) zzaqVarZza2;
                    } else {
                        zzalVar = null;
                    }
                    if (zzalVar == null) {
                        throw new IllegalStateException("Rule function is undefined: " + strZzb);
                    }
                    zzalVar.zza(this.zzc, Collections.singletonList(zzaqVarZza));
                }
            }
        } catch (Throwable th) {
            throw new zzc(th);
        }
    }

    public final void zza(String str, Callable<? extends zzal> callable) {
        this.zzb.zza(str, callable);
    }

    public final boolean zza(zzad zzadVar) throws zzc {
        try {
            this.f13180a.zza(zzadVar);
            this.zzb.f13194b.zzc("runtime.counter", new zzai(Double.valueOf(0.0d)));
            this.zzd.zza(this.zzc.zza(), this.f13180a);
            if (zzd()) {
                return true;
            }
            return zzc();
        } catch (Throwable th) {
            throw new zzc(th);
        }
    }
}
