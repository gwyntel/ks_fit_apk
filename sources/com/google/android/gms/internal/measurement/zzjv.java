package com.google.android.gms.internal.measurement;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/* loaded from: classes3.dex */
final class zzjv<V> implements Runnable {
    private final Future<V> zza;
    private final zzjt<? super V> zzb;

    zzjv(Future future, zzjt zzjtVar) {
        this.zza = future;
        this.zzb = zzjtVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        Throwable thZza;
        Future<V> future = this.zza;
        if ((future instanceof zzka) && (thZza = zzjz.zza((zzka) future)) != null) {
            this.zzb.zza(thZza);
            return;
        }
        try {
            Future<V> future2 = this.zza;
            if (!future2.isDone()) {
                throw new IllegalStateException(zzhp.zza("Future was expected to be done: %s", future2));
            }
            this.zzb.zza((zzjt<? super V>) zzjx.zza(future2));
        } catch (Error e2) {
            e = e2;
            this.zzb.zza(e);
        } catch (RuntimeException e3) {
            e = e3;
            this.zzb.zza(e);
        } catch (ExecutionException e4) {
            this.zzb.zza(e4.getCause());
        }
    }

    public final String toString() {
        return zzhf.zza(this).zza(this.zzb).toString();
    }
}
