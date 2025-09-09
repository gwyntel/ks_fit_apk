package com.google.android.gms.internal.measurement;

/* loaded from: classes3.dex */
final class zznt {
    private static final zznr zza = zzc();
    private static final zznr zzb = new zznq();

    static zznr a() {
        return zza;
    }

    static zznr b() {
        return zzb;
    }

    private static zznr zzc() {
        try {
            return (zznr) Class.forName("com.google.protobuf.NewInstanceSchemaFull").getDeclaredConstructor(null).newInstance(null);
        } catch (Exception unused) {
            return null;
        }
    }
}
