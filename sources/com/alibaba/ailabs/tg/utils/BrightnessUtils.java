package com.alibaba.ailabs.tg.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.view.Window;
import android.view.WindowManager;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

/* loaded from: classes2.dex */
public final class BrightnessUtils {
    private BrightnessUtils() {
    }

    public static int getBrightness(Context context) {
        try {
            return Settings.System.getInt(context.getContentResolver(), "screen_brightness");
        } catch (Settings.SettingNotFoundException e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    public static int getWindowBrightness(Context context, Window window) {
        float f2 = window.getAttributes().screenBrightness;
        return f2 < 0.0f ? getBrightness(context) : (int) (f2 * 255.0f);
    }

    public static boolean isAutoBrightnessEnabled(Context context) {
        try {
            return Settings.System.getInt(context.getContentResolver(), "screen_brightness_mode") == 1;
        } catch (Settings.SettingNotFoundException e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public static boolean setAutoBrightnessEnabled(Context context, boolean z2) {
        if (Settings.System.canWrite(context)) {
            return Settings.System.putInt(context.getContentResolver(), "screen_brightness_mode", z2 ? 1 : 0);
        }
        Intent intent = new Intent("android.settings.action.MANAGE_WRITE_SETTINGS");
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        intent.addFlags(268435456);
        context.startActivity(intent);
        return false;
    }

    public static boolean setBrightness(Context context, @IntRange(from = 0, to = 255) int i2) {
        if (Settings.System.canWrite(context)) {
            ContentResolver contentResolver = context.getContentResolver();
            boolean zPutInt = Settings.System.putInt(contentResolver, "screen_brightness", i2);
            contentResolver.notifyChange(Settings.System.getUriFor("screen_brightness"), null);
            return zPutInt;
        }
        Intent intent = new Intent("android.settings.action.MANAGE_WRITE_SETTINGS");
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        intent.addFlags(268435456);
        context.startActivity(intent);
        return false;
    }

    public static void setWindowBrightness(@NonNull Window window, @IntRange(from = 0, to = 255) int i2) {
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.screenBrightness = i2 / 255.0f;
        window.setAttributes(attributes);
    }
}
