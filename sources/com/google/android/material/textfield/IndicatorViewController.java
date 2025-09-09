package com.google.android.material.textfield;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.view.ViewCompat;
import androidx.core.widget.TextViewCompat;
import androidx.legacy.widget.Space;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.animation.AnimatorSetCompat;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
final class IndicatorViewController {
    private static final int CAPTION_OPACITY_FADE_ANIMATION_DURATION = 167;
    private static final int CAPTION_STATE_ERROR = 1;
    private static final int CAPTION_STATE_HELPER_TEXT = 2;
    private static final int CAPTION_STATE_NONE = 0;
    private static final int CAPTION_TRANSLATE_Y_ANIMATION_DURATION = 217;

    @Nullable
    private Animator captionAnimator;
    private FrameLayout captionArea;
    private int captionDisplayed;
    private int captionToShow;
    private final float captionTranslationYPx;
    private int captionViewsAdded;
    private final Context context;
    private boolean errorEnabled;
    private CharSequence errorText;
    private int errorTextAppearance;
    private TextView errorView;
    private CharSequence helperText;
    private boolean helperTextEnabled;
    private int helperTextTextAppearance;
    private TextView helperTextView;
    private LinearLayout indicatorArea;
    private int indicatorsAdded;
    private final TextInputLayout textInputView;
    private Typeface typeface;

    public IndicatorViewController(TextInputLayout textInputLayout) {
        this.context = textInputLayout.getContext();
        this.textInputView = textInputLayout;
        this.captionTranslationYPx = r0.getResources().getDimensionPixelSize(R.dimen.design_textinput_caption_translate_y);
    }

    private boolean canAdjustIndicatorPadding() {
        return (this.indicatorArea == null || this.textInputView.getEditText() == null) ? false : true;
    }

    private void createCaptionAnimators(List<Animator> list, boolean z2, TextView textView, int i2, int i3, int i4) {
        if (textView == null || !z2) {
            return;
        }
        if (i2 == i4 || i2 == i3) {
            list.add(createCaptionOpacityAnimator(textView, i4 == i2));
            if (i4 == i2) {
                list.add(createCaptionTranslationYAnimator(textView));
            }
        }
    }

    private ObjectAnimator createCaptionOpacityAnimator(TextView textView, boolean z2) {
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(textView, (Property<TextView, Float>) View.ALPHA, z2 ? 1.0f : 0.0f);
        objectAnimatorOfFloat.setDuration(167L);
        objectAnimatorOfFloat.setInterpolator(AnimationUtils.LINEAR_INTERPOLATOR);
        return objectAnimatorOfFloat;
    }

    private ObjectAnimator createCaptionTranslationYAnimator(TextView textView) {
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(textView, (Property<TextView, Float>) View.TRANSLATION_Y, -this.captionTranslationYPx, 0.0f);
        objectAnimatorOfFloat.setDuration(217L);
        objectAnimatorOfFloat.setInterpolator(AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR);
        return objectAnimatorOfFloat;
    }

    @Nullable
    private TextView getCaptionViewFromDisplayState(int i2) {
        if (i2 == 1) {
            return this.errorView;
        }
        if (i2 != 2) {
            return null;
        }
        return this.helperTextView;
    }

    private boolean isCaptionStateError(int i2) {
        return (i2 != 1 || this.errorView == null || TextUtils.isEmpty(this.errorText)) ? false : true;
    }

    private boolean isCaptionStateHelperText(int i2) {
        return (i2 != 2 || this.helperTextView == null || TextUtils.isEmpty(this.helperText)) ? false : true;
    }

    private void setCaptionViewVisibilities(int i2, int i3) {
        TextView captionViewFromDisplayState;
        TextView captionViewFromDisplayState2;
        if (i2 == i3) {
            return;
        }
        if (i3 != 0 && (captionViewFromDisplayState2 = getCaptionViewFromDisplayState(i3)) != null) {
            captionViewFromDisplayState2.setVisibility(0);
            captionViewFromDisplayState2.setAlpha(1.0f);
        }
        if (i2 != 0 && (captionViewFromDisplayState = getCaptionViewFromDisplayState(i2)) != null) {
            captionViewFromDisplayState.setVisibility(4);
            if (i2 == 1) {
                captionViewFromDisplayState.setText((CharSequence) null);
            }
        }
        this.captionDisplayed = i3;
    }

    private void setTextViewTypeface(@Nullable TextView textView, Typeface typeface) {
        if (textView != null) {
            textView.setTypeface(typeface);
        }
    }

    private void setViewGroupGoneIfEmpty(ViewGroup viewGroup, int i2) {
        if (i2 == 0) {
            viewGroup.setVisibility(8);
        }
    }

    private boolean shouldAnimateCaptionView(TextView textView, @Nullable CharSequence charSequence) {
        return ViewCompat.isLaidOut(this.textInputView) && this.textInputView.isEnabled() && !(this.captionToShow == this.captionDisplayed && textView != null && TextUtils.equals(textView.getText(), charSequence));
    }

    private void updateCaptionViewsVisibility(final int i2, final int i3, boolean z2) {
        if (z2) {
            AnimatorSet animatorSet = new AnimatorSet();
            this.captionAnimator = animatorSet;
            ArrayList arrayList = new ArrayList();
            createCaptionAnimators(arrayList, this.helperTextEnabled, this.helperTextView, 2, i2, i3);
            createCaptionAnimators(arrayList, this.errorEnabled, this.errorView, 1, i2, i3);
            AnimatorSetCompat.playTogether(animatorSet, arrayList);
            final TextView captionViewFromDisplayState = getCaptionViewFromDisplayState(i2);
            final TextView captionViewFromDisplayState2 = getCaptionViewFromDisplayState(i3);
            animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.textfield.IndicatorViewController.1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    IndicatorViewController.this.captionDisplayed = i3;
                    IndicatorViewController.this.captionAnimator = null;
                    TextView textView = captionViewFromDisplayState;
                    if (textView != null) {
                        textView.setVisibility(4);
                        if (i2 != 1 || IndicatorViewController.this.errorView == null) {
                            return;
                        }
                        IndicatorViewController.this.errorView.setText((CharSequence) null);
                    }
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator) {
                    TextView textView = captionViewFromDisplayState2;
                    if (textView != null) {
                        textView.setVisibility(0);
                    }
                }
            });
            animatorSet.start();
        } else {
            setCaptionViewVisibilities(i2, i3);
        }
        this.textInputView.f();
        this.textInputView.g(z2);
        this.textInputView.h();
    }

    void A(CharSequence charSequence) {
        f();
        this.helperText = charSequence;
        this.helperTextView.setText(charSequence);
        int i2 = this.captionDisplayed;
        if (i2 != 2) {
            this.captionToShow = 2;
        }
        updateCaptionViewsVisibility(i2, this.captionToShow, shouldAnimateCaptionView(this.helperTextView, charSequence));
    }

    void d(TextView textView, int i2) {
        if (this.indicatorArea == null && this.captionArea == null) {
            LinearLayout linearLayout = new LinearLayout(this.context);
            this.indicatorArea = linearLayout;
            linearLayout.setOrientation(0);
            this.textInputView.addView(this.indicatorArea, -1, -2);
            FrameLayout frameLayout = new FrameLayout(this.context);
            this.captionArea = frameLayout;
            this.indicatorArea.addView(frameLayout, -1, new FrameLayout.LayoutParams(-2, -2));
            this.indicatorArea.addView(new Space(this.context), new LinearLayout.LayoutParams(0, 0, 1.0f));
            if (this.textInputView.getEditText() != null) {
                e();
            }
        }
        if (o(i2)) {
            this.captionArea.setVisibility(0);
            this.captionArea.addView(textView);
            this.captionViewsAdded++;
        } else {
            this.indicatorArea.addView(textView, i2);
        }
        this.indicatorArea.setVisibility(0);
        this.indicatorsAdded++;
    }

    void e() {
        if (canAdjustIndicatorPadding()) {
            ViewCompat.setPaddingRelative(this.indicatorArea, ViewCompat.getPaddingStart(this.textInputView.getEditText()), 0, ViewCompat.getPaddingEnd(this.textInputView.getEditText()), 0);
        }
    }

    void f() {
        Animator animator = this.captionAnimator;
        if (animator != null) {
            animator.cancel();
        }
    }

    boolean g() {
        return isCaptionStateError(this.captionToShow);
    }

    CharSequence h() {
        return this.errorText;
    }

    int i() {
        TextView textView = this.errorView;
        if (textView != null) {
            return textView.getCurrentTextColor();
        }
        return -1;
    }

    ColorStateList j() {
        TextView textView = this.errorView;
        if (textView != null) {
            return textView.getTextColors();
        }
        return null;
    }

    CharSequence k() {
        return this.helperText;
    }

    int l() {
        TextView textView = this.helperTextView;
        if (textView != null) {
            return textView.getCurrentTextColor();
        }
        return -1;
    }

    void m() {
        this.errorText = null;
        f();
        if (this.captionDisplayed == 1) {
            if (!this.helperTextEnabled || TextUtils.isEmpty(this.helperText)) {
                this.captionToShow = 0;
            } else {
                this.captionToShow = 2;
            }
        }
        updateCaptionViewsVisibility(this.captionDisplayed, this.captionToShow, shouldAnimateCaptionView(this.errorView, null));
    }

    void n() {
        f();
        int i2 = this.captionDisplayed;
        if (i2 == 2) {
            this.captionToShow = 0;
        }
        updateCaptionViewsVisibility(i2, this.captionToShow, shouldAnimateCaptionView(this.helperTextView, null));
    }

    boolean o(int i2) {
        return i2 == 0 || i2 == 1;
    }

    boolean p() {
        return this.errorEnabled;
    }

    boolean q() {
        return this.helperTextEnabled;
    }

    void r(TextView textView, int i2) {
        FrameLayout frameLayout;
        if (this.indicatorArea == null) {
            return;
        }
        if (!o(i2) || (frameLayout = this.captionArea) == null) {
            this.indicatorArea.removeView(textView);
        } else {
            int i3 = this.captionViewsAdded - 1;
            this.captionViewsAdded = i3;
            setViewGroupGoneIfEmpty(frameLayout, i3);
            this.captionArea.removeView(textView);
        }
        int i4 = this.indicatorsAdded - 1;
        this.indicatorsAdded = i4;
        setViewGroupGoneIfEmpty(this.indicatorArea, i4);
    }

    void s(boolean z2) {
        if (this.errorEnabled == z2) {
            return;
        }
        f();
        if (z2) {
            AppCompatTextView appCompatTextView = new AppCompatTextView(this.context);
            this.errorView = appCompatTextView;
            appCompatTextView.setId(R.id.textinput_error);
            Typeface typeface = this.typeface;
            if (typeface != null) {
                this.errorView.setTypeface(typeface);
            }
            t(this.errorTextAppearance);
            this.errorView.setVisibility(4);
            ViewCompat.setAccessibilityLiveRegion(this.errorView, 1);
            d(this.errorView, 0);
        } else {
            m();
            r(this.errorView, 0);
            this.errorView = null;
            this.textInputView.f();
            this.textInputView.h();
        }
        this.errorEnabled = z2;
    }

    void t(int i2) {
        this.errorTextAppearance = i2;
        TextView textView = this.errorView;
        if (textView != null) {
            this.textInputView.d(textView, i2);
        }
    }

    void u(ColorStateList colorStateList) {
        TextView textView = this.errorView;
        if (textView != null) {
            textView.setTextColor(colorStateList);
        }
    }

    void v(int i2) {
        this.helperTextTextAppearance = i2;
        TextView textView = this.helperTextView;
        if (textView != null) {
            TextViewCompat.setTextAppearance(textView, i2);
        }
    }

    void w(boolean z2) {
        if (this.helperTextEnabled == z2) {
            return;
        }
        f();
        if (z2) {
            AppCompatTextView appCompatTextView = new AppCompatTextView(this.context);
            this.helperTextView = appCompatTextView;
            appCompatTextView.setId(R.id.textinput_helper_text);
            Typeface typeface = this.typeface;
            if (typeface != null) {
                this.helperTextView.setTypeface(typeface);
            }
            this.helperTextView.setVisibility(4);
            ViewCompat.setAccessibilityLiveRegion(this.helperTextView, 1);
            v(this.helperTextTextAppearance);
            d(this.helperTextView, 1);
        } else {
            n();
            r(this.helperTextView, 1);
            this.helperTextView = null;
            this.textInputView.f();
            this.textInputView.h();
        }
        this.helperTextEnabled = z2;
    }

    void x(ColorStateList colorStateList) {
        TextView textView = this.helperTextView;
        if (textView != null) {
            textView.setTextColor(colorStateList);
        }
    }

    void y(Typeface typeface) {
        if (typeface != this.typeface) {
            this.typeface = typeface;
            setTextViewTypeface(this.errorView, typeface);
            setTextViewTypeface(this.helperTextView, typeface);
        }
    }

    void z(CharSequence charSequence) {
        f();
        this.errorText = charSequence;
        this.errorView.setText(charSequence);
        int i2 = this.captionDisplayed;
        if (i2 != 1) {
            this.captionToShow = 1;
        }
        updateCaptionViewsVisibility(i2, this.captionToShow, shouldAnimateCaptionView(this.errorView, charSequence));
    }
}
