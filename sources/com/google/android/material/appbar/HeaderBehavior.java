package com.google.android.material.appbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.OverScroller;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.math.MathUtils;
import androidx.core.view.ViewCompat;

/* loaded from: classes3.dex */
abstract class HeaderBehavior<V extends View> extends ViewOffsetBehavior<V> {
    private static final int INVALID_POINTER = -1;

    /* renamed from: a, reason: collision with root package name */
    OverScroller f13378a;
    private int activePointerId;
    private Runnable flingRunnable;
    private boolean isBeingDragged;
    private int lastMotionY;
    private int touchSlop;
    private VelocityTracker velocityTracker;

    private class FlingRunnable implements Runnable {
        private final V layout;
        private final CoordinatorLayout parent;

        /* JADX WARN: Multi-variable type inference failed */
        FlingRunnable(CoordinatorLayout coordinatorLayout, View view) {
            this.parent = coordinatorLayout;
            this.layout = view;
        }

        @Override // java.lang.Runnable
        public void run() {
            OverScroller overScroller;
            if (this.layout == null || (overScroller = HeaderBehavior.this.f13378a) == null) {
                return;
            }
            if (!overScroller.computeScrollOffset()) {
                HeaderBehavior.this.g(this.parent, this.layout);
                return;
            }
            HeaderBehavior headerBehavior = HeaderBehavior.this;
            headerBehavior.i(this.parent, this.layout, headerBehavior.f13378a.getCurrY());
            ViewCompat.postOnAnimation(this.layout, this);
        }
    }

    public HeaderBehavior() {
        this.activePointerId = -1;
        this.touchSlop = -1;
    }

    private void ensureVelocityTracker() {
        if (this.velocityTracker == null) {
            this.velocityTracker = VelocityTracker.obtain();
        }
    }

    boolean b(View view) {
        return false;
    }

    final boolean c(CoordinatorLayout coordinatorLayout, View view, int i2, int i3, float f2) {
        Runnable runnable = this.flingRunnable;
        if (runnable != null) {
            view.removeCallbacks(runnable);
            this.flingRunnable = null;
        }
        if (this.f13378a == null) {
            this.f13378a = new OverScroller(view.getContext());
        }
        this.f13378a.fling(0, getTopAndBottomOffset(), 0, Math.round(f2), 0, 0, i2, i3);
        if (!this.f13378a.computeScrollOffset()) {
            g(coordinatorLayout, view);
            return false;
        }
        FlingRunnable flingRunnable = new FlingRunnable(coordinatorLayout, view);
        this.flingRunnable = flingRunnable;
        ViewCompat.postOnAnimation(view, flingRunnable);
        return true;
    }

    int d(View view) {
        return -view.getHeight();
    }

    int e(View view) {
        return view.getHeight();
    }

    int f() {
        return getTopAndBottomOffset();
    }

    void g(CoordinatorLayout coordinatorLayout, View view) {
    }

    final int h(CoordinatorLayout coordinatorLayout, View view, int i2, int i3, int i4) {
        return j(coordinatorLayout, view, f() - i2, i3, i4);
    }

    int i(CoordinatorLayout coordinatorLayout, View view, int i2) {
        return j(coordinatorLayout, view, i2, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    int j(CoordinatorLayout coordinatorLayout, View view, int i2, int i3, int i4) {
        int iClamp;
        int topAndBottomOffset = getTopAndBottomOffset();
        if (i3 == 0 || topAndBottomOffset < i3 || topAndBottomOffset > i4 || topAndBottomOffset == (iClamp = MathUtils.clamp(i2, i3, i4))) {
            return 0;
        }
        setTopAndBottomOffset(iClamp);
        return topAndBottomOffset - iClamp;
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0051  */
    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onInterceptTouchEvent(androidx.coordinatorlayout.widget.CoordinatorLayout r5, V r6, android.view.MotionEvent r7) {
        /*
            r4 = this;
            int r0 = r4.touchSlop
            if (r0 >= 0) goto L12
            android.content.Context r0 = r5.getContext()
            android.view.ViewConfiguration r0 = android.view.ViewConfiguration.get(r0)
            int r0 = r0.getScaledTouchSlop()
            r4.touchSlop = r0
        L12:
            int r0 = r7.getAction()
            r1 = 2
            r2 = 1
            if (r0 != r1) goto L1f
            boolean r0 = r4.isBeingDragged
            if (r0 == 0) goto L1f
            return r2
        L1f:
            int r0 = r7.getActionMasked()
            r3 = 0
            if (r0 == 0) goto L60
            r5 = -1
            if (r0 == r2) goto L51
            if (r0 == r1) goto L2f
            r6 = 3
            if (r0 == r6) goto L51
            goto L83
        L2f:
            int r6 = r4.activePointerId
            if (r6 != r5) goto L34
            goto L83
        L34:
            int r6 = r7.findPointerIndex(r6)
            if (r6 != r5) goto L3b
            goto L83
        L3b:
            float r5 = r7.getY(r6)
            int r5 = (int) r5
            int r6 = r4.lastMotionY
            int r6 = r5 - r6
            int r6 = java.lang.Math.abs(r6)
            int r0 = r4.touchSlop
            if (r6 <= r0) goto L83
            r4.isBeingDragged = r2
            r4.lastMotionY = r5
            goto L83
        L51:
            r4.isBeingDragged = r3
            r4.activePointerId = r5
            android.view.VelocityTracker r5 = r4.velocityTracker
            if (r5 == 0) goto L83
            r5.recycle()
            r5 = 0
            r4.velocityTracker = r5
            goto L83
        L60:
            r4.isBeingDragged = r3
            float r0 = r7.getX()
            int r0 = (int) r0
            float r1 = r7.getY()
            int r1 = (int) r1
            boolean r2 = r4.b(r6)
            if (r2 == 0) goto L83
            boolean r5 = r5.isPointInChildBounds(r6, r0, r1)
            if (r5 == 0) goto L83
            r4.lastMotionY = r1
            int r5 = r7.getPointerId(r3)
            r4.activePointerId = r5
            r4.ensureVelocityTracker()
        L83:
            android.view.VelocityTracker r5 = r4.velocityTracker
            if (r5 == 0) goto L8a
            r5.addMovement(r7)
        L8a:
            boolean r5 = r4.isBeingDragged
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.appbar.HeaderBehavior.onInterceptTouchEvent(androidx.coordinatorlayout.widget.CoordinatorLayout, android.view.View, android.view.MotionEvent):boolean");
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onTouchEvent(CoordinatorLayout coordinatorLayout, V v2, MotionEvent motionEvent) {
        if (this.touchSlop < 0) {
            this.touchSlop = ViewConfiguration.get(coordinatorLayout.getContext()).getScaledTouchSlop();
        }
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked != 0) {
            if (actionMasked == 1) {
                VelocityTracker velocityTracker = this.velocityTracker;
                if (velocityTracker != null) {
                    velocityTracker.addMovement(motionEvent);
                    this.velocityTracker.computeCurrentVelocity(1000);
                    c(coordinatorLayout, v2, -e(v2), 0, this.velocityTracker.getYVelocity(this.activePointerId));
                }
            } else if (actionMasked == 2) {
                int iFindPointerIndex = motionEvent.findPointerIndex(this.activePointerId);
                if (iFindPointerIndex == -1) {
                    return false;
                }
                int y2 = (int) motionEvent.getY(iFindPointerIndex);
                int i2 = this.lastMotionY - y2;
                if (!this.isBeingDragged) {
                    int iAbs = Math.abs(i2);
                    int i3 = this.touchSlop;
                    if (iAbs > i3) {
                        this.isBeingDragged = true;
                        i2 = i2 > 0 ? i2 - i3 : i2 + i3;
                    }
                }
                int i4 = i2;
                if (this.isBeingDragged) {
                    this.lastMotionY = y2;
                    h(coordinatorLayout, v2, i4, d(v2), 0);
                }
            } else if (actionMasked == 3) {
            }
            this.isBeingDragged = false;
            this.activePointerId = -1;
            VelocityTracker velocityTracker2 = this.velocityTracker;
            if (velocityTracker2 != null) {
                velocityTracker2.recycle();
                this.velocityTracker = null;
            }
        } else {
            int x2 = (int) motionEvent.getX();
            int y3 = (int) motionEvent.getY();
            if (!coordinatorLayout.isPointInChildBounds(v2, x2, y3) || !b(v2)) {
                return false;
            }
            this.lastMotionY = y3;
            this.activePointerId = motionEvent.getPointerId(0);
            ensureVelocityTracker();
        }
        VelocityTracker velocityTracker3 = this.velocityTracker;
        if (velocityTracker3 != null) {
            velocityTracker3.addMovement(motionEvent);
        }
        return true;
    }

    public HeaderBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.activePointerId = -1;
        this.touchSlop = -1;
    }
}
