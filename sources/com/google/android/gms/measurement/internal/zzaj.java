package com.google.android.gms.measurement.internal;

/* loaded from: classes3.dex */
enum zzaj {
    UNSET('0'),
    REMOTE_DEFAULT('1'),
    REMOTE_DELEGATION('2'),
    MANIFEST('3'),
    INITIALIZATION('4'),
    API('5'),
    CHILD_ACCOUNT('6'),
    FAILSAFE('9');

    private final char zzj;

    zzaj(char c2) {
        this.zzj = c2;
    }

    public static zzaj zza(char c2) {
        for (zzaj zzajVar : values()) {
            if (zzajVar.zzj == c2) {
                return zzajVar;
            }
        }
        return UNSET;
    }
}
