package com.google.android.gms.measurement.internal;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import androidx.annotation.MainThread;

/* loaded from: classes3.dex */
public final class zzgm implements ServiceConnection {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ zzgj f13279a;
    private final String zzb;

    zzgm(zzgj zzgjVar, String str) {
        this.f13279a = zzgjVar;
        this.zzb = str;
    }

    @Override // android.content.ServiceConnection
    @MainThread
    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        if (iBinder == null) {
            this.f13279a.f13278a.zzj().zzu().zza("Install Referrer connection returned with null binder");
            return;
        }
        try {
            com.google.android.gms.internal.measurement.zzby zzbyVarZza = com.google.android.gms.internal.measurement.zzcb.zza(iBinder);
            if (zzbyVarZza == null) {
                this.f13279a.f13278a.zzj().zzu().zza("Install Referrer Service implementation was not found");
            } else {
                this.f13279a.f13278a.zzj().zzp().zza("Install Referrer Service connected");
                this.f13279a.f13278a.zzl().zzb(new zzgl(this, zzbyVarZza, this));
            }
        } catch (RuntimeException e2) {
            this.f13279a.f13278a.zzj().zzu().zza("Exception occurred while calling Install Referrer API", e2);
        }
    }

    @Override // android.content.ServiceConnection
    @MainThread
    public final void onServiceDisconnected(ComponentName componentName) {
        this.f13279a.f13278a.zzj().zzp().zza("Install Referrer Service disconnected");
    }
}
