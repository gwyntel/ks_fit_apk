package com.google.android.gms.internal.measurement;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* loaded from: classes3.dex */
final class zznx {
    private static final zznx zza = new zznx();
    private final ConcurrentMap<Class<?>, zzob<?>> zzc = new ConcurrentHashMap();
    private final zzoa zzb = new zzmx();

    private zznx() {
    }

    public static zznx zza() {
        return zza;
    }

    public final <T> zzob<T> zza(Class<T> cls) {
        zzlz.c(cls, "messageType");
        zzob<T> zzobVar = (zzob) this.zzc.get(cls);
        if (zzobVar != null) {
            return zzobVar;
        }
        zzob<T> zzobVarZza = this.zzb.zza(cls);
        zzlz.c(cls, "messageType");
        zzlz.c(zzobVarZza, "schema");
        zzob<T> zzobVar2 = (zzob) this.zzc.putIfAbsent(cls, zzobVarZza);
        return zzobVar2 != null ? zzobVar2 : zzobVarZza;
    }

    public final <T> zzob<T> zza(T t2) {
        return zza((Class) t2.getClass());
    }
}
