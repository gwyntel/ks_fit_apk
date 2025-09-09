package com.google.android.gms.internal.auth;

import sun.misc.Unsafe;

/* loaded from: classes3.dex */
final class zzhg extends zzhi {
    zzhg(Unsafe unsafe) {
        super(unsafe);
    }

    @Override // com.google.android.gms.internal.auth.zzhi
    public final double zza(Object obj, long j2) {
        return Double.longBitsToDouble(this.f13027a.getLong(obj, j2));
    }

    @Override // com.google.android.gms.internal.auth.zzhi
    public final float zzb(Object obj, long j2) {
        return Float.intBitsToFloat(this.f13027a.getInt(obj, j2));
    }

    @Override // com.google.android.gms.internal.auth.zzhi
    public final void zzc(Object obj, long j2, boolean z2) {
        if (zzhj.f13028a) {
            zzhj.i(obj, j2, z2);
        } else {
            zzhj.j(obj, j2, z2);
        }
    }

    @Override // com.google.android.gms.internal.auth.zzhi
    public final void zzd(Object obj, long j2, double d2) {
        this.f13027a.putLong(obj, j2, Double.doubleToLongBits(d2));
    }

    @Override // com.google.android.gms.internal.auth.zzhi
    public final void zze(Object obj, long j2, float f2) {
        this.f13027a.putInt(obj, j2, Float.floatToIntBits(f2));
    }

    @Override // com.google.android.gms.internal.auth.zzhi
    public final boolean zzf(Object obj, long j2) {
        return zzhj.f13028a ? zzhj.q(obj, j2) : zzhj.r(obj, j2);
    }
}
