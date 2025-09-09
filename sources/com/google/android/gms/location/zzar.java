package com.google.android.gms.location;

import com.google.android.gms.common.internal.Preconditions;

/* loaded from: classes3.dex */
public final class zzar {
    public static int zza(int i2) {
        boolean z2;
        if (i2 == 0 || i2 == 1) {
            z2 = true;
        } else if (i2 == 2) {
            i2 = 2;
            z2 = true;
        } else {
            z2 = false;
        }
        Preconditions.checkArgument(z2, "throttle behavior %d must be a ThrottleBehavior.THROTTLE_* constant", Integer.valueOf(i2));
        return i2;
    }

    public static String zzb(int i2) {
        if (i2 == 0) {
            return "THROTTLE_BACKGROUND";
        }
        if (i2 == 1) {
            return "THROTTLE_ALWAYS";
        }
        if (i2 == 2) {
            return "THROTTLE_NEVER";
        }
        throw new IllegalArgumentException();
    }
}
