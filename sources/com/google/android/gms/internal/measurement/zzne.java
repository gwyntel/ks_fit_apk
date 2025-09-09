package com.google.android.gms.internal.measurement;

/* loaded from: classes3.dex */
final class zzne {
    private static final zznc zza = zzc();
    private static final zznc zzb = new zznf();

    static zznc a() {
        return zza;
    }

    static zznc b() {
        return zzb;
    }

    private static zznc zzc() {
        try {
            return (zznc) Class.forName("com.google.protobuf.MapFieldSchemaFull").getDeclaredConstructor(null).newInstance(null);
        } catch (Exception unused) {
            return null;
        }
    }
}
