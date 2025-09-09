package com.google.android.material.bottomsheet;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.math.MathUtils;
import androidx.core.view.ViewCompat;
import androidx.customview.view.AbsSavedState;
import androidx.customview.widget.ViewDragHelper;
import com.google.android.material.R;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public class BottomSheetBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {
    private static final float HIDE_FRICTION = 0.1f;
    private static final float HIDE_THRESHOLD = 0.5f;
    public static final int PEEK_HEIGHT_AUTO = -1;
    public static final int STATE_COLLAPSED = 4;
    public static final int STATE_DRAGGING = 1;
    public static final int STATE_EXPANDED = 3;
    public static final int STATE_HALF_EXPANDED = 6;
    public static final int STATE_HIDDEN = 5;
    public static final int STATE_SETTLING = 2;

    /* renamed from: a, reason: collision with root package name */
    int f13408a;

    /* renamed from: b, reason: collision with root package name */
    int f13409b;

    /* renamed from: c, reason: collision with root package name */
    int f13410c;
    private BottomSheetCallback callback;

    /* renamed from: d, reason: collision with root package name */
    boolean f13411d;
    private final ViewDragHelper.Callback dragCallback;

    /* renamed from: e, reason: collision with root package name */
    int f13412e;

    /* renamed from: f, reason: collision with root package name */
    ViewDragHelper f13413f;
    private boolean fitToContents;

    /* renamed from: g, reason: collision with root package name */
    int f13414g;

    /* renamed from: h, reason: collision with root package name */
    WeakReference f13415h;

    /* renamed from: i, reason: collision with root package name */
    WeakReference f13416i;
    private boolean ignoreEvents;
    private Map<View, Integer> importantForAccessibilityMap;
    private int initialY;

    /* renamed from: j, reason: collision with root package name */
    int f13417j;

    /* renamed from: k, reason: collision with root package name */
    boolean f13418k;
    private int lastNestedScrollDy;
    private int lastPeekHeight;
    private float maximumVelocity;
    private boolean nestedScrolled;
    private int peekHeight;
    private boolean peekHeightAuto;
    private int peekHeightMin;
    private boolean skipCollapsed;
    private VelocityTracker velocityTracker;

    public static abstract class BottomSheetCallback {
        public abstract void onSlide(@NonNull View view, float f2);

        public abstract void onStateChanged(@NonNull View view, int i2);
    }

    protected static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>() { // from class: com.google.android.material.bottomsheet.BottomSheetBehavior.SavedState.1
            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int i2) {
                return new SavedState[i2];
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.ClassLoaderCreator
            public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel, (ClassLoader) null);
            }
        };

        /* renamed from: a, reason: collision with root package name */
        final int f13423a;

        public SavedState(Parcel parcel) {
            this(parcel, (ClassLoader) null);
        }

        @Override // androidx.customview.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i2) {
            super.writeToParcel(parcel, i2);
            parcel.writeInt(this.f13423a);
        }

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.f13423a = parcel.readInt();
        }

        public SavedState(Parcelable parcelable, int i2) {
            super(parcelable);
            this.f13423a = i2;
        }
    }

    private class SettleRunnable implements Runnable {
        private final int targetState;
        private final View view;

        SettleRunnable(View view, int i2) {
            this.view = view;
            this.targetState = i2;
        }

        @Override // java.lang.Runnable
        public void run() {
            ViewDragHelper viewDragHelper = BottomSheetBehavior.this.f13413f;
            if (viewDragHelper == null || !viewDragHelper.continueSettling(true)) {
                BottomSheetBehavior.this.e(this.targetState);
            } else {
                ViewCompat.postOnAnimation(this.view, this);
            }
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface State {
    }

    public BottomSheetBehavior() {
        this.fitToContents = true;
        this.f13412e = 4;
        this.dragCallback = new ViewDragHelper.Callback() { // from class: com.google.android.material.bottomsheet.BottomSheetBehavior.2
            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public int clampViewPositionHorizontal(@NonNull View view, int i2, int i3) {
                return view.getLeft();
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public int clampViewPositionVertical(@NonNull View view, int i2, int i3) {
                int expandedOffset = BottomSheetBehavior.this.getExpandedOffset();
                BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.this;
                return MathUtils.clamp(i2, expandedOffset, bottomSheetBehavior.f13411d ? bottomSheetBehavior.f13414g : bottomSheetBehavior.f13410c);
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public int getViewVerticalDragRange(@NonNull View view) {
                BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.this;
                return bottomSheetBehavior.f13411d ? bottomSheetBehavior.f13414g : bottomSheetBehavior.f13410c;
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public void onViewDragStateChanged(int i2) {
                if (i2 == 1) {
                    BottomSheetBehavior.this.e(1);
                }
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public void onViewPositionChanged(@NonNull View view, int i2, int i3, int i4, int i5) {
                BottomSheetBehavior.this.c(i3);
            }

            /* JADX WARN: Removed duplicated region for block: B:10:0x0022 A[PHI: r2
              0x0022: PHI (r2v7 int) = (r2v0 int), (r2v6 int), (r2v0 int) binds: [B:37:0x00a2, B:31:0x0089, B:8:0x001e] A[DONT_GENERATE, DONT_INLINE]] */
            @Override // androidx.customview.widget.ViewDragHelper.Callback
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public void onViewReleased(@androidx.annotation.NonNull android.view.View r7, float r8, float r9) {
                /*
                    Method dump skipped, instructions count: 237
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.bottomsheet.BottomSheetBehavior.AnonymousClass2.onViewReleased(android.view.View, float, float):void");
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public boolean tryCaptureView(@NonNull View view, int i2) {
                WeakReference weakReference;
                View view2;
                BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.this;
                int i3 = bottomSheetBehavior.f13412e;
                if (i3 == 1 || bottomSheetBehavior.f13418k) {
                    return false;
                }
                return ((i3 == 3 && bottomSheetBehavior.f13417j == i2 && (view2 = (View) bottomSheetBehavior.f13416i.get()) != null && view2.canScrollVertically(-1)) || (weakReference = BottomSheetBehavior.this.f13415h) == null || weakReference.get() != view) ? false : true;
            }
        };
    }

    private void calculateCollapsedOffset() {
        if (this.fitToContents) {
            this.f13410c = Math.max(this.f13414g - this.lastPeekHeight, this.f13408a);
        } else {
            this.f13410c = this.f13414g - this.lastPeekHeight;
        }
    }

    public static <V extends View> BottomSheetBehavior<V> from(V v2) {
        ViewGroup.LayoutParams layoutParams = v2.getLayoutParams();
        if (!(layoutParams instanceof CoordinatorLayout.LayoutParams)) {
            throw new IllegalArgumentException("The view is not a child of CoordinatorLayout");
        }
        CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) layoutParams).getBehavior();
        if (behavior instanceof BottomSheetBehavior) {
            return (BottomSheetBehavior) behavior;
        }
        throw new IllegalArgumentException("The view is not associated with BottomSheetBehavior");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getExpandedOffset() {
        if (this.fitToContents) {
            return this.f13408a;
        }
        return 0;
    }

    private float getYVelocity() {
        VelocityTracker velocityTracker = this.velocityTracker;
        if (velocityTracker == null) {
            return 0.0f;
        }
        velocityTracker.computeCurrentVelocity(1000, this.maximumVelocity);
        return this.velocityTracker.getYVelocity(this.f13417j);
    }

    private void reset() {
        this.f13417j = -1;
        VelocityTracker velocityTracker = this.velocityTracker;
        if (velocityTracker != null) {
            velocityTracker.recycle();
            this.velocityTracker = null;
        }
    }

    private void updateImportantForAccessibility(boolean z2) {
        WeakReference weakReference = this.f13415h;
        if (weakReference == null) {
            return;
        }
        ViewParent parent = ((View) weakReference.get()).getParent();
        if (parent instanceof CoordinatorLayout) {
            CoordinatorLayout coordinatorLayout = (CoordinatorLayout) parent;
            int childCount = coordinatorLayout.getChildCount();
            if (z2) {
                if (this.importantForAccessibilityMap != null) {
                    return;
                } else {
                    this.importantForAccessibilityMap = new HashMap(childCount);
                }
            }
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = coordinatorLayout.getChildAt(i2);
                if (childAt != this.f13415h.get()) {
                    if (z2) {
                        this.importantForAccessibilityMap.put(childAt, Integer.valueOf(childAt.getImportantForAccessibility()));
                        ViewCompat.setImportantForAccessibility(childAt, 4);
                    } else {
                        Map<View, Integer> map = this.importantForAccessibilityMap;
                        if (map != null && map.containsKey(childAt)) {
                            ViewCompat.setImportantForAccessibility(childAt, this.importantForAccessibilityMap.get(childAt).intValue());
                        }
                    }
                }
            }
            if (z2) {
                return;
            }
            this.importantForAccessibilityMap = null;
        }
    }

    void c(int i2) {
        BottomSheetCallback bottomSheetCallback;
        View view = (View) this.f13415h.get();
        if (view == null || (bottomSheetCallback = this.callback) == null) {
            return;
        }
        if (i2 > this.f13410c) {
            bottomSheetCallback.onSlide(view, (r2 - i2) / (this.f13414g - r2));
        } else {
            bottomSheetCallback.onSlide(view, (r2 - i2) / (r2 - getExpandedOffset()));
        }
    }

    View d(View view) {
        if (ViewCompat.isNestedScrollingEnabled(view)) {
            return view;
        }
        if (!(view instanceof ViewGroup)) {
            return null;
        }
        ViewGroup viewGroup = (ViewGroup) view;
        int childCount = viewGroup.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View viewD = d(viewGroup.getChildAt(i2));
            if (viewD != null) {
                return viewD;
            }
        }
        return null;
    }

    void e(int i2) {
        BottomSheetCallback bottomSheetCallback;
        if (this.f13412e == i2) {
            return;
        }
        this.f13412e = i2;
        if (i2 == 6 || i2 == 3) {
            updateImportantForAccessibility(true);
        } else if (i2 == 5 || i2 == 4) {
            updateImportantForAccessibility(false);
        }
        View view = (View) this.f13415h.get();
        if (view == null || (bottomSheetCallback = this.callback) == null) {
            return;
        }
        bottomSheetCallback.onStateChanged(view, i2);
    }

    boolean f(View view, float f2) {
        if (this.skipCollapsed) {
            return true;
        }
        return view.getTop() >= this.f13410c && Math.abs((((float) view.getTop()) + (f2 * 0.1f)) - ((float) this.f13410c)) / ((float) this.peekHeight) > 0.5f;
    }

    void g(View view, int i2) {
        int expandedOffset;
        int i3;
        if (i2 == 4) {
            expandedOffset = this.f13410c;
        } else if (i2 == 6) {
            expandedOffset = this.f13409b;
            if (this.fitToContents && expandedOffset <= (i3 = this.f13408a)) {
                i2 = 3;
                expandedOffset = i3;
            }
        } else if (i2 == 3) {
            expandedOffset = getExpandedOffset();
        } else {
            if (!this.f13411d || i2 != 5) {
                throw new IllegalArgumentException("Illegal state argument: " + i2);
            }
            expandedOffset = this.f13414g;
        }
        if (!this.f13413f.smoothSlideViewTo(view, view.getLeft(), expandedOffset)) {
            e(i2);
        } else {
            e(2);
            ViewCompat.postOnAnimation(view, new SettleRunnable(view, i2));
        }
    }

    public final int getPeekHeight() {
        if (this.peekHeightAuto) {
            return -1;
        }
        return this.peekHeight;
    }

    public boolean getSkipCollapsed() {
        return this.skipCollapsed;
    }

    public final int getState() {
        return this.f13412e;
    }

    public boolean isFitToContents() {
        return this.fitToContents;
    }

    public boolean isHideable() {
        return this.f13411d;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onInterceptTouchEvent(CoordinatorLayout coordinatorLayout, V v2, MotionEvent motionEvent) {
        ViewDragHelper viewDragHelper;
        if (!v2.isShown()) {
            this.ignoreEvents = true;
            return false;
        }
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            reset();
        }
        if (this.velocityTracker == null) {
            this.velocityTracker = VelocityTracker.obtain();
        }
        this.velocityTracker.addMovement(motionEvent);
        if (actionMasked == 0) {
            int x2 = (int) motionEvent.getX();
            this.initialY = (int) motionEvent.getY();
            WeakReference weakReference = this.f13416i;
            View view = weakReference != null ? (View) weakReference.get() : null;
            if (view != null && coordinatorLayout.isPointInChildBounds(view, x2, this.initialY)) {
                this.f13417j = motionEvent.getPointerId(motionEvent.getActionIndex());
                this.f13418k = true;
            }
            this.ignoreEvents = this.f13417j == -1 && !coordinatorLayout.isPointInChildBounds(v2, x2, this.initialY);
        } else if (actionMasked == 1 || actionMasked == 3) {
            this.f13418k = false;
            this.f13417j = -1;
            if (this.ignoreEvents) {
                this.ignoreEvents = false;
                return false;
            }
        }
        if (!this.ignoreEvents && (viewDragHelper = this.f13413f) != null && viewDragHelper.shouldInterceptTouchEvent(motionEvent)) {
            return true;
        }
        WeakReference weakReference2 = this.f13416i;
        View view2 = weakReference2 != null ? (View) weakReference2.get() : null;
        return (actionMasked != 2 || view2 == null || this.ignoreEvents || this.f13412e == 1 || coordinatorLayout.isPointInChildBounds(view2, (int) motionEvent.getX(), (int) motionEvent.getY()) || this.f13413f == null || Math.abs(((float) this.initialY) - motionEvent.getY()) <= ((float) this.f13413f.getTouchSlop())) ? false : true;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onLayoutChild(CoordinatorLayout coordinatorLayout, V v2, int i2) {
        if (ViewCompat.getFitsSystemWindows(coordinatorLayout) && !ViewCompat.getFitsSystemWindows(v2)) {
            v2.setFitsSystemWindows(true);
        }
        int top2 = v2.getTop();
        coordinatorLayout.onLayoutChild(v2, i2);
        this.f13414g = coordinatorLayout.getHeight();
        if (this.peekHeightAuto) {
            if (this.peekHeightMin == 0) {
                this.peekHeightMin = coordinatorLayout.getResources().getDimensionPixelSize(R.dimen.design_bottom_sheet_peek_height_min);
            }
            this.lastPeekHeight = Math.max(this.peekHeightMin, this.f13414g - ((coordinatorLayout.getWidth() * 9) / 16));
        } else {
            this.lastPeekHeight = this.peekHeight;
        }
        this.f13408a = Math.max(0, this.f13414g - v2.getHeight());
        this.f13409b = this.f13414g / 2;
        calculateCollapsedOffset();
        int i3 = this.f13412e;
        if (i3 == 3) {
            ViewCompat.offsetTopAndBottom(v2, getExpandedOffset());
        } else if (i3 == 6) {
            ViewCompat.offsetTopAndBottom(v2, this.f13409b);
        } else if (this.f13411d && i3 == 5) {
            ViewCompat.offsetTopAndBottom(v2, this.f13414g);
        } else if (i3 == 4) {
            ViewCompat.offsetTopAndBottom(v2, this.f13410c);
        } else if (i3 == 1 || i3 == 2) {
            ViewCompat.offsetTopAndBottom(v2, top2 - v2.getTop());
        }
        if (this.f13413f == null) {
            this.f13413f = ViewDragHelper.create(coordinatorLayout, this.dragCallback);
        }
        this.f13415h = new WeakReference(v2);
        this.f13416i = new WeakReference(d(v2));
        return true;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onNestedPreFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v2, @NonNull View view, float f2, float f3) {
        return view == this.f13416i.get() && (this.f13412e != 3 || super.onNestedPreFling(coordinatorLayout, v2, view, f2, f3));
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v2, @NonNull View view, int i2, int i3, @NonNull int[] iArr, int i4) {
        if (i4 != 1 && view == ((View) this.f13416i.get())) {
            int top2 = v2.getTop();
            int i5 = top2 - i3;
            if (i3 > 0) {
                if (i5 < getExpandedOffset()) {
                    int expandedOffset = top2 - getExpandedOffset();
                    iArr[1] = expandedOffset;
                    ViewCompat.offsetTopAndBottom(v2, -expandedOffset);
                    e(3);
                } else {
                    iArr[1] = i3;
                    ViewCompat.offsetTopAndBottom(v2, -i3);
                    e(1);
                }
            } else if (i3 < 0 && !view.canScrollVertically(-1)) {
                int i6 = this.f13410c;
                if (i5 <= i6 || this.f13411d) {
                    iArr[1] = i3;
                    ViewCompat.offsetTopAndBottom(v2, -i3);
                    e(1);
                } else {
                    int i7 = top2 - i6;
                    iArr[1] = i7;
                    ViewCompat.offsetTopAndBottom(v2, -i7);
                    e(4);
                }
            }
            c(v2.getTop());
            this.lastNestedScrollDy = i3;
            this.nestedScrolled = true;
        }
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public void onRestoreInstanceState(CoordinatorLayout coordinatorLayout, V v2, Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(coordinatorLayout, v2, savedState.getSuperState());
        int i2 = savedState.f13423a;
        if (i2 == 1 || i2 == 2) {
            this.f13412e = 4;
        } else {
            this.f13412e = i2;
        }
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public Parcelable onSaveInstanceState(CoordinatorLayout coordinatorLayout, V v2) {
        return new SavedState(super.onSaveInstanceState(coordinatorLayout, v2), this.f13412e);
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v2, @NonNull View view, @NonNull View view2, int i2, int i3) {
        this.lastNestedScrollDy = 0;
        this.nestedScrolled = false;
        return (i2 & 2) != 0;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public void onStopNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v2, @NonNull View view, int i2) {
        int expandedOffset;
        int i3 = 3;
        if (v2.getTop() == getExpandedOffset()) {
            e(3);
            return;
        }
        if (view == this.f13416i.get() && this.nestedScrolled) {
            if (this.lastNestedScrollDy > 0) {
                expandedOffset = getExpandedOffset();
            } else if (this.f13411d && f(v2, getYVelocity())) {
                expandedOffset = this.f13414g;
                i3 = 5;
            } else {
                if (this.lastNestedScrollDy == 0) {
                    int top2 = v2.getTop();
                    if (!this.fitToContents) {
                        int i4 = this.f13409b;
                        if (top2 < i4) {
                            if (top2 < Math.abs(top2 - this.f13410c)) {
                                expandedOffset = 0;
                            } else {
                                expandedOffset = this.f13409b;
                            }
                        } else if (Math.abs(top2 - i4) < Math.abs(top2 - this.f13410c)) {
                            expandedOffset = this.f13409b;
                        } else {
                            expandedOffset = this.f13410c;
                        }
                        i3 = 6;
                    } else if (Math.abs(top2 - this.f13408a) < Math.abs(top2 - this.f13410c)) {
                        expandedOffset = this.f13408a;
                    } else {
                        expandedOffset = this.f13410c;
                    }
                } else {
                    expandedOffset = this.f13410c;
                }
                i3 = 4;
            }
            if (this.f13413f.smoothSlideViewTo(v2, v2.getLeft(), expandedOffset)) {
                e(2);
                ViewCompat.postOnAnimation(v2, new SettleRunnable(v2, i3));
            } else {
                e(i3);
            }
            this.nestedScrolled = false;
        }
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onTouchEvent(CoordinatorLayout coordinatorLayout, V v2, MotionEvent motionEvent) {
        if (!v2.isShown()) {
            return false;
        }
        int actionMasked = motionEvent.getActionMasked();
        if (this.f13412e == 1 && actionMasked == 0) {
            return true;
        }
        ViewDragHelper viewDragHelper = this.f13413f;
        if (viewDragHelper != null) {
            viewDragHelper.processTouchEvent(motionEvent);
        }
        if (actionMasked == 0) {
            reset();
        }
        if (this.velocityTracker == null) {
            this.velocityTracker = VelocityTracker.obtain();
        }
        this.velocityTracker.addMovement(motionEvent);
        if (actionMasked == 2 && !this.ignoreEvents && Math.abs(this.initialY - motionEvent.getY()) > this.f13413f.getTouchSlop()) {
            this.f13413f.captureChildView(v2, motionEvent.getPointerId(motionEvent.getActionIndex()));
        }
        return !this.ignoreEvents;
    }

    public void setBottomSheetCallback(BottomSheetCallback bottomSheetCallback) {
        this.callback = bottomSheetCallback;
    }

    public void setFitToContents(boolean z2) {
        if (this.fitToContents == z2) {
            return;
        }
        this.fitToContents = z2;
        if (this.f13415h != null) {
            calculateCollapsedOffset();
        }
        e((this.fitToContents && this.f13412e == 6) ? 3 : this.f13412e);
    }

    public void setHideable(boolean z2) {
        this.f13411d = z2;
    }

    public final void setPeekHeight(int i2) {
        WeakReference weakReference;
        View view;
        if (i2 == -1) {
            if (this.peekHeightAuto) {
                return;
            } else {
                this.peekHeightAuto = true;
            }
        } else {
            if (!this.peekHeightAuto && this.peekHeight == i2) {
                return;
            }
            this.peekHeightAuto = false;
            this.peekHeight = Math.max(0, i2);
            this.f13410c = this.f13414g - i2;
        }
        if (this.f13412e != 4 || (weakReference = this.f13415h) == null || (view = (View) weakReference.get()) == null) {
            return;
        }
        view.requestLayout();
    }

    public void setSkipCollapsed(boolean z2) {
        this.skipCollapsed = z2;
    }

    public final void setState(final int i2) {
        if (i2 == this.f13412e) {
            return;
        }
        WeakReference weakReference = this.f13415h;
        if (weakReference == null) {
            if (i2 == 4 || i2 == 3 || i2 == 6 || (this.f13411d && i2 == 5)) {
                this.f13412e = i2;
                return;
            }
            return;
        }
        final View view = (View) weakReference.get();
        if (view == null) {
            return;
        }
        ViewParent parent = view.getParent();
        if (parent != null && parent.isLayoutRequested() && ViewCompat.isAttachedToWindow(view)) {
            view.post(new Runnable() { // from class: com.google.android.material.bottomsheet.BottomSheetBehavior.1
                @Override // java.lang.Runnable
                public void run() {
                    BottomSheetBehavior.this.g(view, i2);
                }
            });
        } else {
            g(view, i2);
        }
    }

    public BottomSheetBehavior(Context context, AttributeSet attributeSet) {
        int i2;
        super(context, attributeSet);
        this.fitToContents = true;
        this.f13412e = 4;
        this.dragCallback = new ViewDragHelper.Callback() { // from class: com.google.android.material.bottomsheet.BottomSheetBehavior.2
            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public int clampViewPositionHorizontal(@NonNull View view, int i22, int i3) {
                return view.getLeft();
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public int clampViewPositionVertical(@NonNull View view, int i22, int i3) {
                int expandedOffset = BottomSheetBehavior.this.getExpandedOffset();
                BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.this;
                return MathUtils.clamp(i22, expandedOffset, bottomSheetBehavior.f13411d ? bottomSheetBehavior.f13414g : bottomSheetBehavior.f13410c);
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public int getViewVerticalDragRange(@NonNull View view) {
                BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.this;
                return bottomSheetBehavior.f13411d ? bottomSheetBehavior.f13414g : bottomSheetBehavior.f13410c;
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public void onViewDragStateChanged(int i22) {
                if (i22 == 1) {
                    BottomSheetBehavior.this.e(1);
                }
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public void onViewPositionChanged(@NonNull View view, int i22, int i3, int i4, int i5) {
                BottomSheetBehavior.this.c(i3);
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public void onViewReleased(@NonNull View v2, float v3, float v4) {
                /*
                    Method dump skipped, instructions count: 237
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.bottomsheet.BottomSheetBehavior.AnonymousClass2.onViewReleased(android.view.View, float, float):void");
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public boolean tryCaptureView(@NonNull View view, int i22) {
                WeakReference weakReference;
                View view2;
                BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.this;
                int i3 = bottomSheetBehavior.f13412e;
                if (i3 == 1 || bottomSheetBehavior.f13418k) {
                    return false;
                }
                return ((i3 == 3 && bottomSheetBehavior.f13417j == i22 && (view2 = (View) bottomSheetBehavior.f13416i.get()) != null && view2.canScrollVertically(-1)) || (weakReference = BottomSheetBehavior.this.f13415h) == null || weakReference.get() != view) ? false : true;
            }
        };
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.BottomSheetBehavior_Layout);
        TypedValue typedValuePeekValue = typedArrayObtainStyledAttributes.peekValue(R.styleable.BottomSheetBehavior_Layout_behavior_peekHeight);
        if (typedValuePeekValue != null && (i2 = typedValuePeekValue.data) == -1) {
            setPeekHeight(i2);
        } else {
            setPeekHeight(typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.BottomSheetBehavior_Layout_behavior_peekHeight, -1));
        }
        setHideable(typedArrayObtainStyledAttributes.getBoolean(R.styleable.BottomSheetBehavior_Layout_behavior_hideable, false));
        setFitToContents(typedArrayObtainStyledAttributes.getBoolean(R.styleable.BottomSheetBehavior_Layout_behavior_fitToContents, true));
        setSkipCollapsed(typedArrayObtainStyledAttributes.getBoolean(R.styleable.BottomSheetBehavior_Layout_behavior_skipCollapsed, false));
        typedArrayObtainStyledAttributes.recycle();
        this.maximumVelocity = ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
    }
}
