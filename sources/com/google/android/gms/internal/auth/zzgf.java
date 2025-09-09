package com.google.android.gms.internal.auth;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* loaded from: classes3.dex */
final class zzgf {
    private static final zzgf zza = new zzgf();
    private final ConcurrentMap zzc = new ConcurrentHashMap();
    private final zzgj zzb = new zzfp();

    private zzgf() {
    }

    public static zzgf zza() {
        return zza;
    }

    public final zzgi zzb(Class cls) {
        zzfa.b(cls, "messageType");
        zzgi zzgiVarZza = (zzgi) this.zzc.get(cls);
        if (zzgiVarZza == null) {
            zzgiVarZza = this.zzb.zza(cls);
            zzfa.b(cls, "messageType");
            zzgi zzgiVar = (zzgi) this.zzc.putIfAbsent(cls, zzgiVarZza);
            if (zzgiVar != null) {
                return zzgiVar;
            }
        }
        return zzgiVarZza;
    }
}
