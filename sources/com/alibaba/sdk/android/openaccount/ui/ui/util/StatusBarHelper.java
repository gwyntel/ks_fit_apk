package com.alibaba.sdk.android.openaccount.ui.ui.util;

import android.app.Activity;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class StatusBarHelper {
    public static final int ANDROID_M = 3;
    public static final int FLYME = 2;
    public static final int MIUI = 1;
    public static final int OTHER = -1;

    @Retention(RetentionPolicy.SOURCE)
    public @interface SystemType {
    }

    public static void setDarkMode(Activity activity, int i2) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        setStatusBarMode(activity, i2, false);
    }

    public static void setLightMode(Activity activity, int i2) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        setStatusBarMode(activity, i2, true);
    }

    public static int setStatusBarMode(Activity activity, boolean z2) {
        if (new MIUIHelper().setStatusBarLightMode(activity, z2)) {
            return 1;
        }
        if (new FlymeHelper().setStatusBarLightMode(activity, z2)) {
            return 2;
        }
        return new AndroidMHelper().setStatusBarLightMode(activity, z2) ? 3 : 0;
    }

    private static void setStatusBarMode(Activity activity, int i2, boolean z2) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (i2 == 1) {
            new MIUIHelper().setStatusBarLightMode(activity, z2);
        } else if (i2 == 2) {
            new FlymeHelper().setStatusBarLightMode(activity, z2);
        } else if (i2 == 3) {
            new AndroidMHelper().setStatusBarLightMode(activity, z2);
        }
    }
}
