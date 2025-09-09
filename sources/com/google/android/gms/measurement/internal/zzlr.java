package com.google.android.gms.measurement.internal;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.MainThread;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.measurement.internal.zzlv;

/* loaded from: classes3.dex */
public final class zzlr<T extends Context & zzlv> {
    private final T zza;

    public zzlr(T t2) {
        Preconditions.checkNotNull(t2);
        this.zza = t2;
    }

    private final zzfs zzc() {
        return zzhc.zza(this.zza, null, null).zzj();
    }

    final /* synthetic */ void a(int i2, zzfs zzfsVar, Intent intent) {
        if (this.zza.zza(i2)) {
            zzfsVar.zzp().zza("Local AppMeasurementService processed last upload request. StartId", Integer.valueOf(i2));
            zzc().zzp().zza("Completed wakeful intent.");
            this.zza.zza(intent);
        }
    }

    final /* synthetic */ void b(zzfs zzfsVar, JobParameters jobParameters) {
        zzfsVar.zzp().zza("AppMeasurementJobService processed last upload request.");
        this.zza.zza(jobParameters, false);
    }

    @MainThread
    public final int zza(final Intent intent, int i2, final int i3) throws IllegalStateException {
        final zzfs zzfsVarZzj = zzhc.zza(this.zza, null, null).zzj();
        if (intent == null) {
            zzfsVarZzj.zzu().zza("AppMeasurementService started with null intent");
            return 2;
        }
        String action = intent.getAction();
        zzfsVarZzj.zzp().zza("Local AppMeasurementService called. startId, action", Integer.valueOf(i3), action);
        if ("com.google.android.gms.measurement.UPLOAD".equals(action)) {
            zza(new Runnable() { // from class: com.google.android.gms.measurement.internal.zzlt
                @Override // java.lang.Runnable
                public final void run() {
                    this.zza.a(i3, zzfsVarZzj, intent);
                }
            });
        }
        return 2;
    }

    @MainThread
    public final void zzb() {
        zzhc.zza(this.zza, null, null).zzj().zzp().zza("Local AppMeasurementService is shutting down");
    }

    @MainThread
    public final boolean zzc(Intent intent) {
        if (intent == null) {
            zzc().zzg().zza("onUnbind called with null intent");
            return true;
        }
        zzc().zzp().zza("onUnbind called for intent. action", intent.getAction());
        return true;
    }

    @MainThread
    public final void zzb(Intent intent) {
        if (intent == null) {
            zzc().zzg().zza("onRebind called with null intent");
        } else {
            zzc().zzp().zza("onRebind called. action", intent.getAction());
        }
    }

    @MainThread
    public final IBinder zza(Intent intent) {
        if (intent == null) {
            zzc().zzg().zza("onBind called with null intent");
            return null;
        }
        String action = intent.getAction();
        if ("com.google.android.gms.measurement.START".equals(action)) {
            return new zzhg(zzmq.zza(this.zza));
        }
        zzc().zzu().zza("onBind received unknown action", action);
        return null;
    }

    @MainThread
    public final void zza() {
        zzhc.zza(this.zza, null, null).zzj().zzp().zza("Local AppMeasurementService is starting up");
    }

    private final void zza(Runnable runnable) throws IllegalStateException {
        zzmq zzmqVarZza = zzmq.zza(this.zza);
        zzmqVarZza.zzl().zzb(new zzlw(this, zzmqVarZza, runnable));
    }

    @TargetApi(24)
    @MainThread
    public final boolean zza(final JobParameters jobParameters) throws IllegalStateException {
        final zzfs zzfsVarZzj = zzhc.zza(this.zza, null, null).zzj();
        String string = jobParameters.getExtras().getString("action");
        zzfsVarZzj.zzp().zza("Local AppMeasurementJobService called. action", string);
        if (!"com.google.android.gms.measurement.UPLOAD".equals(string)) {
            return true;
        }
        zza(new Runnable() { // from class: com.google.android.gms.measurement.internal.zzlu
            @Override // java.lang.Runnable
            public final void run() {
                this.zza.b(zzfsVarZzj, jobParameters);
            }
        });
        return true;
    }
}
