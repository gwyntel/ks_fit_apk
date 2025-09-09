package com.google.android.gms.measurement.internal;

/* loaded from: classes3.dex */
abstract class zzml extends zzmm {
    private boolean zza;

    zzml(zzmq zzmqVar) {
        super(zzmqVar);
        this.f13314b.C();
    }

    protected final void zzak() {
        if (!zzam()) {
            throw new IllegalStateException("Not initialized");
        }
    }

    public final void zzal() {
        if (this.zza) {
            throw new IllegalStateException("Can't initialize twice");
        }
        zzc();
        this.f13314b.B();
        this.zza = true;
    }

    final boolean zzam() {
        return this.zza;
    }

    protected abstract boolean zzc();
}
