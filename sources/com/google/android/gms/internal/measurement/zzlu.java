package com.google.android.gms.internal.measurement;

import com.google.android.gms.internal.measurement.zzlw;

/* loaded from: classes3.dex */
final class zzlu implements zzng {
    private static final zzlu zza = new zzlu();

    private zzlu() {
    }

    public static zzlu zza() {
        return zza;
    }

    @Override // com.google.android.gms.internal.measurement.zzng
    public final boolean zzb(Class<?> cls) {
        return zzlw.class.isAssignableFrom(cls);
    }

    @Override // com.google.android.gms.internal.measurement.zzng
    public final zznh zza(Class<?> cls) {
        if (!zzlw.class.isAssignableFrom(cls)) {
            throw new IllegalArgumentException("Unsupported message type: " + cls.getName());
        }
        try {
            return (zznh) zzlw.e(cls.asSubclass(zzlw.class)).h(zzlw.zzf.zzc, null, null);
        } catch (Exception e2) {
            throw new RuntimeException("Unable to get message info for " + cls.getName(), e2);
        }
    }
}
