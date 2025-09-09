package com.aliyun.iot.link.ui.component.adapter;

import android.animation.ObjectAnimator;
import android.util.Property;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.aliyun.iot.link.ui.component.R;
import com.aliyun.iot.link.ui.component.RefreshRecycleViewLayout;

/* loaded from: classes3.dex */
public class LoadMoreWrapperAdapter extends RecyclerView.Adapter {
    private static final int LOADING_VIEW = 546;
    private RecyclerView.Adapter mInnerAdapter;
    private LoadMoreViewHolder mLoadMoreView;
    private RecyclerView mRecyclerView;

    @Nullable
    private RefreshRecycleViewLayout mRefreshRecycleViewLayout;
    private RequestLoadMoreListener mRequestLoadMoreListener;
    private SpanSizeLookup mSpanSizeLookup;
    private boolean mNextLoadEnable = false;
    private boolean mLoadMoreEnable = false;
    private boolean mLoading = false;
    private boolean mEnableLoadMoreEndClick = false;
    private RecyclerView.AdapterDataObserver adapterDataObserver = new RecyclerView.AdapterDataObserver() { // from class: com.aliyun.iot.link.ui.component.adapter.LoadMoreWrapperAdapter.6
        private void notifyDisplayChildUpdated() {
            if (LoadMoreWrapperAdapter.this.mInnerAdapter == null || LoadMoreWrapperAdapter.this.mRefreshRecycleViewLayout == null) {
                return;
            }
            if (LoadMoreWrapperAdapter.this.mInnerAdapter.getItemCount() != 0) {
                LoadMoreWrapperAdapter.this.mRefreshRecycleViewLayout.showContentView();
            } else {
                LoadMoreWrapperAdapter.this.mRefreshRecycleViewLayout.showEmptyView();
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public void onChanged() {
            super.onChanged();
            if (LoadMoreWrapperAdapter.this.mRequestLoadMoreListener != null) {
                LoadMoreWrapperAdapter.this.mNextLoadEnable = true;
                LoadMoreWrapperAdapter.this.mLoadMoreEnable = true;
                LoadMoreWrapperAdapter.this.mLoading = false;
                LoadMoreWrapperAdapter.this.mLoadMoreView.setLoadMoreStatus(0);
            }
            LoadMoreWrapperAdapter.this.notifyDataSetChanged();
            notifyDisplayChildUpdated();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public void onItemRangeChanged(int i2, int i3) {
            super.onItemRangeChanged(i2, i3);
            LoadMoreWrapperAdapter.this.notifyItemRangeChanged(i2, i3);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public void onItemRangeInserted(int i2, int i3) {
            super.onItemRangeInserted(i2, i3);
            LoadMoreWrapperAdapter.this.notifyItemRangeInserted(i2, i3);
            notifyDisplayChildUpdated();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public void onItemRangeMoved(int i2, int i3, int i4) {
            super.onItemRangeMoved(i2, i3, i4);
            LoadMoreWrapperAdapter.this.notifyItemMoved(i2, i3);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public void onItemRangeRemoved(int i2, int i3) {
            super.onItemRangeRemoved(i2, i3);
            LoadMoreWrapperAdapter.this.notifyItemRangeRemoved(i2, i3);
            notifyDisplayChildUpdated();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public void onItemRangeChanged(int i2, int i3, Object obj) {
            super.onItemRangeChanged(i2, i3, obj);
            LoadMoreWrapperAdapter.this.notifyItemRangeChanged(i2, i3, obj);
        }
    };

    static class LoadMoreViewHolder extends RecyclerView.ViewHolder {
        static final int STATUS_DEFAULT = 0;
        static final int STATUS_END = 3;
        static final int STATUS_FAIL = 2;
        static final int STATUS_LOADING = 1;
        private final View endView;
        private final View iconView;
        private final View loadingView;
        private boolean mLoadMoreEndGone;
        private int mLoadMoreStatus;
        private ObjectAnimator rotateAnimator;

        private LoadMoreViewHolder(View view) {
            super(view);
            this.endView = null;
            this.loadingView = view.findViewById(R.id.link_loadmore_loading_view);
            View viewFindViewById = view.findViewById(R.id.link_loadmore_icon);
            this.iconView = viewFindViewById;
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(viewFindViewById, (Property<View, Float>) View.ROTATION, 0.0f, 720.0f);
            this.rotateAnimator = objectAnimatorOfFloat;
            objectAnimatorOfFloat.setDuration(800L);
            this.rotateAnimator.setInterpolator(new LinearInterpolator());
            this.rotateAnimator.setRepeatCount(-1);
        }

        static RecyclerView.ViewHolder getInstance(ViewGroup viewGroup) {
            return new LoadMoreViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.link_refresh_layout_loadmore_view, viewGroup, false));
        }

        int getLoadMoreStatus() {
            return this.mLoadMoreStatus;
        }

        boolean isLoadEndMoreGone() {
            return this.endView == null || this.mLoadMoreEndGone;
        }

        void setLoadMoreEndGone(boolean z2) {
            this.mLoadMoreEndGone = z2;
        }

        void setLoadMoreStatus(int i2) {
            this.mLoadMoreStatus = i2;
        }

        void update() {
            if (this.mLoadMoreStatus != 1) {
                this.loadingView.setVisibility(8);
                this.rotateAnimator.end();
                return;
            }
            this.loadingView.setVisibility(0);
            if (this.rotateAnimator.isRunning()) {
                return;
            }
            this.rotateAnimator.cancel();
            this.rotateAnimator.start();
        }
    }

    public interface RequestLoadMoreListener {
        void onLoadMoreRequested();
    }

    public interface SpanSizeLookup {
        int getSpanSize(GridLayoutManager gridLayoutManager, int i2);
    }

    public LoadMoreWrapperAdapter(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.Adapter adapter) {
        init(recyclerView, adapter);
    }

    private void autoLoadMore(int i2) {
        if (getLoadMoreViewCount() != 0 && i2 >= getItemCount() - 1 && this.mLoadMoreView.getLoadMoreStatus() == 0) {
            this.mLoadMoreView.setLoadMoreStatus(1);
            if (this.mLoading) {
                return;
            }
            this.mLoading = true;
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView != null) {
                recyclerView.post(new Runnable() { // from class: com.aliyun.iot.link.ui.component.adapter.LoadMoreWrapperAdapter.5
                    @Override // java.lang.Runnable
                    public void run() {
                        LoadMoreWrapperAdapter.this.mRequestLoadMoreListener.onLoadMoreRequested();
                    }
                });
            } else {
                this.mRequestLoadMoreListener.onLoadMoreRequested();
            }
        }
    }

    private void disableLoadMoreIfNotFullPage(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager;
        setEnableLoadMore(false);
        if (recyclerView == null || (layoutManager = recyclerView.getLayoutManager()) == null) {
            return;
        }
        if (layoutManager instanceof LinearLayoutManager) {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            recyclerView.postDelayed(new Runnable() { // from class: com.aliyun.iot.link.ui.component.adapter.LoadMoreWrapperAdapter.2
                @Override // java.lang.Runnable
                public void run() {
                    if (LoadMoreWrapperAdapter.this.isFullScreen(linearLayoutManager)) {
                        LoadMoreWrapperAdapter.this.setEnableLoadMore(true);
                    }
                }
            }, 50L);
        } else if (!(layoutManager instanceof StaggeredGridLayoutManager)) {
            setEnableLoadMore(false);
        } else {
            final StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
            recyclerView.postDelayed(new Runnable() { // from class: com.aliyun.iot.link.ui.component.adapter.LoadMoreWrapperAdapter.3
                @Override // java.lang.Runnable
                public void run() {
                    int[] iArr = new int[staggeredGridLayoutManager.getSpanCount()];
                    staggeredGridLayoutManager.findLastCompletelyVisibleItemPositions(iArr);
                    if (LoadMoreWrapperAdapter.this.getTheBiggestNumber(iArr) + 1 != LoadMoreWrapperAdapter.this.getItemCount()) {
                        LoadMoreWrapperAdapter.this.setEnableLoadMore(true);
                    }
                }
            }, 50L);
        }
    }

    private int getLoadMoreViewCount() {
        if (this.mRequestLoadMoreListener == null || !this.mLoadMoreEnable) {
            return 0;
        }
        return ((this.mNextLoadEnable || !this.mLoadMoreView.isLoadEndMoreGone()) && this.mInnerAdapter.getItemCount() != 0) ? 1 : 0;
    }

    private int getLoadMoreViewPosition() {
        return this.mInnerAdapter.getItemCount();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getTheBiggestNumber(int[] iArr) {
        int i2 = -1;
        if (iArr != null && iArr.length != 0) {
            for (int i3 : iArr) {
                if (i3 > i2) {
                    i2 = i3;
                }
            }
        }
        return i2;
    }

    private void init(RecyclerView recyclerView, RecyclerView.Adapter adapter) {
        if (recyclerView == null) {
            throw new IllegalArgumentException("must attach a RecyclerView");
        }
        this.mInnerAdapter = adapter;
        this.mRecyclerView = recyclerView;
        LoadMoreViewHolder loadMoreViewHolder = (LoadMoreViewHolder) LoadMoreViewHolder.getInstance(recyclerView);
        this.mLoadMoreView = loadMoreViewHolder;
        loadMoreViewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.iot.link.ui.component.adapter.LoadMoreWrapperAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (LoadMoreWrapperAdapter.this.mLoadMoreView.getLoadMoreStatus() == 2) {
                    LoadMoreWrapperAdapter.this.notifyLoadMoreToLoading();
                }
                if (LoadMoreWrapperAdapter.this.mEnableLoadMoreEndClick && LoadMoreWrapperAdapter.this.mLoadMoreView.getLoadMoreStatus() == 3) {
                    LoadMoreWrapperAdapter.this.notifyLoadMoreToLoading();
                }
            }
        });
        disableLoadMoreIfNotFullPage(this.mRecyclerView);
        adapter.registerAdapterDataObserver(this.adapterDataObserver);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isFixedViewType(int i2) {
        return i2 == LOADING_VIEW;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isFullScreen(LinearLayoutManager linearLayoutManager) {
        return (linearLayoutManager.findLastCompletelyVisibleItemPosition() + 1 == getItemCount() && linearLayoutManager.findFirstCompletelyVisibleItemPosition() == 0) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyLoadMoreToLoading() {
        if (this.mLoadMoreView.getLoadMoreStatus() == 1) {
            return;
        }
        this.mLoadMoreView.setLoadMoreStatus(0);
        notifyItemChanged(getLoadMoreViewPosition());
    }

    private void setFullSpan(RecyclerView.ViewHolder viewHolder) {
        if (viewHolder.itemView.getLayoutParams() instanceof StaggeredGridLayoutManager.LayoutParams) {
            ((StaggeredGridLayoutManager.LayoutParams) viewHolder.itemView.getLayoutParams()).setFullSpan(true);
        }
    }

    public void enableLoadMoreEndClick(boolean z2) {
        this.mEnableLoadMoreEndClick = z2;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mInnerAdapter.getItemCount() + getLoadMoreViewCount();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i2) {
        return i2 < this.mInnerAdapter.getItemCount() ? this.mInnerAdapter.getItemViewType(i2) : LOADING_VIEW;
    }

    public boolean isLoadMoreEnable() {
        return this.mLoadMoreEnable;
    }

    public boolean isLoading() {
        return this.mLoading;
    }

    public void loadMoreComplete() {
        if (getLoadMoreViewCount() == 0) {
            return;
        }
        this.mLoading = false;
        this.mNextLoadEnable = true;
        this.mLoadMoreView.setLoadMoreStatus(0);
        notifyItemChanged(getLoadMoreViewPosition());
    }

    public void loadMoreEnd() {
        loadMoreEnd(false);
    }

    public void loadMoreFail() {
        if (getLoadMoreViewCount() == 0) {
            return;
        }
        this.mLoading = false;
        this.mLoadMoreView.setLoadMoreStatus(2);
        notifyItemChanged(getLoadMoreViewPosition());
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() { // from class: com.aliyun.iot.link.ui.component.adapter.LoadMoreWrapperAdapter.4
                @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
                public int getSpanSize(int i2) {
                    int itemViewType = LoadMoreWrapperAdapter.this.getItemViewType(i2);
                    if (LoadMoreWrapperAdapter.this.mSpanSizeLookup != null) {
                        return LoadMoreWrapperAdapter.this.isFixedViewType(itemViewType) ? gridLayoutManager.getSpanCount() : LoadMoreWrapperAdapter.this.mSpanSizeLookup.getSpanSize(gridLayoutManager, i2);
                    }
                    if (LoadMoreWrapperAdapter.this.isFixedViewType(itemViewType)) {
                        return gridLayoutManager.getSpanCount();
                    }
                    return 1;
                }
            });
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i2) {
        autoLoadMore(i2);
        if (viewHolder.getItemViewType() == LOADING_VIEW) {
            this.mLoadMoreView.update();
        } else {
            this.mInnerAdapter.onBindViewHolder(viewHolder, i2);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i2) {
        return i2 == LOADING_VIEW ? this.mLoadMoreView : this.mInnerAdapter.onCreateViewHolder(viewGroup, i2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onViewAttachedToWindow(RecyclerView.ViewHolder viewHolder) {
        super.onViewAttachedToWindow(viewHolder);
        if (viewHolder.getItemViewType() == LOADING_VIEW) {
            setFullSpan(viewHolder);
        }
    }

    public void setEnableLoadMore(boolean z2) {
        int loadMoreViewCount = getLoadMoreViewCount();
        this.mLoadMoreEnable = z2;
        int loadMoreViewCount2 = getLoadMoreViewCount();
        if (loadMoreViewCount == 1) {
            if (loadMoreViewCount2 == 0) {
                notifyItemRemoved(getLoadMoreViewPosition());
            }
        } else if (loadMoreViewCount2 == 1) {
            this.mLoadMoreView.setLoadMoreStatus(0);
            notifyItemInserted(getLoadMoreViewPosition());
        }
    }

    public void setRequestLoadMoreListener(RequestLoadMoreListener requestLoadMoreListener) {
        this.mRequestLoadMoreListener = requestLoadMoreListener;
        this.mNextLoadEnable = true;
        this.mLoadMoreEnable = true;
        this.mLoading = false;
    }

    public void setSpanSizeLookup(SpanSizeLookup spanSizeLookup) {
        this.mSpanSizeLookup = spanSizeLookup;
    }

    public void loadMoreEnd(boolean z2) {
        if (getLoadMoreViewCount() == 0) {
            return;
        }
        this.mLoading = false;
        this.mNextLoadEnable = false;
        this.mLoadMoreView.setLoadMoreEndGone(z2);
        if (z2) {
            notifyItemRemoved(getLoadMoreViewPosition());
        } else {
            this.mLoadMoreView.setLoadMoreStatus(3);
            notifyItemChanged(getLoadMoreViewPosition());
        }
    }

    public LoadMoreWrapperAdapter(@NonNull RefreshRecycleViewLayout refreshRecycleViewLayout, @NonNull RecyclerView.Adapter adapter) {
        this.mRefreshRecycleViewLayout = refreshRecycleViewLayout;
        init(refreshRecycleViewLayout.getRecyclerView(), adapter);
    }
}
