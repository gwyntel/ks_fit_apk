package com.umeng.message.proguard;

import android.content.res.Resources;

/* loaded from: classes4.dex */
public final class bb {
    public static int a(float f2) {
        return Math.round(Resources.getSystem().getDisplayMetrics().density * f2);
    }

    public static int a() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }
}
