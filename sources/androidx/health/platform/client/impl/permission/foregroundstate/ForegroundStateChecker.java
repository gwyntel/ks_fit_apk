package androidx.health.platform.client.impl.permission.foregroundstate;

import android.annotation.SuppressLint;
import android.app.ActivityManager;

/* loaded from: classes.dex */
public final class ForegroundStateChecker {
    private ForegroundStateChecker() {
    }

    @SuppressLint({"ObsoleteSdkInt"})
    public static boolean isInForeground() {
        ActivityManager.RunningAppProcessInfo runningAppProcessInfo = new ActivityManager.RunningAppProcessInfo();
        ActivityManager.getMyMemoryState(runningAppProcessInfo);
        int i2 = runningAppProcessInfo.importance;
        return i2 == 100 || i2 == 125 || i2 == 200;
    }
}
