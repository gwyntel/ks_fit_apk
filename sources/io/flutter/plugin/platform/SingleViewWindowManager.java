package io.flutter.plugin.platform;

import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowMetrics;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import io.flutter.Log;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* loaded from: classes4.dex */
abstract class SingleViewWindowManager implements WindowManager {
    private static final String TAG = "PlatformViewsController";
    final WindowManager delegate;
    SingleViewFakeWindowViewGroup fakeWindowRootView;

    SingleViewWindowManager(WindowManager windowManager, SingleViewFakeWindowViewGroup singleViewFakeWindowViewGroup) {
        this.delegate = windowManager;
        this.fakeWindowRootView = singleViewFakeWindowViewGroup;
    }

    @Override // android.view.WindowManager
    @RequiresApi(api = 31)
    public void addCrossWindowBlurEnabledListener(@NonNull Consumer<Boolean> consumer) {
        this.delegate.addCrossWindowBlurEnabledListener(consumer);
    }

    @Override // android.view.ViewManager
    public void addView(View view, ViewGroup.LayoutParams layoutParams) {
        SingleViewFakeWindowViewGroup singleViewFakeWindowViewGroup = this.fakeWindowRootView;
        if (singleViewFakeWindowViewGroup == null) {
            Log.w(TAG, "Embedded view called addView while detached from presentation");
        } else {
            singleViewFakeWindowViewGroup.addView(view, layoutParams);
        }
    }

    @Override // android.view.WindowManager
    @NonNull
    @RequiresApi(api = 30)
    public WindowMetrics getCurrentWindowMetrics() {
        return this.delegate.getCurrentWindowMetrics();
    }

    @Override // android.view.WindowManager
    @Deprecated
    public Display getDefaultDisplay() {
        return this.delegate.getDefaultDisplay();
    }

    @Override // android.view.WindowManager
    @NonNull
    @RequiresApi(api = 30)
    public WindowMetrics getMaximumWindowMetrics() {
        return this.delegate.getMaximumWindowMetrics();
    }

    @Override // android.view.WindowManager
    @RequiresApi(api = 31)
    public boolean isCrossWindowBlurEnabled() {
        return this.delegate.isCrossWindowBlurEnabled();
    }

    @Override // android.view.WindowManager
    @RequiresApi(api = 31)
    public void removeCrossWindowBlurEnabledListener(@NonNull Consumer<Boolean> consumer) {
        this.delegate.removeCrossWindowBlurEnabledListener(consumer);
    }

    @Override // android.view.ViewManager
    public void removeView(View view) {
        SingleViewFakeWindowViewGroup singleViewFakeWindowViewGroup = this.fakeWindowRootView;
        if (singleViewFakeWindowViewGroup == null) {
            Log.w(TAG, "Embedded view called removeView while detached from presentation");
        } else {
            singleViewFakeWindowViewGroup.removeView(view);
        }
    }

    @Override // android.view.WindowManager
    public void removeViewImmediate(View view) {
        if (this.fakeWindowRootView == null) {
            Log.w(TAG, "Embedded view called removeViewImmediate while detached from presentation");
        } else {
            view.clearAnimation();
            this.fakeWindowRootView.removeView(view);
        }
    }

    @Override // android.view.ViewManager
    public void updateViewLayout(View view, ViewGroup.LayoutParams layoutParams) {
        SingleViewFakeWindowViewGroup singleViewFakeWindowViewGroup = this.fakeWindowRootView;
        if (singleViewFakeWindowViewGroup == null) {
            Log.w(TAG, "Embedded view called updateViewLayout while detached from presentation");
        } else {
            singleViewFakeWindowViewGroup.updateViewLayout(view, layoutParams);
        }
    }

    @Override // android.view.WindowManager
    @RequiresApi(api = 31)
    public void addCrossWindowBlurEnabledListener(@NonNull Executor executor, @NonNull Consumer<Boolean> consumer) {
        this.delegate.addCrossWindowBlurEnabledListener(executor, consumer);
    }
}
