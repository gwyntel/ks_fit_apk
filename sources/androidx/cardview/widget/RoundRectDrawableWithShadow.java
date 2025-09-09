package androidx.cardview.widget;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import androidx.cardview.R;

/* loaded from: classes.dex */
class RoundRectDrawableWithShadow extends Drawable {
    private static final double COS_45 = Math.cos(Math.toRadians(45.0d));
    private static final float SHADOW_MULTIPLIER = 1.5f;

    /* renamed from: a, reason: collision with root package name */
    static RoundRectHelper f2484a;
    private ColorStateList mBackground;
    private final RectF mCardBounds;
    private float mCornerRadius;
    private Paint mCornerShadowPaint;
    private Path mCornerShadowPath;
    private Paint mEdgeShadowPaint;
    private final int mInsetShadow;
    private float mRawMaxShadowSize;
    private float mRawShadowSize;
    private final int mShadowEndColor;
    private float mShadowSize;
    private final int mShadowStartColor;
    private boolean mDirty = true;
    private boolean mAddPaddingForCorners = true;
    private boolean mPrintedShadowClipWarning = false;
    private Paint mPaint = new Paint(5);

    interface RoundRectHelper {
        void drawRoundRect(Canvas canvas, RectF rectF, float f2, Paint paint);
    }

    RoundRectDrawableWithShadow(Resources resources, ColorStateList colorStateList, float f2, float f3, float f4) {
        this.mShadowStartColor = resources.getColor(R.color.cardview_shadow_start_color);
        this.mShadowEndColor = resources.getColor(R.color.cardview_shadow_end_color);
        this.mInsetShadow = resources.getDimensionPixelSize(R.dimen.cardview_compat_inset_shadow);
        setBackground(colorStateList);
        Paint paint = new Paint(5);
        this.mCornerShadowPaint = paint;
        paint.setStyle(Paint.Style.FILL);
        this.mCornerRadius = (int) (f2 + 0.5f);
        this.mCardBounds = new RectF();
        Paint paint2 = new Paint(this.mCornerShadowPaint);
        this.mEdgeShadowPaint = paint2;
        paint2.setAntiAlias(false);
        setShadowSize(f3, f4);
    }

    static float a(float f2, float f3, boolean z2) {
        return z2 ? (float) (f2 + ((1.0d - COS_45) * f3)) : f2;
    }

    static float b(float f2, float f3, boolean z2) {
        return z2 ? (float) ((f2 * SHADOW_MULTIPLIER) + ((1.0d - COS_45) * f3)) : f2 * SHADOW_MULTIPLIER;
    }

    private void buildComponents(Rect rect) {
        float f2 = this.mRawMaxShadowSize;
        float f3 = SHADOW_MULTIPLIER * f2;
        this.mCardBounds.set(rect.left + f2, rect.top + f3, rect.right - f2, rect.bottom - f3);
        buildShadowCorners();
    }

    private void buildShadowCorners() {
        float f2 = this.mCornerRadius;
        RectF rectF = new RectF(-f2, -f2, f2, f2);
        RectF rectF2 = new RectF(rectF);
        float f3 = this.mShadowSize;
        rectF2.inset(-f3, -f3);
        Path path = this.mCornerShadowPath;
        if (path == null) {
            this.mCornerShadowPath = new Path();
        } else {
            path.reset();
        }
        this.mCornerShadowPath.setFillType(Path.FillType.EVEN_ODD);
        this.mCornerShadowPath.moveTo(-this.mCornerRadius, 0.0f);
        this.mCornerShadowPath.rLineTo(-this.mShadowSize, 0.0f);
        this.mCornerShadowPath.arcTo(rectF2, 180.0f, 90.0f, false);
        this.mCornerShadowPath.arcTo(rectF, 270.0f, -90.0f, false);
        this.mCornerShadowPath.close();
        float f4 = this.mCornerRadius;
        float f5 = f4 / (this.mShadowSize + f4);
        Paint paint = this.mCornerShadowPaint;
        float f6 = this.mCornerRadius + this.mShadowSize;
        int i2 = this.mShadowStartColor;
        Shader.TileMode tileMode = Shader.TileMode.CLAMP;
        paint.setShader(new RadialGradient(0.0f, 0.0f, f6, new int[]{i2, i2, this.mShadowEndColor}, new float[]{0.0f, f5, 1.0f}, tileMode));
        Paint paint2 = this.mEdgeShadowPaint;
        float f7 = this.mCornerRadius;
        float f8 = this.mShadowSize;
        float f9 = (-f7) + f8;
        float f10 = (-f7) - f8;
        int i3 = this.mShadowStartColor;
        paint2.setShader(new LinearGradient(0.0f, f9, 0.0f, f10, new int[]{i3, i3, this.mShadowEndColor}, new float[]{0.0f, 0.5f, 1.0f}, tileMode));
        this.mEdgeShadowPaint.setAntiAlias(false);
    }

    private void drawShadow(Canvas canvas) {
        float f2 = this.mCornerRadius;
        float f3 = (-f2) - this.mShadowSize;
        float f4 = f2 + this.mInsetShadow + (this.mRawShadowSize / 2.0f);
        float f5 = f4 * 2.0f;
        boolean z2 = this.mCardBounds.width() - f5 > 0.0f;
        boolean z3 = this.mCardBounds.height() - f5 > 0.0f;
        int iSave = canvas.save();
        RectF rectF = this.mCardBounds;
        canvas.translate(rectF.left + f4, rectF.top + f4);
        canvas.drawPath(this.mCornerShadowPath, this.mCornerShadowPaint);
        if (z2) {
            canvas.drawRect(0.0f, f3, this.mCardBounds.width() - f5, -this.mCornerRadius, this.mEdgeShadowPaint);
        }
        canvas.restoreToCount(iSave);
        int iSave2 = canvas.save();
        RectF rectF2 = this.mCardBounds;
        canvas.translate(rectF2.right - f4, rectF2.bottom - f4);
        canvas.rotate(180.0f);
        canvas.drawPath(this.mCornerShadowPath, this.mCornerShadowPaint);
        if (z2) {
            canvas.drawRect(0.0f, f3, this.mCardBounds.width() - f5, (-this.mCornerRadius) + this.mShadowSize, this.mEdgeShadowPaint);
        }
        canvas.restoreToCount(iSave2);
        int iSave3 = canvas.save();
        RectF rectF3 = this.mCardBounds;
        canvas.translate(rectF3.left + f4, rectF3.bottom - f4);
        canvas.rotate(270.0f);
        canvas.drawPath(this.mCornerShadowPath, this.mCornerShadowPaint);
        if (z3) {
            canvas.drawRect(0.0f, f3, this.mCardBounds.height() - f5, -this.mCornerRadius, this.mEdgeShadowPaint);
        }
        canvas.restoreToCount(iSave3);
        int iSave4 = canvas.save();
        RectF rectF4 = this.mCardBounds;
        canvas.translate(rectF4.right - f4, rectF4.top + f4);
        canvas.rotate(90.0f);
        canvas.drawPath(this.mCornerShadowPath, this.mCornerShadowPaint);
        if (z3) {
            canvas.drawRect(0.0f, f3, this.mCardBounds.height() - f5, -this.mCornerRadius, this.mEdgeShadowPaint);
        }
        canvas.restoreToCount(iSave4);
    }

    private void setBackground(ColorStateList colorStateList) {
        if (colorStateList == null) {
            colorStateList = ColorStateList.valueOf(0);
        }
        this.mBackground = colorStateList;
        this.mPaint.setColor(colorStateList.getColorForState(getState(), this.mBackground.getDefaultColor()));
    }

    private void setShadowSize(float f2, float f3) {
        if (f2 < 0.0f) {
            throw new IllegalArgumentException("Invalid shadow size " + f2 + ". Must be >= 0");
        }
        if (f3 < 0.0f) {
            throw new IllegalArgumentException("Invalid max shadow size " + f3 + ". Must be >= 0");
        }
        float even = toEven(f2);
        float even2 = toEven(f3);
        if (even > even2) {
            if (!this.mPrintedShadowClipWarning) {
                this.mPrintedShadowClipWarning = true;
            }
            even = even2;
        }
        if (this.mRawShadowSize == even && this.mRawMaxShadowSize == even2) {
            return;
        }
        this.mRawShadowSize = even;
        this.mRawMaxShadowSize = even2;
        this.mShadowSize = (int) ((even * SHADOW_MULTIPLIER) + this.mInsetShadow + 0.5f);
        this.mDirty = true;
        invalidateSelf();
    }

    private int toEven(float f2) {
        int i2 = (int) (f2 + 0.5f);
        return i2 % 2 == 1 ? i2 - 1 : i2;
    }

    ColorStateList c() {
        return this.mBackground;
    }

    float d() {
        return this.mCornerRadius;
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        if (this.mDirty) {
            buildComponents(getBounds());
            this.mDirty = false;
        }
        canvas.translate(0.0f, this.mRawShadowSize / 2.0f);
        drawShadow(canvas);
        canvas.translate(0.0f, (-this.mRawShadowSize) / 2.0f);
        f2484a.drawRoundRect(canvas, this.mCardBounds, this.mCornerRadius, this.mPaint);
    }

    void e(Rect rect) {
        getPadding(rect);
    }

    float f() {
        return this.mRawMaxShadowSize;
    }

    float g() {
        float f2 = this.mRawMaxShadowSize;
        return (Math.max(f2, this.mCornerRadius + this.mInsetShadow + ((f2 * SHADOW_MULTIPLIER) / 2.0f)) * 2.0f) + (((this.mRawMaxShadowSize * SHADOW_MULTIPLIER) + this.mInsetShadow) * 2.0f);
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean getPadding(Rect rect) {
        int iCeil = (int) Math.ceil(b(this.mRawMaxShadowSize, this.mCornerRadius, this.mAddPaddingForCorners));
        int iCeil2 = (int) Math.ceil(a(this.mRawMaxShadowSize, this.mCornerRadius, this.mAddPaddingForCorners));
        rect.set(iCeil2, iCeil, iCeil2, iCeil);
        return true;
    }

    float h() {
        float f2 = this.mRawMaxShadowSize;
        return (Math.max(f2, this.mCornerRadius + this.mInsetShadow + (f2 / 2.0f)) * 2.0f) + ((this.mRawMaxShadowSize + this.mInsetShadow) * 2.0f);
    }

    float i() {
        return this.mRawShadowSize;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        ColorStateList colorStateList = this.mBackground;
        return (colorStateList != null && colorStateList.isStateful()) || super.isStateful();
    }

    void j(boolean z2) {
        this.mAddPaddingForCorners = z2;
        invalidateSelf();
    }

    void k(ColorStateList colorStateList) {
        setBackground(colorStateList);
        invalidateSelf();
    }

    void l(float f2) {
        if (f2 < 0.0f) {
            throw new IllegalArgumentException("Invalid radius " + f2 + ". Must be >= 0");
        }
        float f3 = (int) (f2 + 0.5f);
        if (this.mCornerRadius == f3) {
            return;
        }
        this.mCornerRadius = f3;
        this.mDirty = true;
        invalidateSelf();
    }

    void m(float f2) {
        setShadowSize(this.mRawShadowSize, f2);
    }

    void n(float f2) {
        setShadowSize(f2, this.mRawMaxShadowSize);
    }

    @Override // android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        this.mDirty = true;
    }

    @Override // android.graphics.drawable.Drawable
    protected boolean onStateChange(int[] iArr) {
        ColorStateList colorStateList = this.mBackground;
        int colorForState = colorStateList.getColorForState(iArr, colorStateList.getDefaultColor());
        if (this.mPaint.getColor() == colorForState) {
            return false;
        }
        this.mPaint.setColor(colorForState);
        this.mDirty = true;
        invalidateSelf();
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i2) {
        this.mPaint.setAlpha(i2);
        this.mCornerShadowPaint.setAlpha(i2);
        this.mEdgeShadowPaint.setAlpha(i2);
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter);
    }
}
