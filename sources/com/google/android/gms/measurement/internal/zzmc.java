package com.google.android.gms.measurement.internal;

import android.os.Bundle;
import com.google.android.gms.internal.measurement.zzsm;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes3.dex */
final class zzmc implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    long f13308a;

    /* renamed from: b, reason: collision with root package name */
    long f13309b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ zzlz f13310c;

    zzmc(zzlz zzlzVar, long j2, long j3) {
        this.f13310c = zzlzVar;
        this.f13308a = j2;
        this.f13309b = j3;
    }

    @Override // java.lang.Runnable
    public final void run() throws IllegalStateException {
        this.f13310c.f13307a.zzl().zzb(new Runnable() { // from class: com.google.android.gms.measurement.internal.zzmb
            @Override // java.lang.Runnable
            public final void run() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                zzmc zzmcVar = this.zza;
                zzlz zzlzVar = zzmcVar.f13310c;
                long j2 = zzmcVar.f13308a;
                long j3 = zzmcVar.f13309b;
                zzlzVar.f13307a.zzt();
                zzlzVar.f13307a.zzj().zzc().zza("Application going to the background");
                zzlzVar.f13307a.zzk().zzn.zza(true);
                zzlzVar.f13307a.d(true);
                if (!zzlzVar.f13307a.zze().zzu()) {
                    zzlzVar.f13307a.f13306c.d(j3);
                    zzlzVar.f13307a.zza(false, false, j3);
                }
                if (zzsm.zzb() && zzlzVar.f13307a.zze().zza(zzbi.zzce)) {
                    zzlzVar.f13307a.zzj().zzn().zza("Application backgrounded at: timestamp_millis", Long.valueOf(j2));
                } else {
                    zzlzVar.f13307a.zzm().l("auto", "_ab", j2, new Bundle());
                }
            }
        });
    }
}
