package com.google.android.gms.measurement.internal;

/* loaded from: classes3.dex */
abstract class zze extends zzf {
    private boolean zza;

    zze(zzhc zzhcVar) {
        super(zzhcVar);
        this.f13286a.e();
    }

    protected void a() {
    }

    protected final void zzu() {
        if (!zzy()) {
            throw new IllegalStateException("Not initialized");
        }
    }

    public final void zzv() {
        if (this.zza) {
            throw new IllegalStateException("Can't initialize twice");
        }
        if (zzz()) {
            return;
        }
        this.f13286a.i();
        this.zza = true;
    }

    public final void zzw() {
        if (this.zza) {
            throw new IllegalStateException("Can't initialize twice");
        }
        a();
        this.f13286a.i();
        this.zza = true;
    }

    final boolean zzy() {
        return this.zza;
    }

    protected abstract boolean zzz();
}
