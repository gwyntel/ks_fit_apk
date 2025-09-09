package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/* loaded from: classes3.dex */
final class zzr implements Handler.Callback {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ zzs f12862a;

    /* synthetic */ zzr(zzs zzsVar, zzq zzqVar) {
        this.f12862a = zzsVar;
    }

    @Override // android.os.Handler.Callback
    public final boolean handleMessage(Message message) {
        int i2 = message.what;
        if (i2 == 0) {
            synchronized (this.f12862a.zzb) {
                try {
                    zzo zzoVar = (zzo) message.obj;
                    zzp zzpVar = (zzp) this.f12862a.zzb.get(zzoVar);
                    if (zzpVar != null && zzpVar.zzi()) {
                        if (zzpVar.zzj()) {
                            zzpVar.zzg("GmsClientSupervisor");
                        }
                        this.f12862a.zzb.remove(zzoVar);
                    }
                } finally {
                }
            }
            return true;
        }
        if (i2 != 1) {
            return false;
        }
        synchronized (this.f12862a.zzb) {
            try {
                zzo zzoVar2 = (zzo) message.obj;
                zzp zzpVar2 = (zzp) this.f12862a.zzb.get(zzoVar2);
                if (zzpVar2 != null && zzpVar2.zza() == 3) {
                    Log.e("GmsClientSupervisor", "Timeout waiting for ServiceConnection callback " + String.valueOf(zzoVar2), new Exception());
                    ComponentName componentNameZzb = zzpVar2.zzb();
                    if (componentNameZzb == null) {
                        componentNameZzb = zzoVar2.zza();
                    }
                    if (componentNameZzb == null) {
                        String strZzc = zzoVar2.zzc();
                        Preconditions.checkNotNull(strZzc);
                        componentNameZzb = new ComponentName(strZzc, "unknown");
                    }
                    zzpVar2.onServiceDisconnected(componentNameZzb);
                }
            } finally {
            }
        }
        return true;
    }
}
