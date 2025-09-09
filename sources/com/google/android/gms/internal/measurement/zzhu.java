package com.google.android.gms.internal.measurement;

import java.io.Serializable;
import javax.annotation.CheckForNull;

/* loaded from: classes3.dex */
final class zzhu<T> implements zzhs<T>, Serializable {
    private final zzhs<T> zza;
    private volatile transient boolean zzb;

    @CheckForNull
    private transient T zzc;

    zzhu(zzhs<T> zzhsVar) {
        this.zza = (zzhs) zzhn.zza(zzhsVar);
    }

    public final String toString() {
        Object obj;
        if (this.zzb) {
            obj = "<supplier that returned " + String.valueOf(this.zzc) + ">";
        } else {
            obj = this.zza;
        }
        return "Suppliers.memoize(" + String.valueOf(obj) + ")";
    }

    @Override // com.google.android.gms.internal.measurement.zzhs
    public final T zza() {
        if (!this.zzb) {
            synchronized (this) {
                try {
                    if (!this.zzb) {
                        T tZza = this.zza.zza();
                        this.zzc = tZza;
                        this.zzb = true;
                        return tZza;
                    }
                } finally {
                }
            }
        }
        return this.zzc;
    }
}
