package com.google.android.material.internal;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import androidx.annotation.ColorInt;
import androidx.annotation.Dimension;
import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.core.graphics.ColorUtils;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes3.dex */
public class CircularBorderDrawable extends Drawable {
    private static final float DRAW_STROKE_WIDTH_MULTIPLE = 1.3333f;

    /* renamed from: a, reason: collision with root package name */
    final Paint f13478a;
    private ColorStateList borderTint;

    @ColorInt
    private int bottomInnerStrokeColor;

    @ColorInt
    private int bottomOuterStrokeColor;

    @ColorInt
    private int currentBorderTintColor;

    /* renamed from: e, reason: collision with root package name */
    float f13482e;

    @FloatRange(from = 0.0d, to = 360.0d)
    private float rotation;

    @ColorInt
    private int topInnerStrokeColor;

    @ColorInt
    private int topOuterStrokeColor;

    /* renamed from: b, reason: collision with root package name */
    final Rect f13479b = new Rect();

    /* renamed from: c, reason: collision with root package name */
    final RectF f13480c = new RectF();

    /* renamed from: d, reason: collision with root package name */
    final CircularBorderState f13481d = new CircularBorderState();
    private boolean invalidateShader = true;

    private class CircularBorderState extends Drawable.ConstantState {
        private CircularBorderState() {
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            return 0;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        @NonNull
        public Drawable newDrawable() {
            return CircularBorderDrawable.this;
        }
    }

    public CircularBorderDrawable() {
        Paint paint = new Paint(1);
        this.f13478a = paint;
        paint.setStyle(Paint.Style.STROKE);
    }

    private Shader createGradientShader() {
        copyBounds(this.f13479b);
        float fHeight = this.f13482e / r1.height();
        return new LinearGradient(0.0f, r1.top, 0.0f, r1.bottom, new int[]{ColorUtils.compositeColors(this.topOuterStrokeColor, this.currentBorderTintColor), ColorUtils.compositeColors(this.topInnerStrokeColor, this.currentBorderTintColor), ColorUtils.compositeColors(ColorUtils.setAlphaComponent(this.topInnerStrokeColor, 0), this.currentBorderTintColor), ColorUtils.compositeColors(ColorUtils.setAlphaComponent(this.bottomInnerStrokeColor, 0), this.currentBorderTintColor), ColorUtils.compositeColors(this.bottomInnerStrokeColor, this.currentBorderTintColor), ColorUtils.compositeColors(this.bottomOuterStrokeColor, this.currentBorderTintColor)}, new float[]{0.0f, fHeight, 0.5f, 0.5f, 1.0f - fHeight, 1.0f}, Shader.TileMode.CLAMP);
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        if (this.invalidateShader) {
            this.f13478a.setShader(createGradientShader());
            this.invalidateShader = false;
        }
        float strokeWidth = this.f13478a.getStrokeWidth() / 2.0f;
        RectF rectF = this.f13480c;
        copyBounds(this.f13479b);
        rectF.set(this.f13479b);
        rectF.left += strokeWidth;
        rectF.top += strokeWidth;
        rectF.right -= strokeWidth;
        rectF.bottom -= strokeWidth;
        canvas.save();
        canvas.rotate(this.rotation, rectF.centerX(), rectF.centerY());
        canvas.drawOval(rectF, this.f13478a);
        canvas.restore();
    }

    @Override // android.graphics.drawable.Drawable
    @Nullable
    public Drawable.ConstantState getConstantState() {
        return this.f13481d;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return this.f13482e > 0.0f ? -3 : -2;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean getPadding(Rect rect) {
        int iRound = Math.round(this.f13482e);
        rect.set(iRound, iRound, iRound, iRound);
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        ColorStateList colorStateList = this.borderTint;
        return (colorStateList != null && colorStateList.isStateful()) || super.isStateful();
    }

    @Override // android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect rect) {
        this.invalidateShader = true;
    }

    @Override // android.graphics.drawable.Drawable
    protected boolean onStateChange(int[] iArr) {
        int colorForState;
        ColorStateList colorStateList = this.borderTint;
        if (colorStateList != null && (colorForState = colorStateList.getColorForState(iArr, this.currentBorderTintColor)) != this.currentBorderTintColor) {
            this.invalidateShader = true;
            this.currentBorderTintColor = colorForState;
        }
        if (this.invalidateShader) {
            invalidateSelf();
        }
        return this.invalidateShader;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(@IntRange(from = 0, to = 255) int i2) {
        this.f13478a.setAlpha(i2);
        invalidateSelf();
    }

    public void setBorderTint(ColorStateList colorStateList) {
        if (colorStateList != null) {
            this.currentBorderTintColor = colorStateList.getColorForState(getState(), this.currentBorderTintColor);
        }
        this.borderTint = colorStateList;
        this.invalidateShader = true;
        invalidateSelf();
    }

    public void setBorderWidth(@Dimension float f2) {
        if (this.f13482e != f2) {
            this.f13482e = f2;
            this.f13478a.setStrokeWidth(f2 * DRAW_STROKE_WIDTH_MULTIPLE);
            this.invalidateShader = true;
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.f13478a.setColorFilter(colorFilter);
        invalidateSelf();
    }

    public void setGradientColors(@ColorInt int i2, @ColorInt int i3, @ColorInt int i4, @ColorInt int i5) {
        this.topOuterStrokeColor = i2;
        this.topInnerStrokeColor = i3;
        this.bottomOuterStrokeColor = i4;
        this.bottomInnerStrokeColor = i5;
    }

    public final void setRotation(float f2) {
        if (f2 != this.rotation) {
            this.rotation = f2;
            invalidateSelf();
        }
    }
}
