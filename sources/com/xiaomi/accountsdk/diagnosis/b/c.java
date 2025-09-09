package com.xiaomi.accountsdk.diagnosis.b;

import androidx.exifinterface.media.ExifInterface;

/* loaded from: classes4.dex */
public enum c {
    VERBOSE(ExifInterface.GPS_MEASUREMENT_INTERRUPTED),
    DEBUG("D"),
    INFO("I"),
    WARN(ExifInterface.LONGITUDE_WEST),
    ERROR(ExifInterface.LONGITUDE_EAST);


    /* renamed from: f, reason: collision with root package name */
    private final String f23307f;

    c(String str) {
        this.f23307f = str;
    }

    public static c a(int i2) {
        return i2 != 2 ? i2 != 3 ? i2 != 4 ? i2 != 5 ? i2 != 6 ? VERBOSE : ERROR : WARN : INFO : DEBUG : VERBOSE;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.f23307f;
    }
}
