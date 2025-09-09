package com.alibaba.ailabs.tg.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityEventCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.media3.common.C;
import com.alibaba.ailabs.tg.baseutils.R;
import io.flutter.plugin.platform.PlatformPlugin;

/* loaded from: classes2.dex */
public class StatusBarUtil {
    public static final int DEFAULT_STATUS_BAR_ALPHA = 112;
    private static final int FAKE_STATUS_BAR_VIEW_ID = R.id.statusbarutil_fake_status_bar_view;
    private static final int FAKE_TRANSLUCENT_VIEW_ID = R.id.statusbarutil_translucent_view;
    private static final int TAG_KEY_HAVE_SET_OFFSET = -123;

    /* renamed from: com.alibaba.ailabs.tg.utils.StatusBarUtil$1, reason: invalid class name */
    static class AnonymousClass1 implements Runnable {
        final /* synthetic */ CoordinatorLayout val$coordinatorLayout;

        AnonymousClass1(CoordinatorLayout coordinatorLayout) {
            this.val$coordinatorLayout = coordinatorLayout;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.val$coordinatorLayout.requestLayout();
        }
    }

    private static void addTranslucentView(Activity activity, int i2) {
        ViewGroup viewGroup = (ViewGroup) activity.findViewById(android.R.id.content);
        View viewFindViewById = viewGroup.findViewById(FAKE_TRANSLUCENT_VIEW_ID);
        if (viewFindViewById == null) {
            viewGroup.addView(createTranslucentStatusBarView(activity, i2));
            return;
        }
        if (viewFindViewById.getVisibility() == 8) {
            viewFindViewById.setVisibility(0);
        }
        viewFindViewById.setBackgroundColor(Color.argb(i2, 0, 0, 0));
    }

    private static int calculateStatusColor(@ColorInt int i2, int i3) {
        if (i3 == 0) {
            return i2;
        }
        float f2 = 1.0f - (i3 / 255.0f);
        return ((int) (((i2 & 255) * f2) + 0.5d)) | (((int) ((((i2 >> 16) & 255) * f2) + 0.5d)) << 16) | ViewCompat.MEASURED_STATE_MASK | (((int) ((((i2 >> 8) & 255) * f2) + 0.5d)) << 8);
    }

    @TargetApi(19)
    private static void clearPreviousSetting(Activity activity) {
        ViewGroup viewGroup = (ViewGroup) activity.getWindow().getDecorView();
        View viewFindViewById = viewGroup.findViewById(FAKE_STATUS_BAR_VIEW_ID);
        if (viewFindViewById != null) {
            viewGroup.removeView(viewFindViewById);
            ((ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0)).setPadding(0, 0, 0, 0);
        }
    }

    private static View createStatusBarView(Activity activity, @ColorInt int i2) {
        return createStatusBarView(activity, i2, 0);
    }

    private static View createTranslucentStatusBarView(Activity activity, int i2) {
        View view = new View(activity);
        view.setLayoutParams(new LinearLayout.LayoutParams(-1, getStatusBarHeight(activity)));
        view.setBackgroundColor(Color.argb(i2, 0, 0, 0));
        view.setId(FAKE_TRANSLUCENT_VIEW_ID);
        return view;
    }

    public static int getStatusBarHeight(Context context) {
        return context.getResources().getDimensionPixelSize(context.getResources().getIdentifier("status_bar_height", "dimen", "android"));
    }

    public static void hideFakeStatusBarView(Activity activity) {
        ViewGroup viewGroup = (ViewGroup) activity.getWindow().getDecorView();
        View viewFindViewById = viewGroup.findViewById(FAKE_STATUS_BAR_VIEW_ID);
        if (viewFindViewById != null) {
            viewFindViewById.setVisibility(8);
        }
        View viewFindViewById2 = viewGroup.findViewById(FAKE_TRANSLUCENT_VIEW_ID);
        if (viewFindViewById2 != null) {
            viewFindViewById2.setVisibility(8);
        }
    }

    @SuppressLint({"ObsoleteSdkInt"})
    public static void hideSystemNavigationBar(Activity activity) {
        activity.getWindow().getDecorView().setSystemUiVisibility(4102);
    }

    public static void setColor(Activity activity, @ColorInt int i2) {
        setColor(activity, i2, 112);
    }

    @Deprecated
    public static void setColorDiff(Activity activity, @ColorInt int i2) {
        transparentStatusBar(activity);
        ViewGroup viewGroup = (ViewGroup) activity.findViewById(android.R.id.content);
        View viewFindViewById = viewGroup.findViewById(FAKE_STATUS_BAR_VIEW_ID);
        if (viewFindViewById != null) {
            if (viewFindViewById.getVisibility() == 8) {
                viewFindViewById.setVisibility(0);
            }
            viewFindViewById.setBackgroundColor(i2);
        } else {
            viewGroup.addView(createStatusBarView(activity, i2));
        }
        setRootView(activity);
    }

    public static void setColorForDrawerLayout(Activity activity, DrawerLayout drawerLayout, @ColorInt int i2) {
        setColorForDrawerLayout(activity, drawerLayout, i2, 112);
    }

    @Deprecated
    public static void setColorForDrawerLayoutDiff(Activity activity, DrawerLayout drawerLayout, @ColorInt int i2) {
        activity.getWindow().addFlags(AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL);
        ViewGroup viewGroup = (ViewGroup) drawerLayout.getChildAt(0);
        View viewFindViewById = viewGroup.findViewById(FAKE_STATUS_BAR_VIEW_ID);
        if (viewFindViewById != null) {
            if (viewFindViewById.getVisibility() == 8) {
                viewFindViewById.setVisibility(0);
            }
            viewFindViewById.setBackgroundColor(calculateStatusColor(i2, 112));
        } else {
            viewGroup.addView(createStatusBarView(activity, i2), 0);
        }
        if (!(viewGroup instanceof LinearLayout) && viewGroup.getChildAt(1) != null) {
            viewGroup.getChildAt(1).setPadding(0, getStatusBarHeight(activity), 0, 0);
        }
        setDrawerLayoutProperty(drawerLayout, viewGroup);
    }

    public static void setColorForSwipeBack(Activity activity, int i2) {
        setColorForSwipeBack(activity, i2, 112);
    }

    public static void setColorNoTranslucent(Activity activity, @ColorInt int i2) {
        setColor(activity, i2, 0);
    }

    public static void setColorNoTranslucentForDrawerLayout(Activity activity, DrawerLayout drawerLayout, @ColorInt int i2) {
        setColorForDrawerLayout(activity, drawerLayout, i2, 0);
    }

    private static void setDrawerLayoutProperty(DrawerLayout drawerLayout, ViewGroup viewGroup) {
        ViewGroup viewGroup2 = (ViewGroup) drawerLayout.getChildAt(1);
        drawerLayout.setFitsSystemWindows(false);
        viewGroup.setFitsSystemWindows(false);
        viewGroup.setClipToPadding(true);
        viewGroup2.setFitsSystemWindows(false);
    }

    private static void setRootView(Activity activity) {
        ViewGroup viewGroup = (ViewGroup) activity.findViewById(android.R.id.content);
        int childCount = viewGroup.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = viewGroup.getChildAt(i2);
            if (childAt instanceof ViewGroup) {
                childAt.setFitsSystemWindows(true);
                ((ViewGroup) childAt).setClipToPadding(true);
            }
        }
    }

    static void setStatusBarLightMode(@NonNull Activity activity, boolean z2) {
        setStatusBarLightMode(activity.getWindow(), z2);
    }

    public static void setStatusBarVisibility(@NonNull Activity activity, boolean z2) {
        setStatusBarVisibility(activity.getWindow(), z2);
    }

    public static void setTranslucent(Activity activity) {
        setTranslucent(activity, 112);
    }

    @Deprecated
    public static void setTranslucentDiff(Activity activity) {
        activity.getWindow().addFlags(AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL);
        setRootView(activity);
    }

    public static void setTranslucentForCoordinatorLayout(Activity activity, int i2) {
        transparentStatusBar(activity);
        addTranslucentView(activity, i2);
    }

    public static void setTranslucentForDrawerLayout(Activity activity, DrawerLayout drawerLayout) {
        setTranslucentForDrawerLayout(activity, drawerLayout, 112);
    }

    @Deprecated
    public static void setTranslucentForDrawerLayoutDiff(Activity activity, DrawerLayout drawerLayout) {
        activity.getWindow().addFlags(AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL);
        ViewGroup viewGroup = (ViewGroup) drawerLayout.getChildAt(0);
        viewGroup.setFitsSystemWindows(true);
        viewGroup.setClipToPadding(true);
        ((ViewGroup) drawerLayout.getChildAt(1)).setFitsSystemWindows(false);
        drawerLayout.setFitsSystemWindows(false);
    }

    public static void setTranslucentForImageView(Activity activity, View view) {
        setTranslucentForImageView(activity, 112, view);
    }

    public static void setTranslucentForImageViewInFragment(Activity activity, View view, boolean z2) {
    }

    public static void setTransparent(Activity activity) {
        transparentStatusBar(activity);
        setRootView(activity);
    }

    public static void setTransparentForDrawerLayout(Activity activity, DrawerLayout drawerLayout) {
        activity.getWindow().addFlags(Integer.MIN_VALUE);
        activity.getWindow().clearFlags(AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL);
        activity.getWindow().setStatusBarColor(0);
        ViewGroup viewGroup = (ViewGroup) drawerLayout.getChildAt(0);
        if (!(viewGroup instanceof LinearLayout) && viewGroup.getChildAt(1) != null) {
            viewGroup.getChildAt(1).setPadding(0, getStatusBarHeight(activity), 0, 0);
        }
        setDrawerLayoutProperty(drawerLayout, viewGroup);
    }

    public static void setTransparentForImageView(Activity activity, View view) {
        setTranslucentForImageView(activity, 0, view);
    }

    public static void setTransparentForImageViewInFragment(Activity activity, View view) {
        setTranslucentForImageViewInFragment(activity, 0, view);
    }

    private static void setTransparentForWindow(Activity activity) {
        activity.getWindow().setStatusBarColor(0);
        activity.getWindow().getDecorView().setSystemUiVisibility(PlatformPlugin.DEFAULT_SYSTEM_UI);
    }

    @TargetApi(19)
    private static void transparentStatusBar(Activity activity) {
        activity.getWindow().addFlags(Integer.MIN_VALUE);
        activity.getWindow().clearFlags(AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL);
        activity.getWindow().addFlags(C.BUFFER_FLAG_FIRST_SAMPLE);
        activity.getWindow().setStatusBarColor(0);
    }

    private static View createStatusBarView(Activity activity, @ColorInt int i2, int i3) {
        View view = new View(activity);
        view.setLayoutParams(new LinearLayout.LayoutParams(-1, getStatusBarHeight(activity)));
        view.setBackgroundColor(calculateStatusColor(i2, i3));
        view.setId(FAKE_STATUS_BAR_VIEW_ID);
        return view;
    }

    public static void setColor(Activity activity, @ColorInt int i2, int i3) {
        activity.getWindow().addFlags(Integer.MIN_VALUE);
        activity.getWindow().clearFlags(AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL);
        activity.getWindow().setStatusBarColor(calculateStatusColor(i2, i3));
    }

    public static void setColorForDrawerLayout(Activity activity, DrawerLayout drawerLayout, @ColorInt int i2, int i3) {
        activity.getWindow().addFlags(Integer.MIN_VALUE);
        activity.getWindow().clearFlags(AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL);
        activity.getWindow().setStatusBarColor(0);
        ViewGroup viewGroup = (ViewGroup) drawerLayout.getChildAt(0);
        View viewFindViewById = viewGroup.findViewById(FAKE_STATUS_BAR_VIEW_ID);
        if (viewFindViewById != null) {
            if (viewFindViewById.getVisibility() == 8) {
                viewFindViewById.setVisibility(0);
            }
            viewFindViewById.setBackgroundColor(i2);
        } else {
            viewGroup.addView(createStatusBarView(activity, i2), 0);
        }
        if (!(viewGroup instanceof LinearLayout) && viewGroup.getChildAt(1) != null) {
            viewGroup.getChildAt(1).setPadding(viewGroup.getPaddingLeft(), getStatusBarHeight(activity) + viewGroup.getPaddingTop(), viewGroup.getPaddingRight(), viewGroup.getPaddingBottom());
        }
        setDrawerLayoutProperty(drawerLayout, viewGroup);
        addTranslucentView(activity, i3);
    }

    public static void setColorForSwipeBack(Activity activity, @ColorInt int i2, int i3) {
        ViewGroup viewGroup = (ViewGroup) activity.findViewById(android.R.id.content);
        View childAt = viewGroup.getChildAt(0);
        int statusBarHeight = getStatusBarHeight(activity);
        if (childAt == null || !(childAt instanceof CoordinatorLayout)) {
            viewGroup.setPadding(0, statusBarHeight, 0, 0);
            viewGroup.setBackgroundColor(calculateStatusColor(i2, i3));
        } else {
            ((CoordinatorLayout) childAt).setStatusBarBackgroundColor(calculateStatusColor(i2, i3));
        }
        setTransparentForWindow(activity);
    }

    public static void setStatusBarLightMode(@NonNull Window window, boolean z2) {
        int i2;
        View decorView = window.getDecorView();
        if (decorView != null) {
            int systemUiVisibility = decorView.getSystemUiVisibility();
            if (z2) {
                window.addFlags(Integer.MIN_VALUE);
                i2 = systemUiVisibility | 8192;
            } else {
                i2 = systemUiVisibility & (-8193);
            }
            decorView.setSystemUiVisibility(i2);
        }
    }

    public static void setStatusBarVisibility(@NonNull Window window, boolean z2) {
        if (z2) {
            window.clearFlags(1024);
        } else {
            window.addFlags(1024);
        }
    }

    public static void setTranslucent(Activity activity, int i2) {
        setTransparent(activity);
        addTranslucentView(activity, i2);
    }

    public static void setTranslucentForDrawerLayout(Activity activity, DrawerLayout drawerLayout, int i2) {
        setTransparentForDrawerLayout(activity, drawerLayout);
        addTranslucentView(activity, i2);
    }

    public static void setTranslucentForImageView(Activity activity, int i2, View view) {
        setTransparentForWindow(activity);
        addTranslucentView(activity, i2);
        if (view != null) {
            Object tag = view.getTag(TAG_KEY_HAVE_SET_OFFSET);
            if (tag == null || !((Boolean) tag).booleanValue()) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
                marginLayoutParams.setMargins(marginLayoutParams.leftMargin, marginLayoutParams.topMargin + getStatusBarHeight(activity), marginLayoutParams.rightMargin, marginLayoutParams.bottomMargin);
                view.setTag(TAG_KEY_HAVE_SET_OFFSET, Boolean.TRUE);
            }
        }
    }

    public static void setTranslucentForImageViewInFragment(Activity activity, View view) {
        setTranslucentForImageViewInFragment(activity, 112, view);
    }

    public static void setTranslucentForImageViewInFragment(Activity activity, int i2, View view) {
        setTranslucentForImageView(activity, i2, view);
    }
}
