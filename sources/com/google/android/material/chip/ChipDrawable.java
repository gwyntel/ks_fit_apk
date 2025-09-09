package com.google.android.material.chip;

import android.R;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Xml;
import androidx.annotation.AnimatorRes;
import androidx.annotation.AttrRes;
import androidx.annotation.BoolRes;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;
import androidx.annotation.XmlRes;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.ColorUtils;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.graphics.drawable.TintAwareDrawable;
import androidx.core.internal.view.SupportMenu;
import androidx.core.text.BidiFormatter;
import androidx.core.view.ViewCompat;
import com.google.android.material.animation.MotionSpec;
import com.google.android.material.canvas.CanvasCompat;
import com.google.android.material.drawable.DrawableUtils;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.resources.TextAppearance;
import com.google.android.material.ripple.RippleUtils;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes3.dex */
public class ChipDrawable extends Drawable implements TintAwareDrawable, Drawable.Callback {
    private static final boolean DEBUG = false;
    private static final int[] DEFAULT_STATE = {R.attr.state_enabled};
    private static final String NAMESPACE_APP = "http://schemas.android.com/apk/res-auto";
    private int alpha;
    private boolean checkable;

    @Nullable
    private Drawable checkedIcon;
    private boolean checkedIconVisible;

    @Nullable
    private ColorStateList chipBackgroundColor;
    private float chipCornerRadius;
    private float chipEndPadding;

    @Nullable
    private Drawable chipIcon;
    private float chipIconSize;

    @Nullable
    private ColorStateList chipIconTint;
    private boolean chipIconVisible;
    private float chipMinHeight;
    private final Paint chipPaint;
    private float chipStartPadding;

    @Nullable
    private ColorStateList chipStrokeColor;
    private float chipStrokeWidth;

    @Nullable
    private Drawable closeIcon;

    @Nullable
    private CharSequence closeIconContentDescription;
    private float closeIconEndPadding;
    private float closeIconSize;
    private float closeIconStartPadding;
    private int[] closeIconStateSet;

    @Nullable
    private ColorStateList closeIconTint;
    private boolean closeIconVisible;

    @Nullable
    private ColorFilter colorFilter;

    @Nullable
    private ColorStateList compatRippleColor;
    private final Context context;
    private boolean currentChecked;

    @ColorInt
    private int currentChipBackgroundColor;

    @ColorInt
    private int currentChipStrokeColor;

    @ColorInt
    private int currentCompatRippleColor;

    @ColorInt
    private int currentTextColor;

    @ColorInt
    private int currentTint;

    @Nullable
    private final Paint debugPaint;
    private WeakReference<Delegate> delegate;
    private final ResourcesCompat.FontCallback fontCallback = new ResourcesCompat.FontCallback() { // from class: com.google.android.material.chip.ChipDrawable.1
        @Override // androidx.core.content.res.ResourcesCompat.FontCallback
        /* renamed from: onFontRetrievalFailed */
        public void lambda$callbackFailAsync$1(int i2) {
        }

        @Override // androidx.core.content.res.ResourcesCompat.FontCallback
        /* renamed from: onFontRetrieved */
        public void lambda$callbackSuccessAsync$0(@NonNull Typeface typeface) {
            ChipDrawable.this.textWidthDirty = true;
            ChipDrawable.this.d();
            ChipDrawable.this.invalidateSelf();
        }
    };
    private final Paint.FontMetrics fontMetrics;

    @Nullable
    private MotionSpec hideMotionSpec;
    private float iconEndPadding;
    private float iconStartPadding;
    private int maxWidth;
    private final PointF pointF;

    @Nullable
    private CharSequence rawText;
    private final RectF rectF;

    @Nullable
    private ColorStateList rippleColor;
    private boolean shouldDrawText;

    @Nullable
    private MotionSpec showMotionSpec;

    @Nullable
    private TextAppearance textAppearance;
    private float textEndPadding;
    private final TextPaint textPaint;
    private float textStartPadding;
    private float textWidth;
    private boolean textWidthDirty;

    @Nullable
    private ColorStateList tint;

    @Nullable
    private PorterDuffColorFilter tintFilter;

    @Nullable
    private PorterDuff.Mode tintMode;
    private TextUtils.TruncateAt truncateAt;

    @Nullable
    private CharSequence unicodeWrappedText;
    private boolean useCompatRipple;

    public interface Delegate {
        void onChipDrawableSizeChange();
    }

    private ChipDrawable(Context context) {
        TextPaint textPaint = new TextPaint(1);
        this.textPaint = textPaint;
        this.chipPaint = new Paint(1);
        this.fontMetrics = new Paint.FontMetrics();
        this.rectF = new RectF();
        this.pointF = new PointF();
        this.alpha = 255;
        this.tintMode = PorterDuff.Mode.SRC_IN;
        this.delegate = new WeakReference<>(null);
        this.textWidthDirty = true;
        this.context = context;
        this.rawText = "";
        textPaint.density = context.getResources().getDisplayMetrics().density;
        this.debugPaint = null;
        int[] iArr = DEFAULT_STATE;
        setState(iArr);
        setCloseIconState(iArr);
        this.shouldDrawText = true;
    }

    private void applyChildDrawable(@Nullable Drawable drawable) {
        if (drawable != null) {
            drawable.setCallback(this);
            DrawableCompat.setLayoutDirection(drawable, DrawableCompat.getLayoutDirection(this));
            drawable.setLevel(getLevel());
            drawable.setVisible(isVisible(), false);
            if (drawable == this.closeIcon) {
                if (drawable.isStateful()) {
                    drawable.setState(getCloseIconState());
                }
                DrawableCompat.setTintList(drawable, this.closeIconTint);
            } else if (drawable.isStateful()) {
                drawable.setState(getState());
            }
        }
    }

    private void calculateChipIconBounds(Rect rect, RectF rectF) {
        rectF.setEmpty();
        if (showsChipIcon() || showsCheckedIcon()) {
            float f2 = this.chipStartPadding + this.iconStartPadding;
            if (DrawableCompat.getLayoutDirection(this) == 0) {
                float f3 = rect.left + f2;
                rectF.left = f3;
                rectF.right = f3 + this.chipIconSize;
            } else {
                float f4 = rect.right - f2;
                rectF.right = f4;
                rectF.left = f4 - this.chipIconSize;
            }
            float fExactCenterY = rect.exactCenterY();
            float f5 = this.chipIconSize;
            float f6 = fExactCenterY - (f5 / 2.0f);
            rectF.top = f6;
            rectF.bottom = f6 + f5;
        }
    }

    private void calculateChipTouchBounds(Rect rect, RectF rectF) {
        rectF.set(rect);
        if (showsCloseIcon()) {
            float f2 = this.chipEndPadding + this.closeIconEndPadding + this.closeIconSize + this.closeIconStartPadding + this.textEndPadding;
            if (DrawableCompat.getLayoutDirection(this) == 0) {
                rectF.right = rect.right - f2;
            } else {
                rectF.left = rect.left + f2;
            }
        }
    }

    private void calculateCloseIconBounds(Rect rect, RectF rectF) {
        rectF.setEmpty();
        if (showsCloseIcon()) {
            float f2 = this.chipEndPadding + this.closeIconEndPadding;
            if (DrawableCompat.getLayoutDirection(this) == 0) {
                float f3 = rect.right - f2;
                rectF.right = f3;
                rectF.left = f3 - this.closeIconSize;
            } else {
                float f4 = rect.left + f2;
                rectF.left = f4;
                rectF.right = f4 + this.closeIconSize;
            }
            float fExactCenterY = rect.exactCenterY();
            float f5 = this.closeIconSize;
            float f6 = fExactCenterY - (f5 / 2.0f);
            rectF.top = f6;
            rectF.bottom = f6 + f5;
        }
    }

    private void calculateCloseIconTouchBounds(Rect rect, RectF rectF) {
        rectF.setEmpty();
        if (showsCloseIcon()) {
            float f2 = this.chipEndPadding + this.closeIconEndPadding + this.closeIconSize + this.closeIconStartPadding + this.textEndPadding;
            if (DrawableCompat.getLayoutDirection(this) == 0) {
                float f3 = rect.right;
                rectF.right = f3;
                rectF.left = f3 - f2;
            } else {
                int i2 = rect.left;
                rectF.left = i2;
                rectF.right = i2 + f2;
            }
            rectF.top = rect.top;
            rectF.bottom = rect.bottom;
        }
    }

    private float calculateCloseIconWidth() {
        if (showsCloseIcon()) {
            return this.closeIconStartPadding + this.closeIconSize + this.closeIconEndPadding;
        }
        return 0.0f;
    }

    private void calculateTextBounds(Rect rect, RectF rectF) {
        rectF.setEmpty();
        if (this.unicodeWrappedText != null) {
            float fB = this.chipStartPadding + b() + this.textStartPadding;
            float fCalculateCloseIconWidth = this.chipEndPadding + calculateCloseIconWidth() + this.textEndPadding;
            if (DrawableCompat.getLayoutDirection(this) == 0) {
                rectF.left = rect.left + fB;
                rectF.right = rect.right - fCalculateCloseIconWidth;
            } else {
                rectF.left = rect.left + fCalculateCloseIconWidth;
                rectF.right = rect.right - fB;
            }
            rectF.top = rect.top;
            rectF.bottom = rect.bottom;
        }
    }

    private float calculateTextCenterFromBaseline() {
        this.textPaint.getFontMetrics(this.fontMetrics);
        Paint.FontMetrics fontMetrics = this.fontMetrics;
        return (fontMetrics.descent + fontMetrics.ascent) / 2.0f;
    }

    private float calculateTextWidth(@Nullable CharSequence charSequence) {
        if (charSequence == null) {
            return 0.0f;
        }
        return this.textPaint.measureText(charSequence, 0, charSequence.length());
    }

    private boolean canShowCheckedIcon() {
        return this.checkedIconVisible && this.checkedIcon != null && this.checkable;
    }

    public static ChipDrawable createFromAttributes(Context context, AttributeSet attributeSet, @AttrRes int i2, @StyleRes int i3) {
        ChipDrawable chipDrawable = new ChipDrawable(context);
        chipDrawable.loadFromAttributes(attributeSet, i2, i3);
        return chipDrawable;
    }

    public static ChipDrawable createFromResource(Context context, @XmlRes int i2) throws XmlPullParserException, Resources.NotFoundException, IOException {
        int next;
        try {
            XmlResourceParser xml = context.getResources().getXml(i2);
            do {
                next = xml.next();
                if (next == 2) {
                    break;
                }
            } while (next != 1);
            if (next != 2) {
                throw new XmlPullParserException("No start tag found");
            }
            if (!TextUtils.equals(xml.getName(), "chip")) {
                throw new XmlPullParserException("Must have a <chip> start tag");
            }
            AttributeSet attributeSetAsAttributeSet = Xml.asAttributeSet(xml);
            int styleAttribute = attributeSetAsAttributeSet.getStyleAttribute();
            if (styleAttribute == 0) {
                styleAttribute = com.google.android.material.R.style.Widget_MaterialComponents_Chip_Entry;
            }
            return createFromAttributes(context, attributeSetAsAttributeSet, com.google.android.material.R.attr.chipStandaloneStyle, styleAttribute);
        } catch (IOException e2) {
            e = e2;
            Resources.NotFoundException notFoundException = new Resources.NotFoundException("Can't load chip resource ID #0x" + Integer.toHexString(i2));
            notFoundException.initCause(e);
            throw notFoundException;
        } catch (XmlPullParserException e3) {
            e = e3;
            Resources.NotFoundException notFoundException2 = new Resources.NotFoundException("Can't load chip resource ID #0x" + Integer.toHexString(i2));
            notFoundException2.initCause(e);
            throw notFoundException2;
        }
    }

    private void drawCheckedIcon(@NonNull Canvas canvas, Rect rect) {
        if (showsCheckedIcon()) {
            calculateChipIconBounds(rect, this.rectF);
            RectF rectF = this.rectF;
            float f2 = rectF.left;
            float f3 = rectF.top;
            canvas.translate(f2, f3);
            this.checkedIcon.setBounds(0, 0, (int) this.rectF.width(), (int) this.rectF.height());
            this.checkedIcon.draw(canvas);
            canvas.translate(-f2, -f3);
        }
    }

    private void drawChipBackground(@NonNull Canvas canvas, Rect rect) {
        this.chipPaint.setColor(this.currentChipBackgroundColor);
        this.chipPaint.setStyle(Paint.Style.FILL);
        this.chipPaint.setColorFilter(getTintColorFilter());
        this.rectF.set(rect);
        RectF rectF = this.rectF;
        float f2 = this.chipCornerRadius;
        canvas.drawRoundRect(rectF, f2, f2, this.chipPaint);
    }

    private void drawChipIcon(@NonNull Canvas canvas, Rect rect) {
        if (showsChipIcon()) {
            calculateChipIconBounds(rect, this.rectF);
            RectF rectF = this.rectF;
            float f2 = rectF.left;
            float f3 = rectF.top;
            canvas.translate(f2, f3);
            this.chipIcon.setBounds(0, 0, (int) this.rectF.width(), (int) this.rectF.height());
            this.chipIcon.draw(canvas);
            canvas.translate(-f2, -f3);
        }
    }

    private void drawChipStroke(@NonNull Canvas canvas, Rect rect) {
        if (this.chipStrokeWidth > 0.0f) {
            this.chipPaint.setColor(this.currentChipStrokeColor);
            this.chipPaint.setStyle(Paint.Style.STROKE);
            this.chipPaint.setColorFilter(getTintColorFilter());
            RectF rectF = this.rectF;
            float f2 = rect.left;
            float f3 = this.chipStrokeWidth;
            rectF.set(f2 + (f3 / 2.0f), rect.top + (f3 / 2.0f), rect.right - (f3 / 2.0f), rect.bottom - (f3 / 2.0f));
            float f4 = this.chipCornerRadius - (this.chipStrokeWidth / 2.0f);
            canvas.drawRoundRect(this.rectF, f4, f4, this.chipPaint);
        }
    }

    private void drawCloseIcon(@NonNull Canvas canvas, Rect rect) {
        if (showsCloseIcon()) {
            calculateCloseIconBounds(rect, this.rectF);
            RectF rectF = this.rectF;
            float f2 = rectF.left;
            float f3 = rectF.top;
            canvas.translate(f2, f3);
            this.closeIcon.setBounds(0, 0, (int) this.rectF.width(), (int) this.rectF.height());
            this.closeIcon.draw(canvas);
            canvas.translate(-f2, -f3);
        }
    }

    private void drawCompatRipple(@NonNull Canvas canvas, Rect rect) {
        this.chipPaint.setColor(this.currentCompatRippleColor);
        this.chipPaint.setStyle(Paint.Style.FILL);
        this.rectF.set(rect);
        RectF rectF = this.rectF;
        float f2 = this.chipCornerRadius;
        canvas.drawRoundRect(rectF, f2, f2, this.chipPaint);
    }

    private void drawDebug(@NonNull Canvas canvas, Rect rect) {
        Paint paint = this.debugPaint;
        if (paint != null) {
            paint.setColor(ColorUtils.setAlphaComponent(ViewCompat.MEASURED_STATE_MASK, 127));
            canvas.drawRect(rect, this.debugPaint);
            if (showsChipIcon() || showsCheckedIcon()) {
                calculateChipIconBounds(rect, this.rectF);
                canvas.drawRect(this.rectF, this.debugPaint);
            }
            if (this.unicodeWrappedText != null) {
                canvas.drawLine(rect.left, rect.exactCenterY(), rect.right, rect.exactCenterY(), this.debugPaint);
            }
            if (showsCloseIcon()) {
                calculateCloseIconBounds(rect, this.rectF);
                canvas.drawRect(this.rectF, this.debugPaint);
            }
            this.debugPaint.setColor(ColorUtils.setAlphaComponent(SupportMenu.CATEGORY_MASK, 127));
            calculateChipTouchBounds(rect, this.rectF);
            canvas.drawRect(this.rectF, this.debugPaint);
            this.debugPaint.setColor(ColorUtils.setAlphaComponent(-16711936, 127));
            calculateCloseIconTouchBounds(rect, this.rectF);
            canvas.drawRect(this.rectF, this.debugPaint);
        }
    }

    private void drawText(@NonNull Canvas canvas, Rect rect) {
        if (this.unicodeWrappedText != null) {
            Paint.Align alignC = c(rect, this.pointF);
            calculateTextBounds(rect, this.rectF);
            if (this.textAppearance != null) {
                this.textPaint.drawableState = getState();
                this.textAppearance.updateDrawState(this.context, this.textPaint, this.fontCallback);
            }
            this.textPaint.setTextAlign(alignC);
            int iSave = 0;
            boolean z2 = Math.round(getTextWidth()) > Math.round(this.rectF.width());
            if (z2) {
                iSave = canvas.save();
                canvas.clipRect(this.rectF);
            }
            CharSequence charSequenceEllipsize = this.unicodeWrappedText;
            if (z2 && this.truncateAt != null) {
                charSequenceEllipsize = TextUtils.ellipsize(charSequenceEllipsize, this.textPaint, this.rectF.width(), this.truncateAt);
            }
            CharSequence charSequence = charSequenceEllipsize;
            int length = charSequence.length();
            PointF pointF = this.pointF;
            canvas.drawText(charSequence, 0, length, pointF.x, pointF.y, this.textPaint);
            if (z2) {
                canvas.restoreToCount(iSave);
            }
        }
    }

    private float getTextWidth() {
        if (!this.textWidthDirty) {
            return this.textWidth;
        }
        float fCalculateTextWidth = calculateTextWidth(this.unicodeWrappedText);
        this.textWidth = fCalculateTextWidth;
        this.textWidthDirty = false;
        return fCalculateTextWidth;
    }

    @Nullable
    private ColorFilter getTintColorFilter() {
        ColorFilter colorFilter = this.colorFilter;
        return colorFilter != null ? colorFilter : this.tintFilter;
    }

    private static boolean hasState(@Nullable int[] iArr, @AttrRes int i2) {
        if (iArr == null) {
            return false;
        }
        for (int i3 : iArr) {
            if (i3 == i2) {
                return true;
            }
        }
        return false;
    }

    private void loadFromAttributes(AttributeSet attributeSet, @AttrRes int i2, @StyleRes int i3) {
        TypedArray typedArrayObtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(this.context, attributeSet, com.google.android.material.R.styleable.Chip, i2, i3, new int[0]);
        setChipBackgroundColor(MaterialResources.getColorStateList(this.context, typedArrayObtainStyledAttributes, com.google.android.material.R.styleable.Chip_chipBackgroundColor));
        setChipMinHeight(typedArrayObtainStyledAttributes.getDimension(com.google.android.material.R.styleable.Chip_chipMinHeight, 0.0f));
        setChipCornerRadius(typedArrayObtainStyledAttributes.getDimension(com.google.android.material.R.styleable.Chip_chipCornerRadius, 0.0f));
        setChipStrokeColor(MaterialResources.getColorStateList(this.context, typedArrayObtainStyledAttributes, com.google.android.material.R.styleable.Chip_chipStrokeColor));
        setChipStrokeWidth(typedArrayObtainStyledAttributes.getDimension(com.google.android.material.R.styleable.Chip_chipStrokeWidth, 0.0f));
        setRippleColor(MaterialResources.getColorStateList(this.context, typedArrayObtainStyledAttributes, com.google.android.material.R.styleable.Chip_rippleColor));
        setText(typedArrayObtainStyledAttributes.getText(com.google.android.material.R.styleable.Chip_android_text));
        setTextAppearance(MaterialResources.getTextAppearance(this.context, typedArrayObtainStyledAttributes, com.google.android.material.R.styleable.Chip_android_textAppearance));
        int i4 = typedArrayObtainStyledAttributes.getInt(com.google.android.material.R.styleable.Chip_android_ellipsize, 0);
        if (i4 == 1) {
            setEllipsize(TextUtils.TruncateAt.START);
        } else if (i4 == 2) {
            setEllipsize(TextUtils.TruncateAt.MIDDLE);
        } else if (i4 == 3) {
            setEllipsize(TextUtils.TruncateAt.END);
        }
        setChipIconVisible(typedArrayObtainStyledAttributes.getBoolean(com.google.android.material.R.styleable.Chip_chipIconVisible, false));
        if (attributeSet != null && attributeSet.getAttributeValue(NAMESPACE_APP, "chipIconEnabled") != null && attributeSet.getAttributeValue(NAMESPACE_APP, "chipIconVisible") == null) {
            setChipIconVisible(typedArrayObtainStyledAttributes.getBoolean(com.google.android.material.R.styleable.Chip_chipIconEnabled, false));
        }
        setChipIcon(MaterialResources.getDrawable(this.context, typedArrayObtainStyledAttributes, com.google.android.material.R.styleable.Chip_chipIcon));
        setChipIconTint(MaterialResources.getColorStateList(this.context, typedArrayObtainStyledAttributes, com.google.android.material.R.styleable.Chip_chipIconTint));
        setChipIconSize(typedArrayObtainStyledAttributes.getDimension(com.google.android.material.R.styleable.Chip_chipIconSize, 0.0f));
        setCloseIconVisible(typedArrayObtainStyledAttributes.getBoolean(com.google.android.material.R.styleable.Chip_closeIconVisible, false));
        if (attributeSet != null && attributeSet.getAttributeValue(NAMESPACE_APP, "closeIconEnabled") != null && attributeSet.getAttributeValue(NAMESPACE_APP, "closeIconVisible") == null) {
            setCloseIconVisible(typedArrayObtainStyledAttributes.getBoolean(com.google.android.material.R.styleable.Chip_closeIconEnabled, false));
        }
        setCloseIcon(MaterialResources.getDrawable(this.context, typedArrayObtainStyledAttributes, com.google.android.material.R.styleable.Chip_closeIcon));
        setCloseIconTint(MaterialResources.getColorStateList(this.context, typedArrayObtainStyledAttributes, com.google.android.material.R.styleable.Chip_closeIconTint));
        setCloseIconSize(typedArrayObtainStyledAttributes.getDimension(com.google.android.material.R.styleable.Chip_closeIconSize, 0.0f));
        setCheckable(typedArrayObtainStyledAttributes.getBoolean(com.google.android.material.R.styleable.Chip_android_checkable, false));
        setCheckedIconVisible(typedArrayObtainStyledAttributes.getBoolean(com.google.android.material.R.styleable.Chip_checkedIconVisible, false));
        if (attributeSet != null && attributeSet.getAttributeValue(NAMESPACE_APP, "checkedIconEnabled") != null && attributeSet.getAttributeValue(NAMESPACE_APP, "checkedIconVisible") == null) {
            setCheckedIconVisible(typedArrayObtainStyledAttributes.getBoolean(com.google.android.material.R.styleable.Chip_checkedIconEnabled, false));
        }
        setCheckedIcon(MaterialResources.getDrawable(this.context, typedArrayObtainStyledAttributes, com.google.android.material.R.styleable.Chip_checkedIcon));
        setShowMotionSpec(MotionSpec.createFromAttribute(this.context, typedArrayObtainStyledAttributes, com.google.android.material.R.styleable.Chip_showMotionSpec));
        setHideMotionSpec(MotionSpec.createFromAttribute(this.context, typedArrayObtainStyledAttributes, com.google.android.material.R.styleable.Chip_hideMotionSpec));
        setChipStartPadding(typedArrayObtainStyledAttributes.getDimension(com.google.android.material.R.styleable.Chip_chipStartPadding, 0.0f));
        setIconStartPadding(typedArrayObtainStyledAttributes.getDimension(com.google.android.material.R.styleable.Chip_iconStartPadding, 0.0f));
        setIconEndPadding(typedArrayObtainStyledAttributes.getDimension(com.google.android.material.R.styleable.Chip_iconEndPadding, 0.0f));
        setTextStartPadding(typedArrayObtainStyledAttributes.getDimension(com.google.android.material.R.styleable.Chip_textStartPadding, 0.0f));
        setTextEndPadding(typedArrayObtainStyledAttributes.getDimension(com.google.android.material.R.styleable.Chip_textEndPadding, 0.0f));
        setCloseIconStartPadding(typedArrayObtainStyledAttributes.getDimension(com.google.android.material.R.styleable.Chip_closeIconStartPadding, 0.0f));
        setCloseIconEndPadding(typedArrayObtainStyledAttributes.getDimension(com.google.android.material.R.styleable.Chip_closeIconEndPadding, 0.0f));
        setChipEndPadding(typedArrayObtainStyledAttributes.getDimension(com.google.android.material.R.styleable.Chip_chipEndPadding, 0.0f));
        setMaxWidth(typedArrayObtainStyledAttributes.getDimensionPixelSize(com.google.android.material.R.styleable.Chip_android_maxWidth, Integer.MAX_VALUE));
        typedArrayObtainStyledAttributes.recycle();
    }

    private boolean showsCheckedIcon() {
        return this.checkedIconVisible && this.checkedIcon != null && this.currentChecked;
    }

    private boolean showsChipIcon() {
        return this.chipIconVisible && this.chipIcon != null;
    }

    private boolean showsCloseIcon() {
        return this.closeIconVisible && this.closeIcon != null;
    }

    private void unapplyChildDrawable(@Nullable Drawable drawable) {
        if (drawable != null) {
            drawable.setCallback(null);
        }
    }

    private void updateCompatRippleColor() {
        this.compatRippleColor = this.useCompatRipple ? RippleUtils.convertToRippleDrawableColor(this.rippleColor) : null;
    }

    float b() {
        if (showsChipIcon() || showsCheckedIcon()) {
            return this.iconStartPadding + this.chipIconSize + this.iconEndPadding;
        }
        return 0.0f;
    }

    Paint.Align c(Rect rect, PointF pointF) {
        pointF.set(0.0f, 0.0f);
        Paint.Align align = Paint.Align.LEFT;
        if (this.unicodeWrappedText != null) {
            float fB = this.chipStartPadding + b() + this.textStartPadding;
            if (DrawableCompat.getLayoutDirection(this) == 0) {
                pointF.x = rect.left + fB;
            } else {
                pointF.x = rect.right - fB;
                align = Paint.Align.RIGHT;
            }
            pointF.y = rect.centerY() - calculateTextCenterFromBaseline();
        }
        return align;
    }

    protected void d() {
        Delegate delegate = this.delegate.get();
        if (delegate != null) {
            delegate.onChipDrawableSizeChange();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(@NonNull Canvas canvas) {
        Rect bounds = getBounds();
        if (bounds.isEmpty() || getAlpha() == 0) {
            return;
        }
        int i2 = this.alpha;
        int iSaveLayerAlpha = i2 < 255 ? CanvasCompat.saveLayerAlpha(canvas, bounds.left, bounds.top, bounds.right, bounds.bottom, i2) : 0;
        drawChipBackground(canvas, bounds);
        drawChipStroke(canvas, bounds);
        drawCompatRipple(canvas, bounds);
        drawChipIcon(canvas, bounds);
        drawCheckedIcon(canvas, bounds);
        if (this.shouldDrawText) {
            drawText(canvas, bounds);
        }
        drawCloseIcon(canvas, bounds);
        drawDebug(canvas, bounds);
        if (this.alpha < 255) {
            canvas.restoreToCount(iSaveLayerAlpha);
        }
    }

    void e(boolean z2) {
        this.shouldDrawText = z2;
    }

    boolean f() {
        return this.shouldDrawText;
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.alpha;
    }

    @Nullable
    public Drawable getCheckedIcon() {
        return this.checkedIcon;
    }

    @Nullable
    public ColorStateList getChipBackgroundColor() {
        return this.chipBackgroundColor;
    }

    public float getChipCornerRadius() {
        return this.chipCornerRadius;
    }

    public float getChipEndPadding() {
        return this.chipEndPadding;
    }

    @Nullable
    public Drawable getChipIcon() {
        Drawable drawable = this.chipIcon;
        if (drawable != null) {
            return DrawableCompat.unwrap(drawable);
        }
        return null;
    }

    public float getChipIconSize() {
        return this.chipIconSize;
    }

    @Nullable
    public ColorStateList getChipIconTint() {
        return this.chipIconTint;
    }

    public float getChipMinHeight() {
        return this.chipMinHeight;
    }

    public float getChipStartPadding() {
        return this.chipStartPadding;
    }

    @Nullable
    public ColorStateList getChipStrokeColor() {
        return this.chipStrokeColor;
    }

    public float getChipStrokeWidth() {
        return this.chipStrokeWidth;
    }

    public void getChipTouchBounds(RectF rectF) {
        calculateChipTouchBounds(getBounds(), rectF);
    }

    @Nullable
    public Drawable getCloseIcon() {
        Drawable drawable = this.closeIcon;
        if (drawable != null) {
            return DrawableCompat.unwrap(drawable);
        }
        return null;
    }

    @Nullable
    public CharSequence getCloseIconContentDescription() {
        return this.closeIconContentDescription;
    }

    public float getCloseIconEndPadding() {
        return this.closeIconEndPadding;
    }

    public float getCloseIconSize() {
        return this.closeIconSize;
    }

    public float getCloseIconStartPadding() {
        return this.closeIconStartPadding;
    }

    @NonNull
    public int[] getCloseIconState() {
        return this.closeIconStateSet;
    }

    @Nullable
    public ColorStateList getCloseIconTint() {
        return this.closeIconTint;
    }

    public void getCloseIconTouchBounds(RectF rectF) {
        calculateCloseIconTouchBounds(getBounds(), rectF);
    }

    @Override // android.graphics.drawable.Drawable
    @Nullable
    public ColorFilter getColorFilter() {
        return this.colorFilter;
    }

    public TextUtils.TruncateAt getEllipsize() {
        return this.truncateAt;
    }

    @Nullable
    public MotionSpec getHideMotionSpec() {
        return this.hideMotionSpec;
    }

    public float getIconEndPadding() {
        return this.iconEndPadding;
    }

    public float getIconStartPadding() {
        return this.iconStartPadding;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return (int) this.chipMinHeight;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return Math.min(Math.round(this.chipStartPadding + b() + this.textStartPadding + getTextWidth() + this.textEndPadding + calculateCloseIconWidth() + this.chipEndPadding), this.maxWidth);
    }

    @Px
    public int getMaxWidth() {
        return this.maxWidth;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    @Override // android.graphics.drawable.Drawable
    @TargetApi(21)
    public void getOutline(@NonNull Outline outline) {
        Rect bounds = getBounds();
        if (bounds.isEmpty()) {
            outline.setRoundRect(0, 0, getIntrinsicWidth(), getIntrinsicHeight(), this.chipCornerRadius);
        } else {
            outline.setRoundRect(bounds, this.chipCornerRadius);
        }
        outline.setAlpha(getAlpha() / 255.0f);
    }

    @Nullable
    public ColorStateList getRippleColor() {
        return this.rippleColor;
    }

    @Nullable
    public MotionSpec getShowMotionSpec() {
        return this.showMotionSpec;
    }

    @NonNull
    public CharSequence getText() {
        return this.rawText;
    }

    @Nullable
    public TextAppearance getTextAppearance() {
        return this.textAppearance;
    }

    public float getTextEndPadding() {
        return this.textEndPadding;
    }

    public float getTextStartPadding() {
        return this.textStartPadding;
    }

    public boolean getUseCompatRipple() {
        return this.useCompatRipple;
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void invalidateDrawable(@NonNull Drawable drawable) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.invalidateDrawable(this);
        }
    }

    public boolean isCheckable() {
        return this.checkable;
    }

    @Deprecated
    public boolean isCheckedIconEnabled() {
        return isCheckedIconVisible();
    }

    public boolean isCheckedIconVisible() {
        return this.checkedIconVisible;
    }

    @Deprecated
    public boolean isChipIconEnabled() {
        return isChipIconVisible();
    }

    public boolean isChipIconVisible() {
        return this.chipIconVisible;
    }

    @Deprecated
    public boolean isCloseIconEnabled() {
        return isCloseIconVisible();
    }

    public boolean isCloseIconStateful() {
        return isStateful(this.closeIcon);
    }

    public boolean isCloseIconVisible() {
        return this.closeIconVisible;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        return isStateful(this.chipBackgroundColor) || isStateful(this.chipStrokeColor) || (this.useCompatRipple && isStateful(this.compatRippleColor)) || isStateful(this.textAppearance) || canShowCheckedIcon() || isStateful(this.chipIcon) || isStateful(this.checkedIcon) || isStateful(this.tint);
    }

    @Override // android.graphics.drawable.Drawable
    @TargetApi(23)
    public boolean onLayoutDirectionChanged(int i2) {
        boolean zOnLayoutDirectionChanged = super.onLayoutDirectionChanged(i2);
        if (showsChipIcon()) {
            zOnLayoutDirectionChanged |= this.chipIcon.setLayoutDirection(i2);
        }
        if (showsCheckedIcon()) {
            zOnLayoutDirectionChanged |= this.checkedIcon.setLayoutDirection(i2);
        }
        if (showsCloseIcon()) {
            zOnLayoutDirectionChanged |= this.closeIcon.setLayoutDirection(i2);
        }
        if (!zOnLayoutDirectionChanged) {
            return true;
        }
        invalidateSelf();
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    protected boolean onLevelChange(int i2) {
        boolean zOnLevelChange = super.onLevelChange(i2);
        if (showsChipIcon()) {
            zOnLevelChange |= this.chipIcon.setLevel(i2);
        }
        if (showsCheckedIcon()) {
            zOnLevelChange |= this.checkedIcon.setLevel(i2);
        }
        if (showsCloseIcon()) {
            zOnLevelChange |= this.closeIcon.setLevel(i2);
        }
        if (zOnLevelChange) {
            invalidateSelf();
        }
        return zOnLevelChange;
    }

    @Override // android.graphics.drawable.Drawable
    protected boolean onStateChange(int[] iArr) {
        return onStateChange(iArr, getCloseIconState());
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void scheduleDrawable(@NonNull Drawable drawable, @NonNull Runnable runnable, long j2) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.scheduleDrawable(this, runnable, j2);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i2) {
        if (this.alpha != i2) {
            this.alpha = i2;
            invalidateSelf();
        }
    }

    public void setCheckable(boolean z2) {
        if (this.checkable != z2) {
            this.checkable = z2;
            float fB = b();
            if (!z2 && this.currentChecked) {
                this.currentChecked = false;
            }
            float fB2 = b();
            invalidateSelf();
            if (fB != fB2) {
                d();
            }
        }
    }

    public void setCheckableResource(@BoolRes int i2) {
        setCheckable(this.context.getResources().getBoolean(i2));
    }

    public void setCheckedIcon(@Nullable Drawable drawable) {
        if (this.checkedIcon != drawable) {
            float fB = b();
            this.checkedIcon = drawable;
            float fB2 = b();
            unapplyChildDrawable(this.checkedIcon);
            applyChildDrawable(this.checkedIcon);
            invalidateSelf();
            if (fB != fB2) {
                d();
            }
        }
    }

    @Deprecated
    public void setCheckedIconEnabled(boolean z2) {
        setCheckedIconVisible(z2);
    }

    @Deprecated
    public void setCheckedIconEnabledResource(@BoolRes int i2) {
        setCheckedIconVisible(this.context.getResources().getBoolean(i2));
    }

    public void setCheckedIconResource(@DrawableRes int i2) {
        setCheckedIcon(AppCompatResources.getDrawable(this.context, i2));
    }

    public void setCheckedIconVisible(@BoolRes int i2) {
        setCheckedIconVisible(this.context.getResources().getBoolean(i2));
    }

    public void setChipBackgroundColor(@Nullable ColorStateList colorStateList) {
        if (this.chipBackgroundColor != colorStateList) {
            this.chipBackgroundColor = colorStateList;
            onStateChange(getState());
        }
    }

    public void setChipBackgroundColorResource(@ColorRes int i2) {
        setChipBackgroundColor(AppCompatResources.getColorStateList(this.context, i2));
    }

    public void setChipCornerRadius(float f2) {
        if (this.chipCornerRadius != f2) {
            this.chipCornerRadius = f2;
            invalidateSelf();
        }
    }

    public void setChipCornerRadiusResource(@DimenRes int i2) {
        setChipCornerRadius(this.context.getResources().getDimension(i2));
    }

    public void setChipEndPadding(float f2) {
        if (this.chipEndPadding != f2) {
            this.chipEndPadding = f2;
            invalidateSelf();
            d();
        }
    }

    public void setChipEndPaddingResource(@DimenRes int i2) {
        setChipEndPadding(this.context.getResources().getDimension(i2));
    }

    public void setChipIcon(@Nullable Drawable drawable) {
        Drawable chipIcon = getChipIcon();
        if (chipIcon != drawable) {
            float fB = b();
            this.chipIcon = drawable != null ? DrawableCompat.wrap(drawable).mutate() : null;
            float fB2 = b();
            unapplyChildDrawable(chipIcon);
            if (showsChipIcon()) {
                applyChildDrawable(this.chipIcon);
            }
            invalidateSelf();
            if (fB != fB2) {
                d();
            }
        }
    }

    @Deprecated
    public void setChipIconEnabled(boolean z2) {
        setChipIconVisible(z2);
    }

    @Deprecated
    public void setChipIconEnabledResource(@BoolRes int i2) {
        setChipIconVisible(i2);
    }

    public void setChipIconResource(@DrawableRes int i2) {
        setChipIcon(AppCompatResources.getDrawable(this.context, i2));
    }

    public void setChipIconSize(float f2) {
        if (this.chipIconSize != f2) {
            float fB = b();
            this.chipIconSize = f2;
            float fB2 = b();
            invalidateSelf();
            if (fB != fB2) {
                d();
            }
        }
    }

    public void setChipIconSizeResource(@DimenRes int i2) {
        setChipIconSize(this.context.getResources().getDimension(i2));
    }

    public void setChipIconTint(@Nullable ColorStateList colorStateList) {
        if (this.chipIconTint != colorStateList) {
            this.chipIconTint = colorStateList;
            if (showsChipIcon()) {
                DrawableCompat.setTintList(this.chipIcon, colorStateList);
            }
            onStateChange(getState());
        }
    }

    public void setChipIconTintResource(@ColorRes int i2) {
        setChipIconTint(AppCompatResources.getColorStateList(this.context, i2));
    }

    public void setChipIconVisible(@BoolRes int i2) {
        setChipIconVisible(this.context.getResources().getBoolean(i2));
    }

    public void setChipMinHeight(float f2) {
        if (this.chipMinHeight != f2) {
            this.chipMinHeight = f2;
            invalidateSelf();
            d();
        }
    }

    public void setChipMinHeightResource(@DimenRes int i2) {
        setChipMinHeight(this.context.getResources().getDimension(i2));
    }

    public void setChipStartPadding(float f2) {
        if (this.chipStartPadding != f2) {
            this.chipStartPadding = f2;
            invalidateSelf();
            d();
        }
    }

    public void setChipStartPaddingResource(@DimenRes int i2) {
        setChipStartPadding(this.context.getResources().getDimension(i2));
    }

    public void setChipStrokeColor(@Nullable ColorStateList colorStateList) {
        if (this.chipStrokeColor != colorStateList) {
            this.chipStrokeColor = colorStateList;
            onStateChange(getState());
        }
    }

    public void setChipStrokeColorResource(@ColorRes int i2) {
        setChipStrokeColor(AppCompatResources.getColorStateList(this.context, i2));
    }

    public void setChipStrokeWidth(float f2) {
        if (this.chipStrokeWidth != f2) {
            this.chipStrokeWidth = f2;
            this.chipPaint.setStrokeWidth(f2);
            invalidateSelf();
        }
    }

    public void setChipStrokeWidthResource(@DimenRes int i2) {
        setChipStrokeWidth(this.context.getResources().getDimension(i2));
    }

    public void setCloseIcon(@Nullable Drawable drawable) {
        Drawable closeIcon = getCloseIcon();
        if (closeIcon != drawable) {
            float fCalculateCloseIconWidth = calculateCloseIconWidth();
            this.closeIcon = drawable != null ? DrawableCompat.wrap(drawable).mutate() : null;
            float fCalculateCloseIconWidth2 = calculateCloseIconWidth();
            unapplyChildDrawable(closeIcon);
            if (showsCloseIcon()) {
                applyChildDrawable(this.closeIcon);
            }
            invalidateSelf();
            if (fCalculateCloseIconWidth != fCalculateCloseIconWidth2) {
                d();
            }
        }
    }

    public void setCloseIconContentDescription(@Nullable CharSequence charSequence) {
        if (this.closeIconContentDescription != charSequence) {
            this.closeIconContentDescription = BidiFormatter.getInstance().unicodeWrap(charSequence);
            invalidateSelf();
        }
    }

    @Deprecated
    public void setCloseIconEnabled(boolean z2) {
        setCloseIconVisible(z2);
    }

    @Deprecated
    public void setCloseIconEnabledResource(@BoolRes int i2) {
        setCloseIconVisible(i2);
    }

    public void setCloseIconEndPadding(float f2) {
        if (this.closeIconEndPadding != f2) {
            this.closeIconEndPadding = f2;
            invalidateSelf();
            if (showsCloseIcon()) {
                d();
            }
        }
    }

    public void setCloseIconEndPaddingResource(@DimenRes int i2) {
        setCloseIconEndPadding(this.context.getResources().getDimension(i2));
    }

    public void setCloseIconResource(@DrawableRes int i2) {
        setCloseIcon(AppCompatResources.getDrawable(this.context, i2));
    }

    public void setCloseIconSize(float f2) {
        if (this.closeIconSize != f2) {
            this.closeIconSize = f2;
            invalidateSelf();
            if (showsCloseIcon()) {
                d();
            }
        }
    }

    public void setCloseIconSizeResource(@DimenRes int i2) {
        setCloseIconSize(this.context.getResources().getDimension(i2));
    }

    public void setCloseIconStartPadding(float f2) {
        if (this.closeIconStartPadding != f2) {
            this.closeIconStartPadding = f2;
            invalidateSelf();
            if (showsCloseIcon()) {
                d();
            }
        }
    }

    public void setCloseIconStartPaddingResource(@DimenRes int i2) {
        setCloseIconStartPadding(this.context.getResources().getDimension(i2));
    }

    public boolean setCloseIconState(@NonNull int[] iArr) {
        if (Arrays.equals(this.closeIconStateSet, iArr)) {
            return false;
        }
        this.closeIconStateSet = iArr;
        if (showsCloseIcon()) {
            return onStateChange(getState(), iArr);
        }
        return false;
    }

    public void setCloseIconTint(@Nullable ColorStateList colorStateList) {
        if (this.closeIconTint != colorStateList) {
            this.closeIconTint = colorStateList;
            if (showsCloseIcon()) {
                DrawableCompat.setTintList(this.closeIcon, colorStateList);
            }
            onStateChange(getState());
        }
    }

    public void setCloseIconTintResource(@ColorRes int i2) {
        setCloseIconTint(AppCompatResources.getColorStateList(this.context, i2));
    }

    public void setCloseIconVisible(@BoolRes int i2) {
        setCloseIconVisible(this.context.getResources().getBoolean(i2));
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        if (this.colorFilter != colorFilter) {
            this.colorFilter = colorFilter;
            invalidateSelf();
        }
    }

    public void setDelegate(@Nullable Delegate delegate) {
        this.delegate = new WeakReference<>(delegate);
    }

    public void setEllipsize(@Nullable TextUtils.TruncateAt truncateAt) {
        this.truncateAt = truncateAt;
    }

    public void setHideMotionSpec(@Nullable MotionSpec motionSpec) {
        this.hideMotionSpec = motionSpec;
    }

    public void setHideMotionSpecResource(@AnimatorRes int i2) {
        setHideMotionSpec(MotionSpec.createFromResource(this.context, i2));
    }

    public void setIconEndPadding(float f2) {
        if (this.iconEndPadding != f2) {
            float fB = b();
            this.iconEndPadding = f2;
            float fB2 = b();
            invalidateSelf();
            if (fB != fB2) {
                d();
            }
        }
    }

    public void setIconEndPaddingResource(@DimenRes int i2) {
        setIconEndPadding(this.context.getResources().getDimension(i2));
    }

    public void setIconStartPadding(float f2) {
        if (this.iconStartPadding != f2) {
            float fB = b();
            this.iconStartPadding = f2;
            float fB2 = b();
            invalidateSelf();
            if (fB != fB2) {
                d();
            }
        }
    }

    public void setIconStartPaddingResource(@DimenRes int i2) {
        setIconStartPadding(this.context.getResources().getDimension(i2));
    }

    public void setMaxWidth(@Px int i2) {
        this.maxWidth = i2;
    }

    public void setRippleColor(@Nullable ColorStateList colorStateList) {
        if (this.rippleColor != colorStateList) {
            this.rippleColor = colorStateList;
            updateCompatRippleColor();
            onStateChange(getState());
        }
    }

    public void setRippleColorResource(@ColorRes int i2) {
        setRippleColor(AppCompatResources.getColorStateList(this.context, i2));
    }

    public void setShowMotionSpec(@Nullable MotionSpec motionSpec) {
        this.showMotionSpec = motionSpec;
    }

    public void setShowMotionSpecResource(@AnimatorRes int i2) {
        setShowMotionSpec(MotionSpec.createFromResource(this.context, i2));
    }

    public void setText(@Nullable CharSequence charSequence) {
        if (charSequence == null) {
            charSequence = "";
        }
        if (this.rawText != charSequence) {
            this.rawText = charSequence;
            this.unicodeWrappedText = BidiFormatter.getInstance().unicodeWrap(charSequence);
            this.textWidthDirty = true;
            invalidateSelf();
            d();
        }
    }

    public void setTextAppearance(@Nullable TextAppearance textAppearance) {
        if (this.textAppearance != textAppearance) {
            this.textAppearance = textAppearance;
            if (textAppearance != null) {
                textAppearance.updateMeasureState(this.context, this.textPaint, this.fontCallback);
                this.textWidthDirty = true;
            }
            onStateChange(getState());
            d();
        }
    }

    public void setTextAppearanceResource(@StyleRes int i2) {
        setTextAppearance(new TextAppearance(this.context, i2));
    }

    public void setTextEndPadding(float f2) {
        if (this.textEndPadding != f2) {
            this.textEndPadding = f2;
            invalidateSelf();
            d();
        }
    }

    public void setTextEndPaddingResource(@DimenRes int i2) {
        setTextEndPadding(this.context.getResources().getDimension(i2));
    }

    public void setTextResource(@StringRes int i2) {
        setText(this.context.getResources().getString(i2));
    }

    public void setTextStartPadding(float f2) {
        if (this.textStartPadding != f2) {
            this.textStartPadding = f2;
            invalidateSelf();
            d();
        }
    }

    public void setTextStartPaddingResource(@DimenRes int i2) {
        setTextStartPadding(this.context.getResources().getDimension(i2));
    }

    @Override // android.graphics.drawable.Drawable, androidx.core.graphics.drawable.TintAwareDrawable
    public void setTintList(@Nullable ColorStateList colorStateList) {
        if (this.tint != colorStateList) {
            this.tint = colorStateList;
            onStateChange(getState());
        }
    }

    @Override // android.graphics.drawable.Drawable, androidx.core.graphics.drawable.TintAwareDrawable
    public void setTintMode(@NonNull PorterDuff.Mode mode) {
        if (this.tintMode != mode) {
            this.tintMode = mode;
            this.tintFilter = DrawableUtils.updateTintFilter(this, this.tint, mode);
            invalidateSelf();
        }
    }

    public void setUseCompatRipple(boolean z2) {
        if (this.useCompatRipple != z2) {
            this.useCompatRipple = z2;
            updateCompatRippleColor();
            onStateChange(getState());
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean setVisible(boolean z2, boolean z3) {
        boolean visible = super.setVisible(z2, z3);
        if (showsChipIcon()) {
            visible |= this.chipIcon.setVisible(z2, z3);
        }
        if (showsCheckedIcon()) {
            visible |= this.checkedIcon.setVisible(z2, z3);
        }
        if (showsCloseIcon()) {
            visible |= this.closeIcon.setVisible(z2, z3);
        }
        if (visible) {
            invalidateSelf();
        }
        return visible;
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void unscheduleDrawable(@NonNull Drawable drawable, @NonNull Runnable runnable) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.unscheduleDrawable(this, runnable);
        }
    }

    private boolean onStateChange(int[] iArr, int[] iArr2) {
        boolean z2;
        ColorStateList colorStateList;
        boolean zOnStateChange = super.onStateChange(iArr);
        ColorStateList colorStateList2 = this.chipBackgroundColor;
        int colorForState = colorStateList2 != null ? colorStateList2.getColorForState(iArr, this.currentChipBackgroundColor) : 0;
        boolean state = true;
        if (this.currentChipBackgroundColor != colorForState) {
            this.currentChipBackgroundColor = colorForState;
            zOnStateChange = true;
        }
        ColorStateList colorStateList3 = this.chipStrokeColor;
        int colorForState2 = colorStateList3 != null ? colorStateList3.getColorForState(iArr, this.currentChipStrokeColor) : 0;
        if (this.currentChipStrokeColor != colorForState2) {
            this.currentChipStrokeColor = colorForState2;
            zOnStateChange = true;
        }
        ColorStateList colorStateList4 = this.compatRippleColor;
        int colorForState3 = colorStateList4 != null ? colorStateList4.getColorForState(iArr, this.currentCompatRippleColor) : 0;
        if (this.currentCompatRippleColor != colorForState3) {
            this.currentCompatRippleColor = colorForState3;
            if (this.useCompatRipple) {
                zOnStateChange = true;
            }
        }
        TextAppearance textAppearance = this.textAppearance;
        int colorForState4 = (textAppearance == null || (colorStateList = textAppearance.textColor) == null) ? 0 : colorStateList.getColorForState(iArr, this.currentTextColor);
        if (this.currentTextColor != colorForState4) {
            this.currentTextColor = colorForState4;
            zOnStateChange = true;
        }
        boolean z3 = hasState(getState(), R.attr.state_checked) && this.checkable;
        if (this.currentChecked == z3 || this.checkedIcon == null) {
            z2 = false;
        } else {
            float fB = b();
            this.currentChecked = z3;
            if (fB != b()) {
                zOnStateChange = true;
                z2 = true;
            } else {
                z2 = false;
                zOnStateChange = true;
            }
        }
        ColorStateList colorStateList5 = this.tint;
        int colorForState5 = colorStateList5 != null ? colorStateList5.getColorForState(iArr, this.currentTint) : 0;
        if (this.currentTint != colorForState5) {
            this.currentTint = colorForState5;
            this.tintFilter = DrawableUtils.updateTintFilter(this, this.tint, this.tintMode);
        } else {
            state = zOnStateChange;
        }
        if (isStateful(this.chipIcon)) {
            state |= this.chipIcon.setState(iArr);
        }
        if (isStateful(this.checkedIcon)) {
            state |= this.checkedIcon.setState(iArr);
        }
        if (isStateful(this.closeIcon)) {
            state |= this.closeIcon.setState(iArr2);
        }
        if (state) {
            invalidateSelf();
        }
        if (z2) {
            d();
        }
        return state;
    }

    public void setCheckedIconVisible(boolean z2) {
        if (this.checkedIconVisible != z2) {
            boolean zShowsCheckedIcon = showsCheckedIcon();
            this.checkedIconVisible = z2;
            boolean zShowsCheckedIcon2 = showsCheckedIcon();
            if (zShowsCheckedIcon != zShowsCheckedIcon2) {
                if (zShowsCheckedIcon2) {
                    applyChildDrawable(this.checkedIcon);
                } else {
                    unapplyChildDrawable(this.checkedIcon);
                }
                invalidateSelf();
                d();
            }
        }
    }

    public void setChipIconVisible(boolean z2) {
        if (this.chipIconVisible != z2) {
            boolean zShowsChipIcon = showsChipIcon();
            this.chipIconVisible = z2;
            boolean zShowsChipIcon2 = showsChipIcon();
            if (zShowsChipIcon != zShowsChipIcon2) {
                if (zShowsChipIcon2) {
                    applyChildDrawable(this.chipIcon);
                } else {
                    unapplyChildDrawable(this.chipIcon);
                }
                invalidateSelf();
                d();
            }
        }
    }

    public void setCloseIconVisible(boolean z2) {
        if (this.closeIconVisible != z2) {
            boolean zShowsCloseIcon = showsCloseIcon();
            this.closeIconVisible = z2;
            boolean zShowsCloseIcon2 = showsCloseIcon();
            if (zShowsCloseIcon != zShowsCloseIcon2) {
                if (zShowsCloseIcon2) {
                    applyChildDrawable(this.closeIcon);
                } else {
                    unapplyChildDrawable(this.closeIcon);
                }
                invalidateSelf();
                d();
            }
        }
    }

    private static boolean isStateful(@Nullable ColorStateList colorStateList) {
        return colorStateList != null && colorStateList.isStateful();
    }

    private static boolean isStateful(@Nullable Drawable drawable) {
        return drawable != null && drawable.isStateful();
    }

    private static boolean isStateful(@Nullable TextAppearance textAppearance) {
        ColorStateList colorStateList;
        return (textAppearance == null || (colorStateList = textAppearance.textColor) == null || !colorStateList.isStateful()) ? false : true;
    }
}
