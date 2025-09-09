package com.huawei.secure.android.common.ssl.util;

import android.content.Context;

/* loaded from: classes4.dex */
public class ContextUtil {

    /* renamed from: a, reason: collision with root package name */
    private static Context f18481a;

    public static Context getInstance() {
        return f18481a;
    }

    public static void setContext(Context context) {
        if (context == null || f18481a != null) {
            return;
        }
        f18481a = context.getApplicationContext();
    }
}
