package com.google.android.gms.measurement.internal;

import android.content.Context;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import java.lang.Thread;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.checkerframework.dataflow.qual.Pure;

/* loaded from: classes3.dex */
public final class zzgz extends zzid {
    private static final AtomicLong zza = new AtomicLong(Long.MIN_VALUE);

    @Nullable
    private zzhd zzb;

    @Nullable
    private zzhd zzc;
    private final PriorityBlockingQueue<zzha<?>> zzd;
    private final BlockingQueue<zzha<?>> zze;
    private final Thread.UncaughtExceptionHandler zzf;
    private final Thread.UncaughtExceptionHandler zzg;
    private final Object zzh;
    private final Semaphore zzi;
    private volatile boolean zzj;

    zzgz(zzhc zzhcVar) {
        super(zzhcVar);
        this.zzh = new Object();
        this.zzi = new Semaphore(2);
        this.zzd = new PriorityBlockingQueue<>();
        this.zze = new LinkedBlockingQueue();
        this.zzf = new zzhb(this, "Thread death: Uncaught exception on worker thread");
        this.zzg = new zzhb(this, "Thread death: Uncaught exception on network thread");
    }

    @Override // com.google.android.gms.measurement.internal.zzid
    protected final boolean a() {
        return false;
    }

    final Object d(AtomicReference atomicReference, long j2, String str, Runnable runnable) {
        synchronized (atomicReference) {
            zzl().zzb(runnable);
            try {
                atomicReference.wait(j2);
            } catch (InterruptedException unused) {
                zzj().zzu().zza("Interrupted waiting for " + str);
                return null;
            }
        }
        Object obj = atomicReference.get();
        if (obj == null) {
            zzj().zzu().zza("Timed out waiting for " + str);
        }
        return obj;
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

    public final void zzc(Runnable runnable) throws IllegalStateException {
        zzab();
        Preconditions.checkNotNull(runnable);
        zza(new zzha<>(this, runnable, true, "Task exception on worker thread"));
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

    public final boolean zzg() {
        return Thread.currentThread() == this.zzb;
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

    @Override // com.google.android.gms.measurement.internal.zzia
    @Pure
    public final /* bridge */ /* synthetic */ zzne zzq() {
        return super.zzq();
    }

    @Override // com.google.android.gms.measurement.internal.zzia
    public final void zzr() {
        if (Thread.currentThread() != this.zzc) {
            throw new IllegalStateException("Call expected from network thread");
        }
    }

    @Override // com.google.android.gms.measurement.internal.zzia
    public final /* bridge */ /* synthetic */ void zzs() {
        super.zzs();
    }

    @Override // com.google.android.gms.measurement.internal.zzia
    public final void zzt() {
        if (Thread.currentThread() != this.zzb) {
            throw new IllegalStateException("Call expected from worker thread");
        }
    }

    public final <V> Future<V> zza(Callable<V> callable) throws IllegalStateException {
        zzab();
        Preconditions.checkNotNull(callable);
        zzha<?> zzhaVar = new zzha<>(this, (Callable) callable, false, "Task exception on worker thread");
        if (Thread.currentThread() == this.zzb) {
            if (!this.zzd.isEmpty()) {
                zzj().zzu().zza("Callable skipped the worker queue.");
            }
            zzhaVar.run();
        } else {
            zza(zzhaVar);
        }
        return zzhaVar;
    }

    public final <V> Future<V> zzb(Callable<V> callable) throws IllegalStateException {
        zzab();
        Preconditions.checkNotNull(callable);
        zzha<?> zzhaVar = new zzha<>(this, (Callable) callable, true, "Task exception on worker thread");
        if (Thread.currentThread() == this.zzb) {
            zzhaVar.run();
        } else {
            zza(zzhaVar);
        }
        return zzhaVar;
    }

    public final void zzb(Runnable runnable) throws IllegalStateException {
        zzab();
        Preconditions.checkNotNull(runnable);
        zza(new zzha<>(this, runnable, false, "Task exception on worker thread"));
    }

    private final void zza(zzha<?> zzhaVar) {
        synchronized (this.zzh) {
            try {
                this.zzd.add(zzhaVar);
                zzhd zzhdVar = this.zzb;
                if (zzhdVar == null) {
                    zzhd zzhdVar2 = new zzhd(this, "Measurement Worker", this.zzd);
                    this.zzb = zzhdVar2;
                    zzhdVar2.setUncaughtExceptionHandler(this.zzf);
                    this.zzb.start();
                } else {
                    zzhdVar.zza();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void zza(Runnable runnable) throws IllegalStateException {
        zzab();
        Preconditions.checkNotNull(runnable);
        zzha<?> zzhaVar = new zzha<>(this, runnable, false, "Task exception on network thread");
        synchronized (this.zzh) {
            try {
                this.zze.add(zzhaVar);
                zzhd zzhdVar = this.zzc;
                if (zzhdVar == null) {
                    zzhd zzhdVar2 = new zzhd(this, "Measurement Network", this.zze);
                    this.zzc = zzhdVar2;
                    zzhdVar2.setUncaughtExceptionHandler(this.zzg);
                    this.zzc.start();
                } else {
                    zzhdVar.zza();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
