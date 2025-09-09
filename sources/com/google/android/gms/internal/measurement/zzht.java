package com.google.android.gms.internal.measurement;

import javax.annotation.CheckForNull;

/* loaded from: classes3.dex */
final class zzht<T> implements zzhs<T> {
    private static final zzhs<Void> zza = new zzhs() { // from class: com.google.android.gms.internal.measurement.zzhw
        @Override // com.google.android.gms.internal.measurement.zzhs
        public final Object zza() {
            return zzht.a();
        }
    };
    private volatile zzhs<T> zzb;

    @CheckForNull
    private T zzc;

    zzht(zzhs zzhsVar) {
        this.zzb = (zzhs) zzhn.zza(zzhsVar);
    }

    static /* synthetic */ Void a() {
        throw new IllegalStateException();
    }

    public final String toString() {
        Object obj = this.zzb;
        if (obj == zza) {
            obj = "<supplier that returned " + String.valueOf(this.zzc) + ">";
        }
        return "Suppliers.memoize(" + String.valueOf(obj) + ")";
    }

    @Override // com.google.android.gms.internal.measurement.zzhs
    public final T zza() {
        zzhs<T> zzhsVar = this.zzb;
        zzhs<T> zzhsVar2 = (zzhs<T>) zza;
        if (zzhsVar != zzhsVar2) {
            synchronized (this) {
                try {
                    if (this.zzb != zzhsVar2) {
                        T tZza = this.zzb.zza();
                        this.zzc = tZza;
                        this.zzb = zzhsVar2;
                        return tZza;
                    }
                } finally {
                }
            }
        }
        return this.zzc;
    }
}
