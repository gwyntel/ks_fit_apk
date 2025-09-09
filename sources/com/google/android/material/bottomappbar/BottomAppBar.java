package com.google.android.material.bottomappbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Dimension;
import androidx.annotation.MenuRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.customview.view.AbsSavedState;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.behavior.HideBottomViewOnScrollBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapePathModel;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class BottomAppBar extends Toolbar implements CoordinatorLayout.AttachedBehavior {
    private static final long ANIMATION_DURATION = 300;
    public static final int FAB_ALIGNMENT_MODE_CENTER = 0;
    public static final int FAB_ALIGNMENT_MODE_END = 1;

    /* renamed from: a, reason: collision with root package name */
    AnimatorListenerAdapter f13391a;

    @Nullable
    private Animator attachAnimator;
    private int fabAlignmentMode;
    private boolean fabAttached;
    private final int fabOffsetEndMode;
    private boolean hideOnScroll;
    private final MaterialShapeDrawable materialShapeDrawable;

    @Nullable
    private Animator menuAnimator;

    @Nullable
    private Animator modeAnimator;
    private final BottomAppBarTopEdgeTreatment topEdgeTreatment;

    public static class Behavior extends HideBottomViewOnScrollBehavior<BottomAppBar> {
        private final Rect fabContentRect;

        public Behavior() {
            this.fabContentRect = new Rect();
        }

        private boolean updateFabPositionAndVisibility(FloatingActionButton floatingActionButton, BottomAppBar bottomAppBar) {
            ((CoordinatorLayout.LayoutParams) floatingActionButton.getLayoutParams()).anchorGravity = 17;
            bottomAppBar.addFabAnimationListeners(floatingActionButton);
            return true;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.material.behavior.HideBottomViewOnScrollBehavior
        /* renamed from: d, reason: merged with bridge method [inline-methods] */
        public void b(BottomAppBar bottomAppBar) {
            super.b(bottomAppBar);
            FloatingActionButton floatingActionButtonFindDependentFab = bottomAppBar.findDependentFab();
            if (floatingActionButtonFindDependentFab != null) {
                floatingActionButtonFindDependentFab.getContentRect(this.fabContentRect);
                float measuredHeight = floatingActionButtonFindDependentFab.getMeasuredHeight() - this.fabContentRect.height();
                floatingActionButtonFindDependentFab.clearAnimation();
                floatingActionButtonFindDependentFab.animate().translationY((-floatingActionButtonFindDependentFab.getPaddingBottom()) + measuredHeight).setInterpolator(AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR).setDuration(175L);
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.material.behavior.HideBottomViewOnScrollBehavior
        /* renamed from: e, reason: merged with bridge method [inline-methods] */
        public void c(BottomAppBar bottomAppBar) {
            super.c(bottomAppBar);
            FloatingActionButton floatingActionButtonFindDependentFab = bottomAppBar.findDependentFab();
            if (floatingActionButtonFindDependentFab != null) {
                floatingActionButtonFindDependentFab.clearAnimation();
                floatingActionButtonFindDependentFab.animate().translationY(bottomAppBar.getFabTranslationY()).setInterpolator(AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR).setDuration(225L);
            }
        }

        @Override // com.google.android.material.behavior.HideBottomViewOnScrollBehavior, androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public boolean onLayoutChild(CoordinatorLayout coordinatorLayout, BottomAppBar bottomAppBar, int i2) {
            FloatingActionButton floatingActionButtonFindDependentFab = bottomAppBar.findDependentFab();
            if (floatingActionButtonFindDependentFab != null) {
                updateFabPositionAndVisibility(floatingActionButtonFindDependentFab, bottomAppBar);
                floatingActionButtonFindDependentFab.getMeasuredContentRect(this.fabContentRect);
                bottomAppBar.setFabDiameter(this.fabContentRect.height());
            }
            if (!bottomAppBar.isAnimationRunning()) {
                bottomAppBar.setCutoutState();
            }
            coordinatorLayout.onLayoutChild(bottomAppBar, i2);
            return super.onLayoutChild(coordinatorLayout, (CoordinatorLayout) bottomAppBar, i2);
        }

        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull BottomAppBar bottomAppBar, @NonNull View view, @NonNull View view2, int i2, int i3) {
            return bottomAppBar.getHideOnScroll() && super.onStartNestedScroll(coordinatorLayout, (CoordinatorLayout) bottomAppBar, view, view2, i2, i3);
        }

        public Behavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.fabContentRect = new Rect();
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface FabAlignmentMode {
    }

    static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>() { // from class: com.google.android.material.bottomappbar.BottomAppBar.SavedState.1
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
        int f13402a;

        /* renamed from: b, reason: collision with root package name */
        boolean f13403b;

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        @Override // androidx.customview.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(@NonNull Parcel parcel, int i2) {
            super.writeToParcel(parcel, i2);
            parcel.writeInt(this.f13402a);
            parcel.writeInt(this.f13403b ? 1 : 0);
        }

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.f13402a = parcel.readInt();
            this.f13403b = parcel.readInt() != 0;
        }
    }

    public BottomAppBar(Context context) {
        this(context, null, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addFabAnimationListeners(@NonNull FloatingActionButton floatingActionButton) {
        removeFabAnimationListeners(floatingActionButton);
        floatingActionButton.addOnHideAnimationListener(this.f13391a);
        floatingActionButton.addOnShowAnimationListener(this.f13391a);
    }

    private void cancelAnimations() {
        Animator animator = this.attachAnimator;
        if (animator != null) {
            animator.cancel();
        }
        Animator animator2 = this.menuAnimator;
        if (animator2 != null) {
            animator2.cancel();
        }
        Animator animator3 = this.modeAnimator;
        if (animator3 != null) {
            animator3.cancel();
        }
    }

    private void createCradleShapeAnimation(boolean z2, List<Animator> list) {
        if (z2) {
            this.topEdgeTreatment.j(getFabTranslationX());
        }
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(this.materialShapeDrawable.getInterpolation(), z2 ? 1.0f : 0.0f);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.bottomappbar.BottomAppBar.6
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                BottomAppBar.this.materialShapeDrawable.setInterpolation(((Float) valueAnimator.getAnimatedValue()).floatValue());
            }
        });
        valueAnimatorOfFloat.setDuration(300L);
        list.add(valueAnimatorOfFloat);
    }

    private void createCradleTranslationAnimation(int i2, List<Animator> list) {
        if (this.fabAttached) {
            ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(this.topEdgeTreatment.e(), getFabTranslationX(i2));
            valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.bottomappbar.BottomAppBar.2
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    BottomAppBar.this.topEdgeTreatment.j(((Float) valueAnimator.getAnimatedValue()).floatValue());
                    BottomAppBar.this.materialShapeDrawable.invalidateSelf();
                }
            });
            valueAnimatorOfFloat.setDuration(300L);
            list.add(valueAnimatorOfFloat);
        }
    }

    private void createFabTranslationXAnimation(int i2, List<Animator> list) {
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(findDependentFab(), "translationX", getFabTranslationX(i2));
        objectAnimatorOfFloat.setDuration(300L);
        list.add(objectAnimatorOfFloat);
    }

    private void createFabTranslationYAnimation(boolean z2, List<Animator> list) {
        FloatingActionButton floatingActionButtonFindDependentFab = findDependentFab();
        if (floatingActionButtonFindDependentFab == null) {
            return;
        }
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(floatingActionButtonFindDependentFab, "translationY", getFabTranslationY(z2));
        objectAnimatorOfFloat.setDuration(300L);
        list.add(objectAnimatorOfFloat);
    }

    private void createMenuViewTranslationAnimation(final int i2, final boolean z2, List<Animator> list) {
        final ActionMenuView actionMenuView = getActionMenuView();
        if (actionMenuView == null) {
            return;
        }
        Animator animatorOfFloat = ObjectAnimator.ofFloat(actionMenuView, "alpha", 1.0f);
        if ((!this.fabAttached && (!z2 || !isVisibleFab())) || (this.fabAlignmentMode != 1 && i2 != 1)) {
            if (actionMenuView.getAlpha() < 1.0f) {
                list.add(animatorOfFloat);
            }
        } else {
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(actionMenuView, "alpha", 0.0f);
            objectAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.bottomappbar.BottomAppBar.4
                public boolean cancelled;

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animator) {
                    this.cancelled = true;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    if (this.cancelled) {
                        return;
                    }
                    BottomAppBar.this.translateActionMenuView(actionMenuView, i2, z2);
                }
            });
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(150L);
            animatorSet.playSequentially(objectAnimatorOfFloat, animatorOfFloat);
            list.add(animatorSet);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Nullable
    public FloatingActionButton findDependentFab() {
        if (!(getParent() instanceof CoordinatorLayout)) {
            return null;
        }
        for (View view : ((CoordinatorLayout) getParent()).getDependents(this)) {
            if (view instanceof FloatingActionButton) {
                return (FloatingActionButton) view;
            }
        }
        return null;
    }

    @Nullable
    private ActionMenuView getActionMenuView() {
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            View childAt = getChildAt(i2);
            if (childAt instanceof ActionMenuView) {
                return (ActionMenuView) childAt;
            }
        }
        return null;
    }

    private int getFabTranslationX(int i2) {
        boolean z2 = ViewCompat.getLayoutDirection(this) == 1;
        if (i2 == 1) {
            return ((getMeasuredWidth() / 2) - this.fabOffsetEndMode) * (z2 ? -1 : 1);
        }
        return 0;
    }

    private float getFabTranslationY(boolean z2) {
        FloatingActionButton floatingActionButtonFindDependentFab = findDependentFab();
        if (floatingActionButtonFindDependentFab == null) {
            return 0.0f;
        }
        Rect rect = new Rect();
        floatingActionButtonFindDependentFab.getContentRect(rect);
        float fHeight = rect.height();
        if (fHeight == 0.0f) {
            fHeight = floatingActionButtonFindDependentFab.getMeasuredHeight();
        }
        float height = floatingActionButtonFindDependentFab.getHeight() - rect.bottom;
        float height2 = floatingActionButtonFindDependentFab.getHeight() - rect.height();
        float f2 = (-getCradleVerticalOffset()) + (fHeight / 2.0f) + height;
        float paddingBottom = height2 - floatingActionButtonFindDependentFab.getPaddingBottom();
        float f3 = -getMeasuredHeight();
        if (!z2) {
            f2 = paddingBottom;
        }
        return f3 + f2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isAnimationRunning() {
        Animator animator;
        Animator animator2;
        Animator animator3 = this.attachAnimator;
        return (animator3 != null && animator3.isRunning()) || ((animator = this.menuAnimator) != null && animator.isRunning()) || ((animator2 = this.modeAnimator) != null && animator2.isRunning());
    }

    private boolean isVisibleFab() {
        FloatingActionButton floatingActionButtonFindDependentFab = findDependentFab();
        return floatingActionButtonFindDependentFab != null && floatingActionButtonFindDependentFab.isOrWillBeShown();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void maybeAnimateAttachChange(boolean z2) {
        if (ViewCompat.isLaidOut(this)) {
            Animator animator = this.attachAnimator;
            if (animator != null) {
                animator.cancel();
            }
            ArrayList arrayList = new ArrayList();
            createCradleShapeAnimation(z2 && isVisibleFab(), arrayList);
            createFabTranslationYAnimation(z2, arrayList);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(arrayList);
            this.attachAnimator = animatorSet;
            animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.bottomappbar.BottomAppBar.5
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator2) {
                    BottomAppBar.this.attachAnimator = null;
                }
            });
            this.attachAnimator.start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void maybeAnimateMenuView(int i2, boolean z2) {
        if (ViewCompat.isLaidOut(this)) {
            Animator animator = this.menuAnimator;
            if (animator != null) {
                animator.cancel();
            }
            ArrayList arrayList = new ArrayList();
            if (!isVisibleFab()) {
                i2 = 0;
                z2 = false;
            }
            createMenuViewTranslationAnimation(i2, z2, arrayList);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(arrayList);
            this.menuAnimator = animatorSet;
            animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.bottomappbar.BottomAppBar.3
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator2) {
                    BottomAppBar.this.menuAnimator = null;
                }
            });
            this.menuAnimator.start();
        }
    }

    private void maybeAnimateModeChange(int i2) {
        if (this.fabAlignmentMode == i2 || !ViewCompat.isLaidOut(this)) {
            return;
        }
        Animator animator = this.modeAnimator;
        if (animator != null) {
            animator.cancel();
        }
        ArrayList arrayList = new ArrayList();
        createCradleTranslationAnimation(i2, arrayList);
        createFabTranslationXAnimation(i2, arrayList);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(arrayList);
        this.modeAnimator = animatorSet;
        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.bottomappbar.BottomAppBar.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator2) {
                BottomAppBar.this.modeAnimator = null;
            }
        });
        this.modeAnimator.start();
    }

    private void removeFabAnimationListeners(@NonNull FloatingActionButton floatingActionButton) {
        floatingActionButton.removeOnHideAnimationListener(this.f13391a);
        floatingActionButton.removeOnShowAnimationListener(this.f13391a);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setCutoutState() {
        this.topEdgeTreatment.j(getFabTranslationX());
        FloatingActionButton floatingActionButtonFindDependentFab = findDependentFab();
        this.materialShapeDrawable.setInterpolation((this.fabAttached && isVisibleFab()) ? 1.0f : 0.0f);
        if (floatingActionButtonFindDependentFab != null) {
            floatingActionButtonFindDependentFab.setTranslationY(getFabTranslationY());
            floatingActionButtonFindDependentFab.setTranslationX(getFabTranslationX());
        }
        ActionMenuView actionMenuView = getActionMenuView();
        if (actionMenuView != null) {
            actionMenuView.setAlpha(1.0f);
            if (isVisibleFab()) {
                translateActionMenuView(actionMenuView, this.fabAlignmentMode, this.fabAttached);
            } else {
                translateActionMenuView(actionMenuView, 0, false);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void translateActionMenuView(ActionMenuView actionMenuView, int i2, boolean z2) {
        boolean z3 = ViewCompat.getLayoutDirection(this) == 1;
        int iMax = 0;
        for (int i3 = 0; i3 < getChildCount(); i3++) {
            View childAt = getChildAt(i3);
            if ((childAt.getLayoutParams() instanceof Toolbar.LayoutParams) && (((Toolbar.LayoutParams) childAt.getLayoutParams()).gravity & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK) == 8388611) {
                iMax = Math.max(iMax, z3 ? childAt.getLeft() : childAt.getRight());
            }
        }
        actionMenuView.setTranslationX((i2 == 1 && z2) ? iMax - (z3 ? actionMenuView.getRight() : actionMenuView.getLeft()) : 0.0f);
    }

    @Nullable
    public ColorStateList getBackgroundTint() {
        return this.materialShapeDrawable.getTintList();
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.AttachedBehavior
    @NonNull
    public CoordinatorLayout.Behavior<BottomAppBar> getBehavior() {
        return new Behavior();
    }

    @Dimension
    public float getCradleVerticalOffset() {
        return this.topEdgeTreatment.a();
    }

    public int getFabAlignmentMode() {
        return this.fabAlignmentMode;
    }

    public float getFabCradleMargin() {
        return this.topEdgeTreatment.b();
    }

    @Dimension
    public float getFabCradleRoundedCornerRadius() {
        return this.topEdgeTreatment.c();
    }

    public boolean getHideOnScroll() {
        return this.hideOnScroll;
    }

    @Override // androidx.appcompat.widget.Toolbar, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        super.onLayout(z2, i2, i3, i4, i5);
        cancelAnimations();
        setCutoutState();
    }

    @Override // androidx.appcompat.widget.Toolbar, android.view.View
    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.fabAlignmentMode = savedState.f13402a;
        this.fabAttached = savedState.f13403b;
    }

    @Override // androidx.appcompat.widget.Toolbar, android.view.View
    protected Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.f13402a = this.fabAlignmentMode;
        savedState.f13403b = this.fabAttached;
        return savedState;
    }

    public void replaceMenu(@MenuRes int i2) {
        getMenu().clear();
        inflateMenu(i2);
    }

    public void setBackgroundTint(@Nullable ColorStateList colorStateList) {
        DrawableCompat.setTintList(this.materialShapeDrawable, colorStateList);
    }

    public void setCradleVerticalOffset(@Dimension float f2) {
        if (f2 != getCradleVerticalOffset()) {
            this.topEdgeTreatment.f(f2);
            this.materialShapeDrawable.invalidateSelf();
        }
    }

    public void setFabAlignmentMode(int i2) {
        maybeAnimateModeChange(i2);
        maybeAnimateMenuView(i2, this.fabAttached);
        this.fabAlignmentMode = i2;
    }

    public void setFabCradleMargin(@Dimension float f2) {
        if (f2 != getFabCradleMargin()) {
            this.topEdgeTreatment.g(f2);
            this.materialShapeDrawable.invalidateSelf();
        }
    }

    public void setFabCradleRoundedCornerRadius(@Dimension float f2) {
        if (f2 != getFabCradleRoundedCornerRadius()) {
            this.topEdgeTreatment.h(f2);
            this.materialShapeDrawable.invalidateSelf();
        }
    }

    void setFabDiameter(@Px int i2) {
        float f2 = i2;
        if (f2 != this.topEdgeTreatment.d()) {
            this.topEdgeTreatment.i(f2);
            this.materialShapeDrawable.invalidateSelf();
        }
    }

    public void setHideOnScroll(boolean z2) {
        this.hideOnScroll = z2;
    }

    @Override // androidx.appcompat.widget.Toolbar
    public void setSubtitle(CharSequence charSequence) {
    }

    @Override // androidx.appcompat.widget.Toolbar
    public void setTitle(CharSequence charSequence) {
    }

    public BottomAppBar(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.bottomAppBarStyle);
    }

    public BottomAppBar(Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.fabAttached = true;
        this.f13391a = new AnimatorListenerAdapter() { // from class: com.google.android.material.bottomappbar.BottomAppBar.7
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                BottomAppBar bottomAppBar = BottomAppBar.this;
                bottomAppBar.maybeAnimateAttachChange(bottomAppBar.fabAttached);
                BottomAppBar bottomAppBar2 = BottomAppBar.this;
                bottomAppBar2.maybeAnimateMenuView(bottomAppBar2.fabAlignmentMode, BottomAppBar.this.fabAttached);
            }
        };
        TypedArray typedArrayObtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(context, attributeSet, R.styleable.BottomAppBar, i2, R.style.Widget_MaterialComponents_BottomAppBar, new int[0]);
        ColorStateList colorStateList = MaterialResources.getColorStateList(context, typedArrayObtainStyledAttributes, R.styleable.BottomAppBar_backgroundTint);
        float dimensionPixelOffset = typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.BottomAppBar_fabCradleMargin, 0);
        float dimensionPixelOffset2 = typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.BottomAppBar_fabCradleRoundedCornerRadius, 0);
        float dimensionPixelOffset3 = typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.BottomAppBar_fabCradleVerticalOffset, 0);
        this.fabAlignmentMode = typedArrayObtainStyledAttributes.getInt(R.styleable.BottomAppBar_fabAlignmentMode, 0);
        this.hideOnScroll = typedArrayObtainStyledAttributes.getBoolean(R.styleable.BottomAppBar_hideOnScroll, false);
        typedArrayObtainStyledAttributes.recycle();
        this.fabOffsetEndMode = getResources().getDimensionPixelOffset(R.dimen.mtrl_bottomappbar_fabOffsetEndMode);
        BottomAppBarTopEdgeTreatment bottomAppBarTopEdgeTreatment = new BottomAppBarTopEdgeTreatment(dimensionPixelOffset, dimensionPixelOffset2, dimensionPixelOffset3);
        this.topEdgeTreatment = bottomAppBarTopEdgeTreatment;
        ShapePathModel shapePathModel = new ShapePathModel();
        shapePathModel.setTopEdge(bottomAppBarTopEdgeTreatment);
        MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable(shapePathModel);
        this.materialShapeDrawable = materialShapeDrawable;
        materialShapeDrawable.setShadowEnabled(true);
        materialShapeDrawable.setPaintStyle(Paint.Style.FILL);
        DrawableCompat.setTintList(materialShapeDrawable, colorStateList);
        ViewCompat.setBackground(this, materialShapeDrawable);
    }

    private float getFabTranslationX() {
        return getFabTranslationX(this.fabAlignmentMode);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float getFabTranslationY() {
        return getFabTranslationY(this.fabAttached);
    }
}
