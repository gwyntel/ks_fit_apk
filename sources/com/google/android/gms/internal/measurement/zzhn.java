package com.google.android.gms.internal.measurement;

import com.google.firebase.analytics.FirebaseAnalytics;
import javax.annotation.CheckForNull;

/* loaded from: classes3.dex */
public final class zzhn {
    public static int zza(int i2, int i3) {
        String strZza;
        if (i2 >= 0 && i2 < i3) {
            return i2;
        }
        if (i2 < 0) {
            strZza = zzhp.zza("%s (%s) must not be negative", FirebaseAnalytics.Param.INDEX, Integer.valueOf(i2));
        } else {
            if (i3 < 0) {
                throw new IllegalArgumentException("negative size: " + i3);
            }
            strZza = zzhp.zza("%s (%s) must be less than size (%s)", FirebaseAnalytics.Param.INDEX, Integer.valueOf(i2), Integer.valueOf(i3));
        }
        throw new IndexOutOfBoundsException(strZza);
    }

    public static int zzb(int i2, int i3) {
        if (i2 < 0 || i2 > i3) {
            throw new IndexOutOfBoundsException(zza(i2, i3, FirebaseAnalytics.Param.INDEX));
        }
        return i2;
    }

    public static void zzb(boolean z2, @CheckForNull Object obj) {
        if (!z2) {
            throw new IllegalStateException(String.valueOf(obj));
        }
    }

    public static <T> T zza(@CheckForNull T t2) {
        t2.getClass();
        return t2;
    }

    public static <T> T zza(@CheckForNull T t2, @CheckForNull Object obj) {
        if (t2 != null) {
            return t2;
        }
        throw new NullPointerException(String.valueOf(obj));
    }

    private static String zza(int i2, int i3, String str) {
        if (i2 < 0) {
            return zzhp.zza("%s (%s) must not be negative", str, Integer.valueOf(i2));
        }
        if (i3 >= 0) {
            return zzhp.zza("%s (%s) must not be greater than size (%s)", str, Integer.valueOf(i2), Integer.valueOf(i3));
        }
        throw new IllegalArgumentException("negative size: " + i3);
    }

    public static void zza(boolean z2, @CheckForNull Object obj) {
        if (!z2) {
            throw new IllegalArgumentException(String.valueOf(obj));
        }
    }

    public static void zza(int i2, int i3, int i4) {
        String strZza;
        if (i2 < 0 || i3 < i2 || i3 > i4) {
            if (i2 < 0 || i2 > i4) {
                strZza = zza(i2, i4, "start index");
            } else if (i3 >= 0 && i3 <= i4) {
                strZza = zzhp.zza("end index (%s) must not be less than start index (%s)", Integer.valueOf(i3), Integer.valueOf(i2));
            } else {
                strZza = zza(i3, i4, "end index");
            }
            throw new IndexOutOfBoundsException(strZza);
        }
    }
}
