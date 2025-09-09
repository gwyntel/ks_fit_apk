package com.google.android.gms.cloudmessaging;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.Nullable;
import com.google.android.gms.common.util.concurrent.NamedThreadFactory;
import com.google.android.gms.tasks.Task;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import javax.annotation.concurrent.GuardedBy;

/* loaded from: classes3.dex */
public final class zzs {

    @Nullable
    @GuardedBy("MessengerIpcClient.class")
    private static zzs zza;
    private final Context zzb;
    private final ScheduledExecutorService zzc;

    @GuardedBy("this")
    private zzm zzd = new zzm(this, null);

    @GuardedBy("this")
    private int zze = 1;

    zzs(Context context, ScheduledExecutorService scheduledExecutorService) {
        this.zzc = scheduledExecutorService;
        this.zzb = context.getApplicationContext();
    }

    public static synchronized zzs zzb(Context context) {
        try {
            if (zza == null) {
                com.google.android.gms.internal.cloudmessaging.zze.zza();
                zza = new zzs(context, Executors.unconfigurableScheduledExecutorService(Executors.newScheduledThreadPool(1, new NamedThreadFactory("MessengerIpcClient"))));
            }
        } catch (Throwable th) {
            throw th;
        }
        return zza;
    }

    private final synchronized int zzf() {
        int i2;
        i2 = this.zze;
        this.zze = i2 + 1;
        return i2;
    }

    private final synchronized <T> Task<T> zzg(zzp<T> zzpVar) {
        try {
            if (Log.isLoggable("MessengerIpcClient", 3)) {
                String strValueOf = String.valueOf(zzpVar);
                StringBuilder sb = new StringBuilder(strValueOf.length() + 9);
                sb.append("Queueing ");
                sb.append(strValueOf);
                Log.d("MessengerIpcClient", sb.toString());
            }
            if (!this.zzd.g(zzpVar)) {
                zzm zzmVar = new zzm(this, null);
                this.zzd = zzmVar;
                zzmVar.g(zzpVar);
            }
        } catch (Throwable th) {
            throw th;
        }
        return zzpVar.f12678b.getTask();
    }

    public final Task<Void> zzc(int i2, Bundle bundle) {
        return zzg(new zzo(zzf(), 2, bundle));
    }

    public final Task<Bundle> zzd(int i2, Bundle bundle) {
        return zzg(new zzr(zzf(), 1, bundle));
    }
}
