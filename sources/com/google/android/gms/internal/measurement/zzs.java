package com.google.android.gms.internal.measurement;

/* loaded from: classes3.dex */
public enum zzs {
    DEBUG(3),
    ERROR(6),
    INFO(4),
    VERBOSE(2),
    WARN(5);

    private final int zzg;

    zzs(int i2) {
        this.zzg = i2;
    }

    public static zzs zza(int i2) {
        return i2 != 2 ? i2 != 3 ? i2 != 5 ? i2 != 6 ? INFO : ERROR : WARN : DEBUG : VERBOSE;
    }
}
