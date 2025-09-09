package com.google.android.gms.measurement.internal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import androidx.annotation.MainThread;
import androidx.annotation.VisibleForTesting;
import androidx.annotation.WorkerThread;
import com.google.android.gms.common.internal.Preconditions;
import dev.fluttercommunity.plus.connectivity.ConnectivityBroadcastReceiver;

/* loaded from: classes3.dex */
class zzgc extends BroadcastReceiver {

    @VisibleForTesting
    private static final String zza = "com.google.android.gms.measurement.internal.zzgc";
    private final zzmq zzb;
    private boolean zzc;
    private boolean zzd;

    zzgc(zzmq zzmqVar) {
        Preconditions.checkNotNull(zzmqVar);
        this.zzb = zzmqVar;
    }

    @Override // android.content.BroadcastReceiver
    @MainThread
    public void onReceive(Context context, Intent intent) throws IllegalStateException {
        this.zzb.A();
        String action = intent.getAction();
        this.zzb.zzj().zzp().zza("NetworkBroadcastReceiver received action", action);
        if (!ConnectivityBroadcastReceiver.CONNECTIVITY_ACTION.equals(action)) {
            this.zzb.zzj().zzu().zza("NetworkBroadcastReceiver received unknown action", action);
            return;
        }
        boolean zZzu = this.zzb.zzh().zzu();
        if (this.zzd != zZzu) {
            this.zzd = zZzu;
            this.zzb.zzl().zzb(new zzgb(this, zZzu));
        }
    }

    @WorkerThread
    public final void zza() {
        this.zzb.A();
        this.zzb.zzl().zzt();
        if (this.zzc) {
            return;
        }
        this.zzb.zza().registerReceiver(this, new IntentFilter(ConnectivityBroadcastReceiver.CONNECTIVITY_ACTION));
        this.zzd = this.zzb.zzh().zzu();
        this.zzb.zzj().zzp().zza("Registering connectivity change receiver. Network connected", Boolean.valueOf(this.zzd));
        this.zzc = true;
    }

    @WorkerThread
    public final void zzb() {
        this.zzb.A();
        this.zzb.zzl().zzt();
        this.zzb.zzl().zzt();
        if (this.zzc) {
            this.zzb.zzj().zzp().zza("Unregistering connectivity change receiver");
            this.zzc = false;
            this.zzd = false;
            try {
                this.zzb.zza().unregisterReceiver(this);
            } catch (IllegalArgumentException e2) {
                this.zzb.zzj().zzg().zza("Failed to unregister the network broadcast receiver", e2);
            }
        }
    }
}
