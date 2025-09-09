package com.google.android.gms.internal.measurement;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Process;
import android.os.UserManager;
import android.util.Log;
import androidx.annotation.ChecksSdkIntAtLeast;
import androidx.annotation.GuardedBy;
import androidx.annotation.RequiresApi;

/* loaded from: classes3.dex */
public class zzfv {

    @GuardedBy("DirectBootUtils.class")
    private static UserManager zza;
    private static volatile boolean zzb = !zza();

    @GuardedBy("DirectBootUtils.class")
    private static boolean zzc = false;

    private zzfv() {
    }

    public static boolean zza(Context context) {
        return zza() && !zzc(context);
    }

    public static boolean zzb(Context context) {
        return !zza() || zzc(context);
    }

    @RequiresApi(24)
    @TargetApi(24)
    private static boolean zzc(Context context) {
        if (zzb) {
            return true;
        }
        synchronized (zzfv.class) {
            try {
                if (zzb) {
                    return true;
                }
                boolean zZzd = zzd(context);
                if (zZzd) {
                    zzb = zZzd;
                }
                return zZzd;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @RequiresApi(24)
    @TargetApi(24)
    @GuardedBy("DirectBootUtils.class")
    private static boolean zzd(Context context) {
        boolean z2;
        boolean z3 = true;
        int i2 = 1;
        while (true) {
            z2 = false;
            if (i2 > 2) {
                break;
            }
            if (zza == null) {
                zza = (UserManager) context.getSystemService(UserManager.class);
            }
            UserManager userManager = zza;
            if (userManager == null) {
                return true;
            }
            try {
                if (userManager.isUserUnlocked()) {
                    break;
                }
                if (userManager.isUserRunning(Process.myUserHandle())) {
                    z3 = false;
                }
            } catch (NullPointerException e2) {
                Log.w("DirectBootUtils", "Failed to check if user is unlocked.", e2);
                zza = null;
                i2++;
            }
        }
        z2 = z3;
        if (z2) {
            zza = null;
        }
        return z2;
    }

    @ChecksSdkIntAtLeast(api = 24)
    public static boolean zza() {
        return Build.VERSION.SDK_INT >= 24;
    }
}
