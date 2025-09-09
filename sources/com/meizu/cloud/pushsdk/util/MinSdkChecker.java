package com.meizu.cloud.pushsdk.util;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import com.meizu.cloud.pushsdk.constants.PushConstants;

/* loaded from: classes4.dex */
public class MinSdkChecker {
    public static boolean isSupportBigTextStyleAndAction() {
        return true;
    }

    public static boolean isSupportDeviceDefaultLight() {
        return true;
    }

    public static boolean isSupportKeyguardState() {
        return true;
    }

    public static boolean isSupportNotificationBuild() {
        return true;
    }

    public static boolean isSupportNotificationChannel() {
        return Build.VERSION.SDK_INT >= 26;
    }

    public static boolean isSupportNotificationSort() {
        return Build.VERSION.SDK_INT >= 26;
    }

    public static boolean isSupportSendNotification() {
        return true;
    }

    public static boolean isSupportSetDrawableSmallIcon() {
        return true;
    }

    public static boolean isSupportTransmitMessageValue(Context context, String str) {
        return !TextUtils.isEmpty(MzSystemUtils.findReceiver(context, PushConstants.MZ_PUSH_SYSTEM_RECEIVER_ACTION, str));
    }

    public static boolean isSupportVideoNotification() {
        return true;
    }
}
