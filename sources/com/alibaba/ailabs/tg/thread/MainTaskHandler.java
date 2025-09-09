package com.alibaba.ailabs.tg.thread;

import android.os.Handler;
import android.os.Looper;

/* loaded from: classes2.dex */
public final class MainTaskHandler {
    private static volatile Handler sHandler;

    private static Handler get() {
        if (sHandler == null) {
            synchronized (MainTaskHandler.class) {
                try {
                    if (sHandler == null) {
                        sHandler = new Handler(Looper.getMainLooper());
                    }
                } finally {
                }
            }
        }
        return sHandler;
    }

    public static void post(Runnable runnable) {
        get().post(runnable);
    }

    public static void postDelay(Runnable runnable, long j2) {
        get().postDelayed(runnable, j2);
    }
}
