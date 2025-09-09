package com.google.android.gms.internal.measurement;

import java.io.Serializable;
import java.util.Arrays;
import javax.annotation.CheckForNull;

/* loaded from: classes3.dex */
final class zzhv<T> implements zzhs<T>, Serializable {
    private final T zza;

    zzhv(T t2) {
        this.zza = t2;
    }

    public final boolean equals(@CheckForNull Object obj) {
        if (obj instanceof zzhv) {
            return zzhl.zza(this.zza, ((zzhv) obj).zza);
        }
        return false;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zza});
    }

    public final String toString() {
        return "Suppliers.ofInstance(" + String.valueOf(this.zza) + ")";
    }

    @Override // com.google.android.gms.internal.measurement.zzhs
    public final T zza() {
        return this.zza;
    }
}
