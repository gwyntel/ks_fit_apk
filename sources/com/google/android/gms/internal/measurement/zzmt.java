package com.google.android.gms.internal.measurement;

import java.util.List;

/* loaded from: classes3.dex */
final class zzmt extends zzmo {
    private static <E> zzmf<E> zzc(Object obj, long j2) {
        return (zzmf) zzpc.v(obj, j2);
    }

    @Override // com.google.android.gms.internal.measurement.zzmo
    final List b(Object obj, long j2) {
        zzmf zzmfVarZzc = zzc(obj, j2);
        if (zzmfVarZzc.zzc()) {
            return zzmfVarZzc;
        }
        int size = zzmfVarZzc.size();
        zzmf zzmfVarZza = zzmfVarZzc.zza(size == 0 ? 10 : size << 1);
        zzpc.i(obj, j2, zzmfVarZza);
        return zzmfVarZza;
    }

    @Override // com.google.android.gms.internal.measurement.zzmo
    final void c(Object obj, Object obj2, long j2) {
        zzmf zzmfVarZzc = zzc(obj, j2);
        zzmf zzmfVarZzc2 = zzc(obj2, j2);
        int size = zzmfVarZzc.size();
        int size2 = zzmfVarZzc2.size();
        if (size > 0 && size2 > 0) {
            if (!zzmfVarZzc.zzc()) {
                zzmfVarZzc = zzmfVarZzc.zza(size2 + size);
            }
            zzmfVarZzc.addAll(zzmfVarZzc2);
        }
        if (size > 0) {
            zzmfVarZzc2 = zzmfVarZzc;
        }
        zzpc.i(obj, j2, zzmfVarZzc2);
    }

    @Override // com.google.android.gms.internal.measurement.zzmo
    final void e(Object obj, long j2) {
        zzc(obj, j2).zzb();
    }

    private zzmt() {
        super();
    }
}
