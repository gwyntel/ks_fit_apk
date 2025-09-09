package com.google.android.gms.internal.measurement;

import javax.annotation.CheckForNull;

/* loaded from: classes3.dex */
final class zzhq<T> extends zzho<T> {
    private final T zza;

    zzhq(T t2) {
        this.zza = t2;
    }

    public final boolean equals(@CheckForNull Object obj) {
        if (obj instanceof zzhq) {
            return this.zza.equals(((zzhq) obj).zza);
        }
        return false;
    }

    public final int hashCode() {
        return this.zza.hashCode() + 1502476572;
    }

    public final String toString() {
        return "Optional.of(" + String.valueOf(this.zza) + ")";
    }

    @Override // com.google.android.gms.internal.measurement.zzho
    public final T zza() {
        return this.zza;
    }

    @Override // com.google.android.gms.internal.measurement.zzho
    public final boolean zzb() {
        return true;
    }
}
