package com.google.android.gms.internal.measurement;

import java.util.List;

/* loaded from: classes3.dex */
abstract class zzmo {
    private static final zzmo zza = new zzmr();
    private static final zzmo zzb = new zzmt();

    private zzmo() {
    }

    static zzmo a() {
        return zza;
    }

    static zzmo d() {
        return zzb;
    }

    abstract List b(Object obj, long j2);

    abstract void c(Object obj, Object obj2, long j2);

    abstract void e(Object obj, long j2);
}
