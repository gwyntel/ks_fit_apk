package com.google.android.gms.measurement.internal;

import com.google.android.gms.common.internal.Preconditions;
import java.lang.Thread;

/* loaded from: classes3.dex */
final class zzhb implements Thread.UncaughtExceptionHandler {
    private final String zza;
    private final /* synthetic */ zzgz zzb;

    public zzhb(zzgz zzgzVar, String str) {
        this.zzb = zzgzVar;
        Preconditions.checkNotNull(str);
        this.zza = str;
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public final synchronized void uncaughtException(Thread thread, Throwable th) {
        this.zzb.zzj().zzg().zza(this.zza, th);
    }
}
