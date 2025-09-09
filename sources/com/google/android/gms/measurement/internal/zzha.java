package com.google.android.gms.measurement.internal;

import androidx.annotation.NonNull;
import com.google.android.gms.common.internal.Preconditions;
import java.lang.Thread;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/* loaded from: classes3.dex */
final class zzha<V> extends FutureTask<V> implements Comparable<zzha<V>> {

    /* renamed from: a, reason: collision with root package name */
    final boolean f13284a;
    private final long zzb;
    private final String zzc;
    private final /* synthetic */ zzgz zzd;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzha(zzgz zzgzVar, Runnable runnable, boolean z2, String str) {
        super(com.google.android.gms.internal.measurement.zzcl.zza().zza(runnable), null);
        this.zzd = zzgzVar;
        Preconditions.checkNotNull(str);
        long andIncrement = zzgz.zza.getAndIncrement();
        this.zzb = andIncrement;
        this.zzc = str;
        this.f13284a = z2;
        if (andIncrement == Long.MAX_VALUE) {
            zzgzVar.zzj().zzg().zza("Tasks index overflow");
        }
    }

    @Override // java.lang.Comparable
    public final /* synthetic */ int compareTo(@NonNull Object obj) {
        zzha zzhaVar = (zzha) obj;
        boolean z2 = this.f13284a;
        if (z2 != zzhaVar.f13284a) {
            return z2 ? -1 : 1;
        }
        long j2 = this.zzb;
        long j3 = zzhaVar.zzb;
        if (j2 < j3) {
            return -1;
        }
        if (j2 > j3) {
            return 1;
        }
        this.zzd.zzj().zzm().zza("Two tasks share the same index. index", Long.valueOf(this.zzb));
        return 0;
    }

    @Override // java.util.concurrent.FutureTask
    protected final void setException(Throwable th) {
        Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler;
        this.zzd.zzj().zzg().zza(this.zzc, th);
        if ((th instanceof zzgy) && (defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()) != null) {
            defaultUncaughtExceptionHandler.uncaughtException(Thread.currentThread(), th);
        }
        super.setException(th);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzha(zzgz zzgzVar, Callable callable, boolean z2, String str) {
        super(com.google.android.gms.internal.measurement.zzcl.zza().zza(callable));
        this.zzd = zzgzVar;
        Preconditions.checkNotNull(str);
        long andIncrement = zzgz.zza.getAndIncrement();
        this.zzb = andIncrement;
        this.zzc = str;
        this.f13284a = z2;
        if (andIncrement == Long.MAX_VALUE) {
            zzgzVar.zzj().zzg().zza("Tasks index overflow");
        }
    }
}
