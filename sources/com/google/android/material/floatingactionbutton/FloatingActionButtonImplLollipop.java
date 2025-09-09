package com.google.android.material.floatingactionbutton;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.util.Property;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.graphics.drawable.DrawableCompat;
import com.google.android.material.internal.CircularBorderDrawable;
import com.google.android.material.internal.CircularBorderDrawableLollipop;
import com.google.android.material.internal.VisibilityAwareImageButton;
import com.google.android.material.ripple.RippleUtils;
import com.google.android.material.shadow.ShadowDrawableWrapper;
import com.google.android.material.shadow.ShadowViewDelegate;
import java.util.ArrayList;

@RequiresApi(21)
/* loaded from: classes3.dex */
class FloatingActionButtonImplLollipop extends FloatingActionButtonImpl {
    private InsetDrawable insetDrawable;

    static class AlwaysStatefulGradientDrawable extends GradientDrawable {
        AlwaysStatefulGradientDrawable() {
        }

        @Override // android.graphics.drawable.GradientDrawable, android.graphics.drawable.Drawable
        public boolean isStateful() {
            return true;
        }
    }

    FloatingActionButtonImplLollipop(VisibilityAwareImageButton visibilityAwareImageButton, ShadowViewDelegate shadowViewDelegate) {
        super(visibilityAwareImageButton, shadowViewDelegate);
    }

    @NonNull
    private Animator createElevationAnimator(float f2, float f3) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(ObjectAnimator.ofFloat(this.f13463o, "elevation", f2).setDuration(0L)).with(ObjectAnimator.ofFloat(this.f13463o, (Property<VisibilityAwareImageButton, Float>) View.TRANSLATION_Z, f3).setDuration(100L));
        animatorSet.setInterpolator(FloatingActionButtonImpl.f13442q);
        return animatorSet;
    }

    @Override // com.google.android.material.floatingactionbutton.FloatingActionButtonImpl
    void H(ColorStateList colorStateList) {
        Drawable drawable = this.f13455g;
        if (drawable instanceof RippleDrawable) {
            ((RippleDrawable) drawable).setColor(RippleUtils.convertToRippleDrawableColor(colorStateList));
        } else {
            super.H(colorStateList);
        }
    }

    @Override // com.google.android.material.floatingactionbutton.FloatingActionButtonImpl
    void g(Rect rect) {
        if (!this.f13464p.isCompatPaddingEnabled()) {
            rect.set(0, 0, 0, 0);
            return;
        }
        float radius = this.f13464p.getRadius();
        float elevation = getElevation() + this.f13460l;
        int iCeil = (int) Math.ceil(ShadowDrawableWrapper.calculateHorizontalPadding(elevation, radius, false));
        int iCeil2 = (int) Math.ceil(ShadowDrawableWrapper.calculateVerticalPadding(elevation, radius, false));
        rect.set(iCeil, iCeil2, iCeil, iCeil2);
    }

    @Override // com.google.android.material.floatingactionbutton.FloatingActionButtonImpl
    public float getElevation() {
        return this.f13463o.getElevation();
    }

    @Override // com.google.android.material.floatingactionbutton.FloatingActionButtonImpl
    void m() {
    }

    @Override // com.google.android.material.floatingactionbutton.FloatingActionButtonImpl
    CircularBorderDrawable n() {
        return new CircularBorderDrawableLollipop();
    }

    @Override // com.google.android.material.floatingactionbutton.FloatingActionButtonImpl
    GradientDrawable o() {
        return new AlwaysStatefulGradientDrawable();
    }

    @Override // com.google.android.material.floatingactionbutton.FloatingActionButtonImpl
    void q() {
        L();
    }

    @Override // com.google.android.material.floatingactionbutton.FloatingActionButtonImpl
    void s(int[] iArr) {
    }

    @Override // com.google.android.material.floatingactionbutton.FloatingActionButtonImpl
    void t(float f2, float f3, float f4) {
        int i2 = Build.VERSION.SDK_INT;
        StateListAnimator stateListAnimator = new StateListAnimator();
        stateListAnimator.addState(FloatingActionButtonImpl.f13443r, createElevationAnimator(f2, f4));
        stateListAnimator.addState(FloatingActionButtonImpl.f13444s, createElevationAnimator(f2, f3));
        stateListAnimator.addState(FloatingActionButtonImpl.f13445t, createElevationAnimator(f2, f3));
        stateListAnimator.addState(FloatingActionButtonImpl.f13446u, createElevationAnimator(f2, f3));
        AnimatorSet animatorSet = new AnimatorSet();
        ArrayList arrayList = new ArrayList();
        arrayList.add(ObjectAnimator.ofFloat(this.f13463o, "elevation", f2).setDuration(0L));
        if (i2 <= 24) {
            VisibilityAwareImageButton visibilityAwareImageButton = this.f13463o;
            arrayList.add(ObjectAnimator.ofFloat(visibilityAwareImageButton, (Property<VisibilityAwareImageButton, Float>) View.TRANSLATION_Z, visibilityAwareImageButton.getTranslationZ()).setDuration(100L));
        }
        arrayList.add(ObjectAnimator.ofFloat(this.f13463o, (Property<VisibilityAwareImageButton, Float>) View.TRANSLATION_Z, 0.0f).setDuration(100L));
        animatorSet.playSequentially((Animator[]) arrayList.toArray(new Animator[0]));
        animatorSet.setInterpolator(FloatingActionButtonImpl.f13442q);
        stateListAnimator.addState(FloatingActionButtonImpl.f13447v, animatorSet);
        stateListAnimator.addState(FloatingActionButtonImpl.f13448w, createElevationAnimator(0.0f, 0.0f));
        this.f13463o.setStateListAnimator(stateListAnimator);
        if (this.f13464p.isCompatPaddingEnabled()) {
            L();
        }
    }

    @Override // com.google.android.material.floatingactionbutton.FloatingActionButtonImpl
    void u(Rect rect) {
        if (!this.f13464p.isCompatPaddingEnabled()) {
            this.f13464p.setBackgroundDrawable(this.f13455g);
            return;
        }
        InsetDrawable insetDrawable = new InsetDrawable(this.f13455g, rect.left, rect.top, rect.right, rect.bottom);
        this.insetDrawable = insetDrawable;
        this.f13464p.setBackgroundDrawable(insetDrawable);
    }

    @Override // com.google.android.material.floatingactionbutton.FloatingActionButtonImpl
    boolean x() {
        return false;
    }

    @Override // com.google.android.material.floatingactionbutton.FloatingActionButtonImpl
    void y(ColorStateList colorStateList, PorterDuff.Mode mode, ColorStateList colorStateList2, int i2) {
        Drawable layerDrawable;
        Drawable drawableWrap = DrawableCompat.wrap(c());
        this.f13454f = drawableWrap;
        DrawableCompat.setTintList(drawableWrap, colorStateList);
        if (mode != null) {
            DrawableCompat.setTintMode(this.f13454f, mode);
        }
        if (i2 > 0) {
            this.f13456h = b(i2, colorStateList);
            layerDrawable = new LayerDrawable(new Drawable[]{this.f13456h, this.f13454f});
        } else {
            this.f13456h = null;
            layerDrawable = this.f13454f;
        }
        RippleDrawable rippleDrawable = new RippleDrawable(RippleUtils.convertToRippleDrawableColor(colorStateList2), layerDrawable, null);
        this.f13455g = rippleDrawable;
        this.f13457i = rippleDrawable;
        this.f13464p.setBackgroundDrawable(rippleDrawable);
    }
}
