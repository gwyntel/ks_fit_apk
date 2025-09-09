package com.google.android.gms.measurement.internal;

/* loaded from: classes3.dex */
abstract class zzid extends zzia {
    private boolean zza;

    zzid(zzhc zzhcVar) {
        super(zzhcVar);
        this.f13286a.e();
    }

    protected abstract boolean a();

    protected void b() {
    }

    protected final void zzab() {
        if (!zzae()) {
            throw new IllegalStateException("Not initialized");
        }
    }

    public final void zzac() {
        if (this.zza) {
            throw new IllegalStateException("Can't initialize twice");
        }
        if (a()) {
            return;
        }
        this.f13286a.i();
        this.zza = true;
    }

    public final void zzad() {
        if (this.zza) {
            throw new IllegalStateException("Can't initialize twice");
        }
        b();
        this.f13286a.i();
        this.zza = true;
    }

    final boolean zzae() {
        return this.zza;
    }
}
