package androidx.recyclerview.widget;

import android.R;
import android.animation.LayoutTransition;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Observable;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.FocusFinder;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.Interpolator;
import android.widget.EdgeEffect;
import android.widget.OverScroller;
import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import androidx.core.os.TraceCompat;
import androidx.core.util.Preconditions;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.NestedScrollingChild2;
import androidx.core.view.NestedScrollingChild3;
import androidx.core.view.NestedScrollingChildHelper;
import androidx.core.view.ScrollingView;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewConfigurationCompat;
import androidx.core.view.accessibility.AccessibilityEventCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.customview.view.AbsSavedState;
import androidx.recyclerview.widget.AdapterHelper;
import androidx.recyclerview.widget.ChildHelper;
import androidx.recyclerview.widget.GapWorker;
import androidx.recyclerview.widget.RecyclerViewAccessibilityDelegate;
import androidx.recyclerview.widget.ViewBoundsCheck;
import androidx.recyclerview.widget.ViewInfoStore;
import com.alipay.sdk.m.u.i;
import com.aliyun.alink.business.devicecenter.config.genie.smartconfig.constants.DeviceCommonConstants;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes2.dex */
public class RecyclerView extends ViewGroup implements ScrollingView, NestedScrollingChild2, NestedScrollingChild3 {
    public static final int HORIZONTAL = 0;
    private static final int INVALID_POINTER = -1;
    public static final int INVALID_TYPE = -1;
    private static final Class<?>[] LAYOUT_MANAGER_CONSTRUCTOR_SIGNATURE;
    static final Interpolator M;
    public static final long NO_ID = -1;
    public static final int NO_POSITION = -1;
    public static final int SCROLL_STATE_DRAGGING = 1;
    public static final int SCROLL_STATE_IDLE = 0;
    public static final int SCROLL_STATE_SETTLING = 2;
    public static final int TOUCH_SLOP_DEFAULT = 0;
    public static final int TOUCH_SLOP_PAGING = 1;
    private static final String TRACE_HANDLE_ADAPTER_UPDATES_TAG = "RV PartialInvalidate";
    private static final String TRACE_ON_DATA_SET_CHANGE_LAYOUT_TAG = "RV FullInvalidate";
    private static final String TRACE_ON_LAYOUT_TAG = "RV OnLayout";
    public static final int UNDEFINED_DURATION = Integer.MIN_VALUE;
    public static final int VERTICAL = 1;
    GapWorker.LayoutPrefetchRegistryImpl A;
    final State B;
    boolean C;
    boolean D;
    boolean E;
    RecyclerViewAccessibilityDelegate F;
    final int[] G;
    final List H;

    /* renamed from: a, reason: collision with root package name */
    final Recycler f6016a;

    /* renamed from: b, reason: collision with root package name */
    SavedState f6017b;

    /* renamed from: c, reason: collision with root package name */
    AdapterHelper f6018c;

    /* renamed from: d, reason: collision with root package name */
    ChildHelper f6019d;

    /* renamed from: e, reason: collision with root package name */
    final ViewInfoStore f6020e;

    /* renamed from: f, reason: collision with root package name */
    boolean f6021f;

    /* renamed from: g, reason: collision with root package name */
    final Runnable f6022g;

    /* renamed from: h, reason: collision with root package name */
    final Rect f6023h;

    /* renamed from: i, reason: collision with root package name */
    final RectF f6024i;

    /* renamed from: j, reason: collision with root package name */
    Adapter f6025j;

    /* renamed from: k, reason: collision with root package name */
    LayoutManager f6026k;

    /* renamed from: l, reason: collision with root package name */
    RecyclerListener f6027l;

    /* renamed from: m, reason: collision with root package name */
    final List f6028m;
    private final AccessibilityManager mAccessibilityManager;
    private EdgeEffect mBottomGlow;
    private ChildDrawingOrderCallback mChildDrawingOrderCallback;
    private int mDispatchScrollCounter;
    private int mEatenAccessibilityChangeFlags;

    @NonNull
    private EdgeEffectFactory mEdgeEffectFactory;
    private boolean mIgnoreMotionEventTillDown;
    private int mInitialTouchX;
    private int mInitialTouchY;
    private int mInterceptRequestLayoutDepth;
    private OnItemTouchListener mInterceptingOnItemTouchListener;
    private ItemAnimator.ItemAnimatorListener mItemAnimatorListener;
    private Runnable mItemAnimatorRunner;
    private int mLastAutoMeasureNonExactMeasuredHeight;
    private int mLastAutoMeasureNonExactMeasuredWidth;
    private boolean mLastAutoMeasureSkippedDueToExact;
    private int mLastTouchX;
    private int mLastTouchY;
    private int mLayoutOrScrollCounter;
    private EdgeEffect mLeftGlow;
    private final int mMaxFlingVelocity;
    private final int mMinFlingVelocity;
    private final int[] mMinMaxLayoutPositions;
    private final int[] mNestedOffsets;
    private final RecyclerViewDataObserver mObserver;
    private List<OnChildAttachStateChangeListener> mOnChildAttachStateListeners;
    private OnFlingListener mOnFlingListener;
    private final ArrayList<OnItemTouchListener> mOnItemTouchListeners;
    private boolean mPreserveFocusAfterLayout;
    private EdgeEffect mRightGlow;
    private float mScaledHorizontalScrollFactor;
    private float mScaledVerticalScrollFactor;
    private OnScrollListener mScrollListener;
    private List<OnScrollListener> mScrollListeners;
    private final int[] mScrollOffset;
    private int mScrollPointerId;
    private int mScrollState;
    private NestedScrollingChildHelper mScrollingChildHelper;
    private final Rect mTempRect2;
    private EdgeEffect mTopGlow;
    private int mTouchSlop;
    private VelocityTracker mVelocityTracker;
    private final ViewInfoStore.ProcessCallback mViewInfoProcessCallback;

    /* renamed from: n, reason: collision with root package name */
    final ArrayList f6029n;

    /* renamed from: o, reason: collision with root package name */
    boolean f6030o;

    /* renamed from: p, reason: collision with root package name */
    boolean f6031p;

    /* renamed from: q, reason: collision with root package name */
    boolean f6032q;

    /* renamed from: r, reason: collision with root package name */
    boolean f6033r;

    /* renamed from: s, reason: collision with root package name */
    boolean f6034s;

    /* renamed from: t, reason: collision with root package name */
    boolean f6035t;

    /* renamed from: u, reason: collision with root package name */
    boolean f6036u;

    /* renamed from: v, reason: collision with root package name */
    boolean f6037v;

    /* renamed from: w, reason: collision with root package name */
    boolean f6038w;

    /* renamed from: x, reason: collision with root package name */
    ItemAnimator f6039x;

    /* renamed from: y, reason: collision with root package name */
    final ViewFlinger f6040y;

    /* renamed from: z, reason: collision with root package name */
    GapWorker f6041z;
    private static final int[] NESTED_SCROLLING_ATTRS = {R.attr.nestedScrollingEnabled};
    static final boolean I = false;
    static final boolean J = true;
    static final boolean K = true;
    static final boolean L = true;
    private static final boolean FORCE_ABS_FOCUS_SEARCH_DIRECTION = false;
    private static final boolean IGNORE_DETACHED_FOCUSED_CHILD = false;

    /* renamed from: androidx.recyclerview.widget.RecyclerView$7, reason: invalid class name */
    static /* synthetic */ class AnonymousClass7 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f6047a;

        static {
            int[] iArr = new int[Adapter.StateRestorationPolicy.values().length];
            f6047a = iArr;
            try {
                iArr[Adapter.StateRestorationPolicy.PREVENT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f6047a[Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public static abstract class Adapter<VH extends ViewHolder> {
        private final AdapterDataObservable mObservable = new AdapterDataObservable();
        private boolean mHasStableIds = false;
        private StateRestorationPolicy mStateRestorationPolicy = StateRestorationPolicy.ALLOW;

        public enum StateRestorationPolicy {
            ALLOW,
            PREVENT_WHEN_EMPTY,
            PREVENT
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final void bindViewHolder(@NonNull VH vh, int i2) {
            boolean z2 = vh.mBindingAdapter == null;
            if (z2) {
                vh.mPosition = i2;
                if (hasStableIds()) {
                    vh.mItemId = getItemId(i2);
                }
                vh.setFlags(1, DeviceCommonConstants.WHAT_DHCP_START_FAILED);
                TraceCompat.beginSection("RV OnBindView");
            }
            vh.mBindingAdapter = this;
            onBindViewHolder(vh, i2, vh.getUnmodifiedPayloads());
            if (z2) {
                vh.clearPayload();
                ViewGroup.LayoutParams layoutParams = vh.itemView.getLayoutParams();
                if (layoutParams instanceof LayoutParams) {
                    ((LayoutParams) layoutParams).f6064c = true;
                }
                TraceCompat.endSection();
            }
        }

        boolean canRestoreState() {
            int i2 = AnonymousClass7.f6047a[this.mStateRestorationPolicy.ordinal()];
            if (i2 != 1) {
                return i2 != 2 || getItemCount() > 0;
            }
            return false;
        }

        @NonNull
        public final VH createViewHolder(@NonNull ViewGroup viewGroup, int i2) {
            try {
                TraceCompat.beginSection("RV CreateView");
                VH vh = (VH) onCreateViewHolder(viewGroup, i2);
                if (vh.itemView.getParent() != null) {
                    throw new IllegalStateException("ViewHolder views must not be attached when created. Ensure that you are not passing 'true' to the attachToRoot parameter of LayoutInflater.inflate(..., boolean attachToRoot)");
                }
                vh.mItemViewType = i2;
                return vh;
            } finally {
                TraceCompat.endSection();
            }
        }

        public int findRelativeAdapterPositionIn(@NonNull Adapter<? extends ViewHolder> adapter, @NonNull ViewHolder viewHolder, int i2) {
            if (adapter == this) {
                return i2;
            }
            return -1;
        }

        public abstract int getItemCount();

        public long getItemId(int i2) {
            return -1L;
        }

        public int getItemViewType(int i2) {
            return 0;
        }

        @NonNull
        public final StateRestorationPolicy getStateRestorationPolicy() {
            return this.mStateRestorationPolicy;
        }

        public final boolean hasObservers() {
            return this.mObservable.hasObservers();
        }

        public final boolean hasStableIds() {
            return this.mHasStableIds;
        }

        public final void notifyDataSetChanged() {
            this.mObservable.notifyChanged();
        }

        public final void notifyItemChanged(int i2) {
            this.mObservable.notifyItemRangeChanged(i2, 1);
        }

        public final void notifyItemInserted(int i2) {
            this.mObservable.notifyItemRangeInserted(i2, 1);
        }

        public final void notifyItemMoved(int i2, int i3) {
            this.mObservable.notifyItemMoved(i2, i3);
        }

        public final void notifyItemRangeChanged(int i2, int i3) {
            this.mObservable.notifyItemRangeChanged(i2, i3);
        }

        public final void notifyItemRangeInserted(int i2, int i3) {
            this.mObservable.notifyItemRangeInserted(i2, i3);
        }

        public final void notifyItemRangeRemoved(int i2, int i3) {
            this.mObservable.notifyItemRangeRemoved(i2, i3);
        }

        public final void notifyItemRemoved(int i2) {
            this.mObservable.notifyItemRangeRemoved(i2, 1);
        }

        public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        }

        public abstract void onBindViewHolder(@NonNull VH vh, int i2);

        public void onBindViewHolder(@NonNull VH vh, int i2, @NonNull List<Object> list) {
            onBindViewHolder(vh, i2);
        }

        @NonNull
        public abstract VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i2);

        public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        }

        public boolean onFailedToRecycleView(@NonNull VH vh) {
            return false;
        }

        public void onViewAttachedToWindow(@NonNull VH vh) {
        }

        public void onViewDetachedFromWindow(@NonNull VH vh) {
        }

        public void onViewRecycled(@NonNull VH vh) {
        }

        public void registerAdapterDataObserver(@NonNull AdapterDataObserver adapterDataObserver) {
            this.mObservable.registerObserver(adapterDataObserver);
        }

        public void setHasStableIds(boolean z2) {
            if (hasObservers()) {
                throw new IllegalStateException("Cannot change whether this adapter has stable IDs while the adapter has registered observers.");
            }
            this.mHasStableIds = z2;
        }

        public void setStateRestorationPolicy(@NonNull StateRestorationPolicy stateRestorationPolicy) {
            this.mStateRestorationPolicy = stateRestorationPolicy;
            this.mObservable.notifyStateRestorationPolicyChanged();
        }

        public void unregisterAdapterDataObserver(@NonNull AdapterDataObserver adapterDataObserver) {
            this.mObservable.unregisterObserver(adapterDataObserver);
        }

        public final void notifyItemChanged(int i2, @Nullable Object obj) {
            this.mObservable.notifyItemRangeChanged(i2, 1, obj);
        }

        public final void notifyItemRangeChanged(int i2, int i3, @Nullable Object obj) {
            this.mObservable.notifyItemRangeChanged(i2, i3, obj);
        }
    }

    static class AdapterDataObservable extends Observable<AdapterDataObserver> {
        AdapterDataObservable() {
        }

        public boolean hasObservers() {
            return !((Observable) this).mObservers.isEmpty();
        }

        public void notifyChanged() {
            for (int size = ((Observable) this).mObservers.size() - 1; size >= 0; size--) {
                ((AdapterDataObserver) ((Observable) this).mObservers.get(size)).onChanged();
            }
        }

        public void notifyItemMoved(int i2, int i3) {
            for (int size = ((Observable) this).mObservers.size() - 1; size >= 0; size--) {
                ((AdapterDataObserver) ((Observable) this).mObservers.get(size)).onItemRangeMoved(i2, i3, 1);
            }
        }

        public void notifyItemRangeChanged(int i2, int i3) {
            notifyItemRangeChanged(i2, i3, null);
        }

        public void notifyItemRangeInserted(int i2, int i3) {
            for (int size = ((Observable) this).mObservers.size() - 1; size >= 0; size--) {
                ((AdapterDataObserver) ((Observable) this).mObservers.get(size)).onItemRangeInserted(i2, i3);
            }
        }

        public void notifyItemRangeRemoved(int i2, int i3) {
            for (int size = ((Observable) this).mObservers.size() - 1; size >= 0; size--) {
                ((AdapterDataObserver) ((Observable) this).mObservers.get(size)).onItemRangeRemoved(i2, i3);
            }
        }

        public void notifyStateRestorationPolicyChanged() {
            for (int size = ((Observable) this).mObservers.size() - 1; size >= 0; size--) {
                ((AdapterDataObserver) ((Observable) this).mObservers.get(size)).onStateRestorationPolicyChanged();
            }
        }

        public void notifyItemRangeChanged(int i2, int i3, @Nullable Object obj) {
            for (int size = ((Observable) this).mObservers.size() - 1; size >= 0; size--) {
                ((AdapterDataObserver) ((Observable) this).mObservers.get(size)).onItemRangeChanged(i2, i3, obj);
            }
        }
    }

    public static abstract class AdapterDataObserver {
        public void onChanged() {
        }

        public void onItemRangeChanged(int i2, int i3) {
        }

        public void onItemRangeInserted(int i2, int i3) {
        }

        public void onItemRangeMoved(int i2, int i3, int i4) {
        }

        public void onItemRangeRemoved(int i2, int i3) {
        }

        public void onStateRestorationPolicyChanged() {
        }

        public void onItemRangeChanged(int i2, int i3, @Nullable Object obj) {
            onItemRangeChanged(i2, i3);
        }
    }

    public interface ChildDrawingOrderCallback {
        int onGetChildDrawingOrder(int i2, int i3);
    }

    public static class EdgeEffectFactory {
        public static final int DIRECTION_BOTTOM = 3;
        public static final int DIRECTION_LEFT = 0;
        public static final int DIRECTION_RIGHT = 2;
        public static final int DIRECTION_TOP = 1;

        @Retention(RetentionPolicy.SOURCE)
        public @interface EdgeDirection {
        }

        protected EdgeEffect a(RecyclerView recyclerView, int i2) {
            return new EdgeEffect(recyclerView.getContext());
        }
    }

    public static abstract class ItemAnimator {
        public static final int FLAG_APPEARED_IN_PRE_LAYOUT = 4096;
        public static final int FLAG_CHANGED = 2;
        public static final int FLAG_INVALIDATED = 4;
        public static final int FLAG_MOVED = 2048;
        public static final int FLAG_REMOVED = 8;
        private ItemAnimatorListener mListener = null;
        private ArrayList<ItemAnimatorFinishedListener> mFinishedListeners = new ArrayList<>();
        private long mAddDuration = 120;
        private long mRemoveDuration = 120;
        private long mMoveDuration = 250;
        private long mChangeDuration = 250;

        @Retention(RetentionPolicy.SOURCE)
        public @interface AdapterChanges {
        }

        public interface ItemAnimatorFinishedListener {
            void onAnimationsFinished();
        }

        interface ItemAnimatorListener {
            void onAnimationFinished(@NonNull ViewHolder viewHolder);
        }

        public static class ItemHolderInfo {
            public int bottom;
            public int changeFlags;
            public int left;
            public int right;

            /* renamed from: top, reason: collision with root package name */
            public int f6048top;

            @NonNull
            public ItemHolderInfo setFrom(@NonNull ViewHolder viewHolder) {
                return setFrom(viewHolder, 0);
            }

            @NonNull
            public ItemHolderInfo setFrom(@NonNull ViewHolder viewHolder, int i2) {
                View view = viewHolder.itemView;
                this.left = view.getLeft();
                this.f6048top = view.getTop();
                this.right = view.getRight();
                this.bottom = view.getBottom();
                return this;
            }
        }

        static int a(ViewHolder viewHolder) {
            int i2 = viewHolder.mFlags;
            int i3 = i2 & 14;
            if (viewHolder.isInvalid()) {
                return 4;
            }
            if ((i2 & 4) != 0) {
                return i3;
            }
            int oldPosition = viewHolder.getOldPosition();
            int absoluteAdapterPosition = viewHolder.getAbsoluteAdapterPosition();
            return (oldPosition == -1 || absoluteAdapterPosition == -1 || oldPosition == absoluteAdapterPosition) ? i3 : i3 | 2048;
        }

        public abstract boolean animateAppearance(@NonNull ViewHolder viewHolder, @Nullable ItemHolderInfo itemHolderInfo, @NonNull ItemHolderInfo itemHolderInfo2);

        public abstract boolean animateChange(@NonNull ViewHolder viewHolder, @NonNull ViewHolder viewHolder2, @NonNull ItemHolderInfo itemHolderInfo, @NonNull ItemHolderInfo itemHolderInfo2);

        public abstract boolean animateDisappearance(@NonNull ViewHolder viewHolder, @NonNull ItemHolderInfo itemHolderInfo, @Nullable ItemHolderInfo itemHolderInfo2);

        public abstract boolean animatePersistence(@NonNull ViewHolder viewHolder, @NonNull ItemHolderInfo itemHolderInfo, @NonNull ItemHolderInfo itemHolderInfo2);

        void b(ItemAnimatorListener itemAnimatorListener) {
            this.mListener = itemAnimatorListener;
        }

        public boolean canReuseUpdatedViewHolder(@NonNull ViewHolder viewHolder) {
            return true;
        }

        public final void dispatchAnimationFinished(@NonNull ViewHolder viewHolder) {
            onAnimationFinished(viewHolder);
            ItemAnimatorListener itemAnimatorListener = this.mListener;
            if (itemAnimatorListener != null) {
                itemAnimatorListener.onAnimationFinished(viewHolder);
            }
        }

        public final void dispatchAnimationStarted(@NonNull ViewHolder viewHolder) {
            onAnimationStarted(viewHolder);
        }

        public final void dispatchAnimationsFinished() {
            int size = this.mFinishedListeners.size();
            for (int i2 = 0; i2 < size; i2++) {
                this.mFinishedListeners.get(i2).onAnimationsFinished();
            }
            this.mFinishedListeners.clear();
        }

        public abstract void endAnimation(@NonNull ViewHolder viewHolder);

        public abstract void endAnimations();

        public long getAddDuration() {
            return this.mAddDuration;
        }

        public long getChangeDuration() {
            return this.mChangeDuration;
        }

        public long getMoveDuration() {
            return this.mMoveDuration;
        }

        public long getRemoveDuration() {
            return this.mRemoveDuration;
        }

        public abstract boolean isRunning();

        public final boolean isRunning(@Nullable ItemAnimatorFinishedListener itemAnimatorFinishedListener) {
            boolean zIsRunning = isRunning();
            if (itemAnimatorFinishedListener != null) {
                if (zIsRunning) {
                    this.mFinishedListeners.add(itemAnimatorFinishedListener);
                } else {
                    itemAnimatorFinishedListener.onAnimationsFinished();
                }
            }
            return zIsRunning;
        }

        @NonNull
        public ItemHolderInfo obtainHolderInfo() {
            return new ItemHolderInfo();
        }

        public void onAnimationFinished(@NonNull ViewHolder viewHolder) {
        }

        public void onAnimationStarted(@NonNull ViewHolder viewHolder) {
        }

        @NonNull
        public ItemHolderInfo recordPostLayoutInformation(@NonNull State state, @NonNull ViewHolder viewHolder) {
            return obtainHolderInfo().setFrom(viewHolder);
        }

        @NonNull
        public ItemHolderInfo recordPreLayoutInformation(@NonNull State state, @NonNull ViewHolder viewHolder, int i2, @NonNull List<Object> list) {
            return obtainHolderInfo().setFrom(viewHolder);
        }

        public abstract void runPendingAnimations();

        public void setAddDuration(long j2) {
            this.mAddDuration = j2;
        }

        public void setChangeDuration(long j2) {
            this.mChangeDuration = j2;
        }

        public void setMoveDuration(long j2) {
            this.mMoveDuration = j2;
        }

        public void setRemoveDuration(long j2) {
            this.mRemoveDuration = j2;
        }

        public boolean canReuseUpdatedViewHolder(@NonNull ViewHolder viewHolder, @NonNull List<Object> list) {
            return canReuseUpdatedViewHolder(viewHolder);
        }
    }

    private class ItemAnimatorRestoreListener implements ItemAnimator.ItemAnimatorListener {
        ItemAnimatorRestoreListener() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.ItemAnimator.ItemAnimatorListener
        public void onAnimationFinished(ViewHolder viewHolder) {
            viewHolder.setIsRecyclable(true);
            if (viewHolder.mShadowedHolder != null && viewHolder.mShadowingHolder == null) {
                viewHolder.mShadowedHolder = null;
            }
            viewHolder.mShadowingHolder = null;
            if (viewHolder.shouldBeKeptAsChild() || RecyclerView.this.Z(viewHolder.itemView) || !viewHolder.isTmpDetached()) {
                return;
            }
            RecyclerView.this.removeDetachedView(viewHolder.itemView, false);
        }
    }

    public static abstract class ItemDecoration {
        @Deprecated
        public void getItemOffsets(@NonNull Rect rect, int i2, @NonNull RecyclerView recyclerView) {
            rect.set(0, 0, 0, 0);
        }

        @Deprecated
        public void onDraw(@NonNull Canvas canvas, @NonNull RecyclerView recyclerView) {
        }

        @Deprecated
        public void onDrawOver(@NonNull Canvas canvas, @NonNull RecyclerView recyclerView) {
        }

        public void getItemOffsets(@NonNull Rect rect, @NonNull View view, @NonNull RecyclerView recyclerView, @NonNull State state) {
            getItemOffsets(rect, ((LayoutParams) view.getLayoutParams()).getViewLayoutPosition(), recyclerView);
        }

        public void onDraw(@NonNull Canvas canvas, @NonNull RecyclerView recyclerView, @NonNull State state) {
            onDraw(canvas, recyclerView);
        }

        public void onDrawOver(@NonNull Canvas canvas, @NonNull RecyclerView recyclerView, @NonNull State state) {
            onDrawOver(canvas, recyclerView);
        }
    }

    public static abstract class LayoutManager {

        /* renamed from: a, reason: collision with root package name */
        ChildHelper f6050a;

        /* renamed from: b, reason: collision with root package name */
        RecyclerView f6051b;

        /* renamed from: c, reason: collision with root package name */
        ViewBoundsCheck f6052c;

        /* renamed from: d, reason: collision with root package name */
        ViewBoundsCheck f6053d;

        /* renamed from: e, reason: collision with root package name */
        SmoothScroller f6054e;

        /* renamed from: f, reason: collision with root package name */
        boolean f6055f;

        /* renamed from: g, reason: collision with root package name */
        boolean f6056g;

        /* renamed from: h, reason: collision with root package name */
        boolean f6057h;

        /* renamed from: i, reason: collision with root package name */
        int f6058i;

        /* renamed from: j, reason: collision with root package name */
        boolean f6059j;
        private int mHeight;
        private int mHeightMode;
        private final ViewBoundsCheck.Callback mHorizontalBoundCheckCallback;
        private boolean mItemPrefetchEnabled;
        private boolean mMeasurementCacheEnabled;
        private final ViewBoundsCheck.Callback mVerticalBoundCheckCallback;
        private int mWidth;
        private int mWidthMode;

        public interface LayoutPrefetchRegistry {
            void addPosition(int i2, int i3);
        }

        public static class Properties {
            public int orientation;
            public boolean reverseLayout;
            public int spanCount;
            public boolean stackFromEnd;
        }

        public LayoutManager() {
            ViewBoundsCheck.Callback callback = new ViewBoundsCheck.Callback() { // from class: androidx.recyclerview.widget.RecyclerView.LayoutManager.1
                @Override // androidx.recyclerview.widget.ViewBoundsCheck.Callback
                public View getChildAt(int i2) {
                    return LayoutManager.this.getChildAt(i2);
                }

                @Override // androidx.recyclerview.widget.ViewBoundsCheck.Callback
                public int getChildEnd(View view) {
                    return LayoutManager.this.getDecoratedRight(view) + ((ViewGroup.MarginLayoutParams) ((LayoutParams) view.getLayoutParams())).rightMargin;
                }

                @Override // androidx.recyclerview.widget.ViewBoundsCheck.Callback
                public int getChildStart(View view) {
                    return LayoutManager.this.getDecoratedLeft(view) - ((ViewGroup.MarginLayoutParams) ((LayoutParams) view.getLayoutParams())).leftMargin;
                }

                @Override // androidx.recyclerview.widget.ViewBoundsCheck.Callback
                public int getParentEnd() {
                    return LayoutManager.this.getWidth() - LayoutManager.this.getPaddingRight();
                }

                @Override // androidx.recyclerview.widget.ViewBoundsCheck.Callback
                public int getParentStart() {
                    return LayoutManager.this.getPaddingLeft();
                }
            };
            this.mHorizontalBoundCheckCallback = callback;
            ViewBoundsCheck.Callback callback2 = new ViewBoundsCheck.Callback() { // from class: androidx.recyclerview.widget.RecyclerView.LayoutManager.2
                @Override // androidx.recyclerview.widget.ViewBoundsCheck.Callback
                public View getChildAt(int i2) {
                    return LayoutManager.this.getChildAt(i2);
                }

                @Override // androidx.recyclerview.widget.ViewBoundsCheck.Callback
                public int getChildEnd(View view) {
                    return LayoutManager.this.getDecoratedBottom(view) + ((ViewGroup.MarginLayoutParams) ((LayoutParams) view.getLayoutParams())).bottomMargin;
                }

                @Override // androidx.recyclerview.widget.ViewBoundsCheck.Callback
                public int getChildStart(View view) {
                    return LayoutManager.this.getDecoratedTop(view) - ((ViewGroup.MarginLayoutParams) ((LayoutParams) view.getLayoutParams())).topMargin;
                }

                @Override // androidx.recyclerview.widget.ViewBoundsCheck.Callback
                public int getParentEnd() {
                    return LayoutManager.this.getHeight() - LayoutManager.this.getPaddingBottom();
                }

                @Override // androidx.recyclerview.widget.ViewBoundsCheck.Callback
                public int getParentStart() {
                    return LayoutManager.this.getPaddingTop();
                }
            };
            this.mVerticalBoundCheckCallback = callback2;
            this.f6052c = new ViewBoundsCheck(callback);
            this.f6053d = new ViewBoundsCheck(callback2);
            this.f6055f = false;
            this.f6056g = false;
            this.f6057h = false;
            this.mMeasurementCacheEnabled = true;
            this.mItemPrefetchEnabled = true;
        }

        private void addViewInt(View view, int i2, boolean z2) {
            ViewHolder viewHolderF = RecyclerView.F(view);
            if (z2 || viewHolderF.isRemoved()) {
                this.f6051b.f6020e.b(viewHolderF);
            } else {
                this.f6051b.f6020e.n(viewHolderF);
            }
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            if (viewHolderF.wasReturnedFromScrap() || viewHolderF.isScrap()) {
                if (viewHolderF.isScrap()) {
                    viewHolderF.unScrap();
                } else {
                    viewHolderF.clearReturnedFromScrapFlag();
                }
                this.f6050a.c(view, i2, view.getLayoutParams(), false);
            } else if (view.getParent() == this.f6051b) {
                int iK = this.f6050a.k(view);
                if (i2 == -1) {
                    i2 = this.f6050a.g();
                }
                if (iK == -1) {
                    throw new IllegalStateException("Added View has RecyclerView as parent but view is not a real child. Unfiltered index:" + this.f6051b.indexOfChild(view) + this.f6051b.z());
                }
                if (iK != i2) {
                    this.f6051b.f6026k.moveView(iK, i2);
                }
            } else {
                this.f6050a.a(view, i2, false);
                layoutParams.f6064c = true;
                SmoothScroller smoothScroller = this.f6054e;
                if (smoothScroller != null && smoothScroller.isRunning()) {
                    this.f6054e.c(view);
                }
            }
            if (layoutParams.f6065d) {
                viewHolderF.itemView.invalidate();
                layoutParams.f6065d = false;
            }
        }

        public static int chooseSize(int i2, int i3, int i4) {
            int mode = View.MeasureSpec.getMode(i2);
            int size = View.MeasureSpec.getSize(i2);
            return mode != Integer.MIN_VALUE ? mode != 1073741824 ? Math.max(i3, i4) : size : Math.min(size, Math.max(i3, i4));
        }

        private void detachViewInternal(int i2, @NonNull View view) {
            this.f6050a.d(i2);
        }

        /* JADX WARN: Removed duplicated region for block: B:5:0x000c A[PHI: r3
          0x000c: PHI (r3v5 int) = (r3v0 int), (r3v2 int), (r3v0 int) binds: [B:7:0x0010, B:11:0x0016, B:4:0x000a] A[DONT_GENERATE, DONT_INLINE]] */
        /* JADX WARN: Removed duplicated region for block: B:6:0x000e  */
        @java.lang.Deprecated
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public static int getChildMeasureSpec(int r1, int r2, int r3, boolean r4) {
            /*
                int r1 = r1 - r2
                r2 = 0
                int r1 = java.lang.Math.max(r2, r1)
                r0 = 1073741824(0x40000000, float:2.0)
                if (r4 == 0) goto L10
                if (r3 < 0) goto Le
            Lc:
                r2 = r0
                goto L1e
            Le:
                r3 = r2
                goto L1e
            L10:
                if (r3 < 0) goto L13
                goto Lc
            L13:
                r4 = -1
                if (r3 != r4) goto L18
                r3 = r1
                goto Lc
            L18:
                r4 = -2
                if (r3 != r4) goto Le
                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                r3 = r1
            L1e:
                int r1 = android.view.View.MeasureSpec.makeMeasureSpec(r3, r2)
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.RecyclerView.LayoutManager.getChildMeasureSpec(int, int, int, boolean):int");
        }

        private int[] getChildRectangleOnScreenScrollAmount(View view, Rect rect) {
            int paddingLeft = getPaddingLeft();
            int paddingTop = getPaddingTop();
            int width = getWidth() - getPaddingRight();
            int height = getHeight() - getPaddingBottom();
            int left = (view.getLeft() + rect.left) - view.getScrollX();
            int top2 = (view.getTop() + rect.top) - view.getScrollY();
            int iWidth = rect.width() + left;
            int iHeight = rect.height() + top2;
            int i2 = left - paddingLeft;
            int iMin = Math.min(0, i2);
            int i3 = top2 - paddingTop;
            int iMin2 = Math.min(0, i3);
            int i4 = iWidth - width;
            int iMax = Math.max(0, i4);
            int iMax2 = Math.max(0, iHeight - height);
            if (getLayoutDirection() != 1) {
                if (iMin == 0) {
                    iMin = Math.min(i2, iMax);
                }
                iMax = iMin;
            } else if (iMax == 0) {
                iMax = Math.max(iMin, i4);
            }
            if (iMin2 == 0) {
                iMin2 = Math.min(i3, iMax2);
            }
            return new int[]{iMax, iMin2};
        }

        public static Properties getProperties(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2, int i3) {
            Properties properties = new Properties();
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, androidx.recyclerview.R.styleable.RecyclerView, i2, i3);
            properties.orientation = typedArrayObtainStyledAttributes.getInt(androidx.recyclerview.R.styleable.RecyclerView_android_orientation, 1);
            properties.spanCount = typedArrayObtainStyledAttributes.getInt(androidx.recyclerview.R.styleable.RecyclerView_spanCount, 1);
            properties.reverseLayout = typedArrayObtainStyledAttributes.getBoolean(androidx.recyclerview.R.styleable.RecyclerView_reverseLayout, false);
            properties.stackFromEnd = typedArrayObtainStyledAttributes.getBoolean(androidx.recyclerview.R.styleable.RecyclerView_stackFromEnd, false);
            typedArrayObtainStyledAttributes.recycle();
            return properties;
        }

        private boolean isFocusedChildVisibleAfterScrolling(RecyclerView recyclerView, int i2, int i3) {
            View focusedChild = recyclerView.getFocusedChild();
            if (focusedChild == null) {
                return false;
            }
            int paddingLeft = getPaddingLeft();
            int paddingTop = getPaddingTop();
            int width = getWidth() - getPaddingRight();
            int height = getHeight() - getPaddingBottom();
            Rect rect = this.f6051b.f6023h;
            getDecoratedBoundsWithMargins(focusedChild, rect);
            return rect.left - i2 < width && rect.right - i2 > paddingLeft && rect.top - i3 < height && rect.bottom - i3 > paddingTop;
        }

        private static boolean isMeasurementUpToDate(int i2, int i3, int i4) {
            int mode = View.MeasureSpec.getMode(i3);
            int size = View.MeasureSpec.getSize(i3);
            if (i4 > 0 && i2 != i4) {
                return false;
            }
            if (mode == Integer.MIN_VALUE) {
                return size >= i2;
            }
            if (mode != 0) {
                return mode == 1073741824 && size == i2;
            }
            return true;
        }

        private void scrapOrRecycleView(Recycler recycler, int i2, View view) {
            ViewHolder viewHolderF = RecyclerView.F(view);
            if (viewHolderF.shouldIgnore()) {
                return;
            }
            if (viewHolderF.isInvalid() && !viewHolderF.isRemoved() && !this.f6051b.f6025j.hasStableIds()) {
                removeViewAt(i2);
                recycler.u(viewHolderF);
            } else {
                detachViewAt(i2);
                recycler.v(view);
                this.f6051b.f6020e.onViewDetached(viewHolderF);
            }
        }

        void a(RecyclerView recyclerView) {
            this.f6056g = true;
            onAttachedToWindow(recyclerView);
        }

        public void addDisappearingView(View view) {
            addDisappearingView(view, -1);
        }

        public void addView(View view) {
            addView(view, -1);
        }

        public void assertInLayoutOrScroll(String str) {
            RecyclerView recyclerView = this.f6051b;
            if (recyclerView != null) {
                recyclerView.h(str);
            }
        }

        public void assertNotInLayoutOrScroll(String str) {
            RecyclerView recyclerView = this.f6051b;
            if (recyclerView != null) {
                recyclerView.i(str);
            }
        }

        public void attachView(@NonNull View view, int i2, LayoutParams layoutParams) {
            ViewHolder viewHolderF = RecyclerView.F(view);
            if (viewHolderF.isRemoved()) {
                this.f6051b.f6020e.b(viewHolderF);
            } else {
                this.f6051b.f6020e.n(viewHolderF);
            }
            this.f6050a.c(view, i2, layoutParams, viewHolderF.isRemoved());
        }

        void b(RecyclerView recyclerView, Recycler recycler) {
            this.f6056g = false;
            onDetachedFromWindow(recyclerView, recycler);
        }

        boolean c() {
            int childCount = getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                ViewGroup.LayoutParams layoutParams = getChildAt(i2).getLayoutParams();
                if (layoutParams.width < 0 && layoutParams.height < 0) {
                    return true;
                }
            }
            return false;
        }

        public void calculateItemDecorationsForChild(@NonNull View view, @NonNull Rect rect) {
            RecyclerView recyclerView = this.f6051b;
            if (recyclerView == null) {
                rect.set(0, 0, 0, 0);
            } else {
                rect.set(recyclerView.H(view));
            }
        }

        public boolean canScrollHorizontally() {
            return false;
        }

        public boolean canScrollVertically() {
            return false;
        }

        public boolean checkLayoutParams(LayoutParams layoutParams) {
            return layoutParams != null;
        }

        public void collectAdjacentPrefetchPositions(int i2, int i3, State state, LayoutPrefetchRegistry layoutPrefetchRegistry) {
        }

        public void collectInitialPrefetchPositions(int i2, LayoutPrefetchRegistry layoutPrefetchRegistry) {
        }

        public int computeHorizontalScrollExtent(@NonNull State state) {
            return 0;
        }

        public int computeHorizontalScrollOffset(@NonNull State state) {
            return 0;
        }

        public int computeHorizontalScrollRange(@NonNull State state) {
            return 0;
        }

        public int computeVerticalScrollExtent(@NonNull State state) {
            return 0;
        }

        public int computeVerticalScrollOffset(@NonNull State state) {
            return 0;
        }

        public int computeVerticalScrollRange(@NonNull State state) {
            return 0;
        }

        void d(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            RecyclerView recyclerView = this.f6051b;
            onInitializeAccessibilityNodeInfo(recyclerView.f6016a, recyclerView.B, accessibilityNodeInfoCompat);
        }

        public void detachAndScrapAttachedViews(@NonNull Recycler recycler) {
            for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
                scrapOrRecycleView(recycler, childCount, getChildAt(childCount));
            }
        }

        public void detachAndScrapView(@NonNull View view, @NonNull Recycler recycler) {
            scrapOrRecycleView(recycler, this.f6050a.k(view), view);
        }

        public void detachAndScrapViewAt(int i2, @NonNull Recycler recycler) {
            scrapOrRecycleView(recycler, i2, getChildAt(i2));
        }

        public void detachView(@NonNull View view) {
            int iK = this.f6050a.k(view);
            if (iK >= 0) {
                detachViewInternal(iK, view);
            }
        }

        public void detachViewAt(int i2) {
            detachViewInternal(i2, getChildAt(i2));
        }

        void e(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            ViewHolder viewHolderF = RecyclerView.F(view);
            if (viewHolderF == null || viewHolderF.isRemoved() || this.f6050a.l(viewHolderF.itemView)) {
                return;
            }
            RecyclerView recyclerView = this.f6051b;
            onInitializeAccessibilityNodeInfoForItem(recyclerView.f6016a, recyclerView.B, view, accessibilityNodeInfoCompat);
        }

        public void endAnimation(View view) {
            ItemAnimator itemAnimator = this.f6051b.f6039x;
            if (itemAnimator != null) {
                itemAnimator.endAnimation(RecyclerView.F(view));
            }
        }

        void f(SmoothScroller smoothScroller) {
            if (this.f6054e == smoothScroller) {
                this.f6054e = null;
            }
        }

        @Nullable
        public View findContainingItemView(@NonNull View view) {
            View viewFindContainingItemView;
            RecyclerView recyclerView = this.f6051b;
            if (recyclerView == null || (viewFindContainingItemView = recyclerView.findContainingItemView(view)) == null || this.f6050a.l(viewFindContainingItemView)) {
                return null;
            }
            return viewFindContainingItemView;
        }

        @Nullable
        public View findViewByPosition(int i2) {
            int childCount = getChildCount();
            for (int i3 = 0; i3 < childCount; i3++) {
                View childAt = getChildAt(i3);
                ViewHolder viewHolderF = RecyclerView.F(childAt);
                if (viewHolderF != null && viewHolderF.getLayoutPosition() == i2 && !viewHolderF.shouldIgnore() && (this.f6051b.B.isPreLayout() || !viewHolderF.isRemoved())) {
                    return childAt;
                }
            }
            return null;
        }

        boolean g(int i2, Bundle bundle) {
            RecyclerView recyclerView = this.f6051b;
            return performAccessibilityAction(recyclerView.f6016a, recyclerView.B, i2, bundle);
        }

        public abstract LayoutParams generateDefaultLayoutParams();

        public LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
            return layoutParams instanceof LayoutParams ? new LayoutParams((LayoutParams) layoutParams) : layoutParams instanceof ViewGroup.MarginLayoutParams ? new LayoutParams((ViewGroup.MarginLayoutParams) layoutParams) : new LayoutParams(layoutParams);
        }

        public int getBaseline() {
            return -1;
        }

        public int getBottomDecorationHeight(@NonNull View view) {
            return ((LayoutParams) view.getLayoutParams()).f6063b.bottom;
        }

        @Nullable
        public View getChildAt(int i2) {
            ChildHelper childHelper = this.f6050a;
            if (childHelper != null) {
                return childHelper.f(i2);
            }
            return null;
        }

        public int getChildCount() {
            ChildHelper childHelper = this.f6050a;
            if (childHelper != null) {
                return childHelper.g();
            }
            return 0;
        }

        public boolean getClipToPadding() {
            RecyclerView recyclerView = this.f6051b;
            return recyclerView != null && recyclerView.f6021f;
        }

        public int getColumnCountForAccessibility(@NonNull Recycler recycler, @NonNull State state) {
            return -1;
        }

        public int getDecoratedBottom(@NonNull View view) {
            return view.getBottom() + getBottomDecorationHeight(view);
        }

        public void getDecoratedBoundsWithMargins(@NonNull View view, @NonNull Rect rect) {
            RecyclerView.G(view, rect);
        }

        public int getDecoratedLeft(@NonNull View view) {
            return view.getLeft() - getLeftDecorationWidth(view);
        }

        public int getDecoratedMeasuredHeight(@NonNull View view) {
            Rect rect = ((LayoutParams) view.getLayoutParams()).f6063b;
            return view.getMeasuredHeight() + rect.top + rect.bottom;
        }

        public int getDecoratedMeasuredWidth(@NonNull View view) {
            Rect rect = ((LayoutParams) view.getLayoutParams()).f6063b;
            return view.getMeasuredWidth() + rect.left + rect.right;
        }

        public int getDecoratedRight(@NonNull View view) {
            return view.getRight() + getRightDecorationWidth(view);
        }

        public int getDecoratedTop(@NonNull View view) {
            return view.getTop() - getTopDecorationHeight(view);
        }

        @Nullable
        public View getFocusedChild() {
            View focusedChild;
            RecyclerView recyclerView = this.f6051b;
            if (recyclerView == null || (focusedChild = recyclerView.getFocusedChild()) == null || this.f6050a.l(focusedChild)) {
                return null;
            }
            return focusedChild;
        }

        @Px
        public int getHeight() {
            return this.mHeight;
        }

        public int getHeightMode() {
            return this.mHeightMode;
        }

        public int getItemCount() {
            RecyclerView recyclerView = this.f6051b;
            Adapter adapter = recyclerView != null ? recyclerView.getAdapter() : null;
            if (adapter != null) {
                return adapter.getItemCount();
            }
            return 0;
        }

        public int getItemViewType(@NonNull View view) {
            return RecyclerView.F(view).getItemViewType();
        }

        public int getLayoutDirection() {
            return ViewCompat.getLayoutDirection(this.f6051b);
        }

        public int getLeftDecorationWidth(@NonNull View view) {
            return ((LayoutParams) view.getLayoutParams()).f6063b.left;
        }

        @Px
        public int getMinimumHeight() {
            return ViewCompat.getMinimumHeight(this.f6051b);
        }

        @Px
        public int getMinimumWidth() {
            return ViewCompat.getMinimumWidth(this.f6051b);
        }

        @Px
        public int getPaddingBottom() {
            RecyclerView recyclerView = this.f6051b;
            if (recyclerView != null) {
                return recyclerView.getPaddingBottom();
            }
            return 0;
        }

        @Px
        public int getPaddingEnd() {
            RecyclerView recyclerView = this.f6051b;
            if (recyclerView != null) {
                return ViewCompat.getPaddingEnd(recyclerView);
            }
            return 0;
        }

        @Px
        public int getPaddingLeft() {
            RecyclerView recyclerView = this.f6051b;
            if (recyclerView != null) {
                return recyclerView.getPaddingLeft();
            }
            return 0;
        }

        @Px
        public int getPaddingRight() {
            RecyclerView recyclerView = this.f6051b;
            if (recyclerView != null) {
                return recyclerView.getPaddingRight();
            }
            return 0;
        }

        @Px
        public int getPaddingStart() {
            RecyclerView recyclerView = this.f6051b;
            if (recyclerView != null) {
                return ViewCompat.getPaddingStart(recyclerView);
            }
            return 0;
        }

        @Px
        public int getPaddingTop() {
            RecyclerView recyclerView = this.f6051b;
            if (recyclerView != null) {
                return recyclerView.getPaddingTop();
            }
            return 0;
        }

        public int getPosition(@NonNull View view) {
            return ((LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        }

        public int getRightDecorationWidth(@NonNull View view) {
            return ((LayoutParams) view.getLayoutParams()).f6063b.right;
        }

        public int getRowCountForAccessibility(@NonNull Recycler recycler, @NonNull State state) {
            return -1;
        }

        public int getSelectionModeForAccessibility(@NonNull Recycler recycler, @NonNull State state) {
            return 0;
        }

        public int getTopDecorationHeight(@NonNull View view) {
            return ((LayoutParams) view.getLayoutParams()).f6063b.top;
        }

        public void getTransformedBoundingBox(@NonNull View view, boolean z2, @NonNull Rect rect) {
            Matrix matrix;
            if (z2) {
                Rect rect2 = ((LayoutParams) view.getLayoutParams()).f6063b;
                rect.set(-rect2.left, -rect2.top, view.getWidth() + rect2.right, view.getHeight() + rect2.bottom);
            } else {
                rect.set(0, 0, view.getWidth(), view.getHeight());
            }
            if (this.f6051b != null && (matrix = view.getMatrix()) != null && !matrix.isIdentity()) {
                RectF rectF = this.f6051b.f6024i;
                rectF.set(rect);
                matrix.mapRect(rectF);
                rect.set((int) Math.floor(rectF.left), (int) Math.floor(rectF.top), (int) Math.ceil(rectF.right), (int) Math.ceil(rectF.bottom));
            }
            rect.offset(view.getLeft(), view.getTop());
        }

        @Px
        public int getWidth() {
            return this.mWidth;
        }

        public int getWidthMode() {
            return this.mWidthMode;
        }

        boolean h(View view, int i2, Bundle bundle) {
            RecyclerView recyclerView = this.f6051b;
            return performAccessibilityActionForItem(recyclerView.f6016a, recyclerView.B, view, i2, bundle);
        }

        public boolean hasFocus() {
            RecyclerView recyclerView = this.f6051b;
            return recyclerView != null && recyclerView.hasFocus();
        }

        void i(Recycler recycler) {
            int iG = recycler.g();
            for (int i2 = iG - 1; i2 >= 0; i2--) {
                View viewJ = recycler.j(i2);
                ViewHolder viewHolderF = RecyclerView.F(viewJ);
                if (!viewHolderF.shouldIgnore()) {
                    viewHolderF.setIsRecyclable(false);
                    if (viewHolderF.isTmpDetached()) {
                        this.f6051b.removeDetachedView(viewJ, false);
                    }
                    ItemAnimator itemAnimator = this.f6051b.f6039x;
                    if (itemAnimator != null) {
                        itemAnimator.endAnimation(viewHolderF);
                    }
                    viewHolderF.setIsRecyclable(true);
                    recycler.r(viewJ);
                }
            }
            recycler.c();
            if (iG > 0) {
                this.f6051b.invalidate();
            }
        }

        public void ignoreView(@NonNull View view) {
            ViewParent parent = view.getParent();
            RecyclerView recyclerView = this.f6051b;
            if (parent != recyclerView || recyclerView.indexOfChild(view) == -1) {
                throw new IllegalArgumentException("View should be fully attached to be ignored" + this.f6051b.z());
            }
            ViewHolder viewHolderF = RecyclerView.F(view);
            viewHolderF.addFlags(128);
            this.f6051b.f6020e.o(viewHolderF);
        }

        public boolean isAttachedToWindow() {
            return this.f6056g;
        }

        public boolean isAutoMeasureEnabled() {
            return this.f6057h;
        }

        public boolean isFocused() {
            RecyclerView recyclerView = this.f6051b;
            return recyclerView != null && recyclerView.isFocused();
        }

        public final boolean isItemPrefetchEnabled() {
            return this.mItemPrefetchEnabled;
        }

        public boolean isLayoutHierarchical(@NonNull Recycler recycler, @NonNull State state) {
            return false;
        }

        public boolean isMeasurementCacheEnabled() {
            return this.mMeasurementCacheEnabled;
        }

        public boolean isSmoothScrolling() {
            SmoothScroller smoothScroller = this.f6054e;
            return smoothScroller != null && smoothScroller.isRunning();
        }

        public boolean isViewPartiallyVisible(@NonNull View view, boolean z2, boolean z3) {
            boolean z4 = this.f6052c.b(view, 24579) && this.f6053d.b(view, 24579);
            return z2 ? z4 : !z4;
        }

        void j(RecyclerView recyclerView) {
            k(View.MeasureSpec.makeMeasureSpec(recyclerView.getWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(recyclerView.getHeight(), 1073741824));
        }

        void k(int i2, int i3) {
            this.mWidth = View.MeasureSpec.getSize(i2);
            int mode = View.MeasureSpec.getMode(i2);
            this.mWidthMode = mode;
            if (mode == 0 && !RecyclerView.J) {
                this.mWidth = 0;
            }
            this.mHeight = View.MeasureSpec.getSize(i3);
            int mode2 = View.MeasureSpec.getMode(i3);
            this.mHeightMode = mode2;
            if (mode2 != 0 || RecyclerView.J) {
                return;
            }
            this.mHeight = 0;
        }

        void l(int i2, int i3) {
            int childCount = getChildCount();
            if (childCount == 0) {
                this.f6051b.o(i2, i3);
                return;
            }
            int i4 = Integer.MIN_VALUE;
            int i5 = Integer.MAX_VALUE;
            int i6 = Integer.MIN_VALUE;
            int i7 = Integer.MAX_VALUE;
            for (int i8 = 0; i8 < childCount; i8++) {
                View childAt = getChildAt(i8);
                Rect rect = this.f6051b.f6023h;
                getDecoratedBoundsWithMargins(childAt, rect);
                int i9 = rect.left;
                if (i9 < i7) {
                    i7 = i9;
                }
                int i10 = rect.right;
                if (i10 > i4) {
                    i4 = i10;
                }
                int i11 = rect.top;
                if (i11 < i5) {
                    i5 = i11;
                }
                int i12 = rect.bottom;
                if (i12 > i6) {
                    i6 = i12;
                }
            }
            this.f6051b.f6023h.set(i7, i5, i4, i6);
            setMeasuredDimension(this.f6051b.f6023h, i2, i3);
        }

        public void layoutDecorated(@NonNull View view, int i2, int i3, int i4, int i5) {
            Rect rect = ((LayoutParams) view.getLayoutParams()).f6063b;
            view.layout(i2 + rect.left, i3 + rect.top, i4 - rect.right, i5 - rect.bottom);
        }

        public void layoutDecoratedWithMargins(@NonNull View view, int i2, int i3, int i4, int i5) {
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            Rect rect = layoutParams.f6063b;
            view.layout(i2 + rect.left + ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin, i3 + rect.top + ((ViewGroup.MarginLayoutParams) layoutParams).topMargin, (i4 - rect.right) - ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin, (i5 - rect.bottom) - ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin);
        }

        void m(RecyclerView recyclerView) {
            if (recyclerView == null) {
                this.f6051b = null;
                this.f6050a = null;
                this.mWidth = 0;
                this.mHeight = 0;
            } else {
                this.f6051b = recyclerView;
                this.f6050a = recyclerView.f6019d;
                this.mWidth = recyclerView.getWidth();
                this.mHeight = recyclerView.getHeight();
            }
            this.mWidthMode = 1073741824;
            this.mHeightMode = 1073741824;
        }

        public void measureChild(@NonNull View view, int i2, int i3) {
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            Rect rectH = this.f6051b.H(view);
            int i4 = i2 + rectH.left + rectH.right;
            int i5 = i3 + rectH.top + rectH.bottom;
            int childMeasureSpec = getChildMeasureSpec(getWidth(), getWidthMode(), getPaddingLeft() + getPaddingRight() + i4, ((ViewGroup.MarginLayoutParams) layoutParams).width, canScrollHorizontally());
            int childMeasureSpec2 = getChildMeasureSpec(getHeight(), getHeightMode(), getPaddingTop() + getPaddingBottom() + i5, ((ViewGroup.MarginLayoutParams) layoutParams).height, canScrollVertically());
            if (n(view, childMeasureSpec, childMeasureSpec2, layoutParams)) {
                view.measure(childMeasureSpec, childMeasureSpec2);
            }
        }

        public void measureChildWithMargins(@NonNull View view, int i2, int i3) {
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            Rect rectH = this.f6051b.H(view);
            int i4 = i2 + rectH.left + rectH.right;
            int i5 = i3 + rectH.top + rectH.bottom;
            int childMeasureSpec = getChildMeasureSpec(getWidth(), getWidthMode(), getPaddingLeft() + getPaddingRight() + ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin + i4, ((ViewGroup.MarginLayoutParams) layoutParams).width, canScrollHorizontally());
            int childMeasureSpec2 = getChildMeasureSpec(getHeight(), getHeightMode(), getPaddingTop() + getPaddingBottom() + ((ViewGroup.MarginLayoutParams) layoutParams).topMargin + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin + i5, ((ViewGroup.MarginLayoutParams) layoutParams).height, canScrollVertically());
            if (n(view, childMeasureSpec, childMeasureSpec2, layoutParams)) {
                view.measure(childMeasureSpec, childMeasureSpec2);
            }
        }

        public void moveView(int i2, int i3) {
            View childAt = getChildAt(i2);
            if (childAt != null) {
                detachViewAt(i2);
                attachView(childAt, i3);
            } else {
                throw new IllegalArgumentException("Cannot move a child from non-existing index:" + i2 + this.f6051b.toString());
            }
        }

        boolean n(View view, int i2, int i3, LayoutParams layoutParams) {
            return (!view.isLayoutRequested() && this.mMeasurementCacheEnabled && isMeasurementUpToDate(view.getWidth(), i2, ((ViewGroup.MarginLayoutParams) layoutParams).width) && isMeasurementUpToDate(view.getHeight(), i3, ((ViewGroup.MarginLayoutParams) layoutParams).height)) ? false : true;
        }

        boolean o() {
            return false;
        }

        public void offsetChildrenHorizontal(@Px int i2) {
            RecyclerView recyclerView = this.f6051b;
            if (recyclerView != null) {
                recyclerView.offsetChildrenHorizontal(i2);
            }
        }

        public void offsetChildrenVertical(@Px int i2) {
            RecyclerView recyclerView = this.f6051b;
            if (recyclerView != null) {
                recyclerView.offsetChildrenVertical(i2);
            }
        }

        public void onAdapterChanged(@Nullable Adapter adapter, @Nullable Adapter adapter2) {
        }

        public boolean onAddFocusables(@NonNull RecyclerView recyclerView, @NonNull ArrayList<View> arrayList, int i2, int i3) {
            return false;
        }

        @CallSuper
        public void onAttachedToWindow(RecyclerView recyclerView) {
        }

        @Deprecated
        public void onDetachedFromWindow(RecyclerView recyclerView) {
        }

        @Nullable
        public View onFocusSearchFailed(@NonNull View view, int i2, @NonNull Recycler recycler, @NonNull State state) {
            return null;
        }

        public void onInitializeAccessibilityEvent(@NonNull AccessibilityEvent accessibilityEvent) {
            RecyclerView recyclerView = this.f6051b;
            onInitializeAccessibilityEvent(recyclerView.f6016a, recyclerView.B, accessibilityEvent);
        }

        public void onInitializeAccessibilityNodeInfo(@NonNull Recycler recycler, @NonNull State state, @NonNull AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            if (this.f6051b.canScrollVertically(-1) || this.f6051b.canScrollHorizontally(-1)) {
                accessibilityNodeInfoCompat.addAction(8192);
                accessibilityNodeInfoCompat.setScrollable(true);
            }
            if (this.f6051b.canScrollVertically(1) || this.f6051b.canScrollHorizontally(1)) {
                accessibilityNodeInfoCompat.addAction(4096);
                accessibilityNodeInfoCompat.setScrollable(true);
            }
            accessibilityNodeInfoCompat.setCollectionInfo(AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(getRowCountForAccessibility(recycler, state), getColumnCountForAccessibility(recycler, state), isLayoutHierarchical(recycler, state), getSelectionModeForAccessibility(recycler, state)));
        }

        public void onInitializeAccessibilityNodeInfoForItem(@NonNull Recycler recycler, @NonNull State state, @NonNull View view, @NonNull AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        }

        @Nullable
        public View onInterceptFocusSearch(@NonNull View view, int i2) {
            return null;
        }

        public void onItemsAdded(@NonNull RecyclerView recyclerView, int i2, int i3) {
        }

        public void onItemsChanged(@NonNull RecyclerView recyclerView) {
        }

        public void onItemsMoved(@NonNull RecyclerView recyclerView, int i2, int i3, int i4) {
        }

        public void onItemsRemoved(@NonNull RecyclerView recyclerView, int i2, int i3) {
        }

        public void onItemsUpdated(@NonNull RecyclerView recyclerView, int i2, int i3) {
        }

        public void onLayoutChildren(Recycler recycler, State state) {
            Log.e("RecyclerView", "You must override onLayoutChildren(Recycler recycler, State state) ");
        }

        public void onLayoutCompleted(State state) {
        }

        public void onMeasure(@NonNull Recycler recycler, @NonNull State state, int i2, int i3) {
            this.f6051b.o(i2, i3);
        }

        @Deprecated
        public boolean onRequestChildFocus(@NonNull RecyclerView recyclerView, @NonNull View view, @Nullable View view2) {
            return isSmoothScrolling() || recyclerView.isComputingLayout();
        }

        public void onRestoreInstanceState(Parcelable parcelable) {
        }

        @Nullable
        public Parcelable onSaveInstanceState() {
            return null;
        }

        public void onScrollStateChanged(int i2) {
        }

        boolean p(View view, int i2, int i3, LayoutParams layoutParams) {
            return (this.mMeasurementCacheEnabled && isMeasurementUpToDate(view.getMeasuredWidth(), i2, ((ViewGroup.MarginLayoutParams) layoutParams).width) && isMeasurementUpToDate(view.getMeasuredHeight(), i3, ((ViewGroup.MarginLayoutParams) layoutParams).height)) ? false : true;
        }

        public boolean performAccessibilityAction(@NonNull Recycler recycler, @NonNull State state, int i2, @Nullable Bundle bundle) {
            int height;
            int width;
            int i3;
            int i4;
            RecyclerView recyclerView = this.f6051b;
            if (recyclerView == null) {
                return false;
            }
            if (i2 == 4096) {
                height = recyclerView.canScrollVertically(1) ? (getHeight() - getPaddingTop()) - getPaddingBottom() : 0;
                if (this.f6051b.canScrollHorizontally(1)) {
                    width = (getWidth() - getPaddingLeft()) - getPaddingRight();
                    i3 = height;
                    i4 = width;
                }
                i3 = height;
                i4 = 0;
            } else if (i2 != 8192) {
                i4 = 0;
                i3 = 0;
            } else {
                height = recyclerView.canScrollVertically(-1) ? -((getHeight() - getPaddingTop()) - getPaddingBottom()) : 0;
                if (this.f6051b.canScrollHorizontally(-1)) {
                    width = -((getWidth() - getPaddingLeft()) - getPaddingRight());
                    i3 = height;
                    i4 = width;
                }
                i3 = height;
                i4 = 0;
            }
            if (i3 == 0 && i4 == 0) {
                return false;
            }
            this.f6051b.g0(i4, i3, null, Integer.MIN_VALUE, true);
            return true;
        }

        public boolean performAccessibilityActionForItem(@NonNull Recycler recycler, @NonNull State state, @NonNull View view, int i2, @Nullable Bundle bundle) {
            return false;
        }

        public void postOnAnimation(Runnable runnable) {
            RecyclerView recyclerView = this.f6051b;
            if (recyclerView != null) {
                ViewCompat.postOnAnimation(recyclerView, runnable);
            }
        }

        void q() {
            SmoothScroller smoothScroller = this.f6054e;
            if (smoothScroller != null) {
                smoothScroller.i();
            }
        }

        public void removeAllViews() {
            for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
                this.f6050a.o(childCount);
            }
        }

        public void removeAndRecycleAllViews(@NonNull Recycler recycler) {
            for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
                if (!RecyclerView.F(getChildAt(childCount)).shouldIgnore()) {
                    removeAndRecycleViewAt(childCount, recycler);
                }
            }
        }

        public void removeAndRecycleView(@NonNull View view, @NonNull Recycler recycler) {
            removeView(view);
            recycler.recycleView(view);
        }

        public void removeAndRecycleViewAt(int i2, @NonNull Recycler recycler) {
            View childAt = getChildAt(i2);
            removeViewAt(i2);
            recycler.recycleView(childAt);
        }

        public boolean removeCallbacks(Runnable runnable) {
            RecyclerView recyclerView = this.f6051b;
            if (recyclerView != null) {
                return recyclerView.removeCallbacks(runnable);
            }
            return false;
        }

        public void removeDetachedView(@NonNull View view) {
            this.f6051b.removeDetachedView(view, false);
        }

        public void removeView(View view) {
            this.f6050a.n(view);
        }

        public void removeViewAt(int i2) {
            if (getChildAt(i2) != null) {
                this.f6050a.o(i2);
            }
        }

        public boolean requestChildRectangleOnScreen(@NonNull RecyclerView recyclerView, @NonNull View view, @NonNull Rect rect, boolean z2) {
            return requestChildRectangleOnScreen(recyclerView, view, rect, z2, false);
        }

        public void requestLayout() {
            RecyclerView recyclerView = this.f6051b;
            if (recyclerView != null) {
                recyclerView.requestLayout();
            }
        }

        public void requestSimpleAnimationsInNextLayout() {
            this.f6055f = true;
        }

        public int scrollHorizontallyBy(int i2, Recycler recycler, State state) {
            return 0;
        }

        public void scrollToPosition(int i2) {
        }

        public int scrollVerticallyBy(int i2, Recycler recycler, State state) {
            return 0;
        }

        @Deprecated
        public void setAutoMeasureEnabled(boolean z2) {
            this.f6057h = z2;
        }

        public final void setItemPrefetchEnabled(boolean z2) {
            if (z2 != this.mItemPrefetchEnabled) {
                this.mItemPrefetchEnabled = z2;
                this.f6058i = 0;
                RecyclerView recyclerView = this.f6051b;
                if (recyclerView != null) {
                    recyclerView.f6016a.A();
                }
            }
        }

        public void setMeasuredDimension(Rect rect, int i2, int i3) {
            setMeasuredDimension(chooseSize(i2, rect.width() + getPaddingLeft() + getPaddingRight(), getMinimumWidth()), chooseSize(i3, rect.height() + getPaddingTop() + getPaddingBottom(), getMinimumHeight()));
        }

        public void setMeasurementCacheEnabled(boolean z2) {
            this.mMeasurementCacheEnabled = z2;
        }

        public void smoothScrollToPosition(RecyclerView recyclerView, State state, int i2) {
            Log.e("RecyclerView", "You must override smoothScrollToPosition to support smooth scrolling");
        }

        public void startSmoothScroll(SmoothScroller smoothScroller) {
            SmoothScroller smoothScroller2 = this.f6054e;
            if (smoothScroller2 != null && smoothScroller != smoothScroller2 && smoothScroller2.isRunning()) {
                this.f6054e.i();
            }
            this.f6054e = smoothScroller;
            smoothScroller.h(this.f6051b, this);
        }

        public void stopIgnoringView(@NonNull View view) {
            ViewHolder viewHolderF = RecyclerView.F(view);
            viewHolderF.stopIgnoring();
            viewHolderF.resetInternal();
            viewHolderF.addFlags(4);
        }

        public boolean supportsPredictiveItemAnimations() {
            return false;
        }

        public void addDisappearingView(View view, int i2) {
            addViewInt(view, i2, true);
        }

        public void addView(View view, int i2) {
            addViewInt(view, i2, false);
        }

        @CallSuper
        public void onDetachedFromWindow(RecyclerView recyclerView, Recycler recycler) {
            onDetachedFromWindow(recyclerView);
        }

        public void onInitializeAccessibilityEvent(@NonNull Recycler recycler, @NonNull State state, @NonNull AccessibilityEvent accessibilityEvent) {
            RecyclerView recyclerView = this.f6051b;
            if (recyclerView == null || accessibilityEvent == null) {
                return;
            }
            boolean z2 = true;
            if (!recyclerView.canScrollVertically(1) && !this.f6051b.canScrollVertically(-1) && !this.f6051b.canScrollHorizontally(-1) && !this.f6051b.canScrollHorizontally(1)) {
                z2 = false;
            }
            accessibilityEvent.setScrollable(z2);
            Adapter adapter = this.f6051b.f6025j;
            if (adapter != null) {
                accessibilityEvent.setItemCount(adapter.getItemCount());
            }
        }

        public void onItemsUpdated(@NonNull RecyclerView recyclerView, int i2, int i3, @Nullable Object obj) {
            onItemsUpdated(recyclerView, i2, i3);
        }

        public boolean onRequestChildFocus(@NonNull RecyclerView recyclerView, @NonNull State state, @NonNull View view, @Nullable View view2) {
            return onRequestChildFocus(recyclerView, view, view2);
        }

        public boolean requestChildRectangleOnScreen(@NonNull RecyclerView recyclerView, @NonNull View view, @NonNull Rect rect, boolean z2, boolean z3) {
            int[] childRectangleOnScreenScrollAmount = getChildRectangleOnScreenScrollAmount(view, rect);
            int i2 = childRectangleOnScreenScrollAmount[0];
            int i3 = childRectangleOnScreenScrollAmount[1];
            if ((z3 && !isFocusedChildVisibleAfterScrolling(recyclerView, i2, i3)) || (i2 == 0 && i3 == 0)) {
                return false;
            }
            if (z2) {
                recyclerView.scrollBy(i2, i3);
            } else {
                recyclerView.smoothScrollBy(i2, i3);
            }
            return true;
        }

        /* JADX WARN: Removed duplicated region for block: B:10:0x001a  */
        /* JADX WARN: Removed duplicated region for block: B:14:0x0022  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public static int getChildMeasureSpec(int r4, int r5, int r6, int r7, boolean r8) {
            /*
                int r4 = r4 - r6
                r6 = 0
                int r4 = java.lang.Math.max(r6, r4)
                r0 = -2
                r1 = -1
                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                r3 = 1073741824(0x40000000, float:2.0)
                if (r8 == 0) goto L1d
                if (r7 < 0) goto L12
            L10:
                r5 = r3
                goto L30
            L12:
                if (r7 != r1) goto L1a
                if (r5 == r2) goto L22
                if (r5 == 0) goto L1a
                if (r5 == r3) goto L22
            L1a:
                r5 = r6
                r7 = r5
                goto L30
            L1d:
                if (r7 < 0) goto L20
                goto L10
            L20:
                if (r7 != r1) goto L24
            L22:
                r7 = r4
                goto L30
            L24:
                if (r7 != r0) goto L1a
                if (r5 == r2) goto L2e
                if (r5 != r3) goto L2b
                goto L2e
            L2b:
                r7 = r4
                r5 = r6
                goto L30
            L2e:
                r7 = r4
                r5 = r2
            L30:
                int r4 = android.view.View.MeasureSpec.makeMeasureSpec(r7, r5)
                return r4
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.RecyclerView.LayoutManager.getChildMeasureSpec(int, int, int, int, boolean):int");
        }

        public void attachView(@NonNull View view, int i2) {
            attachView(view, i2, (LayoutParams) view.getLayoutParams());
        }

        public LayoutParams generateLayoutParams(Context context, AttributeSet attributeSet) {
            return new LayoutParams(context, attributeSet);
        }

        public void setMeasuredDimension(int i2, int i3) {
            this.f6051b.setMeasuredDimension(i2, i3);
        }

        public void attachView(@NonNull View view) {
            attachView(view, -1);
        }
    }

    public interface OnChildAttachStateChangeListener {
        void onChildViewAttachedToWindow(@NonNull View view);

        void onChildViewDetachedFromWindow(@NonNull View view);
    }

    public static abstract class OnFlingListener {
        public abstract boolean onFling(int i2, int i3);
    }

    public interface OnItemTouchListener {
        boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent);

        void onRequestDisallowInterceptTouchEvent(boolean z2);

        void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent);
    }

    public static abstract class OnScrollListener {
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int i2) {
        }

        public void onScrolled(@NonNull RecyclerView recyclerView, int i2, int i3) {
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public @interface Orientation {
    }

    public static class RecycledViewPool {
        private static final int DEFAULT_MAX_SCRAP = 5;

        /* renamed from: a, reason: collision with root package name */
        SparseArray f6066a = new SparseArray();
        private int mAttachCount = 0;

        static class ScrapData {

            /* renamed from: a, reason: collision with root package name */
            final ArrayList f6067a = new ArrayList();

            /* renamed from: b, reason: collision with root package name */
            int f6068b = 5;

            /* renamed from: c, reason: collision with root package name */
            long f6069c = 0;

            /* renamed from: d, reason: collision with root package name */
            long f6070d = 0;

            ScrapData() {
            }
        }

        private ScrapData getScrapDataForType(int i2) {
            ScrapData scrapData = (ScrapData) this.f6066a.get(i2);
            if (scrapData != null) {
                return scrapData;
            }
            ScrapData scrapData2 = new ScrapData();
            this.f6066a.put(i2, scrapData2);
            return scrapData2;
        }

        void a() {
            this.mAttachCount++;
        }

        void b() {
            this.mAttachCount--;
        }

        void c(int i2, long j2) {
            ScrapData scrapDataForType = getScrapDataForType(i2);
            scrapDataForType.f6070d = f(scrapDataForType.f6070d, j2);
        }

        public void clear() {
            for (int i2 = 0; i2 < this.f6066a.size(); i2++) {
                ((ScrapData) this.f6066a.valueAt(i2)).f6067a.clear();
            }
        }

        void d(int i2, long j2) {
            ScrapData scrapDataForType = getScrapDataForType(i2);
            scrapDataForType.f6069c = f(scrapDataForType.f6069c, j2);
        }

        void e(Adapter adapter, Adapter adapter2, boolean z2) {
            if (adapter != null) {
                b();
            }
            if (!z2 && this.mAttachCount == 0) {
                clear();
            }
            if (adapter2 != null) {
                a();
            }
        }

        long f(long j2, long j3) {
            return j2 == 0 ? j3 : ((j2 / 4) * 3) + (j3 / 4);
        }

        boolean g(int i2, long j2, long j3) {
            long j4 = getScrapDataForType(i2).f6070d;
            return j4 == 0 || j2 + j4 < j3;
        }

        @Nullable
        public ViewHolder getRecycledView(int i2) {
            ScrapData scrapData = (ScrapData) this.f6066a.get(i2);
            if (scrapData == null || scrapData.f6067a.isEmpty()) {
                return null;
            }
            ArrayList arrayList = scrapData.f6067a;
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                if (!((ViewHolder) arrayList.get(size)).isAttachedToTransitionOverlay()) {
                    return (ViewHolder) arrayList.remove(size);
                }
            }
            return null;
        }

        public int getRecycledViewCount(int i2) {
            return getScrapDataForType(i2).f6067a.size();
        }

        boolean h(int i2, long j2, long j3) {
            long j4 = getScrapDataForType(i2).f6069c;
            return j4 == 0 || j2 + j4 < j3;
        }

        public void putRecycledView(ViewHolder viewHolder) {
            int itemViewType = viewHolder.getItemViewType();
            ArrayList arrayList = getScrapDataForType(itemViewType).f6067a;
            if (((ScrapData) this.f6066a.get(itemViewType)).f6068b <= arrayList.size()) {
                return;
            }
            viewHolder.resetInternal();
            arrayList.add(viewHolder);
        }

        public void setMaxRecycledViews(int i2, int i3) {
            ScrapData scrapDataForType = getScrapDataForType(i2);
            scrapDataForType.f6068b = i3;
            ArrayList arrayList = scrapDataForType.f6067a;
            while (arrayList.size() > i3) {
                arrayList.remove(arrayList.size() - 1);
            }
        }
    }

    public interface RecyclerListener {
        void onViewRecycled(@NonNull ViewHolder viewHolder);
    }

    private class RecyclerViewDataObserver extends AdapterDataObserver {
        RecyclerViewDataObserver() {
        }

        void a() {
            if (RecyclerView.K) {
                RecyclerView recyclerView = RecyclerView.this;
                if (recyclerView.f6031p && recyclerView.f6030o) {
                    ViewCompat.postOnAnimation(recyclerView, recyclerView.f6022g);
                    return;
                }
            }
            RecyclerView recyclerView2 = RecyclerView.this;
            recyclerView2.f6036u = true;
            recyclerView2.requestLayout();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public void onChanged() {
            RecyclerView.this.i(null);
            RecyclerView recyclerView = RecyclerView.this;
            recyclerView.B.f6084f = true;
            recyclerView.W(true);
            if (RecyclerView.this.f6018c.g()) {
                return;
            }
            RecyclerView.this.requestLayout();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public void onItemRangeChanged(int i2, int i3, Object obj) {
            RecyclerView.this.i(null);
            if (RecyclerView.this.f6018c.i(i2, i3, obj)) {
                a();
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public void onItemRangeInserted(int i2, int i3) {
            RecyclerView.this.i(null);
            if (RecyclerView.this.f6018c.j(i2, i3)) {
                a();
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public void onItemRangeMoved(int i2, int i3, int i4) {
            RecyclerView.this.i(null);
            if (RecyclerView.this.f6018c.k(i2, i3, i4)) {
                a();
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public void onItemRangeRemoved(int i2, int i3) {
            RecyclerView.this.i(null);
            if (RecyclerView.this.f6018c.l(i2, i3)) {
                a();
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public void onStateRestorationPolicyChanged() {
            Adapter adapter;
            RecyclerView recyclerView = RecyclerView.this;
            if (recyclerView.f6017b == null || (adapter = recyclerView.f6025j) == null || !adapter.canRestoreState()) {
                return;
            }
            RecyclerView.this.requestLayout();
        }
    }

    public static class SimpleOnItemTouchListener implements OnItemTouchListener {
        @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
        public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
            return false;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
        public void onRequestDisallowInterceptTouchEvent(boolean z2) {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
        public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
        }
    }

    public static abstract class SmoothScroller {
        private LayoutManager mLayoutManager;
        private boolean mPendingInitialRun;
        private RecyclerView mRecyclerView;
        private boolean mRunning;
        private boolean mStarted;
        private View mTargetView;
        private int mTargetPosition = -1;
        private final Action mRecyclingAction = new Action(0, 0);

        public static class Action {
            public static final int UNDEFINED_DURATION = Integer.MIN_VALUE;
            private boolean mChanged;
            private int mConsecutiveUpdates;
            private int mDuration;
            private int mDx;
            private int mDy;
            private Interpolator mInterpolator;
            private int mJumpToPosition;

            public Action(@Px int i2, @Px int i3) {
                this(i2, i3, Integer.MIN_VALUE, null);
            }

            private void validate() {
                if (this.mInterpolator != null && this.mDuration < 1) {
                    throw new IllegalStateException("If you provide an interpolator, you must set a positive duration");
                }
                if (this.mDuration < 1) {
                    throw new IllegalStateException("Scroll duration must be a positive number");
                }
            }

            boolean a() {
                return this.mJumpToPosition >= 0;
            }

            void b(RecyclerView recyclerView) {
                int i2 = this.mJumpToPosition;
                if (i2 >= 0) {
                    this.mJumpToPosition = -1;
                    recyclerView.M(i2);
                    this.mChanged = false;
                } else {
                    if (!this.mChanged) {
                        this.mConsecutiveUpdates = 0;
                        return;
                    }
                    validate();
                    recyclerView.f6040y.smoothScrollBy(this.mDx, this.mDy, this.mDuration, this.mInterpolator);
                    int i3 = this.mConsecutiveUpdates + 1;
                    this.mConsecutiveUpdates = i3;
                    if (i3 > 10) {
                        Log.e("RecyclerView", "Smooth Scroll action is being updated too frequently. Make sure you are not changing it unless necessary");
                    }
                    this.mChanged = false;
                }
            }

            public int getDuration() {
                return this.mDuration;
            }

            @Px
            public int getDx() {
                return this.mDx;
            }

            @Px
            public int getDy() {
                return this.mDy;
            }

            @Nullable
            public Interpolator getInterpolator() {
                return this.mInterpolator;
            }

            public void jumpTo(int i2) {
                this.mJumpToPosition = i2;
            }

            public void setDuration(int i2) {
                this.mChanged = true;
                this.mDuration = i2;
            }

            public void setDx(@Px int i2) {
                this.mChanged = true;
                this.mDx = i2;
            }

            public void setDy(@Px int i2) {
                this.mChanged = true;
                this.mDy = i2;
            }

            public void setInterpolator(@Nullable Interpolator interpolator) {
                this.mChanged = true;
                this.mInterpolator = interpolator;
            }

            public void update(@Px int i2, @Px int i3, int i4, @Nullable Interpolator interpolator) {
                this.mDx = i2;
                this.mDy = i3;
                this.mDuration = i4;
                this.mInterpolator = interpolator;
                this.mChanged = true;
            }

            public Action(@Px int i2, @Px int i3, int i4) {
                this(i2, i3, i4, null);
            }

            public Action(@Px int i2, @Px int i3, int i4, @Nullable Interpolator interpolator) {
                this.mJumpToPosition = -1;
                this.mChanged = false;
                this.mConsecutiveUpdates = 0;
                this.mDx = i2;
                this.mDy = i3;
                this.mDuration = i4;
                this.mInterpolator = interpolator;
            }
        }

        public interface ScrollVectorProvider {
            @Nullable
            PointF computeScrollVectorForPosition(int i2);
        }

        protected void a(PointF pointF) {
            float f2 = pointF.x;
            float f3 = pointF.y;
            float fSqrt = (float) Math.sqrt((f2 * f2) + (f3 * f3));
            pointF.x /= fSqrt;
            pointF.y /= fSqrt;
        }

        void b(int i2, int i3) {
            PointF pointFComputeScrollVectorForPosition;
            RecyclerView recyclerView = this.mRecyclerView;
            if (this.mTargetPosition == -1 || recyclerView == null) {
                i();
            }
            if (this.mPendingInitialRun && this.mTargetView == null && this.mLayoutManager != null && (pointFComputeScrollVectorForPosition = computeScrollVectorForPosition(this.mTargetPosition)) != null) {
                float f2 = pointFComputeScrollVectorForPosition.x;
                if (f2 != 0.0f || pointFComputeScrollVectorForPosition.y != 0.0f) {
                    recyclerView.d0((int) Math.signum(f2), (int) Math.signum(pointFComputeScrollVectorForPosition.y), null);
                }
            }
            this.mPendingInitialRun = false;
            View view = this.mTargetView;
            if (view != null) {
                if (getChildPosition(view) == this.mTargetPosition) {
                    g(this.mTargetView, recyclerView.B, this.mRecyclingAction);
                    this.mRecyclingAction.b(recyclerView);
                    i();
                } else {
                    Log.e("RecyclerView", "Passed over target position while smooth scrolling.");
                    this.mTargetView = null;
                }
            }
            if (this.mRunning) {
                d(i2, i3, recyclerView.B, this.mRecyclingAction);
                boolean zA = this.mRecyclingAction.a();
                this.mRecyclingAction.b(recyclerView);
                if (zA && this.mRunning) {
                    this.mPendingInitialRun = true;
                    recyclerView.f6040y.a();
                }
            }
        }

        protected void c(View view) {
            if (getChildPosition(view) == getTargetPosition()) {
                this.mTargetView = view;
            }
        }

        @Nullable
        public PointF computeScrollVectorForPosition(int i2) {
            Object layoutManager = getLayoutManager();
            if (layoutManager instanceof ScrollVectorProvider) {
                return ((ScrollVectorProvider) layoutManager).computeScrollVectorForPosition(i2);
            }
            Log.w("RecyclerView", "You should override computeScrollVectorForPosition when the LayoutManager does not implement " + ScrollVectorProvider.class.getCanonicalName());
            return null;
        }

        protected abstract void d(int i2, int i3, State state, Action action);

        protected abstract void e();

        protected abstract void f();

        public View findViewByPosition(int i2) {
            return this.mRecyclerView.f6026k.findViewByPosition(i2);
        }

        protected abstract void g(View view, State state, Action action);

        public int getChildCount() {
            return this.mRecyclerView.f6026k.getChildCount();
        }

        public int getChildPosition(View view) {
            return this.mRecyclerView.getChildLayoutPosition(view);
        }

        @Nullable
        public LayoutManager getLayoutManager() {
            return this.mLayoutManager;
        }

        public int getTargetPosition() {
            return this.mTargetPosition;
        }

        void h(RecyclerView recyclerView, LayoutManager layoutManager) {
            recyclerView.f6040y.stop();
            if (this.mStarted) {
                Log.w("RecyclerView", "An instance of " + getClass().getSimpleName() + " was started more than once. Each instance of" + getClass().getSimpleName() + " is intended to only be used once. You should create a new instance for each use.");
            }
            this.mRecyclerView = recyclerView;
            this.mLayoutManager = layoutManager;
            int i2 = this.mTargetPosition;
            if (i2 == -1) {
                throw new IllegalArgumentException("Invalid target position");
            }
            recyclerView.B.f6079a = i2;
            this.mRunning = true;
            this.mPendingInitialRun = true;
            this.mTargetView = findViewByPosition(getTargetPosition());
            e();
            this.mRecyclerView.f6040y.a();
            this.mStarted = true;
        }

        protected final void i() {
            if (this.mRunning) {
                this.mRunning = false;
                f();
                this.mRecyclerView.B.f6079a = -1;
                this.mTargetView = null;
                this.mTargetPosition = -1;
                this.mPendingInitialRun = false;
                this.mLayoutManager.f(this);
                this.mLayoutManager = null;
                this.mRecyclerView = null;
            }
        }

        @Deprecated
        public void instantScrollToPosition(int i2) {
            this.mRecyclerView.scrollToPosition(i2);
        }

        public boolean isPendingInitialRun() {
            return this.mPendingInitialRun;
        }

        public boolean isRunning() {
            return this.mRunning;
        }

        public void setTargetPosition(int i2) {
            this.mTargetPosition = i2;
        }
    }

    public static class State {

        /* renamed from: a, reason: collision with root package name */
        int f6079a = -1;

        /* renamed from: b, reason: collision with root package name */
        int f6080b = 0;

        /* renamed from: c, reason: collision with root package name */
        int f6081c = 0;

        /* renamed from: d, reason: collision with root package name */
        int f6082d = 1;

        /* renamed from: e, reason: collision with root package name */
        int f6083e = 0;

        /* renamed from: f, reason: collision with root package name */
        boolean f6084f = false;

        /* renamed from: g, reason: collision with root package name */
        boolean f6085g = false;

        /* renamed from: h, reason: collision with root package name */
        boolean f6086h = false;

        /* renamed from: i, reason: collision with root package name */
        boolean f6087i = false;

        /* renamed from: j, reason: collision with root package name */
        boolean f6088j = false;

        /* renamed from: k, reason: collision with root package name */
        boolean f6089k = false;

        /* renamed from: l, reason: collision with root package name */
        int f6090l;

        /* renamed from: m, reason: collision with root package name */
        long f6091m;
        private SparseArray<Object> mData;

        /* renamed from: n, reason: collision with root package name */
        int f6092n;

        /* renamed from: o, reason: collision with root package name */
        int f6093o;

        /* renamed from: p, reason: collision with root package name */
        int f6094p;

        void a(int i2) {
            if ((this.f6082d & i2) != 0) {
                return;
            }
            throw new IllegalStateException("Layout state should be one of " + Integer.toBinaryString(i2) + " but it is " + Integer.toBinaryString(this.f6082d));
        }

        void b(Adapter adapter) {
            this.f6082d = 1;
            this.f6083e = adapter.getItemCount();
            this.f6085g = false;
            this.f6086h = false;
            this.f6087i = false;
        }

        public boolean didStructureChange() {
            return this.f6084f;
        }

        public <T> T get(int i2) {
            SparseArray<Object> sparseArray = this.mData;
            if (sparseArray == null) {
                return null;
            }
            return (T) sparseArray.get(i2);
        }

        public int getItemCount() {
            return this.f6085g ? this.f6080b - this.f6081c : this.f6083e;
        }

        public int getRemainingScrollHorizontal() {
            return this.f6093o;
        }

        public int getRemainingScrollVertical() {
            return this.f6094p;
        }

        public int getTargetScrollPosition() {
            return this.f6079a;
        }

        public boolean hasTargetScrollPosition() {
            return this.f6079a != -1;
        }

        public boolean isMeasuring() {
            return this.f6087i;
        }

        public boolean isPreLayout() {
            return this.f6085g;
        }

        public void put(int i2, Object obj) {
            if (this.mData == null) {
                this.mData = new SparseArray<>();
            }
            this.mData.put(i2, obj);
        }

        public void remove(int i2) {
            SparseArray<Object> sparseArray = this.mData;
            if (sparseArray == null) {
                return;
            }
            sparseArray.remove(i2);
        }

        public String toString() {
            return "State{mTargetPosition=" + this.f6079a + ", mData=" + this.mData + ", mItemCount=" + this.f6083e + ", mIsMeasuring=" + this.f6087i + ", mPreviousLayoutItemCount=" + this.f6080b + ", mDeletedInvisibleItemCountSincePreviousLayout=" + this.f6081c + ", mStructureChanged=" + this.f6084f + ", mInPreLayout=" + this.f6085g + ", mRunSimpleAnimations=" + this.f6088j + ", mRunPredictiveAnimations=" + this.f6089k + '}';
        }

        public boolean willRunPredictiveAnimations() {
            return this.f6089k;
        }

        public boolean willRunSimpleAnimations() {
            return this.f6088j;
        }
    }

    public static abstract class ViewCacheExtension {
        @Nullable
        public abstract View getViewForPositionAndType(@NonNull Recycler recycler, int i2, int i3);
    }

    class ViewFlinger implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        OverScroller f6095a;

        /* renamed from: b, reason: collision with root package name */
        Interpolator f6096b;
        private boolean mEatRunOnAnimationRequest;
        private int mLastFlingX;
        private int mLastFlingY;
        private boolean mReSchedulePostAnimationCallback;

        ViewFlinger() {
            Interpolator interpolator = RecyclerView.M;
            this.f6096b = interpolator;
            this.mEatRunOnAnimationRequest = false;
            this.mReSchedulePostAnimationCallback = false;
            this.f6095a = new OverScroller(RecyclerView.this.getContext(), interpolator);
        }

        private int computeScrollDuration(int i2, int i3) {
            int iAbs = Math.abs(i2);
            int iAbs2 = Math.abs(i3);
            boolean z2 = iAbs > iAbs2;
            RecyclerView recyclerView = RecyclerView.this;
            int width = z2 ? recyclerView.getWidth() : recyclerView.getHeight();
            if (!z2) {
                iAbs = iAbs2;
            }
            return Math.min((int) (((iAbs / width) + 1.0f) * 300.0f), 2000);
        }

        private void internalPostOnAnimation() {
            RecyclerView.this.removeCallbacks(this);
            ViewCompat.postOnAnimation(RecyclerView.this, this);
        }

        void a() {
            if (this.mEatRunOnAnimationRequest) {
                this.mReSchedulePostAnimationCallback = true;
            } else {
                internalPostOnAnimation();
            }
        }

        public void fling(int i2, int i3) {
            RecyclerView.this.setScrollState(2);
            this.mLastFlingY = 0;
            this.mLastFlingX = 0;
            Interpolator interpolator = this.f6096b;
            Interpolator interpolator2 = RecyclerView.M;
            if (interpolator != interpolator2) {
                this.f6096b = interpolator2;
                this.f6095a = new OverScroller(RecyclerView.this.getContext(), interpolator2);
            }
            this.f6095a.fling(0, 0, i2, i3, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE);
            a();
        }

        @Override // java.lang.Runnable
        public void run() {
            int i2;
            int i3;
            RecyclerView recyclerView = RecyclerView.this;
            if (recyclerView.f6026k == null) {
                stop();
                return;
            }
            this.mReSchedulePostAnimationCallback = false;
            this.mEatRunOnAnimationRequest = true;
            recyclerView.n();
            OverScroller overScroller = this.f6095a;
            if (overScroller.computeScrollOffset()) {
                int currX = overScroller.getCurrX();
                int currY = overScroller.getCurrY();
                int i4 = currX - this.mLastFlingX;
                int i5 = currY - this.mLastFlingY;
                this.mLastFlingX = currX;
                this.mLastFlingY = currY;
                RecyclerView recyclerView2 = RecyclerView.this;
                int[] iArr = recyclerView2.G;
                iArr[0] = 0;
                iArr[1] = 0;
                if (recyclerView2.dispatchNestedPreScroll(i4, i5, iArr, null, 1)) {
                    int[] iArr2 = RecyclerView.this.G;
                    i4 -= iArr2[0];
                    i5 -= iArr2[1];
                }
                if (RecyclerView.this.getOverScrollMode() != 2) {
                    RecyclerView.this.m(i4, i5);
                }
                RecyclerView recyclerView3 = RecyclerView.this;
                if (recyclerView3.f6025j != null) {
                    int[] iArr3 = recyclerView3.G;
                    iArr3[0] = 0;
                    iArr3[1] = 0;
                    recyclerView3.d0(i4, i5, iArr3);
                    RecyclerView recyclerView4 = RecyclerView.this;
                    int[] iArr4 = recyclerView4.G;
                    i3 = iArr4[0];
                    i2 = iArr4[1];
                    i4 -= i3;
                    i5 -= i2;
                    SmoothScroller smoothScroller = recyclerView4.f6026k.f6054e;
                    if (smoothScroller != null && !smoothScroller.isPendingInitialRun() && smoothScroller.isRunning()) {
                        int itemCount = RecyclerView.this.B.getItemCount();
                        if (itemCount == 0) {
                            smoothScroller.i();
                        } else if (smoothScroller.getTargetPosition() >= itemCount) {
                            smoothScroller.setTargetPosition(itemCount - 1);
                            smoothScroller.b(i3, i2);
                        } else {
                            smoothScroller.b(i3, i2);
                        }
                    }
                } else {
                    i2 = 0;
                    i3 = 0;
                }
                if (!RecyclerView.this.f6029n.isEmpty()) {
                    RecyclerView.this.invalidate();
                }
                RecyclerView recyclerView5 = RecyclerView.this;
                int[] iArr5 = recyclerView5.G;
                iArr5[0] = 0;
                iArr5[1] = 0;
                recyclerView5.dispatchNestedScroll(i3, i2, i4, i5, null, 1, iArr5);
                RecyclerView recyclerView6 = RecyclerView.this;
                int[] iArr6 = recyclerView6.G;
                int i6 = i4 - iArr6[0];
                int i7 = i5 - iArr6[1];
                if (i3 != 0 || i2 != 0) {
                    recyclerView6.t(i3, i2);
                }
                if (!RecyclerView.this.awakenScrollBars()) {
                    RecyclerView.this.invalidate();
                }
                boolean z2 = overScroller.isFinished() || (((overScroller.getCurrX() == overScroller.getFinalX()) || i6 != 0) && ((overScroller.getCurrY() == overScroller.getFinalY()) || i7 != 0));
                SmoothScroller smoothScroller2 = RecyclerView.this.f6026k.f6054e;
                if ((smoothScroller2 == null || !smoothScroller2.isPendingInitialRun()) && z2) {
                    if (RecyclerView.this.getOverScrollMode() != 2) {
                        int currVelocity = (int) overScroller.getCurrVelocity();
                        int i8 = i6 < 0 ? -currVelocity : i6 > 0 ? currVelocity : 0;
                        if (i7 < 0) {
                            currVelocity = -currVelocity;
                        } else if (i7 <= 0) {
                            currVelocity = 0;
                        }
                        RecyclerView.this.a(i8, currVelocity);
                    }
                    if (RecyclerView.L) {
                        RecyclerView.this.A.a();
                    }
                } else {
                    a();
                    RecyclerView recyclerView7 = RecyclerView.this;
                    GapWorker gapWorker = recyclerView7.f6041z;
                    if (gapWorker != null) {
                        gapWorker.b(recyclerView7, i3, i2);
                    }
                }
            }
            SmoothScroller smoothScroller3 = RecyclerView.this.f6026k.f6054e;
            if (smoothScroller3 != null && smoothScroller3.isPendingInitialRun()) {
                smoothScroller3.b(0, 0);
            }
            this.mEatRunOnAnimationRequest = false;
            if (this.mReSchedulePostAnimationCallback) {
                internalPostOnAnimation();
            } else {
                RecyclerView.this.setScrollState(0);
                RecyclerView.this.stopNestedScroll(1);
            }
        }

        public void smoothScrollBy(int i2, int i3, int i4, @Nullable Interpolator interpolator) {
            if (i4 == Integer.MIN_VALUE) {
                i4 = computeScrollDuration(i2, i3);
            }
            int i5 = i4;
            if (interpolator == null) {
                interpolator = RecyclerView.M;
            }
            if (this.f6096b != interpolator) {
                this.f6096b = interpolator;
                this.f6095a = new OverScroller(RecyclerView.this.getContext(), interpolator);
            }
            this.mLastFlingY = 0;
            this.mLastFlingX = 0;
            RecyclerView.this.setScrollState(2);
            this.f6095a.startScroll(0, 0, i2, i3, i5);
            a();
        }

        public void stop() {
            RecyclerView.this.removeCallbacks(this);
            this.f6095a.abortAnimation();
        }
    }

    public static abstract class ViewHolder {
        static final int FLAG_ADAPTER_FULLUPDATE = 1024;
        static final int FLAG_ADAPTER_POSITION_UNKNOWN = 512;
        static final int FLAG_APPEARED_IN_PRE_LAYOUT = 4096;
        static final int FLAG_BOUNCED_FROM_HIDDEN_LIST = 8192;
        static final int FLAG_BOUND = 1;
        static final int FLAG_IGNORE = 128;
        static final int FLAG_INVALID = 4;
        static final int FLAG_MOVED = 2048;
        static final int FLAG_NOT_RECYCLABLE = 16;
        static final int FLAG_REMOVED = 8;
        static final int FLAG_RETURNED_FROM_SCRAP = 32;
        static final int FLAG_TMP_DETACHED = 256;
        static final int FLAG_UPDATE = 2;
        private static final List<Object> FULLUPDATE_PAYLOADS = Collections.emptyList();
        static final int PENDING_ACCESSIBILITY_STATE_NOT_SET = -1;

        @NonNull
        public final View itemView;
        Adapter<? extends ViewHolder> mBindingAdapter;
        int mFlags;
        WeakReference<RecyclerView> mNestedRecyclerView;
        RecyclerView mOwnerRecyclerView;
        int mPosition = -1;
        int mOldPosition = -1;
        long mItemId = -1;
        int mItemViewType = -1;
        int mPreLayoutPosition = -1;
        ViewHolder mShadowedHolder = null;
        ViewHolder mShadowingHolder = null;
        List<Object> mPayloads = null;
        List<Object> mUnmodifiedPayloads = null;
        private int mIsRecyclableCount = 0;
        Recycler mScrapContainer = null;
        boolean mInChangeScrap = false;
        private int mWasImportantForAccessibilityBeforeHidden = 0;

        @VisibleForTesting
        int mPendingAccessibilityState = -1;

        public ViewHolder(@NonNull View view) {
            if (view == null) {
                throw new IllegalArgumentException("itemView may not be null");
            }
            this.itemView = view;
        }

        private void createPayloadsIfNeeded() {
            if (this.mPayloads == null) {
                ArrayList arrayList = new ArrayList();
                this.mPayloads = arrayList;
                this.mUnmodifiedPayloads = Collections.unmodifiableList(arrayList);
            }
        }

        void addChangePayload(Object obj) {
            if (obj == null) {
                addFlags(1024);
            } else if ((1024 & this.mFlags) == 0) {
                createPayloadsIfNeeded();
                this.mPayloads.add(obj);
            }
        }

        void addFlags(int i2) {
            this.mFlags = i2 | this.mFlags;
        }

        void clearOldPosition() {
            this.mOldPosition = -1;
            this.mPreLayoutPosition = -1;
        }

        void clearPayload() {
            List<Object> list = this.mPayloads;
            if (list != null) {
                list.clear();
            }
            this.mFlags &= -1025;
        }

        void clearReturnedFromScrapFlag() {
            this.mFlags &= -33;
        }

        void clearTmpDetachFlag() {
            this.mFlags &= -257;
        }

        boolean doesTransientStatePreventRecycling() {
            return (this.mFlags & 16) == 0 && ViewCompat.hasTransientState(this.itemView);
        }

        void flagRemovedAndOffsetPosition(int i2, int i3, boolean z2) {
            addFlags(8);
            offsetPosition(i3, z2);
            this.mPosition = i2;
        }

        public final int getAbsoluteAdapterPosition() {
            RecyclerView recyclerView = this.mOwnerRecyclerView;
            if (recyclerView == null) {
                return -1;
            }
            return recyclerView.D(this);
        }

        @Deprecated
        public final int getAdapterPosition() {
            return getBindingAdapterPosition();
        }

        @Nullable
        public final Adapter<? extends ViewHolder> getBindingAdapter() {
            return this.mBindingAdapter;
        }

        public final int getBindingAdapterPosition() {
            RecyclerView recyclerView;
            Adapter adapter;
            int iD;
            if (this.mBindingAdapter == null || (recyclerView = this.mOwnerRecyclerView) == null || (adapter = recyclerView.getAdapter()) == null || (iD = this.mOwnerRecyclerView.D(this)) == -1) {
                return -1;
            }
            return adapter.findRelativeAdapterPositionIn(this.mBindingAdapter, this, iD);
        }

        public final long getItemId() {
            return this.mItemId;
        }

        public final int getItemViewType() {
            return this.mItemViewType;
        }

        public final int getLayoutPosition() {
            int i2 = this.mPreLayoutPosition;
            return i2 == -1 ? this.mPosition : i2;
        }

        public final int getOldPosition() {
            return this.mOldPosition;
        }

        @Deprecated
        public final int getPosition() {
            int i2 = this.mPreLayoutPosition;
            return i2 == -1 ? this.mPosition : i2;
        }

        List<Object> getUnmodifiedPayloads() {
            if ((this.mFlags & 1024) != 0) {
                return FULLUPDATE_PAYLOADS;
            }
            List<Object> list = this.mPayloads;
            return (list == null || list.size() == 0) ? FULLUPDATE_PAYLOADS : this.mUnmodifiedPayloads;
        }

        boolean hasAnyOfTheFlags(int i2) {
            return (i2 & this.mFlags) != 0;
        }

        boolean isAdapterPositionUnknown() {
            return (this.mFlags & 512) != 0 || isInvalid();
        }

        boolean isAttachedToTransitionOverlay() {
            return (this.itemView.getParent() == null || this.itemView.getParent() == this.mOwnerRecyclerView) ? false : true;
        }

        boolean isBound() {
            return (this.mFlags & 1) != 0;
        }

        boolean isInvalid() {
            return (this.mFlags & 4) != 0;
        }

        public final boolean isRecyclable() {
            return (this.mFlags & 16) == 0 && !ViewCompat.hasTransientState(this.itemView);
        }

        boolean isRemoved() {
            return (this.mFlags & 8) != 0;
        }

        boolean isScrap() {
            return this.mScrapContainer != null;
        }

        boolean isTmpDetached() {
            return (this.mFlags & 256) != 0;
        }

        boolean isUpdated() {
            return (this.mFlags & 2) != 0;
        }

        boolean needsUpdate() {
            return (this.mFlags & 2) != 0;
        }

        void offsetPosition(int i2, boolean z2) {
            if (this.mOldPosition == -1) {
                this.mOldPosition = this.mPosition;
            }
            if (this.mPreLayoutPosition == -1) {
                this.mPreLayoutPosition = this.mPosition;
            }
            if (z2) {
                this.mPreLayoutPosition += i2;
            }
            this.mPosition += i2;
            if (this.itemView.getLayoutParams() != null) {
                ((LayoutParams) this.itemView.getLayoutParams()).f6064c = true;
            }
        }

        void onEnteredHiddenState(RecyclerView recyclerView) {
            int i2 = this.mPendingAccessibilityState;
            if (i2 != -1) {
                this.mWasImportantForAccessibilityBeforeHidden = i2;
            } else {
                this.mWasImportantForAccessibilityBeforeHidden = ViewCompat.getImportantForAccessibility(this.itemView);
            }
            recyclerView.e0(this, 4);
        }

        void onLeftHiddenState(RecyclerView recyclerView) {
            recyclerView.e0(this, this.mWasImportantForAccessibilityBeforeHidden);
            this.mWasImportantForAccessibilityBeforeHidden = 0;
        }

        void resetInternal() {
            this.mFlags = 0;
            this.mPosition = -1;
            this.mOldPosition = -1;
            this.mItemId = -1L;
            this.mPreLayoutPosition = -1;
            this.mIsRecyclableCount = 0;
            this.mShadowedHolder = null;
            this.mShadowingHolder = null;
            clearPayload();
            this.mWasImportantForAccessibilityBeforeHidden = 0;
            this.mPendingAccessibilityState = -1;
            RecyclerView.k(this);
        }

        void saveOldPosition() {
            if (this.mOldPosition == -1) {
                this.mOldPosition = this.mPosition;
            }
        }

        void setFlags(int i2, int i3) {
            this.mFlags = (i2 & i3) | (this.mFlags & (~i3));
        }

        public final void setIsRecyclable(boolean z2) {
            int i2 = this.mIsRecyclableCount;
            int i3 = z2 ? i2 - 1 : i2 + 1;
            this.mIsRecyclableCount = i3;
            if (i3 < 0) {
                this.mIsRecyclableCount = 0;
                Log.e("View", "isRecyclable decremented below 0: unmatched pair of setIsRecyable() calls for " + this);
                return;
            }
            if (!z2 && i3 == 1) {
                this.mFlags |= 16;
            } else if (z2 && i3 == 0) {
                this.mFlags &= -17;
            }
        }

        void setScrapContainer(Recycler recycler, boolean z2) {
            this.mScrapContainer = recycler;
            this.mInChangeScrap = z2;
        }

        boolean shouldBeKeptAsChild() {
            return (this.mFlags & 16) != 0;
        }

        boolean shouldIgnore() {
            return (this.mFlags & 128) != 0;
        }

        void stopIgnoring() {
            this.mFlags &= -129;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder((getClass().isAnonymousClass() ? "ViewHolder" : getClass().getSimpleName()) + "{" + Integer.toHexString(hashCode()) + " position=" + this.mPosition + " id=" + this.mItemId + ", oldPos=" + this.mOldPosition + ", pLpos:" + this.mPreLayoutPosition);
            if (isScrap()) {
                sb.append(" scrap ");
                sb.append(this.mInChangeScrap ? "[changeScrap]" : "[attachedScrap]");
            }
            if (isInvalid()) {
                sb.append(" invalid");
            }
            if (!isBound()) {
                sb.append(" unbound");
            }
            if (needsUpdate()) {
                sb.append(" update");
            }
            if (isRemoved()) {
                sb.append(" removed");
            }
            if (shouldIgnore()) {
                sb.append(" ignored");
            }
            if (isTmpDetached()) {
                sb.append(" tmpDetached");
            }
            if (!isRecyclable()) {
                sb.append(" not recyclable(" + this.mIsRecyclableCount + ")");
            }
            if (isAdapterPositionUnknown()) {
                sb.append(" undefined adapter position");
            }
            if (this.itemView.getParent() == null) {
                sb.append(" no parent");
            }
            sb.append(i.f9804d);
            return sb.toString();
        }

        void unScrap() {
            this.mScrapContainer.z(this);
        }

        boolean wasReturnedFromScrap() {
            return (this.mFlags & 32) != 0;
        }
    }

    static {
        Class<?> cls = Integer.TYPE;
        LAYOUT_MANAGER_CONSTRUCTOR_SIGNATURE = new Class[]{Context.class, AttributeSet.class, cls, cls};
        M = new Interpolator() { // from class: androidx.recyclerview.widget.RecyclerView.3
            @Override // android.animation.TimeInterpolator
            public float getInterpolation(float f2) {
                float f3 = f2 - 1.0f;
                return (f3 * f3 * f3 * f3 * f3) + 1.0f;
            }
        };
    }

    public RecyclerView(@NonNull Context context) {
        this(context, null);
    }

    static RecyclerView B(View view) {
        if (!(view instanceof ViewGroup)) {
            return null;
        }
        if (view instanceof RecyclerView) {
            return (RecyclerView) view;
        }
        ViewGroup viewGroup = (ViewGroup) view;
        int childCount = viewGroup.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            RecyclerView recyclerViewB = B(viewGroup.getChildAt(i2));
            if (recyclerViewB != null) {
                return recyclerViewB;
            }
        }
        return null;
    }

    static ViewHolder F(View view) {
        if (view == null) {
            return null;
        }
        return ((LayoutParams) view.getLayoutParams()).f6062a;
    }

    static void G(View view, Rect rect) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        Rect rect2 = layoutParams.f6063b;
        rect.set((view.getLeft() - rect2.left) - ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin, (view.getTop() - rect2.top) - ((ViewGroup.MarginLayoutParams) layoutParams).topMargin, view.getRight() + rect2.right + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin, view.getBottom() + rect2.bottom + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin);
    }

    private void addAnimatingView(ViewHolder viewHolder) {
        View view = viewHolder.itemView;
        boolean z2 = view.getParent() == this;
        this.f6016a.z(getChildViewHolder(view));
        if (viewHolder.isTmpDetached()) {
            this.f6019d.c(view, -1, view.getLayoutParams(), true);
        } else if (z2) {
            this.f6019d.j(view);
        } else {
            this.f6019d.b(view, true);
        }
    }

    private void animateChange(@NonNull ViewHolder viewHolder, @NonNull ViewHolder viewHolder2, @NonNull ItemAnimator.ItemHolderInfo itemHolderInfo, @NonNull ItemAnimator.ItemHolderInfo itemHolderInfo2, boolean z2, boolean z3) {
        viewHolder.setIsRecyclable(false);
        if (z2) {
            addAnimatingView(viewHolder);
        }
        if (viewHolder != viewHolder2) {
            if (z3) {
                addAnimatingView(viewHolder2);
            }
            viewHolder.mShadowedHolder = viewHolder2;
            addAnimatingView(viewHolder);
            this.f6016a.z(viewHolder);
            viewHolder2.setIsRecyclable(false);
            viewHolder2.mShadowingHolder = viewHolder;
        }
        if (this.f6039x.animateChange(viewHolder, viewHolder2, itemHolderInfo, itemHolderInfo2)) {
            V();
        }
    }

    private void cancelScroll() {
        resetScroll();
        setScrollState(0);
    }

    private void createLayoutManager(Context context, String str, AttributeSet attributeSet, int i2, int i3) throws NoSuchMethodException, SecurityException {
        Object[] objArr;
        Constructor constructor;
        if (str != null) {
            String strTrim = str.trim();
            if (strTrim.isEmpty()) {
                return;
            }
            String fullClassName = getFullClassName(context, strTrim);
            try {
                Class<? extends U> clsAsSubclass = Class.forName(fullClassName, false, isInEditMode() ? getClass().getClassLoader() : context.getClassLoader()).asSubclass(LayoutManager.class);
                try {
                    constructor = clsAsSubclass.getConstructor(LAYOUT_MANAGER_CONSTRUCTOR_SIGNATURE);
                    objArr = new Object[]{context, attributeSet, Integer.valueOf(i2), Integer.valueOf(i3)};
                } catch (NoSuchMethodException e2) {
                    objArr = null;
                    try {
                        constructor = clsAsSubclass.getConstructor(null);
                    } catch (NoSuchMethodException e3) {
                        e3.initCause(e2);
                        throw new IllegalStateException(attributeSet.getPositionDescription() + ": Error creating LayoutManager " + fullClassName, e3);
                    }
                }
                constructor.setAccessible(true);
                setLayoutManager((LayoutManager) constructor.newInstance(objArr));
            } catch (ClassCastException e4) {
                throw new IllegalStateException(attributeSet.getPositionDescription() + ": Class is not a LayoutManager " + fullClassName, e4);
            } catch (ClassNotFoundException e5) {
                throw new IllegalStateException(attributeSet.getPositionDescription() + ": Unable to find LayoutManager " + fullClassName, e5);
            } catch (IllegalAccessException e6) {
                throw new IllegalStateException(attributeSet.getPositionDescription() + ": Cannot access non-public constructor " + fullClassName, e6);
            } catch (InstantiationException e7) {
                throw new IllegalStateException(attributeSet.getPositionDescription() + ": Could not instantiate the LayoutManager: " + fullClassName, e7);
            } catch (InvocationTargetException e8) {
                throw new IllegalStateException(attributeSet.getPositionDescription() + ": Could not instantiate the LayoutManager: " + fullClassName, e8);
            }
        }
    }

    private boolean didChildRangeChange(int i2, int i3) {
        findMinMaxChildLayoutPositions(this.mMinMaxLayoutPositions);
        int[] iArr = this.mMinMaxLayoutPositions;
        return (iArr[0] == i2 && iArr[1] == i3) ? false : true;
    }

    private void dispatchContentChangedIfNecessary() {
        int i2 = this.mEatenAccessibilityChangeFlags;
        this.mEatenAccessibilityChangeFlags = 0;
        if (i2 == 0 || !L()) {
            return;
        }
        AccessibilityEvent accessibilityEventObtain = AccessibilityEvent.obtain();
        accessibilityEventObtain.setEventType(2048);
        AccessibilityEventCompat.setContentChangeTypes(accessibilityEventObtain, i2);
        sendAccessibilityEventUnchecked(accessibilityEventObtain);
    }

    private void dispatchLayoutStep1() {
        this.B.a(1);
        A(this.B);
        this.B.f6087i = false;
        h0();
        this.f6020e.f();
        S();
        processAdapterUpdatesAndSetAnimationFlags();
        saveFocusInfo();
        State state = this.B;
        state.f6086h = state.f6088j && this.D;
        this.D = false;
        this.C = false;
        state.f6085g = state.f6089k;
        state.f6083e = this.f6025j.getItemCount();
        findMinMaxChildLayoutPositions(this.mMinMaxLayoutPositions);
        if (this.B.f6088j) {
            int iG = this.f6019d.g();
            for (int i2 = 0; i2 < iG; i2++) {
                ViewHolder viewHolderF = F(this.f6019d.f(i2));
                if (!viewHolderF.shouldIgnore() && (!viewHolderF.isInvalid() || this.f6025j.hasStableIds())) {
                    this.f6020e.e(viewHolderF, this.f6039x.recordPreLayoutInformation(this.B, viewHolderF, ItemAnimator.a(viewHolderF), viewHolderF.getUnmodifiedPayloads()));
                    if (this.B.f6086h && viewHolderF.isUpdated() && !viewHolderF.isRemoved() && !viewHolderF.shouldIgnore() && !viewHolderF.isInvalid()) {
                        this.f6020e.c(E(viewHolderF), viewHolderF);
                    }
                }
            }
        }
        if (this.B.f6089k) {
            b0();
            State state2 = this.B;
            boolean z2 = state2.f6084f;
            state2.f6084f = false;
            this.f6026k.onLayoutChildren(this.f6016a, state2);
            this.B.f6084f = z2;
            for (int i3 = 0; i3 < this.f6019d.g(); i3++) {
                ViewHolder viewHolderF2 = F(this.f6019d.f(i3));
                if (!viewHolderF2.shouldIgnore() && !this.f6020e.i(viewHolderF2)) {
                    int iA = ItemAnimator.a(viewHolderF2);
                    boolean zHasAnyOfTheFlags = viewHolderF2.hasAnyOfTheFlags(8192);
                    if (!zHasAnyOfTheFlags) {
                        iA |= 4096;
                    }
                    ItemAnimator.ItemHolderInfo itemHolderInfoRecordPreLayoutInformation = this.f6039x.recordPreLayoutInformation(this.B, viewHolderF2, iA, viewHolderF2.getUnmodifiedPayloads());
                    if (zHasAnyOfTheFlags) {
                        X(viewHolderF2, itemHolderInfoRecordPreLayoutInformation);
                    } else {
                        this.f6020e.a(viewHolderF2, itemHolderInfoRecordPreLayoutInformation);
                    }
                }
            }
            l();
        } else {
            l();
        }
        T();
        i0(false);
        this.B.f6082d = 2;
    }

    private void dispatchLayoutStep2() {
        h0();
        S();
        this.B.a(6);
        this.f6018c.b();
        this.B.f6083e = this.f6025j.getItemCount();
        this.B.f6081c = 0;
        if (this.f6017b != null && this.f6025j.canRestoreState()) {
            Parcelable parcelable = this.f6017b.f6078a;
            if (parcelable != null) {
                this.f6026k.onRestoreInstanceState(parcelable);
            }
            this.f6017b = null;
        }
        State state = this.B;
        state.f6085g = false;
        this.f6026k.onLayoutChildren(this.f6016a, state);
        State state2 = this.B;
        state2.f6084f = false;
        state2.f6088j = state2.f6088j && this.f6039x != null;
        state2.f6082d = 4;
        T();
        i0(false);
    }

    private void dispatchLayoutStep3() {
        this.B.a(4);
        h0();
        S();
        State state = this.B;
        state.f6082d = 1;
        if (state.f6088j) {
            for (int iG = this.f6019d.g() - 1; iG >= 0; iG--) {
                ViewHolder viewHolderF = F(this.f6019d.f(iG));
                if (!viewHolderF.shouldIgnore()) {
                    long jE = E(viewHolderF);
                    ItemAnimator.ItemHolderInfo itemHolderInfoRecordPostLayoutInformation = this.f6039x.recordPostLayoutInformation(this.B, viewHolderF);
                    ViewHolder viewHolderG = this.f6020e.g(jE);
                    if (viewHolderG == null || viewHolderG.shouldIgnore()) {
                        this.f6020e.d(viewHolderF, itemHolderInfoRecordPostLayoutInformation);
                    } else {
                        boolean zH = this.f6020e.h(viewHolderG);
                        boolean zH2 = this.f6020e.h(viewHolderF);
                        if (zH && viewHolderG == viewHolderF) {
                            this.f6020e.d(viewHolderF, itemHolderInfoRecordPostLayoutInformation);
                        } else {
                            ItemAnimator.ItemHolderInfo itemHolderInfoL = this.f6020e.l(viewHolderG);
                            this.f6020e.d(viewHolderF, itemHolderInfoRecordPostLayoutInformation);
                            ItemAnimator.ItemHolderInfo itemHolderInfoK = this.f6020e.k(viewHolderF);
                            if (itemHolderInfoL == null) {
                                handleMissingPreInfoForChangeError(jE, viewHolderF, viewHolderG);
                            } else {
                                animateChange(viewHolderG, viewHolderF, itemHolderInfoL, itemHolderInfoK, zH, zH2);
                            }
                        }
                    }
                }
            }
            this.f6020e.m(this.mViewInfoProcessCallback);
        }
        this.f6026k.i(this.f6016a);
        State state2 = this.B;
        state2.f6080b = state2.f6083e;
        this.f6037v = false;
        this.f6038w = false;
        state2.f6088j = false;
        state2.f6089k = false;
        this.f6026k.f6055f = false;
        ArrayList arrayList = this.f6016a.f6072b;
        if (arrayList != null) {
            arrayList.clear();
        }
        LayoutManager layoutManager = this.f6026k;
        if (layoutManager.f6059j) {
            layoutManager.f6058i = 0;
            layoutManager.f6059j = false;
            this.f6016a.A();
        }
        this.f6026k.onLayoutCompleted(this.B);
        T();
        i0(false);
        this.f6020e.f();
        int[] iArr = this.mMinMaxLayoutPositions;
        if (didChildRangeChange(iArr[0], iArr[1])) {
            t(0, 0);
        }
        recoverFocusFromState();
        resetFocusInfo();
    }

    private boolean dispatchToOnItemTouchListeners(MotionEvent motionEvent) {
        OnItemTouchListener onItemTouchListener = this.mInterceptingOnItemTouchListener;
        if (onItemTouchListener == null) {
            if (motionEvent.getAction() == 0) {
                return false;
            }
            return findInterceptingOnItemTouchListener(motionEvent);
        }
        onItemTouchListener.onTouchEvent(this, motionEvent);
        int action = motionEvent.getAction();
        if (action == 3 || action == 1) {
            this.mInterceptingOnItemTouchListener = null;
        }
        return true;
    }

    private boolean findInterceptingOnItemTouchListener(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        int size = this.mOnItemTouchListeners.size();
        for (int i2 = 0; i2 < size; i2++) {
            OnItemTouchListener onItemTouchListener = this.mOnItemTouchListeners.get(i2);
            if (onItemTouchListener.onInterceptTouchEvent(this, motionEvent) && action != 3) {
                this.mInterceptingOnItemTouchListener = onItemTouchListener;
                return true;
            }
        }
        return false;
    }

    private void findMinMaxChildLayoutPositions(int[] iArr) {
        int iG = this.f6019d.g();
        if (iG == 0) {
            iArr[0] = -1;
            iArr[1] = -1;
            return;
        }
        int i2 = Integer.MAX_VALUE;
        int i3 = Integer.MIN_VALUE;
        for (int i4 = 0; i4 < iG; i4++) {
            ViewHolder viewHolderF = F(this.f6019d.f(i4));
            if (!viewHolderF.shouldIgnore()) {
                int layoutPosition = viewHolderF.getLayoutPosition();
                if (layoutPosition < i2) {
                    i2 = layoutPosition;
                }
                if (layoutPosition > i3) {
                    i3 = layoutPosition;
                }
            }
        }
        iArr[0] = i2;
        iArr[1] = i3;
    }

    @Nullable
    private View findNextViewToFocus() {
        ViewHolder viewHolderFindViewHolderForAdapterPosition;
        State state = this.B;
        int i2 = state.f6090l;
        if (i2 == -1) {
            i2 = 0;
        }
        int itemCount = state.getItemCount();
        for (int i3 = i2; i3 < itemCount; i3++) {
            ViewHolder viewHolderFindViewHolderForAdapterPosition2 = findViewHolderForAdapterPosition(i3);
            if (viewHolderFindViewHolderForAdapterPosition2 == null) {
                break;
            }
            if (viewHolderFindViewHolderForAdapterPosition2.itemView.hasFocusable()) {
                return viewHolderFindViewHolderForAdapterPosition2.itemView;
            }
        }
        int iMin = Math.min(itemCount, i2);
        do {
            iMin--;
            if (iMin < 0 || (viewHolderFindViewHolderForAdapterPosition = findViewHolderForAdapterPosition(iMin)) == null) {
                return null;
            }
        } while (!viewHolderFindViewHolderForAdapterPosition.itemView.hasFocusable());
        return viewHolderFindViewHolderForAdapterPosition.itemView;
    }

    private int getDeepestFocusedViewWithId(View view) {
        int id = view.getId();
        while (!view.isFocused() && (view instanceof ViewGroup) && view.hasFocus()) {
            view = ((ViewGroup) view).getFocusedChild();
            if (view.getId() != -1) {
                id = view.getId();
            }
        }
        return id;
    }

    private String getFullClassName(Context context, String str) {
        if (str.charAt(0) == '.') {
            return context.getPackageName() + str;
        }
        if (str.contains(".")) {
            return str;
        }
        return RecyclerView.class.getPackage().getName() + '.' + str;
    }

    private NestedScrollingChildHelper getScrollingChildHelper() {
        if (this.mScrollingChildHelper == null) {
            this.mScrollingChildHelper = new NestedScrollingChildHelper(this);
        }
        return this.mScrollingChildHelper;
    }

    private void handleMissingPreInfoForChangeError(long j2, ViewHolder viewHolder, ViewHolder viewHolder2) {
        int iG = this.f6019d.g();
        for (int i2 = 0; i2 < iG; i2++) {
            ViewHolder viewHolderF = F(this.f6019d.f(i2));
            if (viewHolderF != viewHolder && E(viewHolderF) == j2) {
                Adapter adapter = this.f6025j;
                if (adapter == null || !adapter.hasStableIds()) {
                    throw new IllegalStateException("Two different ViewHolders have the same change ID. This might happen due to inconsistent Adapter update events or if the LayoutManager lays out the same View multiple times.\n ViewHolder 1:" + viewHolderF + " \n View Holder 2:" + viewHolder + z());
                }
                throw new IllegalStateException("Two different ViewHolders have the same stable ID. Stable IDs in your adapter MUST BE unique and SHOULD NOT change.\n ViewHolder 1:" + viewHolderF + " \n View Holder 2:" + viewHolder + z());
            }
        }
        Log.e("RecyclerView", "Problem while matching changed view holders with the newones. The pre-layout information for the change holder " + viewHolder2 + " cannot be found but it is necessary for " + viewHolder + z());
    }

    private boolean hasUpdatedView() {
        int iG = this.f6019d.g();
        for (int i2 = 0; i2 < iG; i2++) {
            ViewHolder viewHolderF = F(this.f6019d.f(i2));
            if (viewHolderF != null && !viewHolderF.shouldIgnore() && viewHolderF.isUpdated()) {
                return true;
            }
        }
        return false;
    }

    @SuppressLint({"InlinedApi"})
    private void initAutofill() {
        if (ViewCompat.getImportantForAutofill(this) == 0) {
            ViewCompat.setImportantForAutofill(this, 8);
        }
    }

    private void initChildrenHelper() {
        this.f6019d = new ChildHelper(new ChildHelper.Callback() { // from class: androidx.recyclerview.widget.RecyclerView.5
            @Override // androidx.recyclerview.widget.ChildHelper.Callback
            public void addView(View view, int i2) {
                RecyclerView.this.addView(view, i2);
                RecyclerView.this.p(view);
            }

            @Override // androidx.recyclerview.widget.ChildHelper.Callback
            public void attachViewToParent(View view, int i2, ViewGroup.LayoutParams layoutParams) {
                ViewHolder viewHolderF = RecyclerView.F(view);
                if (viewHolderF != null) {
                    if (!viewHolderF.isTmpDetached() && !viewHolderF.shouldIgnore()) {
                        throw new IllegalArgumentException("Called attach on a child which is not detached: " + viewHolderF + RecyclerView.this.z());
                    }
                    viewHolderF.clearTmpDetachFlag();
                }
                RecyclerView.this.attachViewToParent(view, i2, layoutParams);
            }

            @Override // androidx.recyclerview.widget.ChildHelper.Callback
            public void detachViewFromParent(int i2) {
                ViewHolder viewHolderF;
                View childAt = getChildAt(i2);
                if (childAt != null && (viewHolderF = RecyclerView.F(childAt)) != null) {
                    if (viewHolderF.isTmpDetached() && !viewHolderF.shouldIgnore()) {
                        throw new IllegalArgumentException("called detach on an already detached child " + viewHolderF + RecyclerView.this.z());
                    }
                    viewHolderF.addFlags(256);
                }
                RecyclerView.this.detachViewFromParent(i2);
            }

            @Override // androidx.recyclerview.widget.ChildHelper.Callback
            public View getChildAt(int i2) {
                return RecyclerView.this.getChildAt(i2);
            }

            @Override // androidx.recyclerview.widget.ChildHelper.Callback
            public int getChildCount() {
                return RecyclerView.this.getChildCount();
            }

            @Override // androidx.recyclerview.widget.ChildHelper.Callback
            public ViewHolder getChildViewHolder(View view) {
                return RecyclerView.F(view);
            }

            @Override // androidx.recyclerview.widget.ChildHelper.Callback
            public int indexOfChild(View view) {
                return RecyclerView.this.indexOfChild(view);
            }

            @Override // androidx.recyclerview.widget.ChildHelper.Callback
            public void onEnteredHiddenState(View view) {
                ViewHolder viewHolderF = RecyclerView.F(view);
                if (viewHolderF != null) {
                    viewHolderF.onEnteredHiddenState(RecyclerView.this);
                }
            }

            @Override // androidx.recyclerview.widget.ChildHelper.Callback
            public void onLeftHiddenState(View view) {
                ViewHolder viewHolderF = RecyclerView.F(view);
                if (viewHolderF != null) {
                    viewHolderF.onLeftHiddenState(RecyclerView.this);
                }
            }

            @Override // androidx.recyclerview.widget.ChildHelper.Callback
            public void removeAllViews() {
                int childCount = getChildCount();
                for (int i2 = 0; i2 < childCount; i2++) {
                    View childAt = getChildAt(i2);
                    RecyclerView.this.q(childAt);
                    childAt.clearAnimation();
                }
                RecyclerView.this.removeAllViews();
            }

            @Override // androidx.recyclerview.widget.ChildHelper.Callback
            public void removeViewAt(int i2) {
                View childAt = RecyclerView.this.getChildAt(i2);
                if (childAt != null) {
                    RecyclerView.this.q(childAt);
                    childAt.clearAnimation();
                }
                RecyclerView.this.removeViewAt(i2);
            }
        });
    }

    private boolean isPreferredNextFocus(View view, View view2, int i2) {
        int i3;
        if (view2 == null || view2 == this || view2 == view || findContainingItemView(view2) == null) {
            return false;
        }
        if (view == null || findContainingItemView(view) == null) {
            return true;
        }
        this.f6023h.set(0, 0, view.getWidth(), view.getHeight());
        this.mTempRect2.set(0, 0, view2.getWidth(), view2.getHeight());
        offsetDescendantRectToMyCoords(view, this.f6023h);
        offsetDescendantRectToMyCoords(view2, this.mTempRect2);
        char c2 = 65535;
        int i4 = this.f6026k.getLayoutDirection() == 1 ? -1 : 1;
        Rect rect = this.f6023h;
        int i5 = rect.left;
        Rect rect2 = this.mTempRect2;
        int i6 = rect2.left;
        if ((i5 < i6 || rect.right <= i6) && rect.right < rect2.right) {
            i3 = 1;
        } else {
            int i7 = rect.right;
            int i8 = rect2.right;
            i3 = ((i7 > i8 || i5 >= i8) && i5 > i6) ? -1 : 0;
        }
        int i9 = rect.top;
        int i10 = rect2.top;
        if ((i9 < i10 || rect.bottom <= i10) && rect.bottom < rect2.bottom) {
            c2 = 1;
        } else {
            int i11 = rect.bottom;
            int i12 = rect2.bottom;
            if ((i11 <= i12 && i9 < i12) || i9 <= i10) {
                c2 = 0;
            }
        }
        if (i2 == 1) {
            return c2 < 0 || (c2 == 0 && i3 * i4 < 0);
        }
        if (i2 == 2) {
            return c2 > 0 || (c2 == 0 && i3 * i4 > 0);
        }
        if (i2 == 17) {
            return i3 < 0;
        }
        if (i2 == 33) {
            return c2 < 0;
        }
        if (i2 == 66) {
            return i3 > 0;
        }
        if (i2 == 130) {
            return c2 > 0;
        }
        throw new IllegalArgumentException("Invalid direction: " + i2 + z());
    }

    static void k(ViewHolder viewHolder) {
        WeakReference<RecyclerView> weakReference = viewHolder.mNestedRecyclerView;
        if (weakReference != null) {
            RecyclerView recyclerView = weakReference.get();
            while (recyclerView != null) {
                if (recyclerView == viewHolder.itemView) {
                    return;
                }
                Object parent = recyclerView.getParent();
                recyclerView = parent instanceof View ? (View) parent : null;
            }
            viewHolder.mNestedRecyclerView = null;
        }
    }

    private void nestedScrollByInternal(int i2, int i3, @Nullable MotionEvent motionEvent, int i4) {
        LayoutManager layoutManager = this.f6026k;
        if (layoutManager == null) {
            Log.e("RecyclerView", "Cannot scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
            return;
        }
        if (this.f6035t) {
            return;
        }
        int[] iArr = this.G;
        iArr[0] = 0;
        iArr[1] = 0;
        boolean zCanScrollHorizontally = layoutManager.canScrollHorizontally();
        boolean zCanScrollVertically = this.f6026k.canScrollVertically();
        startNestedScroll(zCanScrollVertically ? (zCanScrollHorizontally ? 1 : 0) | 2 : zCanScrollHorizontally ? 1 : 0, i4);
        if (dispatchNestedPreScroll(zCanScrollHorizontally ? i2 : 0, zCanScrollVertically ? i3 : 0, this.G, this.mScrollOffset, i4)) {
            int[] iArr2 = this.G;
            i2 -= iArr2[0];
            i3 -= iArr2[1];
        }
        c0(zCanScrollHorizontally ? i2 : 0, zCanScrollVertically ? i3 : 0, motionEvent, i4);
        GapWorker gapWorker = this.f6041z;
        if (gapWorker != null && (i2 != 0 || i3 != 0)) {
            gapWorker.b(this, i2, i3);
        }
        stopNestedScroll(i4);
    }

    private void onPointerUp(MotionEvent motionEvent) {
        int actionIndex = motionEvent.getActionIndex();
        if (motionEvent.getPointerId(actionIndex) == this.mScrollPointerId) {
            int i2 = actionIndex == 0 ? 1 : 0;
            this.mScrollPointerId = motionEvent.getPointerId(i2);
            int x2 = (int) (motionEvent.getX(i2) + 0.5f);
            this.mLastTouchX = x2;
            this.mInitialTouchX = x2;
            int y2 = (int) (motionEvent.getY(i2) + 0.5f);
            this.mLastTouchY = y2;
            this.mInitialTouchY = y2;
        }
    }

    private boolean predictiveItemAnimationsEnabled() {
        return this.f6039x != null && this.f6026k.supportsPredictiveItemAnimations();
    }

    private void processAdapterUpdatesAndSetAnimationFlags() {
        boolean z2;
        if (this.f6037v) {
            this.f6018c.o();
            if (this.f6038w) {
                this.f6026k.onItemsChanged(this);
            }
        }
        if (predictiveItemAnimationsEnabled()) {
            this.f6018c.m();
        } else {
            this.f6018c.b();
        }
        boolean z3 = this.C || this.D;
        this.B.f6088j = this.f6033r && this.f6039x != null && ((z2 = this.f6037v) || z3 || this.f6026k.f6055f) && (!z2 || this.f6025j.hasStableIds());
        State state = this.B;
        state.f6089k = state.f6088j && z3 && !this.f6037v && predictiveItemAnimationsEnabled();
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0040  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0056  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void pullGlows(float r7, float r8, float r9, float r10) {
        /*
            r6 = this;
            r0 = 0
            int r1 = (r8 > r0 ? 1 : (r8 == r0 ? 0 : -1))
            r2 = 1065353216(0x3f800000, float:1.0)
            r3 = 1
            if (r1 >= 0) goto L21
            r6.w()
            android.widget.EdgeEffect r1 = r6.mLeftGlow
            float r4 = -r8
            int r5 = r6.getWidth()
            float r5 = (float) r5
            float r4 = r4 / r5
            int r5 = r6.getHeight()
            float r5 = (float) r5
            float r9 = r9 / r5
            float r9 = r2 - r9
            androidx.core.widget.EdgeEffectCompat.onPull(r1, r4, r9)
        L1f:
            r9 = r3
            goto L3c
        L21:
            int r1 = (r8 > r0 ? 1 : (r8 == r0 ? 0 : -1))
            if (r1 <= 0) goto L3b
            r6.x()
            android.widget.EdgeEffect r1 = r6.mRightGlow
            int r4 = r6.getWidth()
            float r4 = (float) r4
            float r4 = r8 / r4
            int r5 = r6.getHeight()
            float r5 = (float) r5
            float r9 = r9 / r5
            androidx.core.widget.EdgeEffectCompat.onPull(r1, r4, r9)
            goto L1f
        L3b:
            r9 = 0
        L3c:
            int r1 = (r10 > r0 ? 1 : (r10 == r0 ? 0 : -1))
            if (r1 >= 0) goto L56
            r6.y()
            android.widget.EdgeEffect r9 = r6.mTopGlow
            float r1 = -r10
            int r2 = r6.getHeight()
            float r2 = (float) r2
            float r1 = r1 / r2
            int r2 = r6.getWidth()
            float r2 = (float) r2
            float r7 = r7 / r2
            androidx.core.widget.EdgeEffectCompat.onPull(r9, r1, r7)
            goto L72
        L56:
            int r1 = (r10 > r0 ? 1 : (r10 == r0 ? 0 : -1))
            if (r1 <= 0) goto L71
            r6.v()
            android.widget.EdgeEffect r9 = r6.mBottomGlow
            int r1 = r6.getHeight()
            float r1 = (float) r1
            float r1 = r10 / r1
            int r4 = r6.getWidth()
            float r4 = (float) r4
            float r7 = r7 / r4
            float r2 = r2 - r7
            androidx.core.widget.EdgeEffectCompat.onPull(r9, r1, r2)
            goto L72
        L71:
            r3 = r9
        L72:
            if (r3 != 0) goto L7c
            int r7 = (r8 > r0 ? 1 : (r8 == r0 ? 0 : -1))
            if (r7 != 0) goto L7c
            int r7 = (r10 > r0 ? 1 : (r10 == r0 ? 0 : -1))
            if (r7 == 0) goto L7f
        L7c:
            androidx.core.view.ViewCompat.postInvalidateOnAnimation(r6)
        L7f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.RecyclerView.pullGlows(float, float, float, float):void");
    }

    private void recoverFocusFromState() {
        View viewFindViewById;
        if (!this.mPreserveFocusAfterLayout || this.f6025j == null || !hasFocus() || getDescendantFocusability() == 393216) {
            return;
        }
        if (getDescendantFocusability() == 131072 && isFocused()) {
            return;
        }
        if (!isFocused()) {
            View focusedChild = getFocusedChild();
            if (!IGNORE_DETACHED_FOCUSED_CHILD || (focusedChild.getParent() != null && focusedChild.hasFocus())) {
                if (!this.f6019d.l(focusedChild)) {
                    return;
                }
            } else if (this.f6019d.g() == 0) {
                requestFocus();
                return;
            }
        }
        View viewFindNextViewToFocus = null;
        ViewHolder viewHolderFindViewHolderForItemId = (this.B.f6091m == -1 || !this.f6025j.hasStableIds()) ? null : findViewHolderForItemId(this.B.f6091m);
        if (viewHolderFindViewHolderForItemId != null && !this.f6019d.l(viewHolderFindViewHolderForItemId.itemView) && viewHolderFindViewHolderForItemId.itemView.hasFocusable()) {
            viewFindNextViewToFocus = viewHolderFindViewHolderForItemId.itemView;
        } else if (this.f6019d.g() > 0) {
            viewFindNextViewToFocus = findNextViewToFocus();
        }
        if (viewFindNextViewToFocus != null) {
            int i2 = this.B.f6092n;
            if (i2 != -1 && (viewFindViewById = viewFindNextViewToFocus.findViewById(i2)) != null && viewFindViewById.isFocusable()) {
                viewFindNextViewToFocus = viewFindViewById;
            }
            viewFindNextViewToFocus.requestFocus();
        }
    }

    private void releaseGlows() {
        boolean zIsFinished;
        EdgeEffect edgeEffect = this.mLeftGlow;
        if (edgeEffect != null) {
            edgeEffect.onRelease();
            zIsFinished = this.mLeftGlow.isFinished();
        } else {
            zIsFinished = false;
        }
        EdgeEffect edgeEffect2 = this.mTopGlow;
        if (edgeEffect2 != null) {
            edgeEffect2.onRelease();
            zIsFinished |= this.mTopGlow.isFinished();
        }
        EdgeEffect edgeEffect3 = this.mRightGlow;
        if (edgeEffect3 != null) {
            edgeEffect3.onRelease();
            zIsFinished |= this.mRightGlow.isFinished();
        }
        EdgeEffect edgeEffect4 = this.mBottomGlow;
        if (edgeEffect4 != null) {
            edgeEffect4.onRelease();
            zIsFinished |= this.mBottomGlow.isFinished();
        }
        if (zIsFinished) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    private void requestChildOnScreen(@NonNull View view, @Nullable View view2) {
        View view3 = view2 != null ? view2 : view;
        this.f6023h.set(0, 0, view3.getWidth(), view3.getHeight());
        ViewGroup.LayoutParams layoutParams = view3.getLayoutParams();
        if (layoutParams instanceof LayoutParams) {
            LayoutParams layoutParams2 = (LayoutParams) layoutParams;
            if (!layoutParams2.f6064c) {
                Rect rect = layoutParams2.f6063b;
                Rect rect2 = this.f6023h;
                rect2.left -= rect.left;
                rect2.right += rect.right;
                rect2.top -= rect.top;
                rect2.bottom += rect.bottom;
            }
        }
        if (view2 != null) {
            offsetDescendantRectToMyCoords(view2, this.f6023h);
            offsetRectIntoDescendantCoords(view, this.f6023h);
        }
        this.f6026k.requestChildRectangleOnScreen(this, view, this.f6023h, !this.f6033r, view2 == null);
    }

    private void resetFocusInfo() {
        State state = this.B;
        state.f6091m = -1L;
        state.f6090l = -1;
        state.f6092n = -1;
    }

    private void resetScroll() {
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker != null) {
            velocityTracker.clear();
        }
        stopNestedScroll(0);
        releaseGlows();
    }

    private void saveFocusInfo() {
        View focusedChild = (this.mPreserveFocusAfterLayout && hasFocus() && this.f6025j != null) ? getFocusedChild() : null;
        ViewHolder viewHolderFindContainingViewHolder = focusedChild != null ? findContainingViewHolder(focusedChild) : null;
        if (viewHolderFindContainingViewHolder == null) {
            resetFocusInfo();
            return;
        }
        this.B.f6091m = this.f6025j.hasStableIds() ? viewHolderFindContainingViewHolder.getItemId() : -1L;
        this.B.f6090l = this.f6037v ? -1 : viewHolderFindContainingViewHolder.isRemoved() ? viewHolderFindContainingViewHolder.mOldPosition : viewHolderFindContainingViewHolder.getAbsoluteAdapterPosition();
        this.B.f6092n = getDeepestFocusedViewWithId(viewHolderFindContainingViewHolder.itemView);
    }

    private void setAdapterInternal(@Nullable Adapter adapter, boolean z2, boolean z3) {
        Adapter adapter2 = this.f6025j;
        if (adapter2 != null) {
            adapter2.unregisterAdapterDataObserver(this.mObserver);
            this.f6025j.onDetachedFromRecyclerView(this);
        }
        if (!z2 || z3) {
            Y();
        }
        this.f6018c.o();
        Adapter adapter3 = this.f6025j;
        this.f6025j = adapter;
        if (adapter != null) {
            adapter.registerAdapterDataObserver(this.mObserver);
            adapter.onAttachedToRecyclerView(this);
        }
        LayoutManager layoutManager = this.f6026k;
        if (layoutManager != null) {
            layoutManager.onAdapterChanged(adapter3, this.f6025j);
        }
        this.f6016a.q(adapter3, this.f6025j, z2);
        this.B.f6084f = true;
    }

    private void stopScrollersInternal() {
        this.f6040y.stop();
        LayoutManager layoutManager = this.f6026k;
        if (layoutManager != null) {
            layoutManager.q();
        }
    }

    final void A(State state) {
        if (getScrollState() != 2) {
            state.f6093o = 0;
            state.f6094p = 0;
        } else {
            OverScroller overScroller = this.f6040y.f6095a;
            state.f6093o = overScroller.getFinalX() - overScroller.getCurrX();
            state.f6094p = overScroller.getFinalY() - overScroller.getCurrY();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    androidx.recyclerview.widget.RecyclerView.ViewHolder C(int r6, boolean r7) {
        /*
            r5 = this;
            androidx.recyclerview.widget.ChildHelper r0 = r5.f6019d
            int r0 = r0.i()
            r1 = 0
            r2 = 0
        L8:
            if (r2 >= r0) goto L3a
            androidx.recyclerview.widget.ChildHelper r3 = r5.f6019d
            android.view.View r3 = r3.h(r2)
            androidx.recyclerview.widget.RecyclerView$ViewHolder r3 = F(r3)
            if (r3 == 0) goto L37
            boolean r4 = r3.isRemoved()
            if (r4 != 0) goto L37
            if (r7 == 0) goto L23
            int r4 = r3.mPosition
            if (r4 == r6) goto L2a
            goto L37
        L23:
            int r4 = r3.getLayoutPosition()
            if (r4 == r6) goto L2a
            goto L37
        L2a:
            androidx.recyclerview.widget.ChildHelper r1 = r5.f6019d
            android.view.View r4 = r3.itemView
            boolean r1 = r1.l(r4)
            if (r1 == 0) goto L36
            r1 = r3
            goto L37
        L36:
            return r3
        L37:
            int r2 = r2 + 1
            goto L8
        L3a:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.RecyclerView.C(int, boolean):androidx.recyclerview.widget.RecyclerView$ViewHolder");
    }

    int D(ViewHolder viewHolder) {
        if (viewHolder.hasAnyOfTheFlags(524) || !viewHolder.isBound()) {
            return -1;
        }
        return this.f6018c.applyPendingUpdatesToPosition(viewHolder.mPosition);
    }

    long E(ViewHolder viewHolder) {
        return this.f6025j.hasStableIds() ? viewHolder.getItemId() : viewHolder.mPosition;
    }

    Rect H(View view) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        if (!layoutParams.f6064c) {
            return layoutParams.f6063b;
        }
        if (this.B.isPreLayout() && (layoutParams.isItemChanged() || layoutParams.isViewInvalid())) {
            return layoutParams.f6063b;
        }
        Rect rect = layoutParams.f6063b;
        rect.set(0, 0, 0, 0);
        int size = this.f6029n.size();
        for (int i2 = 0; i2 < size; i2++) {
            this.f6023h.set(0, 0, 0, 0);
            ((ItemDecoration) this.f6029n.get(i2)).getItemOffsets(this.f6023h, view, this, this.B);
            int i3 = rect.left;
            Rect rect2 = this.f6023h;
            rect.left = i3 + rect2.left;
            rect.top += rect2.top;
            rect.right += rect2.right;
            rect.bottom += rect2.bottom;
        }
        layoutParams.f6064c = false;
        return rect;
    }

    void I() {
        this.f6018c = new AdapterHelper(new AdapterHelper.Callback() { // from class: androidx.recyclerview.widget.RecyclerView.6
            void a(AdapterHelper.UpdateOp updateOp) {
                int i2 = updateOp.f5790a;
                if (i2 == 1) {
                    RecyclerView recyclerView = RecyclerView.this;
                    recyclerView.f6026k.onItemsAdded(recyclerView, updateOp.f5791b, updateOp.f5793d);
                    return;
                }
                if (i2 == 2) {
                    RecyclerView recyclerView2 = RecyclerView.this;
                    recyclerView2.f6026k.onItemsRemoved(recyclerView2, updateOp.f5791b, updateOp.f5793d);
                } else if (i2 == 4) {
                    RecyclerView recyclerView3 = RecyclerView.this;
                    recyclerView3.f6026k.onItemsUpdated(recyclerView3, updateOp.f5791b, updateOp.f5793d, updateOp.f5792c);
                } else {
                    if (i2 != 8) {
                        return;
                    }
                    RecyclerView recyclerView4 = RecyclerView.this;
                    recyclerView4.f6026k.onItemsMoved(recyclerView4, updateOp.f5791b, updateOp.f5793d, 1);
                }
            }

            @Override // androidx.recyclerview.widget.AdapterHelper.Callback
            public ViewHolder findViewHolder(int i2) {
                ViewHolder viewHolderC = RecyclerView.this.C(i2, true);
                if (viewHolderC == null || RecyclerView.this.f6019d.l(viewHolderC.itemView)) {
                    return null;
                }
                return viewHolderC;
            }

            @Override // androidx.recyclerview.widget.AdapterHelper.Callback
            public void markViewHoldersUpdated(int i2, int i3, Object obj) {
                RecyclerView.this.j0(i2, i3, obj);
                RecyclerView.this.D = true;
            }

            @Override // androidx.recyclerview.widget.AdapterHelper.Callback
            public void offsetPositionsForAdd(int i2, int i3) {
                RecyclerView.this.P(i2, i3);
                RecyclerView.this.C = true;
            }

            @Override // androidx.recyclerview.widget.AdapterHelper.Callback
            public void offsetPositionsForMove(int i2, int i3) {
                RecyclerView.this.Q(i2, i3);
                RecyclerView.this.C = true;
            }

            @Override // androidx.recyclerview.widget.AdapterHelper.Callback
            public void offsetPositionsForRemovingInvisible(int i2, int i3) {
                RecyclerView.this.R(i2, i3, true);
                RecyclerView recyclerView = RecyclerView.this;
                recyclerView.C = true;
                recyclerView.B.f6081c += i3;
            }

            @Override // androidx.recyclerview.widget.AdapterHelper.Callback
            public void offsetPositionsForRemovingLaidOutOrNewView(int i2, int i3) {
                RecyclerView.this.R(i2, i3, false);
                RecyclerView.this.C = true;
            }

            @Override // androidx.recyclerview.widget.AdapterHelper.Callback
            public void onDispatchFirstPass(AdapterHelper.UpdateOp updateOp) {
                a(updateOp);
            }

            @Override // androidx.recyclerview.widget.AdapterHelper.Callback
            public void onDispatchSecondPass(AdapterHelper.UpdateOp updateOp) {
                a(updateOp);
            }
        });
    }

    void J(StateListDrawable stateListDrawable, Drawable drawable, StateListDrawable stateListDrawable2, Drawable drawable2) {
        if (stateListDrawable != null && drawable != null && stateListDrawable2 != null && drawable2 != null) {
            Resources resources = getContext().getResources();
            new FastScroller(this, stateListDrawable, drawable, stateListDrawable2, drawable2, resources.getDimensionPixelSize(androidx.recyclerview.R.dimen.fastscroll_default_thickness), resources.getDimensionPixelSize(androidx.recyclerview.R.dimen.fastscroll_minimum_range), resources.getDimensionPixelOffset(androidx.recyclerview.R.dimen.fastscroll_margin));
        } else {
            throw new IllegalArgumentException("Trying to set fast scroller without both required drawables." + z());
        }
    }

    void K() {
        this.mBottomGlow = null;
        this.mTopGlow = null;
        this.mRightGlow = null;
        this.mLeftGlow = null;
    }

    boolean L() {
        AccessibilityManager accessibilityManager = this.mAccessibilityManager;
        return accessibilityManager != null && accessibilityManager.isEnabled();
    }

    void M(int i2) {
        if (this.f6026k == null) {
            return;
        }
        setScrollState(2);
        this.f6026k.scrollToPosition(i2);
        awakenScrollBars();
    }

    void N() {
        int i2 = this.f6019d.i();
        for (int i3 = 0; i3 < i2; i3++) {
            ((LayoutParams) this.f6019d.h(i3).getLayoutParams()).f6064c = true;
        }
        this.f6016a.l();
    }

    void O() {
        int i2 = this.f6019d.i();
        for (int i3 = 0; i3 < i2; i3++) {
            ViewHolder viewHolderF = F(this.f6019d.h(i3));
            if (viewHolderF != null && !viewHolderF.shouldIgnore()) {
                viewHolderF.addFlags(6);
            }
        }
        N();
        this.f6016a.m();
    }

    void P(int i2, int i3) {
        int i4 = this.f6019d.i();
        for (int i5 = 0; i5 < i4; i5++) {
            ViewHolder viewHolderF = F(this.f6019d.h(i5));
            if (viewHolderF != null && !viewHolderF.shouldIgnore() && viewHolderF.mPosition >= i2) {
                viewHolderF.offsetPosition(i3, false);
                this.B.f6084f = true;
            }
        }
        this.f6016a.n(i2, i3);
        requestLayout();
    }

    void Q(int i2, int i3) {
        int i4;
        int i5;
        int i6;
        int i7;
        int i8 = this.f6019d.i();
        if (i2 < i3) {
            i6 = -1;
            i5 = i2;
            i4 = i3;
        } else {
            i4 = i2;
            i5 = i3;
            i6 = 1;
        }
        for (int i9 = 0; i9 < i8; i9++) {
            ViewHolder viewHolderF = F(this.f6019d.h(i9));
            if (viewHolderF != null && (i7 = viewHolderF.mPosition) >= i5 && i7 <= i4) {
                if (i7 == i2) {
                    viewHolderF.offsetPosition(i3 - i2, false);
                } else {
                    viewHolderF.offsetPosition(i6, false);
                }
                this.B.f6084f = true;
            }
        }
        this.f6016a.o(i2, i3);
        requestLayout();
    }

    void R(int i2, int i3, boolean z2) {
        int i4 = i2 + i3;
        int i5 = this.f6019d.i();
        for (int i6 = 0; i6 < i5; i6++) {
            ViewHolder viewHolderF = F(this.f6019d.h(i6));
            if (viewHolderF != null && !viewHolderF.shouldIgnore()) {
                int i7 = viewHolderF.mPosition;
                if (i7 >= i4) {
                    viewHolderF.offsetPosition(-i3, z2);
                    this.B.f6084f = true;
                } else if (i7 >= i2) {
                    viewHolderF.flagRemovedAndOffsetPosition(i2 - 1, -i3, z2);
                    this.B.f6084f = true;
                }
            }
        }
        this.f6016a.p(i2, i3, z2);
        requestLayout();
    }

    void S() {
        this.mLayoutOrScrollCounter++;
    }

    void T() {
        U(true);
    }

    void U(boolean z2) {
        int i2 = this.mLayoutOrScrollCounter - 1;
        this.mLayoutOrScrollCounter = i2;
        if (i2 < 1) {
            this.mLayoutOrScrollCounter = 0;
            if (z2) {
                dispatchContentChangedIfNecessary();
                u();
            }
        }
    }

    void V() {
        if (this.E || !this.f6030o) {
            return;
        }
        ViewCompat.postOnAnimation(this, this.mItemAnimatorRunner);
        this.E = true;
    }

    void W(boolean z2) {
        this.f6038w = z2 | this.f6038w;
        this.f6037v = true;
        O();
    }

    void X(ViewHolder viewHolder, ItemAnimator.ItemHolderInfo itemHolderInfo) {
        viewHolder.setFlags(0, 8192);
        if (this.B.f6086h && viewHolder.isUpdated() && !viewHolder.isRemoved() && !viewHolder.shouldIgnore()) {
            this.f6020e.c(E(viewHolder), viewHolder);
        }
        this.f6020e.e(viewHolder, itemHolderInfo);
    }

    void Y() {
        ItemAnimator itemAnimator = this.f6039x;
        if (itemAnimator != null) {
            itemAnimator.endAnimations();
        }
        LayoutManager layoutManager = this.f6026k;
        if (layoutManager != null) {
            layoutManager.removeAndRecycleAllViews(this.f6016a);
            this.f6026k.i(this.f6016a);
        }
        this.f6016a.clear();
    }

    boolean Z(View view) {
        h0();
        boolean zP = this.f6019d.p(view);
        if (zP) {
            ViewHolder viewHolderF = F(view);
            this.f6016a.z(viewHolderF);
            this.f6016a.u(viewHolderF);
        }
        i0(!zP);
        return zP;
    }

    void a(int i2, int i3) {
        if (i2 < 0) {
            w();
            if (this.mLeftGlow.isFinished()) {
                this.mLeftGlow.onAbsorb(-i2);
            }
        } else if (i2 > 0) {
            x();
            if (this.mRightGlow.isFinished()) {
                this.mRightGlow.onAbsorb(i2);
            }
        }
        if (i3 < 0) {
            y();
            if (this.mTopGlow.isFinished()) {
                this.mTopGlow.onAbsorb(-i3);
            }
        } else if (i3 > 0) {
            v();
            if (this.mBottomGlow.isFinished()) {
                this.mBottomGlow.onAbsorb(i3);
            }
        }
        if (i2 == 0 && i3 == 0) {
            return;
        }
        ViewCompat.postInvalidateOnAnimation(this);
    }

    void a0() {
        ViewHolder viewHolder;
        int iG = this.f6019d.g();
        for (int i2 = 0; i2 < iG; i2++) {
            View viewF = this.f6019d.f(i2);
            ViewHolder childViewHolder = getChildViewHolder(viewF);
            if (childViewHolder != null && (viewHolder = childViewHolder.mShadowingHolder) != null) {
                View view = viewHolder.itemView;
                int left = viewF.getLeft();
                int top2 = viewF.getTop();
                if (left != view.getLeft() || top2 != view.getTop()) {
                    view.layout(left, top2, view.getWidth() + left, view.getHeight() + top2);
                }
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void addFocusables(ArrayList<View> arrayList, int i2, int i3) {
        LayoutManager layoutManager = this.f6026k;
        if (layoutManager == null || !layoutManager.onAddFocusables(this, arrayList, i2, i3)) {
            super.addFocusables(arrayList, i2, i3);
        }
    }

    public void addItemDecoration(@NonNull ItemDecoration itemDecoration, int i2) {
        LayoutManager layoutManager = this.f6026k;
        if (layoutManager != null) {
            layoutManager.assertNotInLayoutOrScroll("Cannot add item decoration during a scroll  or layout");
        }
        if (this.f6029n.isEmpty()) {
            setWillNotDraw(false);
        }
        if (i2 < 0) {
            this.f6029n.add(itemDecoration);
        } else {
            this.f6029n.add(i2, itemDecoration);
        }
        N();
        requestLayout();
    }

    public void addOnChildAttachStateChangeListener(@NonNull OnChildAttachStateChangeListener onChildAttachStateChangeListener) {
        if (this.mOnChildAttachStateListeners == null) {
            this.mOnChildAttachStateListeners = new ArrayList();
        }
        this.mOnChildAttachStateListeners.add(onChildAttachStateChangeListener);
    }

    public void addOnItemTouchListener(@NonNull OnItemTouchListener onItemTouchListener) {
        this.mOnItemTouchListeners.add(onItemTouchListener);
    }

    public void addOnScrollListener(@NonNull OnScrollListener onScrollListener) {
        if (this.mScrollListeners == null) {
            this.mScrollListeners = new ArrayList();
        }
        this.mScrollListeners.add(onScrollListener);
    }

    public void addRecyclerListener(@NonNull RecyclerListener recyclerListener) {
        Preconditions.checkArgument(recyclerListener != null, "'listener' arg cannot be null.");
        this.f6028m.add(recyclerListener);
    }

    void b0() {
        int i2 = this.f6019d.i();
        for (int i3 = 0; i3 < i2; i3++) {
            ViewHolder viewHolderF = F(this.f6019d.h(i3));
            if (!viewHolderF.shouldIgnore()) {
                viewHolderF.saveOldPosition();
            }
        }
    }

    boolean c0(int i2, int i3, MotionEvent motionEvent, int i4) {
        int i5;
        int i6;
        int i7;
        int i8;
        n();
        if (this.f6025j != null) {
            int[] iArr = this.G;
            iArr[0] = 0;
            iArr[1] = 0;
            d0(i2, i3, iArr);
            int[] iArr2 = this.G;
            int i9 = iArr2[0];
            int i10 = iArr2[1];
            i5 = i10;
            i6 = i9;
            i7 = i2 - i9;
            i8 = i3 - i10;
        } else {
            i5 = 0;
            i6 = 0;
            i7 = 0;
            i8 = 0;
        }
        if (!this.f6029n.isEmpty()) {
            invalidate();
        }
        int[] iArr3 = this.G;
        iArr3[0] = 0;
        iArr3[1] = 0;
        dispatchNestedScroll(i6, i5, i7, i8, this.mScrollOffset, i4, iArr3);
        int[] iArr4 = this.G;
        int i11 = iArr4[0];
        int i12 = i7 - i11;
        int i13 = iArr4[1];
        int i14 = i8 - i13;
        boolean z2 = (i11 == 0 && i13 == 0) ? false : true;
        int i15 = this.mLastTouchX;
        int[] iArr5 = this.mScrollOffset;
        int i16 = iArr5[0];
        this.mLastTouchX = i15 - i16;
        int i17 = this.mLastTouchY;
        int i18 = iArr5[1];
        this.mLastTouchY = i17 - i18;
        int[] iArr6 = this.mNestedOffsets;
        iArr6[0] = iArr6[0] + i16;
        iArr6[1] = iArr6[1] + i18;
        if (getOverScrollMode() != 2) {
            if (motionEvent != null && !MotionEventCompat.isFromSource(motionEvent, 8194)) {
                pullGlows(motionEvent.getX(), i12, motionEvent.getY(), i14);
            }
            m(i2, i3);
        }
        if (i6 != 0 || i5 != 0) {
            t(i6, i5);
        }
        if (!awakenScrollBars()) {
            invalidate();
        }
        return (!z2 && i6 == 0 && i5 == 0) ? false : true;
    }

    @Override // android.view.ViewGroup
    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return (layoutParams instanceof LayoutParams) && this.f6026k.checkLayoutParams((LayoutParams) layoutParams);
    }

    public void clearOnChildAttachStateChangeListeners() {
        List<OnChildAttachStateChangeListener> list = this.mOnChildAttachStateListeners;
        if (list != null) {
            list.clear();
        }
    }

    public void clearOnScrollListeners() {
        List<OnScrollListener> list = this.mScrollListeners;
        if (list != null) {
            list.clear();
        }
    }

    @Override // android.view.View, androidx.core.view.ScrollingView
    public int computeHorizontalScrollExtent() {
        LayoutManager layoutManager = this.f6026k;
        if (layoutManager != null && layoutManager.canScrollHorizontally()) {
            return this.f6026k.computeHorizontalScrollExtent(this.B);
        }
        return 0;
    }

    @Override // android.view.View, androidx.core.view.ScrollingView
    public int computeHorizontalScrollOffset() {
        LayoutManager layoutManager = this.f6026k;
        if (layoutManager != null && layoutManager.canScrollHorizontally()) {
            return this.f6026k.computeHorizontalScrollOffset(this.B);
        }
        return 0;
    }

    @Override // android.view.View, androidx.core.view.ScrollingView
    public int computeHorizontalScrollRange() {
        LayoutManager layoutManager = this.f6026k;
        if (layoutManager != null && layoutManager.canScrollHorizontally()) {
            return this.f6026k.computeHorizontalScrollRange(this.B);
        }
        return 0;
    }

    @Override // android.view.View, androidx.core.view.ScrollingView
    public int computeVerticalScrollExtent() {
        LayoutManager layoutManager = this.f6026k;
        if (layoutManager != null && layoutManager.canScrollVertically()) {
            return this.f6026k.computeVerticalScrollExtent(this.B);
        }
        return 0;
    }

    @Override // android.view.View, androidx.core.view.ScrollingView
    public int computeVerticalScrollOffset() {
        LayoutManager layoutManager = this.f6026k;
        if (layoutManager != null && layoutManager.canScrollVertically()) {
            return this.f6026k.computeVerticalScrollOffset(this.B);
        }
        return 0;
    }

    @Override // android.view.View, androidx.core.view.ScrollingView
    public int computeVerticalScrollRange() {
        LayoutManager layoutManager = this.f6026k;
        if (layoutManager != null && layoutManager.canScrollVertically()) {
            return this.f6026k.computeVerticalScrollRange(this.B);
        }
        return 0;
    }

    void d0(int i2, int i3, int[] iArr) {
        h0();
        S();
        TraceCompat.beginSection("RV Scroll");
        A(this.B);
        int iScrollHorizontallyBy = i2 != 0 ? this.f6026k.scrollHorizontallyBy(i2, this.f6016a, this.B) : 0;
        int iScrollVerticallyBy = i3 != 0 ? this.f6026k.scrollVerticallyBy(i3, this.f6016a, this.B) : 0;
        TraceCompat.endSection();
        a0();
        T();
        i0(false);
        if (iArr != null) {
            iArr[0] = iScrollHorizontallyBy;
            iArr[1] = iScrollVerticallyBy;
        }
    }

    @Override // android.view.View, androidx.core.view.NestedScrollingChild
    public boolean dispatchNestedFling(float f2, float f3, boolean z2) {
        return getScrollingChildHelper().dispatchNestedFling(f2, f3, z2);
    }

    @Override // android.view.View, androidx.core.view.NestedScrollingChild
    public boolean dispatchNestedPreFling(float f2, float f3) {
        return getScrollingChildHelper().dispatchNestedPreFling(f2, f3);
    }

    @Override // android.view.View, androidx.core.view.NestedScrollingChild
    public boolean dispatchNestedPreScroll(int i2, int i3, int[] iArr, int[] iArr2) {
        return getScrollingChildHelper().dispatchNestedPreScroll(i2, i3, iArr, iArr2);
    }

    @Override // android.view.View, androidx.core.view.NestedScrollingChild
    public boolean dispatchNestedScroll(int i2, int i3, int i4, int i5, int[] iArr) {
        return getScrollingChildHelper().dispatchNestedScroll(i2, i3, i4, i5, iArr);
    }

    @Override // android.view.View
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        onPopulateAccessibilityEvent(accessibilityEvent);
        return true;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchRestoreInstanceState(SparseArray sparseArray) {
        dispatchThawSelfOnly(sparseArray);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchSaveInstanceState(SparseArray sparseArray) {
        dispatchFreezeSelfOnly(sparseArray);
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        boolean z2;
        super.draw(canvas);
        int size = this.f6029n.size();
        boolean z3 = false;
        for (int i2 = 0; i2 < size; i2++) {
            ((ItemDecoration) this.f6029n.get(i2)).onDrawOver(canvas, this, this.B);
        }
        EdgeEffect edgeEffect = this.mLeftGlow;
        if (edgeEffect == null || edgeEffect.isFinished()) {
            z2 = false;
        } else {
            int iSave = canvas.save();
            int paddingBottom = this.f6021f ? getPaddingBottom() : 0;
            canvas.rotate(270.0f);
            canvas.translate((-getHeight()) + paddingBottom, 0.0f);
            EdgeEffect edgeEffect2 = this.mLeftGlow;
            z2 = edgeEffect2 != null && edgeEffect2.draw(canvas);
            canvas.restoreToCount(iSave);
        }
        EdgeEffect edgeEffect3 = this.mTopGlow;
        if (edgeEffect3 != null && !edgeEffect3.isFinished()) {
            int iSave2 = canvas.save();
            if (this.f6021f) {
                canvas.translate(getPaddingLeft(), getPaddingTop());
            }
            EdgeEffect edgeEffect4 = this.mTopGlow;
            z2 |= edgeEffect4 != null && edgeEffect4.draw(canvas);
            canvas.restoreToCount(iSave2);
        }
        EdgeEffect edgeEffect5 = this.mRightGlow;
        if (edgeEffect5 != null && !edgeEffect5.isFinished()) {
            int iSave3 = canvas.save();
            int width = getWidth();
            int paddingTop = this.f6021f ? getPaddingTop() : 0;
            canvas.rotate(90.0f);
            canvas.translate(paddingTop, -width);
            EdgeEffect edgeEffect6 = this.mRightGlow;
            z2 |= edgeEffect6 != null && edgeEffect6.draw(canvas);
            canvas.restoreToCount(iSave3);
        }
        EdgeEffect edgeEffect7 = this.mBottomGlow;
        if (edgeEffect7 != null && !edgeEffect7.isFinished()) {
            int iSave4 = canvas.save();
            canvas.rotate(180.0f);
            if (this.f6021f) {
                canvas.translate((-getWidth()) + getPaddingRight(), (-getHeight()) + getPaddingBottom());
            } else {
                canvas.translate(-getWidth(), -getHeight());
            }
            EdgeEffect edgeEffect8 = this.mBottomGlow;
            if (edgeEffect8 != null && edgeEffect8.draw(canvas)) {
                z3 = true;
            }
            z2 |= z3;
            canvas.restoreToCount(iSave4);
        }
        if ((z2 || this.f6039x == null || this.f6029n.size() <= 0 || !this.f6039x.isRunning()) ? z2 : true) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override // android.view.ViewGroup
    public boolean drawChild(Canvas canvas, View view, long j2) {
        return super.drawChild(canvas, view, j2);
    }

    boolean e0(ViewHolder viewHolder, int i2) {
        if (!isComputingLayout()) {
            ViewCompat.setImportantForAccessibility(viewHolder.itemView, i2);
            return true;
        }
        viewHolder.mPendingAccessibilityState = i2;
        this.H.add(viewHolder);
        return false;
    }

    void f(ViewHolder viewHolder, ItemAnimator.ItemHolderInfo itemHolderInfo, ItemAnimator.ItemHolderInfo itemHolderInfo2) {
        viewHolder.setIsRecyclable(false);
        if (this.f6039x.animateAppearance(viewHolder, itemHolderInfo, itemHolderInfo2)) {
            V();
        }
    }

    boolean f0(AccessibilityEvent accessibilityEvent) {
        if (!isComputingLayout()) {
            return false;
        }
        int contentChangeTypes = accessibilityEvent != null ? AccessibilityEventCompat.getContentChangeTypes(accessibilityEvent) : 0;
        this.mEatenAccessibilityChangeFlags |= contentChangeTypes != 0 ? contentChangeTypes : 0;
        return true;
    }

    @Nullable
    public View findChildViewUnder(float f2, float f3) {
        for (int iG = this.f6019d.g() - 1; iG >= 0; iG--) {
            View viewF = this.f6019d.f(iG);
            float translationX = viewF.getTranslationX();
            float translationY = viewF.getTranslationY();
            if (f2 >= viewF.getLeft() + translationX && f2 <= viewF.getRight() + translationX && f3 >= viewF.getTop() + translationY && f3 <= viewF.getBottom() + translationY) {
                return viewF;
            }
        }
        return null;
    }

    @Nullable
    public View findContainingItemView(@NonNull View view) {
        ViewParent parent = view.getParent();
        while (parent != null && parent != this && (parent instanceof View)) {
            view = parent;
            parent = view.getParent();
        }
        if (parent == this) {
            return view;
        }
        return null;
    }

    @Nullable
    public ViewHolder findContainingViewHolder(@NonNull View view) {
        View viewFindContainingItemView = findContainingItemView(view);
        if (viewFindContainingItemView == null) {
            return null;
        }
        return getChildViewHolder(viewFindContainingItemView);
    }

    @Nullable
    public ViewHolder findViewHolderForAdapterPosition(int i2) {
        ViewHolder viewHolder = null;
        if (this.f6037v) {
            return null;
        }
        int i3 = this.f6019d.i();
        for (int i4 = 0; i4 < i3; i4++) {
            ViewHolder viewHolderF = F(this.f6019d.h(i4));
            if (viewHolderF != null && !viewHolderF.isRemoved() && D(viewHolderF) == i2) {
                if (!this.f6019d.l(viewHolderF.itemView)) {
                    return viewHolderF;
                }
                viewHolder = viewHolderF;
            }
        }
        return viewHolder;
    }

    public ViewHolder findViewHolderForItemId(long j2) {
        Adapter adapter = this.f6025j;
        ViewHolder viewHolder = null;
        if (adapter != null && adapter.hasStableIds()) {
            int i2 = this.f6019d.i();
            for (int i3 = 0; i3 < i2; i3++) {
                ViewHolder viewHolderF = F(this.f6019d.h(i3));
                if (viewHolderF != null && !viewHolderF.isRemoved() && viewHolderF.getItemId() == j2) {
                    if (!this.f6019d.l(viewHolderF.itemView)) {
                        return viewHolderF;
                    }
                    viewHolder = viewHolderF;
                }
            }
        }
        return viewHolder;
    }

    @Nullable
    public ViewHolder findViewHolderForLayoutPosition(int i2) {
        return C(i2, false);
    }

    @Nullable
    @Deprecated
    public ViewHolder findViewHolderForPosition(int i2) {
        return C(i2, false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [boolean] */
    /* JADX WARN: Type inference failed for: r0v6 */
    public boolean fling(int i2, int i3) {
        LayoutManager layoutManager = this.f6026k;
        if (layoutManager == null) {
            Log.e("RecyclerView", "Cannot fling without a LayoutManager set. Call setLayoutManager with a non-null argument.");
            return false;
        }
        if (this.f6035t) {
            return false;
        }
        int iCanScrollHorizontally = layoutManager.canScrollHorizontally();
        boolean zCanScrollVertically = this.f6026k.canScrollVertically();
        if (iCanScrollHorizontally == 0 || Math.abs(i2) < this.mMinFlingVelocity) {
            i2 = 0;
        }
        if (!zCanScrollVertically || Math.abs(i3) < this.mMinFlingVelocity) {
            i3 = 0;
        }
        if (i2 == 0 && i3 == 0) {
            return false;
        }
        float f2 = i2;
        float f3 = i3;
        if (!dispatchNestedPreFling(f2, f3)) {
            boolean z2 = iCanScrollHorizontally != 0 || zCanScrollVertically;
            dispatchNestedFling(f2, f3, z2);
            OnFlingListener onFlingListener = this.mOnFlingListener;
            if (onFlingListener != null && onFlingListener.onFling(i2, i3)) {
                return true;
            }
            if (z2) {
                if (zCanScrollVertically) {
                    iCanScrollHorizontally = (iCanScrollHorizontally == true ? 1 : 0) | 2;
                }
                startNestedScroll(iCanScrollHorizontally, 1);
                int i4 = this.mMaxFlingVelocity;
                int iMax = Math.max(-i4, Math.min(i2, i4));
                int i5 = this.mMaxFlingVelocity;
                this.f6040y.fling(iMax, Math.max(-i5, Math.min(i3, i5)));
                return true;
            }
        }
        return false;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public View focusSearch(View view, int i2) {
        View viewOnFocusSearchFailed;
        boolean z2;
        View viewOnInterceptFocusSearch = this.f6026k.onInterceptFocusSearch(view, i2);
        if (viewOnInterceptFocusSearch != null) {
            return viewOnInterceptFocusSearch;
        }
        boolean z3 = (this.f6025j == null || this.f6026k == null || isComputingLayout() || this.f6035t) ? false : true;
        FocusFinder focusFinder = FocusFinder.getInstance();
        if (z3 && (i2 == 2 || i2 == 1)) {
            if (this.f6026k.canScrollVertically()) {
                int i3 = i2 == 2 ? 130 : 33;
                z2 = focusFinder.findNextFocus(this, view, i3) == null;
                if (FORCE_ABS_FOCUS_SEARCH_DIRECTION) {
                    i2 = i3;
                }
            } else {
                z2 = false;
            }
            if (!z2 && this.f6026k.canScrollHorizontally()) {
                int i4 = (this.f6026k.getLayoutDirection() == 1) ^ (i2 == 2) ? 66 : 17;
                boolean z4 = focusFinder.findNextFocus(this, view, i4) == null;
                if (FORCE_ABS_FOCUS_SEARCH_DIRECTION) {
                    i2 = i4;
                }
                z2 = z4;
            }
            if (z2) {
                n();
                if (findContainingItemView(view) == null) {
                    return null;
                }
                h0();
                this.f6026k.onFocusSearchFailed(view, i2, this.f6016a, this.B);
                i0(false);
            }
            viewOnFocusSearchFailed = focusFinder.findNextFocus(this, view, i2);
        } else {
            View viewFindNextFocus = focusFinder.findNextFocus(this, view, i2);
            if (viewFindNextFocus == null && z3) {
                n();
                if (findContainingItemView(view) == null) {
                    return null;
                }
                h0();
                viewOnFocusSearchFailed = this.f6026k.onFocusSearchFailed(view, i2, this.f6016a, this.B);
                i0(false);
            } else {
                viewOnFocusSearchFailed = viewFindNextFocus;
            }
        }
        if (viewOnFocusSearchFailed == null || viewOnFocusSearchFailed.hasFocusable()) {
            return isPreferredNextFocus(view, viewOnFocusSearchFailed, i2) ? viewOnFocusSearchFailed : super.focusSearch(view, i2);
        }
        if (getFocusedChild() == null) {
            return super.focusSearch(view, i2);
        }
        requestChildOnScreen(viewOnFocusSearchFailed, null);
        return view;
    }

    void g(ViewHolder viewHolder, ItemAnimator.ItemHolderInfo itemHolderInfo, ItemAnimator.ItemHolderInfo itemHolderInfo2) {
        addAnimatingView(viewHolder);
        viewHolder.setIsRecyclable(false);
        if (this.f6039x.animateDisappearance(viewHolder, itemHolderInfo, itemHolderInfo2)) {
            V();
        }
    }

    void g0(int i2, int i3, Interpolator interpolator, int i4, boolean z2) {
        LayoutManager layoutManager = this.f6026k;
        if (layoutManager == null) {
            Log.e("RecyclerView", "Cannot smooth scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
            return;
        }
        if (this.f6035t) {
            return;
        }
        if (!layoutManager.canScrollHorizontally()) {
            i2 = 0;
        }
        if (!this.f6026k.canScrollVertically()) {
            i3 = 0;
        }
        if (i2 == 0 && i3 == 0) {
            return;
        }
        if (i4 != Integer.MIN_VALUE && i4 <= 0) {
            scrollBy(i2, i3);
            return;
        }
        if (z2) {
            int i5 = i2 != 0 ? 1 : 0;
            if (i3 != 0) {
                i5 |= 2;
            }
            startNestedScroll(i5, 1);
        }
        this.f6040y.smoothScrollBy(i2, i3, i4, interpolator);
    }

    @Override // android.view.ViewGroup
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        LayoutManager layoutManager = this.f6026k;
        if (layoutManager != null) {
            return layoutManager.generateDefaultLayoutParams();
        }
        throw new IllegalStateException("RecyclerView has no LayoutManager" + z());
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        LayoutManager layoutManager = this.f6026k;
        if (layoutManager != null) {
            return layoutManager.generateLayoutParams(getContext(), attributeSet);
        }
        throw new IllegalStateException("RecyclerView has no LayoutManager" + z());
    }

    @Override // android.view.ViewGroup, android.view.View
    public CharSequence getAccessibilityClassName() {
        return "androidx.recyclerview.widget.RecyclerView";
    }

    @Nullable
    public Adapter getAdapter() {
        return this.f6025j;
    }

    @Override // android.view.View
    public int getBaseline() {
        LayoutManager layoutManager = this.f6026k;
        return layoutManager != null ? layoutManager.getBaseline() : super.getBaseline();
    }

    public int getChildAdapterPosition(@NonNull View view) {
        ViewHolder viewHolderF = F(view);
        if (viewHolderF != null) {
            return viewHolderF.getAbsoluteAdapterPosition();
        }
        return -1;
    }

    @Override // android.view.ViewGroup
    protected int getChildDrawingOrder(int i2, int i3) {
        ChildDrawingOrderCallback childDrawingOrderCallback = this.mChildDrawingOrderCallback;
        return childDrawingOrderCallback == null ? super.getChildDrawingOrder(i2, i3) : childDrawingOrderCallback.onGetChildDrawingOrder(i2, i3);
    }

    public long getChildItemId(@NonNull View view) {
        ViewHolder viewHolderF;
        Adapter adapter = this.f6025j;
        if (adapter == null || !adapter.hasStableIds() || (viewHolderF = F(view)) == null) {
            return -1L;
        }
        return viewHolderF.getItemId();
    }

    public int getChildLayoutPosition(@NonNull View view) {
        ViewHolder viewHolderF = F(view);
        if (viewHolderF != null) {
            return viewHolderF.getLayoutPosition();
        }
        return -1;
    }

    @Deprecated
    public int getChildPosition(@NonNull View view) {
        return getChildAdapterPosition(view);
    }

    public ViewHolder getChildViewHolder(@NonNull View view) {
        ViewParent parent = view.getParent();
        if (parent == null || parent == this) {
            return F(view);
        }
        throw new IllegalArgumentException("View " + view + " is not a direct child of " + this);
    }

    @Override // android.view.ViewGroup
    public boolean getClipToPadding() {
        return this.f6021f;
    }

    @Nullable
    public RecyclerViewAccessibilityDelegate getCompatAccessibilityDelegate() {
        return this.F;
    }

    public void getDecoratedBoundsWithMargins(@NonNull View view, @NonNull Rect rect) {
        G(view, rect);
    }

    @NonNull
    public EdgeEffectFactory getEdgeEffectFactory() {
        return this.mEdgeEffectFactory;
    }

    @Nullable
    public ItemAnimator getItemAnimator() {
        return this.f6039x;
    }

    @NonNull
    public ItemDecoration getItemDecorationAt(int i2) {
        int itemDecorationCount = getItemDecorationCount();
        if (i2 >= 0 && i2 < itemDecorationCount) {
            return (ItemDecoration) this.f6029n.get(i2);
        }
        throw new IndexOutOfBoundsException(i2 + " is an invalid index for size " + itemDecorationCount);
    }

    public int getItemDecorationCount() {
        return this.f6029n.size();
    }

    @Nullable
    public LayoutManager getLayoutManager() {
        return this.f6026k;
    }

    public int getMaxFlingVelocity() {
        return this.mMaxFlingVelocity;
    }

    public int getMinFlingVelocity() {
        return this.mMinFlingVelocity;
    }

    long getNanoTime() {
        if (L) {
            return System.nanoTime();
        }
        return 0L;
    }

    @Nullable
    public OnFlingListener getOnFlingListener() {
        return this.mOnFlingListener;
    }

    public boolean getPreserveFocusAfterLayout() {
        return this.mPreserveFocusAfterLayout;
    }

    @NonNull
    public RecycledViewPool getRecycledViewPool() {
        return this.f6016a.f();
    }

    public int getScrollState() {
        return this.mScrollState;
    }

    void h(String str) {
        if (isComputingLayout()) {
            return;
        }
        if (str == null) {
            throw new IllegalStateException("Cannot call this method unless RecyclerView is computing a layout or scrolling" + z());
        }
        throw new IllegalStateException(str + z());
    }

    void h0() {
        int i2 = this.mInterceptRequestLayoutDepth + 1;
        this.mInterceptRequestLayoutDepth = i2;
        if (i2 != 1 || this.f6035t) {
            return;
        }
        this.f6034s = false;
    }

    public boolean hasFixedSize() {
        return this.f6031p;
    }

    @Override // android.view.View, androidx.core.view.NestedScrollingChild
    public boolean hasNestedScrollingParent() {
        return getScrollingChildHelper().hasNestedScrollingParent();
    }

    public boolean hasPendingAdapterUpdates() {
        return !this.f6033r || this.f6037v || this.f6018c.g();
    }

    void i(String str) {
        if (isComputingLayout()) {
            if (str != null) {
                throw new IllegalStateException(str);
            }
            throw new IllegalStateException("Cannot call this method while RecyclerView is computing a layout or scrolling" + z());
        }
        if (this.mDispatchScrollCounter > 0) {
            Log.w("RecyclerView", "Cannot call this method in a scroll callback. Scroll callbacks mightbe run during a measure & layout pass where you cannot change theRecyclerView data. Any method call that might change the structureof the RecyclerView or the adapter contents should be postponed tothe next frame.", new IllegalStateException("" + z()));
        }
    }

    void i0(boolean z2) {
        if (this.mInterceptRequestLayoutDepth < 1) {
            this.mInterceptRequestLayoutDepth = 1;
        }
        if (!z2 && !this.f6035t) {
            this.f6034s = false;
        }
        if (this.mInterceptRequestLayoutDepth == 1) {
            if (z2 && this.f6034s && !this.f6035t && this.f6026k != null && this.f6025j != null) {
                r();
            }
            if (!this.f6035t) {
                this.f6034s = false;
            }
        }
        this.mInterceptRequestLayoutDepth--;
    }

    public void invalidateItemDecorations() {
        if (this.f6029n.size() == 0) {
            return;
        }
        LayoutManager layoutManager = this.f6026k;
        if (layoutManager != null) {
            layoutManager.assertNotInLayoutOrScroll("Cannot invalidate item decorations during a scroll or layout");
        }
        N();
        requestLayout();
    }

    public boolean isAnimating() {
        ItemAnimator itemAnimator = this.f6039x;
        return itemAnimator != null && itemAnimator.isRunning();
    }

    @Override // android.view.View
    public boolean isAttachedToWindow() {
        return this.f6030o;
    }

    public boolean isComputingLayout() {
        return this.mLayoutOrScrollCounter > 0;
    }

    @Deprecated
    public boolean isLayoutFrozen() {
        return isLayoutSuppressed();
    }

    @Override // android.view.ViewGroup
    public final boolean isLayoutSuppressed() {
        return this.f6035t;
    }

    @Override // android.view.View, androidx.core.view.NestedScrollingChild
    public boolean isNestedScrollingEnabled() {
        return getScrollingChildHelper().isNestedScrollingEnabled();
    }

    boolean j(ViewHolder viewHolder) {
        ItemAnimator itemAnimator = this.f6039x;
        return itemAnimator == null || itemAnimator.canReuseUpdatedViewHolder(viewHolder, viewHolder.getUnmodifiedPayloads());
    }

    void j0(int i2, int i3, Object obj) {
        int i4;
        int i5 = this.f6019d.i();
        int i6 = i2 + i3;
        for (int i7 = 0; i7 < i5; i7++) {
            View viewH = this.f6019d.h(i7);
            ViewHolder viewHolderF = F(viewH);
            if (viewHolderF != null && !viewHolderF.shouldIgnore() && (i4 = viewHolderF.mPosition) >= i2 && i4 < i6) {
                viewHolderF.addFlags(2);
                viewHolderF.addChangePayload(obj);
                ((LayoutParams) viewH.getLayoutParams()).f6064c = true;
            }
        }
        this.f6016a.C(i2, i3);
    }

    void l() {
        int i2 = this.f6019d.i();
        for (int i3 = 0; i3 < i2; i3++) {
            ViewHolder viewHolderF = F(this.f6019d.h(i3));
            if (!viewHolderF.shouldIgnore()) {
                viewHolderF.clearOldPosition();
            }
        }
        this.f6016a.b();
    }

    void m(int i2, int i3) {
        boolean zIsFinished;
        EdgeEffect edgeEffect = this.mLeftGlow;
        if (edgeEffect == null || edgeEffect.isFinished() || i2 <= 0) {
            zIsFinished = false;
        } else {
            this.mLeftGlow.onRelease();
            zIsFinished = this.mLeftGlow.isFinished();
        }
        EdgeEffect edgeEffect2 = this.mRightGlow;
        if (edgeEffect2 != null && !edgeEffect2.isFinished() && i2 < 0) {
            this.mRightGlow.onRelease();
            zIsFinished |= this.mRightGlow.isFinished();
        }
        EdgeEffect edgeEffect3 = this.mTopGlow;
        if (edgeEffect3 != null && !edgeEffect3.isFinished() && i3 > 0) {
            this.mTopGlow.onRelease();
            zIsFinished |= this.mTopGlow.isFinished();
        }
        EdgeEffect edgeEffect4 = this.mBottomGlow;
        if (edgeEffect4 != null && !edgeEffect4.isFinished() && i3 < 0) {
            this.mBottomGlow.onRelease();
            zIsFinished |= this.mBottomGlow.isFinished();
        }
        if (zIsFinished) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    void n() {
        if (!this.f6033r || this.f6037v) {
            TraceCompat.beginSection(TRACE_ON_DATA_SET_CHANGE_LAYOUT_TAG);
            r();
            TraceCompat.endSection();
            return;
        }
        if (this.f6018c.g()) {
            if (!this.f6018c.f(4) || this.f6018c.f(11)) {
                if (this.f6018c.g()) {
                    TraceCompat.beginSection(TRACE_ON_DATA_SET_CHANGE_LAYOUT_TAG);
                    r();
                    TraceCompat.endSection();
                    return;
                }
                return;
            }
            TraceCompat.beginSection(TRACE_HANDLE_ADAPTER_UPDATES_TAG);
            h0();
            S();
            this.f6018c.m();
            if (!this.f6034s) {
                if (hasUpdatedView()) {
                    r();
                } else {
                    this.f6018c.a();
                }
            }
            i0(true);
            T();
            TraceCompat.endSection();
        }
    }

    public void nestedScrollBy(int i2, int i3) {
        nestedScrollByInternal(i2, i3, null, 1);
    }

    void o(int i2, int i3) {
        setMeasuredDimension(LayoutManager.chooseSize(i2, getPaddingLeft() + getPaddingRight(), ViewCompat.getMinimumWidth(this)), LayoutManager.chooseSize(i3, getPaddingTop() + getPaddingBottom(), ViewCompat.getMinimumHeight(this)));
    }

    public void offsetChildrenHorizontal(@Px int i2) {
        int iG = this.f6019d.g();
        for (int i3 = 0; i3 < iG; i3++) {
            this.f6019d.f(i3).offsetLeftAndRight(i2);
        }
    }

    public void offsetChildrenVertical(@Px int i2) {
        int iG = this.f6019d.g();
        for (int i3 = 0; i3 < iG; i3++) {
            this.f6019d.f(i3).offsetTopAndBottom(i2);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x004e  */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void onAttachedToWindow() {
        /*
            r5 = this;
            super.onAttachedToWindow()
            r0 = 0
            r5.mLayoutOrScrollCounter = r0
            r1 = 1
            r5.f6030o = r1
            boolean r2 = r5.f6033r
            if (r2 == 0) goto L14
            boolean r2 = r5.isLayoutRequested()
            if (r2 != 0) goto L14
            goto L15
        L14:
            r1 = r0
        L15:
            r5.f6033r = r1
            androidx.recyclerview.widget.RecyclerView$LayoutManager r1 = r5.f6026k
            if (r1 == 0) goto L1e
            r1.a(r5)
        L1e:
            r5.E = r0
            boolean r0 = androidx.recyclerview.widget.RecyclerView.L
            if (r0 == 0) goto L61
            java.lang.ThreadLocal r0 = androidx.recyclerview.widget.GapWorker.f5895d
            java.lang.Object r1 = r0.get()
            androidx.recyclerview.widget.GapWorker r1 = (androidx.recyclerview.widget.GapWorker) r1
            r5.f6041z = r1
            if (r1 != 0) goto L5c
            androidx.recyclerview.widget.GapWorker r1 = new androidx.recyclerview.widget.GapWorker
            r1.<init>()
            r5.f6041z = r1
            android.view.Display r1 = androidx.core.view.ViewCompat.getDisplay(r5)
            boolean r2 = r5.isInEditMode()
            if (r2 != 0) goto L4e
            if (r1 == 0) goto L4e
            float r1 = r1.getRefreshRate()
            r2 = 1106247680(0x41f00000, float:30.0)
            int r2 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1))
            if (r2 < 0) goto L4e
            goto L50
        L4e:
            r1 = 1114636288(0x42700000, float:60.0)
        L50:
            androidx.recyclerview.widget.GapWorker r2 = r5.f6041z
            r3 = 1315859240(0x4e6e6b28, float:1.0E9)
            float r3 = r3 / r1
            long r3 = (long) r3
            r2.f5899c = r3
            r0.set(r2)
        L5c:
            androidx.recyclerview.widget.GapWorker r0 = r5.f6041z
            r0.add(r5)
        L61:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.RecyclerView.onAttachedToWindow():void");
    }

    public void onChildAttachedToWindow(@NonNull View view) {
    }

    public void onChildDetachedFromWindow(@NonNull View view) {
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        GapWorker gapWorker;
        super.onDetachedFromWindow();
        ItemAnimator itemAnimator = this.f6039x;
        if (itemAnimator != null) {
            itemAnimator.endAnimations();
        }
        stopScroll();
        this.f6030o = false;
        LayoutManager layoutManager = this.f6026k;
        if (layoutManager != null) {
            layoutManager.b(this, this.f6016a);
        }
        this.H.clear();
        removeCallbacks(this.mItemAnimatorRunner);
        this.f6020e.j();
        if (!L || (gapWorker = this.f6041z) == null) {
            return;
        }
        gapWorker.remove(this);
        this.f6041z = null;
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int size = this.f6029n.size();
        for (int i2 = 0; i2 < size; i2++) {
            ((ItemDecoration) this.f6029n.get(i2)).onDraw(canvas, this, this.B);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x006c  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onGenericMotionEvent(android.view.MotionEvent r6) {
        /*
            r5 = this;
            androidx.recyclerview.widget.RecyclerView$LayoutManager r0 = r5.f6026k
            r1 = 0
            if (r0 != 0) goto L6
            return r1
        L6:
            boolean r0 = r5.f6035t
            if (r0 == 0) goto Lb
            return r1
        Lb:
            int r0 = r6.getAction()
            r2 = 8
            if (r0 != r2) goto L78
            int r0 = r6.getSource()
            r0 = r0 & 2
            r2 = 0
            if (r0 == 0) goto L3e
            androidx.recyclerview.widget.RecyclerView$LayoutManager r0 = r5.f6026k
            boolean r0 = r0.canScrollVertically()
            if (r0 == 0) goto L2c
            r0 = 9
            float r0 = r6.getAxisValue(r0)
            float r0 = -r0
            goto L2d
        L2c:
            r0 = r2
        L2d:
            androidx.recyclerview.widget.RecyclerView$LayoutManager r3 = r5.f6026k
            boolean r3 = r3.canScrollHorizontally()
            if (r3 == 0) goto L3c
            r3 = 10
            float r3 = r6.getAxisValue(r3)
            goto L64
        L3c:
            r3 = r2
            goto L64
        L3e:
            int r0 = r6.getSource()
            r3 = 4194304(0x400000, float:5.877472E-39)
            r0 = r0 & r3
            if (r0 == 0) goto L62
            r0 = 26
            float r0 = r6.getAxisValue(r0)
            androidx.recyclerview.widget.RecyclerView$LayoutManager r3 = r5.f6026k
            boolean r3 = r3.canScrollVertically()
            if (r3 == 0) goto L57
            float r0 = -r0
            goto L3c
        L57:
            androidx.recyclerview.widget.RecyclerView$LayoutManager r3 = r5.f6026k
            boolean r3 = r3.canScrollHorizontally()
            if (r3 == 0) goto L62
            r3 = r0
            r0 = r2
            goto L64
        L62:
            r0 = r2
            r3 = r0
        L64:
            int r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r4 != 0) goto L6c
            int r2 = (r3 > r2 ? 1 : (r3 == r2 ? 0 : -1))
            if (r2 == 0) goto L78
        L6c:
            float r2 = r5.mScaledHorizontalScrollFactor
            float r3 = r3 * r2
            int r2 = (int) r3
            float r3 = r5.mScaledVerticalScrollFactor
            float r0 = r0 * r3
            int r0 = (int) r0
            r3 = 1
            r5.nestedScrollByInternal(r2, r0, r6, r3)
        L78:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.RecyclerView.onGenericMotionEvent(android.view.MotionEvent):boolean");
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        boolean z2;
        if (this.f6035t) {
            return false;
        }
        this.mInterceptingOnItemTouchListener = null;
        if (findInterceptingOnItemTouchListener(motionEvent)) {
            cancelScroll();
            return true;
        }
        LayoutManager layoutManager = this.f6026k;
        if (layoutManager == null) {
            return false;
        }
        boolean zCanScrollHorizontally = layoutManager.canScrollHorizontally();
        boolean zCanScrollVertically = this.f6026k.canScrollVertically();
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(motionEvent);
        int actionMasked = motionEvent.getActionMasked();
        int actionIndex = motionEvent.getActionIndex();
        if (actionMasked == 0) {
            if (this.mIgnoreMotionEventTillDown) {
                this.mIgnoreMotionEventTillDown = false;
            }
            this.mScrollPointerId = motionEvent.getPointerId(0);
            int x2 = (int) (motionEvent.getX() + 0.5f);
            this.mLastTouchX = x2;
            this.mInitialTouchX = x2;
            int y2 = (int) (motionEvent.getY() + 0.5f);
            this.mLastTouchY = y2;
            this.mInitialTouchY = y2;
            if (this.mScrollState == 2) {
                getParent().requestDisallowInterceptTouchEvent(true);
                setScrollState(1);
                stopNestedScroll(1);
            }
            int[] iArr = this.mNestedOffsets;
            iArr[1] = 0;
            iArr[0] = 0;
            int i2 = zCanScrollHorizontally;
            if (zCanScrollVertically) {
                i2 = (zCanScrollHorizontally ? 1 : 0) | 2;
            }
            startNestedScroll(i2, 0);
        } else if (actionMasked == 1) {
            this.mVelocityTracker.clear();
            stopNestedScroll(0);
        } else if (actionMasked == 2) {
            int iFindPointerIndex = motionEvent.findPointerIndex(this.mScrollPointerId);
            if (iFindPointerIndex < 0) {
                Log.e("RecyclerView", "Error processing scroll; pointer index for id " + this.mScrollPointerId + " not found. Did any MotionEvents get skipped?");
                return false;
            }
            int x3 = (int) (motionEvent.getX(iFindPointerIndex) + 0.5f);
            int y3 = (int) (motionEvent.getY(iFindPointerIndex) + 0.5f);
            if (this.mScrollState != 1) {
                int i3 = x3 - this.mInitialTouchX;
                int i4 = y3 - this.mInitialTouchY;
                if (zCanScrollHorizontally == 0 || Math.abs(i3) <= this.mTouchSlop) {
                    z2 = false;
                } else {
                    this.mLastTouchX = x3;
                    z2 = true;
                }
                if (zCanScrollVertically && Math.abs(i4) > this.mTouchSlop) {
                    this.mLastTouchY = y3;
                    z2 = true;
                }
                if (z2) {
                    setScrollState(1);
                }
            }
        } else if (actionMasked == 3) {
            cancelScroll();
        } else if (actionMasked == 5) {
            this.mScrollPointerId = motionEvent.getPointerId(actionIndex);
            int x4 = (int) (motionEvent.getX(actionIndex) + 0.5f);
            this.mLastTouchX = x4;
            this.mInitialTouchX = x4;
            int y4 = (int) (motionEvent.getY(actionIndex) + 0.5f);
            this.mLastTouchY = y4;
            this.mInitialTouchY = y4;
        } else if (actionMasked == 6) {
            onPointerUp(motionEvent);
        }
        return this.mScrollState == 1;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        TraceCompat.beginSection(TRACE_ON_LAYOUT_TAG);
        r();
        TraceCompat.endSection();
        this.f6033r = true;
    }

    @Override // android.view.View
    protected void onMeasure(int i2, int i3) {
        LayoutManager layoutManager = this.f6026k;
        if (layoutManager == null) {
            o(i2, i3);
            return;
        }
        boolean z2 = false;
        if (layoutManager.isAutoMeasureEnabled()) {
            int mode = View.MeasureSpec.getMode(i2);
            int mode2 = View.MeasureSpec.getMode(i3);
            this.f6026k.onMeasure(this.f6016a, this.B, i2, i3);
            if (mode == 1073741824 && mode2 == 1073741824) {
                z2 = true;
            }
            this.mLastAutoMeasureSkippedDueToExact = z2;
            if (z2 || this.f6025j == null) {
                return;
            }
            if (this.B.f6082d == 1) {
                dispatchLayoutStep1();
            }
            this.f6026k.k(i2, i3);
            this.B.f6087i = true;
            dispatchLayoutStep2();
            this.f6026k.l(i2, i3);
            if (this.f6026k.o()) {
                this.f6026k.k(View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824));
                this.B.f6087i = true;
                dispatchLayoutStep2();
                this.f6026k.l(i2, i3);
            }
            this.mLastAutoMeasureNonExactMeasuredWidth = getMeasuredWidth();
            this.mLastAutoMeasureNonExactMeasuredHeight = getMeasuredHeight();
            return;
        }
        if (this.f6031p) {
            this.f6026k.onMeasure(this.f6016a, this.B, i2, i3);
            return;
        }
        if (this.f6036u) {
            h0();
            S();
            processAdapterUpdatesAndSetAnimationFlags();
            T();
            State state = this.B;
            if (state.f6089k) {
                state.f6085g = true;
            } else {
                this.f6018c.b();
                this.B.f6085g = false;
            }
            this.f6036u = false;
            i0(false);
        } else if (this.B.f6089k) {
            setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
            return;
        }
        Adapter adapter = this.f6025j;
        if (adapter != null) {
            this.B.f6083e = adapter.getItemCount();
        } else {
            this.B.f6083e = 0;
        }
        h0();
        this.f6026k.onMeasure(this.f6016a, this.B, i2, i3);
        i0(false);
        this.B.f6085g = false;
    }

    @Override // android.view.ViewGroup
    protected boolean onRequestFocusInDescendants(int i2, Rect rect) {
        if (isComputingLayout()) {
            return false;
        }
        return super.onRequestFocusInDescendants(i2, rect);
    }

    @Override // android.view.View
    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        this.f6017b = savedState;
        super.onRestoreInstanceState(savedState.getSuperState());
        requestLayout();
    }

    @Override // android.view.View
    protected Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        SavedState savedState2 = this.f6017b;
        if (savedState2 != null) {
            savedState.a(savedState2);
        } else {
            LayoutManager layoutManager = this.f6026k;
            if (layoutManager != null) {
                savedState.f6078a = layoutManager.onSaveInstanceState();
            } else {
                savedState.f6078a = null;
            }
        }
        return savedState;
    }

    public void onScrollStateChanged(int i2) {
    }

    public void onScrolled(@Px int i2, @Px int i3) {
    }

    @Override // android.view.View
    protected void onSizeChanged(int i2, int i3, int i4, int i5) {
        super.onSizeChanged(i2, i3, i4, i5);
        if (i2 == i4 && i3 == i5) {
            return;
        }
        K();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00df A[PHI: r0
      0x00df: PHI (r0v36 int) = (r0v26 int), (r0v40 int) binds: [B:41:0x00c8, B:45:0x00db] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00e2  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00f8  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(android.view.MotionEvent r18) {
        /*
            Method dump skipped, instructions count: 477
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.RecyclerView.onTouchEvent(android.view.MotionEvent):boolean");
    }

    void p(View view) {
        ViewHolder viewHolderF = F(view);
        onChildAttachedToWindow(view);
        Adapter adapter = this.f6025j;
        if (adapter != null && viewHolderF != null) {
            adapter.onViewAttachedToWindow(viewHolderF);
        }
        List<OnChildAttachStateChangeListener> list = this.mOnChildAttachStateListeners;
        if (list != null) {
            for (int size = list.size() - 1; size >= 0; size--) {
                this.mOnChildAttachStateListeners.get(size).onChildViewAttachedToWindow(view);
            }
        }
    }

    void q(View view) {
        ViewHolder viewHolderF = F(view);
        onChildDetachedFromWindow(view);
        Adapter adapter = this.f6025j;
        if (adapter != null && viewHolderF != null) {
            adapter.onViewDetachedFromWindow(viewHolderF);
        }
        List<OnChildAttachStateChangeListener> list = this.mOnChildAttachStateListeners;
        if (list != null) {
            for (int size = list.size() - 1; size >= 0; size--) {
                this.mOnChildAttachStateListeners.get(size).onChildViewDetachedFromWindow(view);
            }
        }
    }

    void r() {
        if (this.f6025j == null) {
            Log.w("RecyclerView", "No adapter attached; skipping layout");
            return;
        }
        if (this.f6026k == null) {
            Log.e("RecyclerView", "No layout manager attached; skipping layout");
            return;
        }
        this.B.f6087i = false;
        boolean z2 = this.mLastAutoMeasureSkippedDueToExact && !(this.mLastAutoMeasureNonExactMeasuredWidth == getWidth() && this.mLastAutoMeasureNonExactMeasuredHeight == getHeight());
        this.mLastAutoMeasureNonExactMeasuredWidth = 0;
        this.mLastAutoMeasureNonExactMeasuredHeight = 0;
        this.mLastAutoMeasureSkippedDueToExact = false;
        if (this.B.f6082d == 1) {
            dispatchLayoutStep1();
            this.f6026k.j(this);
            dispatchLayoutStep2();
        } else if (this.f6018c.h() || z2 || this.f6026k.getWidth() != getWidth() || this.f6026k.getHeight() != getHeight()) {
            this.f6026k.j(this);
            dispatchLayoutStep2();
        } else {
            this.f6026k.j(this);
        }
        dispatchLayoutStep3();
    }

    @Override // android.view.ViewGroup
    protected void removeDetachedView(View view, boolean z2) {
        ViewHolder viewHolderF = F(view);
        if (viewHolderF != null) {
            if (viewHolderF.isTmpDetached()) {
                viewHolderF.clearTmpDetachFlag();
            } else if (!viewHolderF.shouldIgnore()) {
                throw new IllegalArgumentException("Called removeDetachedView with a view which is not flagged as tmp detached." + viewHolderF + z());
            }
        }
        view.clearAnimation();
        q(view);
        super.removeDetachedView(view, z2);
    }

    public void removeItemDecoration(@NonNull ItemDecoration itemDecoration) {
        LayoutManager layoutManager = this.f6026k;
        if (layoutManager != null) {
            layoutManager.assertNotInLayoutOrScroll("Cannot remove item decoration during a scroll  or layout");
        }
        this.f6029n.remove(itemDecoration);
        if (this.f6029n.isEmpty()) {
            setWillNotDraw(getOverScrollMode() == 2);
        }
        N();
        requestLayout();
    }

    public void removeItemDecorationAt(int i2) {
        int itemDecorationCount = getItemDecorationCount();
        if (i2 >= 0 && i2 < itemDecorationCount) {
            removeItemDecoration(getItemDecorationAt(i2));
            return;
        }
        throw new IndexOutOfBoundsException(i2 + " is an invalid index for size " + itemDecorationCount);
    }

    public void removeOnChildAttachStateChangeListener(@NonNull OnChildAttachStateChangeListener onChildAttachStateChangeListener) {
        List<OnChildAttachStateChangeListener> list = this.mOnChildAttachStateListeners;
        if (list == null) {
            return;
        }
        list.remove(onChildAttachStateChangeListener);
    }

    public void removeOnItemTouchListener(@NonNull OnItemTouchListener onItemTouchListener) {
        this.mOnItemTouchListeners.remove(onItemTouchListener);
        if (this.mInterceptingOnItemTouchListener == onItemTouchListener) {
            this.mInterceptingOnItemTouchListener = null;
        }
    }

    public void removeOnScrollListener(@NonNull OnScrollListener onScrollListener) {
        List<OnScrollListener> list = this.mScrollListeners;
        if (list != null) {
            list.remove(onScrollListener);
        }
    }

    public void removeRecyclerListener(@NonNull RecyclerListener recyclerListener) {
        this.f6028m.remove(recyclerListener);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void requestChildFocus(View view, View view2) {
        if (!this.f6026k.onRequestChildFocus(this, this.B, view, view2) && view2 != null) {
            requestChildOnScreen(view, view2);
        }
        super.requestChildFocus(view, view2);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public boolean requestChildRectangleOnScreen(View view, Rect rect, boolean z2) {
        return this.f6026k.requestChildRectangleOnScreen(this, view, rect, z2);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void requestDisallowInterceptTouchEvent(boolean z2) {
        int size = this.mOnItemTouchListeners.size();
        for (int i2 = 0; i2 < size; i2++) {
            this.mOnItemTouchListeners.get(i2).onRequestDisallowInterceptTouchEvent(z2);
        }
        super.requestDisallowInterceptTouchEvent(z2);
    }

    @Override // android.view.View, android.view.ViewParent
    public void requestLayout() {
        if (this.mInterceptRequestLayoutDepth != 0 || this.f6035t) {
            this.f6034s = true;
        } else {
            super.requestLayout();
        }
    }

    void s(int i2) {
        LayoutManager layoutManager = this.f6026k;
        if (layoutManager != null) {
            layoutManager.onScrollStateChanged(i2);
        }
        onScrollStateChanged(i2);
        OnScrollListener onScrollListener = this.mScrollListener;
        if (onScrollListener != null) {
            onScrollListener.onScrollStateChanged(this, i2);
        }
        List<OnScrollListener> list = this.mScrollListeners;
        if (list != null) {
            for (int size = list.size() - 1; size >= 0; size--) {
                this.mScrollListeners.get(size).onScrollStateChanged(this, i2);
            }
        }
    }

    @Override // android.view.View
    public void scrollBy(int i2, int i3) {
        LayoutManager layoutManager = this.f6026k;
        if (layoutManager == null) {
            Log.e("RecyclerView", "Cannot scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
            return;
        }
        if (this.f6035t) {
            return;
        }
        boolean zCanScrollHorizontally = layoutManager.canScrollHorizontally();
        boolean zCanScrollVertically = this.f6026k.canScrollVertically();
        if (zCanScrollHorizontally || zCanScrollVertically) {
            if (!zCanScrollHorizontally) {
                i2 = 0;
            }
            if (!zCanScrollVertically) {
                i3 = 0;
            }
            c0(i2, i3, null, 0);
        }
    }

    @Override // android.view.View
    public void scrollTo(int i2, int i3) {
        Log.w("RecyclerView", "RecyclerView does not support scrolling to an absolute position. Use scrollToPosition instead");
    }

    public void scrollToPosition(int i2) {
        if (this.f6035t) {
            return;
        }
        stopScroll();
        LayoutManager layoutManager = this.f6026k;
        if (layoutManager == null) {
            Log.e("RecyclerView", "Cannot scroll to position a LayoutManager set. Call setLayoutManager with a non-null argument.");
        } else {
            layoutManager.scrollToPosition(i2);
            awakenScrollBars();
        }
    }

    @Override // android.view.View, android.view.accessibility.AccessibilityEventSource
    public void sendAccessibilityEventUnchecked(AccessibilityEvent accessibilityEvent) {
        if (f0(accessibilityEvent)) {
            return;
        }
        super.sendAccessibilityEventUnchecked(accessibilityEvent);
    }

    public void setAccessibilityDelegateCompat(@Nullable RecyclerViewAccessibilityDelegate recyclerViewAccessibilityDelegate) {
        this.F = recyclerViewAccessibilityDelegate;
        ViewCompat.setAccessibilityDelegate(this, recyclerViewAccessibilityDelegate);
    }

    public void setAdapter(@Nullable Adapter adapter) {
        setLayoutFrozen(false);
        setAdapterInternal(adapter, false, true);
        W(false);
        requestLayout();
    }

    public void setChildDrawingOrderCallback(@Nullable ChildDrawingOrderCallback childDrawingOrderCallback) {
        if (childDrawingOrderCallback == this.mChildDrawingOrderCallback) {
            return;
        }
        this.mChildDrawingOrderCallback = childDrawingOrderCallback;
        setChildrenDrawingOrderEnabled(childDrawingOrderCallback != null);
    }

    @Override // android.view.ViewGroup
    public void setClipToPadding(boolean z2) {
        if (z2 != this.f6021f) {
            K();
        }
        this.f6021f = z2;
        super.setClipToPadding(z2);
        if (this.f6033r) {
            requestLayout();
        }
    }

    public void setEdgeEffectFactory(@NonNull EdgeEffectFactory edgeEffectFactory) {
        Preconditions.checkNotNull(edgeEffectFactory);
        this.mEdgeEffectFactory = edgeEffectFactory;
        K();
    }

    public void setHasFixedSize(boolean z2) {
        this.f6031p = z2;
    }

    public void setItemAnimator(@Nullable ItemAnimator itemAnimator) {
        ItemAnimator itemAnimator2 = this.f6039x;
        if (itemAnimator2 != null) {
            itemAnimator2.endAnimations();
            this.f6039x.b(null);
        }
        this.f6039x = itemAnimator;
        if (itemAnimator != null) {
            itemAnimator.b(this.mItemAnimatorListener);
        }
    }

    public void setItemViewCacheSize(int i2) {
        this.f6016a.setViewCacheSize(i2);
    }

    @Deprecated
    public void setLayoutFrozen(boolean z2) {
        suppressLayout(z2);
    }

    public void setLayoutManager(@Nullable LayoutManager layoutManager) {
        if (layoutManager == this.f6026k) {
            return;
        }
        stopScroll();
        if (this.f6026k != null) {
            ItemAnimator itemAnimator = this.f6039x;
            if (itemAnimator != null) {
                itemAnimator.endAnimations();
            }
            this.f6026k.removeAndRecycleAllViews(this.f6016a);
            this.f6026k.i(this.f6016a);
            this.f6016a.clear();
            if (this.f6030o) {
                this.f6026k.b(this, this.f6016a);
            }
            this.f6026k.m(null);
            this.f6026k = null;
        } else {
            this.f6016a.clear();
        }
        this.f6019d.m();
        this.f6026k = layoutManager;
        if (layoutManager != null) {
            if (layoutManager.f6051b != null) {
                throw new IllegalArgumentException("LayoutManager " + layoutManager + " is already attached to a RecyclerView:" + layoutManager.f6051b.z());
            }
            layoutManager.m(this);
            if (this.f6030o) {
                this.f6026k.a(this);
            }
        }
        this.f6016a.A();
        requestLayout();
    }

    @Override // android.view.ViewGroup
    @Deprecated
    public void setLayoutTransition(LayoutTransition layoutTransition) {
        if (layoutTransition != null) {
            throw new IllegalArgumentException("Providing a LayoutTransition into RecyclerView is not supported. Please use setItemAnimator() instead for animating changes to the items in this RecyclerView");
        }
        super.setLayoutTransition(null);
    }

    @Override // android.view.View, androidx.core.view.NestedScrollingChild
    public void setNestedScrollingEnabled(boolean z2) {
        getScrollingChildHelper().setNestedScrollingEnabled(z2);
    }

    public void setOnFlingListener(@Nullable OnFlingListener onFlingListener) {
        this.mOnFlingListener = onFlingListener;
    }

    @Deprecated
    public void setOnScrollListener(@Nullable OnScrollListener onScrollListener) {
        this.mScrollListener = onScrollListener;
    }

    public void setPreserveFocusAfterLayout(boolean z2) {
        this.mPreserveFocusAfterLayout = z2;
    }

    public void setRecycledViewPool(@Nullable RecycledViewPool recycledViewPool) {
        this.f6016a.w(recycledViewPool);
    }

    @Deprecated
    public void setRecyclerListener(@Nullable RecyclerListener recyclerListener) {
        this.f6027l = recyclerListener;
    }

    void setScrollState(int i2) {
        if (i2 == this.mScrollState) {
            return;
        }
        this.mScrollState = i2;
        if (i2 != 2) {
            stopScrollersInternal();
        }
        s(i2);
    }

    public void setScrollingTouchSlop(int i2) {
        ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());
        if (i2 != 0) {
            if (i2 == 1) {
                this.mTouchSlop = viewConfiguration.getScaledPagingTouchSlop();
                return;
            }
            Log.w("RecyclerView", "setScrollingTouchSlop(): bad argument constant " + i2 + "; using default value");
        }
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
    }

    public void setViewCacheExtension(@Nullable ViewCacheExtension viewCacheExtension) {
        this.f6016a.x(viewCacheExtension);
    }

    public void smoothScrollBy(@Px int i2, @Px int i3) {
        smoothScrollBy(i2, i3, null);
    }

    public void smoothScrollToPosition(int i2) {
        if (this.f6035t) {
            return;
        }
        LayoutManager layoutManager = this.f6026k;
        if (layoutManager == null) {
            Log.e("RecyclerView", "Cannot smooth scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
        } else {
            layoutManager.smoothScrollToPosition(this, this.B, i2);
        }
    }

    @Override // android.view.View, androidx.core.view.NestedScrollingChild
    public boolean startNestedScroll(int i2) {
        return getScrollingChildHelper().startNestedScroll(i2);
    }

    @Override // android.view.View, androidx.core.view.NestedScrollingChild
    public void stopNestedScroll() {
        getScrollingChildHelper().stopNestedScroll();
    }

    public void stopScroll() {
        setScrollState(0);
        stopScrollersInternal();
    }

    @Override // android.view.ViewGroup
    public final void suppressLayout(boolean z2) {
        if (z2 != this.f6035t) {
            i("Do not suppressLayout in layout or scroll");
            if (z2) {
                long jUptimeMillis = SystemClock.uptimeMillis();
                onTouchEvent(MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 3, 0.0f, 0.0f, 0));
                this.f6035t = true;
                this.mIgnoreMotionEventTillDown = true;
                stopScroll();
                return;
            }
            this.f6035t = false;
            if (this.f6034s && this.f6026k != null && this.f6025j != null) {
                requestLayout();
            }
            this.f6034s = false;
        }
    }

    public void swapAdapter(@Nullable Adapter adapter, boolean z2) {
        setLayoutFrozen(false);
        setAdapterInternal(adapter, true, z2);
        W(true);
        requestLayout();
    }

    void t(int i2, int i3) {
        this.mDispatchScrollCounter++;
        int scrollX = getScrollX();
        int scrollY = getScrollY();
        onScrollChanged(scrollX, scrollY, scrollX - i2, scrollY - i3);
        onScrolled(i2, i3);
        OnScrollListener onScrollListener = this.mScrollListener;
        if (onScrollListener != null) {
            onScrollListener.onScrolled(this, i2, i3);
        }
        List<OnScrollListener> list = this.mScrollListeners;
        if (list != null) {
            for (int size = list.size() - 1; size >= 0; size--) {
                this.mScrollListeners.get(size).onScrolled(this, i2, i3);
            }
        }
        this.mDispatchScrollCounter--;
    }

    void u() {
        int i2;
        for (int size = this.H.size() - 1; size >= 0; size--) {
            ViewHolder viewHolder = (ViewHolder) this.H.get(size);
            if (viewHolder.itemView.getParent() == this && !viewHolder.shouldIgnore() && (i2 = viewHolder.mPendingAccessibilityState) != -1) {
                ViewCompat.setImportantForAccessibility(viewHolder.itemView, i2);
                viewHolder.mPendingAccessibilityState = -1;
            }
        }
        this.H.clear();
    }

    void v() {
        if (this.mBottomGlow != null) {
            return;
        }
        EdgeEffect edgeEffectA = this.mEdgeEffectFactory.a(this, 3);
        this.mBottomGlow = edgeEffectA;
        if (this.f6021f) {
            edgeEffectA.setSize((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight(), (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom());
        } else {
            edgeEffectA.setSize(getMeasuredWidth(), getMeasuredHeight());
        }
    }

    void w() {
        if (this.mLeftGlow != null) {
            return;
        }
        EdgeEffect edgeEffectA = this.mEdgeEffectFactory.a(this, 0);
        this.mLeftGlow = edgeEffectA;
        if (this.f6021f) {
            edgeEffectA.setSize((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom(), (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight());
        } else {
            edgeEffectA.setSize(getMeasuredHeight(), getMeasuredWidth());
        }
    }

    void x() {
        if (this.mRightGlow != null) {
            return;
        }
        EdgeEffect edgeEffectA = this.mEdgeEffectFactory.a(this, 2);
        this.mRightGlow = edgeEffectA;
        if (this.f6021f) {
            edgeEffectA.setSize((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom(), (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight());
        } else {
            edgeEffectA.setSize(getMeasuredHeight(), getMeasuredWidth());
        }
    }

    void y() {
        if (this.mTopGlow != null) {
            return;
        }
        EdgeEffect edgeEffectA = this.mEdgeEffectFactory.a(this, 1);
        this.mTopGlow = edgeEffectA;
        if (this.f6021f) {
            edgeEffectA.setSize((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight(), (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom());
        } else {
            edgeEffectA.setSize(getMeasuredWidth(), getMeasuredHeight());
        }
    }

    String z() {
        return " " + super.toString() + ", adapter:" + this.f6025j + ", layout:" + this.f6026k + ", context:" + getContext();
    }

    public final class Recycler {

        /* renamed from: a, reason: collision with root package name */
        final ArrayList f6071a;

        /* renamed from: b, reason: collision with root package name */
        ArrayList f6072b;

        /* renamed from: c, reason: collision with root package name */
        final ArrayList f6073c;

        /* renamed from: d, reason: collision with root package name */
        int f6074d;

        /* renamed from: e, reason: collision with root package name */
        RecycledViewPool f6075e;
        private int mRequestedCacheMax;
        private final List<ViewHolder> mUnmodifiableAttachedScrap;
        private ViewCacheExtension mViewCacheExtension;

        public Recycler() {
            ArrayList arrayList = new ArrayList();
            this.f6071a = arrayList;
            this.f6072b = null;
            this.f6073c = new ArrayList();
            this.mUnmodifiableAttachedScrap = Collections.unmodifiableList(arrayList);
            this.mRequestedCacheMax = 2;
            this.f6074d = 2;
        }

        private void attachAccessibilityDelegateOnBind(ViewHolder viewHolder) {
            if (RecyclerView.this.L()) {
                View view = viewHolder.itemView;
                if (ViewCompat.getImportantForAccessibility(view) == 0) {
                    ViewCompat.setImportantForAccessibility(view, 1);
                }
                RecyclerViewAccessibilityDelegate recyclerViewAccessibilityDelegate = RecyclerView.this.F;
                if (recyclerViewAccessibilityDelegate == null) {
                    return;
                }
                AccessibilityDelegateCompat itemDelegate = recyclerViewAccessibilityDelegate.getItemDelegate();
                if (itemDelegate instanceof RecyclerViewAccessibilityDelegate.ItemDelegate) {
                    ((RecyclerViewAccessibilityDelegate.ItemDelegate) itemDelegate).d(view);
                }
                ViewCompat.setAccessibilityDelegate(view, itemDelegate);
            }
        }

        private void invalidateDisplayListInt(ViewHolder viewHolder) {
            View view = viewHolder.itemView;
            if (view instanceof ViewGroup) {
                invalidateDisplayListInt((ViewGroup) view, false);
            }
        }

        private boolean tryBindViewHolderByDeadline(@NonNull ViewHolder viewHolder, int i2, int i3, long j2) {
            viewHolder.mBindingAdapter = null;
            viewHolder.mOwnerRecyclerView = RecyclerView.this;
            int itemViewType = viewHolder.getItemViewType();
            long nanoTime = RecyclerView.this.getNanoTime();
            if (j2 != Long.MAX_VALUE && !this.f6075e.g(itemViewType, nanoTime, j2)) {
                return false;
            }
            RecyclerView.this.f6025j.bindViewHolder(viewHolder, i2);
            this.f6075e.c(viewHolder.getItemViewType(), RecyclerView.this.getNanoTime() - nanoTime);
            attachAccessibilityDelegateOnBind(viewHolder);
            if (!RecyclerView.this.B.isPreLayout()) {
                return true;
            }
            viewHolder.mPreLayoutPosition = i3;
            return true;
        }

        void A() {
            LayoutManager layoutManager = RecyclerView.this.f6026k;
            this.f6074d = this.mRequestedCacheMax + (layoutManager != null ? layoutManager.f6058i : 0);
            for (int size = this.f6073c.size() - 1; size >= 0 && this.f6073c.size() > this.f6074d; size--) {
                t(size);
            }
        }

        boolean B(ViewHolder viewHolder) {
            if (viewHolder.isRemoved()) {
                return RecyclerView.this.B.isPreLayout();
            }
            int i2 = viewHolder.mPosition;
            if (i2 >= 0 && i2 < RecyclerView.this.f6025j.getItemCount()) {
                if (RecyclerView.this.B.isPreLayout() || RecyclerView.this.f6025j.getItemViewType(viewHolder.mPosition) == viewHolder.getItemViewType()) {
                    return !RecyclerView.this.f6025j.hasStableIds() || viewHolder.getItemId() == RecyclerView.this.f6025j.getItemId(viewHolder.mPosition);
                }
                return false;
            }
            throw new IndexOutOfBoundsException("Inconsistency detected. Invalid view holder adapter position" + viewHolder + RecyclerView.this.z());
        }

        void C(int i2, int i3) {
            int i4;
            int i5 = i3 + i2;
            for (int size = this.f6073c.size() - 1; size >= 0; size--) {
                ViewHolder viewHolder = (ViewHolder) this.f6073c.get(size);
                if (viewHolder != null && (i4 = viewHolder.mPosition) >= i2 && i4 < i5) {
                    viewHolder.addFlags(2);
                    t(size);
                }
            }
        }

        void a(ViewHolder viewHolder, boolean z2) {
            RecyclerView.k(viewHolder);
            View view = viewHolder.itemView;
            RecyclerViewAccessibilityDelegate recyclerViewAccessibilityDelegate = RecyclerView.this.F;
            if (recyclerViewAccessibilityDelegate != null) {
                AccessibilityDelegateCompat itemDelegate = recyclerViewAccessibilityDelegate.getItemDelegate();
                ViewCompat.setAccessibilityDelegate(view, itemDelegate instanceof RecyclerViewAccessibilityDelegate.ItemDelegate ? ((RecyclerViewAccessibilityDelegate.ItemDelegate) itemDelegate).c(view) : null);
            }
            if (z2) {
                d(viewHolder);
            }
            viewHolder.mBindingAdapter = null;
            viewHolder.mOwnerRecyclerView = null;
            f().putRecycledView(viewHolder);
        }

        void b() {
            int size = this.f6073c.size();
            for (int i2 = 0; i2 < size; i2++) {
                ((ViewHolder) this.f6073c.get(i2)).clearOldPosition();
            }
            int size2 = this.f6071a.size();
            for (int i3 = 0; i3 < size2; i3++) {
                ((ViewHolder) this.f6071a.get(i3)).clearOldPosition();
            }
            ArrayList arrayList = this.f6072b;
            if (arrayList != null) {
                int size3 = arrayList.size();
                for (int i4 = 0; i4 < size3; i4++) {
                    ((ViewHolder) this.f6072b.get(i4)).clearOldPosition();
                }
            }
        }

        public void bindViewToPosition(@NonNull View view, int i2) {
            LayoutParams layoutParams;
            ViewHolder viewHolderF = RecyclerView.F(view);
            if (viewHolderF == null) {
                throw new IllegalArgumentException("The view does not have a ViewHolder. You cannot pass arbitrary views to this method, they should be created by the Adapter" + RecyclerView.this.z());
            }
            int iD = RecyclerView.this.f6018c.d(i2);
            if (iD < 0 || iD >= RecyclerView.this.f6025j.getItemCount()) {
                throw new IndexOutOfBoundsException("Inconsistency detected. Invalid item position " + i2 + "(offset:" + iD + ").state:" + RecyclerView.this.B.getItemCount() + RecyclerView.this.z());
            }
            tryBindViewHolderByDeadline(viewHolderF, iD, i2, Long.MAX_VALUE);
            ViewGroup.LayoutParams layoutParams2 = viewHolderF.itemView.getLayoutParams();
            if (layoutParams2 == null) {
                layoutParams = (LayoutParams) RecyclerView.this.generateDefaultLayoutParams();
                viewHolderF.itemView.setLayoutParams(layoutParams);
            } else if (RecyclerView.this.checkLayoutParams(layoutParams2)) {
                layoutParams = (LayoutParams) layoutParams2;
            } else {
                layoutParams = (LayoutParams) RecyclerView.this.generateLayoutParams(layoutParams2);
                viewHolderF.itemView.setLayoutParams(layoutParams);
            }
            layoutParams.f6064c = true;
            layoutParams.f6062a = viewHolderF;
            layoutParams.f6065d = viewHolderF.itemView.getParent() == null;
        }

        void c() {
            this.f6071a.clear();
            ArrayList arrayList = this.f6072b;
            if (arrayList != null) {
                arrayList.clear();
            }
        }

        public void clear() {
            this.f6071a.clear();
            s();
        }

        public int convertPreLayoutPositionToPostLayout(int i2) {
            if (i2 >= 0 && i2 < RecyclerView.this.B.getItemCount()) {
                return !RecyclerView.this.B.isPreLayout() ? i2 : RecyclerView.this.f6018c.d(i2);
            }
            throw new IndexOutOfBoundsException("invalid position " + i2 + ". State item count is " + RecyclerView.this.B.getItemCount() + RecyclerView.this.z());
        }

        void d(ViewHolder viewHolder) {
            RecyclerListener recyclerListener = RecyclerView.this.f6027l;
            if (recyclerListener != null) {
                recyclerListener.onViewRecycled(viewHolder);
            }
            int size = RecyclerView.this.f6028m.size();
            for (int i2 = 0; i2 < size; i2++) {
                ((RecyclerListener) RecyclerView.this.f6028m.get(i2)).onViewRecycled(viewHolder);
            }
            Adapter adapter = RecyclerView.this.f6025j;
            if (adapter != null) {
                adapter.onViewRecycled(viewHolder);
            }
            RecyclerView recyclerView = RecyclerView.this;
            if (recyclerView.B != null) {
                recyclerView.f6020e.o(viewHolder);
            }
        }

        ViewHolder e(int i2) {
            int size;
            int iD;
            ArrayList arrayList = this.f6072b;
            if (arrayList != null && (size = arrayList.size()) != 0) {
                for (int i3 = 0; i3 < size; i3++) {
                    ViewHolder viewHolder = (ViewHolder) this.f6072b.get(i3);
                    if (!viewHolder.wasReturnedFromScrap() && viewHolder.getLayoutPosition() == i2) {
                        viewHolder.addFlags(32);
                        return viewHolder;
                    }
                }
                if (RecyclerView.this.f6025j.hasStableIds() && (iD = RecyclerView.this.f6018c.d(i2)) > 0 && iD < RecyclerView.this.f6025j.getItemCount()) {
                    long itemId = RecyclerView.this.f6025j.getItemId(iD);
                    for (int i4 = 0; i4 < size; i4++) {
                        ViewHolder viewHolder2 = (ViewHolder) this.f6072b.get(i4);
                        if (!viewHolder2.wasReturnedFromScrap() && viewHolder2.getItemId() == itemId) {
                            viewHolder2.addFlags(32);
                            return viewHolder2;
                        }
                    }
                }
            }
            return null;
        }

        RecycledViewPool f() {
            if (this.f6075e == null) {
                this.f6075e = new RecycledViewPool();
            }
            return this.f6075e;
        }

        int g() {
            return this.f6071a.size();
        }

        @NonNull
        public List<ViewHolder> getScrapList() {
            return this.mUnmodifiableAttachedScrap;
        }

        @NonNull
        public View getViewForPosition(int i2) {
            return k(i2, false);
        }

        ViewHolder h(long j2, int i2, boolean z2) {
            for (int size = this.f6071a.size() - 1; size >= 0; size--) {
                ViewHolder viewHolder = (ViewHolder) this.f6071a.get(size);
                if (viewHolder.getItemId() == j2 && !viewHolder.wasReturnedFromScrap()) {
                    if (i2 == viewHolder.getItemViewType()) {
                        viewHolder.addFlags(32);
                        if (viewHolder.isRemoved() && !RecyclerView.this.B.isPreLayout()) {
                            viewHolder.setFlags(2, 14);
                        }
                        return viewHolder;
                    }
                    if (!z2) {
                        this.f6071a.remove(size);
                        RecyclerView.this.removeDetachedView(viewHolder.itemView, false);
                        r(viewHolder.itemView);
                    }
                }
            }
            int size2 = this.f6073c.size();
            while (true) {
                size2--;
                if (size2 < 0) {
                    return null;
                }
                ViewHolder viewHolder2 = (ViewHolder) this.f6073c.get(size2);
                if (viewHolder2.getItemId() == j2 && !viewHolder2.isAttachedToTransitionOverlay()) {
                    if (i2 == viewHolder2.getItemViewType()) {
                        if (!z2) {
                            this.f6073c.remove(size2);
                        }
                        return viewHolder2;
                    }
                    if (!z2) {
                        t(size2);
                        return null;
                    }
                }
            }
        }

        ViewHolder i(int i2, boolean z2) {
            View viewE;
            int size = this.f6071a.size();
            for (int i3 = 0; i3 < size; i3++) {
                ViewHolder viewHolder = (ViewHolder) this.f6071a.get(i3);
                if (!viewHolder.wasReturnedFromScrap() && viewHolder.getLayoutPosition() == i2 && !viewHolder.isInvalid() && (RecyclerView.this.B.f6085g || !viewHolder.isRemoved())) {
                    viewHolder.addFlags(32);
                    return viewHolder;
                }
            }
            if (z2 || (viewE = RecyclerView.this.f6019d.e(i2)) == null) {
                int size2 = this.f6073c.size();
                for (int i4 = 0; i4 < size2; i4++) {
                    ViewHolder viewHolder2 = (ViewHolder) this.f6073c.get(i4);
                    if (!viewHolder2.isInvalid() && viewHolder2.getLayoutPosition() == i2 && !viewHolder2.isAttachedToTransitionOverlay()) {
                        if (!z2) {
                            this.f6073c.remove(i4);
                        }
                        return viewHolder2;
                    }
                }
                return null;
            }
            ViewHolder viewHolderF = RecyclerView.F(viewE);
            RecyclerView.this.f6019d.q(viewE);
            int iK = RecyclerView.this.f6019d.k(viewE);
            if (iK != -1) {
                RecyclerView.this.f6019d.d(iK);
                v(viewE);
                viewHolderF.addFlags(8224);
                return viewHolderF;
            }
            throw new IllegalStateException("layout index should not be -1 after unhiding a view:" + viewHolderF + RecyclerView.this.z());
        }

        View j(int i2) {
            return ((ViewHolder) this.f6071a.get(i2)).itemView;
        }

        View k(int i2, boolean z2) {
            return y(i2, z2, Long.MAX_VALUE).itemView;
        }

        void l() {
            int size = this.f6073c.size();
            for (int i2 = 0; i2 < size; i2++) {
                LayoutParams layoutParams = (LayoutParams) ((ViewHolder) this.f6073c.get(i2)).itemView.getLayoutParams();
                if (layoutParams != null) {
                    layoutParams.f6064c = true;
                }
            }
        }

        void m() {
            int size = this.f6073c.size();
            for (int i2 = 0; i2 < size; i2++) {
                ViewHolder viewHolder = (ViewHolder) this.f6073c.get(i2);
                if (viewHolder != null) {
                    viewHolder.addFlags(6);
                    viewHolder.addChangePayload(null);
                }
            }
            Adapter adapter = RecyclerView.this.f6025j;
            if (adapter == null || !adapter.hasStableIds()) {
                s();
            }
        }

        void n(int i2, int i3) {
            int size = this.f6073c.size();
            for (int i4 = 0; i4 < size; i4++) {
                ViewHolder viewHolder = (ViewHolder) this.f6073c.get(i4);
                if (viewHolder != null && viewHolder.mPosition >= i2) {
                    viewHolder.offsetPosition(i3, false);
                }
            }
        }

        void o(int i2, int i3) {
            int i4;
            int i5;
            int i6;
            int i7;
            if (i2 < i3) {
                i4 = -1;
                i6 = i2;
                i5 = i3;
            } else {
                i4 = 1;
                i5 = i2;
                i6 = i3;
            }
            int size = this.f6073c.size();
            for (int i8 = 0; i8 < size; i8++) {
                ViewHolder viewHolder = (ViewHolder) this.f6073c.get(i8);
                if (viewHolder != null && (i7 = viewHolder.mPosition) >= i6 && i7 <= i5) {
                    if (i7 == i2) {
                        viewHolder.offsetPosition(i3 - i2, false);
                    } else {
                        viewHolder.offsetPosition(i4, false);
                    }
                }
            }
        }

        void p(int i2, int i3, boolean z2) {
            int i4 = i2 + i3;
            for (int size = this.f6073c.size() - 1; size >= 0; size--) {
                ViewHolder viewHolder = (ViewHolder) this.f6073c.get(size);
                if (viewHolder != null) {
                    int i5 = viewHolder.mPosition;
                    if (i5 >= i4) {
                        viewHolder.offsetPosition(-i3, z2);
                    } else if (i5 >= i2) {
                        viewHolder.addFlags(8);
                        t(size);
                    }
                }
            }
        }

        void q(Adapter adapter, Adapter adapter2, boolean z2) {
            clear();
            f().e(adapter, adapter2, z2);
        }

        void r(View view) {
            ViewHolder viewHolderF = RecyclerView.F(view);
            viewHolderF.mScrapContainer = null;
            viewHolderF.mInChangeScrap = false;
            viewHolderF.clearReturnedFromScrapFlag();
            u(viewHolderF);
        }

        public void recycleView(@NonNull View view) {
            ViewHolder viewHolderF = RecyclerView.F(view);
            if (viewHolderF.isTmpDetached()) {
                RecyclerView.this.removeDetachedView(view, false);
            }
            if (viewHolderF.isScrap()) {
                viewHolderF.unScrap();
            } else if (viewHolderF.wasReturnedFromScrap()) {
                viewHolderF.clearReturnedFromScrapFlag();
            }
            u(viewHolderF);
            if (RecyclerView.this.f6039x == null || viewHolderF.isRecyclable()) {
                return;
            }
            RecyclerView.this.f6039x.endAnimation(viewHolderF);
        }

        void s() {
            for (int size = this.f6073c.size() - 1; size >= 0; size--) {
                t(size);
            }
            this.f6073c.clear();
            if (RecyclerView.L) {
                RecyclerView.this.A.a();
            }
        }

        public void setViewCacheSize(int i2) {
            this.mRequestedCacheMax = i2;
            A();
        }

        void t(int i2) {
            a((ViewHolder) this.f6073c.get(i2), true);
            this.f6073c.remove(i2);
        }

        void u(ViewHolder viewHolder) {
            boolean z2;
            boolean z3 = true;
            if (viewHolder.isScrap() || viewHolder.itemView.getParent() != null) {
                StringBuilder sb = new StringBuilder();
                sb.append("Scrapped or attached views may not be recycled. isScrap:");
                sb.append(viewHolder.isScrap());
                sb.append(" isAttached:");
                sb.append(viewHolder.itemView.getParent() != null);
                sb.append(RecyclerView.this.z());
                throw new IllegalArgumentException(sb.toString());
            }
            if (viewHolder.isTmpDetached()) {
                throw new IllegalArgumentException("Tmp detached view should be removed from RecyclerView before it can be recycled: " + viewHolder + RecyclerView.this.z());
            }
            if (viewHolder.shouldIgnore()) {
                throw new IllegalArgumentException("Trying to recycle an ignored view holder. You should first call stopIgnoringView(view) before calling recycle." + RecyclerView.this.z());
            }
            boolean zDoesTransientStatePreventRecycling = viewHolder.doesTransientStatePreventRecycling();
            Adapter adapter = RecyclerView.this.f6025j;
            if ((adapter != null && zDoesTransientStatePreventRecycling && adapter.onFailedToRecycleView(viewHolder)) || viewHolder.isRecyclable()) {
                if (this.f6074d <= 0 || viewHolder.hasAnyOfTheFlags(526)) {
                    z2 = false;
                } else {
                    int size = this.f6073c.size();
                    if (size >= this.f6074d && size > 0) {
                        t(0);
                        size--;
                    }
                    if (RecyclerView.L && size > 0 && !RecyclerView.this.A.c(viewHolder.mPosition)) {
                        int i2 = size - 1;
                        while (i2 >= 0) {
                            if (!RecyclerView.this.A.c(((ViewHolder) this.f6073c.get(i2)).mPosition)) {
                                break;
                            } else {
                                i2--;
                            }
                        }
                        size = i2 + 1;
                    }
                    this.f6073c.add(size, viewHolder);
                    z2 = true;
                }
                if (z2) {
                    z3 = false;
                } else {
                    a(viewHolder, true);
                }
                z = z2;
            } else {
                z3 = false;
            }
            RecyclerView.this.f6020e.o(viewHolder);
            if (z || z3 || !zDoesTransientStatePreventRecycling) {
                return;
            }
            viewHolder.mBindingAdapter = null;
            viewHolder.mOwnerRecyclerView = null;
        }

        void v(View view) {
            ViewHolder viewHolderF = RecyclerView.F(view);
            if (!viewHolderF.hasAnyOfTheFlags(12) && viewHolderF.isUpdated() && !RecyclerView.this.j(viewHolderF)) {
                if (this.f6072b == null) {
                    this.f6072b = new ArrayList();
                }
                viewHolderF.setScrapContainer(this, true);
                this.f6072b.add(viewHolderF);
                return;
            }
            if (!viewHolderF.isInvalid() || viewHolderF.isRemoved() || RecyclerView.this.f6025j.hasStableIds()) {
                viewHolderF.setScrapContainer(this, false);
                this.f6071a.add(viewHolderF);
            } else {
                throw new IllegalArgumentException("Called scrap view with an invalid view. Invalid views cannot be reused from scrap, they should rebound from recycler pool." + RecyclerView.this.z());
            }
        }

        void w(RecycledViewPool recycledViewPool) {
            RecycledViewPool recycledViewPool2 = this.f6075e;
            if (recycledViewPool2 != null) {
                recycledViewPool2.b();
            }
            this.f6075e = recycledViewPool;
            if (recycledViewPool == null || RecyclerView.this.getAdapter() == null) {
                return;
            }
            this.f6075e.a();
        }

        void x(ViewCacheExtension viewCacheExtension) {
            this.mViewCacheExtension = viewCacheExtension;
        }

        /* JADX WARN: Removed duplicated region for block: B:100:0x020d  */
        /* JADX WARN: Removed duplicated region for block: B:106:0x0229 A[ADDED_TO_REGION] */
        /* JADX WARN: Removed duplicated region for block: B:108:0x022c  */
        /* JADX WARN: Removed duplicated region for block: B:18:0x0037  */
        /* JADX WARN: Removed duplicated region for block: B:27:0x005c  */
        /* JADX WARN: Removed duplicated region for block: B:29:0x005f  */
        /* JADX WARN: Removed duplicated region for block: B:72:0x014a A[PHI: r1 r4
          0x014a: PHI (r1v12 androidx.recyclerview.widget.RecyclerView$ViewHolder) = 
          (r1v11 androidx.recyclerview.widget.RecyclerView$ViewHolder)
          (r1v31 androidx.recyclerview.widget.RecyclerView$ViewHolder)
         binds: [B:28:0x005d, B:59:0x0102] A[DONT_GENERATE, DONT_INLINE]
          0x014a: PHI (r4v3 boolean) = (r4v2 boolean), (r4v7 boolean) binds: [B:28:0x005d, B:59:0x0102] A[DONT_GENERATE, DONT_INLINE]] */
        /* JADX WARN: Removed duplicated region for block: B:82:0x01a3  */
        /* JADX WARN: Removed duplicated region for block: B:88:0x01cf  */
        /* JADX WARN: Removed duplicated region for block: B:99:0x01ff  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        androidx.recyclerview.widget.RecyclerView.ViewHolder y(int r17, boolean r18, long r19) {
            /*
                Method dump skipped, instructions count: 616
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.RecyclerView.Recycler.y(int, boolean, long):androidx.recyclerview.widget.RecyclerView$ViewHolder");
        }

        void z(ViewHolder viewHolder) {
            if (viewHolder.mInChangeScrap) {
                this.f6072b.remove(viewHolder);
            } else {
                this.f6071a.remove(viewHolder);
            }
            viewHolder.mScrapContainer = null;
            viewHolder.mInChangeScrap = false;
            viewHolder.clearReturnedFromScrapFlag();
        }

        private void invalidateDisplayListInt(ViewGroup viewGroup, boolean z2) {
            for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
                View childAt = viewGroup.getChildAt(childCount);
                if (childAt instanceof ViewGroup) {
                    invalidateDisplayListInt((ViewGroup) childAt, true);
                }
            }
            if (z2) {
                if (viewGroup.getVisibility() == 4) {
                    viewGroup.setVisibility(0);
                    viewGroup.setVisibility(4);
                } else {
                    int visibility = viewGroup.getVisibility();
                    viewGroup.setVisibility(4);
                    viewGroup.setVisibility(visibility);
                }
            }
        }
    }

    public RecyclerView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, androidx.recyclerview.R.attr.recyclerViewStyle);
    }

    @Override // androidx.core.view.NestedScrollingChild2
    public boolean dispatchNestedPreScroll(int i2, int i3, int[] iArr, int[] iArr2, int i4) {
        return getScrollingChildHelper().dispatchNestedPreScroll(i2, i3, iArr, iArr2, i4);
    }

    @Override // androidx.core.view.NestedScrollingChild2
    public boolean dispatchNestedScroll(int i2, int i3, int i4, int i5, int[] iArr, int i6) {
        return getScrollingChildHelper().dispatchNestedScroll(i2, i3, i4, i5, iArr, i6);
    }

    @Override // androidx.core.view.NestedScrollingChild2
    public boolean hasNestedScrollingParent(int i2) {
        return getScrollingChildHelper().hasNestedScrollingParent(i2);
    }

    public void smoothScrollBy(@Px int i2, @Px int i3, @Nullable Interpolator interpolator) {
        smoothScrollBy(i2, i3, interpolator, Integer.MIN_VALUE);
    }

    @Override // androidx.core.view.NestedScrollingChild2
    public boolean startNestedScroll(int i2, int i3) {
        return getScrollingChildHelper().startNestedScroll(i2, i3);
    }

    @Override // androidx.core.view.NestedScrollingChild2
    public void stopNestedScroll(int i2) {
        getScrollingChildHelper().stopNestedScroll(i2);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>() { // from class: androidx.recyclerview.widget.RecyclerView.SavedState.1
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
        Parcelable f6078a;

        SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.f6078a = parcel.readParcelable(classLoader == null ? LayoutManager.class.getClassLoader() : classLoader);
        }

        void a(SavedState savedState) {
            this.f6078a = savedState.f6078a;
        }

        @Override // androidx.customview.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i2) {
            super.writeToParcel(parcel, i2);
            parcel.writeParcelable(this.f6078a, 0);
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }
    }

    public RecyclerView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2) throws NoSuchMethodException, SecurityException {
        super(context, attributeSet, i2);
        this.mObserver = new RecyclerViewDataObserver();
        this.f6016a = new Recycler();
        this.f6020e = new ViewInfoStore();
        this.f6022g = new Runnable() { // from class: androidx.recyclerview.widget.RecyclerView.1
            @Override // java.lang.Runnable
            public void run() {
                RecyclerView recyclerView = RecyclerView.this;
                if (!recyclerView.f6033r || recyclerView.isLayoutRequested()) {
                    return;
                }
                RecyclerView recyclerView2 = RecyclerView.this;
                if (!recyclerView2.f6030o) {
                    recyclerView2.requestLayout();
                } else if (recyclerView2.f6035t) {
                    recyclerView2.f6034s = true;
                } else {
                    recyclerView2.n();
                }
            }
        };
        this.f6023h = new Rect();
        this.mTempRect2 = new Rect();
        this.f6024i = new RectF();
        this.f6028m = new ArrayList();
        this.f6029n = new ArrayList();
        this.mOnItemTouchListeners = new ArrayList<>();
        this.mInterceptRequestLayoutDepth = 0;
        this.f6037v = false;
        this.f6038w = false;
        this.mLayoutOrScrollCounter = 0;
        this.mDispatchScrollCounter = 0;
        this.mEdgeEffectFactory = new EdgeEffectFactory();
        this.f6039x = new DefaultItemAnimator();
        this.mScrollState = 0;
        this.mScrollPointerId = -1;
        this.mScaledHorizontalScrollFactor = Float.MIN_VALUE;
        this.mScaledVerticalScrollFactor = Float.MIN_VALUE;
        this.mPreserveFocusAfterLayout = true;
        this.f6040y = new ViewFlinger();
        this.A = L ? new GapWorker.LayoutPrefetchRegistryImpl() : null;
        this.B = new State();
        this.C = false;
        this.D = false;
        this.mItemAnimatorListener = new ItemAnimatorRestoreListener();
        this.E = false;
        this.mMinMaxLayoutPositions = new int[2];
        this.mScrollOffset = new int[2];
        this.mNestedOffsets = new int[2];
        this.G = new int[2];
        this.H = new ArrayList();
        this.mItemAnimatorRunner = new Runnable() { // from class: androidx.recyclerview.widget.RecyclerView.2
            @Override // java.lang.Runnable
            public void run() {
                ItemAnimator itemAnimator = RecyclerView.this.f6039x;
                if (itemAnimator != null) {
                    itemAnimator.runPendingAnimations();
                }
                RecyclerView.this.E = false;
            }
        };
        this.mLastAutoMeasureNonExactMeasuredWidth = 0;
        this.mLastAutoMeasureNonExactMeasuredHeight = 0;
        this.mViewInfoProcessCallback = new ViewInfoStore.ProcessCallback() { // from class: androidx.recyclerview.widget.RecyclerView.4
            @Override // androidx.recyclerview.widget.ViewInfoStore.ProcessCallback
            public void processAppeared(ViewHolder viewHolder, ItemAnimator.ItemHolderInfo itemHolderInfo, ItemAnimator.ItemHolderInfo itemHolderInfo2) {
                RecyclerView.this.f(viewHolder, itemHolderInfo, itemHolderInfo2);
            }

            @Override // androidx.recyclerview.widget.ViewInfoStore.ProcessCallback
            public void processDisappeared(ViewHolder viewHolder, @NonNull ItemAnimator.ItemHolderInfo itemHolderInfo, @Nullable ItemAnimator.ItemHolderInfo itemHolderInfo2) {
                RecyclerView.this.f6016a.z(viewHolder);
                RecyclerView.this.g(viewHolder, itemHolderInfo, itemHolderInfo2);
            }

            @Override // androidx.recyclerview.widget.ViewInfoStore.ProcessCallback
            public void processPersistent(ViewHolder viewHolder, @NonNull ItemAnimator.ItemHolderInfo itemHolderInfo, @NonNull ItemAnimator.ItemHolderInfo itemHolderInfo2) {
                viewHolder.setIsRecyclable(false);
                RecyclerView recyclerView = RecyclerView.this;
                if (recyclerView.f6037v) {
                    if (recyclerView.f6039x.animateChange(viewHolder, viewHolder, itemHolderInfo, itemHolderInfo2)) {
                        RecyclerView.this.V();
                    }
                } else if (recyclerView.f6039x.animatePersistence(viewHolder, itemHolderInfo, itemHolderInfo2)) {
                    RecyclerView.this.V();
                }
            }

            @Override // androidx.recyclerview.widget.ViewInfoStore.ProcessCallback
            public void unused(ViewHolder viewHolder) {
                RecyclerView recyclerView = RecyclerView.this;
                recyclerView.f6026k.removeAndRecycleView(viewHolder.itemView, recyclerView.f6016a);
            }
        };
        setScrollContainer(true);
        setFocusableInTouchMode(true);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        this.mScaledHorizontalScrollFactor = ViewConfigurationCompat.getScaledHorizontalScrollFactor(viewConfiguration, context);
        this.mScaledVerticalScrollFactor = ViewConfigurationCompat.getScaledVerticalScrollFactor(viewConfiguration, context);
        this.mMinFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        this.mMaxFlingVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        setWillNotDraw(getOverScrollMode() == 2);
        this.f6039x.b(this.mItemAnimatorListener);
        I();
        initChildrenHelper();
        initAutofill();
        if (ViewCompat.getImportantForAccessibility(this) == 0) {
            ViewCompat.setImportantForAccessibility(this, 1);
        }
        this.mAccessibilityManager = (AccessibilityManager) getContext().getSystemService("accessibility");
        setAccessibilityDelegateCompat(new RecyclerViewAccessibilityDelegate(this));
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, androidx.recyclerview.R.styleable.RecyclerView, i2, 0);
        ViewCompat.saveAttributeDataForStyleable(this, context, androidx.recyclerview.R.styleable.RecyclerView, attributeSet, typedArrayObtainStyledAttributes, i2, 0);
        String string = typedArrayObtainStyledAttributes.getString(androidx.recyclerview.R.styleable.RecyclerView_layoutManager);
        if (typedArrayObtainStyledAttributes.getInt(androidx.recyclerview.R.styleable.RecyclerView_android_descendantFocusability, -1) == -1) {
            setDescendantFocusability(262144);
        }
        this.f6021f = typedArrayObtainStyledAttributes.getBoolean(androidx.recyclerview.R.styleable.RecyclerView_android_clipToPadding, true);
        boolean z2 = typedArrayObtainStyledAttributes.getBoolean(androidx.recyclerview.R.styleable.RecyclerView_fastScrollEnabled, false);
        this.f6032q = z2;
        if (z2) {
            J((StateListDrawable) typedArrayObtainStyledAttributes.getDrawable(androidx.recyclerview.R.styleable.RecyclerView_fastScrollVerticalThumbDrawable), typedArrayObtainStyledAttributes.getDrawable(androidx.recyclerview.R.styleable.RecyclerView_fastScrollVerticalTrackDrawable), (StateListDrawable) typedArrayObtainStyledAttributes.getDrawable(androidx.recyclerview.R.styleable.RecyclerView_fastScrollHorizontalThumbDrawable), typedArrayObtainStyledAttributes.getDrawable(androidx.recyclerview.R.styleable.RecyclerView_fastScrollHorizontalTrackDrawable));
        }
        typedArrayObtainStyledAttributes.recycle();
        createLayoutManager(context, string, attributeSet, i2, 0);
        int[] iArr = NESTED_SCROLLING_ATTRS;
        TypedArray typedArrayObtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, iArr, i2, 0);
        ViewCompat.saveAttributeDataForStyleable(this, context, iArr, attributeSet, typedArrayObtainStyledAttributes2, i2, 0);
        boolean z3 = typedArrayObtainStyledAttributes2.getBoolean(0, true);
        typedArrayObtainStyledAttributes2.recycle();
        setNestedScrollingEnabled(z3);
    }

    @Override // androidx.core.view.NestedScrollingChild3
    public final void dispatchNestedScroll(int i2, int i3, int i4, int i5, int[] iArr, int i6, @NonNull int[] iArr2) {
        getScrollingChildHelper().dispatchNestedScroll(i2, i3, i4, i5, iArr, i6, iArr2);
    }

    public void smoothScrollBy(@Px int i2, @Px int i3, @Nullable Interpolator interpolator, int i4) {
        g0(i2, i3, interpolator, i4, false);
    }

    public static class LayoutParams extends ViewGroup.MarginLayoutParams {

        /* renamed from: a, reason: collision with root package name */
        ViewHolder f6062a;

        /* renamed from: b, reason: collision with root package name */
        final Rect f6063b;

        /* renamed from: c, reason: collision with root package name */
        boolean f6064c;

        /* renamed from: d, reason: collision with root package name */
        boolean f6065d;

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.f6063b = new Rect();
            this.f6064c = true;
            this.f6065d = false;
        }

        public int getAbsoluteAdapterPosition() {
            return this.f6062a.getAbsoluteAdapterPosition();
        }

        public int getBindingAdapterPosition() {
            return this.f6062a.getBindingAdapterPosition();
        }

        @Deprecated
        public int getViewAdapterPosition() {
            return this.f6062a.getBindingAdapterPosition();
        }

        public int getViewLayoutPosition() {
            return this.f6062a.getLayoutPosition();
        }

        @Deprecated
        public int getViewPosition() {
            return this.f6062a.getPosition();
        }

        public boolean isItemChanged() {
            return this.f6062a.isUpdated();
        }

        public boolean isItemRemoved() {
            return this.f6062a.isRemoved();
        }

        public boolean isViewInvalid() {
            return this.f6062a.isInvalid();
        }

        public boolean viewNeedsUpdate() {
            return this.f6062a.needsUpdate();
        }

        public LayoutParams(int i2, int i3) {
            super(i2, i3);
            this.f6063b = new Rect();
            this.f6064c = true;
            this.f6065d = false;
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
            this.f6063b = new Rect();
            this.f6064c = true;
            this.f6065d = false;
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.f6063b = new Rect();
            this.f6064c = true;
            this.f6065d = false;
        }

        public LayoutParams(LayoutParams layoutParams) {
            super((ViewGroup.LayoutParams) layoutParams);
            this.f6063b = new Rect();
            this.f6064c = true;
            this.f6065d = false;
        }
    }

    @Override // android.view.ViewGroup
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        LayoutManager layoutManager = this.f6026k;
        if (layoutManager != null) {
            return layoutManager.generateLayoutParams(layoutParams);
        }
        throw new IllegalStateException("RecyclerView has no LayoutManager" + z());
    }

    public void addItemDecoration(@NonNull ItemDecoration itemDecoration) {
        addItemDecoration(itemDecoration, -1);
    }
}
