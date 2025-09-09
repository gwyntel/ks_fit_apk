package com.google.android.gms.internal.identity;

import android.location.Location;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.location.zzy;

/* loaded from: classes3.dex */
final class zzdy extends zzy {
    private final zzdr zza;

    zzdy(zzdr zzdrVar) {
        this.zza = zzdrVar;
    }

    final zzdy b(ListenerHolder listenerHolder) {
        this.zza.zzb(listenerHolder);
        return this;
    }

    final /* synthetic */ zzdr d() {
        return this.zza;
    }

    @Override // com.google.android.gms.location.zzz
    public final void zzd(Location location) {
        this.zza.zza().notifyListener(new zzdw(this, location));
    }

    @Override // com.google.android.gms.location.zzz
    public final void zze() {
        this.zza.zza().notifyListener(new zzdx(this));
    }

    final void zzf() {
        this.zza.zza().clear();
    }
}
