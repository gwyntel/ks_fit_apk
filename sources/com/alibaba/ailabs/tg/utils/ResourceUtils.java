package com.alibaba.ailabs.tg.utils;

import android.content.Context;
import android.text.TextUtils;

/* loaded from: classes2.dex */
public final class ResourceUtils {
    public static int getColor(Context context, int i2) {
        if (context == null || i2 <= 0) {
            return 0;
        }
        return context.getResources().getColor(i2);
    }

    public static int getMipmapId(Context context, String str) {
        return getResourceId(context, str, "mipmap");
    }

    public static int getResourceId(Context context, String str, String str2) {
        if (context == null || TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return 0;
        }
        return context.getResources().getIdentifier(str, str2, AppUtils.getPackageName(context));
    }
}
