package com.google.android.gms.internal.auth;

/* loaded from: classes3.dex */
final class zzgd {
    private static final zzgc zza;
    private static final zzgc zzb;

    static {
        zzgc zzgcVar = null;
        try {
            zzgcVar = (zzgc) Class.forName("com.google.protobuf.NewInstanceSchemaFull").getDeclaredConstructor(null).newInstance(null);
        } catch (Exception unused) {
        }
        zza = zzgcVar;
        zzb = new zzgc();
    }

    static zzgc a() {
        return zza;
    }

    static zzgc b() {
        return zzb;
    }
}
