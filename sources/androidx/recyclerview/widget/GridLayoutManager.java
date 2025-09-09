package androidx.recyclerview.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Arrays;

/* loaded from: classes2.dex */
public class GridLayoutManager extends LinearLayoutManager {
    private static final boolean DEBUG = false;
    public static final int DEFAULT_SPAN_COUNT = -1;
    private static final String TAG = "GridLayoutManager";
    private boolean mUsingSpansToEstimateScrollBarDimensions;

    /* renamed from: r, reason: collision with root package name */
    boolean f5904r;

    /* renamed from: s, reason: collision with root package name */
    int f5905s;

    /* renamed from: t, reason: collision with root package name */
    int[] f5906t;

    /* renamed from: u, reason: collision with root package name */
    View[] f5907u;

    /* renamed from: v, reason: collision with root package name */
    final SparseIntArray f5908v;

    /* renamed from: w, reason: collision with root package name */
    final SparseIntArray f5909w;

    /* renamed from: x, reason: collision with root package name */
    SpanSizeLookup f5910x;

    /* renamed from: y, reason: collision with root package name */
    final Rect f5911y;

    public static final class DefaultSpanSizeLookup extends SpanSizeLookup {
        @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
        public int getSpanIndex(int i2, int i3) {
            return i2 % i3;
        }

        @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
        public int getSpanSize(int i2) {
            return 1;
        }
    }

    public static abstract class SpanSizeLookup {
        final SparseIntArray mSpanIndexCache = new SparseIntArray();
        final SparseIntArray mSpanGroupIndexCache = new SparseIntArray();
        private boolean mCacheSpanIndices = false;
        private boolean mCacheSpanGroupIndices = false;

        static int findFirstKeyLessThan(SparseIntArray sparseIntArray, int i2) {
            int size = sparseIntArray.size() - 1;
            int i3 = 0;
            while (i3 <= size) {
                int i4 = (i3 + size) >>> 1;
                if (sparseIntArray.keyAt(i4) < i2) {
                    i3 = i4 + 1;
                } else {
                    size = i4 - 1;
                }
            }
            int i5 = i3 - 1;
            if (i5 < 0 || i5 >= sparseIntArray.size()) {
                return -1;
            }
            return sparseIntArray.keyAt(i5);
        }

        int getCachedSpanGroupIndex(int i2, int i3) {
            if (!this.mCacheSpanGroupIndices) {
                return getSpanGroupIndex(i2, i3);
            }
            int i4 = this.mSpanGroupIndexCache.get(i2, -1);
            if (i4 != -1) {
                return i4;
            }
            int spanGroupIndex = getSpanGroupIndex(i2, i3);
            this.mSpanGroupIndexCache.put(i2, spanGroupIndex);
            return spanGroupIndex;
        }

        int getCachedSpanIndex(int i2, int i3) {
            if (!this.mCacheSpanIndices) {
                return getSpanIndex(i2, i3);
            }
            int i4 = this.mSpanIndexCache.get(i2, -1);
            if (i4 != -1) {
                return i4;
            }
            int spanIndex = getSpanIndex(i2, i3);
            this.mSpanIndexCache.put(i2, spanIndex);
            return spanIndex;
        }

        public int getSpanGroupIndex(int i2, int i3) {
            int i4;
            int i5;
            int cachedSpanIndex;
            int iFindFirstKeyLessThan;
            if (!this.mCacheSpanGroupIndices || (iFindFirstKeyLessThan = findFirstKeyLessThan(this.mSpanGroupIndexCache, i2)) == -1) {
                i4 = 0;
                i5 = 0;
                cachedSpanIndex = 0;
            } else {
                i4 = this.mSpanGroupIndexCache.get(iFindFirstKeyLessThan);
                i5 = iFindFirstKeyLessThan + 1;
                cachedSpanIndex = getCachedSpanIndex(iFindFirstKeyLessThan, i3) + getSpanSize(iFindFirstKeyLessThan);
                if (cachedSpanIndex == i3) {
                    i4++;
                    cachedSpanIndex = 0;
                }
            }
            int spanSize = getSpanSize(i2);
            while (i5 < i2) {
                int spanSize2 = getSpanSize(i5);
                cachedSpanIndex += spanSize2;
                if (cachedSpanIndex == i3) {
                    i4++;
                    cachedSpanIndex = 0;
                } else if (cachedSpanIndex > i3) {
                    i4++;
                    cachedSpanIndex = spanSize2;
                }
                i5++;
            }
            return cachedSpanIndex + spanSize > i3 ? i4 + 1 : i4;
        }

        /* JADX WARN: Removed duplicated region for block: B:12:0x0024  */
        /* JADX WARN: Removed duplicated region for block: B:18:0x0033  */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:14:0x002b -> B:17:0x0030). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:15:0x002d -> B:17:0x0030). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:16:0x002f -> B:17:0x0030). Please report as a decompilation issue!!! */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public int getSpanIndex(int r6, int r7) {
            /*
                r5 = this;
                int r0 = r5.getSpanSize(r6)
                r1 = 0
                if (r0 != r7) goto L8
                return r1
            L8:
                boolean r2 = r5.mCacheSpanIndices
                if (r2 == 0) goto L20
                android.util.SparseIntArray r2 = r5.mSpanIndexCache
                int r2 = findFirstKeyLessThan(r2, r6)
                if (r2 < 0) goto L20
                android.util.SparseIntArray r3 = r5.mSpanIndexCache
                int r3 = r3.get(r2)
                int r4 = r5.getSpanSize(r2)
                int r3 = r3 + r4
                goto L30
            L20:
                r2 = r1
                r3 = r2
            L22:
                if (r2 >= r6) goto L33
                int r4 = r5.getSpanSize(r2)
                int r3 = r3 + r4
                if (r3 != r7) goto L2d
                r3 = r1
                goto L30
            L2d:
                if (r3 <= r7) goto L30
                r3 = r4
            L30:
                int r2 = r2 + 1
                goto L22
            L33:
                int r0 = r0 + r3
                if (r0 > r7) goto L37
                return r3
            L37:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup.getSpanIndex(int, int):int");
        }

        public abstract int getSpanSize(int i2);

        public void invalidateSpanGroupIndexCache() {
            this.mSpanGroupIndexCache.clear();
        }

        public void invalidateSpanIndexCache() {
            this.mSpanIndexCache.clear();
        }

        public boolean isSpanGroupIndexCacheEnabled() {
            return this.mCacheSpanGroupIndices;
        }

        public boolean isSpanIndexCacheEnabled() {
            return this.mCacheSpanIndices;
        }

        public void setSpanGroupIndexCacheEnabled(boolean z2) {
            if (!z2) {
                this.mSpanGroupIndexCache.clear();
            }
            this.mCacheSpanGroupIndices = z2;
        }

        public void setSpanIndexCacheEnabled(boolean z2) {
            if (!z2) {
                this.mSpanGroupIndexCache.clear();
            }
            this.mCacheSpanIndices = z2;
        }
    }

    public GridLayoutManager(Context context, AttributeSet attributeSet, int i2, int i3) {
        super(context, attributeSet, i2, i3);
        this.f5904r = false;
        this.f5905s = -1;
        this.f5908v = new SparseIntArray();
        this.f5909w = new SparseIntArray();
        this.f5910x = new DefaultSpanSizeLookup();
        this.f5911y = new Rect();
        setSpanCount(RecyclerView.LayoutManager.getProperties(context, attributeSet, i2, i3).spanCount);
    }

    static int[] H(int[] iArr, int i2, int i3) {
        int i4;
        if (iArr == null || iArr.length != i2 + 1 || iArr[iArr.length - 1] != i3) {
            iArr = new int[i2 + 1];
        }
        int i5 = 0;
        iArr[0] = 0;
        int i6 = i3 / i2;
        int i7 = i3 % i2;
        int i8 = 0;
        for (int i9 = 1; i9 <= i2; i9++) {
            i5 += i7;
            if (i5 <= 0 || i2 - i5 >= i7) {
                i4 = i6;
            } else {
                i4 = i6 + 1;
                i5 -= i2;
            }
            i8 += i4;
            iArr[i9] = i8;
        }
        return iArr;
    }

    private void assignSpans(RecyclerView.Recycler recycler, RecyclerView.State state, int i2, boolean z2) {
        int i3;
        int i4;
        int i5;
        int i6 = 0;
        if (z2) {
            i5 = 1;
            i4 = i2;
            i3 = 0;
        } else {
            i3 = i2 - 1;
            i4 = -1;
            i5 = -1;
        }
        while (i3 != i4) {
            View view = this.f5907u[i3];
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            int spanSize = getSpanSize(recycler, state, getPosition(view));
            layoutParams.f5913f = spanSize;
            layoutParams.f5912e = i6;
            i6 += spanSize;
            i3 += i5;
        }
    }

    private void cachePreLayoutSpanMapping() {
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            LayoutParams layoutParams = (LayoutParams) getChildAt(i2).getLayoutParams();
            int viewLayoutPosition = layoutParams.getViewLayoutPosition();
            this.f5908v.put(viewLayoutPosition, layoutParams.getSpanSize());
            this.f5909w.put(viewLayoutPosition, layoutParams.getSpanIndex());
        }
    }

    private void calculateItemBorders(int i2) {
        this.f5906t = H(this.f5906t, this.f5905s, i2);
    }

    private void clearPreLayoutSpanMappingCache() {
        this.f5908v.clear();
        this.f5909w.clear();
    }

    private int computeScrollOffsetWithSpanInfo(RecyclerView.State state) {
        if (getChildCount() != 0 && state.getItemCount() != 0) {
            u();
            boolean zIsSmoothScrollbarEnabled = isSmoothScrollbarEnabled();
            View viewX = x(!zIsSmoothScrollbarEnabled, true);
            View viewW = w(!zIsSmoothScrollbarEnabled, true);
            if (viewX != null && viewW != null) {
                int cachedSpanGroupIndex = this.f5910x.getCachedSpanGroupIndex(getPosition(viewX), this.f5905s);
                int cachedSpanGroupIndex2 = this.f5910x.getCachedSpanGroupIndex(getPosition(viewW), this.f5905s);
                int iMax = this.f5966m ? Math.max(0, ((this.f5910x.getCachedSpanGroupIndex(state.getItemCount() - 1, this.f5905s) + 1) - Math.max(cachedSpanGroupIndex, cachedSpanGroupIndex2)) - 1) : Math.max(0, Math.min(cachedSpanGroupIndex, cachedSpanGroupIndex2));
                if (zIsSmoothScrollbarEnabled) {
                    return Math.round((iMax * (Math.abs(this.f5965l.getDecoratedEnd(viewW) - this.f5965l.getDecoratedStart(viewX)) / ((this.f5910x.getCachedSpanGroupIndex(getPosition(viewW), this.f5905s) - this.f5910x.getCachedSpanGroupIndex(getPosition(viewX), this.f5905s)) + 1))) + (this.f5965l.getStartAfterPadding() - this.f5965l.getDecoratedStart(viewX)));
                }
                return iMax;
            }
        }
        return 0;
    }

    private int computeScrollRangeWithSpanInfo(RecyclerView.State state) {
        if (getChildCount() != 0 && state.getItemCount() != 0) {
            u();
            View viewX = x(!isSmoothScrollbarEnabled(), true);
            View viewW = w(!isSmoothScrollbarEnabled(), true);
            if (viewX != null && viewW != null) {
                if (!isSmoothScrollbarEnabled()) {
                    return this.f5910x.getCachedSpanGroupIndex(state.getItemCount() - 1, this.f5905s) + 1;
                }
                int decoratedEnd = this.f5965l.getDecoratedEnd(viewW) - this.f5965l.getDecoratedStart(viewX);
                int cachedSpanGroupIndex = this.f5910x.getCachedSpanGroupIndex(getPosition(viewX), this.f5905s);
                return (int) ((decoratedEnd / ((this.f5910x.getCachedSpanGroupIndex(getPosition(viewW), this.f5905s) - cachedSpanGroupIndex) + 1)) * (this.f5910x.getCachedSpanGroupIndex(state.getItemCount() - 1, this.f5905s) + 1));
            }
        }
        return 0;
    }

    private void ensureAnchorIsInCorrectSpan(RecyclerView.Recycler recycler, RecyclerView.State state, LinearLayoutManager.AnchorInfo anchorInfo, int i2) {
        boolean z2 = i2 == 1;
        int spanIndex = getSpanIndex(recycler, state, anchorInfo.f5972b);
        if (z2) {
            while (spanIndex > 0) {
                int i3 = anchorInfo.f5972b;
                if (i3 <= 0) {
                    return;
                }
                int i4 = i3 - 1;
                anchorInfo.f5972b = i4;
                spanIndex = getSpanIndex(recycler, state, i4);
            }
            return;
        }
        int itemCount = state.getItemCount() - 1;
        int i5 = anchorInfo.f5972b;
        while (i5 < itemCount) {
            int i6 = i5 + 1;
            int spanIndex2 = getSpanIndex(recycler, state, i6);
            if (spanIndex2 <= spanIndex) {
                break;
            }
            i5 = i6;
            spanIndex = spanIndex2;
        }
        anchorInfo.f5972b = i5;
    }

    private void ensureViewSet() {
        View[] viewArr = this.f5907u;
        if (viewArr == null || viewArr.length != this.f5905s) {
            this.f5907u = new View[this.f5905s];
        }
    }

    private int getSpanGroupIndex(RecyclerView.Recycler recycler, RecyclerView.State state, int i2) {
        if (!state.isPreLayout()) {
            return this.f5910x.getCachedSpanGroupIndex(i2, this.f5905s);
        }
        int iConvertPreLayoutPositionToPostLayout = recycler.convertPreLayoutPositionToPostLayout(i2);
        if (iConvertPreLayoutPositionToPostLayout != -1) {
            return this.f5910x.getCachedSpanGroupIndex(iConvertPreLayoutPositionToPostLayout, this.f5905s);
        }
        Log.w(TAG, "Cannot find span size for pre layout position. " + i2);
        return 0;
    }

    private int getSpanIndex(RecyclerView.Recycler recycler, RecyclerView.State state, int i2) {
        if (!state.isPreLayout()) {
            return this.f5910x.getCachedSpanIndex(i2, this.f5905s);
        }
        int i3 = this.f5909w.get(i2, -1);
        if (i3 != -1) {
            return i3;
        }
        int iConvertPreLayoutPositionToPostLayout = recycler.convertPreLayoutPositionToPostLayout(i2);
        if (iConvertPreLayoutPositionToPostLayout != -1) {
            return this.f5910x.getCachedSpanIndex(iConvertPreLayoutPositionToPostLayout, this.f5905s);
        }
        Log.w(TAG, "Cannot find span size for pre layout position. It is not cached, not in the adapter. Pos:" + i2);
        return 0;
    }

    private int getSpanSize(RecyclerView.Recycler recycler, RecyclerView.State state, int i2) {
        if (!state.isPreLayout()) {
            return this.f5910x.getSpanSize(i2);
        }
        int i3 = this.f5908v.get(i2, -1);
        if (i3 != -1) {
            return i3;
        }
        int iConvertPreLayoutPositionToPostLayout = recycler.convertPreLayoutPositionToPostLayout(i2);
        if (iConvertPreLayoutPositionToPostLayout != -1) {
            return this.f5910x.getSpanSize(iConvertPreLayoutPositionToPostLayout);
        }
        Log.w(TAG, "Cannot find span size for pre layout position. It is not cached, not in the adapter. Pos:" + i2);
        return 1;
    }

    private void guessMeasurement(float f2, int i2) {
        calculateItemBorders(Math.max(Math.round(f2 * this.f5905s), i2));
    }

    private void measureChild(View view, int i2, boolean z2) {
        int childMeasureSpec;
        int childMeasureSpec2;
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        Rect rect = layoutParams.f6063b;
        int i3 = rect.top + rect.bottom + ((ViewGroup.MarginLayoutParams) layoutParams).topMargin + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
        int i4 = rect.left + rect.right + ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin;
        int I = I(layoutParams.f5912e, layoutParams.f5913f);
        if (this.f5964k == 1) {
            childMeasureSpec2 = RecyclerView.LayoutManager.getChildMeasureSpec(I, i2, i4, ((ViewGroup.MarginLayoutParams) layoutParams).width, false);
            childMeasureSpec = RecyclerView.LayoutManager.getChildMeasureSpec(this.f5965l.getTotalSpace(), getHeightMode(), i3, ((ViewGroup.MarginLayoutParams) layoutParams).height, true);
        } else {
            int childMeasureSpec3 = RecyclerView.LayoutManager.getChildMeasureSpec(I, i2, i3, ((ViewGroup.MarginLayoutParams) layoutParams).height, false);
            int childMeasureSpec4 = RecyclerView.LayoutManager.getChildMeasureSpec(this.f5965l.getTotalSpace(), getWidthMode(), i4, ((ViewGroup.MarginLayoutParams) layoutParams).width, true);
            childMeasureSpec = childMeasureSpec3;
            childMeasureSpec2 = childMeasureSpec4;
        }
        measureChildWithDecorationsAndMargin(view, childMeasureSpec2, childMeasureSpec, z2);
    }

    private void measureChildWithDecorationsAndMargin(View view, int i2, int i3, boolean z2) {
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
        if (z2 ? p(view, i2, i3, layoutParams) : n(view, i2, i3, layoutParams)) {
            view.measure(i2, i3);
        }
    }

    private void updateMeasurements() {
        int height;
        int paddingTop;
        if (getOrientation() == 1) {
            height = getWidth() - getPaddingRight();
            paddingTop = getPaddingLeft();
        } else {
            height = getHeight() - getPaddingBottom();
            paddingTop = getPaddingTop();
        }
        calculateItemBorders(height - paddingTop);
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager
    View A(RecyclerView.Recycler recycler, RecyclerView.State state, boolean z2, boolean z3) {
        int i2;
        int childCount;
        int childCount2 = getChildCount();
        int i3 = 1;
        if (z3) {
            childCount = getChildCount() - 1;
            i2 = -1;
            i3 = -1;
        } else {
            i2 = childCount2;
            childCount = 0;
        }
        int itemCount = state.getItemCount();
        u();
        int startAfterPadding = this.f5965l.getStartAfterPadding();
        int endAfterPadding = this.f5965l.getEndAfterPadding();
        View view = null;
        View view2 = null;
        while (childCount != i2) {
            View childAt = getChildAt(childCount);
            int position = getPosition(childAt);
            if (position >= 0 && position < itemCount && getSpanIndex(recycler, state, position) == 0) {
                if (((RecyclerView.LayoutParams) childAt.getLayoutParams()).isItemRemoved()) {
                    if (view2 == null) {
                        view2 = childAt;
                    }
                } else {
                    if (this.f5965l.getDecoratedStart(childAt) < endAfterPadding && this.f5965l.getDecoratedEnd(childAt) >= startAfterPadding) {
                        return childAt;
                    }
                    if (view == null) {
                        view = childAt;
                    }
                }
            }
            childCount += i3;
        }
        return view != null ? view : view2;
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager
    void D(RecyclerView.Recycler recycler, RecyclerView.State state, LinearLayoutManager.LayoutState layoutState, LinearLayoutManager.LayoutChunkResult layoutChunkResult) {
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int decoratedMeasurementInOther;
        int decoratedMeasurementInOther2;
        int decoratedMeasurementInOther3;
        int i9;
        int childMeasureSpec;
        int childMeasureSpec2;
        View viewB;
        int modeInOther = this.f5965l.getModeInOther();
        boolean z2 = modeInOther != 1073741824;
        int i10 = getChildCount() > 0 ? this.f5906t[this.f5905s] : 0;
        if (z2) {
            updateMeasurements();
        }
        boolean z3 = layoutState.f5980e == 1;
        int spanIndex = this.f5905s;
        if (!z3) {
            spanIndex = getSpanIndex(recycler, state, layoutState.f5979d) + getSpanSize(recycler, state, layoutState.f5979d);
        }
        int i11 = 0;
        while (i11 < this.f5905s && layoutState.a(state) && spanIndex > 0) {
            int i12 = layoutState.f5979d;
            int spanSize = getSpanSize(recycler, state, i12);
            if (spanSize > this.f5905s) {
                throw new IllegalArgumentException("Item at position " + i12 + " requires " + spanSize + " spans but GridLayoutManager has only " + this.f5905s + " spans.");
            }
            spanIndex -= spanSize;
            if (spanIndex < 0 || (viewB = layoutState.b(recycler)) == null) {
                break;
            }
            this.f5907u[i11] = viewB;
            i11++;
        }
        if (i11 == 0) {
            layoutChunkResult.mFinished = true;
            return;
        }
        assignSpans(recycler, state, i11, z3);
        float f2 = 0.0f;
        int i13 = 0;
        for (int i14 = 0; i14 < i11; i14++) {
            View view = this.f5907u[i14];
            if (layoutState.f5987l == null) {
                if (z3) {
                    addView(view);
                } else {
                    addView(view, 0);
                }
            } else if (z3) {
                addDisappearingView(view);
            } else {
                addDisappearingView(view, 0);
            }
            calculateItemDecorationsForChild(view, this.f5911y);
            measureChild(view, modeInOther, false);
            int decoratedMeasurement = this.f5965l.getDecoratedMeasurement(view);
            if (decoratedMeasurement > i13) {
                i13 = decoratedMeasurement;
            }
            float decoratedMeasurementInOther4 = (this.f5965l.getDecoratedMeasurementInOther(view) * 1.0f) / ((LayoutParams) view.getLayoutParams()).f5913f;
            if (decoratedMeasurementInOther4 > f2) {
                f2 = decoratedMeasurementInOther4;
            }
        }
        if (z2) {
            guessMeasurement(f2, i10);
            i13 = 0;
            for (int i15 = 0; i15 < i11; i15++) {
                View view2 = this.f5907u[i15];
                measureChild(view2, 1073741824, true);
                int decoratedMeasurement2 = this.f5965l.getDecoratedMeasurement(view2);
                if (decoratedMeasurement2 > i13) {
                    i13 = decoratedMeasurement2;
                }
            }
        }
        for (int i16 = 0; i16 < i11; i16++) {
            View view3 = this.f5907u[i16];
            if (this.f5965l.getDecoratedMeasurement(view3) != i13) {
                LayoutParams layoutParams = (LayoutParams) view3.getLayoutParams();
                Rect rect = layoutParams.f6063b;
                int i17 = rect.top + rect.bottom + ((ViewGroup.MarginLayoutParams) layoutParams).topMargin + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
                int i18 = rect.left + rect.right + ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin;
                int I = I(layoutParams.f5912e, layoutParams.f5913f);
                if (this.f5964k == 1) {
                    childMeasureSpec2 = RecyclerView.LayoutManager.getChildMeasureSpec(I, 1073741824, i18, ((ViewGroup.MarginLayoutParams) layoutParams).width, false);
                    childMeasureSpec = View.MeasureSpec.makeMeasureSpec(i13 - i17, 1073741824);
                } else {
                    int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(i13 - i18, 1073741824);
                    childMeasureSpec = RecyclerView.LayoutManager.getChildMeasureSpec(I, 1073741824, i17, ((ViewGroup.MarginLayoutParams) layoutParams).height, false);
                    childMeasureSpec2 = iMakeMeasureSpec;
                }
                measureChildWithDecorationsAndMargin(view3, childMeasureSpec2, childMeasureSpec, true);
            }
        }
        layoutChunkResult.mConsumed = i13;
        if (this.f5964k == 1) {
            if (layoutState.f5981f == -1) {
                i7 = layoutState.f5977b;
                i9 = i7 - i13;
            } else {
                i9 = layoutState.f5977b;
                i7 = i9 + i13;
            }
            i5 = i9;
            i6 = 0;
            i4 = 0;
        } else {
            if (layoutState.f5981f == -1) {
                i3 = layoutState.f5977b;
                i2 = i3 - i13;
            } else {
                i2 = layoutState.f5977b;
                i3 = i2 + i13;
            }
            i4 = i2;
            i5 = 0;
            i6 = i3;
            i7 = 0;
        }
        int i19 = 0;
        while (i19 < i11) {
            View view4 = this.f5907u[i19];
            LayoutParams layoutParams2 = (LayoutParams) view4.getLayoutParams();
            if (this.f5964k == 1) {
                if (C()) {
                    int paddingLeft = getPaddingLeft() + this.f5906t[this.f5905s - layoutParams2.f5912e];
                    decoratedMeasurementInOther3 = i7;
                    decoratedMeasurementInOther = paddingLeft;
                    decoratedMeasurementInOther2 = paddingLeft - this.f5965l.getDecoratedMeasurementInOther(view4);
                } else {
                    int paddingLeft2 = getPaddingLeft() + this.f5906t[layoutParams2.f5912e];
                    decoratedMeasurementInOther3 = i7;
                    decoratedMeasurementInOther2 = paddingLeft2;
                    decoratedMeasurementInOther = this.f5965l.getDecoratedMeasurementInOther(view4) + paddingLeft2;
                }
                i8 = i5;
            } else {
                int paddingTop = getPaddingTop() + this.f5906t[layoutParams2.f5912e];
                i8 = paddingTop;
                decoratedMeasurementInOther = i6;
                decoratedMeasurementInOther2 = i4;
                decoratedMeasurementInOther3 = this.f5965l.getDecoratedMeasurementInOther(view4) + paddingTop;
            }
            layoutDecoratedWithMargins(view4, decoratedMeasurementInOther2, i8, decoratedMeasurementInOther, decoratedMeasurementInOther3);
            if (layoutParams2.isItemRemoved() || layoutParams2.isItemChanged()) {
                layoutChunkResult.mIgnoreConsumed = true;
            }
            layoutChunkResult.mFocusable |= view4.hasFocusable();
            i19++;
            i7 = decoratedMeasurementInOther3;
            i6 = decoratedMeasurementInOther;
            i4 = decoratedMeasurementInOther2;
            i5 = i8;
        }
        Arrays.fill(this.f5907u, (Object) null);
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager
    void E(RecyclerView.Recycler recycler, RecyclerView.State state, LinearLayoutManager.AnchorInfo anchorInfo, int i2) {
        super.E(recycler, state, anchorInfo, i2);
        updateMeasurements();
        if (state.getItemCount() > 0 && !state.isPreLayout()) {
            ensureAnchorIsInCorrectSpan(recycler, state, anchorInfo, i2);
        }
        ensureViewSet();
    }

    int I(int i2, int i3) {
        if (this.f5964k != 1 || !C()) {
            int[] iArr = this.f5906t;
            return iArr[i3 + i2] - iArr[i2];
        }
        int[] iArr2 = this.f5906t;
        int i4 = this.f5905s;
        return iArr2[i4 - i2] - iArr2[(i4 - i2) - i3];
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public boolean checkLayoutParams(RecyclerView.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int computeHorizontalScrollOffset(RecyclerView.State state) {
        return this.mUsingSpansToEstimateScrollBarDimensions ? computeScrollOffsetWithSpanInfo(state) : super.computeHorizontalScrollOffset(state);
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int computeHorizontalScrollRange(RecyclerView.State state) {
        return this.mUsingSpansToEstimateScrollBarDimensions ? computeScrollRangeWithSpanInfo(state) : super.computeHorizontalScrollRange(state);
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int computeVerticalScrollOffset(RecyclerView.State state) {
        return this.mUsingSpansToEstimateScrollBarDimensions ? computeScrollOffsetWithSpanInfo(state) : super.computeVerticalScrollOffset(state);
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int computeVerticalScrollRange(RecyclerView.State state) {
        return this.mUsingSpansToEstimateScrollBarDimensions ? computeScrollRangeWithSpanInfo(state) : super.computeVerticalScrollRange(state);
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return this.f5964k == 0 ? new LayoutParams(-2, -1) : new LayoutParams(-1, -2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public RecyclerView.LayoutParams generateLayoutParams(Context context, AttributeSet attributeSet) {
        return new LayoutParams(context, attributeSet);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int getColumnCountForAccessibility(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (this.f5964k == 1) {
            return this.f5905s;
        }
        if (state.getItemCount() < 1) {
            return 0;
        }
        return getSpanGroupIndex(recycler, state, state.getItemCount() - 1) + 1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int getRowCountForAccessibility(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (this.f5964k == 0) {
            return this.f5905s;
        }
        if (state.getItemCount() < 1) {
            return 0;
        }
        return getSpanGroupIndex(recycler, state, state.getItemCount() - 1) + 1;
    }

    public int getSpanCount() {
        return this.f5905s;
    }

    public SpanSizeLookup getSpanSizeLookup() {
        return this.f5910x;
    }

    public boolean isUsingSpansToEstimateScrollbarDimensions() {
        return this.mUsingSpansToEstimateScrollBarDimensions;
    }

    /* JADX WARN: Code restructure failed: missing block: B:56:0x00d1, code lost:
    
        if (r13 == (r2 > r15)) goto L47;
     */
    /* JADX WARN: Removed duplicated region for block: B:72:0x00f9  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x010f  */
    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.view.View onFocusSearchFailed(android.view.View r24, int r25, androidx.recyclerview.widget.RecyclerView.Recycler r26, androidx.recyclerview.widget.RecyclerView.State r27) {
        /*
            Method dump skipped, instructions count: 317
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.GridLayoutManager.onFocusSearchFailed(android.view.View, int, androidx.recyclerview.widget.RecyclerView$Recycler, androidx.recyclerview.widget.RecyclerView$State):android.view.View");
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onInitializeAccessibilityNodeInfoForItem(RecyclerView.Recycler recycler, RecyclerView.State state, View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (!(layoutParams instanceof LayoutParams)) {
            super.e(view, accessibilityNodeInfoCompat);
            return;
        }
        LayoutParams layoutParams2 = (LayoutParams) layoutParams;
        int spanGroupIndex = getSpanGroupIndex(recycler, state, layoutParams2.getViewLayoutPosition());
        if (this.f5964k == 0) {
            accessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(layoutParams2.getSpanIndex(), layoutParams2.getSpanSize(), spanGroupIndex, 1, false, false));
        } else {
            accessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(spanGroupIndex, 1, layoutParams2.getSpanIndex(), layoutParams2.getSpanSize(), false, false));
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onItemsAdded(RecyclerView recyclerView, int i2, int i3) {
        this.f5910x.invalidateSpanIndexCache();
        this.f5910x.invalidateSpanGroupIndexCache();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onItemsChanged(RecyclerView recyclerView) {
        this.f5910x.invalidateSpanIndexCache();
        this.f5910x.invalidateSpanGroupIndexCache();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onItemsMoved(RecyclerView recyclerView, int i2, int i3, int i4) {
        this.f5910x.invalidateSpanIndexCache();
        this.f5910x.invalidateSpanGroupIndexCache();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onItemsRemoved(RecyclerView recyclerView, int i2, int i3) {
        this.f5910x.invalidateSpanIndexCache();
        this.f5910x.invalidateSpanGroupIndexCache();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onItemsUpdated(RecyclerView recyclerView, int i2, int i3, Object obj) {
        this.f5910x.invalidateSpanIndexCache();
        this.f5910x.invalidateSpanGroupIndexCache();
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (state.isPreLayout()) {
            cachePreLayoutSpanMapping();
        }
        super.onLayoutChildren(recycler, state);
        clearPreLayoutSpanMappingCache();
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onLayoutCompleted(RecyclerView.State state) {
        super.onLayoutCompleted(state);
        this.f5904r = false;
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager
    void s(RecyclerView.State state, LinearLayoutManager.LayoutState layoutState, RecyclerView.LayoutManager.LayoutPrefetchRegistry layoutPrefetchRegistry) {
        int spanSize = this.f5905s;
        for (int i2 = 0; i2 < this.f5905s && layoutState.a(state) && spanSize > 0; i2++) {
            int i3 = layoutState.f5979d;
            layoutPrefetchRegistry.addPosition(i3, Math.max(0, layoutState.f5982g));
            spanSize -= this.f5910x.getSpanSize(i3);
            layoutState.f5979d += layoutState.f5980e;
        }
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int scrollHorizontallyBy(int i2, RecyclerView.Recycler recycler, RecyclerView.State state) {
        updateMeasurements();
        ensureViewSet();
        return super.scrollHorizontallyBy(i2, recycler, state);
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int scrollVerticallyBy(int i2, RecyclerView.Recycler recycler, RecyclerView.State state) {
        updateMeasurements();
        ensureViewSet();
        return super.scrollVerticallyBy(i2, recycler, state);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void setMeasuredDimension(Rect rect, int i2, int i3) {
        int iChooseSize;
        int iChooseSize2;
        if (this.f5906t == null) {
            super.setMeasuredDimension(rect, i2, i3);
        }
        int paddingLeft = getPaddingLeft() + getPaddingRight();
        int paddingTop = getPaddingTop() + getPaddingBottom();
        if (this.f5964k == 1) {
            iChooseSize2 = RecyclerView.LayoutManager.chooseSize(i3, rect.height() + paddingTop, getMinimumHeight());
            int[] iArr = this.f5906t;
            iChooseSize = RecyclerView.LayoutManager.chooseSize(i2, iArr[iArr.length - 1] + paddingLeft, getMinimumWidth());
        } else {
            iChooseSize = RecyclerView.LayoutManager.chooseSize(i2, rect.width() + paddingLeft, getMinimumWidth());
            int[] iArr2 = this.f5906t;
            iChooseSize2 = RecyclerView.LayoutManager.chooseSize(i3, iArr2[iArr2.length - 1] + paddingTop, getMinimumHeight());
        }
        setMeasuredDimension(iChooseSize, iChooseSize2);
    }

    public void setSpanCount(int i2) {
        if (i2 == this.f5905s) {
            return;
        }
        this.f5904r = true;
        if (i2 >= 1) {
            this.f5905s = i2;
            this.f5910x.invalidateSpanIndexCache();
            requestLayout();
        } else {
            throw new IllegalArgumentException("Span count should be at least 1. Provided " + i2);
        }
    }

    public void setSpanSizeLookup(SpanSizeLookup spanSizeLookup) {
        this.f5910x = spanSizeLookup;
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager
    public void setStackFromEnd(boolean z2) {
        if (z2) {
            throw new UnsupportedOperationException("GridLayoutManager does not support stack from end. Consider using reverse layout");
        }
        super.setStackFromEnd(false);
    }

    public void setUsingSpansToEstimateScrollbarDimensions(boolean z2) {
        this.mUsingSpansToEstimateScrollBarDimensions = z2;
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public boolean supportsPredictiveItemAnimations() {
        return this.f5969p == null && !this.f5904r;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public RecyclerView.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof ViewGroup.MarginLayoutParams ? new LayoutParams((ViewGroup.MarginLayoutParams) layoutParams) : new LayoutParams(layoutParams);
    }

    public static class LayoutParams extends RecyclerView.LayoutParams {
        public static final int INVALID_SPAN_ID = -1;

        /* renamed from: e, reason: collision with root package name */
        int f5912e;

        /* renamed from: f, reason: collision with root package name */
        int f5913f;

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.f5912e = -1;
            this.f5913f = 0;
        }

        public int getSpanIndex() {
            return this.f5912e;
        }

        public int getSpanSize() {
            return this.f5913f;
        }

        public LayoutParams(int i2, int i3) {
            super(i2, i3);
            this.f5912e = -1;
            this.f5913f = 0;
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
            this.f5912e = -1;
            this.f5913f = 0;
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.f5912e = -1;
            this.f5913f = 0;
        }

        public LayoutParams(RecyclerView.LayoutParams layoutParams) {
            super(layoutParams);
            this.f5912e = -1;
            this.f5913f = 0;
        }
    }

    public GridLayoutManager(Context context, int i2) {
        super(context);
        this.f5904r = false;
        this.f5905s = -1;
        this.f5908v = new SparseIntArray();
        this.f5909w = new SparseIntArray();
        this.f5910x = new DefaultSpanSizeLookup();
        this.f5911y = new Rect();
        setSpanCount(i2);
    }

    public GridLayoutManager(Context context, int i2, int i3, boolean z2) {
        super(context, i3, z2);
        this.f5904r = false;
        this.f5905s = -1;
        this.f5908v = new SparseIntArray();
        this.f5909w = new SparseIntArray();
        this.f5910x = new DefaultSpanSizeLookup();
        this.f5911y = new Rect();
        setSpanCount(i2);
    }
}
