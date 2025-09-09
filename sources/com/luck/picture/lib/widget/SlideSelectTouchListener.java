package com.luck.picture.lib.widget;

import android.content.Context;
import android.content.res.Resources;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.OverScroller;
import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes4.dex */
public class SlideSelectTouchListener implements RecyclerView.OnItemTouchListener {
    private boolean isActive;
    private int mBottomBoundFrom;
    private int mBottomBoundTo;
    private int mEnd;
    private int mHeaderViewCount;
    private boolean mInBottomSpot;
    private boolean mInTopSpot;
    private int mLastEnd;
    private int mLastStart;
    private float mLastX;
    private float mLastY;
    private RecyclerView mRecyclerView;
    private int mScrollDistance;
    private OverScroller mScroller;
    private OnSlideSelectListener mSelectListener;
    private int mStart;
    private int mTopBoundFrom;
    private int mTopBoundTo;
    private final Runnable mScrollRunnable = new Runnable() { // from class: com.luck.picture.lib.widget.SlideSelectTouchListener.1
        @Override // java.lang.Runnable
        public void run() {
            if (SlideSelectTouchListener.this.mScroller == null || !SlideSelectTouchListener.this.mScroller.computeScrollOffset()) {
                return;
            }
            SlideSelectTouchListener slideSelectTouchListener = SlideSelectTouchListener.this;
            slideSelectTouchListener.scrollBy(slideSelectTouchListener.mScrollDistance);
            ViewCompat.postOnAnimation(SlideSelectTouchListener.this.mRecyclerView, SlideSelectTouchListener.this.mScrollRunnable);
        }
    };
    private int mMaxScrollDistance = 16;
    private int mAutoScrollDistance = (int) (Resources.getSystem().getDisplayMetrics().density * 56.0f);
    private int mTouchRegionTopOffset = 0;
    private int mTouchRegionBottomOffset = 0;
    private boolean mScrollAboveTopRegion = true;
    private boolean mScrollBelowTopRegion = true;

    public interface OnAdvancedSlideSelectListener extends OnSlideSelectListener {
        void onSelectionFinished(int i2);

        void onSelectionStarted(int i2);
    }

    public interface OnSlideSelectListener {
        void onSelectChange(int i2, int i3, boolean z2);
    }

    public SlideSelectTouchListener() {
        reset();
    }

    private void changeSelectedRange(RecyclerView recyclerView, MotionEvent motionEvent) {
        changeSelectedRange(recyclerView, motionEvent.getX(), motionEvent.getY());
    }

    private void initScroller(Context context) {
        if (this.mScroller == null) {
            this.mScroller = new OverScroller(context, new LinearInterpolator());
        }
    }

    private void notifySelectRangeChange() {
        int i2;
        int i3;
        if (this.mSelectListener == null || (i2 = this.mStart) == -1 || (i3 = this.mEnd) == -1) {
            return;
        }
        int iMin = Math.min(i2, i3);
        int iMax = Math.max(this.mStart, this.mEnd);
        if (iMin < 0) {
            return;
        }
        int i4 = this.mLastStart;
        if (i4 != -1 && this.mLastEnd != -1) {
            if (iMin > i4) {
                this.mSelectListener.onSelectChange(i4, iMin - 1, false);
            } else if (iMin < i4) {
                this.mSelectListener.onSelectChange(iMin, i4 - 1, true);
            }
            int i5 = this.mLastEnd;
            if (iMax > i5) {
                this.mSelectListener.onSelectChange(i5 + 1, iMax, true);
            } else if (iMax < i5) {
                this.mSelectListener.onSelectChange(iMax + 1, i5, false);
            }
        } else if (iMax - iMin == 1) {
            this.mSelectListener.onSelectChange(iMin, iMin, true);
        } else {
            this.mSelectListener.onSelectChange(iMin, iMax, true);
        }
        this.mLastStart = iMin;
        this.mLastEnd = iMax;
    }

    private void processAutoScroll(MotionEvent motionEvent) {
        int y2 = (int) motionEvent.getY();
        int i2 = this.mTopBoundFrom;
        if (y2 >= i2 && y2 <= this.mTopBoundTo) {
            this.mLastX = motionEvent.getX();
            this.mLastY = motionEvent.getY();
            int i3 = this.mTopBoundTo;
            int i4 = this.mTopBoundFrom;
            this.mScrollDistance = (int) (this.mMaxScrollDistance * (((i3 - i4) - (y2 - i4)) / (i3 - i4)) * (-1.0f));
            if (this.mInTopSpot) {
                return;
            }
            this.mInTopSpot = true;
            startAutoScroll();
            return;
        }
        if (this.mScrollAboveTopRegion && y2 < i2) {
            this.mLastX = motionEvent.getX();
            this.mLastY = motionEvent.getY();
            this.mScrollDistance = this.mMaxScrollDistance * (-1);
            if (this.mInTopSpot) {
                return;
            }
            this.mInTopSpot = true;
            startAutoScroll();
            return;
        }
        if (y2 >= this.mBottomBoundFrom && y2 <= this.mBottomBoundTo) {
            this.mLastX = motionEvent.getX();
            this.mLastY = motionEvent.getY();
            float f2 = y2;
            int i5 = this.mBottomBoundFrom;
            this.mScrollDistance = (int) (this.mMaxScrollDistance * ((f2 - i5) / (this.mBottomBoundTo - i5)));
            if (this.mInBottomSpot) {
                return;
            }
            this.mInBottomSpot = true;
            startAutoScroll();
            return;
        }
        if (!this.mScrollBelowTopRegion || y2 <= this.mBottomBoundTo) {
            this.mInBottomSpot = false;
            this.mInTopSpot = false;
            this.mLastX = Float.MIN_VALUE;
            this.mLastY = Float.MIN_VALUE;
            stopAutoScroll();
            return;
        }
        this.mLastX = motionEvent.getX();
        this.mLastY = motionEvent.getY();
        this.mScrollDistance = this.mMaxScrollDistance;
        if (this.mInTopSpot) {
            return;
        }
        this.mInTopSpot = true;
        startAutoScroll();
    }

    private void reset() {
        setActive(false);
        OnSlideSelectListener onSlideSelectListener = this.mSelectListener;
        if (onSlideSelectListener != null && (onSlideSelectListener instanceof OnAdvancedSlideSelectListener)) {
            ((OnAdvancedSlideSelectListener) onSlideSelectListener).onSelectionFinished(this.mEnd);
        }
        this.mStart = -1;
        this.mEnd = -1;
        this.mLastStart = -1;
        this.mLastEnd = -1;
        this.mInTopSpot = false;
        this.mInBottomSpot = false;
        this.mLastX = Float.MIN_VALUE;
        this.mLastY = Float.MIN_VALUE;
        stopAutoScroll();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void scrollBy(int i2) {
        this.mRecyclerView.scrollBy(0, i2 > 0 ? Math.min(i2, this.mMaxScrollDistance) : Math.max(i2, -this.mMaxScrollDistance));
        float f2 = this.mLastX;
        if (f2 != Float.MIN_VALUE) {
            float f3 = this.mLastY;
            if (f3 != Float.MIN_VALUE) {
                changeSelectedRange(this.mRecyclerView, f2, f3);
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
    public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
        if (!this.isActive || recyclerView.getAdapter() == null || recyclerView.getAdapter().getItemCount() == 0) {
            return false;
        }
        int action = motionEvent.getAction();
        if (action == 0 || action == 5) {
            reset();
        }
        this.mRecyclerView = recyclerView;
        int height = recyclerView.getHeight();
        int i2 = this.mTouchRegionTopOffset;
        this.mTopBoundFrom = i2;
        int i3 = this.mAutoScrollDistance;
        this.mTopBoundTo = i2 + i3;
        int i4 = this.mTouchRegionBottomOffset;
        this.mBottomBoundFrom = (height + i4) - i3;
        this.mBottomBoundTo = height + i4;
        return true;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
    public void onRequestDisallowInterceptTouchEvent(boolean z2) {
    }

    @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
    public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
        if (!this.isActive) {
            reset();
            return;
        }
        int action = motionEvent.getAction();
        if (action != 1) {
            if (action == 2) {
                if (!this.mInTopSpot && !this.mInBottomSpot) {
                    changeSelectedRange(recyclerView, motionEvent);
                }
                processAutoScroll(motionEvent);
                return;
            }
            if (action != 3 && action != 6) {
                return;
            }
        }
        reset();
    }

    public void setActive(boolean z2) {
        this.isActive = z2;
    }

    public SlideSelectTouchListener setRecyclerViewHeaderCount(int i2) {
        this.mHeaderViewCount = i2;
        return this;
    }

    public void startAutoScroll() {
        RecyclerView recyclerView = this.mRecyclerView;
        if (recyclerView == null) {
            return;
        }
        initScroller(recyclerView.getContext());
        if (this.mScroller.isFinished()) {
            this.mRecyclerView.removeCallbacks(this.mScrollRunnable);
            OverScroller overScroller = this.mScroller;
            overScroller.startScroll(0, overScroller.getCurrY(), 0, 5000, 100000);
            ViewCompat.postOnAnimation(this.mRecyclerView, this.mScrollRunnable);
        }
    }

    public void startSlideSelection(int i2) {
        setActive(true);
        this.mStart = i2;
        this.mEnd = i2;
        this.mLastStart = i2;
        this.mLastEnd = i2;
        OnSlideSelectListener onSlideSelectListener = this.mSelectListener;
        if (onSlideSelectListener == null || !(onSlideSelectListener instanceof OnAdvancedSlideSelectListener)) {
            return;
        }
        ((OnAdvancedSlideSelectListener) onSlideSelectListener).onSelectionStarted(i2);
    }

    public void stopAutoScroll() {
        try {
            OverScroller overScroller = this.mScroller;
            if (overScroller == null || overScroller.isFinished()) {
                return;
            }
            this.mRecyclerView.removeCallbacks(this.mScrollRunnable);
            this.mScroller.abortAnimation();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public SlideSelectTouchListener withBottomOffset(int i2) {
        this.mTouchRegionBottomOffset = i2;
        return this;
    }

    public SlideSelectTouchListener withMaxScrollDistance(int i2) {
        this.mMaxScrollDistance = i2;
        return this;
    }

    public SlideSelectTouchListener withScrollAboveTopRegion(boolean z2) {
        this.mScrollAboveTopRegion = z2;
        return this;
    }

    public SlideSelectTouchListener withScrollBelowTopRegion(boolean z2) {
        this.mScrollBelowTopRegion = z2;
        return this;
    }

    public SlideSelectTouchListener withSelectListener(OnSlideSelectListener onSlideSelectListener) {
        this.mSelectListener = onSlideSelectListener;
        return this;
    }

    public SlideSelectTouchListener withTopOffset(int i2) {
        this.mTouchRegionTopOffset = i2;
        return this;
    }

    public SlideSelectTouchListener withTouchRegion(int i2) {
        this.mAutoScrollDistance = i2;
        return this;
    }

    private void changeSelectedRange(RecyclerView recyclerView, float f2, float f3) {
        int childAdapterPosition;
        View viewFindChildViewUnder = recyclerView.findChildViewUnder(f2, f3);
        if (viewFindChildViewUnder == null || (childAdapterPosition = recyclerView.getChildAdapterPosition(viewFindChildViewUnder) - this.mHeaderViewCount) == -1 || this.mEnd == childAdapterPosition) {
            return;
        }
        this.mEnd = childAdapterPosition;
        notifySelectRangeChange();
    }
}
