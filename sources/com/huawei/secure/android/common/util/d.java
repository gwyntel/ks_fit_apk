package com.huawei.secure.android.common.util;

import android.os.Handler;
import android.os.Looper;

/* loaded from: classes4.dex */
public class d {

    /* renamed from: a, reason: collision with root package name */
    private static Handler f18545a = new Handler(Looper.getMainLooper());

    public static void a(Runnable runnable) {
        if (runnable == null) {
            return;
        }
        f18545a.post(runnable);
    }
}
