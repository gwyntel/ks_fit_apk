package androidx.recyclerview.widget;

import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes2.dex */
public abstract class SnapHelper extends RecyclerView.OnFlingListener {

    /* renamed from: a, reason: collision with root package name */
    RecyclerView f6101a;
    private Scroller mGravityScroller;
    private final RecyclerView.OnScrollListener mScrollListener = new RecyclerView.OnScrollListener() { // from class: androidx.recyclerview.widget.SnapHelper.1

        /* renamed from: a, reason: collision with root package name */
        boolean f6102a = false;

        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public void onScrollStateChanged(RecyclerView recyclerView, int i2) {
            super.onScrollStateChanged(recyclerView, i2);
            if (i2 == 0 && this.f6102a) {
                this.f6102a = false;
                SnapHelper.this.c();
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public void onScrolled(RecyclerView recyclerView, int i2, int i3) {
            if (i2 == 0 && i3 == 0) {
                return;
            }
            this.f6102a = true;
        }
    };

    private void destroyCallbacks() {
        this.f6101a.removeOnScrollListener(this.mScrollListener);
        this.f6101a.setOnFlingListener(null);
    }

    private void setupCallbacks() throws IllegalStateException {
        if (this.f6101a.getOnFlingListener() != null) {
            throw new IllegalStateException("An instance of OnFlingListener already set.");
        }
        this.f6101a.addOnScrollListener(this.mScrollListener);
        this.f6101a.setOnFlingListener(this);
    }

    private boolean snapFromFling(@NonNull RecyclerView.LayoutManager layoutManager, int i2, int i3) {
        RecyclerView.SmoothScroller smoothScrollerA;
        int iFindTargetSnapPosition;
        if (!(layoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider) || (smoothScrollerA = a(layoutManager)) == null || (iFindTargetSnapPosition = findTargetSnapPosition(layoutManager, i2, i3)) == -1) {
            return false;
        }
        smoothScrollerA.setTargetPosition(iFindTargetSnapPosition);
        layoutManager.startSmoothScroll(smoothScrollerA);
        return true;
    }

    protected RecyclerView.SmoothScroller a(RecyclerView.LayoutManager layoutManager) {
        return b(layoutManager);
    }

    public void attachToRecyclerView(@Nullable RecyclerView recyclerView) throws IllegalStateException {
        RecyclerView recyclerView2 = this.f6101a;
        if (recyclerView2 == recyclerView) {
            return;
        }
        if (recyclerView2 != null) {
            destroyCallbacks();
        }
        this.f6101a = recyclerView;
        if (recyclerView != null) {
            setupCallbacks();
            this.mGravityScroller = new Scroller(this.f6101a.getContext(), new DecelerateInterpolator());
            c();
        }
    }

    protected LinearSmoothScroller b(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider) {
            return new LinearSmoothScroller(this.f6101a.getContext()) { // from class: androidx.recyclerview.widget.SnapHelper.2
                @Override // androidx.recyclerview.widget.LinearSmoothScroller, androidx.recyclerview.widget.RecyclerView.SmoothScroller
                protected void g(View view, RecyclerView.State state, RecyclerView.SmoothScroller.Action action) {
                    SnapHelper snapHelper = SnapHelper.this;
                    RecyclerView recyclerView = snapHelper.f6101a;
                    if (recyclerView == null) {
                        return;
                    }
                    int[] iArrCalculateDistanceToFinalSnap = snapHelper.calculateDistanceToFinalSnap(recyclerView.getLayoutManager(), view);
                    int i2 = iArrCalculateDistanceToFinalSnap[0];
                    int i3 = iArrCalculateDistanceToFinalSnap[1];
                    int iK = k(Math.max(Math.abs(i2), Math.abs(i3)));
                    if (iK > 0) {
                        action.update(i2, i3, iK, this.f5993b);
                    }
                }

                @Override // androidx.recyclerview.widget.LinearSmoothScroller
                protected float j(DisplayMetrics displayMetrics) {
                    return 100.0f / displayMetrics.densityDpi;
                }
            };
        }
        return null;
    }

    void c() {
        RecyclerView.LayoutManager layoutManager;
        View viewFindSnapView;
        RecyclerView recyclerView = this.f6101a;
        if (recyclerView == null || (layoutManager = recyclerView.getLayoutManager()) == null || (viewFindSnapView = findSnapView(layoutManager)) == null) {
            return;
        }
        int[] iArrCalculateDistanceToFinalSnap = calculateDistanceToFinalSnap(layoutManager, viewFindSnapView);
        int i2 = iArrCalculateDistanceToFinalSnap[0];
        if (i2 == 0 && iArrCalculateDistanceToFinalSnap[1] == 0) {
            return;
        }
        this.f6101a.smoothScrollBy(i2, iArrCalculateDistanceToFinalSnap[1]);
    }

    @Nullable
    public abstract int[] calculateDistanceToFinalSnap(@NonNull RecyclerView.LayoutManager layoutManager, @NonNull View view);

    public int[] calculateScrollDistance(int i2, int i3) {
        this.mGravityScroller.fling(0, 0, i2, i3, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE);
        return new int[]{this.mGravityScroller.getFinalX(), this.mGravityScroller.getFinalY()};
    }

    @Nullable
    public abstract View findSnapView(RecyclerView.LayoutManager layoutManager);

    public abstract int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int i2, int i3);

    @Override // androidx.recyclerview.widget.RecyclerView.OnFlingListener
    public boolean onFling(int i2, int i3) {
        RecyclerView.LayoutManager layoutManager = this.f6101a.getLayoutManager();
        if (layoutManager == null || this.f6101a.getAdapter() == null) {
            return false;
        }
        int minFlingVelocity = this.f6101a.getMinFlingVelocity();
        return (Math.abs(i3) > minFlingVelocity || Math.abs(i2) > minFlingVelocity) && snapFromFling(layoutManager, i2, i3);
    }
}
