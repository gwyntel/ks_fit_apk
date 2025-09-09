package com.google.android.gms.internal.identity;

import android.os.RemoteException;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.zzv;

/* loaded from: classes3.dex */
final class zzdv extends zzv {
    private final zzdr zza;

    zzdv(zzdr zzdrVar) {
        this.zza = zzdrVar;
    }

    final zzdv b(ListenerHolder listenerHolder) {
        this.zza.zzb(listenerHolder);
        return this;
    }

    final void d() {
        this.zza.zza().clear();
    }

    final /* synthetic */ zzdr e() {
        return this.zza;
    }

    @Override // com.google.android.gms.location.zzw
    public final void zzd(LocationResult locationResult) throws RemoteException {
        this.zza.zza().notifyListener(new zzds(this, locationResult));
    }

    @Override // com.google.android.gms.location.zzw
    public final void zze(LocationAvailability locationAvailability) throws RemoteException {
        this.zza.zza().notifyListener(new zzdt(this, locationAvailability));
    }

    @Override // com.google.android.gms.location.zzw
    public final void zzf() {
        this.zza.zza().notifyListener(new zzdu(this));
    }
}
