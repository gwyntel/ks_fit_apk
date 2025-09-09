package com.google.android.gms.measurement.internal;

import android.app.ActivityManager;
import android.os.Bundle;
import android.text.TextUtils;
import androidx.annotation.VisibleForTesting;
import androidx.annotation.WorkerThread;
import com.google.android.gms.internal.measurement.zzqr;
import com.google.android.gms.internal.measurement.zzss;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes3.dex */
final class zzmg {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ zzly f13313a;

    zzmg(zzly zzlyVar) {
        this.f13313a = zzlyVar;
    }

    @VisibleForTesting
    @WorkerThread
    private final void zzb(long j2, boolean z2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.f13313a.zzt();
        if (this.f13313a.f13286a.zzac()) {
            this.f13313a.zzk().zzk.zza(j2);
            this.f13313a.zzj().zzp().zza("Session started, time", Long.valueOf(this.f13313a.zzb().elapsedRealtime()));
            long j3 = j2 / 1000;
            this.f13313a.zzm().n("auto", "_sid", Long.valueOf(j3), j2);
            this.f13313a.zzk().zzl.zza(j3);
            this.f13313a.zzk().zzg.zza(false);
            Bundle bundle = new Bundle();
            bundle.putLong("_sid", j3);
            if (this.f13313a.zze().zza(zzbi.zzbj) && z2) {
                bundle.putLong("_aib", 1L);
            }
            this.f13313a.zzm().l("auto", "_s", j2, bundle);
            if (zzqr.zzb() && this.f13313a.zze().zza(zzbi.zzbm)) {
                String strZza = this.f13313a.zzk().zzq.zza();
                if (TextUtils.isEmpty(strZza)) {
                    return;
                }
                Bundle bundle2 = new Bundle();
                bundle2.putString("_ffr", strZza);
                this.f13313a.zzm().l("auto", "_ssr", j2, bundle2);
            }
        }
    }

    final void a() {
        this.f13313a.zzt();
        if (this.f13313a.zzk().e(this.f13313a.zzb().currentTimeMillis())) {
            this.f13313a.zzk().zzg.zza(true);
            ActivityManager.RunningAppProcessInfo runningAppProcessInfo = new ActivityManager.RunningAppProcessInfo();
            ActivityManager.getMyMemoryState(runningAppProcessInfo);
            if (runningAppProcessInfo.importance == 100) {
                this.f13313a.zzj().zzp().zza("Detected application was in foreground");
                zzb(this.f13313a.zzb().currentTimeMillis(), false);
            }
        }
    }

    final void b(long j2, boolean z2) {
        this.f13313a.zzt();
        this.f13313a.zzab();
        if (this.f13313a.zzk().e(j2)) {
            this.f13313a.zzk().zzg.zza(true);
            if (zzss.zzb() && this.f13313a.zze().zza(zzbi.zzbs)) {
                this.f13313a.zzg().h();
            }
        }
        this.f13313a.zzk().zzk.zza(j2);
        if (this.f13313a.zzk().zzg.zza()) {
            zzb(j2, z2);
        }
    }
}
