package com.aliyun.iot.link.ui.component.statusview;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.FrameLayout;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.FragmentActivity;

/* loaded from: classes3.dex */
public interface AbstractStatusView {

    public interface OnCancelListener {
        void onCanceled();
    }

    public interface OnRetryListener {
        void onRetry(View view);
    }

    void customizeEmptyView(@NonNull View view, FrameLayout.LayoutParams layoutParams);

    void customizeErrorView(@NonNull View view, FrameLayout.LayoutParams layoutParams);

    void dismissLoading(FragmentActivity fragmentActivity);

    void setDefaultEmptyView(@StringRes int i2);

    void setDefaultEmptyView(@StringRes int i2, @DrawableRes int i3);

    void setDefaultEmptyView(@Nullable String str);

    void setDefaultEmptyView(@Nullable String str, @Nullable Drawable drawable);

    void setDefaultErrorView(@StringRes int i2, @StringRes int i3, @DrawableRes int i4, @Nullable OnRetryListener onRetryListener);

    void setDefaultErrorView(@StringRes int i2, @StringRes int i3, @Nullable OnRetryListener onRetryListener);

    void setDefaultErrorView(@Nullable String str, @Nullable String str2, @Nullable Drawable drawable, @Nullable OnRetryListener onRetryListener);

    void setDefaultErrorView(@NonNull String str, @NonNull String str2, @Nullable OnRetryListener onRetryListener);

    void showContentView();

    void showEmptyView();

    void showErrorView();

    void showLoading(FragmentActivity fragmentActivity);

    void showLoading(FragmentActivity fragmentActivity, @IdRes int i2);
}
