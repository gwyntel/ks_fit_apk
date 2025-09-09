package com.aliyun.iot.link.ui.component.statusview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import com.aliyun.iot.link.ui.component.BetterViewAnimator;
import com.aliyun.iot.link.ui.component.LoadingCompact;
import com.aliyun.iot.link.ui.component.R;
import com.aliyun.iot.link.ui.component.statusview.AbstractStatusView;

/* loaded from: classes3.dex */
public class LinkStatusView extends BetterViewAnimator implements AbstractStatusView {
    public static final String TAG = "LinkStatusView";
    private View mEmptyView;
    private TextView mErrorRetryTextView;
    private int mErrorRetryTint;
    private View mErrorView;

    public LinkStatusView(Context context) {
        super(context);
        this.mEmptyView = null;
        this.mErrorView = null;
        this.mErrorRetryTint = Color.parseColor("#0079ff");
    }

    private void findContentView() {
        if (getChildCount() == 0) {
            throw new IllegalArgumentException("LinkStatusView must have at least one direct child");
        }
    }

    private void findSubviews() {
        this.mEmptyView = findViewById(R.id.link_status_empty_view);
        this.mErrorView = findViewById(R.id.link_status_error_view);
        if (this.mEmptyView == null) {
            this.mEmptyView = inflateChildView(R.layout.link_status_view_empty);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
            this.mEmptyView.setId(R.id.link_status_empty_view);
            addView(this.mEmptyView, layoutParams);
        }
        if (this.mErrorView == null) {
            this.mErrorView = inflateChildView(R.layout.link_status_view_error);
            FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(-1, -1);
            this.mErrorView.setId(R.id.link_status_error_view);
            addView(this.mErrorView, layoutParams2);
        }
    }

    private View inflateChildView(@LayoutRes int i2) {
        return LayoutInflater.from(getContext()).inflate(i2, (ViewGroup) this, false);
    }

    private void removeDisplayChildId(@IdRes int i2) {
        for (int i3 = 0; i3 < getChildCount(); i3++) {
            if (getChildAt(i3).getId() == i2) {
                removeViewAt(i3);
            }
        }
    }

    @Override // com.aliyun.iot.link.ui.component.statusview.AbstractStatusView
    public void customizeEmptyView(@NonNull View view, FrameLayout.LayoutParams layoutParams) {
        view.setId(R.id.link_status_empty_view);
        removeDisplayChildId(R.id.link_status_empty_view);
        addView(view, layoutParams);
        this.mEmptyView = null;
    }

    @Override // com.aliyun.iot.link.ui.component.statusview.AbstractStatusView
    public void customizeErrorView(@NonNull View view, FrameLayout.LayoutParams layoutParams) {
        view.setId(R.id.link_status_error_view);
        removeDisplayChildId(R.id.link_status_error_view);
        addView(view, layoutParams);
        this.mErrorView = null;
    }

    @Override // com.aliyun.iot.link.ui.component.statusview.AbstractStatusView
    public void dismissLoading(FragmentActivity fragmentActivity) {
        LoadingCompact.dismissLoading(fragmentActivity);
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        findContentView();
        findSubviews();
    }

    @Override // com.aliyun.iot.link.ui.component.statusview.AbstractStatusView
    public void setDefaultEmptyView(@Nullable String str) {
        setDefaultEmptyView(str, ContextCompat.getDrawable(getContext(), R.drawable.ic_status_view_empty));
    }

    @Override // com.aliyun.iot.link.ui.component.statusview.AbstractStatusView
    public void setDefaultErrorView(int i2, int i3, @Nullable AbstractStatusView.OnRetryListener onRetryListener) {
        setDefaultErrorView(i2, i3, R.drawable.ic_status_view_error, onRetryListener);
    }

    public void setErrorRetryTint(int i2) {
        this.mErrorRetryTint = i2;
        TextView textView = this.mErrorRetryTextView;
        if (textView != null) {
            textView.setBackgroundTintList(ColorStateList.valueOf(i2));
            this.mErrorRetryTextView.setTextColor(i2);
        }
    }

    @Override // com.aliyun.iot.link.ui.component.statusview.AbstractStatusView
    public void showContentView() {
        if (getChildCount() > 0) {
            setDisplayedChild(0);
        }
    }

    @Override // com.aliyun.iot.link.ui.component.statusview.AbstractStatusView
    public void showEmptyView() throws Resources.NotFoundException {
        setDisplayedChildId(R.id.link_status_empty_view);
    }

    @Override // com.aliyun.iot.link.ui.component.statusview.AbstractStatusView
    public void showErrorView() throws Resources.NotFoundException {
        setDisplayedChildId(R.id.link_status_error_view);
    }

    @Override // com.aliyun.iot.link.ui.component.statusview.AbstractStatusView
    public void showLoading(FragmentActivity fragmentActivity) {
        LoadingCompact.showLoading(fragmentActivity);
    }

    @Override // com.aliyun.iot.link.ui.component.statusview.AbstractStatusView
    public void setDefaultEmptyView(int i2) {
        setDefaultEmptyView(i2, R.drawable.ic_status_view_empty);
    }

    @Override // com.aliyun.iot.link.ui.component.statusview.AbstractStatusView
    public void setDefaultErrorView(@NonNull String str, @NonNull String str2, @Nullable AbstractStatusView.OnRetryListener onRetryListener) {
        setDefaultErrorView(str, str2, ContextCompat.getDrawable(getContext(), R.drawable.ic_status_view_error), onRetryListener);
    }

    @Override // com.aliyun.iot.link.ui.component.statusview.AbstractStatusView
    public void showLoading(FragmentActivity fragmentActivity, int i2) {
        LoadingCompact.showLoading(fragmentActivity, 0, i2, false, false, null);
    }

    @Override // com.aliyun.iot.link.ui.component.statusview.AbstractStatusView
    public void setDefaultEmptyView(int i2, int i3) {
        try {
            setDefaultEmptyView(getResources().getString(i2), ContextCompat.getDrawable(getContext(), i3));
        } catch (Resources.NotFoundException e2) {
            Log.e(TAG, "setDefaultEmptyView: ", e2);
        }
    }

    @Override // com.aliyun.iot.link.ui.component.statusview.AbstractStatusView
    public void setDefaultErrorView(int i2, int i3, int i4, @Nullable AbstractStatusView.OnRetryListener onRetryListener) {
        try {
            setDefaultErrorView(getResources().getString(i2), getResources().getString(i3), ContextCompat.getDrawable(getContext(), i4), onRetryListener);
        } catch (Resources.NotFoundException e2) {
            Log.e(TAG, "setDefaultErrorView: ", e2);
        }
    }

    public LinkStatusView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mEmptyView = null;
        this.mErrorView = null;
        this.mErrorRetryTint = Color.parseColor("#0079ff");
    }

    @Override // com.aliyun.iot.link.ui.component.statusview.AbstractStatusView
    public void setDefaultEmptyView(@Nullable String str, @Nullable Drawable drawable) {
        if (this.mEmptyView == null) {
            removeDisplayChildId(R.id.link_status_empty_view);
            View viewInflateChildView = inflateChildView(R.layout.link_status_view_empty);
            this.mEmptyView = viewInflateChildView;
            viewInflateChildView.setId(R.id.link_status_empty_view);
            addView(this.mEmptyView);
        }
        TextView textView = (TextView) this.mEmptyView.findViewById(R.id.link_status_empty_text);
        ImageView imageView = (ImageView) this.mEmptyView.findViewById(R.id.link_status_empty_icon);
        try {
            textView.setText(str);
            imageView.setImageDrawable(drawable);
        } catch (NullPointerException e2) {
            Log.e(TAG, "setDefaultEmptyView: ", e2);
        }
    }

    @Override // com.aliyun.iot.link.ui.component.statusview.AbstractStatusView
    public void setDefaultErrorView(@Nullable String str, @Nullable String str2, @Nullable Drawable drawable, @Nullable final AbstractStatusView.OnRetryListener onRetryListener) {
        if (this.mErrorView == null) {
            View viewInflateChildView = inflateChildView(R.layout.link_status_view_error);
            this.mErrorView = viewInflateChildView;
            viewInflateChildView.setId(R.id.link_status_error_view);
            removeDisplayChildId(R.id.link_status_error_view);
            addView(this.mErrorView);
        }
        TextView textView = (TextView) this.mErrorView.findViewById(R.id.link_status_error_text);
        ImageView imageView = (ImageView) this.mErrorView.findViewById(R.id.link_status_error_icon);
        this.mErrorRetryTextView = (TextView) this.mErrorView.findViewById(R.id.link_status_error_retry);
        setErrorRetryTint(this.mErrorRetryTint);
        try {
            textView.setText(str);
            imageView.setImageDrawable(drawable);
            this.mErrorRetryTextView.setText(str2);
            this.mErrorRetryTextView.setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.iot.link.ui.component.statusview.LinkStatusView.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    AbstractStatusView.OnRetryListener onRetryListener2 = onRetryListener;
                    if (onRetryListener2 != null) {
                        onRetryListener2.onRetry(view);
                    }
                }
            });
        } catch (NullPointerException e2) {
            Log.e(TAG, "setDefaultErrorView: ", e2);
        }
    }
}
