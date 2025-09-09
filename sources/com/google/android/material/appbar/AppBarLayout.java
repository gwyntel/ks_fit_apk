package com.google.android.material.appbar;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.math.MathUtils;
import androidx.core.util.ObjectsCompat;
import androidx.core.view.NestedScrollingChild;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.customview.view.AbsSavedState;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.internal.ThemeEnforcement;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

@CoordinatorLayout.DefaultBehavior(Behavior.class)
/* loaded from: classes3.dex */
public class AppBarLayout extends LinearLayout {
    private static final int INVALID_SCROLL_RANGE = -1;
    private int downPreScrollRange;
    private int downScrollRange;
    private boolean haveChildWithInterpolator;
    private WindowInsetsCompat lastInsets;
    private boolean liftOnScroll;
    private boolean liftable;
    private boolean liftableOverride;
    private boolean lifted;
    private List<BaseOnOffsetChangedListener> listeners;
    private int pendingAction;
    private int[] tmpStatesArray;
    private int totalScrollRange;

    protected static class BaseBehavior<T extends AppBarLayout> extends HeaderBehavior<T> {
        private static final int INVALID_POSITION = -1;
        private static final int MAX_OFFSET_ANIMATION_DURATION = 600;
        private WeakReference<View> lastNestedScrollingChildRef;
        private int lastStartedType;
        private ValueAnimator offsetAnimator;
        private int offsetDelta;
        private int offsetToChildIndexOnLayout;
        private boolean offsetToChildIndexOnLayoutIsMinHeight;
        private float offsetToChildIndexOnLayoutPerc;
        private BaseDragCallback onDragCallback;

        public static abstract class BaseDragCallback<T extends AppBarLayout> {
            public abstract boolean canDrag(@NonNull T t2);
        }

        public BaseBehavior() {
            this.offsetToChildIndexOnLayout = -1;
        }

        private void animateOffsetTo(CoordinatorLayout coordinatorLayout, T t2, int i2, float f2) {
            int iAbs = Math.abs(f() - i2);
            float fAbs = Math.abs(f2);
            animateOffsetWithDuration(coordinatorLayout, t2, i2, fAbs > 0.0f ? Math.round((iAbs / fAbs) * 1000.0f) * 3 : (int) (((iAbs / t2.getHeight()) + 1.0f) * 150.0f));
        }

        private void animateOffsetWithDuration(final CoordinatorLayout coordinatorLayout, final T t2, int i2, int i3) {
            int iF = f();
            if (iF == i2) {
                ValueAnimator valueAnimator = this.offsetAnimator;
                if (valueAnimator == null || !valueAnimator.isRunning()) {
                    return;
                }
                this.offsetAnimator.cancel();
                return;
            }
            ValueAnimator valueAnimator2 = this.offsetAnimator;
            if (valueAnimator2 == null) {
                ValueAnimator valueAnimator3 = new ValueAnimator();
                this.offsetAnimator = valueAnimator3;
                valueAnimator3.setInterpolator(AnimationUtils.DECELERATE_INTERPOLATOR);
                this.offsetAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.appbar.AppBarLayout.BaseBehavior.1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public void onAnimationUpdate(ValueAnimator valueAnimator4) {
                        BaseBehavior.this.i(coordinatorLayout, t2, ((Integer) valueAnimator4.getAnimatedValue()).intValue());
                    }
                });
            } else {
                valueAnimator2.cancel();
            }
            this.offsetAnimator.setDuration(Math.min(i3, 600));
            this.offsetAnimator.setIntValues(iF, i2);
            this.offsetAnimator.start();
        }

        private boolean canScrollChildren(CoordinatorLayout coordinatorLayout, T t2, View view) {
            return t2.e() && coordinatorLayout.getHeight() - view.getHeight() <= t2.getHeight();
        }

        private static boolean checkFlag(int i2, int i3) {
            return (i2 & i3) == i3;
        }

        @Nullable
        private View findFirstScrollingChild(CoordinatorLayout coordinatorLayout) {
            int childCount = coordinatorLayout.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = coordinatorLayout.getChildAt(i2);
                if (childAt instanceof NestedScrollingChild) {
                    return childAt;
                }
            }
            return null;
        }

        private static View getAppBarChildOnOffset(AppBarLayout appBarLayout, int i2) {
            int iAbs = Math.abs(i2);
            int childCount = appBarLayout.getChildCount();
            for (int i3 = 0; i3 < childCount; i3++) {
                View childAt = appBarLayout.getChildAt(i3);
                if (iAbs >= childAt.getTop() && iAbs <= childAt.getBottom()) {
                    return childAt;
                }
            }
            return null;
        }

        private int getChildIndexOnOffset(T t2, int i2) {
            int childCount = t2.getChildCount();
            for (int i3 = 0; i3 < childCount; i3++) {
                View childAt = t2.getChildAt(i3);
                int top2 = childAt.getTop();
                int bottom = childAt.getBottom();
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (checkFlag(layoutParams.getScrollFlags(), 32)) {
                    top2 -= ((LinearLayout.LayoutParams) layoutParams).topMargin;
                    bottom += ((LinearLayout.LayoutParams) layoutParams).bottomMargin;
                }
                int i4 = -i2;
                if (top2 <= i4 && bottom >= i4) {
                    return i3;
                }
            }
            return -1;
        }

        private int interpolateOffset(T t2, int i2) {
            int iAbs = Math.abs(i2);
            int childCount = t2.getChildCount();
            int topInset = 0;
            int i3 = 0;
            while (true) {
                if (i3 >= childCount) {
                    break;
                }
                View childAt = t2.getChildAt(i3);
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                Interpolator scrollInterpolator = layoutParams.getScrollInterpolator();
                if (iAbs < childAt.getTop() || iAbs > childAt.getBottom()) {
                    i3++;
                } else if (scrollInterpolator != null) {
                    int scrollFlags = layoutParams.getScrollFlags();
                    if ((scrollFlags & 1) != 0) {
                        topInset = childAt.getHeight() + ((LinearLayout.LayoutParams) layoutParams).topMargin + ((LinearLayout.LayoutParams) layoutParams).bottomMargin;
                        if ((scrollFlags & 2) != 0) {
                            topInset -= ViewCompat.getMinimumHeight(childAt);
                        }
                    }
                    if (ViewCompat.getFitsSystemWindows(childAt)) {
                        topInset -= t2.getTopInset();
                    }
                    if (topInset > 0) {
                        float f2 = topInset;
                        return Integer.signum(i2) * (childAt.getTop() + Math.round(f2 * scrollInterpolator.getInterpolation((iAbs - childAt.getTop()) / f2)));
                    }
                }
            }
            return i2;
        }

        private boolean shouldJumpElevationState(CoordinatorLayout coordinatorLayout, T t2) {
            List<View> dependents = coordinatorLayout.getDependents(t2);
            int size = dependents.size();
            for (int i2 = 0; i2 < size; i2++) {
                CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) dependents.get(i2).getLayoutParams()).getBehavior();
                if (behavior instanceof ScrollingViewBehavior) {
                    return ((ScrollingViewBehavior) behavior).getOverlayTop() != 0;
                }
            }
            return false;
        }

        private void snapToChildIfNeeded(CoordinatorLayout coordinatorLayout, T t2) {
            int iF = f();
            int childIndexOnOffset = getChildIndexOnOffset(t2, iF);
            if (childIndexOnOffset >= 0) {
                View childAt = t2.getChildAt(childIndexOnOffset);
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                int scrollFlags = layoutParams.getScrollFlags();
                if ((scrollFlags & 17) == 17) {
                    int i2 = -childAt.getTop();
                    int minimumHeight = -childAt.getBottom();
                    if (childIndexOnOffset == t2.getChildCount() - 1) {
                        minimumHeight += t2.getTopInset();
                    }
                    if (checkFlag(scrollFlags, 2)) {
                        minimumHeight += ViewCompat.getMinimumHeight(childAt);
                    } else if (checkFlag(scrollFlags, 5)) {
                        int minimumHeight2 = ViewCompat.getMinimumHeight(childAt) + minimumHeight;
                        if (iF < minimumHeight2) {
                            i2 = minimumHeight2;
                        } else {
                            minimumHeight = minimumHeight2;
                        }
                    }
                    if (checkFlag(scrollFlags, 32)) {
                        i2 += ((LinearLayout.LayoutParams) layoutParams).topMargin;
                        minimumHeight -= ((LinearLayout.LayoutParams) layoutParams).bottomMargin;
                    }
                    if (iF < (minimumHeight + i2) / 2) {
                        i2 = minimumHeight;
                    }
                    animateOffsetTo(coordinatorLayout, t2, MathUtils.clamp(i2, -t2.getTotalScrollRange(), 0), 0.0f);
                }
            }
        }

        private void stopNestedScrollIfNeeded(int i2, T t2, View view, int i3) {
            if (i3 == 1) {
                int iF = f();
                if ((i2 >= 0 || iF != 0) && (i2 <= 0 || iF != (-t2.getDownNestedScrollRange()))) {
                    return;
                }
                ViewCompat.stopNestedScroll(view, 1);
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:18:0x0041  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private void updateAppBarLayoutDrawableState(androidx.coordinatorlayout.widget.CoordinatorLayout r6, T r7, int r8, int r9, boolean r10) {
            /*
                r5 = this;
                android.view.View r0 = getAppBarChildOnOffset(r7, r8)
                if (r0 == 0) goto L68
                android.view.ViewGroup$LayoutParams r1 = r0.getLayoutParams()
                com.google.android.material.appbar.AppBarLayout$LayoutParams r1 = (com.google.android.material.appbar.AppBarLayout.LayoutParams) r1
                int r1 = r1.getScrollFlags()
                r2 = r1 & 1
                r3 = 1
                r4 = 0
                if (r2 == 0) goto L41
                int r2 = androidx.core.view.ViewCompat.getMinimumHeight(r0)
                if (r9 <= 0) goto L2f
                r9 = r1 & 12
                if (r9 == 0) goto L2f
                int r8 = -r8
                int r9 = r0.getBottom()
                int r9 = r9 - r2
                int r0 = r7.getTopInset()
                int r9 = r9 - r0
                if (r8 < r9) goto L41
            L2d:
                r8 = r3
                goto L42
            L2f:
                r9 = r1 & 2
                if (r9 == 0) goto L41
                int r8 = -r8
                int r9 = r0.getBottom()
                int r9 = r9 - r2
                int r0 = r7.getTopInset()
                int r9 = r9 - r0
                if (r8 < r9) goto L41
                goto L2d
            L41:
                r8 = r4
            L42:
                boolean r9 = r7.isLiftOnScroll()
                if (r9 == 0) goto L57
                android.view.View r9 = r5.findFirstScrollingChild(r6)
                if (r9 == 0) goto L57
                int r8 = r9.getScrollY()
                if (r8 <= 0) goto L55
                goto L56
            L55:
                r3 = r4
            L56:
                r8 = r3
            L57:
                boolean r8 = r7.h(r8)
                if (r10 != 0) goto L65
                if (r8 == 0) goto L68
                boolean r6 = r5.shouldJumpElevationState(r6, r7)
                if (r6 == 0) goto L68
            L65:
                r7.jumpDrawablesToCurrentState()
            L68:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.appbar.AppBarLayout.BaseBehavior.updateAppBarLayoutDrawableState(androidx.coordinatorlayout.widget.CoordinatorLayout, com.google.android.material.appbar.AppBarLayout, int, int, boolean):void");
        }

        @Override // com.google.android.material.appbar.HeaderBehavior
        int f() {
            return getTopAndBottomOffset() + this.offsetDelta;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.android.material.appbar.HeaderBehavior
        /* renamed from: l, reason: merged with bridge method [inline-methods] */
        public boolean b(AppBarLayout appBarLayout) {
            BaseDragCallback baseDragCallback = this.onDragCallback;
            if (baseDragCallback != null) {
                return baseDragCallback.canDrag(appBarLayout);
            }
            WeakReference<View> weakReference = this.lastNestedScrollingChildRef;
            if (weakReference == null) {
                return true;
            }
            View view = weakReference.get();
            return (view == null || !view.isShown() || view.canScrollVertically(-1)) ? false : true;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.android.material.appbar.HeaderBehavior
        /* renamed from: m, reason: merged with bridge method [inline-methods] */
        public int d(AppBarLayout appBarLayout) {
            return -appBarLayout.getDownNestedScrollRange();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.android.material.appbar.HeaderBehavior
        /* renamed from: n, reason: merged with bridge method [inline-methods] */
        public int e(AppBarLayout appBarLayout) {
            return appBarLayout.getTotalScrollRange();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.android.material.appbar.HeaderBehavior
        /* renamed from: o, reason: merged with bridge method [inline-methods] */
        public void g(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout) {
            snapToChildIfNeeded(coordinatorLayout, appBarLayout);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.android.material.appbar.HeaderBehavior
        /* renamed from: p, reason: merged with bridge method [inline-methods] */
        public int j(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, int i2, int i3, int i4) {
            int iF = f();
            int i5 = 0;
            if (i3 == 0 || iF < i3 || iF > i4) {
                this.offsetDelta = 0;
            } else {
                int iClamp = MathUtils.clamp(i2, i3, i4);
                if (iF != iClamp) {
                    int iInterpolateOffset = appBarLayout.d() ? interpolateOffset(appBarLayout, iClamp) : iClamp;
                    boolean topAndBottomOffset = setTopAndBottomOffset(iInterpolateOffset);
                    i5 = iF - iClamp;
                    this.offsetDelta = iClamp - iInterpolateOffset;
                    if (!topAndBottomOffset && appBarLayout.d()) {
                        coordinatorLayout.dispatchDependentViewsChanged(appBarLayout);
                    }
                    appBarLayout.a(getTopAndBottomOffset());
                    updateAppBarLayoutDrawableState(coordinatorLayout, appBarLayout, iClamp, iClamp < iF ? -1 : 1, false);
                }
            }
            return i5;
        }

        public void setDragCallback(@Nullable BaseDragCallback baseDragCallback) {
            this.onDragCallback = baseDragCallback;
        }

        @Override // com.google.android.material.appbar.ViewOffsetBehavior, androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public boolean onLayoutChild(CoordinatorLayout coordinatorLayout, T t2, int i2) {
            boolean zOnLayoutChild = super.onLayoutChild(coordinatorLayout, (CoordinatorLayout) t2, i2);
            int pendingAction = t2.getPendingAction();
            int i3 = this.offsetToChildIndexOnLayout;
            if (i3 >= 0 && (pendingAction & 8) == 0) {
                View childAt = t2.getChildAt(i3);
                i(coordinatorLayout, t2, (-childAt.getBottom()) + (this.offsetToChildIndexOnLayoutIsMinHeight ? ViewCompat.getMinimumHeight(childAt) + t2.getTopInset() : Math.round(childAt.getHeight() * this.offsetToChildIndexOnLayoutPerc)));
            } else if (pendingAction != 0) {
                boolean z2 = (pendingAction & 4) != 0;
                if ((pendingAction & 2) != 0) {
                    int i4 = -t2.getUpNestedPreScrollRange();
                    if (z2) {
                        animateOffsetTo(coordinatorLayout, t2, i4, 0.0f);
                    } else {
                        i(coordinatorLayout, t2, i4);
                    }
                } else if ((pendingAction & 1) != 0) {
                    if (z2) {
                        animateOffsetTo(coordinatorLayout, t2, 0, 0.0f);
                    } else {
                        i(coordinatorLayout, t2, 0);
                    }
                }
            }
            t2.g();
            this.offsetToChildIndexOnLayout = -1;
            setTopAndBottomOffset(MathUtils.clamp(getTopAndBottomOffset(), -t2.getTotalScrollRange(), 0));
            updateAppBarLayoutDrawableState(coordinatorLayout, t2, getTopAndBottomOffset(), 0, true);
            t2.a(getTopAndBottomOffset());
            return zOnLayoutChild;
        }

        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public boolean onMeasureChild(CoordinatorLayout coordinatorLayout, T t2, int i2, int i3, int i4, int i5) {
            if (((ViewGroup.MarginLayoutParams) ((CoordinatorLayout.LayoutParams) t2.getLayoutParams())).height != -2) {
                return super.onMeasureChild(coordinatorLayout, (CoordinatorLayout) t2, i2, i3, i4, i5);
            }
            coordinatorLayout.onMeasureChild(t2, i2, i3, View.MeasureSpec.makeMeasureSpec(0, 0), i5);
            return true;
        }

        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, T t2, View view, int i2, int i3, int[] iArr, int i4) {
            int i5;
            int downNestedPreScrollRange;
            if (i3 != 0) {
                if (i3 < 0) {
                    i5 = -t2.getTotalScrollRange();
                    downNestedPreScrollRange = t2.getDownNestedPreScrollRange() + i5;
                } else {
                    i5 = -t2.getUpNestedPreScrollRange();
                    downNestedPreScrollRange = 0;
                }
                int i6 = i5;
                int i7 = downNestedPreScrollRange;
                if (i6 != i7) {
                    iArr[1] = h(coordinatorLayout, t2, i3, i6, i7);
                    stopNestedScrollIfNeeded(i3, t2, view, i4);
                }
            }
        }

        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public void onNestedScroll(CoordinatorLayout coordinatorLayout, T t2, View view, int i2, int i3, int i4, int i5, int i6) {
            if (i5 < 0) {
                h(coordinatorLayout, t2, i5, -t2.getDownNestedScrollRange(), 0);
                stopNestedScrollIfNeeded(i5, t2, view, i6);
            }
            if (t2.isLiftOnScroll()) {
                t2.h(view.getScrollY() > 0);
            }
        }

        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public void onRestoreInstanceState(CoordinatorLayout coordinatorLayout, T t2, Parcelable parcelable) {
            if (!(parcelable instanceof SavedState)) {
                super.onRestoreInstanceState(coordinatorLayout, (CoordinatorLayout) t2, parcelable);
                this.offsetToChildIndexOnLayout = -1;
                return;
            }
            SavedState savedState = (SavedState) parcelable;
            super.onRestoreInstanceState(coordinatorLayout, (CoordinatorLayout) t2, savedState.getSuperState());
            this.offsetToChildIndexOnLayout = savedState.f13364a;
            this.offsetToChildIndexOnLayoutPerc = savedState.f13365b;
            this.offsetToChildIndexOnLayoutIsMinHeight = savedState.f13366c;
        }

        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public Parcelable onSaveInstanceState(CoordinatorLayout coordinatorLayout, T t2) {
            Parcelable parcelableOnSaveInstanceState = super.onSaveInstanceState(coordinatorLayout, (CoordinatorLayout) t2);
            int topAndBottomOffset = getTopAndBottomOffset();
            int childCount = t2.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = t2.getChildAt(i2);
                int bottom = childAt.getBottom() + topAndBottomOffset;
                if (childAt.getTop() + topAndBottomOffset <= 0 && bottom >= 0) {
                    SavedState savedState = new SavedState(parcelableOnSaveInstanceState);
                    savedState.f13364a = i2;
                    savedState.f13366c = bottom == ViewCompat.getMinimumHeight(childAt) + t2.getTopInset();
                    savedState.f13365b = bottom / childAt.getHeight();
                    return savedState;
                }
            }
            return parcelableOnSaveInstanceState;
        }

        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, T t2, View view, View view2, int i2, int i3) {
            ValueAnimator valueAnimator;
            boolean z2 = (i2 & 2) != 0 && (t2.isLiftOnScroll() || canScrollChildren(coordinatorLayout, t2, view));
            if (z2 && (valueAnimator = this.offsetAnimator) != null) {
                valueAnimator.cancel();
            }
            this.lastNestedScrollingChildRef = null;
            this.lastStartedType = i3;
            return z2;
        }

        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, T t2, View view, int i2) {
            if (this.lastStartedType == 0 || i2 == 1) {
                snapToChildIfNeeded(coordinatorLayout, t2);
            }
            this.lastNestedScrollingChildRef = new WeakReference<>(view);
        }

        public BaseBehavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.offsetToChildIndexOnLayout = -1;
        }

        protected static class SavedState extends AbsSavedState {
            public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>() { // from class: com.google.android.material.appbar.AppBarLayout.BaseBehavior.SavedState.1
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
                    return new SavedState(parcel, null);
                }
            };

            /* renamed from: a, reason: collision with root package name */
            int f13364a;

            /* renamed from: b, reason: collision with root package name */
            float f13365b;

            /* renamed from: c, reason: collision with root package name */
            boolean f13366c;

            public SavedState(Parcel parcel, ClassLoader classLoader) {
                super(parcel, classLoader);
                this.f13364a = parcel.readInt();
                this.f13365b = parcel.readFloat();
                this.f13366c = parcel.readByte() != 0;
            }

            @Override // androidx.customview.view.AbsSavedState, android.os.Parcelable
            public void writeToParcel(Parcel parcel, int i2) {
                super.writeToParcel(parcel, i2);
                parcel.writeInt(this.f13364a);
                parcel.writeFloat(this.f13365b);
                parcel.writeByte(this.f13366c ? (byte) 1 : (byte) 0);
            }

            public SavedState(Parcelable parcelable) {
                super(parcelable);
            }
        }
    }

    public interface BaseOnOffsetChangedListener<T extends AppBarLayout> {
        void onOffsetChanged(T t2, int i2);
    }

    public static class Behavior extends BaseBehavior<AppBarLayout> {

        public static abstract class DragCallback extends BaseBehavior.BaseDragCallback<AppBarLayout> {
        }

        public Behavior() {
        }

        @Override // com.google.android.material.appbar.ViewOffsetBehavior
        public /* bridge */ /* synthetic */ int getLeftAndRightOffset() {
            return super.getLeftAndRightOffset();
        }

        @Override // com.google.android.material.appbar.ViewOffsetBehavior
        public /* bridge */ /* synthetic */ int getTopAndBottomOffset() {
            return super.getTopAndBottomOffset();
        }

        @Override // com.google.android.material.appbar.AppBarLayout.BaseBehavior
        public /* bridge */ /* synthetic */ boolean onLayoutChild(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, int i2) {
            return super.onLayoutChild(coordinatorLayout, (CoordinatorLayout) appBarLayout, i2);
        }

        @Override // com.google.android.material.appbar.AppBarLayout.BaseBehavior
        public /* bridge */ /* synthetic */ boolean onMeasureChild(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, int i2, int i3, int i4, int i5) {
            return super.onMeasureChild(coordinatorLayout, (CoordinatorLayout) appBarLayout, i2, i3, i4, i5);
        }

        @Override // com.google.android.material.appbar.AppBarLayout.BaseBehavior
        public /* bridge */ /* synthetic */ void onNestedPreScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, int i2, int i3, int[] iArr, int i4) {
            super.onNestedPreScroll(coordinatorLayout, (CoordinatorLayout) appBarLayout, view, i2, i3, iArr, i4);
        }

        @Override // com.google.android.material.appbar.AppBarLayout.BaseBehavior
        public /* bridge */ /* synthetic */ void onNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, int i2, int i3, int i4, int i5, int i6) {
            super.onNestedScroll(coordinatorLayout, (CoordinatorLayout) appBarLayout, view, i2, i3, i4, i5, i6);
        }

        @Override // com.google.android.material.appbar.AppBarLayout.BaseBehavior
        public /* bridge */ /* synthetic */ void onRestoreInstanceState(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, Parcelable parcelable) {
            super.onRestoreInstanceState(coordinatorLayout, (CoordinatorLayout) appBarLayout, parcelable);
        }

        @Override // com.google.android.material.appbar.AppBarLayout.BaseBehavior
        public /* bridge */ /* synthetic */ Parcelable onSaveInstanceState(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout) {
            return super.onSaveInstanceState(coordinatorLayout, (CoordinatorLayout) appBarLayout);
        }

        @Override // com.google.android.material.appbar.AppBarLayout.BaseBehavior
        public /* bridge */ /* synthetic */ boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, View view2, int i2, int i3) {
            return super.onStartNestedScroll(coordinatorLayout, (CoordinatorLayout) appBarLayout, view, view2, i2, i3);
        }

        @Override // com.google.android.material.appbar.AppBarLayout.BaseBehavior
        public /* bridge */ /* synthetic */ void onStopNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, int i2) {
            super.onStopNestedScroll(coordinatorLayout, (CoordinatorLayout) appBarLayout, view, i2);
        }

        @Override // com.google.android.material.appbar.AppBarLayout.BaseBehavior
        public /* bridge */ /* synthetic */ void setDragCallback(@Nullable BaseBehavior.BaseDragCallback baseDragCallback) {
            super.setDragCallback(baseDragCallback);
        }

        @Override // com.google.android.material.appbar.ViewOffsetBehavior
        public /* bridge */ /* synthetic */ boolean setLeftAndRightOffset(int i2) {
            return super.setLeftAndRightOffset(i2);
        }

        @Override // com.google.android.material.appbar.ViewOffsetBehavior
        public /* bridge */ /* synthetic */ boolean setTopAndBottomOffset(int i2) {
            return super.setTopAndBottomOffset(i2);
        }

        public Behavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }
    }

    public interface OnOffsetChangedListener extends BaseOnOffsetChangedListener<AppBarLayout> {
        @Override // com.google.android.material.appbar.AppBarLayout.BaseOnOffsetChangedListener
        void onOffsetChanged(AppBarLayout appBarLayout, int i2);
    }

    public static class ScrollingViewBehavior extends HeaderScrollingViewBehavior {
        public ScrollingViewBehavior() {
        }

        private static int getAppBarLayoutOffset(AppBarLayout appBarLayout) {
            CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams()).getBehavior();
            if (behavior instanceof BaseBehavior) {
                return ((BaseBehavior) behavior).f();
            }
            return 0;
        }

        private void offsetChildAsNeeded(View view, View view2) {
            CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) view2.getLayoutParams()).getBehavior();
            if (behavior instanceof BaseBehavior) {
                ViewCompat.offsetTopAndBottom(view, (((view2.getBottom() - view.getTop()) + ((BaseBehavior) behavior).offsetDelta) + f()) - c(view2));
            }
        }

        private void updateLiftedStateIfNeeded(View view, View view2) {
            if (view2 instanceof AppBarLayout) {
                AppBarLayout appBarLayout = (AppBarLayout) view2;
                if (appBarLayout.isLiftOnScroll()) {
                    appBarLayout.h(view.getScrollY() > 0);
                }
            }
        }

        @Override // com.google.android.material.appbar.HeaderScrollingViewBehavior
        float d(View view) {
            int i2;
            if (view instanceof AppBarLayout) {
                AppBarLayout appBarLayout = (AppBarLayout) view;
                int totalScrollRange = appBarLayout.getTotalScrollRange();
                int downNestedPreScrollRange = appBarLayout.getDownNestedPreScrollRange();
                int appBarLayoutOffset = getAppBarLayoutOffset(appBarLayout);
                if ((downNestedPreScrollRange == 0 || totalScrollRange + appBarLayoutOffset > downNestedPreScrollRange) && (i2 = totalScrollRange - downNestedPreScrollRange) != 0) {
                    return (appBarLayoutOffset / i2) + 1.0f;
                }
            }
            return 0.0f;
        }

        @Override // com.google.android.material.appbar.HeaderScrollingViewBehavior
        int e(View view) {
            return view instanceof AppBarLayout ? ((AppBarLayout) view).getTotalScrollRange() : super.e(view);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.android.material.appbar.HeaderScrollingViewBehavior
        /* renamed from: g, reason: merged with bridge method [inline-methods] */
        public AppBarLayout b(List list) {
            int size = list.size();
            for (int i2 = 0; i2 < size; i2++) {
                View view = (View) list.get(i2);
                if (view instanceof AppBarLayout) {
                    return (AppBarLayout) view;
                }
            }
            return null;
        }

        @Override // com.google.android.material.appbar.ViewOffsetBehavior
        public /* bridge */ /* synthetic */ int getLeftAndRightOffset() {
            return super.getLeftAndRightOffset();
        }

        @Override // com.google.android.material.appbar.ViewOffsetBehavior
        public /* bridge */ /* synthetic */ int getTopAndBottomOffset() {
            return super.getTopAndBottomOffset();
        }

        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public boolean layoutDependsOn(CoordinatorLayout coordinatorLayout, View view, View view2) {
            return view2 instanceof AppBarLayout;
        }

        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public boolean onDependentViewChanged(CoordinatorLayout coordinatorLayout, View view, View view2) {
            offsetChildAsNeeded(view, view2);
            updateLiftedStateIfNeeded(view, view2);
            return false;
        }

        @Override // com.google.android.material.appbar.ViewOffsetBehavior, androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public /* bridge */ /* synthetic */ boolean onLayoutChild(CoordinatorLayout coordinatorLayout, View view, int i2) {
            return super.onLayoutChild(coordinatorLayout, view, i2);
        }

        @Override // com.google.android.material.appbar.HeaderScrollingViewBehavior, androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public /* bridge */ /* synthetic */ boolean onMeasureChild(CoordinatorLayout coordinatorLayout, View view, int i2, int i3, int i4, int i5) {
            return super.onMeasureChild(coordinatorLayout, view, i2, i3, i4, i5);
        }

        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public boolean onRequestChildRectangleOnScreen(CoordinatorLayout coordinatorLayout, View view, Rect rect, boolean z2) {
            AppBarLayout appBarLayoutB = b(coordinatorLayout.getDependencies(view));
            if (appBarLayoutB != null) {
                rect.offset(view.getLeft(), view.getTop());
                Rect rect2 = this.f13380a;
                rect2.set(0, 0, coordinatorLayout.getWidth(), coordinatorLayout.getHeight());
                if (!rect2.contains(rect)) {
                    appBarLayoutB.setExpanded(false, !z2);
                    return true;
                }
            }
            return false;
        }

        @Override // com.google.android.material.appbar.ViewOffsetBehavior
        public /* bridge */ /* synthetic */ boolean setLeftAndRightOffset(int i2) {
            return super.setLeftAndRightOffset(i2);
        }

        @Override // com.google.android.material.appbar.ViewOffsetBehavior
        public /* bridge */ /* synthetic */ boolean setTopAndBottomOffset(int i2) {
            return super.setTopAndBottomOffset(i2);
        }

        public ScrollingViewBehavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ScrollingViewBehavior_Layout);
            setOverlayTop(typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.ScrollingViewBehavior_Layout_behavior_overlapTop, 0));
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    public AppBarLayout(Context context) {
        this(context, null);
    }

    private boolean hasCollapsibleChild() {
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            if (((LayoutParams) getChildAt(i2).getLayoutParams()).a()) {
                return true;
            }
        }
        return false;
    }

    private void invalidateScrollRanges() {
        this.totalScrollRange = -1;
        this.downPreScrollRange = -1;
        this.downScrollRange = -1;
    }

    private boolean setLiftableState(boolean z2) {
        if (this.liftable == z2) {
            return false;
        }
        this.liftable = z2;
        refreshDrawableState();
        return true;
    }

    void a(int i2) {
        List<BaseOnOffsetChangedListener> list = this.listeners;
        if (list != null) {
            int size = list.size();
            for (int i3 = 0; i3 < size; i3++) {
                BaseOnOffsetChangedListener baseOnOffsetChangedListener = this.listeners.get(i3);
                if (baseOnOffsetChangedListener != null) {
                    baseOnOffsetChangedListener.onOffsetChanged(this, i2);
                }
            }
        }
    }

    public void addOnOffsetChangedListener(BaseOnOffsetChangedListener baseOnOffsetChangedListener) {
        if (this.listeners == null) {
            this.listeners = new ArrayList();
        }
        if (baseOnOffsetChangedListener == null || this.listeners.contains(baseOnOffsetChangedListener)) {
            return;
        }
        this.listeners.add(baseOnOffsetChangedListener);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.widget.LinearLayout, android.view.ViewGroup
    /* renamed from: b, reason: merged with bridge method [inline-methods] */
    public LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.widget.LinearLayout, android.view.ViewGroup
    /* renamed from: c, reason: merged with bridge method [inline-methods] */
    public LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LinearLayout.LayoutParams ? new LayoutParams((LinearLayout.LayoutParams) layoutParams) : layoutParams instanceof ViewGroup.MarginLayoutParams ? new LayoutParams((ViewGroup.MarginLayoutParams) layoutParams) : new LayoutParams(layoutParams);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup
    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    boolean d() {
        return this.haveChildWithInterpolator;
    }

    boolean e() {
        return getTotalScrollRange() != 0;
    }

    WindowInsetsCompat f(WindowInsetsCompat windowInsetsCompat) {
        WindowInsetsCompat windowInsetsCompat2 = ViewCompat.getFitsSystemWindows(this) ? windowInsetsCompat : null;
        if (!ObjectsCompat.equals(this.lastInsets, windowInsetsCompat2)) {
            this.lastInsets = windowInsetsCompat2;
            invalidateScrollRanges();
        }
        return windowInsetsCompat;
    }

    void g() {
        this.pendingAction = 0;
    }

    int getDownNestedPreScrollRange() {
        int i2 = this.downPreScrollRange;
        if (i2 != -1) {
            return i2;
        }
        int minimumHeight = 0;
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = getChildAt(childCount);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            int measuredHeight = childAt.getMeasuredHeight();
            int i3 = layoutParams.f13367a;
            if ((i3 & 5) != 5) {
                if (minimumHeight > 0) {
                    break;
                }
            } else {
                int i4 = minimumHeight + ((LinearLayout.LayoutParams) layoutParams).topMargin + ((LinearLayout.LayoutParams) layoutParams).bottomMargin;
                minimumHeight = (i3 & 8) != 0 ? i4 + ViewCompat.getMinimumHeight(childAt) : i4 + (measuredHeight - ((i3 & 2) != 0 ? ViewCompat.getMinimumHeight(childAt) : getTopInset()));
            }
        }
        int iMax = Math.max(0, minimumHeight);
        this.downPreScrollRange = iMax;
        return iMax;
    }

    int getDownNestedScrollRange() {
        int i2 = this.downScrollRange;
        if (i2 != -1) {
            return i2;
        }
        int childCount = getChildCount();
        int i3 = 0;
        int minimumHeight = 0;
        while (true) {
            if (i3 >= childCount) {
                break;
            }
            View childAt = getChildAt(i3);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            int measuredHeight = childAt.getMeasuredHeight() + ((LinearLayout.LayoutParams) layoutParams).topMargin + ((LinearLayout.LayoutParams) layoutParams).bottomMargin;
            int i4 = layoutParams.f13367a;
            if ((i4 & 1) == 0) {
                break;
            }
            minimumHeight += measuredHeight;
            if ((i4 & 2) != 0) {
                minimumHeight -= ViewCompat.getMinimumHeight(childAt) + getTopInset();
                break;
            }
            i3++;
        }
        int iMax = Math.max(0, minimumHeight);
        this.downScrollRange = iMax;
        return iMax;
    }

    public final int getMinimumHeightForVisibleOverlappingContent() {
        int topInset = getTopInset();
        int minimumHeight = ViewCompat.getMinimumHeight(this);
        if (minimumHeight == 0) {
            int childCount = getChildCount();
            minimumHeight = childCount >= 1 ? ViewCompat.getMinimumHeight(getChildAt(childCount - 1)) : 0;
            if (minimumHeight == 0) {
                return getHeight() / 3;
            }
        }
        return (minimumHeight * 2) + topInset;
    }

    int getPendingAction() {
        return this.pendingAction;
    }

    @Deprecated
    public float getTargetElevation() {
        return 0.0f;
    }

    @VisibleForTesting
    final int getTopInset() {
        WindowInsetsCompat windowInsetsCompat = this.lastInsets;
        if (windowInsetsCompat != null) {
            return windowInsetsCompat.getSystemWindowInsetTop();
        }
        return 0;
    }

    public final int getTotalScrollRange() {
        int i2 = this.totalScrollRange;
        if (i2 != -1) {
            return i2;
        }
        int childCount = getChildCount();
        int i3 = 0;
        int minimumHeight = 0;
        while (true) {
            if (i3 >= childCount) {
                break;
            }
            View childAt = getChildAt(i3);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            int measuredHeight = childAt.getMeasuredHeight();
            int i4 = layoutParams.f13367a;
            if ((i4 & 1) == 0) {
                break;
            }
            minimumHeight += measuredHeight + ((LinearLayout.LayoutParams) layoutParams).topMargin + ((LinearLayout.LayoutParams) layoutParams).bottomMargin;
            if ((i4 & 2) != 0) {
                minimumHeight -= ViewCompat.getMinimumHeight(childAt);
                break;
            }
            i3++;
        }
        int iMax = Math.max(0, minimumHeight - getTopInset());
        this.totalScrollRange = iMax;
        return iMax;
    }

    int getUpNestedPreScrollRange() {
        return getTotalScrollRange();
    }

    boolean h(boolean z2) {
        if (this.lifted == z2) {
            return false;
        }
        this.lifted = z2;
        refreshDrawableState();
        return true;
    }

    public boolean isLiftOnScroll() {
        return this.liftOnScroll;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected int[] onCreateDrawableState(int i2) {
        if (this.tmpStatesArray == null) {
            this.tmpStatesArray = new int[4];
        }
        int[] iArr = this.tmpStatesArray;
        int[] iArrOnCreateDrawableState = super.onCreateDrawableState(i2 + iArr.length);
        boolean z2 = this.liftable;
        int i3 = R.attr.state_liftable;
        if (!z2) {
            i3 = -i3;
        }
        iArr[0] = i3;
        iArr[1] = (z2 && this.lifted) ? R.attr.state_lifted : -R.attr.state_lifted;
        int i4 = R.attr.state_collapsible;
        if (!z2) {
            i4 = -i4;
        }
        iArr[2] = i4;
        iArr[3] = (z2 && this.lifted) ? R.attr.state_collapsed : -R.attr.state_collapsed;
        return View.mergeDrawableStates(iArrOnCreateDrawableState, iArr);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        super.onLayout(z2, i2, i3, i4, i5);
        invalidateScrollRanges();
        this.haveChildWithInterpolator = false;
        int childCount = getChildCount();
        int i6 = 0;
        while (true) {
            if (i6 >= childCount) {
                break;
            }
            if (((LayoutParams) getChildAt(i6).getLayoutParams()).getScrollInterpolator() != null) {
                this.haveChildWithInterpolator = true;
                break;
            }
            i6++;
        }
        if (this.liftableOverride) {
            return;
        }
        setLiftableState(this.liftOnScroll || hasCollapsibleChild());
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onMeasure(int i2, int i3) {
        super.onMeasure(i2, i3);
        invalidateScrollRanges();
    }

    public void removeOnOffsetChangedListener(BaseOnOffsetChangedListener baseOnOffsetChangedListener) {
        List<BaseOnOffsetChangedListener> list = this.listeners;
        if (list == null || baseOnOffsetChangedListener == null) {
            return;
        }
        list.remove(baseOnOffsetChangedListener);
    }

    public void setExpanded(boolean z2) {
        setExpanded(z2, ViewCompat.isLaidOut(this));
    }

    public void setLiftOnScroll(boolean z2) {
        this.liftOnScroll = z2;
    }

    public boolean setLiftable(boolean z2) {
        this.liftableOverride = true;
        return setLiftableState(z2);
    }

    public boolean setLifted(boolean z2) {
        return h(z2);
    }

    @Override // android.widget.LinearLayout
    public void setOrientation(int i2) {
        if (i2 != 1) {
            throw new IllegalArgumentException("AppBarLayout is always vertical and does not support horizontal orientation");
        }
        super.setOrientation(i2);
    }

    @Deprecated
    public void setTargetElevation(float f2) throws Resources.NotFoundException {
        ViewUtilsLollipop.b(this, f2);
    }

    public AppBarLayout(Context context, AttributeSet attributeSet) throws Resources.NotFoundException {
        super(context, attributeSet);
        this.totalScrollRange = -1;
        this.downPreScrollRange = -1;
        this.downScrollRange = -1;
        this.pendingAction = 0;
        setOrientation(1);
        int i2 = Build.VERSION.SDK_INT;
        ViewUtilsLollipop.a(this);
        ViewUtilsLollipop.c(this, attributeSet, 0, R.style.Widget_Design_AppBarLayout);
        TypedArray typedArrayObtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(context, attributeSet, R.styleable.AppBarLayout, 0, R.style.Widget_Design_AppBarLayout, new int[0]);
        ViewCompat.setBackground(this, typedArrayObtainStyledAttributes.getDrawable(R.styleable.AppBarLayout_android_background));
        if (typedArrayObtainStyledAttributes.hasValue(R.styleable.AppBarLayout_expanded)) {
            setExpanded(typedArrayObtainStyledAttributes.getBoolean(R.styleable.AppBarLayout_expanded, false), false, false);
        }
        if (typedArrayObtainStyledAttributes.hasValue(R.styleable.AppBarLayout_elevation)) {
            ViewUtilsLollipop.b(this, typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.AppBarLayout_elevation, 0));
        }
        if (i2 >= 26) {
            if (typedArrayObtainStyledAttributes.hasValue(R.styleable.AppBarLayout_android_keyboardNavigationCluster)) {
                setKeyboardNavigationCluster(typedArrayObtainStyledAttributes.getBoolean(R.styleable.AppBarLayout_android_keyboardNavigationCluster, false));
            }
            if (typedArrayObtainStyledAttributes.hasValue(R.styleable.AppBarLayout_android_touchscreenBlocksFocus)) {
                setTouchscreenBlocksFocus(typedArrayObtainStyledAttributes.getBoolean(R.styleable.AppBarLayout_android_touchscreenBlocksFocus, false));
            }
        }
        this.liftOnScroll = typedArrayObtainStyledAttributes.getBoolean(R.styleable.AppBarLayout_liftOnScroll, false);
        typedArrayObtainStyledAttributes.recycle();
        ViewCompat.setOnApplyWindowInsetsListener(this, new OnApplyWindowInsetsListener() { // from class: com.google.android.material.appbar.AppBarLayout.1
            @Override // androidx.core.view.OnApplyWindowInsetsListener
            public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                return AppBarLayout.this.f(windowInsetsCompat);
            }
        });
    }

    public void setExpanded(boolean z2, boolean z3) {
        setExpanded(z2, z3, true);
    }

    private void setExpanded(boolean z2, boolean z3, boolean z4) {
        this.pendingAction = (z2 ? 1 : 2) | (z3 ? 4 : 0) | (z4 ? 8 : 0);
        requestLayout();
    }

    public void removeOnOffsetChangedListener(OnOffsetChangedListener onOffsetChangedListener) {
        removeOnOffsetChangedListener((BaseOnOffsetChangedListener) onOffsetChangedListener);
    }

    public void addOnOffsetChangedListener(OnOffsetChangedListener onOffsetChangedListener) {
        addOnOffsetChangedListener((BaseOnOffsetChangedListener) onOffsetChangedListener);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup
    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    public static class LayoutParams extends LinearLayout.LayoutParams {
        public static final int SCROLL_FLAG_ENTER_ALWAYS = 4;
        public static final int SCROLL_FLAG_ENTER_ALWAYS_COLLAPSED = 8;
        public static final int SCROLL_FLAG_EXIT_UNTIL_COLLAPSED = 2;
        public static final int SCROLL_FLAG_SCROLL = 1;
        public static final int SCROLL_FLAG_SNAP = 16;
        public static final int SCROLL_FLAG_SNAP_MARGINS = 32;

        /* renamed from: a, reason: collision with root package name */
        int f13367a;

        /* renamed from: b, reason: collision with root package name */
        Interpolator f13368b;

        @Retention(RetentionPolicy.SOURCE)
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public @interface ScrollFlags {
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.f13367a = 1;
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.AppBarLayout_Layout);
            this.f13367a = typedArrayObtainStyledAttributes.getInt(R.styleable.AppBarLayout_Layout_layout_scrollFlags, 0);
            if (typedArrayObtainStyledAttributes.hasValue(R.styleable.AppBarLayout_Layout_layout_scrollInterpolator)) {
                this.f13368b = android.view.animation.AnimationUtils.loadInterpolator(context, typedArrayObtainStyledAttributes.getResourceId(R.styleable.AppBarLayout_Layout_layout_scrollInterpolator, 0));
            }
            typedArrayObtainStyledAttributes.recycle();
        }

        boolean a() {
            int i2 = this.f13367a;
            return (i2 & 1) == 1 && (i2 & 10) != 0;
        }

        public int getScrollFlags() {
            return this.f13367a;
        }

        public Interpolator getScrollInterpolator() {
            return this.f13368b;
        }

        public void setScrollFlags(int i2) {
            this.f13367a = i2;
        }

        public void setScrollInterpolator(Interpolator interpolator) {
            this.f13368b = interpolator;
        }

        public LayoutParams(int i2, int i3) {
            super(i2, i3);
            this.f13367a = 1;
        }

        public LayoutParams(int i2, int i3, float f2) {
            super(i2, i3, f2);
            this.f13367a = 1;
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.f13367a = 1;
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
            this.f13367a = 1;
        }

        @RequiresApi(19)
        public LayoutParams(LinearLayout.LayoutParams layoutParams) {
            super(layoutParams);
            this.f13367a = 1;
        }

        @RequiresApi(19)
        public LayoutParams(LayoutParams layoutParams) {
            super((LinearLayout.LayoutParams) layoutParams);
            this.f13367a = 1;
            this.f13367a = layoutParams.f13367a;
            this.f13368b = layoutParams.f13368b;
        }
    }
}
