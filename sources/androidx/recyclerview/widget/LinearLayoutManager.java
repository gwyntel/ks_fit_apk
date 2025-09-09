package androidx.recyclerview.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PointF;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

/* loaded from: classes2.dex */
public class LinearLayoutManager extends RecyclerView.LayoutManager implements ItemTouchHelper.ViewDropHandler, RecyclerView.SmoothScroller.ScrollVectorProvider {
    public static final int HORIZONTAL = 0;
    public static final int INVALID_OFFSET = Integer.MIN_VALUE;
    private static final float MAX_SCROLL_FACTOR = 0.33333334f;
    private static final String TAG = "LinearLayoutManager";
    public static final int VERTICAL = 1;

    /* renamed from: k, reason: collision with root package name */
    int f5964k;

    /* renamed from: l, reason: collision with root package name */
    OrientationHelper f5965l;

    /* renamed from: m, reason: collision with root package name */
    boolean f5966m;
    private int mInitialPrefetchItemCount;
    private boolean mLastStackFromEnd;
    private final LayoutChunkResult mLayoutChunkResult;
    private LayoutState mLayoutState;
    private boolean mRecycleChildrenOnDetach;
    private int[] mReusableIntPair;
    private boolean mReverseLayout;
    private boolean mSmoothScrollbarEnabled;
    private boolean mStackFromEnd;

    /* renamed from: n, reason: collision with root package name */
    int f5967n;

    /* renamed from: o, reason: collision with root package name */
    int f5968o;

    /* renamed from: p, reason: collision with root package name */
    SavedState f5969p;

    /* renamed from: q, reason: collision with root package name */
    final AnchorInfo f5970q;

    static class AnchorInfo {

        /* renamed from: a, reason: collision with root package name */
        OrientationHelper f5971a;

        /* renamed from: b, reason: collision with root package name */
        int f5972b;

        /* renamed from: c, reason: collision with root package name */
        int f5973c;

        /* renamed from: d, reason: collision with root package name */
        boolean f5974d;

        /* renamed from: e, reason: collision with root package name */
        boolean f5975e;

        AnchorInfo() {
            c();
        }

        void a() {
            this.f5973c = this.f5974d ? this.f5971a.getEndAfterPadding() : this.f5971a.getStartAfterPadding();
        }

        public void assignFromView(View view, int i2) {
            if (this.f5974d) {
                this.f5973c = this.f5971a.getDecoratedEnd(view) + this.f5971a.getTotalSpaceChange();
            } else {
                this.f5973c = this.f5971a.getDecoratedStart(view);
            }
            this.f5972b = i2;
        }

        public void assignFromViewAndKeepVisibleRect(View view, int i2) {
            int totalSpaceChange = this.f5971a.getTotalSpaceChange();
            if (totalSpaceChange >= 0) {
                assignFromView(view, i2);
                return;
            }
            this.f5972b = i2;
            if (this.f5974d) {
                int endAfterPadding = (this.f5971a.getEndAfterPadding() - totalSpaceChange) - this.f5971a.getDecoratedEnd(view);
                this.f5973c = this.f5971a.getEndAfterPadding() - endAfterPadding;
                if (endAfterPadding > 0) {
                    int decoratedMeasurement = this.f5973c - this.f5971a.getDecoratedMeasurement(view);
                    int startAfterPadding = this.f5971a.getStartAfterPadding();
                    int iMin = decoratedMeasurement - (startAfterPadding + Math.min(this.f5971a.getDecoratedStart(view) - startAfterPadding, 0));
                    if (iMin < 0) {
                        this.f5973c += Math.min(endAfterPadding, -iMin);
                        return;
                    }
                    return;
                }
                return;
            }
            int decoratedStart = this.f5971a.getDecoratedStart(view);
            int startAfterPadding2 = decoratedStart - this.f5971a.getStartAfterPadding();
            this.f5973c = decoratedStart;
            if (startAfterPadding2 > 0) {
                int endAfterPadding2 = (this.f5971a.getEndAfterPadding() - Math.min(0, (this.f5971a.getEndAfterPadding() - totalSpaceChange) - this.f5971a.getDecoratedEnd(view))) - (decoratedStart + this.f5971a.getDecoratedMeasurement(view));
                if (endAfterPadding2 < 0) {
                    this.f5973c -= Math.min(startAfterPadding2, -endAfterPadding2);
                }
            }
        }

        boolean b(View view, RecyclerView.State state) {
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
            return !layoutParams.isItemRemoved() && layoutParams.getViewLayoutPosition() >= 0 && layoutParams.getViewLayoutPosition() < state.getItemCount();
        }

        void c() {
            this.f5972b = -1;
            this.f5973c = Integer.MIN_VALUE;
            this.f5974d = false;
            this.f5975e = false;
        }

        public String toString() {
            return "AnchorInfo{mPosition=" + this.f5972b + ", mCoordinate=" + this.f5973c + ", mLayoutFromEnd=" + this.f5974d + ", mValid=" + this.f5975e + '}';
        }
    }

    protected static class LayoutChunkResult {
        public int mConsumed;
        public boolean mFinished;
        public boolean mFocusable;
        public boolean mIgnoreConsumed;

        protected LayoutChunkResult() {
        }

        void a() {
            this.mConsumed = 0;
            this.mFinished = false;
            this.mIgnoreConsumed = false;
            this.mFocusable = false;
        }
    }

    static class LayoutState {

        /* renamed from: b, reason: collision with root package name */
        int f5977b;

        /* renamed from: c, reason: collision with root package name */
        int f5978c;

        /* renamed from: d, reason: collision with root package name */
        int f5979d;

        /* renamed from: e, reason: collision with root package name */
        int f5980e;

        /* renamed from: f, reason: collision with root package name */
        int f5981f;

        /* renamed from: g, reason: collision with root package name */
        int f5982g;

        /* renamed from: k, reason: collision with root package name */
        int f5986k;

        /* renamed from: m, reason: collision with root package name */
        boolean f5988m;

        /* renamed from: a, reason: collision with root package name */
        boolean f5976a = true;

        /* renamed from: h, reason: collision with root package name */
        int f5983h = 0;

        /* renamed from: i, reason: collision with root package name */
        int f5984i = 0;

        /* renamed from: j, reason: collision with root package name */
        boolean f5985j = false;

        /* renamed from: l, reason: collision with root package name */
        List f5987l = null;

        LayoutState() {
        }

        private View nextViewFromScrapList() {
            int size = this.f5987l.size();
            for (int i2 = 0; i2 < size; i2++) {
                View view = ((RecyclerView.ViewHolder) this.f5987l.get(i2)).itemView;
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
                if (!layoutParams.isItemRemoved() && this.f5979d == layoutParams.getViewLayoutPosition()) {
                    assignPositionFromScrapList(view);
                    return view;
                }
            }
            return null;
        }

        boolean a(RecyclerView.State state) {
            int i2 = this.f5979d;
            return i2 >= 0 && i2 < state.getItemCount();
        }

        public void assignPositionFromScrapList() {
            assignPositionFromScrapList(null);
        }

        View b(RecyclerView.Recycler recycler) {
            if (this.f5987l != null) {
                return nextViewFromScrapList();
            }
            View viewForPosition = recycler.getViewForPosition(this.f5979d);
            this.f5979d += this.f5980e;
            return viewForPosition;
        }

        public View nextViewInLimitedList(View view) {
            int viewLayoutPosition;
            int size = this.f5987l.size();
            View view2 = null;
            int i2 = Integer.MAX_VALUE;
            for (int i3 = 0; i3 < size; i3++) {
                View view3 = ((RecyclerView.ViewHolder) this.f5987l.get(i3)).itemView;
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view3.getLayoutParams();
                if (view3 != view && !layoutParams.isItemRemoved() && (viewLayoutPosition = (layoutParams.getViewLayoutPosition() - this.f5979d) * this.f5980e) >= 0 && viewLayoutPosition < i2) {
                    view2 = view3;
                    if (viewLayoutPosition == 0) {
                        break;
                    }
                    i2 = viewLayoutPosition;
                }
            }
            return view2;
        }

        public void assignPositionFromScrapList(View view) {
            View viewNextViewInLimitedList = nextViewInLimitedList(view);
            if (viewNextViewInLimitedList == null) {
                this.f5979d = -1;
            } else {
                this.f5979d = ((RecyclerView.LayoutParams) viewNextViewInLimitedList.getLayoutParams()).getViewLayoutPosition();
            }
        }
    }

    @SuppressLint({"BanParcelableUsage"})
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static class SavedState implements Parcelable {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: androidx.recyclerview.widget.LinearLayoutManager.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int i2) {
                return new SavedState[i2];
            }
        };

        /* renamed from: a, reason: collision with root package name */
        int f5989a;

        /* renamed from: b, reason: collision with root package name */
        int f5990b;

        /* renamed from: c, reason: collision with root package name */
        boolean f5991c;

        public SavedState() {
        }

        boolean a() {
            return this.f5989a >= 0;
        }

        void b() {
            this.f5989a = -1;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i2) {
            parcel.writeInt(this.f5989a);
            parcel.writeInt(this.f5990b);
            parcel.writeInt(this.f5991c ? 1 : 0);
        }

        SavedState(Parcel parcel) {
            this.f5989a = parcel.readInt();
            this.f5990b = parcel.readInt();
            this.f5991c = parcel.readInt() == 1;
        }

        public SavedState(SavedState savedState) {
            this.f5989a = savedState.f5989a;
            this.f5990b = savedState.f5990b;
            this.f5991c = savedState.f5991c;
        }
    }

    public LinearLayoutManager(Context context) {
        this(context, 1, false);
    }

    private int computeScrollExtent(RecyclerView.State state) {
        if (getChildCount() == 0) {
            return 0;
        }
        u();
        return ScrollbarHelper.a(state, this.f5965l, x(!this.mSmoothScrollbarEnabled, true), w(!this.mSmoothScrollbarEnabled, true), this, this.mSmoothScrollbarEnabled);
    }

    private int computeScrollOffset(RecyclerView.State state) {
        if (getChildCount() == 0) {
            return 0;
        }
        u();
        return ScrollbarHelper.b(state, this.f5965l, x(!this.mSmoothScrollbarEnabled, true), w(!this.mSmoothScrollbarEnabled, true), this, this.mSmoothScrollbarEnabled, this.f5966m);
    }

    private int computeScrollRange(RecyclerView.State state) {
        if (getChildCount() == 0) {
            return 0;
        }
        u();
        return ScrollbarHelper.c(state, this.f5965l, x(!this.mSmoothScrollbarEnabled, true), w(!this.mSmoothScrollbarEnabled, true), this, this.mSmoothScrollbarEnabled);
    }

    private View findFirstPartiallyOrCompletelyInvisibleChild() {
        return y(0, getChildCount());
    }

    private View findLastPartiallyOrCompletelyInvisibleChild() {
        return y(getChildCount() - 1, -1);
    }

    private View findPartiallyOrCompletelyInvisibleChildClosestToEnd() {
        return this.f5966m ? findFirstPartiallyOrCompletelyInvisibleChild() : findLastPartiallyOrCompletelyInvisibleChild();
    }

    private View findPartiallyOrCompletelyInvisibleChildClosestToStart() {
        return this.f5966m ? findLastPartiallyOrCompletelyInvisibleChild() : findFirstPartiallyOrCompletelyInvisibleChild();
    }

    private int fixLayoutEndGap(int i2, RecyclerView.Recycler recycler, RecyclerView.State state, boolean z2) {
        int endAfterPadding;
        int endAfterPadding2 = this.f5965l.getEndAfterPadding() - i2;
        if (endAfterPadding2 <= 0) {
            return 0;
        }
        int i3 = -G(-endAfterPadding2, recycler, state);
        int i4 = i2 + i3;
        if (!z2 || (endAfterPadding = this.f5965l.getEndAfterPadding() - i4) <= 0) {
            return i3;
        }
        this.f5965l.offsetChildren(endAfterPadding);
        return endAfterPadding + i3;
    }

    private int fixLayoutStartGap(int i2, RecyclerView.Recycler recycler, RecyclerView.State state, boolean z2) {
        int startAfterPadding;
        int startAfterPadding2 = i2 - this.f5965l.getStartAfterPadding();
        if (startAfterPadding2 <= 0) {
            return 0;
        }
        int i3 = -G(startAfterPadding2, recycler, state);
        int i4 = i2 + i3;
        if (!z2 || (startAfterPadding = i4 - this.f5965l.getStartAfterPadding()) <= 0) {
            return i3;
        }
        this.f5965l.offsetChildren(-startAfterPadding);
        return i3 - startAfterPadding;
    }

    private View getChildClosestToEnd() {
        return getChildAt(this.f5966m ? 0 : getChildCount() - 1);
    }

    private View getChildClosestToStart() {
        return getChildAt(this.f5966m ? getChildCount() - 1 : 0);
    }

    private void layoutForPredictiveAnimations(RecyclerView.Recycler recycler, RecyclerView.State state, int i2, int i3) {
        if (!state.willRunPredictiveAnimations() || getChildCount() == 0 || state.isPreLayout() || !supportsPredictiveItemAnimations()) {
            return;
        }
        List<RecyclerView.ViewHolder> scrapList = recycler.getScrapList();
        int size = scrapList.size();
        int position = getPosition(getChildAt(0));
        int decoratedMeasurement = 0;
        int decoratedMeasurement2 = 0;
        for (int i4 = 0; i4 < size; i4++) {
            RecyclerView.ViewHolder viewHolder = scrapList.get(i4);
            if (!viewHolder.isRemoved()) {
                if ((viewHolder.getLayoutPosition() < position) != this.f5966m) {
                    decoratedMeasurement += this.f5965l.getDecoratedMeasurement(viewHolder.itemView);
                } else {
                    decoratedMeasurement2 += this.f5965l.getDecoratedMeasurement(viewHolder.itemView);
                }
            }
        }
        this.mLayoutState.f5987l = scrapList;
        if (decoratedMeasurement > 0) {
            updateLayoutStateToFillStart(getPosition(getChildClosestToStart()), i2);
            LayoutState layoutState = this.mLayoutState;
            layoutState.f5983h = decoratedMeasurement;
            layoutState.f5978c = 0;
            layoutState.assignPositionFromScrapList();
            v(recycler, this.mLayoutState, state, false);
        }
        if (decoratedMeasurement2 > 0) {
            updateLayoutStateToFillEnd(getPosition(getChildClosestToEnd()), i3);
            LayoutState layoutState2 = this.mLayoutState;
            layoutState2.f5983h = decoratedMeasurement2;
            layoutState2.f5978c = 0;
            layoutState2.assignPositionFromScrapList();
            v(recycler, this.mLayoutState, state, false);
        }
        this.mLayoutState.f5987l = null;
    }

    private void logChildren() {
        Log.d(TAG, "internal representation of views on the screen");
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            View childAt = getChildAt(i2);
            Log.d(TAG, "item " + getPosition(childAt) + ", coord:" + this.f5965l.getDecoratedStart(childAt));
        }
        Log.d(TAG, "==============");
    }

    private void recycleByLayoutState(RecyclerView.Recycler recycler, LayoutState layoutState) {
        if (!layoutState.f5976a || layoutState.f5988m) {
            return;
        }
        int i2 = layoutState.f5982g;
        int i3 = layoutState.f5984i;
        if (layoutState.f5981f == -1) {
            recycleViewsFromEnd(recycler, i2, i3);
        } else {
            recycleViewsFromStart(recycler, i2, i3);
        }
    }

    private void recycleChildren(RecyclerView.Recycler recycler, int i2, int i3) {
        if (i2 == i3) {
            return;
        }
        if (i3 <= i2) {
            while (i2 > i3) {
                removeAndRecycleViewAt(i2, recycler);
                i2--;
            }
        } else {
            for (int i4 = i3 - 1; i4 >= i2; i4--) {
                removeAndRecycleViewAt(i4, recycler);
            }
        }
    }

    private void recycleViewsFromEnd(RecyclerView.Recycler recycler, int i2, int i3) {
        int childCount = getChildCount();
        if (i2 < 0) {
            return;
        }
        int end = (this.f5965l.getEnd() - i2) + i3;
        if (this.f5966m) {
            for (int i4 = 0; i4 < childCount; i4++) {
                View childAt = getChildAt(i4);
                if (this.f5965l.getDecoratedStart(childAt) < end || this.f5965l.getTransformedStartWithDecoration(childAt) < end) {
                    recycleChildren(recycler, 0, i4);
                    return;
                }
            }
            return;
        }
        int i5 = childCount - 1;
        for (int i6 = i5; i6 >= 0; i6--) {
            View childAt2 = getChildAt(i6);
            if (this.f5965l.getDecoratedStart(childAt2) < end || this.f5965l.getTransformedStartWithDecoration(childAt2) < end) {
                recycleChildren(recycler, i5, i6);
                return;
            }
        }
    }

    private void recycleViewsFromStart(RecyclerView.Recycler recycler, int i2, int i3) {
        if (i2 < 0) {
            return;
        }
        int i4 = i2 - i3;
        int childCount = getChildCount();
        if (!this.f5966m) {
            for (int i5 = 0; i5 < childCount; i5++) {
                View childAt = getChildAt(i5);
                if (this.f5965l.getDecoratedEnd(childAt) > i4 || this.f5965l.getTransformedEndWithDecoration(childAt) > i4) {
                    recycleChildren(recycler, 0, i5);
                    return;
                }
            }
            return;
        }
        int i6 = childCount - 1;
        for (int i7 = i6; i7 >= 0; i7--) {
            View childAt2 = getChildAt(i7);
            if (this.f5965l.getDecoratedEnd(childAt2) > i4 || this.f5965l.getTransformedEndWithDecoration(childAt2) > i4) {
                recycleChildren(recycler, i6, i7);
                return;
            }
        }
    }

    private void resolveShouldLayoutReverse() {
        if (this.f5964k == 1 || !C()) {
            this.f5966m = this.mReverseLayout;
        } else {
            this.f5966m = !this.mReverseLayout;
        }
    }

    private boolean updateAnchorFromChildren(RecyclerView.Recycler recycler, RecyclerView.State state, AnchorInfo anchorInfo) {
        View viewA;
        boolean z2 = false;
        if (getChildCount() == 0) {
            return false;
        }
        View focusedChild = getFocusedChild();
        if (focusedChild != null && anchorInfo.b(focusedChild, state)) {
            anchorInfo.assignFromViewAndKeepVisibleRect(focusedChild, getPosition(focusedChild));
            return true;
        }
        boolean z3 = this.mLastStackFromEnd;
        boolean z4 = this.mStackFromEnd;
        if (z3 != z4 || (viewA = A(recycler, state, anchorInfo.f5974d, z4)) == null) {
            return false;
        }
        anchorInfo.assignFromView(viewA, getPosition(viewA));
        if (!state.isPreLayout() && supportsPredictiveItemAnimations()) {
            int decoratedStart = this.f5965l.getDecoratedStart(viewA);
            int decoratedEnd = this.f5965l.getDecoratedEnd(viewA);
            int startAfterPadding = this.f5965l.getStartAfterPadding();
            int endAfterPadding = this.f5965l.getEndAfterPadding();
            boolean z5 = decoratedEnd <= startAfterPadding && decoratedStart < startAfterPadding;
            if (decoratedStart >= endAfterPadding && decoratedEnd > endAfterPadding) {
                z2 = true;
            }
            if (z5 || z2) {
                if (anchorInfo.f5974d) {
                    startAfterPadding = endAfterPadding;
                }
                anchorInfo.f5973c = startAfterPadding;
            }
        }
        return true;
    }

    private boolean updateAnchorFromPendingData(RecyclerView.State state, AnchorInfo anchorInfo) {
        int i2;
        if (!state.isPreLayout() && (i2 = this.f5967n) != -1) {
            if (i2 >= 0 && i2 < state.getItemCount()) {
                anchorInfo.f5972b = this.f5967n;
                SavedState savedState = this.f5969p;
                if (savedState != null && savedState.a()) {
                    boolean z2 = this.f5969p.f5991c;
                    anchorInfo.f5974d = z2;
                    if (z2) {
                        anchorInfo.f5973c = this.f5965l.getEndAfterPadding() - this.f5969p.f5990b;
                    } else {
                        anchorInfo.f5973c = this.f5965l.getStartAfterPadding() + this.f5969p.f5990b;
                    }
                    return true;
                }
                if (this.f5968o != Integer.MIN_VALUE) {
                    boolean z3 = this.f5966m;
                    anchorInfo.f5974d = z3;
                    if (z3) {
                        anchorInfo.f5973c = this.f5965l.getEndAfterPadding() - this.f5968o;
                    } else {
                        anchorInfo.f5973c = this.f5965l.getStartAfterPadding() + this.f5968o;
                    }
                    return true;
                }
                View viewFindViewByPosition = findViewByPosition(this.f5967n);
                if (viewFindViewByPosition == null) {
                    if (getChildCount() > 0) {
                        anchorInfo.f5974d = (this.f5967n < getPosition(getChildAt(0))) == this.f5966m;
                    }
                    anchorInfo.a();
                } else {
                    if (this.f5965l.getDecoratedMeasurement(viewFindViewByPosition) > this.f5965l.getTotalSpace()) {
                        anchorInfo.a();
                        return true;
                    }
                    if (this.f5965l.getDecoratedStart(viewFindViewByPosition) - this.f5965l.getStartAfterPadding() < 0) {
                        anchorInfo.f5973c = this.f5965l.getStartAfterPadding();
                        anchorInfo.f5974d = false;
                        return true;
                    }
                    if (this.f5965l.getEndAfterPadding() - this.f5965l.getDecoratedEnd(viewFindViewByPosition) < 0) {
                        anchorInfo.f5973c = this.f5965l.getEndAfterPadding();
                        anchorInfo.f5974d = true;
                        return true;
                    }
                    anchorInfo.f5973c = anchorInfo.f5974d ? this.f5965l.getDecoratedEnd(viewFindViewByPosition) + this.f5965l.getTotalSpaceChange() : this.f5965l.getDecoratedStart(viewFindViewByPosition);
                }
                return true;
            }
            this.f5967n = -1;
            this.f5968o = Integer.MIN_VALUE;
        }
        return false;
    }

    private void updateAnchorInfoForLayout(RecyclerView.Recycler recycler, RecyclerView.State state, AnchorInfo anchorInfo) {
        if (updateAnchorFromPendingData(state, anchorInfo) || updateAnchorFromChildren(recycler, state, anchorInfo)) {
            return;
        }
        anchorInfo.a();
        anchorInfo.f5972b = this.mStackFromEnd ? state.getItemCount() - 1 : 0;
    }

    private void updateLayoutState(int i2, int i3, boolean z2, RecyclerView.State state) {
        int startAfterPadding;
        this.mLayoutState.f5988m = F();
        this.mLayoutState.f5981f = i2;
        int[] iArr = this.mReusableIntPair;
        iArr[0] = 0;
        iArr[1] = 0;
        r(state, iArr);
        int iMax = Math.max(0, this.mReusableIntPair[0]);
        int iMax2 = Math.max(0, this.mReusableIntPair[1]);
        boolean z3 = i2 == 1;
        LayoutState layoutState = this.mLayoutState;
        int i4 = z3 ? iMax2 : iMax;
        layoutState.f5983h = i4;
        if (!z3) {
            iMax = iMax2;
        }
        layoutState.f5984i = iMax;
        if (z3) {
            layoutState.f5983h = i4 + this.f5965l.getEndPadding();
            View childClosestToEnd = getChildClosestToEnd();
            LayoutState layoutState2 = this.mLayoutState;
            layoutState2.f5980e = this.f5966m ? -1 : 1;
            int position = getPosition(childClosestToEnd);
            LayoutState layoutState3 = this.mLayoutState;
            layoutState2.f5979d = position + layoutState3.f5980e;
            layoutState3.f5977b = this.f5965l.getDecoratedEnd(childClosestToEnd);
            startAfterPadding = this.f5965l.getDecoratedEnd(childClosestToEnd) - this.f5965l.getEndAfterPadding();
        } else {
            View childClosestToStart = getChildClosestToStart();
            this.mLayoutState.f5983h += this.f5965l.getStartAfterPadding();
            LayoutState layoutState4 = this.mLayoutState;
            layoutState4.f5980e = this.f5966m ? 1 : -1;
            int position2 = getPosition(childClosestToStart);
            LayoutState layoutState5 = this.mLayoutState;
            layoutState4.f5979d = position2 + layoutState5.f5980e;
            layoutState5.f5977b = this.f5965l.getDecoratedStart(childClosestToStart);
            startAfterPadding = (-this.f5965l.getDecoratedStart(childClosestToStart)) + this.f5965l.getStartAfterPadding();
        }
        LayoutState layoutState6 = this.mLayoutState;
        layoutState6.f5978c = i3;
        if (z2) {
            layoutState6.f5978c = i3 - startAfterPadding;
        }
        layoutState6.f5982g = startAfterPadding;
    }

    private void updateLayoutStateToFillEnd(AnchorInfo anchorInfo) {
        updateLayoutStateToFillEnd(anchorInfo.f5972b, anchorInfo.f5973c);
    }

    private void updateLayoutStateToFillStart(AnchorInfo anchorInfo) {
        updateLayoutStateToFillStart(anchorInfo.f5972b, anchorInfo.f5973c);
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x0073  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0077  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    android.view.View A(androidx.recyclerview.widget.RecyclerView.Recycler r17, androidx.recyclerview.widget.RecyclerView.State r18, boolean r19, boolean r20) {
        /*
            r16 = this;
            r0 = r16
            r16.u()
            int r1 = r16.getChildCount()
            r2 = 0
            r3 = 1
            if (r20 == 0) goto L15
            int r1 = r16.getChildCount()
            int r1 = r1 - r3
            r4 = -1
            r5 = r4
            goto L18
        L15:
            r4 = r1
            r1 = r2
            r5 = r3
        L18:
            int r6 = r18.getItemCount()
            androidx.recyclerview.widget.OrientationHelper r7 = r0.f5965l
            int r7 = r7.getStartAfterPadding()
            androidx.recyclerview.widget.OrientationHelper r8 = r0.f5965l
            int r8 = r8.getEndAfterPadding()
            r9 = 0
            r10 = r9
            r11 = r10
        L2b:
            if (r1 == r4) goto L7a
            android.view.View r12 = r0.getChildAt(r1)
            int r13 = r0.getPosition(r12)
            androidx.recyclerview.widget.OrientationHelper r14 = r0.f5965l
            int r14 = r14.getDecoratedStart(r12)
            androidx.recyclerview.widget.OrientationHelper r15 = r0.f5965l
            int r15 = r15.getDecoratedEnd(r12)
            if (r13 < 0) goto L78
            if (r13 >= r6) goto L78
            android.view.ViewGroup$LayoutParams r13 = r12.getLayoutParams()
            androidx.recyclerview.widget.RecyclerView$LayoutParams r13 = (androidx.recyclerview.widget.RecyclerView.LayoutParams) r13
            boolean r13 = r13.isItemRemoved()
            if (r13 == 0) goto L55
            if (r11 != 0) goto L78
            r11 = r12
            goto L78
        L55:
            if (r15 > r7) goto L5b
            if (r14 >= r7) goto L5b
            r13 = r3
            goto L5c
        L5b:
            r13 = r2
        L5c:
            if (r14 < r8) goto L62
            if (r15 <= r8) goto L62
            r14 = r3
            goto L63
        L62:
            r14 = r2
        L63:
            if (r13 != 0) goto L69
            if (r14 == 0) goto L68
            goto L69
        L68:
            return r12
        L69:
            if (r19 == 0) goto L71
            if (r14 == 0) goto L6e
            goto L73
        L6e:
            if (r9 != 0) goto L78
            goto L77
        L71:
            if (r13 == 0) goto L75
        L73:
            r10 = r12
            goto L78
        L75:
            if (r9 != 0) goto L78
        L77:
            r9 = r12
        L78:
            int r1 = r1 + r5
            goto L2b
        L7a:
            if (r9 == 0) goto L7d
            goto L82
        L7d:
            if (r10 == 0) goto L81
            r9 = r10
            goto L82
        L81:
            r9 = r11
        L82:
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.LinearLayoutManager.A(androidx.recyclerview.widget.RecyclerView$Recycler, androidx.recyclerview.widget.RecyclerView$State, boolean, boolean):android.view.View");
    }

    protected int B(RecyclerView.State state) {
        if (state.hasTargetScrollPosition()) {
            return this.f5965l.getTotalSpace();
        }
        return 0;
    }

    protected boolean C() {
        return getLayoutDirection() == 1;
    }

    void D(RecyclerView.Recycler recycler, RecyclerView.State state, LayoutState layoutState, LayoutChunkResult layoutChunkResult) {
        int i2;
        int i3;
        int i4;
        int paddingLeft;
        int decoratedMeasurementInOther;
        View viewB = layoutState.b(recycler);
        if (viewB == null) {
            layoutChunkResult.mFinished = true;
            return;
        }
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) viewB.getLayoutParams();
        if (layoutState.f5987l == null) {
            if (this.f5966m == (layoutState.f5981f == -1)) {
                addView(viewB);
            } else {
                addView(viewB, 0);
            }
        } else {
            if (this.f5966m == (layoutState.f5981f == -1)) {
                addDisappearingView(viewB);
            } else {
                addDisappearingView(viewB, 0);
            }
        }
        measureChildWithMargins(viewB, 0, 0);
        layoutChunkResult.mConsumed = this.f5965l.getDecoratedMeasurement(viewB);
        if (this.f5964k == 1) {
            if (C()) {
                decoratedMeasurementInOther = getWidth() - getPaddingRight();
                paddingLeft = decoratedMeasurementInOther - this.f5965l.getDecoratedMeasurementInOther(viewB);
            } else {
                paddingLeft = getPaddingLeft();
                decoratedMeasurementInOther = this.f5965l.getDecoratedMeasurementInOther(viewB) + paddingLeft;
            }
            if (layoutState.f5981f == -1) {
                int i5 = layoutState.f5977b;
                i4 = i5;
                i3 = decoratedMeasurementInOther;
                i2 = i5 - layoutChunkResult.mConsumed;
            } else {
                int i6 = layoutState.f5977b;
                i2 = i6;
                i3 = decoratedMeasurementInOther;
                i4 = layoutChunkResult.mConsumed + i6;
            }
        } else {
            int paddingTop = getPaddingTop();
            int decoratedMeasurementInOther2 = this.f5965l.getDecoratedMeasurementInOther(viewB) + paddingTop;
            if (layoutState.f5981f == -1) {
                int i7 = layoutState.f5977b;
                i3 = i7;
                i2 = paddingTop;
                i4 = decoratedMeasurementInOther2;
                paddingLeft = i7 - layoutChunkResult.mConsumed;
            } else {
                int i8 = layoutState.f5977b;
                i2 = paddingTop;
                i3 = layoutChunkResult.mConsumed + i8;
                i4 = decoratedMeasurementInOther2;
                paddingLeft = i8;
            }
        }
        layoutDecoratedWithMargins(viewB, paddingLeft, i2, i3, i4);
        if (layoutParams.isItemRemoved() || layoutParams.isItemChanged()) {
            layoutChunkResult.mIgnoreConsumed = true;
        }
        layoutChunkResult.mFocusable = viewB.hasFocusable();
    }

    void E(RecyclerView.Recycler recycler, RecyclerView.State state, AnchorInfo anchorInfo, int i2) {
    }

    boolean F() {
        return this.f5965l.getMode() == 0 && this.f5965l.getEnd() == 0;
    }

    int G(int i2, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getChildCount() == 0 || i2 == 0) {
            return 0;
        }
        u();
        this.mLayoutState.f5976a = true;
        int i3 = i2 > 0 ? 1 : -1;
        int iAbs = Math.abs(i2);
        updateLayoutState(i3, iAbs, true, state);
        LayoutState layoutState = this.mLayoutState;
        int iV = layoutState.f5982g + v(recycler, layoutState, state, false);
        if (iV < 0) {
            return 0;
        }
        if (iAbs > iV) {
            i2 = i3 * iV;
        }
        this.f5965l.offsetChildren(-i2);
        this.mLayoutState.f5986k = i2;
        return i2;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void assertNotInLayoutOrScroll(String str) {
        if (this.f5969p == null) {
            super.assertNotInLayoutOrScroll(str);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public boolean canScrollHorizontally() {
        return this.f5964k == 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public boolean canScrollVertically() {
        return this.f5964k == 1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void collectAdjacentPrefetchPositions(int i2, int i3, RecyclerView.State state, RecyclerView.LayoutManager.LayoutPrefetchRegistry layoutPrefetchRegistry) {
        if (this.f5964k != 0) {
            i2 = i3;
        }
        if (getChildCount() == 0 || i2 == 0) {
            return;
        }
        u();
        updateLayoutState(i2 > 0 ? 1 : -1, Math.abs(i2), true, state);
        s(state, this.mLayoutState, layoutPrefetchRegistry);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void collectInitialPrefetchPositions(int i2, RecyclerView.LayoutManager.LayoutPrefetchRegistry layoutPrefetchRegistry) {
        boolean z2;
        int i3;
        SavedState savedState = this.f5969p;
        if (savedState == null || !savedState.a()) {
            resolveShouldLayoutReverse();
            z2 = this.f5966m;
            i3 = this.f5967n;
            if (i3 == -1) {
                i3 = z2 ? i2 - 1 : 0;
            }
        } else {
            SavedState savedState2 = this.f5969p;
            z2 = savedState2.f5991c;
            i3 = savedState2.f5989a;
        }
        int i4 = z2 ? -1 : 1;
        for (int i5 = 0; i5 < this.mInitialPrefetchItemCount && i3 >= 0 && i3 < i2; i5++) {
            layoutPrefetchRegistry.addPosition(i3, 0);
            i3 += i4;
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int computeHorizontalScrollExtent(RecyclerView.State state) {
        return computeScrollExtent(state);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int computeHorizontalScrollOffset(RecyclerView.State state) {
        return computeScrollOffset(state);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int computeHorizontalScrollRange(RecyclerView.State state) {
        return computeScrollRange(state);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.SmoothScroller.ScrollVectorProvider
    public PointF computeScrollVectorForPosition(int i2) {
        if (getChildCount() == 0) {
            return null;
        }
        int i3 = (i2 < getPosition(getChildAt(0))) != this.f5966m ? -1 : 1;
        return this.f5964k == 0 ? new PointF(i3, 0.0f) : new PointF(0.0f, i3);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int computeVerticalScrollExtent(RecyclerView.State state) {
        return computeScrollExtent(state);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int computeVerticalScrollOffset(RecyclerView.State state) {
        return computeScrollOffset(state);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int computeVerticalScrollRange(RecyclerView.State state) {
        return computeScrollRange(state);
    }

    int convertFocusDirectionToLayoutDirection(int i2) {
        return i2 != 1 ? i2 != 2 ? i2 != 17 ? i2 != 33 ? i2 != 66 ? (i2 == 130 && this.f5964k == 1) ? 1 : Integer.MIN_VALUE : this.f5964k == 0 ? 1 : Integer.MIN_VALUE : this.f5964k == 1 ? -1 : Integer.MIN_VALUE : this.f5964k == 0 ? -1 : Integer.MIN_VALUE : (this.f5964k != 1 && C()) ? -1 : 1 : (this.f5964k != 1 && C()) ? 1 : -1;
    }

    public int findFirstCompletelyVisibleItemPosition() {
        View viewZ = z(0, getChildCount(), true, false);
        if (viewZ == null) {
            return -1;
        }
        return getPosition(viewZ);
    }

    public int findFirstVisibleItemPosition() {
        View viewZ = z(0, getChildCount(), false, true);
        if (viewZ == null) {
            return -1;
        }
        return getPosition(viewZ);
    }

    public int findLastCompletelyVisibleItemPosition() {
        View viewZ = z(getChildCount() - 1, -1, true, false);
        if (viewZ == null) {
            return -1;
        }
        return getPosition(viewZ);
    }

    public int findLastVisibleItemPosition() {
        View viewZ = z(getChildCount() - 1, -1, false, true);
        if (viewZ == null) {
            return -1;
        }
        return getPosition(viewZ);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public View findViewByPosition(int i2) {
        int childCount = getChildCount();
        if (childCount == 0) {
            return null;
        }
        int position = i2 - getPosition(getChildAt(0));
        if (position >= 0 && position < childCount) {
            View childAt = getChildAt(position);
            if (getPosition(childAt) == i2) {
                return childAt;
            }
        }
        return super.findViewByPosition(i2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(-2, -2);
    }

    public int getInitialPrefetchItemCount() {
        return this.mInitialPrefetchItemCount;
    }

    public int getOrientation() {
        return this.f5964k;
    }

    public boolean getRecycleChildrenOnDetach() {
        return this.mRecycleChildrenOnDetach;
    }

    public boolean getReverseLayout() {
        return this.mReverseLayout;
    }

    public boolean getStackFromEnd() {
        return this.mStackFromEnd;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public boolean isAutoMeasureEnabled() {
        return true;
    }

    public boolean isSmoothScrollbarEnabled() {
        return this.mSmoothScrollbarEnabled;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    boolean o() {
        return (getHeightMode() == 1073741824 || getWidthMode() == 1073741824 || !c()) ? false : true;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onDetachedFromWindow(RecyclerView recyclerView, RecyclerView.Recycler recycler) {
        super.onDetachedFromWindow(recyclerView, recycler);
        if (this.mRecycleChildrenOnDetach) {
            removeAndRecycleAllViews(recycler);
            recycler.clear();
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public View onFocusSearchFailed(View view, int i2, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int iConvertFocusDirectionToLayoutDirection;
        resolveShouldLayoutReverse();
        if (getChildCount() == 0 || (iConvertFocusDirectionToLayoutDirection = convertFocusDirectionToLayoutDirection(i2)) == Integer.MIN_VALUE) {
            return null;
        }
        u();
        updateLayoutState(iConvertFocusDirectionToLayoutDirection, (int) (this.f5965l.getTotalSpace() * MAX_SCROLL_FACTOR), false, state);
        LayoutState layoutState = this.mLayoutState;
        layoutState.f5982g = Integer.MIN_VALUE;
        layoutState.f5976a = false;
        v(recycler, layoutState, state, true);
        View viewFindPartiallyOrCompletelyInvisibleChildClosestToStart = iConvertFocusDirectionToLayoutDirection == -1 ? findPartiallyOrCompletelyInvisibleChildClosestToStart() : findPartiallyOrCompletelyInvisibleChildClosestToEnd();
        View childClosestToStart = iConvertFocusDirectionToLayoutDirection == -1 ? getChildClosestToStart() : getChildClosestToEnd();
        if (!childClosestToStart.hasFocusable()) {
            return viewFindPartiallyOrCompletelyInvisibleChildClosestToStart;
        }
        if (viewFindPartiallyOrCompletelyInvisibleChildClosestToStart == null) {
            return null;
        }
        return childClosestToStart;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        if (getChildCount() > 0) {
            accessibilityEvent.setFromIndex(findFirstVisibleItemPosition());
            accessibilityEvent.setToIndex(findLastVisibleItemPosition());
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        int i2;
        int i3;
        int i4;
        int i5;
        int iFixLayoutEndGap;
        int i6;
        View viewFindViewByPosition;
        int decoratedStart;
        int endAfterPadding;
        int i7 = -1;
        if (!(this.f5969p == null && this.f5967n == -1) && state.getItemCount() == 0) {
            removeAndRecycleAllViews(recycler);
            return;
        }
        SavedState savedState = this.f5969p;
        if (savedState != null && savedState.a()) {
            this.f5967n = this.f5969p.f5989a;
        }
        u();
        this.mLayoutState.f5976a = false;
        resolveShouldLayoutReverse();
        View focusedChild = getFocusedChild();
        AnchorInfo anchorInfo = this.f5970q;
        if (!anchorInfo.f5975e || this.f5967n != -1 || this.f5969p != null) {
            anchorInfo.c();
            AnchorInfo anchorInfo2 = this.f5970q;
            anchorInfo2.f5974d = this.f5966m ^ this.mStackFromEnd;
            updateAnchorInfoForLayout(recycler, state, anchorInfo2);
            this.f5970q.f5975e = true;
        } else if (focusedChild != null && (this.f5965l.getDecoratedStart(focusedChild) >= this.f5965l.getEndAfterPadding() || this.f5965l.getDecoratedEnd(focusedChild) <= this.f5965l.getStartAfterPadding())) {
            this.f5970q.assignFromViewAndKeepVisibleRect(focusedChild, getPosition(focusedChild));
        }
        LayoutState layoutState = this.mLayoutState;
        layoutState.f5981f = layoutState.f5986k >= 0 ? 1 : -1;
        int[] iArr = this.mReusableIntPair;
        iArr[0] = 0;
        iArr[1] = 0;
        r(state, iArr);
        int iMax = Math.max(0, this.mReusableIntPair[0]) + this.f5965l.getStartAfterPadding();
        int iMax2 = Math.max(0, this.mReusableIntPair[1]) + this.f5965l.getEndPadding();
        if (state.isPreLayout() && (i6 = this.f5967n) != -1 && this.f5968o != Integer.MIN_VALUE && (viewFindViewByPosition = findViewByPosition(i6)) != null) {
            if (this.f5966m) {
                endAfterPadding = this.f5965l.getEndAfterPadding() - this.f5965l.getDecoratedEnd(viewFindViewByPosition);
                decoratedStart = this.f5968o;
            } else {
                decoratedStart = this.f5965l.getDecoratedStart(viewFindViewByPosition) - this.f5965l.getStartAfterPadding();
                endAfterPadding = this.f5968o;
            }
            int i8 = endAfterPadding - decoratedStart;
            if (i8 > 0) {
                iMax += i8;
            } else {
                iMax2 -= i8;
            }
        }
        AnchorInfo anchorInfo3 = this.f5970q;
        if (!anchorInfo3.f5974d ? !this.f5966m : this.f5966m) {
            i7 = 1;
        }
        E(recycler, state, anchorInfo3, i7);
        detachAndScrapAttachedViews(recycler);
        this.mLayoutState.f5988m = F();
        this.mLayoutState.f5985j = state.isPreLayout();
        this.mLayoutState.f5984i = 0;
        AnchorInfo anchorInfo4 = this.f5970q;
        if (anchorInfo4.f5974d) {
            updateLayoutStateToFillStart(anchorInfo4);
            LayoutState layoutState2 = this.mLayoutState;
            layoutState2.f5983h = iMax;
            v(recycler, layoutState2, state, false);
            LayoutState layoutState3 = this.mLayoutState;
            i3 = layoutState3.f5977b;
            int i9 = layoutState3.f5979d;
            int i10 = layoutState3.f5978c;
            if (i10 > 0) {
                iMax2 += i10;
            }
            updateLayoutStateToFillEnd(this.f5970q);
            LayoutState layoutState4 = this.mLayoutState;
            layoutState4.f5983h = iMax2;
            layoutState4.f5979d += layoutState4.f5980e;
            v(recycler, layoutState4, state, false);
            LayoutState layoutState5 = this.mLayoutState;
            i2 = layoutState5.f5977b;
            int i11 = layoutState5.f5978c;
            if (i11 > 0) {
                updateLayoutStateToFillStart(i9, i3);
                LayoutState layoutState6 = this.mLayoutState;
                layoutState6.f5983h = i11;
                v(recycler, layoutState6, state, false);
                i3 = this.mLayoutState.f5977b;
            }
        } else {
            updateLayoutStateToFillEnd(anchorInfo4);
            LayoutState layoutState7 = this.mLayoutState;
            layoutState7.f5983h = iMax2;
            v(recycler, layoutState7, state, false);
            LayoutState layoutState8 = this.mLayoutState;
            i2 = layoutState8.f5977b;
            int i12 = layoutState8.f5979d;
            int i13 = layoutState8.f5978c;
            if (i13 > 0) {
                iMax += i13;
            }
            updateLayoutStateToFillStart(this.f5970q);
            LayoutState layoutState9 = this.mLayoutState;
            layoutState9.f5983h = iMax;
            layoutState9.f5979d += layoutState9.f5980e;
            v(recycler, layoutState9, state, false);
            LayoutState layoutState10 = this.mLayoutState;
            i3 = layoutState10.f5977b;
            int i14 = layoutState10.f5978c;
            if (i14 > 0) {
                updateLayoutStateToFillEnd(i12, i2);
                LayoutState layoutState11 = this.mLayoutState;
                layoutState11.f5983h = i14;
                v(recycler, layoutState11, state, false);
                i2 = this.mLayoutState.f5977b;
            }
        }
        if (getChildCount() > 0) {
            if (this.f5966m ^ this.mStackFromEnd) {
                int iFixLayoutEndGap2 = fixLayoutEndGap(i2, recycler, state, true);
                i4 = i3 + iFixLayoutEndGap2;
                i5 = i2 + iFixLayoutEndGap2;
                iFixLayoutEndGap = fixLayoutStartGap(i4, recycler, state, false);
            } else {
                int iFixLayoutStartGap = fixLayoutStartGap(i3, recycler, state, true);
                i4 = i3 + iFixLayoutStartGap;
                i5 = i2 + iFixLayoutStartGap;
                iFixLayoutEndGap = fixLayoutEndGap(i5, recycler, state, false);
            }
            i3 = i4 + iFixLayoutEndGap;
            i2 = i5 + iFixLayoutEndGap;
        }
        layoutForPredictiveAnimations(recycler, state, i3, i2);
        if (state.isPreLayout()) {
            this.f5970q.c();
        } else {
            this.f5965l.onLayoutComplete();
        }
        this.mLastStackFromEnd = this.mStackFromEnd;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onLayoutCompleted(RecyclerView.State state) {
        super.onLayoutCompleted(state);
        this.f5969p = null;
        this.f5967n = -1;
        this.f5968o = Integer.MIN_VALUE;
        this.f5970q.c();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof SavedState) {
            SavedState savedState = (SavedState) parcelable;
            this.f5969p = savedState;
            if (this.f5967n != -1) {
                savedState.b();
            }
            requestLayout();
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public Parcelable onSaveInstanceState() {
        if (this.f5969p != null) {
            return new SavedState(this.f5969p);
        }
        SavedState savedState = new SavedState();
        if (getChildCount() > 0) {
            u();
            boolean z2 = this.mLastStackFromEnd ^ this.f5966m;
            savedState.f5991c = z2;
            if (z2) {
                View childClosestToEnd = getChildClosestToEnd();
                savedState.f5990b = this.f5965l.getEndAfterPadding() - this.f5965l.getDecoratedEnd(childClosestToEnd);
                savedState.f5989a = getPosition(childClosestToEnd);
            } else {
                View childClosestToStart = getChildClosestToStart();
                savedState.f5989a = getPosition(childClosestToStart);
                savedState.f5990b = this.f5965l.getDecoratedStart(childClosestToStart) - this.f5965l.getStartAfterPadding();
            }
        } else {
            savedState.b();
        }
        return savedState;
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.ViewDropHandler
    public void prepareForDrop(@NonNull View view, @NonNull View view2, int i2, int i3) {
        assertNotInLayoutOrScroll("Cannot drop a view during a scroll or layout calculation");
        u();
        resolveShouldLayoutReverse();
        int position = getPosition(view);
        int position2 = getPosition(view2);
        char c2 = position < position2 ? (char) 1 : (char) 65535;
        if (this.f5966m) {
            if (c2 == 1) {
                scrollToPositionWithOffset(position2, this.f5965l.getEndAfterPadding() - (this.f5965l.getDecoratedStart(view2) + this.f5965l.getDecoratedMeasurement(view)));
                return;
            } else {
                scrollToPositionWithOffset(position2, this.f5965l.getEndAfterPadding() - this.f5965l.getDecoratedEnd(view2));
                return;
            }
        }
        if (c2 == 65535) {
            scrollToPositionWithOffset(position2, this.f5965l.getDecoratedStart(view2));
        } else {
            scrollToPositionWithOffset(position2, this.f5965l.getDecoratedEnd(view2) - this.f5965l.getDecoratedMeasurement(view));
        }
    }

    protected void r(RecyclerView.State state, int[] iArr) {
        int i2;
        int iB = B(state);
        if (this.mLayoutState.f5981f == -1) {
            i2 = 0;
        } else {
            i2 = iB;
            iB = 0;
        }
        iArr[0] = iB;
        iArr[1] = i2;
    }

    void s(RecyclerView.State state, LayoutState layoutState, RecyclerView.LayoutManager.LayoutPrefetchRegistry layoutPrefetchRegistry) {
        int i2 = layoutState.f5979d;
        if (i2 < 0 || i2 >= state.getItemCount()) {
            return;
        }
        layoutPrefetchRegistry.addPosition(i2, Math.max(0, layoutState.f5982g));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int scrollHorizontallyBy(int i2, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (this.f5964k == 1) {
            return 0;
        }
        return G(i2, recycler, state);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void scrollToPosition(int i2) {
        this.f5967n = i2;
        this.f5968o = Integer.MIN_VALUE;
        SavedState savedState = this.f5969p;
        if (savedState != null) {
            savedState.b();
        }
        requestLayout();
    }

    public void scrollToPositionWithOffset(int i2, int i3) {
        this.f5967n = i2;
        this.f5968o = i3;
        SavedState savedState = this.f5969p;
        if (savedState != null) {
            savedState.b();
        }
        requestLayout();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int scrollVerticallyBy(int i2, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (this.f5964k == 0) {
            return 0;
        }
        return G(i2, recycler, state);
    }

    public void setInitialPrefetchItemCount(int i2) {
        this.mInitialPrefetchItemCount = i2;
    }

    public void setOrientation(int i2) {
        if (i2 != 0 && i2 != 1) {
            throw new IllegalArgumentException("invalid orientation:" + i2);
        }
        assertNotInLayoutOrScroll(null);
        if (i2 != this.f5964k || this.f5965l == null) {
            OrientationHelper orientationHelperCreateOrientationHelper = OrientationHelper.createOrientationHelper(this, i2);
            this.f5965l = orientationHelperCreateOrientationHelper;
            this.f5970q.f5971a = orientationHelperCreateOrientationHelper;
            this.f5964k = i2;
            requestLayout();
        }
    }

    public void setRecycleChildrenOnDetach(boolean z2) {
        this.mRecycleChildrenOnDetach = z2;
    }

    public void setReverseLayout(boolean z2) {
        assertNotInLayoutOrScroll(null);
        if (z2 == this.mReverseLayout) {
            return;
        }
        this.mReverseLayout = z2;
        requestLayout();
    }

    public void setSmoothScrollbarEnabled(boolean z2) {
        this.mSmoothScrollbarEnabled = z2;
    }

    public void setStackFromEnd(boolean z2) {
        assertNotInLayoutOrScroll(null);
        if (this.mStackFromEnd == z2) {
            return;
        }
        this.mStackFromEnd = z2;
        requestLayout();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int i2) {
        LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(recyclerView.getContext());
        linearSmoothScroller.setTargetPosition(i2);
        startSmoothScroll(linearSmoothScroller);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public boolean supportsPredictiveItemAnimations() {
        return this.f5969p == null && this.mLastStackFromEnd == this.mStackFromEnd;
    }

    LayoutState t() {
        return new LayoutState();
    }

    void u() {
        if (this.mLayoutState == null) {
            this.mLayoutState = t();
        }
    }

    int v(RecyclerView.Recycler recycler, LayoutState layoutState, RecyclerView.State state, boolean z2) {
        int i2 = layoutState.f5978c;
        int i3 = layoutState.f5982g;
        if (i3 != Integer.MIN_VALUE) {
            if (i2 < 0) {
                layoutState.f5982g = i3 + i2;
            }
            recycleByLayoutState(recycler, layoutState);
        }
        int i4 = layoutState.f5978c + layoutState.f5983h;
        LayoutChunkResult layoutChunkResult = this.mLayoutChunkResult;
        while (true) {
            if ((!layoutState.f5988m && i4 <= 0) || !layoutState.a(state)) {
                break;
            }
            layoutChunkResult.a();
            D(recycler, state, layoutState, layoutChunkResult);
            if (!layoutChunkResult.mFinished) {
                layoutState.f5977b += layoutChunkResult.mConsumed * layoutState.f5981f;
                if (!layoutChunkResult.mIgnoreConsumed || layoutState.f5987l != null || !state.isPreLayout()) {
                    int i5 = layoutState.f5978c;
                    int i6 = layoutChunkResult.mConsumed;
                    layoutState.f5978c = i5 - i6;
                    i4 -= i6;
                }
                int i7 = layoutState.f5982g;
                if (i7 != Integer.MIN_VALUE) {
                    int i8 = i7 + layoutChunkResult.mConsumed;
                    layoutState.f5982g = i8;
                    int i9 = layoutState.f5978c;
                    if (i9 < 0) {
                        layoutState.f5982g = i8 + i9;
                    }
                    recycleByLayoutState(recycler, layoutState);
                }
                if (z2 && layoutChunkResult.mFocusable) {
                    break;
                }
            } else {
                break;
            }
        }
        return i2 - layoutState.f5978c;
    }

    View w(boolean z2, boolean z3) {
        return this.f5966m ? z(0, getChildCount(), z2, z3) : z(getChildCount() - 1, -1, z2, z3);
    }

    View x(boolean z2, boolean z3) {
        return this.f5966m ? z(getChildCount() - 1, -1, z2, z3) : z(0, getChildCount(), z2, z3);
    }

    View y(int i2, int i3) {
        int i4;
        int i5;
        u();
        if (i3 <= i2 && i3 >= i2) {
            return getChildAt(i2);
        }
        if (this.f5965l.getDecoratedStart(getChildAt(i2)) < this.f5965l.getStartAfterPadding()) {
            i4 = 16644;
            i5 = 16388;
        } else {
            i4 = 4161;
            i5 = 4097;
        }
        return this.f5964k == 0 ? this.f6052c.a(i2, i3, i4, i5) : this.f6053d.a(i2, i3, i4, i5);
    }

    View z(int i2, int i3, boolean z2, boolean z3) {
        u();
        int i4 = z2 ? 24579 : 320;
        int i5 = z3 ? 320 : 0;
        return this.f5964k == 0 ? this.f6052c.a(i2, i3, i4, i5) : this.f6053d.a(i2, i3, i4, i5);
    }

    public LinearLayoutManager(Context context, int i2, boolean z2) {
        this.f5964k = 1;
        this.mReverseLayout = false;
        this.f5966m = false;
        this.mStackFromEnd = false;
        this.mSmoothScrollbarEnabled = true;
        this.f5967n = -1;
        this.f5968o = Integer.MIN_VALUE;
        this.f5969p = null;
        this.f5970q = new AnchorInfo();
        this.mLayoutChunkResult = new LayoutChunkResult();
        this.mInitialPrefetchItemCount = 2;
        this.mReusableIntPair = new int[2];
        setOrientation(i2);
        setReverseLayout(z2);
    }

    private void updateLayoutStateToFillEnd(int i2, int i3) {
        this.mLayoutState.f5978c = this.f5965l.getEndAfterPadding() - i3;
        LayoutState layoutState = this.mLayoutState;
        layoutState.f5980e = this.f5966m ? -1 : 1;
        layoutState.f5979d = i2;
        layoutState.f5981f = 1;
        layoutState.f5977b = i3;
        layoutState.f5982g = Integer.MIN_VALUE;
    }

    private void updateLayoutStateToFillStart(int i2, int i3) {
        this.mLayoutState.f5978c = i3 - this.f5965l.getStartAfterPadding();
        LayoutState layoutState = this.mLayoutState;
        layoutState.f5979d = i2;
        layoutState.f5980e = this.f5966m ? 1 : -1;
        layoutState.f5981f = -1;
        layoutState.f5977b = i3;
        layoutState.f5982g = Integer.MIN_VALUE;
    }

    public LinearLayoutManager(Context context, AttributeSet attributeSet, int i2, int i3) {
        this.f5964k = 1;
        this.mReverseLayout = false;
        this.f5966m = false;
        this.mStackFromEnd = false;
        this.mSmoothScrollbarEnabled = true;
        this.f5967n = -1;
        this.f5968o = Integer.MIN_VALUE;
        this.f5969p = null;
        this.f5970q = new AnchorInfo();
        this.mLayoutChunkResult = new LayoutChunkResult();
        this.mInitialPrefetchItemCount = 2;
        this.mReusableIntPair = new int[2];
        RecyclerView.LayoutManager.Properties properties = RecyclerView.LayoutManager.getProperties(context, attributeSet, i2, i3);
        setOrientation(properties.orientation);
        setReverseLayout(properties.reverseLayout);
        setStackFromEnd(properties.stackFromEnd);
    }
}
