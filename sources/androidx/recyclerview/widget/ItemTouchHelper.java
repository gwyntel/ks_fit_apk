package androidx.recyclerview.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.Interpolator;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.core.view.GestureDetectorCompat;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.R;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class ItemTouchHelper extends RecyclerView.ItemDecoration implements RecyclerView.OnChildAttachStateChangeListener {
    private static final int ACTION_MODE_IDLE_MASK = 255;
    public static final int ACTION_STATE_DRAG = 2;
    public static final int ACTION_STATE_IDLE = 0;
    public static final int ACTION_STATE_SWIPE = 1;
    private static final int ACTIVE_POINTER_ID_NONE = -1;
    public static final int ANIMATION_TYPE_DRAG = 8;
    public static final int ANIMATION_TYPE_SWIPE_CANCEL = 4;
    public static final int ANIMATION_TYPE_SWIPE_SUCCESS = 2;
    private static final boolean DEBUG = false;
    public static final int DOWN = 2;
    public static final int END = 32;
    public static final int LEFT = 4;
    private static final int PIXELS_PER_SECOND = 1000;
    public static final int RIGHT = 8;
    public static final int START = 16;
    private static final String TAG = "ItemTouchHelper";
    public static final int UP = 1;

    /* renamed from: c, reason: collision with root package name */
    float f5916c;

    /* renamed from: d, reason: collision with root package name */
    float f5917d;

    /* renamed from: e, reason: collision with root package name */
    float f5918e;

    /* renamed from: f, reason: collision with root package name */
    float f5919f;

    /* renamed from: h, reason: collision with root package name */
    Callback f5921h;

    /* renamed from: i, reason: collision with root package name */
    int f5922i;

    /* renamed from: k, reason: collision with root package name */
    RecyclerView f5924k;

    /* renamed from: m, reason: collision with root package name */
    VelocityTracker f5926m;
    private List<Integer> mDistances;
    private long mDragScrollStartTimeInMs;
    private ItemTouchHelperGestureListener mItemTouchHelperGestureListener;
    private float mMaxSwipeVelocity;
    private float mSelectedStartX;
    private float mSelectedStartY;
    private int mSlop;
    private List<RecyclerView.ViewHolder> mSwapTargets;
    private float mSwipeEscapeVelocity;
    private Rect mTmpRect;

    /* renamed from: p, reason: collision with root package name */
    GestureDetectorCompat f5929p;

    /* renamed from: a, reason: collision with root package name */
    final List f5914a = new ArrayList();
    private final float[] mTmpPosition = new float[2];

    /* renamed from: b, reason: collision with root package name */
    RecyclerView.ViewHolder f5915b = null;

    /* renamed from: g, reason: collision with root package name */
    int f5920g = -1;
    private int mActionState = 0;

    /* renamed from: j, reason: collision with root package name */
    List f5923j = new ArrayList();

    /* renamed from: l, reason: collision with root package name */
    final Runnable f5925l = new Runnable() { // from class: androidx.recyclerview.widget.ItemTouchHelper.1
        @Override // java.lang.Runnable
        public void run() {
            ItemTouchHelper itemTouchHelper = ItemTouchHelper.this;
            if (itemTouchHelper.f5915b == null || !itemTouchHelper.j()) {
                return;
            }
            ItemTouchHelper itemTouchHelper2 = ItemTouchHelper.this;
            RecyclerView.ViewHolder viewHolder = itemTouchHelper2.f5915b;
            if (viewHolder != null) {
                itemTouchHelper2.f(viewHolder);
            }
            ItemTouchHelper itemTouchHelper3 = ItemTouchHelper.this;
            itemTouchHelper3.f5924k.removeCallbacks(itemTouchHelper3.f5925l);
            ViewCompat.postOnAnimation(ItemTouchHelper.this.f5924k, this);
        }
    };
    private RecyclerView.ChildDrawingOrderCallback mChildDrawingOrderCallback = null;

    /* renamed from: n, reason: collision with root package name */
    View f5927n = null;

    /* renamed from: o, reason: collision with root package name */
    int f5928o = -1;
    private final RecyclerView.OnItemTouchListener mOnItemTouchListener = new RecyclerView.OnItemTouchListener() { // from class: androidx.recyclerview.widget.ItemTouchHelper.2
        @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
        public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
            int iFindPointerIndex;
            RecoverAnimation recoverAnimationC;
            ItemTouchHelper.this.f5929p.onTouchEvent(motionEvent);
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked == 0) {
                ItemTouchHelper.this.f5920g = motionEvent.getPointerId(0);
                ItemTouchHelper.this.f5916c = motionEvent.getX();
                ItemTouchHelper.this.f5917d = motionEvent.getY();
                ItemTouchHelper.this.g();
                ItemTouchHelper itemTouchHelper = ItemTouchHelper.this;
                if (itemTouchHelper.f5915b == null && (recoverAnimationC = itemTouchHelper.c(motionEvent)) != null) {
                    ItemTouchHelper itemTouchHelper2 = ItemTouchHelper.this;
                    itemTouchHelper2.f5916c -= recoverAnimationC.f5949j;
                    itemTouchHelper2.f5917d -= recoverAnimationC.f5950k;
                    itemTouchHelper2.b(recoverAnimationC.f5944e, true);
                    if (ItemTouchHelper.this.f5914a.remove(recoverAnimationC.f5944e.itemView)) {
                        ItemTouchHelper itemTouchHelper3 = ItemTouchHelper.this;
                        itemTouchHelper3.f5921h.clearView(itemTouchHelper3.f5924k, recoverAnimationC.f5944e);
                    }
                    ItemTouchHelper.this.k(recoverAnimationC.f5944e, recoverAnimationC.f5945f);
                    ItemTouchHelper itemTouchHelper4 = ItemTouchHelper.this;
                    itemTouchHelper4.l(motionEvent, itemTouchHelper4.f5922i, 0);
                }
            } else if (actionMasked == 3 || actionMasked == 1) {
                ItemTouchHelper itemTouchHelper5 = ItemTouchHelper.this;
                itemTouchHelper5.f5920g = -1;
                itemTouchHelper5.k(null, 0);
            } else {
                int i2 = ItemTouchHelper.this.f5920g;
                if (i2 != -1 && (iFindPointerIndex = motionEvent.findPointerIndex(i2)) >= 0) {
                    ItemTouchHelper.this.a(actionMasked, motionEvent, iFindPointerIndex);
                }
            }
            VelocityTracker velocityTracker = ItemTouchHelper.this.f5926m;
            if (velocityTracker != null) {
                velocityTracker.addMovement(motionEvent);
            }
            return ItemTouchHelper.this.f5915b != null;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
        public void onRequestDisallowInterceptTouchEvent(boolean z2) {
            if (z2) {
                ItemTouchHelper.this.k(null, 0);
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
        public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
            ItemTouchHelper.this.f5929p.onTouchEvent(motionEvent);
            VelocityTracker velocityTracker = ItemTouchHelper.this.f5926m;
            if (velocityTracker != null) {
                velocityTracker.addMovement(motionEvent);
            }
            if (ItemTouchHelper.this.f5920g == -1) {
                return;
            }
            int actionMasked = motionEvent.getActionMasked();
            int iFindPointerIndex = motionEvent.findPointerIndex(ItemTouchHelper.this.f5920g);
            if (iFindPointerIndex >= 0) {
                ItemTouchHelper.this.a(actionMasked, motionEvent, iFindPointerIndex);
            }
            ItemTouchHelper itemTouchHelper = ItemTouchHelper.this;
            RecyclerView.ViewHolder viewHolder = itemTouchHelper.f5915b;
            if (viewHolder == null) {
                return;
            }
            if (actionMasked != 1) {
                if (actionMasked == 2) {
                    if (iFindPointerIndex >= 0) {
                        itemTouchHelper.l(motionEvent, itemTouchHelper.f5922i, iFindPointerIndex);
                        ItemTouchHelper.this.f(viewHolder);
                        ItemTouchHelper itemTouchHelper2 = ItemTouchHelper.this;
                        itemTouchHelper2.f5924k.removeCallbacks(itemTouchHelper2.f5925l);
                        ItemTouchHelper.this.f5925l.run();
                        ItemTouchHelper.this.f5924k.invalidate();
                        return;
                    }
                    return;
                }
                if (actionMasked != 3) {
                    if (actionMasked != 6) {
                        return;
                    }
                    int actionIndex = motionEvent.getActionIndex();
                    int pointerId = motionEvent.getPointerId(actionIndex);
                    ItemTouchHelper itemTouchHelper3 = ItemTouchHelper.this;
                    if (pointerId == itemTouchHelper3.f5920g) {
                        itemTouchHelper3.f5920g = motionEvent.getPointerId(actionIndex == 0 ? 1 : 0);
                        ItemTouchHelper itemTouchHelper4 = ItemTouchHelper.this;
                        itemTouchHelper4.l(motionEvent, itemTouchHelper4.f5922i, actionIndex);
                        return;
                    }
                    return;
                }
                VelocityTracker velocityTracker2 = itemTouchHelper.f5926m;
                if (velocityTracker2 != null) {
                    velocityTracker2.clear();
                }
            }
            ItemTouchHelper.this.k(null, 0);
            ItemTouchHelper.this.f5920g = -1;
        }
    };

    /* renamed from: androidx.recyclerview.widget.ItemTouchHelper$5, reason: invalid class name */
    class AnonymousClass5 implements RecyclerView.ChildDrawingOrderCallback {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ ItemTouchHelper f5938a;

        @Override // androidx.recyclerview.widget.RecyclerView.ChildDrawingOrderCallback
        public int onGetChildDrawingOrder(int i2, int i3) {
            ItemTouchHelper itemTouchHelper = this.f5938a;
            View view = itemTouchHelper.f5927n;
            if (view == null) {
                return i3;
            }
            int iIndexOfChild = itemTouchHelper.f5928o;
            if (iIndexOfChild == -1) {
                iIndexOfChild = itemTouchHelper.f5924k.indexOfChild(view);
                this.f5938a.f5928o = iIndexOfChild;
            }
            return i3 == i2 + (-1) ? iIndexOfChild : i3 < iIndexOfChild ? i3 : i3 + 1;
        }
    }

    public static abstract class Callback {
        private static final int ABS_HORIZONTAL_DIR_FLAGS = 789516;
        public static final int DEFAULT_DRAG_ANIMATION_DURATION = 200;
        public static final int DEFAULT_SWIPE_ANIMATION_DURATION = 250;
        private static final long DRAG_SCROLL_ACCELERATION_LIMIT_TIME_MS = 2000;
        private static final Interpolator sDragScrollInterpolator = new Interpolator() { // from class: androidx.recyclerview.widget.ItemTouchHelper.Callback.1
            @Override // android.animation.TimeInterpolator
            public float getInterpolation(float f2) {
                return f2 * f2 * f2 * f2 * f2;
            }
        };
        private static final Interpolator sDragViewScrollCapInterpolator = new Interpolator() { // from class: androidx.recyclerview.widget.ItemTouchHelper.Callback.2
            @Override // android.animation.TimeInterpolator
            public float getInterpolation(float f2) {
                float f3 = f2 - 1.0f;
                return (f3 * f3 * f3 * f3 * f3) + 1.0f;
            }
        };
        private int mCachedMaxScrollSpeed = -1;

        public static int convertToRelativeDirection(int i2, int i3) {
            int i4;
            int i5 = i2 & ABS_HORIZONTAL_DIR_FLAGS;
            if (i5 == 0) {
                return i2;
            }
            int i6 = i2 & (~i5);
            if (i3 == 0) {
                i4 = i5 << 2;
            } else {
                int i7 = i5 << 1;
                i6 |= (-789517) & i7;
                i4 = (i7 & ABS_HORIZONTAL_DIR_FLAGS) << 2;
            }
            return i6 | i4;
        }

        @NonNull
        public static ItemTouchUIUtil getDefaultUIUtil() {
            return ItemTouchUIUtilImpl.f5954a;
        }

        private int getMaxDragScroll(RecyclerView recyclerView) {
            if (this.mCachedMaxScrollSpeed == -1) {
                this.mCachedMaxScrollSpeed = recyclerView.getResources().getDimensionPixelSize(R.dimen.item_touch_helper_max_drag_scroll_per_frame);
            }
            return this.mCachedMaxScrollSpeed;
        }

        public static int makeFlag(int i2, int i3) {
            return i3 << (i2 * 8);
        }

        public static int makeMovementFlags(int i2, int i3) {
            return makeFlag(2, i2) | makeFlag(1, i3) | makeFlag(0, i3 | i2);
        }

        final int a(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            return convertToAbsoluteDirection(getMovementFlags(recyclerView, viewHolder), ViewCompat.getLayoutDirection(recyclerView));
        }

        boolean b(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            return (a(recyclerView, viewHolder) & 16711680) != 0;
        }

        boolean c(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            return (a(recyclerView, viewHolder) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) != 0;
        }

        public boolean canDropOver(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder2) {
            return true;
        }

        public RecyclerView.ViewHolder chooseDropTarget(@NonNull RecyclerView.ViewHolder viewHolder, @NonNull List<RecyclerView.ViewHolder> list, int i2, int i3) {
            int bottom;
            int iAbs;
            int top2;
            int iAbs2;
            int left;
            int iAbs3;
            int right;
            int iAbs4;
            int width = i2 + viewHolder.itemView.getWidth();
            int height = i3 + viewHolder.itemView.getHeight();
            int left2 = i2 - viewHolder.itemView.getLeft();
            int top3 = i3 - viewHolder.itemView.getTop();
            int size = list.size();
            RecyclerView.ViewHolder viewHolder2 = null;
            int i4 = -1;
            for (int i5 = 0; i5 < size; i5++) {
                RecyclerView.ViewHolder viewHolder3 = list.get(i5);
                if (left2 > 0 && (right = viewHolder3.itemView.getRight() - width) < 0 && viewHolder3.itemView.getRight() > viewHolder.itemView.getRight() && (iAbs4 = Math.abs(right)) > i4) {
                    viewHolder2 = viewHolder3;
                    i4 = iAbs4;
                }
                if (left2 < 0 && (left = viewHolder3.itemView.getLeft() - i2) > 0 && viewHolder3.itemView.getLeft() < viewHolder.itemView.getLeft() && (iAbs3 = Math.abs(left)) > i4) {
                    viewHolder2 = viewHolder3;
                    i4 = iAbs3;
                }
                if (top3 < 0 && (top2 = viewHolder3.itemView.getTop() - i3) > 0 && viewHolder3.itemView.getTop() < viewHolder.itemView.getTop() && (iAbs2 = Math.abs(top2)) > i4) {
                    viewHolder2 = viewHolder3;
                    i4 = iAbs2;
                }
                if (top3 > 0 && (bottom = viewHolder3.itemView.getBottom() - height) < 0 && viewHolder3.itemView.getBottom() > viewHolder.itemView.getBottom() && (iAbs = Math.abs(bottom)) > i4) {
                    viewHolder2 = viewHolder3;
                    i4 = iAbs;
                }
            }
            return viewHolder2;
        }

        public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            ItemTouchUIUtilImpl.f5954a.clearView(viewHolder.itemView);
        }

        public int convertToAbsoluteDirection(int i2, int i3) {
            int i4;
            int i5 = i2 & 3158064;
            if (i5 == 0) {
                return i2;
            }
            int i6 = i2 & (~i5);
            if (i3 == 0) {
                i4 = i5 >> 2;
            } else {
                int i7 = i5 >> 1;
                i6 |= (-3158065) & i7;
                i4 = (i7 & 3158064) >> 2;
            }
            return i6 | i4;
        }

        void d(Canvas canvas, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, List list, int i2, float f2, float f3) {
            int size = list.size();
            for (int i3 = 0; i3 < size; i3++) {
                RecoverAnimation recoverAnimation = (RecoverAnimation) list.get(i3);
                recoverAnimation.update();
                int iSave = canvas.save();
                onChildDraw(canvas, recyclerView, recoverAnimation.f5944e, recoverAnimation.f5949j, recoverAnimation.f5950k, recoverAnimation.f5945f, false);
                canvas.restoreToCount(iSave);
            }
            if (viewHolder != null) {
                int iSave2 = canvas.save();
                onChildDraw(canvas, recyclerView, viewHolder, f2, f3, i2, true);
                canvas.restoreToCount(iSave2);
            }
        }

        void e(Canvas canvas, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, List list, int i2, float f2, float f3) {
            int size = list.size();
            boolean z2 = false;
            for (int i3 = 0; i3 < size; i3++) {
                RecoverAnimation recoverAnimation = (RecoverAnimation) list.get(i3);
                int iSave = canvas.save();
                onChildDrawOver(canvas, recyclerView, recoverAnimation.f5944e, recoverAnimation.f5949j, recoverAnimation.f5950k, recoverAnimation.f5945f, false);
                canvas.restoreToCount(iSave);
            }
            if (viewHolder != null) {
                int iSave2 = canvas.save();
                onChildDrawOver(canvas, recyclerView, viewHolder, f2, f3, i2, true);
                canvas.restoreToCount(iSave2);
            }
            for (int i4 = size - 1; i4 >= 0; i4--) {
                RecoverAnimation recoverAnimation2 = (RecoverAnimation) list.get(i4);
                boolean z3 = recoverAnimation2.f5952m;
                if (z3 && !recoverAnimation2.f5948i) {
                    list.remove(i4);
                } else if (!z3) {
                    z2 = true;
                }
            }
            if (z2) {
                recyclerView.invalidate();
            }
        }

        public long getAnimationDuration(@NonNull RecyclerView recyclerView, int i2, float f2, float f3) {
            RecyclerView.ItemAnimator itemAnimator = recyclerView.getItemAnimator();
            return itemAnimator == null ? i2 == 8 ? 200L : 250L : i2 == 8 ? itemAnimator.getMoveDuration() : itemAnimator.getRemoveDuration();
        }

        public int getBoundingBoxMargin() {
            return 0;
        }

        public float getMoveThreshold(@NonNull RecyclerView.ViewHolder viewHolder) {
            return 0.5f;
        }

        public abstract int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder);

        public float getSwipeEscapeVelocity(float f2) {
            return f2;
        }

        public float getSwipeThreshold(@NonNull RecyclerView.ViewHolder viewHolder) {
            return 0.5f;
        }

        public float getSwipeVelocityThreshold(float f2) {
            return f2;
        }

        public int interpolateOutOfBoundsScroll(@NonNull RecyclerView recyclerView, int i2, int i3, int i4, long j2) {
            int iSignum = (int) (((int) (((int) Math.signum(i3)) * getMaxDragScroll(recyclerView) * sDragViewScrollCapInterpolator.getInterpolation(Math.min(1.0f, (Math.abs(i3) * 1.0f) / i2)))) * sDragScrollInterpolator.getInterpolation(j2 <= 2000 ? j2 / 2000.0f : 1.0f));
            return iSignum == 0 ? i3 > 0 ? 1 : -1 : iSignum;
        }

        public boolean isItemViewSwipeEnabled() {
            return true;
        }

        public boolean isLongPressDragEnabled() {
            return true;
        }

        public void onChildDraw(@NonNull Canvas canvas, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float f2, float f3, int i2, boolean z2) {
            ItemTouchUIUtilImpl.f5954a.onDraw(canvas, recyclerView, viewHolder.itemView, f2, f3, i2, z2);
        }

        public void onChildDrawOver(@NonNull Canvas canvas, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float f2, float f3, int i2, boolean z2) {
            ItemTouchUIUtilImpl.f5954a.onDrawOver(canvas, recyclerView, viewHolder.itemView, f2, f3, i2, z2);
        }

        public abstract boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder2);

        /* JADX WARN: Multi-variable type inference failed */
        public void onMoved(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, int i2, @NonNull RecyclerView.ViewHolder viewHolder2, int i3, int i4, int i5) {
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof ViewDropHandler) {
                ((ViewDropHandler) layoutManager).prepareForDrop(viewHolder.itemView, viewHolder2.itemView, i4, i5);
                return;
            }
            if (layoutManager.canScrollHorizontally()) {
                if (layoutManager.getDecoratedLeft(viewHolder2.itemView) <= recyclerView.getPaddingLeft()) {
                    recyclerView.scrollToPosition(i3);
                }
                if (layoutManager.getDecoratedRight(viewHolder2.itemView) >= recyclerView.getWidth() - recyclerView.getPaddingRight()) {
                    recyclerView.scrollToPosition(i3);
                }
            }
            if (layoutManager.canScrollVertically()) {
                if (layoutManager.getDecoratedTop(viewHolder2.itemView) <= recyclerView.getPaddingTop()) {
                    recyclerView.scrollToPosition(i3);
                }
                if (layoutManager.getDecoratedBottom(viewHolder2.itemView) >= recyclerView.getHeight() - recyclerView.getPaddingBottom()) {
                    recyclerView.scrollToPosition(i3);
                }
            }
        }

        public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int i2) {
            if (viewHolder != null) {
                ItemTouchUIUtilImpl.f5954a.onSelected(viewHolder.itemView);
            }
        }

        public abstract void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i2);
    }

    private class ItemTouchHelperGestureListener extends GestureDetector.SimpleOnGestureListener {
        private boolean mShouldReactToLongPress = true;

        ItemTouchHelperGestureListener() {
        }

        void a() {
            this.mShouldReactToLongPress = false;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onDown(MotionEvent motionEvent) {
            return true;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public void onLongPress(MotionEvent motionEvent) {
            View viewD;
            RecyclerView.ViewHolder childViewHolder;
            if (!this.mShouldReactToLongPress || (viewD = ItemTouchHelper.this.d(motionEvent)) == null || (childViewHolder = ItemTouchHelper.this.f5924k.getChildViewHolder(viewD)) == null) {
                return;
            }
            ItemTouchHelper itemTouchHelper = ItemTouchHelper.this;
            if (itemTouchHelper.f5921h.b(itemTouchHelper.f5924k, childViewHolder)) {
                int pointerId = motionEvent.getPointerId(0);
                int i2 = ItemTouchHelper.this.f5920g;
                if (pointerId == i2) {
                    int iFindPointerIndex = motionEvent.findPointerIndex(i2);
                    float x2 = motionEvent.getX(iFindPointerIndex);
                    float y2 = motionEvent.getY(iFindPointerIndex);
                    ItemTouchHelper itemTouchHelper2 = ItemTouchHelper.this;
                    itemTouchHelper2.f5916c = x2;
                    itemTouchHelper2.f5917d = y2;
                    itemTouchHelper2.f5919f = 0.0f;
                    itemTouchHelper2.f5918e = 0.0f;
                    if (itemTouchHelper2.f5921h.isLongPressDragEnabled()) {
                        ItemTouchHelper.this.k(childViewHolder, 2);
                    }
                }
            }
        }
    }

    @VisibleForTesting
    static class RecoverAnimation implements Animator.AnimatorListener {

        /* renamed from: a, reason: collision with root package name */
        final float f5940a;

        /* renamed from: b, reason: collision with root package name */
        final float f5941b;

        /* renamed from: c, reason: collision with root package name */
        final float f5942c;

        /* renamed from: d, reason: collision with root package name */
        final float f5943d;

        /* renamed from: e, reason: collision with root package name */
        final RecyclerView.ViewHolder f5944e;

        /* renamed from: f, reason: collision with root package name */
        final int f5945f;

        /* renamed from: g, reason: collision with root package name */
        final ValueAnimator f5946g;

        /* renamed from: h, reason: collision with root package name */
        final int f5947h;

        /* renamed from: i, reason: collision with root package name */
        boolean f5948i;

        /* renamed from: j, reason: collision with root package name */
        float f5949j;

        /* renamed from: k, reason: collision with root package name */
        float f5950k;

        /* renamed from: l, reason: collision with root package name */
        boolean f5951l = false;

        /* renamed from: m, reason: collision with root package name */
        boolean f5952m = false;
        private float mFraction;

        RecoverAnimation(RecyclerView.ViewHolder viewHolder, int i2, int i3, float f2, float f3, float f4, float f5) {
            this.f5945f = i3;
            this.f5947h = i2;
            this.f5944e = viewHolder;
            this.f5940a = f2;
            this.f5941b = f3;
            this.f5942c = f4;
            this.f5943d = f5;
            ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
            this.f5946g = valueAnimatorOfFloat;
            valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: androidx.recyclerview.widget.ItemTouchHelper.RecoverAnimation.1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    RecoverAnimation.this.setFraction(valueAnimator.getAnimatedFraction());
                }
            });
            valueAnimatorOfFloat.setTarget(viewHolder.itemView);
            valueAnimatorOfFloat.addListener(this);
            setFraction(0.0f);
        }

        public void cancel() {
            this.f5946g.cancel();
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationCancel(Animator animator) {
            setFraction(1.0f);
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            if (!this.f5952m) {
                this.f5944e.setIsRecyclable(true);
            }
            this.f5952m = true;
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationRepeat(Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationStart(Animator animator) {
        }

        public void setDuration(long j2) {
            this.f5946g.setDuration(j2);
        }

        public void setFraction(float f2) {
            this.mFraction = f2;
        }

        public void start() {
            this.f5944e.setIsRecyclable(false);
            this.f5946g.start();
        }

        public void update() {
            float f2 = this.f5940a;
            float f3 = this.f5942c;
            if (f2 == f3) {
                this.f5949j = this.f5944e.itemView.getTranslationX();
            } else {
                this.f5949j = f2 + (this.mFraction * (f3 - f2));
            }
            float f4 = this.f5941b;
            float f5 = this.f5943d;
            if (f4 == f5) {
                this.f5950k = this.f5944e.itemView.getTranslationY();
            } else {
                this.f5950k = f4 + (this.mFraction * (f5 - f4));
            }
        }
    }

    public static abstract class SimpleCallback extends Callback {
        private int mDefaultDragDirs;
        private int mDefaultSwipeDirs;

        public SimpleCallback(int i2, int i3) {
            this.mDefaultSwipeDirs = i3;
            this.mDefaultDragDirs = i2;
        }

        public int getDragDirs(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            return this.mDefaultDragDirs;
        }

        @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
        public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            return Callback.makeMovementFlags(getDragDirs(recyclerView, viewHolder), getSwipeDirs(recyclerView, viewHolder));
        }

        public int getSwipeDirs(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            return this.mDefaultSwipeDirs;
        }

        public void setDefaultDragDirs(int i2) {
            this.mDefaultDragDirs = i2;
        }

        public void setDefaultSwipeDirs(int i2) {
            this.mDefaultSwipeDirs = i2;
        }
    }

    public interface ViewDropHandler {
        void prepareForDrop(@NonNull View view, @NonNull View view2, int i2, int i3);
    }

    public ItemTouchHelper(@NonNull Callback callback) {
        this.f5921h = callback;
    }

    private void addChildDrawingOrderCallback() {
    }

    private int checkHorizontalSwipe(RecyclerView.ViewHolder viewHolder, int i2) {
        if ((i2 & 12) == 0) {
            return 0;
        }
        int i3 = this.f5918e > 0.0f ? 8 : 4;
        VelocityTracker velocityTracker = this.f5926m;
        if (velocityTracker != null && this.f5920g > -1) {
            velocityTracker.computeCurrentVelocity(1000, this.f5921h.getSwipeVelocityThreshold(this.mMaxSwipeVelocity));
            float xVelocity = this.f5926m.getXVelocity(this.f5920g);
            float yVelocity = this.f5926m.getYVelocity(this.f5920g);
            int i4 = xVelocity > 0.0f ? 8 : 4;
            float fAbs = Math.abs(xVelocity);
            if ((i4 & i2) != 0 && i3 == i4 && fAbs >= this.f5921h.getSwipeEscapeVelocity(this.mSwipeEscapeVelocity) && fAbs > Math.abs(yVelocity)) {
                return i4;
            }
        }
        float width = this.f5924k.getWidth() * this.f5921h.getSwipeThreshold(viewHolder);
        if ((i2 & i3) == 0 || Math.abs(this.f5918e) <= width) {
            return 0;
        }
        return i3;
    }

    private int checkVerticalSwipe(RecyclerView.ViewHolder viewHolder, int i2) {
        if ((i2 & 3) == 0) {
            return 0;
        }
        int i3 = this.f5919f > 0.0f ? 2 : 1;
        VelocityTracker velocityTracker = this.f5926m;
        if (velocityTracker != null && this.f5920g > -1) {
            velocityTracker.computeCurrentVelocity(1000, this.f5921h.getSwipeVelocityThreshold(this.mMaxSwipeVelocity));
            float xVelocity = this.f5926m.getXVelocity(this.f5920g);
            float yVelocity = this.f5926m.getYVelocity(this.f5920g);
            int i4 = yVelocity > 0.0f ? 2 : 1;
            float fAbs = Math.abs(yVelocity);
            if ((i4 & i2) != 0 && i4 == i3 && fAbs >= this.f5921h.getSwipeEscapeVelocity(this.mSwipeEscapeVelocity) && fAbs > Math.abs(xVelocity)) {
                return i4;
            }
        }
        float height = this.f5924k.getHeight() * this.f5921h.getSwipeThreshold(viewHolder);
        if ((i2 & i3) == 0 || Math.abs(this.f5919f) <= height) {
            return 0;
        }
        return i3;
    }

    private void destroyCallbacks() {
        this.f5924k.removeItemDecoration(this);
        this.f5924k.removeOnItemTouchListener(this.mOnItemTouchListener);
        this.f5924k.removeOnChildAttachStateChangeListener(this);
        for (int size = this.f5923j.size() - 1; size >= 0; size--) {
            RecoverAnimation recoverAnimation = (RecoverAnimation) this.f5923j.get(0);
            recoverAnimation.cancel();
            this.f5921h.clearView(this.f5924k, recoverAnimation.f5944e);
        }
        this.f5923j.clear();
        this.f5927n = null;
        this.f5928o = -1;
        releaseVelocityTracker();
        stopGestureDetection();
    }

    private List<RecyclerView.ViewHolder> findSwapTargets(RecyclerView.ViewHolder viewHolder) {
        RecyclerView.ViewHolder viewHolder2 = viewHolder;
        List<RecyclerView.ViewHolder> list = this.mSwapTargets;
        if (list == null) {
            this.mSwapTargets = new ArrayList();
            this.mDistances = new ArrayList();
        } else {
            list.clear();
            this.mDistances.clear();
        }
        int boundingBoxMargin = this.f5921h.getBoundingBoxMargin();
        int iRound = Math.round(this.mSelectedStartX + this.f5918e) - boundingBoxMargin;
        int iRound2 = Math.round(this.mSelectedStartY + this.f5919f) - boundingBoxMargin;
        int i2 = boundingBoxMargin * 2;
        int width = viewHolder2.itemView.getWidth() + iRound + i2;
        int height = viewHolder2.itemView.getHeight() + iRound2 + i2;
        int i3 = (iRound + width) / 2;
        int i4 = (iRound2 + height) / 2;
        RecyclerView.LayoutManager layoutManager = this.f5924k.getLayoutManager();
        int childCount = layoutManager.getChildCount();
        int i5 = 0;
        while (i5 < childCount) {
            View childAt = layoutManager.getChildAt(i5);
            if (childAt != viewHolder2.itemView && childAt.getBottom() >= iRound2 && childAt.getTop() <= height && childAt.getRight() >= iRound && childAt.getLeft() <= width) {
                RecyclerView.ViewHolder childViewHolder = this.f5924k.getChildViewHolder(childAt);
                if (this.f5921h.canDropOver(this.f5924k, this.f5915b, childViewHolder)) {
                    int iAbs = Math.abs(i3 - ((childAt.getLeft() + childAt.getRight()) / 2));
                    int iAbs2 = Math.abs(i4 - ((childAt.getTop() + childAt.getBottom()) / 2));
                    int i6 = (iAbs * iAbs) + (iAbs2 * iAbs2);
                    int size = this.mSwapTargets.size();
                    int i7 = 0;
                    for (int i8 = 0; i8 < size && i6 > this.mDistances.get(i8).intValue(); i8++) {
                        i7++;
                    }
                    this.mSwapTargets.add(i7, childViewHolder);
                    this.mDistances.add(i7, Integer.valueOf(i6));
                }
            }
            i5++;
            viewHolder2 = viewHolder;
        }
        return this.mSwapTargets;
    }

    private RecyclerView.ViewHolder findSwipedView(MotionEvent motionEvent) {
        View viewD;
        RecyclerView.LayoutManager layoutManager = this.f5924k.getLayoutManager();
        int i2 = this.f5920g;
        if (i2 == -1) {
            return null;
        }
        int iFindPointerIndex = motionEvent.findPointerIndex(i2);
        float x2 = motionEvent.getX(iFindPointerIndex) - this.f5916c;
        float y2 = motionEvent.getY(iFindPointerIndex) - this.f5917d;
        float fAbs = Math.abs(x2);
        float fAbs2 = Math.abs(y2);
        int i3 = this.mSlop;
        if (fAbs < i3 && fAbs2 < i3) {
            return null;
        }
        if (fAbs > fAbs2 && layoutManager.canScrollHorizontally()) {
            return null;
        }
        if ((fAbs2 <= fAbs || !layoutManager.canScrollVertically()) && (viewD = d(motionEvent)) != null) {
            return this.f5924k.getChildViewHolder(viewD);
        }
        return null;
    }

    private void getSelectedDxDy(float[] fArr) {
        if ((this.f5922i & 12) != 0) {
            fArr[0] = (this.mSelectedStartX + this.f5918e) - this.f5915b.itemView.getLeft();
        } else {
            fArr[0] = this.f5915b.itemView.getTranslationX();
        }
        if ((this.f5922i & 3) != 0) {
            fArr[1] = (this.mSelectedStartY + this.f5919f) - this.f5915b.itemView.getTop();
        } else {
            fArr[1] = this.f5915b.itemView.getTranslationY();
        }
    }

    private static boolean hitTest(View view, float f2, float f3, float f4, float f5) {
        return f2 >= f4 && f2 <= f4 + ((float) view.getWidth()) && f3 >= f5 && f3 <= f5 + ((float) view.getHeight());
    }

    private void releaseVelocityTracker() {
        VelocityTracker velocityTracker = this.f5926m;
        if (velocityTracker != null) {
            velocityTracker.recycle();
            this.f5926m = null;
        }
    }

    private void setupCallbacks() {
        this.mSlop = ViewConfiguration.get(this.f5924k.getContext()).getScaledTouchSlop();
        this.f5924k.addItemDecoration(this);
        this.f5924k.addOnItemTouchListener(this.mOnItemTouchListener);
        this.f5924k.addOnChildAttachStateChangeListener(this);
        startGestureDetection();
    }

    private void startGestureDetection() {
        this.mItemTouchHelperGestureListener = new ItemTouchHelperGestureListener();
        this.f5929p = new GestureDetectorCompat(this.f5924k.getContext(), this.mItemTouchHelperGestureListener);
    }

    private void stopGestureDetection() {
        ItemTouchHelperGestureListener itemTouchHelperGestureListener = this.mItemTouchHelperGestureListener;
        if (itemTouchHelperGestureListener != null) {
            itemTouchHelperGestureListener.a();
            this.mItemTouchHelperGestureListener = null;
        }
        if (this.f5929p != null) {
            this.f5929p = null;
        }
    }

    private int swipeIfNecessary(RecyclerView.ViewHolder viewHolder) {
        if (this.mActionState == 2) {
            return 0;
        }
        int movementFlags = this.f5921h.getMovementFlags(this.f5924k, viewHolder);
        int iConvertToAbsoluteDirection = (this.f5921h.convertToAbsoluteDirection(movementFlags, ViewCompat.getLayoutDirection(this.f5924k)) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8;
        if (iConvertToAbsoluteDirection == 0) {
            return 0;
        }
        int i2 = (movementFlags & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8;
        if (Math.abs(this.f5918e) > Math.abs(this.f5919f)) {
            int iCheckHorizontalSwipe = checkHorizontalSwipe(viewHolder, iConvertToAbsoluteDirection);
            if (iCheckHorizontalSwipe > 0) {
                return (i2 & iCheckHorizontalSwipe) == 0 ? Callback.convertToRelativeDirection(iCheckHorizontalSwipe, ViewCompat.getLayoutDirection(this.f5924k)) : iCheckHorizontalSwipe;
            }
            int iCheckVerticalSwipe = checkVerticalSwipe(viewHolder, iConvertToAbsoluteDirection);
            if (iCheckVerticalSwipe > 0) {
                return iCheckVerticalSwipe;
            }
        } else {
            int iCheckVerticalSwipe2 = checkVerticalSwipe(viewHolder, iConvertToAbsoluteDirection);
            if (iCheckVerticalSwipe2 > 0) {
                return iCheckVerticalSwipe2;
            }
            int iCheckHorizontalSwipe2 = checkHorizontalSwipe(viewHolder, iConvertToAbsoluteDirection);
            if (iCheckHorizontalSwipe2 > 0) {
                return (i2 & iCheckHorizontalSwipe2) == 0 ? Callback.convertToRelativeDirection(iCheckHorizontalSwipe2, ViewCompat.getLayoutDirection(this.f5924k)) : iCheckHorizontalSwipe2;
            }
        }
        return 0;
    }

    void a(int i2, MotionEvent motionEvent, int i3) {
        RecyclerView.ViewHolder viewHolderFindSwipedView;
        int iA;
        if (this.f5915b != null || i2 != 2 || this.mActionState == 2 || !this.f5921h.isItemViewSwipeEnabled() || this.f5924k.getScrollState() == 1 || (viewHolderFindSwipedView = findSwipedView(motionEvent)) == null || (iA = (this.f5921h.a(this.f5924k, viewHolderFindSwipedView) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8) == 0) {
            return;
        }
        float x2 = motionEvent.getX(i3);
        float y2 = motionEvent.getY(i3);
        float f2 = x2 - this.f5916c;
        float f3 = y2 - this.f5917d;
        float fAbs = Math.abs(f2);
        float fAbs2 = Math.abs(f3);
        int i4 = this.mSlop;
        if (fAbs >= i4 || fAbs2 >= i4) {
            if (fAbs > fAbs2) {
                if (f2 < 0.0f && (iA & 4) == 0) {
                    return;
                }
                if (f2 > 0.0f && (iA & 8) == 0) {
                    return;
                }
            } else {
                if (f3 < 0.0f && (iA & 1) == 0) {
                    return;
                }
                if (f3 > 0.0f && (iA & 2) == 0) {
                    return;
                }
            }
            this.f5919f = 0.0f;
            this.f5918e = 0.0f;
            this.f5920g = motionEvent.getPointerId(0);
            k(viewHolderFindSwipedView, 1);
        }
    }

    public void attachToRecyclerView(@Nullable RecyclerView recyclerView) {
        RecyclerView recyclerView2 = this.f5924k;
        if (recyclerView2 == recyclerView) {
            return;
        }
        if (recyclerView2 != null) {
            destroyCallbacks();
        }
        this.f5924k = recyclerView;
        if (recyclerView != null) {
            Resources resources = recyclerView.getResources();
            this.mSwipeEscapeVelocity = resources.getDimension(R.dimen.item_touch_helper_swipe_escape_velocity);
            this.mMaxSwipeVelocity = resources.getDimension(R.dimen.item_touch_helper_swipe_escape_max_velocity);
            setupCallbacks();
        }
    }

    void b(RecyclerView.ViewHolder viewHolder, boolean z2) {
        for (int size = this.f5923j.size() - 1; size >= 0; size--) {
            RecoverAnimation recoverAnimation = (RecoverAnimation) this.f5923j.get(size);
            if (recoverAnimation.f5944e == viewHolder) {
                recoverAnimation.f5951l |= z2;
                if (!recoverAnimation.f5952m) {
                    recoverAnimation.cancel();
                }
                this.f5923j.remove(size);
                return;
            }
        }
    }

    RecoverAnimation c(MotionEvent motionEvent) {
        if (this.f5923j.isEmpty()) {
            return null;
        }
        View viewD = d(motionEvent);
        for (int size = this.f5923j.size() - 1; size >= 0; size--) {
            RecoverAnimation recoverAnimation = (RecoverAnimation) this.f5923j.get(size);
            if (recoverAnimation.f5944e.itemView == viewD) {
                return recoverAnimation;
            }
        }
        return null;
    }

    View d(MotionEvent motionEvent) {
        float x2 = motionEvent.getX();
        float y2 = motionEvent.getY();
        RecyclerView.ViewHolder viewHolder = this.f5915b;
        if (viewHolder != null) {
            View view = viewHolder.itemView;
            if (hitTest(view, x2, y2, this.mSelectedStartX + this.f5918e, this.mSelectedStartY + this.f5919f)) {
                return view;
            }
        }
        for (int size = this.f5923j.size() - 1; size >= 0; size--) {
            RecoverAnimation recoverAnimation = (RecoverAnimation) this.f5923j.get(size);
            View view2 = recoverAnimation.f5944e.itemView;
            if (hitTest(view2, x2, y2, recoverAnimation.f5949j, recoverAnimation.f5950k)) {
                return view2;
            }
        }
        return this.f5924k.findChildViewUnder(x2, y2);
    }

    boolean e() {
        int size = this.f5923j.size();
        for (int i2 = 0; i2 < size; i2++) {
            if (!((RecoverAnimation) this.f5923j.get(i2)).f5952m) {
                return true;
            }
        }
        return false;
    }

    void f(RecyclerView.ViewHolder viewHolder) {
        if (!this.f5924k.isLayoutRequested() && this.mActionState == 2) {
            float moveThreshold = this.f5921h.getMoveThreshold(viewHolder);
            int i2 = (int) (this.mSelectedStartX + this.f5918e);
            int i3 = (int) (this.mSelectedStartY + this.f5919f);
            if (Math.abs(i3 - viewHolder.itemView.getTop()) >= viewHolder.itemView.getHeight() * moveThreshold || Math.abs(i2 - viewHolder.itemView.getLeft()) >= viewHolder.itemView.getWidth() * moveThreshold) {
                List<RecyclerView.ViewHolder> listFindSwapTargets = findSwapTargets(viewHolder);
                if (listFindSwapTargets.size() == 0) {
                    return;
                }
                RecyclerView.ViewHolder viewHolderChooseDropTarget = this.f5921h.chooseDropTarget(viewHolder, listFindSwapTargets, i2, i3);
                if (viewHolderChooseDropTarget == null) {
                    this.mSwapTargets.clear();
                    this.mDistances.clear();
                    return;
                }
                int absoluteAdapterPosition = viewHolderChooseDropTarget.getAbsoluteAdapterPosition();
                int absoluteAdapterPosition2 = viewHolder.getAbsoluteAdapterPosition();
                if (this.f5921h.onMove(this.f5924k, viewHolder, viewHolderChooseDropTarget)) {
                    this.f5921h.onMoved(this.f5924k, viewHolder, absoluteAdapterPosition2, viewHolderChooseDropTarget, absoluteAdapterPosition, i2, i3);
                }
            }
        }
    }

    void g() {
        VelocityTracker velocityTracker = this.f5926m;
        if (velocityTracker != null) {
            velocityTracker.recycle();
        }
        this.f5926m = VelocityTracker.obtain();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
        rect.setEmpty();
    }

    void h(final RecoverAnimation recoverAnimation, final int i2) {
        this.f5924k.post(new Runnable() { // from class: androidx.recyclerview.widget.ItemTouchHelper.4
            @Override // java.lang.Runnable
            public void run() {
                RecyclerView recyclerView = ItemTouchHelper.this.f5924k;
                if (recyclerView == null || !recyclerView.isAttachedToWindow()) {
                    return;
                }
                RecoverAnimation recoverAnimation2 = recoverAnimation;
                if (recoverAnimation2.f5951l || recoverAnimation2.f5944e.getAbsoluteAdapterPosition() == -1) {
                    return;
                }
                RecyclerView.ItemAnimator itemAnimator = ItemTouchHelper.this.f5924k.getItemAnimator();
                if ((itemAnimator == null || !itemAnimator.isRunning(null)) && !ItemTouchHelper.this.e()) {
                    ItemTouchHelper.this.f5921h.onSwiped(recoverAnimation.f5944e, i2);
                } else {
                    ItemTouchHelper.this.f5924k.post(this);
                }
            }
        });
    }

    void i(View view) {
        if (view == this.f5927n) {
            this.f5927n = null;
            if (this.mChildDrawingOrderCallback != null) {
                this.f5924k.setChildDrawingOrderCallback(null);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x007d  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00c4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    boolean j() {
        /*
            Method dump skipped, instructions count: 277
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.ItemTouchHelper.j():boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x0121  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    void k(androidx.recyclerview.widget.RecyclerView.ViewHolder r24, int r25) {
        /*
            Method dump skipped, instructions count: 334
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.ItemTouchHelper.k(androidx.recyclerview.widget.RecyclerView$ViewHolder, int):void");
    }

    void l(MotionEvent motionEvent, int i2, int i3) {
        float x2 = motionEvent.getX(i3);
        float y2 = motionEvent.getY(i3);
        float f2 = x2 - this.f5916c;
        this.f5918e = f2;
        this.f5919f = y2 - this.f5917d;
        if ((i2 & 4) == 0) {
            this.f5918e = Math.max(0.0f, f2);
        }
        if ((i2 & 8) == 0) {
            this.f5918e = Math.min(0.0f, this.f5918e);
        }
        if ((i2 & 1) == 0) {
            this.f5919f = Math.max(0.0f, this.f5919f);
        }
        if ((i2 & 2) == 0) {
            this.f5919f = Math.min(0.0f, this.f5919f);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.OnChildAttachStateChangeListener
    public void onChildViewAttachedToWindow(@NonNull View view) {
    }

    @Override // androidx.recyclerview.widget.RecyclerView.OnChildAttachStateChangeListener
    public void onChildViewDetachedFromWindow(@NonNull View view) {
        i(view);
        RecyclerView.ViewHolder childViewHolder = this.f5924k.getChildViewHolder(view);
        if (childViewHolder == null) {
            return;
        }
        RecyclerView.ViewHolder viewHolder = this.f5915b;
        if (viewHolder != null && childViewHolder == viewHolder) {
            k(null, 0);
            return;
        }
        b(childViewHolder, false);
        if (this.f5914a.remove(childViewHolder.itemView)) {
            this.f5921h.clearView(this.f5924k, childViewHolder);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void onDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
        float f2;
        float f3;
        this.f5928o = -1;
        if (this.f5915b != null) {
            getSelectedDxDy(this.mTmpPosition);
            float[] fArr = this.mTmpPosition;
            float f4 = fArr[0];
            f3 = fArr[1];
            f2 = f4;
        } else {
            f2 = 0.0f;
            f3 = 0.0f;
        }
        this.f5921h.d(canvas, recyclerView, this.f5915b, this.f5923j, this.mActionState, f2, f3);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void onDrawOver(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
        float f2;
        float f3;
        if (this.f5915b != null) {
            getSelectedDxDy(this.mTmpPosition);
            float[] fArr = this.mTmpPosition;
            float f4 = fArr[0];
            f3 = fArr[1];
            f2 = f4;
        } else {
            f2 = 0.0f;
            f3 = 0.0f;
        }
        this.f5921h.e(canvas, recyclerView, this.f5915b, this.f5923j, this.mActionState, f2, f3);
    }

    public void startDrag(@NonNull RecyclerView.ViewHolder viewHolder) {
        if (!this.f5921h.b(this.f5924k, viewHolder)) {
            Log.e(TAG, "Start drag has been called but dragging is not enabled");
            return;
        }
        if (viewHolder.itemView.getParent() != this.f5924k) {
            Log.e(TAG, "Start drag has been called with a view holder which is not a child of the RecyclerView which is controlled by this ItemTouchHelper.");
            return;
        }
        g();
        this.f5919f = 0.0f;
        this.f5918e = 0.0f;
        k(viewHolder, 2);
    }

    public void startSwipe(@NonNull RecyclerView.ViewHolder viewHolder) {
        if (!this.f5921h.c(this.f5924k, viewHolder)) {
            Log.e(TAG, "Start swipe has been called but swiping is not enabled");
            return;
        }
        if (viewHolder.itemView.getParent() != this.f5924k) {
            Log.e(TAG, "Start swipe has been called with a view holder which is not a child of the RecyclerView controlled by this ItemTouchHelper.");
            return;
        }
        g();
        this.f5919f = 0.0f;
        this.f5918e = 0.0f;
        k(viewHolder, 1);
    }
}
