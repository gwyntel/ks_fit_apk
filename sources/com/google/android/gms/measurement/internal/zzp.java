package com.google.android.gms.measurement.internal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.MainThread;
import com.google.android.gms.internal.measurement.zzsg;

/* loaded from: classes3.dex */
public final class zzp extends BroadcastReceiver {
    private final zzhc zza;

    public zzp(zzhc zzhcVar) {
        this.zza = zzhcVar;
    }

    @Override // android.content.BroadcastReceiver
    @MainThread
    public final void onReceive(Context context, Intent intent) throws IllegalStateException {
        if (intent == null) {
            this.zza.zzj().zzu().zza("App receiver called with null intent");
            return;
        }
        String action = intent.getAction();
        if (action == null) {
            this.zza.zzj().zzu().zza("App receiver called with null action");
            return;
        }
        if (!action.equals("com.google.android.gms.measurement.TRIGGERS_AVAILABLE")) {
            this.zza.zzj().zzu().zza("App receiver called with unknown action");
            return;
        }
        final zzhc zzhcVar = this.zza;
        if (zzsg.zzb() && zzhcVar.zzf().zzf(null, zzbi.zzcg)) {
            zzhcVar.zzj().zzp().zza("App receiver notified triggers are available");
            zzhcVar.zzl().zzb(new Runnable() { // from class: com.google.android.gms.measurement.internal.zzr
                @Override // java.lang.Runnable
                public final void run() {
                    zzhc zzhcVar2 = zzhcVar;
                    if (!zzhcVar2.zzt().T()) {
                        zzhcVar2.zzj().zzu().zza("registerTrigger called but app not eligible");
                        return;
                    }
                    final zzin zzinVarZzp = zzhcVar2.zzp();
                    zzinVarZzp.getClass();
                    new Thread(new Runnable() { // from class: com.google.android.gms.measurement.internal.zzs
                        @Override // java.lang.Runnable
                        public final void run() throws IllegalStateException {
                            zzinVarZzp.zzal();
                        }
                    }).start();
                }
            });
        }
    }
}
