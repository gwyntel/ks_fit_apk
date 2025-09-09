package androidx.constraintlayout.helper.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import androidx.constraintlayout.motion.widget.MotionHelper;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.motion.widget.MotionScene;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.R;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes.dex */
public class Carousel extends MotionHelper {
    private static final boolean DEBUG = false;
    private static final String TAG = "Carousel";
    public static final int TOUCH_UP_CARRY_ON = 2;
    public static final int TOUCH_UP_IMMEDIATE_STOP = 1;
    private int backwardTransition;
    private float dampening;
    private int emptyViewBehavior;
    private int firstViewReference;
    private int forwardTransition;
    private boolean infiniteCarousel;

    /* renamed from: j, reason: collision with root package name */
    int f2962j;

    /* renamed from: k, reason: collision with root package name */
    Runnable f2963k;
    private Adapter mAdapter;
    private int mAnimateTargetDelay;
    private int mIndex;
    private final ArrayList<View> mList;
    private MotionLayout mMotionLayout;
    private int mPreviousIndex;
    private int mTargetIndex;
    private int nextState;
    private int previousState;
    private int startIndex;
    private int touchUpMode;
    private float velocityThreshold;

    public interface Adapter {
        int count();

        void onNewItem(int i2);

        void populate(View view, int i2);
    }

    public Carousel(Context context) {
        super(context);
        this.mAdapter = null;
        this.mList = new ArrayList<>();
        this.mPreviousIndex = 0;
        this.mIndex = 0;
        this.firstViewReference = -1;
        this.infiniteCarousel = false;
        this.backwardTransition = -1;
        this.forwardTransition = -1;
        this.previousState = -1;
        this.nextState = -1;
        this.dampening = 0.9f;
        this.startIndex = 0;
        this.emptyViewBehavior = 4;
        this.touchUpMode = 1;
        this.velocityThreshold = 2.0f;
        this.mTargetIndex = -1;
        this.mAnimateTargetDelay = 200;
        this.f2962j = -1;
        this.f2963k = new Runnable() { // from class: androidx.constraintlayout.helper.widget.Carousel.1
            @Override // java.lang.Runnable
            public void run() {
                Carousel.this.mMotionLayout.setProgress(0.0f);
                Carousel.this.updateItems();
                Carousel.this.mAdapter.onNewItem(Carousel.this.mIndex);
                float velocity = Carousel.this.mMotionLayout.getVelocity();
                if (Carousel.this.touchUpMode != 2 || velocity <= Carousel.this.velocityThreshold || Carousel.this.mIndex >= Carousel.this.mAdapter.count() - 1) {
                    return;
                }
                final float f2 = velocity * Carousel.this.dampening;
                if (Carousel.this.mIndex != 0 || Carousel.this.mPreviousIndex <= Carousel.this.mIndex) {
                    if (Carousel.this.mIndex != Carousel.this.mAdapter.count() - 1 || Carousel.this.mPreviousIndex >= Carousel.this.mIndex) {
                        Carousel.this.mMotionLayout.post(new Runnable() { // from class: androidx.constraintlayout.helper.widget.Carousel.1.1
                            @Override // java.lang.Runnable
                            public void run() {
                                Carousel.this.mMotionLayout.touchAnimateTo(5, 1.0f, f2);
                            }
                        });
                    }
                }
            }
        };
    }

    private void enableAllTransitions(boolean z2) {
        Iterator<MotionScene.Transition> it = this.mMotionLayout.getDefinedTransitions().iterator();
        while (it.hasNext()) {
            it.next().setEnabled(z2);
        }
    }

    private boolean enableTransition(int i2, boolean z2) {
        MotionLayout motionLayout;
        MotionScene.Transition transition;
        if (i2 == -1 || (motionLayout = this.mMotionLayout) == null || (transition = motionLayout.getTransition(i2)) == null || z2 == transition.isEnabled()) {
            return false;
        }
        transition.setEnabled(z2);
        return true;
    }

    private void init(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.Carousel);
            int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
            for (int i2 = 0; i2 < indexCount; i2++) {
                int index = typedArrayObtainStyledAttributes.getIndex(i2);
                if (index == R.styleable.Carousel_carousel_firstView) {
                    this.firstViewReference = typedArrayObtainStyledAttributes.getResourceId(index, this.firstViewReference);
                } else if (index == R.styleable.Carousel_carousel_backwardTransition) {
                    this.backwardTransition = typedArrayObtainStyledAttributes.getResourceId(index, this.backwardTransition);
                } else if (index == R.styleable.Carousel_carousel_forwardTransition) {
                    this.forwardTransition = typedArrayObtainStyledAttributes.getResourceId(index, this.forwardTransition);
                } else if (index == R.styleable.Carousel_carousel_emptyViewsBehavior) {
                    this.emptyViewBehavior = typedArrayObtainStyledAttributes.getInt(index, this.emptyViewBehavior);
                } else if (index == R.styleable.Carousel_carousel_previousState) {
                    this.previousState = typedArrayObtainStyledAttributes.getResourceId(index, this.previousState);
                } else if (index == R.styleable.Carousel_carousel_nextState) {
                    this.nextState = typedArrayObtainStyledAttributes.getResourceId(index, this.nextState);
                } else if (index == R.styleable.Carousel_carousel_touchUp_dampeningFactor) {
                    this.dampening = typedArrayObtainStyledAttributes.getFloat(index, this.dampening);
                } else if (index == R.styleable.Carousel_carousel_touchUpMode) {
                    this.touchUpMode = typedArrayObtainStyledAttributes.getInt(index, this.touchUpMode);
                } else if (index == R.styleable.Carousel_carousel_touchUp_velocityThreshold) {
                    this.velocityThreshold = typedArrayObtainStyledAttributes.getFloat(index, this.velocityThreshold);
                } else if (index == R.styleable.Carousel_carousel_infinite) {
                    this.infiniteCarousel = typedArrayObtainStyledAttributes.getBoolean(index, this.infiniteCarousel);
                }
            }
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateItems$0() {
        this.mMotionLayout.setTransitionDuration(this.mAnimateTargetDelay);
        if (this.mTargetIndex < this.mIndex) {
            this.mMotionLayout.transitionToState(this.previousState, this.mAnimateTargetDelay);
        } else {
            this.mMotionLayout.transitionToState(this.nextState, this.mAnimateTargetDelay);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateItems() {
        Adapter adapter = this.mAdapter;
        if (adapter == null || this.mMotionLayout == null || adapter.count() == 0) {
            return;
        }
        int size = this.mList.size();
        for (int i2 = 0; i2 < size; i2++) {
            View view = this.mList.get(i2);
            int iCount = (this.mIndex + i2) - this.startIndex;
            if (this.infiniteCarousel) {
                if (iCount < 0) {
                    int i3 = this.emptyViewBehavior;
                    if (i3 != 4) {
                        updateViewVisibility(view, i3);
                    } else {
                        updateViewVisibility(view, 0);
                    }
                    if (iCount % this.mAdapter.count() == 0) {
                        this.mAdapter.populate(view, 0);
                    } else {
                        Adapter adapter2 = this.mAdapter;
                        adapter2.populate(view, adapter2.count() + (iCount % this.mAdapter.count()));
                    }
                } else if (iCount >= this.mAdapter.count()) {
                    if (iCount == this.mAdapter.count()) {
                        iCount = 0;
                    } else if (iCount > this.mAdapter.count()) {
                        iCount %= this.mAdapter.count();
                    }
                    int i4 = this.emptyViewBehavior;
                    if (i4 != 4) {
                        updateViewVisibility(view, i4);
                    } else {
                        updateViewVisibility(view, 0);
                    }
                    this.mAdapter.populate(view, iCount);
                } else {
                    updateViewVisibility(view, 0);
                    this.mAdapter.populate(view, iCount);
                }
            } else if (iCount < 0) {
                updateViewVisibility(view, this.emptyViewBehavior);
            } else if (iCount >= this.mAdapter.count()) {
                updateViewVisibility(view, this.emptyViewBehavior);
            } else {
                updateViewVisibility(view, 0);
                this.mAdapter.populate(view, iCount);
            }
        }
        int i5 = this.mTargetIndex;
        if (i5 != -1 && i5 != this.mIndex) {
            this.mMotionLayout.post(new Runnable() { // from class: androidx.constraintlayout.helper.widget.a
                @Override // java.lang.Runnable
                public final void run() {
                    this.f2979a.lambda$updateItems$0();
                }
            });
        } else if (i5 == this.mIndex) {
            this.mTargetIndex = -1;
        }
        if (this.backwardTransition == -1 || this.forwardTransition == -1) {
            Log.w(TAG, "No backward or forward transitions defined for Carousel!");
            return;
        }
        if (this.infiniteCarousel) {
            return;
        }
        int iCount2 = this.mAdapter.count();
        if (this.mIndex == 0) {
            enableTransition(this.backwardTransition, false);
        } else {
            enableTransition(this.backwardTransition, true);
            this.mMotionLayout.setTransition(this.backwardTransition);
        }
        if (this.mIndex == iCount2 - 1) {
            enableTransition(this.forwardTransition, false);
        } else {
            enableTransition(this.forwardTransition, true);
            this.mMotionLayout.setTransition(this.forwardTransition);
        }
    }

    private boolean updateViewVisibility(View view, int i2) {
        MotionLayout motionLayout = this.mMotionLayout;
        if (motionLayout == null) {
            return false;
        }
        boolean zUpdateViewVisibility = false;
        for (int i3 : motionLayout.getConstraintSetIds()) {
            zUpdateViewVisibility |= updateViewVisibility(i3, view, i2);
        }
        return zUpdateViewVisibility;
    }

    public int getCount() {
        Adapter adapter = this.mAdapter;
        if (adapter != null) {
            return adapter.count();
        }
        return 0;
    }

    public int getCurrentIndex() {
        return this.mIndex;
    }

    public void jumpToIndex(int i2) {
        this.mIndex = Math.max(0, Math.min(getCount() - 1, i2));
        refresh();
    }

    @Override // androidx.constraintlayout.widget.ConstraintHelper, android.view.View
    protected void onAttachedToWindow() throws IllegalAccessException, Resources.NotFoundException, IllegalArgumentException {
        super.onAttachedToWindow();
        if (getParent() instanceof MotionLayout) {
            MotionLayout motionLayout = (MotionLayout) getParent();
            for (int i2 = 0; i2 < this.f3219b; i2++) {
                int i3 = this.f3218a[i2];
                View viewById = motionLayout.getViewById(i3);
                if (this.firstViewReference == i3) {
                    this.startIndex = i2;
                }
                this.mList.add(viewById);
            }
            this.mMotionLayout = motionLayout;
            if (this.touchUpMode == 2) {
                MotionScene.Transition transition = motionLayout.getTransition(this.forwardTransition);
                if (transition != null) {
                    transition.setOnTouchUp(5);
                }
                MotionScene.Transition transition2 = this.mMotionLayout.getTransition(this.backwardTransition);
                if (transition2 != null) {
                    transition2.setOnTouchUp(5);
                }
            }
            updateItems();
        }
    }

    @Override // androidx.constraintlayout.motion.widget.MotionHelper, androidx.constraintlayout.motion.widget.MotionLayout.TransitionListener
    public void onTransitionChange(MotionLayout motionLayout, int i2, int i3, float f2) {
        this.f2962j = i2;
    }

    @Override // androidx.constraintlayout.motion.widget.MotionHelper, androidx.constraintlayout.motion.widget.MotionLayout.TransitionListener
    public void onTransitionCompleted(MotionLayout motionLayout, int i2) {
        int i3 = this.mIndex;
        this.mPreviousIndex = i3;
        if (i2 == this.nextState) {
            this.mIndex = i3 + 1;
        } else if (i2 == this.previousState) {
            this.mIndex = i3 - 1;
        }
        if (this.infiniteCarousel) {
            if (this.mIndex >= this.mAdapter.count()) {
                this.mIndex = 0;
            }
            if (this.mIndex < 0) {
                this.mIndex = this.mAdapter.count() - 1;
            }
        } else {
            if (this.mIndex >= this.mAdapter.count()) {
                this.mIndex = this.mAdapter.count() - 1;
            }
            if (this.mIndex < 0) {
                this.mIndex = 0;
            }
        }
        if (this.mPreviousIndex != this.mIndex) {
            this.mMotionLayout.post(this.f2963k);
        }
    }

    public void refresh() {
        int size = this.mList.size();
        for (int i2 = 0; i2 < size; i2++) {
            View view = this.mList.get(i2);
            if (this.mAdapter.count() == 0) {
                updateViewVisibility(view, this.emptyViewBehavior);
            } else {
                updateViewVisibility(view, 0);
            }
        }
        this.mMotionLayout.rebuildScene();
        updateItems();
    }

    public void setAdapter(Adapter adapter) {
        this.mAdapter = adapter;
    }

    public void transitionToIndex(int i2, int i3) {
        this.mTargetIndex = Math.max(0, Math.min(getCount() - 1, i2));
        int iMax = Math.max(0, i3);
        this.mAnimateTargetDelay = iMax;
        this.mMotionLayout.setTransitionDuration(iMax);
        if (i2 < this.mIndex) {
            this.mMotionLayout.transitionToState(this.previousState, this.mAnimateTargetDelay);
        } else {
            this.mMotionLayout.transitionToState(this.nextState, this.mAnimateTargetDelay);
        }
    }

    private boolean updateViewVisibility(int i2, View view, int i3) {
        ConstraintSet.Constraint constraint;
        ConstraintSet constraintSet = this.mMotionLayout.getConstraintSet(i2);
        if (constraintSet == null || (constraint = constraintSet.getConstraint(view.getId())) == null) {
            return false;
        }
        constraint.propertySet.mVisibilityMode = 1;
        view.setVisibility(i3);
        return true;
    }

    public Carousel(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mAdapter = null;
        this.mList = new ArrayList<>();
        this.mPreviousIndex = 0;
        this.mIndex = 0;
        this.firstViewReference = -1;
        this.infiniteCarousel = false;
        this.backwardTransition = -1;
        this.forwardTransition = -1;
        this.previousState = -1;
        this.nextState = -1;
        this.dampening = 0.9f;
        this.startIndex = 0;
        this.emptyViewBehavior = 4;
        this.touchUpMode = 1;
        this.velocityThreshold = 2.0f;
        this.mTargetIndex = -1;
        this.mAnimateTargetDelay = 200;
        this.f2962j = -1;
        this.f2963k = new Runnable() { // from class: androidx.constraintlayout.helper.widget.Carousel.1
            @Override // java.lang.Runnable
            public void run() {
                Carousel.this.mMotionLayout.setProgress(0.0f);
                Carousel.this.updateItems();
                Carousel.this.mAdapter.onNewItem(Carousel.this.mIndex);
                float velocity = Carousel.this.mMotionLayout.getVelocity();
                if (Carousel.this.touchUpMode != 2 || velocity <= Carousel.this.velocityThreshold || Carousel.this.mIndex >= Carousel.this.mAdapter.count() - 1) {
                    return;
                }
                final float f2 = velocity * Carousel.this.dampening;
                if (Carousel.this.mIndex != 0 || Carousel.this.mPreviousIndex <= Carousel.this.mIndex) {
                    if (Carousel.this.mIndex != Carousel.this.mAdapter.count() - 1 || Carousel.this.mPreviousIndex >= Carousel.this.mIndex) {
                        Carousel.this.mMotionLayout.post(new Runnable() { // from class: androidx.constraintlayout.helper.widget.Carousel.1.1
                            @Override // java.lang.Runnable
                            public void run() {
                                Carousel.this.mMotionLayout.touchAnimateTo(5, 1.0f, f2);
                            }
                        });
                    }
                }
            }
        };
        init(context, attributeSet);
    }

    public Carousel(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mAdapter = null;
        this.mList = new ArrayList<>();
        this.mPreviousIndex = 0;
        this.mIndex = 0;
        this.firstViewReference = -1;
        this.infiniteCarousel = false;
        this.backwardTransition = -1;
        this.forwardTransition = -1;
        this.previousState = -1;
        this.nextState = -1;
        this.dampening = 0.9f;
        this.startIndex = 0;
        this.emptyViewBehavior = 4;
        this.touchUpMode = 1;
        this.velocityThreshold = 2.0f;
        this.mTargetIndex = -1;
        this.mAnimateTargetDelay = 200;
        this.f2962j = -1;
        this.f2963k = new Runnable() { // from class: androidx.constraintlayout.helper.widget.Carousel.1
            @Override // java.lang.Runnable
            public void run() {
                Carousel.this.mMotionLayout.setProgress(0.0f);
                Carousel.this.updateItems();
                Carousel.this.mAdapter.onNewItem(Carousel.this.mIndex);
                float velocity = Carousel.this.mMotionLayout.getVelocity();
                if (Carousel.this.touchUpMode != 2 || velocity <= Carousel.this.velocityThreshold || Carousel.this.mIndex >= Carousel.this.mAdapter.count() - 1) {
                    return;
                }
                final float f2 = velocity * Carousel.this.dampening;
                if (Carousel.this.mIndex != 0 || Carousel.this.mPreviousIndex <= Carousel.this.mIndex) {
                    if (Carousel.this.mIndex != Carousel.this.mAdapter.count() - 1 || Carousel.this.mPreviousIndex >= Carousel.this.mIndex) {
                        Carousel.this.mMotionLayout.post(new Runnable() { // from class: androidx.constraintlayout.helper.widget.Carousel.1.1
                            @Override // java.lang.Runnable
                            public void run() {
                                Carousel.this.mMotionLayout.touchAnimateTo(5, 1.0f, f2);
                            }
                        });
                    }
                }
            }
        };
        init(context, attributeSet);
    }
}
