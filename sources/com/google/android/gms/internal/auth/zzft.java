package com.google.android.gms.internal.auth;

/* loaded from: classes3.dex */
final class zzft {
    private static final zzfs zza;
    private static final zzfs zzb;

    static {
        zzfs zzfsVar = null;
        try {
            zzfsVar = (zzfs) Class.forName("com.google.protobuf.MapFieldSchemaFull").getDeclaredConstructor(null).newInstance(null);
        } catch (Exception unused) {
        }
        zza = zzfsVar;
        zzb = new zzfs();
    }

    static zzfs a() {
        return zza;
    }

    static zzfs b() {
        return zzb;
    }
}
