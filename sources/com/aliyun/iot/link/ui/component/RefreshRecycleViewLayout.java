package com.aliyun.iot.link.ui.component;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.aliyun.iot.link.ui.component.adapter.LoadMoreWrapperAdapter;
import com.aliyun.iot.link.ui.component.statusview.AbstractStatusView;
import com.aliyun.iot.link.ui.component.statusview.LinkStatusView;

/* loaded from: classes3.dex */
public class RefreshRecycleViewLayout extends SwipeRefreshLayout implements AbstractStatusView {
    private LinkStatusView linkStatusView;
    private LoadMoreWrapperAdapter loadMoreWrapperAdapter;
    private LoadMoreWrapperAdapter.RequestLoadMoreListener onLoadMoreListener;
    private RecyclerView recyclerView;

    public RefreshRecycleViewLayout(Context context) {
        this(context, null);
    }

    private void applyStyle() {
        setProgressViewOffset(false, 0, (int) TypedValue.applyDimension(1, 24.0f, getResources().getDisplayMetrics()));
        setColorSchemeColors(-16745985);
        setSize(1);
        setProgressBackgroundColorSchemeColor(-1);
    }

    private void findSubViews() {
        this.linkStatusView = new LinkStatusView(getContext());
        this.linkStatusView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        addView(this.linkStatusView);
        RecyclerView recyclerView = (RecyclerView) LayoutInflater.from(getContext()).inflate(R.layout.recyclerview, (ViewGroup) this.linkStatusView, false);
        this.recyclerView = recyclerView;
        recyclerView.setId(R.id.link_status_content_view);
        this.recyclerView.setLayoutParams(new FrameLayout.LayoutParams(-1, -2));
        this.linkStatusView.addView(this.recyclerView, 0);
    }

    @Override // com.aliyun.iot.link.ui.component.statusview.AbstractStatusView
    public void customizeEmptyView(@NonNull View view, FrameLayout.LayoutParams layoutParams) {
        this.linkStatusView.customizeEmptyView(view, layoutParams);
    }

    @Override // com.aliyun.iot.link.ui.component.statusview.AbstractStatusView
    public void customizeErrorView(@NonNull View view, FrameLayout.LayoutParams layoutParams) {
        this.linkStatusView.customizeErrorView(view, layoutParams);
    }

    @Override // com.aliyun.iot.link.ui.component.statusview.AbstractStatusView
    public void dismissLoading(FragmentActivity fragmentActivity) {
        LoadingCompact.dismissLoading(fragmentActivity);
    }

    public LoadMoreWrapperAdapter getAdapter() {
        return this.loadMoreWrapperAdapter;
    }

    public LinkStatusView getLinkStatusView() {
        return this.linkStatusView;
    }

    public RecyclerView getRecyclerView() {
        return this.recyclerView;
    }

    public void loadMoreComplete() {
        LoadMoreWrapperAdapter loadMoreWrapperAdapter = this.loadMoreWrapperAdapter;
        if (loadMoreWrapperAdapter != null) {
            loadMoreWrapperAdapter.loadMoreComplete();
        }
    }

    public void loadMoreEnd() {
        LoadMoreWrapperAdapter loadMoreWrapperAdapter = this.loadMoreWrapperAdapter;
        if (loadMoreWrapperAdapter != null) {
            loadMoreWrapperAdapter.loadMoreEnd(true);
        }
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        if (adapter == null) {
            return;
        }
        LoadMoreWrapperAdapter loadMoreWrapperAdapter = new LoadMoreWrapperAdapter(this, adapter);
        this.loadMoreWrapperAdapter = loadMoreWrapperAdapter;
        this.recyclerView.setAdapter(loadMoreWrapperAdapter);
    }

    @Override // com.aliyun.iot.link.ui.component.statusview.AbstractStatusView
    public void setDefaultEmptyView(@Nullable String str) {
        this.linkStatusView.setDefaultEmptyView(str);
    }

    @Override // com.aliyun.iot.link.ui.component.statusview.AbstractStatusView
    public void setDefaultErrorView(int i2, int i3, @Nullable AbstractStatusView.OnRetryListener onRetryListener) {
        this.linkStatusView.setDefaultErrorView(i2, i3, onRetryListener);
    }

    public void setEnableLoadMore(boolean z2) {
        LoadMoreWrapperAdapter loadMoreWrapperAdapter = this.loadMoreWrapperAdapter;
        if (loadMoreWrapperAdapter != null) {
            loadMoreWrapperAdapter.setEnableLoadMore(z2);
        }
    }

    public void setOnLoadMoreListener(LoadMoreWrapperAdapter.RequestLoadMoreListener requestLoadMoreListener) {
        LoadMoreWrapperAdapter loadMoreWrapperAdapter = this.loadMoreWrapperAdapter;
        if (loadMoreWrapperAdapter != null) {
            loadMoreWrapperAdapter.setRequestLoadMoreListener(requestLoadMoreListener);
        }
    }

    @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout
    public void setOnRefreshListener(final SwipeRefreshLayout.OnRefreshListener onRefreshListener) {
        super.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() { // from class: com.aliyun.iot.link.ui.component.RefreshRecycleViewLayout.1
            @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
            public void onRefresh() {
                if (onRefreshListener != null) {
                    if (RefreshRecycleViewLayout.this.loadMoreWrapperAdapter != null && RefreshRecycleViewLayout.this.loadMoreWrapperAdapter.isLoading()) {
                        RefreshRecycleViewLayout.this.loadMoreWrapperAdapter.loadMoreComplete();
                    }
                    onRefreshListener.onRefresh();
                }
            }
        });
    }

    @Override // com.aliyun.iot.link.ui.component.statusview.AbstractStatusView
    public void showContentView() {
        this.linkStatusView.showContentView();
    }

    @Override // com.aliyun.iot.link.ui.component.statusview.AbstractStatusView
    public void showEmptyView() {
        this.linkStatusView.showEmptyView();
    }

    @Override // com.aliyun.iot.link.ui.component.statusview.AbstractStatusView
    public void showErrorView() throws Resources.NotFoundException {
        this.linkStatusView.showErrorView();
    }

    @Override // com.aliyun.iot.link.ui.component.statusview.AbstractStatusView
    public void showLoading(FragmentActivity fragmentActivity) {
        this.linkStatusView.showLoading(fragmentActivity);
    }

    public RefreshRecycleViewLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.linkStatusView = null;
        this.recyclerView = null;
        this.loadMoreWrapperAdapter = null;
        this.onLoadMoreListener = null;
        applyStyle();
        findSubViews();
    }

    @Override // com.aliyun.iot.link.ui.component.statusview.AbstractStatusView
    public void setDefaultEmptyView(int i2) {
        this.linkStatusView.setDefaultEmptyView(i2);
    }

    @Override // com.aliyun.iot.link.ui.component.statusview.AbstractStatusView
    public void setDefaultErrorView(@NonNull String str, @NonNull String str2, @Nullable AbstractStatusView.OnRetryListener onRetryListener) {
        this.linkStatusView.setDefaultErrorView(str, str2, onRetryListener);
    }

    @Override // com.aliyun.iot.link.ui.component.statusview.AbstractStatusView
    public void showLoading(FragmentActivity fragmentActivity, int i2) {
        LoadingCompact.showLoading(fragmentActivity, 0, i2, false, false, null);
    }

    @Override // com.aliyun.iot.link.ui.component.statusview.AbstractStatusView
    public void setDefaultEmptyView(int i2, int i3) {
        this.linkStatusView.setDefaultEmptyView(i2, i3);
    }

    @Override // com.aliyun.iot.link.ui.component.statusview.AbstractStatusView
    public void setDefaultErrorView(int i2, int i3, int i4, @Nullable AbstractStatusView.OnRetryListener onRetryListener) {
        this.linkStatusView.setDefaultErrorView(i2, i3, i4, onRetryListener);
    }

    @Override // com.aliyun.iot.link.ui.component.statusview.AbstractStatusView
    public void setDefaultEmptyView(@Nullable String str, @Nullable Drawable drawable) {
        this.linkStatusView.setDefaultEmptyView(str, drawable);
    }

    @Override // com.aliyun.iot.link.ui.component.statusview.AbstractStatusView
    public void setDefaultErrorView(@Nullable String str, @Nullable String str2, @Nullable Drawable drawable, @Nullable AbstractStatusView.OnRetryListener onRetryListener) {
        this.linkStatusView.setDefaultErrorView(str, str2, drawable, onRetryListener);
    }
}
