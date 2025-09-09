package androidx.viewpager2.widget;

import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes2.dex */
final class FakeDrag {
    private int mActualDraggedDistance;
    private long mFakeDragBeginTime;
    private int mMaximumVelocity;
    private final RecyclerView mRecyclerView;
    private float mRequestedDragDistance;
    private final ScrollEventAdapter mScrollEventAdapter;
    private VelocityTracker mVelocityTracker;
    private final ViewPager2 mViewPager;

    FakeDrag(ViewPager2 viewPager2, ScrollEventAdapter scrollEventAdapter, RecyclerView recyclerView) {
        this.mViewPager = viewPager2;
        this.mScrollEventAdapter = scrollEventAdapter;
        this.mRecyclerView = recyclerView;
    }

    private void addFakeMotionEvent(long j2, int i2, float f2, float f3) {
        MotionEvent motionEventObtain = MotionEvent.obtain(this.mFakeDragBeginTime, j2, i2, f2, f3, 0);
        this.mVelocityTracker.addMovement(motionEventObtain);
        motionEventObtain.recycle();
    }

    private void beginFakeVelocityTracker() {
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker != null) {
            velocityTracker.clear();
        } else {
            this.mVelocityTracker = VelocityTracker.obtain();
            this.mMaximumVelocity = ViewConfiguration.get(this.mViewPager.getContext()).getScaledMaximumFlingVelocity();
        }
    }

    boolean a() {
        if (this.mScrollEventAdapter.c()) {
            return false;
        }
        this.mActualDraggedDistance = 0;
        this.mRequestedDragDistance = 0;
        this.mFakeDragBeginTime = SystemClock.uptimeMillis();
        beginFakeVelocityTracker();
        this.mScrollEventAdapter.f();
        if (!this.mScrollEventAdapter.e()) {
            this.mRecyclerView.stopScroll();
        }
        addFakeMotionEvent(this.mFakeDragBeginTime, 0, 0.0f, 0.0f);
        return true;
    }

    boolean b() {
        if (!this.mScrollEventAdapter.d()) {
            return false;
        }
        this.mScrollEventAdapter.h();
        VelocityTracker velocityTracker = this.mVelocityTracker;
        velocityTracker.computeCurrentVelocity(1000, this.mMaximumVelocity);
        if (this.mRecyclerView.fling((int) velocityTracker.getXVelocity(), (int) velocityTracker.getYVelocity())) {
            return true;
        }
        this.mViewPager.c();
        return true;
    }

    boolean c(float f2) {
        if (!this.mScrollEventAdapter.d()) {
            return false;
        }
        float f3 = this.mRequestedDragDistance - f2;
        this.mRequestedDragDistance = f3;
        int iRound = Math.round(f3 - this.mActualDraggedDistance);
        this.mActualDraggedDistance += iRound;
        long jUptimeMillis = SystemClock.uptimeMillis();
        boolean z2 = this.mViewPager.getOrientation() == 0;
        int i2 = z2 ? iRound : 0;
        int i3 = z2 ? 0 : iRound;
        float f4 = z2 ? this.mRequestedDragDistance : 0.0f;
        float f5 = z2 ? 0.0f : this.mRequestedDragDistance;
        this.mRecyclerView.scrollBy(i2, i3);
        addFakeMotionEvent(jUptimeMillis, 2, f4, f5);
        return true;
    }

    boolean d() {
        return this.mScrollEventAdapter.d();
    }
}
