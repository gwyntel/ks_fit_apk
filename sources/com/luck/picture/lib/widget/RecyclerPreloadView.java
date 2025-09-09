package com.luck.picture.lib.widget;

import android.content.Context;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.luck.picture.lib.interfaces.OnRecyclerViewPreloadMoreListener;
import com.luck.picture.lib.interfaces.OnRecyclerViewScrollListener;
import com.luck.picture.lib.interfaces.OnRecyclerViewScrollStateListener;

/* loaded from: classes4.dex */
public class RecyclerPreloadView extends RecyclerView {
    private static final int BOTTOM_DEFAULT = 1;
    public static final int BOTTOM_PRELOAD = 2;
    private static final int LIMIT = 150;
    private static final String TAG = "RecyclerPreloadView";
    private boolean isEnabledLoadMore;
    private boolean isInTheBottom;
    private int mFirstVisiblePosition;
    private int mLastVisiblePosition;
    private OnRecyclerViewPreloadMoreListener onRecyclerViewPreloadListener;
    private OnRecyclerViewScrollListener onRecyclerViewScrollListener;
    private OnRecyclerViewScrollStateListener onRecyclerViewScrollStateListener;
    private int reachBottomRow;

    public RecyclerPreloadView(@NonNull Context context) {
        super(context);
        this.isInTheBottom = false;
        this.isEnabledLoadMore = false;
        this.reachBottomRow = 1;
    }

    private void setLayoutManagerPosition(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            this.mFirstVisiblePosition = gridLayoutManager.findFirstVisibleItemPosition();
            this.mLastVisiblePosition = gridLayoutManager.findLastVisibleItemPosition();
        } else if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            this.mFirstVisiblePosition = linearLayoutManager.findFirstVisibleItemPosition();
            this.mLastVisiblePosition = linearLayoutManager.findLastVisibleItemPosition();
        }
    }

    public int getFirstVisiblePosition() {
        return this.mFirstVisiblePosition;
    }

    public int getLastVisiblePosition() {
        return this.mLastVisiblePosition;
    }

    public boolean isEnabledLoadMore() {
        return this.isEnabledLoadMore;
    }

    @Override // androidx.recyclerview.widget.RecyclerView
    public void onScrollStateChanged(int i2) {
        OnRecyclerViewScrollStateListener onRecyclerViewScrollStateListener;
        super.onScrollStateChanged(i2);
        if (i2 == 0 || i2 == 1) {
            setLayoutManagerPosition(getLayoutManager());
        }
        OnRecyclerViewScrollListener onRecyclerViewScrollListener = this.onRecyclerViewScrollListener;
        if (onRecyclerViewScrollListener != null) {
            onRecyclerViewScrollListener.onScrollStateChanged(i2);
        }
        if (i2 != 0 || (onRecyclerViewScrollStateListener = this.onRecyclerViewScrollStateListener) == null) {
            return;
        }
        onRecyclerViewScrollStateListener.onScrollSlow();
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x004c  */
    @Override // androidx.recyclerview.widget.RecyclerView
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onScrolled(int r5, int r6) {
        /*
            r4 = this;
            super.onScrolled(r5, r6)
            androidx.recyclerview.widget.RecyclerView$LayoutManager r0 = r4.getLayoutManager()
            if (r0 == 0) goto L76
            r4.setLayoutManagerPosition(r0)
            com.luck.picture.lib.interfaces.OnRecyclerViewPreloadMoreListener r1 = r4.onRecyclerViewPreloadListener
            if (r1 == 0) goto L57
            boolean r1 = r4.isEnabledLoadMore
            if (r1 == 0) goto L57
            androidx.recyclerview.widget.RecyclerView$Adapter r1 = r4.getAdapter()
            if (r1 == 0) goto L4f
            boolean r2 = r0 instanceof androidx.recyclerview.widget.GridLayoutManager
            r3 = 0
            if (r2 == 0) goto L4c
            androidx.recyclerview.widget.GridLayoutManager r0 = (androidx.recyclerview.widget.GridLayoutManager) r0
            int r1 = r1.getItemCount()
            int r2 = r0.getSpanCount()
            int r1 = r1 / r2
            int r2 = r0.findLastVisibleItemPosition()
            int r0 = r0.getSpanCount()
            int r2 = r2 / r0
            int r0 = r4.reachBottomRow
            int r1 = r1 - r0
            if (r2 < r1) goto L4c
            boolean r0 = r4.isInTheBottom
            if (r0 != 0) goto L47
            com.luck.picture.lib.interfaces.OnRecyclerViewPreloadMoreListener r0 = r4.onRecyclerViewPreloadListener
            r0.onRecyclerViewPreloadMore()
            if (r6 <= 0) goto L57
            r0 = 1
            r4.isInTheBottom = r0
            goto L57
        L47:
            if (r6 != 0) goto L57
            r4.isInTheBottom = r3
            goto L57
        L4c:
            r4.isInTheBottom = r3
            goto L57
        L4f:
            java.lang.RuntimeException r5 = new java.lang.RuntimeException
            java.lang.String r6 = "Adapter is null,Please check it!"
            r5.<init>(r6)
            throw r5
        L57:
            com.luck.picture.lib.interfaces.OnRecyclerViewScrollListener r0 = r4.onRecyclerViewScrollListener
            if (r0 == 0) goto L5e
            r0.onScrolled(r5, r6)
        L5e:
            com.luck.picture.lib.interfaces.OnRecyclerViewScrollStateListener r5 = r4.onRecyclerViewScrollStateListener
            if (r5 == 0) goto L75
            int r5 = java.lang.Math.abs(r6)
            r6 = 150(0x96, float:2.1E-43)
            if (r5 >= r6) goto L70
            com.luck.picture.lib.interfaces.OnRecyclerViewScrollStateListener r5 = r4.onRecyclerViewScrollStateListener
            r5.onScrollSlow()
            goto L75
        L70:
            com.luck.picture.lib.interfaces.OnRecyclerViewScrollStateListener r5 = r4.onRecyclerViewScrollStateListener
            r5.onScrollFast()
        L75:
            return
        L76:
            java.lang.RuntimeException r5 = new java.lang.RuntimeException
            java.lang.String r6 = "LayoutManager is null,Please check it!"
            r5.<init>(r6)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.luck.picture.lib.widget.RecyclerPreloadView.onScrolled(int, int):void");
    }

    public void setEnabledLoadMore(boolean z2) {
        this.isEnabledLoadMore = z2;
    }

    public void setLastVisiblePosition(int i2) {
        this.mLastVisiblePosition = i2;
    }

    public void setOnRecyclerViewPreloadListener(OnRecyclerViewPreloadMoreListener onRecyclerViewPreloadMoreListener) {
        this.onRecyclerViewPreloadListener = onRecyclerViewPreloadMoreListener;
    }

    public void setOnRecyclerViewScrollListener(OnRecyclerViewScrollListener onRecyclerViewScrollListener) {
        this.onRecyclerViewScrollListener = onRecyclerViewScrollListener;
    }

    public void setOnRecyclerViewScrollStateListener(OnRecyclerViewScrollStateListener onRecyclerViewScrollStateListener) {
        this.onRecyclerViewScrollStateListener = onRecyclerViewScrollStateListener;
    }

    public void setReachBottomRow(int i2) {
        if (i2 < 1) {
            i2 = 1;
        }
        this.reachBottomRow = i2;
    }

    public RecyclerPreloadView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.isInTheBottom = false;
        this.isEnabledLoadMore = false;
        this.reachBottomRow = 1;
    }

    public RecyclerPreloadView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.isInTheBottom = false;
        this.isEnabledLoadMore = false;
        this.reachBottomRow = 1;
    }
}
