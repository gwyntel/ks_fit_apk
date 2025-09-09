package com.google.android.gms.internal.identity;

import androidx.annotation.GuardedBy;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.location.DeviceOrientation;
import com.google.android.gms.location.zzs;

/* loaded from: classes3.dex */
final class zzdq extends zzs {

    @GuardedBy("this")
    private ListenerHolder zza;

    zzdq(ListenerHolder listenerHolder) {
        this.zza = listenerHolder;
    }

    final synchronized void b(ListenerHolder listenerHolder) {
        ListenerHolder listenerHolder2 = this.zza;
        if (listenerHolder2 != listenerHolder) {
            listenerHolder2.clear();
            this.zza = listenerHolder;
        }
    }

    @Override // com.google.android.gms.location.zzt
    public final void zzd(DeviceOrientation deviceOrientation) {
        ListenerHolder listenerHolder;
        synchronized (this) {
            listenerHolder = this.zza;
        }
        listenerHolder.notifyListener(new zzdp(this, deviceOrientation));
    }

    final synchronized void zze() {
        this.zza.clear();
    }
}
