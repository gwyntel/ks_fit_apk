package com.luck.picture.lib.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import com.luck.picture.lib.immersive.RomUtils;

/* loaded from: classes4.dex */
public class DensityUtil {
    public static int dip2px(Context context, float f2) {
        return (int) ((f2 * context.getApplicationContext().getResources().getDisplayMetrics().density) + 0.5f);
    }

    private static int getInternalDimensionSize(Context context, String str) throws Resources.NotFoundException {
        try {
            int identifier = Resources.getSystem().getIdentifier(str, "dimen", "android");
            if (identifier > 0) {
                int dimensionPixelSize = context.getResources().getDimensionPixelSize(identifier);
                int dimensionPixelSize2 = Resources.getSystem().getDimensionPixelSize(identifier);
                if (dimensionPixelSize2 >= dimensionPixelSize) {
                    return dimensionPixelSize2;
                }
                float f2 = (dimensionPixelSize * Resources.getSystem().getDisplayMetrics().density) / context.getResources().getDisplayMetrics().density;
                return (int) (f2 >= 0.0f ? f2 + 0.5f : f2 - 0.5f);
            }
        } catch (Resources.NotFoundException unused) {
        }
        return 0;
    }

    @TargetApi(14)
    public static int getNavigationBarHeight(Context context) {
        boolean z2 = context.getResources().getConfiguration().orientation == 1;
        if (isNavBarVisible(context)) {
            return getInternalDimensionSize(context, z2 ? "navigation_bar_height" : "navigation_bar_height_landscape");
        }
        return 0;
    }

    @TargetApi(14)
    public static int getNavigationBarWidth(Context context) {
        if (isNavBarVisible(context)) {
            return getInternalDimensionSize(context, "navigation_bar_width");
        }
        return 0;
    }

    public static int getRealScreenHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getApplicationContext().getSystemService("window");
        Point point = new Point();
        windowManager.getDefaultDisplay().getRealSize(point);
        return point.y;
    }

    public static int getRealScreenWidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getApplicationContext().getSystemService("window");
        Point point = new Point();
        windowManager.getDefaultDisplay().getRealSize(point);
        return point.x;
    }

    private static String getResNameById(Context context, int i2) {
        try {
            return context.getResources().getResourceEntryName(i2);
        } catch (Exception unused) {
            return "";
        }
    }

    public static int getScreenHeight(Context context) {
        return getRealScreenHeight(context) - getStatusNavigationBarHeight(context);
    }

    @SuppressLint({"NewApi"})
    private static float getSmallestWidthDp(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
        float f2 = displayMetrics.widthPixels;
        float f3 = displayMetrics.density;
        return Math.min(f2 / f3, displayMetrics.heightPixels / f3);
    }

    public static int getStatusBarHeight(Context context) throws Resources.NotFoundException {
        int statusBarHeight;
        int dimensionPixelSize;
        Resources system = Resources.getSystem();
        int identifier = system.getIdentifier("status_bar_height", "dimen", "android");
        if (identifier > 0) {
            try {
                int dimensionPixelSize2 = context.getResources().getDimensionPixelSize(identifier);
                dimensionPixelSize = system.getDimensionPixelSize(identifier);
                if (dimensionPixelSize < dimensionPixelSize2) {
                    float f2 = (dimensionPixelSize2 * system.getDisplayMetrics().density) / context.getResources().getDisplayMetrics().density;
                    dimensionPixelSize = (int) (f2 >= 0.0f ? f2 + 0.5f : f2 - 0.5f);
                }
            } catch (Exception unused) {
                statusBarHeight = getStatusBarHeight();
            }
        } else {
            dimensionPixelSize = 0;
        }
        statusBarHeight = dimensionPixelSize;
        return statusBarHeight == 0 ? dip2px(context, 26.0f) : statusBarHeight;
    }

    private static int getStatusNavigationBarHeight(Context context) {
        return isNavBarVisible(context) ? getStatusBarHeight(context) + getNavigationBarHeight(context) : getStatusBarHeight(context);
    }

    public static boolean isNavBarVisible(Context context) {
        boolean z2;
        if (!(context instanceof Activity)) {
            return false;
        }
        Activity activity = (Activity) context;
        ViewGroup viewGroup = (ViewGroup) activity.getWindow().getDecorView();
        int childCount = viewGroup.getChildCount();
        int i2 = 0;
        while (true) {
            if (i2 >= childCount) {
                z2 = false;
                break;
            }
            View childAt = viewGroup.getChildAt(i2);
            int id = childAt.getId();
            if (id != -1 && "navigationBarBackground".equals(getResNameById(activity, id)) && childAt.getVisibility() == 0) {
                z2 = true;
                break;
            }
            i2++;
        }
        if (!z2) {
            return z2;
        }
        if (RomUtils.isSamsung() && Build.VERSION.SDK_INT < 29) {
            try {
                return Settings.Global.getInt(activity.getContentResolver(), "navigationbar_hide_bar_enabled") == 0;
            } catch (Exception unused) {
            }
        }
        return (viewGroup.getSystemUiVisibility() & 2) == 0;
    }

    public static boolean isNavigationAtBottom(Activity activity) {
        return getSmallestWidthDp(activity) >= 600.0f || (activity.getResources().getConfiguration().orientation == 1);
    }

    public static int getStatusBarHeight() {
        Resources system = Resources.getSystem();
        return system.getDimensionPixelSize(system.getIdentifier("status_bar_height", "dimen", "android"));
    }
}
