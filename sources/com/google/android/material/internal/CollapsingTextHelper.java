package com.google.android.material.internal;

import android.R;
import android.animation.TimeInterpolator;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;
import androidx.annotation.ColorInt;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.widget.TintTypedArray;
import androidx.core.math.MathUtils;
import androidx.core.text.TextDirectionHeuristicsCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import com.google.android.material.animation.AnimationUtils;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes3.dex */
public final class CollapsingTextHelper {
    private static final boolean DEBUG_DRAW = false;
    private boolean boundsChanged;
    private final Rect collapsedBounds;
    private float collapsedDrawX;
    private float collapsedDrawY;
    private int collapsedShadowColor;
    private float collapsedShadowDx;
    private float collapsedShadowDy;
    private float collapsedShadowRadius;
    private ColorStateList collapsedTextColor;
    private Typeface collapsedTypeface;
    private final RectF currentBounds;
    private float currentDrawX;
    private float currentDrawY;
    private float currentTextSize;
    private Typeface currentTypeface;
    private boolean drawTitle;
    private final Rect expandedBounds;
    private float expandedDrawX;
    private float expandedDrawY;
    private float expandedFraction;
    private int expandedShadowColor;
    private float expandedShadowDx;
    private float expandedShadowDy;
    private float expandedShadowRadius;
    private ColorStateList expandedTextColor;
    private Bitmap expandedTitleTexture;
    private Typeface expandedTypeface;
    private boolean isRtl;
    private TimeInterpolator positionInterpolator;
    private float scale;
    private int[] state;
    private CharSequence text;
    private final TextPaint textPaint;
    private TimeInterpolator textSizeInterpolator;
    private CharSequence textToDraw;
    private float textureAscent;
    private float textureDescent;
    private Paint texturePaint;
    private final TextPaint tmpPaint;
    private boolean useTexture;
    private final View view;
    private static final boolean USE_SCALING_TEXTURE = false;
    private static final Paint DEBUG_DRAW_PAINT = null;
    private int expandedTextGravity = 16;
    private int collapsedTextGravity = 16;
    private float expandedTextSize = 15.0f;
    private float collapsedTextSize = 15.0f;

    public CollapsingTextHelper(View view) {
        this.view = view;
        TextPaint textPaint = new TextPaint(129);
        this.textPaint = textPaint;
        this.tmpPaint = new TextPaint(textPaint);
        this.collapsedBounds = new Rect();
        this.expandedBounds = new Rect();
        this.currentBounds = new RectF();
    }

    private static int blendColors(int i2, int i3, float f2) {
        float f3 = 1.0f - f2;
        return Color.argb((int) ((Color.alpha(i2) * f3) + (Color.alpha(i3) * f2)), (int) ((Color.red(i2) * f3) + (Color.red(i3) * f2)), (int) ((Color.green(i2) * f3) + (Color.green(i3) * f2)), (int) ((Color.blue(i2) * f3) + (Color.blue(i3) * f2)));
    }

    private void calculateBaseOffsets() {
        float f2 = this.currentTextSize;
        calculateUsingTextSize(this.collapsedTextSize);
        CharSequence charSequence = this.textToDraw;
        float fMeasureText = charSequence != null ? this.textPaint.measureText(charSequence, 0, charSequence.length()) : 0.0f;
        int absoluteGravity = GravityCompat.getAbsoluteGravity(this.collapsedTextGravity, this.isRtl ? 1 : 0);
        int i2 = absoluteGravity & 112;
        if (i2 == 48) {
            this.collapsedDrawY = this.collapsedBounds.top - this.textPaint.ascent();
        } else if (i2 != 80) {
            this.collapsedDrawY = this.collapsedBounds.centerY() + (((this.textPaint.descent() - this.textPaint.ascent()) / 2.0f) - this.textPaint.descent());
        } else {
            this.collapsedDrawY = this.collapsedBounds.bottom;
        }
        int i3 = absoluteGravity & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK;
        if (i3 == 1) {
            this.collapsedDrawX = this.collapsedBounds.centerX() - (fMeasureText / 2.0f);
        } else if (i3 != 5) {
            this.collapsedDrawX = this.collapsedBounds.left;
        } else {
            this.collapsedDrawX = this.collapsedBounds.right - fMeasureText;
        }
        calculateUsingTextSize(this.expandedTextSize);
        CharSequence charSequence2 = this.textToDraw;
        float fMeasureText2 = charSequence2 != null ? this.textPaint.measureText(charSequence2, 0, charSequence2.length()) : 0.0f;
        int absoluteGravity2 = GravityCompat.getAbsoluteGravity(this.expandedTextGravity, this.isRtl ? 1 : 0);
        int i4 = absoluteGravity2 & 112;
        if (i4 == 48) {
            this.expandedDrawY = this.expandedBounds.top - this.textPaint.ascent();
        } else if (i4 != 80) {
            this.expandedDrawY = this.expandedBounds.centerY() + (((this.textPaint.descent() - this.textPaint.ascent()) / 2.0f) - this.textPaint.descent());
        } else {
            this.expandedDrawY = this.expandedBounds.bottom;
        }
        int i5 = absoluteGravity2 & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK;
        if (i5 == 1) {
            this.expandedDrawX = this.expandedBounds.centerX() - (fMeasureText2 / 2.0f);
        } else if (i5 != 5) {
            this.expandedDrawX = this.expandedBounds.left;
        } else {
            this.expandedDrawX = this.expandedBounds.right - fMeasureText2;
        }
        clearTexture();
        setInterpolatedTextSize(f2);
    }

    private void calculateCurrentOffsets() {
        calculateOffsets(this.expandedFraction);
    }

    private boolean calculateIsRtl(CharSequence charSequence) {
        return (ViewCompat.getLayoutDirection(this.view) == 1 ? TextDirectionHeuristicsCompat.FIRSTSTRONG_RTL : TextDirectionHeuristicsCompat.FIRSTSTRONG_LTR).isRtl(charSequence, 0, charSequence.length());
    }

    private void calculateOffsets(float f2) {
        interpolateBounds(f2);
        this.currentDrawX = lerp(this.expandedDrawX, this.collapsedDrawX, f2, this.positionInterpolator);
        this.currentDrawY = lerp(this.expandedDrawY, this.collapsedDrawY, f2, this.positionInterpolator);
        setInterpolatedTextSize(lerp(this.expandedTextSize, this.collapsedTextSize, f2, this.textSizeInterpolator));
        if (this.collapsedTextColor != this.expandedTextColor) {
            this.textPaint.setColor(blendColors(getCurrentExpandedTextColor(), getCurrentCollapsedTextColor(), f2));
        } else {
            this.textPaint.setColor(getCurrentCollapsedTextColor());
        }
        this.textPaint.setShadowLayer(lerp(this.expandedShadowRadius, this.collapsedShadowRadius, f2, null), lerp(this.expandedShadowDx, this.collapsedShadowDx, f2, null), lerp(this.expandedShadowDy, this.collapsedShadowDy, f2, null), blendColors(this.expandedShadowColor, this.collapsedShadowColor, f2));
        ViewCompat.postInvalidateOnAnimation(this.view);
    }

    private void calculateUsingTextSize(float f2) {
        boolean z2;
        float f3;
        boolean z3;
        if (this.text == null) {
            return;
        }
        float fWidth = this.collapsedBounds.width();
        float fWidth2 = this.expandedBounds.width();
        if (isClose(f2, this.collapsedTextSize)) {
            f3 = this.collapsedTextSize;
            this.scale = 1.0f;
            Typeface typeface = this.currentTypeface;
            Typeface typeface2 = this.collapsedTypeface;
            if (typeface != typeface2) {
                this.currentTypeface = typeface2;
                z3 = true;
            } else {
                z3 = false;
            }
        } else {
            float f4 = this.expandedTextSize;
            Typeface typeface3 = this.currentTypeface;
            Typeface typeface4 = this.expandedTypeface;
            if (typeface3 != typeface4) {
                this.currentTypeface = typeface4;
                z2 = true;
            } else {
                z2 = false;
            }
            if (isClose(f2, f4)) {
                this.scale = 1.0f;
            } else {
                this.scale = f2 / this.expandedTextSize;
            }
            float f5 = this.collapsedTextSize / this.expandedTextSize;
            fWidth = fWidth2 * f5 > fWidth ? Math.min(fWidth / f5, fWidth2) : fWidth2;
            f3 = f4;
            z3 = z2;
        }
        if (fWidth > 0.0f) {
            z3 = this.currentTextSize != f3 || this.boundsChanged || z3;
            this.currentTextSize = f3;
            this.boundsChanged = false;
        }
        if (this.textToDraw == null || z3) {
            this.textPaint.setTextSize(this.currentTextSize);
            this.textPaint.setTypeface(this.currentTypeface);
            this.textPaint.setLinearText(this.scale != 1.0f);
            CharSequence charSequenceEllipsize = TextUtils.ellipsize(this.text, this.textPaint, fWidth, TextUtils.TruncateAt.END);
            if (TextUtils.equals(charSequenceEllipsize, this.textToDraw)) {
                return;
            }
            this.textToDraw = charSequenceEllipsize;
            this.isRtl = calculateIsRtl(charSequenceEllipsize);
        }
    }

    private void clearTexture() {
        Bitmap bitmap = this.expandedTitleTexture;
        if (bitmap != null) {
            bitmap.recycle();
            this.expandedTitleTexture = null;
        }
    }

    private void ensureExpandedTexture() {
        if (this.expandedTitleTexture != null || this.expandedBounds.isEmpty() || TextUtils.isEmpty(this.textToDraw)) {
            return;
        }
        calculateOffsets(0.0f);
        this.textureAscent = this.textPaint.ascent();
        this.textureDescent = this.textPaint.descent();
        TextPaint textPaint = this.textPaint;
        CharSequence charSequence = this.textToDraw;
        int iRound = Math.round(textPaint.measureText(charSequence, 0, charSequence.length()));
        int iRound2 = Math.round(this.textureDescent - this.textureAscent);
        if (iRound <= 0 || iRound2 <= 0) {
            return;
        }
        this.expandedTitleTexture = Bitmap.createBitmap(iRound, iRound2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(this.expandedTitleTexture);
        CharSequence charSequence2 = this.textToDraw;
        canvas.drawText(charSequence2, 0, charSequence2.length(), 0.0f, iRound2 - this.textPaint.descent(), this.textPaint);
        if (this.texturePaint == null) {
            this.texturePaint = new Paint(3);
        }
    }

    @ColorInt
    private int getCurrentExpandedTextColor() {
        int[] iArr = this.state;
        return iArr != null ? this.expandedTextColor.getColorForState(iArr, 0) : this.expandedTextColor.getDefaultColor();
    }

    private void getTextPaintCollapsed(TextPaint textPaint) {
        textPaint.setTextSize(this.collapsedTextSize);
        textPaint.setTypeface(this.collapsedTypeface);
    }

    private void interpolateBounds(float f2) {
        this.currentBounds.left = lerp(this.expandedBounds.left, this.collapsedBounds.left, f2, this.positionInterpolator);
        this.currentBounds.top = lerp(this.expandedDrawY, this.collapsedDrawY, f2, this.positionInterpolator);
        this.currentBounds.right = lerp(this.expandedBounds.right, this.collapsedBounds.right, f2, this.positionInterpolator);
        this.currentBounds.bottom = lerp(this.expandedBounds.bottom, this.collapsedBounds.bottom, f2, this.positionInterpolator);
    }

    private static boolean isClose(float f2, float f3) {
        return Math.abs(f2 - f3) < 0.001f;
    }

    private static float lerp(float f2, float f3, float f4, TimeInterpolator timeInterpolator) {
        if (timeInterpolator != null) {
            f4 = timeInterpolator.getInterpolation(f4);
        }
        return AnimationUtils.lerp(f2, f3, f4);
    }

    private Typeface readFontFamilyTypeface(int i2) throws Resources.NotFoundException {
        TypedArray typedArrayObtainStyledAttributes = this.view.getContext().obtainStyledAttributes(i2, new int[]{R.attr.fontFamily});
        try {
            String string = typedArrayObtainStyledAttributes.getString(0);
            if (string != null) {
                return Typeface.create(string, 0);
            }
            typedArrayObtainStyledAttributes.recycle();
            return null;
        } finally {
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    private static boolean rectEquals(Rect rect, int i2, int i3, int i4, int i5) {
        return rect.left == i2 && rect.top == i3 && rect.right == i4 && rect.bottom == i5;
    }

    private void setInterpolatedTextSize(float f2) {
        calculateUsingTextSize(f2);
        boolean z2 = USE_SCALING_TEXTURE && this.scale != 1.0f;
        this.useTexture = z2;
        if (z2) {
            ensureExpandedTexture();
        }
        ViewCompat.postInvalidateOnAnimation(this.view);
    }

    void a() {
        this.drawTitle = this.collapsedBounds.width() > 0 && this.collapsedBounds.height() > 0 && this.expandedBounds.width() > 0 && this.expandedBounds.height() > 0;
    }

    public float calculateCollapsedTextWidth() {
        if (this.text == null) {
            return 0.0f;
        }
        getTextPaintCollapsed(this.tmpPaint);
        TextPaint textPaint = this.tmpPaint;
        CharSequence charSequence = this.text;
        return textPaint.measureText(charSequence, 0, charSequence.length());
    }

    public void draw(Canvas canvas) {
        float fAscent;
        int iSave = canvas.save();
        if (this.textToDraw != null && this.drawTitle) {
            float f2 = this.currentDrawX;
            float f3 = this.currentDrawY;
            boolean z2 = this.useTexture && this.expandedTitleTexture != null;
            if (z2) {
                fAscent = this.textureAscent * this.scale;
            } else {
                fAscent = this.textPaint.ascent() * this.scale;
                this.textPaint.descent();
            }
            if (z2) {
                f3 += fAscent;
            }
            float f4 = f3;
            float f5 = this.scale;
            if (f5 != 1.0f) {
                canvas.scale(f5, f5, f2, f4);
            }
            if (z2) {
                canvas.drawBitmap(this.expandedTitleTexture, f2, f4, this.texturePaint);
            } else {
                CharSequence charSequence = this.textToDraw;
                canvas.drawText(charSequence, 0, charSequence.length(), f2, f4, this.textPaint);
            }
        }
        canvas.restoreToCount(iSave);
    }

    public void getCollapsedTextActualBounds(RectF rectF) {
        boolean zCalculateIsRtl = calculateIsRtl(this.text);
        Rect rect = this.collapsedBounds;
        float fCalculateCollapsedTextWidth = !zCalculateIsRtl ? rect.left : rect.right - calculateCollapsedTextWidth();
        rectF.left = fCalculateCollapsedTextWidth;
        Rect rect2 = this.collapsedBounds;
        rectF.top = rect2.top;
        rectF.right = !zCalculateIsRtl ? fCalculateCollapsedTextWidth + calculateCollapsedTextWidth() : rect2.right;
        rectF.bottom = this.collapsedBounds.top + getCollapsedTextHeight();
    }

    public ColorStateList getCollapsedTextColor() {
        return this.collapsedTextColor;
    }

    public int getCollapsedTextGravity() {
        return this.collapsedTextGravity;
    }

    public float getCollapsedTextHeight() {
        getTextPaintCollapsed(this.tmpPaint);
        return -this.tmpPaint.ascent();
    }

    public float getCollapsedTextSize() {
        return this.collapsedTextSize;
    }

    public Typeface getCollapsedTypeface() {
        Typeface typeface = this.collapsedTypeface;
        return typeface != null ? typeface : Typeface.DEFAULT;
    }

    @ColorInt
    @VisibleForTesting
    public int getCurrentCollapsedTextColor() {
        int[] iArr = this.state;
        return iArr != null ? this.collapsedTextColor.getColorForState(iArr, 0) : this.collapsedTextColor.getDefaultColor();
    }

    public ColorStateList getExpandedTextColor() {
        return this.expandedTextColor;
    }

    public int getExpandedTextGravity() {
        return this.expandedTextGravity;
    }

    public float getExpandedTextSize() {
        return this.expandedTextSize;
    }

    public Typeface getExpandedTypeface() {
        Typeface typeface = this.expandedTypeface;
        return typeface != null ? typeface : Typeface.DEFAULT;
    }

    public float getExpansionFraction() {
        return this.expandedFraction;
    }

    public CharSequence getText() {
        return this.text;
    }

    public final boolean isStateful() {
        ColorStateList colorStateList;
        ColorStateList colorStateList2 = this.collapsedTextColor;
        return (colorStateList2 != null && colorStateList2.isStateful()) || ((colorStateList = this.expandedTextColor) != null && colorStateList.isStateful());
    }

    public void recalculate() {
        if (this.view.getHeight() <= 0 || this.view.getWidth() <= 0) {
            return;
        }
        calculateBaseOffsets();
        calculateCurrentOffsets();
    }

    public void setCollapsedBounds(int i2, int i3, int i4, int i5) {
        if (rectEquals(this.collapsedBounds, i2, i3, i4, i5)) {
            return;
        }
        this.collapsedBounds.set(i2, i3, i4, i5);
        this.boundsChanged = true;
        a();
    }

    public void setCollapsedTextAppearance(int i2) {
        TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(this.view.getContext(), i2, androidx.appcompat.R.styleable.TextAppearance);
        if (tintTypedArrayObtainStyledAttributes.hasValue(androidx.appcompat.R.styleable.TextAppearance_android_textColor)) {
            this.collapsedTextColor = tintTypedArrayObtainStyledAttributes.getColorStateList(androidx.appcompat.R.styleable.TextAppearance_android_textColor);
        }
        if (tintTypedArrayObtainStyledAttributes.hasValue(androidx.appcompat.R.styleable.TextAppearance_android_textSize)) {
            this.collapsedTextSize = tintTypedArrayObtainStyledAttributes.getDimensionPixelSize(androidx.appcompat.R.styleable.TextAppearance_android_textSize, (int) this.collapsedTextSize);
        }
        this.collapsedShadowColor = tintTypedArrayObtainStyledAttributes.getInt(androidx.appcompat.R.styleable.TextAppearance_android_shadowColor, 0);
        this.collapsedShadowDx = tintTypedArrayObtainStyledAttributes.getFloat(androidx.appcompat.R.styleable.TextAppearance_android_shadowDx, 0.0f);
        this.collapsedShadowDy = tintTypedArrayObtainStyledAttributes.getFloat(androidx.appcompat.R.styleable.TextAppearance_android_shadowDy, 0.0f);
        this.collapsedShadowRadius = tintTypedArrayObtainStyledAttributes.getFloat(androidx.appcompat.R.styleable.TextAppearance_android_shadowRadius, 0.0f);
        tintTypedArrayObtainStyledAttributes.recycle();
        this.collapsedTypeface = readFontFamilyTypeface(i2);
        recalculate();
    }

    public void setCollapsedTextColor(ColorStateList colorStateList) {
        if (this.collapsedTextColor != colorStateList) {
            this.collapsedTextColor = colorStateList;
            recalculate();
        }
    }

    public void setCollapsedTextGravity(int i2) {
        if (this.collapsedTextGravity != i2) {
            this.collapsedTextGravity = i2;
            recalculate();
        }
    }

    public void setCollapsedTextSize(float f2) {
        if (this.collapsedTextSize != f2) {
            this.collapsedTextSize = f2;
            recalculate();
        }
    }

    public void setCollapsedTypeface(Typeface typeface) {
        if (this.collapsedTypeface != typeface) {
            this.collapsedTypeface = typeface;
            recalculate();
        }
    }

    public void setExpandedBounds(int i2, int i3, int i4, int i5) {
        if (rectEquals(this.expandedBounds, i2, i3, i4, i5)) {
            return;
        }
        this.expandedBounds.set(i2, i3, i4, i5);
        this.boundsChanged = true;
        a();
    }

    public void setExpandedTextAppearance(int i2) {
        TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(this.view.getContext(), i2, androidx.appcompat.R.styleable.TextAppearance);
        if (tintTypedArrayObtainStyledAttributes.hasValue(androidx.appcompat.R.styleable.TextAppearance_android_textColor)) {
            this.expandedTextColor = tintTypedArrayObtainStyledAttributes.getColorStateList(androidx.appcompat.R.styleable.TextAppearance_android_textColor);
        }
        if (tintTypedArrayObtainStyledAttributes.hasValue(androidx.appcompat.R.styleable.TextAppearance_android_textSize)) {
            this.expandedTextSize = tintTypedArrayObtainStyledAttributes.getDimensionPixelSize(androidx.appcompat.R.styleable.TextAppearance_android_textSize, (int) this.expandedTextSize);
        }
        this.expandedShadowColor = tintTypedArrayObtainStyledAttributes.getInt(androidx.appcompat.R.styleable.TextAppearance_android_shadowColor, 0);
        this.expandedShadowDx = tintTypedArrayObtainStyledAttributes.getFloat(androidx.appcompat.R.styleable.TextAppearance_android_shadowDx, 0.0f);
        this.expandedShadowDy = tintTypedArrayObtainStyledAttributes.getFloat(androidx.appcompat.R.styleable.TextAppearance_android_shadowDy, 0.0f);
        this.expandedShadowRadius = tintTypedArrayObtainStyledAttributes.getFloat(androidx.appcompat.R.styleable.TextAppearance_android_shadowRadius, 0.0f);
        tintTypedArrayObtainStyledAttributes.recycle();
        this.expandedTypeface = readFontFamilyTypeface(i2);
        recalculate();
    }

    public void setExpandedTextColor(ColorStateList colorStateList) {
        if (this.expandedTextColor != colorStateList) {
            this.expandedTextColor = colorStateList;
            recalculate();
        }
    }

    public void setExpandedTextGravity(int i2) {
        if (this.expandedTextGravity != i2) {
            this.expandedTextGravity = i2;
            recalculate();
        }
    }

    public void setExpandedTextSize(float f2) {
        if (this.expandedTextSize != f2) {
            this.expandedTextSize = f2;
            recalculate();
        }
    }

    public void setExpandedTypeface(Typeface typeface) {
        if (this.expandedTypeface != typeface) {
            this.expandedTypeface = typeface;
            recalculate();
        }
    }

    public void setExpansionFraction(float f2) {
        float fClamp = MathUtils.clamp(f2, 0.0f, 1.0f);
        if (fClamp != this.expandedFraction) {
            this.expandedFraction = fClamp;
            calculateCurrentOffsets();
        }
    }

    public void setPositionInterpolator(TimeInterpolator timeInterpolator) {
        this.positionInterpolator = timeInterpolator;
        recalculate();
    }

    public final boolean setState(int[] iArr) {
        this.state = iArr;
        if (!isStateful()) {
            return false;
        }
        recalculate();
        return true;
    }

    public void setText(CharSequence charSequence) {
        if (charSequence == null || !charSequence.equals(this.text)) {
            this.text = charSequence;
            this.textToDraw = null;
            clearTexture();
            recalculate();
        }
    }

    public void setTextSizeInterpolator(TimeInterpolator timeInterpolator) {
        this.textSizeInterpolator = timeInterpolator;
        recalculate();
    }

    public void setTypefaces(Typeface typeface) {
        this.expandedTypeface = typeface;
        this.collapsedTypeface = typeface;
        recalculate();
    }
}
