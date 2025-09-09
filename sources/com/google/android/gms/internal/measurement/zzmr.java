package com.google.android.gms.internal.measurement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes3.dex */
final class zzmr extends zzmo {
    private static final Class<?> zza = Collections.unmodifiableList(Collections.emptyList()).getClass();

    /* JADX WARN: Multi-variable type inference failed */
    private static <L> List<L> zza(Object obj, long j2, int i2) {
        zzmm zzmmVar;
        List<L> listZzc = zzc(obj, j2);
        if (listZzc.isEmpty()) {
            List<L> zzmmVar2 = listZzc instanceof zzmp ? new zzmm(i2) : ((listZzc instanceof zznv) && (listZzc instanceof zzmf)) ? ((zzmf) listZzc).zza(i2) : new ArrayList<>(i2);
            zzpc.i(obj, j2, zzmmVar2);
            return zzmmVar2;
        }
        if (zza.isAssignableFrom(listZzc.getClass())) {
            ArrayList arrayList = new ArrayList(listZzc.size() + i2);
            arrayList.addAll(listZzc);
            zzpc.i(obj, j2, arrayList);
            zzmmVar = arrayList;
        } else {
            if (!(listZzc instanceof zzpb)) {
                if (!(listZzc instanceof zznv) || !(listZzc instanceof zzmf)) {
                    return listZzc;
                }
                zzmf zzmfVar = (zzmf) listZzc;
                if (zzmfVar.zzc()) {
                    return listZzc;
                }
                zzmf zzmfVarZza = zzmfVar.zza(listZzc.size() + i2);
                zzpc.i(obj, j2, zzmfVarZza);
                return zzmfVarZza;
            }
            zzmm zzmmVar3 = new zzmm(listZzc.size() + i2);
            zzmmVar3.addAll((zzpb) listZzc);
            zzpc.i(obj, j2, zzmmVar3);
            zzmmVar = zzmmVar3;
        }
        return zzmmVar;
    }

    private static <E> List<E> zzc(Object obj, long j2) {
        return (List) zzpc.v(obj, j2);
    }

    @Override // com.google.android.gms.internal.measurement.zzmo
    final List b(Object obj, long j2) {
        return zza(obj, j2, 10);
    }

    @Override // com.google.android.gms.internal.measurement.zzmo
    final void c(Object obj, Object obj2, long j2) {
        List listZzc = zzc(obj2, j2);
        List listZza = zza(obj, j2, listZzc.size());
        int size = listZza.size();
        int size2 = listZzc.size();
        if (size > 0 && size2 > 0) {
            listZza.addAll(listZzc);
        }
        if (size > 0) {
            listZzc = listZza;
        }
        zzpc.i(obj, j2, listZzc);
    }

    @Override // com.google.android.gms.internal.measurement.zzmo
    final void e(Object obj, long j2) {
        Object objUnmodifiableList;
        List list = (List) zzpc.v(obj, j2);
        if (list instanceof zzmp) {
            objUnmodifiableList = ((zzmp) list).zzd();
        } else {
            if (zza.isAssignableFrom(list.getClass())) {
                return;
            }
            if ((list instanceof zznv) && (list instanceof zzmf)) {
                zzmf zzmfVar = (zzmf) list;
                if (zzmfVar.zzc()) {
                    zzmfVar.zzb();
                    return;
                }
                return;
            }
            objUnmodifiableList = Collections.unmodifiableList(list);
        }
        zzpc.i(obj, j2, objUnmodifiableList);
    }

    private zzmr() {
        super();
    }
}
