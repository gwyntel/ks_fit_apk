package com.luck.picture.lib.immersive;

import android.annotation.TargetApi;
import android.app.Activity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import io.flutter.plugin.platform.PlatformPlugin;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes4.dex */
public class LightStatusBarUtils {
    private static void initStatusBarStyle(Activity activity, boolean z2, boolean z3) {
        if (z2 && z3) {
            activity.getWindow().getDecorView().setSystemUiVisibility(256);
            return;
        }
        if (!z2 && !z3) {
            activity.getWindow().getDecorView().setSystemUiVisibility(PlatformPlugin.DEFAULT_SYSTEM_UI);
        } else {
            if (z2 || !z3) {
                return;
            }
            activity.getWindow().getDecorView().setSystemUiVisibility(PlatformPlugin.DEFAULT_SYSTEM_UI);
        }
    }

    @TargetApi(11)
    private static void setAndroidNativeLightStatusBar(Activity activity, boolean z2, boolean z3, boolean z4, boolean z5) {
        try {
            if (z4) {
                Window window = activity.getWindow();
                if (z2 && z3) {
                    if (z5) {
                        window.getDecorView().setSystemUiVisibility(8448);
                    } else {
                        window.getDecorView().setSystemUiVisibility(256);
                    }
                } else if (z2 || z3) {
                    if (z2 || !z3) {
                        return;
                    }
                    if (z5) {
                        window.getDecorView().setSystemUiVisibility(9472);
                    } else {
                        window.getDecorView().setSystemUiVisibility(PlatformPlugin.DEFAULT_SYSTEM_UI);
                    }
                } else if (z5) {
                    window.getDecorView().setSystemUiVisibility(9472);
                } else {
                    window.getDecorView().setSystemUiVisibility(PlatformPlugin.DEFAULT_SYSTEM_UI);
                }
            } else {
                View decorView = activity.getWindow().getDecorView();
                if (z5) {
                    decorView.setSystemUiVisibility(8192);
                } else {
                    decorView.setSystemUiVisibility(0);
                }
            }
        } catch (Exception unused) {
        }
    }

    private static boolean setFlymeLightStatusBar(Activity activity, boolean z2, boolean z3, boolean z4, boolean z5) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        boolean z6 = false;
        if (activity == null) {
            return false;
        }
        initStatusBarStyle(activity, z2, z3);
        try {
            WindowManager.LayoutParams attributes = activity.getWindow().getAttributes();
            Field declaredField = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
            Field declaredField2 = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
            declaredField.setAccessible(true);
            declaredField2.setAccessible(true);
            int i2 = declaredField.getInt(null);
            int i3 = declaredField2.getInt(attributes);
            declaredField2.setInt(attributes, z5 ? i2 | i3 : (~i2) & i3);
            activity.getWindow().setAttributes(attributes);
            try {
                if (RomUtils.getFlymeVersion() >= 7) {
                    setAndroidNativeLightStatusBar(activity, z2, z3, z4, z5);
                }
                return true;
            } catch (Exception unused) {
                z6 = true;
                setAndroidNativeLightStatusBar(activity, z2, z3, z4, z5);
                return z6;
            }
        } catch (Exception unused2) {
        }
    }

    public static void setLightStatusBar(Activity activity, boolean z2) throws IllegalAccessException, NoSuchFieldException, SecurityException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        setLightStatusBar(activity, false, false, false, z2);
    }

    public static void setLightStatusBarAboveAPI23(Activity activity, boolean z2, boolean z3, boolean z4, boolean z5) throws IllegalAccessException, NoSuchFieldException, SecurityException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        setLightStatusBar(activity, z2, z3, z4, z5);
    }

    private static boolean setMIUILightStatusBar(Activity activity, boolean z2, boolean z3, boolean z4, boolean z5) throws IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        initStatusBarStyle(activity, z2, z3);
        Class<?> cls = activity.getWindow().getClass();
        try {
            Class<?> cls2 = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            int i2 = cls2.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE").getInt(cls2);
            Class<?> cls3 = Integer.TYPE;
            cls.getMethod("setExtraFlags", cls3, cls3).invoke(activity.getWindow(), Integer.valueOf(z5 ? i2 : 0), Integer.valueOf(i2));
            return true;
        } catch (Exception unused) {
            setAndroidNativeLightStatusBar(activity, z2, z3, z4, z5);
            return false;
        }
    }

    public static void setLightStatusBar(Activity activity, boolean z2, boolean z3, boolean z4, boolean z5) throws IllegalAccessException, NoSuchFieldException, SecurityException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        int lightStatusBarAvailableRomType = RomUtils.getLightStatusBarAvailableRomType();
        if (lightStatusBarAvailableRomType == 1) {
            if (RomUtils.getMIUIVersionCode() >= 7) {
                setAndroidNativeLightStatusBar(activity, z2, z3, z4, z5);
                return;
            } else {
                setMIUILightStatusBar(activity, z2, z3, z4, z5);
                return;
            }
        }
        if (lightStatusBarAvailableRomType == 2) {
            setFlymeLightStatusBar(activity, z2, z3, z4, z5);
        } else {
            if (lightStatusBarAvailableRomType != 3) {
                return;
            }
            setAndroidNativeLightStatusBar(activity, z2, z3, z4, z5);
        }
    }
}
