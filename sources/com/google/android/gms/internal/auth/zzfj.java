package com.google.android.gms.internal.auth;

/* loaded from: classes3.dex */
final class zzfj extends zzfl {
    /* synthetic */ zzfj(zzfi zzfiVar) {
        super(null);
    }

    @Override // com.google.android.gms.internal.auth.zzfl
    final void a(Object obj, long j2) {
        ((zzez) zzhj.f(obj, j2)).zzb();
    }

    @Override // com.google.android.gms.internal.auth.zzfl
    final void b(Object obj, Object obj2, long j2) {
        zzez zzezVarZzd = (zzez) zzhj.f(obj, j2);
        zzez zzezVar = (zzez) zzhj.f(obj2, j2);
        int size = zzezVarZzd.size();
        int size2 = zzezVar.size();
        if (size > 0 && size2 > 0) {
            if (!zzezVarZzd.zzc()) {
                zzezVarZzd = zzezVarZzd.zzd(size2 + size);
            }
            zzezVarZzd.addAll(zzezVar);
        }
        if (size > 0) {
            zzezVar = zzezVarZzd;
        }
        zzhj.p(obj, j2, zzezVar);
    }

    private zzfj() {
        super(null);
    }
}
