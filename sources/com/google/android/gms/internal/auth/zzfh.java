package com.google.android.gms.internal.auth;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes3.dex */
final class zzfh extends zzfl {
    private static final Class zza = Collections.unmodifiableList(Collections.emptyList()).getClass();

    /* synthetic */ zzfh(zzfg zzfgVar) {
        super(null);
    }

    @Override // com.google.android.gms.internal.auth.zzfl
    final void a(Object obj, long j2) {
        Object objUnmodifiableList;
        List list = (List) zzhj.f(obj, j2);
        if (list instanceof zzff) {
            objUnmodifiableList = ((zzff) list).zze();
        } else {
            if (zza.isAssignableFrom(list.getClass())) {
                return;
            }
            if ((list instanceof zzge) && (list instanceof zzez)) {
                zzez zzezVar = (zzez) list;
                if (zzezVar.zzc()) {
                    zzezVar.zzb();
                    return;
                }
                return;
            }
            objUnmodifiableList = Collections.unmodifiableList(list);
        }
        zzhj.p(obj, j2, objUnmodifiableList);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.internal.auth.zzfl
    final void b(Object obj, Object obj2, long j2) {
        zzfe zzfeVar;
        List list = (List) zzhj.f(obj2, j2);
        int size = list.size();
        List listZzd = (List) zzhj.f(obj, j2);
        if (listZzd.isEmpty()) {
            listZzd = listZzd instanceof zzff ? new zzfe(size) : ((listZzd instanceof zzge) && (listZzd instanceof zzez)) ? ((zzez) listZzd).zzd(size) : new ArrayList(size);
            zzhj.p(obj, j2, listZzd);
        } else {
            if (zza.isAssignableFrom(listZzd.getClass())) {
                ArrayList arrayList = new ArrayList(listZzd.size() + size);
                arrayList.addAll(listZzd);
                zzhj.p(obj, j2, arrayList);
                zzfeVar = arrayList;
            } else if (listZzd instanceof zzhe) {
                zzfe zzfeVar2 = new zzfe(listZzd.size() + size);
                zzfeVar2.addAll(zzfeVar2.size(), (zzhe) listZzd);
                zzhj.p(obj, j2, zzfeVar2);
                zzfeVar = zzfeVar2;
            } else if ((listZzd instanceof zzge) && (listZzd instanceof zzez)) {
                zzez zzezVar = (zzez) listZzd;
                if (!zzezVar.zzc()) {
                    listZzd = zzezVar.zzd(listZzd.size() + size);
                    zzhj.p(obj, j2, listZzd);
                }
            }
            listZzd = zzfeVar;
        }
        int size2 = listZzd.size();
        int size3 = list.size();
        if (size2 > 0 && size3 > 0) {
            listZzd.addAll(list);
        }
        if (size2 > 0) {
            list = listZzd;
        }
        zzhj.p(obj, j2, list);
    }

    private zzfh() {
        super(null);
    }
}
