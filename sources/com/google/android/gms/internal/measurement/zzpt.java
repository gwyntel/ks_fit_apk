package com.google.android.gms.internal.measurement;

/* loaded from: classes3.dex */
public enum zzpt {
    INT(0),
    LONG(0L),
    FLOAT(Float.valueOf(0.0f)),
    DOUBLE(Double.valueOf(0.0d)),
    BOOLEAN(Boolean.FALSE),
    STRING(""),
    BYTE_STRING(zzkm.zza),
    ENUM(null),
    MESSAGE(null);

    private final Object zzk;

    zzpt(Object obj) {
        this.zzk = obj;
    }
}
