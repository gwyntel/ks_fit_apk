package androidx.recyclerview.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

/* loaded from: classes2.dex */
public class StaggeredGridLayoutManager extends RecyclerView.LayoutManager implements RecyclerView.SmoothScroller.ScrollVectorProvider {

    @Deprecated
    public static final int GAP_HANDLING_LAZY = 1;
    public static final int GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS = 2;
    public static final int GAP_HANDLING_NONE = 0;
    public static final int HORIZONTAL = 0;
    private static final float MAX_SCROLL_FACTOR = 0.33333334f;
    private static final String TAG = "StaggeredGridLManager";
    public static final int VERTICAL = 1;

    /* renamed from: k, reason: collision with root package name */
    Span[] f6112k;

    /* renamed from: l, reason: collision with root package name */
    OrientationHelper f6113l;

    /* renamed from: m, reason: collision with root package name */
    OrientationHelper f6114m;
    private int mFullSizeSpec;
    private boolean mLastLayoutFromEnd;
    private boolean mLastLayoutRTL;

    @NonNull
    private final LayoutState mLayoutState;
    private int mOrientation;
    private SavedState mPendingSavedState;
    private int[] mPrefetchDistances;
    private BitSet mRemainingSpans;
    private int mSizePerSpan;
    private int mSpanCount = -1;

    /* renamed from: n, reason: collision with root package name */
    boolean f6115n = false;

    /* renamed from: o, reason: collision with root package name */
    boolean f6116o = false;

    /* renamed from: p, reason: collision with root package name */
    int f6117p = -1;

    /* renamed from: q, reason: collision with root package name */
    int f6118q = Integer.MIN_VALUE;

    /* renamed from: r, reason: collision with root package name */
    LazySpanLookup f6119r = new LazySpanLookup();
    private int mGapStrategy = 2;
    private final Rect mTmpRect = new Rect();
    private final AnchorInfo mAnchorInfo = new AnchorInfo();
    private boolean mLaidOutInvalidFullSpan = false;
    private boolean mSmoothScrollbarEnabled = true;
    private final Runnable mCheckForGapsRunnable = new Runnable() { // from class: androidx.recyclerview.widget.StaggeredGridLayoutManager.1
        @Override // java.lang.Runnable
        public void run() {
            StaggeredGridLayoutManager.this.t();
        }
    };

    class AnchorInfo {

        /* renamed from: a, reason: collision with root package name */
        int f6121a;

        /* renamed from: b, reason: collision with root package name */
        int f6122b;

        /* renamed from: c, reason: collision with root package name */
        boolean f6123c;

        /* renamed from: d, reason: collision with root package name */
        boolean f6124d;

        /* renamed from: e, reason: collision with root package name */
        boolean f6125e;

        /* renamed from: f, reason: collision with root package name */
        int[] f6126f;

        AnchorInfo() {
            c();
        }

        void a() {
            this.f6122b = this.f6123c ? StaggeredGridLayoutManager.this.f6113l.getEndAfterPadding() : StaggeredGridLayoutManager.this.f6113l.getStartAfterPadding();
        }

        void b(int i2) {
            if (this.f6123c) {
                this.f6122b = StaggeredGridLayoutManager.this.f6113l.getEndAfterPadding() - i2;
            } else {
                this.f6122b = StaggeredGridLayoutManager.this.f6113l.getStartAfterPadding() + i2;
            }
        }

        void c() {
            this.f6121a = -1;
            this.f6122b = Integer.MIN_VALUE;
            this.f6123c = false;
            this.f6124d = false;
            this.f6125e = false;
            int[] iArr = this.f6126f;
            if (iArr != null) {
                Arrays.fill(iArr, -1);
            }
        }

        void d(Span[] spanArr) {
            int length = spanArr.length;
            int[] iArr = this.f6126f;
            if (iArr == null || iArr.length < length) {
                this.f6126f = new int[StaggeredGridLayoutManager.this.f6112k.length];
            }
            for (int i2 = 0; i2 < length; i2++) {
                this.f6126f[i2] = spanArr[i2].m(Integer.MIN_VALUE);
            }
        }
    }

    public static class LayoutParams extends RecyclerView.LayoutParams {
        public static final int INVALID_SPAN_ID = -1;

        /* renamed from: e, reason: collision with root package name */
        Span f6128e;

        /* renamed from: f, reason: collision with root package name */
        boolean f6129f;

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public final int getSpanIndex() {
            Span span = this.f6128e;
            if (span == null) {
                return -1;
            }
            return span.f6150e;
        }

        public boolean isFullSpan() {
            return this.f6129f;
        }

        public void setFullSpan(boolean z2) {
            this.f6129f = z2;
        }

        public LayoutParams(int i2, int i3) {
            super(i2, i3);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(RecyclerView.LayoutParams layoutParams) {
            super(layoutParams);
        }
    }

    @SuppressLint({"BanParcelableUsage"})
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static class SavedState implements Parcelable {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: androidx.recyclerview.widget.StaggeredGridLayoutManager.SavedState.1
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
        int f6136a;

        /* renamed from: b, reason: collision with root package name */
        int f6137b;

        /* renamed from: c, reason: collision with root package name */
        int f6138c;

        /* renamed from: d, reason: collision with root package name */
        int[] f6139d;

        /* renamed from: e, reason: collision with root package name */
        int f6140e;

        /* renamed from: f, reason: collision with root package name */
        int[] f6141f;

        /* renamed from: g, reason: collision with root package name */
        List f6142g;

        /* renamed from: h, reason: collision with root package name */
        boolean f6143h;

        /* renamed from: i, reason: collision with root package name */
        boolean f6144i;

        /* renamed from: j, reason: collision with root package name */
        boolean f6145j;

        public SavedState() {
        }

        void a() {
            this.f6139d = null;
            this.f6138c = 0;
            this.f6136a = -1;
            this.f6137b = -1;
        }

        void b() {
            this.f6139d = null;
            this.f6138c = 0;
            this.f6140e = 0;
            this.f6141f = null;
            this.f6142g = null;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i2) {
            parcel.writeInt(this.f6136a);
            parcel.writeInt(this.f6137b);
            parcel.writeInt(this.f6138c);
            if (this.f6138c > 0) {
                parcel.writeIntArray(this.f6139d);
            }
            parcel.writeInt(this.f6140e);
            if (this.f6140e > 0) {
                parcel.writeIntArray(this.f6141f);
            }
            parcel.writeInt(this.f6143h ? 1 : 0);
            parcel.writeInt(this.f6144i ? 1 : 0);
            parcel.writeInt(this.f6145j ? 1 : 0);
            parcel.writeList(this.f6142g);
        }

        SavedState(Parcel parcel) {
            this.f6136a = parcel.readInt();
            this.f6137b = parcel.readInt();
            int i2 = parcel.readInt();
            this.f6138c = i2;
            if (i2 > 0) {
                int[] iArr = new int[i2];
                this.f6139d = iArr;
                parcel.readIntArray(iArr);
            }
            int i3 = parcel.readInt();
            this.f6140e = i3;
            if (i3 > 0) {
                int[] iArr2 = new int[i3];
                this.f6141f = iArr2;
                parcel.readIntArray(iArr2);
            }
            this.f6143h = parcel.readInt() == 1;
            this.f6144i = parcel.readInt() == 1;
            this.f6145j = parcel.readInt() == 1;
            this.f6142g = parcel.readArrayList(LazySpanLookup.FullSpanItem.class.getClassLoader());
        }

        public SavedState(SavedState savedState) {
            this.f6138c = savedState.f6138c;
            this.f6136a = savedState.f6136a;
            this.f6137b = savedState.f6137b;
            this.f6139d = savedState.f6139d;
            this.f6140e = savedState.f6140e;
            this.f6141f = savedState.f6141f;
            this.f6143h = savedState.f6143h;
            this.f6144i = savedState.f6144i;
            this.f6145j = savedState.f6145j;
            this.f6142g = savedState.f6142g;
        }
    }

    class Span {

        /* renamed from: a, reason: collision with root package name */
        ArrayList f6146a = new ArrayList();

        /* renamed from: b, reason: collision with root package name */
        int f6147b = Integer.MIN_VALUE;

        /* renamed from: c, reason: collision with root package name */
        int f6148c = Integer.MIN_VALUE;

        /* renamed from: d, reason: collision with root package name */
        int f6149d = 0;

        /* renamed from: e, reason: collision with root package name */
        final int f6150e;

        Span(int i2) {
            this.f6150e = i2;
        }

        void a(View view) {
            LayoutParams layoutParamsK = k(view);
            layoutParamsK.f6128e = this;
            this.f6146a.add(view);
            this.f6148c = Integer.MIN_VALUE;
            if (this.f6146a.size() == 1) {
                this.f6147b = Integer.MIN_VALUE;
            }
            if (layoutParamsK.isItemRemoved() || layoutParamsK.isItemChanged()) {
                this.f6149d += StaggeredGridLayoutManager.this.f6113l.getDecoratedMeasurement(view);
            }
        }

        void b(boolean z2, int i2) {
            int iJ = z2 ? j(Integer.MIN_VALUE) : m(Integer.MIN_VALUE);
            e();
            if (iJ == Integer.MIN_VALUE) {
                return;
            }
            if (!z2 || iJ >= StaggeredGridLayoutManager.this.f6113l.getEndAfterPadding()) {
                if (z2 || iJ <= StaggeredGridLayoutManager.this.f6113l.getStartAfterPadding()) {
                    if (i2 != Integer.MIN_VALUE) {
                        iJ += i2;
                    }
                    this.f6148c = iJ;
                    this.f6147b = iJ;
                }
            }
        }

        void c() {
            LazySpanLookup.FullSpanItem fullSpanItem;
            ArrayList arrayList = this.f6146a;
            View view = (View) arrayList.get(arrayList.size() - 1);
            LayoutParams layoutParamsK = k(view);
            this.f6148c = StaggeredGridLayoutManager.this.f6113l.getDecoratedEnd(view);
            if (layoutParamsK.f6129f && (fullSpanItem = StaggeredGridLayoutManager.this.f6119r.getFullSpanItem(layoutParamsK.getViewLayoutPosition())) != null && fullSpanItem.f6133b == 1) {
                this.f6148c += fullSpanItem.a(this.f6150e);
            }
        }

        void d() {
            LazySpanLookup.FullSpanItem fullSpanItem;
            View view = (View) this.f6146a.get(0);
            LayoutParams layoutParamsK = k(view);
            this.f6147b = StaggeredGridLayoutManager.this.f6113l.getDecoratedStart(view);
            if (layoutParamsK.f6129f && (fullSpanItem = StaggeredGridLayoutManager.this.f6119r.getFullSpanItem(layoutParamsK.getViewLayoutPosition())) != null && fullSpanItem.f6133b == -1) {
                this.f6147b -= fullSpanItem.a(this.f6150e);
            }
        }

        void e() {
            this.f6146a.clear();
            n();
            this.f6149d = 0;
        }

        int f(int i2, int i3, boolean z2, boolean z3, boolean z4) {
            int startAfterPadding = StaggeredGridLayoutManager.this.f6113l.getStartAfterPadding();
            int endAfterPadding = StaggeredGridLayoutManager.this.f6113l.getEndAfterPadding();
            int i4 = i3 > i2 ? 1 : -1;
            while (i2 != i3) {
                View view = (View) this.f6146a.get(i2);
                int decoratedStart = StaggeredGridLayoutManager.this.f6113l.getDecoratedStart(view);
                int decoratedEnd = StaggeredGridLayoutManager.this.f6113l.getDecoratedEnd(view);
                boolean z5 = false;
                boolean z6 = !z4 ? decoratedStart >= endAfterPadding : decoratedStart > endAfterPadding;
                if (!z4 ? decoratedEnd > startAfterPadding : decoratedEnd >= startAfterPadding) {
                    z5 = true;
                }
                if (z6 && z5) {
                    if (z2 && z3) {
                        if (decoratedStart >= startAfterPadding && decoratedEnd <= endAfterPadding) {
                            return StaggeredGridLayoutManager.this.getPosition(view);
                        }
                    } else {
                        if (z3) {
                            return StaggeredGridLayoutManager.this.getPosition(view);
                        }
                        if (decoratedStart < startAfterPadding || decoratedEnd > endAfterPadding) {
                            return StaggeredGridLayoutManager.this.getPosition(view);
                        }
                    }
                }
                i2 += i4;
            }
            return -1;
        }

        public int findFirstCompletelyVisibleItemPosition() {
            return StaggeredGridLayoutManager.this.f6115n ? h(this.f6146a.size() - 1, -1, true) : h(0, this.f6146a.size(), true);
        }

        public int findFirstPartiallyVisibleItemPosition() {
            return StaggeredGridLayoutManager.this.f6115n ? g(this.f6146a.size() - 1, -1, true) : g(0, this.f6146a.size(), true);
        }

        public int findFirstVisibleItemPosition() {
            return StaggeredGridLayoutManager.this.f6115n ? h(this.f6146a.size() - 1, -1, false) : h(0, this.f6146a.size(), false);
        }

        public int findLastCompletelyVisibleItemPosition() {
            return StaggeredGridLayoutManager.this.f6115n ? h(0, this.f6146a.size(), true) : h(this.f6146a.size() - 1, -1, true);
        }

        public int findLastPartiallyVisibleItemPosition() {
            return StaggeredGridLayoutManager.this.f6115n ? g(0, this.f6146a.size(), true) : g(this.f6146a.size() - 1, -1, true);
        }

        public int findLastVisibleItemPosition() {
            return StaggeredGridLayoutManager.this.f6115n ? h(0, this.f6146a.size(), false) : h(this.f6146a.size() - 1, -1, false);
        }

        int g(int i2, int i3, boolean z2) {
            return f(i2, i3, false, false, z2);
        }

        public int getDeletedSize() {
            return this.f6149d;
        }

        public View getFocusableViewAfter(int i2, int i3) {
            View view = null;
            if (i3 != -1) {
                int size = this.f6146a.size() - 1;
                while (size >= 0) {
                    View view2 = (View) this.f6146a.get(size);
                    StaggeredGridLayoutManager staggeredGridLayoutManager = StaggeredGridLayoutManager.this;
                    if (staggeredGridLayoutManager.f6115n && staggeredGridLayoutManager.getPosition(view2) >= i2) {
                        break;
                    }
                    StaggeredGridLayoutManager staggeredGridLayoutManager2 = StaggeredGridLayoutManager.this;
                    if ((!staggeredGridLayoutManager2.f6115n && staggeredGridLayoutManager2.getPosition(view2) <= i2) || !view2.hasFocusable()) {
                        break;
                    }
                    size--;
                    view = view2;
                }
            } else {
                int size2 = this.f6146a.size();
                int i4 = 0;
                while (i4 < size2) {
                    View view3 = (View) this.f6146a.get(i4);
                    StaggeredGridLayoutManager staggeredGridLayoutManager3 = StaggeredGridLayoutManager.this;
                    if (staggeredGridLayoutManager3.f6115n && staggeredGridLayoutManager3.getPosition(view3) <= i2) {
                        break;
                    }
                    StaggeredGridLayoutManager staggeredGridLayoutManager4 = StaggeredGridLayoutManager.this;
                    if ((!staggeredGridLayoutManager4.f6115n && staggeredGridLayoutManager4.getPosition(view3) >= i2) || !view3.hasFocusable()) {
                        break;
                    }
                    i4++;
                    view = view3;
                }
            }
            return view;
        }

        int h(int i2, int i3, boolean z2) {
            return f(i2, i3, z2, true, false);
        }

        int i() {
            int i2 = this.f6148c;
            if (i2 != Integer.MIN_VALUE) {
                return i2;
            }
            c();
            return this.f6148c;
        }

        int j(int i2) {
            int i3 = this.f6148c;
            if (i3 != Integer.MIN_VALUE) {
                return i3;
            }
            if (this.f6146a.size() == 0) {
                return i2;
            }
            c();
            return this.f6148c;
        }

        LayoutParams k(View view) {
            return (LayoutParams) view.getLayoutParams();
        }

        int l() {
            int i2 = this.f6147b;
            if (i2 != Integer.MIN_VALUE) {
                return i2;
            }
            d();
            return this.f6147b;
        }

        int m(int i2) {
            int i3 = this.f6147b;
            if (i3 != Integer.MIN_VALUE) {
                return i3;
            }
            if (this.f6146a.size() == 0) {
                return i2;
            }
            d();
            return this.f6147b;
        }

        void n() {
            this.f6147b = Integer.MIN_VALUE;
            this.f6148c = Integer.MIN_VALUE;
        }

        void o(int i2) {
            int i3 = this.f6147b;
            if (i3 != Integer.MIN_VALUE) {
                this.f6147b = i3 + i2;
            }
            int i4 = this.f6148c;
            if (i4 != Integer.MIN_VALUE) {
                this.f6148c = i4 + i2;
            }
        }

        void p() {
            int size = this.f6146a.size();
            View view = (View) this.f6146a.remove(size - 1);
            LayoutParams layoutParamsK = k(view);
            layoutParamsK.f6128e = null;
            if (layoutParamsK.isItemRemoved() || layoutParamsK.isItemChanged()) {
                this.f6149d -= StaggeredGridLayoutManager.this.f6113l.getDecoratedMeasurement(view);
            }
            if (size == 1) {
                this.f6147b = Integer.MIN_VALUE;
            }
            this.f6148c = Integer.MIN_VALUE;
        }

        void q() {
            View view = (View) this.f6146a.remove(0);
            LayoutParams layoutParamsK = k(view);
            layoutParamsK.f6128e = null;
            if (this.f6146a.size() == 0) {
                this.f6148c = Integer.MIN_VALUE;
            }
            if (layoutParamsK.isItemRemoved() || layoutParamsK.isItemChanged()) {
                this.f6149d -= StaggeredGridLayoutManager.this.f6113l.getDecoratedMeasurement(view);
            }
            this.f6147b = Integer.MIN_VALUE;
        }

        void r(View view) {
            LayoutParams layoutParamsK = k(view);
            layoutParamsK.f6128e = this;
            this.f6146a.add(0, view);
            this.f6147b = Integer.MIN_VALUE;
            if (this.f6146a.size() == 1) {
                this.f6148c = Integer.MIN_VALUE;
            }
            if (layoutParamsK.isItemRemoved() || layoutParamsK.isItemChanged()) {
                this.f6149d += StaggeredGridLayoutManager.this.f6113l.getDecoratedMeasurement(view);
            }
        }

        void s(int i2) {
            this.f6147b = i2;
            this.f6148c = i2;
        }
    }

    public StaggeredGridLayoutManager(Context context, AttributeSet attributeSet, int i2, int i3) {
        RecyclerView.LayoutManager.Properties properties = RecyclerView.LayoutManager.getProperties(context, attributeSet, i2, i3);
        setOrientation(properties.orientation);
        setSpanCount(properties.spanCount);
        setReverseLayout(properties.reverseLayout);
        this.mLayoutState = new LayoutState();
        createOrientationHelpers();
    }

    private void appendViewToAllSpans(View view) {
        for (int i2 = this.mSpanCount - 1; i2 >= 0; i2--) {
            this.f6112k[i2].a(view);
        }
    }

    private void applyPendingSavedState(AnchorInfo anchorInfo) {
        SavedState savedState = this.mPendingSavedState;
        int i2 = savedState.f6138c;
        if (i2 > 0) {
            if (i2 == this.mSpanCount) {
                for (int i3 = 0; i3 < this.mSpanCount; i3++) {
                    this.f6112k[i3].e();
                    SavedState savedState2 = this.mPendingSavedState;
                    int endAfterPadding = savedState2.f6139d[i3];
                    if (endAfterPadding != Integer.MIN_VALUE) {
                        endAfterPadding += savedState2.f6144i ? this.f6113l.getEndAfterPadding() : this.f6113l.getStartAfterPadding();
                    }
                    this.f6112k[i3].s(endAfterPadding);
                }
            } else {
                savedState.b();
                SavedState savedState3 = this.mPendingSavedState;
                savedState3.f6136a = savedState3.f6137b;
            }
        }
        SavedState savedState4 = this.mPendingSavedState;
        this.mLastLayoutRTL = savedState4.f6145j;
        setReverseLayout(savedState4.f6143h);
        resolveShouldLayoutReverse();
        SavedState savedState5 = this.mPendingSavedState;
        int i4 = savedState5.f6136a;
        if (i4 != -1) {
            this.f6117p = i4;
            anchorInfo.f6123c = savedState5.f6144i;
        } else {
            anchorInfo.f6123c = this.f6116o;
        }
        if (savedState5.f6140e > 1) {
            LazySpanLookup lazySpanLookup = this.f6119r;
            lazySpanLookup.f6130a = savedState5.f6141f;
            lazySpanLookup.f6131b = savedState5.f6142g;
        }
    }

    private void attachViewToSpans(View view, LayoutParams layoutParams, LayoutState layoutState) {
        if (layoutState.f5959e == 1) {
            if (layoutParams.f6129f) {
                appendViewToAllSpans(view);
                return;
            } else {
                layoutParams.f6128e.a(view);
                return;
            }
        }
        if (layoutParams.f6129f) {
            prependViewToAllSpans(view);
        } else {
            layoutParams.f6128e.r(view);
        }
    }

    private int calculateScrollDirectionForPosition(int i2) {
        if (getChildCount() == 0) {
            return this.f6116o ? 1 : -1;
        }
        return (i2 < x()) != this.f6116o ? -1 : 1;
    }

    private boolean checkSpanForGap(Span span) {
        if (this.f6116o) {
            if (span.i() < this.f6113l.getEndAfterPadding()) {
                ArrayList arrayList = span.f6146a;
                return !span.k((View) arrayList.get(arrayList.size() - 1)).f6129f;
            }
        } else if (span.l() > this.f6113l.getStartAfterPadding()) {
            return !span.k((View) span.f6146a.get(0)).f6129f;
        }
        return false;
    }

    private int computeScrollExtent(RecyclerView.State state) {
        if (getChildCount() == 0) {
            return 0;
        }
        return ScrollbarHelper.a(state, this.f6113l, v(!this.mSmoothScrollbarEnabled), u(!this.mSmoothScrollbarEnabled), this, this.mSmoothScrollbarEnabled);
    }

    private int computeScrollOffset(RecyclerView.State state) {
        if (getChildCount() == 0) {
            return 0;
        }
        return ScrollbarHelper.b(state, this.f6113l, v(!this.mSmoothScrollbarEnabled), u(!this.mSmoothScrollbarEnabled), this, this.mSmoothScrollbarEnabled, this.f6116o);
    }

    private int computeScrollRange(RecyclerView.State state) {
        if (getChildCount() == 0) {
            return 0;
        }
        return ScrollbarHelper.c(state, this.f6113l, v(!this.mSmoothScrollbarEnabled), u(!this.mSmoothScrollbarEnabled), this, this.mSmoothScrollbarEnabled);
    }

    private int convertFocusDirectionToLayoutDirection(int i2) {
        return i2 != 1 ? i2 != 2 ? i2 != 17 ? i2 != 33 ? i2 != 66 ? (i2 == 130 && this.mOrientation == 1) ? 1 : Integer.MIN_VALUE : this.mOrientation == 0 ? 1 : Integer.MIN_VALUE : this.mOrientation == 1 ? -1 : Integer.MIN_VALUE : this.mOrientation == 0 ? -1 : Integer.MIN_VALUE : (this.mOrientation != 1 && A()) ? -1 : 1 : (this.mOrientation != 1 && A()) ? 1 : -1;
    }

    private LazySpanLookup.FullSpanItem createFullSpanItemFromEnd(int i2) {
        LazySpanLookup.FullSpanItem fullSpanItem = new LazySpanLookup.FullSpanItem();
        fullSpanItem.f6134c = new int[this.mSpanCount];
        for (int i3 = 0; i3 < this.mSpanCount; i3++) {
            fullSpanItem.f6134c[i3] = i2 - this.f6112k[i3].j(i2);
        }
        return fullSpanItem;
    }

    private LazySpanLookup.FullSpanItem createFullSpanItemFromStart(int i2) {
        LazySpanLookup.FullSpanItem fullSpanItem = new LazySpanLookup.FullSpanItem();
        fullSpanItem.f6134c = new int[this.mSpanCount];
        for (int i3 = 0; i3 < this.mSpanCount; i3++) {
            fullSpanItem.f6134c[i3] = this.f6112k[i3].m(i2) - i2;
        }
        return fullSpanItem;
    }

    private void createOrientationHelpers() {
        this.f6113l = OrientationHelper.createOrientationHelper(this, this.mOrientation);
        this.f6114m = OrientationHelper.createOrientationHelper(this, 1 - this.mOrientation);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r9v0 */
    /* JADX WARN: Type inference failed for: r9v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r9v7 */
    private int fill(RecyclerView.Recycler recycler, LayoutState layoutState, RecyclerView.State state) {
        Span nextSpan;
        int decoratedMeasurement;
        int i2;
        int decoratedMeasurement2;
        int decoratedMeasurement3;
        boolean z2;
        ?? r9 = 0;
        this.mRemainingSpans.set(0, this.mSpanCount, true);
        int i3 = this.mLayoutState.f5963i ? layoutState.f5959e == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE : layoutState.f5959e == 1 ? layoutState.f5961g + layoutState.f5956b : layoutState.f5960f - layoutState.f5956b;
        updateAllRemainingSpans(layoutState.f5959e, i3);
        int endAfterPadding = this.f6116o ? this.f6113l.getEndAfterPadding() : this.f6113l.getStartAfterPadding();
        boolean z3 = false;
        while (layoutState.a(state) && (this.mLayoutState.f5963i || !this.mRemainingSpans.isEmpty())) {
            View viewB = layoutState.b(recycler);
            LayoutParams layoutParams = (LayoutParams) viewB.getLayoutParams();
            int viewLayoutPosition = layoutParams.getViewLayoutPosition();
            int iD = this.f6119r.d(viewLayoutPosition);
            boolean z4 = iD == -1 ? true : r9;
            if (z4) {
                nextSpan = layoutParams.f6129f ? this.f6112k[r9] : getNextSpan(layoutState);
                this.f6119r.h(viewLayoutPosition, nextSpan);
            } else {
                nextSpan = this.f6112k[iD];
            }
            Span span = nextSpan;
            layoutParams.f6128e = span;
            if (layoutState.f5959e == 1) {
                addView(viewB);
            } else {
                addView(viewB, r9);
            }
            measureChildWithDecorationsAndMargin(viewB, layoutParams, r9);
            if (layoutState.f5959e == 1) {
                int maxEnd = layoutParams.f6129f ? getMaxEnd(endAfterPadding) : span.j(endAfterPadding);
                int decoratedMeasurement4 = this.f6113l.getDecoratedMeasurement(viewB) + maxEnd;
                if (z4 && layoutParams.f6129f) {
                    LazySpanLookup.FullSpanItem fullSpanItemCreateFullSpanItemFromEnd = createFullSpanItemFromEnd(maxEnd);
                    fullSpanItemCreateFullSpanItemFromEnd.f6133b = -1;
                    fullSpanItemCreateFullSpanItemFromEnd.f6132a = viewLayoutPosition;
                    this.f6119r.addFullSpanItem(fullSpanItemCreateFullSpanItemFromEnd);
                }
                i2 = decoratedMeasurement4;
                decoratedMeasurement = maxEnd;
            } else {
                int minStart = layoutParams.f6129f ? getMinStart(endAfterPadding) : span.m(endAfterPadding);
                decoratedMeasurement = minStart - this.f6113l.getDecoratedMeasurement(viewB);
                if (z4 && layoutParams.f6129f) {
                    LazySpanLookup.FullSpanItem fullSpanItemCreateFullSpanItemFromStart = createFullSpanItemFromStart(minStart);
                    fullSpanItemCreateFullSpanItemFromStart.f6133b = 1;
                    fullSpanItemCreateFullSpanItemFromStart.f6132a = viewLayoutPosition;
                    this.f6119r.addFullSpanItem(fullSpanItemCreateFullSpanItemFromStart);
                }
                i2 = minStart;
            }
            if (layoutParams.f6129f && layoutState.f5958d == -1) {
                if (z4) {
                    this.mLaidOutInvalidFullSpan = true;
                } else {
                    if (!(layoutState.f5959e == 1 ? r() : s())) {
                        LazySpanLookup.FullSpanItem fullSpanItem = this.f6119r.getFullSpanItem(viewLayoutPosition);
                        if (fullSpanItem != null) {
                            fullSpanItem.f6135d = true;
                        }
                        this.mLaidOutInvalidFullSpan = true;
                    }
                }
            }
            attachViewToSpans(viewB, layoutParams, layoutState);
            if (A() && this.mOrientation == 1) {
                int endAfterPadding2 = layoutParams.f6129f ? this.f6114m.getEndAfterPadding() : this.f6114m.getEndAfterPadding() - (((this.mSpanCount - 1) - span.f6150e) * this.mSizePerSpan);
                decoratedMeasurement3 = endAfterPadding2;
                decoratedMeasurement2 = endAfterPadding2 - this.f6114m.getDecoratedMeasurement(viewB);
            } else {
                int startAfterPadding = layoutParams.f6129f ? this.f6114m.getStartAfterPadding() : (span.f6150e * this.mSizePerSpan) + this.f6114m.getStartAfterPadding();
                decoratedMeasurement2 = startAfterPadding;
                decoratedMeasurement3 = this.f6114m.getDecoratedMeasurement(viewB) + startAfterPadding;
            }
            if (this.mOrientation == 1) {
                layoutDecoratedWithMargins(viewB, decoratedMeasurement2, decoratedMeasurement, decoratedMeasurement3, i2);
            } else {
                layoutDecoratedWithMargins(viewB, decoratedMeasurement, decoratedMeasurement2, i2, decoratedMeasurement3);
            }
            if (layoutParams.f6129f) {
                updateAllRemainingSpans(this.mLayoutState.f5959e, i3);
            } else {
                updateRemainingSpans(span, this.mLayoutState.f5959e, i3);
            }
            recycle(recycler, this.mLayoutState);
            if (!this.mLayoutState.f5962h || !viewB.hasFocusable()) {
                z2 = false;
            } else if (layoutParams.f6129f) {
                this.mRemainingSpans.clear();
                z2 = false;
            } else {
                z2 = false;
                this.mRemainingSpans.set(span.f6150e, false);
            }
            r9 = z2;
            z3 = true;
        }
        int i4 = r9;
        if (!z3) {
            recycle(recycler, this.mLayoutState);
        }
        int startAfterPadding2 = this.mLayoutState.f5959e == -1 ? this.f6113l.getStartAfterPadding() - getMinStart(this.f6113l.getStartAfterPadding()) : getMaxEnd(this.f6113l.getEndAfterPadding()) - this.f6113l.getEndAfterPadding();
        return startAfterPadding2 > 0 ? Math.min(layoutState.f5956b, startAfterPadding2) : i4;
    }

    private int findFirstReferenceChildPosition(int i2) {
        int childCount = getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            int position = getPosition(getChildAt(i3));
            if (position >= 0 && position < i2) {
                return position;
            }
        }
        return 0;
    }

    private int findLastReferenceChildPosition(int i2) {
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            int position = getPosition(getChildAt(childCount));
            if (position >= 0 && position < i2) {
                return position;
            }
        }
        return 0;
    }

    private void fixEndGap(RecyclerView.Recycler recycler, RecyclerView.State state, boolean z2) {
        int endAfterPadding;
        int maxEnd = getMaxEnd(Integer.MIN_VALUE);
        if (maxEnd != Integer.MIN_VALUE && (endAfterPadding = this.f6113l.getEndAfterPadding() - maxEnd) > 0) {
            int i2 = endAfterPadding - (-C(-endAfterPadding, recycler, state));
            if (!z2 || i2 <= 0) {
                return;
            }
            this.f6113l.offsetChildren(i2);
        }
    }

    private void fixStartGap(RecyclerView.Recycler recycler, RecyclerView.State state, boolean z2) {
        int startAfterPadding;
        int minStart = getMinStart(Integer.MAX_VALUE);
        if (minStart != Integer.MAX_VALUE && (startAfterPadding = minStart - this.f6113l.getStartAfterPadding()) > 0) {
            int iC = startAfterPadding - C(startAfterPadding, recycler, state);
            if (!z2 || iC <= 0) {
                return;
            }
            this.f6113l.offsetChildren(-iC);
        }
    }

    private int getMaxEnd(int i2) {
        int iJ = this.f6112k[0].j(i2);
        for (int i3 = 1; i3 < this.mSpanCount; i3++) {
            int iJ2 = this.f6112k[i3].j(i2);
            if (iJ2 > iJ) {
                iJ = iJ2;
            }
        }
        return iJ;
    }

    private int getMaxStart(int i2) {
        int iM = this.f6112k[0].m(i2);
        for (int i3 = 1; i3 < this.mSpanCount; i3++) {
            int iM2 = this.f6112k[i3].m(i2);
            if (iM2 > iM) {
                iM = iM2;
            }
        }
        return iM;
    }

    private int getMinEnd(int i2) {
        int iJ = this.f6112k[0].j(i2);
        for (int i3 = 1; i3 < this.mSpanCount; i3++) {
            int iJ2 = this.f6112k[i3].j(i2);
            if (iJ2 < iJ) {
                iJ = iJ2;
            }
        }
        return iJ;
    }

    private int getMinStart(int i2) {
        int iM = this.f6112k[0].m(i2);
        for (int i3 = 1; i3 < this.mSpanCount; i3++) {
            int iM2 = this.f6112k[i3].m(i2);
            if (iM2 < iM) {
                iM = iM2;
            }
        }
        return iM;
    }

    private Span getNextSpan(LayoutState layoutState) {
        int i2;
        int i3;
        int i4;
        if (preferLastSpan(layoutState.f5959e)) {
            i3 = this.mSpanCount - 1;
            i2 = -1;
            i4 = -1;
        } else {
            i2 = this.mSpanCount;
            i3 = 0;
            i4 = 1;
        }
        Span span = null;
        if (layoutState.f5959e == 1) {
            int startAfterPadding = this.f6113l.getStartAfterPadding();
            int i5 = Integer.MAX_VALUE;
            while (i3 != i2) {
                Span span2 = this.f6112k[i3];
                int iJ = span2.j(startAfterPadding);
                if (iJ < i5) {
                    span = span2;
                    i5 = iJ;
                }
                i3 += i4;
            }
            return span;
        }
        int endAfterPadding = this.f6113l.getEndAfterPadding();
        int i6 = Integer.MIN_VALUE;
        while (i3 != i2) {
            Span span3 = this.f6112k[i3];
            int iM = span3.m(endAfterPadding);
            if (iM > i6) {
                span = span3;
                i6 = iM;
            }
            i3 += i4;
        }
        return span;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0026  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x003d  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0044 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0045  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void handleUpdate(int r7, int r8, int r9) {
        /*
            r6 = this;
            boolean r0 = r6.f6116o
            if (r0 == 0) goto L9
            int r0 = r6.y()
            goto Ld
        L9:
            int r0 = r6.x()
        Ld:
            r1 = 8
            if (r9 != r1) goto L1b
            if (r7 >= r8) goto L17
            int r2 = r8 + 1
        L15:
            r3 = r7
            goto L1e
        L17:
            int r2 = r7 + 1
            r3 = r8
            goto L1e
        L1b:
            int r2 = r7 + r8
            goto L15
        L1e:
            androidx.recyclerview.widget.StaggeredGridLayoutManager$LazySpanLookup r4 = r6.f6119r
            r4.e(r3)
            r4 = 1
            if (r9 == r4) goto L3d
            r5 = 2
            if (r9 == r5) goto L37
            if (r9 == r1) goto L2c
            goto L42
        L2c:
            androidx.recyclerview.widget.StaggeredGridLayoutManager$LazySpanLookup r9 = r6.f6119r
            r9.g(r7, r4)
            androidx.recyclerview.widget.StaggeredGridLayoutManager$LazySpanLookup r7 = r6.f6119r
            r7.f(r8, r4)
            goto L42
        L37:
            androidx.recyclerview.widget.StaggeredGridLayoutManager$LazySpanLookup r9 = r6.f6119r
            r9.g(r7, r8)
            goto L42
        L3d:
            androidx.recyclerview.widget.StaggeredGridLayoutManager$LazySpanLookup r9 = r6.f6119r
            r9.f(r7, r8)
        L42:
            if (r2 > r0) goto L45
            return
        L45:
            boolean r7 = r6.f6116o
            if (r7 == 0) goto L4e
            int r7 = r6.x()
            goto L52
        L4e:
            int r7 = r6.y()
        L52:
            if (r3 > r7) goto L57
            r6.requestLayout()
        L57:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.StaggeredGridLayoutManager.handleUpdate(int, int, int):void");
    }

    private void measureChildWithDecorationsAndMargin(View view, LayoutParams layoutParams, boolean z2) {
        if (layoutParams.f6129f) {
            if (this.mOrientation == 1) {
                measureChildWithDecorationsAndMargin(view, this.mFullSizeSpec, RecyclerView.LayoutManager.getChildMeasureSpec(getHeight(), getHeightMode(), getPaddingTop() + getPaddingBottom(), ((ViewGroup.MarginLayoutParams) layoutParams).height, true), z2);
                return;
            } else {
                measureChildWithDecorationsAndMargin(view, RecyclerView.LayoutManager.getChildMeasureSpec(getWidth(), getWidthMode(), getPaddingLeft() + getPaddingRight(), ((ViewGroup.MarginLayoutParams) layoutParams).width, true), this.mFullSizeSpec, z2);
                return;
            }
        }
        if (this.mOrientation == 1) {
            measureChildWithDecorationsAndMargin(view, RecyclerView.LayoutManager.getChildMeasureSpec(this.mSizePerSpan, getWidthMode(), 0, ((ViewGroup.MarginLayoutParams) layoutParams).width, false), RecyclerView.LayoutManager.getChildMeasureSpec(getHeight(), getHeightMode(), getPaddingTop() + getPaddingBottom(), ((ViewGroup.MarginLayoutParams) layoutParams).height, true), z2);
        } else {
            measureChildWithDecorationsAndMargin(view, RecyclerView.LayoutManager.getChildMeasureSpec(getWidth(), getWidthMode(), getPaddingLeft() + getPaddingRight(), ((ViewGroup.MarginLayoutParams) layoutParams).width, true), RecyclerView.LayoutManager.getChildMeasureSpec(this.mSizePerSpan, getHeightMode(), 0, ((ViewGroup.MarginLayoutParams) layoutParams).height, false), z2);
        }
    }

    private boolean preferLastSpan(int i2) {
        if (this.mOrientation == 0) {
            return (i2 == -1) != this.f6116o;
        }
        return ((i2 == -1) == this.f6116o) == A();
    }

    private void prependViewToAllSpans(View view) {
        for (int i2 = this.mSpanCount - 1; i2 >= 0; i2--) {
            this.f6112k[i2].r(view);
        }
    }

    private void recycle(RecyclerView.Recycler recycler, LayoutState layoutState) {
        if (!layoutState.f5955a || layoutState.f5963i) {
            return;
        }
        if (layoutState.f5956b == 0) {
            if (layoutState.f5959e == -1) {
                recycleFromEnd(recycler, layoutState.f5961g);
                return;
            } else {
                recycleFromStart(recycler, layoutState.f5960f);
                return;
            }
        }
        if (layoutState.f5959e != -1) {
            int minEnd = getMinEnd(layoutState.f5961g) - layoutState.f5961g;
            recycleFromStart(recycler, minEnd < 0 ? layoutState.f5960f : Math.min(minEnd, layoutState.f5956b) + layoutState.f5960f);
        } else {
            int i2 = layoutState.f5960f;
            int maxStart = i2 - getMaxStart(i2);
            recycleFromEnd(recycler, maxStart < 0 ? layoutState.f5961g : layoutState.f5961g - Math.min(maxStart, layoutState.f5956b));
        }
    }

    private void recycleFromEnd(RecyclerView.Recycler recycler, int i2) {
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = getChildAt(childCount);
            if (this.f6113l.getDecoratedStart(childAt) < i2 || this.f6113l.getTransformedStartWithDecoration(childAt) < i2) {
                return;
            }
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            if (layoutParams.f6129f) {
                for (int i3 = 0; i3 < this.mSpanCount; i3++) {
                    if (this.f6112k[i3].f6146a.size() == 1) {
                        return;
                    }
                }
                for (int i4 = 0; i4 < this.mSpanCount; i4++) {
                    this.f6112k[i4].p();
                }
            } else if (layoutParams.f6128e.f6146a.size() == 1) {
                return;
            } else {
                layoutParams.f6128e.p();
            }
            removeAndRecycleView(childAt, recycler);
        }
    }

    private void recycleFromStart(RecyclerView.Recycler recycler, int i2) {
        while (getChildCount() > 0) {
            View childAt = getChildAt(0);
            if (this.f6113l.getDecoratedEnd(childAt) > i2 || this.f6113l.getTransformedEndWithDecoration(childAt) > i2) {
                return;
            }
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            if (layoutParams.f6129f) {
                for (int i3 = 0; i3 < this.mSpanCount; i3++) {
                    if (this.f6112k[i3].f6146a.size() == 1) {
                        return;
                    }
                }
                for (int i4 = 0; i4 < this.mSpanCount; i4++) {
                    this.f6112k[i4].q();
                }
            } else if (layoutParams.f6128e.f6146a.size() == 1) {
                return;
            } else {
                layoutParams.f6128e.q();
            }
            removeAndRecycleView(childAt, recycler);
        }
    }

    private void repositionToWrapContentIfNecessary() {
        if (this.f6114m.getMode() == 1073741824) {
            return;
        }
        int childCount = getChildCount();
        float fMax = 0.0f;
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            float decoratedMeasurement = this.f6114m.getDecoratedMeasurement(childAt);
            if (decoratedMeasurement >= fMax) {
                if (((LayoutParams) childAt.getLayoutParams()).isFullSpan()) {
                    decoratedMeasurement = (decoratedMeasurement * 1.0f) / this.mSpanCount;
                }
                fMax = Math.max(fMax, decoratedMeasurement);
            }
        }
        int i3 = this.mSizePerSpan;
        int iRound = Math.round(fMax * this.mSpanCount);
        if (this.f6114m.getMode() == Integer.MIN_VALUE) {
            iRound = Math.min(iRound, this.f6114m.getTotalSpace());
        }
        F(iRound);
        if (this.mSizePerSpan == i3) {
            return;
        }
        for (int i4 = 0; i4 < childCount; i4++) {
            View childAt2 = getChildAt(i4);
            LayoutParams layoutParams = (LayoutParams) childAt2.getLayoutParams();
            if (!layoutParams.f6129f) {
                if (A() && this.mOrientation == 1) {
                    int i5 = this.mSpanCount;
                    int i6 = layoutParams.f6128e.f6150e;
                    childAt2.offsetLeftAndRight(((-((i5 - 1) - i6)) * this.mSizePerSpan) - ((-((i5 - 1) - i6)) * i3));
                } else {
                    int i7 = layoutParams.f6128e.f6150e;
                    int i8 = this.mSizePerSpan * i7;
                    int i9 = i7 * i3;
                    if (this.mOrientation == 1) {
                        childAt2.offsetLeftAndRight(i8 - i9);
                    } else {
                        childAt2.offsetTopAndBottom(i8 - i9);
                    }
                }
            }
        }
    }

    private void resolveShouldLayoutReverse() {
        if (this.mOrientation == 1 || !A()) {
            this.f6116o = this.f6115n;
        } else {
            this.f6116o = !this.f6115n;
        }
    }

    private void setLayoutStateDirection(int i2) {
        LayoutState layoutState = this.mLayoutState;
        layoutState.f5959e = i2;
        layoutState.f5958d = this.f6116o != (i2 == -1) ? -1 : 1;
    }

    private void updateAllRemainingSpans(int i2, int i3) {
        for (int i4 = 0; i4 < this.mSpanCount; i4++) {
            if (!this.f6112k[i4].f6146a.isEmpty()) {
                updateRemainingSpans(this.f6112k[i4], i2, i3);
            }
        }
    }

    private boolean updateAnchorFromChildren(RecyclerView.State state, AnchorInfo anchorInfo) {
        anchorInfo.f6121a = this.mLastLayoutFromEnd ? findLastReferenceChildPosition(state.getItemCount()) : findFirstReferenceChildPosition(state.getItemCount());
        anchorInfo.f6122b = Integer.MIN_VALUE;
        return true;
    }

    private void updateLayoutState(int i2, RecyclerView.State state) {
        int totalSpace;
        int totalSpace2;
        int targetScrollPosition;
        LayoutState layoutState = this.mLayoutState;
        boolean z2 = false;
        layoutState.f5956b = 0;
        layoutState.f5957c = i2;
        if (!isSmoothScrolling() || (targetScrollPosition = state.getTargetScrollPosition()) == -1) {
            totalSpace = 0;
            totalSpace2 = 0;
        } else {
            if (this.f6116o == (targetScrollPosition < i2)) {
                totalSpace = this.f6113l.getTotalSpace();
                totalSpace2 = 0;
            } else {
                totalSpace2 = this.f6113l.getTotalSpace();
                totalSpace = 0;
            }
        }
        if (getClipToPadding()) {
            this.mLayoutState.f5960f = this.f6113l.getStartAfterPadding() - totalSpace2;
            this.mLayoutState.f5961g = this.f6113l.getEndAfterPadding() + totalSpace;
        } else {
            this.mLayoutState.f5961g = this.f6113l.getEnd() + totalSpace;
            this.mLayoutState.f5960f = -totalSpace2;
        }
        LayoutState layoutState2 = this.mLayoutState;
        layoutState2.f5962h = false;
        layoutState2.f5955a = true;
        if (this.f6113l.getMode() == 0 && this.f6113l.getEnd() == 0) {
            z2 = true;
        }
        layoutState2.f5963i = z2;
    }

    private void updateRemainingSpans(Span span, int i2, int i3) {
        int deletedSize = span.getDeletedSize();
        if (i2 == -1) {
            if (span.l() + deletedSize <= i3) {
                this.mRemainingSpans.set(span.f6150e, false);
            }
        } else if (span.i() - deletedSize >= i3) {
            this.mRemainingSpans.set(span.f6150e, false);
        }
    }

    private int updateSpecWithExtra(int i2, int i3, int i4) {
        if (i3 == 0 && i4 == 0) {
            return i2;
        }
        int mode = View.MeasureSpec.getMode(i2);
        return (mode == Integer.MIN_VALUE || mode == 1073741824) ? View.MeasureSpec.makeMeasureSpec(Math.max(0, (View.MeasureSpec.getSize(i2) - i3) - i4), mode) : i2;
    }

    boolean A() {
        return getLayoutDirection() == 1;
    }

    void B(int i2, RecyclerView.State state) {
        int iX;
        int i3;
        if (i2 > 0) {
            iX = y();
            i3 = 1;
        } else {
            iX = x();
            i3 = -1;
        }
        this.mLayoutState.f5955a = true;
        updateLayoutState(iX, state);
        setLayoutStateDirection(i3);
        LayoutState layoutState = this.mLayoutState;
        layoutState.f5957c = iX + layoutState.f5958d;
        layoutState.f5956b = Math.abs(i2);
    }

    int C(int i2, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getChildCount() == 0 || i2 == 0) {
            return 0;
        }
        B(i2, state);
        int iFill = fill(recycler, this.mLayoutState, state);
        if (this.mLayoutState.f5956b >= iFill) {
            i2 = i2 < 0 ? -iFill : iFill;
        }
        this.f6113l.offsetChildren(-i2);
        this.mLastLayoutFromEnd = this.f6116o;
        LayoutState layoutState = this.mLayoutState;
        layoutState.f5956b = 0;
        recycle(recycler, layoutState);
        return i2;
    }

    boolean D(RecyclerView.State state, AnchorInfo anchorInfo) {
        int i2;
        if (!state.isPreLayout() && (i2 = this.f6117p) != -1) {
            if (i2 >= 0 && i2 < state.getItemCount()) {
                SavedState savedState = this.mPendingSavedState;
                if (savedState == null || savedState.f6136a == -1 || savedState.f6138c < 1) {
                    View viewFindViewByPosition = findViewByPosition(this.f6117p);
                    if (viewFindViewByPosition != null) {
                        anchorInfo.f6121a = this.f6116o ? y() : x();
                        if (this.f6118q != Integer.MIN_VALUE) {
                            if (anchorInfo.f6123c) {
                                anchorInfo.f6122b = (this.f6113l.getEndAfterPadding() - this.f6118q) - this.f6113l.getDecoratedEnd(viewFindViewByPosition);
                            } else {
                                anchorInfo.f6122b = (this.f6113l.getStartAfterPadding() + this.f6118q) - this.f6113l.getDecoratedStart(viewFindViewByPosition);
                            }
                            return true;
                        }
                        if (this.f6113l.getDecoratedMeasurement(viewFindViewByPosition) > this.f6113l.getTotalSpace()) {
                            anchorInfo.f6122b = anchorInfo.f6123c ? this.f6113l.getEndAfterPadding() : this.f6113l.getStartAfterPadding();
                            return true;
                        }
                        int decoratedStart = this.f6113l.getDecoratedStart(viewFindViewByPosition) - this.f6113l.getStartAfterPadding();
                        if (decoratedStart < 0) {
                            anchorInfo.f6122b = -decoratedStart;
                            return true;
                        }
                        int endAfterPadding = this.f6113l.getEndAfterPadding() - this.f6113l.getDecoratedEnd(viewFindViewByPosition);
                        if (endAfterPadding < 0) {
                            anchorInfo.f6122b = endAfterPadding;
                            return true;
                        }
                        anchorInfo.f6122b = Integer.MIN_VALUE;
                    } else {
                        int i3 = this.f6117p;
                        anchorInfo.f6121a = i3;
                        int i4 = this.f6118q;
                        if (i4 == Integer.MIN_VALUE) {
                            anchorInfo.f6123c = calculateScrollDirectionForPosition(i3) == 1;
                            anchorInfo.a();
                        } else {
                            anchorInfo.b(i4);
                        }
                        anchorInfo.f6124d = true;
                    }
                } else {
                    anchorInfo.f6122b = Integer.MIN_VALUE;
                    anchorInfo.f6121a = this.f6117p;
                }
                return true;
            }
            this.f6117p = -1;
            this.f6118q = Integer.MIN_VALUE;
        }
        return false;
    }

    void E(RecyclerView.State state, AnchorInfo anchorInfo) {
        if (D(state, anchorInfo) || updateAnchorFromChildren(state, anchorInfo)) {
            return;
        }
        anchorInfo.a();
        anchorInfo.f6121a = 0;
    }

    void F(int i2) {
        this.mSizePerSpan = i2 / this.mSpanCount;
        this.mFullSizeSpec = View.MeasureSpec.makeMeasureSpec(i2, this.f6114m.getMode());
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void assertNotInLayoutOrScroll(String str) {
        if (this.mPendingSavedState == null) {
            super.assertNotInLayoutOrScroll(str);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public boolean canScrollHorizontally() {
        return this.mOrientation == 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public boolean canScrollVertically() {
        return this.mOrientation == 1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public boolean checkLayoutParams(RecyclerView.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public void collectAdjacentPrefetchPositions(int i2, int i3, RecyclerView.State state, RecyclerView.LayoutManager.LayoutPrefetchRegistry layoutPrefetchRegistry) {
        int iJ;
        int iM;
        if (this.mOrientation != 0) {
            i2 = i3;
        }
        if (getChildCount() == 0 || i2 == 0) {
            return;
        }
        B(i2, state);
        int[] iArr = this.mPrefetchDistances;
        if (iArr == null || iArr.length < this.mSpanCount) {
            this.mPrefetchDistances = new int[this.mSpanCount];
        }
        int i4 = 0;
        for (int i5 = 0; i5 < this.mSpanCount; i5++) {
            LayoutState layoutState = this.mLayoutState;
            if (layoutState.f5958d == -1) {
                iJ = layoutState.f5960f;
                iM = this.f6112k[i5].m(iJ);
            } else {
                iJ = this.f6112k[i5].j(layoutState.f5961g);
                iM = this.mLayoutState.f5961g;
            }
            int i6 = iJ - iM;
            if (i6 >= 0) {
                this.mPrefetchDistances[i4] = i6;
                i4++;
            }
        }
        Arrays.sort(this.mPrefetchDistances, 0, i4);
        for (int i7 = 0; i7 < i4 && this.mLayoutState.a(state); i7++) {
            layoutPrefetchRegistry.addPosition(this.mLayoutState.f5957c, this.mPrefetchDistances[i7]);
            LayoutState layoutState2 = this.mLayoutState;
            layoutState2.f5957c += layoutState2.f5958d;
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
        int iCalculateScrollDirectionForPosition = calculateScrollDirectionForPosition(i2);
        PointF pointF = new PointF();
        if (iCalculateScrollDirectionForPosition == 0) {
            return null;
        }
        if (this.mOrientation == 0) {
            pointF.x = iCalculateScrollDirectionForPosition;
            pointF.y = 0.0f;
        } else {
            pointF.x = 0.0f;
            pointF.y = iCalculateScrollDirectionForPosition;
        }
        return pointF;
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

    public int[] findFirstCompletelyVisibleItemPositions(int[] iArr) {
        if (iArr == null) {
            iArr = new int[this.mSpanCount];
        } else if (iArr.length < this.mSpanCount) {
            throw new IllegalArgumentException("Provided int[]'s size must be more than or equal to span count. Expected:" + this.mSpanCount + ", array size:" + iArr.length);
        }
        for (int i2 = 0; i2 < this.mSpanCount; i2++) {
            iArr[i2] = this.f6112k[i2].findFirstCompletelyVisibleItemPosition();
        }
        return iArr;
    }

    public int[] findFirstVisibleItemPositions(int[] iArr) {
        if (iArr == null) {
            iArr = new int[this.mSpanCount];
        } else if (iArr.length < this.mSpanCount) {
            throw new IllegalArgumentException("Provided int[]'s size must be more than or equal to span count. Expected:" + this.mSpanCount + ", array size:" + iArr.length);
        }
        for (int i2 = 0; i2 < this.mSpanCount; i2++) {
            iArr[i2] = this.f6112k[i2].findFirstVisibleItemPosition();
        }
        return iArr;
    }

    public int[] findLastCompletelyVisibleItemPositions(int[] iArr) {
        if (iArr == null) {
            iArr = new int[this.mSpanCount];
        } else if (iArr.length < this.mSpanCount) {
            throw new IllegalArgumentException("Provided int[]'s size must be more than or equal to span count. Expected:" + this.mSpanCount + ", array size:" + iArr.length);
        }
        for (int i2 = 0; i2 < this.mSpanCount; i2++) {
            iArr[i2] = this.f6112k[i2].findLastCompletelyVisibleItemPosition();
        }
        return iArr;
    }

    public int[] findLastVisibleItemPositions(int[] iArr) {
        if (iArr == null) {
            iArr = new int[this.mSpanCount];
        } else if (iArr.length < this.mSpanCount) {
            throw new IllegalArgumentException("Provided int[]'s size must be more than or equal to span count. Expected:" + this.mSpanCount + ", array size:" + iArr.length);
        }
        for (int i2 = 0; i2 < this.mSpanCount; i2++) {
            iArr[i2] = this.f6112k[i2].findLastVisibleItemPosition();
        }
        return iArr;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return this.mOrientation == 0 ? new LayoutParams(-2, -1) : new LayoutParams(-1, -2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public RecyclerView.LayoutParams generateLayoutParams(Context context, AttributeSet attributeSet) {
        return new LayoutParams(context, attributeSet);
    }

    public int getGapStrategy() {
        return this.mGapStrategy;
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    public boolean getReverseLayout() {
        return this.f6115n;
    }

    public int getSpanCount() {
        return this.mSpanCount;
    }

    public void invalidateSpanAssignments() {
        this.f6119r.a();
        requestLayout();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public boolean isAutoMeasureEnabled() {
        return this.mGapStrategy != 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void offsetChildrenHorizontal(int i2) {
        super.offsetChildrenHorizontal(i2);
        for (int i3 = 0; i3 < this.mSpanCount; i3++) {
            this.f6112k[i3].o(i2);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void offsetChildrenVertical(int i2) {
        super.offsetChildrenVertical(i2);
        for (int i3 = 0; i3 < this.mSpanCount; i3++) {
            this.f6112k[i3].o(i2);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onAdapterChanged(@Nullable RecyclerView.Adapter adapter, @Nullable RecyclerView.Adapter adapter2) {
        this.f6119r.a();
        for (int i2 = 0; i2 < this.mSpanCount; i2++) {
            this.f6112k[i2].e();
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onDetachedFromWindow(RecyclerView recyclerView, RecyclerView.Recycler recycler) {
        super.onDetachedFromWindow(recyclerView, recycler);
        removeCallbacks(this.mCheckForGapsRunnable);
        for (int i2 = 0; i2 < this.mSpanCount; i2++) {
            this.f6112k[i2].e();
        }
        recyclerView.requestLayout();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    @Nullable
    public View onFocusSearchFailed(View view, int i2, RecyclerView.Recycler recycler, RecyclerView.State state) {
        View viewFindContainingItemView;
        View focusableViewAfter;
        if (getChildCount() == 0 || (viewFindContainingItemView = findContainingItemView(view)) == null) {
            return null;
        }
        resolveShouldLayoutReverse();
        int iConvertFocusDirectionToLayoutDirection = convertFocusDirectionToLayoutDirection(i2);
        if (iConvertFocusDirectionToLayoutDirection == Integer.MIN_VALUE) {
            return null;
        }
        LayoutParams layoutParams = (LayoutParams) viewFindContainingItemView.getLayoutParams();
        boolean z2 = layoutParams.f6129f;
        Span span = layoutParams.f6128e;
        int iY = iConvertFocusDirectionToLayoutDirection == 1 ? y() : x();
        updateLayoutState(iY, state);
        setLayoutStateDirection(iConvertFocusDirectionToLayoutDirection);
        LayoutState layoutState = this.mLayoutState;
        layoutState.f5957c = layoutState.f5958d + iY;
        layoutState.f5956b = (int) (this.f6113l.getTotalSpace() * MAX_SCROLL_FACTOR);
        LayoutState layoutState2 = this.mLayoutState;
        layoutState2.f5962h = true;
        layoutState2.f5955a = false;
        fill(recycler, layoutState2, state);
        this.mLastLayoutFromEnd = this.f6116o;
        if (!z2 && (focusableViewAfter = span.getFocusableViewAfter(iY, iConvertFocusDirectionToLayoutDirection)) != null && focusableViewAfter != viewFindContainingItemView) {
            return focusableViewAfter;
        }
        if (preferLastSpan(iConvertFocusDirectionToLayoutDirection)) {
            for (int i3 = this.mSpanCount - 1; i3 >= 0; i3--) {
                View focusableViewAfter2 = this.f6112k[i3].getFocusableViewAfter(iY, iConvertFocusDirectionToLayoutDirection);
                if (focusableViewAfter2 != null && focusableViewAfter2 != viewFindContainingItemView) {
                    return focusableViewAfter2;
                }
            }
        } else {
            for (int i4 = 0; i4 < this.mSpanCount; i4++) {
                View focusableViewAfter3 = this.f6112k[i4].getFocusableViewAfter(iY, iConvertFocusDirectionToLayoutDirection);
                if (focusableViewAfter3 != null && focusableViewAfter3 != viewFindContainingItemView) {
                    return focusableViewAfter3;
                }
            }
        }
        boolean z3 = (this.f6115n ^ true) == (iConvertFocusDirectionToLayoutDirection == -1);
        if (!z2) {
            View viewFindViewByPosition = findViewByPosition(z3 ? span.findFirstPartiallyVisibleItemPosition() : span.findLastPartiallyVisibleItemPosition());
            if (viewFindViewByPosition != null && viewFindViewByPosition != viewFindContainingItemView) {
                return viewFindViewByPosition;
            }
        }
        if (preferLastSpan(iConvertFocusDirectionToLayoutDirection)) {
            for (int i5 = this.mSpanCount - 1; i5 >= 0; i5--) {
                if (i5 != span.f6150e) {
                    View viewFindViewByPosition2 = findViewByPosition(z3 ? this.f6112k[i5].findFirstPartiallyVisibleItemPosition() : this.f6112k[i5].findLastPartiallyVisibleItemPosition());
                    if (viewFindViewByPosition2 != null && viewFindViewByPosition2 != viewFindContainingItemView) {
                        return viewFindViewByPosition2;
                    }
                }
            }
        } else {
            for (int i6 = 0; i6 < this.mSpanCount; i6++) {
                View viewFindViewByPosition3 = findViewByPosition(z3 ? this.f6112k[i6].findFirstPartiallyVisibleItemPosition() : this.f6112k[i6].findLastPartiallyVisibleItemPosition());
                if (viewFindViewByPosition3 != null && viewFindViewByPosition3 != viewFindContainingItemView) {
                    return viewFindViewByPosition3;
                }
            }
        }
        return null;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        if (getChildCount() > 0) {
            View viewV = v(false);
            View viewU = u(false);
            if (viewV == null || viewU == null) {
                return;
            }
            int position = getPosition(viewV);
            int position2 = getPosition(viewU);
            if (position < position2) {
                accessibilityEvent.setFromIndex(position);
                accessibilityEvent.setToIndex(position2);
            } else {
                accessibilityEvent.setFromIndex(position2);
                accessibilityEvent.setToIndex(position);
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onItemsAdded(RecyclerView recyclerView, int i2, int i3) {
        handleUpdate(i2, i3, 1);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onItemsChanged(RecyclerView recyclerView) {
        this.f6119r.a();
        requestLayout();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onItemsMoved(RecyclerView recyclerView, int i2, int i3, int i4) {
        handleUpdate(i2, i3, 8);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onItemsRemoved(RecyclerView recyclerView, int i2, int i3) {
        handleUpdate(i2, i3, 2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onItemsUpdated(RecyclerView recyclerView, int i2, int i3, Object obj) {
        handleUpdate(i2, i3, 4);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        onLayoutChildren(recycler, state, true);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onLayoutCompleted(RecyclerView.State state) {
        super.onLayoutCompleted(state);
        this.f6117p = -1;
        this.f6118q = Integer.MIN_VALUE;
        this.mPendingSavedState = null;
        this.mAnchorInfo.c();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof SavedState) {
            SavedState savedState = (SavedState) parcelable;
            this.mPendingSavedState = savedState;
            if (this.f6117p != -1) {
                savedState.a();
                this.mPendingSavedState.b();
            }
            requestLayout();
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public Parcelable onSaveInstanceState() {
        int iM;
        int startAfterPadding;
        int[] iArr;
        if (this.mPendingSavedState != null) {
            return new SavedState(this.mPendingSavedState);
        }
        SavedState savedState = new SavedState();
        savedState.f6143h = this.f6115n;
        savedState.f6144i = this.mLastLayoutFromEnd;
        savedState.f6145j = this.mLastLayoutRTL;
        LazySpanLookup lazySpanLookup = this.f6119r;
        if (lazySpanLookup == null || (iArr = lazySpanLookup.f6130a) == null) {
            savedState.f6140e = 0;
        } else {
            savedState.f6141f = iArr;
            savedState.f6140e = iArr.length;
            savedState.f6142g = lazySpanLookup.f6131b;
        }
        if (getChildCount() > 0) {
            savedState.f6136a = this.mLastLayoutFromEnd ? y() : x();
            savedState.f6137b = w();
            int i2 = this.mSpanCount;
            savedState.f6138c = i2;
            savedState.f6139d = new int[i2];
            for (int i3 = 0; i3 < this.mSpanCount; i3++) {
                if (this.mLastLayoutFromEnd) {
                    iM = this.f6112k[i3].j(Integer.MIN_VALUE);
                    if (iM != Integer.MIN_VALUE) {
                        startAfterPadding = this.f6113l.getEndAfterPadding();
                        iM -= startAfterPadding;
                    }
                } else {
                    iM = this.f6112k[i3].m(Integer.MIN_VALUE);
                    if (iM != Integer.MIN_VALUE) {
                        startAfterPadding = this.f6113l.getStartAfterPadding();
                        iM -= startAfterPadding;
                    }
                }
                savedState.f6139d[i3] = iM;
            }
        } else {
            savedState.f6136a = -1;
            savedState.f6137b = -1;
            savedState.f6138c = 0;
        }
        return savedState;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onScrollStateChanged(int i2) {
        if (i2 == 0) {
            t();
        }
    }

    boolean r() {
        int iJ = this.f6112k[0].j(Integer.MIN_VALUE);
        for (int i2 = 1; i2 < this.mSpanCount; i2++) {
            if (this.f6112k[i2].j(Integer.MIN_VALUE) != iJ) {
                return false;
            }
        }
        return true;
    }

    boolean s() {
        int iM = this.f6112k[0].m(Integer.MIN_VALUE);
        for (int i2 = 1; i2 < this.mSpanCount; i2++) {
            if (this.f6112k[i2].m(Integer.MIN_VALUE) != iM) {
                return false;
            }
        }
        return true;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int scrollHorizontallyBy(int i2, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return C(i2, recycler, state);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void scrollToPosition(int i2) {
        SavedState savedState = this.mPendingSavedState;
        if (savedState != null && savedState.f6136a != i2) {
            savedState.a();
        }
        this.f6117p = i2;
        this.f6118q = Integer.MIN_VALUE;
        requestLayout();
    }

    public void scrollToPositionWithOffset(int i2, int i3) {
        SavedState savedState = this.mPendingSavedState;
        if (savedState != null) {
            savedState.a();
        }
        this.f6117p = i2;
        this.f6118q = i3;
        requestLayout();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int scrollVerticallyBy(int i2, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return C(i2, recycler, state);
    }

    public void setGapStrategy(int i2) {
        assertNotInLayoutOrScroll(null);
        if (i2 == this.mGapStrategy) {
            return;
        }
        if (i2 != 0 && i2 != 2) {
            throw new IllegalArgumentException("invalid gap strategy. Must be GAP_HANDLING_NONE or GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS");
        }
        this.mGapStrategy = i2;
        requestLayout();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void setMeasuredDimension(Rect rect, int i2, int i3) {
        int iChooseSize;
        int iChooseSize2;
        int paddingLeft = getPaddingLeft() + getPaddingRight();
        int paddingTop = getPaddingTop() + getPaddingBottom();
        if (this.mOrientation == 1) {
            iChooseSize2 = RecyclerView.LayoutManager.chooseSize(i3, rect.height() + paddingTop, getMinimumHeight());
            iChooseSize = RecyclerView.LayoutManager.chooseSize(i2, (this.mSizePerSpan * this.mSpanCount) + paddingLeft, getMinimumWidth());
        } else {
            iChooseSize = RecyclerView.LayoutManager.chooseSize(i2, rect.width() + paddingLeft, getMinimumWidth());
            iChooseSize2 = RecyclerView.LayoutManager.chooseSize(i3, (this.mSizePerSpan * this.mSpanCount) + paddingTop, getMinimumHeight());
        }
        setMeasuredDimension(iChooseSize, iChooseSize2);
    }

    public void setOrientation(int i2) {
        if (i2 != 0 && i2 != 1) {
            throw new IllegalArgumentException("invalid orientation.");
        }
        assertNotInLayoutOrScroll(null);
        if (i2 == this.mOrientation) {
            return;
        }
        this.mOrientation = i2;
        OrientationHelper orientationHelper = this.f6113l;
        this.f6113l = this.f6114m;
        this.f6114m = orientationHelper;
        requestLayout();
    }

    public void setReverseLayout(boolean z2) {
        assertNotInLayoutOrScroll(null);
        SavedState savedState = this.mPendingSavedState;
        if (savedState != null && savedState.f6143h != z2) {
            savedState.f6143h = z2;
        }
        this.f6115n = z2;
        requestLayout();
    }

    public void setSpanCount(int i2) {
        assertNotInLayoutOrScroll(null);
        if (i2 != this.mSpanCount) {
            invalidateSpanAssignments();
            this.mSpanCount = i2;
            this.mRemainingSpans = new BitSet(this.mSpanCount);
            this.f6112k = new Span[this.mSpanCount];
            for (int i3 = 0; i3 < this.mSpanCount; i3++) {
                this.f6112k[i3] = new Span(i3);
            }
            requestLayout();
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int i2) {
        LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(recyclerView.getContext());
        linearSmoothScroller.setTargetPosition(i2);
        startSmoothScroll(linearSmoothScroller);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public boolean supportsPredictiveItemAnimations() {
        return this.mPendingSavedState == null;
    }

    boolean t() {
        int iX;
        int iY;
        if (getChildCount() == 0 || this.mGapStrategy == 0 || !isAttachedToWindow()) {
            return false;
        }
        if (this.f6116o) {
            iX = y();
            iY = x();
        } else {
            iX = x();
            iY = y();
        }
        if (iX == 0 && z() != null) {
            this.f6119r.a();
            requestSimpleAnimationsInNextLayout();
            requestLayout();
            return true;
        }
        if (!this.mLaidOutInvalidFullSpan) {
            return false;
        }
        int i2 = this.f6116o ? -1 : 1;
        int i3 = iY + 1;
        LazySpanLookup.FullSpanItem firstFullSpanItemInRange = this.f6119r.getFirstFullSpanItemInRange(iX, i3, i2, true);
        if (firstFullSpanItemInRange == null) {
            this.mLaidOutInvalidFullSpan = false;
            this.f6119r.c(i3);
            return false;
        }
        LazySpanLookup.FullSpanItem firstFullSpanItemInRange2 = this.f6119r.getFirstFullSpanItemInRange(iX, firstFullSpanItemInRange.f6132a, i2 * (-1), true);
        if (firstFullSpanItemInRange2 == null) {
            this.f6119r.c(firstFullSpanItemInRange.f6132a);
        } else {
            this.f6119r.c(firstFullSpanItemInRange2.f6132a + 1);
        }
        requestSimpleAnimationsInNextLayout();
        requestLayout();
        return true;
    }

    View u(boolean z2) {
        int startAfterPadding = this.f6113l.getStartAfterPadding();
        int endAfterPadding = this.f6113l.getEndAfterPadding();
        View view = null;
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = getChildAt(childCount);
            int decoratedStart = this.f6113l.getDecoratedStart(childAt);
            int decoratedEnd = this.f6113l.getDecoratedEnd(childAt);
            if (decoratedEnd > startAfterPadding && decoratedStart < endAfterPadding) {
                if (decoratedEnd <= endAfterPadding || !z2) {
                    return childAt;
                }
                if (view == null) {
                    view = childAt;
                }
            }
        }
        return view;
    }

    View v(boolean z2) {
        int startAfterPadding = this.f6113l.getStartAfterPadding();
        int endAfterPadding = this.f6113l.getEndAfterPadding();
        int childCount = getChildCount();
        View view = null;
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            int decoratedStart = this.f6113l.getDecoratedStart(childAt);
            if (this.f6113l.getDecoratedEnd(childAt) > startAfterPadding && decoratedStart < endAfterPadding) {
                if (decoratedStart >= startAfterPadding || !z2) {
                    return childAt;
                }
                if (view == null) {
                    view = childAt;
                }
            }
        }
        return view;
    }

    int w() {
        View viewU = this.f6116o ? u(true) : v(true);
        if (viewU == null) {
            return -1;
        }
        return getPosition(viewU);
    }

    int x() {
        if (getChildCount() == 0) {
            return 0;
        }
        return getPosition(getChildAt(0));
    }

    int y() {
        int childCount = getChildCount();
        if (childCount == 0) {
            return 0;
        }
        return getPosition(getChildAt(childCount - 1));
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x0086  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    android.view.View z() {
        /*
            r12 = this;
            int r0 = r12.getChildCount()
            int r1 = r0 + (-1)
            java.util.BitSet r2 = new java.util.BitSet
            int r3 = r12.mSpanCount
            r2.<init>(r3)
            int r3 = r12.mSpanCount
            r4 = 0
            r5 = 1
            r2.set(r4, r3, r5)
            int r3 = r12.mOrientation
            r6 = -1
            if (r3 != r5) goto L21
            boolean r3 = r12.A()
            if (r3 == 0) goto L21
            r3 = r5
            goto L22
        L21:
            r3 = r6
        L22:
            boolean r7 = r12.f6116o
            if (r7 == 0) goto L28
            r0 = r6
            goto L29
        L28:
            r1 = r4
        L29:
            if (r1 >= r0) goto L2c
            r6 = r5
        L2c:
            if (r1 == r0) goto La4
            android.view.View r7 = r12.getChildAt(r1)
            android.view.ViewGroup$LayoutParams r8 = r7.getLayoutParams()
            androidx.recyclerview.widget.StaggeredGridLayoutManager$LayoutParams r8 = (androidx.recyclerview.widget.StaggeredGridLayoutManager.LayoutParams) r8
            androidx.recyclerview.widget.StaggeredGridLayoutManager$Span r9 = r8.f6128e
            int r9 = r9.f6150e
            boolean r9 = r2.get(r9)
            if (r9 == 0) goto L52
            androidx.recyclerview.widget.StaggeredGridLayoutManager$Span r9 = r8.f6128e
            boolean r9 = r12.checkSpanForGap(r9)
            if (r9 == 0) goto L4b
            return r7
        L4b:
            androidx.recyclerview.widget.StaggeredGridLayoutManager$Span r9 = r8.f6128e
            int r9 = r9.f6150e
            r2.clear(r9)
        L52:
            boolean r9 = r8.f6129f
            if (r9 == 0) goto L57
            goto La2
        L57:
            int r9 = r1 + r6
            if (r9 == r0) goto La2
            android.view.View r9 = r12.getChildAt(r9)
            boolean r10 = r12.f6116o
            if (r10 == 0) goto L75
            androidx.recyclerview.widget.OrientationHelper r10 = r12.f6113l
            int r10 = r10.getDecoratedEnd(r7)
            androidx.recyclerview.widget.OrientationHelper r11 = r12.f6113l
            int r11 = r11.getDecoratedEnd(r9)
            if (r10 >= r11) goto L72
            return r7
        L72:
            if (r10 != r11) goto La2
            goto L86
        L75:
            androidx.recyclerview.widget.OrientationHelper r10 = r12.f6113l
            int r10 = r10.getDecoratedStart(r7)
            androidx.recyclerview.widget.OrientationHelper r11 = r12.f6113l
            int r11 = r11.getDecoratedStart(r9)
            if (r10 <= r11) goto L84
            return r7
        L84:
            if (r10 != r11) goto La2
        L86:
            android.view.ViewGroup$LayoutParams r9 = r9.getLayoutParams()
            androidx.recyclerview.widget.StaggeredGridLayoutManager$LayoutParams r9 = (androidx.recyclerview.widget.StaggeredGridLayoutManager.LayoutParams) r9
            androidx.recyclerview.widget.StaggeredGridLayoutManager$Span r8 = r8.f6128e
            int r8 = r8.f6150e
            androidx.recyclerview.widget.StaggeredGridLayoutManager$Span r9 = r9.f6128e
            int r9 = r9.f6150e
            int r8 = r8 - r9
            if (r8 >= 0) goto L99
            r8 = r5
            goto L9a
        L99:
            r8 = r4
        L9a:
            if (r3 >= 0) goto L9e
            r9 = r5
            goto L9f
        L9e:
            r9 = r4
        L9f:
            if (r8 == r9) goto La2
            return r7
        La2:
            int r1 = r1 + r6
            goto L2c
        La4:
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.StaggeredGridLayoutManager.z():android.view.View");
    }

    /* JADX WARN: Removed duplicated region for block: B:86:0x0155  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void onLayoutChildren(androidx.recyclerview.widget.RecyclerView.Recycler r9, androidx.recyclerview.widget.RecyclerView.State r10, boolean r11) {
        /*
            Method dump skipped, instructions count: 374
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.StaggeredGridLayoutManager.onLayoutChildren(androidx.recyclerview.widget.RecyclerView$Recycler, androidx.recyclerview.widget.RecyclerView$State, boolean):void");
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public RecyclerView.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof ViewGroup.MarginLayoutParams ? new LayoutParams((ViewGroup.MarginLayoutParams) layoutParams) : new LayoutParams(layoutParams);
    }

    static class LazySpanLookup {
        private static final int MIN_SIZE = 10;

        /* renamed from: a, reason: collision with root package name */
        int[] f6130a;

        /* renamed from: b, reason: collision with root package name */
        List f6131b;

        LazySpanLookup() {
        }

        private int invalidateFullSpansAfter(int i2) {
            if (this.f6131b == null) {
                return -1;
            }
            FullSpanItem fullSpanItem = getFullSpanItem(i2);
            if (fullSpanItem != null) {
                this.f6131b.remove(fullSpanItem);
            }
            int size = this.f6131b.size();
            int i3 = 0;
            while (true) {
                if (i3 >= size) {
                    i3 = -1;
                    break;
                }
                if (((FullSpanItem) this.f6131b.get(i3)).f6132a >= i2) {
                    break;
                }
                i3++;
            }
            if (i3 == -1) {
                return -1;
            }
            FullSpanItem fullSpanItem2 = (FullSpanItem) this.f6131b.get(i3);
            this.f6131b.remove(i3);
            return fullSpanItem2.f6132a;
        }

        private void offsetFullSpansForAddition(int i2, int i3) {
            List list = this.f6131b;
            if (list == null) {
                return;
            }
            for (int size = list.size() - 1; size >= 0; size--) {
                FullSpanItem fullSpanItem = (FullSpanItem) this.f6131b.get(size);
                int i4 = fullSpanItem.f6132a;
                if (i4 >= i2) {
                    fullSpanItem.f6132a = i4 + i3;
                }
            }
        }

        private void offsetFullSpansForRemoval(int i2, int i3) {
            List list = this.f6131b;
            if (list == null) {
                return;
            }
            int i4 = i2 + i3;
            for (int size = list.size() - 1; size >= 0; size--) {
                FullSpanItem fullSpanItem = (FullSpanItem) this.f6131b.get(size);
                int i5 = fullSpanItem.f6132a;
                if (i5 >= i2) {
                    if (i5 < i4) {
                        this.f6131b.remove(size);
                    } else {
                        fullSpanItem.f6132a = i5 - i3;
                    }
                }
            }
        }

        void a() {
            int[] iArr = this.f6130a;
            if (iArr != null) {
                Arrays.fill(iArr, -1);
            }
            this.f6131b = null;
        }

        public void addFullSpanItem(FullSpanItem fullSpanItem) {
            if (this.f6131b == null) {
                this.f6131b = new ArrayList();
            }
            int size = this.f6131b.size();
            for (int i2 = 0; i2 < size; i2++) {
                FullSpanItem fullSpanItem2 = (FullSpanItem) this.f6131b.get(i2);
                if (fullSpanItem2.f6132a == fullSpanItem.f6132a) {
                    this.f6131b.remove(i2);
                }
                if (fullSpanItem2.f6132a >= fullSpanItem.f6132a) {
                    this.f6131b.add(i2, fullSpanItem);
                    return;
                }
            }
            this.f6131b.add(fullSpanItem);
        }

        void b(int i2) {
            int[] iArr = this.f6130a;
            if (iArr == null) {
                int[] iArr2 = new int[Math.max(i2, 10) + 1];
                this.f6130a = iArr2;
                Arrays.fill(iArr2, -1);
            } else if (i2 >= iArr.length) {
                int[] iArr3 = new int[i(i2)];
                this.f6130a = iArr3;
                System.arraycopy(iArr, 0, iArr3, 0, iArr.length);
                int[] iArr4 = this.f6130a;
                Arrays.fill(iArr4, iArr.length, iArr4.length, -1);
            }
        }

        int c(int i2) {
            List list = this.f6131b;
            if (list != null) {
                for (int size = list.size() - 1; size >= 0; size--) {
                    if (((FullSpanItem) this.f6131b.get(size)).f6132a >= i2) {
                        this.f6131b.remove(size);
                    }
                }
            }
            return e(i2);
        }

        int d(int i2) {
            int[] iArr = this.f6130a;
            if (iArr == null || i2 >= iArr.length) {
                return -1;
            }
            return iArr[i2];
        }

        int e(int i2) {
            int[] iArr = this.f6130a;
            if (iArr == null || i2 >= iArr.length) {
                return -1;
            }
            int iInvalidateFullSpansAfter = invalidateFullSpansAfter(i2);
            if (iInvalidateFullSpansAfter == -1) {
                int[] iArr2 = this.f6130a;
                Arrays.fill(iArr2, i2, iArr2.length, -1);
                return this.f6130a.length;
            }
            int iMin = Math.min(iInvalidateFullSpansAfter + 1, this.f6130a.length);
            Arrays.fill(this.f6130a, i2, iMin, -1);
            return iMin;
        }

        void f(int i2, int i3) {
            int[] iArr = this.f6130a;
            if (iArr == null || i2 >= iArr.length) {
                return;
            }
            int i4 = i2 + i3;
            b(i4);
            int[] iArr2 = this.f6130a;
            System.arraycopy(iArr2, i2, iArr2, i4, (iArr2.length - i2) - i3);
            Arrays.fill(this.f6130a, i2, i4, -1);
            offsetFullSpansForAddition(i2, i3);
        }

        void g(int i2, int i3) {
            int[] iArr = this.f6130a;
            if (iArr == null || i2 >= iArr.length) {
                return;
            }
            int i4 = i2 + i3;
            b(i4);
            int[] iArr2 = this.f6130a;
            System.arraycopy(iArr2, i4, iArr2, i2, (iArr2.length - i2) - i3);
            int[] iArr3 = this.f6130a;
            Arrays.fill(iArr3, iArr3.length - i3, iArr3.length, -1);
            offsetFullSpansForRemoval(i2, i3);
        }

        public FullSpanItem getFirstFullSpanItemInRange(int i2, int i3, int i4, boolean z2) {
            List list = this.f6131b;
            if (list == null) {
                return null;
            }
            int size = list.size();
            for (int i5 = 0; i5 < size; i5++) {
                FullSpanItem fullSpanItem = (FullSpanItem) this.f6131b.get(i5);
                int i6 = fullSpanItem.f6132a;
                if (i6 >= i3) {
                    return null;
                }
                if (i6 >= i2 && (i4 == 0 || fullSpanItem.f6133b == i4 || (z2 && fullSpanItem.f6135d))) {
                    return fullSpanItem;
                }
            }
            return null;
        }

        public FullSpanItem getFullSpanItem(int i2) {
            List list = this.f6131b;
            if (list == null) {
                return null;
            }
            for (int size = list.size() - 1; size >= 0; size--) {
                FullSpanItem fullSpanItem = (FullSpanItem) this.f6131b.get(size);
                if (fullSpanItem.f6132a == i2) {
                    return fullSpanItem;
                }
            }
            return null;
        }

        void h(int i2, Span span) {
            b(i2);
            this.f6130a[i2] = span.f6150e;
        }

        int i(int i2) {
            int length = this.f6130a.length;
            while (length <= i2) {
                length *= 2;
            }
            return length;
        }

        @SuppressLint({"BanParcelableUsage"})
        static class FullSpanItem implements Parcelable {
            public static final Parcelable.Creator<FullSpanItem> CREATOR = new Parcelable.Creator<FullSpanItem>() { // from class: androidx.recyclerview.widget.StaggeredGridLayoutManager.LazySpanLookup.FullSpanItem.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public FullSpanItem createFromParcel(Parcel parcel) {
                    return new FullSpanItem(parcel);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public FullSpanItem[] newArray(int i2) {
                    return new FullSpanItem[i2];
                }
            };

            /* renamed from: a, reason: collision with root package name */
            int f6132a;

            /* renamed from: b, reason: collision with root package name */
            int f6133b;

            /* renamed from: c, reason: collision with root package name */
            int[] f6134c;

            /* renamed from: d, reason: collision with root package name */
            boolean f6135d;

            FullSpanItem(Parcel parcel) {
                this.f6132a = parcel.readInt();
                this.f6133b = parcel.readInt();
                this.f6135d = parcel.readInt() == 1;
                int i2 = parcel.readInt();
                if (i2 > 0) {
                    int[] iArr = new int[i2];
                    this.f6134c = iArr;
                    parcel.readIntArray(iArr);
                }
            }

            int a(int i2) {
                int[] iArr = this.f6134c;
                if (iArr == null) {
                    return 0;
                }
                return iArr[i2];
            }

            @Override // android.os.Parcelable
            public int describeContents() {
                return 0;
            }

            public String toString() {
                return "FullSpanItem{mPosition=" + this.f6132a + ", mGapDir=" + this.f6133b + ", mHasUnwantedGapAfter=" + this.f6135d + ", mGapPerSpan=" + Arrays.toString(this.f6134c) + '}';
            }

            @Override // android.os.Parcelable
            public void writeToParcel(Parcel parcel, int i2) {
                parcel.writeInt(this.f6132a);
                parcel.writeInt(this.f6133b);
                parcel.writeInt(this.f6135d ? 1 : 0);
                int[] iArr = this.f6134c;
                if (iArr == null || iArr.length <= 0) {
                    parcel.writeInt(0);
                } else {
                    parcel.writeInt(iArr.length);
                    parcel.writeIntArray(this.f6134c);
                }
            }

            FullSpanItem() {
            }
        }
    }

    public StaggeredGridLayoutManager(int i2, int i3) {
        this.mOrientation = i3;
        setSpanCount(i2);
        this.mLayoutState = new LayoutState();
        createOrientationHelpers();
    }

    private void measureChildWithDecorationsAndMargin(View view, int i2, int i3, boolean z2) {
        boolean zN;
        calculateItemDecorationsForChild(view, this.mTmpRect);
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        int i4 = ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin;
        Rect rect = this.mTmpRect;
        int iUpdateSpecWithExtra = updateSpecWithExtra(i2, i4 + rect.left, ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin + rect.right);
        int i5 = ((ViewGroup.MarginLayoutParams) layoutParams).topMargin;
        Rect rect2 = this.mTmpRect;
        int iUpdateSpecWithExtra2 = updateSpecWithExtra(i3, i5 + rect2.top, ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin + rect2.bottom);
        if (z2) {
            zN = p(view, iUpdateSpecWithExtra, iUpdateSpecWithExtra2, layoutParams);
        } else {
            zN = n(view, iUpdateSpecWithExtra, iUpdateSpecWithExtra2, layoutParams);
        }
        if (zN) {
            view.measure(iUpdateSpecWithExtra, iUpdateSpecWithExtra2);
        }
    }
}
