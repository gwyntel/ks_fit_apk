package com.alibaba.ailabs.tg.utils;

import android.app.Activity;
import android.os.IBinder;
import android.os.Looper;
import android.view.View;

/* loaded from: classes2.dex */
public final class WindowValidUtils {
    public static boolean isActivityIllegal(Activity activity) {
        return activity == null || activity.isDestroyed();
    }

    private static boolean isTokenValid(View view) {
        IBinder windowToken;
        if (view == null || (windowToken = view.getWindowToken()) == null) {
            return false;
        }
        try {
            if (windowToken.isBinderAlive()) {
                return windowToken.pingBinder();
            }
            return false;
        } catch (Throwable th) {
            LogUtils.e(th.toString());
            th.printStackTrace();
            return false;
        }
    }

    public static boolean isWindowEffective(Activity activity) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            return false;
        }
        if (activity != null) {
            return (activity.isFinishing() || activity.isDestroyed()) ? false : true;
        }
        return true;
    }
}
