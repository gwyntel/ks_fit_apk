package androidx.recyclerview.widget;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes2.dex */
public abstract class OrientationHelper {
    public static final int HORIZONTAL = 0;
    private static final int INVALID_SIZE = Integer.MIN_VALUE;
    public static final int VERTICAL = 1;

    /* renamed from: a, reason: collision with root package name */
    protected final RecyclerView.LayoutManager f6013a;

    /* renamed from: b, reason: collision with root package name */
    final Rect f6014b;
    private int mLastTotalSpace;

    public static OrientationHelper createHorizontalHelper(RecyclerView.LayoutManager layoutManager) {
        return new OrientationHelper(layoutManager) { // from class: androidx.recyclerview.widget.OrientationHelper.1
            @Override // androidx.recyclerview.widget.OrientationHelper
            public int getDecoratedEnd(View view) {
                return this.f6013a.getDecoratedRight(view) + ((ViewGroup.MarginLayoutParams) ((RecyclerView.LayoutParams) view.getLayoutParams())).rightMargin;
            }

            @Override // androidx.recyclerview.widget.OrientationHelper
            public int getDecoratedMeasurement(View view) {
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
                return this.f6013a.getDecoratedMeasuredWidth(view) + ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin;
            }

            @Override // androidx.recyclerview.widget.OrientationHelper
            public int getDecoratedMeasurementInOther(View view) {
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
                return this.f6013a.getDecoratedMeasuredHeight(view) + ((ViewGroup.MarginLayoutParams) layoutParams).topMargin + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
            }

            @Override // androidx.recyclerview.widget.OrientationHelper
            public int getDecoratedStart(View view) {
                return this.f6013a.getDecoratedLeft(view) - ((ViewGroup.MarginLayoutParams) ((RecyclerView.LayoutParams) view.getLayoutParams())).leftMargin;
            }

            @Override // androidx.recyclerview.widget.OrientationHelper
            public int getEnd() {
                return this.f6013a.getWidth();
            }

            @Override // androidx.recyclerview.widget.OrientationHelper
            public int getEndAfterPadding() {
                return this.f6013a.getWidth() - this.f6013a.getPaddingRight();
            }

            @Override // androidx.recyclerview.widget.OrientationHelper
            public int getEndPadding() {
                return this.f6013a.getPaddingRight();
            }

            @Override // androidx.recyclerview.widget.OrientationHelper
            public int getMode() {
                return this.f6013a.getWidthMode();
            }

            @Override // androidx.recyclerview.widget.OrientationHelper
            public int getModeInOther() {
                return this.f6013a.getHeightMode();
            }

            @Override // androidx.recyclerview.widget.OrientationHelper
            public int getStartAfterPadding() {
                return this.f6013a.getPaddingLeft();
            }

            @Override // androidx.recyclerview.widget.OrientationHelper
            public int getTotalSpace() {
                return (this.f6013a.getWidth() - this.f6013a.getPaddingLeft()) - this.f6013a.getPaddingRight();
            }

            @Override // androidx.recyclerview.widget.OrientationHelper
            public int getTransformedEndWithDecoration(View view) {
                this.f6013a.getTransformedBoundingBox(view, true, this.f6014b);
                return this.f6014b.right;
            }

            @Override // androidx.recyclerview.widget.OrientationHelper
            public int getTransformedStartWithDecoration(View view) {
                this.f6013a.getTransformedBoundingBox(view, true, this.f6014b);
                return this.f6014b.left;
            }

            @Override // androidx.recyclerview.widget.OrientationHelper
            public void offsetChild(View view, int i2) {
                view.offsetLeftAndRight(i2);
            }

            @Override // androidx.recyclerview.widget.OrientationHelper
            public void offsetChildren(int i2) {
                this.f6013a.offsetChildrenHorizontal(i2);
            }
        };
    }

    public static OrientationHelper createOrientationHelper(RecyclerView.LayoutManager layoutManager, int i2) {
        if (i2 == 0) {
            return createHorizontalHelper(layoutManager);
        }
        if (i2 == 1) {
            return createVerticalHelper(layoutManager);
        }
        throw new IllegalArgumentException("invalid orientation");
    }

    public static OrientationHelper createVerticalHelper(RecyclerView.LayoutManager layoutManager) {
        return new OrientationHelper(layoutManager) { // from class: androidx.recyclerview.widget.OrientationHelper.2
            @Override // androidx.recyclerview.widget.OrientationHelper
            public int getDecoratedEnd(View view) {
                return this.f6013a.getDecoratedBottom(view) + ((ViewGroup.MarginLayoutParams) ((RecyclerView.LayoutParams) view.getLayoutParams())).bottomMargin;
            }

            @Override // androidx.recyclerview.widget.OrientationHelper
            public int getDecoratedMeasurement(View view) {
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
                return this.f6013a.getDecoratedMeasuredHeight(view) + ((ViewGroup.MarginLayoutParams) layoutParams).topMargin + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
            }

            @Override // androidx.recyclerview.widget.OrientationHelper
            public int getDecoratedMeasurementInOther(View view) {
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
                return this.f6013a.getDecoratedMeasuredWidth(view) + ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin;
            }

            @Override // androidx.recyclerview.widget.OrientationHelper
            public int getDecoratedStart(View view) {
                return this.f6013a.getDecoratedTop(view) - ((ViewGroup.MarginLayoutParams) ((RecyclerView.LayoutParams) view.getLayoutParams())).topMargin;
            }

            @Override // androidx.recyclerview.widget.OrientationHelper
            public int getEnd() {
                return this.f6013a.getHeight();
            }

            @Override // androidx.recyclerview.widget.OrientationHelper
            public int getEndAfterPadding() {
                return this.f6013a.getHeight() - this.f6013a.getPaddingBottom();
            }

            @Override // androidx.recyclerview.widget.OrientationHelper
            public int getEndPadding() {
                return this.f6013a.getPaddingBottom();
            }

            @Override // androidx.recyclerview.widget.OrientationHelper
            public int getMode() {
                return this.f6013a.getHeightMode();
            }

            @Override // androidx.recyclerview.widget.OrientationHelper
            public int getModeInOther() {
                return this.f6013a.getWidthMode();
            }

            @Override // androidx.recyclerview.widget.OrientationHelper
            public int getStartAfterPadding() {
                return this.f6013a.getPaddingTop();
            }

            @Override // androidx.recyclerview.widget.OrientationHelper
            public int getTotalSpace() {
                return (this.f6013a.getHeight() - this.f6013a.getPaddingTop()) - this.f6013a.getPaddingBottom();
            }

            @Override // androidx.recyclerview.widget.OrientationHelper
            public int getTransformedEndWithDecoration(View view) {
                this.f6013a.getTransformedBoundingBox(view, true, this.f6014b);
                return this.f6014b.bottom;
            }

            @Override // androidx.recyclerview.widget.OrientationHelper
            public int getTransformedStartWithDecoration(View view) {
                this.f6013a.getTransformedBoundingBox(view, true, this.f6014b);
                return this.f6014b.top;
            }

            @Override // androidx.recyclerview.widget.OrientationHelper
            public void offsetChild(View view, int i2) {
                view.offsetTopAndBottom(i2);
            }

            @Override // androidx.recyclerview.widget.OrientationHelper
            public void offsetChildren(int i2) {
                this.f6013a.offsetChildrenVertical(i2);
            }
        };
    }

    public abstract int getDecoratedEnd(View view);

    public abstract int getDecoratedMeasurement(View view);

    public abstract int getDecoratedMeasurementInOther(View view);

    public abstract int getDecoratedStart(View view);

    public abstract int getEnd();

    public abstract int getEndAfterPadding();

    public abstract int getEndPadding();

    public RecyclerView.LayoutManager getLayoutManager() {
        return this.f6013a;
    }

    public abstract int getMode();

    public abstract int getModeInOther();

    public abstract int getStartAfterPadding();

    public abstract int getTotalSpace();

    public int getTotalSpaceChange() {
        if (Integer.MIN_VALUE == this.mLastTotalSpace) {
            return 0;
        }
        return getTotalSpace() - this.mLastTotalSpace;
    }

    public abstract int getTransformedEndWithDecoration(View view);

    public abstract int getTransformedStartWithDecoration(View view);

    public abstract void offsetChild(View view, int i2);

    public abstract void offsetChildren(int i2);

    public void onLayoutComplete() {
        this.mLastTotalSpace = getTotalSpace();
    }

    private OrientationHelper(RecyclerView.LayoutManager layoutManager) {
        this.mLastTotalSpace = Integer.MIN_VALUE;
        this.f6014b = new Rect();
        this.f6013a = layoutManager;
    }
}
