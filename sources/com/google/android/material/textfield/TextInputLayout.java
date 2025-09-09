package com.google.android.material.textfield;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStructure;
import android.view.accessibility.AccessibilityEvent;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatDrawableManager;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.DrawableUtils;
import androidx.appcompat.widget.TintTypedArray;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.widget.TextViewCompat;
import androidx.customview.view.AbsSavedState;
import com.alipay.sdk.m.u.i;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.internal.CheckableImageButton;
import com.google.android.material.internal.CollapsingTextHelper;
import com.google.android.material.internal.DescendantOffsetUtils;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes3.dex */
public class TextInputLayout extends LinearLayout {
    public static final int BOX_BACKGROUND_FILLED = 1;
    public static final int BOX_BACKGROUND_NONE = 0;
    public static final int BOX_BACKGROUND_OUTLINE = 2;
    private static final int INVALID_MAX_LENGTH = -1;
    private static final int LABEL_SCALE_ANIMATION_DURATION = 167;
    private static final String LOG_TAG = "TextInputLayout";

    /* renamed from: a, reason: collision with root package name */
    EditText f13597a;
    private ValueAnimator animator;

    /* renamed from: b, reason: collision with root package name */
    boolean f13598b;
    private GradientDrawable boxBackground;

    @ColorInt
    private int boxBackgroundColor;
    private int boxBackgroundMode;
    private final int boxBottomOffsetPx;
    private final int boxCollapsedPaddingTopPx;
    private float boxCornerRadiusBottomEnd;
    private float boxCornerRadiusBottomStart;
    private float boxCornerRadiusTopEnd;
    private float boxCornerRadiusTopStart;
    private final int boxLabelCutoutPaddingPx;

    @ColorInt
    private int boxStrokeColor;
    private final int boxStrokeWidthDefaultPx;
    private final int boxStrokeWidthFocusedPx;
    private int boxStrokeWidthPx;

    /* renamed from: c, reason: collision with root package name */
    final CollapsingTextHelper f13599c;
    private int counterMaxLength;
    private final int counterOverflowTextAppearance;
    private boolean counterOverflowed;
    private final int counterTextAppearance;
    private TextView counterView;
    private ColorStateList defaultHintTextColor;

    @ColorInt
    private final int defaultStrokeColor;

    @ColorInt
    private final int disabledColor;
    private Drawable editTextOriginalDrawable;

    @ColorInt
    private int focusedStrokeColor;
    private ColorStateList focusedTextColor;
    private boolean hasPasswordToggleTintList;
    private boolean hasPasswordToggleTintMode;
    private boolean hasReconstructedEditTextBackground;
    private CharSequence hint;
    private boolean hintAnimationEnabled;
    private boolean hintEnabled;
    private boolean hintExpanded;

    @ColorInt
    private final int hoveredStrokeColor;
    private boolean inDrawableStateChanged;
    private final IndicatorViewController indicatorViewController;
    private final FrameLayout inputFrame;
    private boolean isProvidingHint;
    private Drawable originalEditTextEndDrawable;
    private CharSequence originalHint;
    private CharSequence passwordToggleContentDesc;
    private Drawable passwordToggleDrawable;
    private Drawable passwordToggleDummyDrawable;
    private boolean passwordToggleEnabled;
    private ColorStateList passwordToggleTintList;
    private PorterDuff.Mode passwordToggleTintMode;
    private CheckableImageButton passwordToggleView;
    private boolean passwordToggledVisible;
    private boolean restoringSavedState;
    private final Rect tmpRect;
    private final RectF tmpRectF;
    private Typeface typeface;

    public static class AccessibilityDelegate extends AccessibilityDelegateCompat {
        private final TextInputLayout layout;

        public AccessibilityDelegate(TextInputLayout textInputLayout) {
            this.layout = textInputLayout;
        }

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
            EditText editText = this.layout.getEditText();
            Editable text = editText != null ? editText.getText() : null;
            CharSequence hint = this.layout.getHint();
            CharSequence error = this.layout.getError();
            CharSequence counterOverflowDescription = this.layout.getCounterOverflowDescription();
            boolean z2 = !TextUtils.isEmpty(text);
            boolean z3 = !TextUtils.isEmpty(hint);
            boolean z4 = !TextUtils.isEmpty(error);
            boolean z5 = false;
            boolean z6 = z4 || !TextUtils.isEmpty(counterOverflowDescription);
            if (z2) {
                accessibilityNodeInfoCompat.setText(text);
            } else if (z3) {
                accessibilityNodeInfoCompat.setText(hint);
            }
            if (z3) {
                accessibilityNodeInfoCompat.setHintText(hint);
                if (!z2 && z3) {
                    z5 = true;
                }
                accessibilityNodeInfoCompat.setShowingHintText(z5);
            }
            if (z6) {
                if (!z4) {
                    error = counterOverflowDescription;
                }
                accessibilityNodeInfoCompat.setError(error);
                accessibilityNodeInfoCompat.setContentInvalid(true);
            }
        }

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public void onPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            super.onPopulateAccessibilityEvent(view, accessibilityEvent);
            EditText editText = this.layout.getEditText();
            CharSequence text = editText != null ? editText.getText() : null;
            if (TextUtils.isEmpty(text)) {
                text = this.layout.getHint();
            }
            if (TextUtils.isEmpty(text)) {
                return;
            }
            accessibilityEvent.getText().add(text);
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface BoxBackgroundMode {
    }

    static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>() { // from class: com.google.android.material.textfield.TextInputLayout.SavedState.1
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
        CharSequence f13603a;

        /* renamed from: b, reason: collision with root package name */
        boolean f13604b;

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public String toString() {
            return "TextInputLayout.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " error=" + ((Object) this.f13603a) + i.f9804d;
        }

        @Override // androidx.customview.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i2) {
            super.writeToParcel(parcel, i2);
            TextUtils.writeToParcel(this.f13603a, parcel, i2);
            parcel.writeInt(this.f13604b ? 1 : 0);
        }

        SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.f13603a = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            this.f13604b = parcel.readInt() == 1;
        }
    }

    public TextInputLayout(Context context) {
        this(context, null);
    }

    private void applyBoxAttributes() {
        int i2;
        Drawable drawable;
        if (this.boxBackground == null) {
            return;
        }
        setBoxAttributes();
        EditText editText = this.f13597a;
        if (editText != null && this.boxBackgroundMode == 2) {
            if (editText.getBackground() != null) {
                this.editTextOriginalDrawable = this.f13597a.getBackground();
            }
            ViewCompat.setBackground(this.f13597a, null);
        }
        EditText editText2 = this.f13597a;
        if (editText2 != null && this.boxBackgroundMode == 1 && (drawable = this.editTextOriginalDrawable) != null) {
            ViewCompat.setBackground(editText2, drawable);
        }
        int i3 = this.boxStrokeWidthPx;
        if (i3 > -1 && (i2 = this.boxStrokeColor) != 0) {
            this.boxBackground.setStroke(i3, i2);
        }
        this.boxBackground.setCornerRadii(getCornerRadiiAsArray());
        this.boxBackground.setColor(this.boxBackgroundColor);
        invalidate();
    }

    private void applyCutoutPadding(RectF rectF) {
        float f2 = rectF.left;
        int i2 = this.boxLabelCutoutPaddingPx;
        rectF.left = f2 - i2;
        rectF.top -= i2;
        rectF.right += i2;
        rectF.bottom += i2;
    }

    private void applyPasswordToggleTint() {
        Drawable drawable = this.passwordToggleDrawable;
        if (drawable != null) {
            if (this.hasPasswordToggleTintList || this.hasPasswordToggleTintMode) {
                Drawable drawableMutate = DrawableCompat.wrap(drawable).mutate();
                this.passwordToggleDrawable = drawableMutate;
                if (this.hasPasswordToggleTintList) {
                    DrawableCompat.setTintList(drawableMutate, this.passwordToggleTintList);
                }
                if (this.hasPasswordToggleTintMode) {
                    DrawableCompat.setTintMode(this.passwordToggleDrawable, this.passwordToggleTintMode);
                }
                CheckableImageButton checkableImageButton = this.passwordToggleView;
                if (checkableImageButton != null) {
                    Drawable drawable2 = checkableImageButton.getDrawable();
                    Drawable drawable3 = this.passwordToggleDrawable;
                    if (drawable2 != drawable3) {
                        this.passwordToggleView.setImageDrawable(drawable3);
                    }
                }
            }
        }
    }

    private void assignBoxBackgroundByMode() {
        int i2 = this.boxBackgroundMode;
        if (i2 == 0) {
            this.boxBackground = null;
            return;
        }
        if (i2 == 2 && this.hintEnabled && !(this.boxBackground instanceof CutoutDrawable)) {
            this.boxBackground = new CutoutDrawable();
        } else {
            if (this.boxBackground instanceof GradientDrawable) {
                return;
            }
            this.boxBackground = new GradientDrawable();
        }
    }

    private int calculateBoxBackgroundTop() {
        EditText editText = this.f13597a;
        if (editText == null) {
            return 0;
        }
        int i2 = this.boxBackgroundMode;
        if (i2 == 1) {
            return editText.getTop();
        }
        if (i2 != 2) {
            return 0;
        }
        return editText.getTop() + calculateLabelMarginTop();
    }

    private int calculateCollapsedTextTopBounds() {
        int i2 = this.boxBackgroundMode;
        return i2 != 1 ? i2 != 2 ? getPaddingTop() : getBoxBackground().getBounds().top - calculateLabelMarginTop() : getBoxBackground().getBounds().top + this.boxCollapsedPaddingTopPx;
    }

    private int calculateLabelMarginTop() {
        float collapsedTextHeight;
        if (!this.hintEnabled) {
            return 0;
        }
        int i2 = this.boxBackgroundMode;
        if (i2 == 0 || i2 == 1) {
            collapsedTextHeight = this.f13599c.getCollapsedTextHeight();
        } else {
            if (i2 != 2) {
                return 0;
            }
            collapsedTextHeight = this.f13599c.getCollapsedTextHeight() / 2.0f;
        }
        return (int) collapsedTextHeight;
    }

    private void closeCutout() {
        if (cutoutEnabled()) {
            ((CutoutDrawable) this.boxBackground).b();
        }
    }

    private void collapseHint(boolean z2) {
        ValueAnimator valueAnimator = this.animator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.animator.cancel();
        }
        if (z2 && this.hintAnimationEnabled) {
            b(1.0f);
        } else {
            this.f13599c.setExpansionFraction(1.0f);
        }
        this.hintExpanded = false;
        if (cutoutEnabled()) {
            openCutout();
        }
    }

    private boolean cutoutEnabled() {
        return this.hintEnabled && !TextUtils.isEmpty(this.hint) && (this.boxBackground instanceof CutoutDrawable);
    }

    private void ensureBackgroundDrawableStateWorkaround() {
    }

    private void expandHint(boolean z2) {
        ValueAnimator valueAnimator = this.animator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.animator.cancel();
        }
        if (z2 && this.hintAnimationEnabled) {
            b(0.0f);
        } else {
            this.f13599c.setExpansionFraction(0.0f);
        }
        if (cutoutEnabled() && ((CutoutDrawable) this.boxBackground).a()) {
            closeCutout();
        }
        this.hintExpanded = true;
    }

    @NonNull
    private Drawable getBoxBackground() {
        int i2 = this.boxBackgroundMode;
        if (i2 == 1 || i2 == 2) {
            return this.boxBackground;
        }
        throw new IllegalStateException();
    }

    private float[] getCornerRadiiAsArray() {
        if (ViewUtils.isLayoutRtl(this)) {
            float f2 = this.boxCornerRadiusTopEnd;
            float f3 = this.boxCornerRadiusTopStart;
            float f4 = this.boxCornerRadiusBottomStart;
            float f5 = this.boxCornerRadiusBottomEnd;
            return new float[]{f2, f2, f3, f3, f4, f4, f5, f5};
        }
        float f6 = this.boxCornerRadiusTopStart;
        float f7 = this.boxCornerRadiusTopEnd;
        float f8 = this.boxCornerRadiusBottomEnd;
        float f9 = this.boxCornerRadiusBottomStart;
        return new float[]{f6, f6, f7, f7, f8, f8, f9, f9};
    }

    private boolean hasPasswordTransformation() {
        EditText editText = this.f13597a;
        return editText != null && (editText.getTransformationMethod() instanceof PasswordTransformationMethod);
    }

    private void onApplyBoxBackgroundMode() {
        assignBoxBackgroundByMode();
        if (this.boxBackgroundMode != 0) {
            updateInputLayoutMargins();
        }
        updateTextInputBoxBounds();
    }

    private void openCutout() {
        if (cutoutEnabled()) {
            RectF rectF = this.tmpRectF;
            this.f13599c.getCollapsedTextActualBounds(rectF);
            applyCutoutPadding(rectF);
            ((CutoutDrawable) this.boxBackground).d(rectF);
        }
    }

    private static void recursiveSetEnabled(ViewGroup viewGroup, boolean z2) {
        int childCount = viewGroup.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = viewGroup.getChildAt(i2);
            childAt.setEnabled(z2);
            if (childAt instanceof ViewGroup) {
                recursiveSetEnabled((ViewGroup) childAt, z2);
            }
        }
    }

    private void setBoxAttributes() {
        int i2 = this.boxBackgroundMode;
        if (i2 == 1) {
            this.boxStrokeWidthPx = 0;
        } else if (i2 == 2 && this.focusedStrokeColor == 0) {
            this.focusedStrokeColor = this.focusedTextColor.getColorForState(getDrawableState(), this.focusedTextColor.getDefaultColor());
        }
    }

    private void setEditText(EditText editText) {
        if (this.f13597a != null) {
            throw new IllegalArgumentException("We already have an EditText, can only have one");
        }
        if (!(editText instanceof TextInputEditText)) {
            Log.i(LOG_TAG, "EditText added is not a TextInputEditText. Please switch to using that class instead.");
        }
        this.f13597a = editText;
        onApplyBoxBackgroundMode();
        setTextInputAccessibilityDelegate(new AccessibilityDelegate(this));
        if (!hasPasswordTransformation()) {
            this.f13599c.setTypefaces(this.f13597a.getTypeface());
        }
        this.f13599c.setExpandedTextSize(this.f13597a.getTextSize());
        int gravity = this.f13597a.getGravity();
        this.f13599c.setCollapsedTextGravity((gravity & (-113)) | 48);
        this.f13599c.setExpandedTextGravity(gravity);
        this.f13597a.addTextChangedListener(new TextWatcher() { // from class: com.google.android.material.textfield.TextInputLayout.1
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                TextInputLayout.this.g(!r0.restoringSavedState);
                TextInputLayout textInputLayout = TextInputLayout.this;
                if (textInputLayout.f13598b) {
                    textInputLayout.e(editable.length());
                }
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
            }
        });
        if (this.defaultHintTextColor == null) {
            this.defaultHintTextColor = this.f13597a.getHintTextColors();
        }
        if (this.hintEnabled) {
            if (TextUtils.isEmpty(this.hint)) {
                CharSequence hint = this.f13597a.getHint();
                this.originalHint = hint;
                setHint(hint);
                this.f13597a.setHint((CharSequence) null);
            }
            this.isProvidingHint = true;
        }
        if (this.counterView != null) {
            e(this.f13597a.getText().length());
        }
        this.indicatorViewController.e();
        updatePasswordToggleView();
        updateLabelState(false, true);
    }

    private void setHintInternal(CharSequence charSequence) {
        if (TextUtils.equals(charSequence, this.hint)) {
            return;
        }
        this.hint = charSequence;
        this.f13599c.setText(charSequence);
        if (this.hintExpanded) {
            return;
        }
        openCutout();
    }

    private boolean shouldShowPasswordIcon() {
        return this.passwordToggleEnabled && (hasPasswordTransformation() || this.passwordToggledVisible);
    }

    private void updateEditTextBackgroundBounds() {
        Drawable background;
        EditText editText = this.f13597a;
        if (editText == null || (background = editText.getBackground()) == null) {
            return;
        }
        if (DrawableUtils.canSafelyMutateDrawable(background)) {
            background = background.mutate();
        }
        DescendantOffsetUtils.getDescendantRect(this, this.f13597a, new Rect());
        Rect bounds = background.getBounds();
        if (bounds.left != bounds.right) {
            Rect rect = new Rect();
            background.getPadding(rect);
            background.setBounds(bounds.left - rect.left, bounds.top, bounds.right + (rect.right * 2), this.f13597a.getBottom());
        }
    }

    private void updateInputLayoutMargins() {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.inputFrame.getLayoutParams();
        int iCalculateLabelMarginTop = calculateLabelMarginTop();
        if (iCalculateLabelMarginTop != layoutParams.topMargin) {
            layoutParams.topMargin = iCalculateLabelMarginTop;
            this.inputFrame.requestLayout();
        }
    }

    private void updateLabelState(boolean z2, boolean z3) {
        ColorStateList colorStateList;
        TextView textView;
        boolean zIsEnabled = isEnabled();
        EditText editText = this.f13597a;
        boolean z4 = false;
        boolean z5 = (editText == null || TextUtils.isEmpty(editText.getText())) ? false : true;
        EditText editText2 = this.f13597a;
        if (editText2 != null && editText2.hasFocus()) {
            z4 = true;
        }
        boolean zG = this.indicatorViewController.g();
        ColorStateList colorStateList2 = this.defaultHintTextColor;
        if (colorStateList2 != null) {
            this.f13599c.setCollapsedTextColor(colorStateList2);
            this.f13599c.setExpandedTextColor(this.defaultHintTextColor);
        }
        if (!zIsEnabled) {
            this.f13599c.setCollapsedTextColor(ColorStateList.valueOf(this.disabledColor));
            this.f13599c.setExpandedTextColor(ColorStateList.valueOf(this.disabledColor));
        } else if (zG) {
            this.f13599c.setCollapsedTextColor(this.indicatorViewController.j());
        } else if (this.counterOverflowed && (textView = this.counterView) != null) {
            this.f13599c.setCollapsedTextColor(textView.getTextColors());
        } else if (z4 && (colorStateList = this.focusedTextColor) != null) {
            this.f13599c.setCollapsedTextColor(colorStateList);
        }
        if (z5 || (isEnabled() && (z4 || zG))) {
            if (z3 || this.hintExpanded) {
                collapseHint(z2);
                return;
            }
            return;
        }
        if (z3 || !this.hintExpanded) {
            expandHint(z2);
        }
    }

    private void updatePasswordToggleView() {
        if (this.f13597a == null) {
            return;
        }
        if (!shouldShowPasswordIcon()) {
            CheckableImageButton checkableImageButton = this.passwordToggleView;
            if (checkableImageButton != null && checkableImageButton.getVisibility() == 0) {
                this.passwordToggleView.setVisibility(8);
            }
            if (this.passwordToggleDummyDrawable != null) {
                Drawable[] compoundDrawablesRelative = TextViewCompat.getCompoundDrawablesRelative(this.f13597a);
                if (compoundDrawablesRelative[2] == this.passwordToggleDummyDrawable) {
                    TextViewCompat.setCompoundDrawablesRelative(this.f13597a, compoundDrawablesRelative[0], compoundDrawablesRelative[1], this.originalEditTextEndDrawable, compoundDrawablesRelative[3]);
                    this.passwordToggleDummyDrawable = null;
                    return;
                }
                return;
            }
            return;
        }
        if (this.passwordToggleView == null) {
            CheckableImageButton checkableImageButton2 = (CheckableImageButton) LayoutInflater.from(getContext()).inflate(R.layout.design_text_input_password_icon, (ViewGroup) this.inputFrame, false);
            this.passwordToggleView = checkableImageButton2;
            checkableImageButton2.setImageDrawable(this.passwordToggleDrawable);
            this.passwordToggleView.setContentDescription(this.passwordToggleContentDesc);
            this.inputFrame.addView(this.passwordToggleView);
            this.passwordToggleView.setOnClickListener(new View.OnClickListener() { // from class: com.google.android.material.textfield.TextInputLayout.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    TextInputLayout.this.passwordVisibilityToggleRequested(false);
                }
            });
        }
        EditText editText = this.f13597a;
        if (editText != null && ViewCompat.getMinimumHeight(editText) <= 0) {
            this.f13597a.setMinimumHeight(ViewCompat.getMinimumHeight(this.passwordToggleView));
        }
        this.passwordToggleView.setVisibility(0);
        this.passwordToggleView.setChecked(this.passwordToggledVisible);
        if (this.passwordToggleDummyDrawable == null) {
            this.passwordToggleDummyDrawable = new ColorDrawable();
        }
        this.passwordToggleDummyDrawable.setBounds(0, 0, this.passwordToggleView.getMeasuredWidth(), 1);
        Drawable[] compoundDrawablesRelative2 = TextViewCompat.getCompoundDrawablesRelative(this.f13597a);
        Drawable drawable = compoundDrawablesRelative2[2];
        Drawable drawable2 = this.passwordToggleDummyDrawable;
        if (drawable != drawable2) {
            this.originalEditTextEndDrawable = drawable;
        }
        TextViewCompat.setCompoundDrawablesRelative(this.f13597a, compoundDrawablesRelative2[0], compoundDrawablesRelative2[1], drawable2, compoundDrawablesRelative2[3]);
        this.passwordToggleView.setPadding(this.f13597a.getPaddingLeft(), this.f13597a.getPaddingTop(), this.f13597a.getPaddingRight(), this.f13597a.getPaddingBottom());
    }

    private void updateTextInputBoxBounds() {
        if (this.boxBackgroundMode == 0 || this.boxBackground == null || this.f13597a == null || getRight() == 0) {
            return;
        }
        int left = this.f13597a.getLeft();
        int iCalculateBoxBackgroundTop = calculateBoxBackgroundTop();
        int right = this.f13597a.getRight();
        int bottom = this.f13597a.getBottom() + this.boxBottomOffsetPx;
        if (this.boxBackgroundMode == 2) {
            int i2 = this.boxStrokeWidthFocusedPx;
            left += i2 / 2;
            iCalculateBoxBackgroundTop -= i2 / 2;
            right -= i2 / 2;
            bottom += i2 / 2;
        }
        this.boxBackground.setBounds(left, iCalculateBoxBackgroundTop, right, bottom);
        applyBoxAttributes();
        updateEditTextBackgroundBounds();
    }

    @Override // android.view.ViewGroup
    public void addView(View view, int i2, ViewGroup.LayoutParams layoutParams) {
        if (!(view instanceof EditText)) {
            super.addView(view, i2, layoutParams);
            return;
        }
        FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(layoutParams);
        layoutParams2.gravity = (layoutParams2.gravity & (-113)) | 16;
        this.inputFrame.addView(view, layoutParams2);
        this.inputFrame.setLayoutParams(layoutParams);
        updateInputLayoutMargins();
        setEditText((EditText) view);
    }

    void b(float f2) {
        if (this.f13599c.getExpansionFraction() == f2) {
            return;
        }
        if (this.animator == null) {
            ValueAnimator valueAnimator = new ValueAnimator();
            this.animator = valueAnimator;
            valueAnimator.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
            this.animator.setDuration(167L);
            this.animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.textfield.TextInputLayout.3
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    TextInputLayout.this.f13599c.setExpansionFraction(((Float) valueAnimator2.getAnimatedValue()).floatValue());
                }
            });
        }
        this.animator.setFloatValues(this.f13599c.getExpansionFraction(), f2);
        this.animator.start();
    }

    boolean c() {
        return this.isProvidingHint;
    }

    void d(TextView textView, int i2) {
        try {
            TextViewCompat.setTextAppearance(textView, i2);
            if (textView.getTextColors().getDefaultColor() != -65281) {
                return;
            }
        } catch (Exception unused) {
        }
        TextViewCompat.setTextAppearance(textView, R.style.TextAppearance_AppCompat_Caption);
        textView.setTextColor(ContextCompat.getColor(getContext(), R.color.design_error));
    }

    @Override // android.view.ViewGroup, android.view.View
    public void dispatchProvideAutofillStructure(ViewStructure viewStructure, int i2) {
        EditText editText;
        if (this.originalHint == null || (editText = this.f13597a) == null) {
            super.dispatchProvideAutofillStructure(viewStructure, i2);
            return;
        }
        boolean z2 = this.isProvidingHint;
        this.isProvidingHint = false;
        CharSequence hint = editText.getHint();
        this.f13597a.setHint(this.originalHint);
        try {
            super.dispatchProvideAutofillStructure(viewStructure, i2);
        } finally {
            this.f13597a.setHint(hint);
            this.isProvidingHint = z2;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchRestoreInstanceState(SparseArray sparseArray) {
        this.restoringSavedState = true;
        super.dispatchRestoreInstanceState(sparseArray);
        this.restoringSavedState = false;
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        GradientDrawable gradientDrawable = this.boxBackground;
        if (gradientDrawable != null) {
            gradientDrawable.draw(canvas);
        }
        super.draw(canvas);
        if (this.hintEnabled) {
            this.f13599c.draw(canvas);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void drawableStateChanged() {
        if (this.inDrawableStateChanged) {
            return;
        }
        this.inDrawableStateChanged = true;
        super.drawableStateChanged();
        int[] drawableState = getDrawableState();
        g(ViewCompat.isLaidOut(this) && isEnabled());
        f();
        updateTextInputBoxBounds();
        h();
        CollapsingTextHelper collapsingTextHelper = this.f13599c;
        if (collapsingTextHelper != null ? collapsingTextHelper.setState(drawableState) : false) {
            invalidate();
        }
        this.inDrawableStateChanged = false;
    }

    void e(int i2) {
        boolean z2 = this.counterOverflowed;
        if (this.counterMaxLength == -1) {
            this.counterView.setText(String.valueOf(i2));
            this.counterView.setContentDescription(null);
            this.counterOverflowed = false;
        } else {
            if (ViewCompat.getAccessibilityLiveRegion(this.counterView) == 1) {
                ViewCompat.setAccessibilityLiveRegion(this.counterView, 0);
            }
            boolean z3 = i2 > this.counterMaxLength;
            this.counterOverflowed = z3;
            if (z2 != z3) {
                d(this.counterView, z3 ? this.counterOverflowTextAppearance : this.counterTextAppearance);
                if (this.counterOverflowed) {
                    ViewCompat.setAccessibilityLiveRegion(this.counterView, 1);
                }
            }
            this.counterView.setText(getContext().getString(R.string.character_counter_pattern, Integer.valueOf(i2), Integer.valueOf(this.counterMaxLength)));
            this.counterView.setContentDescription(getContext().getString(R.string.character_counter_content_description, Integer.valueOf(i2), Integer.valueOf(this.counterMaxLength)));
        }
        if (this.f13597a == null || z2 == this.counterOverflowed) {
            return;
        }
        g(false);
        h();
        f();
    }

    void f() {
        Drawable background;
        TextView textView;
        EditText editText = this.f13597a;
        if (editText == null || (background = editText.getBackground()) == null) {
            return;
        }
        ensureBackgroundDrawableStateWorkaround();
        if (DrawableUtils.canSafelyMutateDrawable(background)) {
            background = background.mutate();
        }
        if (this.indicatorViewController.g()) {
            background.setColorFilter(AppCompatDrawableManager.getPorterDuffColorFilter(this.indicatorViewController.i(), PorterDuff.Mode.SRC_IN));
        } else if (this.counterOverflowed && (textView = this.counterView) != null) {
            background.setColorFilter(AppCompatDrawableManager.getPorterDuffColorFilter(textView.getCurrentTextColor(), PorterDuff.Mode.SRC_IN));
        } else {
            DrawableCompat.clearColorFilter(background);
            this.f13597a.refreshDrawableState();
        }
    }

    void g(boolean z2) {
        updateLabelState(z2, false);
    }

    public int getBoxBackgroundColor() {
        return this.boxBackgroundColor;
    }

    public float getBoxCornerRadiusBottomEnd() {
        return this.boxCornerRadiusBottomEnd;
    }

    public float getBoxCornerRadiusBottomStart() {
        return this.boxCornerRadiusBottomStart;
    }

    public float getBoxCornerRadiusTopEnd() {
        return this.boxCornerRadiusTopEnd;
    }

    public float getBoxCornerRadiusTopStart() {
        return this.boxCornerRadiusTopStart;
    }

    public int getBoxStrokeColor() {
        return this.focusedStrokeColor;
    }

    public int getCounterMaxLength() {
        return this.counterMaxLength;
    }

    @Nullable
    CharSequence getCounterOverflowDescription() {
        TextView textView;
        if (this.f13598b && this.counterOverflowed && (textView = this.counterView) != null) {
            return textView.getContentDescription();
        }
        return null;
    }

    @Nullable
    public ColorStateList getDefaultHintTextColor() {
        return this.defaultHintTextColor;
    }

    @Nullable
    public EditText getEditText() {
        return this.f13597a;
    }

    @Nullable
    public CharSequence getError() {
        if (this.indicatorViewController.p()) {
            return this.indicatorViewController.h();
        }
        return null;
    }

    @ColorInt
    public int getErrorCurrentTextColors() {
        return this.indicatorViewController.i();
    }

    @VisibleForTesting
    final int getErrorTextCurrentColor() {
        return this.indicatorViewController.i();
    }

    @Nullable
    public CharSequence getHelperText() {
        if (this.indicatorViewController.q()) {
            return this.indicatorViewController.k();
        }
        return null;
    }

    @ColorInt
    public int getHelperTextCurrentTextColor() {
        return this.indicatorViewController.l();
    }

    @Nullable
    public CharSequence getHint() {
        if (this.hintEnabled) {
            return this.hint;
        }
        return null;
    }

    @VisibleForTesting
    final float getHintCollapsedTextHeight() {
        return this.f13599c.getCollapsedTextHeight();
    }

    @VisibleForTesting
    final int getHintCurrentCollapsedTextColor() {
        return this.f13599c.getCurrentCollapsedTextColor();
    }

    @Nullable
    public CharSequence getPasswordVisibilityToggleContentDescription() {
        return this.passwordToggleContentDesc;
    }

    @Nullable
    public Drawable getPasswordVisibilityToggleDrawable() {
        return this.passwordToggleDrawable;
    }

    @Nullable
    public Typeface getTypeface() {
        return this.typeface;
    }

    void h() {
        TextView textView;
        if (this.boxBackground == null || this.boxBackgroundMode == 0) {
            return;
        }
        EditText editText = this.f13597a;
        boolean z2 = false;
        boolean z3 = editText != null && editText.hasFocus();
        EditText editText2 = this.f13597a;
        if (editText2 != null && editText2.isHovered()) {
            z2 = true;
        }
        if (this.boxBackgroundMode == 2) {
            if (!isEnabled()) {
                this.boxStrokeColor = this.disabledColor;
            } else if (this.indicatorViewController.g()) {
                this.boxStrokeColor = this.indicatorViewController.i();
            } else if (this.counterOverflowed && (textView = this.counterView) != null) {
                this.boxStrokeColor = textView.getCurrentTextColor();
            } else if (z3) {
                this.boxStrokeColor = this.focusedStrokeColor;
            } else if (z2) {
                this.boxStrokeColor = this.hoveredStrokeColor;
            } else {
                this.boxStrokeColor = this.defaultStrokeColor;
            }
            if ((z2 || z3) && isEnabled()) {
                this.boxStrokeWidthPx = this.boxStrokeWidthFocusedPx;
            } else {
                this.boxStrokeWidthPx = this.boxStrokeWidthDefaultPx;
            }
            applyBoxAttributes();
        }
    }

    public boolean isCounterEnabled() {
        return this.f13598b;
    }

    public boolean isErrorEnabled() {
        return this.indicatorViewController.p();
    }

    public boolean isHelperTextEnabled() {
        return this.indicatorViewController.q();
    }

    public boolean isHintAnimationEnabled() {
        return this.hintAnimationEnabled;
    }

    public boolean isHintEnabled() {
        return this.hintEnabled;
    }

    public boolean isPasswordVisibilityToggleEnabled() {
        return this.passwordToggleEnabled;
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        EditText editText;
        super.onLayout(z2, i2, i3, i4, i5);
        if (this.boxBackground != null) {
            updateTextInputBoxBounds();
        }
        if (!this.hintEnabled || (editText = this.f13597a) == null) {
            return;
        }
        Rect rect = this.tmpRect;
        DescendantOffsetUtils.getDescendantRect(this, editText, rect);
        int compoundPaddingLeft = rect.left + this.f13597a.getCompoundPaddingLeft();
        int compoundPaddingRight = rect.right - this.f13597a.getCompoundPaddingRight();
        int iCalculateCollapsedTextTopBounds = calculateCollapsedTextTopBounds();
        this.f13599c.setExpandedBounds(compoundPaddingLeft, rect.top + this.f13597a.getCompoundPaddingTop(), compoundPaddingRight, rect.bottom - this.f13597a.getCompoundPaddingBottom());
        this.f13599c.setCollapsedBounds(compoundPaddingLeft, iCalculateCollapsedTextTopBounds, compoundPaddingRight, (i5 - i3) - getPaddingBottom());
        this.f13599c.recalculate();
        if (!cutoutEnabled() || this.hintExpanded) {
            return;
        }
        openCutout();
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onMeasure(int i2, int i3) {
        updatePasswordToggleView();
        super.onMeasure(i2, i3);
    }

    @Override // android.view.View
    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        setError(savedState.f13603a);
        if (savedState.f13604b) {
            passwordVisibilityToggleRequested(true);
        }
        requestLayout();
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        if (this.indicatorViewController.g()) {
            savedState.f13603a = getError();
        }
        savedState.f13604b = this.passwordToggledVisible;
        return savedState;
    }

    public void passwordVisibilityToggleRequested(boolean z2) {
        if (this.passwordToggleEnabled) {
            int selectionEnd = this.f13597a.getSelectionEnd();
            if (hasPasswordTransformation()) {
                this.f13597a.setTransformationMethod(null);
                this.passwordToggledVisible = true;
            } else {
                this.f13597a.setTransformationMethod(PasswordTransformationMethod.getInstance());
                this.passwordToggledVisible = false;
            }
            this.passwordToggleView.setChecked(this.passwordToggledVisible);
            if (z2) {
                this.passwordToggleView.jumpDrawablesToCurrentState();
            }
            this.f13597a.setSelection(selectionEnd);
        }
    }

    public void setBoxBackgroundColor(@ColorInt int i2) {
        if (this.boxBackgroundColor != i2) {
            this.boxBackgroundColor = i2;
            applyBoxAttributes();
        }
    }

    public void setBoxBackgroundColorResource(@ColorRes int i2) {
        setBoxBackgroundColor(ContextCompat.getColor(getContext(), i2));
    }

    public void setBoxBackgroundMode(int i2) {
        if (i2 == this.boxBackgroundMode) {
            return;
        }
        this.boxBackgroundMode = i2;
        onApplyBoxBackgroundMode();
    }

    public void setBoxCornerRadii(float f2, float f3, float f4, float f5) {
        if (this.boxCornerRadiusTopStart == f2 && this.boxCornerRadiusTopEnd == f3 && this.boxCornerRadiusBottomEnd == f5 && this.boxCornerRadiusBottomStart == f4) {
            return;
        }
        this.boxCornerRadiusTopStart = f2;
        this.boxCornerRadiusTopEnd = f3;
        this.boxCornerRadiusBottomEnd = f5;
        this.boxCornerRadiusBottomStart = f4;
        applyBoxAttributes();
    }

    public void setBoxCornerRadiiResources(@DimenRes int i2, @DimenRes int i3, @DimenRes int i4, @DimenRes int i5) {
        setBoxCornerRadii(getContext().getResources().getDimension(i2), getContext().getResources().getDimension(i3), getContext().getResources().getDimension(i4), getContext().getResources().getDimension(i5));
    }

    public void setBoxStrokeColor(@ColorInt int i2) {
        if (this.focusedStrokeColor != i2) {
            this.focusedStrokeColor = i2;
            h();
        }
    }

    public void setCounterEnabled(boolean z2) {
        if (this.f13598b != z2) {
            if (z2) {
                AppCompatTextView appCompatTextView = new AppCompatTextView(getContext());
                this.counterView = appCompatTextView;
                appCompatTextView.setId(R.id.textinput_counter);
                Typeface typeface = this.typeface;
                if (typeface != null) {
                    this.counterView.setTypeface(typeface);
                }
                this.counterView.setMaxLines(1);
                d(this.counterView, this.counterTextAppearance);
                this.indicatorViewController.d(this.counterView, 2);
                EditText editText = this.f13597a;
                if (editText == null) {
                    e(0);
                } else {
                    e(editText.getText().length());
                }
            } else {
                this.indicatorViewController.r(this.counterView, 2);
                this.counterView = null;
            }
            this.f13598b = z2;
        }
    }

    public void setCounterMaxLength(int i2) {
        if (this.counterMaxLength != i2) {
            if (i2 > 0) {
                this.counterMaxLength = i2;
            } else {
                this.counterMaxLength = -1;
            }
            if (this.f13598b) {
                EditText editText = this.f13597a;
                e(editText == null ? 0 : editText.getText().length());
            }
        }
    }

    public void setDefaultHintTextColor(@Nullable ColorStateList colorStateList) {
        this.defaultHintTextColor = colorStateList;
        this.focusedTextColor = colorStateList;
        if (this.f13597a != null) {
            g(false);
        }
    }

    @Override // android.view.View
    public void setEnabled(boolean z2) {
        recursiveSetEnabled(this, z2);
        super.setEnabled(z2);
    }

    public void setError(@Nullable CharSequence charSequence) {
        if (!this.indicatorViewController.p()) {
            if (TextUtils.isEmpty(charSequence)) {
                return;
            } else {
                setErrorEnabled(true);
            }
        }
        if (TextUtils.isEmpty(charSequence)) {
            this.indicatorViewController.m();
        } else {
            this.indicatorViewController.z(charSequence);
        }
    }

    public void setErrorEnabled(boolean z2) {
        this.indicatorViewController.s(z2);
    }

    public void setErrorTextAppearance(@StyleRes int i2) {
        this.indicatorViewController.t(i2);
    }

    public void setErrorTextColor(@Nullable ColorStateList colorStateList) {
        this.indicatorViewController.u(colorStateList);
    }

    public void setHelperText(@Nullable CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            if (isHelperTextEnabled()) {
                setHelperTextEnabled(false);
            }
        } else {
            if (!isHelperTextEnabled()) {
                setHelperTextEnabled(true);
            }
            this.indicatorViewController.A(charSequence);
        }
    }

    public void setHelperTextColor(@Nullable ColorStateList colorStateList) {
        this.indicatorViewController.x(colorStateList);
    }

    public void setHelperTextEnabled(boolean z2) {
        this.indicatorViewController.w(z2);
    }

    public void setHelperTextTextAppearance(@StyleRes int i2) {
        this.indicatorViewController.v(i2);
    }

    public void setHint(@Nullable CharSequence charSequence) {
        if (this.hintEnabled) {
            setHintInternal(charSequence);
            sendAccessibilityEvent(2048);
        }
    }

    public void setHintAnimationEnabled(boolean z2) {
        this.hintAnimationEnabled = z2;
    }

    public void setHintEnabled(boolean z2) {
        if (z2 != this.hintEnabled) {
            this.hintEnabled = z2;
            if (z2) {
                CharSequence hint = this.f13597a.getHint();
                if (!TextUtils.isEmpty(hint)) {
                    if (TextUtils.isEmpty(this.hint)) {
                        setHint(hint);
                    }
                    this.f13597a.setHint((CharSequence) null);
                }
                this.isProvidingHint = true;
            } else {
                this.isProvidingHint = false;
                if (!TextUtils.isEmpty(this.hint) && TextUtils.isEmpty(this.f13597a.getHint())) {
                    this.f13597a.setHint(this.hint);
                }
                setHintInternal(null);
            }
            if (this.f13597a != null) {
                updateInputLayoutMargins();
            }
        }
    }

    public void setHintTextAppearance(@StyleRes int i2) {
        this.f13599c.setCollapsedTextAppearance(i2);
        this.focusedTextColor = this.f13599c.getCollapsedTextColor();
        if (this.f13597a != null) {
            g(false);
            updateInputLayoutMargins();
        }
    }

    public void setPasswordVisibilityToggleContentDescription(@StringRes int i2) {
        setPasswordVisibilityToggleContentDescription(i2 != 0 ? getResources().getText(i2) : null);
    }

    public void setPasswordVisibilityToggleDrawable(@DrawableRes int i2) {
        setPasswordVisibilityToggleDrawable(i2 != 0 ? AppCompatResources.getDrawable(getContext(), i2) : null);
    }

    public void setPasswordVisibilityToggleEnabled(boolean z2) {
        EditText editText;
        if (this.passwordToggleEnabled != z2) {
            this.passwordToggleEnabled = z2;
            if (!z2 && this.passwordToggledVisible && (editText = this.f13597a) != null) {
                editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
            this.passwordToggledVisible = false;
            updatePasswordToggleView();
        }
    }

    public void setPasswordVisibilityToggleTintList(@Nullable ColorStateList colorStateList) {
        this.passwordToggleTintList = colorStateList;
        this.hasPasswordToggleTintList = true;
        applyPasswordToggleTint();
    }

    public void setPasswordVisibilityToggleTintMode(@Nullable PorterDuff.Mode mode) {
        this.passwordToggleTintMode = mode;
        this.hasPasswordToggleTintMode = true;
        applyPasswordToggleTint();
    }

    public void setTextInputAccessibilityDelegate(AccessibilityDelegate accessibilityDelegate) {
        EditText editText = this.f13597a;
        if (editText != null) {
            ViewCompat.setAccessibilityDelegate(editText, accessibilityDelegate);
        }
    }

    public void setTypeface(@Nullable Typeface typeface) {
        if (typeface != this.typeface) {
            this.typeface = typeface;
            this.f13599c.setTypefaces(typeface);
            this.indicatorViewController.y(typeface);
            TextView textView = this.counterView;
            if (textView != null) {
                textView.setTypeface(typeface);
            }
        }
    }

    public TextInputLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.textInputStyle);
    }

    public TextInputLayout(Context context, AttributeSet attributeSet, int i2) throws Resources.NotFoundException {
        super(context, attributeSet, i2);
        this.indicatorViewController = new IndicatorViewController(this);
        this.tmpRect = new Rect();
        this.tmpRectF = new RectF();
        CollapsingTextHelper collapsingTextHelper = new CollapsingTextHelper(this);
        this.f13599c = collapsingTextHelper;
        setOrientation(1);
        setWillNotDraw(false);
        setAddStatesFromChildren(true);
        FrameLayout frameLayout = new FrameLayout(context);
        this.inputFrame = frameLayout;
        frameLayout.setAddStatesFromChildren(true);
        addView(frameLayout);
        TimeInterpolator timeInterpolator = AnimationUtils.LINEAR_INTERPOLATOR;
        collapsingTextHelper.setTextSizeInterpolator(timeInterpolator);
        collapsingTextHelper.setPositionInterpolator(timeInterpolator);
        collapsingTextHelper.setCollapsedTextGravity(8388659);
        TintTypedArray tintTypedArrayObtainTintedStyledAttributes = ThemeEnforcement.obtainTintedStyledAttributes(context, attributeSet, R.styleable.TextInputLayout, i2, R.style.Widget_Design_TextInputLayout, new int[0]);
        this.hintEnabled = tintTypedArrayObtainTintedStyledAttributes.getBoolean(R.styleable.TextInputLayout_hintEnabled, true);
        setHint(tintTypedArrayObtainTintedStyledAttributes.getText(R.styleable.TextInputLayout_android_hint));
        this.hintAnimationEnabled = tintTypedArrayObtainTintedStyledAttributes.getBoolean(R.styleable.TextInputLayout_hintAnimationEnabled, true);
        this.boxBottomOffsetPx = context.getResources().getDimensionPixelOffset(R.dimen.mtrl_textinput_box_bottom_offset);
        this.boxLabelCutoutPaddingPx = context.getResources().getDimensionPixelOffset(R.dimen.mtrl_textinput_box_label_cutout_padding);
        this.boxCollapsedPaddingTopPx = tintTypedArrayObtainTintedStyledAttributes.getDimensionPixelOffset(R.styleable.TextInputLayout_boxCollapsedPaddingTop, 0);
        this.boxCornerRadiusTopStart = tintTypedArrayObtainTintedStyledAttributes.getDimension(R.styleable.TextInputLayout_boxCornerRadiusTopStart, 0.0f);
        this.boxCornerRadiusTopEnd = tintTypedArrayObtainTintedStyledAttributes.getDimension(R.styleable.TextInputLayout_boxCornerRadiusTopEnd, 0.0f);
        this.boxCornerRadiusBottomEnd = tintTypedArrayObtainTintedStyledAttributes.getDimension(R.styleable.TextInputLayout_boxCornerRadiusBottomEnd, 0.0f);
        this.boxCornerRadiusBottomStart = tintTypedArrayObtainTintedStyledAttributes.getDimension(R.styleable.TextInputLayout_boxCornerRadiusBottomStart, 0.0f);
        this.boxBackgroundColor = tintTypedArrayObtainTintedStyledAttributes.getColor(R.styleable.TextInputLayout_boxBackgroundColor, 0);
        this.focusedStrokeColor = tintTypedArrayObtainTintedStyledAttributes.getColor(R.styleable.TextInputLayout_boxStrokeColor, 0);
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.mtrl_textinput_box_stroke_width_default);
        this.boxStrokeWidthDefaultPx = dimensionPixelSize;
        this.boxStrokeWidthFocusedPx = context.getResources().getDimensionPixelSize(R.dimen.mtrl_textinput_box_stroke_width_focused);
        this.boxStrokeWidthPx = dimensionPixelSize;
        setBoxBackgroundMode(tintTypedArrayObtainTintedStyledAttributes.getInt(R.styleable.TextInputLayout_boxBackgroundMode, 0));
        if (tintTypedArrayObtainTintedStyledAttributes.hasValue(R.styleable.TextInputLayout_android_textColorHint)) {
            ColorStateList colorStateList = tintTypedArrayObtainTintedStyledAttributes.getColorStateList(R.styleable.TextInputLayout_android_textColorHint);
            this.focusedTextColor = colorStateList;
            this.defaultHintTextColor = colorStateList;
        }
        this.defaultStrokeColor = ContextCompat.getColor(context, R.color.mtrl_textinput_default_box_stroke_color);
        this.disabledColor = ContextCompat.getColor(context, R.color.mtrl_textinput_disabled_color);
        this.hoveredStrokeColor = ContextCompat.getColor(context, R.color.mtrl_textinput_hovered_box_stroke_color);
        if (tintTypedArrayObtainTintedStyledAttributes.getResourceId(R.styleable.TextInputLayout_hintTextAppearance, -1) != -1) {
            setHintTextAppearance(tintTypedArrayObtainTintedStyledAttributes.getResourceId(R.styleable.TextInputLayout_hintTextAppearance, 0));
        }
        int resourceId = tintTypedArrayObtainTintedStyledAttributes.getResourceId(R.styleable.TextInputLayout_errorTextAppearance, 0);
        boolean z2 = tintTypedArrayObtainTintedStyledAttributes.getBoolean(R.styleable.TextInputLayout_errorEnabled, false);
        int resourceId2 = tintTypedArrayObtainTintedStyledAttributes.getResourceId(R.styleable.TextInputLayout_helperTextTextAppearance, 0);
        boolean z3 = tintTypedArrayObtainTintedStyledAttributes.getBoolean(R.styleable.TextInputLayout_helperTextEnabled, false);
        CharSequence text = tintTypedArrayObtainTintedStyledAttributes.getText(R.styleable.TextInputLayout_helperText);
        boolean z4 = tintTypedArrayObtainTintedStyledAttributes.getBoolean(R.styleable.TextInputLayout_counterEnabled, false);
        setCounterMaxLength(tintTypedArrayObtainTintedStyledAttributes.getInt(R.styleable.TextInputLayout_counterMaxLength, -1));
        this.counterTextAppearance = tintTypedArrayObtainTintedStyledAttributes.getResourceId(R.styleable.TextInputLayout_counterTextAppearance, 0);
        this.counterOverflowTextAppearance = tintTypedArrayObtainTintedStyledAttributes.getResourceId(R.styleable.TextInputLayout_counterOverflowTextAppearance, 0);
        this.passwordToggleEnabled = tintTypedArrayObtainTintedStyledAttributes.getBoolean(R.styleable.TextInputLayout_passwordToggleEnabled, false);
        this.passwordToggleDrawable = tintTypedArrayObtainTintedStyledAttributes.getDrawable(R.styleable.TextInputLayout_passwordToggleDrawable);
        this.passwordToggleContentDesc = tintTypedArrayObtainTintedStyledAttributes.getText(R.styleable.TextInputLayout_passwordToggleContentDescription);
        if (tintTypedArrayObtainTintedStyledAttributes.hasValue(R.styleable.TextInputLayout_passwordToggleTint)) {
            this.hasPasswordToggleTintList = true;
            this.passwordToggleTintList = tintTypedArrayObtainTintedStyledAttributes.getColorStateList(R.styleable.TextInputLayout_passwordToggleTint);
        }
        if (tintTypedArrayObtainTintedStyledAttributes.hasValue(R.styleable.TextInputLayout_passwordToggleTintMode)) {
            this.hasPasswordToggleTintMode = true;
            this.passwordToggleTintMode = ViewUtils.parseTintMode(tintTypedArrayObtainTintedStyledAttributes.getInt(R.styleable.TextInputLayout_passwordToggleTintMode, -1), null);
        }
        tintTypedArrayObtainTintedStyledAttributes.recycle();
        setHelperTextEnabled(z3);
        setHelperText(text);
        setHelperTextTextAppearance(resourceId2);
        setErrorEnabled(z2);
        setErrorTextAppearance(resourceId);
        setCounterEnabled(z4);
        applyPasswordToggleTint();
        ViewCompat.setImportantForAccessibility(this, 2);
    }

    public void setPasswordVisibilityToggleContentDescription(@Nullable CharSequence charSequence) {
        this.passwordToggleContentDesc = charSequence;
        CheckableImageButton checkableImageButton = this.passwordToggleView;
        if (checkableImageButton != null) {
            checkableImageButton.setContentDescription(charSequence);
        }
    }

    public void setPasswordVisibilityToggleDrawable(@Nullable Drawable drawable) {
        this.passwordToggleDrawable = drawable;
        CheckableImageButton checkableImageButton = this.passwordToggleView;
        if (checkableImageButton != null) {
            checkableImageButton.setImageDrawable(drawable);
        }
    }
}
