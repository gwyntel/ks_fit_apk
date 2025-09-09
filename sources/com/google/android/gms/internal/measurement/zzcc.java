package com.google.android.gms.internal.measurement;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.view.accessibility.AccessibilityEventCompat;
import com.aliyun.alink.linksdk.tmp.device.deviceshadow.DiskLruHelper;

/* loaded from: classes3.dex */
public final class zzcc {
    public static final int zza;
    private static final int zzb;

    static {
        int i2 = Build.VERSION.SDK_INT;
        zzb = AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL;
        zza = i2 >= 31 ? DiskLruHelper.DEFAULT_MAXSIZE : 0;
    }

    public static PendingIntent zza(Context context, int i2, Intent intent, int i3) {
        return PendingIntent.getBroadcast(context, 0, intent, i3);
    }
}
