package com.google.android.gms.measurement.internal;

import android.content.Context;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import org.checkerframework.dataflow.qual.Pure;

/* loaded from: classes3.dex */
class zzia implements zzic {

    /* renamed from: a, reason: collision with root package name */
    protected final zzhc f13286a;

    zzia(zzhc zzhcVar) {
        Preconditions.checkNotNull(zzhcVar);
        this.f13286a = zzhcVar;
    }

    @Override // com.google.android.gms.measurement.internal.zzic
    @Pure
    public Context zza() {
        return this.f13286a.zza();
    }

    @Override // com.google.android.gms.measurement.internal.zzic
    @Pure
    public Clock zzb() {
        return this.f13286a.zzb();
    }

    @Override // com.google.android.gms.measurement.internal.zzic
    @Pure
    public zzae zzd() {
        return this.f13286a.zzd();
    }

    @Pure
    public zzaf zze() {
        return this.f13286a.zzf();
    }

    @Pure
    public zzba zzf() {
        return this.f13286a.zzg();
    }

    @Pure
    public zzfn zzi() {
        return this.f13286a.zzk();
    }

    @Override // com.google.android.gms.measurement.internal.zzic
    @Pure
    public zzfs zzj() {
        return this.f13286a.zzj();
    }

    @Pure
    public zzge zzk() {
        return this.f13286a.zzn();
    }

    @Override // com.google.android.gms.measurement.internal.zzic
    @Pure
    public zzgz zzl() {
        return this.f13286a.zzl();
    }

    @Pure
    public zzne zzq() {
        return this.f13286a.zzt();
    }

    public void zzr() {
        this.f13286a.zzl().zzr();
    }

    public void zzs() {
        this.f13286a.h();
    }

    public void zzt() {
        this.f13286a.zzl().zzt();
    }
}
