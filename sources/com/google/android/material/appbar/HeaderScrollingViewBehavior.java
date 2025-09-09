package com.google.android.material.appbar;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.math.MathUtils;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.List;

/* loaded from: classes3.dex */
abstract class HeaderScrollingViewBehavior extends ViewOffsetBehavior<View> {

    /* renamed from: a, reason: collision with root package name */
    final Rect f13380a;

    /* renamed from: b, reason: collision with root package name */
    final Rect f13381b;
    private int overlayTop;
    private int verticalLayoutGap;

    public HeaderScrollingViewBehavior() {
        this.f13380a = new Rect();
        this.f13381b = new Rect();
        this.verticalLayoutGap = 0;
    }

    private static int resolveGravity(int i2) {
        if (i2 == 0) {
            return 8388659;
        }
        return i2;
    }

    @Override // com.google.android.material.appbar.ViewOffsetBehavior
    protected void a(CoordinatorLayout coordinatorLayout, View view, int i2) {
        View viewB = b(coordinatorLayout.getDependencies(view));
        if (viewB == null) {
            super.a(coordinatorLayout, view, i2);
            this.verticalLayoutGap = 0;
            return;
        }
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) view.getLayoutParams();
        Rect rect = this.f13380a;
        rect.set(coordinatorLayout.getPaddingLeft() + ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin, viewB.getBottom() + ((ViewGroup.MarginLayoutParams) layoutParams).topMargin, (coordinatorLayout.getWidth() - coordinatorLayout.getPaddingRight()) - ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin, ((coordinatorLayout.getHeight() + viewB.getBottom()) - coordinatorLayout.getPaddingBottom()) - ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin);
        WindowInsetsCompat lastWindowInsets = coordinatorLayout.getLastWindowInsets();
        if (lastWindowInsets != null && ViewCompat.getFitsSystemWindows(coordinatorLayout) && !ViewCompat.getFitsSystemWindows(view)) {
            rect.left += lastWindowInsets.getSystemWindowInsetLeft();
            rect.right -= lastWindowInsets.getSystemWindowInsetRight();
        }
        Rect rect2 = this.f13381b;
        GravityCompat.apply(resolveGravity(layoutParams.gravity), view.getMeasuredWidth(), view.getMeasuredHeight(), rect, rect2, i2);
        int iC = c(viewB);
        view.layout(rect2.left, rect2.top - iC, rect2.right, rect2.bottom - iC);
        this.verticalLayoutGap = rect2.top - viewB.getBottom();
    }

    abstract View b(List list);

    final int c(View view) {
        if (this.overlayTop == 0) {
            return 0;
        }
        float fD = d(view);
        int i2 = this.overlayTop;
        return MathUtils.clamp((int) (fD * i2), 0, i2);
    }

    float d(View view) {
        return 1.0f;
    }

    int e(View view) {
        return view.getMeasuredHeight();
    }

    final int f() {
        return this.verticalLayoutGap;
    }

    public final int getOverlayTop() {
        return this.overlayTop;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onMeasureChild(CoordinatorLayout coordinatorLayout, View view, int i2, int i3, int i4, int i5) {
        View viewB;
        int i6 = view.getLayoutParams().height;
        if ((i6 != -1 && i6 != -2) || (viewB = b(coordinatorLayout.getDependencies(view))) == null) {
            return false;
        }
        if (ViewCompat.getFitsSystemWindows(viewB) && !ViewCompat.getFitsSystemWindows(view)) {
            ViewCompat.setFitsSystemWindows(view, true);
            if (ViewCompat.getFitsSystemWindows(view)) {
                view.requestLayout();
                return true;
            }
        }
        int size = View.MeasureSpec.getSize(i4);
        if (size == 0) {
            size = coordinatorLayout.getHeight();
        }
        coordinatorLayout.onMeasureChild(view, i2, i3, View.MeasureSpec.makeMeasureSpec((size - viewB.getMeasuredHeight()) + e(viewB), i6 == -1 ? 1073741824 : Integer.MIN_VALUE), i5);
        return true;
    }

    public final void setOverlayTop(int i2) {
        this.overlayTop = i2;
    }

    public HeaderScrollingViewBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f13380a = new Rect();
        this.f13381b = new Rect();
        this.verticalLayoutGap = 0;
    }
}
