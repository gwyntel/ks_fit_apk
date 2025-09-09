package com.google.android.gms.measurement.internal;

import android.os.Bundle;
import androidx.annotation.VisibleForTesting;
import androidx.annotation.WorkerThread;
import com.google.android.gms.internal.measurement.zzrd;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes3.dex */
final class zzme {

    /* renamed from: a, reason: collision with root package name */
    protected long f13311a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ zzly f13312b;

    @VisibleForTesting
    private long zzc;
    private final zzaw zzd;

    public zzme(zzly zzlyVar) {
        this.f13312b = zzlyVar;
        this.zzd = new zzmd(this, zzlyVar.f13286a);
        long jElapsedRealtime = zzlyVar.zzb().elapsedRealtime();
        this.zzc = jElapsedRealtime;
        this.f13311a = jElapsedRealtime;
    }

    static /* synthetic */ void c(zzme zzmeVar) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        zzmeVar.f13312b.zzt();
        zzmeVar.zza(false, false, zzmeVar.f13312b.zzb().elapsedRealtime());
        zzmeVar.f13312b.zzc().zza(zzmeVar.f13312b.zzb().elapsedRealtime());
    }

    final long a(long j2) {
        long j3 = j2 - this.f13311a;
        this.f13311a = j2;
        return j3;
    }

    final void b() {
        this.zzd.a();
        this.zzc = 0L;
        this.f13311a = 0L;
    }

    final void d(long j2) {
        this.zzd.a();
    }

    final void e(long j2) {
        this.f13312b.zzt();
        this.zzd.a();
        this.zzc = j2;
        this.f13311a = j2;
    }

    @WorkerThread
    public final boolean zza(boolean z2, boolean z3, long j2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.f13312b.zzt();
        this.f13312b.zzu();
        if (!zzrd.zzb() || !this.f13312b.zze().zza(zzbi.zzbn) || this.f13312b.f13286a.zzac()) {
            this.f13312b.zzk().zzk.zza(this.f13312b.zzb().currentTimeMillis());
        }
        long jA = j2 - this.zzc;
        if (!z2 && jA < 1000) {
            this.f13312b.zzj().zzp().zza("Screen exposed for less than 1000 ms. Event not sent. time", Long.valueOf(jA));
            return false;
        }
        if (!z3) {
            jA = a(j2);
        }
        this.f13312b.zzj().zzp().zza("Recording user engagement, ms", Long.valueOf(jA));
        Bundle bundle = new Bundle();
        bundle.putLong("_et", jA);
        zzne.zza(this.f13312b.zzn().zza(!this.f13312b.zze().zzu()), bundle, true);
        if (!z3) {
            this.f13312b.zzm().r("auto", "_e", bundle);
        }
        this.zzc = j2;
        this.zzd.a();
        this.zzd.zza(3600000L);
        return true;
    }
}
