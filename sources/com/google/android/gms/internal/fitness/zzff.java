package com.google.android.gms.internal.fitness;

import com.google.firebase.analytics.FirebaseAnalytics;

/* loaded from: classes3.dex */
public final class zzff {
    public static int zza(int i2, int i3, String str) {
        String strZza;
        if (i2 >= 0 && i2 < i3) {
            return i2;
        }
        if (i2 < 0) {
            strZza = zzfg.zza("%s (%s) must not be negative", FirebaseAnalytics.Param.INDEX, Integer.valueOf(i2));
        } else {
            if (i3 < 0) {
                throw new IllegalArgumentException("negative size: " + i3);
            }
            strZza = zzfg.zza("%s (%s) must be less than size (%s)", FirebaseAnalytics.Param.INDEX, Integer.valueOf(i2), Integer.valueOf(i3));
        }
        throw new IndexOutOfBoundsException(strZza);
    }

    public static int zzb(int i2, int i3, String str) {
        if (i2 < 0 || i2 > i3) {
            throw new IndexOutOfBoundsException(zzd(i2, i3, FirebaseAnalytics.Param.INDEX));
        }
        return i2;
    }

    public static void zzc(int i2, int i3, int i4) {
        if (i2 < 0 || i3 < i2 || i3 > i4) {
            throw new IndexOutOfBoundsException((i2 < 0 || i2 > i4) ? zzd(i2, i4, "start index") : (i3 < 0 || i3 > i4) ? zzd(i3, i4, "end index") : zzfg.zza("end index (%s) must not be less than start index (%s)", Integer.valueOf(i3), Integer.valueOf(i2)));
        }
    }

    private static String zzd(int i2, int i3, String str) {
        if (i2 < 0) {
            return zzfg.zza("%s (%s) must not be negative", str, Integer.valueOf(i2));
        }
        if (i3 >= 0) {
            return zzfg.zza("%s (%s) must not be greater than size (%s)", str, Integer.valueOf(i2), Integer.valueOf(i3));
        }
        throw new IllegalArgumentException("negative size: " + i3);
    }
}
