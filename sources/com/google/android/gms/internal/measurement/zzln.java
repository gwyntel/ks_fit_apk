package com.google.android.gms.internal.measurement;

/* loaded from: classes3.dex */
final class zzln {
    private static final zzll<?> zza = new zzlk();
    private static final zzll<?> zzb = zzc();

    static zzll a() {
        zzll<?> zzllVar = zzb;
        if (zzllVar != null) {
            return zzllVar;
        }
        throw new IllegalStateException("Protobuf runtime is not correctly loaded.");
    }

    static zzll b() {
        return zza;
    }

    private static zzll<?> zzc() {
        try {
            return (zzll) Class.forName("com.google.protobuf.ExtensionSchemaFull").getDeclaredConstructor(null).newInstance(null);
        } catch (Exception unused) {
            return null;
        }
    }
}
