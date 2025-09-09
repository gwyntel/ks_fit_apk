package com.google.android.material.floatingactionbutton;

import android.R;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.util.Property;
import android.view.View;
import android.view.ViewTreeObserver;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.animation.AnimatorSetCompat;
import com.google.android.material.animation.ImageMatrixProperty;
import com.google.android.material.animation.MatrixEvaluator;
import com.google.android.material.animation.MotionSpec;
import com.google.android.material.internal.CircularBorderDrawable;
import com.google.android.material.internal.StateListAnimator;
import com.google.android.material.internal.VisibilityAwareImageButton;
import com.google.android.material.ripple.RippleUtils;
import com.google.android.material.shadow.ShadowDrawableWrapper;
import com.google.android.material.shadow.ShadowViewDelegate;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes3.dex */
class FloatingActionButtonImpl {
    private static final float HIDE_ICON_SCALE = 0.0f;
    private static final float HIDE_OPACITY = 0.0f;
    private static final float HIDE_SCALE = 0.0f;
    private static final float SHOW_ICON_SCALE = 1.0f;
    private static final float SHOW_OPACITY = 1.0f;
    private static final float SHOW_SCALE = 1.0f;

    /* renamed from: q, reason: collision with root package name */
    static final TimeInterpolator f13442q = AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR;

    /* renamed from: r, reason: collision with root package name */
    static final int[] f13443r = {R.attr.state_pressed, R.attr.state_enabled};

    /* renamed from: s, reason: collision with root package name */
    static final int[] f13444s = {R.attr.state_hovered, R.attr.state_focused, R.attr.state_enabled};

    /* renamed from: t, reason: collision with root package name */
    static final int[] f13445t = {R.attr.state_focused, R.attr.state_enabled};

    /* renamed from: u, reason: collision with root package name */
    static final int[] f13446u = {R.attr.state_hovered, R.attr.state_enabled};

    /* renamed from: v, reason: collision with root package name */
    static final int[] f13447v = {R.attr.state_enabled};

    /* renamed from: w, reason: collision with root package name */
    static final int[] f13448w = new int[0];

    /* renamed from: b, reason: collision with root package name */
    Animator f13450b;

    /* renamed from: c, reason: collision with root package name */
    MotionSpec f13451c;

    /* renamed from: d, reason: collision with root package name */
    MotionSpec f13452d;

    @Nullable
    private MotionSpec defaultHideMotionSpec;

    @Nullable
    private MotionSpec defaultShowMotionSpec;

    /* renamed from: e, reason: collision with root package name */
    ShadowDrawableWrapper f13453e;

    /* renamed from: f, reason: collision with root package name */
    Drawable f13454f;

    /* renamed from: g, reason: collision with root package name */
    Drawable f13455g;

    /* renamed from: h, reason: collision with root package name */
    CircularBorderDrawable f13456h;
    private ArrayList<Animator.AnimatorListener> hideListeners;

    /* renamed from: i, reason: collision with root package name */
    Drawable f13457i;

    /* renamed from: j, reason: collision with root package name */
    float f13458j;

    /* renamed from: k, reason: collision with root package name */
    float f13459k;

    /* renamed from: l, reason: collision with root package name */
    float f13460l;

    /* renamed from: m, reason: collision with root package name */
    int f13461m;

    /* renamed from: o, reason: collision with root package name */
    final VisibilityAwareImageButton f13463o;

    /* renamed from: p, reason: collision with root package name */
    final ShadowViewDelegate f13464p;
    private ViewTreeObserver.OnPreDrawListener preDrawListener;
    private float rotation;
    private ArrayList<Animator.AnimatorListener> showListeners;
    private final StateListAnimator stateListAnimator;

    /* renamed from: a, reason: collision with root package name */
    int f13449a = 0;

    /* renamed from: n, reason: collision with root package name */
    float f13462n = 1.0f;
    private final Rect tmpRect = new Rect();
    private final RectF tmpRectF1 = new RectF();
    private final RectF tmpRectF2 = new RectF();
    private final Matrix tmpMatrix = new Matrix();

    private class DisabledElevationAnimation extends ShadowAnimatorImpl {
        DisabledElevationAnimation() {
            super();
        }

        @Override // com.google.android.material.floatingactionbutton.FloatingActionButtonImpl.ShadowAnimatorImpl
        protected float a() {
            return 0.0f;
        }
    }

    private class ElevateToHoveredFocusedTranslationZAnimation extends ShadowAnimatorImpl {
        ElevateToHoveredFocusedTranslationZAnimation() {
            super();
        }

        @Override // com.google.android.material.floatingactionbutton.FloatingActionButtonImpl.ShadowAnimatorImpl
        protected float a() {
            FloatingActionButtonImpl floatingActionButtonImpl = FloatingActionButtonImpl.this;
            return floatingActionButtonImpl.f13458j + floatingActionButtonImpl.f13459k;
        }
    }

    private class ElevateToPressedTranslationZAnimation extends ShadowAnimatorImpl {
        ElevateToPressedTranslationZAnimation() {
            super();
        }

        @Override // com.google.android.material.floatingactionbutton.FloatingActionButtonImpl.ShadowAnimatorImpl
        protected float a() {
            FloatingActionButtonImpl floatingActionButtonImpl = FloatingActionButtonImpl.this;
            return floatingActionButtonImpl.f13458j + floatingActionButtonImpl.f13460l;
        }
    }

    interface InternalVisibilityChangedListener {
        void onHidden();

        void onShown();
    }

    private class ResetElevationAnimation extends ShadowAnimatorImpl {
        ResetElevationAnimation() {
            super();
        }

        @Override // com.google.android.material.floatingactionbutton.FloatingActionButtonImpl.ShadowAnimatorImpl
        protected float a() {
            return FloatingActionButtonImpl.this.f13458j;
        }
    }

    private abstract class ShadowAnimatorImpl extends AnimatorListenerAdapter implements ValueAnimator.AnimatorUpdateListener {
        private float shadowSizeEnd;
        private float shadowSizeStart;
        private boolean validValues;

        private ShadowAnimatorImpl() {
        }

        protected abstract float a();

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            FloatingActionButtonImpl.this.f13453e.setShadowSize(this.shadowSizeEnd);
            this.validValues = false;
        }

        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            if (!this.validValues) {
                this.shadowSizeStart = FloatingActionButtonImpl.this.f13453e.getShadowSize();
                this.shadowSizeEnd = a();
                this.validValues = true;
            }
            ShadowDrawableWrapper shadowDrawableWrapper = FloatingActionButtonImpl.this.f13453e;
            float f2 = this.shadowSizeStart;
            shadowDrawableWrapper.setShadowSize(f2 + ((this.shadowSizeEnd - f2) * valueAnimator.getAnimatedFraction()));
        }
    }

    FloatingActionButtonImpl(VisibilityAwareImageButton visibilityAwareImageButton, ShadowViewDelegate shadowViewDelegate) {
        this.f13463o = visibilityAwareImageButton;
        this.f13464p = shadowViewDelegate;
        StateListAnimator stateListAnimator = new StateListAnimator();
        this.stateListAnimator = stateListAnimator;
        stateListAnimator.addState(f13443r, createElevationAnimator(new ElevateToPressedTranslationZAnimation()));
        stateListAnimator.addState(f13444s, createElevationAnimator(new ElevateToHoveredFocusedTranslationZAnimation()));
        stateListAnimator.addState(f13445t, createElevationAnimator(new ElevateToHoveredFocusedTranslationZAnimation()));
        stateListAnimator.addState(f13446u, createElevationAnimator(new ElevateToHoveredFocusedTranslationZAnimation()));
        stateListAnimator.addState(f13447v, createElevationAnimator(new ResetElevationAnimation()));
        stateListAnimator.addState(f13448w, createElevationAnimator(new DisabledElevationAnimation()));
        this.rotation = visibilityAwareImageButton.getRotation();
    }

    private void calculateImageMatrixFromScale(float f2, Matrix matrix) {
        matrix.reset();
        if (this.f13463o.getDrawable() == null || this.f13461m == 0) {
            return;
        }
        RectF rectF = this.tmpRectF1;
        RectF rectF2 = this.tmpRectF2;
        rectF.set(0.0f, 0.0f, r0.getIntrinsicWidth(), r0.getIntrinsicHeight());
        int i2 = this.f13461m;
        rectF2.set(0.0f, 0.0f, i2, i2);
        matrix.setRectToRect(rectF, rectF2, Matrix.ScaleToFit.CENTER);
        int i3 = this.f13461m;
        matrix.postScale(f2, f2, i3 / 2.0f, i3 / 2.0f);
    }

    @NonNull
    private AnimatorSet createAnimator(@NonNull MotionSpec motionSpec, float f2, float f3, float f4) {
        ArrayList arrayList = new ArrayList();
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.f13463o, (Property<VisibilityAwareImageButton, Float>) View.ALPHA, f2);
        motionSpec.getTiming("opacity").apply(objectAnimatorOfFloat);
        arrayList.add(objectAnimatorOfFloat);
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(this.f13463o, (Property<VisibilityAwareImageButton, Float>) View.SCALE_X, f3);
        motionSpec.getTiming("scale").apply(objectAnimatorOfFloat2);
        arrayList.add(objectAnimatorOfFloat2);
        ObjectAnimator objectAnimatorOfFloat3 = ObjectAnimator.ofFloat(this.f13463o, (Property<VisibilityAwareImageButton, Float>) View.SCALE_Y, f3);
        motionSpec.getTiming("scale").apply(objectAnimatorOfFloat3);
        arrayList.add(objectAnimatorOfFloat3);
        calculateImageMatrixFromScale(f4, this.tmpMatrix);
        ObjectAnimator objectAnimatorOfObject = ObjectAnimator.ofObject(this.f13463o, new ImageMatrixProperty(), new MatrixEvaluator(), new Matrix(this.tmpMatrix));
        motionSpec.getTiming("iconScale").apply(objectAnimatorOfObject);
        arrayList.add(objectAnimatorOfObject);
        AnimatorSet animatorSet = new AnimatorSet();
        AnimatorSetCompat.playTogether(animatorSet, arrayList);
        return animatorSet;
    }

    private ValueAnimator createElevationAnimator(@NonNull ShadowAnimatorImpl shadowAnimatorImpl) {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setInterpolator(f13442q);
        valueAnimator.setDuration(100L);
        valueAnimator.addListener(shadowAnimatorImpl);
        valueAnimator.addUpdateListener(shadowAnimatorImpl);
        valueAnimator.setFloatValues(0.0f, 1.0f);
        return valueAnimator;
    }

    private void ensurePreDrawListener() {
        if (this.preDrawListener == null) {
            this.preDrawListener = new ViewTreeObserver.OnPreDrawListener() { // from class: com.google.android.material.floatingactionbutton.FloatingActionButtonImpl.3
                @Override // android.view.ViewTreeObserver.OnPreDrawListener
                public boolean onPreDraw() {
                    FloatingActionButtonImpl.this.v();
                    return true;
                }
            };
        }
    }

    private MotionSpec getDefaultHideMotionSpec() {
        if (this.defaultHideMotionSpec == null) {
            this.defaultHideMotionSpec = MotionSpec.createFromResource(this.f13463o.getContext(), com.google.android.material.R.animator.design_fab_hide_motion_spec);
        }
        return this.defaultHideMotionSpec;
    }

    private MotionSpec getDefaultShowMotionSpec() {
        if (this.defaultShowMotionSpec == null) {
            this.defaultShowMotionSpec = MotionSpec.createFromResource(this.f13463o.getContext(), com.google.android.material.R.animator.design_fab_show_motion_spec);
        }
        return this.defaultShowMotionSpec;
    }

    private boolean shouldAnimateVisibilityChange() {
        return ViewCompat.isLaidOut(this.f13463o) && !this.f13463o.isInEditMode();
    }

    private void updateFromViewRotation() {
        ShadowDrawableWrapper shadowDrawableWrapper = this.f13453e;
        if (shadowDrawableWrapper != null) {
            shadowDrawableWrapper.setRotation(-this.rotation);
        }
        CircularBorderDrawable circularBorderDrawable = this.f13456h;
        if (circularBorderDrawable != null) {
            circularBorderDrawable.setRotation(-this.rotation);
        }
    }

    void A(PorterDuff.Mode mode) {
        Drawable drawable = this.f13454f;
        if (drawable != null) {
            DrawableCompat.setTintMode(drawable, mode);
        }
    }

    final void B(float f2) {
        if (this.f13458j != f2) {
            this.f13458j = f2;
            t(f2, this.f13459k, this.f13460l);
        }
    }

    final void C(MotionSpec motionSpec) {
        this.f13452d = motionSpec;
    }

    final void D(float f2) {
        if (this.f13459k != f2) {
            this.f13459k = f2;
            t(this.f13458j, f2, this.f13460l);
        }
    }

    final void E(float f2) {
        this.f13462n = f2;
        Matrix matrix = this.tmpMatrix;
        calculateImageMatrixFromScale(f2, matrix);
        this.f13463o.setImageMatrix(matrix);
    }

    final void F(int i2) {
        if (this.f13461m != i2) {
            this.f13461m = i2;
            K();
        }
    }

    final void G(float f2) {
        if (this.f13460l != f2) {
            this.f13460l = f2;
            t(this.f13458j, this.f13459k, f2);
        }
    }

    void H(ColorStateList colorStateList) {
        Drawable drawable = this.f13455g;
        if (drawable != null) {
            DrawableCompat.setTintList(drawable, RippleUtils.convertToRippleDrawableColor(colorStateList));
        }
    }

    final void I(MotionSpec motionSpec) {
        this.f13451c = motionSpec;
    }

    void J(final InternalVisibilityChangedListener internalVisibilityChangedListener, final boolean z2) {
        if (l()) {
            return;
        }
        Animator animator = this.f13450b;
        if (animator != null) {
            animator.cancel();
        }
        if (!shouldAnimateVisibilityChange()) {
            this.f13463o.internalSetVisibility(0, z2);
            this.f13463o.setAlpha(1.0f);
            this.f13463o.setScaleY(1.0f);
            this.f13463o.setScaleX(1.0f);
            E(1.0f);
            if (internalVisibilityChangedListener != null) {
                internalVisibilityChangedListener.onShown();
                return;
            }
            return;
        }
        if (this.f13463o.getVisibility() != 0) {
            this.f13463o.setAlpha(0.0f);
            this.f13463o.setScaleY(0.0f);
            this.f13463o.setScaleX(0.0f);
            E(0.0f);
        }
        MotionSpec defaultShowMotionSpec = this.f13451c;
        if (defaultShowMotionSpec == null) {
            defaultShowMotionSpec = getDefaultShowMotionSpec();
        }
        AnimatorSet animatorSetCreateAnimator = createAnimator(defaultShowMotionSpec, 1.0f, 1.0f, 1.0f);
        animatorSetCreateAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.floatingactionbutton.FloatingActionButtonImpl.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator2) {
                FloatingActionButtonImpl floatingActionButtonImpl = FloatingActionButtonImpl.this;
                floatingActionButtonImpl.f13449a = 0;
                floatingActionButtonImpl.f13450b = null;
                InternalVisibilityChangedListener internalVisibilityChangedListener2 = internalVisibilityChangedListener;
                if (internalVisibilityChangedListener2 != null) {
                    internalVisibilityChangedListener2.onShown();
                }
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator2) {
                FloatingActionButtonImpl.this.f13463o.internalSetVisibility(0, z2);
                FloatingActionButtonImpl floatingActionButtonImpl = FloatingActionButtonImpl.this;
                floatingActionButtonImpl.f13449a = 2;
                floatingActionButtonImpl.f13450b = animator2;
            }
        });
        ArrayList<Animator.AnimatorListener> arrayList = this.showListeners;
        if (arrayList != null) {
            Iterator<Animator.AnimatorListener> it = arrayList.iterator();
            while (it.hasNext()) {
                animatorSetCreateAnimator.addListener(it.next());
            }
        }
        animatorSetCreateAnimator.start();
    }

    final void K() {
        E(this.f13462n);
    }

    final void L() {
        Rect rect = this.tmpRect;
        g(rect);
        u(rect);
        this.f13464p.setShadowPadding(rect.left, rect.top, rect.right, rect.bottom);
    }

    void a(Animator.AnimatorListener animatorListener) {
        if (this.showListeners == null) {
            this.showListeners = new ArrayList<>();
        }
        this.showListeners.add(animatorListener);
    }

    public void addOnHideAnimationListener(@NonNull Animator.AnimatorListener animatorListener) {
        if (this.hideListeners == null) {
            this.hideListeners = new ArrayList<>();
        }
        this.hideListeners.add(animatorListener);
    }

    CircularBorderDrawable b(int i2, ColorStateList colorStateList) {
        Context context = this.f13463o.getContext();
        CircularBorderDrawable circularBorderDrawableN = n();
        circularBorderDrawableN.setGradientColors(ContextCompat.getColor(context, com.google.android.material.R.color.design_fab_stroke_top_outer_color), ContextCompat.getColor(context, com.google.android.material.R.color.design_fab_stroke_top_inner_color), ContextCompat.getColor(context, com.google.android.material.R.color.design_fab_stroke_end_inner_color), ContextCompat.getColor(context, com.google.android.material.R.color.design_fab_stroke_end_outer_color));
        circularBorderDrawableN.setBorderWidth(i2);
        circularBorderDrawableN.setBorderTint(colorStateList);
        return circularBorderDrawableN;
    }

    GradientDrawable c() {
        GradientDrawable gradientDrawableO = o();
        gradientDrawableO.setShape(1);
        gradientDrawableO.setColor(-1);
        return gradientDrawableO;
    }

    final Drawable d() {
        return this.f13457i;
    }

    final MotionSpec e() {
        return this.f13452d;
    }

    float f() {
        return this.f13459k;
    }

    void g(Rect rect) {
        this.f13453e.getPadding(rect);
    }

    float getElevation() {
        return this.f13458j;
    }

    float h() {
        return this.f13460l;
    }

    final MotionSpec i() {
        return this.f13451c;
    }

    void j(final InternalVisibilityChangedListener internalVisibilityChangedListener, final boolean z2) {
        if (k()) {
            return;
        }
        Animator animator = this.f13450b;
        if (animator != null) {
            animator.cancel();
        }
        if (!shouldAnimateVisibilityChange()) {
            this.f13463o.internalSetVisibility(z2 ? 8 : 4, z2);
            if (internalVisibilityChangedListener != null) {
                internalVisibilityChangedListener.onHidden();
                return;
            }
            return;
        }
        MotionSpec defaultHideMotionSpec = this.f13452d;
        if (defaultHideMotionSpec == null) {
            defaultHideMotionSpec = getDefaultHideMotionSpec();
        }
        AnimatorSet animatorSetCreateAnimator = createAnimator(defaultHideMotionSpec, 0.0f, 0.0f, 0.0f);
        animatorSetCreateAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.floatingactionbutton.FloatingActionButtonImpl.1
            private boolean cancelled;

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator2) {
                this.cancelled = true;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator2) {
                FloatingActionButtonImpl floatingActionButtonImpl = FloatingActionButtonImpl.this;
                floatingActionButtonImpl.f13449a = 0;
                floatingActionButtonImpl.f13450b = null;
                if (this.cancelled) {
                    return;
                }
                VisibilityAwareImageButton visibilityAwareImageButton = floatingActionButtonImpl.f13463o;
                boolean z3 = z2;
                visibilityAwareImageButton.internalSetVisibility(z3 ? 8 : 4, z3);
                InternalVisibilityChangedListener internalVisibilityChangedListener2 = internalVisibilityChangedListener;
                if (internalVisibilityChangedListener2 != null) {
                    internalVisibilityChangedListener2.onHidden();
                }
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator2) {
                FloatingActionButtonImpl.this.f13463o.internalSetVisibility(0, z2);
                FloatingActionButtonImpl floatingActionButtonImpl = FloatingActionButtonImpl.this;
                floatingActionButtonImpl.f13449a = 1;
                floatingActionButtonImpl.f13450b = animator2;
                this.cancelled = false;
            }
        });
        ArrayList<Animator.AnimatorListener> arrayList = this.hideListeners;
        if (arrayList != null) {
            Iterator<Animator.AnimatorListener> it = arrayList.iterator();
            while (it.hasNext()) {
                animatorSetCreateAnimator.addListener(it.next());
            }
        }
        animatorSetCreateAnimator.start();
    }

    boolean k() {
        return this.f13463o.getVisibility() == 0 ? this.f13449a == 1 : this.f13449a != 2;
    }

    boolean l() {
        return this.f13463o.getVisibility() != 0 ? this.f13449a == 2 : this.f13449a != 1;
    }

    void m() {
        this.stateListAnimator.jumpToCurrentState();
    }

    CircularBorderDrawable n() {
        return new CircularBorderDrawable();
    }

    GradientDrawable o() {
        return new GradientDrawable();
    }

    void p() {
        if (x()) {
            ensurePreDrawListener();
            this.f13463o.getViewTreeObserver().addOnPreDrawListener(this.preDrawListener);
        }
    }

    void q() {
    }

    void r() {
        if (this.preDrawListener != null) {
            this.f13463o.getViewTreeObserver().removeOnPreDrawListener(this.preDrawListener);
            this.preDrawListener = null;
        }
    }

    public void removeOnHideAnimationListener(@NonNull Animator.AnimatorListener animatorListener) {
        ArrayList<Animator.AnimatorListener> arrayList = this.hideListeners;
        if (arrayList == null) {
            return;
        }
        arrayList.remove(animatorListener);
    }

    void s(int[] iArr) {
        this.stateListAnimator.setState(iArr);
    }

    void t(float f2, float f3, float f4) {
        ShadowDrawableWrapper shadowDrawableWrapper = this.f13453e;
        if (shadowDrawableWrapper != null) {
            shadowDrawableWrapper.setShadowSize(f2, this.f13460l + f2);
            L();
        }
    }

    void u(Rect rect) {
    }

    void v() {
        float rotation = this.f13463o.getRotation();
        if (this.rotation != rotation) {
            this.rotation = rotation;
            updateFromViewRotation();
        }
    }

    void w(Animator.AnimatorListener animatorListener) {
        ArrayList<Animator.AnimatorListener> arrayList = this.showListeners;
        if (arrayList == null) {
            return;
        }
        arrayList.remove(animatorListener);
    }

    boolean x() {
        return true;
    }

    void y(ColorStateList colorStateList, PorterDuff.Mode mode, ColorStateList colorStateList2, int i2) {
        Drawable[] drawableArr;
        Drawable drawableWrap = DrawableCompat.wrap(c());
        this.f13454f = drawableWrap;
        DrawableCompat.setTintList(drawableWrap, colorStateList);
        if (mode != null) {
            DrawableCompat.setTintMode(this.f13454f, mode);
        }
        Drawable drawableWrap2 = DrawableCompat.wrap(c());
        this.f13455g = drawableWrap2;
        DrawableCompat.setTintList(drawableWrap2, RippleUtils.convertToRippleDrawableColor(colorStateList2));
        if (i2 > 0) {
            CircularBorderDrawable circularBorderDrawableB = b(i2, colorStateList);
            this.f13456h = circularBorderDrawableB;
            drawableArr = new Drawable[]{circularBorderDrawableB, this.f13454f, this.f13455g};
        } else {
            this.f13456h = null;
            drawableArr = new Drawable[]{this.f13454f, this.f13455g};
        }
        this.f13457i = new LayerDrawable(drawableArr);
        Context context = this.f13463o.getContext();
        Drawable drawable = this.f13457i;
        float radius = this.f13464p.getRadius();
        float f2 = this.f13458j;
        ShadowDrawableWrapper shadowDrawableWrapper = new ShadowDrawableWrapper(context, drawable, radius, f2, f2 + this.f13460l);
        this.f13453e = shadowDrawableWrapper;
        shadowDrawableWrapper.setAddPaddingForCorners(false);
        this.f13464p.setBackgroundDrawable(this.f13453e);
    }

    void z(ColorStateList colorStateList) {
        Drawable drawable = this.f13454f;
        if (drawable != null) {
            DrawableCompat.setTintList(drawable, colorStateList);
        }
        CircularBorderDrawable circularBorderDrawable = this.f13456h;
        if (circularBorderDrawable != null) {
            circularBorderDrawable.setBorderTint(colorStateList);
        }
    }
}
