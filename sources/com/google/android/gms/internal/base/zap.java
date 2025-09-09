package com.google.android.gms.internal.base;

import android.os.Build;
import com.aliyun.alink.linksdk.tmp.device.deviceshadow.DiskLruHelper;

/* loaded from: classes3.dex */
public final class zap {
    public static final int zaa;

    static {
        zaa = Build.VERSION.SDK_INT >= 31 ? DiskLruHelper.DEFAULT_MAXSIZE : 0;
    }
}
