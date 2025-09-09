package com.luck.picture.lib.immersive;

import android.R;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityEventCompat;
import androidx.media3.common.C;
import com.luck.picture.lib.utils.DensityUtil;
import io.flutter.plugin.platform.PlatformPlugin;

/* loaded from: classes4.dex */
public class ImmersiveManager {
    private static final String TAG_FAKE_STATUS_BAR_VIEW = "TAG_FAKE_STATUS_BAR_VIEW";
    private static final String TAG_MARGIN_ADDED = "TAG_MARGIN_ADDED";
    private static final String TAG_NAVIGATION_BAR_VIEW = "TAG_NAVIGATION_BAR_VIEW";

    public static void immersiveAboveAPI23(AppCompatActivity appCompatActivity, int i2, int i3, boolean z2) {
        immersiveAboveAPI23(appCompatActivity, false, false, i2, i3, z2);
    }

    private static void initBarBelowLOLLIPOP(Activity activity) {
        Window window = activity.getWindow();
        window.addFlags(AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL);
        setupStatusBarView(activity);
        if (DensityUtil.isNavBarVisible(activity)) {
            window.addFlags(C.BUFFER_FLAG_FIRST_SAMPLE);
            setupNavBarView(activity);
        }
    }

    private static void setupNavBarView(Activity activity) {
        FrameLayout.LayoutParams layoutParams;
        Window window = activity.getWindow();
        View viewFindViewWithTag = window.getDecorView().findViewWithTag(TAG_NAVIGATION_BAR_VIEW);
        if (viewFindViewWithTag == null) {
            viewFindViewWithTag = new View(activity);
            viewFindViewWithTag.setTag(TAG_NAVIGATION_BAR_VIEW);
            ((ViewGroup) window.getDecorView()).addView(viewFindViewWithTag);
        }
        if (DensityUtil.isNavigationAtBottom(activity)) {
            layoutParams = new FrameLayout.LayoutParams(-1, DensityUtil.getNavigationBarHeight(activity));
            layoutParams.gravity = 80;
        } else {
            layoutParams = new FrameLayout.LayoutParams(DensityUtil.getNavigationBarWidth(activity), -1);
            layoutParams.gravity = GravityCompat.END;
        }
        viewFindViewWithTag.setLayoutParams(layoutParams);
        viewFindViewWithTag.setBackgroundColor(0);
        viewFindViewWithTag.setVisibility(0);
    }

    private static void setupStatusBarView(Activity activity) {
        Window window = activity.getWindow();
        View viewFindViewWithTag = window.getDecorView().findViewWithTag(TAG_FAKE_STATUS_BAR_VIEW);
        if (viewFindViewWithTag == null) {
            viewFindViewWithTag = new View(activity);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, DensityUtil.getStatusBarHeight(activity));
            layoutParams.gravity = 48;
            viewFindViewWithTag.setLayoutParams(layoutParams);
            viewFindViewWithTag.setVisibility(0);
            viewFindViewWithTag.setTag(TAG_MARGIN_ADDED);
            ((ViewGroup) window.getDecorView()).addView(viewFindViewWithTag);
        }
        viewFindViewWithTag.setBackgroundColor(0);
    }

    public static void translucentStatusBar(Activity activity, boolean z2) {
        Window window = activity.getWindow();
        window.addFlags(Integer.MIN_VALUE);
        window.clearFlags(AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL);
        window.setStatusBarColor(0);
        View decorView = window.getDecorView();
        if (z2) {
            decorView.setSystemUiVisibility(9472);
        } else {
            window.getDecorView().setSystemUiVisibility(PlatformPlugin.DEFAULT_SYSTEM_UI);
        }
        View childAt = ((ViewGroup) window.findViewById(R.id.content)).getChildAt(0);
        if (childAt != null) {
            childAt.setFitsSystemWindows(false);
            ViewCompat.requestApplyInsets(childAt);
        }
    }

    public static void immersiveAboveAPI23(AppCompatActivity appCompatActivity, boolean z2, boolean z3, int i2, int i3, boolean z4) {
        try {
            Window window = appCompatActivity.getWindow();
            boolean z5 = true;
            if (z2 && z3) {
                window.clearFlags(201326592);
                LightStatusBarUtils.setLightStatusBar(appCompatActivity, true, true, i2 == 0, z4);
                window.addFlags(Integer.MIN_VALUE);
            } else if (!z2 && !z3) {
                window.requestFeature(1);
                window.clearFlags(201326592);
                if (i2 != 0) {
                    z5 = false;
                }
                LightStatusBarUtils.setLightStatusBar(appCompatActivity, false, false, z5, z4);
                window.addFlags(Integer.MIN_VALUE);
            } else {
                if (z2) {
                    return;
                }
                window.requestFeature(1);
                window.clearFlags(201326592);
                LightStatusBarUtils.setLightStatusBar(appCompatActivity, false, true, i2 == 0, z4);
                window.addFlags(Integer.MIN_VALUE);
            }
            window.setStatusBarColor(i2);
            window.setNavigationBarColor(i3);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
